package com.csipl.hrms.dto.payroll;

import java.util.Date;

public class ArearMasterDTO {
	private Long arearId;
	private Date arearFrom;
	private Date arearTo;
	private Long companyId;
	private Date dateCreated;
	private Long employeeId;
	private String bookedPayrollMonth;
	private Date dateUpdate;

	private Long isBooked;

	private String payrollMonth;

	private Long userId;

	private Long userIdUpdate;
	public Long getArearId() {
		return arearId;
	}

	public void setArearId(Long arearId) {
		this.arearId = arearId;
	}

	public Date getArearFrom() {
		return arearFrom;
	}

	public void setArearFrom(Date arearFrom) {
		this.arearFrom = arearFrom;
	}

	public Date getArearTo() {
		return arearTo;
	}

	public void setArearTo(Date arearTo) {
		this.arearTo = arearTo;
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

	public Long getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Long isBooked) {
		this.isBooked = isBooked;
	}

	public String getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
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

	public String getBookedPayrollMonth() {
		return bookedPayrollMonth;
	}

	public void setBookedPayrollMonth(String bookedPayrollMonth) {
		this.bookedPayrollMonth = bookedPayrollMonth;
	}

	
}
