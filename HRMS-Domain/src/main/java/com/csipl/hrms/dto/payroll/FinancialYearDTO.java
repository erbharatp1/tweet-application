package com.csipl.hrms.dto.payroll;

import java.util.Date;

import com.csipl.hrms.model.BaseModel;
 
public class FinancialYearDTO  {
	private Long financialYearId;	
	private Date dateFrom;	
	private String isPayrollDaysManuals;	
	private String financialYear;	
	private Long userId;
	private Date dateCreated;
	private Date dateTo;
	private Long companyId;
	private Long groupId;
	
	private String activeStatus;
	
	// new changes by nidhi
	private String isHolidayConsider;
	private String isWeekoffConsider;
	private int payrollDays;
	
	
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

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getIsPayrollDaysManuals() {
		return isPayrollDaysManuals;
	}

	public void setIsPayrollDaysManuals(String isPayrollDaysManuals) {
		this.isPayrollDaysManuals = isPayrollDaysManuals;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getIsHolidayConsider() {
		return isHolidayConsider;
	}

	public void setIsHolidayConsider(String isHolidayConsider) {
		this.isHolidayConsider = isHolidayConsider;
	}

	public String getIsWeekoffConsider() {
		return isWeekoffConsider;
	}

	public void setIsWeekoffConsider(String isWeekoffConsider) {
		this.isWeekoffConsider = isWeekoffConsider;
	}

	public int getPayrollDays() {
		return payrollDays;
	}

	public void setPayrollDays(int payrollDays) {
		this.payrollDays = payrollDays;
	}

	
}
