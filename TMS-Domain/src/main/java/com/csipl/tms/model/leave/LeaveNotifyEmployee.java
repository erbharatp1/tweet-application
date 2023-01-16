package com.csipl.tms.model.leave;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


@Entity
@NamedQuery(name="LeaveNotifyEmployee.findAll", query="SELECT l FROM LeaveNotifyEmployee l")
public class LeaveNotifyEmployee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notifyEmployeeId;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private Long employeeId;
	

	//bi-directional many-to-one association to TMSLeaveEntry
	@ManyToOne
	@JoinColumn(name="leaveEntriesId")
	private TMSLeaveEntry tmsleaveEntry;

	public LeaveNotifyEmployee() {
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getNotifyEmployeeId() {
		return this.notifyEmployeeId;
	}

	public void setNotifyEmployeeId(Long notifyEmployeeId) {
		this.notifyEmployeeId = notifyEmployeeId;
	}
	@JsonIgnore
	public TMSLeaveEntry getTmsleaveEntry() {
		return this.tmsleaveEntry;
	}

	public void setTmsleaveEntry(TMSLeaveEntry tmsleaveEntry) {
		this.tmsleaveEntry = tmsleaveEntry;
	}

}