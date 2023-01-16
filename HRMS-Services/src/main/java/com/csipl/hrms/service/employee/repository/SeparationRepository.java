package com.csipl.hrms.service.employee.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.employee.Separation;

public interface SeparationRepository extends CrudRepository<Separation, Long> {
	@Query(" from Separation where employeeId=?1 And companyId=?2 ORDER BY  separationId  DESC")
	public List<Separation> getSeparationList(Long employeeId, Long companyId);

	@Query(" from Separation where companyId=?1  ORDER BY  separationId  DESC")
	public List<Separation> getAllseparationList(Long companyId);

	@Query("select count(1) from Separation WHERE employeeId=?1  and (status=?2 OR status=?3)")
	public Long checkSeparationForRequest(Long employeeId, String status1, String status2);

	@Query(" from Separation where companyId=?1  AND dateCreated=?2 AND status='P' ORDER BY  separationId  DESC")
	public List<Separation> getAllseparationPendingList(Long companyId, Date currentDate);

	@Query("select count(*) from Separation where companyId=?1 AND  endDate>=Now() AND (status='A' or status='P')")
	public Long getNoticePeriodCount(Long companyId);

	@Query("SELECT COUNT(*) FROM Separation WHERE companyId=?1 AND MONTH(NOW())=MONTH(endDate)")
	public int seperationCount(Long companyId);

	@Query("from Separation where employeeId=?1 AND (status=?2 OR status=?3) ORDER BY  separationId  DESC")
	public List<Separation> employeeCancelledResignReqList(Long employeeId, String status, String status2);

	@Query(" from Separation where employeeId=?1 AND (status=?2 OR status=?3 )ORDER BY  separationId  DESC")
	public Separation employeePendingResignReq(Long employeeId, String status, String approvedStatus2);

	@Modifying
	@Query(" UPDATE Separation SET status =?2, description=?3 WHERE separationId=?1")
	public void updateRequestStatus(Long separationId, String status, String description);

	@Query(" from Separation where companyId=?1 AND status=?2 ORDER BY  separationId  DESC")
	public List<Separation> findAllSeparationPendingReqList(Long companyId, String status);

	@Query(value="SELECT concat(e.firstName, ' ',e.lastName, '(', e.employeeCode, ')'), e.employeeId, e.employeeCode,  d.departmentName, des.designationName, g.gradesName, e.empType, e.endDate, e.gradesId, c.cityName as jobLocation  FROM Employee e join Separation s on e.employeeId=s.employeeId  LEFT JOIN City c on c.cityId=e.cityId    JOIN Department d on d.departmentId=e.departmentId   JOIN Designation des on des.designationId=e.designationId join Grades g on g.gradesId=e.gradesId WHERE e.companyId=?1 and s.status='APR' and (s.isPositionClosed='N' OR s.isPositionClosed is null)", nativeQuery = true)
	public List<Object[]> findAllSeparationAproveList(Long companyId);
	
	@Query("   FROM Separation WHERE companyId=?1 AND  status NOT IN ( ?2 )  ORDER BY  separationId  DESC")
	public List<Separation> findAllSeparationExcludedPendingReqList(Long companyId, String status);

//	@Query(" SELECT count(*) from Separation  where companyId=?1 AND status =?2  ")

	String PENDING_SEPARATION_COUNT = " SELECT count(sp.separationId) from Separation sp JOIN Employee emp ON emp.employeeId=sp.employeeId where sp.companyId=?1 and emp.activeStatus= 'AC' AND sp.status =?2 ";

	@Query(value = PENDING_SEPARATION_COUNT, nativeQuery = true)
	public int getPendingSeparationCount(Long companyId, String strStatus);
//
//	@Query(" SELECT count(*) from Separation  where companyId=?1 AND  status NOT IN ( ?2 ) ")
// 	public int getExcludedPendingSeparationCount(Long companyId, String strStatus);

//	String PENDING_SEPARATION_COUNT = "SELECT  COUNT(sp.separationId) FROM Separation sp JOIN Employee emp ON emp.employeeId=sp.employeeId JOIN Designation desig ON desig.designationId=emp.designationId "
//			+ "where sp.companyId=?1 and emp.activeStatus = 'AC' and sp.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) AND sp.status =?2";
//
//	@Query(value = PENDING_SEPARATION_COUNT, nativeQuery = true)
//	public int getPendingSeparationCount(Long companyId, String strStatus);

	String NON_PENDING_SEPARATION_COUNT = "SELECT COUNT(sp.separationId) FROM Separation sp JOIN Employee emp ON emp.employeeId=sp.employeeId JOIN Designation desig ON desig.designationId=emp.designationId "
			+ "where sp.companyId=?1 and emp.activeStatus = 'AC' and sp.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) AND sp.status NOT IN ( ?2 )";

	@Query(value = NON_PENDING_SEPARATION_COUNT, nativeQuery = true)
	public int getExcludedPendingSeparationCount(Long companyId, String strStatus);

	@Query("from Separation where employeeId=?1 AND status=?2")
	public Separation getSeparationInfo(Long employeeId, String status);

	// month
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from Separation tl INNER join Employee e on tl.employeeId= e.employeeId where year(tl.endDate)= year(now()) AND month(tl.endDate)= month(now()) AND tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2"))
	public int seprationPandingTeamCount(Long employeeId, Long companyId);

	// all time
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from Separation tl INNER join Employee e on tl.employeeId= e.employeeId where tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2"))
	public int seprationAllTimePandingTeamCount(Long employeeId, Long companyId);

	@Query("select count(*) from Separation where employeeId=?1 and companyId=?1 AND status='PEN' ORDER BY  separationId  DESC")
	public int seprationPandingMyCount(Long employeeId, Long companyId);

	@Modifying
	@Query(nativeQuery = true, value = ("UPDATE Employee e SET e.ReportingToEmployee =?2  WHERE e.ReportingToEmployee=?1 "))
	public int changeReportingTo(Long employeeId, Long reportingToEmployee);

	// month
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from Separation tl INNER join Employee e on tl.employeeId= e.employeeId where year(tl.endDate)= year(now()) AND month(tl.endDate)= month(now()) AND tl.status='PEN' and tl.companyId=?1 AND e.activeStatus='AC' "))
	public int allEmployeeSeprationPanding(Long companyId);

	// all time
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from Separation tl INNER join Employee e on tl.employeeId= e.employeeId where tl.status='PEN' and tl.companyId=?1 AND e.activeStatus='AC' "))
	public int allTimeAllEmployeeSeprationPanding(Long companyId);

	@Modifying
	@Query(nativeQuery = true, value = ("UPDATE Separation s SET s.isPositionClosed ='Y'  WHERE s.companyId=?1 and s.employeeId=?2"))
	public void updateisPositionClosedFlag(Long companyID, Long employeeId);
}
