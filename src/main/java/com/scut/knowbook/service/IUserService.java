package com.scut.knowbook.service;

import com.scut.knowbook.model.User;

public interface IUserService {

	public User findByPhoneNumber(String phoneNumber);
	
	public User findById(Long id);
	
	public User findByUserName(String userName);
	
	public User save(User user);
	
	public void delete(User user);
}
