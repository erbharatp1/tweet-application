package com.csipl.tms.dto.common;

import java.math.BigDecimal;
import java.util.Date;

public class SearchDTO {

	int offset;
	int limit;
	String filter;
	String sortDirection;
	String active;
	String activeStaus;

	private String employeeCode;
	private String employeeName;
	private String grade;
	private String department;
	private String designation;
	private String doj;
	private String dob;
	private String reportingTo;
	private Long companyId;
	private String tabName;
	private String attendaceStatus;
	private Long departmentId;
	private Long designationId;
	private String status;

	private String positionCode;
	private String positionTitle;
	private String jobLocation;
	private String employementType;
	
	private String candidateName;
	private String candidateContactNo;
	private String candidateEmailId;
	private Date interviewDate;
	private String interviewTime;
	private String interviewMode;
//	private String positionTitle;
//	private String positionCode ;
	private BigDecimal requiredExperience;
	private Long noOfLevel;
	private String recuiterName;
	private BigDecimal loanAmount;
	private BigDecimal loanPendingAmount;
	private int noOfEmi;
	private Long massCommunicationId;
	private String title;
	private String description;
	private Date dateFrom;
	private Date dateTo;
	private String ticketNo;
	private String dataStatus;

	public String getEmployementType() {
		return employementType;
	}

	public void setEmployementType(String employementType) {
		this.employementType = employementType;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActiveStaus() {
		return activeStaus;
	}

	public void setActiveStaus(String activeStaus) {
		this.activeStaus = activeStaus;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getAttendaceStatus() {
		return attendaceStatus;
	}

	public void setAttendaceStatus(String attendaceStatus) {
		this.attendaceStatus = attendaceStatus;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateContactNo() {
		return candidateContactNo;
	}

	public void setCandidateContactNo(String candidateContactNo) {
		this.candidateContactNo = candidateContactNo;
	}

	public String getCandidateEmailId() {
		return candidateEmailId;
	}

	public void setCandidateEmailId(String candidateEmailId) {
		this.candidateEmailId = candidateEmailId;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public BigDecimal getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(BigDecimal requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public Long getNoOfLevel() {
		return noOfLevel;
	}

	public void setNoOfLevel(Long noOfLevel) {
		this.noOfLevel = noOfLevel;
	}

	@Override
	public String toString() {
		return String.format(
				"SearchDTO [offset=%s, limit=%s, filter=%s, sortDirection=%s, active=%s, activeStaus=%s, employeeCode=%s, employeeName=%s, grade=%s, department=%s, designation=%s, doj=%s, dob=%s, reportingTo=%s, companyId=%s, tabName=%s, attendaceStatus=%s, departmentId=%s, designationId=%s, status=%s, positionCode=%s, positionTitle=%s, jobLocation=%s, employementType=%s, candidateName=%s, candidateContactNo=%s, candidateEmailId=%s, interviewDate=%s, interviewTime=%s, interviewMode=%s, requiredExperience=%s, noOfLevel=%s, recuiterName=%s, getEmployementType()=%s, getJobLocation()=%s, getPositionCode()=%s, getPositionTitle()=%s, getOffset()=%s, getLimit()=%s, getFilter()=%s, getSortDirection()=%s, getActive()=%s, getActiveStaus()=%s, getEmployeeCode()=%s, getEmployeeName()=%s, getGrade()=%s, getDepartment()=%s, getDesignation()=%s, getDoj()=%s, getDob()=%s, getReportingTo()=%s, getCompanyId()=%s, getTabName()=%s, getAttendaceStatus()=%s, getDepartmentId()=%s, getDesignationId()=%s, getStatus()=%s, getCandidateName()=%s, getCandidateContactNo()=%s, getCandidateEmailId()=%s, getInterviewDate()=%s, getInterviewTime()=%s, getInterviewMode()=%s, getRequiredExperience()=%s, getNoOfLevel()=%s, getRecuiterName()=%s, getClass()=%s, hashCode()=%s, toString()=%s]",
				offset, limit, filter, sortDirection, active, activeStaus, employeeCode, employeeName, grade,
				department, designation, doj, dob, reportingTo, companyId, tabName, attendaceStatus, departmentId,
				designationId, status, positionCode, positionTitle, jobLocation, employementType, candidateName,
				candidateContactNo, candidateEmailId, interviewDate, interviewTime, interviewMode, requiredExperience,
				noOfLevel, recuiterName, getEmployementType(), getJobLocation(), getPositionCode(), getPositionTitle(),
				getOffset(), getLimit(), getFilter(), getSortDirection(), getActive(), getActiveStaus(),
				getEmployeeCode(), getEmployeeName(), getGrade(), getDepartment(), getDesignation(), getDoj(), getDob(),
				getReportingTo(), getCompanyId(), getTabName(), getAttendaceStatus(), getDepartmentId(),
				getDesignationId(), getStatus(), getCandidateName(), getCandidateContactNo(), getCandidateEmailId(),
				getInterviewDate(), getInterviewTime(), getInterviewMode(), getRequiredExperience(), getNoOfLevel(),
				getRecuiterName(), getClass(), hashCode(), super.toString());
	}

	public String getRecuiterName() {
		return recuiterName;
	}

	public void setRecuiterName(String recuiterName) {
		this.recuiterName = recuiterName;
	}

	public Long getMassCommunicationId() {
		return massCommunicationId;
	}

	public void setMassCommunicationId(Long massCommunicationId) {
		this.massCommunicationId = massCommunicationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLoanPendingAmount() {
		return loanPendingAmount;
	}

	public void setLoanPendingAmount(BigDecimal loanPendingAmount) {
		this.loanPendingAmount = loanPendingAmount;
	}

	public int getNoOfEmi() {
		return noOfEmi;
	}

	public void setNoOfEmi(int noOfEmi) {
		this.noOfEmi = noOfEmi;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	

}
