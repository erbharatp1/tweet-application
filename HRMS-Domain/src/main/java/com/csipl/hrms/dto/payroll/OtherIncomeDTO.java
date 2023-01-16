package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class OtherIncomeDTO {
	
	private Long otherIncomeId;
	private BigDecimal amount;
	private String description;
	private Long employeeId;
	private Long userId;
	private Date dateCreated;
	private String status;
	private Long companyId;
	private Long userIdUpdate;
	private MultipartFile file;
	
	private String otherIncomeDoc;
	private String documentName;
	
	private String tdsTransactionUpdateStatus;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}
	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	private String approveStatus;
	
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
	public Long getOtherIncomeId() {
		return otherIncomeId;
	}
	public void setOtherIncomeId(Long otherIncomeId) {
		this.otherIncomeId = otherIncomeId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getOtherIncomeDoc() {
		return otherIncomeDoc;
	}
	public void setOtherIncomeDoc(String otherIncomeDoc) {
		this.otherIncomeDoc = otherIncomeDoc;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getTdsTransactionUpdateStatus() {
		return tdsTransactionUpdateStatus;
	}
	public void setTdsTransactionUpdateStatus(String tdsTransactionUpdateStatus) {
		this.tdsTransactionUpdateStatus = tdsTransactionUpdateStatus;
	}

}
