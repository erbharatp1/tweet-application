package com.csipl.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "EmailNotificationMaster.findAll", query = "SELECT e FROM EmailNotificationMaster e")
public class EmailNotificationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailId;

	private String activeStatus;

	private String bcc;

	private String cc;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String fromMail;

	private String password;

	private String subject;

	private String title;

	private String toMail;

	private Long userId;
 
	@Temporal(TemporalType.DATE)
	private Date userIdUpdate;

	private String userName;
	private String mailType;
	@Column(name = "isReportingTo")
	private String isApplicableOnReportingTo;
	@Column(name = "isReportingToManager")
	private String	isApplicableOnReportingToManager;
	// bi-directional many-to-one association to EmailConfiguration
	@ManyToOne(optional = true, targetEntity = EmailConfiguration.class)
	@JoinColumn(name = "emailConfigureId")
	private EmailConfiguration emailConfiguration;
 
	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

 

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

	public EmailConfiguration getEmailConfiguration() {
		return this.emailConfiguration;
	}

	public void setEmailConfiguration(EmailConfiguration emailConfiguration) {
		this.emailConfiguration = emailConfiguration;
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