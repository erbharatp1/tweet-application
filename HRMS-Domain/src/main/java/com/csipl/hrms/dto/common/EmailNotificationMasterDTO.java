package com.csipl.hrms.dto.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailNotificationMasterDTO {

	private Long mailId;

	private String activeStatus;

	private String bcc;

	private String cc;

	private Long companyId;

	private Date dateCreated;

	private Date dateUpdate;

	private String fromMail;

	private String password;

	private String subject;

	private String title;

	private String toMail;

	private Long userId;
	private Long emailConfigureId;
	private Integer countTo;
	private Integer countCC;
	private Integer countBCC;
	private String mailType;
	private String isApplicableOnReportingTo;
	private String	isApplicableOnReportingToManager;
	
	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public Long getEmailConfigureId() {
		return emailConfigureId;
	}

	public void setEmailConfigureId(Long emailConfigureId) {
		this.emailConfigureId = emailConfigureId;
	}

	private Date userIdUpdate;

	private String userName;

	public Long getMailId() {
		return this.mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getBcc() {
		return this.bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getFromMail() {
		return this.fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToMail() {
		return this.toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Date userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getUserName() {
		return this.userName;
	}

	 
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCountTo() {
		return countTo;
	}

	public void setCountTo(Integer countTo) {
		this.countTo = countTo;
	}

	public Integer getCountCC() {
		return countCC;
	}

	public void setCountCC(Integer countCC) {
		this.countCC = countCC;
	}

	public Integer getCountBCC() {
		return countBCC;
	}

	public void setCountBCC(Integer countBCC) {
		this.countBCC = countBCC;
	}

	public String getIsApplicableOnReportingTo() {
		return isApplicableOnReportingTo;
	}

	public void setIsApplicableOnReportingTo(String isApplicableOnReportingTo) {
		this.isApplicableOnReportingTo = isApplicableOnReportingTo;
	}

	public String getIsApplicableOnReportingToManager() {
		return isApplicableOnReportingToManager;
	}

	public void setIsApplicableOnReportingToManager(String isApplicableOnReportingToManager) {
		this.isApplicableOnReportingToManager = isApplicableOnReportingToManager;
	}

	
	 

}