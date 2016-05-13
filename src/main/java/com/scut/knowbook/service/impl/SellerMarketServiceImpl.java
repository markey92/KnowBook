package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.ISellerMarketDao;
import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.service.ISellerMarketService;

@Service("sellerMarketService")
public class SellerMarketServiceImpl implements ISellerMarketService {

	@Autowired
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

}
