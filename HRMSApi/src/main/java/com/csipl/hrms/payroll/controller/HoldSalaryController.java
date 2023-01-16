package com.csipl.hrms.payroll.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
import com.csipl.hrms.model.payroll.HoldSalary;
import com.csipl.hrms.service.adaptor.HoldSalaryAdaptor;
import com.csipl.hrms.service.payroll.HoldSalaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/holdSalary")
@Api(description = "Operations pertaining to Hold Salary in Payroll")
public class HoldSalaryController {


	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(HoldSalaryController.class);
	@Autowired
	HoldSalaryService holdSalaryService;

	HoldSalaryAdaptor holdSalaryAdaptor = new HoldSalaryAdaptor();
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved Hold Salary"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update Hold Salary"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param shiftDto
	 *            This is the first parameter for getting shift Object from UI
	 */
	@ApiOperation(value = "Save or Update HoldSalary")
	@RequestMapping(method = RequestMethod.POST)
	public HoldSalaryDTO saveHoldSalary(@RequestBody HoldSalaryDTO holdSalaryDTO) {
		logger.info("saveHoldSalary is calling : " + " : holdSalaryDTO " + holdSalaryDTO);
		HoldSalary holdSalary = holdSalaryAdaptor.uiDtoToDatabaseModel(holdSalaryDTO);
		logger.info("saveHoldSalary is end  :" + "holdSalary" + holdSalary);
		return holdSalaryAdaptor.databaseModelToUiDto(holdSalaryService.save(holdSalary));
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Shift List"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param companyId
	 *            This is the first parameter for getting companyId from UI
	 */
	@ApiOperation(value = "View List of holdSalary based on company ID")
	@RequestMapping(value="/{companyId}/{payrollMonth}",method = RequestMethod.GET)
	public List<HoldSalaryDTO> holdSalaryList(@PathVariable("companyId") Long companyId,@PathVariable("payrollMonth") String payrollMonth) {
		logger.info("holdSalaryList is calling : " + " : companyId " + companyId);
		//Long companyID = Long.parseLong(companyId);
		List<Object[]> holdSalaryList = holdSalaryService.findAllHoldSalary(companyId,payrollMonth);
		return holdSalaryAdaptor.holddatabaseModelToUiDtoList(holdSalaryList);
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved shift object"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	@ApiOperation(value = "View a leave Based on holdSalary ID")
	@RequestMapping(path = "/getById/{holdSalaryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public HoldSalaryDTO findholdSalary(@PathVariable(value = "holdSalaryId") String holdSalaryId) {
		logger.info("holdSalaryList is calling : " + " :" + "....holdSalaryId.." + holdSalaryId);
		Long holdSalaryID = Long.parseLong(holdSalaryId);
		HoldSalary holdSalary = holdSalaryService.findHoldSalaryById(holdSalaryID);
		HoldSalaryDTO holdSalaryDto = holdSalaryAdaptor.databaseModelToUiDto(holdSalary);
		logger.info("holdSalaryList is end  :" + "holdSalaryDto.." + holdSalaryDto);
		return holdSalaryDto;
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved holdSalary"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	@ApiOperation(value = "delete holdSalary by ID")
	@RequestMapping(path = "/deleteById/{holdSalaryId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteholdSalary(@PathVariable(value = "holdSalaryId") String holdSalaryId) {
		logger.info("holdSalaryList is calling : " + " :" + "....holdSalaryId.." + holdSalaryId);
		Long holdSalaryID = Long.parseLong(holdSalaryId);
		holdSalaryService.deleteById(holdSalaryID);
	}
	
	
	@RequestMapping(value = "/searchEntity",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<HoldSalaryDTO> searchEmployeeLeaveDetails(@RequestBody HoldSalaryDTO holdSalarySearchDto)
			throws PayRollProcessException {
		logger.info(" active employees is calling :");
		System.out.println("companiUyTRT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+holdSalarySearchDto.getCompanyId());
		List<Object[]> employeeHoldSalaryList = holdSalaryService.holdSalarySearch(holdSalarySearchDto);
//		
		List<HoldSalaryDTO> holdSalarySearchDtoList= holdSalaryAdaptor.holddatabaseModelToUiDtoList(employeeHoldSalaryList);
		return holdSalarySearchDtoList;
		
	}
	@RequestMapping(value = "/searchEmployeeHoldDetails/{employeeId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<HoldSalaryDTO> searchEmployeeHoldDetails(@RequestBody HoldSalaryDTO holdSalarySearchDto,@PathVariable("employeeId") Long employeeId)
			throws PayRollProcessException {
		List<HoldSalary> holdSalaryList= holdSalaryService.searchEmployeeHoldDetails(employeeId);
		List<HoldSalaryDTO> holdSalaryDTOList=holdSalaryAdaptor.databaseModelToUiDtoList(holdSalaryList);
		
		return holdSalaryDTOList;
		
	}
	
}
