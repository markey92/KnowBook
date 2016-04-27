package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.Son_comments;

public interface ISonCommentsDao extends CrudRepository<Son_comments, Long> {

	public Son_comments findById(Long id);
	
	@SuppressWarnings("unchecked")
	public Son_comments save(Son_comments comments);
}
