package com.scut.knowbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.User_info;

public interface IUserInfoDao extends CrudRepository<User_info, Long> {

	//public User_info findByPhoneNumber(String phoneNumber);
	
	public User_info findById(Long id);
	
	@SuppressWarnings("unchecked")
	public User_info save(User_info user);
	
	@Query("select a from User_info a where a.location LIKE ?")
	public List<User_info> peopleAround(String locationMode);
	
	public List<User_info> findByLocationLike(String locationMode);
}
