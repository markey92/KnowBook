package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.User_info;

public interface IUserInfoDao extends CrudRepository<User_info, Long> {

	//public User_info findByPhoneNumber(String phoneNumber);
	
	public User_info findById(Long id);
	
	@SuppressWarnings("unchecked")
	public User_info save(User_info user);
}
