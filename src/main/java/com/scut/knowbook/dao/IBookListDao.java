package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scut.knowbook.model.BookList;

public interface IBookListDao extends CrudRepository<BookList, Long> {

	public BookList findById(Long id);
	
	public BookList findByCreaterId(String createrId);
	
	public BookList findByBookListName(String bookListName);
	
	public BookList findByBookId(String bookId);
	
	@SuppressWarnings("unchecked")
	public BookList save(BookList bookList);

	public Iterable<BookList> findAll();
}
