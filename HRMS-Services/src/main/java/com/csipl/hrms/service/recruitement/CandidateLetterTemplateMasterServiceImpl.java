package com.csipl.hrms.service.recruitement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;
import com.csipl.hrms.service.recruitement.repository.CandidateLetterTemplateMasterRepository;

@Service("candidateLetterTemplateMasterService")

public class CandidateLetterTemplateMasterServiceImpl implements CandidateLetterTemplateMasterService {

	@Autowired
	private CandidateLetterTemplateMasterRepository candidateLetterTemplateMasterRepository;

	@Override
	public CandidateLetterTemplateMaster saveInterviewScheduleTemplate(
			CandidateLetterTemplateMaster interviewScheduleTemplate) {
		// TODO Auto-generated method stub
		return candidateLetterTemplateMasterRepository.save(interviewScheduleTemplate);
	}

	@Override
	public CandidateLetterTemplateMaster getCandidateLetterByTemplateType(String templateFlag) {
		// TODO Auto-generated method stub
		return candidateLetterTemplateMasterRepository.findCandidateLetterByTemplateType(templateFlag);
	}

}
