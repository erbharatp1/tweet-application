package com.csipl.tms.dto.attendancetypetransaction;

import java.util.Date;

import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.attendancetype.AttendanceTypeDTO;



public class AttendanceTypeTransactionDTO {
	
	private Long attendanceTypeTransactionId;

	private String activeStatus;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;


	private AttendanceSchemeDTO attendanceSchemeDTO;

	private AttendanceTypeDTO AttendanceTypeDTO;

	private Long attendanceTypeId;
	private String typeName;
	private String typeCodeString;
	
	public AttendanceTypeTransactionDTO() {
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

	public Long getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Long attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCodeString() {
		return typeCodeString;
	}

	public void setTypeCodeString(String typeCodeString) {
		this.typeCodeString = typeCodeString;
	}

	public AttendanceSchemeDTO getAttendanceSchemeDTO() {
		return attendanceSchemeDTO;
	}

	public void setAttendanceSchemeDTO(AttendanceSchemeDTO attendanceSchemeDTO) {
		this.attendanceSchemeDTO = attendanceSchemeDTO;
	}

	public AttendanceTypeDTO getAttendanceTypeDTO() {
		return AttendanceTypeDTO;
	}

	public void setAttendanceTypeDTO(AttendanceTypeDTO attendanceTypeDTO) {
		AttendanceTypeDTO = attendanceTypeDTO;
	}


}
