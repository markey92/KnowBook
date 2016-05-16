package com.scut.knowbook.service;


import java.util.List;

import com.scut.knowbook.model.User_info;

public interface IUserInfoService {

//	public User_info findByPhoneNumber(String phoneNumber);
	
	public User_info findById(Long id);
	
	public User_info save(User_info user_info);
	
	public void delete(User_info user_info);
	
	public String geohashEncode(String Location,int numberOfBits);
	
	public String geohashDecode(String geoHashLocation);
	
	public List<User_info> peopleAround(String locationMode);
}
