package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.knowbook.dao.ICommentsDao;
import com.scut.knowbook.model.Comments;
import com.scut.knowbook.service.ICommentsService;

@Service("commentsService")
public class CommentsServiceImpl implements ICommentsService {

//	@Autowired
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

	public Comments findByNumOfLike(int numOfLike) {
		// TODO Auto-generated method stub
		return this.commentsDao.findByNumOfLike(numOfLike);
	}

	public Comments findByNumOfDislike(int numOfLike) {
		// TODO Auto-generated method stub
		return this.commentsDao.findByNumOfDislike(numOfLike);
	}

	public Comments save(Comments comments) {
		// TODO Auto-generated method stub
		return this.commentsDao.save(comments);
	}

	public void delete(Comments comments) {
		// TODO Auto-generated method stub
		this.commentsDao.delete(comments);
	}

}
