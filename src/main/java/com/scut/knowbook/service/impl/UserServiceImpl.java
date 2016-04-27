package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scut.knowbook.dao.IUserDao;
import com.scut.knowbook.model.User;
import com.scut.knowbook.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userDao.findById(id);
	}

	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(userName);
	}

	@Transactional
	public User save(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	@Transactional
	public void delete(User user) {
		// TODO Auto-generated method stub
        userDao.delete(user);
	}

	public User findByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return userDao.findByPhoneNumber(phoneNumber);
	}

}
