package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class TdsDeductionDTO {

	private Long tdsDeductionId;

	private Date dateCreated;

	private Date dateUpdate;

	private String remark;

	private BigDecimal taxDeductedMonthly;

	private BigDecimal taxTobeDeductedMonthly;

	private BigDecimal totalTax;

	private Long userId;

	private Long userIdUpdate;

	private Long employeeId;

	private String employeeName;

	private Long financialYearId;

	private Long companyId;
	private String firstName;
	private String lastName;
	private String designationName;
	private String activeStatus;
	private String processMonth;
	
	private String financialYear;
	private BigDecimal tdsAmount;
	
	
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

	public Long getTdsDeductionId() {
		return tdsDeductionId;
	}

	public void setTdsDeductionId(Long tdsDeductionId) {
		this.tdsDeductionId = tdsDeductionId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTaxDeductedMonthly() {
		return taxDeductedMonthly;
	}

	public void setTaxDeductedMonthly(BigDecimal taxDeductedMonthly) {
		this.taxDeductedMonthly = taxDeductedMonthly;
	}

	public BigDecimal getTaxTobeDeductedMonthly() {
		return taxTobeDeductedMonthly;
	}

	public void setTaxTobeDeductedMonthly(BigDecimal taxTobeDeductedMonthly) {
		this.taxTobeDeductedMonthly = taxTobeDeductedMonthly;
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

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public BigDecimal getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(BigDecimal tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
}
