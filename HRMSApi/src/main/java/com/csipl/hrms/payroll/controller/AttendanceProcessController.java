package com.csipl.hrms.payroll.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.AttendanceProcesDTO;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.adaptor.AttendanceProcessAdaptor;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.AttendanceProcessService;
import com.csipl.hrms.service.payroll.PayrollControlService;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(description = "Operations pertaining to attendance Process")
@RequestMapping("/processAttendance")
public class AttendanceProcessController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AttendanceProcessController.class);
	@Autowired
	AttendanceProcessService attendanceProcessService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	PayrollControlService payrollControlService;
	@Autowired
	AttendanceLogService attendanceLogService;
	
	@Autowired
	private org.springframework.core.env.Environment environment;

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	
	AttendanceProcessAdaptor attendanceProcessAdaptor = new AttendanceProcessAdaptor();
	
	/**
	 * @param employeeId
	 *            to get all leaveEntries object from database based on employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	@ApiOperation(value = "View  Approved or rejected LeaveEntry List of Employee based on employee Id")
	@RequestMapping(value = "/employeeList/{departmentId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getEmployeeforAttendanceProcess(
			@PathVariable("departmentId") Long departmentId,@PathVariable("processMonth") String processMonth) {
		logger.info("getEmployeeforAttendanceProcess is calling : departmentId=>>" + departmentId);
		List<Object[]> employeeList = attendanceProcessService.findAllEmployeeForAttendanceProcess(departmentId, processMonth);
		List<EmployeeDTO> employeeDtoList=employeePersonalInformationAdaptor.databaseObjModelToUiDtoList(employeeList);
		return employeeDtoList;
	}
	
	

	/**
	 * @param leaveEntryDto
	 *            to get actual leave applied days based on leave apply from date
	 *            and leave apply to date
	 * @throws ErrorHandling 
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully process data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "process employee list ")
	@RequestMapping(value = "/processEmployeeList", method = RequestMethod.POST)
	public List<Attendance> appliedLeaveDays(@RequestBody AttendanceProcesDTO attendanceProcesDto) throws ParseException, ErrorHandling {
		logger.info("Process Attendance  is calling : attendanceProcesDto " + attendanceProcesDto.toString());
		
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(attendanceProcesDto.getProcessMonth());
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new ErrorHandling(attendanceProcesDto.getProcessMonth() +"  is locked, you can not process Attendance");
		}
	List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		for(Long employeeId : attendanceProcesDto.getEmployeeIdList()) {
		//AttendanceLogDTO attendanceLogDto1 = getAttendanceByRestTamplate(employeeId,attendanceProcesDto.getProcessMonth(),attendanceProcesDto.getCompanyId());
		AttendanceLogDTO attendanceLogDto =attendanceLogService.calculateAttendanceForPayroll(employeeId,attendanceProcesDto.getProcessMonth(),
				attendanceProcesDto.getCompanyId());

		System.out.println(attendanceLogDto);
		attendanceLogDtoList.add(attendanceLogDto);
		}
		List<Attendance> attendances = attendanceProcessAdaptor.AttendanceDtoToAttendance(attendanceLogDtoList);
		attendanceProcessService.save(attendances);
		return attendances;
	}
	
	
	/*private AttendanceLogDTO getAttendanceByRestTamplate(Long employeeId,String processMonth,Long companyId) {
		logger.info("employeeId Id----->" + employeeId);
		logger.info("getAttendanceByRestTamplate is calling : ");
		String url = environment.getProperty("application.employeeAttendanceByMonth");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, String> params = new HashMap<>();
		params.put("employeeId", employeeId.toString());
		params.put("processMonth", processMonth);
		params.put("companyId", companyId.toString());
		return restTemplate.getForObject(url, AttendanceLogDTO.class, params);
	}*/

	@RequestMapping(value = "/attendanceRollback/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getEmployeeforAttendanceRollback( @PathVariable("companyId") Long companyId,@PathVariable("processMonth") String processMonth) {
		logger.info("getEmployeeforAttendanceProcess is calling : departmentId=>>" + companyId);
		List<Object[]> employeeList = attendanceProcessService.getEmployeeforAttendanceRollback(companyId, processMonth);
		List<EmployeeDTO> employeeDtoList=employeePersonalInformationAdaptor.databaseObjModelToUiRollbackDtoList(employeeList);
		return employeeDtoList;
	}	
	
	@RequestMapping(value = "/attendanceRollback", method = RequestMethod.POST)
	public int attendanceRollback(@RequestParam("processMonth") String processMonth, @RequestBody  List<EmployeeDTO> employeeDTO) {
		int updateCount = attendanceProcessService.attendanceRollback(employeeDTO, processMonth);
		return updateCount;
	}	
	
}
