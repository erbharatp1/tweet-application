package com.csipl.tms.dto.attendancetypetransaction;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AttendanceTypeTxnHistoryDTO {
	
	private Long txnId;

	private String activeStatus;

	private Long attendanceSchemeId;

	private Long attendanceTypeId;

	private Long createdBy;

	private Date createdDate;

	private Long employeeId;

	private Long locationId;

	private Date updatedDate;

	private Long updateedBy;

	public AttendanceTypeTxnHistoryDTO() {
	}

	public Long getTxnId() {
		return this.txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getAttendanceSchemeId() {
		return this.attendanceSchemeId;
	}

	public void setAttendanceSchemeId(Long attendanceSchemeId) {
		this.attendanceSchemeId = attendanceSchemeId;
	}

	public Long getAttendanceTypeId() {
		return this.attendanceTypeId;
	}

	public void setAttendanceTypeId(Long attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdateedBy() {
		return this.updateedBy;
	}

	public void setUpdateedBy(Long updateedBy) {
		this.updateedBy = updateedBy;
	}

}
