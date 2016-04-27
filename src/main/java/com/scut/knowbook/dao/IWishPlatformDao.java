package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.Wish_platform;

public interface IWishPlatformDao extends CrudRepository<Wish_platform, Long> {

	public Wish_platform findById(Long id);
	
	public Wish_platform findByBookName(String bookName);
	
	public Wish_platform findByWisherId(String wisherId);
	
	public Wish_platform findByBookAuthor(String bookAuthor);
	
	public Wish_platform findByBookClass(String bookClass);
	
	@SuppressWarnings("unchecked")
	public Wish_platform save(Wish_platform wish_platform);
}
