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
@Table(name="son_comments")
public class Son_comments extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="comment_content", nullable= false)
	private String commentContent;
	
	@ManyToOne
	private Comments comments;
	
    public Comments getComments() {
		return comments;
	}
	public void setComments(Comments comments) {
		this.comments = comments;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
}
