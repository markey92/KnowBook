package com.scut.knowbook.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.model.Wish_platform;

public interface IWishPlatformDao extends CrudRepository<Wish_platform, Long>, PagingAndSortingRepository<Wish_platform, Long> {

	public Wish_platform findById(Long id);
	
	public Wish_platform findByBookName(String bookName);
	
	public Wish_platform findByWisherId(String wisherId);
	
	public Wish_platform findByBookAuthor(String bookAuthor);
	
	public Wish_platform findByBookClass(String bookClass);
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Wish_platform save(Wish_platform wish_platform);
	
	public Page<Wish_platform> findAll(Pageable pageable);
    
	@Query("select w frow Wish_platform w where w.bookClass = ?1")
	public Page<Wish_platform> findByTypeAndPage(String type, Pageable pageable);
}
