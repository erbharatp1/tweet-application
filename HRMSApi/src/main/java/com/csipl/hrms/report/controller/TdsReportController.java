package com.csipl.hrms.report.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.TdsExcelWriter;
import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.service.adaptor.TdsSummaryChangeAdaptor;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.report.TdsReportService;

@RestController
@RequestMapping("/TdsReports")
public class TdsReportController {

	@Autowired
	TdsReportService tdsReportService;
	
	@Autowired
	CompanyService companyService;
	
	TdsSummaryChangeAdaptor tdsSummaryChangeAdaptor = new TdsSummaryChangeAdaptor();
	
	
	@GetMapping("/generateStatememntOfAnnualTaxReport/{companyId}/{financialYearId}/{financialYear}")
	public void generateStatememntOfAnnualTaxReport(@PathVariable Long companyId, @PathVariable Long financialYearId,
			@PathVariable("financialYear") String financialYear, HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining",
				"PAN Number", "Gross Paid", "Gross to be Paid", "Total Gross", "10(13)(a)-House Rent Allowance",
				"Provident Fund Employer's Share (Paid + To Be Paid)", "16(ia)-Standard Deduction",
				"16(iii)-Professional/Employment Tax (Paid + To Be Paid)", "Taxable Income Under Head Salaries",
				"Other Income Received", "Total Gross Under All Heads", "Deduction from Chapter VI A",
				"Deduction from Section 24", "Total Taxable Income Under All Heads", "Tax On Taxable Income",
				"Tax Exemption Under Rebate", "Income Tax", "Educational Cess", "Surcharge", "Total Tax Liability",
				"Tax Paid", "Total Tax Payable" };
//		String financialYear = "2020-2021";
//		Long companyId = 1l;
		List<Object[]> StatememntOfAnnualTax = tdsReportService.getStatememntOfAnnualTax(companyId, financialYearId);

		List<TdsSummaryChangeDTO> tdsSummaryChangeDTO = tdsSummaryChangeAdaptor.databaseModelToUiDtoLists(StatememntOfAnnualTax);
		
		System.out.println("singya");
		
		// Department department =null;

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=StatememntOfAnnualTaxReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			
			if (companyId != null) {
				Company company = companyService.getCompany(companyId);

				if (company != null /* && reportPayoutDtoList.size()>0 */) {
					Workbook workbook = TdsExcelWriter.annualTax(tdsSummaryChangeDTO, columns,
							financialYear, company);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
	@GetMapping("/generateEmployeeTdsDeclarationReport/{companyId}/{financialYearId}/{financialYear}")
	public void generateEmployeeTdsDeclarationReport(@PathVariable Long companyId,
			@PathVariable Long financialYearId,@PathVariable("financialYear") String financialYear, HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining",
				"PAN Number" };
		
		
//		String financialYear = "2020-2021";
//		Long companyId = 1l;
		
		List<String> columnsList = new ArrayList<>(Arrays.asList(columns));
		
//		List<String> columnsList = Arrays.asList(columns);

		List<String> dbColumnsList = new ArrayList<>();
		List<Object[]> declarationColumn = tdsReportService.getDeclarationColumn(companyId, financialYearId);
		
		List<Long> sectionIdList = new ArrayList<>();
		
		for (Object[] col : declarationColumn) {
			Integer id = col[0] != null ? (Integer) col[0] : null;
			String column = col[1] != null ? (String) col[1] : null;
			sectionIdList.add(Long.valueOf(id));
			dbColumnsList.add(column);
		}
		
		columnsList.addAll(dbColumnsList);
		columnsList.add("Other Income");
		List<Object[]> employeeTdsDeclaration = tdsReportService.getEmployeeTdsDeclaration(companyId, financialYearId);

		Map<String, List<TdsTransactionDTO>> map = tdsSummaryChangeAdaptor.databaseModelToMap(employeeTdsDeclaration);
		
		System.out.println("singya"+map.size());
		System.out.println("hsfg");
		// Department department =null;

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=EmployeeTdsDeclarationReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			
			if (companyId != null) {
				Company company = companyService.getCompany(companyId);

				if (company != null /* && reportPayoutDtoList.size()>0 */) {
					Workbook workbook = TdsExcelWriter.employeeTdsDeclaration(map, columnsList,
							financialYear, company,sectionIdList);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
}
