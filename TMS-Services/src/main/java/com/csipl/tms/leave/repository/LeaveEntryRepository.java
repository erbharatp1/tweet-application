package com.csipl.tms.leave.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.leave.TMSLeaveEntry;

public interface LeaveEntryRepository extends CrudRepository<TMSLeaveEntry, Long> {

	String QUERY_LEAVE_BALANCE = "SELECT tmsLeaveTypeHd.leaveTypeHdId,tmsLeaveTypeHd.leaveRuleType,tmsLeaveType.leaveType,"
			+ "tmsLeaveType.yearlyLimit,tmsLeaveType.indexCol,tmsLeaveType.indexDays,tmsLeaveType.maxLeaveInMonth,"
			+ "tmsLeaveType.leaveFrequencyInMonth, sum( tmsLeaveEntries.days) , "
			+ "tmsLeaveTypeHd.effectiveStartDate, tmsLeaveTypeHd.effectiveEndDate,tmsLeaveType.leaveTypeId "
			+ "FROM TMSLeaveTypeHd tmsLeaveTypeHd "
			+ "JOIN TMSLeaveType tmsLeaveType ON tmsLeaveTypeHd.leaveTypeHdId = tmsLeaveType.leaveTypeHdId "
			+ "LEFT OUTER JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId "
			+ "AND CURRENT_DATE BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate "
			+ "AND tmsLeaveEntries.employeeId =?1 AND tmsLeaveEntries.status = 'APR' "
			+ "AND tmsLeaveTypeHd.activestatus='AC' AND tmsLeaveEntries.fromDate BETWEEN tmsLeaveTypeHd.effectiveStartDate"
			+ " AND tmsLeaveTypeHd.effectiveEndDate AND tmsLeaveEntries.toDate BETWEEN tmsLeaveTypeHd.effectiveStartDate "
			+ "AND tmsLeaveTypeHd.effectiveEndDate GROUP BY tmsLeaveType.leaveType";

	// weeklyoffPatten
	String QUERY_EMPLOYEE_DETAIL = "SELECT dept.departmentId,  desig.designationId,   emp.dateOfJoining, dept.patternId,  tmsWeekOffPattern.day FROM  Employee emp JOIN Department dept ON  dept.departmentId = emp.departmentId JOIN Designation desig ON desig.designationId = emp.designationId LEFT JOIN TMSWeekOffPattern tmsWeekOffPattern  ON    tmsWeekOffPattern.patternId = dept.patternId WHERE emp.employeeId =?1";
	String QUERY_LEAVE_BALANCE_LOGIC = "SELECT tmsLeaveTypeHd.leaveTypeHdId, tmsLeaveTypeHd.leaveRuleType,   tmsLeaveType.leaveType,  tmsLeaveType.yearlyLimit,  tmsLeaveType.indexCol,  tmsLeaveType.indexDays,   tmsLeaveType.maxLeaveInMonth,  tmsLeaveType.leaveFrequencyInMonth,  SUM(tmsLeaveEntries.days),  tmsLeaveTypeHd.effectiveStartDate,   tmsLeaveTypeHd.effectiveEndDate,emp.dateOfJoining, dept.departmentId,tmsWeekOffPattern.patternId,  tmsWeekOffPattern.day,  tmsLeaveType.isWeekOffAsPL,  tmsLeaveType.weekOffAsPLCount,  tmsLeaveType.isLeaveInProbation,    emp.probationDays FROM TMSLeaveTypeHd tmsLeaveTypeHd JOIN TMSLeaveType tmsLeaveType ON   tmsLeaveTypeHd.leaveTypeHdId = tmsLeaveType.leaveTypeHdId LEFT OUTER JOIN TMSLeaveEntries tmsLeaveEntries ON  tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND CURRENT_DATE BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.activestatus='AC' AND tmsLeaveTypeHd.effectiveEndDate AND tmsLeaveEntries.employeeId =?1 AND tmsLeaveType.leaveTypeId=?2 AND tmsLeaveEntries.status = 'APR'   AND tmsLeaveEntries.fromDate BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate AND tmsLeaveEntries.toDate BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate JOIN Employee emp ON   emp.employeeId = tmsLeaveEntries.employeeId  JOIN Department dept ON  emp.departmentId = dept.departmentId JOIN TMSWeekOffPattern tmsWeekOffPattern ON  dept.patternId = tmsWeekOffPattern.patternId";
	String QUERY_LEAVE_PENDING = "SELECT   tmsLeaveTypeHd.leaveTypeHdId,   tmsLeaveTypeHd.leaveRuleType,  tmsLeaveType.leaveType,    tmsLeaveType.yearlyLimit,   tmsLeaveType.indexCol,     tmsLeaveType.indexDays,  tmsLeaveType.maxLeaveInMonth,  tmsLeaveType.leaveFrequencyInMonth,  SUM(tmsLeaveEntries.days),   tmsLeaveTypeHd.effectiveStartDate,  tmsLeaveTypeHd.effectiveEndDate,   emp.dateOfJoining,  dept.departmentId,   tmsWeekOffPattern.patternId,  tmsWeekOffPattern.day,  tmsLeaveType.isWeekOffAsPL,     tmsLeaveType.weekOffAsPLCount,tmsLeaveType.isLeaveInProbation,emp.probationDays FROM   TMSLeaveTypeHd tmsLeaveTypeHd JOIN TMSLeaveType tmsLeaveType ON   tmsLeaveTypeHd.leaveTypeHdId = tmsLeaveType.leaveTypeHdId JOIN TMSLeaveEntries tmsLeaveEntries ON  tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND CURRENT_DATE BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate AND tmsLeaveEntries.employeeId =?1 AND tmsLeaveType.leaveTypeId =?2 AND tmsLeaveEntries.status = 'PEN' AND tmsLeaveEntries.fromDate BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate AND tmsLeaveEntries.toDate BETWEEN tmsLeaveTypeHd.effectiveStartDate AND tmsLeaveTypeHd.effectiveEndDate  JOIN Employee emp ON emp.employeeId = tmsLeaveEntries.employeeId JOIN Department dept ON  emp.departmentId = dept.departmentId JOIN TMSWeekOffPattern tmsWeekOffPattern ON  dept.patternId = tmsWeekOffPattern.patternId GROUP BY   tmsLeaveType.leaveType";
	String QUERY_APPROVED_LEAVE_IN_DURATION = "SELECT tmsLeaveEntries.status, SUM(tmsLeaveEntries.days) FROM TMSLeaveEntries tmsLeaveEntries  JOIN TMSLeaveType tmsLeaveType ON tmsLeaveEntries.leaveTypeId=tmsLeaveType.leaveTypeId WHERE tmsLeaveEntries.fromDate >=?1 AND tmsLeaveEntries.toDate <=?2 AND tmsLeaveEntries.employeeId=?3 AND  tmsLeaveEntries.leaveTypeId=?4";

	String QUERY_WEEKOFF_PATTERN = "select tmsWeekOffPattern.patternId,tmsWeekOffPattern.day from TMSWeekOffPattern tmsWeekOffPattern\r\n"
			+ "JOIN Employee employee ON employee.patternId=tmsWeekOffPattern.patternId \r\n"
			+ "WHERE employee.employeeId=?1 and employee.activeStatus='AC'";

	String QUERY_PENDING_LEAVE_IN_DURATION_BASED_ON_PK = "SELECT tmsLeaveEntries.status, SUM(tmsLeaveEntries.days) FROM TMSLeaveEntries tmsLeaveEntries  JOIN TMSLeaveType tmsLeaveType ON tmsLeaveEntries.leaveTypeId=tmsLeaveType.leaveTypeId WHERE tmsLeaveEntries.fromDate >=?1 AND tmsLeaveEntries.toDate <=?2 AND tmsLeaveEntries.employeeId=?3 AND  tmsLeaveEntries.leaveTypeId=?4 and tmsLeaveEntries.leaveId=?5 AND tmsLeaveEntries.status='PEN'";
	String QUERY_MONTLY_FREQUENCY_CHECK = "SELECT tmsLeaveEntries.status, SUM(tmsLeaveEntries.days) FROM TMSLeaveEntries tmsLeaveEntries  JOIN TMSLeaveType tmsLeaveType ON tmsLeaveEntries.leaveTypeId=tmsLeaveType.leaveTypeId WHERE tmsLeaveEntries.fromDate >=?1 AND tmsLeaveEntries.toDate <=?2 AND tmsLeaveEntries.employeeId=?3 AND  tmsLeaveEntries.leaveTypeId=?4";

	String QUERY_LEAVEENTRY = "SELECT ecd.firstName,ecd.lastName,ecd.departmentName,ecd.designationName,ecd.employeeCode,le.leaveId,le.approvalRemark,le.days,le.employeeRemark,le.fromDate,le.halfFullDay,le.halfDayFor,le.isRead,le.status,le.leaveTypeId,lt.leaveMode,le.companyId,le.toDate,le.dateCreated,le.userId,le.dateUpdate,le.actionableDate,le.employeeId FROM TMSLeaveEntries le JOIN TMSLeaveType lt ON lt.leaveTypeId=le.leaveTypeId JOIN EmpCommonDetails ecd on ecd.employeeId=le.employeeId where le.companyId=?1";

	String QUERY_LEAVEBALANCESUMMRY = "SELECT  a.leaveTypeId,a.leavePeriodId,a.leaveTypeMasterId,a.leaveMode,a.leaveName,a.yearlyLimit,a.carryForwordLeave,a.totalleave, \r\n"
			+ "						(a.consumed + a.optype) as consumed ,(a.balanceleave-a.optype) as balanceleave, \r\n"
			+ "						a.indexDays,a.maxLeaveInMonth,a.leaveFrequencyInMonth,a.isLeaveInProbation,a.carryForwardLimit,a.nature,a.notice,a.weekOffAsPLCount, \r\n"
			+ "						a.leavePeriodName,a.startDate,a.endDate, \r\n"
			+ "						a.dateOfJoining,a.encashLimit,a.openingLeave,a.leaveProbationCalculatedOn ,a.probationDays\r\n"
			+ "						from ((SELECT \r\n"
			+ "						  tmsLeaveType.leaveTypeId, tmsLeaveType.leavePeriodId,tmsLeaveType.leaveTypeMasterId,tmsLeaveType.leaveMode,ltm.leaveName, tmsLeaveType.yearlyLimit,  \r\n"
			+ "						  IFNULL(( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?1 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId),  \r\n"
			+ "						  0) AS carryForwordLeave,\r\n" + "						 ( \r\n"
			+ "						   CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff  ec LEFT JOIN TMSLeavePeriod lp ON lp.leavePeriodId = ec.leavePeriodId WHERE lp.activeStatus ='AC' AND ec.employeeId =?1 AND ec.STATUS = 'APR'  ) + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?1 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ), 0), 0 )  \r\n"
			+ "						ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?1 AND STATUS  = 'CF'\r\n"
			+ "						AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0)  \r\n"
			+ "						  )\r\n" + "						  END  \r\n"
			+ "						) AS totalleave,\r\n" + "						SUM( \r\n"
			+ "						  IFNULL(tmsLeaveEntries.days,0) \r\n"
			+ "						) AS consumed,  \r\n"
			+ "						( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId =?1 AND STATUS = 'APR' ),0) \r\n"
			+ "						 ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId =?1 AND  STATUS = 'CF' \r\n"
			+ "						 AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0) \r\n"
			+ "						    )\r\n" + "						  END \r\n"
			+ "						)- SUM( \r\n" + "						  IFNULL(tmsLeaveEntries.days, 0)  \r\n"
			+ "						) \r\n"
			+ "						)AS balanceleave, IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?1 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ), \r\n"
			+ "						 0) as optype,\r\n"
			+ "						tmsLeaveType.indexDays,tmsLeaveType.maxLeaveInMonth,tmsLeaveType.leaveFrequencyInMonth,tmsLeaveType.isLeaveInProbation,tmsLeaveType.carryForwardLimit,tmsLeaveType.nature, \r\n"
			+ "						tmsLeaveType.notice,tmsLeaveType.weekOffAsPLCount,lp.leavePeriodName,lp.startDate,lp.endDate,  \r\n"
			+ "						employee.dateOfJoining,tmsLeaveType.encashLimit,IFNULL( ol.noOfOpening,0) AS openingLeave, tmsLeaveType.leaveProbationCalculatedOn,employee.probationDays    \r\n"
			+ "					FROM TMSLeavePeriod lp \r\n"
			+ "						JOIN Employee employee ON employee.employeeId =?1 \r\n"
			+ "						LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId \r\n"
			+ "						 JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC'\r\n"
			+ "						LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId \r\n"
			+ "						LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?1 AND ( tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN' )\r\n"
			+ "						AND lp.activestatus = 'AC' LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId\r\n"
			+ "						LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId =?1\r\n"
			+ "						WHERE lp.activeStatus = 'AC' AND ltm.companyId =?2  \r\n"
			+ "						GROUP BY tmsLeaveType.leaveTypeId) as a )";

	/*
	 * String
	 * QUERY_LEAVEBALANCESUMMRY=" SELECT tmsLeaveType.leaveTypeId , tmsLeaveType.leavePeriodId , tmsLeaveType.leaveTypeMasterId , \r\n "
	 * +
	 * " tmsLeaveType.leaveMode ,ltm.leaveName ,tmsLeaveType.yearlyLimit,SUM(IFNULL(lc.leaveCount, 0)) as carryForwordLeave,(tmsLeaveType.yearlyLimit + IFNULL(lc.leaveCount, 0)) as totalleave, \r\n "
	 * + " SUM(IFNULL(tmsLeaveEntriesdatewise.day,0)) as consumed, \r\n " +
	 * " ((IFNULL(tmsLeaveType.yearlyLimit,0)+IFNULL(lc.leaveCount, 0) )-SUM(IFNULL(tmsLeaveEntriesdatewise.day,0)) ) as balanceleave ,tmsLeaveType.indexDays,tmsLeaveType.maxLeaveInMonth,tmsLeaveType.leaveFrequencyInMonth ,tmsLeaveType.isLeaveInProbation,tmsLeaveType.carryForwardLimit,tmsLeaveType.nature,tmsLeaveType.notice, \r\n "
	 * +
	 * " tmsLeaveType.weekOffAsPLCount ,tmsLeaveTypeHd.leavePeriodName ,tmsLeaveTypeHd.startDate ,tmsLeaveTypeHd.endDate , employee.dateOfJoining,tmsLeaveType.encashLimit \r\n "
	 * + " FROM TMSLeavePeriod tmsLeaveTypeHd \r\n " +
	 * " JOIN TMSLeaveType tmsLeaveType ON tmsLeaveTypeHd.leavePeriodId = tmsLeaveType.leavePeriodId  AND tmsLeaveType.activeStatus ='AC' \r\n "
	 * +
	 * " LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON   tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =1650 AND (tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN') AND tmsLeaveTypeHd.activestatus='AC' \r\n "
	 * +
	 * " LEFT JOIN TMSLeaveEntriesDatewise  tmsLeaveEntriesdatewise  ON  tmsLeaveEntriesdatewise.leaveId = tmsLeaveEntries.leaveId AND tmsLeaveEntriesdatewise.employeeId =?1 \r\n "
	 * +
	 * " AND   (tmsLeaveEntriesdatewise.leaveStatus = 'APR' OR tmsLeaveEntriesdatewise.leaveStatus = 'PEN') \r\n "
	 * + " AND tmsLeaveTypeHd.activestatus='AC' \r\n " +
	 * " Left JOIN TMSLeaveCarryForward lc on lc.leavePeriodId=tmsLeaveType.leavePeriodId  and lc.leaveTypeMasterId=tmsLeaveType.leaveTypeMasterId and  \r\n "
	 * + "      		lc.employeeId=tmsLeaveEntries.employeeId \r\n " +
	 * " Left join TMSLeaveTypeMaster ltm on ltm.leaveId=tmsLeaveType.leaveTypeMasterId \r\n "
	 * + " Left join Employee employee on employee.employeeId=?1 \r\n " +
	 * " where tmsLeaveTypeHd.activeStatus='AC' And ltm.companyId=?2  GROUP BY tmsLeaveType.leaveTypeId "
	 * ;
	 */

	String QUERY_APPROVED_PENDING_LEAVE_IN_DURATION = "SELECT IFNULL(SUM(tmsLeaveEntries.days),0) FROM TMSLeaveEntries tmsLeaveEntries WHERE tmsLeaveEntries.fromDate >=?1 AND tmsLeaveEntries.toDate <=?2 AND tmsLeaveEntries.employeeId=?3 AND tmsLeaveEntries.leaveTypeId=?4 and tmsLeaveEntries.status in('APR','PEN')";

	String QUERY_TEAMONLEAVE = "SELECT a.employeeId,a.employeeCode,\r\n"
			+ "UPPER(concat(concat(a.firstName,' '),a.lastName)) as empname\r\n"
			+ ",tle.status,tle.fromDate,tle.toDate\r\n" + ",(CASE \r\n"
			+ "  WHEN (Month(tle.fromDate)=Month(tle.toDate)) THEN DAY(tle.toDate)-DAY(tle.fromDate)\r\n"
			+ "  WHEN (Month(tle.fromDate)<Month(tle.toDate)) THEN DAY(LAST_DAY(tle.fromDate))-DAY(tle.fromDate)+DAY(tle.toDate)\r\n"
			+ "\r\n" + "  END )as 'leaves'\r\n" + "FROM Employee a\r\n"
			+ "INNER JOIN Employee b ON   b.employeeId =?1 and b.ReportingToEmployee=a.ReportingToEmployee\r\n"
			+ " join TMSLeaveEntries tle on tle.employeeId=a.employeeId and tle.status='APR' and Month(tle.fromDate)=Month(?2)\r\n"
			+ " \r\n" + " UNION\r\n" + " \r\n" + " SELECT a.employeeId,a.employeeCode,\r\n"
			+ "UPPER(concat(concat(a.firstName,' '),a.lastName)) as empname\r\n"
			+ ",tle.status,tle.fromDate,tle.toDate\r\n" + ",(CASE \r\n"
			+ "  WHEN (Month(tle.fromDate)=Month(tle.toDate)) THEN DAY(tle.toDate)-DAY(tle.fromDate)\r\n"
			+ "  WHEN (Month(tle.fromDate)<Month(tle.toDate)) THEN DAY(LAST_DAY(tle.fromDate))-DAY(tle.fromDate)+DAY(tle.toDate)\r\n"
			+ "\r\n" + "  END )as 'leaves'\r\n" + "FROM Employee a\r\n"
			+ "INNER JOIN Employee b ON   b.employeeId =?1 and b.ReportingToEmployee=a.ReportingToEmployee\r\n"
			+ " join TMSLeaveEntries tle on tle.employeeId=a.employeeId and  tle.status='PEN' and Month(tle.fromDate)=Month(?2)";

	@Query(value = QUERY_LEAVEENTRY, nativeQuery = true)
	public List<Object[]> leaveEntryList(Long companyId);

	/*
	 * @Query(" from LeaveEntry where companyId=?1 ORDER BY  leaveId  DESC") public
	 * List<LeaveEntry> leaveEntryList(Long companyId);
	 */

	@Query(" from TMSLeaveEntry where employeeId=?1 and (status='PEN' or  status='APR')  ")
	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId);

	@Query(" from TMSLeaveEntry tl where tl.employeeId=?1 and Month(tl.fromDate)=?2 ")
	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId, int processMonth);

	@Query(nativeQuery = true, value = "select * from TMSLeaveEntries where employeeId=?1 AND status='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)   ORDER BY  fromDate DESC ")
	public List<TMSLeaveEntry> getEmployeePendingLeaveEntry(Long employeeId);

	@Query(" from TMSLeaveEntry where leaveId=?1 AND status='PEN'")
	public List<TMSLeaveEntry> getEmployeePendingLeaveEntryForDaysUpdate(Long leaveId);

	// @Query(nativeQuery = true,value="select * from TMSLeaveEntries where
	// (employeeId=?1 AND status='APR') or (employeeId=?1 AND status='CEN') or
	// (employeeId=?1 AND status='REJ') and dateCreated > DATE_SUB(now(), INTERVAL 6
	// MONTH)")
	@Query(nativeQuery = true, value = "select * from TMSLeaveEntries where dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) and ((employeeId=?1 AND status='APR') or (employeeId=?1 AND status='CEN') or (employeeId=?1 AND status='REJ'))   ORDER BY  fromDate DESC ")
	public List<TMSLeaveEntry> getEmployeeApprovedLeaveEntry(Long employeeId);

	@Query(value = QUERY_LEAVE_BALANCE, nativeQuery = true)
	public List<Object[]> getEmployeeLeaveBalance(Long employeeId);

	@Query(value = QUERY_EMPLOYEE_DETAIL, nativeQuery = true)
	public List<Object[]> getEmployeeDetails(Long employeeId);

	@Query(value = QUERY_LEAVE_BALANCE_LOGIC, nativeQuery = true)
	public List<Object[]> getEmployeeLeaveLogic(Long employeeId, Long leaveTypeId);

	@Query(value = QUERY_LEAVE_PENDING, nativeQuery = true)
	public List<Object[]> getEmployeePendingLeave(Long employeeId, Long leaveTypeId);

	@Query(value = QUERY_APPROVED_PENDING_LEAVE_IN_DURATION, nativeQuery = true)
	public Object getEmployeeApprovedLeaveInDuration(Date fromDate, Date toDate, Long employeeId, Long leaveTypeId);

	@Query(value = QUERY_WEEKOFF_PATTERN, nativeQuery = true)
	public List<Object[]> getWeekOffPattern(Long employeeId);

	@Query("SELECT count(1) from TMSLeaveEntry WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR') AND leaveId !=?4 ")
	public int checkDateValidation(Long employeeId, Date fromDate, Date toDate, Long leaveId);

	@Query("SELECT count(1) from TMSLeaveEntry WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR') ")
	public int checkDateValidation1(Long employeeId, Date fromDate, Date toDate);

	@Query(value = QUERY_PENDING_LEAVE_IN_DURATION_BASED_ON_PK, nativeQuery = true)
	public List<Object[]> getEmployeePndingLeaveInDurationBasedOnPK(Date fromDate, Date toDate, Long employeeId,
			Long leaveTypeId, Long leaveId);

	@Query("SELECT COUNT(1) FROM  TMSLeaveEntry   WHERE fromDate >=?1 AND toDate <=?2 AND employeeId =?3 AND leaveTypeId = ?4 AND (status='APR' OR status='PEN')")
	public int getEmployeeMonthlyFrequencyCount(Date fromDate, Date toDate, Long employeeId, Long leaveTypeId);

	@Query(" from TMSLeaveEntry where ?1 BETWEEN fromDate AND toDate")
	public List<TMSLeaveEntry> getEmployeeLeaveEntryListByDate(Date date);

	@Query("SELECT COUNT(*) FROM TMSLeaveEntry WHERE companyId=?1 AND employeeId=?2 AND status='PEN'")
	public int leaveCount(Long companyId, Long employeeId);

	@Query(" from TMSLeaveEntry where companyId=?1 AND status='APR' or status='REJ' or status='CEN'")
	public List<TMSLeaveEntry> getAllEmployeeApprovedLeaveEntry(Long companyId);

	@Query(" SELECT count(*) from TMSLeaveEntry le JOIN Employee emp ON emp.employeeId=le.employeeId where le.companyId=?1 AND (le.status='APR' or le.status='REJ' or le.status='CEN') AND emp.activeStatus ='AC'")
	public int entitySearch(Long companyId);

	@Query("SELECT count(*) from TMSLeaveEntry le JOIN Employee emp ON emp.employeeId=le.employeeId where le.companyId=?1 AND le.status='PEN' AND emp.activeStatus ='AC'")
	public int pendingEntitySearch(Long companyId);

	@Query(value = QUERY_LEAVEBALANCESUMMRY, nativeQuery = true)
	public List<Object[]> getEmployeeLeaveBalanceSummry(Long employeeId, Long companyId);

	@Query(value = QUERY_TEAMONLEAVE, nativeQuery = true)
	public List<Object[]> getTeamLeaveOnCalender(String employeeId, String currentDate);

	@Modifying
	@Query("UPDATE TMSLeaveEntry SET status=?1 WHERE leaveId=?2")
	void actonOnPendingLeaveAttendace(String status, Long leaveId);

	@Modifying
	@Query("UPDATE TMSLeaveEntry SET days=?1 WHERE leaveId=?2")
	void updateLeaveEntryDay(BigDecimal days, Long leaveId);

	// for current month
	public static final String COUNT_MY_TEAM_REQ = "select count(*) from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId	where month(tl.fromDate)= month(now()) AND tl.status='PEN' and	 e.companyId=?1 and e.ReportingToEmployee=?2 ";

	@Query(value = COUNT_MY_TEAM_REQ, nativeQuery = true)
	public int countMyTeamPandingReq(Long ReportingToEmployee, Long companyId);

	// for all
	public static final String COUNT_ALLTIME_MY_TEAM_REQ = "select count(*) from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId	where tl.status='PEN' and	 e.companyId=?1 and e.ReportingToEmployee=?2 ";

	@Query(value = COUNT_ALLTIME_MY_TEAM_REQ, nativeQuery = true)
	public int countAllTimeMyTeamPandingReq(Long ReportingToEmployee, Long companyId);

	@Modifying
	@Query("Update TMSLeaveEntry tms SET tms.status=:status,tms.approvalId=:approvalId,tms.actionableDate=:actionableDate WHERE tms.leaveId=:leaveId")
	public void leaveStatusUpdate(@Param("leaveId") Long leaveId, @Param("status") String status,
			@Param("approvalId") Long approvalId, @Param("actionableDate") Date date);

	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and  ecd.ReportingToEmployee=?2 and le.status = 'PEN' and le.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)"))
	public List<Object[]> getLeaveApprovalsPending(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and  ecd.ReportingToEmployee=?2 and le.status != 'PEN' and le.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)"))
	public List<Object[]> getLeaveApprovalsNonPending(Long companyId, Long employeeId);

	String leaveValidation = "SELECT tmr.employeeId,emp.departmentId,dept.departmentName from TMSARRequest tmr \r\n"
			+ "				 Left JOIN   TMSLeaveEntries tl  on tl.employeeId= tmr.employeeId \r\n"
			+ "			 JOIN   Employee emp on emp.employeeId= tmr.employeeId \r\n"
			+ "				 JOIN   Department dept on dept.departmentId=emp.departmentId   \r\n"
			+ "				 where \r\n"
			+ "                    ( ( (Month(tmr.fromDate)=?1 and year(tmr.fromDate)=?2 ) or (month(tmr.toDate)=?1 AND year (tmr.toDate)=?2) )   AND tmr.status='PEN') \r\n"
			+ "                 or  \r\n"
			+ "				( ( (Month(tl.fromDate)=?1 and year(tl.fromDate)=?2 ) or (month(tl.toDate)=?1 AND year (tl.toDate)=?2) )   AND tl.status='PEN') \r\n"
			+ "				 GROUP BY tmr.employeeId";

	@Query(nativeQuery = true, value = leaveValidation)
	public List<Object[]> isPendingRequestLeaveAndARByMonth(int month, int year);

//	String LeaveRequestSummaryEmployeeWise = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name, \r\n"
//			+ "	dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName)\r\n"
//			+ "	AS Reporting_Manager,te.dateCreated,tms.leaveName,te.fromDate,te.toDate,te.days,\r\n"
//			+ "    te.employeeRemark,te.status,concat(emp.firstName,' ',emp.lastName) AS Action_taken_By,\r\n"
//			+ "	te.actionableDate,te.approvalRemark\r\n"
//			+ "    FROM Employee e LEFT JOIN  TMSLeaveEntries te ON te.employeeId=e.employeeId\r\n"
//			+ "	LEFT JOIN Department dep ON e.departmentId=dep.departmentId LEFT JOIN Designation des \r\n"
//			+ "	ON e.designationId=des.designationId LEFT JOIN City c ON e.cityId=c.cityId \r\n"
//			+ "	LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId\r\n"
//			+ "	LEFT JOIN Employee emp ON emp.employeeId = te.approvalId  Left JOIN TMSLeaveTypeMaster tms on tms.leaveId=te.leaveTypeId\r\n"
//			+ "     WHERE e.companyId=?1  AND e.employeeId= ?2 AND te.fromDate >=?3 \r\n" + "	AND te.toDate <=?4 ";
	String LeaveRequestSummaryEmployeeWise = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name, 	dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName)AS Reporting_Manager,te.dateCreated,tms.leaveName,te.fromDate,te.toDate,te.days,   te.employeeRemark,te.status,concat(emp.firstName,' ',emp.lastName) AS Action_taken_By,	te.actionableDate,te.approvalRemark    FROM Employee e \r\n"
			+ "LEFT JOIN  TMSLeaveEntries te ON te.employeeId=e.employeeId	\r\n"
			+ "LEFT JOIN Department dep ON e.departmentId=dep.departmentId \r\n"
			+ "LEFT JOIN Designation des	ON e.designationId=des.designationId \r\n"
			+ "LEFT JOIN City c ON e.cityId=c.cityId 	\r\n"
			+ "LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId	\r\n"
			+ "LEFT JOIN Employee emp ON emp.employeeId = te.approvalId  \r\n"
			+ "LEFT JOIN TMSLeaveType tly on te.leaveTypeId=tly.leaveTypeId\r\n"
			+ "Left JOIN TMSLeaveTypeMaster tms on tms.leaveId=tly.leaveTypeMasterId \r\n"
			+ "WHERE e.companyId=?1  AND e.employeeId= ?2 AND te.fromDate >=?3 	AND te.toDate <=?4";

	@Query(value = LeaveRequestSummaryEmployeeWise, nativeQuery = true)
	public List<Object[]> getLeaveRequestSummaryEmployeeWise(Long companyId, Long employeeId, Date fDate, Date tDate);

//	String LeaveRequestSummaryDepartmentWise = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name, \r\n"
//			+ "	dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName)\r\n"
//			+ "	AS Reporting_Manager,te.dateCreated,tms.leaveName,te.fromDate,te.toDate,te.days,\r\n"
//			+ "    te.employeeRemark,te.status,concat(emp.firstName,' ',emp.lastName) "
//			+ "  AS Action_taken_By,te.actionableDate,te.approvalRemark\r\n"
//			+ "    FROM Employee e LEFT JOIN  TMSLeaveEntries te ON te.employeeId=e.employeeId\r\n"
//			+ "	LEFT JOIN Department dep ON e.departmentId=dep.departmentId LEFT JOIN Designation des \r\n"
//			+ "	ON e.designationId=des.designationId LEFT JOIN City c ON e.cityId=c.cityId \r\n"
//			+ "	LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId\r\n"
//			+ "	LEFT JOIN Employee emp ON emp.employeeId = te.approvalId Left JOIN TMSLeaveTypeMaster tms on tms.leaveId=te.leaveTypeId\r\n"
//			+ "   WHERE e.companyId= ?1  AND dep.departmentId IN ?2 AND \r\n"
//			+ "   te.fromDate >=?3 AND te.toDate <=?4 ";

	String LeaveRequestSummaryDepartmentWise = "SELECT e.employeeCode,concat(e.firstName,' ',e.lastName) as Employee_Name, 	dep.departmentName,des.designationName ,c.cityName, concat(em.firstName,' ',em.lastName) 	AS Reporting_Manager,te.dateCreated,tms.leaveName,te.fromDate,te.toDate,te.days,    te.employeeRemark,te.status,concat(emp.firstName,' ',emp.lastName)   AS Action_taken_By,te.actionableDate,te.approvalRemark    FROM Employee e \r\n"
			+ "LEFT JOIN  TMSLeaveEntries te ON te.employeeId=e.employeeId \r\n"
			+ "LEFT JOIN Department dep ON e.departmentId=dep.departmentId \r\n"
			+ "LEFT JOIN Designation des 	ON e.designationId=des.designationId \r\n"
			+ "LEFT JOIN City c ON e.cityId=c.cityId  	\r\n"
			+ "LEFT JOIN Employee em on e.ReportingToEmployee=em.employeeId 	\r\n"
			+ "LEFT JOIN Employee emp ON emp.employeeId = te.approvalId \r\n"
			+ "LEFT JOIN TMSLeaveType tly on te.leaveTypeId=tly.leaveTypeId\r\n"
			+ "Left JOIN TMSLeaveTypeMaster tms on tms.leaveId=tly.leaveTypeMasterId  WHERE e.companyId= ?1  AND dep.departmentId IN ?2 AND   te.fromDate >=?3 AND te.toDate <=?4";

	@Query(value = LeaveRequestSummaryDepartmentWise, nativeQuery = true)
	public List<Object[]> getLeaveRequestSummaryDepartmentWise(Long companyId, List<Long> departmentList, Date fDate,
			Date tDate);

	// SELECT COUNT(tms.employeeId) FROM TMSLeaveEntries tms WHERE tms.employeeId =
	// 1 AND tms.companyId = 1 AND (tms.status='APR' OR tms.status='PEN')
	@Query(nativeQuery = true, value = "SELECT COUNT(tms.employeeId) FROM TMSLeaveEntries tms LEFT JOIN Employee employee  on employee.employeeId =?2 left join LeaveSchemeMaster lsm on lsm.leaveSchemeId = employee.leaveSchemeId left join TMSLeavePeriod lp on lp.leavePeriodId = lsm.leavePeriodId WHERE  lp.activeStatus = 'AC' AND tms.companyId=?1 AND tms.employeeId=?2 AND (tms.status='APR' OR tms.status='PEN' )")
	public int leaveCountAprPen(Long companyId, Long employeeId);

	String weeklyOfPattenData = "select child.id, child.patternId, child.dayName, child.positionOfDay, child.natureOfDay  from TMSWeekOffMasterPattern tmsWeekOffPattern  "
			+ "  JOIN TMSWeekOffChildPattern child on child.patternId = tmsWeekOffPattern.patternId \r\n"
			+ "	 JOIN Employee employee ON employee.patternId=tmsWeekOffPattern.patternId   \r\n"
			+ "	 WHERE employee.employeeId = ?1 and employee.activeStatus='AC' ";

	@Query(value = weeklyOfPattenData, nativeQuery = true)
	public List<Object[]> getWeekOffPatternList(Long id);

	@Query(nativeQuery = true, value = "SELECT COUNT(tms.employeeId) FROM TMSLeaveEntries tms left join TMSLeavePeriod lp on lp.leavePeriodId=?2 left join TMSLeaveType lt on lt.leaveTypeId=tms.leaveTypeId  WHERE  tms.companyId=?1 AND  tms.status='PEN'")
	public int leaveCountApr(Long companyId, Long leavePeriodId);

	/// current month pending
	// public static final String COUNT_All_EMPLOYEE_REQ="select count(*) from
	/// TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId
	/// where month(tl.fromDate)= month(now()) AND year(tl.fromDate)= year(now())
	/// AND tl.status='PEN' and e.companyId=?1 ";
	// @Query(value = COUNT_All_EMPLOYEE_REQ, nativeQuery = true)
	// public int allEmployeeLeaveCount(Long companyId);
	// ALL pending
	public static final String COUNT_All_EMPLOYEE_REQ = "select count(*) from TMSLeaveEntries tl INNER join Employee e on tl.employeeId= e.employeeId	where tl.status='PEN' and e.companyId=?1 ";

	@Query(value = COUNT_All_EMPLOYEE_REQ, nativeQuery = true)
	public int allEmployeeLeaveCount(Long companyId);

// 	@Query(nativeQuery = true,value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and le.status = 'PEN'"))
//	public List<Object[]> getAllLeaveApprovalsPending(Long companyId);

//	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and le.status != 'PEN'"))
//	public List<Object[]> getAllLeaveApprovalsNonPending(Long companyId);

	@Query(nativeQuery = true, value = "select * from TMSLeaveEntries tle where tle.companyId=?1 and tle.employeeId=?2 AND tle.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)   ORDER BY  tle.fromDate DESC ")
	public List<TMSLeaveEntry> getEmployeesAllTypeLeaveEntry(Long companyId, Long employeeId);

	@Query(nativeQuery = true, value = "SELECT ln.leaveEntriesId,  ln.employeeId FROM   LeaveNotifyEmployee ln  WHERE ln.leaveEntriesId=?1")
	public List<Object[]> getEmpNotifyLeaveEntry(Long leaveEntriesId);

	public static final String COUNT_PENDING_LEAVE_REQUEST = "SELECT COUNT(*) FROM TMSLeaveEntries WHERE companyId=?1 and status = 'PEN' ";

	@Query(value = COUNT_PENDING_LEAVE_REQUEST, nativeQuery = true)
	public int getPendingLeaveReqCount(Long longCompanyId);

	public static final String COUNT_NON_PENDING_LEAVE_REQUEST = "SELECT COUNT(*) FROM TMSLeaveEntries WHERE companyId=?1 and status != 'PEN' ";

	@Query(value = COUNT_NON_PENDING_LEAVE_REQUEST, nativeQuery = true)
	public int getNonPendingLeaveReqCount(Long longCompanyId);
	
	/*@Query(value = "select * from TMSLeaveEntries where employeeId=?1 AND status='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY \n-- #pageable\n",
	*		countQuery = "SELECT count(*) from TMSLeaveEntries where employeeId=?1 AND status='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)",
	*		nativeQuery = true)
	*/
	@Query(value = "SELECT * FROM TMSLeaveEntries  WHERE employeeId=:employeeId AND status='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY fromDate desc limit :limit offset :offset" ,nativeQuery = true)
	public List<TMSLeaveEntry> getPendingLeaveReqbyPagination(@Param("employeeId")Long employeeId, @Param("limit")int limit, @Param("offset")int offset);

	//public List<TMSLeaveEntry> getPendingLeaveReqbyPagination(Long employeeId);
	
	@Query(value = "SELECT * FROM TMSLeaveEntries  WHERE employeeId=:employeeId AND status!='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY fromDate desc limit :limit offset :offset" ,nativeQuery = true)
	public List<TMSLeaveEntry> getNonPendingLeaveReqbyPagination(@Param("employeeId")Long employeeId, @Param("limit")int limit, @Param("offset")int offset);


	
}
