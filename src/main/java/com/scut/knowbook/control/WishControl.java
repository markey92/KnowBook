package com.scut.knowbook.control;

import java.awt.print.Pageable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.Wish_platform;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IFileUpLoadService;
import com.scut.knowbook.service.IUserInfoService;
import com.scut.knowbook.service.IUserService;
import com.scut.knowbook.service.IWishPlatformService;


@Controller
@RequestMapping(value="/wish")
public class WishControl {

	private Logger logger = Logger.getLogger(getClass());
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="userInfoService")
	private IUserInfoService userInfoService;
	
	@Resource(name="wishPlatformService")
	private IWishPlatformService wishPlatformService;
	
	@Resource(name="fileUpLoadService")
	private IFileUpLoadService fileUpLoadService;

	/**
	 * 按id查找某本确定的书
	 */
	@RequestMapping(value="/detailWant",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailWant(@RequestParam long WantBookId, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
        if(WantBookId <= 0){
			logger.info("WantBookId不存在");
			jsonPacked.setResult("WantBookId,<=0");
			return jsonPacked;
        }
	    Wish_platform wish_platform = wishPlatformService.findById(WantBookId);
		jsonPacked.setResult("success");
//		jsonPacked.getResultSet().add(wish_platform);
		Map<String, String> map=new ConcurrentHashMap<String, String>();
		map.put("WantBookPay", wish_platform.getWishPay());
		map.put("UserPicture", wish_platform.getUserinfo().getHeadPicture());
		map.put("qq", wish_platform.getUserinfo().getQq());
		map.put("weixin", wish_platform.getUserinfo().getWeixin());
		map.put("phoneNumber", wish_platform.getUserinfo().getUser().getPhoneNumber());
		jsonPacked.getResultSet().add(map);
		return jsonPacked;
	}
	/**
	 * 按类型查看所有心愿
	 */
	@RequestMapping(value="/fragmentWantSome",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentWantSome(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String type, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		if (page == null) {
			page = 0;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
        if(type == null || StringUtils.isEmpty(type)){
			logger.info("type不存在");
			jsonPacked.setResult("type,null");
			return jsonPacked;
        }
		Page<Wish_platform> wish_platform_page = wishPlatformService.findByBookClassPage(type, new PageRequest(page, pageSize));
		for(Wish_platform wish_platform : wish_platform_page){
			jsonPacked.getResultSet().add(wish_platform);
			Map<String, Object> map=new ConcurrentHashMap<String, Object>();
			map.put("UserName", wish_platform.getUserinfo().getUser().getUserName());
			map.put("UserSex", wish_platform.getUserinfo().getUser().getSex());
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
//		jsonPacked.getResultSet().add(wish_platform_page);
		return jsonPacked;
	}
	/**
	 * 查看所有心愿
	 */
	@RequestMapping(value="/fragmentWant",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentWant(@RequestParam Integer page, @RequestParam Integer pageSize, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		if (page == null ) {
			page = 0;
		}
		if (pageSize == null ) {
			pageSize = 10;
		}
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}

		Page<Wish_platform> wish_platform_page = wishPlatformService.findAllByPage(new PageRequest(page,pageSize));
		for(Wish_platform wish_platform:wish_platform_page){
			jsonPacked.getResultSet().add(wish_platform);
			Map<String, String> map=new ConcurrentHashMap<String, String>();
			map.put("UserName", wish_platform.getUserinfo().getUser().getUserName());
			map.put("UserSex", wish_platform.getUserinfo().getUser().getSex());
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
//		jsonPacked.getResultSet().add(wish_platform_page);
		return jsonPacked;
	}
	/**
	 * 查看自己的心愿
	 */
	@RequestMapping(value="/myWish",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object mywish(HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

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
		Set<Wish_platform> wish_platforms = user_info.getWish_platform();
		jsonPacked.setResult("success");
		for(Wish_platform wish_platform:wish_platforms){
			jsonPacked.getResultSet().add(wish_platform);
			Map<String, String> map=new ConcurrentHashMap<String, String>();
			map.put("UserName", wish_platform.getUserinfo().getUser().getUserName());
			map.put("UserSex", wish_platform.getUserinfo().getUser().getSex());
			jsonPacked.getResultSet().add(map);
		}
		return jsonPacked;
	}
	/*
	 * @author makai
	 * 创建心愿，
	 * 接收请求参数，然后save心愿
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/createWant", method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody JsonPacked createWant(@RequestParam String WantBookName,@RequestParam("WantBookPicture") MultipartFile WantBookPicture,
			@RequestParam String WantBookAuthor, @RequestParam String WantBookPay,@RequestParam String WantBookType,
			@RequestParam String WantBookContent,@RequestParam String WishPostiton, HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
		User user = userService.findByPhoneNumber(phoneNumber);
		User_info user_info = user.getUser_info();
		if (user == null) {
			logger.info("user不存在");
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		//书籍图片肯定不是这样的，后面再改
		logger.info("用户"+phoneNumber+"上传了"+WantBookPicture.getOriginalFilename()+"并试图创建心愿");
		String url=fileUpLoadService.FileUpload(WantBookPicture, System.currentTimeMillis()+phoneNumber+".jpg"); 
		if(url==null||url.equals("")||url.isEmpty()){
			logger.info("url为空");
			jsonPacked.setResult("WantBookPicture,null");
			return jsonPacked;
		}
		//验证各参数合法性
		//书籍作者
		if (WantBookAuthor == null || StringUtils.isEmpty(WantBookAuthor)) {
			logger.info("书籍作者不能为空");
			jsonPacked.setResult("WantBookAuthor,null");
			return jsonPacked;
		}
		//书籍名
		if (WantBookName == null || StringUtils.isEmpty(WantBookName)) {
			logger.info("书籍名不能为空");
			jsonPacked.setResult("wantbookname,null");
			return jsonPacked;
		}
		//报答方式
		if (WantBookPay == null || StringUtils.isEmpty(WantBookPay)) {
			logger.info("报答方式不能为空");
			jsonPacked.setResult("WantBookPay,null");
			return jsonPacked;
		}
		//书籍类型
		if (WantBookType == null || StringUtils.isEmpty(WantBookType)) {
			logger.info("书籍类型不能为空");
			jsonPacked.setResult("WantBookType,null");
			return jsonPacked;
		}
		//心愿内容
		if (WantBookContent == null || StringUtils.isEmpty(WantBookContent)) {
			logger.info("心愿内容不能为空");
			jsonPacked.setResult("WantBookContent,null");
			return jsonPacked;
		}
//		//联系qq和联系微信可以为空么？
//		
//		//联系qq
//		if (connectQQ == null || StringUtils.isEmpty(connectQQ)) {
//			logger.info("联系qq不能为空");
//			jsonPacked.setResult("connectQQ,null");
//			return jsonPacked;
//		}
//		//联系微信
//		if (connectWeiXin == null || StringUtils.isEmpty(connectWeiXin)) {
//			logger.info("书籍类型不能为空");
//			jsonPacked.setResult("connectWeiXin,null");
//			return jsonPacked;
//		}
		//位置
		if (WishPostiton == null || StringUtils.isEmpty(WishPostiton)) {
			logger.info("位置不能为空");
			jsonPacked.setResult("WishPostiton,null");
			return jsonPacked;
		}
		Wish_platform wish_platform = new Wish_platform();
		wish_platform.setWantBookPicture(url);
		wish_platform.setBookAuthor(WantBookAuthor);
		wish_platform.setBookClass(WantBookType);
		wish_platform.setBookName(WantBookName);
		wish_platform.setCreateBy(user.getPhoneNumber());
		wish_platform.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wish_platform.setUserinfo(user_info);
		wish_platform.setWishContent(WantBookContent);
		wish_platform.setWishPay(WantBookPay);
		
		
		//使用geohash算法将wishPostion换算成geohash编码，从而使经纬度坐标能用一个单一变量代替，在数据库可排序,最后一个参数默认为40
		String geoHashWishPostiton=wishPlatformService.geohashEncode(WishPostiton, 50);
		wish_platform.setWishLocation(geoHashWishPostiton);
		logger.info("输入的地理位置为："+WishPostiton);
		logger.info("写入的地理位置为："+geoHashWishPostiton);
		
		//保存
		wishPlatformService.save(wish_platform);
		userInfoService.save(user_info);
//		Page<Wish_platform> wishPlatformPage = wishPlatformService.findAllByPage(new PageRequest(0, 10));
		jsonPacked.setResult("success");
//		jsonPacked.getResultSet().add(wishPlatformPage);
		return jsonPacked;
	}

	/**
	 * 删除自己的心愿记录
	 */
	@RequestMapping(value="/deleteWish",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object deleteWish(@RequestParam long WantBookId, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
		Wish_platform wish_platform = wishPlatformService.findById(WantBookId);
        if(wish_platform ==null){
			logger.info("WantBookId不存在");
			jsonPacked.setResult("id,null");
			return jsonPacked;
        }
        wishPlatformService.delete(wish_platform);
        logger.info("成功删除id为"+WantBookId+"的心愿记录");
		jsonPacked.setResult("success");
		return jsonPacked;
	}
}
