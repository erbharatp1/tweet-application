package com.csipl.tms.leave.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.dto.report.Data;
import com.csipl.hrms.dto.report.LeaveChart;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.tms.attendanceregularizationrequest.service.AttendanceRegularizationRequestService;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.dto.leave.LeaveEntryChartDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.dto.monthattendance.LeaveChartEnum;
import com.csipl.tms.empcommondetail.service.EmpCommonDetailService;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.adaptor.TMSLeaveEarnAdaptor;
import com.csipl.tms.leave.service.EmployeeLeavePaginationService;
import com.csipl.tms.leave.service.EmployeeLeaveService;
import com.csipl.tms.leave.service.LeaveValidationResult;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;
import com.csipl.tms.model.leave.LeaveSearchDTO;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hrms.org.payrollprocess.dto.PayrollInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(description = "Operations pertaining to Leave in Time Management")
@RequestMapping("/leaveApply")
public class EmployeeLeaveController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeLeaveController.class);

	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
	TMSLeaveEarnAdaptor tmsLeaveEarnAdaptor = new TMSLeaveEarnAdaptor();

	@Autowired
	EmployeeLeaveService employeeLeaveService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	EmpCommonDetailService empCommonDetailService;

	@Autowired
	AttendanceRegularizationRequestService attendanceRegularizationRequestService;

	@Autowired
	EmployeeLeavePaginationService employeeLeavePaginationService;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "You have already applied Leave in the given duration") })

	@ApiOperation(value = "Save or Update LeaveEntry")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody TMSLeaveEntry saveLeaveEntry(@RequestBody LeaveBalanceSummryDTO leaveBalanceSummryDto)
			throws ParseException, ErrorHandling {
		Employee employeeEmp = null;
		boolean validateLeave;
		logger.info("saveLeaveEntry is calling : leaveEntryDto " + leaveBalanceSummryDto.toString());

		LocalDate start = leaveBalanceSummryDto.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = leaveBalanceSummryDto.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		List<LocalDate> totalDates = new ArrayList<>();
		List<String> leaveDates = new ArrayList<>();

		while (!start.isAfter(end)) {
			totalDates.add(start);
			leaveDates.add(start.toString());
			start = start.plusDays(1);
		}

		List<String> dbArRequest = new ArrayList<>();
		List<Date> totalDatesAr = new ArrayList<Date>();
		List<String> totalDatesArstr = new ArrayList<String>();
		List<AttendanceRegularizationRequest> arRequestList = attendanceRegularizationRequestService
				.getPenAprARRequestList(leaveBalanceSummryDto.getEmployeeId());

		arRequestList.forEach(item -> {
			Calendar calendar = new GregorianCalendar();
			if (item != null && item.getFromDate() != null) {
				calendar.setTime(item.getFromDate());
				LocalDate aRDate = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				totalDatesArstr.add(aRDate.toString());
				while (calendar.getTime().before(item.getToDate())) {
					Date result = calendar.getTime();
					totalDatesAr.add(result);
					LocalDate arDate = result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					totalDatesArstr.add(arDate.toString());
					calendar.add(Calendar.DATE, 1);
				}
			}

		});

		totalDatesArstr.retainAll(leaveDates);

		if (totalDatesArstr.size() > 0 && leaveBalanceSummryDto.getStatus().equalsIgnoreCase("PEN")) {
			throw new ErrorHandling(
					"You cannot apply Leave request because you have already applied AR in this duration");
		}

//			if(leaveBalanceSummryDto.getApprovalId()==null)
//			validateLeave= employeeLeaveService.validateLeaveEntry(leaveBalanceSummryDto);
		if (leaveBalanceSummryDto.getLeaveId() == null) {
			validateLeave = employeeLeaveService.validateLeaveEntry(leaveBalanceSummryDto);
			logger.info("Inside If is calling :  =========>>>>>>>> " + leaveBalanceSummryDto.toString());
		} else if (leaveBalanceSummryDto.getLeaveId() != null && !leaveBalanceSummryDto.getStatus().equals("CEN")
				&& leaveBalanceSummryDto.getApprovalId() == null) {
			validateLeave = employeeLeaveService.validateLeaveEntry(leaveBalanceSummryDto);
		}
		TMSLeaveEntry leaveEntry = leaveEntryAdaptor.uiDtoToDatabaseModel(leaveBalanceSummryDto);
		return employeeLeaveService.saveLeaveEntry(leaveEntry, leaveBalanceSummryDto);

	}

	/**
	 * @param leaveId to get leaveEntrie object from database based on leavId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@RequestMapping(value = "/{leaveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LeaveEntryDTO getLeaveEntry(@PathVariable("leaveId") Long leaveId) {

		logger.info("getLeaveEntry is calling : leaveId =>>" + leaveId);
		LeaveEntryDTO leaveEntryDto = employeeLeaveService.getLeaveEntry(leaveId);
		logger.info("getLeaveEntry is end   leaveEntry=>>:" + leaveEntryDto.toString());

		// logger.info("getLeaveEntry employeeDto---->" + employeeEmp.toString());
		return leaveEntryDto;
	}

	/**
	 * @param employeeId to get all leaveEntries object from database based on
	 *                   employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View List of LeaveEntry  of Employee based on employeeId ")
	@RequestMapping(value = "/employeeLeaveEntry/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getEmployeeLeaveEntry(@PathVariable("employeeId") Long employeeId) {
		logger.info("getEmployeeLeaveEntry is calling : employeeId =>>" + employeeId);
		List<TMSLeaveEntry> employeeLeaveEntryList = employeeLeaveService.getEmployeeLeaveEntry(employeeId);
		return leaveEntryAdaptor.databaseModelToUiDtoList(employeeLeaveEntryList);
	}

	/**
	 * @param employeeId to get all leaveEntries object from database based on
	 *                   employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View  Pending LeaveEntry List of Employee based on employee Id")
	@RequestMapping(value = "/employeePendingLeaveEntry/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getEmployeePendingLeaveEntry(@PathVariable("employeeId") Long employeeId) {
		logger.info("getEmployeeLeaveEntry is calling : employeeId =>>" + employeeId);
		List<TMSLeaveEntry> employeeLeaveEntryList = employeeLeaveService.getEmployeePendingLeaveEntry(employeeId);
		// List<TMSLeaveEntriesDatewise> employeeLeaveEntryList =
		// employeeLeaveService.getEmployeePendingLeaveEntry(employeeId);
		return leaveEntryAdaptor.databaseModelToUiDtoList(employeeLeaveEntryList);
	}

	/**
	 * @param companyId to get all leaveEntries from database based on companyId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View List of leaveEntries based on company ID")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getleaveEntryList(@RequestParam("companyId") Long companyId) {
		logger.info(" getleaveEntryList is calling companyId >>:" + companyId);
		List<Object[]> objLeaveEntryList = employeeLeaveService.leaveEntryList(companyId);
		return leaveEntryAdaptor.databaseObjToUiDtoList(objLeaveEntryList);
	}

	/**
	 * @param employeId to get all leave balance calculation based on employeeId
	 */
	/*
	 * @ApiResponses(value = { @ApiResponse(code = 200, message =
	 * "Successfully retrieved data"),
	 * 
	 * @ApiResponse(code = 401, message =
	 * "You are not authorized to view the resource"),
	 * 
	 * @ApiResponse(code = 403, message =
	 * "Accessing the resource you were trying to reach is forbidden"),
	 * 
	 * @ApiResponse(code = 404, message =
	 * "The resource you were trying to reach is not found") })
	 * 
	 * @ApiOperation(value =
	 * "View List of leave Balance List of Employee based on employee ID")
	 * 
	 * @RequestMapping(value = "/leaveBalance/{employeeId}", method =
	 * RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 * public @ResponseBody List<LeaveBalanceDTO> getEmployeeLeaveBalanceList(
	 * 
	 * @PathVariable("employeeId") Long employeeId) {
	 * logger.info("getEmployeeLeaveBalanceList employeeId--->" + employeeId);
	 * return employeeLeaveService.getEmployeeLeaveBalance(employeeId); }
	 */
	/**
	 * @param leaveEntryDto to get actual leave applied days based on leave apply
	 *                      from date and leave apply to date
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "get actual leave applied days ")
	@RequestMapping(value = "/appliedLeaveDays", method = RequestMethod.POST)
	public String appliedLeaveDays(@RequestBody LeaveEntryDTO leaveEntryDto) throws ParseException, ErrorHandling {
		logger.info("appliedLeaveDays is calling : leaveEntryDto " + leaveEntryDto.toString());
		String leaveValidationResultJson = employeeLeaveService.appliedLeaveDays(leaveEntryDto);

		return leaveValidationResultJson;
	}

	/**
	 * @param employeeId to get employee object based on employee id
	 */
	/*
	 * private EmployeeDTO getEmployeeByRestTamplate(String employeeId) {
	 * logger.info("Employee Id----->" + employeeId);
	 * logger.info("getEmployeeBy RestTamplate is calling : "); String url =
	 * "http://localhost:8080/hrmsApi/employee/{employeeId}"; RestTemplate
	 * restTemplate = new RestTemplate(); HttpHeaders header = new HttpHeaders();
	 * header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); Map<String,
	 * String> params = new HashMap<>(); params.put("employeeId", employeeId);
	 * return restTemplate.getForObject(url, EmployeeDTO.class, params); }
	 */

	/**
	 * @param employeeId to get all leaveEntries object from database based on
	 *                   employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View  Approved or rejected LeaveEntry List of Employee based on employee Id")
	@RequestMapping(value = "/empLeaveEntry/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getEmployeeApprovedLeaveEntry(
			@PathVariable("employeeId") Long employeeId) {
		logger.info("getEmployeeLeaveEntry is calling : employeeId =>>" + employeeId);
		List<TMSLeaveEntry> employeeLeaveEntryList = employeeLeaveService.getEmployeeApprovedLeaveEntry(employeeId);
		return leaveEntryAdaptor.databaseModelToUiDtoList(employeeLeaveEntryList);
	}

	/**
	 * @param employeeId to get all leaveEntries object from database based on
	 *                   employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View  Approved or rejected LeaveEntry List of Employee based on company Id")
	@RequestMapping(value = "/allEmpLeaveEntry/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getAllEmployeeApprovedLeaveEntry(
			@PathVariable("companyId") Long companyId) {
		logger.info("getEmployeeLeaveEntry is calling : companyId =>>" + companyId);
		List<TMSLeaveEntry> employeeLeaveEntryList = employeeLeaveService.getAllEmployeeApprovedLeaveEntry(companyId);
		return leaveEntryAdaptor.databaseModelToUiDtoList(employeeLeaveEntryList);
	}

	/**
	 * @param employeeId,companyId to get count of all pending status of
	 *                             LeaveEntryDTO from database based on companyId
	 *                             and employeeId
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View  count of leaveEntry based on company Id and employeeId")
	@RequestMapping(value = "/count/{companyId}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LeaveEntryDTO getLeaveCount(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("leaveCount is calling : companyId =>>" + companyId);
		return employeeLeaveService.leaveCount(companyId, employeeId);
	}

	@GetMapping(value = "/entitycount/{companyId}/{pageSize}")
	public @ResponseBody EntityCountDTO getAllEmployeeCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int count;
		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		employeeLeavePaginationService.getEntityCount(longCompanyId, entityCountDto);
		count = entityCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@GetMapping(value = "/pendingEntitycount/{companyId}/{pageSize}")
	public @ResponseBody EntityCountDTO getPendingEmployeeCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int count;
		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		employeeLeavePaginationService.getPendingEntityCount(longCompanyId, entityCountDto);
		count = entityCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	/**
	 * to get all employees List from data base based of company
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = "/searchEntity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LeaveEntryDTO> searchPendingEmployeeLeaveDetails(@RequestBody LeaveSearchDTO leavesearchDto)
			throws PayRollProcessException {
		logger.info(" active employees is calling :");

		List<Object[]> employeeLeaveList = employeeLeavePaginationService
				.getPendingEmployeeLeavebyPagination(leavesearchDto.getCompanyId(), leavesearchDto);

		List<LeaveEntryDTO> leaveEntryDtoList = leaveEntryAdaptor.modeltoDTOList(employeeLeaveList, leavesearchDto);
		return leaveEntryDtoList;

	}

	/**
	 * @param employeId to get all leave balance summry based on employeeId
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View List of leave Balance List of Employee based on employee ID")
	@RequestMapping(value = "/leaveBalance/{employeeId}/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<LeaveBalanceSummryDTO> getEmployeeLeaveBalanceSummryList(
			@PathVariable("employeeId") Long employeeId, @PathVariable("companyId") Long companyId) {
		logger.info("getEmployeeLeaveBalanceList employeeId--->" + employeeId);

		return employeeLeaveService.getEmployeeLeaveBalanceSummry(employeeId, companyId);
	}

	/**
	 * @param employeId to get all team leave of all month based on employeeId and
	 *                  current date
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View List of leave Balance List of Employee based on employee ID")

	@RequestMapping(value = "/teamonleave/{employeeId}/{currentDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<TeamLeaveOnCalenderDTO> TeamOnLeave(@PathVariable("employeeId") String employeeId,
			@PathVariable("currentDate") String currentDate) {
		logger.info("getEmployeeLeaveBalanceList employeeId--->" + employeeId);
		logger.info("getEmployeeLeaveBalanceList employeeId--->" + currentDate);

		return employeeLeaveService.getTeamLeaveOnCalender(employeeId, currentDate);
	}

	@RequestMapping(value = "/teamonleaveNew/{employeeId}/{currentDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String teamonleaveNew(@PathVariable("employeeId") String employeeId,
			@PathVariable("currentDate") String currentDate) throws ParseException {
		logger.info("getEmployeeLeaveBalanceList employeeId--->" + employeeId);
		logger.info("getEmployeeLeaveBalanceList employeeId--->" + currentDate);
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy ");
		Date date = inputFormat.parse(currentDate);
		int year = DateUtils.getYear(date);
		int month = DateUtils.getMonth(date);

		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		String monthAcronym = shortMonths[month - 1];
		String processMonth = monthAcronym.toUpperCase() + "-" + String.valueOf(year);
		Long empId = Long.valueOf(employeeId);
		TeamLeaveOnCalenderDTO list = employeeLeaveService.getTeamLeaveOnCalenderNew(empId, processMonth);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setDateFormat(df);
		objectMapper.setTimeZone(TimeZone.getDefault());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String dateWiseResultJson = null;
		try {
			dateWiseResultJson = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateWiseResultJson;
	}

	@RequestMapping(path = "/leaveWiseRatio/{employeeId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody LeaveEntryChartDTO empleaveWiseRatio(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {

		List<LeaveBalanceSummryDTO> leaveBlanceList = employeeLeaveService.getEmployeeLeaveBalanceSummry(employeeId,
				companyId);
		logger.info("empleaveWiseRatio employeeId--->" + employeeId);
		BigDecimal sumBalanceLeave = new BigDecimal(0), sumConsumedLeave = new BigDecimal(0);
		for (LeaveBalanceSummryDTO leaveBalanceSummryDTO : leaveBlanceList) {
			if (leaveBalanceSummryDTO.getTmsleaveTypeMasterId() != 6) {
				sumBalanceLeave = sumBalanceLeave.add(leaveBalanceSummryDTO.getLeaveBalancedCount());
				sumConsumedLeave = sumConsumedLeave.add(leaveBalanceSummryDTO.getLeaveConsumedCount());
			}

		}
		ArrayList<Data> arrayData = new ArrayList<Data>();

//		LeaveEntryChart chart = new LeaveEntryChart(LeaveChartEnum.showBorder.getPieChartValue(),
//				LeaveChartEnum.use3DLighting.getPieChartValue(), LeaveChartEnum.enableSmartLabels.getPieChartValue(),
//				LeaveChartEnum.startingAngle.getPieChartValue(), LeaveChartEnum.showLabels.getPieChartValue(),
//				LeaveChartEnum.showPercentValues.getPieChartValue(), LeaveChartEnum.showLegend.getPieChartValue(),
//				LeaveChartEnum.centerLabelBold.getPieChartValue(), LeaveChartEnum.showTooltip.getPieChartValue(),
//				LeaveChartEnum.decimals.getPieChartValue(),
//				LeaveChartEnum.useDataPlotColorForLabels.getPieChartValue(), LeaveChartEnum.theme.getPieChartValue(),
//				LeaveChartEnum.chartLeftMargin.getPieChartValue(),LeaveChartEnum.chartRightMargin.getPieChartValue(),LeaveChartEnum.defaultCenterLabel.getPieChartValue(),LeaveChartEnum.centerLabelColor.getPieChartValue(),LeaveChartEnum.doughnutRadius.getPieChartValue(),LeaveChartEnum.canvasbgColor.getPieChartValue());
		LeaveChart chart = new LeaveChart(LeaveChartEnum.bgColor.getPieChartValue(),
				LeaveChartEnum.startingAngle.getPieChartValue(), LeaveChartEnum.showLegend.getPieChartValue(),
				LeaveChartEnum.showTooltip.getPieChartValue(), LeaveChartEnum.decimals.getPieChartValue(),
				LeaveChartEnum.theme.getPieChartValue(), LeaveChartEnum.pieRadius.getPieChartValue(),
				LeaveChartEnum.labelDistance.getPieChartValue(), LeaveChartEnum.showPercentValues.getPieChartValue(),
				LeaveChartEnum.showPercentInToolTip.getPieChartValue(), LeaveChartEnum.formatNumber.getPieChartValue(),
				LeaveChartEnum.formatNumberScale.getPieChartValue(), LeaveChartEnum.forceNumberScale.getPieChartValue(),
				LeaveChartEnum.numberScaleUnit.getPieChartValue(), LeaveChartEnum.smartLineColor.getPieChartValue(),
				LeaveChartEnum.labelFontColor.getPieChartValue(), LeaveChartEnum.showLabels.getPieChartValue(),
				LeaveChartEnum.legendItemFontColor.getPieChartValue());
		// for (LeaveBalanceSummryDTO empDt : leaveBlanceList) {
		logger.info("empleaveWiseRatio sumBalanceLeave.toString()--->" + sumBalanceLeave
				+ "---umConsumedLeave.toString()---" + sumConsumedLeave);
		// Data BalanceData = ;
		// Data consumedData = ;

		arrayData.add(new Data("Balance", sumBalanceLeave.toString(), "#9ba7ed"));
		arrayData.add(new Data("Consumed", sumConsumedLeave.toString(), "#575bde"));

		// }

		LeaveEntryChartDTO gt = new LeaveEntryChartDTO(chart, arrayData);

		return gt;

	}

	/*
	 * @RequestMapping(value = "/leaveAttendance/{companyId}/{processMonth}", method
	 * = RequestMethod.GET) public @ResponseBody List<TeamLeaveOnCalenderDTO>
	 * getLeaveAttendancePendingList(
	 * 
	 * @PathVariable("companyId") Long companyId,@PathVariable("processMonth")
	 * String processMonth) {
	 * logger.info("getLeaveAttendancePendingList is calling : companyId =>>" +
	 * companyId+" processMonth>>>>"+processMonth); return
	 * employeeLeavePaginationService.getLeaveAttendancePendingList(companyId,
	 * processMonth); }
	 * 
	 */

	@RequestMapping(value = "/getLeaveAttendancePendingListByDepartmentId/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PayrollInputDTO LeaveAttendancePendingListByDepartmentId(
			@RequestBody PayrollInputDTO payrollInputDTO) throws ErrorHandling {
		logger.info("LeaveAttendancePendingListByDepartmentId is calling : processMonth =>>"
				+ payrollInputDTO.getProcessMonth());
		LeaveEntryDTO leaveDto = employeeLeaveService.isPendingRequestLeaveAndARByMonth(
				DateUtils.getMonthForProcessMonth(payrollInputDTO.getProcessMonth()),
				DateUtils.getYearForProcessMonth(payrollInputDTO.getProcessMonth()));
		for (Long departmentId : payrollInputDTO.getDepartmentIds()) {
			if (leaveDto.getMap().containsKey(departmentId)) {
				throw new ErrorHandling("Payroll cannot be processed if Leave or AR request is pending of "
						+ leaveDto.getMap().get(departmentId));
			}

		}

		return payrollInputDTO;

	}

	@ApiOperation(value = "Save or Update LeaveEntry")
	@RequestMapping(value = "/actonLeaveAttendace", method = RequestMethod.POST)
	public void actonOnPendingLeaveAttendace(@RequestBody List<TeamLeaveOnCalenderDTO> actonLeaveAttendace)
			throws ParseException, ErrorHandling {
		logger.info("actonLeaveAttendace is calling : actonLeaveAttendace>>> " + actonLeaveAttendace.toString());
		employeeLeaveService.actonOnPendingLeaveAttendace(actonLeaveAttendace);

		// logger.info("saveLeaveEntry is end :" + leaveEntry.toString());
	}

	@RequestMapping(value = "/leaveAttendance/{processMonth}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TeamLeaveOnCalenderDTO> getLeaveAttendancePendingList(@RequestBody EmployeeSearchDTO employeeSearchDto,
			@PathVariable("processMonth") String processMonth) {
		logger.info("getLeaveAttendancePendingList is calling :  processMonth>>>>" + processMonth);
		return employeeLeavePaginationService.getPendingEmployeeLeaveAndAR(employeeSearchDto, processMonth);
	}

	@GetMapping(value = "/leaveAttendaceEntitycount/{count}/{pageSize}")
	public @ResponseBody EntityCountDTO leaveAttendaceEntitycount(@PathVariable("count") int count,
			@PathVariable("pageSize") String pageSize) {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		// count = entityCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View  count of leaveEntry based on company Id and employeeId")
	@GetMapping(value = "/countMyTeam/{companyId}/{employeeId}")
	public @ResponseBody LeaveEntryDTO getMyTeamLeaveCount(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("getMyTeamLeaveCount is calling : companyId =>>" + companyId);
		return employeeLeaveService.getMyTeamLeaveCount(companyId, employeeId);
	}

	@ApiOperation(value = "View  count of leaveEntry based on company Id and employeeId")
	@GetMapping(value = "/countAllTimeMyTeam/{companyId}/{employeeId}")
	public @ResponseBody LeaveEntryDTO getAllTimeMyTeamLeaveCount(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("getMyTeamLeaveCount is calling : companyId =>>" + companyId);
		return employeeLeaveService.getAllTimeMyTeamLeaveCount(companyId, employeeId);
	}

	/*
	 * View count of leaveEntry based on company Id (All employee)
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@ApiOperation(value = "View  count of leaveEntry based on company Id and employeeId")
	@GetMapping(value = "/allEmployeeLeaveCount/{companyId}")
	public @ResponseBody LeaveEntryDTO allEmployeeLeaveCount(@PathVariable("companyId") Long companyId) {
		logger.info("allEmployeeLeaveCount is calling : companyId =>>" + companyId);
		return employeeLeaveService.allEmployeeLeaveCount(companyId);
	}

	@RequestMapping(value = "/approvalsPending/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> LeaveApprovalsPending(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> leaveEntryObjList = employeeLeaveService.getLeaveApprovalsPending(companyId, employeeId);

		return leaveEntryAdaptor.objLeaveListToObjUiDtoList(leaveEntryObjList);
	}

	@RequestMapping(value = "/approvalsNonPending/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> LeaveApprovalsNonPending(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> leaveEntryObjList = employeeLeaveService.getLeaveApprovalsNonPending(companyId, employeeId);
		return leaveEntryAdaptor.objLeaveListToObjUiDtoList(leaveEntryObjList);
	}

	@RequestMapping(value = "/allApprovalsNonPending/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<LeaveEntryDTO> allLeaveApprovalsNonPending(@PathVariable("companyId") Long companyId, @RequestBody SearchDTO searcDto) {
		logger.info("allApprovalsNonPending is calling : ");
		List<Object[]> leaveEntryObjList = employeeLeaveService.getAllLeaveApprovalsNonPending(companyId, searcDto);
		return leaveEntryAdaptor.objLeaveListToObjUiDtoList(leaveEntryObjList);
	}

	@RequestMapping(value = "/leaveStatusUpdate", method = RequestMethod.POST)
	public void leaveStatusUpdate(@RequestBody LeaveEntryDTO leaveEntryDTO, HttpServletRequest req) {
		logger.info("LeaveEntryDTO is calling :  " + leaveEntryDTO);
		TMSLeaveEntry tMSLeaveEntry = new TMSLeaveEntry();
		tMSLeaveEntry.setStatus(leaveEntryDTO.getStatus());
		tMSLeaveEntry.setLeaveId(leaveEntryDTO.getLeaveId());
		tMSLeaveEntry.setApprovalId(leaveEntryDTO.getApprovalId());
		tMSLeaveEntry.setActionableDate(leaveEntryDTO.getActionableDate());
		employeeLeaveService.updateById(tMSLeaveEntry);

	}

	@RequestMapping(value = "/approvedPending/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody Long approvependingByEmpId(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("approvedPending is calling : ");

		return employeeLeaveService.getapprovependingByEmpId(companyId, employeeId);
	}

	@RequestMapping(value = "/allApprovalsPending/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<LeaveEntryDTO> allLeaveApprovalsPending(@PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) {
		List<Object[]> leaveEntryObjList = employeeLeaveService.getAllLeaveApprovalsPending(companyId, searcDto);

		return leaveEntryAdaptor.objLeaveListToObjUiDtoList(leaveEntryObjList);
	}

	@RequestMapping(value = "/employeeAllTypeLeaveEntry/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<LeaveEntryDTO> getEmployeeAllLeaveEntry(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("getEmployeesAllTypeLeaveEntry is calling : employeeId =>>" + employeeId);
		return employeeLeaveService.getEmployeesAllTypeLeaveEntry(companyId, employeeId);
	}

	
	
	@ApiOperation(value = "Save or Update LeaveEntry")
	@RequestMapping(value = "/bulkLeaveEntry" ,method = RequestMethod.POST)
	public  @ResponseBody void saveLeaveEntryBulk(@RequestBody List<LeaveBalanceSummryDTO> leaveBalanceSummry) throws ParseException, ErrorHandling {
		Employee employeeEmp=null;
		boolean validateLeave=false;
//		logger.info("saveLeaveEntry is calling : leaveEntryDto " + leaveBalanceSummryDto.toString());
	
			List<String> messageList =	new ArrayList<String>();
		
		for(LeaveBalanceSummryDTO leaveBalanceSummryDto :leaveBalanceSummry) {
			LocalDate start = leaveBalanceSummryDto.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate end = leaveBalanceSummryDto.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			List<LocalDate> totalDates = new ArrayList<>();
			while (!start.isAfter(end)) {
			    totalDates.add(start);
			    start = start.plusDays(1);
			}
			List<String> leaveDates = new ArrayList<>();
			totalDates.forEach(item -> {
				leaveDates.add(item.toString());
			});
			List<String> dbArRequest = new ArrayList<>();
			
			List<AttendanceRegularizationRequest> arRequestList=attendanceRegularizationRequestService.getEmployeeARRequestList(leaveBalanceSummryDto.getEmployeeId());
		//	List<LocalDate> totalDatesAr = new ArrayList<>();
			List<Date> totalDatesAr = new ArrayList<Date>();
			arRequestList.forEach(item -> {

				    Calendar calendar = new GregorianCalendar();
				    calendar.setTime(item.getFromDate());

				    while (calendar.getTime().before(item.getToDate()))
				    {
				        Date result = calendar.getTime();
				        totalDatesAr.add(result);
				        calendar.add(Calendar.DATE, 1);
				    } 
			});
			totalDatesAr.forEach(item -> {
				dbArRequest.add(item.toString());
			});
			dbArRequest.retainAll(leaveDates);
			if (dbArRequest.size() > 0) {
				throw new ErrorHandling("You cannot apply Leave request");
			}
			 
			else {
 
				if(leaveBalanceSummryDto.getLeaveId()==null) {
					validateLeave= employeeLeaveService.validateBulkLeaveEntry(leaveBalanceSummryDto,messageList);
//					logger.info("Inside If is calling :  =========>>>>>>>> " + leaveBalanceSummryDto.toString());
				}
				else if(leaveBalanceSummryDto.getLeaveId()!=null  && ! leaveBalanceSummryDto.getStatus().equals("CEN") && leaveBalanceSummryDto.getApprovalId()==null) {
					validateLeave= employeeLeaveService.validateBulkLeaveEntry(leaveBalanceSummryDto, messageList);
				}
//				TMSLeaveEntry leaveEntry = leaveEntryAdaptor.uiDtoToDatabaseModel(leaveBalanceSummryDto);
//				  employeeLeaveService.saveLeaveEntry(leaveEntry,leaveBalanceSummryDto);
			}

		}
		
		if(messageList.isEmpty()) {
			for(LeaveBalanceSummryDTO leaveBalanceSummryDto :leaveBalanceSummry) {
				TMSLeaveEntry leaveEntry = leaveEntryAdaptor.uiDtoToDatabaseModel(leaveBalanceSummryDto);
				employeeLeaveService.saveLeaveEntry(leaveEntry, leaveBalanceSummryDto);
			}
			
		}else {
			throw new ErrorHandling(messageList.toString());
		}
		//logger.info("saveLeaveEntry is end  :" + leaveEntry.toString());
		 
	}
	
	@RequestMapping(value = "/leaveApplyFileUpload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public List<LeaveBalanceSummryDTO> leaveApplyFileUpload(@RequestPart("uploadFile") MultipartFile file ,@RequestPart("info") LeaveBalanceSummryDTO fileLeaveBalanceSummryDto )
			throws ErrorHandling, Exception {
		logger.info("saveOneTimeDeductionFile is calling : " + " : file " + file);
		Employee employeeEmp = null;
		boolean validateLeave = false;
		// logger.info("saveLeaveEntry is calling : leaveEntryDto " +
		// leaveBalanceSummryDto.toString());

		List<String> validateBulkLeaveEntry = new ArrayList<String>();
		AppUtils util = new AppUtils();
		List<String> listEmpCode = new ArrayList<>();
		List<String> DateValidationEmpCode = new ArrayList<>();
		List<String> ARAppliedEmpCode = new ArrayList<>();
		List<String> leaveSummaryNotFound = new ArrayList<>();
		List<String> listEmpInpayrollCode = new ArrayList<>();
		List<Long> listPayheadIdList = new ArrayList<>();
		List<Long> listPayheadIdDB = new ArrayList<>();
		List<String> listDB = new ArrayList<>();
		List<String> listEmpCodeDB = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
	 
		HashMap<String, Employee> mapEmpList = new HashMap<String, Employee>();
		List<LeaveBalanceSummryDTO> leaveBalanceSummryList = new ArrayList<>();
		Map<String, LeaveEntryDTO> leaveEntryMap = new HashMap<>();
		List<Employee> allEmployeeList  = employeePersonalInformationService.fetchEmployee(fileLeaveBalanceSummryDto.getCompanyId());
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<>();
		listDB = AppUtils.getEmployeeCode(allEmployeeList);
//		listEmpInpayrollCode =  AppUtils.getEmployeeCode(employeeList);
		for (Employee employee : allEmployeeList) {
			mapEmpList.put(employee.getEmployeeCode(), employee);
		}
		
		// ecxel data reading
		Workbook workbook = WorkbookFactory.create(AppUtils.createXLSFile(file));
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
		   FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		int i = 0;
		for (Row row : sheet) {
			int j = 0;
			LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
			leaveEntryDto.setCompanyId(fileLeaveBalanceSummryDto.getCompanyId());
			String cellEmpCode = null;
			Date fromDate = null;
			Date toDate = null;
			String halfFullDay =null;
			Long leaveTypeId= null;
			for (Cell cell : row) {
				if (i > 0) {
					
					try {
					
					if (j == 0) {
						cellEmpCode = dataFormatter.formatCellValue(cell);
						listEmpCode.add(cellEmpCode);
						leaveEntryDto.setEmployeeCode(cellEmpCode);
					}
					if (j == 1) {
						String cellValue =  dataFormatter.formatCellValue(cell);
						leaveEntryDto.setEmployeeName(cellValue);
					}
					if (j == 2) {
						String cellValue = dataFormatter.formatCellValue(cell);
						leaveEntryDto.setLeaveType(cellValue);
					}

					if (j == 3) {
						Long cellValue = Long.parseLong(dataFormatter.formatCellValue(cell));
						leaveTypeId=cellValue;
						leaveEntryDto.setLeaveTypeId(cellValue);
					}
					if (j == 4) {
						String cellValue = dataFormatter.formatCellValue(cell);
						halfFullDay	=cellValue;
						leaveEntryDto.setHalf_fullDay(cellValue);
						 
					}
					if (j == 5) {
//						String cellValue1  =   dataFormatter.formatCellValue(cell);
//						fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(cellValue1);
//						leaveEntryDto.setFromDate(new SimpleDateFormat("dd/MM/yyyy").parse(cellValue1)); 
					 
						if(cell != null ) { 
							CellValue cellValue = evaluator.evaluate(cell);
							if(cellValue != null) {
							 Date date= cell.getDateCellValue();
									if (!date.toString().equals("")) {
										 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
										 String dateString= DtFormat.format(date);
										 DateUtils dateUtils = new DateUtils();
										 fromDate =	dateUtils.getDateFromString(dateString);
										 leaveEntryDto.setFromDate(fromDate);
									}
								}
							}
					} 
					if (j == 6) {
						if(cell != null ) { 
							CellValue cellValue = evaluator.evaluate(cell);
							if(cellValue != null) {
							 Date date= cell.getDateCellValue();
									if (!date.toString().equals("")) {
										 SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
										 String dateString= DtFormat.format(date);
										 DateUtils dateUtils = new DateUtils();
										 toDate =	dateUtils.getDateFromString(dateString);
										 leaveEntryDto.setToDate(toDate);
										 
									}
								}
							}else {
							leaveEntryDto.setToDate(fromDate);
						}
						
					}
					if (j == 7) {
						String cellValue = dataFormatter.formatCellValue(cell);
						leaveEntryDto.setEmployeeRemark(cellValue);
					}
 					
					}catch(Exception e){
						throw new PayRollProcessException("please upload correct data");
					}
				} 

				j++;
			}
			// oneEarningDeductionList.add(onetimeEarningDeduction);
			 
			if(i>0) {
				if (fromDate != null  &&   cellEmpCode!= null && cellEmpCode!="" && halfFullDay!=null && halfFullDay!="" && leaveTypeId!=null) {
					if (toDate != null && (fromDate.compareTo(toDate) > 0)) {
						DateValidationEmpCode.add(cellEmpCode); 
					}
				}else {
					throw new PayRollProcessException("Any column in row can't be blank please upload correct data");
				}
				
//				if (halfFullDay != null && halfFullDay.equals("halfDay")) {
//					leaveEntryDto.setHalf_fullDay("H");
//					leaveEntryDto.setDays(new BigDecimal("0.5"));
//				} else if (halfFullDay != null && halfFullDay.equals("fullDay")) {
//					leaveEntryDto.setHalf_fullDay("F");
//				}
			}
			
			if(i>0) 
			leaveEntryDtoList.add(leaveEntryDto);
//			leaveEntryMap.put(cellEmpCode, leaveEntryDto);
			System.out.println();
			i++;
		}
		
		Collection<String> similarEmployeeCode = new HashSet<String>(listEmpCode);
		Collection<String> differentEmployeeCode = new HashSet<String>();
		differentEmployeeCode.addAll(listDB);
		differentEmployeeCode.addAll(listEmpCode);
		similarEmployeeCode.retainAll(listDB);
		differentEmployeeCode.removeAll(listDB);
		
		System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listEmpCode, listDB, similarEmployeeCode, differentEmployeeCode);
		if (differentEmployeeCode.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" System can't upload  file due to mismatch of employ code : \n ");
			builder.append(" Mismatch employee Codes are " + differentEmployeeCode);
			throw new PayRollProcessException(builder.toString());
		}
		
		if(DateValidationEmpCode.size()>0) {
			throw new PayRollProcessException("To date should be grater than from date "+DateValidationEmpCode.toString());
		}
	 	List<String> exceptionEmployeeCode = new  ArrayList<>();
		for(LeaveEntryDTO leaveEntryDto :leaveEntryDtoList) {
			Employee employee = employeePersonalInformationService
					.findEmployees(leaveEntryDto.getEmployeeCode(), fileLeaveBalanceSummryDto.getCompanyId());
			leaveEntryDto.setEmployeeId(employee.getEmployeeId()); 
			leaveEntryDto.setCompanyId(employee.getCompany().getCompanyId()); 
			LeaveValidationResult leaveValidationResult = employeeLeaveService.appliedBulkLeaveDays(leaveEntryDto, exceptionEmployeeCode);
//			if(leaveValidationResult!=null && leaveValidationResult.getTotalLeaveApplyDays()!=null)
//			leaveEntryDto.getValue().setDays(leaveValidationResult.getTotalLeaveApplyDays());
			
			if(leaveValidationResult!=null && leaveValidationResult.getTotalLeaveApplyDays()!=null) {
				if(leaveEntryDto.getHalf_fullDay().equals("fullDay"))
					leaveEntryDto.setDays(leaveValidationResult.getTotalLeaveApplyDays());
			 		 else
			 			leaveEntryDto.setDays(new BigDecimal("0.5"));
			}
			
			
			if(leaveValidationResult!=null && leaveValidationResult.getTotalLeaveApplyDays()!=null)
	 		System.out.println("Employe Coce And Total Day Leave Apllied  " +employee.getEmployeeCode() + "  "+leaveValidationResult.getTotalLeaveApplyDays());

			List<LeaveBalanceSummryDTO> leaveBalanceSummryDTOList = employeeLeaveService.getEmployeeLeaveBalanceSummry(employee.getEmployeeId(),leaveEntryDto.getCompanyId());
			if(leaveBalanceSummryDTOList.isEmpty()) {
				leaveSummaryNotFound.add(employee.getEmployeeCode());
			}
			
			int count =0;
			
			if(exceptionEmployeeCode.isEmpty()  && leaveSummaryNotFound.isEmpty())
			for(LeaveBalanceSummryDTO leaveBalanceSummary : leaveBalanceSummryDTOList) {
				 	if(leaveBalanceSummary.getTmsleaveTypeMasterId()==leaveEntryDto.getLeaveTypeId()) {
				 		count++;
				 		leaveBalanceSummary.setUserId(fileLeaveBalanceSummryDto.getUserId());
				 		leaveBalanceSummary.setUserIdUpdate(fileLeaveBalanceSummryDto.getUserId());  
				 		leaveBalanceSummary.setCompanyId(employee.getCompany().getCompanyId());  
				 		leaveBalanceSummary.setStatus("APR"); 
				 		leaveBalanceSummary.setDateCreated(new Date()); 
//				 		leaveBalanceSummary.setEmployeeRemark(leaveEntryDto.getValue().getEmployeeRemark());
				 		leaveBalanceSummary.setFromDate(leaveEntryDto.getFromDate());
				 		leaveBalanceSummary.setToDate(leaveEntryDto.getToDate());
				 		leaveBalanceSummary.setEmployeeId(leaveEntryDto.getEmployeeId());
				 		leaveBalanceSummary.setHalf_fullDay(leaveEntryDto.getHalf_fullDay());
				 		leaveBalanceSummary.setHalfDayFor(leaveEntryDto.getHalf_fullDay());
				 		leaveBalanceSummary.setEmployeeCode(employee.getFirstName() + " " + employee.getLastName() + " ("
								+ employee.getEmployeeCode() + ")");
//				 		leaveBalanceSummary.setHalfDayFor(leaveEntryDto.getHalfDayFor());
				 		leaveBalanceSummary.setAppliedSandwhichDates(leaveValidationResult.getAppliedSandwhichDates());
				 		leaveBalanceSummary.setFinalSandwichDates(leaveValidationResult.getFinalSandwichDates());
				 		leaveBalanceSummary.setAppliedLeaveEnteriesDates(leaveValidationResult.getAppliedLeaveEnteriesDates());
				 		leaveBalanceSummary.setApprovalId(fileLeaveBalanceSummryDto.getApprovalId());
				 		leaveBalanceSummary.setApprovalRemark(leaveEntryDto.getEmployeeRemark());
				 		leaveBalanceSummary.setSandwichDatesFromSetObj(leaveValidationResult.getSandwichDatesFromSetObj());
				 		leaveBalanceSummary.setSandwichDatesToSetObj(leaveValidationResult.getSandwichDatesToSetObj());
				 		leaveBalanceSummary.setNature(leaveValidationResult.getLeaveNature());
				 		leaveBalanceSummary.setSandwitchNature(leaveValidationResult.getSandwitchNature());
				 		 if(leaveEntryDto.getHalf_fullDay().equals("fullDay"))
				 		leaveBalanceSummary.setDays(leaveValidationResult.getTotalLeaveApplyDays());
				 		 else
				 			leaveBalanceSummary.setDays(leaveEntryDto.getDays());
				 		 
				 		leaveBalanceSummryList.add(leaveBalanceSummary);
				 		
						LocalDate start = leaveBalanceSummary.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate end = leaveBalanceSummary.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						List<LocalDate> totalDates = new ArrayList<>();
						while (!start.isAfter(end)) {
						    totalDates.add(start);
						    start = start.plusDays(1);
						}
						List<String> leaveDates = new ArrayList<>();
						totalDates.forEach(item -> {
							leaveDates.add(item.toString());
						});
						List<String> dbArRequest = new ArrayList<>();
						
						List<AttendanceRegularizationRequest> arRequestList=attendanceRegularizationRequestService.getEmployeeARRequestList(leaveBalanceSummary.getEmployeeId());
					//	List<LocalDate> totalDatesAr = new ArrayList<>();
						List<Date> totalDatesAr = new ArrayList<Date>();
						arRequestList.forEach(item -> {
							Calendar calendar = new GregorianCalendar();
							calendar.setTime(item.getFromDate());
							while (calendar.getTime().before(item.getToDate())) {
								Date result = calendar.getTime();
								totalDatesAr.add(result);
								calendar.add(Calendar.DATE, 1);
							}
						});
						totalDatesAr.forEach(item -> {
							dbArRequest.add(item.toString());
						});
						dbArRequest.retainAll(leaveDates);
						if (dbArRequest.size() > 0) {
//							throw new ErrorHandling("You cannot apply Leave request");
							ARAppliedEmpCode.add(employee.getEmployeeCode());
						}
						else {
							if(leaveBalanceSummary.getLeaveId()==null) {
								validateLeave= employeeLeaveService.validateBulkLeaveEntry(leaveBalanceSummary,validateBulkLeaveEntry);
							}
							else if(leaveBalanceSummary.getLeaveId()!=null  && ! leaveBalanceSummary.getStatus().equals("CEN") && leaveBalanceSummary.getApprovalId()==null) {
								validateLeave= employeeLeaveService.validateBulkLeaveEntry(leaveBalanceSummary, validateBulkLeaveEntry);
							}
						}
						break;
				 	}
			 }
			if(count==0) {
				leaveSummaryNotFound.add(employee.getEmployeeCode());
			}
		}
		if (ARAppliedEmpCode.isEmpty()) {
			if (leaveSummaryNotFound.isEmpty()) {
				if (!exceptionEmployeeCode.isEmpty()) {
					throw new PayRollProcessException(
							"You cannot apply leave on requested duration as payroll has been created already "
									+ exceptionEmployeeCode.toString());
				} else {
					if (validateBulkLeaveEntry.isEmpty()) {
						return leaveBalanceSummryList;
//						for (LeaveBalanceSummryDTO leaveBalanceSummryDto : leaveBalanceSummryList) {
//							TMSLeaveEntry leaveEntry = leaveEntryAdaptor.uiDtoToDatabaseModel(leaveBalanceSummryDto);
//							employeeLeaveService.saveLeaveEntry(leaveEntry, leaveBalanceSummryDto);
//							System.out.println("In side Save");
//						}

					} else {
						throw new ErrorHandling(validateBulkLeaveEntry.toString());
					}
				}
			} else {
				throw new PayRollProcessException("Leave Scheme not found " + leaveSummaryNotFound.toString());
			}
		} else {
		throw new ErrorHandling("AR already applied You cannot apply Leave request "+ARAppliedEmpCode.toString());
	}
//		System.out.println("Singya bhalse");
	}
	



	//@GetMapping(value = "/pendingLeaveReqCount/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/pendingLeaveReqCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingLeaveReqCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") String status, @RequestBody SearchDTO searcDto)
			throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		//Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		//employeeLeaveService.getPendingLeaveReqCount(longCompanyId, entityCountDto);
		//count = entityCountDto.getCount();
		List<Object[]> leaveEntryObjList = employeeLeaveService.getAllLeaveApprovalsPending(companyId, searcDto);
		count = leaveEntryObjList.size();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	//@GetMapping(value = "/nonPendingLeaveReqCount/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/nonPendingLeaveReqCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getNonPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") String status, @RequestBody SearchDTO searcDto)
			throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		//Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		//employeeLeaveService.getNonPendingLeaveReqCount(longCompanyId, entityCountDto);
		List<Object[]> leaveEntryObjList = employeeLeaveService.getAllLeaveApprovalsNonPending(companyId, searcDto);
		//count = entityCountDto.getCount();
		count = leaveEntryObjList.size();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}
	@RequestMapping(value = "/getPendingLeaveRequest/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LeaveEntryDTO> getPendingLeaveRequest(@PathVariable("employeeId") Long employeeId,
			@RequestBody SearchDTO searcDto)  {

		 logger.info("getPendNonPendLeaveRequest is calling "+searcDto.getDataStatus());

		 List<TMSLeaveEntry> PendingLeaveRequestList = employeeLeaveService.getPendingLeaveReqbyPagination(employeeId, searcDto);	
		 logger.info("list size:"+PendingLeaveRequestList.size());
		 return leaveEntryAdaptor.databaseModelToUiDtoList(PendingLeaveRequestList);
		//return ticketRaisingHDList;
	}

	@RequestMapping(value = "/getPendingLeaveRequestCount/{employeeId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingLeaveRequestCount(@PathVariable("employeeId") Long employeeId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto)  {
		 logger.info("getPendNonPendLeaveRequestCount is calling "+searcDto.getDataStatus());
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		 List<TMSLeaveEntry> PendingLeaveRequestList = employeeLeaveService.getPendingLeaveReqbyPagination(employeeId, searcDto);

		count = PendingLeaveRequestList.size();

		logger.info("getPendNonPendLeaveRequestCount...count" + count);

		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		logger.info("getPendNonPendLeaveRequestCount...pages" + entityCountDto.getPageIndexs());

		return entityCountDto;
	}
	
}
