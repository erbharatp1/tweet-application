
package com.csipl.hrms.report.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.LoanExcelWriter;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.service.adaptor.LoanIssueAdaptor;
import com.csipl.hrms.service.adaptor.LoanReportAdaptor;
import com.csipl.hrms.service.report.LoanReportService;

@RequestMapping("/loanReport")
@RestController

public class LoanReportController {
	@Autowired
	LoanReportService loanReportService;

	LoanIssueAdaptor loanIssueAdaptor = new LoanIssueAdaptor();

	private static final Logger logger = LoggerFactory.getLogger(LoanReportController.class);

	@RequestMapping(path = "/loanConsolidatedReport/{companyId}/{loanStatus}", method = RequestMethod.GET)
	public void generateLoanConsolidatedReport(@PathVariable("companyId") Long companyId,
			@PathVariable List<String> loanStatus, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException {

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Loan A/c Number",
				"Loan Amount", "Loan Released On", "Status", "EMI Start From", "Actual EMI", "Remaining EMI",
				"Out Standing Amount", "Loan Recovered" };

		// List<Object[]> loanConsolidatedReportList =
		// loanReportService.findLoanConsolidatedStatement(longCompanyId,loanStatus);
		// for (Object[] obj : loanConsolidatedReportList) {
		// System.out.println("Object data============ " + Arrays.toString(obj));
		// }
		// List<LoanIssueDTO> LoanIssueDtoList
		// =LoanReportAdaptor.databaseListToUIDtoList(loanConsolidatedReportList);
		List<LoanIssue> loanConsolidatedReportList = loanReportService.findAllLoanIssue(companyId, loanStatus);

		List<LoanIssueDTO> LoanIssueDtoList = loanIssueAdaptor.databaseModelToUiDtoList(loanConsolidatedReportList);
		logger.info("loanConsolidatedReportList controller  :" + LoanIssueDtoList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=loanConsolidatedReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = LoanExcelWriter.loanConsolidatedStatementReport(LoanIssueDtoList, columns,
						companyId, loanStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(path = "/loanDetailedReport/{companyId}/{loanAccountNo}", method = RequestMethod.GET)
	public void generateLoanDetailedMonthlyReport(@PathVariable("companyId") Long companyId,
			@PathVariable("loanAccountNo") Long loanAccountNo, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException {

		String[] rows = { "Employee Code", "Loan Account Number", "Designation", "Loan Amount", "Department",
				"Released On" };
		String[] columns = { "Date", "EMI Amount", "Remaining Loan", "Remarks" };

		List<LoanIssue> loanIssueList = loanReportService.getAllLoanIssues(companyId);
		Long transactionNo = 0l;
		boolean flag = true;

		for (int i = 0; i < loanIssueList.size(); i++) {
			if (loanAccountNo.equals(loanIssueList.get(i).getTransactionNo())) {
				transactionNo = loanAccountNo;
				flag = false;
			}
		}

		if (flag == true) {
			throw new ErrorHandling("Please Enter Valid Account Number");
		}

		List<Object[]> loanDetailedReportList = loanReportService.findLoanDetailedMonthlyStatement(companyId,
				transactionNo);

		String activeStatus = null;
		for (Object[] loanIssueObj : loanDetailedReportList) {
			activeStatus = loanIssueObj[7] != null ? (String) loanIssueObj[7] : null;
		}

		// List<Object[]> loanAccountNoList = loanReportService.getEmiDetails(companyId,
		// loanAccountNo,activeStatus);
		LoanIssue loanIssue = loanReportService.getLoanEmiDetailsList(companyId, transactionNo, activeStatus);

		List<LoanIssueDTO> loanIssueDtoList = LoanReportAdaptor.databaseListsToUIDtoLists(loanDetailedReportList);
		LoanIssueDTO loanIssueDto = loanIssueAdaptor.databaseModelToUiDto(loanIssue);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=loanDetailedReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {

				Workbook workbook = LoanExcelWriter.loanDetailedStatementReport(loanIssueDtoList, loanIssueDto, rows,
						columns, companyId, transactionNo);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
