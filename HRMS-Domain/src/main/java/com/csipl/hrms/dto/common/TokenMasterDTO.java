package com.csipl.hrms.dto.common;

import java.util.Date;

public class TokenMasterDTO {
	private Long tokenId;

	private Long createdBy;

	private Date dateCreated;

	private String tokenValue;

	private Long tokentypeId;

	private Long userId;

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public Long getTokentypeId() {
		return tokentypeId;
	}

	public void setTokentypeId(Long tokentypeId) {
		this.tokentypeId = tokentypeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
