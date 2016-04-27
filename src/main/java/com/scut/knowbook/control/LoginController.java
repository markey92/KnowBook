package com.scut.knowbook.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.OP.JsonPacked;
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
			jsonPacked.setResult("login,success");
			request.getSession().setAttribute("phoneNumber", phoneNumber);
		}
		logger.info("user:" + phoneNumber + " password:" + password);
		return jsonPacked;
	}
	/**
	 * 用户注册验证
	 */
	@RequestMapping(value="/registe", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object registe(@RequestParam ("phoneNumber") String phoneNumber, @RequestParam ("password") String password) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		User user=new User();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		
		User_info user_info=new User_info();
		user.setUser_info(user_info);
		userInfoService.save(user_info);
		userService.save(user);
		
		logger.info("user:" + phoneNumber + " password:" + password);
		jsonPacked.setResult("registe,success");
		return jsonPacked;
	}
	
	/**
	 * 用户数据添加
	 */
	@RequestMapping(value="/loginAdd", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object loginAdd(@RequestParam ("userName") String userName, @RequestParam ("sex") String sex,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
		if(phoneNumber==null){
			jsonPacked.setResult("notLogin");
			return jsonPacked;
		}
		User user=userService.findByPhoneNumber(phoneNumber);
		user.setSex(sex);
		user.setUserName(userName);
		userService.save(user);
		jsonPacked.setResult("loginAdd,success");
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
		jsonPacked.setResult("relogin,success");
		return jsonPacked;
	}
	
	@RequestMapping(value="/getinfo", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object getInfo(@RequestParam ("phoneNumber") String phoneNumber) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("getinfo,notexit");
			return jsonPacked;
		}
		User_info user_info =user.getUser_info();
		if(user_info==null){
			jsonPacked.setResult("getinfo,notexit");
			return jsonPacked;
		}
		else{
			jsonPacked.getResultSet().add(user_info);
		}
		logger.info("user_info:" + phoneNumber);
		return jsonPacked;
	}
}
