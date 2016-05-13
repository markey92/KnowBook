package com.scut.knowbook.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="seller_market")
public class Seller_market extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	//BuyBookName
	@Column(name="book_name", nullable= false)
	private String bookName;
	//price
	@Column(name="book_price", nullable= false)
	private double bookPrice;
	//null
	@Column(name="bookOwner_id", nullable= true)
	private String bookOwnerId;
	//newOrold
	@Column(name="book_situation", nullable= false)
	private String bookSituation;
	//type
	@Column(name="book_class", nullable= false)
	private String bookClass;
	//null
	@Column(name="owner_online_time", nullable= true)
	private String ownerOnlineTime;
	//BuyBookAuthor
	@Column(name="book_author", nullable= false)
	private String bookAuthor;
	//SellType
	@Column(name="selling_way", nullable= false)
	private String sellingWay;
	public String getBookPicture() {
		return bookPicture;
	}
	public void setBookPicture(String bookPicture) {
		this.bookPicture = bookPicture;
	}
	public String getBookDescript() {
		return bookDescript;
	}
	public void setBookDescript(String bookDescript) {
		this.bookDescript = bookDescript;
	}
	//BuyBookPicture
	@Column(name="book_picture", nullable= true)
	private String bookPicture;
	//BuyBookDescript
	@Column(name="book_descript", nullable= false)
	private String bookDescript;
	
	@ManyToOne(targetEntity =User_info.class)
//	@JoinColumn(name = "bookOwner_id", referencedColumnName = "PhoneNumber", nullable = false,insertable=false, updatable=false)
	private User_info user_info;
	
	public User_info getUser_info() {
		return user_info;
	}
	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}
	public String getBookOwnerId() {
		return bookOwnerId;
	}
	public void setBookOwnerId(String bookOwnerId) {
		this.bookOwnerId = bookOwnerId;
	}
	public String getBookSituation() {
		return bookSituation;
	}
	public void setBookSituation(String bookSituation) {
		this.bookSituation = bookSituation;
	}
	public String getBookClass() {
		return bookClass;
	}
	public void setBookClass(String bookClass) {
		this.bookClass = bookClass;
	}
	public String getOwnerOnlineTime() {
		return ownerOnlineTime;
	}
	public void setOwnerOnlineTime(String ownerOnlineTime) {
		this.ownerOnlineTime = ownerOnlineTime;
	}
	public String getSellingWay() {
		return sellingWay;
	}
	public void setSellingWay(String sellingWay) {
		this.sellingWay = sellingWay;
	}
	
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
}
