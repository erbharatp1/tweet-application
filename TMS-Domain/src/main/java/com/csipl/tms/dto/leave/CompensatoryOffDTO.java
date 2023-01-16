package com.csipl.tms.dto.leave;

import java.math.BigDecimal;
import java.util.Date;
//import com.csipl.hrms.common.constant.StatusMessage;

public class CompensatoryOffDTO {

	private Long tmsCompensantoryOffId;

	private Long approvalId;

	private String approvalRemark;

	private Date dateCreated;

	private Date dateUpdate;

	private Long employeeId;

	private Date fromDate;

	private Long leaveTypeId;

	private String remark;

	private String status;

	private Date toDate;

	private String statusValue;

	private BigDecimal days;

	private Long userId;

	private String cancelRemark;

	private String employeeCode;

	private String employeeName;

	private String department;

	private String designation;

	private String approvalEmployeeCode;

	private String approvalEmployeeName;

	private String approvalEmployeeDepartment;

	private String approvalEmployeeDesignation;

	private String employeeLogoPath;

	private Long userIdUpdate;
	private Long companyId;
	private int compOffCountMyTeam;
	private int compOffCountMy;

	private String half_fullDay;

	private String jobLocation;
	private String reportingManager;
	private Date duration;
	private Date requestedOn;
	private String requesterRemark;
	private String actionTakenBy;
	private Date actionTakenOn;
	private String actionerRemark;
	private String day;

	public Long getTmsCompensantoryOffId() {
		return tmsCompensantoryOffId;
	}

	public void setTmsCompensantoryOffId(Long tmsCompensantoryOffId) {
		this.tmsCompensantoryOffId = tmsCompensantoryOffId;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public BigDecimal getDays() {
		return days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
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

	public String getApprovalEmployeeCode() {
		return approvalEmployeeCode;
	}

	public void setApprovalEmployeeCode(String approvalEmployeeCode) {
		this.approvalEmployeeCode = approvalEmployeeCode;
	}

	public String getApprovalEmployeeName() {
		return approvalEmployeeName;
	}

	public void setApprovalEmployeeName(String approvalEmployeeName) {
		this.approvalEmployeeName = approvalEmployeeName;
	}

	public String getApprovalEmployeeDepartment() {
		return approvalEmployeeDepartment;
	}

	public void setApprovalEmployeeDepartment(String approvalEmployeeDepartment) {
		this.approvalEmployeeDepartment = approvalEmployeeDepartment;
	}

	public String getApprovalEmployeeDesignation() {
		return approvalEmployeeDesignation;
	}

	public void setApprovalEmployeeDesignation(String approvalEmployeeDesignation) {
		this.approvalEmployeeDesignation = approvalEmployeeDesignation;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}

	public int getCompOffCountMyTeam() {
		return compOffCountMyTeam;
	}

	public void setCompOffCountMyTeam(int compOffCountMyTeam) {
		this.compOffCountMyTeam = compOffCountMyTeam;
	}

	/**
	 * @return the compOffCountMy
	 */
	public int getCompOffCountMy() {
		return compOffCountMy;
	}

	/**
	 * @param compOffCountMy the compOffCountMy to set
	 */
	public void setCompOffCountMy(int compOffCountMy) {
		this.compOffCountMy = compOffCountMy;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public Date getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(Date requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getRequesterRemark() {
		return requesterRemark;
	}

	public void setRequesterRemark(String requesterRemark) {
		this.requesterRemark = requesterRemark;
	}

	public String getActionTakenBy() {
		return actionTakenBy;
	}

	public void setActionTakenBy(String actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}

	public Date getActionTakenOn() {
		return actionTakenOn;
	}

	public void setActionTakenOn(Date actionTakenOn) {
		this.actionTakenOn = actionTakenOn;
	}

	public String getActionerRemark() {
		return actionerRemark;
	}

	public void setActionerRemark(String actionerRemark) {
		this.actionerRemark = actionerRemark;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHalf_fullDay() {
		return half_fullDay;
	}

	public void setHalf_fullDay(String half_fullDay) {
		this.half_fullDay = half_fullDay;
	}

}
