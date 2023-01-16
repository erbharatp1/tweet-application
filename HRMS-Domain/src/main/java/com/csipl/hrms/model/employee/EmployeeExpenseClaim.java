package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the EmployeeExpenseClaims database table.
 * 
 */
@Entity
@Table(name="EmployeeExpenseClaims")
@NamedQuery(name="EmployeeExpenseClaim.findAll", query="SELECT e FROM EmployeeExpenseClaim e")
public class EmployeeExpenseClaim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long employeeExpeneseClaimId;

	private Long amountExpenses;

	@Temporal(TemporalType.DATE)
	private Date approvalDate;

	private String approvalRemark;
	
	private String expenceTitle;

	private Long billNumber;

	@Temporal(TemporalType.DATE)
	private Date claimDate;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long employeId;

	private Long expenseTypeId;

	private String filePath;

	private String marchantName;

	private String payrollMonth;

	private String recheckRemark;

	private String remark;

	private String status;

	private Long userId;

	
	private Long userIdUpDate;

	private String varifyStatus;
	private Long approvedAmount;

	public EmployeeExpenseClaim() {
	}

	public Long getEmployeeExpeneseClaimId() {
		return this.employeeExpeneseClaimId;
	}

	public void setEmployeeExpeneseClaimId(Long employeeExpeneseClaimId) {
		this.employeeExpeneseClaimId = employeeExpeneseClaimId;
	}

	public String getExpenceTitle() {
		return expenceTitle;
	}

	public void setExpenceTitle(String expenceTitle) {
		this.expenceTitle = expenceTitle;
	}

	public Long getAmountExpenses() {
		return this.amountExpenses;
	}

	public void setAmountExpenses(Long amountExpenses) {
		this.amountExpenses = amountExpenses;
	}

	public Date getApprovalDate() {
		return this.approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovalRemark() {
		return this.approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public Long getBillNumber() {
		return this.billNumber;
	}

	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}

	public Date getClaimDate() {
		return this.claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
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

	public Long getEmployeId() {
		return this.employeId;
	}

	public void setEmployeId(Long employeId) {
		this.employeId = employeId;
	}

	public Long getExpenseTypeId() {
		return this.expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMarchantName() {
		return this.marchantName;
	}

	public void setMarchantName(String marchantName) {
		this.marchantName = marchantName;
	}

	public String getPayrollMonth() {
		return this.payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public String getRecheckRemark() {
		return this.recheckRemark;
	}

	public void setRecheckRemark(String recheckRemark) {
		this.recheckRemark = recheckRemark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpDate() {
		return this.userIdUpDate;
	}

	public void setUserIdUpDate(Long userIdUpDate) {
		this.userIdUpDate = userIdUpDate;
	}

	public String getVarifyStatus() {
		return this.varifyStatus;
	}

	public void setVarifyStatus(String varifyStatus) {
		this.varifyStatus = varifyStatus;
	}

	public Long getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Long approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

}