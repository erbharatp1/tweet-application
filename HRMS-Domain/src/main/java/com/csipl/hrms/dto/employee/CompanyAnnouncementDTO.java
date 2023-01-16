package com.csipl.hrms.dto.employee;

import java.util.Date;
import java.util.List;

public class CompanyAnnouncementDTO {
	private Long massCommunicationId;	 
	private List<Long> departmentId;
	private String departmentName;
	private Date dateFrom;
	private Date dateTo;
	private Long userId;
	private Date dateCreated;
	private Long companyId;
	private Long userIdUpdate;
	private Long deptId;
	public Long  companyAnnouncementId;
	 

	public Long getCompanyAnnouncementId() {
		return companyAnnouncementId;
	}

	public void setCompanyAnnouncementId(Long companyAnnouncementId) {
		this.companyAnnouncementId = companyAnnouncementId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getMassCommunicationId() {
		return massCommunicationId;
	}

	public void setMassCommunicationId(Long massCommunicationId) {
		this.massCommunicationId = massCommunicationId;
	}

	public List<Long> getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(List<Long> departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	 
}
