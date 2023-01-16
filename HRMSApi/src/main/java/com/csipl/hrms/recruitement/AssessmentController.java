package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.recruitment.FinalCandidateEvolutionDTO;
import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.recruitment.CandidateFinalEvalution;
import com.csipl.hrms.service.recruitement.AssessmentService;
import com.csipl.hrms.service.recruitement.adaptor.InterviewSchedulerAdaptor;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

	@Autowired
	private AssessmentService assessmentService;

	@Autowired
	private InterviewSchedulerAdaptor interviewSchedulerAdaptor;

	@RequestMapping(value = "/findAllassessment/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<InterviewSchedulerDTO> findAllassessment(@PathVariable("companyId") Long companyId)
			throws PayRollProcessException {
		List<Object[]> assessmentData = assessmentService.findAllassessment(companyId);
		List<InterviewSchedulerDTO> InterviewSchedulerDTOList = interviewSchedulerAdaptor
				.databaseModelToAssesmentList(assessmentData);
		return InterviewSchedulerDTOList;
	}

	@RequestMapping(value = "/findAssessmentDetail/{companyId}/{interviewScheduleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InterviewSchedulerDTO findAssessment(@PathVariable("companyId") Long companyId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId) throws PayRollProcessException {
		List<Object[]> assessmentDetails = assessmentService.findAssessment(companyId, interviewScheduleId);
		List<Object[]> levelList = assessmentService.getLevelList(companyId, interviewScheduleId);
		return interviewSchedulerAdaptor.databaseModelToInterviewSchedulerUiDto(assessmentDetails, levelList);
	}

	@RequestMapping(value = "/saveAssesmentDetailsFile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void saveAssesmentDetails(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") InterviewLevelDTO interviewLevelDTO, HttpServletRequest req)
			throws PayRollProcessException {
		assessmentService.saveAssesmentDetails(interviewLevelDTO, file);
	}

	@RequestMapping(value = "/saveFinalCandidateEvolution", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void finalCandidateEvolution(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") InterviewSchedulerDTO interviewSchedulerDTO, HttpServletRequest req)
			throws PayRollProcessException {
		CandidateFinalEvalution candidateFinalEvalution = interviewSchedulerAdaptor
				.databaseModelToCandidateFinalEvalution(interviewSchedulerDTO);
		assessmentService.finalCandidateEvolution(candidateFinalEvalution, file);
	}

	@PutMapping(value = "/updatePositionAsClosed")
	public void updatePositionAsClosed(@RequestBody FinalCandidateEvolutionDTO candidateEvolutionDTO,
			HttpServletRequest req) {
		logger.info("updatePositionAsClosed is calling :  " + candidateEvolutionDTO);
		assessmentService.updatePositionAsClosed(candidateEvolutionDTO.getInterviewScheduledId(),
				candidateEvolutionDTO.getFinalStatus());
		logger.info("updatePositionAsClosed is End :  " + candidateEvolutionDTO);
	}

	@RequestMapping(value = "/findCandidateFinalEvalution", method = RequestMethod.GET)
	public List<FinalCandidateEvolutionDTO> findCandidateFinalEvalution() {
		logger.info("findCandidateFinalEvalution() calling");
		List<CandidateFinalEvalution> finalEvlList = assessmentService.findCandidateFinalEvalutions();
		return interviewSchedulerAdaptor.databaseModelToFinalCandidateEvolutionDTO(finalEvlList);
	}

}
