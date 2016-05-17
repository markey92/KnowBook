package com.scut.knowbook.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scut.knowbook.dao.IUserDao;
import com.scut.knowbook.dao.IUserInfoDao;
import com.scut.knowbook.geohash.GeoHash;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private IUserInfoDao userInfoDao;
	@Autowired
	private IUserDao userDao;

	private Logger logger = Logger.getLogger(getClass());
	
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
	
	public String geohashEncode(String Location,int numberOfBits) {
		// TODO Auto-generated method stub
		String[] Locations=Location.split(",");
		if(Locations.length==2){
			double latitude=Double.parseDouble(Locations[0]);
			double longitude=Double.parseDouble(Locations[1]);
			logger.info("转化后的经纬度"+latitude+","+longitude);
			GeoHash geoHash=GeoHash.withBitPrecision(latitude, longitude, numberOfBits);
			return geoHash.toBase32();
		}
		else{
			return null;
		}
	}

	public String geohashDecode(String geoHashLocation) {
		// TODO Auto-generated method stub
		GeoHash decodedHash = GeoHash.fromGeohashString(geoHashLocation);
		return decodedHash.getBoundingBoxCenterPoint().toString();
	}

	public Page<User_info> peopleAround(String locationMode,Pageable page) {
		// TODO Auto-generated method stub
//		return this.userInfoDao.peopleAround(locationMode);
		return this.userInfoDao.findByLocationLike(locationMode,page);
	}

}
