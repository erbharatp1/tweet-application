package com.csipl.hrms.dto.employee;

import java.util.Date;

import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;

public class ApprovalHierarchyDTO {

	private Long approvalHierarchyId;
	private String activeStatus;
	private Long companyId;
	private Date dateCreated;
	private Date dateUpdate;
	private Long designationId;
	private String levels;
	private Long userId;
	private Long userIdUpdate;
	private Long approvalHierarchyMasterId;
	private Long letterId;
	private String letterName;

//	//bi-directional many-to-one association to ApprovalHierarchyMaster
//	@ManyToOne
//	@JoinColumn(name="approvalHierarchyMasterId")
	private ApprovalHierarchyMaster approvalHierarchyMaster;

	public Long getApprovalHierarchyId() {
		return approvalHierarchyId;
	}

	public void setApprovalHierarchyId(Long approvalHierarchyId) {
		this.approvalHierarchyId = approvalHierarchyId;
	}

	public Long getApprovalHierarchyMasterId() {
		return approvalHierarchyMasterId;
	}

	public void setApprovalHierarchyMasterId(Long approvalHierarchyMasterId) {
		this.approvalHierarchyMasterId = approvalHierarchyMasterId;
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

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

}