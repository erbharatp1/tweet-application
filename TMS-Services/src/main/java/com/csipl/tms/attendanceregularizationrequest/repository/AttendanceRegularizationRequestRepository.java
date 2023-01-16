package com.csipl.tms.attendanceregularizationrequest.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;

@Transactional
public interface AttendanceRegularizationRequestRepository
		extends CrudRepository<AttendanceRegularizationRequest, Long> {

	/*
	 * @Query("from AttendanceRegularizationRequest where companyId=?1") public
	 * List<AttendanceRegularizationRequest> getAllARRequest(Long companyId);
	 */

	@Query("from AttendanceRegularizationRequest where arID=?1")
	public AttendanceRegularizationRequest getARRequest(Long arID);

	@Query(nativeQuery = true, value = "select * from TMSARRequest where employeeId=?1 AND status ='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ")
	public List<AttendanceRegularizationRequest> getEmployeePendingARRequest(Long employeeId);

	@Query("from AttendanceRegularizationRequest where employeeId=?1  AND companyId=?2 AND status ='APR' AND ( Month(fromDate)=?3 OR Month(toDate)=?3 )")
	public List<AttendanceRegularizationRequest> getEmployeeApprovedARRequest(Long employeeId, Long companyId,
			int processMonth);

	@Query(nativeQuery = true, value = "select * from TMSARRequest where employeeId=?1 AND  status !='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)")
	public List<AttendanceRegularizationRequest> getEmployeeARRequest(Long employeeId);

	@Query(nativeQuery = true, value = "select * from TMSARRequest where employeeId=?1 ")
	public List<AttendanceRegularizationRequest> getEmployeeARRequestList(Long employeeId);
	/*
	 * @Query("FROM AttendanceRegularizationRequest WHERE companyId=?1 AND employeeId=?2"
	 * ) public List<AttendanceRegularizationRequest>
	 * getAttendanceRegularizationRequest(Long companyId, Long employeeId);
	 */

	@Query("SELECT count(1) from AttendanceRegularizationRequest WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR')")
	public int checkDateValidationOfAROnSameDate(Long employeeId, Date fromDate, Date toDate);

	@Query("SELECT count(1) from AttendanceRegularizationRequest WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR') AND arID NOT IN (?4)")
	public int checkDateValidationOfAR(Long employeeId, Date fromDate, Date toDate, Long arID);

	@Query("SELECT COUNT(*) FROM AttendanceRegularizationRequest WHERE companyId=?1 and employeeId=?2 and status='PEN'")
	public int arCount(Long companyId, Long employeeId);

	@Query("SELECT COUNT(*) FROM AttendanceRegularizationRequest WHERE companyId=?1 and status='PEN'")
	public int getPendingARCount(long longCompanyId);

	@Query("SELECT COUNT(*) FROM AttendanceRegularizationRequest WHERE companyId=?1 and status !='PEN'")
	public int getNonPendingARCount(long longCompanyId);

	// for month
	@Query(nativeQuery = true, value = ("select count(*) from TMSARRequest tl INNER join Employee e on tl.employeeId= e.employeeId where year(tl.fromDate)= year(now()) AND month(tl.fromDate)= month(now()) AND tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2"))
	public int countMyTeamPendingARCount(Long ReportingToEmployee, Long companyId);

	// all time
	@Query(nativeQuery = true, value = ("select count(*) from TMSARRequest tl INNER join Employee e on tl.employeeId= e.employeeId where tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2"))
	public int countAllTimeMyTeamPendingARCount(Long ReportingToEmployee, Long companyId);

	// for reportees
	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=?1 and  ecd.ReportingToEmployee=?2 and ar.status='PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)"))
	public List<Object[]> getAllApprovalsPending(Long companyId, Long employeeId);

	// for all EMP
	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=?1 and ar.status='PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)"))
	public List<Object[]> getAllEmpApprovalsPending(Long companyId);

	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=?1 and  ecd.ReportingToEmployee=?2 and ar.status != 'PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)"))
	public List<Object[]> getAllApprovalsNonPending(Long companyId, Long employeeId);

	@Modifying
	@Query("Update AttendanceRegularizationRequest tms SET tms.status=:status ,tms.approvalId=:approvalId ,tms.actionableDate=:actionableDate WHERE tms.arID=:arID")
	public void updateById(@Param("arID") Long arID, @Param("status") String status,
			@Param("approvalId") Long approvalId, @Param("actionableDate") Date actionableDate);

	public static final String AR_REQUEST_COUNT = "SELECT sum(tmsRequest.days) FROM TMSARRequest as tmsRequest WHERE tmsRequest.employeeId=?1 AND month(?2) = month(tmsRequest.fromDate) and (tmsRequest.status = 'PEN' or tmsRequest.status = 'APR')";

	@Query(nativeQuery = true, value = AR_REQUEST_COUNT)
	public Long arCountsOfEmployee(Long employeeId, Date fromDateMonth);

	/// for month
	@Query(nativeQuery = true, value = ("select count(*) from TMSARRequest tl INNER join Employee e on tl.employeeId= e.employeeId where year(tl.fromDate)= year(now()) AND month(tl.fromDate)= month(now()) AND tl.status='PEN' and tl.companyId=?1"))
	public int allEmployeePendingARCount(Long companyId);

	// all time
	@Query(nativeQuery = true, value = ("select count(*) from TMSARRequest tl INNER join Employee e on tl.employeeId= e.employeeId where tl.status='PEN' and tl.companyId=?1"))
	public int allTimeAllEmployeePendingARCount(Long companyId);

	@Query(nativeQuery = true, value = "select * from TMSARRequest ar where ar.employeeId=?1 and( ar.status='PEN' ||  ar.status='APR' )")
	public List<AttendanceRegularizationRequest> getPenAprARRequestList(Long employeeId);

	public static final String AR_COUNT_PENDING = " SELECT COUNT(*) FROM TMSARRequest ar WHERE ar.companyId=?1 and ar.status ='PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)  ";

	@Query(nativeQuery = true, value = AR_COUNT_PENDING)
	public int getARCountPending(Long longCompanyId);

	public static final String AR_COUNT_NON_PENDING = " SELECT COUNT(*) FROM TMSARRequest ar WHERE ar.companyId=?1 and ar.status != 'PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)  ";

	@Query(nativeQuery = true, value = AR_COUNT_NON_PENDING)
	public int getARCountNonPending(Long longCompanyId);
}
