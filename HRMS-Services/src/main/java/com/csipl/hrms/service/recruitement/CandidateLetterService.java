package com.csipl.hrms.service.recruitement;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.recruitment.CandidateLetter;

public interface CandidateLetterService {

	CandidateLetter getCandidateLetterById(Long interviewScheduleId);

	CandidateLetter saveCandidateLetter(CandidateLetter candidateLetter);

	CandidateLetter getOfferLetterId();

	void generateCandidateLetter(Long companyId, Long interviewScheduleId) throws ErrorHandling;

	CandidateLetter getCandidateOfferLetterById(Long candidateLetterId);

	void updateDeclarationStatus(Long candidateLetterId, String declerationStatus, Long interviewScheduleId);
	
	void updateSelectedAnnexure(Long candidateLetterId, String annexureStatus, Long interviewScheduleId);

}
