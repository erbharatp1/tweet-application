package com.csipl.hrms.dto.organisation;

import java.util.Date;

import com.csipl.hrms.model.organisation.Designation;

public class DeptDesignationMappingDTO {

	private Long deptDesgId;

	private String activeStatus;

	private Long companyId;

	private Date dateCreated;

	private Date dateUpdate;

	private Long departmentId;

	private Long userId;

	private Long userIdUpdate;

	private Designation designation;

	public DeptDesignationMappingDTO() {
		super();
	}

	public Long getDeptDesgId() {
		return this.deptDesgId;
	}

	public void setDeptDesgId(Long deptDesgId) {
		this.deptDesgId = deptDesgId;
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

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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

	public Designation getDesignation() {
		return this.designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

}