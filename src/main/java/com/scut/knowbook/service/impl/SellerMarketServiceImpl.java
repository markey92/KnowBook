package com.scut.knowbook.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.ISellerMarketDao;
import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.ISellerMarketService;

@Service("sellerMarketService")
public class SellerMarketServiceImpl implements ISellerMarketService {

	@Autowired
	private ISellerMarketDao sellerMarketDao;

	private Logger logger = Logger.getLogger(getClass());
	
	private JsonPacked jsonPacked=new JsonPacked();
	
	private List<Seller_market> seller_markets=new ArrayList<Seller_market>();
	
	public Seller_market findById(Long id) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findById(id);
	}

	public Seller_market findByBookName(String bookName) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookName(bookName);
	}

	public Seller_market findByBookPrice(int bookPrice) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookPrice(bookPrice);
	}

	public Seller_market findByBookOwnerId(String bookOwnerId) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookOwnerId(bookOwnerId);
	}

	public Seller_market findByBookSituation(String bookSituation) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookSituation(bookSituation);
	}

	public Seller_market findByOwnerOnlineTime(String ownerOnlineTime) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByOwnerOnlineTime(ownerOnlineTime);
	}

	public Seller_market findByBookAuthor(String bookAuthor) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookAuthor(bookAuthor);
	}


	@Transactional
	public Seller_market save(Seller_market seller_market) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.save(seller_market);
	}

	@Transactional
	public void delete(Seller_market seller_market) {
		// TODO Auto-generated method stub
		this.sellerMarketDao.delete(seller_market);
	}

	public Page<Seller_market> findAllByPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return sellerMarketDao.findAll(pageable);
	}

	public Page<Seller_market> findByBookClass(String bookClass,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return sellerMarketDao.findByBookClass(bookClass, pageable);
	}

	public Page<Seller_market> findBySellingWay(String sellingWay,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return sellerMarketDao.findBySellingWay(sellingWay, pageable);
	}

	public Page<Seller_market> findBySellingWayAndBookClass(String sellingWay,
			String bookClass, Pageable pageable) {
		// TODO Auto-generated method stub
		return sellerMarketDao.findBySellingWayAndBookClass(sellingWay, bookClass, pageable);
	}

	public Object findByUser_infoLocationLike(String locationMode,Integer locationRange) {
		// TODO Auto-generated method stub
		if(locationMode==null||locationMode.isEmpty()||locationRange>8||locationRange<1){
			jsonPacked.setResult("error");
			return jsonPacked;
		}
		List<Seller_market> sellerMarkets2;
		if(locationRange==8){
			sellerMarkets2=this.sellerMarketDao.findByUserinfoLocationLike("%"+locationMode.substring(0,locationRange)+"%");
			logger.info("sellerMarketservice方法操作中");
			logger.info(sellerMarkets2);
		}
		else{
			List<Seller_market> sellerMarkets=this.sellerMarketDao.findByUserinfoLocationLike("%"+locationMode.substring(0,locationRange+1)+"%");
			sellerMarkets2=this.sellerMarketDao.findByUserinfoLocationLike("%"+locationMode.substring(0,locationRange)+"%");
			logger.info("sellerMarketservice方法操作中"+locationRange);
			logger.info(sellerMarkets);
			logger.info(sellerMarkets2);
			sellerMarkets2.removeAll(sellerMarkets);
			logger.info(sellerMarkets2);
		}
		logger.info("sellerMarket方法插入数据前的jsonpacked");
		logger.info(jsonPacked.getResultSet().size());
		for(Seller_market seller_market:sellerMarkets2){
			jsonPacked.getResultSet().add(seller_market);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("BuyBookUser", seller_market.getUserinfo().getUser().getUserName());
			map.put("BuyBookUserSex", seller_market.getUserinfo().getUser().getSex());
			map.put("locationRange", locationRange);
			jsonPacked.getResultSet().add(map);
		}
		if(jsonPacked.getResultSet().size()<1){
			jsonPacked.setResult("nodata");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		JsonPacked jsonPackedResult=jsonPacked;
		this.jsonPacked=new JsonPacked();
		return jsonPackedResult;
	}

	public Object findByUserinfoLocationLike(String locationMode,Integer locationRange) {
		// TODO Auto-generated method stub
		if(locationMode==null||locationMode.isEmpty()){
			jsonPacked.setResult("error");
			return jsonPacked;
		}
		if(locationMode.length()<locationRange){
			if(jsonPacked.getResultSet().size()<1){
				jsonPacked.setResult("nodata");
				return jsonPacked;
			}
			jsonPacked.setResult("success");
			JsonPacked jsonPackedResult=jsonPacked;
			jsonPacked=new JsonPacked();
			return jsonPackedResult;
		}
		List<Seller_market> sellerMarkets=this.sellerMarketDao.findByUserinfoLocationLike("%"+locationMode+"%");
		for(Seller_market seller_market:sellerMarkets){
			if(!(jsonPacked.getResultSet().contains(seller_market))){
				jsonPacked.getResultSet().add(seller_market);
				Map<String, Object> map=new ConcurrentHashMap<String, Object>();
				map.put("locationRange", locationMode.length());
				map.put("BuyBookUser", seller_market.getUserinfo().getUser().getUserName());
				map.put("BuyBookUserSex", seller_market.getUserinfo().getUser().getSex());
				jsonPacked.getResultSet().add(map);
			}
		}
//		this.seller_markets.addAll(this.sellerMarketDao.findByUserinfoLocationLike("%"+locationMode+"%"));
		return this.findByUserinfoLocationLike(locationMode.substring(0,locationMode.length()-1),locationRange);
	}


}
