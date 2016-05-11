package com.scut.knowbook.service.impl;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

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

//	private Logger logger;
	
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

	public List<BookList> findMostBookList(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<BookList> booklists=this.bookListDao.findAllBookList(pageable);
		ArrayList<BookList> myBookLists=new ArrayList<BookList>();
		for(BookList bookList:booklists){
			myBookLists.add(bookList);
		}
		Comparator<BookList> comparator=new Comparator<BookList>() {

			public int compare(BookList b1, BookList b2) {
				// TODO Auto-generated method stub
				Integer bb1=b1.getUsers().size();
				Integer bb2=b2.getUsers().size();
				return bb2.compareTo(bb1);
			}
		};
		Collections.sort(myBookLists,comparator);
		return myBookLists;
	}

	public List<BookList> findByCreateDateBetween(Timestamp max, Timestamp min,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<BookList> booklists= this.bookListDao.findByCreateDateBetween(max, min, pageable);
		ArrayList<BookList> myArrayList=new ArrayList<BookList>();
		for(BookList bookList:booklists){
			myArrayList.add(bookList);
		}
		return myArrayList;
	}

	public List<BookList> findHotBookList(Timestamp max, Timestamp min,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<BookList> booklists= this.bookListDao.findByCreateDateBetween(max, min,pageable);
		ArrayList<BookList> myBookLists=new ArrayList<BookList>();
		for(BookList bookList:booklists){
			myBookLists.add(bookList);
		}
		Comparator<BookList> comparator=new Comparator<BookList>() {
			public int compare(BookList b1, BookList b2) {
				// TODO Auto-generated method stub
				Integer bb1=b1.getUsers().size();
				Integer bb2=b2.getUsers().size();
				return bb2.compareTo(bb1);
			}
		};
		Collections.sort(myBookLists,comparator);
		return myBookLists;
	}

}
