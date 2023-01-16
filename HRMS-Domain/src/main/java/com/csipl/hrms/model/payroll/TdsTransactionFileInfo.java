package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name="TdsTransactionFileInfo.findAll", query="SELECT t FROM TdsTransactionFileInfo t")
public class TdsTransactionFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tdsTransactionFileInfoId;

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
	@JoinColumn(name="tdsTransactionId")
	private TdsTransaction tdsTransaction;
	
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

	public TdsTransactionFileInfo() {
	}
  
}
