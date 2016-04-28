package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.IRecommenBooksDao;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.service.IRecommenBooksService;

@Service("recommenBooksService")
public class RecommenBooksServiceImpl implements IRecommenBooksService {

	@Autowired
	private IRecommenBooksDao recommenBooksDao;
	
	public Recommen_books findById(Long id) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findById(id);
	}

	public Recommen_books findByRecommenerId(String recommenerId) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findByRecommenerId(recommenerId);
	}

	public Recommen_books findByBookScore(int bookScore) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findByBookScore(bookScore);
	}

	public Recommen_books findByBookName(String bookName) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findByBookName(bookName);
	}

	public Recommen_books findByBookAuthor(String bookAuthor) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findByBookAuthor(bookAuthor);
	}

	public Recommen_books findByBookClass(String bookClass) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findByBookClass(bookClass);
	}

	@Transactional
	public Recommen_books save(Recommen_books recommen_books) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.save(recommen_books);
	}

	@Transactional
	public void delete(Recommen_books recommen_books) {
		// TODO Auto-generated method stub
		this.recommenBooksDao.delete(recommen_books);
	}

	@Transactional
	public Iterable<Recommen_books> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<Recommen_books> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.recommenBooksDao.findAll(pageable);
	}


}
