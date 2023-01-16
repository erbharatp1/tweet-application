package com.csipl.hrms.payroll.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.TdsSlabDTO;
import com.csipl.hrms.dto.payroll.TdsSlabHdDTO;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.service.adaptor.TdsSlabAdaptor;
import com.csipl.hrms.service.payroll.TdsSlabService;
import com.csipl.hrms.service.payroll.repository.TdsSlabRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/tdsSlab")
@RestController
public class TdsSlabController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TdsSlabController.class);
	@Autowired
	TdsSlabService tdsSlabService;

	@Autowired
	TdsSlabRepository tdsSlabRepository;

	TdsSlabAdaptor tdsSlabAdaptor = new TdsSlabAdaptor();

	/**
	 * 
	 * saveTdsSlabHd}
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void saveTdsSlabHd(@RequestBody TdsSlabHdDTO tdsSlabHdDto, HttpServletRequest req) {
		logger.info("saveTdsSlabHd is calling");
		TdsSlabHd tdsSlabHd = tdsSlabAdaptor.uiDtoToDatabaseModel(tdsSlabHdDto);
		tdsSlabHd = tdsSlabService.save(tdsSlabHd);
	}

	/**
	 * 
	 * saveTdsSlab}
	 */
	@RequestMapping(path = "/saveTdsSlabHd", method = RequestMethod.POST)
	public List<TdsSlabDTO> saveTdsSlab(@RequestBody List<TdsSlabDTO> tdsSlabDtoList, HttpServletRequest req) {
		logger.info("saveTdsSlabHd is calling" + tdsSlabDtoList);

		for (TdsSlabDTO tdsSlabDTO : tdsSlabDtoList) {
			TdsSlabHd tdsSlabHd = tdsSlabRepository.finddsSlabHd(tdsSlabDTO.getTdsSlabMasterId(),
					tdsSlabDTO.getCompanyId());

			tdsSlabDTO.setTdsSLabHdId(tdsSlabHd.getTdsSLabHdId());
		}
		List<TdsSlab> tdsSlabList = tdsSlabAdaptor.tdsSlabuiDtoToDatabaseModelListTds(tdsSlabDtoList);
		List<TdsSlab> tdsSlabListResult = tdsSlabService.saveTdsSlab(tdsSlabList);
		return null;
	}

	/**
	 * 
	 * findAllTdsSlabHdById}
	 */
	@ApiOperation(value = "findAllTdsSlabHdById  by tdsCategory,companyId")
	@RequestMapping(value = "findBy/{tdsSlabMasterId}/{companyId}/{planType}", method = RequestMethod.GET)
	public @ResponseBody List<TdsSlabDTO> findAllTdsSlabHdById(@PathVariable("tdsSlabMasterId") Long tdsSlabMasterId,
			@PathVariable("companyId") Long companyId,@PathVariable("planType") String planType, HttpServletRequest req) {
		logger.info("findAllTdsSlabHdById is calling :" + "companyId" + companyId + "tdsCategory" + tdsSlabMasterId);
		TdsSlabHd tdsSlabHd1 = tdsSlabRepository.finddsSlabHd(tdsSlabMasterId, companyId);
		return tdsSlabAdaptor
				.tdsSlabdatabaseModelToUiDtoList(tdsSlabService.findAllTdsSlabHdById(tdsSlabHd1.getTdsSLabHdId(), planType));
	}

	/**
	 * 
	 * deleteTdsSlab}
	 */
	@ApiOperation(value = "delete tdsSlab by ID")
	@RequestMapping(path = "/deleteById/{tdsSLabId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTdsSlab(@PathVariable(value = "tdsSLabId") Long tdsSLabId) {
		logger.info("deleteTdsSlab is calling : " + " :" + "....tdsSLabId.." + tdsSLabId);
		tdsSlabService.deleteById(tdsSLabId);
	}

	/**
	 * 
	 * findAllTdsSlabList}
	 */
	@ApiOperation(value = " findAllTdsSlabList  by companyId")
	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TdsSlabHdDTO> findAllTdsSlabList(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findAllTdsSlabList : companyId " + companyId);
		List<TdsSlabHd> tdsSlabHdList = tdsSlabService.getAllTdsSlabHd(companyId);
		if (tdsSlabHdList != null && tdsSlabHdList.size() > 0) {
			return tdsSlabAdaptor.databaseModelToUiDtoList(tdsSlabHdList);
		} else {
			throw new ErrorHandling("Tds Slab List Not Available");
		}
	}

	/**
	 * 
	 * updateTdsSection}
	 */
	@ApiOperation(value = "update updateTdsSlab by Id")
	@RequestMapping(path = "/updateByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTdsSection(@RequestBody TdsSlabDTO tdsSlabDTO, HttpServletRequest req) {
		logger.info("updateTdsSection is calling : " + " :" + "...TdsSLabId.." + tdsSlabDTO.getTdsSLabId());
		TdsSlab tdsSlab = new TdsSlab();
		tdsSlab.setTdsSLabId(tdsSlabDTO.getTdsSLabId());
		tdsSlab.setActiveStatus(tdsSlabDTO.getActiveStatus());

		tdsSlabService.updateByStatus(tdsSlab);
	}
}
