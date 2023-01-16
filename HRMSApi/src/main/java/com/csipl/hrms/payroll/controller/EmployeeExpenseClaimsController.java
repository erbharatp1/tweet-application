package com.csipl.hrms.payroll.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.csipl.hrms.dto.employee.EmployeeExpenseClaimDTO;
import com.csipl.hrms.dto.employee.ExpenseTypeDTO;
import com.csipl.hrms.model.employee.EmployeeExpenseClaim;
import com.csipl.hrms.model.employee.ExpenseType;
import com.csipl.hrms.service.adaptor.EmployeeExpenseClaimsAdaptor;
import com.csipl.hrms.service.payroll.EmployeeExpenseClaimsService;
import com.csipl.hrms.service.payroll.repository.ExpenseTypeRepository;
import com.csipl.hrms.service.util.SalaryPdfReport;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
@RequestMapping("/expenseClaims")
public class EmployeeExpenseClaimsController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeExpenseClaimsController.class);

	@Autowired
	private EmployeeExpenseClaimsService employeeExpenseClaimsService;

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Autowired
	private EmployeeExpenseClaimsAdaptor employeeExpenseClaimsAdaptor;

	@PostMapping(value = "/file", consumes = "multipart/form-data")
	public void saveExpenseClaim(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") EmployeeExpenseClaimDTO empExpenseClaimDTO) {
		logger.info("saveExpenseClaim is calling : " + " : empExpenseClaimDTO " + empExpenseClaimDTO + ":uploadFile"
				+ file);
		EmployeeExpenseClaim employeeExpenseClaim = employeeExpenseClaimsAdaptor
				.uiDtoToDatabaseModel(empExpenseClaimDTO);
		employeeExpenseClaimsService.save(employeeExpenseClaim, file, true);
	}

	@PostMapping(value = "save")
	public void saveExpenseClaim(@RequestBody EmployeeExpenseClaimDTO employeeExpenseClaimDTO) {
		logger.info("saveExpenseClaim is calling : " + " : EmployeeExpenseClaimDTO " + employeeExpenseClaimDTO);
		EmployeeExpenseClaim empExpenseClaim = employeeExpenseClaimsAdaptor
				.uiDtoToDatabaseModel(employeeExpenseClaimDTO);
		logger.info("empExpenseClaim  : " + empExpenseClaim);
		employeeExpenseClaimsService.save(empExpenseClaim, null, false);
	}

	@GetMapping(value = "findById/{employeeExpeneseClaimId}")
	public EmployeeExpenseClaimDTO findById(@PathVariable Long employeeExpeneseClaimId, HttpServletRequest req) {
		logger.info("findById is calling : employeeExpeneseClaimId " + employeeExpeneseClaimId);

		return employeeExpenseClaimsAdaptor
				.databaseModelToUiDto(employeeExpenseClaimsService.findById(employeeExpeneseClaimId));
	}

	@GetMapping(value = "findExpenseList/{companyId}")
	public List<EmployeeExpenseClaimDTO> findExpenseList(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findExpenseList is calling : ");
		List<EmployeeExpenseClaim> claimsList = employeeExpenseClaimsService.findExpenseList(companyId);
		logger.info("findExpenseList  : findExpenseList " + claimsList);
	
			return employeeExpenseClaimsAdaptor.databaseModelToUiDtoList(claimsList);
		
	}

	@GetMapping(value = "findExpenseTypeList/{companyId}")
	public List<ExpenseTypeDTO> findExpenseTypeList(@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("findExpenseTypeList is calling : ");
		List<ExpenseType> expenseTypeList = expenseTypeRepository.findExpenseTypeList(companyId);
		logger.info("findExpenseTypeList  : findExpenseList " + expenseTypeList);
	
			return employeeExpenseClaimsAdaptor.findExpenseTypeList(expenseTypeList);
		
	}

	// np Start
	@RequestMapping(value = "/expensesClaimPendingApprovals/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeExpenseClaimDTO> getExpensesClaimPendingApprovals(
			@PathVariable("companyId") Long companyId, @RequestBody SearchDTO searcDto) {

		List<Object[]> expensesPendingList = employeeExpenseClaimsService.getExpensesClaimPendingApprovals(companyId,
				searcDto);

		return employeeExpenseClaimsAdaptor.objLeaveListToObjUiDtoList(expensesPendingList);
	}

	@RequestMapping(value = "/expensesClaimPendingApprovalsCount/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getExpensesClaimPendingApprovalsCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) {

		logger.info("expensesClaimPendingApprovalsCount is calling " + searcDto.getDataStatus());
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		List<Object[]> expensesPendingList = employeeExpenseClaimsService.getExpensesClaimPendingApprovals(companyId,
				searcDto);

		count = expensesPendingList.size();

		logger.info("expensesClaimPendingApprovalsCount..." + count);

		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);

		return entityCountDto;
	}

	@RequestMapping(value = "/expensesClaimNonPendingApprovals/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeExpenseClaimDTO> getExpensesClaimNonPendingApprovals(
			@PathVariable("companyId") Long companyId, @RequestBody SearchDTO searcDto) {

		List<Object[]> expensesPendingList = employeeExpenseClaimsService.getExpensesClaimNonPendingApprovals(companyId,
				searcDto);

		return employeeExpenseClaimsAdaptor.objLeaveListToObjUiDtoList(expensesPendingList);
	}

	@RequestMapping(value = "/expensesClaimNonPendingApprovalsCount/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getExpensesClaimNonPendingApprovalsCount(
			@PathVariable("companyId") Long companyId, @PathVariable("pageSize") String pageSize,
			@RequestBody SearchDTO searcDto) {

		logger.info("getExpensesClaimNonPendingApprovalsCount is calling " + searcDto.getDataStatus());
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		List<Object[]> expensesNonPendingList = employeeExpenseClaimsService
				.getExpensesClaimNonPendingApprovals(companyId, searcDto);

		count = expensesNonPendingList.size();

		logger.info("getExpensesClaimNonPendingApprovalsCount..." + count);

		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);

		return entityCountDto;
	}

	@GetMapping(value = "expenseClaimSummary/{employeeId}")
	public List<EmployeeExpenseClaimDTO> getExpenseClaimSummary(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {

		logger.info("getExpenseClaimSummary is calling : ");

		List<Object[]> expenseClaimList = employeeExpenseClaimsService.getExpenseClaimSummary(employeeId);

		
			return employeeExpenseClaimsAdaptor.databaseModeltouiObjExpenseSummaryDto(expenseClaimList);
		
	}

	@GetMapping(path = "/generateInvoice/{employeeExpeneseClaimId}")
	public StreamingResponseBody generateInvoice(@PathVariable("employeeExpeneseClaimId") Long employeeExpeneseClaimId,
			HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, IOException {

		logger.info("generateInvoice is calling :  ");

		EmployeeExpenseClaim invoice = employeeExpenseClaimsService.findById(employeeExpeneseClaimId);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"Invoice.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().generateInvoice(invoice);
		logger.info("generateInvoice is end ");
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};
	}

	@GetMapping(value = "getExpenseTypeById/{expenseTypeId}")
	public ExpenseType getExpenseTypeById(@PathVariable("expenseTypeId") Long expenseTypeId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("getExpenseTypeById is calling ");
		ExpenseType expenseType = expenseTypeRepository.findExpenseTypeById(expenseTypeId);
		logger.info("getExpenseTypeById is end ");
		return expenseType;

	}
	
	@GetMapping(value = "/updateByStatus/{employeeExpeneseClaimId}")
	public void updateByStatus(@PathVariable("employeeExpeneseClaimId") Long[] employeeExpeneseClaimIds) {
		logger.info("EmployeeExpenseClaimsController.updateByStatus()" + employeeExpeneseClaimIds);
					employeeExpenseClaimsService.updateByStatus(employeeExpeneseClaimIds);
			
	}

}
