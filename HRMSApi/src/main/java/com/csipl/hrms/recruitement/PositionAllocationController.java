package com.csipl.hrms.recruitement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.organisation.CompanyDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.model.recruitment.PositionAllocationXref;
import com.csipl.hrms.service.recruitement.PositionAllocationService;
import com.csipl.hrms.service.recruitement.adaptor.PositionAllocationAdaptor;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
@RequestMapping("/positionAllocation")
public class PositionAllocationController {

	private static final Logger logger = LoggerFactory.getLogger(PositionAllocationController.class);

	@Autowired
	private PositionAllocationService positionAllocationService;

	@Autowired
	private PositionAllocationAdaptor positionAllocationAdaptor;

	/**
	 * 
	 * @param positionDTO
	 * @param req
	 */
	@PostMapping(path = "/savePositionAllocation")
	public void savePositionAllocation(@RequestBody PositionAllocationXrefDTO positionAllocationXrefDTO) {
		logger.info("savePositionAllocation is calling");
		PositionAllocationXref position = positionAllocationAdaptor.uiDtoToDatabaseModel(positionAllocationXrefDTO);
		positionAllocationService.savePositionAllocation(position);
		logger.info("savePositionAllocation is Done");
	}

	@GetMapping(path = "/getRecentPositions/{companyId}/{positionCode}")
	public List<PositionAllocationXrefDTO> getRecentPositions(@PathVariable("companyId") Long companyId,
			@PathVariable("positionCode") String positionCode, HttpServletRequest req) {

		logger.info("getRecentPositions is calling ");

		List<Object[]> positionAllocationList = positionAllocationService.findRecentPositions(companyId, positionCode);

		return positionAllocationAdaptor.databaseModelToUiRecentPositionDto(positionAllocationList);

	}

	@RequestMapping(value = "/getAssignedPositions/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PositionAllocationXrefDTO> getAssignedPositions(@PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {

		logger.info("getAssignedPositions is calling ");
		
		List<Object[]> closedAllocations = positionAllocationService.getClosedAllocations(companyId);

		List<Object[]> positionAllocationList = positionAllocationService.findAssignedPositions(companyId, searcDto);

		return positionAllocationAdaptor.databaseModelToUiAssignedPositionList(positionAllocationList, closedAllocations);

	}

	@RequestMapping(value = "/assignedPositionsCount/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getAssignedPositionsCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		List<Object[]> positionAllocationList = positionAllocationService.findAssignedPositions(companyId, searcDto);

		count = positionAllocationList.size();

		System.out.println("getAssignedPositionsCount :" + count);

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

	@GetMapping(path = "/getNoOfPositionCount/{positionId}")
	public @ResponseBody PositionAllocationXrefDTO getNoOfPosition(@PathVariable("positionId") Long positionId,
			HttpServletRequest req) {

		logger.info("getNoOfPositionCount is calling ");

		PositionAllocationXrefDTO positionCount = positionAllocationService.findNoOfPosition(positionId);

		return positionCount;

	}

	@GetMapping(path = "/getPositiongById/{positionId}")
	public List<PositionAllocationXrefDTO> getPositiongById(@PathVariable("positionId") Long positionId,
			HttpServletRequest req) {

		logger.info("getPositiongById is calling ");

		List<Object[]> position = positionAllocationService.findPositiongById(positionId);

		return positionAllocationAdaptor.databaseModelToUiPositionDto(position);

	}

	@GetMapping(path = "/getPositionAllocationById/{positionId}/{positionAllocationId}")
	public List<PositionAllocationXrefDTO> getPositionAllocationById(@PathVariable("positionId") Long positionId,
			@PathVariable("positionAllocationId") Long positionAllocationId, HttpServletRequest req) {

		logger.info("getPositionAllocationById is calling ");

		List<Object[]> position = positionAllocationService.findPositionAllocationById(positionId,
				positionAllocationId);

		return positionAllocationAdaptor.databaseModelToUiPositionAllocationDto(position);

	}

	@GetMapping(path = "/getAssignToRecruiterList")
	public List<PositionAllocationXrefDTO> getAssignToRecruiterList() {

		logger.info("getAssignToRecruiterList is calling ");

		List<Object[]> positionAllocationList = positionAllocationService.findAssignToRecruiterList();

		List<Object[]> noOfPositionList = positionAllocationService.findNoOfPositionList();

		List<PositionAllocationXrefDTO> positionAllocationXrefDTOList = positionAllocationService
				.findRecruiterList(positionAllocationList, noOfPositionList);

		return positionAllocationXrefDTOList;

	}

	@GetMapping(path = "/getRecruiterBacklogList/{companyId}")
	public Map<String, Integer> getRecruiterBacklogList(@PathVariable("companyId") Long companyId) {

		logger.info("getRecruiterBacklogList is calling ");

		Map<String, Integer> recruiterBacklogList = positionAllocationService.getOverallBacklogs(companyId);

		return recruiterBacklogList;

	}

	@RequestMapping(path = "/getCompanyLogo/{companyId}", method = RequestMethod.GET)
	public @ResponseBody CompanyDTO getCompany(@PathVariable("companyId") Long companyId) {
		logger.info("getCompanyLogo is calling :companyId " + companyId);
		String company = positionAllocationService.getCompany(companyId);
		CompanyDTO companyDTO = new CompanyDTO();

		String rootPath = System.getProperty("catalina.home");
		rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + company;

		companyDTO.setCompanyLogoPath(rootPath);
		logger.info("getCompanyLogo end " + rootPath);

		return companyDTO;
	}
	
	@RequestMapping(value = "/findAssessmentDetail/{companyId}/{interviewScheduleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  InterviewSchedulerDTO findAssessment(@PathVariable("companyId") Long companyId, @PathVariable("interviewScheduleId") Long interviewScheduleId) throws PayRollProcessException {
		List<Object[]> assessmentDetails = positionAllocationService.findAssessment(companyId, interviewScheduleId);
		List<Object[]> levelList = positionAllocationService.getLevelList(companyId, interviewScheduleId);
		return positionAllocationAdaptor.databaseModelToInterviewSchedulerUiDto(assessmentDetails, levelList);
	}

}
