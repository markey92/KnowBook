package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.Comments;

public interface ICommentsDao extends CrudRepository<Comments, Long> {

	public Comments findById(Long id);
	
	public Comments findByCommenterId(String commenterId);
	
	public Comments findByCommentScore(String commentScore);
	
	public Comments findByNumOfLike(int numOfLike);
	
	public Comments findByNumOfDislike(int numOfDislike);
	
	@SuppressWarnings("unchecked")
	public Comments save(Comments comments);
}
