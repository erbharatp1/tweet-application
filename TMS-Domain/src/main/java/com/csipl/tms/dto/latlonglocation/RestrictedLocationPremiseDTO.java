package com.csipl.tms.dto.latlonglocation;

public class RestrictedLocationPremiseDTO {

	
	private Long attendanceSchemeId ;
	private String firstName ;
	private Long employeeId ;
	private Long userId;
	private String aschemeStatus  ;
	private Long arDays;
	private Long attendanceTypeId;
	private String typeCode ;
	private String aTypeTransactionstatus; 
	private Long locationId;
	private Double latitude;
	private Double longitude;
	private Double radius;
	public Long getAttendanceSchemeId() {
		return attendanceSchemeId;
	}
	public void setAttendanceSchemeId(Long attendanceSchemeId) {
		this.attendanceSchemeId = attendanceSchemeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAschemeStatus() {
		return aschemeStatus;
	}
	public void setAschemeStatus(String aschemeStatus) {
		this.aschemeStatus = aschemeStatus;
	}
	public Long getArDays() {
		return arDays;
	}
	public void setArDays(Long arDays) {
		this.arDays = arDays;
	}
	public Long getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(Long attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getaTypeTransactionstatus() {
		return aTypeTransactionstatus;
	}
	public void setaTypeTransactionstatus(String aTypeTransactionstatus) {
		this.aTypeTransactionstatus = aTypeTransactionstatus;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	
	
}
