package com.scut.knowbook.control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.Wish_platform;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IUserInfoService;
import com.scut.knowbook.service.IUserService;
import com.scut.knowbook.service.IWishPlatformService;


@Controller
@RequestMapping(value="/wish")
public class WishControl {

	private Logger logger = Logger.getLogger(getClass());
	@Resource(name="userService")
	private IUserService UserService;
	
	@Resource(name="userInfoService")
	private IUserInfoService userInfoService;
	
	@Resource(name="wishPlatformService")
	private IWishPlatformService wishPlatformService;
	
	/**
	 * 查看自己的心愿
	 */
	@RequestMapping(value="/mywish",method=RequestMethod.GET)
	public @ResponseBody Object mywish(String phoneNumber)throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		User user=UserService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		User_info user_info=user.getUser_info();
		if(user_info==null){
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		Set<Wish_platform> wish_platforms=user_info.getWish_platform();
		if(wish_platforms.isEmpty()){
			jsonPacked.setResult("wish,null");
			return jsonPacked;
		}
		for(Wish_platform wish_platform:wish_platforms){
			jsonPacked.getResultSet().add(wish_platform);
		}
		return jsonPacked;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
