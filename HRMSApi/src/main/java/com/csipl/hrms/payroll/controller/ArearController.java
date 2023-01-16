package com.csipl.hrms.payroll.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.payroll.ArearCalculationDTO;
import com.csipl.hrms.dto.payroll.ArearMasterDTO;
import com.csipl.hrms.model.payroll.ArearCalculation;
import com.csipl.hrms.model.payroll.ArearMaster;
import com.csipl.hrms.service.adaptor.ArearAdaptor;
import com.csipl.hrms.service.payroll.ArearService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/arear")
@Api(description = "Operations pertaining to arear in Payroll")
public class ArearController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ArearController.class);
	
	@Autowired
	private ArearService arearService;
	
	ArearAdaptor arearAdaptor=new ArearAdaptor();
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved Hold Salary"),
			@ApiResponse(code = 401, message = "You are not authorized to save or update Hold Salary"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param shiftDto
	 *            This is the first parameter for getting shift Object from UI
	 */
	@ApiOperation(value = "Save or Update HoldSalary")
	@RequestMapping(value="saveData",method = RequestMethod.POST)
	public void saveArearMaster(@RequestBody ArearMasterDTO arearMasterDTO) {
		logger.info("saveArearMaster is calling : " + " : arearMasterDTO " + arearMasterDTO);
		ArearMaster arearMaster = arearAdaptor.arearMasteruiDtoToDatabaseModel(arearMasterDTO);
		logger.info("saveArearMaster is end  :" + "arearMaster" + arearMaster);
		arearService.save(arearMaster);
	}
	
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Shift List"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param companyId
	 *            This is the first parameter for getting companyId from UI
	 */
	@ApiOperation(value = "View List of arear based on company ID")
	@RequestMapping(value="/{companyId}",method = RequestMethod.GET)
	public List<ArearCalculationDTO> areatList(@PathVariable("companyId") Long companyId) throws ParseException {
		logger.info("holdSalaryList is calling : " + " : companyId " + companyId);
		List<Object[]> arearList = arearService.findAllArear(companyId);
		return arearAdaptor.areardatabaseModelToUiDtoList(arearList);
	}
	
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Shift List"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param companyId
	 *            This is the first parameter for getting companyId from UI
	 */
	@ApiOperation(value = "View List of arear based on company ID")
	@RequestMapping(value="/getCalculation/{arearId}",method = RequestMethod.GET)
	public List<ArearCalculationDTO> arearCalculation(@PathVariable("arearId") Long arearId) throws ParseException {
		logger.info("arearList is calling : " + " : arearId " + arearId);
		//Long companyID = Long.parseLong(companyId);
//		List<ArearCalculation> arear = arearService.findArearCalculation(arearId);
//		logger.info("arearList is end : " + " : arearId " + arearId);
//		return arearAdaptor.databaseModelToUiDto(arear);
		List<Object[]> areatList = arearService.findArearCalculation(arearId);
		return	arearAdaptor.arearCaldatabaseModelToUiDtoList(areatList);
		
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully delete Arear"),
			@ApiResponse(code = 401, message = "You are not authorized to delete Arear"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	/**
	 * @param companyId
	 *            This is the first parameter for getting companyId from UI
	 */
	@ApiOperation(value = "delete Arear based on arearIdD")
	@RequestMapping(value="/deleteArear/{arearId}",method = RequestMethod.DELETE)
	public Boolean deleteArearCalculation(@PathVariable("arearId") Long arearId) throws ParseException {
		logger.info(" deleteArearCalculation is calling : " + " : arearId " + arearId);
	
		Boolean deleteFlag = arearService.delete(arearId);
		if(deleteFlag)
		return	true;
		else
			return	false;	
	}
	
	@ApiOperation(value = "View List of arear based on company ID")
	@RequestMapping(value="/getArear/{employeeId}",method = RequestMethod.GET)
	public int arearByEmployeeId(@PathVariable("employeeId") Long employeeId) throws ParseException {
	logger.info("arearList is calling : " + " : arearId " + employeeId);
	int count = arearService.getArear(employeeId);
	return	count;

	}
}
