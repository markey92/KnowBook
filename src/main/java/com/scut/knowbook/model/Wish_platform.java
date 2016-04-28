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

import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
@Table(name="wish_platform")
public class Wish_platform extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="wisher_id", nullable= false, length = 11)
	private String wisherId;
	
	@Column(name="book_name", nullable= false, length = 11)
	private String bookName;
	
	@Column(name="book_class", nullable= false, length = 11)
	private String bookClass;
	
	@Column(name="wish_content", nullable= false, length = 11)
	private String wishContent;
	
	@Column(name="book_author", nullable= false, length = 11)
	private String bookAuthor;
	
	@Column(name="wish_location", nullable= false)
	private String wishLocation;
	
	public String getWishLocation() {
		return wishLocation;
	}
	public void setWishLocation(String wishLocation) {
		this.wishLocation = wishLocation;
	}
	@ManyToOne(targetEntity =User_info.class)
	private User_info user_info=new User_info();
	
	@JsonIgnore
	public User_info getUser_info() {
		return user_info;
	}
	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}
	public String getWisherId() {
		return wisherId;
	}
	public void setWisherId(String wisherId) {
		this.wisherId = wisherId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookClass() {
		return bookClass;
	}
	public void setBookClass(String bookClass) {
		this.bookClass = bookClass;
	}
	public String getWishContent() {
		return wishContent;
	}
	public void setWishContent(String wishContent) {
		this.wishContent = wishContent;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
}
