package com.csipl.tms.dto.leave;

import java.util.List;

import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;

public class TeamLeaveOnCalenderDTO {
	
	String employeeId ;
	String departmentId ;
	String employeeCode;
	String employeeName;
	String status;
	String fromDate;
	String toDate ;
	String leaves;
	String departmentName;
	String remark;
	String leaveStatus;
	String leaveId;
	String statusCondition;
	private List<DateWiseAttendanceLogDTO> events;
	
	public List<DateWiseAttendanceLogDTO> getEvents() {
		return events;
	}
	public void setEvents(List<DateWiseAttendanceLogDTO> events) {
		this.events = events;
	}
	private String arID; 	private String id; 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	int size;
	
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	
	
	
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLeaves() {
		return leaves;
	}
	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the arID
	 */
	public String getArID() {
		return arID;
	}
	/**
	 * @param arID the arID to set
	 */
	public void setArID(String arID) {
		this.arID = arID;
	}
	public String getStatusCondition() {
		return statusCondition;
	}
	public void setStatusCondition(String statusCondition) {
		this.statusCondition = statusCondition;
	}
	@Override
	public String toString() {
		return "TeamLeaveOnCalenderDTO [employeeId=" + employeeId + ", departmentId=" + departmentId + ", employeeCode="
				+ employeeCode + ", employeeName=" + employeeName + ", status=" + status + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", leaves=" + leaves + ", departmentName=" + departmentName + ", remark="
				+ remark + ", leaveStatus=" + leaveStatus + ", leaveId=" + leaveId + ", statusCondition="
				+ statusCondition + ", events=" + events + ", arID=" + arID + ", id=" + id + ", size=" + size + "]";
	}
	
	
	

}
