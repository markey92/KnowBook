package com.scut.knowbook.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;

public interface IUserInfoDao extends CrudRepository<User_info, Long>, PagingAndSortingRepository<User_info,Long> {

	//public User_info findByPhoneNumber(String phoneNumber);
	
	public User_info findById(Long id);
	
	@SuppressWarnings("unchecked")
	public User_info save(User_info user);
	
	
//	public List<User_info> findByLocationLike(String locationMode);
	
	public Page<User_info> findByLocationLike(String locationMode,Pageable page);
	
}
