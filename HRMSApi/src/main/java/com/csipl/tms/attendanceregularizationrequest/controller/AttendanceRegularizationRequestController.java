package com.csipl.tms.attendanceregularizationrequest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceRegularizationRequestAdaptor;
import com.csipl.tms.attendanceregularizationrequest.service.ARRequestPagingAndFilterService;
import com.csipl.tms.attendanceregularizationrequest.service.AttendanceRegularizationRequestService;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.empcommondetail.service.EmpCommonDetailService;
import com.csipl.tms.leave.service.LeaveEntriesDatewiseService;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/attendanceregularizationrequest")
@RestController
@Api(description = "Operations pertaining to AttendanceRegularizationRequest in Time Management")
public class AttendanceRegularizationRequestController {

	@Autowired
	AttendanceRegularizationRequestService attendanceRegularizationRequestService;

	@Autowired
	EmpCommonDetailService empCommonDetailService;

	@Autowired
	ARRequestPagingAndFilterService aRRequestPagingAndFilterService;

	@Autowired
	LeaveEntriesDatewiseService leaveEntriesDatewiseService;
	/*
	 * @Autowired private final RestTemplate restTemplate;
	 */

	// @Qualifier("employeeService")
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(AttendanceRegularizationRequestController.class);

	AttendanceRegularizationRequestAdaptor attendanceRegularizationRequestAdaptor = new AttendanceRegularizationRequestAdaptor();

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "You have already applied AR in the given duration") })
	@ApiOperation(value = "Save or update AttendanceRegularizationRequest")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody AttendanceRegularizationRequest save(
			@RequestBody AttendanceRegularizationRequestDTO attendanceRegularizationRequestDto)
			throws ErrorHandling, PayRollProcessException {
		logger.info("save AttendanceRegularizationRequest is calling : " + " : attendanceRegularizationRequestDto "
				+ attendanceRegularizationRequestDto);

		LocalDate start = attendanceRegularizationRequestDto.getFromDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate end = attendanceRegularizationRequestDto.getToDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		List<LocalDate> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
			totalDates.add(start);
			start = start.plusDays(1);
		}
		List<String> arDates = new ArrayList<>();
		totalDates.forEach(item -> {
			arDates.add(item.toString());
		});
		List<String> dbleaveEntriesDatewise = new ArrayList<>();
		List<TMSLeaveEntriesDatewise> dbleaveEntriesDatewiseList = leaveEntriesDatewiseService
				.getPenAprLeaveEntryDateWise(attendanceRegularizationRequestDto.getEmployeeId());

		dbleaveEntriesDatewiseList.forEach(item -> {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(item.getLeaveDate());
			dbleaveEntriesDatewise.add(strDate);
		});
		dbleaveEntriesDatewise.retainAll(arDates);

		if (dbleaveEntriesDatewise.size() > 0
				&& attendanceRegularizationRequestDto.getStatus().equalsIgnoreCase("PEN")) {
			// StringBuilder builder = new StringBuilder();
			throw new ErrorHandling(
					"You cannot apply AR request because you have already applied Leave in this duration");
		}

		AttendanceRegularizationRequest attendanceRegularizationRequest = attendanceRegularizationRequestAdaptor
				.uiDtoToDatabaseModel(attendanceRegularizationRequestDto);
		logger.info("AttendanceRegularizationRequest  : " + attendanceRegularizationRequest);
		attendanceRegularizationRequestService.save(attendanceRegularizationRequest);

		logger.info("save AttendanceRegularizationRequest is end  :" + attendanceRegularizationRequest);
		return attendanceRegularizationRequest;

	}

	/**
	 * to get all AttendanceRegularizationRequest List from database based on
	 * companyId
	 * 
	 * @param companyId
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View a AttendanceRegularizationRequest based on companyId ")
	@RequestMapping(value = "/all_pending/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> getAllPendingARRequest(
			@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllARRequest is calling : ");
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllPendingARRequest(companyId);
		logger.info("getAllARRequest is end  :" + arRequestObjList);
		if (arRequestObjList != null && arRequestObjList.size() > 0) {
			return attendanceRegularizationRequestAdaptor.objarListToObjUiDtoList(arRequestObjList);
		} else
			throw new ErrorHandling("AttendanceRegularizationRequest not found");
	}

	@RequestMapping(value = "/all_ar/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> getAllARRequest(
			@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllARRequest is calling : ");
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllARRequest(companyId);
		logger.info("getAllARRequest is end  :" + arRequestObjList);
		if (arRequestObjList != null && arRequestObjList.size() > 0) {
			return attendanceRegularizationRequestAdaptor.objarListToObjUiDtoList(arRequestObjList);
		} else
			throw new ErrorHandling("AttendanceRegularizationRequest not found");
	}

	/**
	 *
	 * to get AttendanceRegularizationRequest object from database based on arId
	 * 
	 * @param arId
	 */

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View a AttendanceRegularizationRequest based on arId ")
	@RequestMapping(value = "/{arId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AttendanceRegularizationRequestDTO getARRequest(@PathVariable("arId") Long arId) {
		Employee approvalEmp = null;
		Employee employeeEmp = null;
		logger.info("getARRequest is calling : arId =" + arId);
		AttendanceRegularizationRequest arRequest = attendanceRegularizationRequestService.getARRequest(arId);
		if (arRequest.getEmployeeId() != null) {
			Long employeeId = arRequest.getEmployeeId();
			// employeeEmp = empCommonDetailService.getEmployeeCommonDetails(employeeId);
			employeeEmp = employeePersonalInformationService.getEmployeeInfo(employeeId);
		}
		if (arRequest.getApprovalId() != null) {
			Long approvalId = arRequest.getApprovalId();
			approvalEmp = employeePersonalInformationService.getEmployeeInfo(approvalId);
		}

		// logger.info("employeeDto---->" + employeeEmp.toString());

		AttendanceRegularizationRequestDTO aRRequestDto = attendanceRegularizationRequestAdaptor
				.arRequestDetailsObjToUiDto(arRequest, employeeEmp, approvalEmp);

		logger.info("AR Data->" + aRRequestDto.toString());

		return aRRequestDto;
	}

	/**
	 * to get all AttendanceRegularizationRequest List from database based on
	 * companyId
	 * 
	 * @param employeeId
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View a AttendanceRegularizationRequest based on employeeId ")
	@RequestMapping(value = "/pending/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> getEmployeePendingARRequest(
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling {
		logger.info("getEmployeeARRequest is calling : ");
		List<AttendanceRegularizationRequest> attendanceRegularizationRequestList = attendanceRegularizationRequestService
				.getEmployeePendingARRequest(employeeId);

		logger.info("getEmployeeARRequest is end  :" + attendanceRegularizationRequestList);
		if (attendanceRegularizationRequestList != null)
			return attendanceRegularizationRequestAdaptor.databaseModelToUiDtoList(attendanceRegularizationRequestList);
		else
			throw new ErrorHandling("Employee AttendanceRegularizationRequest  not found");
	}

	@RequestMapping(value = "/ar_request/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> getEmployeeARRequest(
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling {
		logger.info("getEmployeeARRequest is calling : ");
		List<AttendanceRegularizationRequest> attendanceRegularizationRequestList = attendanceRegularizationRequestService
				.getEmployeeARRequest(employeeId);

		logger.info("getEmployeeARRequest is end  :" + attendanceRegularizationRequestList);
		if (attendanceRegularizationRequestList != null)
			return attendanceRegularizationRequestAdaptor.databaseModelToUiDtoList(attendanceRegularizationRequestList);
		else
			throw new ErrorHandling("Employee AttendanceRegularizationRequest  not found");
	}

	/**
	 * to get count of all pending status of AttendanceRegularizationRequest from
	 * database based on companyId and employeeId
	 * 
	 * @param employeeId
	 * @throws ErrorHandling
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "View a count of AttendanceRegularizationRequest based on companyId and employeeId ")
	@RequestMapping(value = "/count/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody AttendanceRegularizationRequestDTO getArCount(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling {

		return attendanceRegularizationRequestService.arCount(companyId, employeeId);
	}

	@RequestMapping(value = "/ar/{companyId}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceRegularizationRequestDTO> getARRequestPaging(@PathVariable("companyId") Long companyId,
			@PathVariable("status") Boolean status, @RequestBody SearchDTO searcDto) throws PayRollProcessException { //
		logger.info(" ar is calling :" + searcDto.toString());
		List<Object[]> arRequestList = aRRequestPagingAndFilterService.getARByPagingAndFilter(companyId, status,
				searcDto);

		return attendanceRegularizationRequestAdaptor.modeltoDTOList(arRequestList, searcDto);
	}

	// @GetMapping(value = "/pendingARCount/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/pendingARCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") Boolean status,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		// attendanceRegularizationRequestService.getPendingARCount(longCompanyId,
		// entityCountDto);
		// count = entityCountDto.getCount();
		List<Object[]> arRequestList = aRRequestPagingAndFilterService.getARByPagingAndFilter(companyId, status,
				searcDto);
		count = arRequestList.size();
		System.out.println("pendingARCount :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	// @GetMapping(value = "/nonPendingARCount/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/nonPendingARCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getNonPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") Boolean status,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		// attendanceRegularizationRequestService.getNonPendingARCount(longCompanyId,
		// entityCountDto);
		// count = entityCountDto.getCount();
		List<Object[]> arRequestList = aRRequestPagingAndFilterService.getARByPagingAndFilter(companyId, status,
				searcDto);
		count = arRequestList.size();
		System.out.println("nonPendingARCount :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	@GetMapping(value = "/countMyTeam/{employeeId}/{companyId}")
	public @ResponseBody AttendanceRegularizationRequestDTO countMyTeamPendingARCount(
			@PathVariable("employeeId") Long employeeId, @PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		return attendanceRegularizationRequestService.countMyTeamPendingARCount(employeeId, companyId);
	}

	@GetMapping(value = "/countAllTimeMyTeam/{employeeId}/{companyId}")
	public @ResponseBody AttendanceRegularizationRequestDTO countAllTimeMyTeamPendingARCount(
			@PathVariable("employeeId") Long employeeId, @PathVariable("companyId") Long companyId)
			throws ErrorHandling {
		return attendanceRegularizationRequestService.countAllTimeMyTeamPendingARCount(employeeId, companyId);
	}

	@GetMapping(value = "/allEmployeeARCount/{companyId}")
	public @ResponseBody AttendanceRegularizationRequestDTO allEmployeeARCount(
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return attendanceRegularizationRequestService.allEmployeePendingARCount(companyId);
	}

	@GetMapping(value = "/allTimeAllEmployeeARCount/{companyId}")
	public @ResponseBody AttendanceRegularizationRequestDTO allTimeAllEmployeeARCount(
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return attendanceRegularizationRequestService.allTimeAllEmployeePendingARCount(companyId);
	}

	@RequestMapping(value = "/approvalsPending/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> ARApprovalsPending(
			@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllApprovalsPending(companyId,
				employeeId);

		return attendanceRegularizationRequestAdaptor.objarListToObjUiDtoList(arRequestObjList);
	}

	@RequestMapping(value = "/approvalsAllEmpPending/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> allEmpARApprovalsPending(
			@PathVariable("companyId") Long companyId, @RequestBody SearchDTO searcDto) {

		logger.info("all ARApprovals is calling : ");

		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllEmpApprovalsPending(companyId,
				searcDto);

		return attendanceRegularizationRequestAdaptor.objarListToObjUiDtoList(arRequestObjList);
	}

	@RequestMapping(value = "/approvalsNonPending/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AttendanceRegularizationRequestDTO> ARApprovalsNonPending(
			@PathVariable("employeeId") Long employeeId, @PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllApprovalsNonPending(companyId,
				employeeId, searcDto);
		return attendanceRegularizationRequestAdaptor.objarListToObjUiDtoList(arRequestObjList);
	}

	@RequestMapping(value = "/arRequestStatusUpdate", method = RequestMethod.POST)
	public void arRequstStatusUpdate(@RequestBody AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO,
			HttpServletRequest req) {
		logger.info("arRequstStatusUpdate is calling :  " + attendanceRegularizationRequestDTO);
		AttendanceRegularizationRequest attendanceRegularizationRequest = new AttendanceRegularizationRequest();
		attendanceRegularizationRequest.setStatus(attendanceRegularizationRequestDTO.getStatus());
		attendanceRegularizationRequest.setArID(attendanceRegularizationRequestDTO.getArID());
		attendanceRegularizationRequest.setActionableDate(attendanceRegularizationRequestDTO.getActionableDate());
		attendanceRegularizationRequest.setApprovalId(attendanceRegularizationRequestDTO.getApprovalId());
		attendanceRegularizationRequestService.updateById(attendanceRegularizationRequest);

	}

	// @GetMapping(value = "/ARCountPending/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/ARCountPending/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingARCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") String status,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		// attendanceRegularizationRequestService.getARCountPending(longCompanyId,
		// entityCountDto);
		// count = entityCountDto.getCount();
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllEmpApprovalsPending(companyId,
				searcDto);
		count = arRequestObjList.size();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	// @GetMapping(value = "/ARCountNonPending/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/ARCountNonPending/{companyId}/{employeeId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getARCountNonPending(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @PathVariable("pageSize") String pageSize,
			@PathVariable("status") String status, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		// attendanceRegularizationRequestService.getARCountNonPending(longCompanyId,
		// entityCountDto);
		// count = entityCountDto.getCount();
		List<Object[]> arRequestObjList = attendanceRegularizationRequestService.getAllApprovalsNonPending(companyId,
				employeeId, searcDto);
		count = arRequestObjList.size();

		System.out.println("ARNon pending Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("non pending Page size : -" + pageSize);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}
	
	/*
	 * Shubham yaduwanshi
	 */
	@RequestMapping(value = "/getARRequestDetails/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceRegularizationRequestDTO> getEmployeeARRequestDetails(@PathVariable("employeeId") String employeeId,@RequestBody SearchDTO searchDTO)  {
		    logger.info("getEmployeeARRequestDetails is calling :"+employeeId);
			Long empId = Long.parseLong(employeeId);
		    List<Object[]> ARRequestList = attendanceRegularizationRequestService.getARRequestDetailsbyPagination(empId, searchDTO);	
		    logger.info("list size:"+ARRequestList.size());
			return attendanceRegularizationRequestAdaptor.databaseModelARRequestDetailsDtoListWithPagination(ARRequestList);
	}
	
	@RequestMapping(value = "/getARRequestCount/{employeeId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getEmployeeARRequestCount(@PathVariable("employeeId") String employeeId,@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) {	
		logger.info("getEmployeeARRequestCount...companyId" + employeeId+" page size:"+pageSize);
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDTO = new EntityCountDTO();
		Long empId = Long.parseLong(employeeId);
		List<Object[]> ARRequestList = attendanceRegularizationRequestService.getARRequestDetailsbyPagination(empId, searcDto);	
        count=ARRequestList.size();
		logger.info("getEmployeeARRequestCount...count" + count);
		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDTO.setPageIndexs(pageIndexList);
		entityCountDTO.setCount(count);
		logger.info("getEmployeeARRequestCount...pages" + entityCountDTO.getPageIndexs());

		return entityCountDTO;
	}
	
	@RequestMapping(value = "/getARPendingRequestDetails/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttendanceRegularizationRequestDTO> getARPendingRequestDetails(@PathVariable("employeeId") String employeeId,@RequestBody SearchDTO searchDTO)  {
		    logger.info("getARPendingRequestDetails is calling :"+employeeId);
			Long empId = Long.parseLong(employeeId);
		    List<Object[]> ARRequestList = attendanceRegularizationRequestService.getARPendingRequestDetailsbyPagination(empId, searchDTO);	
		    logger.info("list size:"+ARRequestList.size());
			return attendanceRegularizationRequestAdaptor.databaseModelARRequestDetailsDtoListWithPagination(ARRequestList);
	}
	
	@RequestMapping(value = "/getARPendingRequestCount/{employeeId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getARPendingRequestCount(@PathVariable("employeeId") String employeeId,@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) {	
		logger.info("getARPendingRequestCount...companyId" + employeeId+" page size:"+pageSize);
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDTO = new EntityCountDTO();
		Long empId = Long.parseLong(employeeId);
		List<Object[]> ARRequestList = attendanceRegularizationRequestService.getARPendingRequestDetailsbyPagination(empId, searcDto);	
        count=ARRequestList.size();
		logger.info("getARPendingRequestCount...count" + count);
		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDTO.setPageIndexs(pageIndexList);
		entityCountDTO.setCount(count);
		logger.info("getARPendingRequestCount...pages" + entityCountDTO.getPageIndexs());

		return entityCountDTO;
	}

}
