package com.csipl.hrms.model.recruitment;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "CandidateLetterTemplateMaster.findAll", query = "SELECT c FROM CandidateLetterTemplateMaster c")
public class CandidateLetterTemplateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long latterTemplateId;

	private String bodyValue;

	private String footerValue;

	private String greetingFlag;

	private Long companyId;

	private String positionTitle;

	private String positionCode;

	private String interviewMode;

	private String interviewDateAndTime;

	private String hiringSpoc;
	
	private String templateFlag;
	
	private String greetingType;
	
	private String letterDescription;
	
	private String offeredCTC;
	
	private String reportingTime;

	private String jobDescription;
	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public String getInterviewDateAndTime() {
		return interviewDateAndTime;
	}

	public void setInterviewDateAndTime(String interviewDateAndTime) {
		this.interviewDateAndTime = interviewDateAndTime;
	}

	public String getHiringSpoc() {
		return hiringSpoc;
	}

	public void setHiringSpoc(String hiringSpoc) {
		this.hiringSpoc = hiringSpoc;
	}

	public Long getLatterTemplateId() {
		return latterTemplateId;
	}

	public void setLatterTemplateId(Long latterTemplateId) {
		this.latterTemplateId = latterTemplateId;
	}

	public String getFooterValue() {
		return footerValue;
	}

	public void setFooterValue(String footerValue) {
		this.footerValue = footerValue;
	}

	public String getBodyValue() {
		return bodyValue;
	}

	public void setBodyValue(String bodyValue) {
		this.bodyValue = bodyValue;
	}

	public String getGreetingFlag() {
		return greetingFlag;
	}

	public void setGreetingFlag(String greetingFlag) {
		this.greetingFlag = greetingFlag;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTemplateFlag() {
		return templateFlag;
	}

	public void setTemplateFlag(String templateFlag) {
		this.templateFlag = templateFlag;
	}

	public String getGreetingType() {
		return greetingType;
	}

	public void setGreetingType(String greetingType) {
		this.greetingType = greetingType;
	}

	public String getLetterDescription() {
		return letterDescription;
	}

	public void setLetterDescription(String letterDescription) {
		this.letterDescription = letterDescription;
	}

	public String getOfferedCTC() {
		return offeredCTC;
	}

	public void setOfferedCTC(String offeredCTC) {
		this.offeredCTC = offeredCTC;
	}

	public String getReportingTime() {
		return reportingTime;
	}

	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	
	
	
	
	
}
