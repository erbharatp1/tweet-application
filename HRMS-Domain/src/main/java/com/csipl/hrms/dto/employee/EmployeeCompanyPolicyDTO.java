package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeCompanyPolicyDTO {

	private Long employeeCompanyPolicyId;
	private String status;
	private Long userId;
	private Long userIdUpdate;
	private Date dateCreated;
	private Date dateUpdated;
	private Long companyPolicyId;
	private Long employeeId;
	private Long departmentId;

	public Long getEmployeeCompanyPolicyId() {
		return employeeCompanyPolicyId;
	}

	public void setEmployeeCompanyPolicyId(Long employeeCompanyPolicyId) {
		this.employeeCompanyPolicyId = employeeCompanyPolicyId;
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

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Long getCompanyPolicyId() {
		return companyPolicyId;
	}

	public void setCompanyPolicyId(Long companyPolicyId) {
		this.companyPolicyId = companyPolicyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}