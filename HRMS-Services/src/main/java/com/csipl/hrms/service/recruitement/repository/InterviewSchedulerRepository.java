package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.InterviewScheduler;

@Repository("interviewSchedulerRepository")
public interface InterviewSchedulerRepository extends CrudRepository<InterviewScheduler, Long> {

	@Query(" from InterviewScheduler  where interviewScheduleId=?1 ")
	InterviewScheduler findInterviewScheduleById(Long interviewScheduleId);

	public static final String update_By_InterviewSchedule = " UPDATE InterviewScheduler i SET i.activeStatus =:activeStatus  , i.declineRemark=:declineRemark WHERE i.interviewScheduleId = :interviewScheduleId   ";

	@Modifying
	@Query(value = update_By_InterviewSchedule)
	void updateByInterviewSchedule(@Param("interviewScheduleId") Long interviewScheduleId,
			@Param("activeStatus") String activeStatus, @Param("declineRemark") String declineRemark);

	public static final String Candidate_Evolution_List = "Select c.evalutionId, c.levelId, c.interviewTime, c.interviewDate , c.interviewMode, c.status  from CandidateEvolution c";

	@Query(value = Candidate_Evolution_List, nativeQuery = true)
	List<Object[]> findCandidateEvolutionList();

//	public static final String LevelsListBasedOnScheduleId = "SELECT i.positionId, p.levelIndex FROM InterviewScheduler i LEFT JOIN PositionInterviewlevelXRef p on p.positionId=i.positionId WHERE i.interviewScheduleId=?1";
//
//	@Query(value = LevelsListBasedOnScheduleId, nativeQuery = true)
//	List<Object[]> getLevelsListBasedOnScheduleId(Long interviewScheduleId);

	// and c.status IS NOT Null
	public static final String Selected_Level_List = "Select c.levelId from CandidateEvolution c  LEFT JOIN InterviewScheduler i on i.interviewScheduleId=c.interviewScheduleId WHERE i.interviewScheduleId=?1 AND c.isInterviewScheduled='Y' ";

	@Query(value = Selected_Level_List, nativeQuery = true)
	List<Object[]> findSelectedLevelList(Long interviewScheduleId);

	public static final String AllLevelsFromCandidate = " Select i.interviewScheduleId, GROUP_CONCAT(concat(pi.levelIndex,'-', pi.levelName, '-', c.isInterviewScheduled) ORDER by pi.levelId) \r\n"
			+ "		from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId\r\n"
			+ "		LEFT JOIN Position p on p.positionId=i.positionId LEFT JOIN  Employee e on e.employeeId=i.recuiterId    \r\n"
			+ "		LEFT JOIN PositionInterviewlevelXRef pi on pi.levelId=c.levelId and   i.positionId=pi.positionId\r\n"
			+ "		WHERE  i.interviewScheduleId=?1 AND i.activeStatus='AC' group by c.interviewScheduleId order by c.interviewScheduleId desc";

	@Query(value = AllLevelsFromCandidate, nativeQuery = true)
	List<Object[]> findAllLevelsFromCandidate(Long interviewScheduleId);

	
	
	
//	public static final String LevelList = "Select  c.levelId , pi.levelIndex, pi.levelName, p.positionTitle, p.positionCode from CandidateEvolution c Left JOIN InterviewScheduler i on i.interviewScheduleId=c.interviewScheduleId\r\n"
//			+ "			LEFT JOIN Position p on p.positionId=i.positionId\r\n"
//			+ "			LEFT JOIN PositionInterviewlevelXRef pi on pi.levelId=c.levelId   WHERE c.interviewScheduleId=?1 and c.levelId=?2 ";
//
//	@Query(value = LevelList, nativeQuery = true)
//	List<Object[]> findLevelList(Long scheduleId, Long interviewLevelId);

	

}
