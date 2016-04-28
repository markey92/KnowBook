package com.scut.knowbook.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="book_list")
public class BookList extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="creater_id", nullable= false)
	private String createrId;
	
	@Column(name="book_id", nullable= true)
	private String bookId;
	
	@Column(name="booklist_name", nullable= false)
	private String bookListName;
	
	@ManyToMany(targetEntity=Recommen_books.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "bookList", fetch = FetchType.LAZY)
    public Set<Recommen_books> recommen_books=new HashSet<Recommen_books>();
    
    @ManyToMany
    private Set<User> users=new HashSet<User>();
	
	public String getBookListName() {
		return bookListName;
	}
	public void setBookListName(String bookListName) {
		this.bookListName = bookListName;
	}

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@JsonIgnore
	public Set<Recommen_books> getRecommen_books() {
		return recommen_books;
	}
	public void setRecommen_books(Set<Recommen_books> recommen_books) {
		this.recommen_books = recommen_books;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

}
