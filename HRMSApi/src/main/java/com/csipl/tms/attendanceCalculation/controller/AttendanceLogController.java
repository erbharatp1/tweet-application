package com.csipl.tms.attendanceCalculation.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.payroll.PayrollControlService;
import com.csipl.tms.attendanceCalculation.adaptor.AttendanceCalculationAdaptor;
import com.csipl.tms.attendanceCalculation.adaptor.AttendanceLogAdaptor;
import com.csipl.tms.attendancelog.service.AttendanceCalculationService;
import com.csipl.tms.attendancelog.service.AttendanceLogPaginationService;
import com.csipl.tms.attendancelog.service.AttendanceLogPagingAndFilterService;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.deviceinfo.adaptor.DeviceInfoAdaptor;
import com.csipl.tms.deviceinfo.service.DeviceInfoService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDetailsDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogSearchDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.empcommondetail.service.EmpCommonDetailService;
import com.csipl.tms.holiday.adaptor.HolidayAdaptor;
import com.csipl.tms.holiday.service.HolidayService;
import com.csipl.tms.leave.service.EmployeeLeaveService;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.deviceinfo.DeviceInfo;
import com.csipl.tms.model.empcommondetails.EmpCommonDetail;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.csipl.tms.rules.service.RulesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/attendanceLog")
@Api(description = "Operations pertaining to attendanceLog in TMS")
public class AttendanceLogController {

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	AttendanceLogService attendanceLogService;

	@Autowired
	EmpCommonDetailService empCommonDetailService;

	@Autowired
	RulesService rulesService;

	@Autowired
	EmployeeLeaveService employeeLeaveService;

	@Autowired
	HolidayService holidayService;

	@Autowired
	DeviceInfoService deviceInfoService;

	@Autowired
	AttendanceLogPagingAndFilterService attendanceLogPagingAndFilterService;

	@Autowired
	AttendanceLogPaginationService attendanceLogPaginationService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	AttendanceCalculationService attendanceCalculationService;

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

	DeviceInfoAdaptor deviceInfoAdaptor = new DeviceInfoAdaptor();

	AttendanceCalculationAdaptor attendanceCalculationAdaptor = new AttendanceCalculationAdaptor();
	@Autowired
	PayrollControlService payrollControlService;
	
	private static final Logger logger = LoggerFactory.getLogger(AttendanceLogController.class);
	AttendanceLogAdaptor attendanceLogAdaptor = new AttendanceLogAdaptor();
	HolidayAdaptor holidayAdaptor = new HolidayAdaptor();

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully save PunchTimeDetails data from ESSL mashine"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update the resource"),
			@ApiResponse(code = 403, message = "You were trying to save resource is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/attendanceLogDetails", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceLogDetailsDTO> getAttendanceLogDetails(@RequestParam("date") String date,
			@RequestParam("companyId") Long companyId) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date formatedDate = formatter.parse(date);
		List<AttendanceLog> attendanceLogDetailsList = attendanceLogService.getAttendanceLogDetails(companyId, date);
		logger.info("attendanceLogDetailsList..." + attendanceLogDetailsList);
		List<EmpCommonDetail> empCommonDetailList = empCommonDetailService.getEmployeeCommonDetailsList(companyId);

		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);
		List<TMSLeaveEntry> leaveList = employeeLeaveService.getEmployeeLeaveEntryListByDate(formatedDate);
		List<Object[]> tmsHolidays = holidayService.findMonthHoliday(companyId, date, date);
		List<HolidayDTO> holidayDtoList = holidayAdaptor.databaseModelObjToUiDtoList(tmsHolidays);

		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = attendanceLogService.getAttendanceLogDetailsDTOList(
				attendanceLogDetailsList, empCommonDetailList, halfDayRule, leaveList, holidayDtoList, formatedDate);

		return attendanceLogDetailsDtoList;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceLogDetailsDTO> getAttendanceLog(@RequestBody AttendanceLogSearchDTO attendanceLogSearchDto)
			throws ParseException { //
		logger.info(" getAttendanceLog is calling :");
		AttendanceLogAdaptor attendanceLogAdaptor = new AttendanceLogAdaptor();
		String attendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceLogSearchDto.getAttendanceDate());
		List<Object[]> attendanceLogDetails = attendanceLogPagingAndFilterService
				.getAttendanceLogByPagingAndFilter(attendanceLogSearchDto, attendanceDate);
		return attendanceLogAdaptor.modeltoDTONewList(attendanceLogDetails, attendanceDate);
	}

	@RequestMapping(value = "/count/{pageSize}", method = RequestMethod.POST)
	public @ResponseBody EntityCountDTO getAttendanceLogCount(@PathVariable("pageSize") String pageSize,
			@RequestBody AttendanceLogSearchDTO attendanceLogSearchDto) throws ParseException {
		int count;
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String attendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceLogSearchDto.getAttendanceDate());

		logger.info(
				"---before---" + new SimpleDateFormat("yyyy-MM-dd").format(attendanceLogSearchDto.getAttendanceDate()));
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		////commented because of inconsistency
		////attendanceLogPagingAndFilterService.getAttendanceLogCount(entityCountDto, attendanceLogSearchDto,attendanceDate);
		////count = entityCountDto.getCount();
		List<Object[]> attendanceLogDetails = attendanceLogPagingAndFilterService.getAttendanceLogByPagingAndFilter(attendanceLogSearchDto, attendanceDate);
		entityCountDto.setCount(attendanceLogDetails.size());
		count = entityCountDto.getCount();
		logger.info("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		logger.info("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	// save attendance
	@RequestMapping(value = "/bulkAttendance/{attendanceDate}/{attendanceStatus}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceLogDTO> markBulkAttendance(@RequestBody List<AttendanceLogDTO> attendanceLogDtoList,
			@PathVariable("attendanceDate") Date attendanceDate,
			@PathVariable("attendanceStatus") String attendanceStatus) throws ErrorHandling {
		String attendstatus = attendanceLogAdaptor.changePbyTwo(attendanceStatus);
		
		String psMonth = DateUtils.getDateStringWithYYYYMMDD(attendanceDate);
		String processMonth = psMonth.toUpperCase();
		//here we will check process month is locked or not if locked throw exception
		List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
		if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
			throw new ErrorHandling(processMonth +"  is locked, you can not mark bulk attendance");
		}
		
		List<AttendanceLog> attendanceLogList = attendanceLogAdaptor.uiDtoToDatabaseModelList(attendanceLogDtoList,
				attendanceDate, attendstatus);

		List<AttendanceLog> attendanceLogList1 = attendanceLogService.markBulkAttendance(attendanceLogList);

		List<AttendanceLogDTO> attendanceLogDTOList = attendanceLogAdaptor.databaseModelToUiDtoList(attendanceLogList1);

		return attendanceLogDTOList;
	}

	// fatching data
	@RequestMapping(value = "/attendanceLogs/{attendanceDate}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceLogDTO> getAttendanceLogPaginationList(@RequestBody EmployeeSearchDTO employeeSearcDto,
			@PathVariable("attendanceDate") Date attendanceDate) {
		String strAttendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceDate);
		logger.info("deptId " + employeeSearcDto.getDepartmentId() + "desigId " + employeeSearcDto.getDesignationId()
				+ " AttendanceStatus>>" + employeeSearcDto.getAttendaceStatus() + "EmpCode "
				+ employeeSearcDto.getEmployeeCode());
		List<Object[]> objAttendanceLogList = attendanceLogPaginationService
				.getAttendanceLogForMarkAttendance(employeeSearcDto, strAttendanceDate);
		List<AttendanceLogDTO> attendanceDtoList = attendanceLogAdaptor
				.databaseObjModelToUiDtoList(objAttendanceLogList);
		List<AttendanceLogDTO> attendanceDtoListLeaveExcluded = attendanceLogAdaptor
				.attendanceDtoListLeaveExcluded(attendanceDtoList);

		if (attendanceDtoListLeaveExcluded != null && attendanceDtoListLeaveExcluded.size() > 0) {
			List<ReportPayOut> reportPayOutForEmployees = payStructureService.findReportPayoutForMonth(attendanceDate,
					attendanceDtoListLeaveExcluded.get(0).getCompanyId());
			return attendanceLogAdaptor.attendanceDtoListPayrollExcluded(attendanceDtoListLeaveExcluded,
					reportPayOutForEmployees);

		}
		return attendanceDtoListLeaveExcluded;
	}

	@RequestMapping(path = "/attendanceReport/{companyId}/{attendanceDate}", method = RequestMethod.GET)
	public void attendanceReport(@PathVariable("companyId") Long companyId,
			@PathVariable("attendanceDate") Date attendanceDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException {
		logger.info(">>>>>> attendanceReport <<<<<<<<<<<<<<<<<<< Date " + attendanceDate);
		logger.info(">>>>>> attendanceReport <<<<<<<<<<<<<<<<<<< Date toString " + attendanceDate.toString());
		String[] columns = { "Sr. No.", "Date", "Employee Code", "Employee", "Department", "Job Location",
				"Reporting Manager", "Shift", "Shift Duration", "Punching Mode", "Time In", "Time Out", "Total Hours",
				"Attendance Status", "Late By", "Early By", "Early Before", "Location -Time In", "Location-Time Out" };
		List<Object[]> attendanceLogDetails = attendanceLogService.attendanceReport(companyId, attendanceDate);

		String attendDate = new SimpleDateFormat("yyyy-MM-dd").format(attendanceDate);

		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = attendanceLogAdaptor
				.modeltoDTOCurrentDateList(attendanceLogDetails, attendDate);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=attandanceReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (attendanceLogDetailsDtoList.size() > 0) {
				Workbook workbook = AttendanceReportExcelWriter.attendanceNewReport(attendanceLogDetailsDtoList,
						columns, attendDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			}

		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully get attendanceLog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the attendanceLog"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * This is the first parameter for getting shift Object from UI
	 */
	@ApiOperation(value = "View List of attendanceLog based on employee ID,company ID")
	@RequestMapping(path = "/{employeeId}/{processMonth}/{companyId}", method = RequestMethod.GET)
	public String attendanceLog(@PathVariable("employeeId") Long employeeId,
			@PathVariable("processMonth") String processMonth, @PathVariable("companyId") Long companyId) throws ErrorHandling, ParseException {
		logger.info(
				">>>>>> attendanceLog <<<<<<<<<<<<<<<<<<<" + employeeId + "<<<<<<<<<<month>>>>>>>>>" + processMonth);
		AttendanceLogDTO calendarDto = attendanceLogService.calculateAttendanceForPayroll(employeeId, processMonth,
				companyId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setDateFormat(df);
		objectMapper.setTimeZone(TimeZone.getDefault());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String dateWiseResultJson = null;
		try {
			dateWiseResultJson = objectMapper.writeValueAsString(calendarDto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateWiseResultJson;
	}

	@RequestMapping(path = "/getAllCheckInDetails/{employeeId}", method = RequestMethod.GET)
	public List<AttendanceLogDTO> getAllCheckInDetailsList(@PathVariable("employeeId") Long employeeId)
			throws ParseException {

		List<Object[]> checkInList = attendanceLogService.getCheckInFromPunchTimeDetails(employeeId);

		List<AttendanceLogDTO> checkInDTOList = attendanceLogAdaptor.modeltoCheckInDTOList(checkInList);

		List<AttendanceLogDTO> epushAttendanceLogDtoList = getCheckInDetailsFromEPushServer(employeeId);

		logger.info(">>>>>> epushAttendanceLogDtoList <<<<<<<<" + epushAttendanceLogDtoList);

		logger.info(">>>>>> checkInList <<<<<<<<" + checkInList);

		if (epushAttendanceLogDtoList == null || epushAttendanceLogDtoList.size() == 0) {
			return checkInDTOList;
		}

		List<AttendanceLogDTO> commonList = Stream.concat(checkInDTOList.stream(), epushAttendanceLogDtoList.stream())
				.collect(Collectors.toList());

		Collections.sort(commonList, new Comparator<AttendanceLogDTO>() {

			public int compare(AttendanceLogDTO al1, AttendanceLogDTO al2) {
				long time1 = 0;
				long time2 = 0;

				try {
					String d1 = al1.getCheckInTime();
					Date date1;
					date1 = new SimpleDateFormat("hh:mm:ss").parse(d1);
					time1 = date1.getTime();

					String d2 = al2.getCheckInTime();
					Date date2 = new SimpleDateFormat("hh:mm:ss").parse(d2);
					time2 = date2.getTime();

				} catch (ParseException e) {
					e.printStackTrace();
				}
				return time1 > time2 ? -1 : (time1 < time2 ? 1 : 0);
			}
		});

		logger.info(">>>>>> commonList <<<<<<<<" + commonList);

		return commonList;
	}

	@SuppressWarnings("unused")
	public List<AttendanceLogDTO> getCheckInDetailsFromEPushServer(Long employeeId) {

		List<Object[]> deviceInfoObjList = deviceInfoService.findDevice();
		List<String> sreialNoList = new ArrayList<String>();
		List<DeviceInfo> deviceInfoList = deviceInfoAdaptor.getDeviceInfoList(deviceInfoObjList);

		List<AttendanceLogDTO> epushAttendanceLogDtoList = new ArrayList<AttendanceLogDTO>();

		Long companyId = 1L;

		for (DeviceInfo deviceInfo : deviceInfoList) {
			sreialNoList.add(deviceInfo.getSerialNumber());
			companyId = deviceInfo.getCompanyId();
		}

		Employee employeeDetails = employeePersonalInformationService.fetchEmployeeByEmployeeId(employeeId);
		EmployeeDTO employeeDtoDetails = employeePersonalInformationAdaptor
				.databaseModelToUiDtoCheckInList(employeeDetails);
		String boimetricId = employeeDtoDetails.getBiometricId();

		if (sreialNoList.size() > 0) {
			List<Object[]> epushAttendanceobjectList = attendanceCalculationService
					.getCheckInAttendanceFromEpush(sreialNoList, boimetricId);

			try {
				epushAttendanceLogDtoList = attendanceCalculationAdaptor
						.EpushAttendanceObjCheckInListToDto(epushAttendanceobjectList);

			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		return epushAttendanceLogDtoList;
	}

}
