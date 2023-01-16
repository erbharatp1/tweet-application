package com.csipl.tms.dto.attendancelog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;

public class AttendanceValidationResult {
	
	private  Map<Date, DateWiseAttendanceLogDTO> attendanceMap = new HashMap<Date, DateWiseAttendanceLogDTO>();
	private  Map<Date, String> leaveEnteriesMap = new HashMap<Date, String>();
	private Map<Date, String> attendanceLogMap = new HashMap<Date, String>();
	private Map<Date, AttendanceLog> attendanceLogObjMap = new HashMap<Date, AttendanceLog>();
	private Map<Date, String> holidaydateMap = new HashMap<Date, String>();
	private  Map<Date, String> weeklyoffdateMap = new HashMap<Date, String>();
	private Map<Date, String> attendanceRegMap = new HashMap<Date, String>();
	
	private Long presentDays=0L;
	//private Long leaveDays = 0L;
	private Long weeklyoffDays =0L;
	private Long holidayDays =0L;
	private Long absense =0L;
	private Long halfDays=0L;
	private Long arDays=0L;
	private Long holidayAsAbsentDateCount=0L;
	private Long weekoffAbsentDateCount=0L;
//	private Long sandwitchWeekoffCount=0L;
	//private Long sandwitchHolidayCount=0L;
	
	private BigDecimal leaveDays = new BigDecimal(0);
	private BigDecimal totleLeaveDays = new BigDecimal(0);
	private BigDecimal sandwitchWeekoffCount = new BigDecimal(0);
	private BigDecimal sandwitchHolidayCount = new BigDecimal(0);
	//private BigDecimal leaveDays = new BigDecimal(0);
	
	  boolean weekOffBetweenTwoAbsent = false;
 boolean holidayBetweenTwoAbsent = false;
	
	private String mode;
	
	public Map<Date, DateWiseAttendanceLogDTO> getAttendanceMap() {
		return attendanceMap;
	}
	public void setAttendanceMap(Map<Date, DateWiseAttendanceLogDTO> attendanceMap) {
		this.attendanceMap = attendanceMap;
	}
	public Map<Date, String> getLeaveEnteriesMap() {
		return leaveEnteriesMap;
	}
	public void setLeaveEnteriesMap(Map<Date, String> leaveEnteriesMap) {
		this.leaveEnteriesMap = leaveEnteriesMap;
	}
	public Map<Date, String> getAttendanceLogMap() {
		return attendanceLogMap;
	}
	public void setAttendanceLogMap(Map<Date, String> attendanceLogMap) {
		this.attendanceLogMap = attendanceLogMap;
	}
	public Map<Date, AttendanceLog> getAttendanceLogObjMap() {
		return attendanceLogObjMap;
	}
	public void setAttendanceLogObjMap(Map<Date, AttendanceLog> attendanceLogObjMap) {
		this.attendanceLogObjMap = attendanceLogObjMap;
	}
	public Map<Date, String> getHolidaydateMap() {
		return holidaydateMap;
	}
	public void setHolidaydateMap(Map<Date, String> holidaydateMap) {
		this.holidaydateMap = holidaydateMap;
	}
	public Map<Date, String> getWeeklyoffdateMap() {
		return weeklyoffdateMap;
	}
	public void setWeeklyoffdateMap(Map<Date, String> weeklyoffdateMap) {
		this.weeklyoffdateMap = weeklyoffdateMap;
	}
	public Map<Date, String> getAttendanceRegMap() {
		return attendanceRegMap;
	}
	public void setAttendanceRegMap(Map<Date, String> attendanceRegMap) {
		this.attendanceRegMap = attendanceRegMap;
	}
	public Long getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(Long presentDays) {
		this.presentDays = presentDays;
	}
/*	public Long getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Long leaveDays) {
		this.leaveDays = leaveDays;
	}*/
	public Long getWeeklyoffDays() {
		return weeklyoffDays;
	}
	public void setWeeklyoffDays(Long weeklyoffDays) {
		this.weeklyoffDays = weeklyoffDays;
	}
	public Long getHolidayDays() {
		return holidayDays;
	}
	public void setHolidayDays(Long holidayDays) {
		this.holidayDays = holidayDays;
	}
	public Long getAbsense() {
		return absense;
	}
	public void setAbsense(Long absense) {
		this.absense = absense;
	}

	public BigDecimal getSandwitchWeekoffCount() {
		return sandwitchWeekoffCount;
	}
	public void setSandwitchWeekoffCount(BigDecimal sandwitchWeekoffCount) {
		this.sandwitchWeekoffCount = sandwitchWeekoffCount;
	}
	public BigDecimal getSandwitchHolidayCount() {
		return sandwitchHolidayCount;
	}
	public void setSandwitchHolidayCount(BigDecimal sandwitchHolidayCount) {
		this.sandwitchHolidayCount = sandwitchHolidayCount;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public BigDecimal getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(BigDecimal leaveDays) {
		this.leaveDays = leaveDays;
	}
	public Long getHalfDays() {
		return halfDays;
	}
	public void setHalfDays(Long halfDays) {
		this.halfDays = halfDays;
	}
	public Long getArDays() {
		return arDays;
	}
	public BigDecimal getTotleLeaveDays() {
		return totleLeaveDays;
	}
	public void setTotleLeaveDays(BigDecimal totleLeaveDays) {
		this.totleLeaveDays = totleLeaveDays;
	}
	public void setArDays(Long arDays) {
		this.arDays = arDays;
	}
	public boolean isWeekOffBetweenTwoAbsent() {
		return weekOffBetweenTwoAbsent;
	}
	public void setWeekOffBetweenTwoAbsent(boolean weekOffBetweenTwoAbsent) {
		this.weekOffBetweenTwoAbsent = weekOffBetweenTwoAbsent;
	}
	public boolean isHolidayBetweenTwoAbsent() {
		return holidayBetweenTwoAbsent;
	}
	public void setHolidayBetweenTwoAbsent(boolean holidayBetweenTwoAbsent) {
		this.holidayBetweenTwoAbsent = holidayBetweenTwoAbsent;
	}
	public Long getHolidayAsAbsentDateCount() {
		return holidayAsAbsentDateCount;
	}
	public void setHolidayAsAbsentDateCount(Long holidayAsAbsentDateCount) {
		this.holidayAsAbsentDateCount = holidayAsAbsentDateCount;
	}
	public Long getWeekoffAbsentDateCount() {
		return weekoffAbsentDateCount;
	}
	public void setWeekoffAbsentDateCount(Long weekoffAbsentDateCount) {
		this.weekoffAbsentDateCount = weekoffAbsentDateCount;
	}
	
	

}
