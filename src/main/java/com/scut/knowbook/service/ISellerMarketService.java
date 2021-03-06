package com.scut.knowbook.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scut.knowbook.model.Seller_market;

public interface ISellerMarketService{

	public Seller_market findById(Long id);
	
	public Seller_market findByBookName(String bookName);
	
	public Seller_market findByBookPrice(int bookPrice);
	
	public Seller_market findByBookOwnerId(String bookOwnerId);
	
	public Seller_market findByBookSituation(String bookSituation);
	
	public Seller_market findByOwnerOnlineTime(String ownerOnlineTime);
	
	public Seller_market findByBookAuthor(String bookAuthor);
	
	public Page<Seller_market> findByBookClass(String bookClass,Pageable pageable);
	
	public Page<Seller_market> findBySellingWay(String sellingWay,Pageable pageable);
	
	public Page<Seller_market> findBySellingWayAndBookClass(String sellingWay,String bookClass, Pageable pageable);
	
	public Seller_market save(Seller_market seller_market);
	
	public void delete(Seller_market seller_market);
	
	public Page<Seller_market> findAllByPage(Pageable pageable);
	
	public Object findByUser_infoLocationLike(String locationMode,Integer locationRange);
	
	public Object findByUserinfoLocationLike(String locationMode,Integer locationRange);
}
