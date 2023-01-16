package com.csipl.tms.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.leave.CompensatoryOff;

public interface CompensatoryOffRepository extends CrudRepository<CompensatoryOff, Long> {

	@Query(" from CompensatoryOff where companyId =?1 ORDER BY  tmsCompensantoryOffId  DESC ")
	public List<CompensatoryOff> findAllCompensatoryOff(Long companyId);

	@Query(nativeQuery = true, value = "select * from TMSCompensantoryOff where employeeId=?1 AND status=?2 and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId  DESC")
	public List<CompensatoryOff> findMyCompOffPendingReqList(Long employeeId, String status);

	@Query(nativeQuery = true, value = "select * from TMSCompensantoryOff where employeeId=?1 AND  status NOT IN ( ?2 ) and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId  DESC")
	public List<CompensatoryOff> findMyCompOffExcludedPendingReqList(Long employeeId, String status);

	@Query(" from CompensatoryOff where companyId=?1 AND status=?2 ORDER BY  tmsCompensantoryOffId  DESC")
	public List<CompensatoryOff> findAllCompOffPendingReqList(Long companyId, String status);

	@Query(" FROM CompensatoryOff where companyId=?1 AND  status NOT IN ( ?2 )  ORDER BY  tmsCompensantoryOffId  DESC")
	public List<CompensatoryOff> findAllCompOffExcludedPendingReqList(Long companyId, String status);

	@Query(" SELECT count(*) from CompensatoryOff  where companyId=?1 AND status =?2")
	int countAllCompOffPendingReqList(long longCompanyId, String status);

	@Query(" SELECT count(*) from CompensatoryOff  where companyId=?1  AND status NOT IN ( ?2 )")
	int countAllCompOffExcludedPendingReqList(long longCompanyId, String status);

	@Query("SELECT count(1) from CompensatoryOff WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR')")
	public int checkDateValidation(Long employeeId, Date fromDate, Date toDate);

	@Query("SELECT count(1) from CompensatoryOff WHERE ( (fromDate <=?2 AND toDate >=?2) OR (fromDate <=?3 AND toDate=?3) OR (fromDate >?2 AND toDate < ?3) )AND employeeId=?1 AND (status='PEN' OR status='APR') AND tmsCompensantoryOffId NOT IN (?4)")
	public int checkDateValidation1(Long employeeId, Date fromDate, Date toDate, Long tmsCompensantoryOffId);

	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId  ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where co.companyId=?1 and  ecd.ReportingToEmployee=?2 and co.status = 'PEN'"))
	public List<Object[]> getApprovalsPendingCompOff(Long companyId, Long employeeId);
	//all emp
//	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId  ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where co.companyId=?1 and co.status = 'PEN'"))
//	public List<Object[]> getAllEmpApprovalsPendingCompOff(Long companyId);
	
	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId  ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where co.companyId=?1 and  ecd.ReportingToEmployee=?2 and co.status != 'PEN'"))
	public List<Object[]> getApprovalsNonPendingCompOff(Long companyId, Long employeeId);
	// SELECT
	// ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId
	// ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN
	// Employee ecd JOIN Department dept JOIN Designation deg on
	// ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND
	// deg.designationId=ecd.designationId where co.companyId=1 and
	// ecd.ReportingToEmployee=1605 and co.status != 'PEN'

	//team comp of req of month
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from TMSCompensantoryOff tl INNER join Employee e on tl.employeeId=e.employeeId where month(tl.fromDate)=  month(now()) AND tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2 "))
	public int compOffCountPandingMyTeam(Long employeeId, Long companyId);
	//All time compoff
	@Query(nativeQuery = true, value = ("select count(e.employeeId) from TMSCompensantoryOff tl INNER join Employee e on tl.employeeId=e.employeeId where  tl.status='PEN' and e.ReportingToEmployee=?1 and tl.companyId=?2 "))
	public int compOffAllTimeCountPandingMyTeam(Long employeeId, Long companyId);
	
	@Query("SELECT COUNT(*) FROM CompensatoryOff WHERE  employeeId=?1 and companyId=?2 and status='PEN'")
	public int compOffCountPandingMy(Long employeeId, Long companyId);

	String COMP_OFF_REQ_SUMMARY_EMP_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee, dep.departmentName, \r\n"
			+ "	des.designationName, c.cityName as jobLocation, CONCAT(em.firstName,' ', em.lastName) as reportingManager, \r\n"
			+ "	comp.dateCreated as requestedOn,  comp.fromDate, comp.toDate, comp.days, comp.remark as requesterRemark, \r\n"
			+ "	comp.status, concat(emp.firstName,' ', emp.lastName) as actionTakenBy, comp.dateUpdate as ActionTakenOn, \r\n"
			+ "	comp.approvalRemark as actionerRemark FROM Employee e LEFT JOIN TMSCompensantoryOff comp \r\n"
			+ "	on e.employeeId=comp.employeeId LEFT JOIN  Department dep ON e.departmentId=dep.departmentId \r\n"
			+ "	LEFT JOIN Designation des on e.designationId=des.designationId LEFT JOIN City c on  e.cityId=c.cityId \r\n"
			+ "	LEFT JOIN Employee emp ON emp.employeeId = comp.approvalId LEFT JOIN Employee em ON e.ReportingToEmployee=em.employeeId where e.activeStatus='AC' and "
			+ "e.companyId= ?1 AND e.employeeId =?2 and comp.fromDate >= ?3 and comp.toDate <= ?4 group by comp.tmsCompensantoryOffId";

	@Query(value = COMP_OFF_REQ_SUMMARY_EMP_WISE, nativeQuery = true)
	public List<Object[]> getCompOffReqSummaryEmpWise(Long companyId, Long employeeId, Date fDate, Date tDate);

	String COMP_OFF_REQ_SUMMARY_DEPT_WISE = "SELECT e.employeeCode, concat(e.firstName, ' ', e.lastName) as employee, dep.departmentName, \r\n"
			+ "	des.designationName, c.cityName as jobLocation, CONCAT(em.firstName,' ', em.lastName) as reportingManager, \r\n"
			+ "	comp.dateCreated as requestedOn,  comp.fromDate, comp.toDate, comp.days, comp.remark as requesterRemark, \r\n"
			+ "	comp.status, concat(emp.firstName,' ', emp.lastName) as actionTakenBy, comp.dateUpdate as ActionTakenOn, \r\n"
			+ "	comp.approvalRemark as actionerRemark FROM Employee e LEFT JOIN TMSCompensantoryOff comp \r\n"
			+ "	on e.employeeId=comp.employeeId LEFT JOIN  Department dep ON e.departmentId=dep.departmentId \r\n"
			+ "	LEFT JOIN Designation des on e.designationId=des.designationId LEFT JOIN City c on  e.cityId=c.cityId \r\n"
			+ "	LEFT JOIN Employee emp ON emp.employeeId = comp.approvalId LEFT JOIN Employee em ON e.ReportingToEmployee=em.employeeId where e.activeStatus='AC' and "
			+ "e.companyId= ?1 AND dep.departmentId in ?2 and comp.fromDate >= ?3 and comp.toDate <= ?4 group by comp.tmsCompensantoryOffId";

	@Query(value = COMP_OFF_REQ_SUMMARY_DEPT_WISE, nativeQuery = true)
	public List<Object[]> getCompOffReqSummaryDeptWise(Long companyId, List<Long> departmentIds, Date fDate,
			Date tDate);
	
	@Query(nativeQuery = true, value = "select * from TMSCompensantoryOff where employeeId=:employeeId AND status=:status and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId  DESC  limit :limit offset :offset")
	public List<CompensatoryOff> getPendingCompffOfEntitybyPagination(@Param("employeeId")Long employeeId,@Param("status")String status, @Param("limit")int limit,@Param("offset")int offset);

	@Query(nativeQuery = true, value = "select * from TMSCompensantoryOff where employeeId=:employeeId AND status!=:status and  dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  tmsCompensantoryOffId  DESC  limit :limit offset :offset")
	public List<CompensatoryOff> getNonPendingCompffOfEntitybyPagination(@Param("employeeId")Long employeeId,@Param("status")String status, @Param("limit")int limit,@Param("offset")int offset);

}
