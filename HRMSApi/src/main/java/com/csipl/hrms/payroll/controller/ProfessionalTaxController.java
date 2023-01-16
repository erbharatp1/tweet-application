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
import com.csipl.hrms.dto.organisation.StateDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxInfoDTO;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;
import com.csipl.hrms.org.BaseController;
import com.csipl.hrms.service.adaptor.ProfessionalTaxAdaptor;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/professionalTax")
@RestController
public class ProfessionalTaxController extends BaseController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProfessionalTaxController.class);
	@Autowired
	ProfessionalTaxService professionalTaxService;
	ProfessionalTaxAdaptor professionalTaxAdaptor = new ProfessionalTaxAdaptor();

	/**
	 * @param professionalTaxDto This is the first parameter for getting
	 *                           professionalTax Object from UI
	 * @param req                This is the second parameter to maintain user
	 *                           session
	 * @throws PayRollProcessException
	 */
//	@RequestMapping(method = RequestMethod.POST)
//	public void save(@RequestBody ProfessionalTaxDTO professionalTaxDto, HttpServletRequest req)
//			throws PayRollProcessException {
//		
//		ProfessionalTax professionalTax = professionalTaxAdaptor.uiDtoToDatabaseModel(professionalTaxDto);
//	if (professionalTaxDto.getProfessionalHeadId() == null) {
//			professionalTaxService.save(professionalTax);
//		}
//		else {
//			professionalTaxService.save(professionalTax);
//			professionalTax.setProfessionalHeadId(null);
//			professionalTax.setActiveStatus("AC");
//			professionalTax.setDateCreated(new Date());
//			System.out.println("ProfessionalTaxController.date ()=================="+professionalTax.getDateCreated());
//			for (ProfessionalTaxInfo professionalTaxInfo : professionalTax.getProfessionalTaxInfos()) {
//				professionalTaxInfo.setProfessionalTaxInfoId(null);
//				professionalTaxInfo.setActiveStatus("AC");
//			}
//			professionalTaxService.save(professionalTax);
//		}
//
//	}

	/*
	 * @neeraj 20-MAR-2020
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody ProfessionalTaxDTO professionalTaxDto, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling  {

		ProfessionalTax professionalTax = professionalTaxAdaptor.uiDtoToDatabaseModel(professionalTaxDto);

		//check payroll has been created or not
		boolean payrollAlreadyCreated = professionalTaxService.payrollCheck( professionalTaxDto.getEffectiveStartDate(), professionalTaxDto.getFinancialYearId() ,professionalTaxDto.getCompanyId() );
		if(payrollAlreadyCreated){
			throw new ErrorHandling("Payroll Already Created ");
		}
		
		if (professionalTaxDto.getProfessionalHeadId() == null) {
			logger.info("ProfessionalHeadId is null");
			professionalTaxService.save(professionalTax);
		} else {
			ProfessionalTax existingProfessionalTax = professionalTaxService
					.findProfessionalTax(professionalTaxDto.getProfessionalHeadId(), professionalTaxDto.getCompanyId());
			
			
			
			if (existingProfessionalTax != null && existingProfessionalTax.getEffectiveStartDate() != null) {
				if (professionalTaxAdaptor.efectiveDatesEquals(existingProfessionalTax, professionalTax)) {
					
					if(existingProfessionalTax.getEffectiveEndDate()!=null && professionalTaxDto.getEffectiveStartDate().before(existingProfessionalTax.getEffectiveEndDate())) {
					throw new ErrorHandling("Effective Date can not before existing PT Effective End Date ");
					}
					logger.info("new and existing start date is same so replace existing");
					ProfessionalTax acProfessionalTax = professionalTaxAdaptor.activateProfessionalTax(professionalTax);
					professionalTaxService.save(acProfessionalTax);
				}else {
					boolean payrollAlreadyCreatedExistingPt = professionalTaxService.payrollCheck( existingProfessionalTax.getEffectiveStartDate(), professionalTaxDto.getFinancialYearId() ,professionalTaxDto.getCompanyId() );
					
					if(payrollAlreadyCreatedExistingPt) {
						//create new entry
						if(existingProfessionalTax.getEffectiveEndDate()!=null && professionalTaxDto.getEffectiveStartDate().before(existingProfessionalTax.getEffectiveEndDate())) {
							throw new ErrorHandling("Effective Date can not before existing PT Effective End Date ");
						}else {
							logger.info(" create existing to DE and New to AC ");
							ProfessionalTax deProfessionalTax = professionalTaxAdaptor.deactivateProfessionalTax(existingProfessionalTax, professionalTaxDto);
							deProfessionalTax.setActiveStatus("DE");
							professionalTaxService.save(deProfessionalTax);
							ProfessionalTax newProfessionalTax = professionalTaxAdaptor.createNewProfessionalTax(professionalTax, professionalTaxDto);
							professionalTaxService.save(newProfessionalTax);
						}
					}else {
						//replace existing
						logger.info(" existing payroll no created so replace existing");
						ProfessionalTax acProfessionalTax = professionalTaxAdaptor.activateProfessionalTax(professionalTax);
						professionalTaxService.save(acProfessionalTax);
					}
					
				}
				
			}
			else {
				if(existingProfessionalTax.getEffectiveEndDate()!=null && professionalTaxDto.getEffectiveStartDate().before(existingProfessionalTax.getEffectiveEndDate())) {
					throw new ErrorHandling("Effective Date can not before existing PT Effective End Date ");
				}
				logger.info(" existing start date is null ");
			ProfessionalTax deProfessionalTax = professionalTaxAdaptor.deactivateProfessionalTax(professionalTax, professionalTaxDto);
			professionalTaxService.save(deProfessionalTax);
			ProfessionalTax newProfessionalTax = professionalTaxAdaptor.createNewProfessionalTax(professionalTax, professionalTaxDto);
			professionalTaxService.save(newProfessionalTax);
		}
		}

	}

	/**
	 * to get List of ProfessionalTax objects from database
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<ProfessionalTaxDTO> findAllProfessionalTax(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		List<ProfessionalTax> professionalTaxs = professionalTaxService.getAllProfessionalTax(companyId);
		if (professionalTaxs != null && professionalTaxs.size() > 0)
			return professionalTaxAdaptor.databaseModelToUiDtoList(professionalTaxs);
		else
			throw new ErrorHandling("ProfessionalTax data not present");
	}

	/**
	 * to get ProfessionalTax object from database proTaxId (Primary Key)
	 * 
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@RequestMapping(path = "/{proTaxId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody ProfessionalTaxDTO findProfessionalTax(@PathVariable("proTaxId") Long proTaxId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling {
		
		ProfessionalTax professionalTax = professionalTaxService.findProfessionalTax(proTaxId, companyId);
		if (professionalTax != null)
			return professionalTaxAdaptor.databaseModelToUiDto(professionalTax);
		else
			throw new ErrorHandling("ProfessionalTax data not present");
	}

	/**
	 * to get List of State objects from database
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "findById/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<StateDTO> findAllStateById(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {
		return professionalTaxAdaptor.statedatabaseModelToUiDtoList(professionalTaxService.findAllState(companyId));
	}

	@ApiOperation(value = "update ProfessionalTaxInfo by status")
	@RequestMapping(path = "/updateByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTdsSection(@RequestBody ProfessionalTaxInfoDTO professionalTaxInfoDTO, HttpServletRequest req) {
		logger.info("ProfessionalTaxController.updateTdsSection()" + professionalTaxInfoDTO.getProfessionalTaxInfoId());
		ProfessionalTaxInfo professionalTaxInfo = new ProfessionalTaxInfo();
		professionalTaxInfo.setProfessionalTaxInfoId(professionalTaxInfoDTO.getProfessionalTaxInfoId());
		professionalTaxInfo.setActiveStatus(professionalTaxInfoDTO.getActiveStatus());
		logger.info("ProfessionalTaxController.ActiveStatus--->>()" + professionalTaxInfoDTO.getActiveStatus());
		professionalTaxService.updateByActiveStatus(professionalTaxInfo);
	}
	
	/*
	 * @neeraj 20-APR-2020
	 */
	@RequestMapping(path = "findAllPTDescDate/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<ProfessionalTaxDTO>  findAllPTDescDate(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		return professionalTaxService.findAllPTDescByDate(companyId);
	}
	/*
	 * @neeraj 20-APR-2020
	 */
	@RequestMapping(path = "findPTByStateDescDated/{companyId}/{professionalHeadId}", method = RequestMethod.GET)
	public @ResponseBody List<ProfessionalTaxDTO>  findAllPTByStateDescDate2(@PathVariable("companyId") Long companyId,
			@PathVariable("professionalHeadId") Long professionalHeadId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		return professionalTaxService.findAllPTStateIdDescByDate(companyId , professionalHeadId);
	}
	
	@RequestMapping(path = "findAllPTByProfessionalTaxIdDes/{companyId}/{professionalTaxId}", method = RequestMethod.GET)
	public @ResponseBody ProfessionalTaxDTO  findAllPTByStateDescDate(@PathVariable("companyId") Long companyId,
			@PathVariable("professionalTaxId") Long professionalTaxId,HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		ProfessionalTax professionalTax= professionalTaxService.findAllPTProfessionalTaxIdDescByDate(professionalTaxId,companyId);
		 return professionalTaxAdaptor.databaseModelToUiDtoAll(professionalTax);
	}
}
