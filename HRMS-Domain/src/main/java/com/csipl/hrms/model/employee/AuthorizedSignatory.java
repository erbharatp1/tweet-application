package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AuthorizedSignatory database table.
 * 
 */
@Entity
@NamedQuery(name="AuthorizedSignatory.findAll", query="SELECT a FROM AuthorizedSignatory a")
public class AuthorizedSignatory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long authorizedId;

	private String activeStatus;

	private Long companyId;

	private String contactNo;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String designationName;

	private String emailId;

	private Long letterId;

	private String personName;

	private String qrCodeStatus;
	
	private String qrCodeImagePath;
	private String signatureImagePath;

	private Long userId;

	private Long userIdUpdate;

	public AuthorizedSignatory() {
	}

	public Long getAuthorizedId() {
		return this.authorizedId;
	}

	public void setAuthorizedId(Long authorizedId) {
		this.authorizedId = authorizedId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public String getDesignationName() {
		return this.designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getLetterId() {
		return this.letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getQrCodeStatus() {
		return this.qrCodeStatus;
	}

	public void setQrCodeStatus(String qrCodeStatus) {
		this.qrCodeStatus = qrCodeStatus;
	}

	public String getQrCodeImagePath() {
		return qrCodeImagePath;
	}

	public void setQrCodeImagePath(String qrCodeImagePath) {
		this.qrCodeImagePath = qrCodeImagePath;
	}

	public String getSignatureImagePath() {
		return this.signatureImagePath;
	}

	public void setSignatureImagePath(String signatureImagePath) {
		this.signatureImagePath = signatureImagePath;
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

}