package com.scut.knowbook.service;


import com.scut.knowbook.model.Seller_market;
import com.scut.knowbook.model.User;

public interface ISellerMarketService{

	public Seller_market findById(Long id);
	
	public Seller_market findByBookName(String bookName);
	
	public Seller_market findByBookPrice(int bookPrice);
	
	public Seller_market findByBookOwnerId(String bookOwnerId);
	
	public Seller_market findByBookSituation(String bookSituation);
	
	public Seller_market findByOwnerOnlineTime(String ownerOnlineTime);
	
	public Seller_market findByBookAuthor(String bookAuthor);
	
	public Seller_market findByBookClass(String bookClass);
	
	public Seller_market findBySellingWay(String sellingWay);
	
	public Seller_market save(Seller_market seller_market);
	
	public void delete(Seller_market seller_market);
}
