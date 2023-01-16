package com.csipl.tms.dto.deviceinfo;

import java.sql.Timestamp;
import java.util.Date;

public class DeviceLogsInfoDTO {
	private int deviceLogId;

	private int deviceId;

	private String direction;

	private Date logDate;
	private String logMinDate;
	private String userId;
	private String empName;
	private String empCode;
	private String departmentName;
	private String status;
	private String mode;
	private String reportedLateBy;
	private String punchRecords;
	private String startTime;

	private String repotedLateBy;
	private String leftEarlyBy;

	private String leaveStatus;
	private String lateBy;
	private String reportingTo;
	private String totalHours;

	private String locationIn;
	private String locationOut;
	private String timeIn;

	private String timeOut;
	private String shift;
	private String shiftDuration;

	private String earlyBefore;
	private String jobLocation;
	private String attedanceDate;
	private String name;
	private String designationName;
	private String employeeCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getRepotedLateBy() {
		return repotedLateBy;
	}

	public void setRepotedLateBy(String repotedLateBy) {
		this.repotedLateBy = repotedLateBy;
	}

	public String getLeftEarlyBy() {
		return leftEarlyBy;
	}

	public void setLeftEarlyBy(String leftEarlyBy) {
		this.leftEarlyBy = leftEarlyBy;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getLateBy() {
		return lateBy;
	}

	public void setLateBy(String lateBy) {
		this.lateBy = lateBy;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getLocationIn() {
		return locationIn;
	}

	public void setLocationIn(String locationIn) {
		this.locationIn = locationIn;
	}

	public String getLocationOut() {
		return locationOut;
	}

	public void setLocationOut(String locationOut) {
		this.locationOut = locationOut;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getShiftDuration() {
		return shiftDuration;
	}

	public void setShiftDuration(String shiftDuration) {
		this.shiftDuration = shiftDuration;
	}

	public String getEarlyBefore() {
		return earlyBefore;
	}

	public void setEarlyBefore(String earlyBefore) {
		this.earlyBefore = earlyBefore;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getAttedanceDate() {
		return attedanceDate;
	}

	public void setAttedanceDate(String attedanceDate) {
		this.attedanceDate = attedanceDate;
	}

	public int getDeviceLogId() {
		return deviceLogId;
	}

	public void setDeviceLogId(int deviceLogId) {
		this.deviceLogId = deviceLogId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getReportedLateBy() {
		return reportedLateBy;
	}

	public void setReportedLateBy(String reportedLateBy) {
		this.reportedLateBy = reportedLateBy;
	}

	public String getPunchRecords() {
		return punchRecords;
	}

	public void setPunchRecords(String punchRecords) {
		this.punchRecords = punchRecords;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLogMinDate() {
		return logMinDate;
	}

	public void setLogMinDate(String logMinDate) {
		this.logMinDate = logMinDate;
	}

}
