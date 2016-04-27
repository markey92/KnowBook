package com.scut.knowbook.service;


import com.scut.knowbook.model.Son_comments;
import com.scut.knowbook.model.User;

public interface ISonCommentsService{

	public Son_comments findById(Long id);
	
	public Son_comments save(Son_comments comments);
	
	public void delete(Son_comments son_comments);
}
