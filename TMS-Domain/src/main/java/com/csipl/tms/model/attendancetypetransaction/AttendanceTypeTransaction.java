package com.csipl.tms.model.attendancetypetransaction;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.tms.model.attendancescheme.AttendanceScheme;
import com.csipl.tms.model.attendancetype.AttendanceType;

import java.util.Date;


/**
 * The persistent class for the AttendanceTypeTransaction database table.
 * 
 */
@Entity
@NamedQuery(name="AttendanceTypeTransaction.findAll", query="SELECT a FROM AttendanceTypeTransaction a")
public class AttendanceTypeTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attendanceTypeTransactionId;

	private String activeStatus;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	//bi-directional many-to-one association to AttendanceScheme
	@ManyToOne
	@JoinColumn(name="attendanceSchemeId")
	private AttendanceScheme attendanceScheme;

	//bi-directional many-to-one association to AttendanceType
	@ManyToOne
	@JoinColumn(name="attendanceTypeId")
	private AttendanceType attendanceType;

	public AttendanceTypeTransaction() {
	}

	public Long getAttendanceTypeTransactionId() {
		return this.attendanceTypeTransactionId;
	}

	public void setAttendanceTypeTransactionId(Long attendanceTypeTransactionId) {
		this.attendanceTypeTransactionId = attendanceTypeTransactionId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public AttendanceScheme getAttendanceScheme() {
		return this.attendanceScheme;
	}

	public void setAttendanceScheme(AttendanceScheme attendanceScheme) {
		this.attendanceScheme = attendanceScheme;
	}

	public AttendanceType getAttendanceType() {
		return this.attendanceType;
	}

	public void setAttendanceType(AttendanceType attendanceType) {
		this.attendanceType = attendanceType;
	}

}