package com.csipl.tms.model.leave;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the TMSLeaveEntries database table.
 * 
 */
@Entity
@Table(name="TMSLeaveEntries")
@NamedQuery(name="TMSLeaveEntry.findAll", query="SELECT t FROM TMSLeaveEntry t")
public class TMSLeaveEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveId;

	@Temporal(TemporalType.DATE)
	private Date actionableDate;

	private Long appliedByEmployeeId;

	private Long approvalId;

	private String approvalRemark;

	private String cancleRemark;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private BigDecimal days;

	private Long employeeId;

	private String employeeRemark;

	@Temporal(TemporalType.DATE)
	private Date fromDate;

	private String halfDayFor;

	private String halfFullDay;

	private byte isApproved;

	private byte isRead;

	private Long notificationId;

	private String status;
	
	private String notifyEmployee;
	

	@Temporal(TemporalType.DATE)
	private Date toDate;

	private Long userId;

	private Long userIdUpdate;
	
	@Transient
	private String  employeeCode;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	//bi-directional many-to-one association to TMSLeaveEmailNotification
	@OneToMany(mappedBy="tmsleaveEntry")
	private List<TMSLeaveEmailNotification> tmsleaveEmailNotifications;

	//bi-directional many-to-one association to TMSLeaveType
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name="leaveTypeId")
	private TMSLeaveType tmsleaveType;
	

	//bi-directional many-to-one association to TMSLeaveEntriesDatewise
	@OneToMany(mappedBy="tmsleaveEntry")
	private List<TMSLeaveEntriesDatewise> tmsleaveEntriesDatewises;

	//bi-directional many-to-one association to AttendanceLocationMapping
	@OneToMany(mappedBy="tmsleaveEntry" , cascade = CascadeType.ALL)
	private List<LeaveNotifyEmployee> leaveNotifyEmployees;

	public TMSLeaveEntry() {
	}

	public Long getLeaveId() {
		return this.leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public Date getActionableDate() {
		return this.actionableDate;
	}

	public void setActionableDate(Date actionableDate) {
		this.actionableDate = actionableDate;
	}

	public Long getAppliedByEmployeeId() {
		return this.appliedByEmployeeId;
	}

	public void setAppliedByEmployeeId(Long appliedByEmployeeId) {
		this.appliedByEmployeeId = appliedByEmployeeId;
	}

	public Long getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalRemark() {
		return this.approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public String getCancleRemark() {
		return this.cancleRemark;
	}

	public void setCancleRemark(String cancleRemark) {
		this.cancleRemark = cancleRemark;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public BigDecimal getDays() {
		return this.days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeRemark() {
		return this.employeeRemark;
	}

	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getHalfDayFor() {
		return this.halfDayFor;
	}

	public void setHalfDayFor(String halfDayFor) {
		this.halfDayFor = halfDayFor;
	}

	public String getHalfFullDay() {
		return this.halfFullDay;
	}

	public void setHalfFullDay(String halfFullDay) {
		this.halfFullDay = halfFullDay;
	}

	public byte getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(byte isApproved) {
		this.isApproved = isApproved;
	}

	public byte getIsRead() {
		return this.isRead;
	}

	public void setIsRead(byte isRead) {
		this.isRead = isRead;
	}

	public Long getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public List<TMSLeaveEmailNotification> getTmsleaveEmailNotifications() {
		return this.tmsleaveEmailNotifications;
	}

	public void setTmsleaveEmailNotifications(List<TMSLeaveEmailNotification> tmsleaveEmailNotifications) {
		this.tmsleaveEmailNotifications = tmsleaveEmailNotifications;
	}

	public TMSLeaveEmailNotification addTmsleaveEmailNotification(TMSLeaveEmailNotification tmsleaveEmailNotification) {
		getTmsleaveEmailNotifications().add(tmsleaveEmailNotification);
		tmsleaveEmailNotification.setTmsleaveEntry(this);

		return tmsleaveEmailNotification;
	}

	public TMSLeaveEmailNotification removeTmsleaveEmailNotification(TMSLeaveEmailNotification tmsleaveEmailNotification) {
		getTmsleaveEmailNotifications().remove(tmsleaveEmailNotification);
		tmsleaveEmailNotification.setTmsleaveEntry(null);

		return tmsleaveEmailNotification;
	}

	public TMSLeaveType getTmsleaveType() {
		return this.tmsleaveType;
	}

	public void setTmsleaveType(TMSLeaveType tmsleaveType) {
		this.tmsleaveType = tmsleaveType;
	}

	public String getNotifyEmployee() {
		return notifyEmployee;
	}

	public void setNotifyEmployee(String notifyEmployee) {
		this.notifyEmployee = notifyEmployee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leaveId == null) ? 0 : leaveId.hashCode());
		result = prime * result + ((tmsleaveType == null) ? 0 : tmsleaveType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TMSLeaveEntry other = (TMSLeaveEntry) obj;
		if (leaveId == null) {
			if (other.leaveId != null)
				return false;
		} else if (!leaveId.equals(other.leaveId))
			return false;
		if (tmsleaveType == null) {
			if (other.tmsleaveType != null)
				return false;
		} else if (!tmsleaveType.equals(other.tmsleaveType))
			return false;
		return true;
	}

	public List<LeaveNotifyEmployee> getLeaveNotifyEmployees() {
		return leaveNotifyEmployees;
	}

	public void setLeaveNotifyEmployees(List<LeaveNotifyEmployee> leaveNotifyEmployees) {
		this.leaveNotifyEmployees = leaveNotifyEmployees;
	}

	
	
	

}