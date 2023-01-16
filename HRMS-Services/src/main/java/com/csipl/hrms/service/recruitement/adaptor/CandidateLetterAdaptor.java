package com.csipl.hrms.service.recruitement.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.LetterDTO;
import com.csipl.hrms.dto.recruitment.CandidateLetterDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.recruitment.CandidateLetter;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class CandidateLetterAdaptor implements Adaptor<CandidateLetterDTO, CandidateLetter> {

	@Override
	public List<CandidateLetter> uiDtoToDatabaseModelList(List<CandidateLetterDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<CandidateLetterDTO> databaseModelToUiDtoList(List<CandidateLetter> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public CandidateLetter uiDtoToDatabaseModel(CandidateLetterDTO uiobj) {

		CandidateLetter candidateLetter = new CandidateLetter();
		InterviewScheduler interviewScheduler = new InterviewScheduler();
		Letter letter = new Letter();

		candidateLetter.setCandidateLetterId(uiobj.getCandidateLetterId());
		letter.setLetterId(uiobj.getLetterId());
		interviewScheduler.setInterviewScheduleId(uiobj.getInterviewScheduleId());
		candidateLetter.setActiveStatus(uiobj.getActiveStatus());
		candidateLetter.setDateCreated(uiobj.getDateCreated());
		candidateLetter.setHrStatus(uiobj.getHrStatus());
		candidateLetter.setLetter(letter);
		candidateLetter.setInterviewScheduler(interviewScheduler);
		candidateLetter.setLetterDescription(uiobj.getLetterDescription());
		candidateLetter.setUserId(uiobj.getUserId());
		candidateLetter.setCandidateStatus(uiobj.getCandidateStatus());
		candidateLetter.setDeclerationDate(uiobj.getDeclerationDate());
		candidateLetter.setDeclerationStatus(uiobj.getDeclerationStatus());
		candidateLetter.setReleaseStatus(uiobj.getReleaseStatus());
		candidateLetter.setHideAnnexure(uiobj.getHideAnnexure());
		candidateLetter.setAnnexureStatus(uiobj.getAnnexureStatus());

		return candidateLetter;
	}

	@Override
	public CandidateLetterDTO databaseModelToUiDto(CandidateLetter dbobj) {

		CandidateLetterDTO candidateLetter = new CandidateLetterDTO();
		InterviewSchedulerDTO interviewScheduler = new InterviewSchedulerDTO();
		LetterDTO letter = new LetterDTO();

		candidateLetter.setCandidateLetterId(dbobj.getCandidateLetterId());
		letter.setLetterId(letter.getLetterId());
		candidateLetter.setLetterId(dbobj.getLetter().getLetterId());
		interviewScheduler.setInterviewScheduleId(interviewScheduler.getInterviewScheduleId());
		candidateLetter.setInterviewScheduleId(dbobj.getInterviewScheduler().getInterviewScheduleId());
		candidateLetter.setActiveStatus(dbobj.getActiveStatus());
		candidateLetter.setDateCreated(dbobj.getDateCreated());
		candidateLetter.setHrStatus(dbobj.getHrStatus());
		candidateLetter.setLetterDescription(dbobj.getLetterDescription());
		candidateLetter.setUserId(dbobj.getUserId());
		candidateLetter.setCandidateStatus(dbobj.getCandidateStatus());
		candidateLetter.setDeclerationDate(dbobj.getDeclerationDate());
		candidateLetter.setDeclerationStatus(dbobj.getDeclerationStatus());
		candidateLetter.setReleaseStatus(dbobj.getReleaseStatus());
		candidateLetter.setHideAnnexure(dbobj.getHideAnnexure());
		candidateLetter.setAnnexureStatus(dbobj.getAnnexureStatus());

		return candidateLetter;
	}

	public CandidateLetterDTO databaseModelToUiLetterDto(CandidateLetter candidateLetter) {

		return null;
	}

}
