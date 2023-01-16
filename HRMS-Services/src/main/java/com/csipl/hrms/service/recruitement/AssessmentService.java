package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.model.recruitment.CandidateFinalEvalution;

public interface AssessmentService {
	List<Object[]> findAllassessment(Long companyId);
	List<Object[]> findAssessment(Long companyId, Long interviewScheduleId);
	List<Object[]> getLevelList(Long companyId, Long interviewScheduleId);
	void saveAssesmentDetails(InterviewLevelDTO interviewLevelDTO, MultipartFile file);
	void finalCandidateEvolution(CandidateFinalEvalution candidateFinalEvalution, MultipartFile file);
	void updatePositionAsClosed(Long interviewScheduledId, String finalStatus);
	List<CandidateFinalEvalution> findCandidateFinalEvalutions();

}
