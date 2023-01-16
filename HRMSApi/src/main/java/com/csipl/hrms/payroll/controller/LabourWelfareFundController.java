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
import com.csipl.hrms.dto.payroll.LabourWelfareFundDTO;
import com.csipl.hrms.dto.payroll.LabourWelfareFundInfoDTO;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;
import com.csipl.hrms.service.adaptor.LabourWelfareFundAdaptor;
import com.csipl.hrms.service.payroll.LabourWelfareFundService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/labourWelfareFund")
@RestController
public class LabourWelfareFundController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(LabourWelfareFundController.class);

	@Autowired
	LabourWelfareFundService labourWelfareFundService;

	LabourWelfareFundAdaptor labourWelfareFundAdaptor = new LabourWelfareFundAdaptor();
	@Autowired
	ProfessionalTaxService professionalTaxService;

	/**
	 * 
	 * @param labourWelfareFundDto
	 * @param req
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
//	@RequestMapping(method = RequestMethod.POST)
//	public void save(@RequestBody LabourWelfareFundDTO labourWelfareFundDto, HttpServletRequest req)
//			throws PayRollProcessException {
//		logger.info("LabourWelfareFundDTO.save()========" + labourWelfareFundDto.getEffectiveStartDate());
//		LabourWelfareFund labourWelfareFund = new LabourWelfareFund();
//		if (labourWelfareFundDto.getLabourWelfareFundHeadId() == null) {
//			labourWelfareFund = labourWelfareFundAdaptor.uiDtoToDatabaseModel(labourWelfareFundDto);
//			labourWelfareFundService.save(labourWelfareFund);
//		} else {
//			labourWelfareFund = labourWelfareFundAdaptor.uiDtoToDatabaseModel(labourWelfareFundDto);
//			labourWelfareFundService.save(labourWelfareFund);
//			labourWelfareFund.setLabourWelfareFundHeadId(null);
//			labourWelfareFund.setActiveStatus("AC");
//			labourWelfareFund.setDateCreated(new Date());
//			logger.info("LabourWelfareFundDTO.date ()==================" + labourWelfareFund.getDateCreated());
//			for (LabourWelfareFundInfo labourIds : labourWelfareFund.getLabourWelfareFundInfos()) {
//				labourIds.setLabourWelfareFundInfoId(null);
//				labourIds.setActiveStatus("AC");
//			}
//
//			labourWelfareFundService.save(labourWelfareFund);
//		}
//
//	}

	/*
	 * @neeraj 25-MAR-2020
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody LabourWelfareFundDTO labourWelfareFundDto, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling {

		LabourWelfareFund labourWelfareFund = labourWelfareFundAdaptor.uiDtoToDatabaseModel(labourWelfareFundDto);
		// check payroll has been created or not
		boolean payrollAlreadyCreated = professionalTaxService.payrollCheck(labourWelfareFundDto.getEffectiveStartDate(), labourWelfareFundDto.getFinancialYearId(),labourWelfareFundDto.getCompanyId());
		if (payrollAlreadyCreated) {
			throw new ErrorHandling("Payroll Already Created ");
		}
		if (labourWelfareFundDto.getLabourWelfareFundHeadId() == null) {
			labourWelfareFundService.save(labourWelfareFund);
		} else {
			LabourWelfareFund existingLabourWelfare = labourWelfareFundService.findLabourWelfareFund(labourWelfareFundDto.getLabourWelfareFundHeadId(), labourWelfareFundDto.getCompanyId());

			if (existingLabourWelfare != null && existingLabourWelfare.getEffectiveStartDate() != null) {

				if (labourWelfareFundAdaptor.efectiveDatesEquals(existingLabourWelfare, labourWelfareFund)) {
					if (existingLabourWelfare.getEffectiveEndDate() != null && existingLabourWelfare.getEffectiveStartDate().before(existingLabourWelfare.getEffectiveEndDate())) {
						throw new ErrorHandling("Effective Date can not before existing LWF Effective End Date ");
					}

					logger.info("new and existing start date is same so replace existing");
					LabourWelfareFund acLabourWelfareFund = labourWelfareFundAdaptor.activateLabourWelfareFund(labourWelfareFund);
					labourWelfareFundService.save(acLabourWelfareFund);
				} else {
					boolean payrollAlreadyCreatedExistingLwf = professionalTaxService.payrollCheck(existingLabourWelfare.getEffectiveStartDate(), labourWelfareFundDto.getFinancialYearId(),labourWelfareFundDto.getCompanyId());
					if (payrollAlreadyCreatedExistingLwf) {
						// create new entry
						if (existingLabourWelfare.getEffectiveEndDate() != null && labourWelfareFundDto.getEffectiveStartDate().before(existingLabourWelfare.getEffectiveEndDate())) {
							throw new ErrorHandling("Effective Date can not before existing LWF Effective End Date ");
						}
						logger.info(" create existing to DE and New to AC ");
						LabourWelfareFund deLabourWelfareFund = labourWelfareFundAdaptor.deactivateLabourWelfareFund(existingLabourWelfare, labourWelfareFundDto);
						deLabourWelfareFund.setActiveStatus("DE");
						labourWelfareFundService.save(deLabourWelfareFund);
						LabourWelfareFund newLabourWelfareFund = labourWelfareFundAdaptor.createNewLabourWelfareFund(labourWelfareFund, labourWelfareFundDto);
						labourWelfareFundService.save(newLabourWelfareFund);
					} else {
						// replace existing
						logger.info(" existing payroll no created so replace existing");
						LabourWelfareFund acLabourWelfareFund = labourWelfareFundAdaptor.activateLabourWelfareFund(labourWelfareFund);
						labourWelfareFundService.save(acLabourWelfareFund);
					}

				}

			} else {
				if (existingLabourWelfare.getEffectiveEndDate() != null && labourWelfareFundDto.getEffectiveStartDate().before(existingLabourWelfare.getEffectiveEndDate())) {
					throw new ErrorHandling("Effective Date can not before existing LWF Effective End Date ");
				}
				logger.info(" existing start date is null ");
				LabourWelfareFund deLabourWelfareFund = labourWelfareFundAdaptor.deactivateLabourWelfareFund(labourWelfareFund, labourWelfareFundDto);
				labourWelfareFundService.save(deLabourWelfareFund);
				LabourWelfareFund newLabourWelfareFund = labourWelfareFundAdaptor.createNewLabourWelfareFund(labourWelfareFund, labourWelfareFundDto);
				labourWelfareFundService.save(newLabourWelfareFund);
			}
		}

	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<LabourWelfareFundDTO> findAllLabourWelfareFund(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("LabourWelfareFundController.findAllLabourWelfareFund()");
		return labourWelfareFundAdaptor
				.databaseModelToUiDtoList(labourWelfareFundService.getAllLabourWelfareFund(companyId));
	}

	/**
	 * 
	 * @param labourWelfareFundHeadId
	 * @param companyId
	 * @param req
	 * @return
	 * @throws PayRollProcessException
	 * @throws ErrorHandling
	 */
	@RequestMapping(path = "/{labourWelfareFundHeadId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody LabourWelfareFundDTO findLabourWelfareFund(
			@PathVariable("labourWelfareFundHeadId") Long labourWelfareFundHeadId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws PayRollProcessException, ErrorHandling {
		logger.info("LabourWelfareFundController findLabourWelfareFund " + labourWelfareFundHeadId);
		LabourWelfareFund labourWelfare = labourWelfareFundService.findLabourWelfareFund(labourWelfareFundHeadId,
				companyId);
		if (labourWelfare != null)
			return labourWelfareFundAdaptor.databaseModelToUiDto(labourWelfare);
		else
			throw new ErrorHandling("labourWelfareFund data not present");
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "findById/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<StateDTO> findAllStateById(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {
		return labourWelfareFundAdaptor.statedatabaseModelToUiDtoList(labourWelfareFundService.findAllState(companyId));
	}

	/**
	 * 
	 * @param labourWelfareFundInfoDTO
	 * @param req
	 */
	@ApiOperation(value = "update LabourWelfareFundInfo by status")
	@RequestMapping(path = "/updateByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updatelabourWelfareFund(@RequestBody LabourWelfareFundInfoDTO labourWelfareFundInfoDTO,
			HttpServletRequest req) {
		logger.info("updatelabourWelfareFund.()" + labourWelfareFundInfoDTO.getLabourWelfareFundInfoId());
		LabourWelfareFundInfo labourWelfareFundInfo = new LabourWelfareFundInfo();
		labourWelfareFundInfo.setLabourWelfareFundInfoId(labourWelfareFundInfoDTO.getLabourWelfareFundInfoId());
		labourWelfareFundInfo.setActiveStatus(labourWelfareFundInfoDTO.getActiveStatus());
		logger.info("updatelabourWelfareFund.ActiveStatus--->>()" + labourWelfareFundInfoDTO.getActiveStatus());
		labourWelfareFundService.updateByActiveStatus(labourWelfareFundInfo);
	}

	@RequestMapping("/updateApplicableStatus/{applicableStatus}")
	public void updateApplicableStatus(@PathVariable String applicableStatus, HttpServletRequest req) {
		System.out.println("LWF applicableStatus===" + applicableStatus);
		labourWelfareFundService.updateApplicableStatus(applicableStatus);

	}

	@RequestMapping("/activeCount/{companyId}")
	public int getActiveCount(@PathVariable Long companyId, HttpServletRequest req) {
		System.out.println("LWF applicableStatus===" + companyId);
		return labourWelfareFundService.getActiveCount(companyId);

	}

	/*
	 * @neeraj 20-APR-2020
	 */
	@RequestMapping(path = "findAllLWFDescDate/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<LabourWelfareFundDTO> findAllLWFDescDate(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {

		return labourWelfareFundService.findAllLWFDescByDate(companyId);
	}

	/*
	 * @neeraj 20-APR-2020
	 */
	@RequestMapping(path = "findAllLWFByStateDescDate/{companyId}/{stateId}", method = RequestMethod.GET)
	public @ResponseBody List<LabourWelfareFundDTO> findAllLWFByStateDescDate(@PathVariable("companyId") Long companyId,
			@PathVariable("stateId") Long stateId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {

		return labourWelfareFundService.findAllLWFStateIdDescByDate(companyId, stateId);
	}
	
	@RequestMapping(path = "findAllLWFById/{companyId}/{labourWelfareFundHeadId}", method = RequestMethod.GET)
	public @ResponseBody LabourWelfareFundDTO findAllLWFById(@PathVariable("companyId") Long companyId,
			@PathVariable("labourWelfareFundHeadId") Long labourWelfareFundHeadId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		return labourWelfareFundAdaptor.databaseModelToUiDtoAll(labourWelfareFundService.findAllLWFById(companyId, labourWelfareFundHeadId));
	}
	
}


