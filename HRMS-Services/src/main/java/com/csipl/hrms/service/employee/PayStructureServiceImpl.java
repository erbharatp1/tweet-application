package com.csipl.hrms.service.employee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.PayStructureDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.EsiCycle;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.employee.repository.EmployeeLettersTransactionRepository;
import com.csipl.hrms.service.employee.repository.PayStructureRepository;
import com.csipl.hrms.service.employee.repository.PayStructureTransactionRepository;
import com.csipl.hrms.service.payroll.EsicCycleService;
import com.csipl.hrms.service.payroll.EsicService;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.InvestmentService;
import com.csipl.hrms.service.payroll.OtherIncomeService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.PayRollService;
import com.csipl.hrms.service.payroll.TdsPayrollService;
import com.csipl.hrms.service.payroll.TdsStandardExemptionService;
import com.csipl.hrms.service.payroll.TdsTransactionFileService;
import com.csipl.hrms.service.payroll.TdsTransactionService;
import com.csipl.hrms.service.payroll.repository.ArearRepository;
import com.csipl.hrms.service.payroll.repository.PayHeadRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;

@Transactional
@Service("payStructureService")
public class PayStructureServiceImpl implements PayStructureService {
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager entityManager;

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PayStructureServiceImpl.class);

	@Autowired
	private PayStructureRepository payStructureRepository;

	@Autowired
	private EmployeeLettersTransactionService employeeLettersTransactionService;
	@Autowired
	private EmployeeLetterService empLetterService;
	@Autowired
	private ApprovalHierarchyMasterService approvalHierarchyMasterService;
	@Autowired
	private LetterService letterService;
	@Autowired
	private PayHeadRepository payHeadRepository;

	@Autowired
	PayRollService payRollService;

	@Autowired
	PayHeadService payHeadService;

	@Autowired
	private ReportPayOutRepository reportPayOutRepository;

	@Autowired
	ArearRepository arearRepository;

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	InvestmentService investmentService;

	@Autowired
	FinancialYearService financialYearService;

	@Autowired
	TdsPayrollService tdsPayrollService;

	@Autowired
	TdsTransactionService tdsTransactionService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	TdsTransactionFileService tdsTransactionFileService;

	@Autowired
	OtherIncomeService otherIncomeService;

	@Autowired
	TdsStandardExemptionService tdsStandardExemptionService;

	@Autowired
	EsicService esicService;

	@Autowired
	EsicCycleService esicCycleService;

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager eManager;

	@Autowired
	private EmployeeLettersTransactionRepository employeeLettersTransactionRepository;
	@Autowired
	PayStructureTransactionRepository payStructureNewRepository;

	/**
	 * Method performed save OR update operation
	 * 
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */

	@Override
	public PayStructureHd save(PayStructureHd payStructureHd, Long companyId, List<String> arrearMonths)
			throws ErrorHandling, PayRollProcessException {

		if (payStructureHd.getPayStructureHdId() != null) {

			// calculate ESIC lifecycle
			System.out.println("inside if...." + payStructureHd.getPayStructureHdId());
			Date esicEndDate = esiEndDateCalculation(payStructureHd, companyId);
			if (esicEndDate != null)
				payStructureHd.setEsicEndDate(esicEndDate);
			payStructureHd.setDateCreated(new Date());
			// find out paystrucre which end date is null
			PayStructureHd existsPayStructureHd = getEndDateNullPayStructure(
					payStructureHd.getEmployee().getEmployeeId());
			List<String> list = new ArrayList<String>();

			// find the list of process month which will be start from pay revision process
			// month and future process month from report payout table
			list = arrearCalculationMonths(existsPayStructureHd.getProcessMonth(),
					payStructureHd.getEmployee().getEmployeeId());
			if (arrearMonths != null)
				if (arrearMonths.size() > 0)
					if (arrearValidation(arrearMonths, existsPayStructureHd.getEmployee().getEmployeeId())) {
						System.out.println("arrear already exits");
						//throw new ErrorHandling("Arear already exits..");
						throw new ErrorHandling("Arrears has already been created.");
					}

			if (list.size() > 0) {
				existsPayStructureHd.setDateEnd(
						DateUtils.subtractDays(DateUtils.getDateForProcessMonth(payStructureHd.getProcessMonth()), 1));
				payStructureHd.setPayStructureHdId(null);
				entityManager.persist(payStructureHd);
				entityManager.merge(existsPayStructureHd);
			} else {
				PayStructureHd payStructureHdNew = findPayStructureOnDate(
						DateUtils.subtractDays(existsPayStructureHd.getEffectiveDate(), 1),
						payStructureHd.getEmployee().getEmployeeId());
				if (payStructureHdNew != null) {
					payStructureHdNew.setDateEnd(DateUtils
							.subtractDays(DateUtils.getDateForProcessMonth(payStructureHd.getProcessMonth()), 1));
					entityManager.merge(payStructureHdNew);
					payStructureHd.setDateEnd(null);
				}
				if (existsPayStructureHd != null)
					payStructureRepository.delete(existsPayStructureHd.getPayStructureHdId());
				deleteFuturePayStructure(payStructureHd.getEmployee().getEmployeeId());
				payStructureHd.setPayStructureHdId(null);
				entityManager.persist(payStructureHd);
			}

			// Arrears calculation
			if (arrearMonths != null && arrearMonths.size() > 0) {
				try {
					payRollService.employeeArrearCalculation(companyId, arrearMonths,
							payStructureHd.getEmployee().getEmployeeId(), payStructureHd.getUserId(), payStructureHd);
				} catch (PayRollProcessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else {
			System.out.println("inside else....." + payStructureHd.getPayStructureHdId());
			deleteFuturePayStructure(payStructureHd.getEmployee().getEmployeeId());
			payStructureHd = payStructureRepository.save(payStructureHd);
			System.out.println("data saved ");
		}

		/*
		 * logger.info("save payStructureHd===== "+payStructureHd);
		 * 
		 */
		// Tds Calculate
		// DateUtils dateUtils = new DateUtils();
		// Date currentDate = dateUtils.getCurrentDate();
		// Long empId= payStructureHd.getEmployee().getEmployeeId();
		// Long companyId=payStructureHd.getEmployee().getCompany().getCompanyId();
		// logger.info("tdsDeclaration is calling employeeId :"+empId+"companyId is
		// :"+companyId+"currentDate :"+currentDate);

		// List<PayOut> payOutList1 =
		// payStructureService.processPayrollForTds(companyId, empId,payStructureHd);

		// TdsStandardExemption tdsStandardExemption1 =
		// tdsStandardExemptionService.getTdsStandardExemption(companyId);
		// List<OtherIncome> otherIncomeList1 =
		// otherIncomeService.findOtherIncomes(empId, companyId);
		// Employee employee1 =
		// employeePersonalInformationService.findEmployeesById(empId);

		// logger.info("payOutList "+payOutList1+"tdsStandardExemption :
		// "+tdsStandardExemption1+"otherIncomeList
		// :"+otherIncomeList1+"employee"+employee1);

		// tds declaration process required data gethering----
		// FinancialYear financialYear1 =
		// financialYearService.findCurrentFinancialYear(currentDate,companyId);
		// List<TdsGroup> tdsGroupList = investmentService.getInvestmentList(companyId);
		// List<TdsTransaction> tdsTrasactionist =
		// tdsTransactionService.getTdsTrasactionListforApproval(empId,financialYear1.getFinancialYear());

		// TransactionApprovedHd transactionApprovedHd1 =
		// tdsApprovalService.getTransactionApprovedHd(employee1.getEmployeeId(),
		// financialYear1.getFinancialYear());

		// logger.info("financialYear "+financialYear1+"tdsTrasactionist
		// :"+tdsTrasactionist+"getCompanyId"+companyId);
		//

		// tdsApprovalService.saveTdsApprovalsList(transactionApprovedHd1, employee1,
		// financialYear1,companyId, null,payOutList1, tdsStandardExemption1,
		// otherIncomeList1,true,null);
		return payStructureHd;
	}

	/**
	 * to get PayStructureHd object from database based on employeeId and current
	 * date I have to change currentDate logic with startdate of passing process
	 * month
	 * 
	 */
	@Override
	public PayStructureHd employeeCurrentPayStructure(Long employeeId) {
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		LOGGER.info("currentDate payStructureService == " + currentDate);
		LOGGER.info("employeeId payStructureService == " + employeeId);
		return payStructureRepository.employeeCurrentPayStructure(currentDate, employeeId);
	}

	@Override
	public PayStructureHd findPayStructureForPayroll(Long employeeId, String processMonth) {
		// DateUtils dateUtils = new DateUtils();
		Date currentDate = DateUtils.getDateForProcessMonth(processMonth);

		// Date currentDate = dateUtils.getCurrentDate();
		LOGGER.info("currentDate payStructureService == " + currentDate);
		LOGGER.info("employeeId payStructureService == " + employeeId);
		return payStructureRepository.employeeCurrentPayStructure(currentDate, employeeId);
	}

	/**
	 * to get Employee Earning and Deduction from database based on employeeId and
	 * companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@Override
	public List<PayOut> processPayroll(Long companyId, Long employeeId, PayStructureHd payStructureHdForArrear,
			boolean isArrearCalculation) throws PayRollProcessException {
		LOGGER.info(" payStructureHd===== " + "companyId" + companyId + " employeeId " + employeeId);
		List<PayHead> payHeads = payHeadService.findAllPayHeadOfCompany(companyId);
		List<PayOut> payOuts = payRollService.processPayRoll(companyId, employeeId, payStructureHdForArrear,
				isArrearCalculation);

		for (PayOut payOut : payOuts) {
			for (PayHead payHead : payHeads) {
				if (payHead.getPayHeadId() == payOut.getId().getPayHeadId()) {
					payOut.setPayHeadName(payHead.getPayHeadName());
					payOut.setEarningDeduction(payHead.getEarningDeduction());
					break;
				}
			}
		}

		return payOuts;
	}

	@Override
	public List<PayOut> processPayrollForTds(Long companyId, Long employeeId, PayStructureHd payStructureHd) {

		LOGGER.info(" processPayrollForTds===== " + "companyId" + companyId + " employeeId " + employeeId);

		List<PayHead> payHeads = payHeadService.findAllPayHeadOfCompany(companyId);
		List<PayOut> payOuts = payRollService.processPayRollForTds(companyId, employeeId, payStructureHd);

		for (PayOut payOut : payOuts) {
			for (PayHead payHead : payHeads) {
				if (payHead.getPayHeadId() == payOut.getId().getPayHeadId()) {
					payOut.setPayHeadName(payHead.getPayHeadName());
					payOut.setEarningDeduction(payHead.getEarningDeduction());
					break;
				}
			}
		}

		return payOuts;

	}

	@Override
	public void updateCTC(PayStructureHd payStructureHd, List<PayOut> payOutList) {
		BigDecimal grossPay = payStructureHd.getGrossPay();
		BigDecimal ctc = new BigDecimal(0);
		BigDecimal employeerDeduction = new BigDecimal(0);

		ctc = ctc.add(grossPay);

		BigDecimal emplyoer_pf = new BigDecimal(0);
		BigDecimal emplyoeresi = new BigDecimal(0);
		BigDecimal emplyoer_pension = new BigDecimal(0);

		for (PayOut payOut : payOutList) {

			if (payOut.getId().getPayHeadId() == StandardDeductionEnum.PF_Employer_Contribution
					.getStandardDeduction()) {
				emplyoer_pf = emplyoer_pf.add(payOut.getAmount());
				ctc = ctc.add(emplyoer_pf);

			} else if (payOut.getId().getPayHeadId() == StandardDeductionEnum.ESI_Employer_Contribution
					.getStandardDeduction()) {
				emplyoeresi = emplyoeresi.add(payOut.getAmount());
				ctc = ctc.add(emplyoeresi);

			} else if (payOut.getId().getPayHeadId() == StandardDeductionEnum.Pension_Employer_Contribution
					.getStandardDeduction()) {
				emplyoer_pension = emplyoer_pension.add(payOut.getAmount());
				ctc = ctc.add(emplyoer_pension);
			}

		}
		payStructureHd.setCostToCompany(ctc);
		payStructureRepository.save(payStructureHd);
	}

	/**
	 * to get Employee Pay object from database based on payStructureHdId (Primary
	 * Key)
	 */
	@Override
	public PayStructureHd getPayStructureHd(Long payStructureHdId) {
		return payStructureRepository.findOne(payStructureHdId);
	}

	/**
	 * Method Performed save operation ,save List of pay structure into database
	 * 
	 */
	@Override
	public void saveAll(List<PayStructureHd> payStructureHdList, Long empId, Long companyId,
			PayStructureHd payStructureHd) {
		System.out.println("payStructureHdList>>>" + payStructureHdList.size());
		payStructureRepository.save(payStructureHdList);

		// // Tds Calculate
		// DateUtils dateUtils = new DateUtils();
		// Date currentDate = dateUtils.getCurrentDate();
		// // Long empId= payStructureHd.getEmployee().getEmployeeId();
		// // Long companyId=payStructureHd.getEmployee().getCompany().getCompanyId();
		// logger.info("tdsDeclaration is calling employeeId :" + empId + "companyId is
		// :" + companyId + "currentDate :"
		// + currentDate);
		//
		// List<PayOut> payOutList1 =
		// payStructureService.processPayrollForTds(companyId, empId, payStructureHd);
		//
		// TdsStandardExemption tdsStandardExemption1 =
		// tdsStandardExemptionService.getTdsStandardExemption(companyId);
		// List<OtherIncome> otherIncomeList1 =
		// otherIncomeService.findOtherIncomes(empId, companyId);
		// Employee employee1 =
		// employeePersonalInformationService.findEmployeesById(empId);
		//
		// logger.info("payOutList " + payOutList1 + "tdsStandardExemption : " +
		// tdsStandardExemption1
		// + "otherIncomeList :" + otherIncomeList1 + "employee" + employee1);
		//
		// // tds declaration process required data gethering----
		// FinancialYear financialYear1 =
		// financialYearService.findCurrentFinancialYear(currentDate, companyId);
		// // List<TdsGroup> tdsGroupList =
		// investmentService.getInvestmentList(companyId);
		// List<TdsTransaction> tdsTrasactionist =
		// tdsTransactionService.getTdsTrasactionListforApproval(empId,
		// financialYear1.getFinancialYear());
		//
		// // TransactionApprovedHd transactionApprovedHd1 =
		// // tdsApprovalService.getTransactionApprovedHd(employee1.getEmployeeId(),
		// // financialYear1.getFinancialYear());
		//
		// logger.info("financialYear " + financialYear1 + "tdsTrasactionist :" +
		// tdsTrasactionist + "getCompanyId"
		// + companyId);

		//

		// tdsApprovalService.saveTdsApprovalsList(transactionApprovedHd1, employee1,
		// financialYear1,companyId, null,payOutList1, tdsStandardExemption1,
		// otherIncomeList1,true,null);

	}

	/**
	 * Delete employee pay from database based on payStructureHdId (Primary key)
	 */
	@Override
	public void deletePayRevision(Long payStructureHdId) {
		System.out.println("deletePayRevision>>>>>>> payStructureHdId>> " + payStructureHdId);
		payStructureRepository.delete(payStructureHdId);

	}

	/**
	 * to get Employee Pay from database based on employeeId and currentDate and
	 * delete if Employee Pay presents in database
	 */
	@Override
	public void deleteFuturePayStructure(Long employeeId) {
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		PayStructureHd payStructureHd = payStructureRepository.getPayRevision(currentDate, employeeId);
		if (payStructureHd != null && payStructureHd.getPayStructureHdId() != null)
			payStructureRepository.delete(payStructureHd.getPayStructureHdId());
	}

	/**
	 * to get List of Employee Pay from database based on employeeId
	 */
	@Override
	public List<PayStructureHd> getEmployeePayRevisionList(Long employeeId) {
		return payStructureRepository.getEmployeePayRevisionList(employeeId);
	}

	@Override
	public PayStructureHd getPayStructure(Long longPayStructureHdId) {
		return payStructureRepository.findOne(longPayStructureHdId);
	}

	@Override
	public List<Object[]> getEmployees(Long companyId) {
		return payStructureRepository.getEmployees(companyId);
	}

	@Override
	public List<String> arrearCalculationMonths(String processMonth, Long employeeId) {
		List<String> processMonthList = reportPayOutRepository.processMonthList(employeeId);

		List<String> arrearMonthList = new ArrayList<String>();
		for (String month : processMonthList) {
			System.out.println("processMonth>>>>" + processMonth + " ");
			if (DateUtils.getDateForProcessMonth(processMonth).before(DateUtils.getDateForProcessMonth(month))
					|| DateUtils.getDateForProcessMonth(processMonth).equals(DateUtils.getDateForProcessMonth(month))) {
				arrearMonthList.add(month);
			}
		}
		for (String month : arrearMonthList) {
			System.out.println("months>>>" + month);
		}
		return arrearMonthList;
	}

	public Boolean arrearValidation(List<String> processMonthList, Long employeeId) {
		// String query = "SELECT ar.arearId FROM ArearMaster ar JOIN ArearCalculation
		// ac on ac.arearId = ar.arearId WHERE ar.employeeId=?1 AND ac.payrollMonth IN
		// :processMonthList";

		// Query nativeQuery = eManager.createNativeQuery(query);
		// nativeQuery.setParameter(1, employeeId);
		// final List<Object[]> resultList = nativeQuery.getResultList();
		final List<Object[]> resultList = arearRepository.arearListByprocessMonth(processMonthList, employeeId);
		if (resultList.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * to get Employee Pay from database based on employeeId and currentDate
	 */
	@Override
	public PayStructureHd getEndDateNullPayStructure(Long employeeId) {
		return payStructureRepository.monthValidationList(employeeId);

	}

	@Override
	public PayStructureHd findPayStructureOnDate(Date subtractDays, Long employeeId) {
		return payStructureRepository.findPayStructureOnDateAndEmpId(subtractDays, employeeId);
	}

	public Date esiEndDateCalculation(PayStructureHd payStructureHd, Long companyId) throws PayRollProcessException {

		Date esicEndDate = null;

		BigDecimal newEsicBasedAmount = new BigDecimal(0);

		boolean flag = false;
		int processMonth;

		PayStructureHd currentPayStructureHd = payStructureService
				.employeeCurrentPayStructure(payStructureHd.getEmployee().getEmployeeId());

		// BigDecimal currentEsicBasedAmount = new
		// BigDecimal(0);//payStructureRepository.calculateSumOfHeadsBasedOnEsic(payStructureHd.getEmployee().getEmployeeId());
		BigDecimal currentEsicBasedAmount = calculateEsicBasedAmount(currentPayStructureHd);
		processMonth = getMonthCountBasedOnProcessMonth(payStructureHd.getProcessMonth());
		Esi esi = esicService.getESIByPayrollPsMonth(payStructureHd.getProcessMonth(), companyId);

		// esi is active on current paystructre then only true
		if (esi.getMaxGrossLimit().compareTo(currentEsicBasedAmount) >= 0)
			newEsicBasedAmount = calculateEsicBasedAmount(payStructureHd);

		if (esi.getMaxGrossLimit().compareTo(newEsicBasedAmount) < 0) {

			// call esic cycle and get list of esic cycle object

			List<EsiCycle> esiCycleList = esicCycleService.getEsicCycle(esi.getEsiId());
			EsiCycle esiCycleNew = null;
			boolean newYearFlag = false;
			for (EsiCycle esiCycle : esiCycleList) {

				int fromPeriod;
				int toPeriod;

				fromPeriod = getMonthCountBasedOnMonth(esiCycle.getFromperiod());

				toPeriod = getMonthCountBasedOnMonth(esiCycle.getToperiod());
				if (fromPeriod > toPeriod) {
					// toPeriod=toPeriod+12;
					if (toPeriod <= processMonth && fromPeriod >= processMonth) {

						if (fromPeriod <= processMonth && processMonth <= 12)
							newYearFlag = false;
						else
							newYearFlag = true;

						esiCycleNew = esiCycle;
						break;
					}

				}

				if (fromPeriod <=processMonth && toPeriod >= processMonth) {
				    if(fromPeriod==processMonth)
				    	break;
					esiCycleNew = esiCycle;
					break;
				}

			}
			if (esiCycleNew != null) {
				if (newYearFlag) {
					// one year should be add
					String year = payStructureHd.getProcessMonth().substring(4, 8);
					int yearInt = Integer.parseInt(year);
					int y = yearInt + 1;
					String newS = "";
					newS = newS.concat(esiCycleNew.getToperiod() + "-" + y);
					esicEndDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(newS));
				} else {
					String year = payStructureHd.getProcessMonth().substring(4, 8);
					String newS = "";
					newS = newS.concat(esiCycleNew.getToperiod() + "-" + year);
					esicEndDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(newS));
				}
			}

		}
		return esicEndDate;
	}

	public BigDecimal calculateEsicBasedAmount(PayStructureHd payStructureHd) {
		BigDecimal currentEsicBasedAmount = new BigDecimal(0.0);

		for (PayStructure payStructure : payStructureHd.getPayStructures()) {
			PayHead payHead = payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId());
			if (payHead != null)
				if (payHead.getIsApplicableOnEsi().equals("Y"))
					currentEsicBasedAmount = currentEsicBasedAmount.add(payStructure.getAmount());
		}

		return currentEsicBasedAmount;
	}

	public int getMonthCountBasedOnProcessMonth(String processMonth) {
		String month = processMonth.substring(0, processMonth.indexOf("-"));
		int monthumber = 0;

		try {
			Date date = new SimpleDateFormat("MMMM").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			monthumber = cal.get(Calendar.MONTH) + 1;
			System.out.println(monthumber);

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return monthumber;
	}

	public static int getMonthCountBasedOnMonth(String month) {
		int monthumber = 0;
		try {
			Date date = new SimpleDateFormat("MMMM").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			monthumber = cal.get(Calendar.MONTH) + 1;

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return monthumber;
	}

	/**
	 * to get Employee Earning and Deduction view on employeeId and companyId
	 */
	@Override

	public List<PayOut> processPayrollForEarningDeductionView(Long companyId, Long employeeId,
			PayStructureHd payStructureHd, Boolean flag, Boolean exitsPaystructurePayheadsFlag)
			throws PayRollProcessException {
		LOGGER.info(" payStructureHd===== " + "companyId" + companyId + " employeeId " + employeeId
				+ "exitsPaystructurePayheadsFlag...." + exitsPaystructurePayheadsFlag);

		LOGGER.info(" payStructureHd===== " + "companyId" + companyId + " employeeId " + employeeId
				+ "exitsPaystructurePayheadsFlag...." + exitsPaystructurePayheadsFlag);

		List<PayHead> payHeads = new ArrayList<PayHead>();
		// List<PayHead>
		// payHeads=payHeadService.findAllEarnigPaystructurePayHeads(companyId);

		if (exitsPaystructurePayheadsFlag)
			payHeads = payHeadService.findAllPayHeadOfCompany(companyId);
		else
			payHeads = payHeadService.findActivePayHeadOfCompany(companyId);
		// System.out.println(payHeads);
		// payStructureService.processPayrollForEarningDeductionView(companyId,
		// employeeId,payStructureHd);
		List<PayOut> payOuts = payRollService.processPayRollForEarningDeductionView(companyId, employeeId,
				payStructureHd, flag);

		for (PayOut payOut : payOuts) {
			for (PayHead payHead : payHeads) {
				if (payHead.getPayHeadId() == payOut.getId().getPayHeadId()) {
					payOut.setPayHeadName(payHead.getPayHeadName());
					payOut.setEarningDeduction(payHead.getEarningDeduction());
					// System.out.println("-------------payHead.getPayHeadName()-------"+payHead.getPayHeadName());
					break;
				}
			}
		}

		return payOuts;
	}

	@Override

	public PayStructureHdDTO calculateEarningDeduction(Long companyId, Long employeeId, PayStructureHd payStructureHd,
			Boolean flag, Boolean exitsPaystructurePayheadsFlag) throws PayRollProcessException {

		List<PayOut> payOutList = payStructureService.processPayrollForEarningDeductionView(companyId, employeeId,
				payStructureHd, flag, exitsPaystructurePayheadsFlag);

		PayStructureHdDTO payStructureHdDto = new PayStructureHdDTO();
		List<PayOutDTO> payOutDtoList = new ArrayList<PayOutDTO>();
		List<PayStructureDTO> payStructureDtoList = new ArrayList<PayStructureDTO>();

		for (PayOut payOut : payOutList) {
			if ((StatusMessage.EARNING_CODE).equals(payOut.getEarningDeduction())) {
				PayStructureDTO payStructureDTO = new PayStructureDTO();
				payStructureDTO.setAmount(payOut.getAmount());
				payStructureDTO.setPayHeadId(payOut.getId().getPayHeadId());
				payStructureDTO.setPayHeadName(payOut.getPayHeadName());
				payStructureDtoList.add(payStructureDTO);
			} else {
				PayOutDTO payOutDTO = new PayOutDTO();
				payOutDTO.setAmount(payOut.getAmount());
				payOutDTO.setPayHeadId(payOut.getId().getPayHeadId());
				payOutDTO.setPayHeadName(payOut.getPayHeadName());
				payOutDtoList.add(payOutDTO);
			}
		}
		payStructureHdDto.setPayOutDtoList(payOutDtoList);
		payStructureHdDto.setPayStructureDtoList(payStructureDtoList);
		return payStructureHdDto;
	}

	@Override
	public List<Integer> getPayStructureHdEmployeeId(Long companyId) {
		return payStructureRepository.getPayStructureHdEmployeeId(companyId, new Date());
	}

	@Override
	public void letterGenerater(PayStructureHd payStructureHd, Long companyId, List<String> processMonthList) {
		LOGGER.info("-----------------==letterGenerater==-----------");
		Letter ltr = null;
		Long count = 0l;
		EmployeeLetter employeeLetter = new EmployeeLetter();
		EmployeeLetter empLetter = null;
		EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();
		count = payStructureRepository.checkEmployeePayStructure(payStructureHd.getEmployee().getEmployeeId());
		List<Letter> ltrList = new ArrayList<Letter>();
		if (count == 1l) {
			// fetch all letter list based on grading system
			ltrList = letterService.findLetterByTypeList(StatusMessage.APPOINTMENT_LETTER_CODE);		 
			for (Letter letter : ltrList) {
				if (ltrList.size() > 1) {
					if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
						Employee employee = employeePersonalInformationService
								.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
						if (letter.getGradeId().equals(employee.getGradesId())) {
							ltr = letter;
						}
					} // close nested if block
				} // close if block
				else {
					if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
						Employee employee = employeePersonalInformationService
								.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
						if (letter.getGradeId().equals(employee.getGradesId())) {
							ltr = letter;
						}
					} else {
						String enableGrade = StatusMessage.NO_CODE;
						ltr = letterService.findLetterByEnableGrade(StatusMessage.APPOINTMENT_LETTER_CODE, enableGrade);
				 
					}
				} // close else block
			} // close for loop

			empLetter = empLetterService.findEmployeeLetterById(payStructureHd.getEmployee().getEmployeeId(),
					ltr.getLetterId());
			if (empLetter != null) {
				if (empLetter.getEmpLetterId() != null && empLetter.getEmpLetterId() != 0)
					employeeLetter.setEmpLetterId(empLetter.getEmpLetterId());
				else
					employeeLetter.setEmpLetterId(null);
			} else {
				employeeLetter.setEmpLetterId (null);
			}
		} else {

			// fetch all letter list based on grading system
			ltrList = letterService.findLetterByTypeList(StatusMessage.APPRAISAL_LETTER_CODE);
			System.out.println("ltrList------------" + ltrList.size());
			for (Letter letter : ltrList) {
				if (ltrList.size() > 1) {
					if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
						Employee employee = employeePersonalInformationService
								.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());						 
						if (letter.getGradeId().equals(employee.getGradesId())) {
							ltr = letter;					 
						}
					}
				} else {
					if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
						Employee employee = employeePersonalInformationService
								.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
						if (letter.getGradeId().equals(employee.getGradesId())) {
							ltr = letter;						 
						}
					} else {
						String enableGrade = StatusMessage.NO_CODE;
						ltr = letterService.findLetterByEnableGrade(StatusMessage.APPRAISAL_LETTER_CODE, enableGrade);				 
					}
				}
			} // close for loop

		//	ltr = letterService.findLetterByType(StatusMessage.APPRAISAL_LETTER_CODE);
			empLetter = empLetterService.findEmployeeLetterById(payStructureHd.getEmployee().getEmployeeId(),
					ltr.getLetterId());
			if (empLetter != null) {
				if (empLetter.getEmpLetterId() != null && empLetter.getEmpLetterId() != 0)
					employeeLetter.setEmpLetterId(empLetter.getEmpLetterId());
				else
					employeeLetter.setEmpLetterId(null);
			} else {
				employeeLetter.setEmpLetterId(null);
			}
		}

		Employee employee = employeePersonalInformationService
				.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
		employeeLetter.setEmpId(employee.getEmployeeId());
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
		employeeLetter.setLetterId(ltr.getLetterId());
		employeeLetter.setEmpStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setDateCreated(new Date());
		employeeLetter.setUserId(employee.getUserId());
		employeeLetter.setHRStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
		EmployeeLetter empLetter1 = empLetterService.saveLtr(employeeLetter);

		ApprovalHierarchyMaster approvalHierarchyMaster = approvalHierarchyMasterService
				.getMasterApprovalStatus(ltr.getCompanyId(), ltr.getLetterId());
		EmployeeLettersTransaction empLettersTransaction = null;
		if (approvalHierarchyMaster != null) {
			if (approvalHierarchyMaster.getActiveStatus().equalsIgnoreCase(StatusMessage.ACTIVE_CODE)) {
				List<Object[]> approvalList = approvalHierarchyMasterService.findLetterApprovalById(ltr.getLetterId());

				int i = 0;
				Long designationId = 0l;
				for (Object[] obj : approvalList) {
					if (i == 0) {
						designationId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
						i++;
					}
				}

				if (empLetter1 != null) {
					empLettersTransaction = employeeLettersTransactionRepository
							.findByLetterId(empLetter1.getEmpLetterId());
					if (empLettersTransaction != null) {
						if (empLettersTransaction.getEmployeeLetterTransactionId() != null
								&& empLettersTransaction.getEmployeeLetterTransactionId() != 0) {
							employeeLettersTransaction.setEmployeeLetterTransactionId(
									empLettersTransaction.getEmployeeLetterTransactionId());
						} else
							employeeLettersTransaction.setEmployeeLetterTransactionId(null);
					} else
						employeeLettersTransaction.setEmployeeLetterTransactionId(null);
				}

				employeeLettersTransaction.setDateCreated(new Date());
				employeeLettersTransaction.setUserId(employee.getUserId());

				employeeLettersTransaction.setDesignationId(designationId);
				employeeLettersTransaction.setEmployeeLetter(empLetter1);
				employeeLettersTransaction.setLevels(StatusMessage.LEVEL_CODE);
				employeeLettersTransaction.setStatus(StatusMessage.PENDING_CODE);

				employeeLettersTransaction.setCompanyId(employee.getCompanyId());
				employeeLettersTransaction.setDesignationId(designationId);

				employeeLettersTransactionService.saveEmpLetterTransaction(employeeLettersTransaction);
				LOGGER.info("-----------------=Done===letterGenerater====-----------");
			}
		}
	}

	@Override
	public void generateLetters(Long companyId, Long employeeId, Long employeeLetterId) throws ErrorHandling {

		PayStructureHd payStructureHd = null;


		LOGGER.info("-----------------=Start===EmployeeLetter====-----------");
		Letter ltr = null;
		List<Letter> ltrList = new ArrayList<Letter>();
		Long count = 0l;
		count = payStructureRepository.checkEmployeePayStructure(employeeId);
		if (count == 1l) {
			payStructureHd = payStructureRepository.monthValidationList(employeeId);
		ltr = letterService.findLetterByType(StatusMessage.APPOINTMENT_LETTER_CODE);
		} else {
			payStructureHd = payStructureRepository.monthValidationList(employeeId);
			ltr = letterService.findLetterByType(StatusMessage.APPRAISAL_LETTER_CODE);
		}

		if (ltr != null) {

			EmployeeLetter employeeLetter = empLetterService.findEmpLetterByStatus(employeeLetterId, ltr.getLetterId());
			EmployeeLetter empLetter = new EmployeeLetter();
			Employee employee = employeePersonalInformationService
					.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
			empLetter.setEmpId(employee.getEmployeeId());
			empLetter.setEmpLetterId(employeeLetter.getEmpLetterId());
			SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
			String effictive = sm.format(payStructureHd.getEffectiveDate());
			String currentDT = sm.format(new Date());
			String doj = sm.format(employee.getDateOfJoining());

			PayStructure p1 = new PayStructure();
			PayStructure p2 = new PayStructure();
			BigDecimal basic_year = new BigDecimal(0);
			BigDecimal hra = new BigDecimal(0);
			int count1 = 0;
			List<PayStructure> list = payStructureNewRepository
					.findAllPayStructureById(payStructureHd.getPayStructureHdId());
			for (PayStructure item : list) {
				if (count1 == 0) {
					basic_year = item.getAmount();
					count1++;
				}
				hra = item.getAmount();

			}

			BigDecimal professinal_Tx = new BigDecimal(0);
			BigDecimal professinal_Tx_Year = new BigDecimal(0);
			BigDecimal employee_PF = new BigDecimal(0);
			BigDecimal employee_PF_year = new BigDecimal(0);
			BigDecimal employer_PF = new BigDecimal(0);
			BigDecimal employer_PF_year = new BigDecimal(0);
			BigDecimal employee_HRA = new BigDecimal(0);
			BigDecimal employee_HRA_Year = new BigDecimal(0);
			BigDecimal employee_Esic = new BigDecimal(0);
			BigDecimal employee_Esic_Year = new BigDecimal(0);
			BigDecimal employer_Esic = new BigDecimal(0);
			BigDecimal employer_Esic_Year = new BigDecimal(0);
			BigDecimal current_Ad_Bonus = new BigDecimal(0);
			BigDecimal current_Ad_Bonus_Year = new BigDecimal(0);
			BigDecimal gross_Salary_Year = new BigDecimal(0);
			BigDecimal current_CTC_Year = new BigDecimal(0);

			basic_year.add(basic_year.multiply(new BigDecimal(12)));

			if (payStructureHd.getProfessionalTax() != null) {
				professinal_Tx = professinal_Tx.add(payStructureHd.getProfessionalTax());
				professinal_Tx_Year = professinal_Tx_Year
						.add(payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEpfEmployee() != null) {
				employee_PF = employee_PF.add(payStructureHd.getEpfEmployee());
				employee_PF_year = employee_PF_year.add(payStructureHd.getEpfEmployee().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEpfEmployer() != null) {
				employer_PF = employer_PF.add(payStructureHd.getEpfEmployer());
				employer_PF_year = employer_PF_year.add(payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEsiEmployer() != null) {
				employer_Esic = employer_Esic.add(payStructureHd.getEsiEmployer());
				employer_Esic_Year = employer_Esic_Year
						.add(payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)));
			}
			if (payStructureHd.getEsiEmployee() != null) {
				employee_Esic = employee_Esic.add(payStructureHd.getEsiEmployee());
				employee_Esic_Year = employee_Esic_Year
						.add(payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)));
			}

			gross_Salary_Year.add(payStructureHd.getGrossPay().multiply(new BigDecimal(12)));
			current_CTC_Year.add(payStructureHd.getCtc().multiply(new BigDecimal(12)));
			StringBuilder sb = new StringBuilder();

			sb.append(ltr.getLetterDecription().replace(StatusMessage.EMPLOYEE_NAME,
					employee.getFirstName() + " " + employee.getLastName()).replace(StatusMessage.LOCATION, employee.getCity().getCityName())
					.replace(StatusMessage.CITY_NAME, employee.getAddress2().getAddressText()).replace(StatusMessage.LETTER_NAME, ltr.getLetterName())
					.replace(StatusMessage.COMPANY_NAME, employee.getCompany().getCompanyName())
					.replace(StatusMessage.EFFECTIVE_DATE, effictive)
					.replace(StatusMessage.EMPLOYEE_CODE, employee.getEmployeeCode())
					.replace(StatusMessage.DESIGNATION_NAME, employee.getDesignation().getDesignationName())
					.replace(StatusMessage.EMPLOYEE_ESIC, employee_Esic.toString())
					.replace(StatusMessage.CURRENT_DATE, currentDT)
					.replace(StatusMessage.EMPLOYEE_BASIC, payStructureHd.getNetPay().toString())
					.replace(StatusMessage.DATE_OF_JOINING, doj)
					.replace(StatusMessage.AUTHORIZED_PERSON, employee.getFirstName())
					.concat(
							"<br><table border='1'><tbody><tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Salary Component</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Monthly</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Annually</strong></span></td></tr>"));
			for (PayStructure pay : list) {
				pay.setPayHead(payHeadRepository.findOne(pay.getPayHead().getPayHeadId()));
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
								+ pay.getPayHead().getPayHeadName() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pay.getAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pay.getAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getGrossPay() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Gross</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getGrossPay() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getGrossPay().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEpfEmployee() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>PF-Employee </span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployee() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployee().multiply(new BigDecimal(12))
						+ "</span></td></tr>");
			}
			if (payStructureHd.getEpfEmployer() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>PF-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployer() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEpfEmployer() != null || payStructureHd.getEpfEmployer() != null) {
				BigDecimal pensionEmp = new BigDecimal(0);
				BigDecimal empr = new BigDecimal(0);
				BigDecimal emp = new BigDecimal(0);
				emp = payStructureHd.getEpfEmployee();
				empr = payStructureHd.getEpfEmployer();
				pensionEmp = emp.subtract(empr);
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Pension-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pensionEmp + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pensionEmp.multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEsiEmployee() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employee</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployee() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getEsiEmployer() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployer() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getProfessionalTax() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Professional Tax</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getProfessionalTax() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getLwfEmployeeAmount() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>LWF-Employee</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployeeAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployeeAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getLwfEmployerAmount() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>LWF-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployerAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployerAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getNetPay() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Nat Payable</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getNetPay().toBigInteger() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getNetPay().multiply(new BigDecimal(12)).toBigInteger() + "</span></td></tr>");
			}

			if (payStructureHd.getCtc() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>CTC</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getCtc().toBigInteger() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getCtc().multiply(new BigDecimal(12)).toBigInteger() + "</span></td></tr>");
			}
//			if (ltr.getLetterName().equals("Appointment Letter")) {
//				sb.append(
//						"</tr></tbody></table><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>DECLARATION BY THE EMPLOYEE&nbsp;</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;I have read and understood the above terms and conditions of services and agreed without any coercion or pressure to abide by the same.</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p><p>Date: <strong>"
//								+ currentDT + "</strong></p><p>Place: <strong>" + employee.getCity().getCityName()
//								+ "</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></div>");
//			} else {
//
//				sb.append(
//						"</tr></tbody></table><p><br data-cke-filler=\"true\"></p><p><br data-cke-filler=\\\"true\\\"></p>"
//								+ "<p>For " + "" + employee.getCompany().getCompanyName()
//								+ "</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p></div>");
//			}

			empLetter.setLetterDecription(sb.toString());
			empLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
			empLetter.setLetterId(ltr.getLetterId());
			empLetter.setEmpStatus(StatusMessage.PENDING_CODE);
			empLetter.setDateCreated(new Date());
			empLetter.setUserId(employee.getUserId());
			empLetter.setHRStatus(StatusMessage.PENDING_CODE);
			empLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);

			empLetter = empLetterService.saveLtr(empLetter);

			ApprovalHierarchyMaster approvalHierarchyMaster = approvalHierarchyMasterService
					.getMasterApprovalStatus(ltr.getCompanyId(), ltr.getLetterId());
			EmployeeLettersTransaction empLettersTransaction = employeeLettersTransactionRepository
					.findByLetterId(empLetter.getEmpLetterId());
			List<EmployeeLettersTransaction> empLettersTransactionList = employeeLettersTransactionRepository
					.findByLetterList(empLetter.getEmpLetterId());
			if (approvalHierarchyMaster.getActiveStatus().equalsIgnoreCase(StatusMessage.ACTIVE_CODE)) {

				List<Object[]> approvalList = approvalHierarchyMasterService.findLetterApprovalById(ltr.getLetterId());
				int i = 0;
				Long designationId = 0l;
				for (Object[] obj : approvalList) {
					if (i == 0) {
						designationId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
						i++;
					}
				}

				EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();
				employeeLettersTransaction.setEmployeeLetter(empLetter);
				employeeLettersTransaction.setLevels(StatusMessage.LEVEL_CODE);
				employeeLettersTransaction.setStatus(StatusMessage.PENDING_CODE);
				employeeLettersTransaction.setCompanyId(ltr.getCompanyId());
				employeeLettersTransaction.setDesignationId(designationId);
				if (empLettersTransaction != null) {
					if (empLettersTransaction.getEmployeeLetterTransactionId() != null
							&& empLettersTransaction.getEmployeeLetterTransactionId() != 0) {
						if (empLettersTransactionList != null) {
							for (EmployeeLettersTransaction objects : empLettersTransactionList) {
								if(objects.getEmployeeLetterTransactionId()!=null) {
									employeeLettersTransactionRepository.delete(objects.getEmployeeLetterTransactionId());
								}
								
							}
						}
					
					} else {
						employeeLettersTransaction.setEmployeeLetterTransactionId(null);
						employeeLettersTransaction.setDateCreated(new Date());
						employeeLettersTransaction.setUserId(employee.getUserId());
					}
				} 
				employeeLettersTransactionService.saveEmpLetterTransaction(employeeLettersTransaction);
			}
			LOGGER.info("PayStructureServiceImpl.generateLetters()-> generate Letter Successfully");
		} // close if
		else
			throw new ErrorHandling("Letter not create Please contact to Admin");
	}// close method
	
	
	
	public List<ReportPayOut> findReportPayoutForMonth(Date attendanceDate , Long companyId) {
		String processMonth = DateUtils.getDateStringWithYYYYMMDD(attendanceDate);
		return reportPayOutRepository.findReportPayoutForMonth(processMonth , companyId);
	}
	
}
