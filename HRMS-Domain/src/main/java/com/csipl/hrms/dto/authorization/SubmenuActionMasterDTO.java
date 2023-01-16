package com.csipl.hrms.dto.authorization;

import java.util.Date;

public class SubmenuActionMasterDTO {
	private Long submenuActionId;

	private Date dateCreated;

	
	private String uniqueCode;
	private String description;

	private String status;

	private String title;

	private String urlPath;

	private Long userId;

	public Long getSubmenuActionId() {
		return submenuActionId;
	}

	public void setSubmenuActionId(Long submenuActionId) {
		this.submenuActionId = submenuActionId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Long submenuId) {
		this.submenuId = submenuId;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	private Long submenuId;
}
