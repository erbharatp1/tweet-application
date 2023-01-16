package com.csipl.tms.dto.monthattendance;

import java.math.BigDecimal;

public class EmployeeLeaveGraphDTO {

	private BigDecimal employeeOnLeavePercentage;
	private String month;
	
	private String leaveType;
	private String leaveTypeCount;
	
	private BigDecimal employeeOnAbsent;
	
	private String freLeaveCount;
	private String empName;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
 
	public BigDecimal getEmployeeOnLeavePercentage() {
		return employeeOnLeavePercentage;
	}
	public void setEmployeeOnLeavePercentage(BigDecimal employeeOnLeavePercentage) {
		this.employeeOnLeavePercentage = employeeOnLeavePercentage;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveTypeCount() {
		return leaveTypeCount;
	}
	public void setLeaveTypeCount(String leaveTypeCount) {
		this.leaveTypeCount = leaveTypeCount;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getFreLeaveCount() {
		return freLeaveCount;
	}
	public void setFreLeaveCount(String freLeaveCount) {
		this.freLeaveCount = freLeaveCount;
	}
	public BigDecimal getEmployeeOnAbsent() {
		return employeeOnAbsent;
	}
	public void setEmployeeOnAbsent(BigDecimal employeeOnAbsent) {
		this.employeeOnAbsent = employeeOnAbsent;
	}
 
	
}
