package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LoanEMI database table.
 * 
 */
@Entity
@NamedQuery(name="LoanEMI.findAll", query="SELECT l FROM LoanEMI l")
public class LoanEMI implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
 	private Long emiNo;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	private BigDecimal emiAmount;

	@Temporal(TemporalType.DATE)
	private Date emiDate;

	private String emiStatus;

	private String remarks;

	private Long userId;
	
	private String transactionFlag;
	
	private String processMonth;

	//bi-directional many-to-one association to LoanIssue
	@ManyToOne
	@JoinColumn(name="transactionNo")
	private LoanIssue loanIssue;

	public LoanEMI() {
	}

	public Long getEmiNo() {
		return this.emiNo;
	}

	public void setEmiNo(Long emiNo) {
		this.emiNo = emiNo;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BigDecimal getEmiAmount() {
		return this.emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Date getEmiDate() {
		return this.emiDate;
	}

	public void setEmiDate(Date emiDate) {
		this.emiDate = emiDate;
	}

	public String getEmiStatus() {
		return this.emiStatus;
	}

	public void setEmiStatus(String emiStatus) {
		this.emiStatus = emiStatus;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LoanIssue getLoanIssue() {
		return this.loanIssue;
	}

	public void setLoanIssue(LoanIssue loanIssue) {
		this.loanIssue = loanIssue;
	}

	public String getTransactionFlag() {
		return transactionFlag;
	}

	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}


}