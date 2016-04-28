package com.scut.knowbook.control;

import java.awt.print.Pageable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import com.scut.knowbook.model.Seller_market;
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
	private IUserService userService;
	
	@Resource(name="userInfoService")
	private IUserInfoService userInfoService;
	
	@Resource(name="wishPlatformService")
	private IWishPlatformService wishPlatformService;

	/**
	 * 按类型查看所有心愿
	 */
	@RequestMapping(value="/fragmentWantSome",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentWantSome(@RequestParam String type, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

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
		Page wish_platform_page = wishPlatformService.findByBookClassPage(type, new PageRequest(0, 10));
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(wish_platform_page);
		return jsonPacked;
	}
	/**
	 * 查看所有心愿
	 */
	@RequestMapping(value="/fragmentWant",method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentWant(HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}

		Page wish_platform_page = wishPlatformService.findAllByPage(new PageRequest(0, 10));
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(wish_platform_page);
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
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
		User user = userService.findByPhoneNumber(phoneNumber);
		if (user == null) {
			logger.info("user不存在");
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		User_info user_info = user.getUser_info();
		Set<Wish_platform> wish_platforms = user_info.getWish_platform();
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(wish_platforms);
		return jsonPacked;
	}
	/*
	 * @author makai
	 * 创建心愿，
	 * 接收请求参数，然后save心愿
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/createWant", method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody JsonPacked createWant(@RequestParam String WantBookName,@RequestParam String WantBookPicture,
			@RequestParam String WantBookAuthor, @RequestParam String WantBookPay,@RequestParam String WantBookType,
			@RequestParam String WantBookContent,@RequestParam String WishPostiton, HttpServletRequest request, HttpServletResponse response){
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
		if (WantBookPicture == null || StringUtils.isEmpty(WantBookPicture)) {
			logger.info("书籍图片不能为空");
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
		wish_platform.setBookAuthor(WantBookAuthor);
		wish_platform.setBookClass(WantBookType);
		wish_platform.setBookName(WantBookName);
		wish_platform.setCreateBy(user.getPhoneNumber());
		wish_platform.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wish_platform.setUser_info(user_info);
		wish_platform.setWishContent(WantBookContent);
		wish_platform.setWishLocation(WishPostiton);
		wish_platform.setWishPay(WantBookPay);
		//保存
		wishPlatformService.save(wish_platform);
		userInfoService.save(user_info);
		Page<Wish_platform> wishPlatformPage = wishPlatformService.findAllByPage(new PageRequest(0, 10));
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(wishPlatformPage);
		return jsonPacked;
	}

}
