package com.csipl.tms.calendar.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.AgeWiseChartEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.common.DateWiseAttendanceLogDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.report.AgeData;
import com.csipl.hrms.dto.report.AgeDto;
import com.csipl.hrms.dto.report.AgeWiseChart;
import com.csipl.hrms.dto.report.AttendanceChart;
import com.csipl.hrms.dto.report.AttendanceData;
import com.csipl.hrms.dto.report.AttendanceDto;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.tms.attendanceCalculation.controller.AttendanceReportExcelWriter;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceAdaptor;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceRegularizationRequestAdaptor;
import com.csipl.tms.attendanceregularizationrequest.controller.AttendanceRegularizationRequestController;
import com.csipl.tms.attendanceregularizationrequest.service.AttendanceRegularizationRequestService;
import com.csipl.tms.calendar.service.CalendarService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceValidationResult;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.calendar.CalendarDTO;
import com.csipl.tms.dto.halfdayrule.HalfDayRuleDTO;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.monthattendance.AttendanceChartEnum;
import com.csipl.tms.empcommondetail.service.EmpCommonDetailService;
import com.csipl.tms.halfdayrule.adaptor.HalfDayRuleAdaptor;
import com.csipl.tms.holiday.adaptor.HolidayAdaptor;
import com.csipl.tms.holiday.service.HolidayService;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.service.EmployeeLeaveService;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.model.shift.Shift;
import com.csipl.tms.report.service.LeaveAttendanceReportService;
import com.csipl.tms.rules.service.RulesService;
import com.csipl.tms.tmsempshift.service.TMSEmpShiftService;
import com.csipl.tms.weekoffpattern.adaptor.WeekOffPatternAdaptor;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class CalendarController {

	@Autowired
	AttendanceLogService attendanceLogService;

	@Autowired
	AttendanceRegularizationRequestService attendanceRegularizationRequestService;

	@Autowired
	WeekOffPatternService weekOffPatternService;

	@Autowired
	HolidayService holidayService;
	Date start = null;
	@Autowired
	TMSEmpShiftService tMSEmpShiftService;

	@Autowired
	EmployeeLeaveService employeeLeaveService;

	@Autowired
	RulesService rulesService;

	@Autowired
	CalendarService calendarService;

	@Autowired
	EmpCommonDetailService empCommonDetailService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	
	
	@Autowired
	LeaveAttendanceReportService leaveAttendanceReportService;
	
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AttendanceRegularizationRequestController.class);

	@RequestMapping(value = "/calendar1", method = RequestMethod.GET)
	public @ResponseBody CalendarDTO getCalendar(@RequestParam("companyId") Long companyId,
			@RequestParam("employeeId") Long employeeId, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) throws ParseException {

		String weekOffPattern = null;
		String empShiftName = null;
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = dateFormat.format(inputFormat.parse(fromDate));
		String endDate = dateFormat.format(inputFormat.parse(toDate));
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		// Object[] empJoiningDateObj =
		// empCommonDetailService.getEmployeeJoiningDate(employeeId);

		List<Object[]> attendanceLogList = attendanceLogService.getAttendance(companyId, employeeId, startDate,
				endDate);

		List<Object[]> arRequestList = attendanceRegularizationRequestService.getARRequestforMonth(companyId,
				employeeId, startDate, endDate);

		Object[] weekOffPatternObj = weekOffPatternService.getWeekOffPatternByEmp(companyId, employeeId);

		if (weekOffPatternObj.length > 0) {
			weekOffPattern = weekOffPatternObj[0].toString();
		}

		List<Object[]> tmsHolidays = holidayService.findMonthHoliday(companyId, startDate, endDate);
		// EmpCommonDetail
		// empCommonDetail=empCommonDetailService.getEmployeeCommonDetails(employeeId);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Date empJoiningDateObj = employee.getDateOfJoining();
		List<Object[]> empShiftNameObj = employeePersonalInformationService.getEmpShiftName(employeeId);
		System.out.println("empShiftNameObj" + empShiftNameObj);
		Shift shift = new Shift();
		for (Object[] emp : empShiftNameObj) {

			if (emp[0] != null) {
				shift.setShiftId(employee.getShiftId());
			}
			if (emp[1] != null) {
				shift.setShiftFName(emp[1].toString());
			}
		}

//		if (empShiftNameObj != null && empShiftNameObj.length > 0) {
//			empShiftName = empShiftNameObj[0].toString();
//		}

//		shift.setShiftId(employee.getShiftId());
//		shift.setShiftFName(empShiftName);

		// Object[] shiftNameObj =
		// tMSEmpShiftService.getEmpShiftOnemployeeId(employeeId);

		List<Object[]> leaveEntryObj = employeeLeaveService.getMonthEmployeeLeaveEntry(companyId, employeeId, startDate,
				endDate);

		HalfDayRule halfDayRule = rulesService.getHalfDayRule(companyId);

		WeekOffPatternAdaptor weekOffPatternAdaptor = new WeekOffPatternAdaptor();
		HolidayAdaptor holidayAdaptor = new HolidayAdaptor();
		LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
		HalfDayRuleAdaptor halfDayRuleAdaptor = new HalfDayRuleAdaptor();
		AttendanceAdaptor attendanceAdaptor = new AttendanceAdaptor();
		AttendanceRegularizationRequestAdaptor arRequestAdaptor = new AttendanceRegularizationRequestAdaptor();

		List<AttendanceLogDTO> attendanceLogDtoList = attendanceAdaptor.objArraytoDtoList(attendanceLogList);
		List<AttendanceRegularizationRequestDTO> arRequestDtoList = arRequestAdaptor
				.arModelListToArDtoList(arRequestList);

		String[] weekOffPatternArray = weekOffPatternAdaptor.databaseModeObjlToUiDto(weekOffPattern);
		List<HolidayDTO> holidayDtoList = holidayAdaptor.databaseModelObjToUiDtoList(tmsHolidays);
		List<LeaveEntryDTO> leaveEntryDtoList = leaveEntryAdaptor.databaseModelObjToUiDtoList(leaveEntryObj);
		HalfDayRuleDTO halfDayRuleDto = halfDayRuleAdaptor.databaseModelToUiDto(halfDayRule);

		CalendarDTO calendarDto = calendarService.calendarLogs(empJoiningDateObj, fDate, tDate, attendanceLogDtoList,
				arRequestDtoList, weekOffPatternArray, holidayDtoList, shift, leaveEntryDtoList, halfDayRuleDto);

		logger.info("calendarDtoList------->" + calendarDto.getEvents().size());

		return calendarDto;

	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully get attendanceLog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the attendanceLog"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param shiftDto This is the first parameter for getting shift Object from UI
	 *                 (@RequestParam("companyId") Long
	 *                 companyId, @RequestParam("employeeId") Long
	 *                 employeeId, @RequestParam("fromDate") String
	 *                 fromDate, @RequestParam("toDate") String toDate)
	 */
	@ApiOperation(value = "View List of attendanceLog based on employee ID,company ID")
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public @ResponseBody String getCalendar1(@RequestParam("companyId") Long companyId,
			@RequestParam("employeeId") Long employeeId, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) throws ParseException , ErrorHandling{
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date date = inputFormat.parse(fromDate);
		int year = DateUtils.getYear(date);
		int month = DateUtils.getMonth(date);

		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		String monthAcronym = shortMonths[month - 1];
		String processMonth = monthAcronym.toUpperCase() + "-" + String.valueOf(year);
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

	
	@ApiOperation(value = "View List of attendanceLog based on employee ID,company ID")
	@RequestMapping(value = "/getMarkBulkAttentanceData", method = RequestMethod.GET)
	public @ResponseBody String getMarkBulkAttentanceData(@RequestParam("companyId") Long companyId,
			@RequestParam("departmentIdList") List<Long> departmentId, @RequestParam("processMonth") String processMonth) throws ParseException , ErrorHandling{
		List<Employee> employeeList = employeePersonalInformationService.findAllEmpByDeptIdList(companyId, departmentId);
		List<AttendanceLogDTO> calendarList = new ArrayList<>(); 
		for (Employee employee : employeeList) {
			List<com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO> list = new ArrayList<com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO>();
			AttendanceLogDTO calendarDto = attendanceLogService.calculateAttendanceForPayroll(employee.getEmployeeId(),
					processMonth.toUpperCase(), companyId);
			List<com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO> events = calendarDto.getEvents().stream()
					.filter(dates -> dates.getStart().compareTo(new Date())<0).collect(Collectors.toList());
			Date currentDate = new Date();
			int currentmonth = currentDate.getMonth();
			Date employeeJoiningDate = employee.getDateOfJoining();
			Date startDate = DateUtils.getDateForProcessMonth(processMonth);
			Date endDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(processMonth));
			AttendanceValidationResult attendanceValidationResult = new AttendanceValidationResult();
			if (currentmonth == employeeJoiningDate.getMonth()
					&& (currentDate.getYear() == employeeJoiningDate.getYear())) {
				com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO dateWiseAttendanceLogDTO =	events.get(0);
				DateUtils.getDatesBetweenUsingForFormate(startDate, endDate).forEach(localdate -> {
					com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO dto = new com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO(null,
							DateUtils.getDateFromLocalDateWithYYYYMMDD(localdate), "", "");
					attendanceValidationResult.getAttendanceMap().put(DateUtils.getDateFromLocalDate(localdate), dto);
				});
				Collection<com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO> dateWiseAttendanceLog = attendanceValidationResult.getAttendanceMap()
						.values();
				dateWiseAttendanceLog.forEach(action->{
					if(dateWiseAttendanceLogDTO.getStart().compareTo(action.getStart())>0) {
						list.add(action);
					}
				});
				events.addAll(list);
				Collections.sort(events, Comparator.comparing(com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO::getStart));
//				System.out.println("sagar");
			}
			for(com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO dwa:events) {
				if(dwa.getTitle().equals("P/2")) {
					dwa.setTitle("HD");
				}
			}
			calendarDto.setEvents(events);
			calendarList.add(calendarDto);

		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setDateFormat(df);
		objectMapper.setTimeZone(TimeZone.getDefault());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String dateWiseResultJson = null;
		try {
			dateWiseResultJson = objectMapper.writeValueAsString(calendarList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return dateWiseResultJson;

	}
	
	
 
	@RequestMapping(value = "/updateAttendaceData", method = RequestMethod.POST)
	public  void updateAttendaceData( @RequestBody List<com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO> dateWiseAttendanceLogDTO) throws ParseException  {
		dateWiseAttendanceLogDTO.forEach(action -> {
			// DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Date date;
			// date = inputFormat.parse(action.getStart().toString());
			attendanceLogService.updateAttendaceData(action.getCompanyId(), action.getEmployeeCode(), action.getStart(),
					action.getStatus(), action.getEmployeeId());
		});
		
	}

	
	@ApiOperation(value = "View List of attendanceLog based on employee ID,company ID")
	@RequestMapping(value = "/attendanceLog", method = RequestMethod.GET)
	public @ResponseBody AttendanceLogDTO getAttendanceLog(@RequestParam("companyId") Long companyId,
			@RequestParam("employeeId") Long employeeId, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) throws ParseException , ErrorHandling{
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date date = inputFormat.parse(fromDate);
		int year = DateUtils.getYear(date);
		int month = DateUtils.getMonth(date);

		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		String monthAcronym = shortMonths[month - 1];
		String processMonth = monthAcronym.toUpperCase() + "-" + String.valueOf(year);
		AttendanceLogDTO calendarDto = attendanceLogService.calculateAttendanceForPayroll(employeeId, processMonth,
				companyId);

		return calendarDto;

	}

	@GetMapping(path = "/empAttendanceRatio/{companyId}/{employeeId}")
	public @ResponseBody AttendanceDto empAttendanceMonthly(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId)   throws ParseException , ErrorHandling{
		List<EmployeeReportDTO> employeeReportDTO = calendarService.empAttendanceRatio(companyId, employeeId);
		ArrayList<AttendanceData> arrayData = new ArrayList<AttendanceData>();
		AttendanceChart chart = new AttendanceChart(AttendanceChartEnum.bgColor.getPieChartValue(),
				AttendanceChartEnum.startingAngle.getPieChartValue(), AttendanceChartEnum.showLegend.getPieChartValue(),
				AttendanceChartEnum.showTooltip.getPieChartValue(), AttendanceChartEnum.decimals.getPieChartValue(),
				AttendanceChartEnum.theme.getPieChartValue(), AttendanceChartEnum.pieRadius.getPieChartValue(),
				AttendanceChartEnum.labelDistance.getPieChartValue(),
				AttendanceChartEnum.showPercentValues.getPieChartValue(),
				AttendanceChartEnum.showPercentInToolTip.getPieChartValue(),
				AttendanceChartEnum.formatNumber.getPieChartValue(), AttendanceChartEnum.formatNumberScale.getPieChartValue(),
				AttendanceChartEnum.forceNumberScale.getPieChartValue(),
				AttendanceChartEnum.numberScaleUnit.getPieChartValue(), AttendanceChartEnum.smartLineColor.getPieChartValue(),
				AttendanceChartEnum.labelFontColor.getPieChartValue(), AttendanceChartEnum.showLabels.getPieChartValue(),
				AttendanceChartEnum.legendItemFontColor.getPieChartValue());
		for (EmployeeReportDTO empDt : employeeReportDTO) {
			if (empDt.getEmpAttenance().equalsIgnoreCase("0")) {

			} else {
				AttendanceData data = new AttendanceData(empDt.getEmpAttendanceeRange(), empDt.getEmpAttenance(), empDt.getColor());
				arrayData.add(data);
			}

		}

		AttendanceDto at = new AttendanceDto(chart, arrayData); //
		System.out.println("attendance dto.." + at.toString());
		return at;

	}

	
//	@RequestMapping(path = "/empAgeWiseRatio/{companyId}", method = RequestMethod.GET)
//	public @ResponseBody AgeDto empAgeWiseRatioApp(@PathVariable("companyId") Long companyId,@PathVariable("companyId") Long employeeId)
//			throws ErrorHandling, PayRollProcessException, ParseException {
//	//	log.info("EmpDashboardController.empAgeWiseRatioApp()");
//
//		List<EmployeeReportDTO> employeeReportDTO = calendarService.empAttendanceRatio(companyId, employeeId);
//	//	ArrayList<AgeData> arrayData = new ArrayList<AgeData>();
//		ArrayList<AttendanceData> arrayData = new ArrayList<AttendanceData>();
//		AgeWiseChart chart = new AgeWiseChart(AgeWiseChartEnum.bgColor.getPieChartValue(),
//				AgeWiseChartEnum.startingAngle.getPieChartValue(), AgeWiseChartEnum.showLegend.getPieChartValue(),
//				AgeWiseChartEnum.showTooltip.getPieChartValue(), AgeWiseChartEnum.decimals.getPieChartValue(),
//				AgeWiseChartEnum.theme.getPieChartValue(), AgeWiseChartEnum.pieRadius.getPieChartValue(),
//				AgeWiseChartEnum.labelDistance.getPieChartValue(),
//				AgeWiseChartEnum.showPercentValues.getPieChartValue(),
//				AgeWiseChartEnum.showPercentInToolTip.getPieChartValue(),
//				AgeWiseChartEnum.formatNumber.getPieChartValue(), AgeWiseChartEnum.formatNumberScale.getPieChartValue(),
//				AgeWiseChartEnum.forceNumberScale.getPieChartValue(),
//				AgeWiseChartEnum.numberScaleUnit.getPieChartValue(), AgeWiseChartEnum.smartLineColor.getPieChartValue(),
//				AgeWiseChartEnum.labelFontColor.getPieChartValue(), AgeWiseChartEnum.showLabels.getPieChartValue(),
//				AgeWiseChartEnum.legendItemFontColor.getPieChartValue());
//
//		for (EmployeeReportDTO empDt : employeeReportDTO) {
//			if (empDt.getEmpAge().equalsIgnoreCase("0")) {
//			} else {
//				AgeData data = new AgeData(empDt.getEmpRange(), empDt.getEmpAge(), empDt.getColor());
//				arrayData.add(data);
//			}
//		}
//		AgeDto at = new AgeDto(chart, arrayData);
//		return at;
//
//	}
	
	
	
	
	
	
	@RequestMapping(value = "/employeeMonthlyAttendance/{companyId}/{processMonth}/{departmentDTOList}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceLogDTO>  get(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") String processMonth,
			@PathVariable("departmentDTOList") List<Long> departmentDTOList, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException {
		EmployeeDTO employeeDto = new EmployeeDTO();
		List<EmployeeDTO> employeeDtoList = new ArrayList<>();
		//int pMonth = DateUtils.getMonthForProcessMonth(processMonth);
		//int pYear = DateUtils.getYearForProcessMonth(processMonth);
		Date lastDate=DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(processMonth));
		for (Long dept : departmentDTOList) {
			
			List<Employee>  employeeList = employeePersonalInformationService.fetchEmployeeWithDepartment(companyId,Long.valueOf(dept.toString()));
			List<EmployeeDTO> employeeDtoListData = employeePersonalInformationAdaptor
					.databaseModelToUiDtoListNativeSearch(employeeList);

			employeeDto.setEmployessList(employeeDtoListData);
			
//			employeeDto = getEmployeeIdByRestTemplate(companyId.toString(), dept.toString());
			employeeDto.getEmployessList().add(employeeDto);
			employeeDtoList.addAll(employeeDto.getEmployessList());
		}
		// employeeDtoList = employeeDto.getEmployessList();
		List<AttendanceLogDTO> timeAttendanceList = new ArrayList<AttendanceLogDTO>();
		for (int i = 0; i <= employeeDtoList.size() - 1; i++) {
			if (employeeDtoList.get(i).getEmployeeId() != null) {
				Date empJoiningDate = employeeDtoList.get(i).getDateOfJoining();
				if(empJoiningDate!=null) {
				if(empJoiningDate.before(lastDate)) {
				//if( (pMonth != empJoiningDate.getMonth()) && (pYear != empJoiningDate.getYear())) {
					//if last date of process month < employee joining
					AttendanceLogDTO calendarDto = leaveAttendanceReportService.getAttendanceReportMonthWise(
							employeeDtoList.get(i).getEmployeeId(), processMonth, companyId);
					//logger.info(" attendance generate for employee===" + employeeDtoList.get(i).getEmployeeId());
					timeAttendanceList.add(calendarDto);
				}
				}
			}
		}


		logger.info(" timeAttendanceList===" +timeAttendanceList);
  return timeAttendanceList;
	}


	
	
	@RequestMapping(value="/findAllPreviousMonth/{companyId}",method = RequestMethod.GET)
	public List<String> allMonthList(@PathVariable("companyId") Long companyId) throws ParseException {
		

		List<String> allDates = new ArrayList<>();
		String maxDate ="AUG-2020";
		
		
		//String todaysDate=new Date().toString();
//		DateFormat df=new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
//		String str=df.format(todaysDate);
		
//		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
//		Date date = inputFormat.parse(todaysDate);
//		int year = DateUtils.getYear(date);
//		int month = DateUtils.getMonth(date);
//
//		String[] shortMonths = new DateFormatSymbols().getShortMonths();
//		String monthAcronym = shortMonths[month - 1];
//		String processMonth = monthAcronym.toUpperCase() + "-" + String.valueOf(year);
		
		
		
		SimpleDateFormat monthDate = new SimpleDateFormat("MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(monthDate.parse(maxDate));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		for (int i = 1; i <= 12; i++) {
		    String month_name1 = monthDate.format(cal.getTime());
		    allDates.add(month_name1);
		    cal.add(Calendar.MONTH, -1);
		}
		//System.out.println(allDates);
		logger.info("allDates is end  :" + "allDates" + allDates);
		return allDates;
	}

}
