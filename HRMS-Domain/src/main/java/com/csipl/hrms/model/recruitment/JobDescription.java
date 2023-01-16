package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "JobDescription.findAll", query = "SELECT j FROM JobDescription j")
public class JobDescription implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jdId;
	
	private String jdName;
	
	private String jdFile;
	
	private Long userId;

	private Long userIdUpdate;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	public Long getJdId() {
		return jdId;
	}

	public void setJdId(Long jdId) {
		this.jdId = jdId;
	}

	public String getJdName() {
		return jdName;
	}

	public void setJdName(String jdName) {
		this.jdName = jdName;
	}

	public String getJdFile() {
		return jdFile;
	}

	public void setJdFile(String jdFile) {
		this.jdFile = jdFile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	

	
	
	
}
