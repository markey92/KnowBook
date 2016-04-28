package com.scut.knowbook.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.User;

public interface IRecommenBooksService {

	public Recommen_books findById(Long id);
	
	public Recommen_books findByRecommenerId(String recommenerId);
	
	public Recommen_books findByBookScore(int bookScore);
	
	public Recommen_books findByBookName(String bookName);
	
	public Recommen_books findByBookAuthor(String bookAuthor);
	
	public Recommen_books findByBookClass(String bookClass);
	
	public Recommen_books save(Recommen_books recommen_books);
	
	public void delete(Recommen_books recommen_books);
	
	public Iterable<Recommen_books> findAll();
	
	public Page<Recommen_books> findAll(Pageable pageable);
	
}
