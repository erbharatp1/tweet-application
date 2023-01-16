package com.csipl.tms.dto.shift;



public class ShiftDurationDTO {
		 
	 private String employeeId;
	 private String employeeCode;
	 private String employeeName;
	 private String ShiftName;
	 private String startTime;
	 private String endTime;
	 private String shiftDuration;
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
	public String getShiftName() {
		return ShiftName;
	}
	public void setShiftName(String shiftName) {
		ShiftName = shiftName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getShiftDuration() {
		return shiftDuration;
	}
	public void setShiftDuration(String shiftDuration) {
		this.shiftDuration = shiftDuration;
	}
	 
	
	
}