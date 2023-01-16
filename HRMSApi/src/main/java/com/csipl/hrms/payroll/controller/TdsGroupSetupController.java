package com.csipl.hrms.payroll.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.TdsGroupSetupDTO;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;
import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.adaptor.TdsGroupMasterAdaptor;
import com.csipl.hrms.service.adaptor.TdsGroupSetupAdaptor;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.TdsGroupSetupService;
import com.csipl.hrms.service.payroll.TdsSectionSetupService;
import com.csipl.hrms.service.payroll.repository.TdsGroupMasterRepository;

@RequestMapping("/tdsGroupSetup")
@RestController
public class TdsGroupSetupController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TdsGroupSetupController.class);
	@Autowired
	TdsSectionSetupService tdsSectionSetupService;
	@Autowired
	TdsGroupSetupService tdsGroupSetupService;
	@Autowired
	TdsGroupMasterRepository tdsGroupMasterRepository;

	@Autowired
	FinancialYearService financialYearService;

	TdsGroupSetupAdaptor tdsGroupSetupAdaptor = new TdsGroupSetupAdaptor();
	TdsGroupMasterAdaptor tdsGroupMasterAdaptor = new TdsGroupMasterAdaptor();


	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TdsGroupSetupDTO> findAll(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) {
		logger.info("getByFinancialYear is calling" + companyId);
		List<TdsGroupSetup> tdsGroupSetupList = tdsGroupSetupService.findByCompanyId(companyId);
		
		return tdsGroupSetupAdaptor
			.databaseModelToUiDtoList(tdsGroupSetupList);
	
	}
	

	@GetMapping(value = "findByIdTdsGroup/{companyId}/{financialYearId}")
	public @ResponseBody void findtdsMaster(@PathVariable("companyId") Long companyId,
			@PathVariable("financialYearId") Long financialYearId, 
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info(" TdsGroupMaster is calling :" + "companyId" + companyId + "tdsGroupName" + financialYearId);
		List<TdsGroupSetup> tdsGroupSetupList = tdsGroupSetupService.findByCompanyId(companyId);
	     
		for (TdsGroupSetup tdsGroupSetup : tdsGroupSetupList) {
			tdsGroupSetup.setActiveStatus("DE");
			 for (TdsSectionSetup section : tdsGroupSetup.getTdsSectionSetups()) {
				 section.setActiveStatus("DE");
				}
		}
		
		tdsGroupSetupService.saveTdsGroup(tdsGroupSetupList);
		
		
	List<TdsGroupSetupDTO>  tdsGroupSetupDTOList= tdsGroupSetupAdaptor.databaseModelToUiDtoList(tdsGroupSetupList);
	
	List<TdsGroupSetup> tdsGroupSetup = tdsGroupSetupAdaptor.uisetupDtoToDatabaseModelList(tdsGroupSetupDTOList ,financialYearId);
	System.out.println("save method calling!!!!!!!!!!!!!!!!!!!");
	tdsGroupSetupService.saveTdsGroup(tdsGroupSetup);
	//	List<TdsGroupSetup> tdsGroupSetup = tdsGroupSetupAdaptor.tdsSetupConversion(tdsGroupSetupList,financialYearId);
		
		
		
		//tdsGroupSetupService.saveTdsGroup(tdsGroupSetup);
			//tdsGroupSetupAdaptor.tdsSetupConversion(tdsGroupSetupList,financialYearId);
	}
	
	
	@RequestMapping(value = "makeNew/{companyId}/{financialYearId}", method = RequestMethod.GET)
	public @ResponseBody void makeNew(@PathVariable("companyId") Long companyId,@PathVariable("financialYearId") Long financialYearId,
			HttpServletRequest req) {
		logger.info("getByFinancialYear is calling" + financialYearId);
		List<TdsGroupMaster> tdsGroupMasterList = tdsGroupMasterRepository.findTdsGroupMaster(companyId);
		List<TdsGroupSetup> tdsGroupSetupList = tdsGroupMasterAdaptor.tdsGroupMasterTotdsGroupSetupConversion(tdsGroupMasterList,financialYearId);
//		for (TdsGroupSetup tdsGroupSetup : tdsGroupMasterList) {
//			
//		}
		tdsGroupSetupService.saveTdsGroup(tdsGroupSetupList);
	}


}
