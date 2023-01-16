package com.csipl.hrms.payroll.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.services.dropdown.DropDownHdService;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.payroll.PayrollControlDTO;
import com.csipl.hrms.dto.payrollprocess.HeaderReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutSalaryDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayoutSearchDTO;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.PayrollControlAdaptor;
import com.csipl.hrms.service.adaptor.ReportPayOutAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.AttendanceService;
import com.csipl.hrms.service.payroll.EpfService;
import com.csipl.hrms.service.payroll.EsicService;
import com.csipl.hrms.service.payroll.PayRollLockService;
import com.csipl.hrms.service.payroll.PayRollService;
import com.csipl.hrms.service.payroll.PayrollControlService;
import com.csipl.hrms.service.payroll.ProcessPayRollInformationService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.payroll.ReportPayoutPagingService;
import com.csipl.hrms.util.UIUtil;
import com.hrms.org.payrollprocess.dto.DepartmentProcessDTO;
import com.hrms.org.payrollprocess.dto.PayrollInputDTO;

@RequestMapping("/payrollProcess")
@RestController
public class PayRollProcessController  {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(PayRollProcessController.class);
	@Autowired
	PayRollService payRollService;
	@Autowired
	PayrollControlService payrollControlService;

	@Autowired
	private EpfService epfService;

	@Autowired
	private EsicService esicService;

	@Autowired
	private ReportPayOutService reportPayOutService;

	@Autowired
	PayRollLockService payRollLockService;

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	DropDownHdService dropDownHdService;

	@Autowired
	ReportPayoutPagingService reportPayoutPagingService;
	@Autowired
	ProcessPayRollInformationService processPayRollInformationService;
	
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	
	
	
	PayrollControlAdaptor payrollControlAdaptor = new PayrollControlAdaptor();
	ReportPayOutAdaptor reportPayOutAdaptor = new ReportPayOutAdaptor();

	/**
	 * to get List of PayrollControl objects from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payrollProcessControl/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<PayrollControlDTO> getAllPayrollControl(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		Long longCompanyId = Long.parseLong(companyId);
		List<PayrollControlDTO> listProcessMonth = new ArrayList<PayrollControlDTO>();
		List<String> payrollControlList = payrollControlService.findPayrollProcessControl(longCompanyId);
		List<String> processMonthList = UIUtil.sortProcessMonth(payrollControlList);
		for (String month : processMonthList) {
			PayrollControlDTO pc = new PayrollControlDTO();
			pc.setProcessMonth(month);
			listProcessMonth.add(pc);

		}
		return listProcessMonth;
	}

	/**
	 * to get List of PayrollControl objects from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payrollProcessControl1/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<PayrollControlDTO> getSrPayrollControlMon(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		Long longCompanyId = Long.parseLong(companyId);
		List<PayrollControlDTO> listProcessMonth = new ArrayList<PayrollControlDTO>();
		List<String> payrollControlList = payrollControlService.findPayrollProcessControl1(longCompanyId);
		List<String> processMonthList = UIUtil.sortProcessMonth(payrollControlList);
		for (String month : processMonthList) {
			PayrollControlDTO pc = new PayrollControlDTO();
			pc.setProcessMonth(month);
			listProcessMonth.add(pc);

		}
		return listProcessMonth;
	}

	/**
	 * to get List of PayrollControl objects from database based on companyId and
	 * processMonth must be open
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payrollOpenProMon/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<PayrollControlDTO> getPayrollOpenProMon(@PathVariable("companyId") String companyId,HttpServletRequest req)
			throws PayRollProcessException {
		Long longCompanyId = Long.parseLong(companyId);
		List<PayrollControlDTO> listProcessMonth = new ArrayList<PayrollControlDTO>();
		List<String> payrollControlList = payrollControlService.getPayrollOpenProMon(longCompanyId);
		List<String> processMonthList = UIUtil.sortProcessMonth(payrollControlList);
		for (String month : processMonthList) {
			PayrollControlDTO pc = new PayrollControlDTO();
			pc.setProcessMonth(month);
			listProcessMonth.add(pc);

		}
		return listProcessMonth;
	}

	
	/**
	 * Method performed save operation
	 * 
	 * @param payrollControlDto
	 *            This is the first parameter for getting PayrollControl object from
	 *            UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	/*@RequestMapping(method = RequestMethod.POST)
	public void payrollProcess(@RequestBody PayrollControlDTO payrollControlDto, HttpServletRequest req)
			throws PayRollProcessException {

		
		 * HttpSession session = req.getSession(); User user = (User)
		 * session.getAttribute("User");
		 
		Long longCompanyId = payrollControlDto.getCompanyId();
		Long userId = payrollControlDto.getUserId();
		String processMonth = payrollControlDto.getProcessMonth();

		if (payrollControlDto.getDepartmentId() != null && payrollControlDto.getDepartmentId() == 0) {

			payRollService.processPayRoll(longCompanyId, processMonth, userId);

		} else if (payrollControlDto.getDepartmentId() != null) {
			Epf epf = epfService.getEPF(longCompanyId);
			Esi esi = esicService.getESI(longCompanyId);
			PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(longCompanyId,
					processMonth);
			DrowpdownHd bankList = dropDownHdService.findDropDownById(DropDownEnum.BankName.drowpdownId);
			payRollService.processPayRollByDepartment(longCompanyId, payrollControlDto.getDepartmentId(), epf, esi,
					userId, payrollControl, bankList);

		}
	}

*/	
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ErrorHandling payrollProcess(@RequestBody PayrollInputDTO payrollInputDTO, HttpServletRequest req)
			throws PayRollProcessException {
		ErrorHandling error = new ErrorHandling();
		StringBuilder builder = new StringBuilder();
		boolean isArrearCalculation=false;
		Long longCompanyId = payrollInputDTO.getCompanyId();
		Long userId = payrollInputDTO.getUserId();
		String processMonth = payrollInputDTO.getProcessMonth();
		
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new PayRollProcessException(processMonth +"  is locked, you can not process payroll");
		}
		
		PayrollInputDTO dto= payRollService.isPendingRequestLeaveAndARByMonth(DateUtils.getMonthForProcessMonth(payrollInputDTO.getProcessMonth()));
		for(Long employeeId:payrollInputDTO.getEmployeeIds()) {
			if(dto.getMap().containsKey(employeeId)) {
				builder.append("Payroll cannot be processed if Leave or AR request is pending of  " +dto.getMap().get(employeeId));
				throw new PayRollProcessException( builder.toString());
			}
			
		} 
		
		
		
		
		if(payrollInputDTO.getDeptEmpFlag()!=null && payrollInputDTO.getDeptEmpFlag().equals("Per") )
		{
			
			payRollService.processPayRollByEmployees(longCompanyId,processMonth,payrollInputDTO.getEmployeeIds() , userId ,isArrearCalculation,null,null);
		}
		else 
		{
	    	payRollService.processPayRollByDepartments( longCompanyId, processMonth,payrollInputDTO.getDepartmentIds() ,userId,null  );
		
		}
		return error;
	}
	
	
	
	
	
	/**
	 * Method performed save operation
	 * 
	 * @param payrollControlDto
	 *            This is the first parameter for getting PayrollControl object from
	 *            UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(path = "/status", method = RequestMethod.POST)
	public @ResponseBody ErrorHandling validatePayrollProcess(@RequestBody PayrollControlDTO payrollControlDto,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("validatePayrollProcess is calling");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("User");
		Long companyId = user.getCompany().getCompanyId();
		String processMonth = payrollControlDto.getProcessMonth();
		ErrorHandling error = new ErrorHandling();

		if (payrollControlDto.getDepartmentId() != null && payrollControlDto.getDepartmentId() == 0) {
			int count = payRollLockService.isSalaryProcessStartedForCompany(companyId, processMonth);

//			if (count == 0) {
//				List<String> departments = attendanceService.findDepartmentForProcessing(companyId, processMonth);
//				// int size = departments.size();
//				String departmentsName = "";
//				for (String departmentName : departments) {
//					departmentsName = departmentsName + departmentName + ",";
//				}
//
//				error.setMessage("PayRoll Process is started for department " + departmentsName
//						+ ", Please click preview button for report");
//			} else {
//				throw new PayRollProcessException("Payroll has been created already");
//			}

		} else if (payrollControlDto.getDepartmentId() != null) {
			int count = payRollLockService.isSalaryProcessStartedForDepartment(payrollControlDto.getDepartmentId(),
					processMonth);
			if (count == 0) {
				error.setErrorMessage("success");
			} else {
				throw new PayRollProcessException("Payroll has been created already");
			}
		}
		return error;
	}

	/**
	 * to get List of HeaderReportPayOut objects from database based on departmentId
	 * OR else companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payOutReport/{companyId}/{deptId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<HeaderReportPayOutDTO> getPayOutReportOfDepartment(
			@PathVariable("companyId") String companyId, @PathVariable("deptId") String deptId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req) throws PayRollProcessException {
		logger.info("getPayOutReportOfDepartment for deptId :" + deptId + " and processMonth  " + processMonth);

		List<Object[]> objectListOfReport = null;
		Long longCompanyId = Long.parseLong(companyId);
		if (deptId != null && !deptId.equalsIgnoreCase("null") && !deptId.equalsIgnoreCase("0")) {
			Long departmentId = Long.parseLong(deptId);
			objectListOfReport = reportPayOutService.findPayOutReportOfDepartment(departmentId, processMonth);
		} else {
			objectListOfReport = reportPayOutService.findPayOutReportOfCompany(longCompanyId, processMonth);
		}

		List<HeaderReportPayOutDTO> reportPayOutDtoList = reportPayOutAdaptor
				.objectListToReportPayOutDTOList(objectListOfReport, processMonth);

		return reportPayOutDtoList;
	}

	/**
	 * to get List of ReportPayOut objects from database based on departmentId And
	 * processMonth
	 */
	@RequestMapping(path = "/employeesPayOutReport", method = RequestMethod.GET)
	public @ResponseBody List<ReportPayOutDTO> employeesPayOutReport(@RequestParam("deptId") String deptId,
			@RequestParam("processMonth") String processMonth, HttpServletRequest req) {
		logger.info("employeesPayOutReport for deptId :" + deptId + " and processMonth  " + processMonth);

		Long departmentId = Long.parseLong(deptId);
		List<ReportPayOut> reportPayOutList = reportPayOutService.findEmployeesPayOutReport(departmentId, processMonth);
		return reportPayOutAdaptor.databaseModelToUiDtoList(reportPayOutList);
	}

	/**
	 * to get List of ReportPayOut objects from database based on processMonth And
	 * companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/allEmpPayOutReport/{processMonth}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<ReportPayOutDTO> allEmpPayOutReport(@PathVariable("processMonth") String processMonth,@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("allEmpPayOutReport for  processMonth  " + processMonth);
		Long companyID = Long.parseLong(companyId);

		List<ReportPayOut> reportPayOutList = reportPayOutService.findAllEmployeesPayOutReport(companyID,
				processMonth);
		return reportPayOutAdaptor.databaseModelToUiDtoList(reportPayOutList);
	}

	/**
	 * to get List of ReportPayOutSalary objects from database based on departmentId
	 * And processMonth
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/employeesPayOutSalaryReport/{deptId}/{processMonth}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<ReportPayOutSalaryDTO> employeesPayOutSalaryReport(@PathVariable("deptId") String deptId,
			@PathVariable("processMonth") String processMonth, @PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("employeesPayOutSalaryReport for deptId :" + deptId + " and processMonth  " + processMonth);

		List<Object[]> reportPayOutListObj = null;

		if (deptId != null && !deptId.equalsIgnoreCase("null") && !deptId.equalsIgnoreCase("0")) {
			Long departmentId = Long.parseLong(deptId);
			reportPayOutListObj = reportPayOutService.findEmployeesPayOutReportForSalaryProcess(departmentId,
					processMonth);
		} else {
			reportPayOutListObj = reportPayOutService.findAllEmployeesPayOutReportForSalaryProcess(companyId,
					processMonth);
		}
		if (reportPayOutListObj != null && reportPayOutListObj.size() > 0) {
			return reportPayOutAdaptor.databaseModelToUiSalaryDtoList(reportPayOutListObj);
		}
		throw new ErrorHandling("No Employee available to generate salary slip for this Process Month AND Department");

	}

	/**
	 * to check is payroll process or not for employee
	 */
	@RequestMapping(path = "/checkEmpPayroll/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody Boolean checkEmployeePayroll(@PathVariable("employeeId") String employeeId,
			HttpServletRequest req) {
		logger.info("checkEmployeePayroll for employeeId :" + employeeId);
		Long empId = Long.parseLong(employeeId);
		boolean flag = true;
		Long count = reportPayOutService.checkEmployeePayroll(empId);
		logger.info("return count for checkEmployeePayroll " + count);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * to check is payroll process or not for employee
	 * 
	 * @throws PayRollProcessException
	 *//*
	@RequestMapping(path = "/checkEmpPayrollForSalarySlip", method = RequestMethod.GET)
	public @ResponseBody Boolean checkEmployeePayrollForSalarySlip(@RequestParam("empCode") String empCode,
			@RequestParam("processMonth") String processMonth,@RequestParam("companyId") String companyId, HttpServletRequest req) throws PayRollProcessException {
		logger.info("checkEmployeePayrollForSalarySlip for empCode :" + empCode + "and processMonth" + processMonth);
		boolean flag = false;
		Long companyID = Long.parseLong(companyId);
		Long salarySlipCheckCount = reportPayOutService.checkEmployeePayrollForSalarySlip(empCode, processMonth,
				companyID);
		logger.info("return salarySlipCheckCount for checkEmployeePayrollForSalarySlip " + salarySlipCheckCount);
		if (salarySlipCheckCount > 0) {
			flag = true;
		}
		return flag;
	}
*/
	@GetMapping(path = "/companypayoutreportcount/{companyId}/{processMonth}")
	public @ResponseBody EmployeeCountDTO getCompanyPayoutReportCount(@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req) throws PayRollProcessException {
		Long longCompanyId = Long.parseLong(companyId);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		reportPayoutPagingService.getCompanyPayoutReportCount(longCompanyId, processMonth, employeeCountDto);
		int count = employeeCountDto.getCount();
		new EmployeeCountDTO().setCount(count);
		System.out.println("CompanyPayoutReportCount(--------" + employeeCountDto.getCount());

		return employeeCountDto;
	}

	@GetMapping(path = "/departmentpayoutreportcount/{companyId}/{departmentId}/{processMonth}")
	public @ResponseBody EmployeeCountDTO getDepartmentPayOutReportCount(@PathVariable("companyId") String companyId,
			@PathVariable("departmentId") String departmentId, @PathVariable("processMonth") String processMonth,
			HttpServletRequest req) throws PayRollProcessException {
		Long longCompanyId = Long.parseLong(companyId);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		reportPayoutPagingService.getDepartmentPayoutReportCount(longCompanyId, processMonth, departmentId,
				employeeCountDto);
		int count = employeeCountDto.getCount();
		new EmployeeCountDTO().setCount(count);
		System.out.println("DepartmentPayoutReportCount(--------" + employeeCountDto.getCount());
		return employeeCountDto;
	}

	@RequestMapping(path = "/employeesPayOutReport", method = RequestMethod.POST)
	public @ResponseBody List<ReportPayOutDTO> getPayOutReportByPaging(
			@RequestBody ReportPayoutSearchDTO reportPayoutSearchDto, HttpServletRequest req)
			throws PayRollProcessException {
		Long longCompanyId = reportPayoutSearchDto.getCompanyId();
		logger.info(" active employees is calling :");

		List<Object[]> reportPayOutList = reportPayoutPagingService.getReportPayOutByPaging(longCompanyId,
				reportPayoutSearchDto);
		System.out.println("reportPayOutList -------" + reportPayOutList);

		return reportPayOutAdaptor.modeltoDTOList(reportPayOutList, reportPayoutSearchDto);

	}

	/**
	 * to get List of PayrollControl objects from database based on companyId and
	 * financilYearId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payrollProcessMonth/{financialYearId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<PayrollControlDTO> getPayrollControlMon(
			@PathVariable("financialYearId") String financialYearId,@PathVariable("companyId") String companyId, HttpServletRequest req)
			throws PayRollProcessException {
		
		List<PayrollControlDTO> listProcessMonth = new ArrayList<PayrollControlDTO>();
		Long financialID = Long.parseLong(financialYearId);
		Long longCompanyId = Long.parseLong(companyId);
		List<String> payrollControlList = payrollControlService.findPayrollProcessControlById(longCompanyId,
				financialID);
		List<String> processMonthList = UIUtil.sortProcessMonth(payrollControlList);
		for (String month : processMonthList) {
			PayrollControlDTO pc = new PayrollControlDTO();
			pc.setProcessMonth(month);
			listProcessMonth.add(pc);

		}

		return listProcessMonth;
	}
	
	@RequestMapping(value="/findByCompanyId/{companyId}",method = RequestMethod.GET)
	public List<PayrollControlDTO> payrollControlList(@PathVariable("companyId") Long companyId) {
		logger.info("payrollControlList is calling : " + " : companyId " + companyId);
		//Long companyID = Long.parseLong(companyId);
		List<PayrollControl> PayrollControlList = payrollControlService.findPayrollControl(companyId);
		List<PayrollControlDTO> PayrollControlDTOList = payrollControlAdaptor.databaseModelToUiDtoList(PayrollControlList);
		logger.info("PayrollControlList is end  :" + "PayrollControlDTOList" + PayrollControlDTOList);
		return PayrollControlDTOList;
	}
	
	@RequestMapping(value="findByCompanyId/{companyId}/{employeeId}",method = RequestMethod.GET)
	public List<PayrollControlDTO> payrollControlBasedOnPayStructure(@PathVariable("companyId") Long companyId,@PathVariable("employeeId") Long employeeId) {
		List<PayrollControlDTO> payrollControlDTOListObj=new ArrayList<PayrollControlDTO>();
		
 		List<PayrollControl> PayrollControlList = payrollControlService.findPayrollControl(companyId);
 		List<PayrollControlDTO> payrollControlDTOList = payrollControlAdaptor.databaseModelToUiDtoList(PayrollControlList);
		Employee employee=employeePersonalInformationService.getEmployeeInfo(employeeId);
		 Date doj=getFirstDateOfMonth(employee.getDateOfJoining());

		/* for (PayrollControlDTO payrollControlDto : payrollControlDTOList) {
			 System.out.println("doj>>>"+doj+" payrollControlDto>>"+DateUtils.getDateForProcessMonth(payrollControlDto.getProcessMonth()));
			if(doj.before(DateUtils.getDateForProcessMonth(payrollControlDto.getProcessMonth())) ||doj.equals(DateUtils.getDateForProcessMonth(payrollControlDto.getProcessMonth()))) {
				 System.out.println("+++++++++ "+doj+"  |||||||||||||"+DateUtils.getDateForProcessMonth(payrollControlDto.getProcessMonth()));
  				payrollControlDTOListObj.add(payrollControlDto);
			}
 		}*/
 		
 		return payrollControlDTOList;
	}
	   public static Date getFirstDateOfMonth(Date date){
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        return cal.getTime();
	    }
	   
	   

		/**
		 * to get List of PayrollControl objects from database based on companyId and
		 * processMonth must be open
		 * 
		 * @throws PayRollProcessException
		 */
		@RequestMapping(path = "/ProcessMonthForPyrollRecord/{companyId}", method = RequestMethod.GET)
		public @ResponseBody List<PayrollControlDTO> getPayrollMonthFromReportPayout(@PathVariable("companyId") String companyId,HttpServletRequest req)
				throws PayRollProcessException {
			Long longCompanyId = Long.parseLong(companyId);
			//List<PayrollControlDTO> listProcessMonth = new ArrayList<PayrollControlDTO>();
			List<PayrollControl> payrollControlList = payrollControlService.findPayrollMonthFromReportPayout(longCompanyId);
			List<PayrollControlDTO> payrollControlDTOList = payrollControlAdaptor.databaseModelToUiDtoList(payrollControlList);
			//			List<String> processMonthList = UIUtil.sortProcessMonth(payrollControlList);
//			for (String month : processMonthList) {
//				PayrollControlDTO pc = new PayrollControlDTO();
//				pc.setProcessMonth(month);
//				listProcessMonth.add(pc);
//
//			}
			return payrollControlDTOList;
		}
		
		
		@GetMapping(value = "/validateDept/{departmentId}/{processMonth}/{companyId}")
		public List<DepartmentProcessDTO> validateDeptProcess(@PathVariable("departmentId") Long[] departmentId,
				@PathVariable("processMonth") String processMonth, @PathVariable("companyId") Long companyId,
				HttpServletRequest req) throws Exception {
			logger.info("PayRollProcessController.validateDeptProcess()");
			List<DepartmentProcessDTO> deptList = processPayRollInformationService.processPayRollinformation(departmentId,
					processMonth, companyId);
			return deptList;
		}
		
		
		@GetMapping(value = "/payRollOverview/{processMonth}/{companyId}")
		public  DepartmentProcessDTO  getPayRollOverview(@PathVariable("processMonth") String processMonth,
				@PathVariable("companyId") Long companyId, HttpServletRequest req) throws Exception {
			Date toDate = DateUtils.getPayMonth(processMonth);
			Date from = getFirstDateOfMonth(toDate);
			 DepartmentProcessDTO  deptList = processPayRollInformationService.getPayRollOverview(processMonth,companyId , from, toDate);
			return deptList;
		}
}
