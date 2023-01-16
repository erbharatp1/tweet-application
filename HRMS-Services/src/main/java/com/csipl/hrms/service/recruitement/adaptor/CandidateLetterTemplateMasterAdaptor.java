package com.csipl.hrms.service.recruitement.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.LetterDTO;
import com.csipl.hrms.dto.recruitment.CandidateLetterDTO;
import com.csipl.hrms.dto.recruitment.CandidateLetterTemplateMasterDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class CandidateLetterTemplateMasterAdaptor implements Adaptor<CandidateLetterTemplateMasterDTO, CandidateLetterTemplateMaster> {

	@Override
	public List<CandidateLetterTemplateMaster> uiDtoToDatabaseModelList(List<CandidateLetterTemplateMasterDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CandidateLetterTemplateMasterDTO> databaseModelToUiDtoList(List<CandidateLetterTemplateMaster> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidateLetterTemplateMaster uiDtoToDatabaseModel(CandidateLetterTemplateMasterDTO interviewScheduleTemplateDto) {
		// TODO Auto-generated method stub
		CandidateLetterTemplateMaster candidateLetterTemplateMaster = new CandidateLetterTemplateMaster();
		
//		if(interviewScheduleTemplateDto.getLatterTemplateId()!=null &&   interviewScheduleTemplateDto.getLatterTemplateId()!=0 &&  interviewScheduleTemplateDto.getTemplateFlag().equalsIgnoreCase("ISF")) 
		
		candidateLetterTemplateMaster.setLatterTemplateId(interviewScheduleTemplateDto.getLatterTemplateId());
		
		candidateLetterTemplateMaster.setBodyValue(interviewScheduleTemplateDto.getBodyValue());
		candidateLetterTemplateMaster.setFooterValue(interviewScheduleTemplateDto.getFooterValue());
		candidateLetterTemplateMaster.setGreetingFlag(interviewScheduleTemplateDto.getGreetingFlag());
		candidateLetterTemplateMaster.setCompanyId(interviewScheduleTemplateDto.getCompanyId());
		candidateLetterTemplateMaster.setPositionTitle(interviewScheduleTemplateDto.getPositionTitle());
		candidateLetterTemplateMaster.setPositionCode(interviewScheduleTemplateDto.getPositionCode());
		candidateLetterTemplateMaster.setInterviewMode(interviewScheduleTemplateDto.getInterviewMode());
		candidateLetterTemplateMaster.setInterviewDateAndTime(interviewScheduleTemplateDto.getInterviewDateAndTime());
		candidateLetterTemplateMaster.setHiringSpoc(interviewScheduleTemplateDto.getHiringSpoc());
		candidateLetterTemplateMaster.setTemplateFlag(interviewScheduleTemplateDto.getTemplateFlag());
		candidateLetterTemplateMaster.setGreetingType(interviewScheduleTemplateDto.getGreetingType());
		candidateLetterTemplateMaster.setLetterDescription(interviewScheduleTemplateDto.getBodyValue()  + ""  + interviewScheduleTemplateDto.getFooterValue()) ;
		candidateLetterTemplateMaster.setOfferedCTC(interviewScheduleTemplateDto.getOfferedCTC());
		candidateLetterTemplateMaster.setReportingTime(interviewScheduleTemplateDto.getReportingTime());
		candidateLetterTemplateMaster.setJobDescription(interviewScheduleTemplateDto.getJobDescription());
		return candidateLetterTemplateMaster;
	}

	@Override
	public CandidateLetterTemplateMasterDTO databaseModelToUiDto(CandidateLetterTemplateMaster candidateLetterTemplateMaster) {
		// TODO Auto-generated method stub
		
		CandidateLetterTemplateMasterDTO candidateLetterDto = new CandidateLetterTemplateMasterDTO();
		
		candidateLetterDto.setLatterTemplateId(candidateLetterTemplateMaster.getLatterTemplateId());
		candidateLetterDto.setBodyValue(candidateLetterTemplateMaster.getBodyValue());
		candidateLetterDto.setFooterValue(candidateLetterTemplateMaster.getFooterValue());
		candidateLetterDto.setGreetingFlag(candidateLetterTemplateMaster.getGreetingFlag());
		candidateLetterDto.setPositionTitle(candidateLetterTemplateMaster.getPositionTitle());
		candidateLetterDto.setPositionCode(candidateLetterTemplateMaster.getPositionCode());
		candidateLetterDto.setInterviewMode(candidateLetterTemplateMaster.getInterviewMode());
		candidateLetterDto.setInterviewDateAndTime(candidateLetterTemplateMaster.getInterviewDateAndTime());
		candidateLetterDto.setHiringSpoc(candidateLetterTemplateMaster.getHiringSpoc());
		candidateLetterDto.setTemplateFlag(candidateLetterTemplateMaster.getTemplateFlag());
		candidateLetterDto.setGreetingType(candidateLetterTemplateMaster.getGreetingType());
		candidateLetterDto.setLetterDescription(candidateLetterTemplateMaster.getLetterDescription());
		candidateLetterDto.setOfferedCTC(candidateLetterTemplateMaster.getOfferedCTC());
		candidateLetterDto.setReportingTime(candidateLetterTemplateMaster.getReportingTime());
		candidateLetterDto.setCompanyId(candidateLetterTemplateMaster.getCompanyId());
		candidateLetterDto.setJobDescription(candidateLetterTemplateMaster.getJobDescription());
		return candidateLetterDto;
	}

}
