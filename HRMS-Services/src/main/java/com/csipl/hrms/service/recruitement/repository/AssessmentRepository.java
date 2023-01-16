package com.csipl.hrms.service.recruitement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.recruitment.InterviewScheduler;

public interface AssessmentRepository extends CrudRepository<InterviewScheduler, Long> {

	@Query(value=" Select i.candidateName, i.candidateContactNo, i.candidateEmailId,concat(p.positionTitle, '(ID-', p.positionCode, ')'), \r\n" + 
			"						  concat(e.firstName, ' ', e.lastName) ,   GROUP_CONCAT(concat(pi.levelIndex,'-', pi.levelName, '-', IFNULL(c.status,'Pending')) ORDER by pi.levelId) , i.interviewScheduleId \r\n" + 
			"						  from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId  \r\n" + 
			"                            JOIN  PositionInterviewlevelXRef pi on i.positionId=pi.positionId and pi.levelId=c.levelId \r\n" + 
			"						    JOIN Position p on p.positionId=i.positionId  \r\n" + 
			"                          LEFT JOIN  Employee e on e.employeeId=i.recuiterId  "
			+ " LEFT JOIN CandidateFinalEvalution cf on cf.interviewScheduledId=i.interviewScheduleId                          \r\n" + 
			"						  WHERE e.companyId=?1 and i.activeStatus='AC' and cf.finalStatus NOT Like 'C' and  c.dateCreated > DATE_SUB(now(), INTERVAL 3 MONTH) group by c.interviewScheduleId ORDER by c.dateCreated DESC", nativeQuery = true)
	List<Object[]> findAllassessment(Long companyId);
	
	@Query(value="Select i.candidateName, i.candidateContactNo, i.candidateEmailId, p.positionTitle, \r\n" + 
			"						     GROUP_CONCAT(concat(pi.levelName, '-', c.status, '-', c.interviewMode)),i.interviewScheduleId, p.positionCode,  p.jobLocation,   g.gradesName                      \r\n" + 
			"					,p.positionType, p.positionId, concat(e.firstName, ' ', e.lastName), IFNULL(cf.finalStatus, 'Pending'), p.noofLevel , p.noOfInterviewLevel from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId  \r\n" + 
			"                          LEFT JOIN  PositionInterviewlevelXRef pi on i.positionId=pi.positionId\r\n" + 
			"						  LEFT JOIN Position p on p.positionId=i.positionId  \r\n" + 
			"                          LEFT JOIN  Employee e on e.employeeId=i.recuiterId\r\n" + 
			"                          LEFT JOIN PositionInterviewlevelXRef pix on i.positionId=pix.positionId \r\n" + 
			"                          LEFT JOIN Grades g on g.gradesId=p.gradeId\r\n" + 
			" LEFT JOIN CandidateFinalEvalution cf ON cf.interviewScheduledId=i.interviewScheduleId and cf.positionId=i.positionId " +
			"						  WHERE e.companyId=?1   and i.interviewScheduleId=?2 ", nativeQuery = true)
	List<Object[]> findAssessment(Long companyId, Long interviewScheduleId);

	@Query(value=" Select pi.levelName,c.status,c.remark,  c.interviewMode, IFNull(pi.externalInterviewerName, concat(e.firstName, ' ', e.lastName)), c.evalutionId, c.interviewScheduleId, c.levelId \r\n" + 
			"						, c.filePath, c.dateUpdated, c.fileName from InterviewScheduler i LEFT JOIN CandidateEvolution c on i.interviewScheduleId=c.interviewScheduleId  \r\n" + 
			"                          LEFT JOIN  PositionInterviewlevelXRef pi on i.positionId=pi.positionId AND c.levelId=pi.levelId \r\n" + 
			"						  LEFT JOIN Position p on p.positionId=i.positionId  \r\n" + 
			"                          LEFT JOIN  Employee e on pi.internalInterviewerId=e.employeeId \r\n" + 
			"						  WHERE e.companyId=?1 and isInterviewScheduled='Y'  and i.interviewScheduleId=?2 ", nativeQuery = true)
	List<Object[]> getLevelList(Long companyId, Long interviewScheduleId);
}
