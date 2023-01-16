package com.csipl.hrms.employee.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.enums.EmployeeLifeCycleChartEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payroll.PayHeadDTO;
import com.csipl.hrms.dto.payroll.dashboard.Categories;
import com.csipl.hrms.dto.payroll.dashboard.Category;
import com.csipl.hrms.dto.payroll.dashboard.Dataset;
import com.csipl.hrms.dto.payroll.dashboard.EmployeeLifeCycleChart;
import com.csipl.hrms.dto.payroll.dashboard.EmployeeLifeCycleData;
import com.csipl.hrms.dto.payroll.dashboard.EmployeeLifeCycleGraphDto;
import com.csipl.hrms.dto.payroll.dashboard.ObjectSorter;
import com.csipl.hrms.dto.payrollprocess.PayOutListDTO;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.service.adaptor.GradesPayDefinitionAdaptor;
import com.csipl.hrms.service.adaptor.PayHeadAdaptor;
import com.csipl.hrms.service.adaptor.PayOutAdaptor;
import com.csipl.hrms.service.adaptor.PayStructureHdAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.employee.repository.PayStructureRepository;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.InvestmentService;
import com.csipl.hrms.service.payroll.OtherIncomeService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.payroll.TdsApprovalService;
import com.csipl.hrms.service.payroll.TdsPayrollService;
import com.csipl.hrms.service.payroll.TdsStandardExemptionService;
import com.csipl.hrms.service.payroll.TdsTransactionFileService;
import com.csipl.hrms.service.payroll.TdsTransactionService;
import com.csipl.hrms.service.payroll.repository.PayHeadRepository;

@RestController
@RequestMapping({ "/payStructure" })
public class PayStructureController {
	private static final Logger logger = LoggerFactory.getLogger(PayStructureController.class);

	PayStructureHdAdaptor payStructureHdAdaptor = new PayStructureHdAdaptor();
	GradesPayDefinitionAdaptor gradesPayDefinitionAdaptor = new GradesPayDefinitionAdaptor();

//	@Autowired
//	private EmployeeLettersTransactionService employeeLettersTransactionService;
//	@Autowired
//	private EmployeeLetterService empLetterService;
//	@Autowired
//	private ApprovalHierarchyMasterService approvalHierarchyMasterService;
//	@Autowired
//	private LetterService letterService;
//	@Autowired
//	private EmployeeLettersTransactionRepository employeeLettersTransactionRepository;

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	InvestmentService investmentService;

	@Autowired
	FinancialYearService financialYearService;

	@Autowired
	TdsPayrollService tdsPayrollService;

	@Autowired
	TdsApprovalService tdsApprovalService;

	@Autowired
	TdsTransactionService tdsTransactionService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	PayHeadRepository payHeadRepository;

	@Autowired
	TdsTransactionFileService tdsTransactionFileService;

	@Autowired
	OtherIncomeService otherIncomeService;

	@Autowired
	TdsStandardExemptionService tdsStandardExemptionService;

	@Autowired
	ReportPayOutService reportPayOutService;
	@Autowired
	com.csipl.hrms.service.payroll.PayRollService payRollService;

	@Autowired
	PayHeadService payHeadService;

	PayOutAdaptor payOutAdaptor = new PayOutAdaptor();
	@Autowired
	PayStructureRepository payStructureRepository;

	@Autowired
	ProfessionalTaxService professionalTaxService;

	/*
	 * @neeraj 26-05-2020
	 */
	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void savePayStructureDetails(@RequestBody PayStructureHdDTO payStructureHdDto)
			throws ErrorHandling, ParseException, PayRollProcessException {
		PayStructureHd payStructureHd = payStructureHdAdaptor.uiDtoToDatabaseModel(payStructureHdDto);
		String processMonth = payStructureHd.getProcessMonth();
		Date date = DateUtils.getDateForProcessMonth(processMonth);

		Date dateOfJoining = payStructureHdDto.getDateOfJoining();
		if (date != null && dateOfJoining != null) {
			date.setDate(dateOfJoining.getDate());
			if (getDate(date).before(getDate(dateOfJoining))) {
				logger.info("ps month " + date);
				logger.info("dateOfJoining " + dateOfJoining);
				throw new PayRollProcessException("Pay structure process month can not before Date of Joining");
			}
		}

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate,
				payStructureHdDto.getCompanyId());
		if (financialYear == null) {
			// we have to find active financialYear first to get created payroll list on
			// that duration
			throw new PayRollProcessException("financialYear not found");
		}

		boolean grossLessthnExisting = professionalTaxService.payrollCheckObj(processMonth,
				financialYear.getFinancialYearId(), payStructureHdDto.getCompanyId(), payStructureHdDto.getEmployeeId(),
				payStructureHdDto.getGrossPay());
		if (grossLessthnExisting) {
			// can not revise pay because gross pay is lesser thn existing
			logger.info("Can not revise pay because gross pay is lesser thn existing");
			// throw new PayRollProcessException("Can Not Define Pay Structure");
			throw new PayRollProcessException("New Gross Salary cannot be less while creating Arrears. Please check.");
		}

		payStructureService.save(payStructureHd, payStructureHdDto.getCompanyId(),
				payStructureHdDto.getProcessMonthList());

		payStructureService.letterGenerater(payStructureHd, payStructureHdDto.getCompanyId(),
				payStructureHdDto.getProcessMonthList());
	}

	public Date getDate(Date date) {
		Calendar calInput = Calendar.getInstance();
		calInput.setTime(date);
		calInput.set(Calendar.MINUTE, 0);
		calInput.set(Calendar.SECOND, 0);
		calInput.set(Calendar.HOUR_OF_DAY, 0);
		Date dateformat = calInput.getTime();
		return dateformat;
	}

	@RequestMapping(value = { "/{empid}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public PayStructureHdDTO employeeCurrentPayStructure(@PathVariable("empid") Long empid)
			throws ErrorHandling, PayRollProcessException {
		logger.info("getPayStructure empId id : " + empid);
		PayStructureHd payStructureHd = payStructureService.employeeCurrentPayStructure(empid);
		/* if we get existing paystructure then flag value will be false */
		Boolean flag = false;
		if (payStructureHd != null) {
			Boolean exitsPaystructurePayheadsFlag = true;
			PayStructureHdDTO payStructureHdDTO = payStructureHdAdaptor.databaseModelToUiDto(payStructureHd);
			payStructureHdDTO.setPayOutDtoList(payStructureService
					.calculateEarningDeduction(1l, empid, payStructureHd, flag, exitsPaystructurePayheadsFlag)
					.getPayOutDtoList());
			return payStructureHdDTO;
		}
		logger.info("Pay Structure data not present");
		throw new ErrorHandling("Pay Structure data not present");

	}

	@RequestMapping(value = { "/deduction/{empid}/{companyId}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public PayOutListDTO getDeduction(@PathVariable("empid") Long empid, @PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getDeduction empid is :" + empid + "companyId>>>" + companyId);
		List<PayOut> payOutList = payStructureService.processPayroll(companyId, empid, null, false);

		if ((payOutList != null) && (payOutList.size() > 0)) {
			logger.info("payOutList is coverter : " + payOutList.size());
			return payOutAdaptor.databaseModelToUiDtoList(payOutList);
		}
		logger.info("Pay Structure data not present ");
		throw new ErrorHandling("Pay Structure data not present");
	}

	/**
	 * Method PayRevision Performed Pay Revision Operation
	 * 
	 * @param payStructureHdDto This is the first parameter for getting
	 *                          payStructureHd Object from UI
	 * @param req               This is the second parameter to maintain user
	 *                          session
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/payRevision", method = RequestMethod.POST)
	public void savePayRevision(@RequestBody PayStructureHdDTO payStructureHdDto, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {

		/*
		 * PayStructureHd existsPayStructureHd =
		 * payStructureService.getEndDateNullPayStructure(payStructureHdDto.
		 * getEmployeeId()); List<String> list =new ArrayList<String>(); list =
		 * payStructureService.arrearCalculationMonths(existsPayStructureHd.
		 * getProcessMonth(), payStructureHdDto.getEmployeeId()); if (list.size() > 0) {
		 * List<PayStructureHd> payStructureHdList = new ArrayList<PayStructureHd>();
		 * existsPayStructureHd.setDateEnd(DateUtils.subtractDays(DateUtils.
		 * getDateForProcessMonth(payStructureHdDto.getProcessMonth()), 1));
		 * payStructureHdList.add(existsPayStructureHd);
		 * payStructureHdList.add(payStructureHdAdaptor
		 * .uiDtoToDatabaseModelRevision(payStructureHdDto));
		 * payStructureService.saveAll(payStructureHdList, null, null, null);
		 * 
		 * } else { List<PayStructureHd> payStructureHdList = new
		 * ArrayList<PayStructureHd>(); PayStructureHd payStructureHdNew
		 * =payStructureService.findPayStructureOnDate(DateUtils.subtractDays(
		 * existsPayStructureHd.getEffectiveDate(),
		 * 1),payStructureHdDto.getEmployeeId());
		 * 
		 * if(payStructureHdNew!=null) { list =
		 * payStructureService.arrearCalculationMonths(payStructureHdNew.getProcessMonth
		 * (), payStructureHdDto.getEmployeeId());
		 * 
		 * }if(list.size() > 0) { System.out.println("$$$$$$$$$$");
		 * payStructureHdNew.setDateEnd(DateUtils.subtractDays(DateUtils.
		 * getDateForProcessMonth(payStructureHdDto.getProcessMonth()), 1));
		 * payStructureHdList.add(payStructureHdNew); }else {
		 * System.out.println("###################3"); }
		 * payStructureService.deletePayRevision(payStructureHdDto.getPayStructureHdId()
		 * );
		 * payStructureService.deleteFuturePayStructure(payStructureHdDto.getEmployeeId(
		 * ));
		 * payStructureHdList.add(payStructureHdAdaptor.uiDtoToDatabaseModelRevision(
		 * payStructureHdDto)); payStructureService.saveAll(payStructureHdList, null,
		 * null, null); System.out.println("data updated ");
		 * 
		 * }
		 */
	}

	@RequestMapping(value = { "/payRevision/{empid}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<PayStructureHdDTO> getEmployeePayRevisionList(@PathVariable("empid") Long empid, HttpServletRequest req)
			throws ErrorHandling {
		logger.info("getEmployeePayRevisionList empid is : " + empid);
		List<PayStructureHd> payStructureHdList = payStructureService.getEmployeePayRevisionList(empid);
		if (payStructureHdList != null) {
			return payStructureHdAdaptor.databaseModelToUiDtoList(payStructureHdList);
		}
		throw new ErrorHandling("Pay Structure data not present");
	}

	/**
	 * 
	 * @param payStructureHdId
	 * @param req
	 * 
	 * @return PayStructureHdDTO Object
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = { "/payStructureHd/{payStructureHdId}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public PayStructureHdDTO getEmployeePayStructure(@PathVariable("payStructureHdId") Long payStructureHdId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getEmployeePayStructure payStructureHdId is : " + payStructureHdId);
		/* if we get existing paystructure then flag value will be false */
		Boolean flag = false;
		Boolean exitsPaystructurePayheadsFlag = true;
		PayStructureHd payStructureHd = payStructureService.getPayStructureHd(payStructureHdId);

		if (payStructureHd != null) {
			PayStructureHdDTO payStructureHdDTO = payStructureHdAdaptor.databaseModelToUiDto(payStructureHd);
			payStructureHdDTO.setPayOutDtoList(
					payStructureService.calculateEarningDeduction(1l, payStructureHd.getEmployee().getEmployeeId(),
							payStructureHd, flag, exitsPaystructurePayheadsFlag).getPayOutDtoList());

			return payStructureHdDTO;
		}

		logger.info("pay structre Data not Available");
		throw new ErrorHandling("Data not Available");
	}

	@RequestMapping(value = { "/employeePayrollValidate" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public PayStructureHdDTO employeePayrollValidate(@RequestBody PayStructureHdDTO payStructureHdDto,
			HttpServletRequest req) throws Exception {
		System.out.println("payStructureHdDto.getEmployeeId()" + payStructureHdDto.getEmployeeId());
		System.out.println("payStructureHdDto.getEffectiveDate()" + payStructureHdDto.getEffectiveDate());

		Long count = reportPayOutService.checkPayrollOfEmployee(payStructureHdDto.getEmployeeId(),
				DateUtils.getDateStringWithYYYYMMDD(payStructureHdDto.getEffectiveDate()));
		System.out.println("count" + count);
		if (count.longValue() > 0L) {
			logger.info("Entry denied, payroll has been created already.");
			throw new ErrorHandling("Entry denied, payroll has been created already.");
		}
		PayStructureHd payStructureHd = payStructureService.getPayStructure(payStructureHdDto.getPayStructureHdId());
		return payStructureHdAdaptor.databaseModelToUiDto(payStructureHd);
	}

	@RequestMapping(value = { "/updatePayRevision/{payStructureHdId}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public PayStructureHdDTO updatePayRevision(@PathVariable("payStructureHdId") String payStructureHdId,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("updatePayRevision payStructureHdId id : " + payStructureHdId);
		Long longPayStructureHdId = Long.valueOf(Long.parseLong(payStructureHdId));
		PayStructureHd payStructureHd = payStructureService.getPayStructure(longPayStructureHdId);
		if (payStructureHd != null) {
			return payStructureHdAdaptor.databaseModelToUiDto(payStructureHd);
		}
		logger.info("Pay Structure data not present");
		throw new ErrorHandling("Pay Structure data not present");
	}

	@RequestMapping(value = { "/employees/{companyId}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<EmployeeDTO> PayStructureEmployee(@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling {
		List<Object[]> employees = payStructureService.getEmployees(companyId);
		List<EmployeeDTO> employeeDTOList = payStructureHdAdaptor.databaseModelObjtoUiDto(employees);
		return employeeDTOList;
	}

	@RequestMapping(value = { "/arrearMonths/{employeeId}/{processMonth}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> arrearCalculationMonths(@PathVariable("employeeId") Long employeeId,
			@PathVariable("processMonth") String processMonth) {
		logger.info("arrearCalculationMonths  employeeId>>> " + employeeId + "processMonth>> " + processMonth);
		return payStructureService.arrearCalculationMonths(processMonth, employeeId);
	}

	/**
	 * to get List PayHead objects from database based on companyId and earning
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(path = "/earningDeduction/{companyId}/{grossPay}/{employeeId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody PayStructureHdDTO getEarningDeductionHeadsBasedOnGross(
			@PathVariable("companyId") Long companyId, @PathVariable("grossPay") BigDecimal grossPay,
			@PathVariable("employeeId") Long employeeId, @PathVariable("processMonth") String processMonth)
			throws PayRollProcessException {
		logger.info("getEarningDeductionHeadsBasedOnGross companyId" + companyId + " grossPay" + grossPay);
		PayHeadAdaptor payheadAdaptor = new PayHeadAdaptor();
		List<PayHeadDTO> earningDtoList = new ArrayList<PayHeadDTO>();
		List<PayHead> earningPayHeads = payHeadService.findAllEarnigPaystructurePayHeads(companyId);
		Boolean exitsPaystructurePayheadsFlag = false;
		/* if we create new paystructure then flag value will be true */
		Boolean flag = true;
		if (earningPayHeads != null && earningPayHeads.size() > 0)
			earningDtoList = payheadAdaptor.payStractureStanderedCalculation(earningPayHeads, grossPay);

		PayStructureHd payStructureHd = new PayStructureHd();

		List<PayStructure> payStructureList = new ArrayList<PayStructure>();

		for (PayHeadDTO payHeadDto : earningDtoList) {

			PayStructure payStructure = new PayStructure();
			payStructure.setPayHead(payheadAdaptor.uiDtoToDatabaseModel(payHeadDto));
			payStructure.setAmount(payHeadDto.getAmount());
			payStructureList.add(payStructure);
		}
		payStructureHd.setPayStructures(payStructureList);
		payStructureHd.setProcessMonth(processMonth);
		return payStructureService.calculateEarningDeduction(companyId, employeeId, payStructureHd, flag,
				exitsPaystructurePayheadsFlag);
	}

	/**
	 * to get List PayHead objects from database based on companyId and earning
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = "/ctcCalculation", method = RequestMethod.POST)
	public @ResponseBody PayStructureHdDTO ctcCalculation(@RequestBody PayStructureHdDTO payStructureHdDto)
			throws PayRollProcessException {
		logger.info(" #######################ctcCalculation companyId" + payStructureHdDto.getPayStructureHdId());
		/* if we get existing paystructure then flag value will be false */
		Boolean flag = false;
		Boolean exitsPaystructurePayheadsFlag = false;
		PayStructureHd payStructureHd = payStructureHdAdaptor.uiDtoToDatabaseModel(payStructureHdDto);
		for (PayStructure payStructure : payStructureHd.getPayStructures()) {
			payStructure.setPayHead(payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId()));
		}
		if (payStructureHd.getPayStructureHdId() == null)
			flag = true;
		return payStructureService.calculateEarningDeduction(payStructureHdDto.getCompanyId(),
				payStructureHdDto.getEmployeeId(), payStructureHd, flag, exitsPaystructurePayheadsFlag);
	}

	@RequestMapping(value = { "/employeePayRevision/{empid}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody EmployeeLifeCycleGraphDto getEmployeePayRevision(@PathVariable("empid") Long empid,
			HttpServletRequest req) throws ErrorHandling {
		logger.info("getEmployeePayRevisionList empid is : " + empid);
		List<PayStructureHd> payStructureHdList = payStructureService.getEmployeePayRevisionList(empid);

		EmployeeLifeCycleGraphDto employeeLifeCycleGraphDto = null;

		if (payStructureHdList.size() > 0) {
			Collections.sort(payStructureHdList, new ObjectSorter());

			List<String> colorList = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
					"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");
			ArrayList<EmployeeLifeCycleData> arrayData = new ArrayList<EmployeeLifeCycleData>();
			ArrayList<Category> categoryList = new ArrayList<Category>();

			ArrayList<Dataset> datasetList = new ArrayList<Dataset>();

			Dataset dataset = new Dataset();
			Categories categories = new Categories();
			ArrayList<Categories> categoriesList = new ArrayList<Categories>();

			EmployeeLifeCycleChart chart = new EmployeeLifeCycleChart(
					EmployeeLifeCycleChartEnum.theme.getPieChartValue(),
					EmployeeLifeCycleChartEnum.showValues.getPieChartValue(),
					EmployeeLifeCycleChartEnum.numVisiblePlot.getPieChartValue(),
					EmployeeLifeCycleChartEnum.scrollheight.getPieChartValue(),
					EmployeeLifeCycleChartEnum.flatScrollBars.getPieChartValue(),
					EmployeeLifeCycleChartEnum.scrollShowButtons.getPieChartValue(),
					EmployeeLifeCycleChartEnum.showHoverEffect.getPieChartValue(),
					EmployeeLifeCycleChartEnum.showYAxisvalue.getPieChartValue(),
					EmployeeLifeCycleChartEnum.labelFontColor.getPieChartValue());

			int index = 0;
			for (PayStructureHd pay : payStructureHdList) {
				if (colorList.size() <= index) {
					index = 0;
				}
				EmployeeLifeCycleData data = new EmployeeLifeCycleData();
				data.setValue(pay.getGrossPay());
				data.setColor(colorList.get(index));
				Category category = new Category();

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

				formatter = new SimpleDateFormat("dd MMMM yyyy");

				category.setLabel(formatter.format(pay.getEffectiveDate()));
				arrayData.add(data);
				categoryList.add(category);
				index++;
			}
			dataset.setData(arrayData);
			categories.setCategory(categoryList);
			datasetList.add(dataset);
			categoriesList.add(categories);

			employeeLifeCycleGraphDto = new EmployeeLifeCycleGraphDto(chart, categoriesList, datasetList);
		}

		return employeeLifeCycleGraphDto;
	}

	@GetMapping(path = "/generateLetters/{companyId}/{employeeId}/{employeeLetterId}")
	public void generateLetters(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId,
			@PathVariable("employeeLetterId") Long employeeLetterId) throws ErrorHandling {
		payStructureService.generateLetters(companyId, employeeId, employeeLetterId);
//     	PayStructureHd payStructureHd = payStructureRepository.monthValidationList(employeeId);
//
//		// TODO Auto-generated method stub
//		logger.info("-----------------=Start===EmployeeLetter====-----------");
//		Letter ltr = null;
//		Long count = 0l;
//		count = payStructureRepository.checkEmployeePayStructure(payStructureHd.getEmployee().getEmployeeId());
//
//		if (count == 1l) {
//			ltr = letterService.findLetterByType(StatusMessage.APPOINTMENT_LETTER_CODE);
//		} else {
//
//			ltr = letterService.findLetterByType(StatusMessage.APPRAISAL_LETTER_CODE);
//		}
//
//		if (ltr != null) {
//			String status = "PEN";
//			EmployeeLetter employeeLetter = empLetterService.findEmployeeLetter(employeeLetterId, employeeId, status);
//			EmployeeLetter employeeLetterNew = new EmployeeLetter();
//			Employee employee = employeePersonalInformationService
//					.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
//			employeeLetterNew.setEmpId(employee.getEmployeeId());
//
//			employeeLetterNew.setEmpLetterId(employeeLetter.getEmpLetterId());
//
//			// employeeLetter.setLetterDecription(ltr.getLetterDecription());
//			SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
//			String effictive = sm.format(payStructureHd.getEffectiveDate());
//			String currentDT = sm.format(new Date());
//			String doj = sm.format(employee.getDateOfJoining());
//
//			PayStructure p1 = new PayStructure();
//			PayStructure p2 = new PayStructure();
//			BigDecimal basic_year = new BigDecimal(0);
//			BigDecimal hra = new BigDecimal(0);
//			int count1 = 0;
//			List<PayStructure> list = payStructureNewRepository
//					.findAllPayStructureById(payStructureHd.getPayStructureHdId());
//			for (PayStructure item : list) {
//				if (count1 == 0) {
//					basic_year = item.getAmount();
//					count1++;
//				}
//				hra = item.getAmount();
//
//			}
//
//			BigDecimal professinal_Tx = new BigDecimal(0);
//			BigDecimal professinal_Tx_Year = new BigDecimal(0);
//			BigDecimal employee_PF = new BigDecimal(0);
//			BigDecimal employee_PF_year = new BigDecimal(0);
//			BigDecimal employer_PF = new BigDecimal(0);
//			BigDecimal employer_PF_year = new BigDecimal(0);
//			BigDecimal employee_HRA = new BigDecimal(0);
//			BigDecimal employee_HRA_Year = new BigDecimal(0);
//			BigDecimal employee_Esic = new BigDecimal(0);
//			BigDecimal employee_Esic_Year = new BigDecimal(0);
//			BigDecimal employer_Esic = new BigDecimal(0);
//			BigDecimal employer_Esic_Year = new BigDecimal(0);
//			BigDecimal current_Ad_Bonus = new BigDecimal(0);
//			BigDecimal current_Ad_Bonus_Year = new BigDecimal(0);
//			BigDecimal gross_Salary_Year = new BigDecimal(0);
//			BigDecimal current_CTC_Year = new BigDecimal(0);
//
//			basic_year.add(basic_year.multiply(new BigDecimal(12)));
//
//			if (payStructureHd.getProfessionalTax() != null) {
//				professinal_Tx = professinal_Tx.add(payStructureHd.getProfessionalTax());
//				professinal_Tx_Year = professinal_Tx_Year
//						.add(payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)));
//			}
//
//			if (payStructureHd.getEpfEmployee() != null) {
//				employee_PF = employee_PF.add(payStructureHd.getEpfEmployee());
//				employee_PF_year = employee_PF_year.add(payStructureHd.getEpfEmployee().multiply(new BigDecimal(12)));
//			}
//
//			if (payStructureHd.getEpfEmployer() != null) {
//				employer_PF = employer_PF.add(payStructureHd.getEpfEmployer());
//				employer_PF_year = employer_PF_year.add(payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)));
//			}
//
//			if (payStructureHd.getEsiEmployer() != null) {
//				employer_Esic = employer_Esic.add(payStructureHd.getEsiEmployer());
//				employer_Esic_Year = employer_Esic_Year
//						.add(payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)));
//			}
//			if (payStructureHd.getEsiEmployee() != null) {
//				employee_Esic = employee_Esic.add(payStructureHd.getEsiEmployee());
//				employee_Esic_Year = employee_Esic_Year
//						.add(payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)));
//			}
////		if (hra != null) {
////			employee_HRA = hra;
////			employee_HRA_Year = hra.multiply(new BigDecimal(12));
////		}
//
//			gross_Salary_Year.add(payStructureHd.getGrossPay().multiply(new BigDecimal(12)));
//			current_CTC_Year.add(payStructureHd.getCtc().multiply(new BigDecimal(12)));
//			/**
//			 * new code
//			 */
//			String desc1 = ltr.getLetterDecription().replace("@EMPLOYEE_NAME@",
//					employee.getFirstName() + " " + employee.getLastName());
//			String desc2 = desc1.replace("@Location@", employee.getCity().getCityName()).replace("@City_Name@",
//					employee.getAddress2().getAddressText());
//			;
//			String desc3 = desc2.replace("@Letter_Name@", ltr.getLetterName());
//
//			String desc4 = desc3.replace("@COMPANY_NAME@", employee.getCompany().getCompanyName());
//			String desc5 = desc4.replace("@AUTHORIZED_PERSON@", employee.getFirstName());
//			String desc6 = desc5.replace("@EMPLOYEE_CODE@", employee.getEmployeeCode()).replace("@DESIGNATION_NAME@",
//					employee.getDesignation().getDesignationName());
//			String desc7 = desc6.replace("@EFFECTIVE_DATE@", effictive);
//			String desc8 = desc7.replace("@CURRENT_DATE@", currentDT);
//
//			String desc9 = desc8.replace("@Employee_Basic@", payStructureHd.getNetPay().toString());
//			String desc10 = desc9.replace("@DATE_OF_JOINING@", doj);
//			String desc11 = desc10.replace("@Employee_Esic@", employee_Esic.toString());
//
//			String desc12 = desc11.concat(
//					"<br><table border='1'><tbody><tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Salary Component</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Monthly</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Annually</strong></span></td></tr>");
//
//			StringBuilder sb = new StringBuilder(desc12);
//
//			for (PayStructure pay : list) {
//				pay.setPayHead(payHeadRepository.findOne(pay.getPayHead().getPayHeadId()));
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//								+ pay.getPayHead().getPayHeadName() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ pay.getAmount() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ pay.getAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
//
//			}
//
//			if (payStructureHd.getGrossPay() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Gross</strong></span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getGrossPay() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getGrossPay().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//
//			if (payStructureHd.getEpfEmployee() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>  PF-Employee </span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEpfEmployee().toString() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEpfEmployee().multiply(new BigDecimal(12)).toString()
//						+ "</span></td></tr>");
//			}
//			if (payStructureHd.getEpfEmployer() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>PF-Employer</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEpfEmployer() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//
//			if (payStructureHd.getEpfEmployer() != null || payStructureHd.getEpfEmployer() != null) {
//				BigDecimal pensionEmp = new BigDecimal(0);
//				BigDecimal empr = new BigDecimal(0);
//				BigDecimal emp = new BigDecimal(0);
//				emp = payStructureHd.getEpfEmployee();
//				empr = payStructureHd.getEpfEmployer();
//				pensionEmp = emp.subtract(empr);
//				// pensionEmp =
//				// pensionEmp.add(payStructureHd.getEpfEmployee().subtract(payStructureHd.getEpfEmployee()));
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Pension-Employer</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ pensionEmp + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ pensionEmp.multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//
//			if (payStructureHd.getEsiEmployee() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employee</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEsiEmployee() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//			if (payStructureHd.getEsiEmployer() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employer</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEsiEmployer() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//			if (payStructureHd.getProfessionalTax() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Professional Tax</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getProfessionalTax() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//
//			if (payStructureHd.getNetPay() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Nat Payable</strong></span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getNetPay() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getNetPay().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//
//			if (payStructureHd.getCtc() != null) {
//				sb.append(
//						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>CTC</strong></span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getCtc() + "</span></td>");
//				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
//						+ payStructureHd.getCtc().multiply(new BigDecimal(12)) + "</span></td></tr>");
//			}
//			if (ltr.getLetterName().equals("Appointment Letter")) {
//				sb.append(
//						"</tr></tbody></table><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>DECLARATION BY THE EMPLOYEE&nbsp;</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;I have read and understood the above terms and conditions of services and agreed without any coercion or pressure to abide by the same.</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Human Resource Manager&nbsp;</p><p>Date: <strong>"
//								+ currentDT + "</strong></p><p>Place: <strong>" + employee.getCity().getCityName()
//								+ "</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Signature&nbsp;</p>");
//			} else {
//
//				sb.append(
//						"</tr></tbody></table><p><br data-cke-filler=\"true\"></p><p><br data-cke-filler=\\\"true\\\"></p>"
//								+ "<p>For " + "" + employee.getCompany().getCompanyName()
//								+ "</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Authorized Signatory&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>");
//			}
//
//			employeeLetterNew.setLetterDecription(sb.toString());
//			employeeLetterNew.setActiveStatus(StatusMessage.ACTIVE_CODE);
//			employeeLetterNew.setLetterId(ltr.getLetterId());
//			employeeLetterNew.setEmpStatus(StatusMessage.PENDING_CODE);
//			employeeLetterNew.setDateCreated(new Date());
//			employeeLetterNew.setUserId(employee.getUserId());
//			employeeLetterNew.setHRStatus(StatusMessage.PENDING_CODE);
//			employeeLetterNew.setActiveStatus(StatusMessage.ACTIVE_CODE);
//
//			EmployeeLetter empLetter = empLetterService.saveLtr(employeeLetterNew);
//
//			ApprovalHierarchyMaster approvalHierarchyMaster = approvalHierarchyMasterService
//					.getMasterApprovalStatus(ltr.getCompanyId(), ltr.getLetterId());
//			EmployeeLettersTransaction empLettersTransaction = employeeLettersTransactionRepository
//					.findByLetterId(empLetter.getEmpLetterId());
//			if (approvalHierarchyMaster.getActiveStatus().equalsIgnoreCase(StatusMessage.ACTIVE_CODE)) {
//
//				List<Object[]> approvalList = approvalHierarchyMasterService.findLetterApprovalById(ltr.getLetterId());
//				int i = 0;
//				Long designationId = 0l;
//				for (Object[] obj : approvalList) {
//					if (i == 0) {
//						designationId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
//						i++;
//					}
//				}
//
//				EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();
//				employeeLettersTransaction.setEmployeeLetter(empLetter);
//				employeeLettersTransaction.setLevels(StatusMessage.LEVEL_CODE);
//				employeeLettersTransaction.setStatus(StatusMessage.PENDING_CODE);
//
//				employeeLettersTransaction.setCompanyId(employee.getCompanyId());
//				employeeLettersTransaction.setDesignationId(designationId);
//
//				if (empLettersTransaction.getEmployeeLetterTransactionId() != null
//						&& empLettersTransaction.getEmployeeLetterTransactionId() != 0) {
//					employeeLettersTransaction
//							.setEmployeeLetterTransactionId(empLettersTransaction.getEmployeeLetterTransactionId());
//					employeeLettersTransaction.setDateUpdate(new Date());
//					employeeLettersTransaction.setDateCreated(empLettersTransaction.getDateCreated());
//					employeeLettersTransaction.setUserIdUpdate(employee.getUserId());
//				} else {
//					employeeLettersTransaction.setEmployeeLetterTransactionId(null);
//					employeeLettersTransaction.setDateCreated(new Date());
//					employeeLettersTransaction.setUserId(employee.getUserId());
//				}
//				employeeLettersTransactionService.saveEmpLetterTransaction(employeeLettersTransaction);
//			}
//
//			logger.info("-----------------=====Successfully generate Letter====-----------");
//		} // close if
//		else
//			throw new ErrorHandling("Letter not create Please contact to Admin");
//	}// close method
	}
}
