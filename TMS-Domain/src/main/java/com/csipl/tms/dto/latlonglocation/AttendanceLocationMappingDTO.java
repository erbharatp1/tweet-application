package com.csipl.tms.dto.latlonglocation;

import java.util.Date;

import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;


public class AttendanceLocationMappingDTO {


	private Long attendanceLocationId;

	private String activeStatus;

	private Long createdBy;

	private Date createdDate;

	private AttendanceSchemeDTO attendanceSchemeDto;

	private LocationMasterDTO locationMasterDto;

	//LocationMasterDTO
	private Long locationId;

	private double latitude;

	private String locationAddress;

	private double longitude;

	private double radius;
	
	public AttendanceLocationMappingDTO() {
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

	public AttendanceSchemeDTO getAttendanceSchemeDto() {
		return attendanceSchemeDto;
	}

	public void setAttendanceSchemeDto(AttendanceSchemeDTO attendanceSchemeDto) {
		this.attendanceSchemeDto = attendanceSchemeDto;
	}

	public LocationMasterDTO getLocationMasterDto() {
		return locationMasterDto;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setLocationMasterDto(LocationMasterDTO locationMasterDto) {
		this.locationMasterDto = locationMasterDto;
	}



}