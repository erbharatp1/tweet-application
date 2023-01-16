package com.csipl.hrms.service.recruitement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.EarningDeductionEnum;
import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.PayStructureDTO;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.PayOutPK;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOutPK;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;
import com.csipl.hrms.service.employee.PayStructureServiceImpl;
import com.csipl.hrms.service.payroll.EpfService;
import com.csipl.hrms.service.payroll.EsicService;
import com.csipl.hrms.service.payroll.LabourWelfareFundService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;
import com.csipl.hrms.service.recruitement.repository.CandidatePayStructureRepository;
import com.hrms.org.payrollprocess.deduction.CalculationPayHead;
import com.hrms.org.payrollprocess.dto.PayRollProcessDTO;
import com.hrms.org.payrollprocess.dto.PayRollProcessHDDTO;
import com.hrms.org.payrollprocess.loan.LoanCalculation;
import com.hrms.org.payrollprocess.loan.OneTimeCalculation;
import com.hrms.org.payrollprocess.util.PayRollProcessUtil;

@Transactional
@Service("candidatePayStructureService")
public class CandidatePayStructureServiceImpl implements CandidatePayStructureService{
	@Autowired
	PayHeadService payHeadService;
	
	@Autowired
	private EpfService epfService;

	@Autowired
	private EsicService esicService;
	
	@Autowired
	ProfessionalTaxService professionalTaxService;
	
	@Autowired
	LabourWelfareFundService labourWelfareFundService;
	
	@Autowired
	CandidatePayStructureRepository candidatePayStructureRepository;
	
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CandidatePayStructureServiceImpl.class);

	@Override
	public PayStructureHdDTO calculateEarningDeduction(Long companyId, Long employeeId, PayStructureHd payStructureHd,
			Boolean flag, Boolean exitsPaystructurePayheadsFlag) throws PayRollProcessException {
		PayStructureHdDTO payStructureHdDto = new PayStructureHdDTO();
		List<PayOutDTO> payOutDtoList = new ArrayList<PayOutDTO>();
		List<PayStructureDTO> payStructureDtoList = new ArrayList<PayStructureDTO>();
		List<PayOut> payOutList = processPayrollForEarningDeductionView(companyId, employeeId,
				payStructureHd, flag, exitsPaystructurePayheadsFlag);
		for (PayOut payOut : payOutList) {
			if ((StatusMessage.EARNING_CODE).equals(payOut.getEarningDeduction())) {
				PayStructureDTO payStructureDTO = new PayStructureDTO();
				payStructureDTO.setAmount(payOut.getAmount());
				payStructureDTO.setPayHeadId(payOut.getId().getPayHeadId());
				payStructureDTO.setPayHeadName(payOut.getPayHeadName());
				payStructureDtoList.add(payStructureDTO);
			} else {
				PayOutDTO payOutDTO = new PayOutDTO();
				payOutDTO.setAmount(payOut.getAmount());
				payOutDTO.setPayHeadId(payOut.getId().getPayHeadId());
				payOutDTO.setPayHeadName(payOut.getPayHeadName());
				payOutDtoList.add(payOutDTO);
			}
		}
		payStructureHdDto.setPayOutDtoList(payOutDtoList);
		payStructureHdDto.setPayStructureDtoList(payStructureDtoList);
		return payStructureHdDto;
		
	}
	
	public List<PayOut> processPayrollForEarningDeductionView(Long companyId, Long employeeId,
			PayStructureHd payStructureHd, Boolean flag, Boolean exitsPaystructurePayheadsFlag)
			throws PayRollProcessException {
		LOGGER.info(" payStructureHd===== " + "companyId" + companyId + " employeeId " + employeeId
				+ "exitsPaystructurePayheadsFlag...." + exitsPaystructurePayheadsFlag);

		LOGGER.info(" payStructureHd===== " + "companyId" + companyId + " employeeId " + employeeId
				+ "exitsPaystructurePayheadsFlag...." + exitsPaystructurePayheadsFlag);

		List<PayHead> payHeads = new ArrayList<PayHead>();
		// List<PayHead>
		// payHeads=payHeadService.findAllEarnigPaystructurePayHeads(companyId);

		if (exitsPaystructurePayheadsFlag)
			payHeads = payHeadService.findAllPayHeadOfCompany(companyId);
		else
			payHeads = payHeadService.findActivePayHeadOfCompany(companyId);
		// System.out.println(payHeads);
		// payStructureService.processPayrollForEarningDeductionView(companyId,
		// employeeId,payStructureHd);
		List<PayOut> payOuts = processPayRollForEarningDeductionView(companyId, employeeId,
				payStructureHd, flag);

		for (PayOut payOut : payOuts) {
			for (PayHead payHead : payHeads) {
				if (payHead.getPayHeadId() == payOut.getId().getPayHeadId()) {
					payOut.setPayHeadName(payHead.getPayHeadName());
					payOut.setEarningDeduction(payHead.getEarningDeduction());
					// System.out.println("-------------payHead.getPayHeadName()-------"+payHead.getPayHeadName());
					break;
				}
			}
		}

		return payOuts;
		
	}
	public List<PayOut> processPayRollForEarningDeductionView(long companyId, long employeeId,
			PayStructureHd payStructureHd,Boolean flag) throws PayRollProcessException{
		LOGGER.info("processPayRoll employeeId is : " + employeeId);
		List<PayOut> payOuts = new ArrayList<PayOut>();

		//get epf and esi based on effective date ---pragya---
		Epf epf = epfService.getEPFByPayrollPsMonth(payStructureHd.getProcessMonth(),companyId);
		 Esi esi = esicService.getESIByPayrollPsMonth(payStructureHd.getProcessMonth(),companyId);
		//Esi esi = esicService.getActiveESI(companyId);

		// PayrollControl payrollControl =
		// payrollControlService.findPayrollControlByMonth(companyId, payMonth);
		PayrollControl payrollControl = new PayrollControl();
		payrollControl.setProcessMonth("MAR-2018");
		payrollControl.setPayrollDays(31);

		//Employee employee = employeeService.findEmployeesById(employeeId);
		Employee employee = new Employee();
		ReportPayOut reportPayOut = new ReportPayOut();
		reportPayOut.setAbsense(new BigDecimal(0));
		reportPayOut.setPayDays(new BigDecimal(31));
		reportPayOut.setCompanyId(companyId);

		ReportPayOutPK pk = new ReportPayOutPK();
		pk.setProcessMonth("MAR-2018");
		pk.setEmployeeId(employee.getEmployeeId());
		reportPayOut.setId(pk);

		if (employee.getDepartment() != null)
			reportPayOut.setDepartmentId(employee.getDepartment().getDepartmentId());

		if (employee.getCity() != null)
			reportPayOut.setCityId(employee.getCity().getCityId());
		if (employee.getState() != null)
			reportPayOut.setStateId(employee.getState().getStateId());

		reportPayOut.setDateOfJoining(employee.getDateOfJoining());

		
		processSalaryForEmployee(epf, esi, payOuts, reportPayOut, payrollControl, payStructureHd, employee,flag);
		return payOuts;
		
	}
	
	// pay stracture calulation for view only

	private void processSalaryForEmployee(Epf epf, Esi esi, List<PayOut> payOuts, ReportPayOut reportPayOut,
			PayrollControl payrollControl, PayStructureHd payStructureHd, Employee employee,Boolean flagForNewPaystructure) {

		PayRollProcessUtil util = new PayRollProcessUtil();
		BigDecimal netSalary = new BigDecimal(0);
		PayRollProcessHDDTO payRollProcessHDDTO = new PayRollProcessHDDTO();

		LOGGER.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());

		
	
		payRollProcessHDDTO.setPayMonth(payStructureHd.getProcessMonth());
		payRollProcessHDDTO.setEpf(epf);
		payRollProcessHDDTO.setEsi(esi);
		payRollProcessHDDTO.setReportPayOut(reportPayOut);
		payRollProcessHDDTO.setEmployee(employee);
		setProfessionalTax(reportPayOut, payRollProcessHDDTO);
		setLabourWelfareFund(reportPayOut, payRollProcessHDDTO);
		List<PayRollProcessDTO> earningPayStructures = new ArrayList<PayRollProcessDTO>();

		LOGGER.info(" reportPayOut.getId().getEmployeeId() " + reportPayOut.getId().getEmployeeId());

		// calculate All pay structure Based on Earning
		// processEarning(payOuts, reportPayOut, util, payRollProcessHDDTO,
		// earningPayStructures,payStructureHd.getPayStructures(), payrollControl);

		// 99999999999999999
		/*
		 * List<PayOut> payOuts, ReportPayOut reportPayOut, PayRollProcessUtil util,
		 * PayRollProcessHDDTO payRollProcessHDDTO, List<PayRollProcessDTO>
		 * earningPayStructures, List<PayStructure> payStructures, PayrollControl
		 * payrollControl) {
		 * 
		 */

		// 99999

		BigDecimal grossAmount = new BigDecimal(0);
		BigDecimal totalEarningAmount = new BigDecimal(0);
		if (payStructureHd.getPayStructures() != null && payStructureHd.getPayStructures().size() > 0) {

			for (PayStructure payStructure : payStructureHd.getPayStructures()) {

				// payStructure.setPayHead(payHeadService.findPayHeadById(payStructure.getPayHead().getPayHeadId()));

				if (payStructure.getPayHead().getEarningDeduction()
						.equals(EarningDeductionEnum.Earning.getEarningDeductionType())
						&& (StatusMessage.YES_VALUE).equals(payStructure.getPayHead().getPayHeadFlag())) {

					PayRollProcessDTO payRollProcessDTO = new PayRollProcessDTO();

					// PayOut payOut = calcualteEarning(payStructure, reportPayOut, payrollControl);
					PayOut payOut = new PayOut();
					PayOutPK payOutPK = new PayOutPK();
					payOutPK.setEmployeeId(employee.getEmployeeId());
					payOutPK.setPayHeadId(payStructure.getPayHead().getPayHeadId());
					payOutPK.setProcessMonth(reportPayOut.getId().getProcessMonth());

					payOut.setAmount(payStructure.getAmount());
					payOut.setEarningDeduction(payStructure.getPayHead().getEarningDeduction());
					payOut.setId(payOutPK);
					payOut.setEmployee(employee);
					payOut.setPayHeadName(payStructure.getPayHead().getPayHeadName());

					totalEarningAmount = totalEarningAmount.add(payOut.getAmount());
					grossAmount = grossAmount.add(payStructure.getAmount());
					/*
					 * util.fillEarningValueInReportPayOut(reportPayOut,
					 * payStructure.getPayHead().getPayHeadId(), payStructure.getAmount(),
					 * payOut.getAmount());
					 */
					payOuts.add(payOut);
					payRollProcessDTO.setPayOut(payOut);
					payRollProcessDTO.setPayStructure(payStructure);
					earningPayStructures.add(payRollProcessDTO);
				}
			}
			reportPayOut.setTotalEarning(totalEarningAmount);
			reportPayOut.setGrossSalary(grossAmount);
			payRollProcessHDDTO.setListPayRollProcessDTOs(earningPayStructures);
		}

		// 99999999999999999

		// Calculate All Deduction
		if (earningPayStructures != null && earningPayStructures.size() > 0) {
			payRollProcessHDDTO.setTotalGrossSalary(reportPayOut.getGrossSalary());

			calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd, null, null);
			payOuts.addAll(
					calcualteDeduction(payRollProcessHDDTO, reportPayOut, payrollControl, payStructureHd, null, null));
		}

		netSalary = reportPayOut.getGrossSalary() != null
				? reportPayOut.getTotalEarning().subtract(reportPayOut.getTotalDeduction())
				: netSalary;
		netSalary = reportPayOut.getArearAmount() != null ? netSalary.add(reportPayOut.getArearAmount()) : netSalary;
		netSalary = reportPayOut.getOtherEarning() != null ? netSalary.add(reportPayOut.getOtherEarning()) : netSalary;
		reportPayOut.setNetPayableAmount(netSalary);
	}
	
	/**
	 * @param reportPayOut
	 * @param payRollProcessHDDTO
	 */
	private void setProfessionalTax(ReportPayOut reportPayOut, PayRollProcessHDDTO payRollProcessHDDTO) {
		if (reportPayOut.getStateId() != null) {

			ProfessionalTax professionalTax1 = professionalTaxService
					.findProfessionalTaxOfEmployee(reportPayOut.getStateId(), reportPayOut.getCompanyId());
		
			ProfessionalTax professionalTax = professionalTaxService
					.findProfessionalTaxOfEmployeeByProcessMonth(reportPayOut.getStateId(), reportPayOut.getCompanyId(), payRollProcessHDDTO.getPayMonth() );
		
			payRollProcessHDDTO.setProfessionalTax(professionalTax);
		}
	}
	
	private void setLabourWelfareFund(ReportPayOut reportPayOut, PayRollProcessHDDTO payRollProcessHDDTO) {
		if (reportPayOut.getStateId() != null) {
			LabourWelfareFund labourWelfareFund = labourWelfareFundService.findLabourWelfareFundEmployee(reportPayOut.getStateId(), reportPayOut.getCompanyId(),payRollProcessHDDTO.getPayMonth());
			payRollProcessHDDTO.setLabourWelfareFund(labourWelfareFund);
		}
	}
	/**
	 * 
	 * @param payRollProcessHDDTO
	 * @param reportPayOut
	 * @param payrollControl
	 * @param payStructureHd
	 * @param loanIssues
	 * @param oneTimeDeductionList
	 * 
	 * @return
	 */

	private List<PayOut> calcualteDeduction(PayRollProcessHDDTO payRollProcessHDDTO, ReportPayOut reportPayOut,
			PayrollControl payrollControl, PayStructureHd payStructureHd, List<LoanIssue> loanIssues,
			List<OneTimeEarningDeduction> oneTimeDeductionList) {

		CalculationPayHead calculationPayHead = new CalculationPayHead();

		BigDecimal totalDeductionAmount = new BigDecimal(0);

		// Calculate ESI ,EPF,PT

		List<PayOut> dedudtionPayOuts = calculationPayHead.calculationDeduction(payRollProcessHDDTO, payrollControl,
				payStructureHd);

		
	

		for (PayOut payOut : dedudtionPayOuts) {

			if (payOut != null) {
				if (payOut.getId().getPayHeadId() != StandardDeductionEnum.PF_Employer_Contribution
						.getStandardDeduction()
						&& payOut.getId().getPayHeadId() != StandardDeductionEnum.Pension_Employer_Contribution
								.getStandardDeduction()
						&& payOut.getId().getPayHeadId() != StandardDeductionEnum.ESI_Employer_Contribution
								.getStandardDeduction() && payOut.getId().getPayHeadId() != StandardDeductionEnum.LWF_Employer
										.getStandardDeduction()) {

					LOGGER.info(" totalDeductionAmount ====== " + totalDeductionAmount);
					LOGGER.info(" payOut.getAmount()  ====== " + payOut.getAmount());

					if (totalDeductionAmount != null && payOut != null && payOut.getAmount() != null) {
						totalDeductionAmount = totalDeductionAmount.add(payOut.getAmount());
					}

				}
			}

		}

		reportPayOut.setTotalDeduction(totalDeductionAmount);
		return dedudtionPayOuts;
	}

	@Override
	public void saveCandidatePayStructure(CandidatePayStructureHd candidatePaystructurehd) {
		candidatePayStructureRepository.save(candidatePaystructurehd);
		
	}

	@Override
	public CandidatePayStructureHd getCandidatePaystructure(Long intrviewScheduleId) {
		return candidatePayStructureRepository.getCandidatePaystructure(intrviewScheduleId);
		
	}

	
}
