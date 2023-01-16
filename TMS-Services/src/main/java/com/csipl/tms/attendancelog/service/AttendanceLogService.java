package com.csipl.tms.attendancelog.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDetailsDTO;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.empcommondetails.EmpCommonDetail;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.model.leave.TMSLeaveEntry;

public interface AttendanceLogService {

	void savePunchTimeLog(List<AttendanceLog> attendanceLogList );

	void savePunchTimeLogViaDate(List<AttendanceLog> attendanceLogList ,Date date);
	
	public List<Object[]> getAttendance(Long companyId, Long employeeId, String fromDate, String toDate);

	public List<AttendanceLog> getAttendanceLogDetails(Long companyId, String date);
	
	

	public List<AttendanceLogDetailsDTO> getAttendanceLogDetailsDTOList(List<AttendanceLog> attendanceLogDetailsList,
			List<EmpCommonDetail> empCommonDetailList, HalfDayRule halfDayRule, List<TMSLeaveEntry> leaveList,
			List<HolidayDTO> holidayDtoList, Date date);

	List<AttendanceLog> markBulkAttendance(List<AttendanceLog> attendanceLogList);
	
	public AttendanceLogDTO calculateAttendanceForPayroll(Long employeeId , String processMonth ,Long companyId) throws ErrorHandling, ParseException;

	List<Object[]> attendanceReport(Long companyId, Date attendanceDate);
	
	public List<Object[]> getLateCommersEmployeeListWithCount(Long companyId);
	
	public List<Object[]> getLateCommersEmployeeListWithCountViaDate(Long companyId , Date date);
	
	public List<Object[]> getCheckInFromPunchTimeDetails(Long employeeId);
	
	public List<AttendanceLog> getAttendanceLogPreviousDays(Long companyId, Date date);

	void updateAttendaceData(Long companyId, String employeeCode, Date date, String status, Long employeeId);
}
