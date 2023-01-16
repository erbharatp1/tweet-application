package com.csipl.tms.dto.attendancescheme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.attendancetypetransaction.AttendanceTypeTransactionDTO;
import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.fasterxml.jackson.annotation.JsonProperty;




/**
 * The persistent class for the AttendanceScheme database table.
 * 
 */

public class AttendanceSchemeDTO implements Serializable {
	

	private Long attendanceSchemeId;
	private String activeStatus;
	private Long createdBy;
	
	private Date createdDate;
	private String schemeName;
	private Long arDays;

	private List<AttendanceLocationMappingDTO> attendanceLocationMappingsDtoList;

	private List<AttendanceTypeTransactionDTO> attendanceTypeTransactionsDtoList;
	
	@JsonProperty( "attendanceSchemeDto" )
	private List<AttendanceSchemeDTO> attendanceSchemeDto= new ArrayList<AttendanceSchemeDTO>();
	
	//scheme setting
	private Long employeeId;
	private Long userId;
	private String aschemeStatus;
	private Long attendanceTypeId;
	private String typeCode;
	private String aTypeTransactionstatus;
	

	public AttendanceSchemeDTO() {
	}

	public Long getAttendanceSchemeId() {
		return this.attendanceSchemeId;
	}

	public void setAttendanceSchemeId(Long attendanceSchemeId) {
		this.attendanceSchemeId = attendanceSchemeId;
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

	public String getSchemeName() {
		return this.schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public List<AttendanceLocationMappingDTO> getAttendanceLocationMappingsDtoList() {
		return attendanceLocationMappingsDtoList;
	}

	public void setAttendanceLocationMappingsDtoList(List<AttendanceLocationMappingDTO> attendanceLocationMappingsDtoList) {
		this.attendanceLocationMappingsDtoList = attendanceLocationMappingsDtoList;
	}

	public List<AttendanceTypeTransactionDTO> getAttendanceTypeTransactionsDtoList() {
		return attendanceTypeTransactionsDtoList;
	}

	public void setAttendanceTypeTransactionsDtoList(List<AttendanceTypeTransactionDTO> attendanceTypeTransactionsDtoList) {
		this.attendanceTypeTransactionsDtoList = attendanceTypeTransactionsDtoList;
	}

	public Long getArDays() {
		return arDays;
	}

	public void setArDays(Long arDays) {
		this.arDays = arDays;
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
	
	public List<AttendanceSchemeDTO> getAttendanceSchemeDto() {
		return attendanceSchemeDto;
	}

	public void setAttendanceSchemeDto(List<AttendanceSchemeDTO> attendanceSchemeDto) {
		this.attendanceSchemeDto = attendanceSchemeDto;
	}



}