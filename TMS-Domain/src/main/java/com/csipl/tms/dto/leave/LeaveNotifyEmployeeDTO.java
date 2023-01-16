package com.csipl.tms.dto.leave;

import java.util.Date;

public class LeaveNotifyEmployeeDTO {
	
	private Date createdDate;

	private Long employeeId;
	//pk
	private Long notifyEmployeeId;

	
	private LeaveBalanceSummryDTO leaveBalanceSummryDTO;

	public LeaveNotifyEmployeeDTO() {
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

	public LeaveBalanceSummryDTO getLeaveBalanceSummryDTO() {
		return leaveBalanceSummryDTO;
	}

	public void setLeaveBalanceSummryDTO(LeaveBalanceSummryDTO leaveBalanceSummryDTO) {
		this.leaveBalanceSummryDTO = leaveBalanceSummryDTO;
	}

	
}
