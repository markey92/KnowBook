package com.scut.knowbook.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.User;

public interface ICommentsService{

	public Comments findById(Long id);
	
	public Comments findByCommenterId(String commenterId);
	
	public Comments findByCommentScore(String commentScore);
	
//	public Comments findByNumOfLike(int numOfLike);
//	
//	public Comments findByNumOfDislike(int numOfDislike);
//	
	public Comments save(Comments comments);
	
	public void delete(Comments comments);
	
	public Page<Comments> findBy(Long recommen_books_id,Pageable pageable);
}
