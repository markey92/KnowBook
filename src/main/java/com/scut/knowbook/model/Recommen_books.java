package com.scut.knowbook.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="recommen_books")
public class Recommen_books extends BaseModel implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Column(name="recommener_id", nullable= true)
	private String recommenerId;
	
	@Column(name="book_score", nullable= false)
	private double bookScore;
	
	@Column(name="title_image", nullable= false)
	private String titleImage;
	
	@Column(name="book_name", nullable= false)
	private String bookName;
	
	@Column(name="book_location", nullable= false)
	private String bookLocation;
	
	@Column(name="recommen_reason", nullable= false)
	private String recommenReason;
	
	@Column(name="book_author", nullable= false)
	private String bookAuthor;
	
	@Column(name="book_class", nullable= false)
	private String bookClass;
	
	@Column(name="book_summary", nullable= false)
	private String bookSummary;

	/**
	 * joinTable->连接表的名字
	 * 		joincolumn->name对应连接表名为book_id的列，joincolumn->referencedColumnName对应此表中对应名为id的列
	 * 		inverseJoinColumns->name对应连接表中名为booklist_id的列,->referencedColumnName对应关联表中名为booklist_id的列
	 */
	@ManyToMany(targetEntity=BookList.class)
	@JoinTable(name="recommen_books_list",
	joinColumns=@JoinColumn(name="book_id",referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="booklist_id",referencedColumnName="id",unique=false))
	private Set<BookList> bookList=new HashSet<BookList>();
	
	@OneToMany(targetEntity = Comments.class, mappedBy = "recommen_books")
	private Set<Comments> comments=new HashSet<Comments>();
	
	@ManyToOne(targetEntity=User.class)
	private User user;
	
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public Set<Comments> getComments() {
		return comments;
	}
	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}
	
	@JsonIgnore
	public Set<BookList> getBookList() {
		return bookList;
	}
	public void setBookList(Set<BookList> bookList) {
		this.bookList = bookList;
	}
	@JsonIgnore
	public String getRecommenerId() {
		return recommenerId;
	}
	public void setRecommenerId(String recommenerId) {
		this.recommenerId = recommenerId;
	}
	
	public double getBookScore() {
		return bookScore;
	}
	public void setBookScore(double bookScore) {
		this.bookScore = bookScore;
	}
	public void setBookScore(int bookScore) {
		this.bookScore = bookScore;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookLocation() {
		return bookLocation;
	}
	public void setBookLocation(String bookLocation) {
		this.bookLocation = bookLocation;
	}
	public String getRecommenReason() {
		return recommenReason;
	}
	public void setRecommenReason(String recommenReason) {
		this.recommenReason = recommenReason;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookClass() {
		return bookClass;
	}
	public void setBookClass(String bookClass) {
		this.bookClass = bookClass;
	}
	public String getBookSummary() {
		return bookSummary;
	}
	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}
	@Override
	public String toString() {
		return "Recommen_books [bookScore=" + bookScore + ", titleImage=" + titleImage + ", bookName=" + bookName
				+ ", bookLocation=" + bookLocation + ", recommenReason=" + recommenReason + ", bookSummary="
				+ bookSummary + ", getId()=" + getId() + "]";
	}
	
}
