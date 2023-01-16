package com.csipl.tms.dto.latlonglocation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.attendancetypetransaction.AttendanceTypeTransactionDTO;



public class LocationMasterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long locationId;

	private Long createdBy;

	private Date createdDate;

	private double latitude;

	private String locationAddress;

	private double longitude;

	private double radius;

	private List<AttendanceTypeTransactionDTO> attendanceTypeTransactionsDtoList;

	public LocationMasterDTO() {
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return this.locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public List<AttendanceTypeTransactionDTO> getAttendanceTypeTransactions() {
		return this.attendanceTypeTransactionsDtoList;
	}

	public void setAttendanceTypeTransactions(List<AttendanceTypeTransactionDTO> attendanceTypeTransactions) {
		this.attendanceTypeTransactionsDtoList = attendanceTypeTransactions;
	}


}