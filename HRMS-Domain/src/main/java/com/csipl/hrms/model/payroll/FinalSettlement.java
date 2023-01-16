package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the finalSettlement database table.
 * 
 */
@Entity
@Table(name="finalSettlement")
@NamedQuery(name="FinalSettlement.findAll", query="SELECT f FROM FinalSettlement f")
public class FinalSettlement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long finalSettlementId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	private Long employeeId;

	private BigDecimal gratuity;

	private BigDecimal incomeTax;

	private BigDecimal leaveEncashment;

	private BigDecimal loan;

	private BigDecimal netPayable;

	private BigDecimal salaryPayable;

	private Long userId;
 
	private Long companyId;
	
	public FinalSettlement() {
	}

	public Long getFinalSettlementId() {
		return this.finalSettlementId;
	}

	public void setFinalSettlementId(Long finalSettlementId) {
		this.finalSettlementId = finalSettlementId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUserId() {
		return this.userId;
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

}