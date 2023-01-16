package com.csipl.hrms.employee.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.CompanyPolicyDTO;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.employee.CompanyPolicy;
import com.csipl.hrms.service.adaptor.CompanyPolicyAdaptor;
import com.csipl.hrms.service.employee.CompanyPolicyService;
import com.csipl.hrms.service.employee.EmployeePagingAndFilterService;
import com.csipl.hrms.service.util.SalaryPdfReport;

@RestController
@RequestMapping("/companyPolicy")
public class CompanyPolicyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyPolicyController.class);

	@Autowired
	private CompanyPolicyService companyPolicyService;

	@Autowired
	private CompanyPolicyAdaptor companyPolicyAdaptor;

	@Autowired
	private EmployeePagingAndFilterService employeePagingAndFilterService;

	/**
	 * Method performed save operation with file
	 * 
	 */
	@RequestMapping(value = "/uploadCompanyPolicyWithFile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public CompanyPolicyDTO saveCompanyPolicyWithFile(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") CompanyPolicyDTO companyPolicyDTO, HttpServletRequest req) {

		CompanyPolicy companyPolicy = companyPolicyAdaptor.uiDtoToDatabaseModelForFileUpload(companyPolicyDTO, file,
				true);

		CompanyPolicy companyPolicySaved = companyPolicyService.save(companyPolicy);

		logger.info("UpdateQue is calling : QuestiontemplateDTO " + companyPolicyDTO + "uploadFile" + file);
		return companyPolicyAdaptor.databaseModelToUiDto(companyPolicySaved);

	}

	@GetMapping(path = "/getCompanyPolicyList/{companyId}")
	public @ResponseBody List<CompanyPolicyDTO> getAllCompanyPolicies(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {

		logger.info("getAllLetters is calling :  ");

		List<CompanyPolicy> companyPolicyList = companyPolicyService.findAllCompanyPolicies(companyId);

		logger.info("getAllCompanyPolicy is end : CompanyPolicy List " + companyPolicyList);

		if (companyPolicyList.size() > 0)
			return companyPolicyAdaptor.databaseModelToUiDtoList(companyPolicyList);
		else
			throw new ErrorHandling("Company Policies are not available in company");
	}

	@GetMapping(path = "/getCompanyPolicyById/{policyId}")
	public @ResponseBody CompanyPolicyDTO getCompanyPolicyById(@PathVariable("policyId") Long policyId)
			throws ErrorHandling, PayRollProcessException {

		logger.info("getcompanyPolicyById is calling :  ");

		CompanyPolicy companyPolicyList = companyPolicyService.findCompanyPolicyById(policyId);

		return companyPolicyAdaptor.databaseModelToUiDto(companyPolicyList);
	}

	@GetMapping(path = "/generatePolicyFile/{policyId}")
	public StreamingResponseBody generatePolicyFile(@PathVariable("policyId") Long policyId, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, IOException {

		logger.info("generatePolicyFile is calling :  ");

		CompanyPolicy companyPolicyList = companyPolicyService.findCompanyPolicyById(policyId);

		logger.info("getAllLetters is end : Letter List " + companyPolicyList);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"CompanyPolicy.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().generatePolicyFile(companyPolicyList);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};
	}

	@RequestMapping(path = "/employeeListforPolicy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CompanyPolicyDTO> getEmployeeListforPolicy(
			@RequestBody EmployeeSearchDTO employeeSearcDto) {

		logger.info("getEmployeeListforPolicy is calling :  ");

		List<Object[]> employeeList = employeePagingAndFilterService
				.findEmployeeListforPolicy(employeeSearcDto.getCompanyId(), employeeSearcDto);

		return companyPolicyAdaptor.databaseModelToUiDtoEmployeeList(employeeList, employeeSearcDto);
	}

	@GetMapping(value = "/empcount/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		companyPolicyService.getEmployeeCount(longCompanyId, employeeCountDto);
		int count;
		count = employeeCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);
		return employeeCountDto;
	}

	@DeleteMapping(value = "/deleteCompanyPolicy/{policyId}")
	public void deleteCompanyPolicy(@PathVariable("policyId") Long policyId) {

		logger.info("deletepolicy is calling :" + "policyId" + policyId);

		companyPolicyService.delete(policyId);
		logger.info("deletepolicy is calling Successfully :");
	}

	/**
	 * 
	 * @param employeeId
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@GetMapping(path = "/getAllCompanyPolicyByEmployee/{employeeId}")
	public @ResponseBody List<CompanyPolicyDTO> getAllCompanyPolicyByEmployee(
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllCompanyPolicyByEmployee is calling :  " + employeeId);
		List<CompanyPolicyDTO> companyPolicyList = companyPolicyService.getAllCompanyPolicyByEmployee(employeeId);
		if (companyPolicyList.size() > 0)
			return companyPolicyList;
		else
			throw new ErrorHandling("Company Policies are not available in company");
	}

}
