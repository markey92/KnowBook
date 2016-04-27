package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.Recommen_books;

public interface IRecommenBooksDao extends CrudRepository<Recommen_books, Long> {

	public Recommen_books findById(Long id);
	
	public Recommen_books findByRecommenerId(String recommenerId);
	
	public Recommen_books findByBookScore(int bookScore);
	
	public Recommen_books findByBookName(String bookName);
	
	public Recommen_books findByBookAuthor(String bookAuthor);
	
	public Recommen_books findByBookClass(String bookClass);
	
	@SuppressWarnings("unchecked")
	public Recommen_books save(Recommen_books recommen_books);
}
