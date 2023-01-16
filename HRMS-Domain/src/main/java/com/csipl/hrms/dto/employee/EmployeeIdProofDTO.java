package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeIdProofDTO {

	private Long employeeIdProofsId;
	private Long userId;
	private Date dateCreated;
	private Long CompanyId;
	private Long userIdUpdate;
	
	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	private String activeStatus;
	private String dateFrom;
	private String dateTo;
	private String idNumber;
	private String idTypeId;
	private Date dateIssue;
	private Date dateExpiry;
	private String adharCardNo;
	private String panCardNo;
	private String voterIdNo;
	private String drivingLicenceNo;
	private Date dLIssueDate;
	private Date dLExpiryDate;
	private String passportNo;
	private Date pSIssueDate;
	private Date pSExpiryDate;
	private String empName;
	private String empCode;
	private String deptName;
	
	
	
	
	public Date getDateExpiry() {
		return dateExpiry;
	}

	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}

	public Long getCompanyId() {
		return CompanyId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public void setCompanyId(Long companyId) {
		CompanyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getEmployeeIdProofsId() {
		return employeeIdProofsId;
	}

	public void setEmployeeIdProofsId(Long employeeIdProofsId) {
		this.employeeIdProofsId = employeeIdProofsId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(String idTypeId) {
		this.idTypeId = idTypeId;
	}

	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public String getAdharCardNo() {
		return adharCardNo;
	}

	public void setAdharCardNo(String adharCardNo) {
		this.adharCardNo = adharCardNo;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getVoterIdNo() {
		return voterIdNo;
	}

	public void setVoterIdNo(String voterIdNo) {
		this.voterIdNo = voterIdNo;
	}

	public String getDrivingLicenceNo() {
		return drivingLicenceNo;
	}

	public void setDrivingLicenceNo(String drivingLicenceNo) {
		this.drivingLicenceNo = drivingLicenceNo;
	}

	public Date getdLIssueDate() {
		return dLIssueDate;
	}

	public void setdLIssueDate(Date dLIssueDate) {
		this.dLIssueDate = dLIssueDate;
	}

	public Date getdLExpiryDate() {
		return dLExpiryDate;
	}

	public void setdLExpiryDate(Date dLExpiryDate) {
		this.dLExpiryDate = dLExpiryDate;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Date getpSIssueDate() {
		return pSIssueDate;
	}

	public void setpSIssueDate(Date pSIssueDate) {
		this.pSIssueDate = pSIssueDate;
	}

	public Date getpSExpiryDate() {
		return pSExpiryDate;
	}

	public void setpSExpiryDate(Date pSExpiryDate) {
		this.pSExpiryDate = pSExpiryDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


}



