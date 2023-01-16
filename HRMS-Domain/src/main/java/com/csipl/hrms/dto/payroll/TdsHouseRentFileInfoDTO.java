package com.csipl.hrms.dto.payroll;

import java.util.Date;

/**
 * The persistent class for the TdsHouseRentFileInfo database table.
 * 
 */

public class TdsHouseRentFileInfoDTO {

	private Long tdsHouseRentFileInfoId;

	private String activeStatus;

	private Date dateCreated;

	private Date dateUpdated;

	private String fileName;

	private String filePath;

	private Long userId;

	private Long userIdUpdate;

	private String originalFilename;

	private Long tdsHouseRentInfoId;

	public Long getTdsHouseRentInfoId() {
		return tdsHouseRentInfoId;
	}

	public void setTdsHouseRentInfoId(Long tdsHouseRentInfoId) {
		this.tdsHouseRentInfoId = tdsHouseRentInfoId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
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

	public Long getTdsHouseRentFileInfoId() {
		return tdsHouseRentFileInfoId;
	}

	public void setTdsHouseRentFileInfoId(Long tdsHouseRentFileInfoId) {
		this.tdsHouseRentFileInfoId = tdsHouseRentFileInfoId;
	}

}