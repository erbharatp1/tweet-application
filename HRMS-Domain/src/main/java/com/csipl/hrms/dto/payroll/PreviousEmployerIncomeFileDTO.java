package com.csipl.hrms.dto.payroll;

import java.util.Date;

import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;

public class PreviousEmployerIncomeFileDTO {

	private Long previousEmployerIncomeFileId;
	private String activeStatus;
	private Date dateCreated;
	private Date dateUpdate;
	private String fileName;
	private String originalFilename;
	private String filePath;
	private Long userId;
	private Long userIdUpdate;
	private Long employeeId;
	private Long financialYearId;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	private PreviousEmployerIncomeTds previousEmployerIncomeTds;

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public PreviousEmployerIncomeTds getPreviousEmployerIncomeTds() {
		return previousEmployerIncomeTds;
	}

	public void setPreviousEmployerIncomeTds(PreviousEmployerIncomeTds previousEmployerIncomeTds) {
		this.previousEmployerIncomeTds = previousEmployerIncomeTds;
	}

	public Long getPreviousEmployerIncomeFileId() {
		return previousEmployerIncomeFileId;
	}

	public void setPreviousEmployerIncomeFileId(Long previousEmployerIncomeFileId) {
		this.previousEmployerIncomeFileId = previousEmployerIncomeFileId;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}


	

	
}
