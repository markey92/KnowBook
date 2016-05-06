package com.scut.knowbook.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Son_comments;

public interface ISonCommentsDao extends CrudRepository<Son_comments, Long>,PagingAndSortingRepository<Son_comments, Long>{

	public Son_comments findById(Long id);

	@Query("select a from Son_comments a where a.comments.id = ?1")
	public Page<Son_comments> findByCommentsId(Long commentsId,Pageable pageable);
	
	@SuppressWarnings("unchecked")
	public Son_comments save(Son_comments comments);
}
