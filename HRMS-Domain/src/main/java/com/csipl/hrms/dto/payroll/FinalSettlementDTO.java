package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class FinalSettlementDTO {
	private Long finalSettlementId;

	private Date dateCreated;

	private Long employeeId;

	private BigDecimal gratuity;

	private BigDecimal incomeTax;

	private BigDecimal leaveEncashment;

	private BigDecimal loan;

	private BigDecimal netPayable;

	private BigDecimal salaryPayable;
	private Long companyId;
	private Long userId;

	public Long getFinalSettlementId() {
		return finalSettlementId;
	}

	public void setFinalSettlementId(Long finalSettlementId) {
		this.finalSettlementId = finalSettlementId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}



	public BigDecimal getLoan() {
		return loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	public BigDecimal getNetPayable() {
		return netPayable;
	}

	public void setNetPayable(BigDecimal netPayable) {
		this.netPayable = netPayable;
	}

	public BigDecimal getSalaryPayable() {
		return salaryPayable;
	}

	public void setSalaryPayable(BigDecimal salaryPayable) {
		this.salaryPayable = salaryPayable;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getGratuity() {
		return gratuity;
	}

	public void setGratuity(BigDecimal gratuity) {
		this.gratuity = gratuity;
	}

	public BigDecimal getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}

	public BigDecimal getLeaveEncashment() {
		return leaveEncashment;
	}

	public void setLeaveEncashment(BigDecimal leaveEncashment) {
		this.leaveEncashment = leaveEncashment;
	}
	
}
