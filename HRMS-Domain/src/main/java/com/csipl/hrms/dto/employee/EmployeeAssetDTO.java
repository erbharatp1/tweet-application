package com.csipl.hrms.dto.employee;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeAssetDTO {

	private Long employeeAssetsId;
	private String activeStatus;
	private BigDecimal amount;
	private Long quantity;
	private String dateFrom;
	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	private String employeeName;
	private String employeeDesignation;
	private String itemName;
	private Long employeeId;
	private String recievedRemark;
	private String employeeCode;
	private String Department;
	private Date allocatedOn;
	private Date receivedOn;
	
	public Date getReceivedOn() {
		return receivedOn;
	}

	public void setReceivedOn(Date receivedOn) {
		this.receivedOn = receivedOn;
	}



	
	public Date getAllocatedOn() {
		return allocatedOn;
	}

	public void setAllocatedOn(Date allocatedOn) {
		this.allocatedOn = allocatedOn;
	}

	
	
	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getRecievedRemark() {
		return recievedRemark;
	}

	public void setRecievedRemark(String recievedRemark) {
		this.recievedRemark = recievedRemark;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	private Long companyId;

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Long getEmployeeAssetsId() {
		return employeeAssetsId;
	}

	public void setEmployeeAssetsId(Long employeeAssetsId) {
		this.employeeAssetsId = employeeAssetsId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	private String dateTo;

	private String issueDescription;

	private Long itemId;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}


}
