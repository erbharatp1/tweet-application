package com.csipl.tms.model.attendancetype;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTransaction;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AttendanceType database table.
 * 
 */
@Entity
@NamedQuery(name="AttendanceType.findAll", query="SELECT a FROM AttendanceType a")
public class AttendanceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attendanceTypeId;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private String typeCode;

	private String typeName;

	//bi-directional many-to-one association to AttendanceTypeTransaction
	@OneToMany(mappedBy="attendanceType")
	private List<AttendanceTypeTransaction> attendanceTypeTransactions;

	public AttendanceType() {
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

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<AttendanceTypeTransaction> getAttendanceTypeTransactions() {
		return this.attendanceTypeTransactions;
	}

	public void setAttendanceTypeTransactions(List<AttendanceTypeTransaction> attendanceTypeTransactions) {
		this.attendanceTypeTransactions = attendanceTypeTransactions;
	}

	public AttendanceTypeTransaction addAttendanceTypeTransaction(AttendanceTypeTransaction attendanceTypeTransaction) {
		getAttendanceTypeTransactions().add(attendanceTypeTransaction);
		attendanceTypeTransaction.setAttendanceType(this);

		return attendanceTypeTransaction;
	}

	public AttendanceTypeTransaction removeAttendanceTypeTransaction(AttendanceTypeTransaction attendanceTypeTransaction) {
		getAttendanceTypeTransactions().remove(attendanceTypeTransaction);
		attendanceTypeTransaction.setAttendanceType(null);

		return attendanceTypeTransaction;
	}

}