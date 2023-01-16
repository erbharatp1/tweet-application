package com.csipl.hrms.payroll.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.EsiDTO;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.EsiCycle;
import com.csipl.hrms.service.adaptor.EsicAdaptor;
import com.csipl.hrms.service.payroll.EsicService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;

@RequestMapping("/esic")
@RestController
public class EsicController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EsicController.class);
	EsicAdaptor esicAdaptor = new EsicAdaptor();
	boolean status = false;
	
	@Autowired
	EsicService esicService;
	@Autowired
	ProfessionalTaxService professionalTaxService;

	/**
	 * @param esiDTO This is the first parameter for getting epf Object from UI
	 * @param req    This is the second parameter to maintain user session
	 * @throws ErrorHandling
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void EsicSave(@RequestBody EsiDTO esiDTO, HttpServletRequest req) throws ErrorHandling {
		logger.info("EsicSave is calling===================" + esiDTO.getEffectiveDate());
		Esi esic = esicAdaptor.uiDtoToDatabaseModel(esiDTO);
		// check payroll has been created or not
		boolean payrollAlreadyCreated = professionalTaxService.payrollCheck(esiDTO.getEffectiveDate(),
				esiDTO.getFinancialYearId(), esiDTO.getCompanyId());

		if (payrollAlreadyCreated) {
			throw new ErrorHandling("Payroll Already Created ");
		}

		if (esiDTO.getEsiId() == null) {
			logger.info("ESI Id is null");
			esicService.save(esic);
		} else {

			Esi existingEsic = esicService.getAllESI(esiDTO.getCompanyId());
			if (existingEsic != null && existingEsic.getEffectiveDate() != null) {
				if (esicAdaptor.efectiveDatesEquals(existingEsic , esic)) {
					if(existingEsic.getEffectiveEndDate()!=null && esiDTO.getEffectiveDate().before(existingEsic.getEffectiveEndDate())) {
   						throw new ErrorHandling("Effective Date can not before existing ESI Effective End Date ");
   						}
   						logger.info("new and existing start date is same so replace existing");
					
					esic.setActiveStatus(StatusMessage.ACTIVE_CODE);
					esic.setEffectiveEndDate(null);
					esicService.save(esic);
				} else {
					 boolean payrollAlreadyCreatedExistingEsi = professionalTaxService.payrollCheck( existingEsic.getEffectiveDate(), esiDTO.getFinancialYearId() ,esiDTO.getCompanyId() );
			    	 //create new entry
					 if(payrollAlreadyCreatedExistingEsi) {
					 if(existingEsic.getEffectiveEndDate()!=null && esiDTO.getEffectiveDate().before(existingEsic.getEffectiveEndDate())) {
							throw new ErrorHandling("Effective Date can not before existing ESI Effective End Date ");
						}

		    		   logger.info(" create existing to DE and New to AC ");
		    		   Esi existingEsiDE = esicAdaptor.deactivateExistingEsi(existingEsic , esiDTO);
		    		   Esi newEsi = esicAdaptor.createNewEsi(esic, esiDTO);
		    		   esicService.saveEsics(existingEsiDE, newEsi);
					   
					 }else {
						//replace existing
						 logger.info(" existing payroll no created so replace existing");
						 esic.setActiveStatus(StatusMessage.ACTIVE_CODE);
						 esic.setEffectiveEndDate(null);
						 esicService.save(esic);
					 }
					
				}
			}else {
				if(existingEsic.getEffectiveEndDate()!=null && esiDTO.getEffectiveDate().before(existingEsic.getEffectiveEndDate())) {
					throw new ErrorHandling("Effective Date can not before existing ESIC Effective End Date ");
				}
				logger.info(" existing start date is null ");
				Esi existingEsiDE = esicAdaptor.deactivateExistingEsi(existingEsic , esiDTO);
	    		Esi newEsi = esicAdaptor.createNewEsi(esic, esiDTO);
	    		esicService.saveEsics(existingEsiDE, newEsi);
			}
		}
	}

	/**
	 * to get Esi Object from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
//	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
//	public @ResponseBody EsiDTO findESIC(@PathVariable("companyId") Long companyId, HttpServletRequest req)
//			throws ErrorHandling, PayRollProcessException {
//		logger.info("findESIC is calling");
//		Esi esi = esicService.getESI(companyId);
//		if (esi != null)
//			return esicAdaptor.databaseModelToUiDto(esicService.getESI(companyId));
//		else {
//			logger.info("Esi data not present");
//			throw new ErrorHandling("Esi data not present");
//		}
//	}

	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody EsiDTO findAllEsic(@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		Esi esic = esicService.getAllESI(companyId);
		return esicAdaptor.databaseModelToUiDto(esic);

	}

	@RequestMapping(path = "findAllEsicDescDate/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EsiDTO> findAllEsicDescDate(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		return esicService.findAllEsiDescByDate(companyId);
	}
}
