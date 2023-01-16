package com.csipl.hrms.dto.organisation;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.DeptDesignationMapping;

public class DesignationDTO {
	private Long designationId;
	private String activeStatus;
	private String allowModi;
	private Long companyId;
	private Date dateCreated;
	private Date dateUpdate;
	private Long departmentId;
	private String designationName;
	private String departmentName;
	private Date effectiveEndDate;
	private Date effectiveStartDate;
	private Long groupId;
	private Long userId;
	private Long userIdUpdate;
	
	private List<DeptDesignationMapping> deptDesignationMapping;
	private List<DepartmentDTO>  department;
	
	public DesignationDTO() {
		super();
	}
	
	
	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public List<DeptDesignationMapping> getDeptDesignationMapping() {
		return deptDesignationMapping;
	}

	public void setDeptDesignationMapping(List<DeptDesignationMapping> deptDesignationMapping) {
		this.deptDesignationMapping = deptDesignationMapping;
	}

	

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public List<DepartmentDTO> getDepartment() {
		return department;
	}

	public void setDepartment(List<DepartmentDTO> department) {
		this.department = department;
	}

	

}
