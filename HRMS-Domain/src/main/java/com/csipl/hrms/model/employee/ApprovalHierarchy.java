package com.csipl.hrms.model.employee;

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

/**
 * The persistent class for the ApprovalHierarchy database table.
 * 
 */
@Entity
@NamedQuery(name = "ApprovalHierarchy.findAll", query = "SELECT a FROM ApprovalHierarchy a")
public class ApprovalHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long approvalHierarchyId;

	private String activeStatus;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private Long designationId;

	private String levels;

	private Long userId;

	private Long userIdUpdate;

	// bi-directional many-to-one association to ApprovalHierarchyMaster
	@ManyToOne
	@JoinColumn(name = "approvalHierarchyMasterId")
	private ApprovalHierarchyMaster approvalHierarchyMaster;

	public ApprovalHierarchy() {
	}

	public Long getApprovalHierarchyId() {
		return approvalHierarchyId;
	}

	public void setApprovalHierarchyId(Long approvalHierarchyId) {
		this.approvalHierarchyId = approvalHierarchyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
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

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getLevels() {
		return this.levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public ApprovalHierarchyMaster getApprovalHierarchyMaster() {
		return this.approvalHierarchyMaster;
	}

	public void setApprovalHierarchyMaster(ApprovalHierarchyMaster approvalHierarchyMaster) {
		this.approvalHierarchyMaster = approvalHierarchyMaster;
	}

}