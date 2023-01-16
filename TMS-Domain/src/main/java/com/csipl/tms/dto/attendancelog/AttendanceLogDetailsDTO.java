package com.csipl.tms.dto.attendancelog;

public class AttendanceLogDetailsDTO {
	private String employeeCode;

	private String name;

	private String designationName;

	private String departmentName;

	private String firstPunch;

	private String mode;

	private String location;

	private String punchRecord;

	private Long workHours;

	private String present;

	private String absent;

	private String delayedTime;

	private String overTime;

	private String earlyGoing;

	private String leave;

	private String publicHoliday;

	private String weekOff;

	private String charFirstName;

	private String charLastName;

	private String status;

	private String presentCount;

	private String leaveCount;

	private String absentCount;

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
	private Integer index;

	private String earlyBefore;
	private String jobLocation;
	private String attedanceDate;
	public String getAttedanceDate() {
		return attedanceDate;
	}

	public void setAttedanceDate(String attedanceDate) {
		this.attedanceDate = attedanceDate;
	}

	public String getLateBy() {
		return lateBy;
	}

	public void setLateBy(String lateBy) {
		this.lateBy = lateBy;
	}

	public String getLeftEarlyBy() {
		return leftEarlyBy;
	}

	public void setLeftEarlyBy(String leftEarlyBy) {
		this.leftEarlyBy = leftEarlyBy;
	}

	public String getRepotedLateBy() {
		return repotedLateBy;
	}

	public void setRepotedLateBy(String repotedLateBy) {
		this.repotedLateBy = repotedLateBy;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getFirstPunch() {
		return firstPunch;
	}

	public void setFirstPunch(String firstPunch) {
		this.firstPunch = firstPunch;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPunchRecord() {
		return punchRecord;
	}

	public void setPunchRecord(String punchRecord) {
		this.punchRecord = punchRecord;
	}

	public Long getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Long workHours) {
		this.workHours = workHours;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getAbsent() {
		return absent;
	}

	public void setAbsent(String absent) {
		this.absent = absent;
	}

	public String getDelayedTime() {
		return delayedTime;
	}

	public void setDelayedTime(String delayedTime) {
		this.delayedTime = delayedTime;
	}

	public String getOverTime() {
		return overTime;
	}

	public String getEarlyGoing() {
		return earlyGoing;
	}

	public void setEarlyGoing(String earlyGoing) {
		this.earlyGoing = earlyGoing;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getPublicHoliday() {
		return publicHoliday;
	}

	public void setPublicHoliday(String publicHoliday) {
		this.publicHoliday = publicHoliday;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getWeekOff() {
		return weekOff;
	}

	public void setWeekOff(String weekOff) {
		this.weekOff = weekOff;
	}

	@Override
	public String toString() {
		return "AttendanceLogDetailsDTO [employeeCode=" + employeeCode + ", name=" + name + ", designationName="
				+ designationName + ", departmentName=" + departmentName + ", firstPunch=" + firstPunch + ", mode="
				+ mode + ", location=" + location + ", punchRecord=" + punchRecord + ", workHours=" + workHours
				+ ", present=" + present + ", absent=" + absent + ", delayedTime=" + delayedTime + ", overTime="
				+ overTime + ", earlyGoing=" + earlyGoing + ", leave=" + leave + ", publicHoliday=" + publicHoliday
				+ ", weekOff=" + weekOff + "]";
	}

	public String getCharFirstName() {
		return charFirstName;
	}

	public void setCharFirstName(String charFirstName) {
		this.charFirstName = charFirstName;
	}

	public String getCharLastName() {
		return charLastName;
	}

	public void setCharLastName(String charLastName) {
		this.charLastName = charLastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPresentCount() {
		return presentCount;
	}

	public void setPresentCount(String presentCount) {
		this.presentCount = presentCount;
	}

	public String getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}

	public String getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(String absentCount) {
		this.absentCount = absentCount;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	 

}
