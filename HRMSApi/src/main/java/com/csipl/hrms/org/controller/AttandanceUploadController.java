package com.csipl.hrms.org.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.hrms.service.payroll.AttendanceService;
import com.csipl.hrms.service.payroll.PayrollControlService;
import com.csipl.hrms.service.payroll.ReportPayoutPagingService;
import com.csipl.hrms.service.payroll.repository.AttendanceRepository;

@RestController
public class AttandanceUploadController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AttandanceUploadController.class);

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	PayrollControlService payrollControlService;

	@Autowired
	ReportPayoutPagingService reportPayoutPagingService;
	
	@Autowired
	AttendanceRepository attendanceRepository;
	
	
	/**
	 * Method performed attendance upload logic based on user uploaded file
	 * 
	 * @param file
	 *            This is the first parameter for taking file Input
	 * @param attendance
	 *            This is the second parameter for getting attendance Object from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 * @throws Exception 
	 */
	@RequestMapping(value = "/attendanceUpload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public @ResponseBody ErrorHandling attendanceUpload(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") AttendanceDTO attendance, HttpServletRequest req)
			throws PayRollProcessException {
		logger.info("attendanceUpload is calling : " + " : AttendanceDTO " + attendance + "uploadFile" + file);
		ErrorHandling error = new ErrorHandling();
		
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(attendance.getProcessMonth());
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new PayRollProcessException(attendance.getProcessMonth() +"  is locked, you can not process Attendance");
		}
		
		List<Object> employeeCode;
		Long companyId = attendance.getCompanyId();
		Long userId = attendance.getUserId();
		AppUtils util = new AppUtils();
		Map<String, Attendance> attendancesMap = new HashMap<String, Attendance>();
		PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(companyId,
				attendance.getProcessMonth());

		int payRollDays = payrollControl.getPayrollDays();
		StringBuffer sb = new StringBuffer();
		List<String> listCsv = util.csvReaderFile(AppUtils.createCsvFile(file), attendancesMap,
				attendance.getProcessMonth(), userId, companyId, payRollDays, sb);
//		List<String> listCsv = util.csvReaderFile(AppUtils.createCsvFile(file), attendancesMap,
//				attendance.getProcessMonth(), userId, companyId, 0, payRollDays, sb);
		
		attendanceService.checkFormerEmployees(listCsv, companyId);
		
		String employeeCodeSb = sb.substring(0, sb.length() - 1).toString();

		System.out.println("Employee Code Str--------------->" + employeeCodeSb);
        System.out.println("Listof csv------------------>"+listCsv);
        System.out.println("attendance.getProcessMonth()------------------>"+attendance.getProcessMonth());
		/*employeeCode = reportPayoutPagingService.payrollValidationByEmployeeCode(companyId,
				attendance.getProcessMonth(), employeeCodeSb);

		if (employeeCode != null)
			throw new PayRollProcessException("Payroll doesn't configure for these employee code " + employeeCode);*/

//		if (payrollControl == null)
//			throw new PayRollProcessException(
//					" System doesn't configure payroll days of month " + attendance.getProcessMonth());

		//validateAttendance(attendance);
	//	List<EmployeeDTO> empDB = new ArrayList<EmployeeDTO>();
//		if (attendance.getDepartmentId() > 0) {
//			empDB = attendanceService.fetchEmployeeForValidation(companyId, attendance.getDepartmentId(),
//					attendance.getProcessMonth());
//		} else {
//			empDB = attendanceService.fetchEmployeeForValidation(companyId, attendance.getProcessMonth());
//
//		}

		// AppUtils util = new AppUtils();

		/*
		 * PayrollControl payrollControl =
		 * payrollControlService.findPayrollControlByMonth(companyId,
		 * attendance.getProcessMonth()); if (payrollControl == null) throw new
		 * PayRollProcessException( " System doesn't configure payroll days of month " +
		 * attendance.getProcessMonth());
		 * 
		 * int payRollDays = payrollControl.getPayrollDays();
		 */
		// List<Attendance> attendances = new ArrayList<Attendance>();
		// Map<String, Attendance> attendancesMap = new HashMap<String, Attendance>();

		/*
		 * StringBuffer sb = new StringBuffer(); List<String> listCsv =
		 * util.csvReaderFile(AppUtils.createCsvFile(file), attendancesMap,
		 * attendance.getProcessMonth(), userId, companyId,
		 * attendance.getDepartmentId(), payRollDays);
		 */
        List<String> empDB = attendanceRepository.empCodeAttendenceList(companyId, attendance.getProcessMonth());
        
		List<String> empCodes = new ArrayList<>();
//		empDB.forEach(employeeDTO -> {
//			//empCodes.add(employeeDTO);
//			System.out.println("empDB----------------->"+empCodes);
//		});
       System.out.println("empDB----------------->"+empDB.toString());
       System.out.println("Listof csv------------------>"+listCsv);
       
         empDB.retainAll(listCsv);
			
	//	listCsv.removeAll(empDB);
         System.out.println("empDB11----------------->"+empDB);
         System.out.println("Listof csv111------------------>"+listCsv);
		
		
//		Collection<String> similar = new HashSet<String>(listCsv);
//		Collection<String> different = new HashSet<String>();
//
//		different.addAll(empCodes);
//		different.addAll(listCsv);
//
//	    similar.retainAll(empCodes);
//		different.removeAll(similar);

		// System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listCsv,
		// empCodes, similar, different);

		if (empDB.size() > 0) {
			StringBuilder builder = new StringBuilder();
//			builder.append("System can't upload  file due to mismatch of employ code : \n ");
			builder.append("Attendance already exist " + empDB);
			throw new PayRollProcessException(builder.toString());
		}

		List<Attendance> attendances = new ArrayList<Attendance>();
		int i=0;
		for(Map.Entry<String, Attendance> entry : attendancesMap.entrySet()) {
		    String key = entry.getKey();
		    Attendance attendanceValue = entry.getValue();
//		    Department d= new Department();
//		    d.setDepartmentId(attendance.getDepartmentId());
//		    attendanceValue.setDepartment(d);
		    Company c = new Company();
		    c.setCompanyId(companyId);
		    attendanceValue.setCompany(c);
		    attendances.add(attendanceValue);
		    i++;
		    // do what you have to do here
		    // In your case, another loop.
		}
		StringBuilder builder = new StringBuilder();
		/*	String finalMessage = null;
		for (EmployeeDTO empDTO : empDB) {
			boolean errorFlag = false;
			String message = null;
			String empCode = empDTO.getEmployeeCode();
			Attendance attendanceValue = attendancesMap.get(empCode);
			Department depart = new Department();
			depart.setDepartmentId(empDTO.getDepartmentId());
		//	attendanceValue.setDepartment(depart);

//			if (empDTO.getAadharNumber() == null) {
//				errorFlag = true;
//				message = message + " Aadhar card, ";
//			}
//
//			if (empDTO.getBankId() == null) {
//				errorFlag = true;
//				message = message + " Bank Details, ";
//			}
//
//			if (empDTO.getPayStructureHdId() == null) {
//				errorFlag = true;
//				message = message + " Pay Structure,  ";
//			}
//
//			if (errorFlag) {
//				finalMessage = message.substring(0, message.lastIndexOf(","));
//				builder.append(empCode + finalMessage + " is missing ");
//			}
			attendances.add(attendanceValue);
		}*/

		if (builder.length() > 0) {
			throw new PayRollProcessException(builder.toString());
		} else {
			attendanceService.upload(attendances);
		}
		error.setMessage("Attendance uploaded successfully");
		return error;
	}

	/**
	 * to validate attendance data before upload into database
	 */
//	private void validateAttendance(AttendanceDTO attendance) throws PayRollProcessException {
//
//		Long companyId = attendance.getCompanyId();
//		System.out.println(
//				"----------------->CompanyId : " + companyId + " departmentId : " + attendance.getDepartmentId());
//		List<String> deptList = attendanceService.validateAttendanceBeforeUpload(companyId,
//				attendance.getDepartmentId(), attendance.getProcessMonth());
//		if (deptList != null && deptList.size() > 0) {
//			if (deptList.size() == 1)
//				throw new PayRollProcessException(deptList + " Department attendance already exist in the System .");
//			else
//				throw new PayRollProcessException(deptList + " Departments attendance already exist in the System .");
//
//		}
//
//	}

}
