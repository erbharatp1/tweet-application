package com.csipl.tms.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;

public interface LeaveEntriesDatewiseRepository extends CrudRepository<TMSLeaveEntriesDatewise, Long> {
	@Modifying
	@Query("UPDATE TMSLeaveEntriesDatewise SET leaveStatus =?1 WHERE leaveId=?2")
	void updateLeaveDatewiseEntry(String status, Long leaveId);

	@Modifying
	@Query("UPDATE TMSLeaveEntriesDatewise SET leaveStatus =?1 WHERE  employeeId=?3 and leaveFromToSandwitchDate=?2 and leaveStatus='PEN' ")
	int updateLeaveDateSandwizewiseEntry(String status, Date leaveFromDate, Long employeeId);

	@Query(" from TMSLeaveEntriesDatewise tld where tld.employeeId=?1 and Month(tld.leaveDate)=?2  and leaveStatus='APR' ")
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntry(Long employeeId, int processMonth);

	@Query(" from TMSLeaveEntriesDatewise tld where tld.employeeId=?1  and  leaveStatus='PEN'")
	public List<TMSLeaveEntriesDatewise> getEmployeePendingLeaveEntryDateWise(Long employeeId);

	@Query(" from TMSLeaveEntriesDatewise tld where tld.leaveFromToSandwitchDate=?1  and  leaveStatus='PEN'")
	// public List<TMSLeaveEntriesDatewise>
	// getEmployeePendingLeaveEntryDateWise(Date leaveFromToSandwitchDate);

	List<TMSLeaveEntriesDatewise> findAllByLeaveFromToSandwitchDate(Date leaveFromToSandwitchDate);

//	String EmployeeOnLeave = " select   date_format(leaveDate,'%b') ,round((count(leaveDate)*100)) as leavecount,fun_emp_count_overview(MONTH( leaveDate))  As empCount  from TMSLeaveEntriesDatewise\r\n"
//			+ " where year(leaveDate)=Year(now()) AND leaveStatus='APR' and companyId=?1 AND leaveDate>DATE_SUB(now(), INTERVAL 6 MONTH) GROUP by MONTH(leaveDate)";
//	String EmployeeOnLeave =  "select   date_format(leaveDate,'%b') , count( DISTINCT employeeId) as employeeCount  from TMSLeaveEntriesDatewise  " + 
//  "			  where   leaveStatus='APR' and companyId=?1 and month(now()) not in(Month(leaveDate)) AND leaveDate BETWEEN    DATE_SUB(now(), INTERVAL 6 MONTH) and now() GROUP by MONTH(leaveDate) ORDER BY  leaveDate DESC LIMIT 6";
	
	String EmployeeOnLeave =  "select   date_format(leaveDate,'%b') , count( DISTINCT employeeId) as employeeCount  from TMSLeaveEntriesDatewise      \r\n" + 
			" 		  where   leaveStatus='APR' and companyId= ?1 and leaveDate<(SELECT LAST_DAY(CURDATE() - INTERVAL 1 MONTH) AS first_day) AND leaveDate BETWEEN    DATE_SUB(now(), INTERVAL 6 MONTH) and now() GROUP by MONTH(leaveDate) ORDER BY  leaveDate DESC LIMIT 6";
	@Query(value = EmployeeOnLeave, nativeQuery = true)
	public List<Object[]> getEmployeeOnLeavePercentage(Long companyId);

//	String lastSixProcessMonth = "select    SUBSTRING_INDEX(pc.processMonth,'-',1) as months  from PayrollControl pc   \r\n" + 
//			"			  where    (Month(DATE_SUB(now(),INTERVAL  6 MONTH ))) >=month(str_to_date(SUBSTRING_INDEX(pc.processMonth,'-',1),'%b')) OR (Month(DATE_SUB(now(),INTERVAL  6 MONTH ))) <=month(str_to_date(SUBSTRING_INDEX(pc.processMonth,'-',1),'%b'))  ORDER BY  pc.controlId DESC LIMIT 6";

	String lastSixProcessMonth = "select    SUBSTRING_INDEX(pc.processMonth,'-',1) as months  from PayrollControl pc   \r\n" + 
			"			  where    pc.controlId<(SELECT p.controlId FROM PayrollControl p WHERE p.processMonth=(SELECT concat( date_format(now(),'%b'),'-', Year(now())))) GROUP BY pc.processMonth ORDER BY  pc.controlId DESC LIMIT 6";
	
	@Query(value = lastSixProcessMonth, nativeQuery = true)
	public List<String> getlastSixProcessMonth();
	
	
	String activeEmployeeByDate="SELECT COUNT(em.employeeId) from Employee em WHERE em.activeStatus='AC' and em.dateOfJoining <=(SELECT LAST_DAY(CURDATE() - INTERVAL ?1 MONTH))";
	@Query(value = activeEmployeeByDate, nativeQuery = true)
	public Long getActiveEmployeeByDate(Long interval);
	
	String formerEmployeeByDate="SELECT COUNT(em.employeeId) from Employee em WHERE em.activeStatus='DE' and em.endDate BETWEEN  (SELECT date_add(date_add(LAST_DAY((SELECT LAST_DAY(CURDATE() - INTERVAL 1 MONTH))),interval 1 DAY),interval - ?1 MONTH)) and (SELECT LAST_DAY(CURDATE() - INTERVAL ?1 MONTH))";
	@Query(value = formerEmployeeByDate, nativeQuery = true)
	public Long getFormerEmployeeByDate(Long interval);
	
//	String leaveTakenByLeaveType = "SELECT leaveNature,count(leaveNature) FROM TMSLeaveEntriesDatewise where companyId=?1 and leaveStatus='APR' and  year(leaveDate)=Year(now())   GROUP by leaveNature";
//			@Query(value = leaveTakenByLeaveType, nativeQuery = true)
//			public List<Object[]> getLeaveTakenByLeaveType(Long companyId);

	String leaveTakenByLeaveType = "SELECT ltm.leaveName, COUNT(led.leaveId) FROM TMSLeaveEntriesDatewise led LEFT JOIN TMSLeaveEntries le ON le.leaveId=led.leaveId \r\n"
			+ "LEFT JOIN TMSLeaveType lt ON lt.leaveTypeId=le.leaveTypeId \r\n"
			+ "LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId=lt.leaveTypeMasterId\r\n"
			+ "where led.companyId=?1 and led.leaveStatus='APR' and year(led.leaveDate)=Year(now()) group by ltm.leaveId";

	@Query(value = leaveTakenByLeaveType, nativeQuery = true)
	public List<Object[]> getLeaveTakenByLeaveType(Long companyId);

	String EmployeeOnAbsent = "CALL  	Pro_employee_On_absent_percentage( :p_comp_id) ";

	@Query(value = EmployeeOnAbsent, nativeQuery = true)
	public List<Object[]> getEmployeeOnAbsent(@Param(value = "p_comp_id") Long p_comp_id);

	String EmployeeFrequentLeaveTaker = "SELECT count(leaveId),e.firstName FROM TMSLeaveEntriesDatewise  tm\r\n"
			+ "LEFT JOIN Employee e ON tm.employeeId=e.employeeId\r\n"
			+ "where  tm.leaveDate  BETWEEN NOW() - INTERVAL 30 DAY AND NOW() AND e.activeStatus='AC' AND tm.companyId=?1 \r\n"
			+ "GROUP BY tm.employeeId";

	@Query(value = EmployeeFrequentLeaveTaker, nativeQuery = true)
	public List<Object[]> getEmployeeFrequentLeaveTaker(Long companyId);

	String lastSixMonthEmployeeCount= "SELECT  COUNT(e.employeeId),(SELECT COUNT(e.employeeId) from Employee e WHERE e.activeStatus='AC') AS activeEmployeCount , concat( date_format(e.endDate,'%b'),'-', Year(e.endDate))   FROM Employee e WHERE e.activeStatus='DE' and month(e.endDate) !=month(now()) and e.companyId=?1  and e.endDate   BETWEEN DATE_SUB(now(),INTERVAL  6 MONTH ) and now() GROUP by month(e.endDate) ORDER BY e.endDate DESC LIMIT 6";
	
	@Query(value = lastSixMonthEmployeeCount, nativeQuery = true)
	public List<Object[]> getLastSixMonthEmployeeCount(Long companyId);
	
	@Query(" from TMSLeaveEntriesDatewise tld where tld.employeeId=?1")
	public List<TMSLeaveEntriesDatewise> getAllLeaveEntryDateWise(Long employeeId);

	@Query(nativeQuery = true, value = " SELECT tld.id,tld.leaveId,tld.employeeId,tld.leaveDate,tld.leaveStatus,tld.leaveNature,tld.leaveFromToSandwitchDate,tld.halfFullDay,tld.day,tld.companyId from TMSLeaveEntriesDatewise tld JOIN Employee emp on tld.employeeId=emp.employeeId where emp.ReportingToEmployee=?1 AND  Month(tld.leaveDate)=?2  and leaveStatus='APR'")
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntryNew(Long employeeId, int processMonth);

	@Query("FROM TMSLeaveEntriesDatewise ld WHERE ld.employeeId=?1 and ld.leaveDate>=?2 and ld.leaveDate<=?3 and ld.leaveStatus='APR' ")
	List<TMSLeaveEntriesDatewise> getEmployeeLeaveDate(Long employeeId, Date startDate, Date endDate);
	

	@Query(" from TMSLeaveEntriesDatewise tld where tld.employeeId=?1 and (tld.leaveStatus='PEN' or tld.leaveStatus='APR'  )")
	public List<TMSLeaveEntriesDatewise> getPenAprLeaveEntryDateWise(Long employeeId);
}
