package com.scut.knowbook.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.model.Seller_market;

public interface ISellerMarketDao extends CrudRepository<Seller_market, Long> {

	public Seller_market findById(Long id);
	
	public Seller_market findByBookName(String bookName);
	
	public Seller_market findByBookPrice(int bookPrice);
	
	public Seller_market findByBookOwnerId(String bookOwnerId);
	
	public Seller_market findByBookSituation(String bookSituation);
	
	public Seller_market findByOwnerOnlineTime(String ownerOnlineTime);
	
	public Seller_market findByBookAuthor(String bookAuthor);
	
	public Page<Seller_market> findByBookClass(String bookClass,Pageable pageable);
	
	public Page<Seller_market> findBySellingWay(String sellingWay,Pageable pageable);
	
	public Page<Seller_market> findBySellingWayAndBookClass(String sellingWay,String bookClass,Pageable pageable);
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Seller_market save(Seller_market seller_market);
	
	public Page<Seller_market> findAll(Pageable pageable);
	
	public List<Seller_market> findByUserinfoLocationLike(String locationMode);
}
