package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.List;

public class TdsTransactionDTO {

	private Long tdsTransactionId;
	private String approveStatus;
	private BigDecimal investmentAmount;
	private BigDecimal approvedAmount;
	private String investmentDetail;
	private int noOfDocuments;
	private String proof;
	private Long tdsSectionSetupId;
	private String tdsSectionName;
	private Long tdsGroupSetupId;
	private String tdsGroupName;
	private String tdsDescription;
	private Long employeeId;
	private Long userId;
	private String dateCreated;
	private BigDecimal maxLimit;
	private String fileLocation;
	private String status;
	private String financialYear;
	private String city;
	private Long userIdUpdate;
	private Long tdsGroupMasterId;
	private BigDecimal tdsGroupmaxLimit;
	private String remarks;
	private String landlordName;
	private String landlordPAN;
	private String tdsTransactionUpdateStatus;
	
	private String employeeName;
	private String employeeCode;
	private String department;
	private String designation;
	private String dateOFJoining;
	private String PanNumber;
	private BigDecimal totalOtherIncome;
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateOFJoining() {
		return dateOFJoining;
	}

	public void setDateOFJoining(String dateOFJoining) {
		this.dateOFJoining = dateOFJoining;
	}

	public String getPanNumber() {
		return PanNumber;
	}

	public void setPanNumber(String panNumber) {
		PanNumber = panNumber;
	}

	private List<TdsTransactionFileInfoDTO> tdsTransactionFileInfoDTO;
	
	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Long getTdsTransactionId() {
		return tdsTransactionId;
	}

	public BigDecimal getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getInvestmentDetail() {
		return investmentDetail;
	}

	public void setInvestmentDetail(String investmentDetail) {
		this.investmentDetail = investmentDetail;
	}

	public int getNoOfDocuments() {
		return noOfDocuments;
	}

	public void setNoOfDocuments(int noOfDocuments) {
		this.noOfDocuments = noOfDocuments;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getTdsSectionName() {
		return tdsSectionName;
	}

	public Long getTdsSectionSetupId() {
		return tdsSectionSetupId;
	}

	public void setTdsSectionSetupId(Long tdsSectionSetupId) {
		this.tdsSectionSetupId = tdsSectionSetupId;
	}

	public Long getTdsGroupSetupId() {
		return tdsGroupSetupId;
	}

	public void setTdsGroupSetupId(Long tdsGroupSetupId) {
		this.tdsGroupSetupId = tdsGroupSetupId;
	}

	public void setTdsSectionName(String tdsSectionName) {
		this.tdsSectionName = tdsSectionName;
	}

	public String getTdsGroupName() {
		return tdsGroupName;
	}

	public String getTdsDescription() {
		return tdsDescription;
	}

	public void setTdsDescription(String tdsDescription) {
		this.tdsDescription = tdsDescription;
	}

	public void setTdsGroupName(String tdsGroupName) {
		this.tdsGroupName = tdsGroupName;
	}

	public void setTdsTransactionId(Long tdsTransactionId) {
		this.tdsTransactionId = tdsTransactionId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getTdsGroupMasterId() {
		return tdsGroupMasterId;
	}

	public void setTdsGroupMasterId(Long tdsGroupMasterId) {
		this.tdsGroupMasterId = tdsGroupMasterId;
	}

	public BigDecimal getTdsGroupmaxLimit() {
		return tdsGroupmaxLimit;
	}

	public void setTdsGroupmaxLimit(BigDecimal tdsGroupmaxLimit) {
		this.tdsGroupmaxLimit = tdsGroupmaxLimit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordPAN() {
		return landlordPAN;
	}

	public void setLandlordPAN(String landlordPAN) {
		this.landlordPAN = landlordPAN;
	}

	public List<TdsTransactionFileInfoDTO> getTdsTransactionFileInfoDTO() {
		return tdsTransactionFileInfoDTO;
	}

	public void setTdsTransactionFileInfoDTO(List<TdsTransactionFileInfoDTO> tdsTransactionFileInfoDTO) {
		this.tdsTransactionFileInfoDTO = tdsTransactionFileInfoDTO;
	}

	public String getTdsTransactionUpdateStatus() {
		return tdsTransactionUpdateStatus;
	}

	public void setTdsTransactionUpdateStatus(String tdsTransactionUpdateStatus) {
		this.tdsTransactionUpdateStatus = tdsTransactionUpdateStatus;
	}

	public BigDecimal getTotalOtherIncome() {
		return totalOtherIncome;
	}

	public void setTotalOtherIncome(BigDecimal totalOtherIncome) {
		this.totalOtherIncome = totalOtherIncome;
	}
}
