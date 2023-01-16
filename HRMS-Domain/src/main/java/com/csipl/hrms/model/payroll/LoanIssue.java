package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.PayOut;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the LoanIssue database table.
 * 
 */
@Entity
@NamedQuery(name="LoanIssue.findAll", query="SELECT l FROM LoanIssue l")
public class LoanIssue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
 	private Long transactionNo;

	private String activeStatus;

	private String allowModi;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	private BigDecimal emiAmount;

	@Temporal(TemporalType.DATE)
	private Date emiStartDate;

	private String instrumentNo;

	//private String LongerestType;

	private String isSettlementCompleted;

	@Temporal(TemporalType.DATE)
	private Date issueDate;

	private BigDecimal loanAmount;

	private BigDecimal loanPendingAmount;

	private String loanType;

	private String naration;

	private int noOfEmi;

	private String paymentMode;

	//private BigDecimal rateOfLongerest;

	private String remark;

	private BigDecimal settlementAmount;

	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to LoanEMI
	@OneToMany(mappedBy="loanIssue",cascade = CascadeType.ALL)
	private List<LoanEMI> loanEmis;
  
	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;
	
	@Transient
	private Boolean isloanDeduction;
	

	//bi-directional many-to-one association to PayOut
//	@OneToMany(mappedBy="loanIssue")
//	private List<PayOut> payOuts;

	/*public List<PayOut> getPayOuts() {
		return payOuts;
	}

	public void setPayOuts(List<PayOut> payOuts) {
		this.payOuts = payOuts;
	}*/

	public LoanIssue() {
	}

	public Long getTransactionNo() {
		return this.transactionNo;
	}

	public void setTransactionNo(Long transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
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

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public BigDecimal getEmiAmount() {
		return this.emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Date getEmiStartDate() {
		return this.emiStartDate;
	}

	public void setEmiStartDate(Date emiStartDate) {
		this.emiStartDate = emiStartDate;
	}

	public String getInstrumentNo() {
		return this.instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	/*public String getLongerestType() {
		return this.LongerestType;
	}

	public void setLongerestType(String LongerestType) {
		this.LongerestType = LongerestType;
	}*/

	public String getIsSettlementCompleted() {
		return this.isSettlementCompleted;
	}

	public void setIsSettlementCompleted(String isSettlementCompleted) {
		this.isSettlementCompleted = isSettlementCompleted;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLoanPendingAmount() {
		return this.loanPendingAmount;
	}

	public void setLoanPendingAmount(BigDecimal loanPendingAmount) {
		this.loanPendingAmount = loanPendingAmount;
	}

	public String getLoanType() {
		return this.loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getNaration() {
		return this.naration;
	}

	public void setNaration(String naration) {
		this.naration = naration;
	}

	public int getNoOfEmi() {
		return this.noOfEmi;
	}

	public void setNoOfEmi(int noOfEmi) {
		this.noOfEmi = noOfEmi;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

/*	public BigDecimal getRateOfLongerest() {
		return this.rateOfLongerest;
	}

	public void setRateOfLongerest(BigDecimal rateOfLongerest) {
		this.rateOfLongerest = rateOfLongerest;
	}*/

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public List<LoanEMI> getLoanEmis() {
		return this.loanEmis;
	}

	public void setLoanEmis(List<LoanEMI> loanEmis) {
		this.loanEmis = loanEmis;
	}

	public LoanEMI addLoanEmi(LoanEMI loanEmi) {
		getLoanEmis().add(loanEmi);
		loanEmi.setLoanIssue(this);

		return loanEmi;
	}

	public LoanEMI removeLoanEmi(LoanEMI loanEmi) {
		getLoanEmis().remove(loanEmi);
		loanEmi.setLoanIssue(null);

		return loanEmi;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Boolean getIsloanDeduction() {
		return isloanDeduction;
	}

	public void setIsloanDeduction(Boolean isloanDeduction) {
		this.isloanDeduction = isloanDeduction;
	}
	

}