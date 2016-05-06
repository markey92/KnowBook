package com.scut.knowbook.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="comments")
public class Comments extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="commenter_id", nullable= true)
	private String commenterId;
	
	@Column(name="comment_content", nullable= false)
	private String commentContent;
	
	@Column(name="comment_score", nullable= false)
	private double commentScore;
	
	@Column(name="num_of_like", nullable= false)
	private int numOfLike;
	
	@Column(name="num_of_dislike", nullable= false)
	private int numOfDislike;
	
	@ManyToOne(targetEntity=User.class)
	private User user;
	
	@ManyToOne(targetEntity =Recommen_books.class)
//	@JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false,insertable=false, updatable=false)
	private Recommen_books recommen_books;
	
	@OneToMany(targetEntity = Son_comments.class, mappedBy = "comments")
	private Set<Son_comments> son_comments=new HashSet<Son_comments>();
	
	@JsonIgnore
	public Set<Son_comments> getSon_comments() {
		return son_comments;
	}
	public void setSon_comments(Set<Son_comments> son_comments) {
		this.son_comments = son_comments;
	}
	
	@JsonIgnore
	public Recommen_books getRecommen_books() {
		return recommen_books;
	}
	public void setRecommen_books(Recommen_books recommen_books) {
		this.recommen_books = recommen_books;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@JsonIgnore
	public String getCommenterId() {
		return commenterId;
	}
	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	public double getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(double commentScore) {
		this.commentScore = commentScore;
	}
	public int getNumOfLike() {
		return numOfLike;
	}
	public void setNumOfLike(int numOfLike) {
		this.numOfLike = numOfLike;
	}
	public int getNumOfDislike() {
		return numOfDislike;
	}
	public void setNumOfDislike(int numOfDislike) {
		this.numOfDislike = numOfDislike;
	}
	
}
