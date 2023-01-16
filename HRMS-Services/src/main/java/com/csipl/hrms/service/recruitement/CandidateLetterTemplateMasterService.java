package com.csipl.hrms.service.recruitement;

import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;


public interface CandidateLetterTemplateMasterService {

	public CandidateLetterTemplateMaster saveInterviewScheduleTemplate(CandidateLetterTemplateMaster interviewScheduleTemplate);

	public CandidateLetterTemplateMaster getCandidateLetterByTemplateType(String templateFlag);

}
