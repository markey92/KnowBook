package com.scut.knowbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.scut.knowbook.model.User;
import com.sun.corba.se.spi.activation.Repository;

public interface IUserDao extends CrudRepository<User,Long>{

	public User findByPhoneNumber(String phoneNumber);
	
	public User findById(Long id);
	
	public User findByUserName(String userName);
	
	@SuppressWarnings("unchecked")
	public User save(User user);
}
