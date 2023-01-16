package com.csipl.hrms.service.recruitement;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.common.util.StorageUtil;
import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.model.recruitment.CandidateEvolution;
import com.csipl.hrms.model.recruitment.CandidateFinalEvalution;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.recruitement.repository.AssessmentRepository;
import com.csipl.hrms.service.recruitement.repository.CandidateEvolutionRepository;
import com.csipl.hrms.service.recruitement.repository.CandidateFinalEvolutionRepository;

@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	AssessmentRepository assessmentRepository;
	@Autowired
	private MasterBookRepository masterBookRepository;
	@Autowired
	StorageService storageService;
	@Autowired
	CandidateEvolutionRepository candidateEvolutionRepository;

	@Autowired
	CandidateFinalEvolutionRepository candidateFinalEvolutionRepository;

	StorageUtil storageUtil = new StorageUtil();

	@Override
	public List<Object[]> findAllassessment(Long companyId) {
		return assessmentRepository.findAllassessment(companyId);
	}

	@Override
	public List<Object[]> findAssessment(Long companyId, Long interviewScheduleId) {
		return assessmentRepository.findAssessment(companyId, interviewScheduleId);
	}

	@Override
	public List<Object[]> getLevelList(Long companyId, Long interviewScheduleId) {
		return assessmentRepository.getLevelList(companyId, interviewScheduleId);
	}

	@Override
	public void saveAssesmentDetails(InterviewLevelDTO interviewLevelDTO, MultipartFile file) {
		CandidateEvolution candidateEvolution = candidateEvolutionRepository
				.findOne(interviewLevelDTO.getEvalutionId());
		candidateEvolution.setRemark(interviewLevelDTO.getRemarks());
		candidateEvolution.setStatus(interviewLevelDTO.getStatus());
		candidateEvolution.setDateUpdated(new Date());
		String fileName = "";
		String bookCode = "EMPNO";
		if (!file.getOriginalFilename().equals("blob")) {
			MasterBook masterBook = masterBookRepository.findMasterBook(1l, bookCode);
			BigDecimal lastNumberValue;
			lastNumberValue = masterBook.getLastNo();
			long longValue;
			longValue = lastNumberValue.longValue() + 1;
			BigDecimal newDecimalValue = new BigDecimal(longValue);
			fileName = masterBook.getPrefixBook() + newDecimalValue;
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			fileName = fileName + "." + extension;
			String path = storageService.createFilePath(HrmsGlobalConstantUtil.PROJECT_DOC);
			String dbPath = path + File.separator + fileName;
			storageService.store(file, path, fileName);
			candidateEvolution.setFileName(file.getOriginalFilename());
			masterBook.setLastNo(newDecimalValue);
			candidateEvolution.setFilePath(dbPath);
		}
		candidateEvolutionRepository.save(candidateEvolution);
	}

	@Override
	public void finalCandidateEvolution(CandidateFinalEvalution candidateFinalEvalution, MultipartFile file) {
		CandidateFinalEvalution previousDetails = candidateFinalEvolutionRepository
				.CandidateFinalEvolutionDetails(candidateFinalEvalution.getInterviewScheduledId());
		if (previousDetails != null)
			candidateFinalEvalution.setId(previousDetails.getId());
		candidateFinalEvolutionRepository.save(candidateFinalEvalution);
	}

	@Override
	public void updatePositionAsClosed(Long interviewScheduledId, String finalStatus) {
		finalStatus = "C";
		CandidateFinalEvalution previousDetails = candidateFinalEvolutionRepository
				.CandidateFinalEvolutionDetails(interviewScheduledId);
		candidateFinalEvolutionRepository.updatePositionAsClosed(previousDetails.getId(), finalStatus, new Date());
	}

	public List<CandidateFinalEvalution> findCandidateFinalEvalutions() {
		return candidateFinalEvolutionRepository.findAllCandidateEvalution();

	}

}
