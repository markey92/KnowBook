package com.scut.knowbook.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id",insertable=false,updatable=false) 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "create_by",nullable = true,length = 20)
	private String createBy;

	@Column(name = "create_date",nullable = true,columnDefinition="timestamp")
	private Timestamp createDate;
	
	/**
	 * 数据状�? 
	 * makai:"这里的default�? 似乎没用，默认还�?"
	 */
	@Column(name="state",nullable=false,columnDefinition="tinyint default 1")
	private int state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}
