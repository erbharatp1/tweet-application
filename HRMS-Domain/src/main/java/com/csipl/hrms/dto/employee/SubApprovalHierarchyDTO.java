package com.csipl.hrms.dto.employee;

import java.util.Date;

 
public class SubApprovalHierarchyDTO  {
 
	private Long subApprovalId;

	private String activeStatus;

	private Long approvalHierarchyId;

	private Long companyId;

	 
	private Date dateCreated;

	 
	private Date dateUpdate;

	private Long employeeId;

	private String levelStatus;

	private Long userId;

	private Long userIdUpdate;

	 

	public Long getSubApprovalId() {
		return this.subApprovalId;
	}

	public void setSubApprovalId(Long subApprovalId) {
		this.subApprovalId = subApprovalId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getApprovalHierarchyId() {
		return this.approvalHierarchyId;
	}

	public void setApprovalHierarchyId(Long approvalHierarchyId) {
		this.approvalHierarchyId = approvalHierarchyId;
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

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getLevelStatus() {
		return this.levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
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