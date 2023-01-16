package com.csipl.hrms.payroll.controller;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.FinalSettlementDTO;
import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.common.City;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeAsset;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.employee.Separation;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.model.payroll.FinalSettlement;
import com.csipl.hrms.model.payroll.FinalSettlementReport;
import com.csipl.hrms.model.payroll.Gratuaty;
import com.csipl.hrms.model.payroll.HoldSalary;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.adaptor.FinalSettlementAdaptor;
import com.csipl.hrms.service.adaptor.HoldSalaryAdaptor;
import com.csipl.hrms.service.adaptor.LoanEmiAdaptor;
import com.csipl.hrms.service.adaptor.LoanIssueAdaptor;
import com.csipl.hrms.service.adaptor.ReportPayOutAdaptor;
import com.csipl.hrms.service.common.CityService;
import com.csipl.hrms.service.employee.EmployeeAssetService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.employee.SeparationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.GradeService;
import com.csipl.hrms.service.payroll.FinalSettlementService;
import com.csipl.hrms.service.payroll.GratuityService;
import com.csipl.hrms.service.payroll.HoldSalaryService;
import com.csipl.hrms.service.payroll.LoanIssueService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.hrms.service.util.SalaryPdfReport;
import com.csipl.tms.leave.service.EmployeeLeaveService;

@RestController
class FinalSettlementController {

	@Autowired
	FinalSettlementService finalSettlementService;

	@Autowired
	SeparationService separationService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	GratuityService gratuityService;

	@Autowired
	GradeService gradeService;

	@Autowired
	ReportPayOutService reportPayOutService;

	@Autowired
	private org.springframework.core.env.Environment environment;

	@Autowired
	HoldSalaryService holdSalaryService;

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	EmployeeAssetService employeeAssetService;

	@Autowired
	CompanyService companyService;

	@Autowired
	CityService cityService;

	@Autowired
	EmployeeLeaveService employeeLeaveService;
	
	@Autowired
	ReportPayOutRepository reportPayOutRepository;

	LoanIssueAdaptor loanIssueAdaptor = new LoanIssueAdaptor();
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	LoanEmiAdaptor loanEmiAdaptor = new LoanEmiAdaptor();
	HoldSalaryAdaptor holdSalaryAdaptor = new HoldSalaryAdaptor();
	ReportPayOutAdaptor reportPayOutAdaptor = new ReportPayOutAdaptor();

	@Autowired
	LoanIssueService loanIssueService;

	private static final Logger logger = LoggerFactory.getLogger(Employee.class);

	FinalSettlementAdaptor finalSettlementAdaptor = new FinalSettlementAdaptor();

	@RequestMapping(value = "/finalSettlementEmployee/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> finalSettlementEmployee(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {

		List<Object[]> employees = finalSettlementService.getEmployees(companyId);
		List<EmployeeDTO> employeeDTOList = finalSettlementAdaptor.databaseModelObjtoUiDTO(employees);
		return employeeDTOList;
	}

	@RequestMapping(path = "/finalSettlementLoan", method = RequestMethod.GET)
	public @ResponseBody List<LoanIssueDTO> getMyLoanInfo(@RequestParam("empolyeeId") Long empolyeeId,
			HttpServletRequest req) {

		List<LoanIssueDTO> loanIssueDTOList = new ArrayList<LoanIssueDTO>();

		BigDecimal pendingAmount = BigDecimal.ZERO;
		logger.info("--------------------------------------getMyLoanInfo is :----------------------------------"
				+ empolyeeId);
		// Long empId = Long.parseLong(empolyeeId);

		List<LoanIssue> loanIssueList = loanIssueService.getMyLoanInfo(empolyeeId);

		loanIssueDTOList = loanIssueAdaptor.databaseModelToUiDtoList(loanIssueList);

		for (LoanIssueDTO loanIssueDTO : loanIssueDTOList) {
			pendingAmount = pendingAmount.add(loanIssueDTO.getLoanPendingAmount());
		}

		logger.info("--------------------------------------getMyLoanInfoList is :----------------------------------"
				+ pendingAmount);
		return loanIssueDTOList;
	}

	@RequestMapping(path = "/employeeInformation/{employeeId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody EmployeeDTO getGratuity(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Grade grade = gradeService.findGradeDetails(employee.getGradesId());
		Separation separation = separationService.getSeparationInfo(employeeId);
		employeeDTO = employeePersonalInformationAdaptor.databaseModelToUiDto(employee);
		employeeDTO.setGradeName(grade.getGradesName());
		employeeDTO.setNoticeReason(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.ResignationReason.getDropDownName(), separation.getResoan()));
		employeeDTO.setNoticeDate(separation.getDateCreated().toString());
		employeeDTO.setEndDate(separation.getEndDate());
		employeeDTO.setExitDate(separation.getExitDate());
//		employeeDTO.setNoticeReason(separation.getResoan());
		String reason = separation.getResoan();
		System.out.println("reason@@@@@@@@========>>>" + reason);
		if (separation.getResoan().equals("CE"))
			employeeDTO.setNoticeReason("Career Growth");
		if (separation.getResoan().equals("IC"))
			employeeDTO.setNoticeReason("Issue with Company");
		if (separation.getResoan().equals("MI"))
			employeeDTO.setNoticeReason("Medical Issue");
		if (separation.getResoan().equals("OT"))
			employeeDTO.setNoticeReason("Other");
		if (separation.getResoan().equals("PI"))
			employeeDTO.setNoticeReason("Personal Issue");
		if (separation.getResoan().equals("AB")) {
			System.out.println("iffffffffff@@@@@@@@========>>>" + reason);
			employeeDTO.setNoticeReason("Absconded");
		}

		if (separation.getResoan().equals("RE"))
			employeeDTO.setNoticeReason("Resigned");
		if (separation.getResoan().equals("TE"))
			employeeDTO.setNoticeReason("Terminated");
		long difference;
		long daysBetween;
		if (separation.getExitDate().getTime() < separation.getDateCreated().getTime()) {
			difference = separation.getExitDate().getTime() - separation.getDateCreated().getTime();
			daysBetween = (difference / (1000 * 60 * 60 * 24));
		} else {
			difference = separation.getEndDate().getTime() - separation.getExitDate().getTime();
			daysBetween = (difference / (1000 * 60 * 60 * 24));
		}

		// daysBetween = (difference / (1000 * 60 * 60 * 24));
		employeeDTO.setShortFallDays(daysBetween);
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(employee.getDateOfJoining());
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date());

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		employeeDTO.setWorkingMonths(diffMonth);
		Gratuaty gratuaty = gratuityService.getAllGratuity(companyId);

		logger.info("-------------------------------------- gratuaty Month:----------------------------------"
				+ gratuaty.getNoOfMonths());
		if (diffMonth >= gratuaty.getNoOfMonths()) {
			logger.info("-------------------------------------- gratuaty exists:----------------------------------");
			BigDecimal gratuatyAmount = finalSettlementService.calculateGratuity(gratuaty, employee);
			logger.info("-------------------------------------- gratuaty exists:----------------------------------"
					+ gratuatyAmount);
			employeeDTO.setGratuatyAmount(gratuatyAmount);

		}
		logger.info("-------------------------------------- gratuaty Amount:----------------------------------"
				+ employeeDTO.getGratuatyAmount());

		return employeeDTO;
	}

	@RequestMapping(path = "/leaveEncashment/{employeeId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<com.csipl.tms.dto.leave.LeaveBalanceSummryDTO> leaveEncashment(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) {
		logger.info(
				"-------------------------------------- leaveEncashment calling:----------------------------------employeeId"
						+ employeeId + "companyId" + companyId);
		String url = environment.getProperty("application.leaveBalanceSummary");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Long> params = new HashMap<>();
		params.put("employeeId", employeeId);
		params.put("companyId", companyId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		//List<LeaveBalanceSummryDTO> leaveBalanceSummryDTOList = Arrays
		//		.asList(restTemplate.getForObject(url, LeaveBalanceSummryDTO[].class, params));
		List<com.csipl.tms.dto.leave.LeaveBalanceSummryDTO> leaveBalanceSummryDTOList = employeeLeaveService.getEmployeeLeaveBalanceSummry(employeeId,companyId);
		List<com.csipl.tms.dto.leave.LeaveBalanceSummryDTO> leaveBalanceSummryList = new ArrayList<com.csipl.tms.dto.leave.LeaveBalanceSummryDTO>();
		// Date date = Calendar.getInstance().getTime();
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String actualDate = dateFormat.format(date);
		// logger.info("--------------------------------------
		// actualDate----------------------------------" +actualDate);
		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd",
		// Locale.ENGLISH);
		// DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM-yyyy",
		// Locale.ENGLISH);
		// LocalDate ld = LocalDate.parse(actualDate, dtf);
		// String month_name = dtf2.format(ld);

		YearMonth yearMonthObject = YearMonth.of(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH) + 1);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		logger.info(
				"-------------------------------------- daysInMonth----------------------------------" + daysInMonth);
		// ReportPayOut
		// reportPayOut=reportPayOutService.findEmployeePayOutPdf(employee.getEmployeeCode(),month_name,
		// companyId);
		PayStructureHd payStructureHd = payStructureService.employeeCurrentPayStructure(employeeId);
		BigDecimal perDayEarning = payStructureHd.getGrossPay().divide(new BigDecimal(daysInMonth), 2,
				RoundingMode.HALF_UP);
		if(perDayEarning== null) {
			perDayEarning =BigDecimal.valueOf(0l);
		}
		
		// BigDecimal
		// netPay=reportPayOut.getTotalEarning().subtract(reportPayOut.getTotalDeduction());
		// BigDecimal perDayEarning=netPay.divide(reportPayOut.getPayDays(),2,
		// RoundingMode.HALF_UP);

		for (com.csipl.tms.dto.leave.LeaveBalanceSummryDTO leaveBalanceSummryDTO : leaveBalanceSummryDTOList) {
			logger.info("-------------------------------------- Encash Nature:----------------------------------"
					+ leaveBalanceSummryDTO.getNature());
			if (leaveBalanceSummryDTO.getNature().equals(StatusMessage.isEncash)) {
				logger.info(
						"-------------------------------------- getLeaveBalanceCount----------------------------------"
								+ leaveBalanceSummryDTO.getLeaveBalancedCount());
				logger.info("-------------------------------------- EncashLimit----------------------------------"
						+ leaveBalanceSummryDTO.getEncashLimit());
				if (leaveBalanceSummryDTO.getLeaveBalancedCount()
						.compareTo(new BigDecimal(leaveBalanceSummryDTO.getEncashLimit())) == 1)
					leaveBalanceSummryDTO.setEncashableAmount(
							new BigDecimal(leaveBalanceSummryDTO.getEncashLimit()).multiply(perDayEarning));
				else
					leaveBalanceSummryDTO
							.setEncashableAmount(leaveBalanceSummryDTO.getLeaveBalancedCount().multiply(perDayEarning));

				leaveBalanceSummryList.add(leaveBalanceSummryDTO);
			}

		}
		return leaveBalanceSummryList;

	}

	@RequestMapping(path = "/holdSalary/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<HoldSalaryDTO> getHoldSalary(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		List<HoldSalary> holdSalaryList = holdSalaryService.searchEmployeeHoldDetails(employeeId);
		return holdSalaryAdaptor.databaseModelToUiDtoList(holdSalaryList);
	}

	@RequestMapping(path = "/pendingSalary/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<ReportPayOutDTO> getPendingSalary(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		List<ReportPayOut> report = reportPayOutService.getSalary(employeeId);
		List<ReportPayOutDTO> reportList = reportPayOutAdaptor.databaseModelToUiDtoList(report);

		// List<ReportPayOutDTO>
		// reportPayOutDTOList=reportPayOutAdaptor.dataBaseModelObjtoUiDto(reportPayOutService.getSalary(employeeId));
		return reportList;
	}

	@RequestMapping(path = "/createSalary/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody Collection<String> getSalary(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		List<ReportPayOutDTO> reportPayOutDTOList = reportPayOutAdaptor
				.databaseModelToUiDtoList(reportPayOutService.createSalary(employeeId));
		List<String> processMonth = new ArrayList<String>();
		for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
			processMonth.add(reportPayOutDTO.getProcessMonth());
			System.out.println("process month*******************************************************"
					+ reportPayOutDTO.getProcessMonth());
		}
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);

		Separation separation = separationService.getSeparationInfo(employeeId);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String joiningDate = formatter.format(employee.getDateOfJoining());
		String endDate = formatter.format(separation.getEndDate());
		List<String> duration = new ArrayList<String>();
		org.joda.time.LocalDate date1 = new org.joda.time.LocalDate(joiningDate);
		org.joda.time.LocalDate date2 = new org.joda.time.LocalDate(endDate);
		while (date1.isBefore(date2)) {
			System.out.println("*******************************************************"
					+ date1.toString("MMM-yyyy").toUpperCase());
			duration.add(date1.toString("MMM-yyyy").toUpperCase());
			date1 = date1.plus(Period.months(1));
		}
		Collection<String> similar = new HashSet<String>(processMonth);
		Collection<String> different = new HashSet<String>();

		different.addAll(duration);
		different.addAll(processMonth);
		different.removeAll(similar);
		List<String> differentList = new ArrayList<String>(different);
		Collections.sort(differentList, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("MMM-yyyy");

			@Override
			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		// Collections.sort(differentList);

		// similar.retainAll(duration);
		System.out.println("different*******************************************************" + differentList);

		return differentList;
	}

	@RequestMapping(path = "/assetStatus/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody boolean assetStatus(@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {
		List<EmployeeAsset> employeeAssets = employeeAssetService.findAllemployeeAssets(employeeId.toString());
		if (employeeAssets.size() > 0) {
			for (EmployeeAsset employeeAsset : employeeAssets) {
				if (employeeAsset.getDateTo() == null) {
					logger.info("__________________Recieved date null_____________________");
					return false;
				}
			}
		}

		return true;
	}

	@RequestMapping(path = "/getReportPayOutMonth/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<String> getReportPaoutMonth(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
		List<String> reportPayOutDTOList = reportPayOutService.getReportPayOutMonth(employeeId);
		return reportPayOutDTOList;
	}

	@RequestMapping(path = "/finalSettlement/{employeeId}", method = RequestMethod.POST)
	public @ResponseBody void finalSettlement(@RequestBody FinalSettlementDTO FinalSettlementDto,
			@PathVariable("employeeId") Long employeeId, HttpServletRequest req) {
		FinalSettlement finalSettlement = finalSettlementAdaptor.finalSettlementDtoToDatabaseModel(FinalSettlementDto);

		FinalSettlement finalSettle = finalSettlementService.save(finalSettlement);
		if (finalSettle != null) {
			int emp = employeePersonalInformationService.changeEmployeeStatus(employeeId);
		}

		List<ReportPayOut> report = reportPayOutService.getSalary(employeeId);

		List<Object[]> lastPaid = reportPayOutService.lastPaid(employeeId);

		List<FinalSettlementReport> finalSettlementReportList = finalSettlementAdaptor.reportPayoutToFinalReport(report,
				lastPaid);
		finalSettlementService.saveReport(finalSettlementReportList);

		finalSettlementService.generateLetter(employeeId, finalSettlementReportList);

	}

 

	@RequestMapping(path = "/employeeList/{companyId}/{status}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getAllEmployee(@PathVariable("companyId") Long companyId,
			@PathVariable("status") String status, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {

		logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = finalSettlementService.getFinalSettlementEmployee(companyId, status);
		List<EmployeeDTO> employeeDtoList = finalSettlementAdaptor.databaseObjModelToUiDtoList(employeeObjList);
		System.out.println(employeeDtoList);
		return employeeDtoList;

	}

	@RequestMapping(path = "/finalSettlementStatement/{employeeId}/{companyId}/{cityId}", method = RequestMethod.GET)
	public StreamingResponseBody finalSettlementStatement(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId, @PathVariable("cityId") Long cityId, HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		Employee employee = null;
		City city = null;
		Company company = null;
		FinalSettlement finalSettlement = null;
		Separation separation = null;
		List<ReportPayOutDTO> reportPayoutList = null;
		ReportPayOutDTO lastPaidValue = null;

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"finalSettlementStatement.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		company = companyService.getCompany(companyId);
		employee = employeePersonalInformationService.findEmployeesById(employeeId);
		city = cityService.getCity(cityId);
		finalSettlement = finalSettlementService.getFinalSettlementById(employeeId);
		separation = separationService.getSeparationInfo(employeeId);
		List<FinalSettlementReport> finalSettlementReportList = finalSettlementService
				.getFinalSettlementReport(employeeId);
		// List<Object[]>
		// employeeList=finalSettlementService.getFinalSettlementEmployee(companyId);

		List<ReportPayOut> report = reportPayOutService.getSalary(employeeId);
		reportPayoutList = reportPayOutAdaptor.databaseModelToUiDtoList(report);

		List<Object[]> lastPaid = reportPayOutService.lastPaid(employeeId);
		lastPaidValue = reportPayOutAdaptor.lastPaodModelToLastPaidDTO(lastPaid);

		ByteArrayInputStream bis = new SalaryPdfReport().finalSettlementStatement(company, employee, city,
				finalSettlement, separation, finalSettlementReportList, lastPaidValue);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};
	}

}
