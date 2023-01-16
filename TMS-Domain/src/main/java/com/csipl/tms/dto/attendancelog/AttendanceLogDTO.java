package com.csipl.tms.dto.attendancelog;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.dto.daysattendancelog.DaysAttendanceLogDTO;

public class AttendanceLogDTO extends SearchDTO implements Comparable<AttendanceLogDTO> {

	private Long attendanceLogId;
	private Date attendanceDate;
	private Long createdBy;
	private Date createdDate;
	private Long inDeviceId;
	private LocalDate attendanceDates;
	private String inTime;
	private Long outDeviceId;
	private String outTime;
	private Long updatedBy;
	private Date updatedDate;
	private Long companyId;
	private Long employeeId;
	private Long departmentId;
	private String employeeCode;
	private String location;
	private String mode;

	private Long maxSno;
	private Long minSno;
	private String firstName;
	private String lastName;
	private String employeeName;
	private String status;
	private String delayedTime;
	private String processMonth;
	private BigDecimal leaves;
	private BigDecimal absense;
	private BigDecimal presense;
	private BigDecimal weekoff;
	private BigDecimal publicholidays;

	// private Long absense;
	// private Long payDays;
	// private Long presense;
	// private Long weekoff;
	// private Long publicholidays;
	private String totalTime;
	private Map<Date, String> attendanceMap;
	private BigDecimal absenseForCalender;
	private String latitude;
	
	private BigDecimal absenseEmployeeForCalender;

	private String longitude;

	private String address;
	private Long deviceId;

	private String direction;
	private String departmentName;
	private String designationName;
	private String reportingTo;
	private String jobLocation;

	private String biomatricId;

	private Long arDays;
	private Long halfDays;

	private String shiftName;
	private String shiftDuration;

	private String reportingTIme;
	private String earlyTime;
	private String exitTime;
	private String overTime;

	private String attDay;
	private Integer patternId;
	private String day;

	public BigDecimal halfD;
	public BigDecimal arD;

	private String outTimeLatitude;

	private String outTimeLangitude;

	private String outTimeAddress;

	private Date date;
	private String userName;
	private Long empId;
	private Date checkInTimeD;
	private String checkInTime;
	private String modeCode;

	private String lateBy;
	private String earlyBy;
	private String earlyBefore;
	private String locationTimeIn;
	private String locationTimeOut;
	private String startDates;

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getAttDay() {
		return attDay;
	}

	public void setAttDay(String attDay) {
		this.attDay = attDay;
	}

	public Integer getPatternId() {
		return patternId;
	}

	public void setPatternId(Integer patternId) {
		this.patternId = patternId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getExitTime() {
		return exitTime;
	}

	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	private List<DateWiseAttendanceLogDTO> events;

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	private String statusValue;

	public Long getAttendanceLogId() {
		return attendanceLogId;
	}

	public void setAttendanceLogId(Long attendanceLogId) {
		this.attendanceLogId = attendanceLogId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getInDeviceId() {
		return inDeviceId;
	}

	public void setInDeviceId(Long inDeviceId) {
		this.inDeviceId = inDeviceId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public Long getOutDeviceId() {
		return outDeviceId;
	}

	public void setOutDeviceId(Long outDeviceId) {
		this.outDeviceId = outDeviceId;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getMaxSno() {
		return maxSno;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setMaxSno(Long maxSno) {
		this.maxSno = maxSno;
	}

	public Long getMinSno() {
		return minSno;
	}

	public void setMinSno(Long minSno) {
		this.minSno = minSno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
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
		AttendanceLogDTO other = (AttendanceLogDTO) obj;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		return true;
	}

	@Override
	public int compareTo(AttendanceLogDTO attendanceLogDto) {
		// TODO Auto-generated method stub
		return this.employeeCode.compareTo(attendanceLogDto.employeeCode);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AttendanceLogDTO [attendanceLogId=" + attendanceLogId + ", attendanceDate=" + attendanceDate
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", inDeviceId=" + inDeviceId
				+ ", inTime=" + inTime + ", outDeviceId=" + outDeviceId + ", outTime=" + outTime + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", companyId=" + companyId + ", employeeId=" + employeeId
				+ ", employeeCode=" + employeeCode + ", location=" + location + ", mode=" + mode + ", maxSno=" + maxSno
				+ ", minSno=" + minSno + ", firstName=" + firstName + ", lastName=" + lastName + ", status=" + status
				+ "]";
	}

	public String getDelayedTime() {
		return delayedTime;
	}

	public void setDelayedTime(String delayedTime) {
		this.delayedTime = delayedTime;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	/*
	 * public Long getLeaves() { return leaves; }
	 * 
	 * public void setLeaves(Long leaves) { this.leaves = leaves; }
	 */
	public BigDecimal payDays;
	public BigDecimal totalPresentDays;

	public BigDecimal getPayDays() {
		return payDays;
	}

	public void setPayDays(BigDecimal payDays) {
		this.payDays = payDays;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Map<Date, String> getAttendanceMap() {
		return attendanceMap;
	}

	public void setAttendanceMap(Map<Date, String> attendanceMap) {
		this.attendanceMap = attendanceMap;
	}

	public List<DateWiseAttendanceLogDTO> getEvents() {
		return events;
	}

	public void setEvents(List<DateWiseAttendanceLogDTO> events) {
		this.events = events;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getBiomatricId() {
		return biomatricId;
	}

	public void setBiomatricId(String biomatricId) {
		this.biomatricId = biomatricId;
	}

	public Long getArDays() {
		return arDays;
	}

	public void setArDays(Long arDays) {
		this.arDays = arDays;
	}

	public Long getHalfDays() {
		return halfDays;
	}

	public void setHalfDays(Long halfDays) {
		this.halfDays = halfDays;
	}

	public BigDecimal getTotalPresentDays() {
		return totalPresentDays;
	}

	public void setTotalPresentDays(BigDecimal totalPresentDays) {
		this.totalPresentDays = totalPresentDays;
	}

	public BigDecimal getAbsenseForCalender() {
		return absenseForCalender;
	}

	public void setAbsenseForCalender(BigDecimal absenseForCalender) {
		this.absenseForCalender = absenseForCalender;
	}

	public BigDecimal getLeaves() {
		return leaves;
	}

	public void setLeaves(BigDecimal leaves) {
		this.leaves = leaves;
	}

	public BigDecimal getAbsense() {
		return absense;
	}

	public void setAbsense(BigDecimal absense) {
		this.absense = absense;
	}

	public BigDecimal getPresense() {
		return presense;
	}

	public void setPresense(BigDecimal presense) {
		this.presense = presense;
	}

	public BigDecimal getWeekoff() {
		return weekoff;
	}

	public void setWeekoff(BigDecimal weekoff) {
		this.weekoff = weekoff;
	}

	public BigDecimal getPublicholidays() {
		return publicholidays;
	}

	public void setPublicholidays(BigDecimal publicholidays) {
		this.publicholidays = publicholidays;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getShiftDuration() {
		return shiftDuration;
	}

	public void setShiftDuration(String shiftDuration) {
		this.shiftDuration = shiftDuration;
	}

	public String getReportingTIme() {
		return reportingTIme;
	}

	public void setReportingTIme(String reportingTIme) {
		this.reportingTIme = reportingTIme;
	}

	public String getEarlyTime() {
		return earlyTime;
	}

	public void setEarlyTime(String earlyTime) {
		this.earlyTime = earlyTime;
	}

	public BigDecimal getHalfD() {
		return halfD;
	}

	public void setHalfD(BigDecimal halfD) {
		this.halfD = halfD;
	}

	public BigDecimal getArD() {
		return arD;
	}

	public void setArD(BigDecimal arD) {
		this.arD = arD;
	}

	public String getOutTimeLatitude() {
		return outTimeLatitude;
	}

	public void setOutTimeLatitude(String outTimeLatitude) {
		this.outTimeLatitude = outTimeLatitude;
	}

	public String getOutTimeLangitude() {
		return outTimeLangitude;
	}

	public void setOutTimeLangitude(String outTimeLangitude) {
		this.outTimeLangitude = outTimeLangitude;
	}

	public String getOutTimeAddress() {
		return outTimeAddress;
	}

	public void setOutTimeAddress(String outTimeAddress) {
		this.outTimeAddress = outTimeAddress;
	}

	public String getLateBy() {
		return lateBy;
	}

	public void setLateBy(String lateBy) {
		this.lateBy = lateBy;
	}

	public String getEarlyBy() {
		return earlyBy;
	}

	public void setEarlyBy(String earlyBy) {
		this.earlyBy = earlyBy;
	}

	public String getEarlyBefore() {
		return earlyBefore;
	}

	public void setEarlyBefore(String earlyBefore) {
		this.earlyBefore = earlyBefore;
	}

	public String getLocationTimeIn() {
		return locationTimeIn;
	}

	public void setLocationTimeIn(String locationTimeIn) {
		this.locationTimeIn = locationTimeIn;
	}

	public String getLocationTimeOut() {
		return locationTimeOut;
	}

	public void setLocationTimeOut(String locationTimeOut) {
		this.locationTimeOut = locationTimeOut;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDate getAttendanceDates() {
		return attendanceDates;
	}

	public void setAttendanceDates(LocalDate attendanceDates) {
		this.attendanceDates = attendanceDates;
	}

	public String getStartDates() {
		return startDates;
	}

	public void setStartDates(String startDates) {
		this.startDates = startDates;
	}

	public Date getCheckInTimeD() {
		return checkInTimeD;
	}

	public void setCheckInTimeD(Date checkInTimeD) {
		this.checkInTimeD = checkInTimeD;
	}

	public BigDecimal getAbsenseEmployeeForCalender() {
		return absenseEmployeeForCalender;
	}

	public void setAbsenseEmployeeForCalender(BigDecimal absenseEmployeeForCalender) {
		this.absenseEmployeeForCalender = absenseEmployeeForCalender;
	}
	
	
	

}
