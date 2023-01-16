package com.csipl.hrms.payroll.controller;

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
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.TdsGroupMasterDTO;
import com.csipl.hrms.dto.payroll.TdsGroupSetupDTO;
import com.csipl.hrms.dto.payroll.TdsSectionSetupDTO;
import com.csipl.hrms.model.payroll.EsiCycle;
import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.adaptor.TdsGroupMasterAdaptor;
import com.csipl.hrms.service.adaptor.TdsGroupSetupAdaptor;
import com.csipl.hrms.service.adaptor.TdsSectionSetupAdaptor;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.TdsSectionSetupService;
import com.csipl.hrms.service.payroll.repository.TdsGroupMasterRepository;
import com.csipl.hrms.service.payroll.repository.TdsGroupSetupRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/tdsSectionSetup")
@RestController
public class TdsSectionSetupController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TdsSectionSetupController.class);
	@Autowired
	TdsSectionSetupService tdsSectionSetupService;
	@Autowired
	TdsGroupSetupRepository tdsGroupSetupRepository;
	@Autowired
	TdsGroupMasterRepository tdsGroupMasterRepository;

	@Autowired
	FinancialYearService financialYearService;

	TdsSectionSetupAdaptor tdsSectionSetupAdaptor = new TdsSectionSetupAdaptor();
	TdsGroupSetupAdaptor tdsGroupSetupAdaptor = new TdsGroupSetupAdaptor();
	TdsGroupMasterAdaptor tdsGroupMasterAdaptor = new TdsGroupMasterAdaptor();

	@RequestMapping(path = "/saveTdsSection", method = RequestMethod.POST)
	public List<TdsSectionSetupDTO> saveTdsSection(@RequestBody List<TdsSectionSetupDTO> tdsSectionDtoList,
			HttpServletRequest req) {
		logger.info("saveTdsSection is calling");
		for (TdsSectionSetupDTO tdsSectionSetupDto : tdsSectionDtoList) {
			TdsGroupSetup tdsGroupSetup = tdsGroupSetupRepository.findGroupSetup(tdsSectionSetupDto.getCompanyId(),
					tdsSectionSetupDto.getTdsGroupMasterId());

			tdsSectionSetupDto.setTdsGroupId(tdsGroupSetup.getTdsGroupId());
		}
		List<TdsSectionSetup> tdsSectionSetupList = tdsSectionSetupAdaptor
				.tdsSectionuiDtoToDatabaseModelListTds(tdsSectionDtoList);
		List<TdsSectionSetup> tdsSectionSetupListResult = tdsSectionSetupService.saveTdsSection(tdsSectionSetupList);
		return null;
	}

	@GetMapping(value = "findById/{tdsGroupMasterId}/{companyId}")
	public @ResponseBody List<TdsSectionSetupDTO> findTdsSectionList(@PathVariable("tdsGroupMasterId") Long tdsGroupMasterId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		logger.info(" TdsSectionSetupDTO is calling :" + "companyId" + companyId + "tdsGroupMasterId" + tdsGroupMasterId);

		TdsGroupSetup tdsGroupSetup = tdsGroupSetupRepository.findGroupSetup(companyId, tdsGroupMasterId);
		
		return tdsSectionSetupAdaptor.tdsSectiondatabaseModelToUiDtoList(
				tdsSectionSetupService.getAllTdsSection(tdsGroupSetup.getTdsGroupId()));

	}

	@ApiOperation(value = "delete tdsSection by ID")
	@RequestMapping(path = "/deleteById/{tdsSectionId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTdsSection(@PathVariable(value = "tdsSectionId") Long tdsSectionId) {
		logger.info("deleteTdsSection is calling : " + " :" + "....tdsSectionId.." + tdsSectionId);
		tdsSectionSetupService.deleteById(tdsSectionId);
	}

	@RequestMapping(value = "findByGroup/{tdsGroupMasterId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody TdsGroupSetupDTO findBy(@PathVariable("tdsGroupMasterId") Long tdsGroupMasterId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) throws PayRollProcessException {
		logger.info("findAllGratuity is calling" + companyId);
		return tdsGroupSetupAdaptor.databaseModelToUiDto(tdsSectionSetupService.getByGroupId(companyId, tdsGroupMasterId));
	}

	@RequestMapping(path = "/saveTdsGroup/save", method = RequestMethod.POST)
	public void saveTdsGroup(@RequestBody TdsGroupSetupDTO tdsGroupSetupDTO, HttpServletRequest req) {
		logger.info("saveTdsGroup is calling" + tdsGroupSetupDTO);
		TdsGroupSetup tdsGroupSetup = tdsGroupSetupAdaptor.uiDtoToDatabaseModel(tdsGroupSetupDTO);
		tdsSectionSetupService.saveTdsGroup(tdsGroupSetup);
	}

	@ApiOperation(value = "update tdsGroup by ID")
	@RequestMapping(path = "/updateById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTdsGroup(@RequestBody TdsGroupSetupDTO tdsGroupSetupDTO, HttpServletRequest req) {
		logger.info("TdsSectionSetupController is calling : ");
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setTdsGroupId(tdsGroupSetupDTO.getTdsGroupId());
		tdsGroupSetup.setMaxLimit(tdsGroupSetupDTO.getMaxLimit());
		logger.info("TdsSectionSetupController.max--->>()" + tdsGroupSetupDTO.getMaxLimit());
		tdsSectionSetupService.updateById(tdsGroupSetup);
	}

	@ApiOperation(value = "update updateTdsSection by Id")
	@RequestMapping(path = "/updateByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTdsSection(@RequestBody TdsSectionSetupDTO tdsSectionSetupDTO, HttpServletRequest req) {
		TdsSectionSetup tdsSectionSetup = new TdsSectionSetup();
		tdsSectionSetup.setTdsSectionId(tdsSectionSetupDTO.getTdsSectionId());
		tdsSectionSetup.setActiveStatus(tdsSectionSetupDTO.getActiveStatus());
		tdsSectionSetupService.updateByStatus(tdsSectionSetup);
	}


	/**
	 * 
	 * saveTdsSection}
	 */
//	@RequestMapping(path = "/saveTdsGroupSetup", method = RequestMethod.POST)
//	public List<TdsGroupSetupDTO> saveTdsGroupSetup(@RequestBody List<TdsGroupSetupDTO> tdsSectionDtoList,
//			HttpServletRequest req) {
//		logger.info("saveTdsGroupSetup is calling");
//		for (TdsGroupSetupDTO tdsSectionSetupDto : tdsSectionDtoList) {
//			TdsGroupMaster tdsGroupSetup = tdsGroupMasterRepository
//					.findTdsGroupMaster(tdsSectionSetupDto.getTdsGroupName(), tdsSectionSetupDto.getCompanyId());
//			tdsSectionSetupDto.setTdsGroupId(tdsGroupSetup.getTdsGroupMasterId());
//		}
//		List<TdsGroupSetup> tdsSectionSetupList = tdsGroupSetupAdaptor
//				.tdsSectionuiDtoToDatabaseModelListTds(tdsSectionDtoList);
//		List<TdsGroupSetup> tdsSectionSetupListResult = tdsSectionSetupService.saveTdsGroupSetup(tdsSectionSetupList);
//		return null;
//	}

	/**
	 * findByFinanicalYear}
	 */
	@RequestMapping(value = "findByGroupFinancial/{companyId}", method = RequestMethod.GET)
	public @ResponseBody FinancialYearDTO getByFinancialYear(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("getByFinancialYear is calling" + companyId);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		return tdsGroupMasterAdaptor
				.financialdatabaseModelToUiDto(financialYearService.findCurrentFinancialYear(currentDate, companyId));
	}

	@RequestMapping(value = "findByFinanicalYearId/{companyId}/{financialYearId}", method = RequestMethod.GET)
	public @ResponseBody List<TdsGroupSetupDTO> findByFinanicalYear(@PathVariable("companyId") Long companyId,
			@PathVariable("financialYearId") Long financialYearId, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling {
		logger.info("findByFinanicalYear is calling" + companyId);
		return tdsGroupSetupAdaptor.tdsSectiondatabaseModelToUiDtoList(
				tdsSectionSetupService.findByFinanicalYear(companyId, financialYearId));

	}


	
}
