package com.csipl.hrms.employee.controller;

import java.util.List;

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

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.ApprovalHierarchyMasterDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.service.adaptor.ApprovalHierarchyMasterAdaptor;
import com.csipl.hrms.service.employee.ApprovalHierarchyMasterService;

@RestController
@RequestMapping("/approvalHierarchyMaster")
public class ApprovalHierarchyMasterController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApprovalHierarchyMasterController.class);

	@Autowired
	private ApprovalHierarchyMasterService approvalHierarchyMasterService;

	@Autowired
	private ApprovalHierarchyMasterAdaptor approvalHierarchyMasterAdaptor;

	@PostMapping(path = "/save")
	public void save(@RequestBody ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO, HttpServletRequest req) throws ErrorHandling {

		logger.info("ApprovalHierarchy  save is calling : LetterDTO " + approvalHierarchyMasterDTO);

		ApprovalHierarchyMaster approvalHierarchyMaster = approvalHierarchyMasterAdaptor
				.approvalMastertoDatabaseModel(approvalHierarchyMasterDTO);

		approvalHierarchyMasterService.saveHierarchy(approvalHierarchyMaster);

		logger.info("ApprovalHierarchy save is end  :");
	}

	@GetMapping(path = "getAllMasterApprovalList/{companyId}")
	public @ResponseBody List<ApprovalHierarchyMasterDTO> getAllMasterApprovalList(
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {

		List<Object[]> approvalHierarchyMasterList = approvalHierarchyMasterService.getMasterApprovalList(companyId);

		return approvalHierarchyMasterAdaptor.databaseModelToMasterApprovalDtoList(approvalHierarchyMasterList);
	}

	@GetMapping(path = "getLetterApprovals/{companyId}")
	public @ResponseBody List<ApprovalHierarchyMasterDTO> getLetterApprovals(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {

		List<ApprovalHierarchyMaster> approvalHierarchyMasterList = approvalHierarchyMasterService
				.getLetterApprovals(companyId);

		return approvalHierarchyMasterAdaptor.databaseModelToUiDtoList(approvalHierarchyMasterList);
	}

	@GetMapping(path = "/letterApprovalById/{letterId}")
	public ApprovalHierarchyMasterDTO getLetterApprovalById(@PathVariable("letterId") Long letterId,
			HttpServletRequest req) {

		logger.info("getLetterApprovalById is calling : letterId  " + letterId);

		List<Object[]> approvalList = approvalHierarchyMasterService.findLetterApprovalById(letterId);

		return approvalHierarchyMasterAdaptor.databaseModelToApprovalListByIdDto(approvalList);

	}
	
	@GetMapping(path = "/findApprovalHierarchyByStatus/{letterId}")
	public int findApprovalHierarchyByStatus(@PathVariable("letterId") Long letterId,
			HttpServletRequest req) {
		logger.info("getLetterApprovalById is calling : letterId  " + letterId);
		return approvalHierarchyMasterService.findApprovalHierarchyByStatus(letterId);

	}

}
