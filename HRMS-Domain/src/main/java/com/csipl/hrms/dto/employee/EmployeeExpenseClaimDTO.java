package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeExpenseClaimDTO {
	private Long employeeExpeneseClaimId;
	private Long amountExpenses;
	private Date approvalDate;
	private String approvalRemark;
	private Long billNumber;
	private Date claimDate;
	private Long companyId;
	private Date dateCreated;
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
	private String expenceTitle;

	// np
	private String employeeName;
	private String expenseTypeName;
	private Long approvedAmount;

	public String getExpenceTitle() {
		return expenceTitle;
	}

	public void setExpenceTitle(String expenceTitle) {
		this.expenceTitle = expenceTitle;
	}

	public EmployeeExpenseClaimDTO() {
	}

	public Long getEmployeeExpeneseClaimId() {
		return this.employeeExpeneseClaimId;
	}

	public void setEmployeeExpeneseClaimId(Long employeeExpeneseClaimId) {
		this.employeeExpeneseClaimId = employeeExpeneseClaimId;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Long getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Long approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

}