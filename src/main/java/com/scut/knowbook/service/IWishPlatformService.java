package com.scut.knowbook.service;


import com.scut.knowbook.model.Wish_platform;

public interface IWishPlatformService  {

	public Wish_platform findById(Long id);
	
	public Wish_platform findByBookName(String bookName);
	
	public Wish_platform findByWisherId(String wisherId);
	
	public Wish_platform findByBookAuthor(String bookAuthor);
	
	public Wish_platform findByBookClass(String bookClass);
	
	public Wish_platform save(Wish_platform wish_platform);
	
	public void delete(Wish_platform wish_platform);
}
