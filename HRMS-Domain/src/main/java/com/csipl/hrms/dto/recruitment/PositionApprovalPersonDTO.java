package com.csipl.hrms.dto.recruitment;

import java.util.Date;
import java.util.List;

public class PositionApprovalPersonDTO {

	private Long positionApprovalPersonId;

	private Long employeeId;

	private List<Long> deactiveEmployeeList;

	private Date dateCreated;

	private Date upatedDate;

	private String employeeName;

	private String employeeCode;

	private String firstName;

	private String lastName;

	private Long userId;

	private Long userIdUpdate;

	private String status;

	public Long getPositionApprovalPersonId() {
		return positionApprovalPersonId;
	}

	public void setPositionApprovalPersonId(Long positionApprovalPersonId) {
		this.positionApprovalPersonId = positionApprovalPersonId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getUpatedDate() {
		return upatedDate;
	}

	public void setUpatedDate(Date upatedDate) {
		this.upatedDate = upatedDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getDeactiveEmployeeList() {
		return deactiveEmployeeList;
	}

	public void setDeactiveEmployeeList(List<Long> deactiveEmployeeList) {
		this.deactiveEmployeeList = deactiveEmployeeList;
	}

}