package com.csipl.tms.model.leave;


import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.employee.Employee;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EmployeeLeaveHistory database table.
 * 
 */
@Entity
@NamedQuery(name="EmployeeLeaveHistory.findAll", query="SELECT e FROM EmployeeLeaveHistory e")
public class EmployeeLeaveHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long leaveHistoryId;

	private BigDecimal balancedLeave;

	private BigDecimal carryforwardLeave;

	private BigDecimal consumedLeave;

	private BigDecimal encashLeave;

	private BigDecimal expiredLeave;

	//bi-directional many-to-one association to TMSLeavePeriod
	@ManyToOne
	@JoinColumn(name="leavePeriodId")
	private TMSLeavePeriod tmsleavePeriod;

	@ManyToOne
	@JoinColumn(name="leaveTypeMasterId")
	private TMSLeaveTypeMaster tmsleaveTypeMaster;

	private BigDecimal totalLeave;
	
	private Long userId;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	public EmployeeLeaveHistory() {
	}

	public Long getLeaveHistoryId() {
		return this.leaveHistoryId;
	}

	public void setLeaveHistoryId(Long leaveHistoryId) {
		this.leaveHistoryId = leaveHistoryId;
	}

	public BigDecimal getBalancedLeave() {
		return this.balancedLeave;
	}

	public void setBalancedLeave(BigDecimal balancedLeave) {
		this.balancedLeave = balancedLeave;
	}

	public BigDecimal getCarryforwardLeave() {
		return this.carryforwardLeave;
	}

	public void setCarryforwardLeave(BigDecimal carryforwardLeave) {
		this.carryforwardLeave = carryforwardLeave;
	}

	public BigDecimal getConsumedLeave() {
		return this.consumedLeave;
	}

	public void setConsumedLeave(BigDecimal consumedLeave) {
		this.consumedLeave = consumedLeave;
	}

	public BigDecimal getEncashLeave() {
		return this.encashLeave;
	}

	public void setEncashLeave(BigDecimal encashLeave) {
		this.encashLeave = encashLeave;
	}

	public BigDecimal getExpiredLeave() {
		return this.expiredLeave;
	}

	public void setExpiredLeave(BigDecimal expiredLeave) {
		this.expiredLeave = expiredLeave;
	}

	public TMSLeavePeriod getTmsleavePeriod() {
		return this.tmsleavePeriod;
	}

	public void setTmsleavePeriod(TMSLeavePeriod tmsleavePeriod) {
		this.tmsleavePeriod = tmsleavePeriod;
	}


	public TMSLeaveTypeMaster getTmsleaveTypeMaster() {
		return this.tmsleaveTypeMaster;
	}

	public void setTmsleaveTypeMaster(TMSLeaveTypeMaster tmsleaveTypeMaster) {
		this.tmsleaveTypeMaster = tmsleaveTypeMaster;
	}
	public BigDecimal getTotalLeave() {
		return this.totalLeave;
	}

	public void setTotalLeave(BigDecimal totalLeave) {
		this.totalLeave = totalLeave;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}