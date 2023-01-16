package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.payrollprocess.Attendance;



public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

	
	String attendanceQuery ="   select "
			+ "attendance.presense , " // index = 0
			+ "attendance.weekoff ,"	// index = 1
			+ "attendance.seekleave , "	// index = 2
			+ " attendance.paidleave ,"	// index = 3
			+ " attendance.payDays , "	// index = 4
			+ " attendance.publicholidays,"	// index = 5
			+ " attendance.absense,"		// index = 6
			+ " emp.employeeId,"			// index = 7
			+ " emp.departmentId, "	// index = 8
			+ " emp.cityId, "					// index = 9
			+ " emp.firstName , "		// index = 10
			+ " emp.lastName , "		// index = 11
			+ " emp.employeeCode , "	// index = 12
			+ " COALESCE(empBank.accountNumber, ''),   "	// index = 13
			+ " emp.dateOfJoining,   "			// index = 14
			//+" COALESCE(dropDownList.listValue, ''),  "	// index = 15
			+ " COALESCE(empBank.bankId, ''),   "	// index = 15
			+ " emp.companyId,  "				// index = 16
			+" COALESCE(empFamily.relation, ''),"	// index = 17
			+ " empFamily.name, "				// index = 18
			+" emp.maritalStatus,"				// index = 19
			+ " address.emailId,"				// index = 20
			+ " address.mobile,  "				// index = 21
			+ " empBank.ifscCode,  "				// index = 22
			+ " emp.gender,  "				// index = 23
			+ " emp.dateOfBirth,  "				// index = 24
			+ " emp.stateId,  "				// index = 25
			+ " emp.adharNumber,  "				// index = 26
			+ " phd.isNoPFDeduction "  		// index = 27
			+" from Attendance attendance JOIN Employee emp ON attendance.employeeCode = emp.employeeCode and attendance.processMonth=?3 and emp.employeeId in(?2) and attendance.companyId = ?1 "
			+" LEFT JOIN EmployeeBank empBank ON emp.employeeId =  empBank.employeeId "
			+ " LEFT JOIN EmployeeFamily empFamily ON emp.employeeId = empFamily.employeeId " 
			+ " LEFT JOIN Address address ON emp.permanentAddressId = address.addressId "
			+ " LEFT JOIN PayStructureHd phd  ON emp.employeeId = phd.employeeId "
			+ " and  ( phd.dateEnd is null  and phd.effectiveDate is NOT null and phd.effectiveDate> NOW() ) "
			+" and ( ( accountType='SA' or accountType='SL') and empBank.activeStatus='AC'  )   group by emp.employeeId order by emp.departmentId, emp.employeeId";		
	
	/* String employDepartmentQuery ="select emp.employeeCode , emp.employeeId   "
	+ "from Department department JOIN Employee emp ON department.departmentId = emp.departmentId and department.companyId =?1 and emp.departmentId=?2 order by emp.departmentId, emp.employeeId " ; */
	
	
	@Query(value=attendanceQuery,nativeQuery = true)
	public List<Object[]> fetchEmployeeForSalary( Long companyId, StringBuilder employeeIds,  String payMonth  );
	
	//@Query(value=employDepartmentQuery,nativeQuery = true)
	@Query( " from Employee employee INNER JOIN employee.department department where  department.departmentId=?1 " )
	public List<Employee> fetchEmployeeOfDepartment(  long departmentId );
	
	
	@Query( " from PayStructure payStructure INNER JOIN payStructure.payStructureHd payStructureHd where payStructureHd.employee.employeeId=?1 and payStructureHd.effectiveDate <= ?2  " )
	public List<PayStructure> fetchPayStructureByEmployee(  long employeeId, Date today );
	
	
	
	String attendanceValidationQuery="SELECT emp.employeeId,emp.employeeCode ,dept.departmentId ,empBank.bankId ,"
			+ "payHd.payStructureHdId, emp.adharNumber as aadhar FROM Employee as emp "
			+ "left join Department as dept ON dept.departmentId= emp.departmentId "
			+ "left join EmployeeBank empBank on empBank.employeeId=emp.employeeId and "
			+ " ( empBank.accountType='SA' or  empBank.accountType='SL' ) left join PayStructureHd payHd on payHd.employeeId=emp.employeeId "
		//	+ "  left join EmployeeIdProofs et1 on et1.employeeId=emp.employeeId and et1.idTypeId='AA' "
			+ " and ( emp.adharNumber is NOT NULL or  emp.adharNumber <> '' )"
			+ "   where emp.companyId=?1 and emp.departmentId=?2 and emp.dateOfJoining <= ?3 and emp.activeStatus ='AC'";
			
			
	
	
	String attendanceValidationQueryforCompany="SELECT emp.employeeId,emp.employeeCode ,dept.departmentId ,empBank.bankId, "
			+" payHd.payStructureHdId, emp.adharNumber as aadhar FROM Employee as emp left join Department as dept ON dept.departmentId= emp.departmentId  "
			+" left join EmployeeBank empBank on empBank.employeeId=emp.employeeId and "
			+ " ( empBank.accountType='SA' or  empBank.accountType='SL' ) left join PayStructureHd payHd on payHd.employeeId=emp.employeeId  "
			+ " and  ( emp.adharNumber is NOT NULL or  emp.adharNumber <> '' ) "
			+  " where emp.companyId= ?1 and emp.dateOfJoining <= ?2 and emp.activeStatus ='AC' ";
	
	
	@Query(value=attendanceValidationQuery,nativeQuery = true)
	public List<Object[]> fetchEmployeeForValidation(long companyId, long departmentId, Date processMonth );
	
	@Query(value=attendanceValidationQueryforCompany,nativeQuery = true)
	public List<Object[]> fetchEmployeeForValidation(long companyId, Date processMonth );
	

	//@Query( "select count( attendance ) from Attendance  attendance  where attendance.department.departmentId=?1 and attendance.id.processMonth=?2" )
//	@Query( "select DISTINCT dept.departmentName from Attendance attendance  JOIN Department dept on attendance.departmentId=dept.departmentId "
//			+ "where attendance.departmentId=?1 and attendance.id.processMonth=?2" )
//	public List<String> isAttendanceUploadedForMonthAndDepartment(  long departmentId,  String processMonth  );
	
	//@Query( "select count( attendance ) from Attendance  attendance  where attendance.company.companyId=?1 and attendance.id.processMonth=?2" )
//	@Query( "select DISTINCT dept.departmentName from Attendance attendance  JOIN Department dept on attendance.departmentId=dept.departmentId where attendance.company.companyId=?1 and attendance.id.processMonth=?2" )
//	public List<String> isAttendanceUploadedForMonthAndCompany(  long companyId,  String processMonth  );
	
	//@Query( "SELECT distinct dept.departmentName  FROM Attendance att join Department dept  on att.departmentId = dept.departmentId \r\n" + 
//			" where processMonth = ?2 and att.company.companyId=?1" )
	//public List<String> findDepartmentForProcessing(  long companyId,  String processMonth  );
	
	 @Query(nativeQuery = true,value = "SELECT emp.employeeCode FROM Attendance a JOIN Employee emp ON a.employeeCode = emp.employeeCode WHERE a.companyId =?1 AND a.processMonth=?2")
	 public List<String>  empIDAttendenceList(Long companyId ,String processMonth );
	 
	 
	 @Query(nativeQuery = true,value ="SELECT employeeCode FROM Attendance WHERE companyId =?1 AND processMonth=?2")
	 public List<String>  empCodeAttendenceList(Long companyId ,String processMonth );
	 
	 String findEmployeeForAttendanceProcess = "SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp\r\n" + 
				"LEFT JOIN Department dept  ON emp.departmentId= dept.departmentId\r\n" + 
				"LEFT JOIN Designation desg ON desg.designationId =emp.designationId\r\n" + 
				"WHERE emp.activeStatus ='AC' and emp.departmentId =?2 and emp.employeeId NOT IN (select rp.employeeId FROM ReportPayOut rp WHERE rp.processMonth =?1 ) and  emp.employeeCode NOT IN  (SELECT al.employeeCode FROM Attendance al where al.processMonth = ?1)";

		@Query(value = findEmployeeForAttendanceProcess, nativeQuery = true)
		// @Query(" from ReportPayOut rp where rp.id.processMonth=?1 and rp.companyId
		// =?2 and rp.providentFundEmployee > 0 ORDER BY dateOfJoining ASC")
		List<Object[]> findEmployeeForAttendanceProcess(String processMonth, Long departmentId);

		
	String employeeforAttendanceRollback = " SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath ,dept.departmentId,"
			+ " dept.departmentName, desg.designationId,desg.designationName, a.dateCreated FROM  Attendance a  JOIN Employee emp ON a.employeeCode=emp.employeeCode  "
			+ " LEFT JOIN Department dept  ON emp.departmentId= dept.departmentId "
			+ " LEFT JOIN Designation desg ON desg.designationId =emp.designationId  WHERE emp.companyId=?1 and a.processMonth=?2  "
			+ " and  a.employeeCode NOT IN (SELECT rp.employeeCode FROM ReportPayOut rp WHERE rp.processMonth=?2 ) and emp.activeStatus ='AC' ";
		 @Query(value = employeeforAttendanceRollback, nativeQuery = true)
		public List<Object[]> getEmployeeforAttendanceRollback(Long companyId, String processMonth);

		
	String attendanceRollback = "DELETE FROM Attendance WHERE employeeCode in ?1  and  processMonth=?2 ";

	@Query(value = attendanceRollback, nativeQuery = true)
	@Modifying
	public int attendanceRollback(List<String> employeeCode, String processMonth);
	
//	String leaveValidation=" SELECT tmr.employeeId,emp.departmentId,dept.departmentName from TMSARRequest tmr \r\n" + 
//			"				 LEFT JOIN   TMSLeaveEntries tl  on tl.employeeId= tmr.employeeId  \r\n" + 
//			"				 JOIN   Employee emp on emp.employeeId= tmr.employeeId \r\n" + 
//			"				 JOIN   Department dept on dept.departmentId=emp.departmentId  \r\n" + 
//			"				 where  ((Month(tmr.fromDate)=?1 OR Month(tmr.toDate)=?1) and (tmr.status='PEN' )) \r\n" + 
//			"                 or   \r\n" + 
//			"				 ((Month(tl.fromDate)=?1 OR Month(tl.toDate)=?1) and (tl.status='PEN' )) \r\n" + 
//			"				 GROUP BY tmr.employeeId ";
	
	String leaveValidation="SELECT emp.employeeId,emp.departmentId,dept.departmentName from Employee emp   \r\n" + 
			"		 				\r\n" + 
			"			 		 	LEFT JOIN TMSARRequest tmr   on emp.employeeId= tmr.employeeId \r\n" + 
			"                         LEFT   JOIN   TMSLeaveEntries tl  on tl.employeeId= emp.employeeId \r\n" + 
			"			 				 JOIN   Department dept on dept.departmentId=emp.departmentId\r\n" + 
			"                              where  ((Month(tmr.fromDate)=?1 OR Month(tmr.toDate)=?1) and (tmr.status='PEN' )) \r\n" + 
			"		                or   \r\n" + 
			"			 				 ((Month(tl.fromDate)=?1 OR Month(tl.toDate)=?1) and (tl.status='PEN' ))  \r\n" + 
			"			 				 GROUP BY emp.employeeId";
	
	@Query(value = leaveValidation, nativeQuery = true)
	public List<Object[]> isPendingRequestLeaveAndARByMonth(int month);
	
	
//	 SELECT employeeCode,processMonth FROM Attendance WHERE companyId =1 AND processMonth='MAY-2019'
	
	
	String findLeaveEntitlement ="	SELECT concat(ltm.leaveName,'/',ltm.leaveId ) FROM TMSLeaveTypeMaster ltm where ltm.companyId=?1 "
			+ " AND ltm.leaveId!=6 And ltm.leaveId!=7 "
			+ " GROUP BY ltm.leaveId ORDER BY ltm.leaveId ASC";
	
	@Query(value = findLeaveEntitlement, nativeQuery = true)
	public String[] getTypesOfLeaves(Long companyId);
	
	
	String findTypesOfLeaveEntitlement ="	SELECT concat(ltm.leaveName,'/',ltm.leaveId ) FROM TMSLeaveTypeMaster ltm where ltm.companyId=?1 "
			+ " AND ltm.leaveId>=6 And ltm.leaveId<=7 "
			+ " GROUP BY ltm.leaveId ORDER BY ltm.leaveId ASC";
	
	@Query(value = findTypesOfLeaveEntitlement, nativeQuery = true)
		public String[] getTypesOfLeavesEntitlement(Long companyId);
	

//	String findLeaveEntitlementEmployeeWise ="SELECT a.leaveTypeMasterId,a.carryForwordLeave,(a.consumed + a.optype)as consumed,a.balanceleave,a.totalleave,a.leaveMode,a.indexDays,a.nature,a.yearlyLimit,a.startDate,a.endDate,a.dateOfJoining,\r\n" + 
//			"a.encashLimit,a.openingLeave\r\n" + 
//			"from ((SELECT\r\n" + 
//			"  tmsLeaveType.leaveTypeMasterId,\r\n" + 
//			"  SUM(IFNULL(lc.leaveCount,0)) AS carryForwordLeave,SUM(IFNULL(tmsLeaveEntries.days,0) ) AS consumed,\r\n" + 
//			"      ( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId =?2 AND STATUS = 'APR' ),0) ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId =?2 AND  STATUS = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0)\r\n" + 
//			"    )\r\n" + 
//			"  END\r\n" + 
//			")- SUM(\r\n" + 
//			"  IFNULL(tmsLeaveEntries.days, 0) \r\n" + 
//			")\r\n" + 
//			")AS balanceleave, IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?2 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ),\r\n" + 
//			"  0) as optype,\r\n" + 
//			"  (CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff WHERE  employeeId =?2 AND STATUS = 'APR' ), 0 ) ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0)\r\n" + 
//			"    )\r\n" + 
//			"  END\r\n" + 
//			") AS totalleave,\r\n" + 
//			"\r\n" + 
//			"tmsLeaveType.leaveMode,tmsLeaveType.indexDays,tmsLeaveType.nature,tmsLeaveType.yearlyLimit,lp.startDate,lp.endDate,employee.dateOfJoining,tmsLeaveType.encashLimit,\r\n" + 
//			"       IFNULL( ol.noOfOpening,0) AS openingLeave\r\n" + 
//			"  \r\n" + 
//			" FROM TMSLeavePeriod lp\r\n" + 
//			"JOIN Employee employee ON employee.employeeId =?2\r\n" + 
//			"LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId\r\n" + 
//			" JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC'\r\n" + 
//			"LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId\r\n" + 
//			"LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?2 AND ( tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN' ) AND lp.activestatus = 'AC' LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId\r\n" + 
//			"LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId =?2\r\n" + 
//			"WHERE ltm.companyId=?1 And employee.employeeId=?2 AND lp.leavePeriodId=?3 and lp.activeStatus='AC' \r\n" + 
//			"GROUP BY  tmsLeaveType.leaveTypeId ORDER BY tmsLeaveType.leaveTypeId ASC) as a )";

//	String findLeaveEntitlementByActiveSession = "SELECT  a.leaveTypeMasterId, a.carryForwordLeave, (a.consumed + a.optype) as consumed , (a.balanceleave-a.optype) as balanceleave,\r\n"
//			+ "    a.totalleave, a.leaveMode, a.indexDays, a.nature, a.yearlyLimit, \r\n"
//			+ "    a.startDate,a.endDate, a.dateOfJoining, a.encashLimit, a.openingLeave, a.leaveProbationCalculatedOn ,a.probationDays\r\n"
//			+ "	    from ((SELECT 	tmsLeaveType.leaveTypeMasterId, \r\n"
//			+ "       IFNULL(( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId),    0) AS carryForwordLeave,      "
//			+ " 	SUM( IFNULL(tmsLeaveEntries.days,0) ) AS consumed, \r\n"
//			+ "   ( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId = ?2 AND STATUS = 'APR' ),0) \r\n"
//			+ "		 ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId = ?2 AND  STATUS = 'CF'\r\n"
//			+ "		 AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0)\r\n" + "	)  END\r\n"
//			+ "		)- SUM(  IFNULL(tmsLeaveEntries.days, 0) 	)	)AS balanceleave, \r\n"
//			+ "       IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?2 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ), \r\n"
//			+ "			 0) as optype, \r\n"
//			+ "        (CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff  ec LEFT JOIN TMSLeavePeriod lp ON lp.leavePeriodId = ec.leavePeriodId WHERE lp.activeStatus ='AC' "
//			+ "			AND ec.employeeId = ?2 AND ec.STATUS = 'APR'  ) + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId = ?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0), 0 ) \r\n"
//			+ "			ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId = ?2 AND STATUS  = 'CF'\r\n"
//			+ "			AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0) \r\n" + "	) \r\n"
//			+ "	     END ) AS totalleave,\r\n"
//			+ "         tmsLeaveType.leaveMode, tmsLeaveType.indexDays, tmsLeaveType.nature,  tmsLeaveType.yearlyLimit, lp.startDate,lp.endDate, employee.dateOfJoining, \r\n"
//			+ "		 	tmsLeaveType.encashLimit, IFNULL( ol.noOfOpening,0) AS openingLeave, tmsLeaveType.leaveProbationCalculatedOn,employee.probationDays  FROM TMSLeavePeriod lp\r\n"
//			+ "			JOIN Employee employee ON employee.employeeId = ?2 \r\n"
//			+ "			LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId \r\n"
//			+ "			 JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC'\r\n"
//			+ "			LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId \r\n"
//			+ "			LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?2 AND ( tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN' )\r\n"
//			+ "			AND lp.activestatus = 'AC' LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId  \r\n"
//			+ "			LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId = ?2\r\n"
//			+ "					WHERE  ltm.companyId =?1 AND employee.employeeId=?2 AND  lp.leavePeriodId=?3  AND lp.activeStatus = 'AC' \r\n"
//			+ "					GROUP BY tmsLeaveType.leaveTypeId) as a ) order by  a.leaveTypeMasterId" ;
	
	
	String findLeaveEntitlementByActiveSession="SELECT  a.leaveTypeMasterId, a.carryForwordLeave, (a.consumed + a.optype) as consumed , (a.balanceleave-a.optype) as balanceleave,\r\n" + 
			"		 a.totalleave, a.leaveMode, a.indexDays, a.nature, a.yearlyLimit, \r\n" + 
			"	    a.startDate,a.endDate, a.dateOfJoining, a.encashLimit, a.openingLeave, a.leaveProbationCalculatedOn ,a.probationDays\r\n" + 
			"	    from ((SELECT 	tmsLeaveType.leaveTypeMasterId, \r\n" + 
			"	    IFNULL(( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId),    0) AS carryForwordLeave,   \r\n" + 
			"               \r\n" + 
			"               SUM( IFNULL(tmsLeaveEntries.days,0) ) AS consumed,          \r\n" + 
			"      ( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId =?2 AND STATUS = 'APR' ),0)  \r\n" + 
			"		 ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId =?2 AND  STATUS = 'CF' \r\n" + 
			"		 AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0) \r\n" + 
			"			    )   END )- SUM(   IFNULL(tmsLeaveEntries.days, 0) ) )AS balanceleave ,       \r\n" + 
			"		  IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?2 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ), \r\n" + 
			"			 0) as optype,          \r\n" + 
			"          (CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff  ec LEFT JOIN TMSLeavePeriod lp ON lp.leavePeriodId = ec.leavePeriodId WHERE lp.activeStatus ='AC' AND ec.employeeId =?2 AND ec.STATUS = 'APR'  ) + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ), 0), 0 ) \r\n" + 
			"	ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' \r\n" + 
			"		AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0)  \r\n" + 
			"				 )\r\n" + 
			"				  END  \r\n" + 
			"				) AS totalleave,\r\n" + 
			"               \r\n" + 
			"       tmsLeaveType.leaveMode, tmsLeaveType.indexDays, tmsLeaveType.nature,  tmsLeaveType.yearlyLimit, lp.startDate,lp.endDate, employee.dateOfJoining,\r\n" + 
			"		tmsLeaveType.encashLimit, IFNULL( ol.noOfOpening,0) AS openingLeave, tmsLeaveType.leaveProbationCalculatedOn,employee.probationDays  FROM TMSLeavePeriod lp\r\n" + 
			"	JOIN Employee employee ON employee.employeeId = ?2\r\n" + 
			"		LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId \r\n" + 
			"	 JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC'\r\n" + 
			"	LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId \r\n" + 
			"	LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?2 AND ( tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN' )\r\n" + 
			"	AND lp.activestatus = 'AC' LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId \r\n" + 
			"	LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId = ?2\r\n" + 
			"	WHERE  ltm.companyId =?1 AND employee.employeeId=?2 AND  lp.leavePeriodId=?3  AND lp.activeStatus = 'AC' \r\n" + 
			"		GROUP BY tmsLeaveType.leaveTypeId) as a ) order by  a.leaveTypeMasterId";

	@Query(value = findLeaveEntitlementByActiveSession, nativeQuery = true)
	public List<Object[]> findLeaveEntitlementByActiveSession(Long companyId, Long employeeId, Long leavePeriodId);
	
	
	
	
	
	String findLeaveEntitlementByDeactiveSession ="Select elh.leaveTypeMasterId, elh.carryforwardLeave,  elh.consumedLeave,  elh.balancedLeave, elh.totalLeave\r\n" + 
			"		FROM Employee e \r\n" + 
			"		LEFt Join EmployeeLeaveHistory elh on elh.employeeId=e.employeeId\r\n" + 
			"	    LEFT JOIN TMSLeavePeriod lp on lp.leavePeriodId=elh.leavePeriodId\r\n" + 
			"        LEFT JOIN TMSLeaveTypeMaster tmsLtm  on tmsLtm.leaveId=elh.leaveTypeMasterId\r\n" + 
			"	    where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 and lp.activeStatus='DE'\r\n" + 
			"        GROUP By elh.leaveTypeMasterId ORDER BY elh.leaveTypeMasterId";
	
	@Query(value = findLeaveEntitlementByDeactiveSession, nativeQuery = true)
	public List<Object[]> findLeaveEntitlementByDeactiveSession(Long companyId, Long employeeId, Long leavePeriodId);
	

	String findLeaveBalanceSummaryEmployeeWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName, "
			+ " des.designationName,c.cityName as jobLocation , e.dateOfJoining, CONCAT(emp.firstName,' ', emp.lastName) as reportingManager,lm.leaveSchemeName, e.employeeId, lp.activeStatus \r\n"
			+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
			+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
			+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId\r\n"
			+ "  JOIN TMSLeavePeriod lp on lp.leavePeriodId=?3   Left JOIN LeaveSchemeMaster lm on lm.leaveSchemeId=emp.leaveSchemeId  \r\n"
			+ " where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 ";

	@Query(value = findLeaveBalanceSummaryEmployeeWise, nativeQuery = true)
	public List<Object[]> findLeaveBalanceSummaryEmployeeWise(Long companyId, Long employeeId, Long leavePeriodId);

	
   String findLeaveBalanceSummaryDepartmentWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName,"
			+ " des.designationName,c.cityName as jobLocation ,  e.dateOfJoining, CONCAT(emp.firstName,' ', emp.lastName) as reportingManager, lm.leaveSchemeName, e.employeeId, lp.activeStatus \r\n"
			+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
			+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
			+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId JOIN TMSLeavePeriod lp on lp.leavePeriodId= ?3 "
			+ " Left JOIN LeaveSchemeMaster lm on lm.leaveSchemeId=emp.leaveSchemeId\r\n"
			+ " where e.companyId=?1 AND dp.departmentId in ?2 AND lp.leavePeriodId=?3 ";
	
	@Query(value = findLeaveBalanceSummaryDepartmentWise, nativeQuery = true)
	public List<Object[]> findLeaveBalanceSummaryDepartmentWise(Long companyId, List<Long> departmentId, Long leavePeriodId);
	

//		String findTypesOfEncashedLeave = "Select concat(tlm.leaveName,'/',tlm.leaveId )  \r\n"
//				+ "	 from  TMSLeaveType tm \r\n"
//				+ "  LEFT join  TMSLeaveTypeMaster tlm  ON tlm.leaveId=tm.leaveTypeMasterId  \r\n"
//				+ "	LEFT JOIN TMSLeaveEntries te ON te.leaveTypeId= tm.leaveTypeMasterId AND te.status='APR'  and te.employeeId  \r\n"
//				+ " LEFT JOIN TMSLeavePeriod tp ON tp.leavePeriodId= tm.leavePeriodId  and tp.activeStatus='DE' \r\n"
//				+ "	 LEFT JOIN Employee e ON e.employeeId \r\n"
//				+ "	  where   tm.companyId=?1  and  tp.leavePeriodId=?2 and tm.nature='EC' \r\n"
//				+ "	 GROUP by tm.leaveTypeMasterId   ORDER BY tm.leaveTypeMasterId ASC";
	String findTypesOfEncashedLeave="	SELECT concat(ltm.leaveName,'/',ltm.leaveId ) FROM TMSLeaveTypeMaster ltm where ltm.companyId=?1 "
			+ " GROUP BY ltm.leaveId ORDER BY ltm.leaveId ASC";
	
		@Query(value = findTypesOfEncashedLeave, nativeQuery = true)
		public String[] findTypesOfEncashedLeave(Long companyId);
		
		
		String findLeaveEncashedSummaryEmployeeWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName, "
				+ " des.designationName,c.cityName as jobLocation ,CONCAT(emp.firstName,' ', emp.lastName) as reportingManager, e.employeeId \r\n"
				+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
				+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
				+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId\r\n"
				+ "  JOIN TMSLeavePeriod lp on lp.leavePeriodId=?3 and lp.activeStatus='DE' \r\n"
				+ " where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 ";
		
		@Query(value = findLeaveEncashedSummaryEmployeeWise, nativeQuery = true)
		public List<Object[]> findLeaveEncashedSummaryEmployeeWise(Long companyId, Long employeeId, Long leavePeriodId);

		
//		String findClosingBalanceLeave  = "Select tm.leaveTypeMasterId,abs(coalesce(tm.yearlyLimit,0) - coalesce(SUM(te.days),0)) as closingBalance\r\n" + 
//				"		from  TMSLeaveType tm\r\n" + 
//				"    LEFT join  TMSLeaveTypeMaster tlm  ON tlm.leaveId=tm.leaveTypeMasterId  \r\n" + 
//				"	LEFT JOIN TMSLeaveEntries te ON te.leaveTypeId= tm.leaveTypeMasterId AND te.status='APR'  and te.employeeId =?2 \r\n" + 
//				"	LEFT JOIN TMSLeavePeriod tp ON tp.leavePeriodId= tm.leavePeriodId  and tp.activeStatus='DE' \r\n" + 
//				"	LEFT JOIN Employee e ON e.employeeId=?2 \r\n" + 
//				"    where   tm.companyId=?1  and e.employeeId=?2 and  tp.leavePeriodId=?3  and tm.nature='EC' \r\n" + 
//				"    GROUP by tm.leaveTypeMasterId\r\n" + 
//				"    ORDER BY tm.leaveTypeMasterId ASC";
		
//		String findClosingBalanceLeave="SELECT a.leaveTypeMasterId,(a.consumed + a.optype)as consumed,a.totalleave,a.balanceleave,a.leaveMode,a.indexDays,"
//				+ " a.nature,a.yearlyLimit,a.startDate,a.endDate,a.dateOfJoining,\r\n" + 
//				"a.encashLimit,a.openingLeave\r\n" + 
//				"from ((SELECT\r\n" + 
//				"  tmsLeaveType.leaveTypeMasterId,SUM(IFNULL(tmsLeaveEntries.days,0) ) AS consumed,\r\n" + 
//				"  (CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff WHERE \r\n" + 
//				" employeeId =?2 AND STATUS = 'APR' ), 0 ) ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?2 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0)   )\r\n" + 
//				"  END\r\n" + 
//				") AS totalleave,  ( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId =?2 AND STATUS = 'APR' ),0) ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId =?2 AND  STATUS = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ), 0)\r\n" + 
//				"    )\r\n" + 
//				"  END\r\n" + 
//				")- SUM(\r\n" + 
//				"  IFNULL(tmsLeaveEntries.days, 0) \r\n" + 
//				")\r\n" + 
//				")AS balanceleave, IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?2 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ),\r\n" + 
//				"  0) as optype,\r\n" + 
//				"tmsLeaveType.leaveMode,tmsLeaveType.indexDays,tmsLeaveType.nature,tmsLeaveType.yearlyLimit,"
//				+ "lp.startDate,lp.endDate,employee.dateOfJoining,tmsLeaveType.encashLimit,\r\n" + 
//				"       IFNULL( ol.noOfOpening,0) AS openingLeave\r\n" + 
//				"  \r\n" + 
//				" FROM TMSLeavePeriod lp\r\n" + 
//				"JOIN Employee employee ON employee.employeeId =?2\r\n" + 
//				"LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId\r\n" + 
//				" JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC'\r\n" + 
//				"LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId\r\n" + 
//				"LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?2 AND ( tmsLeaveEntries.status = 'APR' OR tmsLeaveEntries.status = 'PEN' ) AND lp.activestatus = 'DE' LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId\r\n" + 
//				"LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId =?2 \r\n" + 
//				"WHERE ltm.companyId=?1 And employee.employeeId=?2 AND lp.leavePeriodId=?3 and lp.activeStatus='DE' \r\n" + 
//				"GROUP BY  tmsLeaveType.leaveTypeId ORDER BY tmsLeaveType.leaveTypeId ASC) as a )";

		
	String findClosingBalanceLeave = "Select elh.leaveTypeMasterId, elh.totalLeave, elh.consumedLeave,  elh.balancedLeave, elh.encashLeave\r\n"
			+ "		FROM Employee e \r\n"
			+ "		LEFt Join EmployeeLeaveHistory elh on elh.employeeId=e.employeeId\r\n"
			+ "	    LEFT JOIN TMSLeavePeriod lp on lp.leavePeriodId=elh.leavePeriodId\r\n"
			+ "        LEFT JOIN TMSLeaveTypeMaster tmsLtm  on tmsLtm.leaveId=elh.leaveTypeMasterId\r\n"
			+ "	    where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 and lp.activeStatus='DE'\r\n"
			+ "        GROUP By elh.leaveTypeMasterId ORDER BY elh.leaveTypeMasterId";

	@Query(value = findClosingBalanceLeave, nativeQuery = true)
	public List<Object[]> findClosingBalanceLeave(Long companyId, Long employeeId, Long leavePeriodId);
		
		String findLeaveEncashed = " Select tm.leaveTypeMasterId,IF( abs (coalesce(tm.yearlyLimit,0) - (coalesce(SUM(te.days),0)) ) > coalesce(tm.encashLimit,0),                   coalesce(tm.encashLimit,0),\r\n"
				+ "	abs(coalesce(tm.yearlyLimit,0) - coalesce(SUM(te.days),0)) ) as encashLeave\r\n"
				+ "	from  TMSLeaveType tm\r\n"
				+ "     LEFT join  TMSLeaveTypeMaster tlm  ON tlm.leaveId=tm.leaveTypeMasterId  \r\n"
				+ "	LEFT JOIN TMSLeaveEntries te ON te.leaveTypeId= tm.leaveTypeMasterId AND te.status='APR'  and te.employeeId =?2 \r\n"
				+ "	LEFT JOIN TMSLeavePeriod tp ON tp.leavePeriodId= tm.leavePeriodId  and tp.activeStatus='DE' \r\n"
				+ "	LEFT JOIN Employee e ON e.employeeId=?2 \r\n"
				+ "    where   tm.companyId=?1  and e.employeeId=?2 and  tp.leavePeriodId=?3 and tm.nature='EC' \r\n"
				+ "    GROUP by tm.leaveTypeMasterId	ORDER BY tm.leaveTypeMasterId ASC";

		@Query(value = findLeaveEncashed, nativeQuery = true)
		public List<Object[]> findLeaveEncashed(Long companyId, Long employeeId, Long leavePeriodId);
		
		
		String findLeaveEncashSummaryDepartmentWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName,"
					+ " des.designationName,c.cityName as jobLocation ,CONCAT(emp.firstName,' ', emp.lastName) as reportingManager,e.employeeId \r\n"
					+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
					+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
					+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId JOIN TMSLeavePeriod lp on lp.leavePeriodId= ?3  and lp.activeStatus='DE' \r\n"
					+ " where e.companyId=?1 AND dp.departmentId in ?2 AND lp.leavePeriodId=?3 ";
			
			@Query(value = findLeaveEncashSummaryDepartmentWise, nativeQuery = true)
		public List<Object[]> findLeaveEncashSummaryDepartmentWise(Long companyId, List<Long> departmentId, Long leavePeriodId);
	
		String findTypesOfExpiryLeaves = "	SELECT concat(ltm.leaveName,'/',ltm.leaveId ) FROM TMSLeaveTypeMaster ltm where ltm.companyId=?1 "
				+  " GROUP BY ltm.leaveId ORDER BY ltm.leaveId ASC";
		
		@Query(value = findTypesOfExpiryLeaves, nativeQuery = true)
		public String[] findTypesOfExpiryLeaves(Long companyId);
		
		String findLeaveExpirySummaryEmployeeWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName, "
				+ " des.designationName,c.cityName as jobLocation ,CONCAT(emp.firstName,' ', emp.lastName) as reportingManager, e.employeeId \r\n"
				+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
				+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
				+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId\r\n"
				+ "  JOIN TMSLeavePeriod lp on lp.leavePeriodId=?3  \r\n"
				+ " where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 ";
		
		@Query(value = findLeaveExpirySummaryEmployeeWise, nativeQuery = true)
		public List<Object[]> findLeaveExpirySummaryEmployeeWise(Long companyId, Long employeeId, Long leavePeriodId);

		
		
		
		String findLeaveExpirySummaryDepartmentWise = "Select e.employeeCode, CONCAT(e.firstName, ' ', e.lastName) as employeeName, dp.departmentName,"
				+ " des.designationName,c.cityName as jobLocation ,CONCAT(emp.firstName,' ', emp.lastName) as reportingManager,e.employeeId \r\n"
				+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des "
				+ " ON des.designationId= e.designationId LEFT JOIN City c ON c.cityId = e.cityId\r\n"
				+ " LEFT JOIN Employee emp on e.ReportingToEmployee=emp.employeeId JOIN TMSLeavePeriod lp on lp.leavePeriodId= ?3   \r\n"
				+ " where e.companyId=?1 AND dp.departmentId in ?2 AND lp.leavePeriodId=?3 ";
		
		@Query(value = findLeaveExpirySummaryDepartmentWise, nativeQuery = true)
	    public List<Object[]> findLeaveExpirySummaryDepartmentWise(Long companyId, List<Long> departmentId,
				Long leavePeriodId);

	    
//	    String findExpiryLeave = "SELECT tmsLeaveType.leaveTypeMasterId  ,"
//	    		+ "  tmsLeaveType.yearlyLimit,SUM(tmsLeaveEntries.days),\r\n" + 
//	    		"( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7 ) THEN IFNULL(  (SELECT SUM(days)  FROM  TMSCompensantoryOff\r\n" + 
//	    		"			     WHERE  employeeId = ?2 AND STATUS   = 'APR'  ), 0   ) ELSE(  tmsLeaveType.yearlyLimit + IFNULL( (\r\n" + 
//	    		"			     SELECT   noOfOpening    FROM     EmployeeOpeningLeaveMaster\r\n" + 
//	    		"			     WHERE employeeId = ?2 AND STATUS   = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId ),  0)) END) AS totalleave, "
//	    		+ "  abs(coalesce(tmsLeaveType.yearlyLimit,0) - coalesce(SUM(tmsLeaveEntries.days),0)) as consumedBalance,"
//	    		+ " tmsLeaveType.nature,tmsLeaveType.encashLimit,\r\n" + 
//	    		"lp.startDate ,lp.endDate,e.dateOfJoining,\r\n" + 
//	    		"tmsLeaveType.leaveMode,tmsLeaveType.carryForwardLimit,tmsLeaveType.indexDays\r\n" + 
//	    		"FROM TMSLeaveType tmsLeaveType\r\n" + 
//	    		"join TMSLeaveTypeMaster ltm on ltm.leaveId=tmsLeaveType.leaveTypeMasterId  \r\n" + 
//	    		"LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId= tmsLeaveType.leaveTypeMasterId "
//	    		+ " AND tmsLeaveEntries.status='APR'  and tmsLeaveEntries.employeeId =?2 \r\n" + 
//	    		"LEFT JOIN TMSLeavePeriod lp ON lp.leavePeriodId= tmsLeaveType.leavePeriodId  \r\n" + 
//	    		"LEFT JOIN Employee e ON e.employeeId= ?2 \r\n" + 
//	    		"where   tmsLeaveType.companyId=?1  and e.employeeId=?2  and  lp.leavePeriodId=?3 and lp.activeStatus='DE'\r\n" + 
//	    		"GROUP by  tmsLeaveType.leaveTypeMasterId";

	String findExpiryLeave = "		Select elh.leaveTypeMasterId, elh.expiredLeave\r\n" + "		FROM Employee e \r\n"
			+ "		LEFt Join EmployeeLeaveHistory elh on elh.employeeId=e.employeeId\r\n"
			+ "	    LEFT JOIN TMSLeavePeriod lp on lp.leavePeriodId=elh.leavePeriodId\r\n"
			+ "        LEFT JOIN TMSLeaveTypeMaster tmsLtm  on tmsLtm.leaveId=elh.leaveTypeMasterId\r\n"
			+ "	    where e.companyId=?1 AND e.employeeId=?2 and lp.leavePeriodId=?3 and lp.activeStatus='DE'\r\n"
			+ "        GROUP By elh.leaveTypeMasterId ORDER BY elh.leaveTypeMasterId";

	@Query(value = findExpiryLeave, nativeQuery = true)
	public List<Object[]> findExpiryLeave(Long companyId, Long employeeId, Long leavePeriodId);

	
		
		


}
