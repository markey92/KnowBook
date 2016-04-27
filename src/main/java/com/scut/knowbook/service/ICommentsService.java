package com.scut.knowbook.service;


import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.User;

public interface ICommentsService{

	public Comments findById(Long id);
	
	public Comments findByCommenterId(String commenterId);
	
	public Comments findByCommentScore(String commentScore);
	
	public Comments findByNumOfLike(int numOfLike);
	
	public Comments findByNumOfDislike(int numOfDislike);
	
	public Comments save(Comments comments);
	
	public void delete(Comments comments);
}
