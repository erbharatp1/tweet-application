package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.csipl.common.model.DrowpdownHd;
import com.csipl.common.services.dropdown.DropDownHdService;
import com.csipl.common.util.EnumUtil;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.ActiveStatusEnum;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.enums.EarningDeductionEnum;
import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.enums.StandardEarningEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payroll.ArearCalculation;
import com.csipl.hrms.model.payroll.ArearMaster;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LoanEMI;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.model.payroll.OneTimeDeduction;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.model.payrollprocess.ArrearPayOut;
import com.csipl.hrms.model.payrollprocess.ArrearReportPayOut;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.PayRegister;
import com.csipl.hrms.model.payrollprocess.PayRegisterHd;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOutPK;
import com.csipl.hrms.service.adaptor.PayrollArrearAdaptor;
import com.csipl.hrms.service.employee.EmployeeIdProofService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.EmployeeStatuaryService;
import com.csipl.hrms.service.employee.FamilyService;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.employee.repository.PayStructureRepository;
import com.csipl.hrms.service.organization.DepartmentServiceImpl;
import com.csipl.hrms.service.organization.repository.CityRepository;
import com.csipl.hrms.service.payroll.repository.ArearMasterRepository;
import com.csipl.hrms.service.payroll.repository.ArrearPayOutRepository;
import com.csipl.hrms.service.payroll.repository.ArrearReportPayOutRepository;
import com.csipl.hrms.service.payroll.repository.AttendanceRepository;
import com.csipl.hrms.service.payroll.repository.PayOutRepository;
import com.csipl.hrms.service.payroll.repository.PayrollRegisterRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.hrms.service.util.ConverterUtil;
import com.hrms.org.payrollprocess.PayRollValidation;
import com.hrms.org.payrollprocess.deduction.CalculationPayHead;
import com.hrms.org.payrollprocess.dto.PayRollProcessDTO;
import com.hrms.org.payrollprocess.dto.PayRollProcessHDDTO;
import com.hrms.org.payrollprocess.dto.PayrollInputDTO;
import com.hrms.org.payrollprocess.earning.AttendanceBased;
import com.hrms.org.payrollprocess.earning.EarningTypeFactory;
import com.hrms.org.payrollprocess.loan.LoanCalculation;
import com.hrms.org.payrollprocess.loan.OneTimeCalculation;
import com.hrms.org.payrollprocess.util.PayRollProcessUtil;


@Transactional
@Service("payRollService")
public class PayRollServiceImpl implements PayRollService {

	private final Logger logger = LoggerFactory.getLogger(PayRollServiceImpl.class);

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EpfService epfService;

	@Autowired
	private EsicService esicService;

	@Autowired
	ProfessionalTaxService professionalTaxService;
	
	@Autowired
	LabourWelfareFundService labourWelfareFundService;

	@Autowired
	PayOutRepository payOutRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	EmployeePersonalInformationService employeeService;

	@Autowired
	ReportPayOutRepository reportPayOutRepository;

	@Autowired
	PayrollControlService payrollControlService;

	@Autowired
	DropDownHdService dropDownHdService;

	@Autowired
	LoanIssueService loanIssueService;

	@Autowired
	EmployeeStatuaryService employeeStatuaryService;

	@Autowired
	EmployeeIdProofService employeeIdProofService;

	@Autowired
	PayRollLockService payRollLockService;

	@Autowired
	PayHeadService payHeadService;

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	OneTimeEarningDeductionService oneTimeEarningDeductionService;

	@Autowired
	FamilyService familyService;

	@Autowired
	ArearService arearService;

	@Autowired
	ArrearPayOutRepository arrearPayOutRepository;

	@Autowired
	ArrearReportPayOutRepository arrearReportPayOutRepository;

	@Autowired
	ArearMasterRepository arearMasterRepository;

	@Autowired
	PayrollRegisterService payrollRegisterService;
	@Autowired
	PayrollRegisterRepository payrollRegisterRepository;

	@Autowired
	EmployeePersonalInformationRepository employeePersonalInformationRepository;

	@Autowired
	PayStructureRepository payStructureRepository;

	PayRollValidation payRollValidation = new PayRollValidation();

	ArearMaster arearMaster = new ArearMaster();
	List<ArearCalculation> arearCalculationListNew = new ArrayList<ArearCalculation>();

	@Autowired
	TdsDeductionService tdsDeductionService;

	/**
	 * @throws PayRollProcessException
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.csipl.hrms.service.payroll.PayRollService#processPayRollByEmployees(java.
	 * lang.Long, java.lang.String, java.util.List, long)
	 */
	@Override
	public void processPayRollByEmployees(Long companyId, String payMonth, List<Long> employeeIds, long userId,
			boolean isArrearCalculation, PayStructureHd payStructureHdForArrear, ArearMaster arearMaster)
			throws PayRollProcessException {

		List<String> employeeCodeList1 = new ArrayList<String>();
		
		

		for (Long empId : employeeIds) {
			String employeeCode = employeePersonalInformationRepository.getEmployeeCode(empId);
			employeeCodeList1.add(employeeCode);
		}

		List<String> employeeIdReportpayOutList = payStructureRepository.getEmployeeIdFromPayStructureHD();

		employeeIdReportpayOutList.retainAll(employeeCodeList1);
		employeeCodeList1.removeAll(employeeIdReportpayOutList);

		if (employeeCodeList1.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" Employee Codes are not present in PayStructure" + employeeCodeList1);

			throw new PayRollProcessException(builder.toString());
		}

		StringBuilder employeeList = getEmployeeIdList(employeeIds);

		PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(companyId, payMonth);

		List<String> employeeCodeList2 = new ArrayList<String>();
		List<String> empIdAttandanceList = attendanceRepository.empIDAttendenceList(companyId, payMonth);
		for (Long empId : employeeIds) {
			String employeeCode = employeePersonalInformationRepository.getEmployeeCode(empId);
			employeeCodeList2.add(employeeCode);
		}
		empIdAttandanceList.retainAll(employeeCodeList2);

		employeeCodeList2.removeAll(empIdAttandanceList);

		if (employeeCodeList2.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" Employee Codes are not present in Attendance" + employeeCodeList2);

			throw new PayRollProcessException(builder.toString());
		}
//				if(empIdAttandanceList.size() > 0) {
//					
//					StringBuilder builder = new StringBuilder();
//					builder.append(" Employee Codes are not present in Attendance" + employeeCodeList);
//
//					throw new PayRollProcessException(builder.toString());
//				}

		processPayRollByDeptEmp(companyId, userId, payrollControl, employeeList, isArrearCalculation,
				payStructureHdForArrear, arearMaster, payMonth);

	}

	public StringBuilder getEmployeeIdList(List<Long> employeeIds) {
		StringBuilder employeeList = new StringBuilder();

		// if(employeeList!=null && employeeList.length()>0) {
		employeeIds.forEach(employeeId -> {
			employeeList.append(employeeId + ",");
		});

		employeeList.setLength(employeeList.length() - 1);
		// }

	
		return employeeList;
	}
	
	
	
   /*
    * @param companyId
    * @param payroll month
    * @param department list
    * @param userId
    * @param payStructureHdForArrear
    * @return void
    * @see com.csipl.hrms.service.payroll.PayRollService#processPayRollByDepartments(java.lang.Long, java.lang.String, java.util.List, java.lang.Long, com.csipl.hrms.model.employee.PayStructureHd)
    * this function only check  all employees  for dept lsit are eligible for payroll calcuation on the basis of existance in  attendance and paystructure table
    */
	@Override
	public void processPayRollByDepartments(Long companyId, String payMonth, List<Long> departmentIdList, Long userId,
			PayStructureHd payStructureHdForArrear) throws PayRollProcessException {
		boolean isArrearCalculation = false;

		StringBuilder employeeList = new StringBuilder();
		HashMap<Integer, String> empMap = new HashMap<>();
		
		PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(companyId, payMonth);
		int index = 0;

		for (Long deptIds : departmentIdList) {
			List<Integer> empIdList = new ArrayList<Integer>();
			List<String> empCodeListForAttandance = new ArrayList<String>();// whose salary not done
			List<String> empIdAttandanceList = attendanceRepository.empIDAttendenceList(companyId, payMonth);
			List<Object[]> empList = reportPayOutRepository.checkPayRollForEmployeeList(deptIds, payMonth);

			for (Object[] obj : empList) {// whose salary not done
				empMap.put((Integer) obj[0], (String) obj[1]);
				empIdList.add((Integer) obj[0]);
				empCodeListForAttandance.add((String) obj[1]);

			}
			empIdAttandanceList.retainAll(empCodeListForAttandance);
			empCodeListForAttandance.removeAll(empIdAttandanceList);

			if (empCodeListForAttandance.size() > 0) {
				StringBuilder builder = new StringBuilder();
				builder.append(" Employee Codes are not present in Attendance" + empCodeListForAttandance);

				throw new PayRollProcessException(builder.toString());
			}

		

			List<String> employeeCodeListForPayStructure = new ArrayList<String>();

			for (Integer empId : empIdList) {
				String employeeCode = employeePersonalInformationRepository.getEmployeeCode(empId.longValue());
				employeeCodeListForPayStructure.add(employeeCode);
			}

			List<String> employeeIdReportpayOutList = payStructureRepository.getEmployeeIdFromPayStructureHD();

			employeeIdReportpayOutList.retainAll(employeeCodeListForPayStructure);
			employeeCodeListForPayStructure.removeAll(employeeIdReportpayOutList);

			if (employeeCodeListForPayStructure.size() > 0) {
				StringBuilder builder = new StringBuilder();
				builder.append(" Employee Codes are not present in PayStructure" + employeeCodeListForPayStructure);

				throw new PayRollProcessException(builder.toString());
			}

			for (Integer empIds : empIdList) {

				if (index == 0) {
					employeeList.append(empIds.toString());
					index++;
				} else
					employeeList.append("," + empIds.toString());

			}

		}
		if (employeeList.length() > 0) {
			processPayRollByDeptEmp(companyId, userId, payrollControl, employeeList, isArrearCalculation,
					payStructureHdForArrear, null,payMonth);
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("No One Employee Codes  In this Department to Processing");

			throw new PayRollProcessException(builder.toString());

		}

	}

	
	
	
	public PayrollInputDTO isPendingRequestLeaveAndARByMonth(int month) {
		
        List<Object[]> pendingRequestLeaveAndARObjList =attendanceRepository.isPendingRequestLeaveAndARByMonth(month);
    	HashMap< Long, String> map = new HashMap<Long ,String>();
        PayrollInputDTO leaveEntryDto = new PayrollInputDTO();
      		for (Object[] leaveObj : pendingRequestLeaveAndARObjList) {
      			
      		
      			Long employeeId = leaveObj[0] != null ? Long.parseLong(leaveObj[0].toString()) : null;
      			Long departmentId = leaveObj[1] != null ? Long.parseLong(leaveObj[1].toString()) : null;
      			String deptName =  leaveObj[2] != null ? (String) leaveObj[2] : null;
      			map.put(employeeId, deptName);
      			
      			leaveEntryDto.setMap(map);
      			
      		}
      		return leaveEntryDto;
	}
	
	
 
	
	
	/**
	 * 
	 * @param companyId
	 * @param departmentId
	 * @param epf
	 * @param esi
	 * @throws PayRollProcessException
	 * @respons void
	 * this function calculate net pay 
	 */
	// @Override
	public void processPayRollByDeptEmp(Long companyId, Long userId, PayrollControl payrollControl,
			StringBuilder employeeIds, boolean isArrearCalculation, PayStructureHd payStructureHdForArrear,
			ArearMaster arearMaster,String payMonth) throws PayRollProcessException {
		PayrollArrearAdaptor payrollArrearAdaptor = new PayrollArrearAdaptor();
		Epf epf = epfService.getEPFByPayrollPsMonth(payMonth,companyId);
		//Esi esi = esicService.getESI(companyId);
		 Esi esi = esicService.getESIByPayrollPsMonth(payMonth,companyId);
 
		DrowpdownHd bankList = dropDownHdService.findDropDownById(DropDownEnum.BankName.drowpdownId);

		List<ReportPayOut> reportPayOuts = fetchEmployeeForSalary(companyId, employeeIds,
				payrollControl.getProcessMonth());

		List<PayOut> payOuts = new ArrayList<PayOut>();
        
		
		
		
		
		calculateNetPay(reportPayOuts, epf, esi, payOuts, userId, payrollControl, bankList, isArrearCalculation,
				payStructureHdForArrear);

		if (isArrearCalculation) {
			ArrearReportPayOut arrearReportPayOut = payrollArrearAdaptor
					.reportPayOutListToArrearReportPayOutConversion(reportPayOuts);

			List<ArrearPayOut> arrearPayOutList = payrollArrearAdaptor.payOutListToArrearPayOutListConversion(payOuts);

			arrearPayOutRepository.save(arrearPayOutList);
			arrearReportPayOutRepository.save(arrearReportPayOut);

			List<Object[]> reportPayOutObjectList;
			List<Object[]> rayOutObjectList;

			reportPayOutObjectList = reportPayOutRepository.employeeArrearCalculationOnReportPayOut(
					arrearReportPayOut.getId().getEmployeeId(), arrearReportPayOut.getId().getProcessMonth());

			// rayOutObjectList =
			// payOutRepository.employeeArrearCalculationOnPayOut(arrearReportPayOut.getId().getEmployeeId(),
			// arrearReportPayOut.getId().getProcessMonth());

			ReportPayOut reportPayOut = payrollArrearAdaptor
					.reportPayOutObjectListToreportPayOutConversion(reportPayOutObjectList);
			setEmployeePayHeadNullValue(reportPayOut);
			// PayOut payOut=
			// payrollArrearAdaptor.payOutObjectListTopayOutConversion(rayOutObjectList);

			finalArrearCalculation(reportPayOut, arrearReportPayOut, arearMaster);
		} else {

			payOutRepository.save(payOuts);
			reportPayOutRepository.save(reportPayOuts);

			PayRegisterHd payRegisterHd = new PayRegisterHd();
			payRegisterHd.setProcessMonth(payrollControl.getProcessMonth());
			payRegisterHd.setUserId(userId);
			payRegisterHd.setDateCreated(new Date());
			payRegisterHd.setDateUpdate(new Date());
			payRegisterHd.setCompanyId(companyId);

			List<PayRegister> payRegisterList = new ArrayList<>();

			for (ReportPayOut reportPayout : reportPayOuts) {
				PayRegister payRegister = new PayRegister();
				payRegister.setPayRegisterHd(payRegisterHd);
				Department dept = new Department();
				dept.setDepartmentId(reportPayout.getDepartmentId());
				payRegister.setDepartment(dept);
				Employee emp = new Employee();
				emp.setEmployeeId(reportPayout.getId().getEmployeeId());
				payRegister.setEmployee(emp);
				payRegister.setPayRegisterHd(payRegisterHd);
				payRegister.setEmployeeCode(reportPayout.getEmployeeCode());
				payRegister.setUserId(userId);
				payRegister.setDateCreated(new Date());
				payRegister.setDateUpdate(new Date());
				payRegister.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());
				payRegister.setPayrollLockFlag(Boolean.FALSE);
				payRegisterList.add(payRegister);
			}

			payRegisterHd.setPayRegisters(payRegisterList);
			payrollRegisterService.save(payRegisterHd);

		}

	}

	/**
	 * @throws PayRollProcessException
	 * 
	 */
	/**
	 * 
	 * @param companyId
	 * @param departmentId
	 * @return
	 */
	private List<ReportPayOut> fetchEmployeeForSalary(long companyId, StringBuilder employeeIds, String payMonth) {

		List<ReportPayOut> reportPayOuts = new ArrayList<ReportPayOut>();

		reportPayOuts = attendanceService.fetchEmployeeForSalary(companyId, employeeIds, payMonth);

		return reportPayOuts;
	};

	/**
	 * 
	 * @param departmentId
	 * @return
	 */
	private List<Employee> fetchEmployeeOfDepartment(long departmentId) {

		List<Employee> employees = new ArrayList<Employee>();

		employees = attendanceRepository.fetchEmployeeOfDepartment(departmentId);

		return employees;
	};

	/**
	 * 
	 * @param attendanceList
	 * @param epf
	 * @param esi
	 * @return
	 * @throws PayRollProcessException 
	 */
	private List<PayOut> calculateNetPay(List<ReportPayOut> reportPayOuts, Epf epf, Esi esi, List<PayOut> payOuts,
			long userId, PayrollControl payrollControl, DrowpdownHd bankList, boolean isArrearCalculation,
			PayStructureHd payStructureHdForArrear) throws PayRollProcessException {

		EnumUtil enumUtil = new EnumUtil();
		String bankName = null;
		ArearMaster arearMaster = new ArearMaster();
		List<LoanIssue> loanIssues = new ArrayList<LoanIssue>();
		List<OneTimeEarningDeduction> oneTimeDeductionList = new ArrayList<OneTimeEarningDeduction>();
		List<OneTimeEarningDeduction> oneTimeEarningList = new ArrayList<OneTimeEarningDeduction>();
		BigDecimal payAbleDays = new BigDecimal(0.00);
		for (ReportPayOut reportPayOut : reportPayOuts) {
			reportPayOut.setUserId(userId);
			reportPayOut.setDateCreated(new Date());
            reportPayOut.setIsEpfOnActualAmount(epf.getIsActual());
			bankName = enumUtil.getEnumValue(bankList, reportPayOut.getBankShortName());

			reportPayOut.setBankName(bankName);

			List<EmployeeStatuary> empStatuaries = employeeStatuaryService
					.findAllEmployeeStatuary(reportPayOut.getId().getEmployeeId());

			List<EmployeeIdProof> idList = employeeIdProofService
					.findAllemployeeIdProofs(reportPayOut.getId().getEmployeeId());

			if (empStatuaries != null) {
				setEmployeeStatuary(reportPayOut, empStatuaries);
			}

			if (idList != null) {
				setEmployeeID(reportPayOut, idList);
			}

			//* Payable days calculation change according to neelesh sir*//
			// Payable_Days= Presense+weekoff+publicholidays+leaves
			// Paydays is Days in month
			
			BigDecimal payDays = new BigDecimal(payrollControl.getPayrollDays());
			//BigDecimal daysWorkedStep1 = payDays.subtract(reportPayOut.getAbsense());
			reportPayOut.setPayDays( payDays);
			
//			BigDecimal Days=reportPayOut.getPresense().add(reportPayOut.getWeekoff());
//			payAbleDays=Days.add(reportPayOut.getLeaves()==null?new BigDecimal(0.00):reportPayOut.getLeaves());
//			payAbleDays=payAbleDays.add(reportPayOut.getPublicholidays());
//			reportPayOut.setPayableDays(payAbleDays);
			
			
			//new changes applied on-20/12/2019 -by nidhi -according to neelesh sir
			//if weekoff flag is N - don't add weekoff
			//if holiday flag is N -don't add holiday
			
			BigDecimal Days = reportPayOut.getPresense();
			payAbleDays=Days.add(reportPayOut.getLeaves()==null?new BigDecimal(0.00):reportPayOut.getLeaves());
			if(payrollControl.getFinancialYear().getIsWeekOffConsider() != null && payrollControl.getFinancialYear().getIsWeekOffConsider().equalsIgnoreCase("Y") ){
				payAbleDays=payAbleDays.add(reportPayOut.getWeekoff());
			}
			if(payrollControl.getFinancialYear().getIsHolidayConsider() != null && payrollControl.getFinancialYear().getIsHolidayConsider().equalsIgnoreCase("Y")) {
				payAbleDays=payAbleDays.add(reportPayOut.getPublicholidays());
			}
			reportPayOut.setPayableDays(payAbleDays);
			//* Payable days calculation change according to neelesh sir*//
			if (!isArrearCalculation) {
				arearMaster = arearService.findArearCalculationByemployeeIdAndProcessMonth(
						reportPayOut.getId().getEmployeeId(), payrollControl.getProcessMonth());
				loanIssues = loanIssueService.getLoanIssueDetails(reportPayOut.getId().getEmployeeId(),
						payrollControl.getProcessMonth());
				oneTimeDeductionList = oneTimeEarningDeductionService.findOneTimeDeductionForEmployee(
						reportPayOut.getId().getEmployeeId(), payrollControl.getProcessMonth());
				oneTimeEarningList = oneTimeEarningDeductionService.findOneTimeEarningForEmployee(
						reportPayOut.getId().getEmployeeId(), payrollControl.getProcessMonth());

				processSalaryForEmployee(epf, esi, payOuts, reportPayOut, payrollControl, loanIssues,
						oneTimeDeductionList, oneTimeEarningList, arearMaster, payStructureHdForArrear,
						isArrearCalculation);

				if (loanIssues != null && loanIssues.size() > 0)
					loanIssueService.save(loanIssues);
			} else

			processSalaryForEmployee(epf, esi, payOuts, reportPayOut, payrollControl, null, null, null, null,
						payStructureHdForArrear, isArrearCalculation);
			setEmployeePayHeadNullValue(reportPayOut);

		}

		return payOuts;
	}

	private void setEmployeePayHeadNullValue(ReportPayOut reportPayOut) {
		if (reportPayOut.getProvidentFundEmployee() == null)
			reportPayOut.setProvidentFundEmployee(new BigDecimal(0.0));
		if (reportPayOut.getProvidentFundEmployer() == null)
			reportPayOut.setProvidentFundEmployer(new BigDecimal(0.0));
		if (reportPayOut.getProvidentFundEmployerPension() == null)
			reportPayOut.setProvidentFundEmployerPension(new BigDecimal(0.0));
		if (reportPayOut.getESI_Employee() == null)
			reportPayOut.setESI_Employee(new BigDecimal(0.0));
		if (reportPayOut.getESI_Employer() == null)
			reportPayOut.setESI_Employer(new BigDecimal(0.0));
		if (reportPayOut.getPt() == null)
			reportPayOut.setPt(new BigDecimal(0.0));
		if (reportPayOut.getLwf() == null)
			reportPayOut.setLwf(new BigDecimal(0.0));
		if (reportPayOut.getEmployerWelFareAmount() == null)
			reportPayOut.setEmployerWelFareAmount(new BigDecimal(0.0));
		if (reportPayOut.getEmployerWelFareAmount()==null)
			reportPayOut.setEmployerWelFareAmount(new BigDecimal(0.0));
		if (reportPayOut.getLoan() == null)
			reportPayOut.setLoan(new BigDecimal(0.0));
		if (reportPayOut.getGrossSalary() == null)
			reportPayOut.setGrossSalary(new BigDecimal(0.0));
	}

	private void setEmployeeStatuary(ReportPayOut reportPayOut, List<EmployeeStatuary> statuaries) {

		for (EmployeeStatuary employeeStatuary : statuaries) {
			if (employeeStatuary.getStatuaryType().equals("UA")) {
				EmployeeFamily empFamily = null;
				reportPayOut.setUanno(employeeStatuary.getStatuaryNumber());
				// reportPayOut.setAadharNo( employeeStatuary.getStatuaryNumber() );
				// if(employeeStatuary.getEmployeeFamily().getFamilyId()!=null)
				if (employeeStatuary.getEmployeeFamily() != null)
					empFamily = familyService.findFamily(employeeStatuary.getEmployeeFamily().getFamilyId());
				if (empFamily != null) {
					reportPayOut.setEpfNominee(empFamily.getName());

					reportPayOut.setEpfNomineeRelation(getReleation(empFamily.getRelation()));
				}

			} else if (employeeStatuary.getStatuaryType().equals("PF")) {
				reportPayOut.setPFNumber(employeeStatuary.getStatuaryNumber());
				reportPayOut.setEpfJoining(employeeStatuary.getDateFrom());

				// employeeStatuary.getIsApplicable() is yes means no.
				reportPayOut.setIsNoPFDeduction(employeeStatuary.getIsApplicable());
				EmployeeFamily empFamily = null;
				if (employeeStatuary.getEmployeeFamily() != null)
					empFamily = familyService.findFamily(employeeStatuary.getEmployeeFamily().getFamilyId());
				if (empFamily != null) {
					reportPayOut.setEpfNominee(empFamily.getName());
					reportPayOut.setEpfNomineeRelation(getReleation(empFamily.getRelation()));
				}

			}

			else if (employeeStatuary.getStatuaryType().equals("ES")) {
				EmployeeFamily empFamily = null;
				reportPayOut.setESICNumber(employeeStatuary.getStatuaryNumber());
				reportPayOut.setEsicjoining(employeeStatuary.getDateFrom());
				// employeeStatuary.getIsApplicable() is yes means no.
				reportPayOut.setIsNoESIDeduction(employeeStatuary.getIsApplicable());
				if (employeeStatuary.getEmployeeFamily() != null
						&& employeeStatuary.getEmployeeFamily().getFamilyId() != null)
					empFamily = familyService.findFamily(employeeStatuary.getEmployeeFamily().getFamilyId());
				if (empFamily != null) {
					reportPayOut.setEsicNominee(empFamily.getName());
					reportPayOut.setEsicNomineeRelation(getReleation(empFamily.getRelation()));
				}
			}
		}

	}

	private String getReleation(String releationShip) {

		String releationName = null;
		if (releationShip.equalsIgnoreCase("FA")) {
			releationName = "Father";
		} else if (releationShip.equalsIgnoreCase("SP")) {
			releationName = "Spouse";
		} else if (releationShip.equalsIgnoreCase("MO")) {
			releationName = "Mother";
		} else if (releationShip.equalsIgnoreCase("CH")) {
			releationName = "Child";
		} else if (releationShip.equalsIgnoreCase("SI")) {
			releationName = "Sister";
		}
		return releationName;
	}

	private void setEmployeeID(ReportPayOut reportPayOut, List<EmployeeIdProof> idList) {

		for (EmployeeIdProof id : idList) {
			/*
			 * if ( id.getIdTypeId().equals("AA") ) {
			 * 
			 * reportPayOut.setAadharNo( id.getIdNumber() ); }
			 */
			if (id.getIdTypeId().equals("PA")) {
				reportPayOut.setPanno(id.getIdNumber());
			}
			/*
			 * if ( id.getIdTypeId().equals("DL") ) { reportPayOut.setESICNumber(
			 * id.getIdNumber() ); }
			 */
		}

	}

	// Note : Please Insert processSalaryForEmployee logic into this
	// processSalaryForEmployeeForTds also
	/**
	 * @param epf
	 * @param esi
	 * @param payMonth
	 * @param payOuts
	 * @param reportPayOut
	 * @param payrollControl
	 * @param loanIssues
	 * @param oneTimeDeductionList
	 * @param oneTimeEarningList
	 * @param arearMaster
	 * @throws PayRollProcessException 
	 * @ PT AND @LWF also calcuate
	 */
	private void processSalaryForEmployee(Epf epf, Esi esi, List<PayOut> payOuts, ReportPayOut reportPayOut,
			PayrollControl payrollControl, List<LoanIssue> loanIssues,
			List<OneTimeEarningDeduction> oneTimeDeductionList, List<OneTimeEarningDeduction> oneTimeEarningList,
			ArearMaster arearMaster, PayStructureHd payStructureHdForArrear, boolean isArrearCalculation) throws PayRollProcessException {

		PayRollProcessUtil util = new PayRollProcessUtil();
		BigDecimal netSalary = new BigDecimal(0);
		PayRollProcessHDDTO payRollProcessHDDTO = new PayRollProcessHDDTO();
		Employee employee= new Employee();
		if(reportPayOut.getEmployeeCode()!=null )
			employee=employeeService.findEmployees(reportPayOut.getEmployeeCode(),reportPayOut.getCompanyId());
		payRollProcessHDDTO.setPayMonth(payrollControl.getProcessMonth());
		payRollProcessHDDTO.setEpf(epf);
		payRollProcessHDDTO.setEsi(esi);
		payRollProcessHDDTO.setReportPayOut(reportPayOut);
		payRollProcessHDDTO.setEmployee(employee);
		setProfessionalTax(reportPayOut, payRollProcessHDDTO);
		setLabourWelfareFund(reportPayOut, payRollProcessHDDTO);
		List<PayRollProcessDTO> earningPayStructures = new ArrayList<PayRollProcessDTO>();
		TdsDeduction tdsDeduction = null;

		/*
		 * DateUtils dateUtils = new DateUtils(); Date currentDate =
		 * dateUtils.getCurrentDate();
		 * 
		 * FinancialYear financialYear =
		 * financialYearService.findCurrentFinancialYear(currentDate, companyId);
		 */

		// if (tdsDeduction!=null )

		logger.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());
		
		PayStructureHd payStructureHd = payStructureService
				.findPayStructureForPayroll(reportPayOut.getId().getEmployeeId(), payrollControl.getProcessMonth());
		if(payStructureHd==null) {
			throw new PayRollProcessException( "Please Check PayStructure of "+reportPayOut.getEmployeeCode());
		}
      
		
		if (payStructureHd.getEsicEndDate() != null) {
			payRollProcessHDDTO.setEsiLifeCycleEndDate(payStructureHd.getEsicEndDate());
		}
		

		if (!isArrearCalculation) {

			processEarning(payOuts, reportPayOut, util, payRollProcessHDDTO, earningPayStructures,
					payStructureHd.getPayStructures(), payrollControl);

		} else {

			processEarning(payOuts, reportPayOut, util, payRollProcessHDDTO, earningPayStructures,
					payStructureHdForArrear.getPayStructures(), payrollControl);

		}

		// arear calculation
		if (arearMaster != null) {
			payOuts.add(calculateArearEarning(arearMaster, reportPayOut, payRollProcessHDDTO.getPayMonth()));
		} else {
			reportPayOut.setArearAmount(new BigDecimal(0.0));
		}

		// One Time Earning
		if (oneTimeEarningList != null && oneTimeEarningList.size() > 0) {
			OneTimeCalculation oneTimeCalculation = new OneTimeCalculation();
			payOuts.addAll(oneTimeCalculation.calculateOneTimeEarning(oneTimeEarningList, reportPayOut,
					payRollProcessHDDTO.getPayMonth()));
		} else {
			reportPayOut.setOtherEarning(new BigDecimal(0.0));
		}

		// Calculate All Deduction
		if (earningPayStructures != null && earningPayStructures.size() > 0) {
			payRollProcessHDDTO.setTotalGrossSalary(reportPayOut.getGrossSalary());
			payOuts.addAll(calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd,
					loanIssues, oneTimeDeductionList));
		}

		netSalary = reportPayOut.getGrossSalary() != null
				? reportPayOut.getTotalEarning().subtract(reportPayOut.getTotalDeduction())
				: netSalary;
		netSalary = reportPayOut.getArearAmount() != null ? netSalary.add(reportPayOut.getArearAmount()) : netSalary;
		netSalary = reportPayOut.getOtherEarning() != null ? netSalary.add(reportPayOut.getOtherEarning()) : netSalary;
		reportPayOut.setNetPayableAmount(netSalary);
	}

	// pay stracture calulation for view only

	private void processSalaryForEmployee(Epf epf, Esi esi, List<PayOut> payOuts, ReportPayOut reportPayOut,
			PayrollControl payrollControl, PayStructureHd payStructureHd, Employee employee,Boolean flagForNewPaystructure) {

		PayRollProcessUtil util = new PayRollProcessUtil();
		BigDecimal netSalary = new BigDecimal(0);
		PayRollProcessHDDTO payRollProcessHDDTO = new PayRollProcessHDDTO();

		logger.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());

		// employeeCurrentPayStructure
		if(!flagForNewPaystructure) {
			PayStructureHd currentPayStructureHd = payStructureService
					.employeeCurrentPayStructure(reportPayOut.getId().getEmployeeId());

			if (currentPayStructureHd.getEsicEndDate() != null) {
				payRollProcessHDDTO.setEsiLifeCycleEndDate(currentPayStructureHd.getEsicEndDate());
			}

		}
	
		payRollProcessHDDTO.setPayMonth(payStructureHd.getProcessMonth());
		payRollProcessHDDTO.setEpf(epf);
		payRollProcessHDDTO.setEsi(esi);
		payRollProcessHDDTO.setReportPayOut(reportPayOut);
		payRollProcessHDDTO.setEmployee(employee);
		setProfessionalTax(reportPayOut, payRollProcessHDDTO);
		setLabourWelfareFund(reportPayOut, payRollProcessHDDTO);
		List<PayRollProcessDTO> earningPayStructures = new ArrayList<PayRollProcessDTO>();

		logger.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());

		// calculate All pay structure Based on Earning
		// processEarning(payOuts, reportPayOut, util, payRollProcessHDDTO,
		// earningPayStructures,payStructureHd.getPayStructures(), payrollControl);

		// 99999999999999999
		/*
		 * List<PayOut> payOuts, ReportPayOut reportPayOut, PayRollProcessUtil util,
		 * PayRollProcessHDDTO payRollProcessHDDTO, List<PayRollProcessDTO>
		 * earningPayStructures, List<PayStructure> payStructures, PayrollControl
		 * payrollControl) {
		 * 
		 */

		// 99999

		BigDecimal grossAmount = new BigDecimal(0);
		BigDecimal totalEarningAmount = new BigDecimal(0);
		if (payStructureHd.getPayStructures() != null && payStructureHd.getPayStructures().size() > 0) {

			for (PayStructure payStructure : payStructureHd.getPayStructures()) {

				// payStructure.setPayHead(payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId()));

				if (payStructure.getPayHead().getEarningDeduction()
						.equals(EarningDeductionEnum.Earning.getEarningDeductionType())
						&& (StatusMessage.YES_VALUE).equals(payStructure.getPayHead().getPayHeadFlag())) {

					PayRollProcessDTO payRollProcessDTO = new PayRollProcessDTO();

					// PayOut payOut = calcualteEarning(payStructure, reportPayOut, payrollControl);
					PayOut payOut = new PayOut();
					PayOutPK payOutPK = new PayOutPK();
					payOutPK.setEmployeeId(employee.getEmployeeId());
					payOutPK.setPayHeadId(payStructure.getPayHead().getPayHeadId());
					payOutPK.setProcessMonth(reportPayOut.getId().getProcessMonth());

					payOut.setAmount(payStructure.getAmount());
					payOut.setEarningDeduction(payStructure.getPayHead().getEarningDeduction());
					payOut.setId(payOutPK);
					payOut.setEmployee(employee);
					payOut.setPayHeadName(payStructure.getPayHead().getPayHeadName());

					totalEarningAmount = totalEarningAmount.add(payOut.getAmount());
					grossAmount = grossAmount.add(payStructure.getAmount());
					/*
					 * util.fillEarningValueInReportPayOut(reportPayOut,
					 * payStructure.getPayHead().getPayHeadId(), payStructure.getAmount(),
					 * payOut.getAmount());
					 */
					payOuts.add(payOut);
					payRollProcessDTO.setPayOut(payOut);
					payRollProcessDTO.setPayStructure(payStructure);
					earningPayStructures.add(payRollProcessDTO);
				}
			}
			reportPayOut.setTotalEarning(totalEarningAmount);
			reportPayOut.setGrossSalary(grossAmount);
			payRollProcessHDDTO.setListPayRollProcessDTOs(earningPayStructures);
		}

		// 99999999999999999

		// Calculate All Deduction
		if (earningPayStructures != null && earningPayStructures.size() > 0) {
			payRollProcessHDDTO.setTotalGrossSalary(reportPayOut.getGrossSalary());

			calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd, null, null);
			payOuts.addAll(
					calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd, null, null));
		}

		netSalary = reportPayOut.getGrossSalary() != null
				? reportPayOut.getTotalEarning().subtract(reportPayOut.getTotalDeduction())
				: netSalary;
		netSalary = reportPayOut.getArearAmount() != null ? netSalary.add(reportPayOut.getArearAmount()) : netSalary;
		netSalary = reportPayOut.getOtherEarning() != null ? netSalary.add(reportPayOut.getOtherEarning()) : netSalary;
		reportPayOut.setNetPayableAmount(netSalary);
	}

	// Tds calculation

	/**
	 * @param epf
	 * @param esi
	 * @param payMonth
	 * @param payOuts
	 * @param attendance
	 */
	private void processSalaryForEmployeeForTds(Epf epf, Esi esi, List<PayOut> payOuts, ReportPayOut reportPayOut,
			PayrollControl payrollControl, List<LoanIssue> loanIssues,
			List<OneTimeEarningDeduction> oneTimeDeductionList, PayStructureHd payStructureHd) {

		logger.info("processSalaryForEmployeeForTds is calling :");
		PayRollProcessUtil util = new PayRollProcessUtil();
		BigDecimal netSalary = new BigDecimal(0);
		PayRollProcessHDDTO payRollProcessHDDTO = new PayRollProcessHDDTO();

		payRollProcessHDDTO.setPayMonth(payrollControl.getProcessMonth());
		payRollProcessHDDTO.setEpf(epf);
		payRollProcessHDDTO.setEsi(esi);
		payRollProcessHDDTO.setReportPayOut(reportPayOut);
		setProfessionalTax(reportPayOut, payRollProcessHDDTO);
		List<PayRollProcessDTO> earningPayStructures = new ArrayList<PayRollProcessDTO>();

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();

		/*
		 * List<PayStructure> payStructures = attendanceRepository
		 * .fetchPayStructureByEmployee( reportPayOut.getId().getEmployeeId(),
		 * currentDate );
		 */

		logger.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());

		// PayStructureHd payStructureHd = payStructureService.findPayStructure(
		// reportPayOut.getId().getEmployeeId() );

		for (PayStructure payStructure : payStructureHd.getPayStructures()) {
			if (payStructure.getPayHead().getEarningDeduction() == null) {
				PayHead payHead = payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId());
				payStructure.setPayHead(payHead);
			}
		}

		processEarning(payOuts, reportPayOut, util, payRollProcessHDDTO, earningPayStructures,
				payStructureHd.getPayStructures(), payrollControl);

		if (earningPayStructures != null && earningPayStructures.size() > 0) {
			payRollProcessHDDTO.setTotalGrossSalary(reportPayOut.getGrossSalary());
			payOuts.addAll(calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd,
					loanIssues, oneTimeDeductionList));
		}

		if (reportPayOut.getGrossSalary() != null) {
			netSalary = reportPayOut.getTotalEarning().subtract(reportPayOut.getTotalDeduction());
		}

		reportPayOut.setNetPayableAmount(netSalary);
	}

	// Tds

	/**
	 * @param reportPayOut
	 * @param payRollProcessHDDTO
	 */
	private void setProfessionalTax(ReportPayOut reportPayOut, PayRollProcessHDDTO payRollProcessHDDTO) {
		if (reportPayOut.getStateId() != null) {

			ProfessionalTax professionalTax1 = professionalTaxService
					.findProfessionalTaxOfEmployee(reportPayOut.getStateId(), reportPayOut.getCompanyId());
		
			ProfessionalTax professionalTax = professionalTaxService
					.findProfessionalTaxOfEmployeeByProcessMonth(reportPayOut.getStateId(), reportPayOut.getCompanyId(), payRollProcessHDDTO.getPayMonth() );
		
			payRollProcessHDDTO.setProfessionalTax(professionalTax);
		}
	}
	
	private void setLabourWelfareFund(ReportPayOut reportPayOut, PayRollProcessHDDTO payRollProcessHDDTO) {
		if (reportPayOut.getStateId() != null) {
			LabourWelfareFund labourWelfareFund = labourWelfareFundService.findLabourWelfareFundEmployee(reportPayOut.getStateId(), reportPayOut.getCompanyId(),payRollProcessHDDTO.getPayMonth());
			payRollProcessHDDTO.setLabourWelfareFund(labourWelfareFund);
		}
	}
	
	
	

	/**
	 * @param payMonth
	 * @param payOuts
	 * @param reportPayOut
	 * @param util
	 * @param payRollProcessHDDTO
	 * @param grossAmountamount
	 * @param earningPayStructures
	 * @param payStructures
	 * @return
	 */
	private BigDecimal processEarning(List<PayOut> payOuts, ReportPayOut reportPayOut, PayRollProcessUtil util,
			PayRollProcessHDDTO payRollProcessHDDTO, List<PayRollProcessDTO> earningPayStructures,
			List<PayStructure> payStructures, PayrollControl payrollControl) {

		BigDecimal grossAmount = new BigDecimal(0);
		BigDecimal totalEarningAmount = new BigDecimal(0);
		BigDecimal stdEpfWagesAmount = new BigDecimal(0);
		BigDecimal earnedStdEpfWagesAmount = new BigDecimal(0);

		if (payStructures != null && payStructures.size() > 0) {
			
			for (PayStructure payStructure : payStructures) {

				payStructure.setPayHead(payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId()));

				if (payStructure.getPayHead().getEarningDeduction()
						.equals(EarningDeductionEnum.Earning.getEarningDeductionType())
						&& (StatusMessage.YES_VALUE).equals(payStructure.getPayHead().getPayHeadFlag())) {

					PayRollProcessDTO payRollProcessDTO = new PayRollProcessDTO();
					PayOut payOut = calcualteEarning(payStructure, reportPayOut, payrollControl);
					
					totalEarningAmount = totalEarningAmount.add(payOut.getAmount());
					grossAmount = grossAmount.add(payStructure.getAmount());
					util.fillEarningValueInReportPayOut(reportPayOut, payStructure.getPayHead().getPayHeadId(),
							payStructure.getAmount(), payOut.getAmount());
					payOuts.add(payOut);
					payRollProcessDTO.setPayOut(payOut);
					payRollProcessDTO.setPayStructure(payStructure);
					earningPayStructures.add(payRollProcessDTO);
				}
				
				if (payStructure.getPayHead().getEarningDeduction()
						.equals(EarningDeductionEnum.Earning.getEarningDeductionType())
						&& (StatusMessage.YES_VALUE).equals(payStructure.getPayHead().getIsApplicableOnPf())) {

				
					stdEpfWagesAmount = stdEpfWagesAmount.add(payStructure.getAmount());
					
				}
				
				
			}
		    reportPayOut.setStdEpfWagesAmount(stdEpfWagesAmount);
			reportPayOut.setTotalEarning(totalEarningAmount);
			reportPayOut.setGrossSalary(grossAmount);
			payRollProcessHDDTO.setListPayRollProcessDTOs(earningPayStructures);
		}
		return grossAmount;
	}
	
	
	


	/**
	 * 
	 * @param payStructure
	 * @param attendance
	 * @return
	 */
	private PayOut calcualteEarning(PayStructure payStructure, ReportPayOut reportPayOut,
			PayrollControl payrollControl) {
		PayOut payOut = new EarningTypeFactory(payStructure.getPayHead().getIncomeType()).getEarningType()
				.calculateEarning(payStructure, reportPayOut, payrollControl);
		return payOut;

	};

	/**
	 * 
	 * @param earningAmount
	 * @param reportPayOut
	 * @param processMonth
	 * 
	 * @return payOut
	 */

	private PayOut calculateArearEarning(ArearMaster arearMaster, ReportPayOut reportPayOut, String processMonth) {

		List<ArearCalculation> arearCalculationList = arearMaster.getArearCalculations();
		BigDecimal earningArearAmount = new BigDecimal(0);

		for (ArearCalculation arearCalculation : arearCalculationList) {
			earningArearAmount = earningArearAmount.add(arearCalculation.getNetPayableAmount());
		}
		PayOut payOut = new PayOut();
		PayOutPK pk = new PayOutPK();
		pk.setEmployeeId(reportPayOut.getId().getEmployeeId());
		pk.setProcessMonth(processMonth);
		pk.setPayHeadId(StandardEarningEnum.Arrears.getStandardEarning());
		payOut.setId(pk);
		payOut.setAmount(earningArearAmount);
		reportPayOut.setArearAmount(earningArearAmount);
		return payOut;
	}

	/**
	 * 
	 * @param tdsDeduction
	 * @param reportPayOut
	 * @param processMonth
	 * 
	 * @return payOut
	 */

	private PayOut calculatTdsDeduction(TdsDeduction tdsDeduction, ReportPayOut reportPayOut, String processMonth) {

		PayOut payOut = new PayOut();
		PayOutPK pk = new PayOutPK();
		pk.setEmployeeId(reportPayOut.getId().getEmployeeId());
		pk.setProcessMonth(processMonth);
		pk.setPayHeadId(StandardDeductionEnum.TDS.getStandardDeduction());
		payOut.setId(pk);
		payOut.setAmount(tdsDeduction.getTaxDeductedMonthly());
		reportPayOut.setTdsAmount(tdsDeduction.getTaxDeductedMonthly());
		
		return payOut;
	}

	/**
	 * 
	 * @param payRollProcessHDDTO
	 * @param reportPayOut
	 * @param payrollControl
	 * @param payStructureHd
	 * @param loanIssues
	 * @param oneTimeDeductionList
	 * 
	 * @return
	 */

	private List<PayOut> calcualteDeduction(PayRollProcessHDDTO payRollProcessHDDTO, ReportPayOut reportPayOut,
			PayrollControl payrollControl, PayStructureHd payStructureHd, List<LoanIssue> loanIssues,
			List<OneTimeEarningDeduction> oneTimeDeductionList) {

		CalculationPayHead calculationPayHead = new CalculationPayHead();

		BigDecimal totalDeductionAmount = new BigDecimal(0);

		// Calculate ESI ,EPF,PT

		List<PayOut> dedudtionPayOuts = calculationPayHead.calculationDeduction(payRollProcessHDDTO, payrollControl,
				payStructureHd);

		// loan calculation
		if (loanIssues != null && loanIssues.size() > 0) {
			LoanCalculation loanCalculation = new LoanCalculation();
			StringBuilder sb = new StringBuilder();
			List<PayOut> loanPayOuts = loanCalculation.calculateLoan(loanIssues, reportPayOut,
					payRollProcessHDDTO.getPayMonth());

			BigDecimal loanAmount = new BigDecimal(0.0);
			for (PayOut payout : loanPayOuts) {
				if (payout.getAmount() != null) {

					loanAmount = loanAmount.add(payout.getAmount());
				}
				if(sb!=null)
					sb.append(",");
				sb.append(payout.getLoanId());
				
			}
			
			//
			for (PayOut payout : loanPayOuts) {
				payout.setAmount(loanAmount);
				payout.setLoanId(sb.toString());
			
			}
			reportPayOut.setLoan(loanAmount);
           if(loanPayOuts!=null && loanPayOuts.size() > 0)
			dedudtionPayOuts.add(loanPayOuts.get(0));
		}

		// One Time Deduction
		if (oneTimeDeductionList != null && oneTimeDeductionList.size() > 0) {
			OneTimeCalculation oneTimeDeductionCalculation = new OneTimeCalculation();
			dedudtionPayOuts.addAll(oneTimeDeductionCalculation.calculateOneTimeDeduction(oneTimeDeductionList,
					reportPayOut, payRollProcessHDDTO.getPayMonth()));
		} else {
			reportPayOut.setOtherDeduction(new BigDecimal(0.0));
		}

		// TdS calculation
		TdsDeduction tdsDeduction = null;

		if (payStructureHd != null && payrollControl.getFinancialYear() != null
				&& payrollControl.getFinancialYear().getCompany() != null)

			tdsDeduction = tdsDeductionService.getTdsDeduction(
					payrollControl.getFinancialYear().getCompany().getCompanyId(),
					payrollControl.getFinancialYear().getFinancialYearId(), reportPayOut.getId().getEmployeeId());

		if (tdsDeduction != null && tdsDeduction.getTaxDeductedMonthly() != null) {
			dedudtionPayOuts.add(calculatTdsDeduction(tdsDeduction, reportPayOut, payRollProcessHDDTO.getPayMonth()));
		} else {
			reportPayOut.setTdsAmount(new BigDecimal(0.0));
		}

		for (PayOut payOut : dedudtionPayOuts) {

			if (payOut != null) {
				if (payOut.getId().getPayHeadId() != StandardDeductionEnum.PF_Employer_Contribution
						.getStandardDeduction()
						&& payOut.getId().getPayHeadId() != StandardDeductionEnum.Pension_Employer_Contribution
								.getStandardDeduction()
						&& payOut.getId().getPayHeadId() != StandardDeductionEnum.ESI_Employer_Contribution
								.getStandardDeduction() && payOut.getId().getPayHeadId() != StandardDeductionEnum.LWF_Employer
										.getStandardDeduction()) {

					logger.info(" totalDeductionAmount ====== " + totalDeductionAmount);
					logger.info(" payOut.getAmount()  ====== " + payOut.getAmount());

					if (totalDeductionAmount != null && payOut != null && payOut.getAmount() != null) {
						totalDeductionAmount = totalDeductionAmount.add(payOut.getAmount());
					}

				}
			}

		}

		reportPayOut.setTotalDeduction(totalDeductionAmount);
		return dedudtionPayOuts;
	};

	@Override
	public List<PayOut> processPayRoll(long companyId, long employeeId, PayStructureHd payStructureHdForArrear,
			boolean isArrearCalculation) throws PayRollProcessException {
		logger.info("processPayRoll employeeId is : " + employeeId);
		List<PayOut> payOuts = new ArrayList<PayOut>();
		Epf epf = epfService.getEPF(companyId);
		Esi esi = esicService.getESI(companyId);

		// PayrollControl payrollControl =
		// payrollControlService.findPayrollControlByMonth(companyId, payMonth);
		PayrollControl payrollControl = new PayrollControl();
		payrollControl.setProcessMonth("MAR-2018");
		payrollControl.setPayrollDays(31);

		Employee employee = employeeService.findEmployeesById(employeeId);

		ReportPayOut reportPayOut = new ReportPayOut();
		reportPayOut.setAbsense(new BigDecimal(0));
		reportPayOut.setPayDays(new BigDecimal(31));
		reportPayOut.setCompanyId(companyId);

		ReportPayOutPK pk = new ReportPayOutPK();
		pk.setProcessMonth("MAR-2018");
		pk.setEmployeeId(employee.getEmployeeId());
		reportPayOut.setId(pk);

		if (employee.getDepartment() != null)
			reportPayOut.setDepartmentId(employee.getDepartment().getDepartmentId());

		if (employee.getCity() != null)
			reportPayOut.setCityId(employee.getCity().getCityId());
		if (employee.getState() != null)
			reportPayOut.setStateId(employee.getState().getStateId());

		reportPayOut.setDateOfJoining(employee.getDateOfJoining());
		processSalaryForEmployee(epf, esi, payOuts, reportPayOut, payrollControl, null, null, null, null,
				payStructureHdForArrear, isArrearCalculation);

		return payOuts;
	}

	@Override
	public List<PayOut> processPayRollForTds(long companyId, long employeeId, PayStructureHd payStructureHd) {

		logger.info("processPayRollForTds employeeId is : " + employeeId);

		List<PayOut> payOuts = new ArrayList<PayOut>();
		Epf epf = epfService.getEPF(companyId);
		Esi esi = esicService.getESI(companyId);

		// PayrollControl payrollControl =
		// payrollControlService.findPayrollControlByMonth(companyId, payMonth);
		PayrollControl payrollControl = new PayrollControl();
		payrollControl.setProcessMonth("MAR-2018");
		payrollControl.setPayrollDays(31);

		Employee employee = employeeService.findEmployeesById(employeeId);

		ReportPayOut reportPayOut = new ReportPayOut();
		reportPayOut.setAbsense(new BigDecimal(0));
		reportPayOut.setPayDays(new BigDecimal(31));
		reportPayOut.setCompanyId(companyId);
		
		

		ReportPayOutPK pk = new ReportPayOutPK();
		pk.setProcessMonth("MAR-2018");
		pk.setEmployeeId(employee.getEmployeeId());
		reportPayOut.setId(pk);

		if (employee.getDepartment() != null)
			reportPayOut.setDepartmentId(employee.getDepartment().getDepartmentId());

		if (employee.getCity() != null)
			reportPayOut.setCityId(employee.getCity().getCityId());
		if (employee.getState() != null)
			reportPayOut.setStateId(employee.getState().getStateId());

		reportPayOut.setDateOfJoining(employee.getDateOfJoining());
		
		//epf on actual amount flag
	
		reportPayOut.setIsEpfOnActualAmount(epf.getIsActual());

		processSalaryForEmployeeForTds(epf, esi, payOuts, reportPayOut, payrollControl, null, null, payStructureHd);

		return payOuts;
	}

	@Override
	public List<PayOut> processPayRollForEarningDeductionView(long companyId, long employeeId,
			PayStructureHd payStructureHd,Boolean flag) throws PayRollProcessException {

		logger.info("processPayRoll employeeId is : " + employeeId);
		List<PayOut> payOuts = new ArrayList<PayOut>();

		//get epf and esi based on effective date ---pragya---
		Epf epf = epfService.getEPFByPayrollPsMonth(payStructureHd.getProcessMonth(),companyId);
		 Esi esi = esicService.getESIByPayrollPsMonth(payStructureHd.getProcessMonth(),companyId);
		//Esi esi = esicService.getActiveESI(companyId);

		// PayrollControl payrollControl =
		// payrollControlService.findPayrollControlByMonth(companyId, payMonth);
		PayrollControl payrollControl = new PayrollControl();
		payrollControl.setProcessMonth("MAR-2018");
		payrollControl.setPayrollDays(31);

		Employee employee = employeeService.findEmployeesById(employeeId);

		ReportPayOut reportPayOut = new ReportPayOut();
		reportPayOut.setAbsense(new BigDecimal(0));
		reportPayOut.setPayDays(new BigDecimal(31));
		reportPayOut.setCompanyId(companyId);

		ReportPayOutPK pk = new ReportPayOutPK();
		pk.setProcessMonth("MAR-2018");
		pk.setEmployeeId(employee.getEmployeeId());
		reportPayOut.setId(pk);

		if (employee.getDepartment() != null)
			reportPayOut.setDepartmentId(employee.getDepartment().getDepartmentId());

		if (employee.getCity() != null)
			reportPayOut.setCityId(employee.getCity().getCityId());
		if (employee.getState() != null)
			reportPayOut.setStateId(employee.getState().getStateId());

		reportPayOut.setDateOfJoining(employee.getDateOfJoining());

		List<EmployeeStatuary> empStatuaries = employeeStatuaryService
				.findAllEmployeeStatuary(reportPayOut.getId().getEmployeeId());

		if (empStatuaries != null) {
			setEmployeeStatuary(reportPayOut, empStatuaries);
		}

		processSalaryForEmployee(epf, esi, payOuts, reportPayOut, payrollControl, payStructureHd, employee,flag);
		return payOuts;
	}

	@Override
	public void employeeArrearCalculation(Long companyId, List<String> processMonthList, Long employeeId, Long userId,
			PayStructureHd payStructureHdForArrear) throws PayRollProcessException {
		boolean isArrearCalculation = true;
		List<Long> employeeIds = new ArrayList<Long>();
		employeeIds.add(employeeId);
		String firstMonth = processMonthList.get(0);
		String lastMonth = processMonthList.get(processMonthList.size() - 1);
		Date arrearFromDate = DateUtils.getDateForProcessMonth(firstMonth);
		Date arrearToDate = DateUtils.getDateForProcessMonth(lastMonth);
		ArearMaster arearMaster = new ArearMaster();
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		arearMaster.setEmployee(employee);
		arearMaster.setCompanyId(companyId);
		arearMaster.setUserId(userId);
		arearMaster.setArearFrom(arrearFromDate);
		arearMaster.setArearTo(arrearToDate);
		arearMaster.setDateCreated(new Date());
		arearMaster.setIsBooked(0l);
		List<ArearCalculation> arearCalculationListNew = new ArrayList<ArearCalculation>();
		arearMaster.setArearCalculations(arearCalculationListNew);
		for (String processMonth : processMonthList) {
			processPayRollByEmployees(companyId, processMonth, employeeIds, userId, isArrearCalculation,
					payStructureHdForArrear, arearMaster);
		}

		arearMasterRepository.save(arearMaster);
		// public void processPayRollByEmployees(Long companyId, String payMonth,
		// List<Long> employeeIds, long userId, boolean isArrearCalculation )

	}

	private ArearCalculation finalArrearCalculation(ReportPayOut reportPayOut, ArrearReportPayOut arrearReportPayOut,
			ArearMaster arearMaster) {

		ArearCalculation arearCalculation = new ArearCalculation();
		arearCalculation.setActualAmount(arrearReportPayOut.getTotalEarning().subtract(reportPayOut.getTotalEarning()));

		if ("N".equalsIgnoreCase(arrearReportPayOut.getIsNoPFDeduction())
				&& ("N".equalsIgnoreCase(reportPayOut.getIsNoPFDeduction())))
			arearCalculation.setPfDeduction(
					arrearReportPayOut.getProvidentFundEmployee().subtract(reportPayOut.getProvidentFundEmployee()));
		else
			arearCalculation.setPfDeduction(BigDecimal.ZERO);

		if ("N".equalsIgnoreCase(arrearReportPayOut.getIsNoESIDeduction())
				&& "N".equalsIgnoreCase(reportPayOut.getIsNoESIDeduction())) {
			if(arrearReportPayOut.getESI_Employee().equals(BigDecimal.ZERO))
				arearCalculation
				.setEsiDeduction(BigDecimal.ZERO);
			else
			arearCalculation
					.setEsiDeduction(arrearReportPayOut.getESI_Employee().subtract(reportPayOut.getESI_Employee()));}

		if (reportPayOut.getPt() != null && arrearReportPayOut.getPt() != null)
			arearCalculation.setPtDeduction(arrearReportPayOut.getPt().subtract(reportPayOut.getPt()));
		arearCalculation.setCompanyId(arrearReportPayOut.getCompanyId());
		arearCalculation.setPayrollMonth(arrearReportPayOut.getId().getProcessMonth());
		if (arearCalculation.getPfDeduction() != null)
			arearCalculation.setNetPayableAmount(
					arearCalculation.getActualAmount().subtract(arearCalculation.getPfDeduction()));
		if (arearCalculation.getEsiDeduction() != null)
			arearCalculation.setNetPayableAmount(
					arearCalculation.getNetPayableAmount().subtract(arearCalculation.getEsiDeduction()));
		if (arearCalculation.getPtDeduction() != null)
			arearCalculation.setNetPayableAmount(
					arearCalculation.getNetPayableAmount().subtract(arearCalculation.getPtDeduction()));
	/*	if (arearCalculation.getEsiDeduction() != null)
			arearCalculation.setActualAmount(
					arearCalculation.getNetPayableAmount().subtract(arearCalculation.getEsiDeduction()));
		if (arearCalculation.getPtDeduction() != null)
			arearCalculation.setActualAmount(
					arearCalculation.getNetPayableAmount().subtract(arearCalculation.getPtDeduction()));*/
		arearCalculation.setDateCreated(new Date());
		arearCalculation.setUserId(arrearReportPayOut.getUserId());
		arearCalculation.setArearMaster(arearMaster);
		arearCalculation.setDateCreated(new Date());
		arearCalculation.setDateUpdated(new Date());
		arearMaster.getArearCalculations().add(arearCalculation);

		return arearCalculation;
	}
	
	private void proffessionTaxArearCalculation(ReportPayOut reportPayOut, ArrearReportPayOut arrearReportPayOut) {
	 	
	}
	
	
}