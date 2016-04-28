package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.IBookListDao;
import com.scut.knowbook.model.BookList;
import com.scut.knowbook.service.IBookListService;

@Service("bookListService")
public class BookListServiceImpl implements IBookListService {

	@Autowired(required=true)
	private IBookListDao bookListDao;

	public BookList findById(Long id) {
		// TODO Auto-generated method stub
		return this.bookListDao.findById(id);
	}

	public BookList findByCreater_id(String creater_id) {
		// TODO Auto-generated method stub
		return this.bookListDao.findByCreaterId(creater_id);
	}

	@Transactional
	public BookList save(BookList book_list) {
		// TODO Auto-generated method stub
		return this.bookListDao.save(book_list);
	}

	@Transactional
	public void delete(BookList book_list) {
		// TODO Auto-generated method stub
		this.bookListDao.delete(book_list);
	}

	public Iterable<BookList> findAll() {
		// TODO Auto-generated method stub
		return this.bookListDao.findAll();
	}

	public Page<BookList> findAllBookList(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.bookListDao.findAllBookList(pageable);
	}

}
