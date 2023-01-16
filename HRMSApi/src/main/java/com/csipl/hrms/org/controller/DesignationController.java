package com.csipl.hrms.org.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.organisation.DesignationDTO;
import com.csipl.hrms.model.organisation.Designation;
import com.csipl.hrms.service.adaptor.DesignationAdaptor;
import com.csipl.hrms.service.organization.DesignationService;

@RestController
@RequestMapping("/designation")
public class DesignationController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);
	@Autowired
	DesignationService designationService;

	DesignationAdaptor designationAdaptor = new DesignationAdaptor();

	/**
	 * to get all designations List from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DesignationDTO> getAllDesignations(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllDesignations is calling : companyId" + companyId);
		Long longcompanyId = Long.parseLong(companyId);
		List<Designation> designationList = designationService.findAllDesignation(longcompanyId);
		if (designationList != null && designationList.size() > 0) {
			logger.info("getAllDesignations is end  :" + "designationList");
			return designationAdaptor.databaseModelToUiDtoList(designationList);
		}
		throw new ErrorHandling("Designation data not present");
	}

	/**
	 * to get all designations List from database based on companyId and
	 * departmentId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/{departmentId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DesignationDTO> designationList(@PathVariable("departmentId") String departmentId,
			@PathVariable("companyId") String companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info("getAllDesignations is calling : " + ":departmentId" + departmentId + "...companyId.." + companyId);
		Long deptId = Long.parseLong(departmentId);
		Long longcompanyId = Long.parseLong(companyId);
		List<Object[]> designationList = designationService.designationListBasedOnDepartmnt(longcompanyId, deptId);
		if (designationList != null && designationList.size() > 0)
			return designationAdaptor.databaseModelToUiDtoLists(designationList);
		throw new ErrorHandling("Designation data not present");
	}

	@RequestMapping(value = "designation/{designationId}", method = RequestMethod.GET)
	public @ResponseBody DesignationDTO designationById(@PathVariable("designationId") String designationId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getByDesignations is calling : " + ":departmentId" + designationId);
		Long deptId = Long.parseLong(designationId);
		Designation designation = designationService.findById(deptId);
		if (designation != null)
			return designationAdaptor.databaseModelToUiDto(designation);
		throw new ErrorHandling("Designation data not present");
	}

	@RequestMapping(value = "/designationSave", method = RequestMethod.POST)
	public @ResponseBody DesignationDTO saveDesignation(@RequestBody DesignationDTO designationDTOList,
			HttpServletRequest req) {
		Designation designationList = designationAdaptor.uiDtoToDatabaseModel(designationDTOList);
		Designation designationListObj = designationService.save(designationList);
		// System.out.println(designationAdaptor.databaseModelToUiDtoList(designationListObj).toString());
		return designationAdaptor.databaseModelToUiDto(designationListObj);

	}

	@RequestMapping(value = "/designationStatusUpdate", method = RequestMethod.POST)
	public void designationStatusUpdate(@RequestBody DesignationDTO designationDTO, HttpServletRequest req) {
		logger.info("saveDesignation is calling :  " + designationDTO);
		Designation designation = new Designation();
		designation.setActiveStatus(designationDTO.getActiveStatus());
		designation.setDesignationId(designationDTO.getDesignationId());
		designationService.updateById(designation);

	}

	@RequestMapping(value = "getById/{designationId}", method = RequestMethod.GET)
	public DesignationDTO designById(@PathVariable("designationId") Long designationId) {
		logger.info("designById is calling : " + " : designationId " + designationId);
		List<Object[]> designationList = designationService.getDesigById(designationId);
		return designationAdaptor.designationdatabaseModelToUiDtoList(designationList);
	}

	@RequestMapping(value = "getAllActiveDesignation/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<DesignationDTO> getAllActiveDesignations(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllDesignations is calling : companyId" + companyId);
		Long longcompanyId = Long.parseLong(companyId);
		List<Designation> designationList = designationService.findAllActiveDesignation(longcompanyId);
		if (designationList != null && designationList.size() > 0) {
			logger.info("getAllDesignations is end  :" + "designationList" );
			return designationAdaptor.databaseModelToUiDtoList(designationList);
		}
		throw new ErrorHandling("Designation data not present");
	}

}
