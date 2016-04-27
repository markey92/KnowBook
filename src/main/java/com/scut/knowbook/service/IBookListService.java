package com.scut.knowbook.service;

import com.scut.knowbook.model.BookList;

public interface IBookListService {

	public BookList findById(Long id);
	
	public BookList findByCreater_id(String creater_id);
	
	public BookList save(BookList bookList);
	
	public void delete(BookList bookList);
	
	public Iterable<BookList> findAll();
}
