package com.csipl.tms.model.attendancetypetransaction;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AttendanceTypeTxnHistory database table.
 * 
 */
@Entity
@NamedQuery(name="AttendanceTypeTxnHistory.findAll", query="SELECT a FROM AttendanceTypeTxnHistory a")
public class AttendanceTypeTxnHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long txnId;

	private String activeStatus;

	private Long attendanceSchemeId;

	private String attendanceTypeId;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name="EmployeeId")
	private Long employeeId;

	private String locationId;

	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	private Long updateedBy;

	public AttendanceTypeTxnHistory() {
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

	public String getAttendanceTypeId() {
		return this.attendanceTypeId;
	}

	public void setAttendanceTypeId(String attendanceTypeId) {
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

	public String getLocationId() {
		return this.locationId;
	}

	public void setLocationId(String locationId) {
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