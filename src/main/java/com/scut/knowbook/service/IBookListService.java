package com.scut.knowbook.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scut.knowbook.model.BookList;

public interface IBookListService {

	public BookList findById(Long id);
	
	public BookList findByCreater_id(String creater_id);
	
	public BookList save(BookList bookList);
	
	public void delete(BookList bookList);
	
	public Iterable<BookList> findAll();
	
	public Page<BookList> findAllBookList(Pageable pageable);
	
	public List<BookList> findMostBookList(Pageable pageable);
	
	public List<BookList> findByCreateDateBetween(Timestamp max, Timestamp min,Pageable pageable);
	
	public List<BookList> findHotBookList(Timestamp max, Timestamp min,Pageable pageable);
}
