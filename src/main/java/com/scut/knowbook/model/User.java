package com.scut.knowbook.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity
@Table(name="user")
public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="phone_number", nullable= true, length = 11)
	private String phoneNumber;
	
	@Column(name="user_name", nullable= true, length = 20)
	private String userName;
	
	@Column(name="password", nullable= true, length = 20)
	private String password;
	
	@Column(name="sex", nullable= true, length = 1)
	private String sex;
	
	//@OneToMany(targetEntity = User_info.class,cascade=CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "user")
	@OneToOne(mappedBy="user",fetch=FetchType.LAZY)
	private User_info user_info;
	
	@OneToMany(targetEntity=Recommen_books.class,mappedBy = "user")
	private Set<Recommen_books> recommen_books=new HashSet<Recommen_books>();
	
	
	@OneToMany(targetEntity=Comments.class,mappedBy="user")
	private Set<Comments> comments=new HashSet<Comments>();
	
	@OneToMany(targetEntity=Son_comments.class,mappedBy="user")
	private Set<Son_comments> son_comments=new HashSet<Son_comments>();
	
	@ManyToMany(targetEntity=BookList.class,mappedBy = "users")
	private Set<BookList> bookList=new HashSet<BookList>();
	
	@JsonIgnore
	public User_info getUser_info() {
		return user_info;
	}
	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}
	
	@JsonIgnore
	public Set<BookList> getBookList() {
		return bookList;
	}
	public void setBookList(Set<BookList> bookList) {
		this.bookList = bookList;
	}
	
	@JsonIgnore
	public Set<Recommen_books> getRecommen_books() {
		return recommen_books;
	}
	public void setRecommen_books(Set<Recommen_books> recommen_books) {
		this.recommen_books = recommen_books;
	}
	@JsonIgnore
	public Set<Comments> getComments() {
		return comments;
	}
	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}
	
	@JsonIgnore
	public Set<Son_comments> getSon_comments() {
		return son_comments;
	}
	public void setSon_comments(Set<Son_comments> son_comments) {
		this.son_comments = son_comments;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
