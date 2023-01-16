package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeTicketSummaryDTO {
	
	private Long ticketId;
	private String employeeCode;
	private String employeeName;
	private String employeeDesignation;
	private String employeeDepartment;
	private String ticketNo;
	private String category;
	private Date datedOn;
	private String status;
	private Date closedOn;
	private String comments;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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
	public String getEmployeeDesignation() {
		return employeeDesignation;
	}
	public void setEmployeeDesignation(String employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}
	public String getEmployeeDepartment() {
		return employeeDepartment;
	}
	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDatedOn() {
		return datedOn;
	}
	public void setDatedOn(Date datedOn) {
		this.datedOn = datedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getClosedOn() {
		return closedOn;
	}
	public void setClosedOn(Date closedOn) {
		this.closedOn = closedOn;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
	
}
