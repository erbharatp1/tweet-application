
package com.csipl.hrms.report.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.PayRegisterExcelWriter;
import com.csipl.hrms.common.util.PayrollExelWriter;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payroll.ArrearReportPayOutDTO;
import com.csipl.hrms.dto.payroll.OneTimeEarningDeductionDTO;
import com.csipl.hrms.dto.payroll.TdsHouseRentInfoDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportSummaryDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.adaptor.PayrollReportAdaptor;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.payroll.EsicService;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.report.EmployeeReportService;
import com.csipl.hrms.service.report.PayrollReportService;

@RequestMapping("/report")
@RestController
public class PayrollReportController {
	@Autowired
	PayrollReportService payrollReportService;
	@Autowired
	CompanyService companyService;

	@Autowired
	PayHeadService payHeadService;

	@Autowired
	ReportPayOutService reportPayOutService;

	@Autowired
	FinancialYearService financialYearService;

	@Autowired
	EmployeeReportService employeeReportService;

	@Autowired
	EsicService esicService;

	PayrollReportAdaptor payrollReportAdaptor = new PayrollReportAdaptor();
    private static final Logger logger = LoggerFactory.getLogger(PayrollReportController.class);


	@RequestMapping(path = "/proTaxReport/{companyId}/{fromProcessMonth}/{toProcessMonth}/{deptId}/{employeeId}/{stateId}", method = RequestMethod.GET)
	public void generatePTReport(@PathVariable("companyId") String companyId,
			@PathVariable("fromProcessMonth") String fromProcessMonth,
			@PathVariable("toProcessMonth") String toProcessMonth, @PathVariable("deptId") String deptId,
			@PathVariable("employeeId") String employeeId, @PathVariable("stateId") String stateId,
			HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, PayRollProcessException {
		// logger.info("generate : "+" : processMonth "+ processMonth );
		System.out.println("PayrollReportController");
		Long empId = Long.parseLong(employeeId);
		Long departmentId = Long.parseLong(deptId);
		Long stateID = Long.parseLong(stateId);
		Long longcompanyId = Long.parseLong(companyId);
		String[] columns = { "Employee Code", "Employee Name", "Department", "Designation", "Amount", "Process Month",
				"State" };
		List<Object[]> PTReportList = payrollReportService.findPTReport(longcompanyId, fromProcessMonth, toProcessMonth,
				departmentId, empId, stateID);
		// List<Object[]>
		// PTReportList=payrollReportService.findPTReport(getCompanyId(req), "JAN-2018",
		// "JUN-2018" ,4l ,"" ,13l);
		// System.out.println(PTReportList);
		List<ReportPayOutDTO> reportPayoutList = payrollReportAdaptor.objectListToReportPayoutList(PTReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=professionalTaxReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null) {
					Workbook workbook = PayrollExelWriter.PTReport(reportPayoutList, columns, company, fromProcessMonth,
							toProcessMonth);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}
				/*
				 * else { throw new ErrorHandling("Comapny and Esi data not available"); }
				 */
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/provisionReport/{companyId}/{fromDate}/{toDate}/{deptId}", method = RequestMethod.GET)
	public void generateProvisionReport(@PathVariable("companyId") String companyId,
			@PathVariable("fromDate") String fromdate, @PathVariable("toDate") String todate,
			@PathVariable("deptId") String deptId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {
		// logger.info("generate : "+" : processMonth "+ processMonth );
		System.out.println("generateProvisionReport" + "fromdate.." + fromdate + "..toDate..." + todate);
		String[] columns = { "Employee name", "Employee Code", "Bank Name", "Account No", "Net Amount", "Salary hold",
				"Department" };

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fromDate = inputFormat.parse(fromdate);
		Date toDate = inputFormat.parse(todate);
		System.out.println(fromDate);
		System.out.println(toDate);
		Long departmentId = Long.parseLong(deptId);
		Long longcompanyId = Long.parseLong(companyId);
		List<Object[]> provisionReportList = payrollReportService.findProvisionReport(longcompanyId, fromDate, toDate,
				departmentId);

		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor
				.objectListToProvisionReportList(provisionReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=provisionReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null && reportPayoutDtoList.size() > 0) {
					System.out.println("fromDate" + fromDate + "TDate" + toDate);
					Workbook workbook = PayrollExelWriter.provisionReport(reportPayoutDtoList, columns, fromDate,
							toDate, company, departmentId);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/payrollRegReport/{companyId}/{deptId}/{processMonth}", method = RequestMethod.GET)
	public void generatePayrollMonthlyReport(@PathVariable("companyId") String companyId,
			@PathVariable("deptId") String deptId, @PathVariable("processMonth") String processMonth,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {

		String[] columns = { "Particulars", "Employee Number", "Bank Name", "Account Number", "Date Of Joining",
				"Basic", "DA", "Conveyance Allowance", "Employee HRA", "Medical Allowance", "AdvanceBonus",
				"Special Allowance", "CompanyBenefits", "Other Allowance", "Total Gross Salary", "Absent",
				"Casual Leave", "Sick Leave", "Paid Leave", "Present", "Public Holiday", "Weekely Off",
				/* "OVERTIME", */"Payable Days", "Basic", "DA", "Conveyance Allowance", "Employee HRA",
				"Medical Allowance", "Advance Bonus", "Special Allowance", "Company Benefits", "Other Allowance",
				"Total Earnings", "Employee Loans & Advance A/C", "Provident Fund", "ESIC 1.75%", "PT", "TDS",
				"Total Deductions", "Net Amount" };

		// Department department =null;

		Long departmentId = Long.parseLong(deptId);
		Long longcompanyId = Long.parseLong(companyId);

		List<Object[]> monthlyReportList = payrollReportService.findPayrollReportByMonth(longcompanyId, departmentId,
				processMonth);

		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor
				.objectListToMonthlyReportList(monthlyReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=payrollReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null /* && reportPayoutDtoList.size()>0 */) {
					Workbook workbook = PayrollExelWriter.payrollMonthlyReport(reportPayoutDtoList, columns,
							processMonth, company, departmentId);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/payrollEmpReport/{companyId}/{fromProcessMonth}/{toProcessMonth}/{empId}", method = RequestMethod.GET)
	public void generatePayrollEmployeeReport(@PathVariable("companyId") String companyId,
			@PathVariable("fromProcessMonth") String fromProcessMonth,
			@PathVariable("toProcessMonth") String toProcessMonth, @PathVariable("empId") String empId,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {
		Long longcompanyId = Long.parseLong(companyId);
		System.out.println(("empploye based payroll Report controller"));
		String[] columns = { "Process Month", "Particulars", "Employee Number", "Bank Name", "Account Number",
				"Date Of Joining", "Basic", "DA", "Conveyance Allowance", "Employee HRA", "Medical Allowance",
				"AdvanceBonus", "Special Allowance", "CompanyBenefits", "Other Allowance", "Total Gross Salary",
				"Absent", "Casual Leave", "Sick Leave", "Paid Leave", "Present", "Public Holiday", "Weekely Off",
				/* "OVERTIME", */"Payable Days", "Basic", "DA", "Conveyance Allowance", "Employee HRA",
				"Medical Allowance", "Advance Bonus", "Special Allowance", "Company Benefits", "Other Allowance",
				"Total Earnings", "Employee Loans & Advance A/C", "Provident Fund", "ESIC 1.75%", "PT", "TDS",
				"Total Deductions", "Net Amount" };

		Long employeeId = Long.parseLong(empId);

		List<Object[]> monthlyReportList = payrollReportService.findPayrollReportByempId(longcompanyId, employeeId,
				fromProcessMonth, toProcessMonth);

		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor
				.objectListToEmpMonthlyReportList(monthlyReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=payrollEmployeeReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null && reportPayoutDtoList.size() > 0) {
					Workbook workbook = PayrollExelWriter.payrollMonthlyReportByEmpId(reportPayoutDtoList, columns,
							fromProcessMonth, toProcessMonth, company);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/bankReport/{companyId}/{processMonth}/{bankName}", method = RequestMethod.GET)
	public void generateBankReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable("bankName") String bankName,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, InvalidFormatException, IOException {
		System.out.println("generateBankReport processMonth" + processMonth + "bankName>>" + bankName);
		Long longcompanyId = Long.parseLong(companyId);
		String[] columns = { "Employee Name", "Employee Code", "Bank Name", "Account No", "Net Amount", "Department" };
		List<Object[]> bankTRFReportList = payrollReportService.findPayRollBankTRF(longcompanyId, processMonth,
				bankName);
		List<ReportPayOutDTO> reportPayOutDtoList = payrollReportAdaptor.objectListToBankReportList(bankTRFReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=bankReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);
				if (company != null /* && esi!=null && reportPayOutList!=null && reportPayOutList.size()>0 */) {
					Workbook workbook = PayrollExelWriter.bankReport(reportPayOutDtoList, columns, company,
							processMonth);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/reconciliationReport/{companyId}/{departmentId}/{processMonth}/{isReco}", method = RequestMethod.GET)
	public void generateRecoSummryReport(@PathVariable("companyId") String companyId,
			@PathVariable("departmentId") String departmentId, @PathVariable("processMonth") String processMonth,
			@PathVariable("isReco") String isReco, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, InvalidFormatException, IOException {
		System.out.println("generateRecoSummryReport processMonth" + processMonth + "departmentId>>" + departmentId);
		String[] columns = { "Employee Code", "Employee Name", "Bank Name", "Account No", "Net Amount",
				"Reconciliation Date", "Transaction No" };

		String[] columns1 = { "Employee Code", "Employee Name", "Department Name", "Bank Name", "Account No",
				"Net Amount", "Reconciliation Date", "Transaction No" };

		Long longDeptId = Long.parseLong(departmentId);
		Long longcompanyId = Long.parseLong(companyId);

		List<Object[]> recoReportList = null;
		String checkReco = null;
		String status = null;
		if (isReco.equals("RC")) {
			checkReco = "false";

		} else {
			checkReco = "true";

		}

		// Long count=
		// payrollReportService.checkForRecoReprotAvailability(longDeptId,processMonth,checkReco);
		// if(count>0) {
		recoReportList = payrollReportService.findReconciliationReport(longcompanyId, longDeptId, processMonth,
				checkReco);
		// }

		List<ReportPayOutDTO> reportPayOutDtoList = payrollReportAdaptor.objectListToRecoReportList(recoReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			if (checkReco.equals("true")) {
				response.setHeader("Content-Disposition", "attachment;filename=non-recoReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			}
			else {
				response.setHeader("Content-Disposition", "attachment;filename=recoReport.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			}

			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);
				if (company != null) {
					Workbook workbook;
					if (longDeptId == 0)
						workbook = PayrollExelWriter.reconciliationReport(reportPayOutDtoList, columns, company,
								processMonth, checkReco, longDeptId);
					else
						workbook = PayrollExelWriter.reconciliationReport(reportPayOutDtoList, columns1, company,
								processMonth, checkReco, longDeptId);

					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/epfEcrReport/{companyId}/{processMonth}", method = RequestMethod.GET)
	public void generateEPF_ECRReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {

		// String[] columns = { "UAN", "Member Name", "Gross_Wages", "EPF_Wages",
		// "EPS_Wages", "EDLI_Wages",
		// "EPF_CONTRI_REMITTED", "EPS_CONTRI_REMITTED", "EPF_EPS_Diff_Remitted",
		// "NCP_Days",
		// "Refund_Of_Advances" };dcs

		String[] columns = { "UAN", "Member Name", "Gross_Wages", "EPF_Wages", "EPS_Wages", "EDLI_Wages",
				"EPF_Contri_Remitted", "EPS_Contri_Remitted", "EPF_EPS_Diff_Remitted", "NCP_Days",
				"Refund_Of_Advances" };

		// Department department =null;

		// Long departmentId =Long.parseLong(deptId);
		Long longcompanyId = Long.parseLong(companyId);
		List<Object[]> EpfEcrList = payrollReportService.findEpfEcrReport(longcompanyId, processMonth);
		List<Object[]> total = payrollReportService.getTotal(longcompanyId, processMonth);

		List<Object[]> employeeCount = reportPayOutService.findPfWorkingConsultantReport(processMonth, longcompanyId);

		// List<Object[]> EpfList = payrollReportService.findEpfReport(longcompanyId,
		// processMonth);

		// EmployeeReportDTO empDto =
		// employeeReportService.countEMPIMPTODAYDATE(longcompanyId, " ");
		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor.objectListToEpfEcrReportList(EpfEcrList);

		// ReportSummaryDTO reportSummary =
		// payrollReportAdaptor.objectListToReportSummary(EpfList);

		// System.out.println(reportSummary.getEpfExcludedEmpCount() + "...." +
		// reportSummary.getEpfExcludedGrassSum());
		// System.out.println("employeeReportDTO.getEmpCount" + empDto.getEmpCount());
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=epfEcrReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null /* && reportPayoutDtoList.size()>0 */) {
					Workbook workbook = PayrollExelWriter.payrollEpfEcrReport(reportPayoutDtoList, columns,
							processMonth, company, employeeCount, total);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/esicEcrReport/{companyId}/{processMonth}", method = RequestMethod.GET)
	public void generateECI_ECRReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {
		Long longcompanyId = Long.parseLong(companyId);

		String[] columns = { "IP Number", "IP Name", "No of days", "Total Monthly Wages",
				"Reason Code for Zero workings days(numeric only; provide 0 for all other reasons)",
				"Last Working Day ( Format DD/MM/YYYY  or DD-MM-YYYY)" };

		List<Object[]> EsiEcrList = payrollReportService.findEsicEcrReport(longcompanyId, processMonth);
		List<Object[]> EsiCountList = payrollReportService.findEsicReport(longcompanyId, processMonth);
		// EmployeeReportDTO empDto =
		// employeeReportService.countEMPIMPTODAYDATE(longcompanyId, " ");
		ReportSummaryDTO reportSummary = payrollReportAdaptor.objectListToReportSummary(EsiCountList);
		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor.objectListToEsiEcrReportList(EsiEcrList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=esicEcrReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Company company = companyService.getCompany(longcompanyId);

				if (company != null && reportPayoutDtoList.size() > 0) {
					Workbook workbook = PayrollExelWriter.payrollEsicEcrReport(reportPayoutDtoList, columns,
							processMonth, company, reportSummary);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/earningDeductionReport/{companyId}/{processMonth}/{type}/{departmentDTOList}", method = RequestMethod.GET)
	public void generateEarningDeductionReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable("type") String type,
			@PathVariable List<Long> departmentDTOList, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {

		// String[] columns = { "Employee Code", "Employee Name", "Designation",
		// "Department", "Deduction Type",
		// "Amount", "Comment"};
		String[] columnsEarning = { "Employee Code", "Employee Name", "Designation", "Department", "Earning Type",
				"Amount", "Comment" };
		String[] columnsDeduction = { "Employee Code", "Employee Name", "Designation", "Department", "Deduction Type",
				"Amount", "Comment" };
		Long longcompanyId = Long.parseLong(companyId);

		List<Object[]> monthlyReportList = payrollReportService.findEarningDeductionForEmployee(longcompanyId,
				departmentDTOList, processMonth, type);
		List<OneTimeEarningDeductionDTO> oneTimeEarningDeductionDTOList = payrollReportAdaptor
				.databaseListToUIDtoList(monthlyReportList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=earningDeductionReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				if (type.equals("EA")) {
					Workbook workbook = PayrollExelWriter.earningDeductionReport(oneTimeEarningDeductionDTOList,
							columnsEarning, processMonth, type);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				} else if (type.equals("DE")) {
					Workbook workbook = PayrollExelWriter.earningDeductionReport(oneTimeEarningDeductionDTOList,
							columnsDeduction, processMonth, type);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/salarySheetReport/{companyId}/{processMonth}/{departmentDTOList}", method = RequestMethod.GET)
	public void generateSalarySheet(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable List<Long> departmentDTOList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {
		// @RequestBody List<Long> departmentId,

		// String[] columns = { "Employee Code", "Employee Name", "Designation",
		// "Department", "Date of Joining",
		// "Gender", "Job Location","Days In Month","Absent","Days Payable","Monthly
		// Gross","Basic",
		// "Conveyance Allowance","HRA","Medical Allowance","Advance Bonus","Special
		// Allowance","One Time Earnings",
		// "Earned Gross","PF Employee","ESI Employee","Professional Tax","Total Income
		// Tax","Loan EMI",
		// "One Time Deductions","Total Deductions","Net Pay","Bank","Account
		// Number","IFSC","Branch"};

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining", "Gender",
				"Job Location", "Days In Month", "Absent", "Days Payable", "Monthly Gross", };
		String[] totalGross = { "Earned Gross" };

		String[] column = { "Total Deductions", "Net Pay", "Bank", "Account Number", "IFSC", "Branch" };
		Long longcompanyId = Long.parseLong(companyId);

		String earnngPayHeads[] = payrollReportService.getEarnngPayHeads(longcompanyId);
		String deductionPayHeads[] = payrollReportService.getDeductionPayHeads(longcompanyId);
		String earnngPayHeadsNew[] = new String[earnngPayHeads.length];
		String earnngPayHeadsId[] = new String[earnngPayHeads.length];
		String deductionPayHeadsNew[] = new String[deductionPayHeads.length];
		String deductionPayHeadsId[] = new String[deductionPayHeads.length];

		for (int i = 0; i < earnngPayHeads.length; i++) {
			String columnName[] = earnngPayHeads[i].split("/");
			earnngPayHeadsNew[i] = columnName[0];
			earnngPayHeadsId[i] = columnName[1];
		}

		for (int i = 0; i < deductionPayHeads.length; i++) {
			String columnNames[] = deductionPayHeads[i].split("/");
			deductionPayHeadsNew[i] = columnNames[0];
			deductionPayHeadsId[i] = columnNames[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(earnngPayHeadsNew));
		list.addAll(Arrays.asList(totalGross));
		list.addAll(Arrays.asList(deductionPayHeadsNew));
		list.addAll(Arrays.asList(column));

		Object[] newColumns = list.toArray();
		List<Object[]> reportPayoutObj = payrollReportService.getSalarySheetData(longcompanyId, departmentDTOList,
				processMonth);

		List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
				.objectListToMonthlySalarySheetReportList(reportPayoutObj);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=salarySheetReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.SalarySheetReport(reportPayOutDTOList, newColumns, processMonth,
						earnngPayHeadsId, deductionPayHeadsId);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Professional Tax Statement Monthly
	@RequestMapping(path = "/ptMonthlyReport/{companyId}/{processMonth}/{wise}", method = RequestMethod.GET)
	public void generateProfessionalTaxStatementMonthlyReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable("wise") String wise,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columnsEmployeWise = { "Sr. No", "Employee Code", "Employee Name", "Job Location", "State",
				"Prof Tax Gross", "Amount" };
		String[] columnsStatewise = { "State", "Total Employees", "Total Gross Amount", "Total PT Amount" };

		Long longcompanyId = Long.parseLong(companyId);
		List<Object[]> professionalTaxStatementMonthlyList = payrollReportService
				.findProfessionalTaxStatementMonthly(longcompanyId, processMonth, wise);
		List<ReportPayOutDTO> reportPayOutDTO = payrollReportAdaptor
				.databaseListToUIDtoPTReports(professionalTaxStatementMonthlyList, wise);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=PTStatementMonthlyReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				if (wise.equalsIgnoreCase("EW")) {
					Workbook workbook = PayrollExelWriter.professionalTaxStatementMonthlyEmployeeWiseReport(
							reportPayOutDTO, columnsEmployeWise, processMonth, wise);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				} else if (wise.equalsIgnoreCase("SW")) {
					Workbook workbook = PayrollExelWriter.professionalTaxStatementMonthlyStateWiseReport(
							reportPayOutDTO, columnsStatewise, processMonth, wise);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author ${Mayuri}
	 *
	 */
	// Arrears report
	@RequestMapping(path = "/arrearReport", method = RequestMethod.GET)
	public void generateArrearMonthlyReport(@RequestParam Long companyId,
			@RequestParam(required = false) List<Long> departmentDTOList,
			@RequestParam(required = false) String processMonth, @RequestParam(required = false) Long financialYearId,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining", "Gender",
				"Job Location", "Amount" };
		String[] columns1 = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining",
				"Gender", "Job Location", "Amount", "Paid On" };

		// Long longcompanyId = Long.parseLong(companyId);
		// Long financialYrId =Long.valueOf(financialYearId);

		try {

			if (processMonth != null && departmentDTOList != null) {
				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=arrearMonthlyReport.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			} else {
				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=arrearAnuallyReport.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			}

			if (companyId != null) {
				if (processMonth != null && departmentDTOList != null) {
					List<Object[]> reportPayoutObj = payrollReportService.getArrearMonthlyReport(companyId,
							departmentDTOList, processMonth);
					List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
							.databaseModelToUIDtoList(reportPayoutObj);
					Workbook workbook = PayrollExelWriter.ArrearReport(reportPayOutDTOList, columns, processMonth);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				} else if (financialYearId != null) {

					List<Object[]> reportPayoutObject = payrollReportService.getArrearAnnualyReport(companyId,
							financialYearId);
					FinancialYear financialYear = payrollReportService.findFinancialYear(companyId, financialYearId);
					List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
							.databaseModelToUIDtoList(reportPayoutObject);
					Workbook workbook = PayrollExelWriter.ArrearReport(reportPayOutDTOList, columns1,
							financialYear.getFinancialYear());
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	// pfExitReport
	@RequestMapping(path = "/pfExitReport/{companyId}/{flag}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void generatePfExitStatementReport(@PathVariable("companyId") String companyId,
			@PathVariable("flag") Long flag, @PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String columns[] = { "Sr No", "Employee Code", "UAN No", "Employee Name", "PF JOINING", "PF EXIT" };
		Long longcompanyId = Long.parseLong(companyId);

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate1 = dateFormat.parse(fromDate);
		Date toDate1 = dateFormat.parse(toDate);

		List<Object[]> PFReportList = payrollReportService.getPFExitStatementReport(longcompanyId, fromDate1, toDate1);
		List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor.databaseListToUIDtoReports(PFReportList, flag);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=pfExitStatementReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.PFExitReport(reportPayOutDTOList, columns, fromDate, toDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */
	// esiExitReport
	@RequestMapping(path = "/esiExitReport/{companyId}/{flag}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void generateESIExitStatementReport(@PathVariable("companyId") String companyId,
			@PathVariable("flag") Long flag, @PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String columns[] = { "Sr No", "Employee Code", "ESIC No", "Name Of Employee", "ESI JOINING", "ESI EXIT" };
		Long longcompanyId = Long.parseLong(companyId);

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate1 = dateFormat.parse(fromDate);
		Date toDate1 = dateFormat.parse(toDate);

		List<Object[]> PFReportList = payrollReportService.getESIExitStatementReport(longcompanyId, fromDate1, toDate1);
		List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor.databaseListToUIDtoReports(PFReportList, flag);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=esiExitStatementReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.ESIExitReport(reportPayOutDTOList, columns, fromDate, toDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else

				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */
	@RequestMapping(path = "/recordPaymentReport/{companyId}/{processMonth}/{departmentDTOList}", method = RequestMethod.GET)
	public void generatePaymentRecordStatementReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable List<Long> departmentDTOList,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String columns[] = { "Employee Code", "Employee Name", "Bank", "Account Number", "IFSC", "Net Pay",
				"Transaction ID", "Payment Mode", "Updated On" };
		Long longcompanyId = Long.parseLong(companyId);

		List<Object[]> paymentRecordList = payrollReportService.getPaymentRecordStatementReport(longcompanyId,
				processMonth, departmentDTOList);

		List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor.databaseListToModelDto(paymentRecordList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=recordPaymentReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.PaymentRecordStatementReport(reportPayOutDTOList, columns,
						processMonth);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else

				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */

	@RequestMapping(path = "/esiArrearEcr/{companyId}/{processMonth}", method = RequestMethod.GET)
	public void generateESIArrearECRReport(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String columns[] = { "IP Number", "IP Name", "No Of days", "Total Monthly Wages",
				"Reason Code for Zero workings days", "Last Working Day" };
		Long longcompanyId = Long.parseLong(companyId);

		List<Object[]> esiArrearEcrList = payrollReportService.getESIArrearECRReport(longcompanyId, processMonth);
		List<Object[]> esicExcludedEmployeeList = payrollReportService.getEsicExcludedEmployee(longcompanyId,
				processMonth);
		Company company = companyService.getCompany(longcompanyId);
		Esi esi = esicService.getESI(longcompanyId);
		List<ArrearReportPayOutDTO> arrearReportPayOutList = payrollReportAdaptor
				.databaseListToUIDtoArrearReport(esiArrearEcrList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=esiArrearEcr.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.EsiArrearEcrReport(arrearReportPayOutList,
						esicExcludedEmployeeList, columns, processMonth, company, esi);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else

				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// Current salary Structure Or PayStructure Report

	@RequestMapping(path = "/payStructureReport", method = RequestMethod.GET)
	public void generatePayStructureReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam(required = false) String status, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "Code", "Employee Name", "Designation", "Department", "Grade", "Employment Type",
				"Employment Status", "Salary Effective From", "Last Updated On", "Gross" };
		String[] column = { "PF Employee", "PF Employer", "EPS Employer", "ESI Employee", "ESI Employer",
				"LWF Employee Contribution", "LWF Employer Contribution", "Professional Tax", "Net Pay" };

		String currentPayHeads[] = payrollReportService.getcurrentPayHeads(companyId);
		String currentPayHeadsNew[] = new String[currentPayHeads.length];
		String currentPayHeadsId[] = new String[currentPayHeads.length];

		for (int i = 0; i < currentPayHeads.length; i++) {
			String columnName[] = currentPayHeads[i].split("/");
			currentPayHeadsNew[i] = columnName[0];
			currentPayHeadsId[i] = columnName[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(currentPayHeadsNew));
		list.addAll(Arrays.asList(column));

		Object[] newColumns = list.toArray();

		List<Object[]> payStructureList = payrollReportService.getPayStructureReport(companyId, employeeId,
				departmentList, status);

		List<PayStructureHdDTO> payStructureHdList = new ArrayList<PayStructureHdDTO>();

		if (payStructureList.isEmpty() == false)
			payStructureHdList = payrollReportAdaptor.objectListToPayStructureReport(payStructureList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=payStructureSalarySheetReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = PayrollExelWriter.payStructureExcelWriterReport(payStructureHdList, newColumns,
						currentPayHeadsId, status);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/// currentCostToCompanyReport
	@RequestMapping(path = "/currentCostToCompanyReport", method = RequestMethod.GET)
	public void currentCostToCompanyReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam(required = false) String status, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "Employee Number", "Employee Name", "Designation", "Department", "Grade",
				"Employment Type", "Employment Status", "Salary Effective From", "Last Updated On", "CTC", "Gross", };

//		String earnngPayHeads[] = payrollReportService.getAllEarnngPayHeads(companyId);
		String earnngPayHeads[] = payrollReportService.getcurrentPayHeads(companyId);
		// String deductionPayHeads[] =
		// payrollReportService.getAllDeductionPayHeads(companyId);
		String[] column = { "PF Employee", "PF Employer", "EPS Employer", "PF Admin Charge", "ESI Employee",
				"ESI Employer", "LWF Employee Contribution", "LWF Employer Contribution", "Professional Tax",
				"Net Pay" };
		String earnngPayHeadsNew[] = new String[earnngPayHeads.length];
		String earnngPayHeadsId[] = new String[earnngPayHeads.length];
//		String deductionPayHeadsNew[] = new String[deductionPayHeads.length];
//		String deductionPayHeadsId[] = new String[deductionPayHeads.length];

		for (int i = 0; i < earnngPayHeads.length; i++) {
			String columnName[] = earnngPayHeads[i].split("/");
			earnngPayHeadsNew[i] = columnName[0];
			earnngPayHeadsId[i] = columnName[1];
		}

//		for (int i = 0; i < deductionPayHeads.length; i++) {
//			String columnNames[] = deductionPayHeads[i].split("/");
//			deductionPayHeadsNew[i] = columnNames[0];
//			deductionPayHeadsId[i] = columnNames[1];
//		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(earnngPayHeadsNew));
		// list.addAll(Arrays.asList(totalGross));
		// list.addAll(Arrays.asList(deductionPayHeadsNew));
		// list.addAll(Arrays.asList(column));
		list.addAll(Arrays.asList(column));

		Object[] newColumns = list.toArray();
		List<Object[]> reportPayoutObj = payrollReportService.getcurrentCostToCompanyReport(companyId, employeeId,
				departmentList, status);

		List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
				.objectListToCurrentCostToCompanyReport(reportPayoutObj);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=currentCostToCompanyReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = PayrollExelWriter.currentCostToCompanyWriter(reportPayOutDTOList, newColumns,
						earnngPayHeadsId);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Income Tax Monthly

	@RequestMapping(value = "/incomeTaxReport", method = RequestMethod.GET)
	public void incomeTaxReport(@RequestParam Long companyId, @RequestParam(required = false) String processMonth,
			@RequestParam(required = false) List<Long> departmentIds,
			@RequestParam(required = false) Long financialYearId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, IOException {

		String[] columns = { " Sl No ", "Employee Code", " Employee ", " Date Joined ", " PAN Number ", " Gross ",
				" Tax ", "Health and Education Cess ", "Total Income Tax" };

		String[] one = { " Sl No ", "Employee Code", " Employee ", " Date Joined ", " PAN Number " };
		String[] two = payrollReportService.getMonthByFinId(financialYearId);

		Integer oneLen = one.length;
		Integer twoLen = two.length;

		String[] result = new String[oneLen + twoLen];

		System.arraycopy(one, 0, result, 0, oneLen);
		System.arraycopy(two, 0, result, oneLen, twoLen);

		try {

			if (companyId > 0) {
				if (processMonth != null && departmentIds.size() > 0) {
					List<Object[]> incomeTaxMonthlyObj = payrollReportService.getIncomeTaxMonthlyReport(companyId,
							departmentIds, processMonth);
					List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
							.databaseModelToIncomeTaxMonthlyList(incomeTaxMonthlyObj, financialYearId);

					response.setContentType(
							"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					response.setHeader("Content-Disposition", "attachment;filename=IncomeTaxMonthlyReport.xlsx");
					response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

					Workbook workbook = PayrollExelWriter.incomeTaxMonthlyReportWriter(reportPayOutDTOList, columns,
							processMonth, financialYearId);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				} else if (financialYearId > 0) {

					List<Object[]> reportPayoutObject = payrollReportService.getIncomeTaxAnnualyReport(companyId,
							financialYearId);
					FinancialYear financialYear = payrollReportService.findFinancialYear(companyId, financialYearId);
					List<ReportPayOutDTO> reportPayOutDTOList = payrollReportAdaptor
							.databaseModelToIncomeTaxMonthlyList(reportPayoutObject, financialYearId);
					response.setHeader("Content-Disposition", "attachment;filename=incomeTaxAnuallyReport.xlsx");
					response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
					Workbook workbook = PayrollExelWriter.incomeTaxMonthlyReportWriter(reportPayOutDTOList, result,
							financialYear.getFinancialYear(), financialYearId);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(path = "/rentPaidAndLandlordDetails/{companyId}/{financialYearId}/{financialYear}", method = RequestMethod.GET)
	public void getRentPaidAndLandlordDetails(@PathVariable("companyId") Long companyId,
			@PathVariable("financialYearId") Long financialYearId, @PathVariable("financialYear") String financialYear,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { " Employee Code ", " Employee Name ", " From Month ", " To Month ", " Address ", " City ",
				" Rent Paid ", " LandLord Name ", " LandLord Address ", " LandLord Pan " };

		List<Object[]> rentPaidDetailsObj = payrollReportService.getRentPaidLandlordDetailsReport(companyId,
				financialYearId);

		List<TdsHouseRentInfoDTO> rentPaidDetailsList = payrollReportAdaptor
				.objectListToRentPaidDetailsReport(rentPaidDetailsObj);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Rent Paid & Landlord Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Workbook workbook = PayrollExelWriter.rentPaidAndLandLordReportWriter(rentPaidDetailsList, columns,
						financialYear);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Labour Welfare Fund Summary
	@RequestMapping(path = "/labourWelfareReport/{companyId}/{processMonth}/{wise}", method = RequestMethod.GET)
	public void generateLabourWelfareFundSummaryReport(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") String processMonth, @PathVariable("wise") String wise,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columnsEmployeWise = { "Sr. No", "Employee Code", "Employee Name", "Job Location", "State",
				"Standard Gross", "LWF Employee Contr.", "LWF Employer Contr." };
		String[] columnsStatewise = { "State", "Total Employees", "Total Gross Amount", "LWF Employee Contri",
				"LWF Employer Contri" };

		List<Object[]> labourWelfareFundMonthlyList = payrollReportService
				.findLabourWelfareFundSummaryMonthly(companyId, processMonth, wise);
		List<ReportPayOutDTO> reportPayOutDTO = payrollReportAdaptor
				.databaseListToUIDtoLWFReports(labourWelfareFundMonthlyList, wise);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=LabourWelfareFundSummaryMonthlyReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (wise.equalsIgnoreCase("EW")) {
				Workbook workbook = PayrollExelWriter.labourWelfareFundReport(reportPayOutDTO, columnsEmployeWise,
						processMonth, wise);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else if (wise.equalsIgnoreCase("SW")) {
				Workbook workbook = PayrollExelWriter.labourWelfareFundReport(reportPayOutDTO, columnsStatewise,
						processMonth, wise);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/salarySheetAnnualReport/{companyId}/{financialYearId}/{financialYear}", method = RequestMethod.GET)
	public void generateSalarySheetAnnual(@PathVariable("companyId") String companyId,
			@PathVariable("financialYearId") String financialYearId,
			@PathVariable("financialYear") String financialYear, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] summaryColumns = { "Month", "Gross", "Basic", "Other", "Net Payable", "One Time Earnings",
				"PF Employee", "PF Employer", "EPS Employer", "PF Admin Charge", "ESI Employee", "ESI Employer",
				"LWF Employee", "LWF Employer", "Professional Tax", "TDS", "Loan", "Total Deductions", "CTC" };

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining", "Gender",
				"Job Location", "Days In Month", "Absent", "Days Payable", "Monthly Gross", };
		String[] totalGross = { "Earned Gross" };

		String[] column = { "Total Deductions", "Net Pay", "Bank", "Account Number", "IFSC", "Branch" };
		Long longcompanyId = Long.parseLong(companyId);
		Long financialYearIds = Long.parseLong(financialYearId);

		String earnngPayHeads[] = payrollReportService.getEarnngPayHeads(longcompanyId);
		String deductionPayHeads[] = payrollReportService.getDeductionPayHeads(longcompanyId);
		String earnngPayHeadsNew[] = new String[earnngPayHeads.length];
		String earnngPayHeadsId[] = new String[earnngPayHeads.length];
		String deductionPayHeadsNew[] = new String[deductionPayHeads.length];
		String deductionPayHeadsId[] = new String[deductionPayHeads.length];

		for (int i = 0; i < earnngPayHeads.length; i++) {
			String columnName[] = earnngPayHeads[i].split("/");
			earnngPayHeadsNew[i] = columnName[0];
			earnngPayHeadsId[i] = columnName[1];
		}

		for (int i = 0; i < deductionPayHeads.length; i++) {
			String columnNames[] = deductionPayHeads[i].split("/");
			deductionPayHeadsNew[i] = columnNames[0];
			deductionPayHeadsId[i] = columnNames[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(earnngPayHeadsNew));
		list.addAll(Arrays.asList(totalGross));
		list.addAll(Arrays.asList(deductionPayHeadsNew));
		list.addAll(Arrays.asList(column));

		List<Object[]> annualSummary = payrollReportService.getAnnualSummary(longcompanyId, financialYearIds);
		List<Object[]> reportPayoutObj = payrollReportService.getSalarySheetAnnual(longcompanyId, financialYearIds);
		List<ReportPayOutDTO> annualSummaryList = payrollReportAdaptor.annualSummary(annualSummary);
		Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap = payrollReportAdaptor
				.objectListToSalarySheetAnnualReport(reportPayoutObj, annualSummary);

		Object[] newColumns = list.toArray();
		// Long empId = Long.parseLong(employeeId);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=SalarySheetAnnualReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (longcompanyId != null) {
				Workbook workbook = PayrollExelWriter.SalarySheetAnnualReport(reportPayOutDtoMap, annualSummaryList,
						newColumns, earnngPayHeadsId, deductionPayHeadsId, summaryColumns, financialYear);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// professional tax statement Annual Report

	@RequestMapping(path = "/ptAnnualReport/{companyId}/{financialYearId}/{wise}/{financialYear}", method = RequestMethod.GET)
	public void generateProfessionalTaxStatementAnnualReport(@PathVariable("companyId") Long companyId,
			@PathVariable("financialYearId") Long financialYearId, @PathVariable("wise") String wise,
			@PathVariable("financialYear") String financialYear, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columnsEmployeWise = { "Sr. No", "Employee Code", "Employee Name", "Job Location", "State",
				"Prof Tax Gross", "Amount" };
		String[] columnsStatewise = { "State", "Total Employees", "Total Gross Amount", "Total PT Amount" };

		List<Object[]> processMonthList = payrollReportService.findProcessMonth(companyId, financialYearId, wise);

		List<Object[]> ptStatementAnnualSummaryList = payrollReportService
				.findProfessionalTaxStatementAnnualSummary(companyId, financialYearId, wise);

		List<Object[]> ptStatementAnnualMonthlyList = payrollReportService
				.findProfessionalTaxStatementAnnualMonthly(companyId, financialYearId, wise);

		List<ReportPayOutDTO> annualSummaryList = payrollReportAdaptor
				.databaseListToUIDtoPTReports(ptStatementAnnualSummaryList, wise);

		Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap = payrollReportAdaptor
				.databaseListToUIDtoPTMonthlyReports(ptStatementAnnualMonthlyList, processMonthList, wise);

		List<ReportPayOutDTO> monthList = payrollReportAdaptor.databaseListToUIDtoProcessMonth(processMonthList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=PTStatementAnnualReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				if (wise.equalsIgnoreCase("EW")) {
					Workbook workbook = PayrollExelWriter.pTAnnualMonthlyEmployeeWiseReport(reportPayOutDtoMap,
							annualSummaryList, columnsEmployeWise, financialYearId, monthList, financialYear);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				} else if (wise.equalsIgnoreCase("SW")) {
					Workbook workbook = PayrollExelWriter.pTAnnualMonthlyStateWiseReport(reportPayOutDtoMap,
							annualSummaryList, columnsStatewise, financialYearId, monthList, financialYear);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
				}
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// PF Contribution Statement Report

	@RequestMapping(path = "/annualPFContributionReport/{companyId}/{financialYearId}/{financialYear}", method = RequestMethod.GET)
	public void generateAnnualPFContribution(@PathVariable("companyId") Long companyId,
			@PathVariable("financialYearId") Long financialYearId, @PathVariable("financialYear") String financialYear,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] summaryColumns = { "UAN", "Member Name", "Gross_Wages", "EPF_Wages", "EPS_Wages", "EDLI_Wages",
				"EPF_Contri_Remitted", "EPS_Contri_Remitted", "EPF_EPS_Diff_Remitted", "NCP_Days",
				"Refund_Of_Advances" };

		String[] newColumns = { "Month", "Gross_Wages", "EPF_Wages", "EPS_Wages", "EDLI_Wages", "EPF_Contri_Remitted",
				"EPS_Contri_Remitted", "EPF_EPS_Diff_Remitted", "NCP_Days", "Refund_Of_Advances" };

		List<Object[]> pfAnnualList = payrollReportService.getPFControAnnual(companyId, financialYearId);
		List<ReportPayOutDTO> annualSummaryList = payrollReportAdaptor.uiDTOtoPfAnnualList(pfAnnualList);
		List<Object[]> pfMonthlyList = payrollReportService.getPFControMonthly(companyId, financialYearId);

		List<Object[]> total = payrollReportService.getTotalPFControbution(companyId, financialYearId);

		Map<String, List<ReportPayOutDTO>> reportPayOutDtoMap = payrollReportAdaptor
				.objectListToPFControAnnualReport(pfMonthlyList, pfAnnualList);

		List<Object[]> employeeCount = reportPayOutService.findPfWorkingConsultantReportMonthly(financialYearId,
				companyId);
		List<Object[]> totalMonthly = payrollReportService.getTotalMonthly(companyId, financialYearId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Annual_PF_Contribution_Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {
				Company company = companyService.getCompany(companyId);
				Workbook workbook = PayrollExelWriter.annualPFContributionReport(reportPayOutDtoMap, annualSummaryList,
						summaryColumns, company, newColumns, financialYear, total, employeeCount, totalMonthly);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

@RequestMapping(path = "/employeePFContibution/{companyId}/{employeeId}/{activeStatus}", method = RequestMethod.GET)
	public void generatePFContributionEmployeeWise(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @PathVariable("activeStatus") String activeStatus,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException {

		String[] columns = { " Month ", " Earned Gross ", " EPF Wages ", " EPS_Wages ", "Employee Share",
				"Employer Share", "Pension Contribution", "Total Contribution", "NCP Days" };

		List<Object[]> employeePFControList = payrollReportService.getPFContributionEmpWise(companyId, employeeId,
				activeStatus);

		List<Object[]> total = payrollReportService.getTotalOfEmployee(companyId, employeeId, activeStatus);

		List<Object[]> employeeCount = reportPayOutService.getEmployeePFInfo(companyId, employeeId, activeStatus);

		List<ReportPayOutDTO> reportPayoutDtoList = payrollReportAdaptor
				.objectListToEmployeePFControList(employeePFControList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=PF_Contribution_Employee_Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			if (companyId != null) {

				Workbook workbook = PayrollExelWriter.employeePFContributionReport(reportPayoutDtoList, columns,
						employeeCount, total, activeStatus);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);

			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

     // generate Payment transfer sheet report

     @RequestMapping(path = "/paymentTransferSheet/{financialYearId}/{processMonth}/{departmentId}", method = RequestMethod.GET)
	public void getPaymentTransferSheetReport(@PathVariable("financialYearId") Long financialYearId,
			@PathVariable("processMonth") String processMonth, @PathVariable("departmentId") Long departmentId,
			HttpServletRequest req, HttpServletResponse response) throws PayRollProcessException, ErrorHandling {
		logger.info(" getPaymentTransferSheetReport for  : " + financialYearId);

		List<Object[]> payRegisterList = payrollReportService.getPaymentTransferSheet(financialYearId, processMonth,
				departmentId);
		logger.info(" payRegisterList  " + payRegisterList);
		List<ReportPayOutDTO> payRegisterDtoList = payrollReportAdaptor.paymentTransferSheetReport(payRegisterList,
				processMonth);

		if (payRegisterList.size() > 0) {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=BankPaymentTransferSheet.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
			String[] columns = { "BANK NAME", "HEAD COUNT", "TOTAL NET PAY" };
			String[] sheet2Columns = { "S No.", "EmpNo", "Name", "Department", "Bank", "IFSC Code", "Bank Account No",
					"Amount", "Status" };
			Workbook workbook;
			try {
				workbook = PayRegisterExcelWriter.paymentTransferSheet(payRegisterDtoList, columns, sheet2Columns,
						processMonth);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} catch (InvalidFormatException | IOException e) {

				e.printStackTrace();
			}
		} else {
			throw new ErrorHandling("Invalid session .Please login again");
		}

	}
}
