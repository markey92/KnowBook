package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.ICommentsDao;
import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.service.ICommentsService;

@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {

	@Autowired
	private ICommentsDao commentsDao;
	
	public Comments findById(Long id) {
		// TODO Auto-generated method stub
		return this.commentsDao.findById(id);
	}

	public Comments findByCommenterId(String commenterId) {
		// TODO Auto-generated method stub
		return this.commentsDao.findByCommenterId(commenterId);
	}

	public Comments findByCommentScore(String commentScore) {
		// TODO Auto-generated method stub
		return this.commentsDao.findByCommentScore(commentScore);
	}

//	public Comments findByNumOfLike(int numOfLike) {
//		// TODO Auto-generated method stub
//		return this.commentsDao.findByNumOfLike(numOfLike);
//	}
//
//	public Comments findByNumOfDislike(int numOfLike) {
//		// TODO Auto-generated method stub
//		return this.commentsDao.findByNumOfDislike(numOfLike);
//	}

	@Transactional
	public Comments save(Comments comments) {
		// TODO Auto-generated method stub
		return this.commentsDao.save(comments);
	}

	@Transactional
	public void delete(Comments comments) {
		// TODO Auto-generated method stub
		this.commentsDao.delete(comments);
	}

	@Transactional
	public Page<Comments> findBy(Long recommen_books_id, Pageable pageable) {
		// TODO Auto-generated method stub
		return this.commentsDao.findByRecommen_books_id(recommen_books_id, pageable);
	}

}
