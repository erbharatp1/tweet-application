package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;

public class FinalSettlementReportDTO {
	private Long finalReportId;

	private Long employeeId;

	private String lastPaidMonth;

	private BigDecimal lastPaidSalary;

	private BigDecimal salaryPayable;

	private String salaryPayableMonth;

	public Long getFinalReportId() {
		return finalReportId;
	}

	public void setFinalReportId(Long finalReportId) {
		this.finalReportId = finalReportId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getLastPaidMonth() {
		return lastPaidMonth;
	}

	public void setLastPaidMonth(String lastPaidMonth) {
		this.lastPaidMonth = lastPaidMonth;
	}

	public BigDecimal getLastPaidSalary() {
		return lastPaidSalary;
	}

	public void setLastPaidSalary(BigDecimal lastPaidSalary) {
		this.lastPaidSalary = lastPaidSalary;
	}

	public BigDecimal getSalaryPayable() {
		return salaryPayable;
	}

	public void setSalaryPayable(BigDecimal salaryPayable) {
		this.salaryPayable = salaryPayable;
	}

	public String getSalaryPayableMonth() {
		return salaryPayableMonth;
	}

	public void setSalaryPayableMonth(String salaryPayableMonth) {
		this.salaryPayableMonth = salaryPayableMonth;
	}
	
}
