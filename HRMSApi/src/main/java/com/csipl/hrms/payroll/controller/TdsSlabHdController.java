package com.csipl.hrms.payroll.controller;

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

import com.csipl.hrms.dto.payroll.TdsSlabDTO;
import com.csipl.hrms.dto.payroll.TdsSlabHdDTO;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.model.payroll.TdsSlabMaster;
import com.csipl.hrms.service.adaptor.TdsSlabAdaptor;
import com.csipl.hrms.service.adaptor.TdsSlabHdAdaptor;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.TdsSlabHdService;
import com.csipl.hrms.service.payroll.TdsSlabService;
import com.csipl.hrms.service.payroll.repository.TdsSlabMasterRepository;
import com.csipl.hrms.service.payroll.repository.TdsSlabRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/tdsSlabHd")
@RestController
public class TdsSlabHdController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TdsSlabHdController.class);
	@Autowired
	TdsSlabHdService tdsSlabHdService;
	@Autowired
	TdsSlabService tdsSlabService;
	@Autowired
	FinancialYearService financialYearService;
	@Autowired
	TdsSlabRepository tdsSlabRepository;
	@Autowired
	TdsSlabMasterRepository tdsSlabMasterRepository;
	TdsSlabAdaptor tdsSlabAdaptor = new TdsSlabAdaptor();
	TdsSlabHdAdaptor tdsSlabHdAdaptor = new TdsSlabHdAdaptor();

	/**
	 * 
	 * findByCompanyId
	 */
	@GetMapping(value = "findById/{companyId}")
	public @ResponseBody List<TdsSlabHdDTO> findByCompanyId(@PathVariable("companyId") Long companyId) {
		logger.info("findByCompanyId is calling :" + "companyId" + companyId);
		List<TdsSlabHd> tdsList = tdsSlabHdService.getAllTdsSlabHd(companyId);
		return tdsSlabAdaptor.tdsSlabHddatabaseModelToUiDtoList(tdsList);
	}

	/**
	 * 
	 * findTdsSlabHdByFinencailYear
	 */

	@GetMapping(value = "findByFinencailYear/{companyId}/{finencialYearId}")
	public @ResponseBody List<TdsSlabHdDTO> findTdsSlabHdByFinencailYear(@PathVariable("companyId") Long companyId,
			@PathVariable("finencialYearId") Long finencialYearId) {
		logger.info("findTdsSlabHdByFinencailYear is calling :" + "companyId" + companyId);
		List<TdsSlabHd> tdsList = tdsSlabHdService.findTdsSlabHdByFinencailYear(companyId, finencialYearId);

		return tdsSlabAdaptor.tdsSlabHddatabaseModelToUiDtoList(tdsSlabHdService.saveTdsSlabHd(tdsList));
	}

	/**
	 * 
	 * findTdsSlabHdByFinencailYearByS
	 */
	@GetMapping(value = "findByFinencailYearBy/{companyId}/{finencialYearId}")
	public @ResponseBody void findTdsSlabHdByFinencailYearByS(@PathVariable("companyId") Long companyId,
			@PathVariable("finencialYearId") Long finencialYearId) {
		logger.info("findTdsSlabHdByFinencailYear is calling :" + "companyId" + companyId);
		List<TdsSlabHd> tdsList = tdsSlabHdService.getAllTdsSlabHd(companyId);
		for (TdsSlabHd tdsSlabHd : tdsList) {
			tdsSlabHd.setActiveStatus("DE");
			for (TdsSlab tdsSlab : tdsSlabHd.getTdsSlabs()) {
				tdsSlab.setActiveStatus("DE");
			}
		}
		tdsSlabHdService.saveTdsSlabHd(tdsList);
		List<TdsSlabHdDTO> tdsListDTO = tdsSlabAdaptor.tdsSlabHddatabaseModelToUiDtoList(tdsList);

		List<TdsSlabHd> tdsSlabHd = tdsSlabAdaptor.uislabHdDtoToDatabaseModelList(tdsListDTO, finencialYearId);
		tdsSlabHdService.saveTdsSlabHd(tdsSlabHd);

	}

	/**
	 * 
	 * findEmpIdProofs
	 */
	@ApiOperation(value = "findTdsSlabHd  by tdsSlabMasterId,companyId")
	@RequestMapping(value = "findBySlabMasterId/{tdsSlabMasterId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TdsSlabDTO> findTdsSlabHd(@PathVariable("tdsSlabMasterId") Long tdsSlabMasterId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) {
		logger.info("findEmpIdProofs is calling :" + "companyId" + companyId + "tdsCategory" + tdsSlabMasterId);
		TdsSlabHd tdsSlabHD = tdsSlabRepository.finddsSlabHd(tdsSlabMasterId, companyId);
		String planType = "A";
		return tdsSlabAdaptor
				.tdsSlabdatabaseModelToUiDtoList(tdsSlabService.findAllTdsSlabHdById(tdsSlabHD.getTdsSLabHdId(), planType));
	}

	/**
	 * 
	 * makeNewSlab
	 */
	@GetMapping(value = "makeNewSlab/{companyId}/{finencialYearId}")
	public @ResponseBody void makeNewSlab(@PathVariable("finencialYearId") Long finencialYearId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) {
		logger.info("makeNewSlab is calling :" + "companyId" + companyId + "finencialYearId" + finencialYearId);
		List<TdsSlabMaster> tdsSlabMasterList = tdsSlabMasterRepository.findsSlabMaster(companyId);
		List<TdsSlabHd> tdsSlabHdList = tdsSlabHdAdaptor.tdsMasterToTdsSlabHdConversion(tdsSlabMasterList,
				finencialYearId);
		tdsSlabHdService.saveTdsSlabHd(tdsSlabHdList);
		/*
		 * List<TdsSlabHd> tdsSlabHd= tdsSlabRepository.getAllSlabHd(companyId);
		 * 
		 * List<TdsSlab> slabList =
		 * tdsSlabHdAdaptor.tdsHdToTdsSlabConversion(tdsSlabHd);
		 * tdsSlabService.saveTdsSlab(slabList);
		 */
	
	}


}
