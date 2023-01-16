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
import com.csipl.hrms.dto.employee.EmployeeCompanyPolicyDTO;
import com.csipl.hrms.model.employee.EmployeeCompanyPolicy;
import com.csipl.hrms.service.adaptor.EmployeeCompanyPolicyAdaptor;
import com.csipl.hrms.service.employee.EmployeeCompanyPolicyService;

@RestController
@RequestMapping("/employeeCompanyPolicy")
public class EmployeeCompanyPolicyController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeCompanyPolicyController.class);

	@Autowired
	private EmployeeCompanyPolicyService employeeCompanyPolicyService;

	@Autowired
	private EmployeeCompanyPolicyAdaptor employeeCompanyPolicyAdaptor;

	@PostMapping(path = "/save")
	public void saveEmployeePolicy(@RequestBody List<EmployeeCompanyPolicyDTO> empCompanyPolicyDTO,
			HttpServletRequest req) {

		logger.info("saveEmployeePolicy is Start  :");

		List<EmployeeCompanyPolicy> empCompanyPolicy = employeeCompanyPolicyAdaptor
				.uiDtoToDatabaseModelList(empCompanyPolicyDTO);

		employeeCompanyPolicyService.save(empCompanyPolicy);

		logger.info("saveEmployeePolicy is end  :" + empCompanyPolicy);
	}

	@GetMapping(path = "/getEmployeePolicyList/{companyPolicyId}")
	public @ResponseBody List<EmployeeCompanyPolicyDTO> getAllEmployeePolicies(
			@PathVariable("companyPolicyId") Long companyPolicyId) throws ErrorHandling, PayRollProcessException {

		List<EmployeeCompanyPolicy> empPolicyList = employeeCompanyPolicyService
				.findAllEmployeePolicies(companyPolicyId);

		logger.info("getAllEmployeePolicy is end : employeePolicy List " + empPolicyList);

		return employeeCompanyPolicyAdaptor.databaseModelToUiDtoList(empPolicyList);
	}

}
