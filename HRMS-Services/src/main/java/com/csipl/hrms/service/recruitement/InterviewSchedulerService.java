package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;
import com.csipl.tms.dto.common.SearchDTO;

public interface InterviewSchedulerService {

	void save(InterviewScheduler interviewScheduler);

	List<Object[]> getInterviewSchedulerDetailsList(Long companyId, SearchDTO searcDto);

	InterviewScheduler findInterviewScheduleById(Long interviewScheduleId);

	void updateByInterviewScheduler(InterviewScheduler interviewScheduler);

	List<Object[]> getCandidateEvolutionList();

	List<Object[]> getSelectedLevelList(Long interviewScheduleId);

	List<PositionInterviewlevelXRef> findlevelsByPositionId(Long positionId);

	List<Object[]> getAllLevelsFromCandidate(Long interviewScheduleId);
	
	InterviewScheduler findCandidateById(Long interviewScheduleId);

	

}
