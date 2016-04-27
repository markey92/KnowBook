package com.scut.knowbook.service;


import com.scut.knowbook.model.User_info;

public interface IUserInfoService {

//	public User_info findByPhoneNumber(String phoneNumber);
	
	public User_info findById(Long id);
	
	public User_info save(User_info user_info);
	
	public void delete(User_info user_info);
}
