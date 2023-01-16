package com.csipl.hrms.recruitement;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.employee.EmployeeLetterDTO;
import com.csipl.hrms.dto.recruitment.CandidateLetterDTO;
import com.csipl.hrms.dto.recruitment.CandidateLetterTemplateMasterDTO;
import com.csipl.hrms.model.recruitment.CandidateLetter;
import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;
import com.csipl.hrms.service.recruitement.CandidateLetterTemplateMasterService;
import com.csipl.hrms.service.recruitement.adaptor.CandidateLetterTemplateMasterAdaptor;


@RestController
@RequestMapping("/candidateLetterTemplateMaster")
public class CandidateLetterTemplateMasterController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateLetterTemplateMasterController.class);
	
	@Autowired
	CandidateLetterTemplateMasterService candidateLetterTemplateMasterService;
	
	@Autowired
	CandidateLetterTemplateMasterAdaptor candidateLetterTemplateMasterAdaptor;
	
	@PostMapping(path = "/save")
	public @ResponseBody CandidateLetterTemplateMaster saveInterviewScheduleTemplate(@RequestBody CandidateLetterTemplateMasterDTO candidateLetterTemplateMasterDTO, HttpServletRequest req) {
		logger.info("saveLetterTemplateMaster is calling : CandidateLetterTemplateMasterDTO " + candidateLetterTemplateMasterDTO);
		CandidateLetterTemplateMaster interviewScheduleTemplate = candidateLetterTemplateMasterAdaptor.uiDtoToDatabaseModel(candidateLetterTemplateMasterDTO);
		logger.info("saveLetterTemplateMaster is end  :" + interviewScheduleTemplate);
		return candidateLetterTemplateMasterService.saveInterviewScheduleTemplate(interviewScheduleTemplate);
	}
	
	
	@GetMapping(value = "/getCandidateLetterByTemplateType/{templateFlag}")
	public @ResponseBody CandidateLetterTemplateMasterDTO getCandidateLetterByTemplateType(
			@PathVariable("templateFlag") String templateFlag, HttpServletRequest req) {

		logger.info("getCandidateLetterByTemplateType is calling...");
		CandidateLetterTemplateMaster candidateLetterTemplateMaster = candidateLetterTemplateMasterService.getCandidateLetterByTemplateType(templateFlag);
		return candidateLetterTemplateMasterAdaptor.databaseModelToUiDto(candidateLetterTemplateMaster);
	}

}
