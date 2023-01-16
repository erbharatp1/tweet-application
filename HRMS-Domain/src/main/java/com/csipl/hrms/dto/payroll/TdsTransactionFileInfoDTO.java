/**
 * 
 */
package com.csipl.hrms.dto.payroll;

import java.util.Date;

import com.csipl.hrms.model.payroll.TdsTransaction;

/**
 * @author soft-t
 *
 */
public class TdsTransactionFileInfoDTO {
	
	private Long tdsTransactionFileInfoId;

	public Long getTdsTransactionFileInfoId() {
		return tdsTransactionFileInfoId;
	}

	public void setTdsTransactionFileInfoId(Long tdsTransactionFileInfoId) {
		this.tdsTransactionFileInfoId = tdsTransactionFileInfoId;
	}

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

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
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

	public TdsTransaction getTdsTransaction() {
		return tdsTransaction;
	}

	public void setTdsTransaction(TdsTransaction tdsTransaction) {
		this.tdsTransaction = tdsTransaction;
	}

	private String activeStatus;

	private Date dateCreated;

	private Date dateUpdated;

	private String fileName;
	
	private String originalFilename;
	
	private String filePath;

	private Long userId;

	private Long userIdUpdate;

	private TdsTransaction tdsTransaction;
}
