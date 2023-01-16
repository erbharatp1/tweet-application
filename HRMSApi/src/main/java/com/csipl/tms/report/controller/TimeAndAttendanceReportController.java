package com.csipl.tms.report.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.tms.attendanceCalculation.controller.AttendanceReportExcelWriter;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.TMSLeavePeriodDTO;
import com.csipl.tms.empcommondetail.service.EmpCommonDetailService;
import com.csipl.tms.leave.report.adaptor.LeaveAttendanceReportAdapator;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.report.service.LeaveAttendanceReportService;

@RestController
@RequestMapping("/timeAttendanceLeaveReport")
public class TimeAndAttendanceReportController {

	private static final Logger logger = LoggerFactory.getLogger(TimeAndAttendanceReportController.class);

	@Autowired
	LeaveAttendanceReportService leaveAttendanceReportService;

	@Autowired
	EmpCommonDetailService empCommonDetailService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	private org.springframework.core.env.Environment environment;

	LeaveAttendanceReportAdapator leaveAttendanceReportAdapator = new LeaveAttendanceReportAdapator();
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();

// Monthly Attendance report
	@RequestMapping(value = "/monthlyAttendance/{companyId}/{processMonth}/{departmentDTOList}", method = RequestMethod.GET)
	public void TimeAndAttendanceReport(@PathVariable("companyId") Long companyId,
			@PathVariable("processMonth") String processMonth,
			@PathVariable("departmentDTOList") List<Long> departmentDTOList, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException {
		EmployeeDTO employeeDto = new EmployeeDTO();
		List<EmployeeDTO> employeeDtoList = new ArrayList<>();

		Date lastDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(processMonth));
		for (Long dept : departmentDTOList) {

			List<Employee> employeeList = employeePersonalInformationService
					.fetchEmployeeFromDepartmentAndSeparation(companyId, Long.valueOf(dept.toString()));
			List<EmployeeDTO> employeeDtoListData = employeePersonalInformationAdaptor
					.databaseModelToUiDtoListNativeSearch(employeeList);

			employeeDto.setEmployessList(employeeDtoListData);

			employeeDto.getEmployessList().add(employeeDto);
			employeeDtoList.addAll(employeeDto.getEmployessList());
		}

		List<AttendanceLogDTO> timeAttendanceList = new ArrayList<AttendanceLogDTO>();
		for (int i = 0; i <= employeeDtoList.size() - 1; i++) {
			if (employeeDtoList.get(i).getEmployeeId() != null) {
				Date empJoiningDate = employeeDtoList.get(i).getDateOfJoining();
				if (empJoiningDate.before(lastDate) || empJoiningDate.equals(lastDate)) {

					AttendanceLogDTO calendarDto = leaveAttendanceReportService.getAttendanceReportMonthWise(
							employeeDtoList.get(i).getEmployeeId(), processMonth, companyId);

					timeAttendanceList.add(calendarDto);
				}
			}
		}

		String[] columns = { "Employee Code", "Employee", "Department", "Designation", "Job Location",
				"Reporting Manager" };
		List<Date> dateColumnList = new ArrayList<Date>();
		Date calDate = new Date();

		for (int j = 0; j < timeAttendanceList.get(0).getEvents().size(); j++) {
			calDate = timeAttendanceList.get(0).getEvents().get(j).getStart();
			dateColumnList.add(calDate);
		}
		String[] moreColumns = { "Present", "Half Day", "Attendance Regularize", "Total Present", "Weekly Off",
				"Public holiday", "Leave", "Absent", "Total Days Worked", "Days In Month" };

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Monthly Attendance.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (timeAttendanceList.size() > 0) {
				Workbook workbook = AttendanceReportExcelWriter.dayWiseattendanceReport(timeAttendanceList, columns,
						dateColumnList, moreColumns);
				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			}

		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private EmployeeDTO getEmployeeIdByRestTemplate(String companyId, String deptId) {
// logger.info("Company Id----->" + companyId);
// logger.info("getEmployeeIdByRestTamplate is calling : ");
		String url = environment.getProperty("application.getEmployeeByDept");
// String url =
// "http://localhost:8080/hrmsApi/employee/employeeWithDept/{companyId}/{deptId}";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Long> params = new HashMap<>();
		params.put("companyId", Long.parseLong(companyId));
		params.put("deptId", Long.parseLong(deptId));
		return restTemplate.getForObject(url, EmployeeDTO.class, params);
	}

// late comers report
	@RequestMapping(value = "/lateComerReport", method = RequestMethod.GET)
	public void lateComersReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException {
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<AttendanceLogDTO> lateComerList = leaveAttendanceReportService.getLateComersReport(companyId, employeeId,
				departmentIds, fDate, tDate);
		logger.info("lateComerList is calling : " + lateComerList);
		String[] columns = { "Date", "Code", "Employee", "Department", "Designation", "Job Location",
				"Reporting Manager", "Shift", "Shift Duration", "Time In ", "Time Out ", "Total Hours ", "Late By",
				"Attendance Status" };

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Late Comers Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			Workbook workbook = AttendanceReportExcelWriter.lateComersReport(lateComerList, columns, fDate, tDate);
			ServletOutputStream fileOut = response.getOutputStream();
			logger.info("after generate late comers report ========");
			workbook.write(fileOut);
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

	}

// Leave Entitlement And Leave Summary Report

	@RequestMapping(path = "/leaveEntitlement", method = RequestMethod.GET)
	public void generateLeaveEntitlementAndBalanceSummary(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam Long leavePeriodId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {
// logger.info("leaveEntitlementList is start :" + "attendanceDTOList");
		String columns[] = { "Code", "Employee", "Department", "Designation", "Job Location ", "Date Of Joining",
				"Reporting Manager", "Leave Scheme", "Leave Entitlement (Annual Quota) ", "Leave Carry Forward ",
				"Leave Consumed", "Balance" };

		String typesOfLeaves[] = leaveAttendanceReportService.getTypesOfLeaves(companyId);
		String typesOfLeavesName[] = new String[typesOfLeaves.length];
		String typesOfLeavesId[] = new String[typesOfLeaves.length];

		for (int i = 0; i < typesOfLeaves.length; i++) {
			String columnName[] = typesOfLeaves[i].split("/");
			typesOfLeavesName[i] = columnName[0];
			typesOfLeavesId[i] = columnName[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		List<String> finalColumns = new ArrayList<>();

// for(String value:list) {
		for (String value : typesOfLeavesName) {
			finalColumns.add(value.concat("-Carryforward"));
			finalColumns.add(value.concat("-Consumed"));
			finalColumns.add(value.concat("-Balance"));
			finalColumns.add(value.concat("-AnnualQuota"));

		}

		String typesOfLeavesEntitlement[] = leaveAttendanceReportService.getTypesOfLeavesEntitlement(companyId);
		String typesOfLeavesNameEntitlement[] = new String[typesOfLeavesEntitlement.length];
		String typesOfLeavesIdEntitlement[] = new String[typesOfLeavesEntitlement.length];

		for (int i = 0; i < typesOfLeavesEntitlement.length; i++) {
			String columnName[] = typesOfLeavesEntitlement[i].split("/");
			typesOfLeavesNameEntitlement[i] = columnName[0];
			typesOfLeavesIdEntitlement[i] = columnName[1];

		}

		List<String> finalColumnsEntitlement = new ArrayList<>();
		for (int i = 0; i < typesOfLeavesIdEntitlement.length; i++) {
			if (typesOfLeavesIdEntitlement[i].equals("6")) {
				finalColumnsEntitlement.add("LOP-Consumed");
			} else if (typesOfLeavesIdEntitlement[i].equals("7")) {
				finalColumnsEntitlement.add("Compensantory Off-Consumed");
				finalColumnsEntitlement.add("Compensantory Off-Balance");
			}
		}

		list.addAll(finalColumns);
		list.addAll(finalColumnsEntitlement);
		Object[] newColumns = list.toArray();

		List<TMSLeavePeriod> leavePeriodList = leaveAttendanceReportService.getLeavePeriod(companyId);

		List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportService.getLeaveEntitlementAndBalanceSummaryReport(
				companyId, employeeId, departmentList, typesOfLeavesId, leavePeriodId, typesOfLeavesIdEntitlement,
				leavePeriodList);

//TMSLeavePeriod tmsLeavePeriod = leaveAttendanceReportService.getLeavePeriodId(companyId);

		Date startDate = new Date();
		Date endDate = new Date();
		for (TMSLeavePeriod tmsLeavePeriodSession : leavePeriodList) {
			Long tmsLeavePeriodId = tmsLeavePeriodSession.getLeavePeriodId();
			if (tmsLeavePeriodId.equals(leavePeriodId)) {
				startDate = tmsLeavePeriodSession.getStartDate();
				endDate = tmsLeavePeriodSession.getEndDate();
			}
		}

// logger.info("leaveEntitlementList is end :" + attendanceDTOList);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=leaveEntitlementReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.leaveEntitlementAndBalanceSummaryWriter(newColumns,
						attendanceDTOList, startDate, endDate, typesOfLeavesId, typesOfLeavesIdEntitlement);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public List<TMSLeavePeriodDTO> getLeavePeriod(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		List<TMSLeavePeriod> leavePeriodList = leaveAttendanceReportService.getLeavePeriod(companyId);
		List<TMSLeavePeriodDTO> leavePeriodDtoList = leaveAttendanceReportAdapator
				.databaseModelToUiDtoList(leavePeriodList);
		logger.info("leavePeriodList is end :" + "shiftDtoList" + leavePeriodDtoList);
		return leavePeriodDtoList;

	}

	/**
	 * @author ${Mayuri}
	 *
	 */
// Leave Request Report
	@RequestMapping(path = "/leaveRequestSummary", method = RequestMethod.GET)
	public void generateleaveRequestSummaryReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Code", "Employee", "Department", "Designation", "Job Location", "Reporting Manager",
				"Requested On", "Leave Type", "Duration", "Days", "Requester Remarks", "Status", "Action Taken By",
				"Action Taken On", "Actioner Remarks" };

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);
		List<Object[]> leaveRequestList = leaveAttendanceReportService.getLeaveRequestSummaryReport(companyId,
				employeeId, departmentList, fDate, tDate);

		List<LeaveEntryDTO> leaveRequestDtoList = leaveAttendanceReportAdapator
				.objectListToLeaveRequestReport(leaveRequestList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=LeaveRequestReport.xlsx");

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.leaveRequestExcelWriter(columns, leaveRequestDtoList,
						fDate, tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else {
				throw new ErrorHandling("Invalid session-->Please login again");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
// Comp off request summary report
	@RequestMapping(path = "/compOffReqSummaryReport", method = RequestMethod.GET)
	public void CompOffReqSummaryReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, @RequestParam String fromDate,
			@RequestParam String toDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		String[] columns = { "Employee Code", "Employee", "Department", "Designation", "Job Location",
				"Reporting Manager", "Requested On", "Duration", "Days", "Request Remarks", "Status", "Action Taken By",
				"Action Taken On", "Actioner Remarks" };

		List<Object[]> compOffReqSummaryObj = leaveAttendanceReportService.getCompOffReqSummaryReport(companyId,
				employeeId, departmentIds, fDate, tDate);
		List<CompensatoryOffDTO> compensatoryOffList = leaveAttendanceReportAdapator
				.objectListToCompOffReqSummaryReport(compOffReqSummaryObj);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=CompOffRequestSummary.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.compOffReqSummaryReportWriter(compensatoryOffList,
						columns, fDate, tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@RequestMapping(value = "/earlyComersReport", method = RequestMethod.GET)
	public void earlyComersReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException, InvalidFormatException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<Object[]> earlyComersObj = leaveAttendanceReportService.getEarlyComersReport(companyId, employeeId,
				departmentIds, fDate, tDate);

		List<AttendanceLogDTO> attendanceLogDTOList = leaveAttendanceReportAdapator
				.objectListToEarlyComersReport(earlyComersObj);

		String[] columns = { " Date ", " Employee Code ", " Employee ", " Department ", " Designation ",
				" Job Location ", " Reporting Manager ", " Shift ", " Shift Duration ", " Time in ", " Time Out ",
				"Total Hours ", " Early Before ", " Attendance Status " };

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Early Comers Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId > 0) {
				Workbook workbook = AttendanceReportExcelWriter.earlyComersReport(attendanceLogDTOList, columns, fDate,
						tDate);

				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
// Worked On Holidays Report
	@RequestMapping(value = "/workedOnHolidaysReport", method = RequestMethod.GET)
	public void workedOnHolidaysReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException, InvalidFormatException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		String[] columns = { " Date ", " Employee Code ", " Employee ", " Department ", " Designation ",
				" Job Location ", " Reporting Manager ", " Shift ", " Shift Duration ", " Time In ", " Time Out ",
				" Total Hours ", " Attendance Status " };

		List<Object[]> workedOnHolidaysObj = leaveAttendanceReportService.getWorkedOnHolidaysReport(companyId,
				employeeId, departmentIds, fDate, tDate);

		List<Object[]> workedOnWeekOffObj = leaveAttendanceReportService.getWorkedOnWeekOffReport(companyId, employeeId,
				departmentIds, fDate, tDate);

		List<AttendanceLogDTO> attendanceHolidayList = leaveAttendanceReportAdapator
				.objectListToWorkedOnHolidaysReport(workedOnHolidaysObj);

		List<AttendanceLogDTO> attendanceWeekOffList = leaveAttendanceReportAdapator
				.objectListToWorkedOnWeekOffReport(workedOnWeekOffObj);

		List<AttendanceLogDTO> newAttendanceLogDTOList = new ArrayList<AttendanceLogDTO>(attendanceHolidayList);
		newAttendanceLogDTOList.addAll(attendanceWeekOffList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Worked On Holidays Details Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId > 0) {
				Workbook workbook = AttendanceReportExcelWriter.WorkedOnHolidaysDetailsReport(newAttendanceLogDTOList,
						columns, fDate, tDate);

				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author ${Jaiswal}
	 *
	 */
// Early Leavers Details

	@RequestMapping(value = "/earlyLeaversReport", method = RequestMethod.GET)
	public void earlyLeaversReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		String[] columns = { " Date ", " Code ", "Employee Name", " Department ", " Designation ", "Job Location",
				"Reporting Manager", " Shift ", "Shift Duration", "Time In", "Time Out", "Total Hours ", "Early By",
				"Attendance Status" };

		List<Object[]> earlyLeaversObjectList = leaveAttendanceReportService.getEarlyLeaversReport(companyId,
				employeeId, departmentIds, fDate, tDate);

		List<AttendanceLogDTO> attendanceLogDtoList = leaveAttendanceReportAdapator
				.objectListToEarlyLeaversList(earlyLeaversObjectList);

		if (companyId != null) {
			try {
				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=Early Leavers Report.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

				Workbook workbook = AttendanceReportExcelWriter.earlyLeaversReportWriter(attendanceLogDtoList, columns,
						fDate, tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				logger.info("after generate Early Leavers report ");
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}

		}

	}

	/**
	 * @author ${Jaiswal}
	 *
	 */
	@RequestMapping(path = "/arSummaryReport", method = RequestMethod.GET)
	public void arRequestReport(@RequestParam Long companyId, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentList, @RequestParam String fromDate,
			@RequestParam String toDate, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		String[] columns = { " Code ", " Employee ", " Department ", " Designation ", "Job Location",
				"Reporting Manager", "Requested On", " Reason ", " Duration ", "Days", "Requester Remarks", " Status ",
				"Action Taken By", "Action Taken On", " Actioner Remarks " };

		List<Object[]> arRequestObjectList = leaveAttendanceReportService.getArRequestReport(companyId, employeeId,
				departmentList, fDate, tDate);
		List<AttendanceRegularizationRequestDTO> arRequestDTOList = leaveAttendanceReportAdapator
				.objectListToAttendanceRegularizationRequestDtoList(arRequestObjectList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=AR Summary Report.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.arRequestReportWriter(columns, arRequestDTOList, fDate,
						tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */

	@RequestMapping(path = "/leaveExpirySummary", method = RequestMethod.GET)
	public void generateLeaveExpirySummaryReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam Long leavePeriodId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Code", "Employee", " Department ", " Designation ", " Job Location ",
				" Reporting Manager " };

		String typesOfExpiryLeaves[] = leaveAttendanceReportService.getTypesOfExpiryLeaves(companyId);
		String typesOfLeavesExpiryName[] = new String[typesOfExpiryLeaves.length];
		String typesOfLeavesExpiryId[] = new String[typesOfExpiryLeaves.length];

		for (int i = 0; i < typesOfExpiryLeaves.length; i++) {
			String columnName[] = typesOfExpiryLeaves[i].split("/");
			typesOfLeavesExpiryName[i] = columnName[0];
			typesOfLeavesExpiryId[i] = columnName[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(typesOfLeavesExpiryName));

		Object[] newColumns = list.toArray();

		TMSLeavePeriod tmsLeavePeriod = leaveAttendanceReportService.getEncashedLeavePeriodId(companyId, leavePeriodId);

		List<AttendanceDTO> leaveExpiryList = leaveAttendanceReportService.getLeaveExpirySummaryReport(companyId,
				employeeId, departmentList, typesOfLeavesExpiryId, leavePeriodId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=leaveExpiryReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.leaveExpirySummaryExcelWriter(newColumns,
						leaveExpiryList, tmsLeavePeriod.getStartDate(), tmsLeavePeriod.getEndDate(),
						typesOfLeavesExpiryId);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// shift Schedule Summary report
	@RequestMapping(value = "/shiftScheduleSummary", method = RequestMethod.GET)
	public void shiftScheduleSummaryReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentIds,
			HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, ParseException, IOException {

		logger.info("start generate Shift Schedule Summary report ");

		String[] columns = { "   Code   ", " Employee ", " Department ", " Designation ", "Job Location",
				"Reporting Manager", "  Shift Name  ", "Shift Timing" };

		List<Object[]> otDetailsObjectList = leaveAttendanceReportService.getshiftScheduleSummary(companyId, employeeId,
				departmentIds);

		List<AttendanceLogDTO> attendanceLogDtoList = leaveAttendanceReportAdapator
				.objectListToShiftScheduleList(otDetailsObjectList);

		if (companyId != null) {
			try {

				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=Shift Schedule Summary Report.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

				Workbook workbook = AttendanceReportExcelWriter.shiftScheduleSummaryWriter(attendanceLogDtoList,
						columns);
				ServletOutputStream fileOut = response.getOutputStream();
				logger.info("after generate Shift Schedule Summary report ");
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}

		}

	}

	/**
	 * @author ${Jaiswal}
	 *
	 */
	// Over Time-Day Wise
	@RequestMapping(value = "/overTimeDayWiseReport", method = RequestMethod.GET)
	public void overTimeDayWiseReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		String[] columns = { "   Date   ", "   Code   ", "     Employee     ", "   Department   ", " Designation ",
				"Job Location", "Reporting Manager", "   Shift   ", "Shift Duration", " Time In ", " Time Out ",
				"Total Hours", "Over Time" };

		List<Object[]> otDetailsObjectList = leaveAttendanceReportService.getOverTimeDayWiseReport(companyId,
				employeeId, departmentIds, fDate, tDate);

		List<AttendanceLogDTO> attendanceLogDtoList = leaveAttendanceReportAdapator
				.objectListToOverTimeDayWiseList(otDetailsObjectList);

		if (companyId != null) {
			try {
				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=Over Time Day Wise Report.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

				Workbook workbook = AttendanceReportExcelWriter.overTimeDayWiseReportWriter(attendanceLogDtoList,
						columns, fDate, tDate);
				ServletOutputStream fileOut = response.getOutputStream();
				logger.info("after generate Over Time Day Wise report ");
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}

		}

	}

	/**
	 * @author ${Jaiswal}
	 *
	 */
	@RequestMapping(value = "/overTimeMonthWiseReport", method = RequestMethod.GET)
	public void overTimeMonthWiseReport(@RequestParam Long companyId, @RequestParam String processMonth,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentIds,
			HttpServletRequest req, HttpServletResponse response) throws ErrorHandling, ParseException, IOException {

		int pMonth = DateUtils.getMonthForProcessMonth(processMonth);
		int pYear = DateUtils.getYearForProcessMonth(processMonth);

		String[] columns = { "   Code   ", " Employee ", " Department ", " Designation ", "Job Location",
				"Reporting Manager", "  Shift  ", "Shift Duration", "Total Hours", "Over Time" };

		List<Object[]> otDetailsObjectList = leaveAttendanceReportService.getOverTimeMonthWiseReport(companyId, pMonth,
				pYear, employeeId, departmentIds);

		List<AttendanceLogDTO> attendanceLogDtoList = leaveAttendanceReportAdapator
				.objectListToOverTimeMonthWiseList(otDetailsObjectList);

		if (companyId != null) {
			try {
				response.setContentType(
						"application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=Over Time Month Wise Report.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

				Workbook workbook = AttendanceReportExcelWriter.overTimeMonthWiseReportWriter(attendanceLogDtoList,
						columns, pMonth, pYear);
				ServletOutputStream fileOut = response.getOutputStream();
				logger.info("after generate Over Time Month Wise report ");
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}

		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */
	// Missing Punch Record Report
	@RequestMapping(path = "/missingPunchRecords", method = RequestMethod.GET)
	public void generateMissingPunchRecordsReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Date", "Code", "Employee", "Department", "Designation", "Job Location",
				"Reporting Manager", "Shift", "Shift Duration", "Time In", "Attendance Status" };

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date startDate = inputFormat.parse(fromDate);
		Date endDate = inputFormat.parse(toDate);

		List<Object[]> missingPunchRecordList = leaveAttendanceReportService.getMissingPunchRecordReport(companyId,
				employeeId, departmentList, startDate, endDate);

		List<AttendanceLogDTO> missingPunchRecordDtoList = leaveAttendanceReportAdapator
				.objectListToMissingPunchRecordList(missingPunchRecordList);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=MissingCheckIn_OutReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.missingCheckIn_OutExcelWriter(columns,
						missingPunchRecordDtoList, startDate, endDate);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else {
				throw new ErrorHandling("Invalid session-->Please login again");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}

	}

	/**
	 * @author ${Mayuri}
	 *
	 */
	// Leave Encashed Summary Report

	@RequestMapping(path = "/leaveEncashedSummary", method = RequestMethod.GET)
	public void generateLeaveEncashedSummaryReport(@RequestParam Long companyId,
			@RequestParam(required = false) Long employeeId, @RequestParam(required = false) List<Long> departmentList,
			@RequestParam Long leavePeriodId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, ParseException, InvalidFormatException, IOException {

		String[] columns = { "Code", "Employee", "   Department  ", "   Designation   ", "  Job Location  ",
				"    Reporting Manager  " };
		String[] total = { "       Total  " };

		String typesOfLeaves[] = leaveAttendanceReportService.getTypesOfEncashedLeaves(companyId);
		String typesOfLeavesName[] = new String[typesOfLeaves.length];
		String typesOfLeavesId[] = new String[typesOfLeaves.length];

		for (int i = 0; i < typesOfLeaves.length; i++) {
			String columnName[] = typesOfLeaves[i].split("/");
			typesOfLeavesName[i] = columnName[0];
			typesOfLeavesId[i] = columnName[1];
		}

		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(typesOfLeavesName));
		list.addAll(Arrays.asList(total));
		list.addAll(Arrays.asList(typesOfLeavesName));
		list.addAll(Arrays.asList(total));

		int leavesSize = typesOfLeavesName.length + 1;
		// int listSize=list.size();

		Object[] newColumns = list.toArray();

		TMSLeavePeriod tmsLeavePeriod = leaveAttendanceReportService.getEncashedLeavePeriodId(companyId, leavePeriodId);
		List<AttendanceDTO> leaveEncashedList = leaveAttendanceReportService.getLeaveEncashedSummaryReport(companyId,
				employeeId, departmentList, typesOfLeavesId, leavePeriodId);

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=leaveEncashedReport.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId != null) {
				Workbook workbook = AttendanceReportExcelWriter.leaveEncashedSummaryExcelWriter(newColumns,
						leaveEncashedList, tmsLeavePeriod.getStartDate(), tmsLeavePeriod.getEndDate(), typesOfLeavesId,
						leavesSize);
				ServletOutputStream fileOut = response.getOutputStream();
				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@RequestMapping(path = "/leaveEncashed/{companyId}", method = RequestMethod.GET)
	public List<TMSLeavePeriodDTO> getEncashedLeavePeriod(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		List<TMSLeavePeriod> leavePeriodList = leaveAttendanceReportService.getEncashedLeavePeriod(companyId);
		List<TMSLeavePeriodDTO> leavePeriodDtoList = leaveAttendanceReportAdapator
				.databaseModelToUiDtoList(leavePeriodList);
		return leavePeriodDtoList;

	}

	/**
	 * @author ${Nisha}
	 *
	 */
	// AttendanceLogs Summary
	@RequestMapping(value = "/attendanceLogsSumReport", method = RequestMethod.GET)
	public void attendanceLogsSumReport(@RequestParam Long companyId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) List<Long> departmentIds, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, ParseException, IOException, InvalidFormatException {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<Object[]> attendanceLogsSumObj = leaveAttendanceReportService.getAttendanceLogsSumReport(companyId,
				employeeId, departmentIds, fDate, tDate);

		List<AttendanceLogDTO> attendanceLogDTOList = leaveAttendanceReportAdapator
				.objectListToAttendanceLogsSumReport(attendanceLogsSumObj);

		String[] columns = { "  Date  ", "  Employee Code  ", "  Employee  ", "  Department  ", "  Designation  ",
				"  Job Location  ", "  Reporting Manager  ", "  Shift  ", "  Shift Duration  ", "  Punching Mode  ",
				"  Time In  ", "  Time Out  ", "  Total Hours  ", "  Attendance Status  ", "  Late By  ",
				"  Early By  ", "  Early Before  ", "  Location-Time In  ", "  Location-Time Out  " };

		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=Attendance Logs.xlsx");
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			if (companyId > 0) {

				Workbook workbook = AttendanceReportExcelWriter.attendanceLogsSumReportWriter(attendanceLogDTOList,
						columns, fDate, tDate);

				ServletOutputStream fileOut = response.getOutputStream();

				workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}