package com.csipl.tms.dto.attendancetype;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.attendancetypetransaction.AttendanceTypeTransactionDTO;


/**
 * The persistent class for the AttendanceType database table.
 * 
 */

public class AttendanceTypeDTO implements Serializable {

	private Long attendanceTypeId;

	private Long createdBy;

	private Date createdDate;

	private String typeName;

	private String typeCode;
	
	private List<AttendanceTypeTransactionDTO> attendanceTypeTransactionsDto;

	public AttendanceTypeDTO() {
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

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public List<AttendanceTypeTransactionDTO> getAttendanceTypeTransactions() {
		return this.attendanceTypeTransactionsDto;
	}

	public void setAttendanceTypeTransactions(List<AttendanceTypeTransactionDTO> attendanceTypeTransactions) {
		this.attendanceTypeTransactionsDto = attendanceTypeTransactions;
	}


}