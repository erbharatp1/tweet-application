package com.csipl.hrms.dto.recruitment;

import java.util.Date;

public class PositionInterviewlevelXRefDTO {

	private Long levelId;

	private String contactNo;

	private Date datecreated;

	private Date dateUpdated;

	private String emailId;

	private String externalInterviewerName;

	private String interviewLevelType;

	private String levelName;

	private Long userId;

	private Long userIdUpdated;

	private Long positionId;
	private Long position;

	private Long internalInterviewerId;
	
	private String levelIndex;
	private String fullNameCodeValues;
	public PositionInterviewlevelXRefDTO() {
	}

	public Long getInternalInterviewerId() {
		return internalInterviewerId;
	}

	public void setInternalInterviewerId(Long internalInterviewerId) {
		this.internalInterviewerId = internalInterviewerId;
	}

	public String getLevelIndex() {
		return levelIndex;
	}

	public void setLevelIndex(String levelIndex) {
		this.levelIndex = levelIndex;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getExternalInterviewerName() {
		return this.externalInterviewerName;
	}

	public void setExternalInterviewerName(String externalInterviewerName) {
		this.externalInterviewerName = externalInterviewerName;
	}

	public String getInterviewLevelType() {
		return this.interviewLevelType;
	}

	public void setInterviewLevelType(String interviewLevelType) {
		this.interviewLevelType = interviewLevelType;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdated() {
		return userIdUpdated;
	}

	public void setUserIdUpdated(Long userIdUpdated) {
		this.userIdUpdated = userIdUpdated;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getFullNameCodeValues() {
		return fullNameCodeValues;
	}

	public void setFullNameCodeValues(String fullNameCodeValues) {
		this.fullNameCodeValues = fullNameCodeValues;
	}
}