package com.scut.knowbook.control;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IUserInfoService;
import com.scut.knowbook.service.IUserService;

@Controller
@RequestMapping(value="/sellerMarket")
public class SellControl {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="userInfoService")
	private IUserInfoService userInfoService;
	/*
	 * @author makai
	 * 接口名称：查看卖书记录
	 */
	@RequestMapping(value="/myBuyBook", method=RequestMethod.GET, produces = "text/html;charset=utf-8")
	public @ResponseBody JsonPacked myBuyBook(HttpServletRequest request, HttpServletResponse response) {

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
		Set<Seller_market> seller_markets = user_info.getSeller_market();
		jsonPacked.setResult("success");
		if (seller_markets != null) {
			jsonPacked.getResultSet().add(seller_markets);
		}
		return jsonPacked;		
	}
}
