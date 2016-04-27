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

	@Column(name="book_name", nullable= false)
	private String bookName;
	
	@Column(name="book_price", nullable= false)
	private int bookPrice;
	
	@Column(name="bookOwner_id", nullable= false)
	private String bookOwnerId;
	
	@Column(name="book_situation", nullable= false)
	private String bookSituation;
	
	@Column(name="book_class", nullable= false)
	private String bookClass;
	
	@Column(name="owner_online_time", nullable= false)
	private String ownerOnlineTime;
	
	@Column(name="book_author", nullable= false)
	private String bookAuthor;
	
	@Column(name="selling_way", nullable= false)
	private String sellingWay;
	
	@ManyToOne(targetEntity =User_info.class)
//	@JoinColumn(name = "bookOwner_id", referencedColumnName = "PhoneNumber", nullable = false,insertable=false, updatable=false)
	private Set<User_info> user_info=new HashSet<User_info>();
	
	public Set<User_info> getUser_info() {
		return user_info;
	}
	public void setUser_info(Set<User_info> user_info) {
		this.user_info = user_info;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public int getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(int bookPrice) {
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
	@Override
	public String toString() {
		return "Seller_market [bookName=" + bookName + ", bookPrice=" + bookPrice + ", bookOwnerId=" + bookOwnerId
				+ ", bookSituation=" + bookSituation + ", bookClass=" + bookClass + ", ownerOnlineTime="
				+ ownerOnlineTime + ", bookAuthor=" + bookAuthor + ", sellingWay=" + sellingWay + "]";
	}
	
}
