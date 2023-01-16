package com.csipl.hrms.dto.payroll;

import java.util.Date;

/**
 * The persistent class for the FinancialMonth database table.
 * 
 */

public class FinancialMonthDTO {

	private Long financialMonthId;

	private Long companyId;

	private Date dateCreated;

	private String month;

	private Date updatedDate;

	private Long userId;

	private Long userIdUpdate;

	public FinancialMonthDTO() {
	}

	public Long getFinancialMonthId() {
		return this.financialMonthId;
	}

	public void setFinancialMonthId(Long financialMonthId) {
		this.financialMonthId = financialMonthId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

}