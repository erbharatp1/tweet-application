package com.csipl.tms.model.latlonglocation;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.tms.model.attendancescheme.AttendanceScheme;

import java.util.Date;


/**
 * The persistent class for the AttendanceLocationMapping database table.
 * 
 */
@Entity
@NamedQuery(name="AttendanceLocationMapping.findAll", query="SELECT a FROM AttendanceLocationMapping a")
public class AttendanceLocationMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attendanceLocationId;

	private String activeStatus;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	//bi-directional many-to-one association to AttendanceScheme
	@ManyToOne
	@JoinColumn(name="attendanceSchemeId")
	private AttendanceScheme attendanceScheme;

	//bi-directional many-to-one association to LocationMaster
	@ManyToOne
	@JoinColumn(name="locationId")
	private LocationMaster locationMaster;

	public AttendanceLocationMapping() {
	}

	public Long getAttendanceLocationId() {
		return this.attendanceLocationId;
	}

	public void setAttendanceLocationId(Long attendanceLocationId) {
		this.attendanceLocationId = attendanceLocationId;
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

	public AttendanceScheme getAttendanceScheme() {
		return this.attendanceScheme;
	}

	public void setAttendanceScheme(AttendanceScheme attendanceScheme) {
		this.attendanceScheme = attendanceScheme;
	}

	public LocationMaster getLocationMaster() {
		return this.locationMaster;
	}

	public void setLocationMaster(LocationMaster locationMaster) {
		this.locationMaster = locationMaster;
	}

}