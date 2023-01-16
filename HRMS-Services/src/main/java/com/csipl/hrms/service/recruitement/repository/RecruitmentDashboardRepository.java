package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.recruitment.Position;

public interface RecruitmentDashboardRepository extends CrudRepository<Position, Long> {

	String POSITION_CREATED_SOURCE = "SELECT p.sourceOfPosion, COUNT(p.sourceOfPosion) FROM Position p JOIN Grades g ON g.gradesId=p.gradeId "
			+ "WHERE g.companyId=?1 AND p.positionStatus='APR' AND p.datecreated > date_sub(now(),Interval ?2 month) GROUP BY p.sourceOfPosion";

	@Query(value = POSITION_CREATED_SOURCE, nativeQuery = true)
	public List<Object[]> findPositionCreatedSource(Long companyId, Long processMonth);

	String TOTAL_POSITION_COUNT = "SELECT COUNT(p.noOfPosition) FROM Position p JOIN Grades g ON g.gradesId=p.gradeId "
			+ " WHERE g.companyId =?1 AND p.positionStatus='APR' AND p.datecreated > date_sub(now(),Interval ?2 month)	 ";

	@Query(value = TOTAL_POSITION_COUNT, nativeQuery = true)
	public Integer findTotalPositionCount(Long companyId, Long processMonth);

	String CLOSED_POSITION_COUNT = "SELECT COUNT(p.noOfPosition) FROM Position p JOIN Grades g ON g.gradesId=p.gradeId JOIN CandidateFinalEvalution cfe ON cfe.positionId=p.positionId "
			+ "WHERE g.companyId =?1 AND cfe.finalStatus='C' AND p.datecreated > date_sub(now(),Interval ?2 month) ";

	@Query(value = CLOSED_POSITION_COUNT, nativeQuery = true)
	public Integer findClosedPositionCount(Long companyId, Long processMonth);

	String POSITION_CLOSED_BY_SOURCE = "SELECT ish.sourceOfProfile, COUNT(ish.sourceOfProfile) FROM InterviewScheduler ish JOIN CandidateFinalEvalution cfe ON cfe.interviewScheduledId=ish.interviewScheduleId WHERE cfe.finalStatus='C' AND cfe.positionClosedBy > date_sub(now(),Interval 6 month) GROUP BY ish.sourceOfProfile ";

	@Query(value = POSITION_CLOSED_BY_SOURCE, nativeQuery = true)
	public List<Object[]> findPositionClosedBySource();

	String CLOSED_POSITION_COUNT_BY_MONTH = "SELECT MONTH(cf.positionClosedBy) MONTH, MONTHNAME(cf.positionClosedBy), YEAR(cf.positionClosedBy) AS Year,  COUNT(*) COUNT\r\n"
			+ "FROM CandidateFinalEvalution cf\r\n"
			+ "LEFT JOIN Position p on p.positionId=cf.positionId LEFT JOIN Grades g on g.gradesId=p.gradeId\r\n"
			+ "WHERE YEAR(cf.positionClosedBy) and cf.finalStatus='C' and g.companyId=?1  GROUP BY MONTH(cf.positionClosedBy) ";

	@Query(value = CLOSED_POSITION_COUNT_BY_MONTH, nativeQuery = true)
	public List<Object[]> findPositionClosedByMonth(Long companyId);

	String TOTAL_COUNT = "SELECT concat(e.firstName, ' ', e.lastName)as recruiterName, COUNT(p.positionId) FROM Position p JOIN Employee e ON e.employeeId=p.hiringManagerId "
			+ " WHERE e.companyId =?1 AND p.positionStatus='APR' AND p.datecreated > date_sub(now(),Interval ?2 month) GROUP BY p.hiringManagerId ";

	@Query(value = TOTAL_COUNT, nativeQuery = true)
	public List<Object[]> findTotalCount(Long companyId, Long processMonth);

	String CLOSED_COUNT = "SELECT cfe.recruiterName, COUNT(pa.positionId) FROM PositionAllocationXref pa JOIN InterviewScheduler ish ON ish.recuiterId=pa.recruiterEmployeeId JOIN CandidateFinalEvalution cfe ON cfe.interviewScheduledId=ish.interviewScheduleId JOIN Employee e ON e.employeeId=pa.recruiterEmployeeId "
			+ " WHERE e.companyId =?1 AND cfe.finalStatus='C' AND cfe.positionClosedBy > date_sub(now(),Interval ?2 month) GROUP BY pa.recruiterEmployeeId  ";

	@Query(value = CLOSED_COUNT, nativeQuery = true)
	public List<Object[]> findClosedCount(Long companyId, Long processMonth);

}
