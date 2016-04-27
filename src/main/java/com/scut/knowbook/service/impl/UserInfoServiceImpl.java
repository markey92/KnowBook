package com.scut.knowbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.knowbook.dao.IUserDao;
import com.scut.knowbook.dao.IUserInfoDao;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private IUserInfoDao userInfoDao;
	@Autowired
	private IUserDao userDao;

	public User_info findById(Long id) {
		// TODO Auto-generated method stub
		return this.userInfoDao.findById(id);
	}

	public User_info save(User_info user_info) {
		// TODO Auto-generated method stub
		return this.userInfoDao.save(user_info);
	}

	public void delete(User_info user_info) {
		// TODO Auto-generated method stub
		this.userInfoDao.delete(user_info);
	}

}
