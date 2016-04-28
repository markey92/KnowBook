package com.scut.knowbook.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;

public interface ICommentsDao extends CrudRepository<Comments, Long>,PagingAndSortingRepository<Comments, Long>{

	public Comments findById(Long id);
	
	public Comments findByCommenterId(String commenterId);
	
	public Comments findByCommentScore(String commentScore);
	
//	public Comments findByNumOfLike(int numOfLike);
//	
//	public Comments findByNumOfDislike(int numOfDislike);
	
	@SuppressWarnings("unchecked")
	public Comments save(Comments comments);
	
	@Query("select a from Comments a where a.recommen_books.id = ?1")
	public Page<Comments> findByRecommen_books_id(Long recommen_books_id,Pageable pageable);
}
