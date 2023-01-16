package com.csipl.tms.attendancelog.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.attendancelog.AttendanceLog;

@Repository
public interface AttendanceLogRepository extends CrudRepository<AttendanceLog, Long> {

	@Query("FROM AttendanceLog  WHERE companyId=?1 AND attendanceDate=?2")
	List<AttendanceLog> getAttendanceLog(Long companyId, Date date);

	String ATTENDANCE_BY_EMPLOYEEID_AND_MONTH = "SELECT emp.employeeId,emp.employeeCode,concat(concat(emp.firstName,' '),emp.lastName) as empname,\r\n"
			+ "dept.departmentName,des.designationName,concat(concat(e.firstName,' '),e.lastName) as reportingmanager,c.cityName,\r\n"
			+ "al.attendanceDate,al.inTime,al.outTime,al.location,al.mode,al.status,al.totalTime ,shift.shiftFName,shift.shiftDuration\r\n"
			+ "from Employee emp \r\n"
			+ "LEFT join AttendanceLogs al ON al.employeeId= emp.employeeId and MONTH(al.attendanceDate)=?2  \r\n"
			+ "LEFT JOIN Department dept on emp.departmentId=dept.departmentId\r\n"
			+ "LEFT JOIN Designation des on emp.designationId= des.designationId\r\n"
			+ "LEFT JOIN Employee e on emp.ReportingToEmployee=e.employeeId\r\n"
			+ "LEFT JOIN TMSShift shift ON shift.shiftId = emp.shiftId  Left Join City c on emp.cityId=c.cityId "
			+ " WHERE emp.employeeId=?1  and  emp.companyId=?3";

	@Query(value = ATTENDANCE_BY_EMPLOYEEID_AND_MONTH, nativeQuery = true)
	List<Object[]> getAttendanceLogByEmployeeIdandMonth(Long employeeId, int processMonth, Long companyId);

	String ATTENDANCE_BY_EMPLOYEEID_AND_MONTH_AND_YEAR = " SELECT emp.employeeId,emp.employeeCode,concat(concat(emp.firstName,' '),emp.lastName) as empname,\r\n"
			+ "			dept.departmentName,des.designationName,concat(concat(e.firstName,' '),e.lastName) as reportingmanager,c.cityName, \r\n"
			+ "			al.attendanceDate,al.inTime,al.outTime,al.location,al.mode,al.status,al.totalTime ,shift.shiftFName,shift.shiftDuration \r\n"
			+ "			from Employee emp \r\n"
			+ "			LEFT join AttendanceLogs al ON al.employeeId= emp.employeeId and MONTH(al.attendanceDate)=?3  and  YEAR(al.attendanceDate)=?4 \r\n"
			+ "			LEFT JOIN Department dept on emp.departmentId=dept.departmentId \r\n"
			+ "			LEFT JOIN Designation des on emp.designationId= des.designationId \r\n"
			+ "			LEFT JOIN Employee e on emp.ReportingToEmployee=e.employeeId \r\n"
			+ "			LEFT JOIN TMSShift shift ON shift.shiftId = emp.shiftId  Left Join City c on emp.cityId=c.cityId \r\n "
			+ "			WHERE emp.employeeId=?1  and  emp.companyId=?2 ";

	@Query(value = ATTENDANCE_BY_EMPLOYEEID_AND_MONTH_AND_YEAR, nativeQuery = true)
	List<Object[]> getAttendanceLogByEmployeeIdandMonthAndYear(Long employeeId, Long companyId, int processMonth,
			int processYear);

	String LATE_COMERS_LIST_FROM_ATTENDANCE_LOG_DEPARTMENT_WISE = "select dl.attendanceDate,e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName,concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')),\r\n"
			+ "dl.inTime, dl.outTime, dl.totalTime,cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time)as ReportedLateBy, dl.status from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId\r\n"
			+ "  LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId\r\n"
			+ "	WHERE e.companyId=?1 and dl.attendanceDate>=?2 AND dl.attendanceDate<=?3 AND dept.departmentId IN (?4)"
			+ " AND e.activeStatus='AC' AND TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) > 0  ORDER BY e.employeeCode ";

	@Query(value = LATE_COMERS_LIST_FROM_ATTENDANCE_LOG_DEPARTMENT_WISE, nativeQuery = true)
	List<Object[]> getLateComersListFromAttendanceLogDepartmentWise(Long companyId, Date fDate, Date tDate,
			List<Long> deptids);

	String LATE_COMERS_LIST_FROM_ATTENDANCE_LOG_EMPLOYEE_WISE = " select dl.attendanceDate,e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName,concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime, dl.outTime, dl.totalTime, cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time)as ReportedLateBy, dl.status from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId\r\n"
			+ " LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN  Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId\r\n"
			+ "	WHERE   e.companyId=?1  and  dl.attendanceDate>=?2 AND dl.attendanceDate<=?3  AND dl.employeeId=?4"
			+ " AND e.activeStatus='AC' AND TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) > 0  ORDER BY e.employeeCode";

	@Query(value = LATE_COMERS_LIST_FROM_ATTENDANCE_LOG_EMPLOYEE_WISE, nativeQuery = true)
	List<Object[]> getLateComersListFromAttendanceLogEmployeeWise(Long companyId, Date fDate, Date tDate, Long empId);

	String EARLY_COMERS_LIST_EMPLOYEE_WISE = "select dl.attendanceDate,e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "			concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName,concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime as TimeIn,dl.outTime,dl.totalTime,\r\n"
			+ "			TIMEDIFF(sh.startTime, dl.inTime)as ReportedEarlyBy, dl.status\r\n"
			+ "			from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId \r\n"
			+ "			JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "			  JOIN  Designation des ON des.designationId=e.designationId\r\n"
			+ "			  LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId\r\n"
			+ "	JOIN City c ON e.cityId=c.cityId WHERE e.companyId= ?1 and  dl.attendanceDate>=?2 AND dl.attendanceDate<=?3 "
			+ "AND dl.employeeId=?4 AND e.activeStatus='AC' AND TIMEDIFF(sh.startTime, dl.inTime) > 0 ORDER BY date(dl.attendanceDate)";

	@Query(value = EARLY_COMERS_LIST_EMPLOYEE_WISE, nativeQuery = true)
	List<Object[]> getEarlyComersListEmployeeWise(Long companyId, Date fDate, Date tDate, Long employeeId);

	String EARLY_COMERS_LIST_DEPT_WISE = "select dl.attendanceDate,e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "		concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName,concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime as TimeIn,dl.outTime,dl.totalTime,\r\n"
			+ "	TIMEDIFF(sh.startTime, dl.inTime)as ReportedEarlyBy, dl.status\r\n"
			+ "			from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId \r\n"
			+ "			JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "			  JOIN  Designation des ON des.designationId=e.designationId\r\n"
			+ "			  LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId\r\n"
			+ "	JOIN City c ON e.cityId=c.cityId WHERE e.companyId= ?1 and  dl.attendanceDate>=?2 AND dl.attendanceDate<=?3 "
			+ "AND dept.departmentId IN (?4) AND e.activeStatus='AC' AND TIMEDIFF(sh.startTime, dl.inTime) > 0 ORDER BY date(dl.attendanceDate)";

	@Query(value = EARLY_COMERS_LIST_DEPT_WISE, nativeQuery = true)
	List<Object[]> getEarlyComersListDeptWise(Long companyId, Date fDate, Date tDate, List<Long> departmentIds);

	String WORKED_ON_HOLIDAYS_DETAILS_EMP_WISE = "select dl.attendanceDate, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime, dl.outTime, dl.totalTime, dl.status from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId LEFT JOIN TMSHolidays th on th.companyId=dl.companyId WHERE dl.attendanceDate BETWEEN th.fromDate AND th.toDate and "
			+ "e.companyId= ?1 AND dl.employeeId =?2 AND dl.attendanceDate>= ?3 AND dl.attendanceDate<= ?4 AND e.activeStatus='AC' ORDER BY date(dl.attendanceDate)";

	@Query(value = WORKED_ON_HOLIDAYS_DETAILS_EMP_WISE, nativeQuery = true)
	List<Object[]> getWorkedOnHolidayDetailsEmpWise(Long companyId, Long employeeId, Date fDate, Date tDate);

	String WORKED_ON_HOLIDAYS_DETAILS_DEPT_WISE = "select dl.attendanceDate, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime, dl.outTime, dl.totalTime, dl.status from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN  Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId LEFT JOIN TMSHolidays th on dl.companyId=th.companyId WHERE dl.attendanceDate BETWEEN th.fromDate AND th.toDate and "
			+ "e.companyId= ?1 AND dept.departmentId in ?2 AND dl.attendanceDate>=?3 AND dl.attendanceDate<=?4 AND e.activeStatus='AC' ORDER BY date(dl.attendanceDate)";

	@Query(value = WORKED_ON_HOLIDAYS_DETAILS_DEPT_WISE, nativeQuery = true)
	List<Object[]> getWorkedOnHolidayDetailsDeptWise(Long companyId, List<Long> departmentIds, Date fDate, Date tDate);

	String WORKED_ON_WEEKOFF_DETAILS_EMP_WISE = "select dl.attendanceDate, UPPER(DATE_FORMAT(dl.attendanceDate, '%a')) as attendanceDay, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime, dl.outTime, dl.totalTime, dl.status, e.patternId, tw.day from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId LEFT JOIN TMSWeekOffPattern tw on e.patternId=tw.patternId WHERE "
			+ "e.companyId= ?1 AND dl.employeeId=?2 AND dl.attendanceDate>= ?3 AND dl.attendanceDate<= ?4  AND e.activeStatus='AC'";

	@Query(value = WORKED_ON_WEEKOFF_DETAILS_EMP_WISE, nativeQuery = true)
	List<Object[]> getWorkedOnWeekOffDetailsEmpWise(Long companyId, Long employeeId, Date fDate, Date tDate);

	String WORKED_ON_WEEKOFF_DETAILS_DEPT_WISE = "select dl.attendanceDate, UPPER(DATE_FORMAT(dl.attendanceDate, '%a')) as attendanceDay, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Name,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repName,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.inTime, dl.outTime, dl.totalTime, dl.status, e.patternId, tw.day from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN  Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId LEFT JOIN TMSWeekOffPattern tw on tw.patternId=e.patternId WHERE "
			+ "e.companyId= ?1 AND dept.departmentId in ?2 AND dl.attendanceDate>= ?3 AND dl.attendanceDate<= ?4 AND e.activeStatus='AC' ORDER BY date(dl.attendanceDate)";

	@Query(value = WORKED_ON_WEEKOFF_DETAILS_DEPT_WISE, nativeQuery = true)
	List<Object[]> getWorkedOnWeekOffDetailsDeptWise(Long companyId, List<Long> departmentIds, Date fDate, Date tDate);

	String EARLY_LEAVERS_DETAILS_EMPLOYEE_WISE = "select dl.attendanceDate as date,e.employeeCode as Code , concat( e.firstName,' ',e.lastName) AS\r\n"
			+ "Employee_Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift,"
			+ "concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) as Shift_Duration, dl.inTime as TimeIn,dl.outTime as TimeOut,dl.totalTime,cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) AS EarlyBy, dl.status\r\n"
			+ "from AttendanceLogs dl  LEFT JOIN Employee e ON \r\n"
			+ "e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "JOIN  Designation des ON des.designationId=e.designationId\r\n"
			+ "LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId\r\n"
			+ "JOIN City c ON e.cityId=c.cityId\r\n"
			+ "WHERE   e.companyId=?1 and  dl.attendanceDate>=?2 AND dl.attendanceDate<=?3\r\n"
			+ " AND dl.employeeId=?4 AND e.activeStatus='AC'  AND  cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) > 0 ORDER BY date(dl.attendanceDate)";

	@Query(value = EARLY_LEAVERS_DETAILS_EMPLOYEE_WISE, nativeQuery = true)
	List<Object[]> getEarlyLeaversDetailsEmployeeWise(Long companyId, Date fromDate, Date toDate, Long employeeId);

	String EARLY_LEAVERS_DETAILS_DEPARTMENT_WISE = "select dl.attendanceDate as date,e.employeeCode as Code , concat( e.firstName,' ',e.lastName) AS\r\n"
			+ "Employee_Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift,"
			+ "concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) as Shift_Duration, dl.inTime as TimeIn,dl.outTime as TimeOut,dl.totalTime ,"
			+ "cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) AS EarlyBy, dl.status\r\n"
			+ "from AttendanceLogs dl  LEFT JOIN Employee e ON \r\n"
			+ "e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId \r\n"
			+ "JOIN  Designation des ON des.designationId=e.designationId\r\n"
			+ "LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId\r\n"
			+ "JOIN City c ON e.cityId=c.cityId\r\n"
			+ "WHERE   e.companyId=?1 and  dl.attendanceDate>=?2 AND dl.attendanceDate<=?3\r\n"
			+ "AND dept.departmentId IN (?4) AND e.activeStatus='AC'  AND  cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) > 0 ORDER BY date(dl.attendanceDate) ";

	@Query(value = EARLY_LEAVERS_DETAILS_DEPARTMENT_WISE, nativeQuery = true)
	List<Object[]> getEarlyLeaversDetailsDepartmentWise(Long companyId, Date fromDate, Date toDate,
			List<Long> departmentIds);

	String AR_REQUEST_EMPLOYEE_WISE = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name \r\n"
			+ "					,dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName) \r\n"
			+ "					AS Reporting_Manager, tm.dateCreated ,tm.arCategory as Reason,tm.fromDate ,tm.toDate  ,tm.days,\r\n"
			+ "						tm.employeeRemark AS Requester_remark,tm.status ,concat(emp.firstName,' ',emp.lastName) AS Action_taken_By ,tm.actionableDate ,tm.approvalRemark FROM Employee e JOIN  TMSARRequest tm on tm.employeeId=e.employeeId\r\n"
			+ "						  JOIN Department dep ON e.departmentId=dep.departmentId JOIN Designation des \r\n"
			+ "						ON e.designationId=des.designationId JOIN City c ON e.cityId=c.cityId LEFT JOIN Employee emp\r\n"
			+ "						ON emp.employeeId = tm.approvalId LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId\r\n"
			+ "						 WHERE e.companyId=?1  AND e.employeeId=?2 AND  tm.fromDate >=?3 AND tm.toDate<=?4 ";

	@Query(value = AR_REQUEST_EMPLOYEE_WISE, nativeQuery = true)
	public List<Object[]> getArRequestReportEmloyeeWise(Long companyId, Long employeeId, Date fDate, Date tDate);

	String AR_REQUEST__DEPARTMENT_WISE = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name \r\n"
			+ "					,dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName) \r\n"
			+ "					AS Reporting_Manager, tm.dateCreated ,tm.arCategory as Reason,tm.fromDate ,tm.toDate  ,tm.days,\r\n"
			+ "						tm.employeeRemark AS Requester_remark,tm.status ,concat(emp.firstName,' ',emp.lastName) AS Action_taken_By ,tm.actionableDate ,tm.approvalRemark FROM Employee e JOIN  TMSARRequest tm on tm.employeeId=e.employeeId\r\n"
			+ "						  JOIN Department dep ON e.departmentId=dep.departmentId JOIN Designation des \r\n"
			+ "						ON e.designationId=des.designationId JOIN City c ON e.cityId=c.cityId LEFT JOIN Employee emp\r\n"
			+ "						ON emp.employeeId = tm.approvalId LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId\r\n"
			+ "						 WHERE   e.activeStatus='AC' AND e.companyId=?1  AND dep.departmentId IN (?2) AND tm.fromDate >=?3 AND tm.toDate<=?4";

	@Query(value = AR_REQUEST__DEPARTMENT_WISE, nativeQuery = true)
	public List<Object[]> getArRequestReportDepartmentWise(Long companyId, List<Long> departmentList, Date fDate,
			Date tDate);

	// Over Time-Day Wise

	String OVER_TIME_DAY_WISE_DETAILS_EMPLOYEE_WISE = "select dl.attendanceDate as date,e.employeeCode as Code , concat( e.firstName,' ',e.lastName) AS Employee_Name,dept.departmentName,des.designationName,c.cityName,concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) AS Shift_Duration, dl.inTime AS TimeIn,dl.outTime as TimeOut,dl.totalTime , cast(TIMEDIFF( TIMEDIFF(dl.outTime,dl.inTime) ,cast(sh.shiftDuration*10000 AS time)) AS Time ) AS Over_Time from AttendanceLogs dl   JOIN Employee e ON  e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  JOIN Department dept ON e.departmentId=dept.departmentId  JOIN  Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId WHERE  e.companyId=?1 and  dl.attendanceDate>=?2 AND dl.attendanceDate <=?3 AND dl.employeeId=?4 AND e.activeStatus='AC' AND TIMEDIFF(dl.outTime,dl.inTime) > cast(sh.shiftDuration*10000 AS time) AND cast(TIMEDIFF( TIMEDIFF(dl.outTime,dl.inTime) ,cast(sh.shiftDuration*10000 AS time)) AS Time ) > 0 ORDER BY date(dl.attendanceDate)";

	@Query(value = OVER_TIME_DAY_WISE_DETAILS_EMPLOYEE_WISE, nativeQuery = true)
	List<Object[]> getOverTimeDayWiseDetailsEmployeeWise(Long companyId, Date fromDate, Date toDate, Long employeeId);

	String OVER_TIME_DAY_WISE_DETAILS_DEPARTMENT_WISE = "select dl.attendanceDate as date,e.employeeCode as Code , concat( e.firstName,' ',e.lastName) AS\r\n"
			+ "Employee_Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) AS Shift_Duration,\r\n"
			+ "dl.inTime AS TimeIn,dl.outTime as TimeOut,dl.totalTime ,\r\n"
			+ " cast(TIMEDIFF( TIMEDIFF(dl.outTime,dl.inTime) ,cast(sh.shiftDuration*10000 AS time)) AS Time ) AS Over_Time from AttendanceLogs dl   JOIN Employee e ON \r\n"
			+ "e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  \r\n"
			+ "JOIN Department dept ON e.departmentId=dept.departmentId  \r\n"
			+ "JOIN  Designation des ON des.designationId=e.designationId\r\n"
			+ "LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId\r\n"
			+ "JOIN City c ON e.cityId=c.cityId \r\n" + "WHERE  e.companyId=?1 and  dl.attendanceDate>=?2 AND\r\n"
			+ "dl.attendanceDate <= ?3 AND\r\n"
			+ "dept.departmentId IN (?4) AND e.activeStatus='AC' AND TIMEDIFF(dl.outTime,dl.inTime) > cast(sh.shiftDuration*10000 AS time)  AND cast(TIMEDIFF( TIMEDIFF(dl.outTime,dl.inTime) ,cast(sh.shiftDuration*10000 AS time)) AS Time ) > 0   ORDER BY date(dl.attendanceDate)";

	@Query(value = OVER_TIME_DAY_WISE_DETAILS_DEPARTMENT_WISE, nativeQuery = true)
	List<Object[]> getOverTimeDayWiseDetailsDepartmentWise(Long companyId, Date fromDate, Date toDate,
			List<Long> departmentIds);

	// Over Time-Month Wise

	String OVER_TIME_MONTH_WISE_DETAILS_EMPLOYEE_WISE = "select e.employeeCode as Code , \r\n"
			+ "			concat( e.firstName,' ',e.lastName) AS\r\n"
			+ "Employee_Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift,\r\n"
			+ " concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) AS Shift_Duration\r\n"
			+ "\r\n"
			+ ",SUM(hour(TIMEDIFF(dl.outTime,dl.inTime))) as totH,SUM(minute(TIMEDIFF(dl.outTime,dl.inTime))) as totM ,SUM(second(TIMEDIFF(dl.outTime,dl.inTime))) as totS,\r\n"
			+ "\r\n"
			+ "SUM(hour(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otH\r\n"
			+ ",SUM(minute(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otM,\r\n"
			+ "SUM(second(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otS\r\n"
			+ "\r\n" + "from AttendanceLogs dl\r\n" + "\r\n" + "JOIN Employee e ON \r\n"
			+ "e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  \r\n"
			+ "			JOIN Department dept ON e.departmentId=dept.departmentId   \r\n"
			+ "			JOIN  Designation des ON des.designationId=e.designationId \r\n"
			+ "			LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId \r\n"
			+ "			JOIN City c ON e.cityId=c.cityId\r\n"
			+ "			WHERE  e.companyId=?1 and  MONTH(dl.attendanceDate)=?2 AND YEAR(dl.attendanceDate)=?3  AND\r\n"
			+ "		e.employeeId=?4 AND e.activeStatus='AC' AND cast(TIMEDIFF(dl.outTime,dl.inTime) AS Time) > cast(sh.shiftDuration*10000 AS Time) AND cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time) > 0\r\n"
			+ "                  GROUP BY e.employeeId    ORDER by e.firstName";

	@Query(value = OVER_TIME_MONTH_WISE_DETAILS_EMPLOYEE_WISE, nativeQuery = true)
	List<Object[]> getOverTimeMonthWiseDetailsEmployeeWise(Long companyId, int pMonth, int pYear, Long employeeId);

	String OVER_TIME_MONTH_WISE_DETAILS_DEPARTMENT_WISE = "select e.employeeCode as Code , \r\n"
			+ "			concat( e.firstName,' ',e.lastName) AS\r\n"
			+ "Employee_Name,dept.departmentName,des.designationName,c.cityName,\r\n"
			+ "concat( re.firstName,' ',re.lastName) AS Reporting_Manager,sh.shiftFName AS Shift,\r\n"
			+ " concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) AS Shift_Duration\r\n"
			+ "\r\n"
			+ ",SUM(hour(TIMEDIFF(dl.outTime,dl.inTime))) as totH,SUM(minute(TIMEDIFF(dl.outTime,dl.inTime))) as totM ,SUM(second(TIMEDIFF(dl.outTime,dl.inTime))) as totS,\r\n"
			+ "\r\n"
			+ "SUM(hour(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otH\r\n"
			+ ",SUM(minute(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otM,\r\n"
			+ "SUM(second(cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time))) as otS\r\n"
			+ "\r\n" + "from AttendanceLogs dl\r\n" + "\r\n" + "JOIN Employee e ON \r\n"
			+ "e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  \r\n"
			+ "			JOIN Department dept ON e.departmentId=dept.departmentId   \r\n"
			+ "			JOIN  Designation des ON des.designationId=e.designationId \r\n"
			+ "			LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId \r\n"
			+ "			JOIN City c ON e.cityId=c.cityId\r\n"
			+ "			WHERE  e.companyId=?1 and  MONTH(dl.attendanceDate)=?2 AND YEAR(dl.attendanceDate)=?3  AND\r\n"
			+ "		dept.departmentId IN (?4) AND e.activeStatus='AC' AND cast(TIMEDIFF(dl.outTime,dl.inTime) AS Time) > cast(sh.shiftDuration*10000 AS Time) AND cast(TIMEDIFF(TIMEDIFF(dl.outTime,dl.inTime),cast(sh.shiftDuration*10000 as time)) as time) > 0\r\n"
			+ "                  GROUP BY e.employeeId    ORDER by e.firstName";

	@Query(value = OVER_TIME_MONTH_WISE_DETAILS_DEPARTMENT_WISE, nativeQuery = true)
	List<Object[]> getOverTimeMonthWiseDetailsDepartmentWise(Long companyId, int pMonth, int pYear,
			List<Long> departmentIds);

	String MissingCheckIn_OutEmployeeWise = "   SELECT al.attendanceDate, emp.employeeCode, concat(emp.firstName,' ',emp.lastName) as empname,\r\n"
			+ "	dept.departmentName, des.designationName,  c.cityName, concat(concat(e.firstName,' '),e.lastName) as reportingmanager,\r\n"
			+ "	 shift.shiftFName,   concat(TIME_FORMAT(shift.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(shift.endTime, '%h:%i:%s %p'))as shiftDuration ,al.inTime ,al.status  from Employee emp \r\n"
			+ "	LEFT JOIN Department dept on emp.departmentId=dept.departmentId \r\n"
			+ "	LEFT JOIN Designation des on emp.designationId= des.designationId \r\n"
			+ "	LEFT JOIN Employee e on emp.ReportingToEmployee=e.employeeId \r\n"
			+ "	 LEFT JOIN TMSShift shift ON shift.shiftId = emp.shiftId \r\n"
			+ "	Left Join City c on emp.cityId=c.cityId \r\n"
			+ "   LEFT join AttendanceLogs al ON al.employeeId= emp.employeeId  AND al.status ='A' \r\n"
			+ "	LEFT  JOIN TMSLeaveEntriesDatewise tmsld On emp.employeeId=tmsld.employeeId   and tmsld.leaveStatus ='PEN'\r\n"
			+ "	LEFT JOIN TMSARRequest tmsar On emp.employeeId=tmsar.employeeId  and tmsar.status ='PEN'\r\n"
			+ "   WHERE   e.companyId=?1 AND emp.employeeId= ?2  and  al.attendanceDate>= ?3 AND al.attendanceDate<= ?4  "
			+ "   GROUP By  al.attendanceDate,al.employeeId ORDER BY al.attendanceDate\r\n";

	@Query(value = MissingCheckIn_OutEmployeeWise, nativeQuery = true)
	List<Object[]> getMissingPunchRecordEmployeeWise(Long companyId, Long employeeId, Date startDate, Date endDate);

	String MissingCheckIn_OutDepartmentWise = " SELECT al.attendanceDate, emp.employeeCode, concat(emp.firstName,' ',emp.lastName) as empname,\r\n"
			+ "	dept.departmentName, des.designationName,  c.cityName, concat(concat(e.firstName,' '),e.lastName) as reportingmanager,\r\n"
			+ "	 shift.shiftFName,   concat(TIME_FORMAT(shift.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(shift.endTime, '%h:%i:%s %p')) as shiftDuration ,al.inTime ,al.status  from Employee emp \r\n"
			+ "	LEFT JOIN Department dept on emp.departmentId=dept.departmentId \r\n"
			+ "	LEFT JOIN Designation des on emp.designationId= des.designationId \r\n"
			+ "	LEFT JOIN Employee e on emp.ReportingToEmployee=e.employeeId \r\n"
			+ "	 LEFT JOIN TMSShift shift ON shift.shiftId = emp.shiftId \r\n"
			+ "	Left Join City c on emp.cityId=c.cityId \r\n"
			+ "   LEFT join AttendanceLogs al ON al.employeeId= emp.employeeId  AND al.status ='A' \r\n"
			+ "	LEFT  JOIN TMSLeaveEntriesDatewise tmsld On emp.employeeId=tmsld.employeeId   and tmsld.leaveStatus ='PEN'\r\n"
			+ "	LEFT JOIN TMSARRequest tmsar On emp.employeeId=tmsar.employeeId  and tmsar.status ='PEN'\r\n"
			+ "   WHERE   e.companyId=?1 AND dept.departmentId In ?2\r\n"
			+ "  AND  al.attendanceDate>= ?3 AND al.attendanceDate<= ?4  "
			+ "   GROUP By  al.attendanceDate,al.employeeId ORDER BY al.attendanceDate\r\n";

	@Query(value = MissingCheckIn_OutDepartmentWise, nativeQuery = true)
	List<Object[]> getMissingPunchRecordDepartmentWise(Long companyId, List<Long> departmentList, Date startDate,
			Date endDate);

	String ATTENDANCE_LOGS_EMP_WISE = "select dl.attendanceDate, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Employee,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repManager,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.mode, dl.inTime, dl.outTime, dl.totalTime,\r\n"
			+ "dl.status, (CASE WHEN(cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time) LIKE '-%') Then 'On Time' else \r\n"
			+ "cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time) END )  as 'LateBy',(CASE      WHEN(cast(TIMEDIFF(sh.startTime , dl.inTime) as time) LIKE '-%') Then 'On Time' else cast(TIMEDIFF(sh.startTime , dl.inTime) as time) END )  as 'EarlyComer',(CASE WHEN(cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) LIKE '-%') Then 'On Time' else cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) END )  as 'EarlyLeaver', concat(dl.address,'(',dl.latitude, ' / ',dl.longitude,')'), concat(dl.address,'(',dl.outTimeLatitude, ' / ',dl.outTimeLangitude,')') from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId "
			+ "WHERE e.companyId = ?1 AND dl.employeeId = ?2 AND dl.attendanceDate>= ?3 AND dl.attendanceDate<= ?4 AND e.activeStatus='AC' ORDER BY dl.attendanceDate ";

	@Query(value = ATTENDANCE_LOGS_EMP_WISE, nativeQuery = true)
	List<Object[]> getAttendanceLogsSummaryEmpWise(Long companyId, Long employeeId, Date fDate, Date tDate);

	String ATTENDANCE_LOGS_DEPT_WISE = "select dl.attendanceDate, e.employeeCode, concat( e.firstName,' ',e.lastName) AS Employee,dept.departmentName,des.designationName,c.cityName, concat( re.firstName,' ',re.lastName) AS repManager,sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')), dl.mode, dl.inTime, dl.outTime, dl.totalTime,\r\n"
			+ "dl.status, (CASE WHEN(cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time) LIKE '-%') Then 'On Time' else \r\n"
			+ "cast(TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) as time) END )  as 'LateBy',(CASE      WHEN(cast(TIMEDIFF(sh.startTime , dl.inTime) as time) LIKE '-%') Then 'On Time' else cast(TIMEDIFF(sh.startTime , dl.inTime) as time) END )  as 'EarlyComer',(CASE WHEN(cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) LIKE '-%') Then 'On Time' else cast(TIMEDIFF(sh.endTime,dl.outTime) AS TIME ) END )  as 'EarlyLeaver', concat(dl.address,'(',dl.latitude, ' / ',dl.longitude,')'), concat(dl.address,'(',dl.outTimeLatitude, ' / ',dl.outTimeLangitude,')') from AttendanceLogs dl LEFT JOIN Employee e ON e.employeeId=dl.employeeId LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Employee re ON e.ReportingToEmployee=re.employeeId JOIN City c ON e.cityId=c.cityId"
			+ "	 WHERE e.companyId = ?1 AND dept.departmentId in ?2 AND dl.attendanceDate>= ?3 AND dl.attendanceDate<= ?4 AND e.activeStatus='AC' ORDER BY dl.attendanceDate ";

	@Query(value = ATTENDANCE_LOGS_DEPT_WISE, nativeQuery = true)
	List<Object[]> getAttendanceLogsSummaryDeptWise(Long companyId, List<Long> departmentIds, Date fDate, Date tDate);

	@Query(nativeQuery = true, value = "select al.attendanceDate,e.employeeCode, concat(e.firstName,' ',e.lastName) as 'Employee',dept.departmentName, dg.designationName,ad.cityName as 'Job Location' ,(select concat(emp.firstName,' ',emp.lastName)  from Employee emp where emp.employeeId=e.ReportingToEmployee) as ReportingTo ,sh.shiftFName,concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) as 'Shift Duration',al.mode as 'Punching Mode', al.inTime as 'Time In' ,al.outTime as 'Time Out' ,al.totalTime ,  \r\n"
			+ "			 (CASE  \r\n"
			+ "			  when((select tl.holidayId FROM TMSHolidays tl  WHERE (?2 >=tl.fromDate) AND (?2<=tl.toDate)>0)) THEN  \r\n"
			+ "			       'Holiday'  \r\n"
			+ "			       WHEN(tle.halfFullDay='H') Then 'Half Day Leave'  \r\n"
			+ "			       when(tle.halfFullDay='F') THEN 'Full Day Leave'  \r\n"
			+ "			       when((SELECT fun_check_patternIdDays_bydate(e.patternId,?2))=1)  then 'Week-Off'  \r\n"
			+ "			       WHEN( al.status='P') Then 'Present'  \r\n"
			+ "			       WHEN( al.status='P/2') Then 'Present'  \r\n" + "			       else   \r\n"
			+ "			      'Absent'  END )  as 'Attedance Status' ,  concat(TIMEDIFF(cast(al.inTime as time) ,sh.graceTime),'') as LateBy ,concat(TIMEDIFF(cast(al.outTime as time) ,sh.toTime),'') as EarlyBy,concat(TIMEDIFF(cast(al.inTime as time) ,sh.toTime),'') as 'Early before',concat(al.address,' (',al.latitude,'/ ',al.longitude,' )') as 'Location In' ,concat(al.outTimeAddress,' (',al.outTimeLatitude,'/ ',al.outTimeLangitude,' )') as 'Location Out'  \r\n"
			+ "			         \r\n" + "			      from Employee e  \r\n"
			+ "			      left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and al.attendanceDate =?2 and al.companyId=1  \r\n"
			+ "			      left OUTER join TMSLeaveEntriesDatewise tle on  tle.employeeId=e.employeeId and tle.leaveStatus='APR' and tle.companyId=?1 and tle.leaveDate=?2  \r\n"
			+ "			  left OUTER join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=?1  \r\n"
			+ "			   \r\n"
			+ "			      left OUTER join Designation dg  on  dg.designationId= e.designationId  and dg.companyId=?1  \r\n"
			+ "			      LEFT OUTER JOIN City ad on ad.cityId= e.cityId  \r\n"
			+ "			       left OUTER join TMSShift sh on al.employeeCode = e.employeeCode and sh.shiftId=e.shiftId and sh.companyId=?1  \r\n"
			+ "			  WHERE e.activeStatus='AC' group by e.employeeId")
	List<Object[]> getAttendanceReport(Long companyId, String attendanceDate);

	String LateCommersEmployeeListWithCount = "select COUNT(e.employeeId),  e.employeeCode,  e.employeeId,  dl.attendanceDate, sh.graceFrqInMonth, sh.halfDayRuleflag  from AttendanceLogs dl  "
			+ "          JOIN Employee e ON e.employeeId=dl.employeeId "
			+ "			  LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  WHERE e.companyId=?1 and dl.attendanceDate>=?2 AND dl.attendanceDate<=?3 "
			+ "			  AND e.activeStatus='AC' AND TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) > 0  GROUP BY e.employeeCode ORDER BY e.employeeCode";

	@Query(value = LateCommersEmployeeListWithCount, nativeQuery = true)
	List<Object[]> getLateCommersEmployeeListWithCount(Long companyId, Date fromDate, Date toDate);

	String LateCommersEmployeeListWithCountViaDate = "select COUNT(e.employeeId),  e.employeeCode,  e.employeeId,  dl.attendanceDate, sh.graceFrqInMonth, sh.halfDayRuleflag  from AttendanceLogs dl  "
			+ "          JOIN Employee e ON e.employeeId=dl.employeeId "
			+ "			  LEFT JOIN TMSShift sh on sh.shiftId=e.shiftId  WHERE e.companyId=?1 and dl.attendanceDate>=?2 AND dl.attendanceDate<=?3 "
			+ "			  AND e.activeStatus='AC' AND TIMEDIFF(cast(dl.inTime as time) ,sh.graceTime) > 0  GROUP BY e.employeeCode ORDER BY e.employeeCode";

	@Query(value = LateCommersEmployeeListWithCount, nativeQuery = true)
	List<Object[]> getLateCommersEmployeeListWithCountViaDate(Long companyId, Date fromDate, Date toDate);

	String CHECK_IN_FROM_PUNCH_TIME_DETAILS = "SELECT cast(pt.date as date), user.nameOfUser, e.employeeId, cast(pt.time as time), pt.flag FROM PunchTimeDetail pt\r\n"
			+ "	JOIN Users user on user.loginName=pt.tktNo \r\n"
			+ "	JOIN Employee e on e.employeeCode=user.nameOfUser\r\n"
			+ "	WHERE e.employeeId=?1 AND cast(pt.date as date)=cast(CURDATE() as date) ORDER BY cast(pt.time as time) DESC";

	@Query(value = CHECK_IN_FROM_PUNCH_TIME_DETAILS, nativeQuery = true)
	List<Object[]> getCheckInRecordsFromPuchTimeDetails(Long employeeId);

	public static final String DELETE_ATTENDANCE_VIADATE = "DELETE FROM AttendanceLogs WHERE attendanceDate = ?";

	@Modifying
	@Query(value = DELETE_ATTENDANCE_VIADATE, nativeQuery = true)
	public void deleteAttendanceVDate(Date attendanceDate);

	String ATTENDANCELOG_PREVIOUS_DAYS = "SELECT * FROM AttendanceLogs a WHERE a.attendanceDate = ?1 OR a.attendanceDate >= ?2 and a.companyId=?3 GROUP BY a.attendanceDate";

	@Query(value = ATTENDANCELOG_PREVIOUS_DAYS, nativeQuery = true)
	List<AttendanceLog> getAttendanceLogPreviousDays(Date fDate, Date priviousDate, Long companyId);

	String SHIFT_SCHEDULE_SUMMARY_DEPT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employeeName, dep.departmentName, des.designationName,c.cityName as jobLocation, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)) as reportingManager, sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) as shiftTiming\r\n"
			+ "	FROM Employee e LEFT JOIN City c on c.cityId=e.cityId LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0 LEFT JOIN TMSShift sh ON sh.shiftId=e.shiftId\r\n"
			+ " WHERE e.activeStatus='AC' AND e.companyId=?1 AND dep.departmentId IN (?2) GROUP BY e.employeeId ORDER by e.firstName";

	@Query(value = SHIFT_SCHEDULE_SUMMARY_DEPT_WISE, nativeQuery = true)
	List<Object[]> getShiftScheduleSumDepartmentWise(Long companyId, List<Long> departmentIds);

	String SHIFT_SCHEDULE_SUMMARY_EMP_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employeeName, dep.departmentName, des.designationName,c.cityName as jobLocation, IF(e.ReportingToEmployee=0, ' ' , CONCAT(emp.firstName,' ', emp.lastName)) as reportingManager, sh.shiftFName, concat(TIME_FORMAT(sh.startTime, '%h:%i:%s %p'), ' - ', TIME_FORMAT(sh.endTime, '%h:%i:%s %p')) as shiftTiming\r\n"
			+ "	FROM Employee e LEFT JOIN City c on c.cityId=e.cityId LEFT JOIN Department dep on dep.departmentId= e.departmentId LEFT JOIN Designation des on des.designationId=e.designationId LEFT JOIN Employee emp on (emp.employeeId=e.ReportingToEmployee) OR e.ReportingToEmployee=0 LEFT JOIN TMSShift sh ON sh.shiftId=e.shiftId\r\n"
			+ "	WHERE e.activeStatus='AC' AND e.companyId=?1 AND e.employeeId=?2";

	@Query(value = SHIFT_SCHEDULE_SUMMARY_EMP_WISE, nativeQuery = true)
	List<Object[]> getShiftScheduleSumEmployeeWise(Long companyId, Long employeeId);
	
	@Modifying
	@Query(value = "Update AttendanceLogs set status=?4 WHERE attendanceDate = ?3 and companyId=?1 and employeeCode=?2", nativeQuery = true)
	void updateAttendaceData(Long companyId, String employeeCode, Date date, String status);
	
	@Query("FROM AttendanceLog  WHERE companyId=?1 AND attendanceDate=?2 and employeeCode=?3")
	 AttendanceLog  getAttendanceLogData(Long companyId, Date date, String employeeCode);
}
