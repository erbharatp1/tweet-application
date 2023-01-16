package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.PositionAllocationXref;

@Repository("positionAllocationRepository")
public interface PositionAllocationRepository extends CrudRepository<PositionAllocationXref, Long> {

	String GET_RECENT_POSITIONS = " SELECT concat(e.firstName, ' ', e.lastName)as employeeName, e.employeeCode, pa.noOfPosition, p.positionTitle, p.positionCode FROM PositionAllocationXref pa LEFT JOIN Employee e ON e.employeeId=pa.recruiterEmployeeId JOIN Position p ON p.positionId=pa.positionId "
			+ " WHERE e.companyId=?1 AND p.positionCode=?2 ORDER BY pa.positionAllocationId DESC LIMIT 3 ";

	@Query(value = GET_RECENT_POSITIONS, nativeQuery = true)
	public List<Object[]> findRecentPositions(Long companyId, String positionCode);

	String GET_NO_OF_POSITION = " SELECT SUM(pa.noOfPosition) FROM PositionAllocationXref pa JOIN Position p ON p.positionId=pa.positionId WHERE p.positionId=?1 ";

	@Query(value = GET_NO_OF_POSITION, nativeQuery = true)
	public Integer findNoOfPosition(Long positionId);

	String POSITION_BY_ID = " SELECT p.positionCode, p.positionTitle, p.noOfLevel, p.requiredExperience, p.jobLocation, p.noOfPosition, p.expectedate, p.maxBudget, p.sourceOfPosion, p.extraBudget, concat(e.firstName, ' ', e.lastName)as employeeName, g.gradesName, p.interviewType, pi.levelName, p.employeementType "
			+ "from Position p LEFT JOIN Employee e ON e.employeeId=p.hiringManagerId LEFT JOIN Grades g ON g.gradesId=p.gradeId LEFT JOIN PositionInterviewlevelXRef pi on pi.positionId=p.positionId WHERE p.positionId=?1 GROUP BY p.positionId ";

	@Query(value = POSITION_BY_ID, nativeQuery = true)
	public List<Object[]> findPositiongById(Long positionId);

	String POSITION_ALLOCATION_BY_ID = " SELECT p.positionCode, p.positionTitle, p.noOfLevel, p.requiredExperience, p.jobLocation, p.noOfPosition as noOfPosition, p.expectedate, p.maxBudget, p.sourceOfPosion, p.extraBudget, concat(e.firstName, ' ', e.lastName)as employeeName, g.gradesName, concat(ee.firstName, ' ', ee.lastName)as recruiter, "
			+ "pa.noOfPosition as assignedPosition, pa.recruiterEmployeeId, p.interviewType, pi.levelName, p.employeementType "
			+ "	from Position p LEFT JOIN Employee e ON e.employeeId=p.hiringManagerId LEFT JOIN Grades g ON g.gradesId=p.gradeId "
			+ " JOIN PositionAllocationXref pa ON pa.positionId=p.positionId LEFT JOIN Employee ee ON ee.employeeId=pa.recruiterEmployeeId LEFT JOIN PositionInterviewlevelXRef pi on pi.positionId=p.positionId WHERE p.positionId=?1 AND pa.positionAllocationId=?2 GROUP BY p.positionId ";

	@Query(value = POSITION_ALLOCATION_BY_ID, nativeQuery = true)

	public List<Object[]> findPositionAllocationById(Long positionId, Long positionAllocationId);

	String TOTAL_POSITION_COUNT = "SELECT COUNT(p.noOfPosition) FROM Position p JOIN Grades g ON g.gradesId=p.gradeId "
			+ " WHERE g.companyId =?1 AND p.positionStatus='APR' ";

	@Query(value = TOTAL_POSITION_COUNT, nativeQuery = true)
	public Integer findTotalPositionCount(Long companyId);

	String CLOSED_POSITION_COUNT = "SELECT COUNT(p.noOfPosition) FROM Position p JOIN Grades g ON g.gradesId=p.gradeId JOIN CandidateFinalEvalution cfe ON cfe.positionId=p.positionId "
			+ "WHERE g.companyId =?1 AND cfe.finalStatus='C' ";

	@Query(value = CLOSED_POSITION_COUNT, nativeQuery = true)
	public Integer findClosedPositionCount(Long companyId);

	String ASSIGN_RECRUITER_LIST = "SELECT p.positionTitle, p.positionCode, p.requiredExperience,p.jobLocation, p.positionId, p.noOfPosition FROM Position p WHERE p.positionStatus='APR' ORDER BY p.positionId DESC ";

	@Query(value = ASSIGN_RECRUITER_LIST, nativeQuery = true)
	public List<Object[]> findAssignToRecruiterList();

	String NO_OF_POSITION_LIST = "SELECT pa.positionId, SUM(pa.noOfPosition) FROM Position p JOIN PositionAllocationXref pa ON p.positionId=pa.positionId WHERE p.positionId=pa.positionId GROUP BY pa.positionId ORDER BY pa.positionId DESC ";

	@Query(value = NO_OF_POSITION_LIST, nativeQuery = true)
	public List<Object[]> findNoOfPositionList();

	String COMPANY_LOGO_PATH = " SELECT c.companyLogoPath FROM Company c WHERE c.companyId=?1 ";

	@Query(value = COMPANY_LOGO_PATH, nativeQuery = true)
	public String getCompany(Long companyId);

	@Query(value = "Select i.candidateName, i.candidateContactNo, i.candidateEmailId, p.positionTitle, \r\n"
			+ "						     GROUP_CONCAT(concat(pi.levelName, '-', c.status, '-', c.interviewMode)),i.interviewScheduleId, p.positionCode,  p.jobLocation,   g.gradesName                      \r\n"
			+ "					,p.positionType, p.positionId, concat(e.firstName, ' ', e.lastName), IFNULL(cf.finalStatus, 'Pending'), p.noofLevel , p.noOfInterviewLevel, g.gradesId, cf.id from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId  \r\n"
			+ "                          LEFT JOIN  PositionInterviewlevelXRef pi on i.positionId=pi.positionId\r\n"
			+ "						  LEFT JOIN Position p on p.positionId=i.positionId  \r\n"
			+ "                          LEFT JOIN  Employee e on e.employeeId=i.recuiterId\r\n"
			+ "                          LEFT JOIN PositionInterviewlevelXRef pix on i.positionId=pix.positionId \r\n"
			+ "                          LEFT JOIN Grades g on g.gradesId=p.gradeId\r\n"
			+ " LEFT JOIN CandidateFinalEvalution cf ON cf.interviewScheduledId=i.interviewScheduleId and cf.positionId=i.positionId "
			+ "						  WHERE e.companyId=?1   and i.interviewScheduleId=?2 ", nativeQuery = true)
	List<Object[]> findAssessment(Long companyId, Long interviewScheduleId);

	@Query(value = " Select pi.levelName,c.status,c.remark,  c.interviewMode, IFNull(pi.externalInterviewerName, concat(e.firstName, ' ', e.lastName)), c.evalutionId, c.interviewScheduleId, c.levelId \r\n"
			+ "						, c.filePath, c.dateUpdated, c.fileName from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId  \r\n"
			+ "                          LEFT JOIN  PositionInterviewlevelXRef pi on i.positionId=pi.positionId AND c.levelId=pi.levelId \r\n"
			+ "						  LEFT JOIN Position p on p.positionId=i.positionId  \r\n"
			+ "                          LEFT JOIN  Employee e on pi.internalInterviewerId=e.employeeId \r\n"
			+ "						  WHERE e.companyId=?1 and isInterviewScheduled='Y'  and i.interviewScheduleId=?2 ", nativeQuery = true)
	List<Object[]> getLevelList(Long companyId, Long interviewScheduleId);

	String CLOSED_POSITION_ALLOCATIONS = "SELECT p.positionId, cfe.recruiterName FROM Position p JOIN Grades g ON g.gradesId=p.gradeId JOIN CandidateFinalEvalution cfe ON cfe.positionId=p.positionId\r\n"
			+ "			WHERE g.companyId =?1 AND cfe.finalStatus='C' AND p.datecreated > date_sub(now(),Interval 6 month) ";

	@Query(value = CLOSED_POSITION_ALLOCATIONS, nativeQuery = true)
	public List<Object[]> findClosedAllocations(Long companyId);

}
