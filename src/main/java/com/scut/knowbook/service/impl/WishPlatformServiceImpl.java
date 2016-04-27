package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.knowbook.dao.IWishPlatformDao;
import com.scut.knowbook.model.Wish_platform;
import com.scut.knowbook.service.IWishPlatformService;

@Service("wishPlatformService")
public class WishPlatformServiceImpl implements IWishPlatformService {

//	@Autowired
	private IWishPlatformDao wishPlatformDao;
	
	public Wish_platform findById(Long id) {
		// TODO Auto-generated method stub
		return this.wishPlatformDao.findById(id);
	}

	public Wish_platform findByBookName(String bookName) {
		// TODO Auto-generated method stub
		return this.wishPlatformDao.findByBookName(bookName);
	}

	public Wish_platform findByWisherId(String wisherId) {
		// TODO Auto-generated method stub
		return this.wishPlatformDao.findByWisherId(wisherId);
	}

	public Wish_platform findByBookAuthor(String bookAuthor) {
		// TODO Auto-generated method stub
		return this.wishPlatformDao.findByBookAuthor(bookAuthor);
	}

	public Wish_platform findByBookClass(String bookClass) {
		// TODO Auto-generated method stub
		return this.findByBookClass(bookClass);
	}

	public Wish_platform save(Wish_platform wish_platform) {
		// TODO Auto-generated method stub
		return this.wishPlatformDao.save(wish_platform);
	}

	public void delete(Wish_platform wish_platform) {
		// TODO Auto-generated method stub
		this.wishPlatformDao.delete(wish_platform);
	}

}
