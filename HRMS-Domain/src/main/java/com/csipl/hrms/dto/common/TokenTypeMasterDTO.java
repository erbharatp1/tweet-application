package com.csipl.hrms.dto.common;

import java.util.Date;



public class TokenTypeMasterDTO {
	private Long tokenTypeId;

	private Date dateCreated;

	private String tokenType;

	private Long userId;

	public Long getTokenTypeId() {
		return tokenTypeId;
	}

	public void setTokenTypeId(Long tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
