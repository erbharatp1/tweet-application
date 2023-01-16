package com.csipl.tms.model.leave;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TMSLeaveEntriesDatewise database table.
 * 
 */
@Entity
@NamedQuery(name="TMSLeaveEntriesDatewise.findAll", query="SELECT t FROM TMSLeaveEntriesDatewise t")
public class TMSLeaveEntriesDatewise implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date leaveFromToSandwitchDate;


	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date leaveDate;

	private String leaveNature;

	private String leaveStatus;
	
	private Long employeeId;
	
	private BigDecimal day;
	
	private String halfFullDay;

	public String getHalfFullDay() {
		return halfFullDay;
	}

	public void setHalfFullDay(String halfFullDay) {
		this.halfFullDay = halfFullDay;
	}

	//bi-directional many-to-one association to TMSLeaveEntry
	@ManyToOne
	@JoinColumn(name="leaveId")
	private TMSLeaveEntry tmsleaveEntry;

/*	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;
*/
	public TMSLeaveEntriesDatewise() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	public Date getLeaveFromToSandwitchDate() {
		return leaveFromToSandwitchDate;
	}

	public void setLeaveFromToSandwitchDate(Date leaveFromToSandwitchDate) {
		this.leaveFromToSandwitchDate = leaveFromToSandwitchDate;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLeaveNature() {
		return this.leaveNature;
	}

	public void setLeaveNature(String leaveNature) {
		this.leaveNature = leaveNature;
	}

	public String getLeaveStatus() {
		return this.leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public TMSLeaveEntry getTmsleaveEntry() {
		return this.tmsleaveEntry;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setTmsleaveEntry(TMSLeaveEntry tmsleaveEntry) {
		this.tmsleaveEntry = tmsleaveEntry;
	}

	public BigDecimal getDay() {
		return day;
	}

	public void setDay(BigDecimal day) {
		this.day = day;
	}
	
	

	/*public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}*/

}