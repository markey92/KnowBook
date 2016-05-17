package com.scut.knowbook.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IFileUpLoadService;
import com.scut.knowbook.service.IUserInfoService;
import com.scut.knowbook.service.IUserService;

@Controller
@RequestMapping(value="/users")
public class LoginController {

	/**
	 * 用于用户登录、注册、查询和修改个人信息
	 */
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="userInfoService")
	private IUserInfoService userInfoService;
	
	@Resource(name="fileUpLoadService")
	private IFileUpLoadService fileUpLoadService;
	/**
	 * 用户登录验证
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object login(@RequestParam ("phoneNumber") String phoneNumber, @RequestParam ("password") String password, HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		User user = userService.findByPhoneNumber(phoneNumber);
		JsonPacked jsonPacked=new JsonPacked();
		if(user == null){
			jsonPacked.setResult("user,null");
		}else if (!password.equals(user.getPassword())) {
			jsonPacked.setResult("password,error");
		}else{
			jsonPacked.setResult("success");
			request.getSession().setAttribute("phoneNumber", phoneNumber);
		}
		logger.info("user:" + phoneNumber + " password:" + password+"用户登录成功");
		logger.info("登录ip为："+request.getRemoteAddr());
		return jsonPacked;
	}
	/**
	 * 用户注册验证
	 */
	@RequestMapping(value="/registe", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public @ResponseBody Object registe(String phoneNumber, String password,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("注册ip为："+request.getRemoteAddr());
		JsonPacked jsonPacked=new JsonPacked();
		
		if(phoneNumber==null||password==null){
			logger.info("null");
			logger.info(request.getRemoteAddr());
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		if(userService.findByPhoneNumber(phoneNumber)!=null){
			logger.info("该号码已被注册过");
			jsonPacked.setResult("error");
			return jsonPacked;
		}
		User user=new User();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		
		User_info user_info=new User_info();
		user_info.setUser(user);
		userInfoService.save(user_info);
		userService.save(user);
		
		logger.info("user:" + phoneNumber + " password:" + password);
		jsonPacked.setResult("success");
		logger.info("用户注册成功");
		return jsonPacked;
	}
	
	/**
	 * 用户数据添加
	 */
	@RequestMapping(value="/loginAdd", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object loginAdd(@RequestParam ("userName") String userName, @RequestParam ("sex") String sex,MultipartFile headPicture,String location,
										String qq,String weixin,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
		logger.info("用户："+phoneNumber);
		if(phoneNumber==null){
			jsonPacked.setResult("notLogin");
			return jsonPacked;
		}
		if(sex.length()>1){
			logger.info("性别长度超过1");
			jsonPacked.setResult("error");
			return jsonPacked;
		}
		User user=userService.findByPhoneNumber(phoneNumber);
		if(!sex.isEmpty()){
			user.setSex(sex);
		}
		if(!userName.isEmpty()){
			user.setUserName(userName);
		}
		userService.save(user);
		User_info user_info=user.getUser_info();
//		String savePath=request.getSession().getServletContext().getRealPath("/static/images").toLowerCase();
		String savePath="e:\\javass\\knowbook\\src\\main\\webapp\\static\\images";
		String fileName=System.currentTimeMillis()+phoneNumber+".jpg";
		logger.info("保存路径为："+savePath);
		String url=null;
		try {
			url = fileUpLoadService.anotherFileUpload(headPicture, savePath, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			jsonPacked.setResult("fileTooBig");
			return jsonPacked;
		}
		user_info.setHeadPicture(url);
		String geohashLocation=userInfoService.geohashEncode(location, 40);
		user_info.setLocation(geohashLocation);
		logger.info(geohashLocation);
		if(!qq.isEmpty()){
			user_info.setQq(qq);
		}
		if(!weixin.isEmpty()){
			user_info.setWeixin(weixin);
		}
		userInfoService.save(user_info);
		userService.save(user);
		jsonPacked.setResult("success");
		Map<String, String> map=new ConcurrentHashMap<String, String>();
		map.put("headPicture", url);
		jsonPacked.getResultSet().add(map);
		logger.info("用户添加数据成功");
		return jsonPacked;
	}
	/**
	 * 重置密码
	 */
	@RequestMapping(value="/relogin", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object relogin(@RequestParam ("phoneNumber") String phoneNumber, @RequestParam ("password") String password) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		user.setPassword(password);
		userService.save(user);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * 返回user_info信息
	 */
	@RequestMapping(value="/getinfo", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object getInfo(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
		JsonPacked jsonPacked=new JsonPacked();
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		User_info user_info =user.getUser_info();
		if(user_info==null){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		else{
			jsonPacked.getResultSet().add(user_info);
		}
		logger.info("user_info:" + phoneNumber);
		return jsonPacked;
	}
	/*
	 * 获取头像接口
	 * 前端发起请求，无提交参数
	 * 后台返回一个headPicture的url
	 */
	@RequestMapping(value="/myPicture", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object myPicture(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		User user = userService.findByPhoneNumber(phoneNumber);
		if (user == null) {
			logger.info("user不存在");
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		User_info user_info = user.getUser_info();
		String pictureUrl = user_info.getHeadPicture();
		if (pictureUrl == null || StringUtils.isEmpty(pictureUrl)) {
			logger.info("头像不存在");
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(phoneNumber);
		return jsonPacked;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/myLocation", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object myLocation(String location,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		User user = userService.findByPhoneNumber(phoneNumber);
		if (user == null) {
			logger.info("user不存在");
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		User_info user_info = user.getUser_info();
		
		//这是要进行geohash算法
		String geoHashLocation=userInfoService.geohashEncode(location, 40);
		user_info.setLocation(geoHashLocation);
		userInfoService.save(user_info);
		logger.info("geohash编码前的location为："+location);
		logger.info("geohash编码后的location为："+geoHashLocation);
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(phoneNumber);
		return jsonPacked;
	}
	@RequestMapping(value="/getPeopleAround", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object getPeopleAround(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		User user = userService.findByPhoneNumber(phoneNumber);
		if (user == null) {
			logger.info("user不存在");
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		User_info user_info = user.getUser_info();
		
		//这是要进行geohash解码算法
		if(user_info.getLocation()==null){
			jsonPacked.setResult("error");
			return jsonPacked;
		}
		String locationMode=user_info.getLocation().substring(0, 5);
		Page<User_info> user_infos=userInfoService.peopleAround(locationMode, new PageRequest(0, 10));
		jsonPacked.setResult("success");
		for(User_info userInfoTemp:user_infos){
			String decode=userInfoService.geohashDecode(userInfoTemp.getLocation());
			userInfoTemp.setLocation(decode);
			jsonPacked.getResultSet().add(userInfoTemp);
		}
		return jsonPacked;
	}
	/**
	 * 图片上传测试接口，用来测试安卓端与后台能否成功连接，后期可删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/doUpload", method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object doUploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request)  throws JsonGenerationException, JsonMappingException, IOException{
		logger.info(request.getRemoteAddr()+"上传了文件:"+file.getOriginalFilename());
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		String savePath=request.getSession().getServletContext().getRealPath("/static/images").toLowerCase();
//		String savePath="e:\\tomcat\\webapps\\knowbook\\static\\images";
		String fileName=System.currentTimeMillis()+phoneNumber+".jpg";
		logger.info("保存路径为："+savePath);
		String url=null;
		try {
			url = fileUpLoadService.anotherFileUpload(file, savePath, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			jsonPacked.setResult("fileTooBig");
			return jsonPacked;
		}
//		String url="/images/"+phoneNumber+file.getOriginalFilename();
		if(url.isEmpty()||url==null||url.equals("")){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		jsonPacked.setResult("successs");
		jsonPacked.getResultSet().add(url);
		return jsonPacked;
	}
}
