package com.csipl.hrms.recruitement;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payroll.PayHeadDTO;
import com.csipl.hrms.dto.recruitment.CandidatePayStructureHdDTO;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;
import com.csipl.hrms.service.adaptor.PayHeadAdaptor;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.recruitement.CandidatePayStructureService;
import com.csipl.hrms.service.recruitement.adaptor.CandidatePaystructureAdaptor;


@RestController
public class CandidatePaystructureController {
	private static final Logger logger = LoggerFactory.getLogger(CandidatePaystructureController.class);
	@Autowired
	PayHeadService payHeadService;
	
	@Autowired
	CandidatePayStructureService candidatePayStructureService;
	
	
	CandidatePaystructureAdaptor candidatePaystructureAdaptor =new CandidatePaystructureAdaptor();
	
	/*
	 * @pragya 24-07-2020
	 */
	@RequestMapping(path = "/saveCandidatePaystructure",method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void savePayStructureDetails(@RequestBody CandidatePayStructureHdDTO candidatePayStructureHdDto)
			throws ErrorHandling, ParseException, PayRollProcessException {
		CandidatePayStructureHd candidatePayStructureHd = candidatePaystructureAdaptor.uiDtoToDatabaseModel(candidatePayStructureHdDto);
		candidatePayStructureService.saveCandidatePayStructure(candidatePayStructureHd);
		
		//	String processMonth = candidatePayStructureHd.getProcessMonth();
		//Date date = DateUtils.getDateForProcessMonth(processMonth);

//		Date dateOfJoining = payStructureHdDto.getDateOfJoining();
//		if (date != null && dateOfJoining != null) {
//			date.setDate(dateOfJoining.getDate());
//			if (getDate(date).before(getDate(dateOfJoining))) {
//				logger.info("ps month " + date);
//				logger.info("dateOfJoining " + dateOfJoining);
//				throw new PayRollProcessException("Pay structure process month can not before Date of Joining");
//			}
//		}
//
//		DateUtils dateUtils = new DateUtils();
//		Date currentDate = dateUtils.getCurrentDate();
//		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate,
//				payStructureHdDto.getCompanyId());
//		if (financialYear == null) {
//			// we have to find active financialYear first to get created payroll list on
//			// that duration
//			throw new PayRollProcessException("financialYear not found");
//		}
//
//		boolean grossLessthnExisting = professionalTaxService.payrollCheckObj(processMonth,
//				financialYear.getFinancialYearId(), payStructureHdDto.getCompanyId(), payStructureHdDto.getEmployeeId(),
//				payStructureHdDto.getGrossPay());
//		if (grossLessthnExisting) {
//			// can not revise pay because gross pay is lesser thn existing
//			logger.info("Can not revise pay because gross pay is lesser thn existing");
//			// throw new PayRollProcessException("Can Not Define Pay Structure");
//			throw new PayRollProcessException("New Gross Salary cannot be less while creating Arrears. Please check.");
//		}

//		payStructureService.save(payStructureHd, payStructureHdDto.getCompanyId(),
//				payStructureHdDto.getProcessMonthList());
//
//		payStructureService.letterGenerater(payStructureHd, payStructureHdDto.getCompanyId(),
//				payStructureHdDto.getProcessMonthList());
	}
	/**
	 * to get List PayHead objects from database based on companyId and earning
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(path = "/candidatePaystructure", method = RequestMethod.POST)
	public @ResponseBody PayStructureHdDTO getEarningDeductionHeadsBasedOnGross(
			@RequestBody CandidatePayStructureHdDTO candidatePayStructureHdDto)
			throws PayRollProcessException {
		logger.info("getEarningDeductionHeadsBasedOnGross companyId" + 1l + " grossPay" + candidatePayStructureHdDto.getGrossPay());
		PayHeadAdaptor payheadAdaptor = new PayHeadAdaptor();
		List<PayHeadDTO> earningDtoList = new ArrayList<PayHeadDTO>();
		List<PayHead> earningPayHeads = payHeadService.findAllEarnigPaystructurePayHeads(1l);
		Boolean exitsPaystructurePayheadsFlag = false;
		/* if we create new paystructure then flag value will be true */
		Boolean flag = true;
		if (earningPayHeads != null && earningPayHeads.size() > 0)
			earningDtoList = payheadAdaptor.payStractureStanderedCalculation(earningPayHeads, candidatePayStructureHdDto.getGrossPay());

		PayStructureHd payStructureHd = new PayStructureHd();

		List<PayStructure> payStructureList = new ArrayList<PayStructure>();

		for (PayHeadDTO payHeadDto : earningDtoList) {

			PayStructure payStructure = new PayStructure();
			payStructure.setPayHead(payheadAdaptor.uiDtoToDatabaseModel(payHeadDto));
			payStructure.setAmount(payHeadDto.getAmount());
			payStructureList.add(payStructure);
		}
		payStructureHd.setPayStructures(payStructureList);
		payStructureHd.setProcessMonth("JUN-2020");
		
		return candidatePayStructureService.calculateEarningDeduction(1l, 1842l, payStructureHd, flag,
				exitsPaystructurePayheadsFlag);
	}
	
	@RequestMapping(value = { "/candidatePaystructure/{intrviewScheduleId}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public CandidatePayStructureHdDTO getEmployeePayStructure(@PathVariable("intrviewScheduleId") Long intrviewScheduleId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getCandidatePayStructure payStructureHdId is : " + intrviewScheduleId);
		
		CandidatePayStructureHd candidatePayStructureHd =candidatePayStructureService.getCandidatePaystructure(intrviewScheduleId);

		return candidatePaystructureAdaptor.databaseModelToUiDto(candidatePayStructureHd);
		

//		logger.info("pay structre Data not Available");
//		throw new ErrorHandling("Data not Available");
	}
}
