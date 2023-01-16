//package com.csipl.tms.org;
//
//import java.text.ParseException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import com.csipl.tms.attendanceCalculation.adaptor.AttendanceCalculationAdaptor;
//import com.csipl.tms.attendancelog.service.AttendanceCalculationService;
//import com.csipl.tms.attendancelog.service.AttendanceLogService;
//import com.csipl.tms.attendanceregularizationrequest.service.PunchTimeDetailsService;
//import com.csipl.tms.deviceinfo.adaptor.DeviceInfoAdaptor;
//import com.csipl.tms.deviceinfo.service.DeviceInfoService;
//import com.csipl.tms.deviceinfo.service.DeviceLogsService;
//import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
//import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
//import com.csipl.hrms.dto.employee.EmployeeDTO;
//import com.csipl.hrms.model.employee.Employee;
//import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
//import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
//import com.csipl.tms.model.attendancelog.AttendanceLog;
//import com.csipl.tms.model.deviceinfo.DeviceInfo;
//import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;
//import com.csipl.tms.model.halfdayrule.HalfDayRule;
//import com.csipl.tms.rules.service.RulesService;
//
//
//
//@Component
//public class AttendanceProcessScheduler {
//private static final Logger logger = LoggerFactory.getLogger(AttendanceProcessScheduler.class);
//private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");	
//	 
//@Autowired
//AttendanceCalculationService attendanceCalculationService;
//
//@Autowired
//AttendanceLogService attendanceLogService;
//
//@Autowired
//DeviceInfoService deviceInfoService;
//
//@Autowired
//PunchTimeDetailsService punchTimeDetailsService;
//
//@Autowired
//DeviceLogsService deviceLogsService;
//
//@Autowired
//EmployeePersonalInformationService employeePersonalInformationService;
//
//@Autowired
//RulesService rulesService;
//
//EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
//
//@Autowired
//private org.springframework.core.env.Environment environment;
//
//AttendanceCalculationAdaptor attendanceCalculationAdaptor = new AttendanceCalculationAdaptor();
//
//DeviceInfoAdaptor deviceInfoAdaptor = new DeviceInfoAdaptor();
//
//	public void scheduleTaskWithFixedDelay() {
//	}
//
//	public void scheduleTaskWithInitialDelay() {
//	}
//
//	@Scheduled(cron = "0 0 23 * * ?")
//	public void scheduleTaskWithCronExpression() {
//		logger.info("AttendanceProcessScheduler final day attendance calculation ...");
//		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
//		List<String> sreialNoList = new ArrayList<String>();
//		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
//		Long companyId =1L;
//		String prefix = "COM";
//		for (DeviceInfo deviceInfo : deviceInfoList) {
//			sreialNoList.add(deviceInfo.getSerialNumber());	
//			companyId = deviceInfo.getCompanyId();
//			prefix = deviceInfo.getPrefix();
//		}
//		
////		EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
////		List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();
//		
//		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
//		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor.databaseModelToUiDtoListNativeSearch(employeeList);
//		EmployeeDTO employeeDto = new EmployeeDTO();
//		employeeDto.setEmployessList(employeeDtoList);
//		
//		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
//		Map<String, Long> mapEmpCode = new HashMap<>();
//		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
//			Long employeeId = employeeDtoObj.getEmployeeId();
//			String boimatricId = employeeDtoObj.getBiometricId();
//			String employeeCode = employeeDtoObj.getEmployeeCode();
//			mapEmpCode.put(employeeCode, employeeId);
//		}
//
//		List<Object[]> lateCommersEmployeeListWithCount = attendanceLogService.getLateCommersEmployeeListWithCount(companyId);
//		
//		List<DeviceLogsInfoDTO>  deviceLogsInfoDTO=	deviceLogsService.getAllLateComersListByDate(companyId, new Date());
//		
//		List<Object[]> objectList =attendanceCalculationService.getAttendanceFromEpush( sreialNoList);
//		List<AttendanceLogDTO> attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList,employeeDtoList,mapEmpIdAndEmpCode,prefix,companyId);
//		
//		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
//		List<AttendanceLogDTO> systemAttendanceLogList= attendanceCalculationAdaptor.objectListToUImodel(empAttendanceList,mapEmpCode);
//		List<AttendanceLogDTO> attendanceLogList= attendanceCalculationAdaptor.attendanceCalculation1(attendanceLogDtoList, systemAttendanceLogList,mapEmpIdAndEmpCode,companyId);
//		
//		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);  
//		List<AttendanceLog> attendanceLogList1 = attendanceCalculationAdaptor.uiDtoToDatabaseModelList2(attendanceLogList,mapEmpIdAndEmpCode,halfDayRule, lateCommersEmployeeListWithCount, deviceLogsInfoDTO);
//
//		attendanceLogService.savePunchTimeLog(attendanceLogList1);
//		logger.info("AttendanceProcessScheduler data saved  successfully...");
//	}
//	
//	@Scheduled(cron = "0 0 12 * * ?")
//	public void scheduleTaskWithCronExpressionForFirstAttendance() throws ParseException {
//		logger.info("late comers scheduler Id----->" );
//		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
//		List<String> sreialNoList = new ArrayList<String>();
//		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
//		Long companyId =1L;
//		String prefix = "COM";
//		for (DeviceInfo deviceInfo : deviceInfoList) {
//			sreialNoList.add(deviceInfo.getSerialNumber());	
//			companyId = deviceInfo.getCompanyId();
//			prefix = deviceInfo.getPrefix();
//		}
//		
////		EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
////		List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();
//		
//		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
//		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
//				.databaseModelToUiDtoListNativeSearch(employeeList);
//		EmployeeDTO employeeDto = new EmployeeDTO();
//		employeeDto.setEmployessList(employeeDtoList);
//		
//		
//		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
//		Map<String, Long> mapEmpCode = new HashMap<>();
//		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
//			Long employeeId = employeeDtoObj.getEmployeeId();
//			String boimatricId = employeeDtoObj.getBiometricId();
//			String employeeCode = employeeDtoObj.getEmployeeCode();
//			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
//			mapEmpCode.put(employeeCode, employeeId);
//		}
//
//		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
//		List<AttendanceLogDTO> systemAttendanceLogList= attendanceCalculationAdaptor.objectListToUImodel(empAttendanceList,mapEmpCode);
//		
//		List<Object[]> epushAttendanceobjectList =attendanceCalculationService.getFirstAttendanceLogFromEpush( sreialNoList);
//	
//		List<AttendanceLogDTO> epushAttendanceLogDtoList = attendanceCalculationAdaptor.EpushAttendanceObjListToDto(epushAttendanceobjectList,prefix,mapEmpIdAndEmpCode);
//	
//		List<AttendanceLogDTO> attendanceLogList= attendanceCalculationAdaptor.attendanceCalculationForfirstAttendanceList(epushAttendanceLogDtoList, systemAttendanceLogList,mapEmpIdAndEmpCode,companyId);
//		
//		List<DeviceLogsInfo> deviceLogsInfoList	 = 	attendanceCalculationAdaptor.attendanceLogDtotoDeviceInfoList(attendanceLogList);
//		deviceLogsService.saveAll(deviceLogsInfoList);
//	
//	
//	}
//	private EmployeeDTO getEmployeeIdByRestTamplate(String companyId) {
//		logger.info("Company Id----->" + companyId);
//		logger.info("getEmployeeIdByRestTamplate is calling : ");
//		String url = environment.getProperty("application.employeeTemp");
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders header = new HttpHeaders();
//		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		Map<String, String> params = new HashMap<>();
//		params.put("companyId", companyId);
//		return restTemplate.getForObject(url, EmployeeDTO.class, params);
//	}
//	public void scheduleTaskWithFixedRate() {
//		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
//	}
//	
//}
