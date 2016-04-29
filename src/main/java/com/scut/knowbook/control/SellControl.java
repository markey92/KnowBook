package com.scut.knowbook.control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.jstl.AndOperator;
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
import com.scut.knowbook.service.ISellerMarketService;
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
	
	@Resource(name="sellerMarketService")
	private ISellerMarketService sellerMarketService;
	
	/**
	 * 按id查找某本确定的书
	 */
	@RequestMapping(value="/detailBuy",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailBuy(@RequestParam long BuyBookId, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		JsonPacked jsonPacked=new JsonPacked();
		//获取session中的phoneNumber
		String phoneNumber = (String) request.getSession().getAttribute("phoneNumber");
		//检查参数phone_number是否为空
		if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
			logger.info("phone_number不存在");
			jsonPacked.setResult("user,unlogined");
			return jsonPacked;
		}
        if(BuyBookId <= 0){
			logger.info("WantBookId不存在");
			jsonPacked.setResult("WantBookId,<=0");
			return jsonPacked;
        }
	    Seller_market seller_market = sellerMarketService.findById(BuyBookId);
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(seller_market);
		return jsonPacked;
	}
	/**
	 * 按类型和售卖方法查看所有心愿
	 */
	@RequestMapping(value="/fragmentBuySome",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentBuySome(@RequestParam String sellType, @RequestParam String Type, @RequestParam String pageNow, @RequestParam String pageSize, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		if (pageNow == null || StringUtils.isEmpty(pageNow)) {
			pageNow = "1";
		}
		if (pageSize == null || StringUtils.isEmpty(pageNow)) {
			pageSize = "10";
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
		Page sell_market_page = null;
		if (sellType != null && Type != null && !StringUtils.isEmpty(sellType) && !StringUtils.isEmpty(Type)) {
			jsonPacked.setResult("success");
			sell_market_page = sellerMarketService.findBySellingWayAndBookClass(sellType, Type, new PageRequest(Integer.parseInt(pageNow)-1, Integer.parseInt(pageSize)));
			jsonPacked.getResultSet().add(sell_market_page);
			return jsonPacked;
		}
		if (sellType != null && !StringUtils.isEmpty(sellType)) {
			jsonPacked.setResult("success");
			sell_market_page = sellerMarketService.findBySellingWay(sellType, new PageRequest(Integer.parseInt(pageNow)-1, Integer.parseInt(pageSize)));
			jsonPacked.getResultSet().add(sell_market_page);
			return jsonPacked;
		}
		if (Type != null && !StringUtils.isEmpty(Type)) {
			jsonPacked.setResult("success");
			sell_market_page = sellerMarketService.findByBookClass(Type, new PageRequest(Integer.parseInt(pageNow)-1, Integer.parseInt(pageSize)));
			jsonPacked.getResultSet().add(sell_market_page);
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		sell_market_page = sellerMarketService.findAllByPage(new PageRequest(Integer.parseInt(pageNow)-1, Integer.parseInt(pageSize)));
		jsonPacked.getResultSet().add(sell_market_page);
		return jsonPacked;
	}
	/**
	 * 查看所有卖的书
	 */
	@RequestMapping(value="/fragmentBuy",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentBuy(@RequestParam String pageNow, @RequestParam String pageSize, HttpServletRequest request, HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{

		if (pageNow == null || StringUtils.isEmpty(pageNow)) {
			pageNow = "1";
		}
		if (pageSize == null || StringUtils.isEmpty(pageNow)) {
			pageSize = "10";
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

		Page sell_market_page = sellerMarketService.findAllByPage(new PageRequest(Integer.parseInt(pageNow)-1, Integer.parseInt(pageSize)));
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(sell_market_page);
		return jsonPacked;
	}
	/*
	 * @author makai
	 * 创建心愿，
	 * 接收请求参数，然后save心愿
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/createBuy", method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody JsonPacked createBuy(@RequestParam String BuyBookName,@RequestParam String BuyBookPicture,
			@RequestParam String BuyBookAuthor, @RequestParam String Type,@RequestParam String BuyBookDescript,
			@RequestParam String SellType,@RequestParam String price, @RequestParam String newOrold,HttpServletRequest request, HttpServletResponse response){
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
		if (BuyBookPicture == null || StringUtils.isEmpty(BuyBookPicture)) {
			logger.info("书籍图片不能为空");
			jsonPacked.setResult("BuyBookPicture,null");
			return jsonPacked;
		}
		//验证各参数合法性
		//书籍作者
		if (BuyBookAuthor == null || StringUtils.isEmpty(BuyBookAuthor)) {
			logger.info("书籍作者不能为空");
			jsonPacked.setResult("BuyBookAuthor,null");
			return jsonPacked;
		}
		//书籍名
		if (BuyBookName == null || StringUtils.isEmpty(BuyBookName)) {
			logger.info("书籍名不能为空");
			jsonPacked.setResult("BuyBookName,null");
			return jsonPacked;
		}
		//卖书方法
		if (SellType == null || StringUtils.isEmpty(SellType)) {
			logger.info("不能为空");
			jsonPacked.setResult("SellType,null");
			return jsonPacked;
		}
		//书籍类型
		if (Type == null || StringUtils.isEmpty(Type)) {
			logger.info("书籍类型不能为空");
			jsonPacked.setResult("Type,null");
			return jsonPacked;
		}
		//价格
		if (price == null || StringUtils.isEmpty(price)) {
			logger.info("价格不能为空");
			jsonPacked.setResult("price,null");
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
		//新旧程度
		if (newOrold == null || StringUtils.isEmpty(newOrold)) {
			logger.info("新旧不能为空");
			jsonPacked.setResult("newOrold,null");
			return jsonPacked;
		}
		//书籍描述
		if (BuyBookDescript == null || StringUtils.isEmpty(BuyBookDescript)) {
			logger.info("书籍描述不能为空");
			jsonPacked.setResult("BuyBookDescript,null");
			return jsonPacked;
		}		
		Seller_market seller_market = new Seller_market();
		seller_market.setBookAuthor(BuyBookAuthor);
		seller_market.setBookClass(Type);
		seller_market.setBookDescript(BuyBookDescript);
		seller_market.setBookName(BuyBookName);
		seller_market.setBookPicture(BuyBookPicture);
		seller_market.setBookPrice(Double.parseDouble(price));
		seller_market.setBookSituation(newOrold);
		seller_market.setCreateBy(phoneNumber);
		seller_market.setCreateDate(new Timestamp(System.currentTimeMillis()));
		seller_market.setSellingWay(SellType);
		seller_market.setUser_info(user_info);
		//这两个字段应该是没用的
		seller_market.setBookOwnerId(user.getId().toString());
		seller_market.setOwnerOnlineTime("24小时");
		//保存
		sellerMarketService.save(seller_market);
		userInfoService.save(user_info);
		Page<Seller_market> sellMarkeyPage = sellerMarketService.findAllByPage(new PageRequest(0, 10));
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(sellMarkeyPage);
		return jsonPacked;
	}
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
