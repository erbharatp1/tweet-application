package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class OneTimeEarningDeductionDTO {
	private Long Id;
	
	private BigDecimal amount;

	private Long companyId;
	
	private String earningDeductionMonth;

	private Long employeeId;

	private String isEarningDeduction;

	private Long payHeadId;

	private String remarks;

	private String type;
	
	private Date dateCreated;
	
	private Long userId;
	
	private String employeeName;
	private String employeeCode;
	private String departmentName;
	private String designationName;
	private String deductionType;
	
	private Long updateId;

	private Date updateDate;	
	
	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	private String	fullNameCodeValues;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getEarningDeductionMonth() {
		return earningDeductionMonth;
	}

	public void setEarningDeductionMonth(String earningDeductionMonth) {
		this.earningDeductionMonth = earningDeductionMonth;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getIsEarningDeduction() {
		return isEarningDeduction;
	}

	public void setIsEarningDeduction(String isEarningDeduction) {
		this.isEarningDeduction = isEarningDeduction;
	}

	public Long getPayHeadId() {
		return payHeadId;
	}

	public void setPayHeadId(Long payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "OneTimeEarningDeductionDTO [Id=" + Id + ", amount=" + amount + ", companyId=" + companyId
				+ ", earningDeductionMonth=" + earningDeductionMonth + ", employeeId=" + employeeId
				+ ", isEarningDeduction=" + isEarningDeduction + ", payHeadId=" + payHeadId + ", remarks=" + remarks
				+ ", type=" + type + ", dateCreated=" + dateCreated + ", userId=" + userId + "]";
	}

	public String getFullNameCodeValues() {
		return fullNameCodeValues;
	}

	public void setFullNameCodeValues(String fullNameCodeValues) {
		this.fullNameCodeValues = fullNameCodeValues;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}
	
	
}
