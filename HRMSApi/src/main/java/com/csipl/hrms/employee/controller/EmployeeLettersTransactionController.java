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
import com.csipl.hrms.dto.employee.EmployeeLettersTransactionDTO;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.service.adaptor.EmployeeLettersTransactionAdaptor;
import com.csipl.hrms.service.employee.EmployeeLetterService;
import com.csipl.hrms.service.employee.EmployeeLettersTransactionService;

@RestController
@RequestMapping("/empLetterTransaction")
public class EmployeeLettersTransactionController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeLettersTransactionController.class);

	@Autowired
	private EmployeeLettersTransactionService empLettersTransactionServiceService;
	@Autowired
	private EmployeeLettersTransactionAdaptor empLettersTransactionAdaptor;
	@Autowired
	private EmployeeLetterService empLetterService;

	@PostMapping(path = "/save")
	public void saveLetterTransaction(@RequestBody EmployeeLettersTransactionDTO empLettersTransactionDTO,
			HttpServletRequest req) {
		logger.info("saveLetterTransaction is calling : EmpLetterDTO " + empLettersTransactionDTO);
		EmployeeLetter employeeLetter = empLetterService
				.getEmployeeLetterById(empLettersTransactionDTO.getEmployeeLetterId());
		EmployeeLettersTransaction employeeLettersTransaction = empLettersTransactionAdaptor
				.uiDtoToDatabaseModel(empLettersTransactionDTO);
		employeeLettersTransaction.setEmployeeLetter(employeeLetter);
		empLettersTransactionServiceService.saveEmpLetterTransaction(employeeLettersTransaction);

	}

	@GetMapping(path = "/fetchAllApprovalStatus/{companyId}/{letterId}/{employeeId}")
	public @ResponseBody List<EmployeeLettersTransactionDTO> findAllApprovalStatus(
			@PathVariable("companyId") Long companyId, @PathVariable("letterId") Long letterId,
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException {
		logger.info("findAllApprovalStatus is calling :  ");

		List<EmployeeLettersTransactionDTO> empTransactionList = empLettersTransactionAdaptor.databaseModelToUiDtoList(
				empLettersTransactionServiceService.findAllApprovalStatus(companyId, letterId, employeeId));

		logger.info("findAllApprovalStatus is end : Letter List " + empTransactionList);
		return empTransactionList;

	}
}
