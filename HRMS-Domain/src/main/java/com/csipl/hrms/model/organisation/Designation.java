package com.csipl.hrms.model.organisation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the Designation database table.
 * 
 */
@Entity
@NamedQuery(name="Designation.findAll", query="SELECT d FROM Designation d")
public class Designation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long designationId;
	private String activeStatus;
	private String allowModi;
	private Long companyId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;
	private String designationName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;
	private Long groupId;
	private Long userId;
	private Long userIdUpdate;

	@OneToMany
	@Transient
	private List<Department>  department;
	//bi-directional many-to-many association to Department
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="designationId")
	private List<DeptDesignationMapping>  deptDesignationMapping;
	
	public List<DeptDesignationMapping> getDeptDesignationMapping() {
		return deptDesignationMapping;
	}

	public void setDeptDesignationMapping(List<DeptDesignationMapping> deptDesignationMapping) {
		this.deptDesignationMapping = deptDesignationMapping;
	}

	public Designation() {
	}

	public Long getDesignationId() {
		return this.designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
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

	public String getDesignationName() {
		return this.designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}


	 

}