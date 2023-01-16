package com.csipl.hrms.dto.employee;

import java.util.Date;

public class EmployeeNomineeDTO {
	private Long employeeId;
	
	private Long employeeNomineeid;

	private String activeStatus;

	private Date dateCreated;

	private Date dateUpdate;

	private Long staturyHeadId;

	private String staturyHeadName;

	private Long userId;

	private Long userIdUpdate;

	private Long familyId;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getEmployeeNomineeid() {
		return employeeNomineeid;
	}

	public void setEmployeeNomineeid(Long employeeNomineeid) {
		this.employeeNomineeid = employeeNomineeid;
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

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getStaturyHeadId() {
		return staturyHeadId;
	}

	public void setStaturyHeadId(Long staturyHeadId) {
		this.staturyHeadId = staturyHeadId;
	}

	public String getStaturyHeadName() {
		return staturyHeadName;
	}

	public void setStaturyHeadName(String staturyHeadName) {
		this.staturyHeadName = staturyHeadName;
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

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

}
