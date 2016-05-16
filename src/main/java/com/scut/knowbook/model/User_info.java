package com.scut.knowbook.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.parsing.Location;

@Entity
@Table(name="user_info")
public class User_info extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="qq",nullable=true,length=11)
	private String qq;
	
	@Column(name="weixin",nullable=true,length=20)
	private String weixin;
	
	@Column(name="headPicture",nullable=true,length=11)
	private String headPicture;
	
	@Column(name="location",nullable=true,length=11)
	private String location;
	
	@OneToOne
	private User user;
	
	@OneToMany(targetEntity = Seller_market.class, mappedBy = "user_info")
	private Set<Seller_market> seller_market=new HashSet<Seller_market>();
	
	@OneToMany(targetEntity = Wish_platform.class, mappedBy = "user_info")
	private Set<Wish_platform> wish_platform=new HashSet<Wish_platform>();
	
	@JsonIgnore
	public Set<Wish_platform> getWish_platform() {
		return wish_platform;
	}

	public void setWish_platform(Set<Wish_platform> wish_platform) {
		this.wish_platform = wish_platform;
	}

	@JsonIgnore
	public Set<Seller_market> getSeller_market() {
		return seller_market;
	}

	public void setSeller_market(Set<Seller_market> seller_market) {
		this.seller_market = seller_market;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getHeadPicture() {
		return headPicture;
	}

	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
//
//	@Override
//	public String toString() {
//		return "User_info [qq=" + qq + ", weixin=" + weixin + ", headPicture=" + headPicture + ", getCreateDate()="
//				+ getCreateDate() + "]";
//	}
//	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
