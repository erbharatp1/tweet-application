package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeLettersTransactionDTO {

	private Long employeeLetterTransactionId;

	private Long companyId;

	private Date dateCreated;

	private Date dateUpdate;

	private Long designationId;

	private String levels;

	private String status;

	private Long userId;

	private Long userIdUpdate;

	private Long approvalId;

	private String approvalRemarks;

	private Long employeeLetterId;

	private Long empLetterId;
	private String approvalLevel;
	private String designationName;

	private Integer count;

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public Long getEmpLetterId() {
		return empLetterId;
	}

	public void setEmpLetterId(Long empLetterId) {
		this.empLetterId = empLetterId;
	}

	public Long getEmployeeLetterId() {
		return employeeLetterId;
	}

	public void setEmployeeLetterId(Long employeeLetterId) {
		this.employeeLetterId = employeeLetterId;
	}

	public Long getEmployeeLetterTransactionId() {
		return employeeLetterTransactionId;
	}

	public void setEmployeeLetterTransactionId(Long employeeLetterTransactionId) {
		this.employeeLetterTransactionId = employeeLetterTransactionId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
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

	public Long getApprovalId() {
		return approvalId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

}