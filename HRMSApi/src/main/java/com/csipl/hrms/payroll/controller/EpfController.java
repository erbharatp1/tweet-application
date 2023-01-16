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
import com.csipl.hrms.dto.payroll.EpfDTO;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.org.BaseController;
import com.csipl.hrms.service.adaptor.EpfAdaptor;
import com.csipl.hrms.service.payroll.EpfService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;

@RequestMapping("/epf")
@RestController
public class EpfController extends BaseController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EpfController.class);
	EpfAdaptor epfAdaptor=new EpfAdaptor();

	@Autowired
	EpfService epfService;
	@Autowired
	ProfessionalTaxService professionalTaxService;
	/**
	 * @param epfDTO
	 *            This is the first parameter for getting epf Object from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * @throws ErrorHandling 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void  save(@RequestBody EpfDTO epfDTO, HttpServletRequest req) throws ErrorHandling {
		logger.info("save epfDTO is "+epfDTO);
   			Epf epf=epfAdaptor.uiDtoToDatabaseModel(epfDTO);
   			
   		    //check payroll has been created or not
   			boolean payrollAlreadyCreated = professionalTaxService.payrollCheck( epfDTO.getEffectiveDate(), epfDTO.getFinancialYearId() ,epfDTO.getCompanyId() );
   			
   			if(payrollAlreadyCreated){
   				throw new ErrorHandling("Payroll Already Created ");
   			}
   			if(epfDTO.getEpfId() == null) {
   				logger.info("Epf Id is null");
   				epfService.save(epf);
   			}else {
   				Epf existingEpf = epfService.getEPF(epfDTO.getCompanyId() );
   				if (existingEpf != null && existingEpf.getEffectiveDate() != null) {
   				if(epfAdaptor.efectiveDatesEquals(existingEpf , epf)){
   					if(existingEpf.getEffectiveEndDate()!=null && epfDTO.getEffectiveDate().before(existingEpf.getEffectiveEndDate())) {
   						throw new ErrorHandling("Effective Date can not before existing EPF Effective End Date ");
   						}
   						logger.info("new and existing start date is same so replace existing");
   					
  					 epf.setActiveStatus(StatusMessage.ACTIVE_CODE);
  					 epf.setEffectiveEndDate(null);
					 epfService.save(epf);
			       }else{
			    	   boolean payrollAlreadyCreatedExisting = professionalTaxService.payrollCheck( existingEpf.getEffectiveDate(), epfDTO.getFinancialYearId() ,epfDTO.getCompanyId() );
			    	 //create new entry
			    	   if(payrollAlreadyCreatedExisting) {
			    		   if(existingEpf.getEffectiveEndDate()!=null && epfDTO.getEffectiveDate().before(existingEpf.getEffectiveEndDate())) {
								throw new ErrorHandling("Effective Date can not before existing EPF Effective End Date ");
							}
			    		   
			    		   logger.info(" create existing to DE and New to AC ");
			    		   Epf existingEpfDE = epfAdaptor.deactivateExistingEpf(existingEpf , epfDTO);
				    	   Epf newEpf = epfAdaptor.createNewEpf(epf, epfDTO);
				    	   epfService.saveEpfs(existingEpfDE, newEpf);
			    	   }else {
			    		 //replace existing
							logger.info(" existing payroll no created so replace existing");
			    		   epf.setActiveStatus(StatusMessage.ACTIVE_CODE);
		  					 epf.setEffectiveEndDate(null);
							 epfService.save(epf); 
			    	   }
			    	   
			       }
   				
   			}else {
   				if(existingEpf.getEffectiveEndDate()!=null && epfDTO.getEffectiveDate().before(existingEpf.getEffectiveEndDate())) {
					throw new ErrorHandling("Effective Date can not before existing EPF Effective End Date ");
				}
				logger.info(" existing start date is null ");
   				   Epf existingEpfDE = epfAdaptor.deactivateExistingEpf(existingEpf , epfDTO);
		    	   Epf newEpf = epfAdaptor.createNewEpf(epf, epfDTO);
		    	   epfService.saveEpfs(existingEpfDE, newEpf);
   			}
   				
   			}
			
  	}
	/**
	 * to get EPF Object from database based on companyId
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(value = "/{companyId}",method = RequestMethod.GET)
	public @ResponseBody EpfDTO findEpf(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findEpf  is calling : companyId.."+companyId);
		
		Epf epf=epfService.getEPF(companyId );
		if(epf!=null) 
	 		return epfAdaptor.databaseModelToUiDto(epf);
		else {
			logger.info("findEpf  Epf data not present : ");
			throw new ErrorHandling("Epf data not present");
		}
 	}

	@RequestMapping(value = "findAllEpf/{companyId}",method = RequestMethod.GET)
	public @ResponseBody List<EpfDTO> findAllEpf(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("findAllEpf  is calling : companyId.."+companyId);
		
		return epfService.findAllEpfsDescByDate(companyId );
		
 	}
	
}
