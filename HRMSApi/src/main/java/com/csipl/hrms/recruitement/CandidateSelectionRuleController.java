package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.tools.config.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.recruitment.CandidateSelectionRuleDTO;
import com.csipl.hrms.employee.controller.CompanyPolicyController;
import com.csipl.hrms.model.recruitment.SelectionRuleDescription;
import com.csipl.hrms.service.recruitement.CandidateSelectionRuleService;
import com.csipl.hrms.service.recruitement.adaptor.CandidateSelectionRuleAdaptor;

/**
 * shubham yaduwanshi
 *
 */
@RestController
@RequestMapping("/candidateSelectionRule")
public class CandidateSelectionRuleController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyPolicyController.class);

	@Autowired
	private CandidateSelectionRuleService candidateSelectionRuleService;

	@Autowired
	private CandidateSelectionRuleAdaptor candidateSelectionRuleAdaptor;

	/**
	 * Method performed save operation
	 * 
	 */
	@PostMapping(path = "/saveCandidateSelectionRule")
	public void saveCandidateSelectionRule(@RequestBody CandidateSelectionRuleDTO candidateSelectionRuleDTO,
			HttpServletRequest req) {
		logger.info("saveCandidateSelectionRule is calling : CandidateSelectionRuleDTO: " + candidateSelectionRuleDTO);
		SelectionRuleDescription candidateSelectionRule = candidateSelectionRuleAdaptor
				.uiDtoToDatabaseModel(candidateSelectionRuleDTO);
		candidateSelectionRuleService.saveCandidateSelectionRule(candidateSelectionRule);
		logger.info("saveCandidateSelectionRule is ending : " + candidateSelectionRule);
	}

	/**
	 * Method performed select operation
	 * 
	 */
	@GetMapping(path = "/getAllCandidateSelectionRule")
	public List<CandidateSelectionRuleDTO> getAllCandidateSelectionRule(HttpServletRequest req) {
		logger.info("getAllCandidateSelectionRule is calling : ");
		List<SelectionRuleDescription> candidateSelectionRulList = candidateSelectionRuleService
				.getAllCandidateSelectionRule();
		return candidateSelectionRuleAdaptor.databaseModelToUiDtoList(candidateSelectionRulList);
	}

	/**
	 * Method performed update operation
	 * 
	 */

	@PutMapping(path = "/updateCandidateSelectionRule")
	public void updateCandidateSelectionRule(@RequestBody List<CandidateSelectionRuleDTO> candidateSelectionRuleDTO,
			HttpServletRequest req) {
		logger.info("updateCandidateSelectionRule is calling:CandidateSelectionRuleDTO" + candidateSelectionRuleDTO);
		List<SelectionRuleDescription> candidateSelectionRule = candidateSelectionRuleAdaptor
				.uiDtoToDatabaseModelList(candidateSelectionRuleDTO);
		candidateSelectionRuleService.updateCandidateSelectionRule(candidateSelectionRule);
		logger.info("updateCandidateSelectionRule is ending:");

	}

	/**
	 * Method performed delete operation
	 * 
	 */

	@DeleteMapping(path = "/deleteCandidateSelectionRule/{selectionRuleId}")
	public void deleteCandidateSelectionRule(@PathVariable Long selectionRuleId, HttpServletRequest req) {
		logger.info("deleteCandidateSelectionRule is calling :selectionRuleId: " + selectionRuleId);
		candidateSelectionRuleService.deleteCandidateSelectionRule(selectionRuleId);
		logger.info("deleteCandidateSelectionRule is ending:");
	}

}
