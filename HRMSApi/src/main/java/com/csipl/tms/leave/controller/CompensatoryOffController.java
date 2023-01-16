package com.csipl.tms.leave.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.leave.CompOffSearchDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.leave.adaptor.CompensatoryOffAdaptor;
import com.csipl.tms.leave.service.CompensatoryOffPaginationService;
import com.csipl.tms.leave.service.CompensatoryOffService;
import com.csipl.tms.leave.service.LeavePeriodService;
import com.csipl.tms.model.leave.CompensatoryOff;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.csipl.tms.model.leave.TMSLeavePeriod;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/compensatoryOff")
public class CompensatoryOffController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(CompensatoryOffController.class);

	@Autowired
	private CompensatoryOffService compensatoryOffService;

	@Autowired
	private CompensatoryOffPaginationService compensatoryOffPaginationService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	LeavePeriodService leavePeriodService;

	CompensatoryOffAdaptor compensatoryOffAdaptor = new CompensatoryOffAdaptor();

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved Shift"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update shift"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param shiftDto This is the first parameter for getting shift Object from UI
	 */
	@ApiOperation(value = "Save or Update CompensatoryOff")
	@RequestMapping(method = RequestMethod.POST)
	public void saveCompensatoryOff(@RequestBody CompensatoryOffDTO compensatoryOffDTO) throws ErrorHandling {

		logger.info("compensatoryOffDTO is calling : " + " : compensatoryOffDTO " + compensatoryOffDTO);

		// here we find TMSLeavePeriod based on leave period Id
		String formatedPsMonth = getFormatedDaete(compensatoryOffDTO.getFromDate());
		logger.info(" psMonthLocalDate " + formatedPsMonth);
		TMSLeavePeriod tMSLeavePeriod = leavePeriodService
				.findLeavePeriodByProcessMonth(compensatoryOffDTO.getCompanyId(), formatedPsMonth);

		if (tMSLeavePeriod == null) {
			throw new ErrorHandling("Leave Session Not Found Regarding Applied Date");
		}

		//// TMSLeavePeriod leavePeriod =
		//// leavePeriodService.leavePeriod(compensatoryOffDTO.getCompanyId());
		CompensatoryOff compensatoryOff = compensatoryOffAdaptor.uiDtoToDatabaseModel(compensatoryOffDTO,
				tMSLeavePeriod.getLeavePeriodId());
		logger.info("compensatoryOff is end  :" + "compensatoryOff" + compensatoryOff);
		compensatoryOffService.saveAll(compensatoryOff);

		// return
		// compensatoryOffAdaptor.databaseModelToUiDto(CompensatoryOffResult);CompensatoryOff
		// CompensatoryOffResult =
	}

	public String getFormatedDaete(Date fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		LocalDate psMonthLocalDate = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return psMonthLocalDate.toString();
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Shift List"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param companyId This is the first parameter for getting companyId from UI
	 */
	@ApiOperation(value = "View List of shift based on company ID")
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public List<CompensatoryOffDTO> CompensatoryOffList(@PathVariable("companyId") Long companyId) {
		logger.info("CompensatoryOffList is calling : " + " : companyId " + companyId);

		List<CompensatoryOff> CompensatoryOffList = compensatoryOffService.findAllCompensatoryOff(companyId);

		List<CompensatoryOffDTO> CompensatoryOffDTOList = compensatoryOffAdaptor
				.databaseModelToUiDtoList(CompensatoryOffList);

		logger.info("shiftList is end  :" + "shiftDtoList" + CompensatoryOffDTOList);

		return CompensatoryOffDTOList;
	}

	@RequestMapping(value = "/pending/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<CompensatoryOffDTO> findMyCompOffPendingReqList(
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling {
		logger.info("findAllCompOffPending is calling : " + employeeId);
		List<CompensatoryOff> compOffPendingList = compensatoryOffService.findMyCompOffPendingReqList(employeeId);
		if (compOffPendingList != null)
			return compensatoryOffAdaptor.databaseModelToUiDtoList(compOffPendingList);
		else
			throw new ErrorHandling("No one compOff pending request");

	}

	@RequestMapping(value = "/excludedPending/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<CompensatoryOffDTO> findMyCompOffExcludedPendingReqList(
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling {
		logger.info("findAllCompOffExcludedPending is calling : " + employeeId);
		List<CompensatoryOff> compOffExcludedPendingList = compensatoryOffService
				.findMyCompOffExcludedPendingReqList(employeeId);
		if (compOffExcludedPendingList != null)
			return compensatoryOffAdaptor.databaseModelToUiDtoList(compOffExcludedPendingList);
		else
			throw new ErrorHandling("No one  completed request");

	}

	@RequestMapping(value = "/compOffData/{compOffId}", method = RequestMethod.GET)
	public @ResponseBody CompensatoryOffDTO compensatoryOff(@PathVariable("compOffId") String compOffId,
			HttpServletRequest req) {
		Employee approvalEmp = null;
		Employee employeeEmp = null;
		logger.info("compensatoryOff is calling : " + "compOffId :" + compOffId);
		Long tmsCompensantoryOffId = Long.parseLong(compOffId);
		CompensatoryOff compensatoryOff = compensatoryOffService.getCompensatoryOff(tmsCompensantoryOffId);

		if (compensatoryOff.getEmployeeId() != null) {
			Long employeeId = compensatoryOff.getEmployeeId();
			employeeEmp = employeePersonalInformationService.getEmployeeInfo(employeeId);
		}
		if (compensatoryOff.getApprovalId() != null) {
			Long approvalId = compensatoryOff.getApprovalId();
			approvalEmp = employeePersonalInformationService.getEmployeeInfo(approvalId);
		}

		CompensatoryOffDTO compensatoryOffDTO = compensatoryOffAdaptor.databaseModelToUiDto(compensatoryOff,
				employeeEmp, approvalEmp);

//		compensatoryOffAdaptor.databaseModelToUiDto(compensatoryOffService.getCompensatoryOff(tmsCompensantoryOffId));
		return compensatoryOffDTO;
	}

	@RequestMapping(value = "/pendingRequest/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<CompensatoryOffDTO> findAllCompOffPendingReqList(
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		logger.info("findAllCompOffPendingReqList is calling : " + companyId);
		List<CompensatoryOff> compOffPendingList = compensatoryOffService.findAllCompOffPendingReqList(companyId);
		if (compOffPendingList != null)
			return compensatoryOffAdaptor.databaseModelToUiDtoList(compOffPendingList);
		else
			throw new ErrorHandling("No one compOff pending request");

	}

	@RequestMapping(value = "/excludedPendingRequest/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<CompensatoryOffDTO> findAllCompOffExcludedPendingReqList(
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		logger.info("findAllCompOffExcludedPendingReqList is calling : " + companyId);
		List<CompensatoryOff> compOffExcludedPendingList = compensatoryOffService
				.findAllCompOffExcludedPendingReqList(companyId);
		if (compOffExcludedPendingList != null)
			return compensatoryOffAdaptor.databaseModelToUiDtoList(compOffExcludedPendingList);
		else
			throw new ErrorHandling("No one  completed request");

	}

	// @GetMapping(value = "/CompOffCount/{companyId}/{pageSize}/{status}")
	@RequestMapping(value = "/CompOffCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getCompOffCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") boolean status,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int count;
		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDTO = new EntityCountDTO();
		// compensatoryOffPaginationService.getCompOffCount(longCompanyId,entityCountDTO,status);
		// count = entityCountDTO.getCount();
		List<Object[]> compOffObjList = compensatoryOffService.getAllEmpApprovalsPendingCompOff(companyId, searcDto);
		count = compOffObjList.size();
		logger.info("compensatoryOffService Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDTO.setPageIndexs(pageIndexList);
		entityCountDTO.setCount(count);
		return entityCountDTO;
	}

	@RequestMapping(value = "/compOffList/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompensatoryOffDTO> getAllCompOffDetails(@PathVariable("status") boolean status,
			@RequestBody CompOffSearchDTO compOffSearchDTO) throws PayRollProcessException {
		logger.info(" Compensatory off is calling  :");

		List<Object[]> compOffList = compensatoryOffPaginationService
				.findCandidatePagedAndFilterResult(compOffSearchDTO.getCompanyId(), compOffSearchDTO, status);

		return compensatoryOffAdaptor.modeltoDTOList(compOffList, compOffSearchDTO);
	}

	@RequestMapping(value = "/approvalsPending/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<CompensatoryOffDTO> CompOffApprovalsPending(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> compOffObjList = compensatoryOffService.getApprovalsPendingCompOff(companyId, employeeId);

		return compensatoryOffAdaptor.objcompOffListToObjUiDtoList(compOffObjList);
	}

	@RequestMapping(value = "/approvalsAllEmpPending/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CompensatoryOffDTO> allEmpCompOffApprovalsPending(
			@PathVariable("companyId") Long companyId, @RequestBody SearchDTO searcDto) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> compOffObjList = compensatoryOffService.getAllEmpApprovalsPendingCompOff(companyId, searcDto);

		return compensatoryOffAdaptor.objcompOffListToObjUiDtoList(compOffObjList);
	}

	@RequestMapping(value = "/approvalsNonPending/{companyId}/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CompensatoryOffDTO> CompOffApprovalsNonPending(
			@PathVariable("employeeId") Long employeeId, @PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) {
		logger.info("ARApprovals is calling : ");
		List<Object[]> compOffObjList = compensatoryOffService.getApprovalsNonPendingCompOff(companyId, employeeId,
				searcDto);
		return compensatoryOffAdaptor.objcompOffListToObjUiDtoList(compOffObjList);
	}

	@RequestMapping(value = "/CompOffNonPendingCount/{companyId}/{employeeId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getCompOffNonPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId, @PathVariable("pageSize") String pageSize,
			@PathVariable("status") boolean status, @RequestBody SearchDTO searcDto) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int count;
		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDTO = new EntityCountDTO();
		// compensatoryOffPaginationService.getCompOffCount(longCompanyId,entityCountDTO,status);
		// count = entityCountDTO.getCount();
		List<Object[]> compOffObjList = compensatoryOffService.getApprovalsNonPendingCompOff(companyId, employeeId,
				searcDto);
		count = compOffObjList.size();
		logger.info("compensatoryOffService non pending Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDTO.setPageIndexs(pageIndexList);
		entityCountDTO.setCount(count);
		return entityCountDTO;
	}

	@GetMapping(value = "/compOffCountMyTeam/{employeeId}/{companyId}")
	public @ResponseBody CompensatoryOffDTO compOffCountMyTeam(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return compensatoryOffService.compOffCountMyTeam(employeeId, companyId);
	}

	@GetMapping(value = "/compOffAllTimeCountMyTeam/{employeeId}/{companyId}")
	public @ResponseBody CompensatoryOffDTO compOffAllTimeCountMyTeam(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		return compensatoryOffService.compOffAllTimeCountMyTeam(employeeId, companyId);
	}

	@GetMapping(value = "/compOffCountMy/{employeeId}/{companyId}")
	public @ResponseBody CompensatoryOffDTO compOffCountMy(@PathVariable("employeeId") Long employeeId,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		logger.info("compOffCountMy count ");
		return compensatoryOffService.compOffCountMy(employeeId, companyId);
	}

	@RequestMapping(value = "/compensatoryOffCount/{companyId}/{pageSize}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getCompensatoryOffCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @PathVariable("status") boolean status,
			@RequestBody CompOffSearchDTO compOffSearchDTO) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int count;
		// Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDTO = new EntityCountDTO();
		// compensatoryOffPaginationService.getCompOffCount(longCompanyId,entityCountDTO,status);
		// count = entityCountDTO.getCount();
		List<Object[]> compOffObjList = compensatoryOffPaginationService
				.findCandidatePagedAndFilterResult(compOffSearchDTO.getCompanyId(), compOffSearchDTO, status);
		// List<Object[]> compOffObjList =
		// compensatoryOffService.getAllEmpApprovalsPendingCompOff(companyId, searcDto);
		count = compOffObjList.size();
		logger.info("compensatoryOffService Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDTO.setPageIndexs(pageIndexList);
		entityCountDTO.setCount(count);
		return entityCountDTO;
	}
	
	@RequestMapping(value = "/pendingCompoffEntity/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompensatoryOffDTO> getPendingCompffOfEntity(@PathVariable("employeeId") Long employeeId,
			@RequestBody SearchDTO searcDto) throws ErrorHandling  {

		 logger.info("pendingCompffOfEntity is calling ");

		 List<CompensatoryOff> compOffPendingList = compensatoryOffService.getPendingCompffOfEntitybyPagination(employeeId, searcDto);	
		 logger.info("pendingCompoffEntity list size:"+compOffPendingList.size());

				return compensatoryOffAdaptor.databaseModelToUiDtoList(compOffPendingList);
			

	}

	@RequestMapping(value = "/pendingCompoffEntityCount/{employeeId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingCompffOfEntityCount(@PathVariable("employeeId") Long employeeId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto)  {
		 logger.info("pendingCompffOfEntityCount is calling ");
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		 List<CompensatoryOff> compOffPendingList = compensatoryOffService.getPendingCompffOfEntitybyPagination(employeeId, searcDto);

		count = compOffPendingList.size();

		logger.info("pendingCompffOfEntityCount...count" + count);

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
		logger.info("pendingCompffOfEntityCount...pages" + entityCountDto.getPageIndexs());

		return entityCountDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
