package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;
import com.csipl.hrms.service.recruitement.repository.InterviewSchedulerPaginationRepository;
import com.csipl.hrms.service.recruitement.repository.InterviewSchedulerRepository;
import com.csipl.hrms.service.recruitement.repository.PositionInterviewlevelXRefRepository;
import com.csipl.tms.dto.common.SearchDTO;

@Service("interviewSchedulerService")
@Transactional
public class InterviewSchedulerServiceImpl implements InterviewSchedulerService {

	@Autowired
	private InterviewSchedulerRepository interviewSchedulerRepository;

	@Autowired
	private InterviewSchedulerPaginationRepository interviewSchedulerPaginationRepository;
	
	@Autowired
	private PositionInterviewlevelXRefRepository positionInterviewlevelXRefRepository;

	@Override
	@Transactional
	public void save(InterviewScheduler interviewScheduler) {
		// TODO Auto-generated method stub
		interviewSchedulerRepository.save(interviewScheduler);
	}

	@Override
	public List<Object[]> getInterviewSchedulerDetailsList(Long companyId, SearchDTO searcDto) {
		// TODO Auto-generated method stub
		return interviewSchedulerPaginationRepository.findAssignedInterviewSchedule(companyId, searcDto);
	}

	@Override
	public InterviewScheduler findInterviewScheduleById(Long interviewScheduleId) {
		// TODO Auto-generated method stub
		return interviewSchedulerRepository.findInterviewScheduleById(interviewScheduleId);
	}

	@Override
	public void updateByInterviewScheduler(final InterviewScheduler interviewScheduler) {
		// TODO Auto-generated method stub
		interviewSchedulerRepository.updateByInterviewSchedule(interviewScheduler.getInterviewScheduleId(),
				interviewScheduler.getActiveStatus(), interviewScheduler.getDeclineRemark());
	}

	@Override
	public List<Object[]> getCandidateEvolutionList() {
		// TODO Auto-generated method stub
		return interviewSchedulerRepository.findCandidateEvolutionList();
	}

	

	@Override
	public List<Object[]> getSelectedLevelList(Long interviewScheduleId) {
		// TODO Auto-generated method stub
		return interviewSchedulerRepository.findSelectedLevelList(interviewScheduleId);	}

	@Override
	public List<PositionInterviewlevelXRef> findlevelsByPositionId(Long positionId) {
		// TODO Auto-generated method stub
		return positionInterviewlevelXRefRepository.findlevelsByPositionId(positionId);
	}
	
	
	@Override
	public List<Object[]> getAllLevelsFromCandidate(Long interviewScheduleId) {
		// TODO Auto-generated method stub
		return interviewSchedulerRepository.findAllLevelsFromCandidate(interviewScheduleId);
	}

	@Override
	public InterviewScheduler findCandidateById(Long interviewScheduleId) {
		
		return interviewSchedulerRepository.findOne(interviewScheduleId);
	}

	

}
