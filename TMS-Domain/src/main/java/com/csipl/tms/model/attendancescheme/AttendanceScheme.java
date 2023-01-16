package com.csipl.tms.model.attendancescheme;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTransaction;
import com.csipl.tms.model.latlonglocation.AttendanceLocationMapping;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AttendanceScheme database table.
 * 
 */
@Entity
@NamedQuery(name="AttendanceScheme.findAll", query="SELECT a FROM AttendanceScheme a")
public class AttendanceScheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attendanceSchemeId;

	private String activeStatus;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private String schemeName;
	
	private Long arDays;
	
	//bi-directional many-to-one association to AttendanceLocationMapping
	@OneToMany(mappedBy="attendanceScheme" , cascade = CascadeType.ALL)
	private List<AttendanceLocationMapping> attendanceLocationMappings;

	//bi-directional many-to-one association to AttendanceTypeTransaction
	@OneToMany(mappedBy="attendanceScheme" , cascade = CascadeType.ALL)
	private List<AttendanceTypeTransaction> attendanceTypeTransactions;

	public AttendanceScheme() {
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

	public List<AttendanceLocationMapping> getAttendanceLocationMappings() {
		return this.attendanceLocationMappings;
	}

	public void setAttendanceLocationMappings(List<AttendanceLocationMapping> attendanceLocationMappings) {
		this.attendanceLocationMappings = attendanceLocationMappings;
	}

	public AttendanceLocationMapping addAttendanceLocationMapping(AttendanceLocationMapping attendanceLocationMapping) {
		getAttendanceLocationMappings().add(attendanceLocationMapping);
		attendanceLocationMapping.setAttendanceScheme(this);

		return attendanceLocationMapping;
	}

	public AttendanceLocationMapping removeAttendanceLocationMapping(AttendanceLocationMapping attendanceLocationMapping) {
		getAttendanceLocationMappings().remove(attendanceLocationMapping);
		attendanceLocationMapping.setAttendanceScheme(null);

		return attendanceLocationMapping;
	}

	public List<AttendanceTypeTransaction> getAttendanceTypeTransactions() {
		return this.attendanceTypeTransactions;
	}

	public void setAttendanceTypeTransactions(List<AttendanceTypeTransaction> attendanceTypeTransactions) {
		this.attendanceTypeTransactions = attendanceTypeTransactions;
	}

	public AttendanceTypeTransaction addAttendanceTypeTransaction(AttendanceTypeTransaction attendanceTypeTransaction) {
		getAttendanceTypeTransactions().add(attendanceTypeTransaction);
		attendanceTypeTransaction.setAttendanceScheme(this);

		return attendanceTypeTransaction;
	}

	public AttendanceTypeTransaction removeAttendanceTypeTransaction(AttendanceTypeTransaction attendanceTypeTransaction) {
		getAttendanceTypeTransactions().remove(attendanceTypeTransaction);
		attendanceTypeTransaction.setAttendanceScheme(null);

		return attendanceTypeTransaction;
	}

	public Long getArDays() {
		return arDays;
	}

	public void setArDays(Long arDays) {
		this.arDays = arDays;
	}

}