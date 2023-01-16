package com.csipl.hrms.candidate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.candidate.CandidateFamilyDTO;
import com.csipl.hrms.dto.candidate.CandidateNomineeDTO;
import com.csipl.hrms.model.candidate.CandidateFamily;
import com.csipl.hrms.model.candidate.CandidateNominee;
import com.csipl.hrms.service.adaptor.CandidateNomineeAdaptor;
import com.csipl.hrms.service.candidate.CandidateNomineeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/candidate")
public class CandidateNomineeController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateNomineeController.class);
	
	CandidateNomineeAdaptor candidateNomineeAdaptor=new CandidateNomineeAdaptor();
	
	@Autowired
	CandidateNomineeService candidateNomineeService;
	
	
	@RequestMapping(value="/candidateNominee",method = RequestMethod.POST)
	public List<CandidateNomineeDTO> saveCandidateNominee(@RequestBody List<CandidateNomineeDTO> candidateNomineeDTOList, HttpServletRequest req) {
		logger.info("saveCandidateFamily is calling : CandidateFamilyDTO "+ candidateNomineeDTOList);
		List<CandidateNominee> candidateNomineeList = candidateNomineeAdaptor.uiDtoToDatabaseModelList(candidateNomineeDTOList);
	List<CandidateNominee> candidateNomineeListObj =	candidateNomineeService.save(candidateNomineeList);
	//System.out.println(candidateFamilyAdaptor.databaseModelToUiDtoList(candidateFamilyListObj).toString());
	return candidateNomineeAdaptor.databaseModelToUiDtoList(candidateNomineeListObj);
	}
	
	
	@RequestMapping(value="/getNominee/{candidateId}", method = RequestMethod.GET)
	public @ResponseBody List<CandidateNomineeDTO> getCandidateFamilyDetails(@PathVariable("candidateId") Long candidateId,
			HttpServletRequest req) {
		
		logger.info("FamilyDetailsController getAllEmployeeFamilyDetails  empId is :" + candidateId);
		List<CandidateNominee> candidateNomineeList=candidateNomineeService.findAllNominee(candidateId);
		
		List<CandidateNomineeDTO> candidateNomineeDTOList=candidateNomineeAdaptor.databaseModelToUiDtoList(candidateNomineeList);
		logger.info("getting  FamilyDetails :==========================="  + candidateNomineeList);
		return candidateNomineeDTOList;
}
}
