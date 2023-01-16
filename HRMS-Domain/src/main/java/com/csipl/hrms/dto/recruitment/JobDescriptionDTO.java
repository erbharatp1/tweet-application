package com.csipl.hrms.dto.recruitment;

import java.util.Date;

public class JobDescriptionDTO {
	
	private Long jdId;
	private String jdName;
	private String jdFile;
	private Long userId;
	private Long userIdUpdate;
	private Date dateCreated;
	private Date updatedDate;
	private String jdFileName;
	
	
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
	public String getJdFileName() {
		return jdFileName;
	}
	public void setJdFileName(String jdFileName) {
		this.jdFileName = jdFileName;
	}
	
	
	
	

}
