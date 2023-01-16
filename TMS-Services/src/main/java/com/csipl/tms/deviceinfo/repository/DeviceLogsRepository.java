package com.csipl.tms.deviceinfo.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;

@Transactional
public interface DeviceLogsRepository extends CrudRepository<DeviceLogsInfo, Long> {
	String newPresent = "SELECT COUNT(DISTINCT dl.userId) FROM DeviceLogsInfo dl JOIN Employee e ON dl.userId=e.employeeCode WHERE cast(dl.logDate as date) = CURRENT_DATE AND e.activeStatus='AC' ";

//	@Query("SELECT COUNT(DISTINCT dl.userId) FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date) = CURRENT_DATE")
	@Query(value = newPresent, nativeQuery = true)
	public int presentCountAll();

	@Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT dl.userId)  FROM DeviceLogsInfo dl JOIN Employee e ON dl.userId = e.employeeCode WHERE cast(dl.logDate as date)=CURRENT_DATE and e.companyId =?1 AND e.activeStatus='AC' and e.ReportingToEmployee =?2")
	public int presentCountReportingTo(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = "SELECT COUNT(*)  FROM Employee WHERE employeeCode  NOT IN (SELECT DISTINCT(dl.userId) FROM DeviceLogsInfo dl where cast(dl.logDate as date)=CURRENT_DATE  ) AND activeStatus='AC'")
	public int absentCountAll();

	@Query(nativeQuery = true, value = "SELECT count(*)  FROM Employee e WHERE e.employeeCode NOT IN (SELECT DISTINCT(dl.userId) FROM DeviceLogsInfo  dl  where cast(dl.logDate as date)=CURRENT_DATE ) and e.companyId=?1 AND e.ReportingToEmployee=?2 AND e.activeStatus='AC' ")
	public int absentCountReportingTo(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = "select COUNT(DISTINCT dl.userId) from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId WHERE  cast(dl.logDate as date) = CURRENT_DATE and cast(dl.logDate as time) > sh.graceTime")
	public int lateComersAll();

	@Query(nativeQuery = true, value = "select COUNT(DISTINCT dl.userId) from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId WHERE  cast(dl.logDate as date) = CURRENT_DATE and e.companyId=?1 and e.ReportingToEmployee =?2 and  cast(dl.logDate as time) > sh.graceTime")
	public int lateComersReportingTo(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime)as ReportedLateBy ,dl.mode \r\n"
			+ "from DeviceLogsInfo dl\r\n"
			+ "JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE   e.companyId=?1  AND e.activeStatus='AC'  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= CURRENT_DATE \r\n"
			+ "group by e.employeeCode")
	public List<Object[]> getAllLateComersList(Long companyId);

	@Query(nativeQuery = true, value = "SELECT concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName  FROM Employee e \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE e.employeeCode NOT IN (SELECT userId FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date)=CURRENT_DATE ) \r\n"
			+ "and e.companyId=?1  AND e.activeStatus='AC'  GROUP BY e.employeeCode")
	public List<Object[]> getAllAbsentList(Long companyId);

	String presentNew = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,dl.mode ,concat(TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime),'') as ReportedLateBy from DeviceLogsInfo dl 	JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId  	JOIN Department dept ON e.departmentId=dept.departmentId  	WHERE   e.companyId=?1  AND e.activeStatus='AC'  and cast(dl.logDate as date)=CURRENT_DATE group by e.employeeCode";

	@Query(nativeQuery = true, value = presentNew)
	public List<Object[]> getAllPresentList(Long companyId);

	@Query(nativeQuery = true, value = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime)as ReportedLateBy ,dl.mode,sh.startTime \r\n"
			+ "from DeviceLogsInfo dl\r\n"
			+ "JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE   e.companyId=?1  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= ?2 AND e.activeStatus='AC'  \r\n"
			+ "group by e.employeeCode")
	public List<Object[]> getAllLateComersListByDate(Long companyId, String selectDate);

	@Query(nativeQuery = true, value = "SELECT concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName  FROM Employee e \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE e.employeeCode NOT IN (SELECT userId FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date)=?2 ) \r\n"
			+ "and e.companyId=?1  AND e.activeStatus='AC'  GROUP BY e.employeeCode")
	public List<Object[]> getAllAbsentListByDate(Long companyId, String selectDate);

	@Query(nativeQuery = true, value = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,dl.mode  ,concat(TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime),'') as ReportedLateBy ,sh.startTime from DeviceLogsInfo dl 	JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId  	JOIN Department dept ON e.departmentId=dept.departmentId  	WHERE   e.companyId=?1  AND e.activeStatus='AC'  and cast(dl.logDate as date)=?2 group by e.employeeCode")
	public List<Object[]> getAllPresentListByDate(Long companyId, String selectDate);

	@Modifying
	@Query(value = "delete from  DeviceLogsInfo where cast(logDate as date)=CURRENT_DATE")
	public void deleteByLogDate();

	String CURRENT_ATTENDANCE_REPORT = "CALL 	pro_live_attendance_report( :p_comp_id ,:p_selected_date)";

	@Query(nativeQuery = true, value = CURRENT_ATTENDANCE_REPORT)
	public List<Object[]> currentAttendanceReport(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_selected_date") String p_selected_date);

//	/* get punch in on current date */
//	@Query(" from where CAST(logDate AS DATE) =?1 AND userId =?2 ")
//	public List<Object[]> punchInOnCDate(Date cdate , String nameOfUser);

	/* get punch in on current date */
	String CDATE_PUNCHIN = "SELECT dl.userId, dl.logDate ,dl.mode FROM DeviceLogsInfo dl WHERE CAST(dl.logDate AS DATE)  =?1 AND dl.userId  =?2";

	@Query(nativeQuery = true, value = CDATE_PUNCHIN)
	public List<Object[]> punchInOnCDate(Date cdate, String nameOfUser);

	public static final String LATE_COMMERS_EMPLOYEE_LIST = "select concat( e.firstName,' ',e.lastName) as 'empName',e.employeeCode  ,e.officialEmail,sh.graceFrqInMonth , cast(dl.logDate as time) as reportingTime\r\n"
			+ "from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh JOIN AttendanceLogs al on dl.userId = e.employeeCode and sh.shiftId=e.shiftId  AND e.employeeId=al.employeeId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n" + "    \r\n"
			+ "WHERE   e.companyId=?1  AND e.activeStatus='AC'  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= CURRENT_DATE AND\r\n"
			+ " al.attendanceDate>=?2 AND al.attendanceDate<=?3 AND e.activeStatus='AC' AND TIMEDIFF(cast(al.inTime as time) ,sh.graceTime) > 0 \r\n"
			+ "\r\n" + "group by e.employeeCode";

	@Query(value = LATE_COMMERS_EMPLOYEE_LIST, nativeQuery = true)
	List<Object[]> getLateCommersEmployeeListWithCount(Long companyId, String fromDate, Date toDate);

	public static final String GET_ATTEDANCE_SHORTFALL_EMPLOYEE = "SELECT concat( emp.firstName,' ',emp.lastName) as 'empName',emp.employeeCode ,emp.officialEmail ,att.attendanceDate,att.inTime as 'checkIn', cast(TIMEDIFF(cast(shift.endTime as time) ,shift.startTime) as time)as shiftduration, cast(TIMEDIFF(cast(att.outTime as time) ,att.inTime) as time)as totaltime FROM AttendanceLogs att LEFT JOIN Employee emp ON att.employeeId = emp.employeeId LEFT JOIN TMSShift shift ON emp.shiftId = shift.shiftId where att.attendanceDate =SUBDATE(CURDATE(),1) AND cast(TIMEDIFF(cast(shift.endTime as time) ,shift.startTime) as time) >cast(TIMEDIFF(cast(att.outTime as time) ,att.inTime) as time) and emp.companyId=?1";

	@Query(value = GET_ATTEDANCE_SHORTFALL_EMPLOYEE, nativeQuery = true)
	public List<Object[]> getAttedanceShortFallEmployee(Long companyId);

	@Query(nativeQuery = true, value = "SELECT concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName  FROM Employee e \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE e.employeeCode NOT IN (SELECT userId FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date)=?3 ) \r\n"
			+ "and e.companyId=?1 AND e.ReportingToEmployee=?2  AND e.activeStatus='AC'  GROUP BY e.employeeCode")
	public List<Object[]> getTeamsAbsentListByDate(Long companyId, Long employeeId, String selectDate);

	@Query(nativeQuery = true, value = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,dl.mode  ,concat(TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime),'') as ReportedLateBy ,sh.startTime from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId  	WHERE   e.companyId=?1  AND e.ReportingToEmployee=?2 AND e.activeStatus='AC'  "
			+ "and cast(dl.logDate as date)=?3 group by e.employeeCode")
	public List<Object[]> getTeamsPresentListByDate(Long companyId, Long employeeId, String selectDate);

	@Query(nativeQuery = true, value = "select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime)as ReportedLateBy ,dl.mode,sh.startTime ,sh.graceTime\r\n"
			+ "from DeviceLogsInfo dl\r\n"
			+ "JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "WHERE   e.companyId=?1  AND e.ReportingToEmployee=?2  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= ?3 AND e.activeStatus='AC'  \r\n"
			+ "group by e.employeeCode")
	public List<Object[]> getTeamsLateComersListByDate(Long companyId, Long employeeId, String selectDate);

	// by
	String TEAMS_ATTENDANCE_REPORT = "CALL 	pro_live_attendance_report_of_team( :p_comp_id ,:p_emp_id ,:p_selected_date)";

	@Query(nativeQuery = true, value = TEAMS_ATTENDANCE_REPORT)
	public List<Object[]> teamsAttendanceReport(@Param(value = "p_comp_id") Long p_comp_id,
			@Param(value = "p_emp_id") Long p_emp_id, @Param(value = "p_selected_date") String p_selected_date);
}