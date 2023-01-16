package com.csipl.hrms.dto.recruitment;

import java.util.Date;

public class CandidateSelectionRuleDTO {

	private Long selectionRuleId;

	private String selectionRuleDescription;

	private String status;

	private Long userId;

	private Long userIdUpdate;

	private Date dateCreated;

	private Date updatedDate;

	public Long getSelectionRuleId() {
		return selectionRuleId;
	}

	public void setSelectionRuleId(Long selectionRuleId) {
		this.selectionRuleId = selectionRuleId;
	}

	public String getSelectionRuleDescription() {
		return selectionRuleDescription;
	}

	public void setSelectionRuleDescription(String selectionRuleDescription) {
		this.selectionRuleDescription = selectionRuleDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
