package com.scut.knowbook.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scut.knowbook.model.BookList;

public interface IBookListDao extends CrudRepository<BookList, Long>,PagingAndSortingRepository<BookList, Long> {

	public BookList findById(Long id);
	
	public BookList findByCreaterId(String createrId);
	
	public BookList findByBookListName(String bookListName);
	
	public BookList findByBookId(String bookId);
	
	@SuppressWarnings("unchecked")
	public BookList save(BookList bookList);

	@Query("select a from BookList a Order by a.createDate")
	public Page<BookList> findAllBookList(Pageable pageable);
}
