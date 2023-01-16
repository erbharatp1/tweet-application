package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CompanyPolicy database table.
 * 
 */
@Entity
@NamedQuery(name="CompanyPolicy.findAll", query="SELECT c FROM CompanyPolicy c")
public class CompanyPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long policyId;

	private String activeStatus;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String fileLocation;

	private String policyName;

	private Long userId;

	private Long userIdUpdate;

	public CompanyPolicy() {
	}

	public Long getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
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

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getPolicyName() {
		return this.policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
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