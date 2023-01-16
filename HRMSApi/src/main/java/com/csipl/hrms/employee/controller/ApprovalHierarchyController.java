package com.csipl.hrms.employee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.employee.ApprovalHierarchyDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchy;
import com.csipl.hrms.service.adaptor.ApprovalHierarchyAdaptor;
import com.csipl.hrms.service.employee.ApprovalHierarchyService;

@RestController
@RequestMapping("/approvalHierarchy")
public class ApprovalHierarchyController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApprovalHierarchyController.class);

	@Autowired
	private ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	private ApprovalHierarchyAdaptor approvalHierarchyAdaptor;

	@PostMapping(path = "/saveApprovalHierarchy")
	public void save(@RequestBody List<ApprovalHierarchyDTO> approvalHierarchyDTO, HttpServletRequest req) {
		logger.info("ApprovalHierarchy  save is calling : ApprovalHierarchyDTO " + approvalHierarchyDTO);
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyAdaptor
				.uiDtoToDatabaseModelList(approvalHierarchyDTO);

		approvalHierarchyService.save(approvalHierarchyList);

		logger.info("ApprovalHierarchy save is end  :" + approvalHierarchyList);
	}

	@GetMapping(path = "/letterApprovalList/{companyId}")
	public List<ApprovalHierarchyDTO> getAllLetterApproval(@PathVariable("companyId") Long companyId) {
		logger.info("getAllLetters is calling :  ");

		List<Object[]> allLetterApprovalList = approvalHierarchyService.getAllLetterApproval(companyId);

		List<ApprovalHierarchyDTO> letterApprovalList = approvalHierarchyAdaptor
				.databaseModelToLetterApprovalList(allLetterApprovalList);

		return letterApprovalList;

	}

	@DeleteMapping(value = "/deleteApprovalLettersById/{approvalHierarchyId}")
	public void deleteCompanyPolicy(@PathVariable("approvalHierarchyId") Long approvalHierarchyId) {

		logger.info("deletepolicy is calling :approvalHierarchyId");
		approvalHierarchyService.delete(approvalHierarchyId);
	}

}
