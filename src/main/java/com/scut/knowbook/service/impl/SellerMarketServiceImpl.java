package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.knowbook.dao.ISellerMarketDao;
import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.service.ISellerMarketService;

@Service("sellerMarketService")
public class SellerMarketServiceImpl implements ISellerMarketService {

//	@Autowired
	private ISellerMarketDao sellerMarketDao;

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

	public Seller_market findByBookClass(String bookClass) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findByBookClass(bookClass);
	}

	public Seller_market findBySellingWay(String sellingWay) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.findBySellingWay(sellingWay);
	}

	public Seller_market save(Seller_market seller_market) {
		// TODO Auto-generated method stub
		return this.sellerMarketDao.save(seller_market);
	}

	public void delete(Seller_market seller_market) {
		// TODO Auto-generated method stub
		this.sellerMarketDao.delete(seller_market);
	}

}
