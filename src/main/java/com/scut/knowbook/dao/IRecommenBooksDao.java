package com.scut.knowbook.dao;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scut.knowbook.model.Recommen_books;

public interface IRecommenBooksDao extends CrudRepository<Recommen_books, Long>,PagingAndSortingRepository<Recommen_books, Long>{

	public Recommen_books findById(Long id);
	
	public Recommen_books findByRecommenerId(String recommenerId);
	
	public Recommen_books findByBookScore(int bookScore);
	
	public Recommen_books findByBookName(String bookName);
	
	public Recommen_books findByBookAuthor(String bookAuthor);
	
	public Recommen_books findByBookClass(String bookClass);
	
	@SuppressWarnings("unchecked")
	public Recommen_books save(Recommen_books recommen_books);

	public Page<Recommen_books> findAll(Pageable pageable);
	
//	@Query("select id,book_name,book_author,book_score,title_image,book_summary,book_location from recommen_books")
//	public Page<Recommen_books> findFragmentShow(Pageable pageable);
	
}
