package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TdsHouseRentFileInfo database table.
 * 
 */
@Entity
@NamedQuery(name="TdsHouseRentFileInfo.findAll", query="SELECT t FROM TdsHouseRentFileInfo t")
public class TdsHouseRentFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsHouseRentFileInfoId;

	private String activeStatus;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdated;

	private String fileName;
	
	private String originalFilename;
	
	private String filePath;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to TdsHouseRentInfo
	@ManyToOne
	@JoinColumn(name="tdsHouseRentInfoId")
	private TdsHouseRentInfo tdsHouseRentInfo;

	public TdsHouseRentFileInfo() {
	}

	 
	public Long getTdsHouseRentFileInfoId() {
		return tdsHouseRentFileInfoId;
	}

	public void setTdsHouseRentFileInfoId(Long tdsHouseRentFileInfoId) {
		this.tdsHouseRentFileInfoId = tdsHouseRentFileInfoId;
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

	public String getOriginalFilename() {
		return originalFilename;
	}


	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}


	public TdsHouseRentInfo getTdsHouseRentInfo() {
		return this.tdsHouseRentInfo;
	}

	public void setTdsHouseRentInfo(TdsHouseRentInfo tdsHouseRentInfo) {
		this.tdsHouseRentInfo = tdsHouseRentInfo;
	}

}