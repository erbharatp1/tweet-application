package com.csipl.tms.attendanceCalculation.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.csipl.tms.attendanceCalculation.adaptor.AttendanceCalculationAdaptor;
import com.csipl.tms.attendancelog.service.AttendanceCalculationService;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.attendanceregularizationrequest.service.PunchTimeDetailsService;
import com.csipl.tms.deviceinfo.adaptor.DeviceInfoAdaptor;
import com.csipl.tms.deviceinfo.service.DeviceInfoService;
import com.csipl.tms.deviceinfo.service.DeviceLogsService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.deviceinfo.DeviceInfo;
import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.rules.service.RulesService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AttendanceCalculationController {

	@Autowired
	AttendanceCalculationService attendanceCalculationService;

	@Autowired
	AttendanceLogService attendanceLogService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	DeviceInfoService deviceInfoService;

	@Autowired
	PunchTimeDetailsService punchTimeDetailsService;

	/*
	 * @Autowired EmployeeInfoService employeeInfoService;
	 */

	@Autowired
	DeviceLogsService deviceLogsService;

	@Autowired
	RulesService rulesService;

	@Autowired
	private org.springframework.core.env.Environment environment;

	AttendanceCalculationAdaptor attendanceCalculationAdaptor = new AttendanceCalculationAdaptor();

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

	DeviceInfoAdaptor deviceInfoAdaptor = new DeviceInfoAdaptor();

	private static final Logger logger = LoggerFactory.getLogger(AttendanceCalculationController.class);

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully save PunchTimeDetails data from ESSL mashine"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update the resource"),
			@ApiResponse(code = 403, message = "You were trying to save resource is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	/*
	 * @RequestMapping(value = "/punchtimedetailss", method = RequestMethod.GET)
	 * public @ResponseBody void getAllPunchDetails() throws ParseException {
	 * 
	 * List<DeviceInfo> deviceInfo = deviceInfoService.findDeviceInfo(1L);
	 * 
	 * Object[] object = deviceInfoAdaptor.getDeviceInfo(deviceInfo); String prefix
	 * = (String) object[0]; Long companyId = (Long) object[1];
	 * 
	 * if ( companyId == null ) companyId = 1l;
	 * 
	 * EmployeeDTO employeeDto=getEmployeeIdByRestTamplate( companyId.toString() );
	 * 
	 * List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();
	 * logger.info("employeeDtoList Size------->"+employeeDtoList.size());
	 * 
	 * Map<String, Long> mapEmpIdAndEmpCode = new HashMap<>();
	 * 
	 * List<Object[]> objectList =
	 * attendanceCalculationService.getAllPunchDetails(object);
	 * List<AttendanceLogDTO> attendanceLogDtoList = null;
	 * //attendanceCalculationAdaptor.objListToDto(objectList, prefix,
	 * //companyId,employeeDtoList,mapEmpIdAndEmpCode);
	 * 
	 * List<Object[]> empAttendanceList =
	 * punchTimeDetailsService.getEmpAttendaceList(1l); List<AttendanceLogDTO>
	 * systemAttendanceLogList=
	 * attendanceCalculationAdaptor.objectListToUImodel(empAttendanceList);
	 * List<AttendanceLogDTO> attendanceLogList=
	 * attendanceCalculationAdaptor.attendanceCalculation(attendanceLogDtoList,
	 * systemAttendanceLogList,mapEmpIdAndEmpCode);
	 * 
	 * 
	 * List<AttendanceLog> attendanceLogList1 =
	 * attendanceCalculationAdaptor.uiDtoToDatabaseModelList(attendanceLogList);
	 * 
	 * attendanceLogService.savePunchTimeLog(attendanceLogList1);
	 * logger.info(" attendanceLogList----->" + attendanceLogList); }
	 */
	/*
	 * @RequestMapping(value = "/punchtimedetails", method = RequestMethod.GET)
	 * public void scheduleTaskWithCronExpression() { List<Object[]>
	 * devideInfoObjList = deviceInfoService.findDevice(); List<String> sreialNoList
	 * = new ArrayList<String>(); List<DeviceInfo> deviceInfoList =
	 * deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList); Long companyId =1L;
	 * String prefix = "COM"; for (DeviceInfo deviceInfo : deviceInfoList) {
	 * sreialNoList.add(deviceInfo.getSerialNumber()); companyId =
	 * deviceInfo.getCompanyId(); prefix = deviceInfo.getPrefix(); }
	 * 
	 * EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
	 * List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();
	 * Map<String, Long> mapEmpIdAndEmpCode = new HashMap<>();
	 * 
	 * 
	 * List<Object[]> objectList
	 * =attendanceCalculationService.getAttendanceFromEpush( sreialNoList);
	 * List<AttendanceLogDTO> attendanceLogDtoList =
	 * attendanceCalculationAdaptor.objListToDto(objectList,employeeDtoList,
	 * mapEmpIdAndEmpCode,prefix,companyId);
	 * 
	 * List<Object[]> empAttendanceList =
	 * punchTimeDetailsService.getEmpAttendaceList(companyId);
	 * List<AttendanceLogDTO> systemAttendanceLogList=
	 * attendanceCalculationAdaptor.objectListToUImodel(empAttendanceList);
	 * List<AttendanceLogDTO> attendanceLogList=
	 * attendanceCalculationAdaptor.attendanceCalculation(attendanceLogDtoList,
	 * systemAttendanceLogList,mapEmpIdAndEmpCode,companyId);
	 * 
	 * HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);
	 * List<AttendanceLog> attendanceLogList1 =
	 * attendanceCalculationAdaptor.uiDtoToDatabaseModelList1(attendanceLogList,
	 * mapEmpIdAndEmpCode,halfDayRule);
	 * 
	 * attendanceLogService.savePunchTimeLog(attendanceLogList1); }
	 */

	@RequestMapping(value = "/attendanceSync", method = RequestMethod.GET)
	public void scheduleTaskWithCronExpression1() {
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}
		// sagar
//		EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
//		List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpCode.put(employeeCode, employeeId);
		}

		List<Object[]> lateCommersEmployeeListWithCount = attendanceLogService
				.getLateCommersEmployeeListWithCount(companyId);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = deviceLogsService.getAllLateComersListByDate(companyId, new Date());

//		Object count = lateCommersEmployeeListWithCount.stream().filter(x ->x[1].equals("COM-2434")).map(map->map[0]).findAny().orElse(null); 
//		Object graceFrqInMonth = lateCommersEmployeeListWithCount.stream().filter(x ->x[1].equals("COM-2434")).map(map->map[4]).findAny().orElse(null); 
//		String employeeCode =   deviceLogsInfoDTO.stream().filter(emp->emp.getEmpCode().equals("COM-2434")).map(DeviceLogsInfoDTO::getEmpCode).findAny().orElse(null);

//		if(Long.valueOf(count.toString()).compareTo(Long.valueOf(graceFrqInMonth.toString()))>0 && employeeCode!=null) {
//			
//		}
//		
		List<Object[]> objectList = attendanceCalculationService.getAttendanceFromEpush(sreialNoList);
		List<AttendanceLogDTO> attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList,
				employeeDtoList, mapEmpIdAndEmpCode, prefix, companyId);

		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodel(empAttendanceList, mapEmpCode);
		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor
				.attendanceCalculation1(attendanceLogDtoList, systemAttendanceLogList, mapEmpIdAndEmpCode, companyId);

		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);
		List<AttendanceLog> attendanceLogList1 = attendanceCalculationAdaptor.uiDtoToDatabaseModelList2(
				attendanceLogList, mapEmpIdAndEmpCode, halfDayRule, lateCommersEmployeeListWithCount,
				deviceLogsInfoDTO);

		attendanceLogService.savePunchTimeLog(attendanceLogList1);
	}

	/*
	 * 03-MAR-2020
	 */
	@GetMapping(value = "/attendanceSyncViaDate/{date}")
	public void SyncAttendanceViaDate(@PathVariable("date") @DateTimeFormat(iso = ISO.DATE) Date date) {
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}

		List<Object[]> lateCommersEmployeeListWithCount = attendanceLogService
				.getLateCommersEmployeeListWithCountViaDate(companyId, date);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = deviceLogsService.getAllLateComersListByDate(companyId, date);
		List<Object[]> objectList = new ArrayList<Object[]>();
		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		if (sreialNoList.size() > 0) {
			objectList = attendanceCalculationService.getAttendanceFromEpushViaDate(sreialNoList, date);
			attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList, employeeDtoList,
					mapEmpIdAndEmpCode, prefix, companyId);
		}

		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceListViaDate(companyId, date);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodelforCron(empAttendanceList, mapEmpCode);
		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor
				.attendanceCalculation1(attendanceLogDtoList, systemAttendanceLogList, mapEmpIdAndEmpCode, companyId);

		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);
		List<AttendanceLog> attendanceLogList1 = attendanceCalculationAdaptor.uiDtoToDatabaseModelList2(
				attendanceLogList, mapEmpIdAndEmpCode, halfDayRule, lateCommersEmployeeListWithCount,
				deviceLogsInfoDTO);

		attendanceLogService.savePunchTimeLogViaDate(attendanceLogList1, date);

	}

	/*
	 * 04-MAR-2020
	 */
	@GetMapping(value = "/unsuccessSyncAttendance/{companyId}")
	public List<AttendanceLogDTO> unsuccessSyncAttendance(@PathVariable("companyId") Long companyId) {
		Date currentDate = new Date();

		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		// Long companyId =1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);
		
		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}
		List<AttendanceLog> attendanceLogList = attendanceLogService.getAttendanceLogPreviousDays(companyId,
				currentDate);
		List<AttendanceLogDTO> attendanceLogSyncedFailedList = attendanceCalculationAdaptor
				.unseccessSyncedDateList(attendanceLogList, currentDate);

		List<Object[]> objectList = new ArrayList<Object[]>();
		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		
		List<Object[]> empAttendanceList = new ArrayList<Object[]>();

		Iterator<AttendanceLogDTO> itr = attendanceLogSyncedFailedList.iterator();

		while (itr.hasNext()) {
			AttendanceLogDTO syncedFailed = itr.next();
			if (sreialNoList.size() > 0) {
				objectList = attendanceCalculationService.getAttendanceFromEpushViaDate(sreialNoList,
						syncedFailed.getAttendanceDates());
				attendanceLogDtoList = attendanceCalculationAdaptor.objListToDto1(objectList, employeeDtoList,
						mapEmpIdAndEmpCode, prefix, companyId);
			}

			empAttendanceList = punchTimeDetailsService.getEmpAttendaceListViaDate(companyId,
					syncedFailed.getStartDates());
			
			
			if (attendanceLogDtoList.size() == 0 && empAttendanceList.size() == 0) {
				itr.remove();
			}
		}
		return attendanceLogSyncedFailedList;
	}

	@RequestMapping(value = "/firstAttendanceDetailsSync", method = RequestMethod.GET)
	public void FirstAttendanceCanculation1() throws ParseException {
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}
		// data
//		EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
//		List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}

		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodel(empAttendanceList, mapEmpCode);

		List<Object[]> epushAttendanceobjectList = attendanceCalculationService
				.getFirstAttendanceLogFromEpush(sreialNoList);

		List<AttendanceLogDTO> epushAttendanceLogDtoList = attendanceCalculationAdaptor
				.EpushAttendanceObjListToDto(epushAttendanceobjectList, prefix, mapEmpIdAndEmpCode);

		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor
				.attendanceCalculationForfirstAttendanceList(epushAttendanceLogDtoList, systemAttendanceLogList,
						mapEmpIdAndEmpCode, companyId);

		List<DeviceLogsInfo> deviceLogsInfoList = attendanceCalculationAdaptor
				.attendanceLogDtotoDeviceInfoList(attendanceLogList);
		deviceLogsService.saveAll(deviceLogsInfoList);

	}

	@RequestMapping(value = "/firstAttendanceDetails", method = RequestMethod.GET)
	public void FirstAttendanceCanculation() throws ParseException {
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

//		EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
//		List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, Long> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employee : employeeDtoList) {
			Long employeeId = employee.getEmployeeId();
			String employeeCode = employee.getEmployeeCode();
			mapEmpIdAndEmpCode.put(employeeCode, employeeId);
			mapEmpCode.put(employeeCode, employeeId);
		}
		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodel(empAttendanceList, mapEmpCode);

		List<Object[]> objectList = attendanceCalculationService.getFirstAttendanceLogFromEpush(sreialNoList);

		List<AttendanceLogDTO> attendanceLogDtoList = attendanceCalculationAdaptor.AttendanceObjListToDto(objectList,
				systemAttendanceLogList, prefix);

		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor.attendanceCalculationForfirstAttendance(
				attendanceLogDtoList, systemAttendanceLogList, mapEmpIdAndEmpCode, companyId);

		List<DeviceLogsInfo> deviceLogsInfoList = attendanceCalculationAdaptor
				.attendanceLogDtotoDeviceInfoList(attendanceLogList);
		deviceLogsService.saveAll(deviceLogsInfoList);

	}

	// sagar
	private EmployeeDTO getEmployeeIdByRestTamplate(String companyId) {
		logger.info("Company Id----->" + companyId);
		logger.info("getEmployeeIdByRestTamplate is calling : ");
		String url = environment.getProperty("application.employeeTemp");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, String> params = new HashMap<>();
		params.put("companyId", companyId);
		return restTemplate.getForObject(url, EmployeeDTO.class, params);
	}

	@RequestMapping(value = "/deleteByLogDate", method = RequestMethod.DELETE)
	public void deleteByLogDate(HttpServletRequest req) throws ParseException {
		logger.info("AttendanceCalculationController.deleteByLogDate()");
		deviceLogsService.deleteByLogDate();
		List<Object[]> devideInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(devideInfoObjList);
		Long companyId = 1L;
		String prefix = "COM";
		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
			prefix = deviceInfo.getPrefix();
		}

		// sagar
//		  EmployeeDTO employeeDto=getEmployeeIdByRestTamplate(companyId.toString());
		// List<EmployeeDTO> employeeDtoList = employeeDto.getEmployessList();

		List<Employee> employeeList = employeePersonalInformationService.fetchEmployee(companyId);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseModelToUiDtoListNativeSearch(employeeList);
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setEmployessList(employeeDtoList);

		Map<String, EmployeeDTO> mapEmpIdAndEmpCode = new HashMap<>();
		Map<String, Long> mapEmpCode = new HashMap<>();
		for (EmployeeDTO employeeDtoObj : employeeDtoList) {
			Long employeeId = employeeDtoObj.getEmployeeId();
			String boimatricId = employeeDtoObj.getBiometricId();
			String employeeCode = employeeDtoObj.getEmployeeCode();
			mapEmpIdAndEmpCode.put(boimatricId, employeeDtoObj);
			mapEmpCode.put(employeeCode, employeeId);
		}

		List<Object[]> empAttendanceList = punchTimeDetailsService.getEmpAttendaceList(companyId);
		List<AttendanceLogDTO> systemAttendanceLogList = attendanceCalculationAdaptor
				.objectListToUImodel(empAttendanceList, mapEmpCode);
		List<AttendanceLogDTO> epushAttendanceLogDtoList = new ArrayList<AttendanceLogDTO>();
		if (sreialNoList.size() > 0) {
		List<Object[]> epushAttendanceobjectList = attendanceCalculationService
				.getFirstAttendanceLogFromEpush(sreialNoList);
		
		epushAttendanceLogDtoList = attendanceCalculationAdaptor
				.EpushAttendanceObjListToDto(epushAttendanceobjectList, prefix, mapEmpIdAndEmpCode);
		}
		List<AttendanceLogDTO> attendanceLogList = attendanceCalculationAdaptor
				.attendanceCalculationForfirstAttendanceList(epushAttendanceLogDtoList, systemAttendanceLogList,
						mapEmpIdAndEmpCode, companyId);

		List<DeviceLogsInfo> deviceLogsInfoList = attendanceCalculationAdaptor
				.attendanceLogDtotoDeviceInfoList(attendanceLogList);
		deviceLogsService.saveAll(deviceLogsInfoList);
	}
}
