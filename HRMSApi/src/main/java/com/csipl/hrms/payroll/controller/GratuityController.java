package com.csipl.hrms.payroll.controller;

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
import com.csipl.hrms.dto.payroll.GratuityDTO;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.Gratuaty;
import com.csipl.hrms.org.BaseController;
import com.csipl.hrms.service.adaptor.GratuityAdaptor;
import com.csipl.hrms.service.payroll.GratuityService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;

@RequestMapping("/gratuity")
@RestController
public class GratuityController extends BaseController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(GratuityController.class);
	GratuityAdaptor gratuityAdaptor = new GratuityAdaptor();
	@Autowired
	GratuityService gratuityService;
	@Autowired
	ProfessionalTaxService professionalTaxService;
	/**
	 * @param gratuityDto
	 *            This is the first parameter for getting epf Object from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * @throws ErrorHandling 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void saveGratuaty(@RequestBody GratuityDTO gratuityDto, HttpServletRequest req) throws ErrorHandling {
		logger.info("saveGratuaty is calling"+gratuityDto);
		Gratuaty gratuity = gratuityAdaptor.uiDtoToDatabaseModel(gratuityDto);
		boolean payrollAlreadyCreated = professionalTaxService.payrollCheck(gratuityDto.getEffectiveDate(),gratuityDto.getFinancialYearId(), gratuityDto.getCompanyId());

		if (payrollAlreadyCreated) {
			throw new ErrorHandling("Payroll Already Created ");
		}
		if(gratuityDto.getGratuityId() == null) {
				logger.info("gratuity Id is null");
				gratuityService.save(gratuity);
			}else {
				Gratuaty existingGratuity = gratuityService.getAllGratuity(gratuityDto.getCompanyId());
				if (existingGratuity != null && existingGratuity.getEffectiveDate() != null) {
					if(gratuityAdaptor.efectiveDatesEquals(existingGratuity , gratuity)){
						if(existingGratuity.getEffectiveEndDate()!=null && gratuityDto.getEffectiveDate().before(existingGratuity.getEffectiveEndDate())) {
	   						throw new ErrorHandling("Effective Date can not before existing Gratuity Effective End Date ");
	   						}
	   						logger.info("new and existing start date is same so replace existing");
	   						gratuity.setActiveStatus(StatusMessage.ACTIVE_CODE);
	   						gratuity.setEffectiveEndDate(null);
	  					gratuityService.save(gratuity);
					}else {
						  boolean payrollAlreadyCreatedExistingPt = professionalTaxService.payrollCheck( existingGratuity.getEffectiveDate(), gratuityDto.getFinancialYearId() ,gratuityDto.getCompanyId() );
					    	 //create new entry
						  if(payrollAlreadyCreatedExistingPt) {
				    		   if(existingGratuity.getEffectiveEndDate()!=null && gratuityDto.getEffectiveDate().before(existingGratuity.getEffectiveEndDate())) {
									throw new ErrorHandling("Effective Date can not before existing Gratuity Effective End Date ");
								}
				    		   logger.info(" create existing to DE and New to AC ");
				    		   Gratuaty existingEsiDE = gratuityAdaptor.deactivateExistingGratuaty(existingGratuity , gratuityDto);
							   Gratuaty newEsi = gratuityAdaptor.createNewGratuaty(gratuity, gratuityDto);
							   gratuityService.saveGratuatys(existingEsiDE, newEsi);
						  }else {
							//replace existing
								logger.info(" existing payroll no created so replace existing");
								gratuity.setActiveStatus(StatusMessage.ACTIVE_CODE);
		   						gratuity.setEffectiveEndDate(null);
		  					gratuityService.save(gratuity);
						  }	   
					}
					
				}else {
					logger.info(" existing start date is null ");
					if(existingGratuity.getEffectiveEndDate()!=null && gratuityDto.getEffectiveDate().before(existingGratuity.getEffectiveEndDate())) {
						throw new ErrorHandling("Effective Date can not before existing Gratuaty Effective End Date ");
					}
					Gratuaty existingEsiDE = gratuityAdaptor.deactivateExistingGratuaty(existingGratuity , gratuityDto);
					Gratuaty newEsi = gratuityAdaptor.createNewGratuaty(gratuity, gratuityDto);
					gratuityService.saveGratuatys(existingEsiDE, newEsi);
					
				}
			}
		
	}
	/**
	 * to get List of gratuity Objects from database based on companyId
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody  GratuityDTO  findAllGratuity(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws PayRollProcessException {
		logger.info("findAllGratuity is calling");
		return gratuityAdaptor.databaseModelToUiDto(gratuityService.getAllGratuity(companyId));
	}
	
	@RequestMapping(value = "findAllGratuityByDesc/{companyId}", method = RequestMethod.GET)
	public @ResponseBody  List<GratuityDTO>  findAllGratuityByDescEffective(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws PayRollProcessException {
		logger.info("findAllGratuityByDescEffectiveDate is calling");
		return gratuityService.findAllGratuityByDescEffectiveDate(companyId);
	}
	
}
