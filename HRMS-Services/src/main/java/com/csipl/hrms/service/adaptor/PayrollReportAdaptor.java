package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.enums.RecordPaymentEnum;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.dto.payroll.ArrearReportPayOutDTO;
import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.OneTimeEarningDeductionDTO;
import com.csipl.hrms.dto.payroll.TdsHouseRentInfoDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportSummaryDTO;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public class PayrollReportAdaptor {
	public List<ReportPayOutDTO> objectListToReportPayoutList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();
			// Long
			// tdsGroupId=reportPayoutObj[0]!=null?Long.parseLong(tdsTransactionObj[0].toString()):null;
			// Long
			// tdsSectionId=tdsTransactionObj[1]!=null?Long.parseLong(tdsTransactionObj[1].toString()):null;
			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String departmentName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String desginationName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			String processMonth = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;
			BigDecimal pt = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;
			String stateName = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutDto.setDesignationName(desginationName);
			reportPayoutDto.setProcessMonth(processMonth);
			reportPayoutDto.setPt(pt);
			reportPayoutDto.setStateName(stateName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToProvisionReportList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			// tdsGroupId=reportPayoutObj[0]!=null?Long.parseLong(tdsTransactionObj[0].toString()):null;
			// Long
			// tdsSectionId=tdsTransactionObj[1]!=null?Long.parseLong(tdsTransactionObj[1].toString()):null;
			String employeeName = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeCode = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String bankName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String accountNo = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;

			BigDecimal netAmount = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString())) : null;

			Date dateCreated = reportPayoutObj[5] != null ? (Date) reportPayoutObj[5] : null;
			String departmentName = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNo);
			reportPayoutDto.setNetPayableAmount(netAmount);
			reportPayoutDto.setProvisionDateCreated(dateCreated);
			reportPayoutDto.setEmpDetp(departmentName);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToMonthlyReportList(List<Object[]> monthlyReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : monthlyReportList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeName = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeCode = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String bankName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String accountNo = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			/*
			 * if (reportPayoutObj[4] != null) netAmount = reportPayoutObj[4] != null ? (new
			 * BigDecimal(reportPayoutObj[4].toString())) : null; else netAmount = new
			 * BigDecimal(0);
			 */
			Date dateOfJoining = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;
			BigDecimal basic = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;
			BigDecimal da = reportPayoutObj[6] != null ? (new BigDecimal(reportPayoutObj[6].toString())) : null;
			BigDecimal conveyanceAllowance = reportPayoutObj[7] != null
					? (new BigDecimal(reportPayoutObj[7].toString()))
					: null;
			BigDecimal employeeHRA = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString()))
					: null;
			BigDecimal medicaAllowance = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString()))
					: null;
			BigDecimal advanceBonus = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			BigDecimal specialAllowance = reportPayoutObj[11] != null ? (new BigDecimal(reportPayoutObj[11].toString()))
					: null;
			BigDecimal companyBenifits = reportPayoutObj[12] != null ? (new BigDecimal(reportPayoutObj[12].toString()))
					: null;
			BigDecimal otherAllowance = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
					: null;
			BigDecimal totelGrassSalary = reportPayoutObj[14] != null ? (new BigDecimal(reportPayoutObj[14].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[15] != null ? (new BigDecimal(reportPayoutObj[15].toString())) : null;
			BigDecimal casualLeave = reportPayoutObj[16] != null ? (new BigDecimal(reportPayoutObj[16].toString()))
					: null;
			BigDecimal sickLeave = reportPayoutObj[17] != null ? (new BigDecimal(reportPayoutObj[17].toString()))
					: null;
			BigDecimal paidLeave = reportPayoutObj[18] != null ? (new BigDecimal(reportPayoutObj[18].toString()))
					: null;
			BigDecimal prsent = reportPayoutObj[19] != null ? (new BigDecimal(reportPayoutObj[19].toString())) : null;
			BigDecimal publicHoliday = reportPayoutObj[20] != null ? (new BigDecimal(reportPayoutObj[20].toString()))
					: null;
			BigDecimal weeklyOff = reportPayoutObj[21] != null ? (new BigDecimal(reportPayoutObj[21].toString()))
					: null;
			BigDecimal overTime = reportPayoutObj[22] != null ? (new BigDecimal(reportPayoutObj[22].toString())) : null;
			BigDecimal payDays = reportPayoutObj[23] != null ? (new BigDecimal(reportPayoutObj[23].toString())) : null;
			BigDecimal basicEarning = reportPayoutObj[24] != null ? (new BigDecimal(reportPayoutObj[24].toString()))
					: null;
			BigDecimal daEarning = reportPayoutObj[25] != null ? (new BigDecimal(reportPayoutObj[25].toString()))
					: null;
			BigDecimal conveyanceEarning = reportPayoutObj[26] != null
					? (new BigDecimal(reportPayoutObj[26].toString()))
					: null;
			BigDecimal employeeHraEarning = reportPayoutObj[27] != null
					? (new BigDecimal(reportPayoutObj[27].toString()))
					: null;
			BigDecimal medicalAllowEarning = reportPayoutObj[28] != null
					? (new BigDecimal(reportPayoutObj[28].toString()))
					: null;
			BigDecimal addvanceBonusEarning = reportPayoutObj[29] != null
					? (new BigDecimal(reportPayoutObj[29].toString()))
					: null;
			BigDecimal specialAllEarning = reportPayoutObj[30] != null
					? (new BigDecimal(reportPayoutObj[30].toString()))
					: null;
			BigDecimal companyBenifitsEarning = reportPayoutObj[31] != null
					? (new BigDecimal(reportPayoutObj[31].toString()))
					: null;
			BigDecimal otherAlloEarning = reportPayoutObj[32] != null ? (new BigDecimal(reportPayoutObj[32].toString()))
					: null;
			BigDecimal totelEarning = reportPayoutObj[33] != null ? (new BigDecimal(reportPayoutObj[33].toString()))
					: null;
			BigDecimal empLoan = reportPayoutObj[34] != null ? (new BigDecimal(reportPayoutObj[34].toString())) : null;
			BigDecimal PF = reportPayoutObj[35] != null ? (new BigDecimal(reportPayoutObj[35].toString())) : null;
			BigDecimal ESIC = reportPayoutObj[36] != null ? (new BigDecimal(reportPayoutObj[36].toString())) : null;
			BigDecimal PT = reportPayoutObj[37] != null ? (new BigDecimal(reportPayoutObj[37].toString())) : null;
			BigDecimal TDS = reportPayoutObj[38] != null ? (new BigDecimal(reportPayoutObj[38].toString())) : null;
			BigDecimal totelDeduction = reportPayoutObj[39] != null ? (new BigDecimal(reportPayoutObj[39].toString()))
					: null;
			BigDecimal netAmount = reportPayoutObj[40] != null ? (new BigDecimal(reportPayoutObj[40].toString()))
					: null;
			String departmentName = reportPayoutObj[41] != null ? (String) reportPayoutObj[41] : null;
			// String departmentName = reportPayoutObj[6] != null ? (String)
			// reportPayoutObj[6] : null;
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNo);
			reportPayoutDto.setDateOfJoining(dateOfJoining);
			reportPayoutDto.setNetPayableAmount(netAmount);
			reportPayoutDto.setBasic(basic);
			reportPayoutDto.setDearnessAllowance(da);
			reportPayoutDto.setConveyanceAllowance(conveyanceAllowance);
			reportPayoutDto.setHra(employeeHRA);
			reportPayoutDto.setMedicalAllowance(medicaAllowance);
			reportPayoutDto.setAdvanceBonus(advanceBonus);
			reportPayoutDto.setSpecialAllowance(specialAllowance);
			reportPayoutDto.setCompanyBenefits(companyBenifits);
			reportPayoutDto.setOtherAllowance(otherAllowance);
			reportPayoutDto.setGrossSalary(totelGrassSalary);
			;
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setCasualleave(casualLeave);
			reportPayoutDto.setSeekleave(sickLeave);
			reportPayoutDto.setPaidleave(paidLeave);
			reportPayoutDto.setPresense(prsent);
			reportPayoutDto.setPublicholidays(publicHoliday);
			reportPayoutDto.setWeekoff(weeklyOff);
			reportPayoutDto.setOvertime(overTime);
			reportPayoutDto.setPayDays(payDays);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setDearnessAllowanceEarning(daEarning);
			reportPayoutDto.setConveyanceAllowanceEarning(conveyanceEarning);
			reportPayoutDto.setHraEarning(employeeHraEarning);
			reportPayoutDto.setMedicalAllowanceEarning(medicalAllowEarning);
			reportPayoutDto.setAdvanceBonusEarning(addvanceBonusEarning);
			reportPayoutDto.setSpecialAllowanceEarning(specialAllEarning);
			reportPayoutDto.setCompanyBenefitsEarning(companyBenifitsEarning);
			reportPayoutDto.setOtherAllowanceEarning(otherAlloEarning);
			reportPayoutDto.setTotalEarning(totelEarning);
			reportPayoutDto.setEmployeeLoansAdvnaceEarning(empLoan);
			reportPayoutDto.setProvidentFundEmployee(PF);
			reportPayoutDto.setEsi_Employee(ESIC);
			reportPayoutDto.setPt(PT);
			reportPayoutDto.setTds(TDS);
			reportPayoutDto.setTotalDeduction(totelDeduction);
			reportPayoutDto.setNetPayableAmount(netAmount);
			// reportPayoutDto.setProvisionDateCreated(dateCreated);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToBankReportList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayOutDto = new ReportPayOutDTO();
			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String name = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String bankName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String accountNumber = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			BigDecimal netPayableAmount = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString()))
					: null;
			String processMonth = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			String departmentName = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			reportPayOutDto.setEmployeeCode(employeeCode);
			reportPayOutDto.setName(name);
			reportPayOutDto.setBankName(bankName);
			reportPayOutDto.setAccountNumber(accountNumber);
			reportPayOutDto.setNetPayableAmount(netPayableAmount);
			reportPayOutDto.setProcessMonth(processMonth);
			reportPayOutDto.setDepartmentName(departmentName);
			reportPayoutList.add(reportPayOutDto);
		}

		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToRecoReportList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayOutDto = new ReportPayOutDTO();
			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String name = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String bankName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String accountNumber = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			BigDecimal netPayableAmount = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString()))
					: null;
			String processMonth = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			String departmentName = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			String transactionNo = reportPayoutObj[7] != null ? (String) reportPayoutObj[7] : null;
			Date recoDate = reportPayoutObj[8] != null ? (Date) reportPayoutObj[8] : null;
			reportPayOutDto.setEmployeeCode(employeeCode);
			reportPayOutDto.setName(name);
			reportPayOutDto.setBankName(bankName);
			reportPayOutDto.setAccountNumber(accountNumber);
			reportPayOutDto.setNetPayableAmount(netPayableAmount);
			reportPayOutDto.setProcessMonth(processMonth);
			reportPayOutDto.setDepartmentName(departmentName);
			reportPayOutDto.setReconciliationDate(recoDate);
			reportPayOutDto.setTransactionNo(transactionNo);

			System.out.println("EMP CODE" + reportPayOutDto.getEmployeeCode());
			System.out.println("EMP Name" + reportPayOutDto.getName());
			System.out.println("EMP BankName" + reportPayOutDto.getBankName());
			System.out.println("EMP AccountNumber" + reportPayOutDto.getAccountNumber());
			System.out.println("EMP ProcessMonth" + reportPayOutDto.getProcessMonth());
			System.out.println("EMP DepartmentName" + reportPayOutDto.getDepartmentName());
			System.out.println("EMP ReconciliationDate" + reportPayOutDto.getReconciliationDate());
			System.out.println("EMP TransactionNo" + reportPayOutDto.getTransactionNo());

			reportPayoutList.add(reportPayOutDto);
		}

		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToEmpMonthlyReportList(List<Object[]> monthlyReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : monthlyReportList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();
			String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String employeeCode = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String bankName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			String accountNo = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;
			/*
			 * if (reportPayoutObj[4] != null) netAmount = reportPayoutObj[4] != null ? (new
			 * BigDecimal(reportPayoutObj[4].toString())) : null; else netAmount = new
			 * BigDecimal(0);
			 */
			Date dateOfJoining = reportPayoutObj[5] != null ? (Date) reportPayoutObj[5] : null;
			BigDecimal basic = reportPayoutObj[6] != null ? (new BigDecimal(reportPayoutObj[6].toString())) : null;
			BigDecimal da = reportPayoutObj[7] != null ? (new BigDecimal(reportPayoutObj[7].toString())) : null;
			BigDecimal conveyanceAllowance = reportPayoutObj[8] != null
					? (new BigDecimal(reportPayoutObj[8].toString()))
					: null;
			BigDecimal employeeHRA = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString()))
					: null;
			BigDecimal medicaAllowance = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			BigDecimal advanceBonus = reportPayoutObj[11] != null ? (new BigDecimal(reportPayoutObj[11].toString()))
					: null;
			BigDecimal specialAllowance = reportPayoutObj[12] != null ? (new BigDecimal(reportPayoutObj[12].toString()))
					: null;
			BigDecimal companyBenifits = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
					: null;
			BigDecimal otherAllowance = reportPayoutObj[14] != null ? (new BigDecimal(reportPayoutObj[14].toString()))
					: null;
			BigDecimal totelGrassSalary = reportPayoutObj[15] != null ? (new BigDecimal(reportPayoutObj[15].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[16] != null ? (new BigDecimal(reportPayoutObj[16].toString())) : null;
			BigDecimal casualLeave = reportPayoutObj[17] != null ? (new BigDecimal(reportPayoutObj[17].toString()))
					: null;
			BigDecimal sickLeave = reportPayoutObj[18] != null ? (new BigDecimal(reportPayoutObj[18].toString()))
					: null;
			BigDecimal paidLeave = reportPayoutObj[19] != null ? (new BigDecimal(reportPayoutObj[19].toString()))
					: null;
			BigDecimal prsent = reportPayoutObj[20] != null ? (new BigDecimal(reportPayoutObj[20].toString())) : null;
			BigDecimal publicHoliday = reportPayoutObj[21] != null ? (new BigDecimal(reportPayoutObj[21].toString()))
					: null;
			BigDecimal weeklyOff = reportPayoutObj[22] != null ? (new BigDecimal(reportPayoutObj[22].toString()))
					: null;
			BigDecimal overTime = reportPayoutObj[23] != null ? (new BigDecimal(reportPayoutObj[23].toString())) : null;
			BigDecimal payDays = reportPayoutObj[24] != null ? (new BigDecimal(reportPayoutObj[24].toString())) : null;
			BigDecimal basicEarning = reportPayoutObj[25] != null ? (new BigDecimal(reportPayoutObj[25].toString()))
					: null;
			BigDecimal daEarning = reportPayoutObj[26] != null ? (new BigDecimal(reportPayoutObj[26].toString()))
					: null;
			BigDecimal conveyanceEarning = reportPayoutObj[27] != null
					? (new BigDecimal(reportPayoutObj[27].toString()))
					: null;
			BigDecimal employeeHraEarning = reportPayoutObj[28] != null
					? (new BigDecimal(reportPayoutObj[28].toString()))
					: null;
			BigDecimal medicalAllowEarning = reportPayoutObj[29] != null
					? (new BigDecimal(reportPayoutObj[29].toString()))
					: null;
			BigDecimal addvanceBonusEarning = reportPayoutObj[30] != null
					? (new BigDecimal(reportPayoutObj[30].toString()))
					: null;
			BigDecimal specialAllEarning = reportPayoutObj[31] != null
					? (new BigDecimal(reportPayoutObj[31].toString()))
					: null;
			BigDecimal companyBenifitsEarning = reportPayoutObj[32] != null
					? (new BigDecimal(reportPayoutObj[32].toString()))
					: null;
			BigDecimal otherAlloEarning = reportPayoutObj[33] != null ? (new BigDecimal(reportPayoutObj[33].toString()))
					: null;
			BigDecimal totelEarning = reportPayoutObj[34] != null ? (new BigDecimal(reportPayoutObj[34].toString()))
					: null;
			BigDecimal empLoan = reportPayoutObj[35] != null ? (new BigDecimal(reportPayoutObj[35].toString())) : null;
			BigDecimal PF = reportPayoutObj[36] != null ? (new BigDecimal(reportPayoutObj[36].toString())) : null;
			BigDecimal ESIC = reportPayoutObj[37] != null ? (new BigDecimal(reportPayoutObj[37].toString())) : null;
			BigDecimal PT = reportPayoutObj[38] != null ? (new BigDecimal(reportPayoutObj[38].toString())) : null;
			BigDecimal TDS = reportPayoutObj[39] != null ? (new BigDecimal(reportPayoutObj[39].toString())) : null;
			BigDecimal totelDeduction = reportPayoutObj[40] != null ? (new BigDecimal(reportPayoutObj[40].toString()))
					: null;
			BigDecimal netAmount = reportPayoutObj[41] != null ? (new BigDecimal(reportPayoutObj[41].toString()))
					: null;

			// String departmentName = reportPayoutObj[6] != null ? (String)
			// reportPayoutObj[6] : null;
			reportPayoutDto.setProcessMonth(processMonth);
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNo);
			reportPayoutDto.setDateOfJoining(dateOfJoining);
			reportPayoutDto.setNetPayableAmount(netAmount);
			reportPayoutDto.setBasic(basic);
			reportPayoutDto.setDearnessAllowance(da);
			reportPayoutDto.setConveyanceAllowance(conveyanceAllowance);
			reportPayoutDto.setHra(employeeHRA);
			reportPayoutDto.setMedicalAllowance(medicaAllowance);
			reportPayoutDto.setAdvanceBonus(advanceBonus);
			reportPayoutDto.setSpecialAllowance(specialAllowance);
			reportPayoutDto.setCompanyBenefits(companyBenifits);
			reportPayoutDto.setOtherAllowance(otherAllowance);
			reportPayoutDto.setGrossSalary(totelGrassSalary);
			;
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setCasualleave(casualLeave);
			reportPayoutDto.setSeekleave(sickLeave);
			reportPayoutDto.setPaidleave(paidLeave);
			reportPayoutDto.setPresense(prsent);
			reportPayoutDto.setPublicholidays(publicHoliday);
			reportPayoutDto.setWeekoff(weeklyOff);
			reportPayoutDto.setOvertime(overTime);
			reportPayoutDto.setPayDays(payDays);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setDearnessAllowanceEarning(daEarning);
			reportPayoutDto.setConveyanceAllowanceEarning(conveyanceEarning);
			reportPayoutDto.setHraEarning(employeeHraEarning);
			reportPayoutDto.setMedicalAllowanceEarning(medicalAllowEarning);
			reportPayoutDto.setAdvanceBonusEarning(addvanceBonusEarning);
			reportPayoutDto.setSpecialAllowanceEarning(specialAllEarning);
			reportPayoutDto.setCompanyBenefitsEarning(companyBenifitsEarning);
			reportPayoutDto.setOtherAllowanceEarning(otherAlloEarning);
			reportPayoutDto.setTotalEarning(totelEarning);
			reportPayoutDto.setEmployeeLoansAdvnaceEarning(empLoan);
			reportPayoutDto.setProvidentFundEmployee(PF);
			reportPayoutDto.setEsi_Employee(ESIC);
			reportPayoutDto.setPt(PT);
			reportPayoutDto.setTds(TDS);
			reportPayoutDto.setTotalDeduction(totelDeduction);
			reportPayoutDto.setNetPayableAmount(netAmount);
			// reportPayoutDto.setProvisionDateCreated(dateCreated);
			// reportPayoutDto.setEmpDetp(departmentName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToEpfReport(List<Object[]> epfReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : epfReportList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String uanNo = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String employeeName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String fatherName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			String nominee = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;
			String nomineeRelation = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			Date dateOfBirth = reportPayoutObj[6] != null ? (Date) reportPayoutObj[6] : null;
			String gender = reportPayoutObj[7] != null ? (String) reportPayoutObj[7] : null;
			Date EpfJoining = reportPayoutObj[8] != null ? (Date) reportPayoutObj[8] : null;
			String maritalStatus = reportPayoutObj[9] != null ? (String) reportPayoutObj[9] : null;
			String accountNo = reportPayoutObj[10] != null ? (String) reportPayoutObj[10] : null;
			String ifscCode = reportPayoutObj[11] != null ? (String) reportPayoutObj[11] : null;
			String adharNo = reportPayoutObj[12] != null ? (String) reportPayoutObj[12] : null;
			String PANNo = reportPayoutObj[13] != null ? (String) reportPayoutObj[13] : null;
			String mobNo = reportPayoutObj[14] != null ? (String) reportPayoutObj[14] : null;
			String email = reportPayoutObj[15] != null ? (String) reportPayoutObj[15] : null;
			BigDecimal grassSalary = reportPayoutObj[16] != null ? (new BigDecimal(reportPayoutObj[16].toString()))
					: null;
			BigDecimal da = reportPayoutObj[17] != null ? (new BigDecimal(reportPayoutObj[17].toString())) : null;
			BigDecimal basic = reportPayoutObj[18] != null ? (new BigDecimal(reportPayoutObj[18].toString())) : null;
			BigDecimal payDays = reportPayoutObj[19] != null ? (new BigDecimal(reportPayoutObj[19].toString())) : null;
			BigDecimal absent = reportPayoutObj[20] != null ? (new BigDecimal(reportPayoutObj[20].toString())) : null;
			BigDecimal totelGrassSalary = reportPayoutObj[21] != null ? (new BigDecimal(reportPayoutObj[21].toString()))
					: null;
			BigDecimal basicEarning = reportPayoutObj[22] != null ? (new BigDecimal(reportPayoutObj[22].toString()))
					: null;

			BigDecimal PensionEarningSalary = reportPayoutObj[23] != null
					? (new BigDecimal(reportPayoutObj[23].toString()))
					: null;
			BigDecimal ProvidentFundEmployee = reportPayoutObj[24] != null
					? (new BigDecimal(reportPayoutObj[24].toString()))
					: null;

			// BigDecimal dearnessAllowanceEarning=reportPayoutObj[25]!=null?(new
			// BigDecimal(reportPayoutObj[25].toString())):null;
			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setUnNo(uanNo);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setFatherName(fatherName);
			reportPayoutDto.setNominee(nominee);
			reportPayoutDto.setNomineeRelation(nomineeRelation);
			// reportPayoutDto.setNomineeRelation(DropDownCache.getInstance().getDropDownValue(
			// DropDownEnum.Relation.getDropDownName() , nomineeRelation ));
			reportPayoutDto.setDOB(dateOfBirth);
			reportPayoutDto.setGender(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(), gender));
			reportPayoutDto.setEpfJoining(EpfJoining);
			reportPayoutDto.setMaritalStatus(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), maritalStatus));
			reportPayoutDto.setAccountNumber(accountNo);
			reportPayoutDto.setIfscCode(ifscCode);
			reportPayoutDto.setAadharNo(adharNo);
			reportPayoutDto.setPanNo(PANNo);
			reportPayoutDto.setMobNo(mobNo);
			reportPayoutDto.setEmail(email);
			reportPayoutDto.setGrossSalary(grassSalary);
			reportPayoutDto.setBasic(basic);
			reportPayoutDto.setDearnessAllowance(da);
			reportPayoutDto.setPayDays(payDays);
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setTotalEarning(totelGrassSalary);
			// reportPayoutDto.setDearnessAllowanceEarning(dearnessAllowanceEarning);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setPensionEarningSalary(PensionEarningSalary);
			reportPayoutDto.setProvidentFundEmployee(ProvidentFundEmployee);

			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToESICReport(List<Object[]> epfReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : epfReportList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String esicNo = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String employeeName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String fatherName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			String nominee = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;
			String nomineeRelation = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			Date dateOfBirth = reportPayoutObj[6] != null ? (Date) reportPayoutObj[6] : null;
			String gender = reportPayoutObj[7] != null ? (String) reportPayoutObj[7] : null;
			Date esicJoining = reportPayoutObj[8] != null ? (Date) reportPayoutObj[8] : null;
			BigDecimal present = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString())) : null;
			BigDecimal grassSalary = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			BigDecimal totalEarning = reportPayoutObj[11] != null ? (new BigDecimal(reportPayoutObj[11].toString()))
					: null;
			BigDecimal ESI_employee = reportPayoutObj[12] != null ? (new BigDecimal(reportPayoutObj[12].toString()))
					: null;

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setEsiNo(esicNo);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setFatherName(fatherName);
			reportPayoutDto.setNominee(nominee);
			reportPayoutDto.setNomineeRelation(nomineeRelation);
			// reportPayoutDto.setNomineeRelation(DropDownCache.getInstance().getDropDownValue(
			// DropDownEnum.Relation.getDropDownName() , nomineeRelation ));
			reportPayoutDto.setDOB(dateOfBirth);
			reportPayoutDto.setGender(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(), gender));
			reportPayoutDto.setEsicJoining(esicJoining);

			reportPayoutDto.setPresense(present);
			reportPayoutDto.setGrossSalary(grassSalary);
			reportPayoutDto.setTotalEarning(totalEarning);
			reportPayoutDto.setEsi_Employee(ESI_employee);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> objectListToEpfEcrReportList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();
			// Long
			// tdsGroupId=reportPayoutObj[0]!=null?Long.parseLong(tdsTransactionObj[0].toString()):null;
			// Long
			// tdsSectionId=tdsTransactionObj[1]!=null?Long.parseLong(tdsTransactionObj[1].toString()):null;
			String UANNo = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			BigDecimal totalEarning = reportPayoutObj[2] != null ? (new BigDecimal(reportPayoutObj[2].toString()))
					: null;
			BigDecimal basicEarning = reportPayoutObj[3] != null ? (new BigDecimal(reportPayoutObj[3].toString()))
					: null;
			BigDecimal pensionEarningSalary = reportPayoutObj[4] != null
					? (new BigDecimal(reportPayoutObj[4].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;
			BigDecimal providentFundEmployee = reportPayoutObj[6] != null
					? (new BigDecimal(reportPayoutObj[6].toString()))
					: null;
			BigDecimal providentFundEmployer = reportPayoutObj[7] != null
					? (new BigDecimal(reportPayoutObj[7].toString()))
					: null;
			BigDecimal providentFundEmployerPension = reportPayoutObj[8] != null
					? (new BigDecimal(reportPayoutObj[8].toString()))
					: null;
			BigDecimal dearnessAllowanceEarning = reportPayoutObj[9] != null
					? (new BigDecimal(reportPayoutObj[9].toString()))
					: null;

			reportPayoutDto.setUnNo(UANNo);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setTotalEarning(totalEarning);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setPensionEarningSalary(pensionEarningSalary);
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setProvidentFundEmployee(providentFundEmployee);
			reportPayoutDto.setProvidentFundEmployer(providentFundEmployer);
			reportPayoutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
			reportPayoutDto.setDearnessAllowanceEarning(dearnessAllowanceEarning);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public ReportSummaryDTO objectListToReportSummary(List<Object[]> objectReportPayout) {

		ReportSummaryDTO reportSummary = new ReportSummaryDTO();
		for (Object[] report : objectReportPayout) {
			BigInteger empcount = report[0] != null ? (BigInteger) report[0] : null;
			BigDecimal grassSum = report[1] != null ? (BigDecimal) report[1] : null;

			reportSummary.setEpfExcludedEmpCount(empcount);
			reportSummary.setEpfExcludedGrassSum(grassSum);
		}

		return reportSummary;
	}

	public List<ReportPayOutDTO> objectListToEsiEcrReportList(List<Object[]> objectReportPayoutList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : objectReportPayoutList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String esicNo = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			BigDecimal payDays = reportPayoutObj[2] != null ? (new BigDecimal(reportPayoutObj[2].toString())) : null;
			BigDecimal totalEarning = reportPayoutObj[3] != null ? (new BigDecimal(reportPayoutObj[3].toString()))
					: null;

			reportPayoutDto.setEsiNo(esicNo);
			reportPayoutDto.setName(employeeName);
			reportPayoutDto.setTotalEarning(totalEarning);
			reportPayoutDto.setPayDays(payDays);

			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<FinancialYearDTO> databaseToUI(List<FinancialYear> financialYearList) {
		List<FinancialYearDTO> FinancialYearDTOList = new ArrayList<>();

		for (FinancialYear financialYear : financialYearList) {
			FinancialYearDTO financialYearDTO = new FinancialYearDTO();

			financialYearDTO.setFinancialYear(financialYear.getFinancialYear());
			financialYearDTO.setFinancialYearId(financialYear.getFinancialYearId());
			financialYearDTO.setCompanyId(financialYear.getCompany().getCompanyId());
			financialYearDTO.setUserId(financialYear.getUserId());
			FinancialYearDTOList.add(financialYearDTO);
		}
		return FinancialYearDTOList;
	}

	public List<OneTimeEarningDeductionDTO> databaseListToUIDtoList(List<Object[]> monthlyReportList) {

		List<OneTimeEarningDeductionDTO> oneTimeEarningDeductionDTOList = new ArrayList<OneTimeEarningDeductionDTO>();

		for (Object[] monthlyReport : monthlyReportList) {
			OneTimeEarningDeductionDTO oneTimeEarningDeductionDTO = new OneTimeEarningDeductionDTO();

			String employeeCode = monthlyReport[0] != null ? (String) monthlyReport[0] : null;
			String employeeName = monthlyReport[1] != null ? (String) monthlyReport[1] : null;
			String designationName = monthlyReport[2] != null ? (String) monthlyReport[2] : null;
			String departmentName = monthlyReport[3] != null ? (String) monthlyReport[3] : null;
			String deductionType = monthlyReport[4] != null ? (String) monthlyReport[4] : null;
			BigDecimal amount = monthlyReport[5] != null ? (BigDecimal) monthlyReport[5] : null;
			String comment = monthlyReport[6] != null ? (String) monthlyReport[6] : null;

			oneTimeEarningDeductionDTO.setEmployeeCode(employeeCode);
			oneTimeEarningDeductionDTO.setEmployeeName(employeeName);
			oneTimeEarningDeductionDTO.setDesignationName(designationName);
			oneTimeEarningDeductionDTO.setDepartmentName(departmentName);
			oneTimeEarningDeductionDTO.setRemarks(comment);
			oneTimeEarningDeductionDTO.setDeductionType(deductionType);
			oneTimeEarningDeductionDTO.setAmount(amount);

			oneTimeEarningDeductionDTOList.add(oneTimeEarningDeductionDTO);
		}

		return oneTimeEarningDeductionDTOList;

	}

	public List<ReportPayOutDTO> objectListToCurrentCostToCompanyReport(List<Object[]> monthlyReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

		for (Object[] reportPayoutObj : monthlyReportList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;

			if (employeeCode != null) {
				String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
				String designationName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
				String departmentName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
				String gradesName = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;
				String employeeType = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
				String employeeStatus = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
				Date effectiveDate = reportPayoutObj[7] != null ? (Date) reportPayoutObj[7] : null;
				Date dateUpdate = reportPayoutObj[8] != null ? (Date) reportPayoutObj[8] : null;
				BigDecimal ctc = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString())) : null;
				BigDecimal grossSalary = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
						: null;
				BigDecimal netPayableAmount = reportPayoutObj[13] != null
						? (new BigDecimal(reportPayoutObj[13].toString()))
						: null;

				String amount = null;
				String payHeadId = null;
				payHeadId = reportPayoutObj[11] != null ? (String) reportPayoutObj[11] : null;
				amount = reportPayoutObj[12] != null ? (String) reportPayoutObj[12] : null;

				if (amount != null) {
//				if (!amount.contains(",")) {
//					String amounts = "0.00";
//					String[] payHeadIds = payHeadId.split(",");
//					Map<String, String> map = new HashMap<String, String>();
//
//					for (int i = 0; i < payHeadIds.length; i++) {
//						map.put(payHeadIds[i], amounts);
//
//					}
//					reportPayoutDto.setPayHeadsMap(map);
//				} else {

					String[] amounts = amount.split(",");
					String[] payHeadIds = payHeadId.split(",");
					Map<String, String> map = new HashMap<String, String>();

					for (int i = 0; i < payHeadIds.length; i++) {
						map.put(payHeadIds[i], amounts[i]);

					}
					reportPayoutDto.setPayHeadsMap(map);
					// }
				}

//				if (amount != null) {
//					String[] amounts = amount.split(",");
//					String[] payHeadIds = payHeadId.split(",");
//					Map<String, String> map = new HashMap<String, String>();
//					// System.out.println(amounts);
//					for (int i = 0; i < payHeadIds.length; i++) {
//						map.put(payHeadIds[i], amounts[i]);
//					}
//					payStructureHdDTO.setPayHeadsMap(map);
//				}

				BigDecimal epfEmployee = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
						: null;
				BigDecimal epfEmployer = reportPayoutObj[14] != null ? (new BigDecimal(reportPayoutObj[14].toString()))
						: null;
				BigDecimal esi_Employee = reportPayoutObj[15] != null ? (new BigDecimal(reportPayoutObj[15].toString()))
						: null;
				BigDecimal esi_Employer = reportPayoutObj[16] != null ? (new BigDecimal(reportPayoutObj[16].toString()))
						: null;
				BigDecimal lwfEmployeeContr = reportPayoutObj[17] != null
						? (new BigDecimal(reportPayoutObj[17].toString()))
						: null;
				BigDecimal lwfEmployerContr = reportPayoutObj[18] != null
						? (new BigDecimal(reportPayoutObj[18].toString()))
						: null;
				BigDecimal professionalTax = reportPayoutObj[19] != null
						? (new BigDecimal(reportPayoutObj[19].toString()))
						: null;
				BigDecimal netPay = reportPayoutObj[20] != null ? (new BigDecimal(reportPayoutObj[20].toString()))
						: null;
			
				BigDecimal epfEmployeePension = reportPayoutObj[21] != null
						? (new BigDecimal(reportPayoutObj[21].toString()))
						: null;

//				String[] payHeadIds = payHeadId.split(",");
//				Map<String, String> map = new HashMap<String, String>();
//
//				for (int i = 0; i < payHeadIds.length; i++) {
//					map.put(payHeadIds[i], amounts[i]);
//				}
//				reportPayoutDto.setPayHeadsMap(map);

				reportPayoutDto.setEmployeeCode(employeeCode);
				reportPayoutDto.setEmpName(empName);
				reportPayoutDto.setDesignationName(designationName);
				reportPayoutDto.setDepartmentName(departmentName);

				reportPayoutDto.setDateUpdate(dateUpdate);

				reportPayoutDto.setEmpGrade(gradesName);
				reportPayoutDto.setGrossSalary(grossSalary);
				reportPayoutDto.setNetPayableAmount(netPayableAmount);
				reportPayoutDto.setDateUpdate(dateUpdate);
				reportPayoutDto.setEmployeeType(employeeType);
				reportPayoutDto.setEmployeeStatus(employeeStatus);
				reportPayoutDto.setCtc(ctc);
				reportPayoutDto.setEffectiveDate(effectiveDate);

				reportPayoutDto.setEpfEmployee(epfEmployee);
				reportPayoutDto.setEpfEmployer(epfEmployer);
				reportPayoutDto.setEsi_Employee(esi_Employee);
				reportPayoutDto.setEsi_Employer(esi_Employer);
				reportPayoutDto.setProfessionalTax(professionalTax);
				reportPayoutDto.setNetPayableAmount(netPay);
			
				reportPayoutDto.setLwfEmployeeAmount(lwfEmployeeContr);
				reportPayoutDto.setLwfEmployerAmount(lwfEmployerContr);
				reportPayoutDto.setEpfEmployeePension(epfEmployeePension);
				reportPayoutList.add(reportPayoutDto);
			}
			// return reportPayoutList;
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> annualSummary(List<Object[]> annualSummaryList) {

		List<ReportPayOutDTO> annualSummary = new ArrayList<ReportPayOutDTO>();

		for (Object[] reportPayoutObj : annualSummaryList) {

			ReportPayOutDTO reportPayOutDTO = new ReportPayOutDTO();

			String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			BigDecimal grossSalary = reportPayoutObj[1] != null ? (new BigDecimal(reportPayoutObj[1].toString()))
					: null;
			BigDecimal basic = reportPayoutObj[2] != null ? (new BigDecimal(reportPayoutObj[2].toString())) : null;
			BigDecimal other = reportPayoutObj[3] != null ? (new BigDecimal(reportPayoutObj[3].toString())) : null;
			BigDecimal netPay = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString())) : null;
			BigDecimal oneTimeEarnings = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString()))
					: null;
			BigDecimal pfEmployee = reportPayoutObj[6] != null ? (new BigDecimal(reportPayoutObj[6].toString())) : null;
			BigDecimal pfEmployer = reportPayoutObj[7] != null ? (new BigDecimal(reportPayoutObj[7].toString())) : null;
			BigDecimal esicEmployee = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString()))
					: null;
			BigDecimal esicEmployer = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString()))
					: null;
			BigDecimal professionTax = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			BigDecimal tds = reportPayoutObj[11] != null ? (new BigDecimal(reportPayoutObj[11].toString())) : null;
			BigDecimal loan = reportPayoutObj[12] != null ? (new BigDecimal(reportPayoutObj[12].toString())) : null;
			BigDecimal otherDeduction = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
					: null;
			BigDecimal EPSEmployer = reportPayoutObj[14] != null ? (new BigDecimal(reportPayoutObj[14].toString()))
					: null;
			BigDecimal LWFEmployee = reportPayoutObj[15] != null ? (new BigDecimal(reportPayoutObj[15].toString()))
					: null;
			BigDecimal LWFEmployer = reportPayoutObj[16] != null ? (new BigDecimal(reportPayoutObj[16].toString()))
					: null;
			BigDecimal ctc = reportPayoutObj[17] != null ? (new BigDecimal(reportPayoutObj[17].toString())) : null;
//			BigDecimal adminCharge= adminChargesCalculationService.adminChargesCalculation(companyId, basic, EPSEmployer);

			reportPayOutDTO.setProcessMonth(processMonth);
			reportPayOutDTO.setGrossSalary(grossSalary);
			reportPayOutDTO.setBasicEarning(basic);
			reportPayOutDTO.setOtherEarning(other);
			reportPayOutDTO.setNetPayableAmount(netPay);
			reportPayOutDTO.setTotalEarning(oneTimeEarnings);
			reportPayOutDTO.setProvidentFundEmployee(pfEmployee);
			reportPayOutDTO.setProvidentFundEmployer(pfEmployer);
			reportPayOutDTO.setEsi_Employee(esicEmployee);
			reportPayOutDTO.setEsi_Employer(esicEmployer);
			reportPayOutDTO.setPt(professionTax);
			reportPayOutDTO.setTds(tds);
			reportPayOutDTO.setOtherDeduction(otherDeduction);
			reportPayOutDTO.setLoan(loan);
			reportPayOutDTO.setLwfEmployeeAmount(LWFEmployee);
			reportPayOutDTO.setLwfEmployerAmount(LWFEmployer);
			reportPayOutDTO.setProvidentFundEmployerPension(EPSEmployer);
			reportPayOutDTO.setCtc(ctc);
			reportPayOutDTO.setAdminCharge(new BigDecimal("0.00"));
			annualSummary.add(reportPayOutDTO);
		}

		return annualSummary;

	}

	public Map<String, List<ReportPayOutDTO>> objectListToSalarySheetAnnualReport(List<Object[]> annualReportList,
			List<Object[]> annualSummaryList) {

		Map<String, List<ReportPayOutDTO>> annualMap = new HashMap<String, List<ReportPayOutDTO>>();

		for (Object[] annualSummary : annualSummaryList) {
			List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
			String summaryProccessMonth = annualSummary[0] != null ? (String) annualSummary[0] : null;

			for (Object[] reportPayoutObj : annualReportList) {
				String proccessMonth = reportPayoutObj[18] != null ? (String) reportPayoutObj[18] : null;

				if (summaryProccessMonth.equalsIgnoreCase(proccessMonth)) {

					ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

					String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
					String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
					String designationName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
					String departmentName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
					Date dateOfJoining = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;
					String gender = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
					String jobLocation = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
					BigDecimal daysInMonth = reportPayoutObj[7] != null
							? (new BigDecimal(reportPayoutObj[7].toString()))
							: null;
					BigDecimal absent = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString()))
							: null;
					BigDecimal payDays = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString()))
							: null;
					BigDecimal monthlyGross = reportPayoutObj[10] != null
							? (new BigDecimal(reportPayoutObj[10].toString()))
							: null;
					String amount = null;
					String payHeadId = null;
					payHeadId = reportPayoutObj[11] != null ? (String) reportPayoutObj[11] : null;
					amount = reportPayoutObj[12] != null ? (String) reportPayoutObj[12] : null;
					String[] amounts = amount.split(",");
					String[] payHeadIds = payHeadId.split(",");
					Map<String, String> map = new HashMap<String, String>();

					for (int i = 0; i < payHeadIds.length; i++) {
						map.put(payHeadIds[i], amounts[i]);
					}
					reportPayoutDto.setPayHeadsMap(map);

					BigDecimal netPayableAmount = reportPayoutObj[13] != null
							? (new BigDecimal(reportPayoutObj[13].toString()))
							: null;
					String bankName = reportPayoutObj[14] != null ? (String) reportPayoutObj[14].toString() : null;
					String accountNumber = reportPayoutObj[15] != null ? (String) reportPayoutObj[15].toString() : null;
					String ifscCode = reportPayoutObj[16] != null ? (String) reportPayoutObj[16].toString() : null;
					String branchName = reportPayoutObj[17] != null ? (String) reportPayoutObj[17].toString() : null;

					reportPayoutDto.setEmployeeCode(employeeCode);
					reportPayoutDto.setEmpName(empName);
					reportPayoutDto.setDesignationName(designationName);
					reportPayoutDto.setDepartmentName(departmentName);
					reportPayoutDto.setDateOfJoining(dateOfJoining);
					reportPayoutDto.setMonthalyGross(monthlyGross);

					if (gender.equals("M"))
						reportPayoutDto.setGender("Male");

					if (gender.equals("F"))
						reportPayoutDto.setGender("Female");

					reportPayoutDto.setDaysInMonth(daysInMonth.longValue());
					reportPayoutDto.setJobLocation(jobLocation);
					reportPayoutDto.setAbsent(absent.longValue());
					reportPayoutDto.setPayDays(payDays);
					reportPayoutDto.setNetPayableAmount(netPayableAmount);
					reportPayoutDto.setBankName(bankName);
					reportPayoutDto.setAccountNumber(accountNumber);
					reportPayoutDto.setIfscCode(ifscCode);
					reportPayoutDto.setBranchName(branchName);
					reportPayoutList.add(reportPayoutDto);
				}
				// return reportPayoutList;
			}
			annualMap.put(summaryProccessMonth, reportPayoutList);
		}
		return annualMap;
	}

	public List<ReportPayOutDTO> databaseListToUIDtoPTReports(List<Object[]> PTReportsList, String wise) {

		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();

		if (wise.equalsIgnoreCase("EW")) {
			for (Object[] PTReports : PTReportsList) {
				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String employeeCode = PTReports[0] != null ? (String) PTReports[0] : null;
				String employeeName = PTReports[1] != null ? (String) PTReports[1] : null;
				String jobLocation = PTReports[2] != null ? (String) PTReports[2] : null;
				String state = PTReports[3] != null ? (String) PTReports[3] : null;
				BigDecimal profTaxGross = PTReports[4] != null ? (BigDecimal) PTReports[4] : null;
				BigDecimal amount = PTReports[5] != null ? (BigDecimal) PTReports[5] : null;

				System.out.println("Employee Code====" + employeeCode);

				reportPayoutDto.setEmployeeCode(employeeCode);
				reportPayoutDto.setEmpName(employeeName);
				reportPayoutDto.setJobLocation(jobLocation);
				reportPayoutDto.setStateName(state);
				reportPayoutDto.setTotalEarning(profTaxGross);
				reportPayoutDto.setAmount(amount);

				reportPayOutDTOList.add(reportPayoutDto);
			}
		} else if (wise.equalsIgnoreCase("SW")) {
			for (Object[] PTReports : PTReportsList) {
				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String state = PTReports[0] != null ? (String) PTReports[0] : null;
				BigInteger totalEmployee = PTReports[1] != null ? (BigInteger) PTReports[1] : null;
				BigDecimal totalGrossAmount = PTReports[2] != null ? (BigDecimal) PTReports[2] : null;
				BigDecimal totalPTAmount = PTReports[3] != null ? (BigDecimal) PTReports[3] : null;

				reportPayoutDto.setStateName(state);
				reportPayoutDto.setTotalEmployee(totalEmployee.intValue());
				reportPayoutDto.setTotalEarning(totalGrossAmount);
				reportPayoutDto.setAmount(totalPTAmount);

				reportPayOutDTOList.add(reportPayoutDto);
			}
		}
		return reportPayOutDTOList;
	}

	public List<ReportPayOutDTO> databaseModelToUIDtoList(List<Object[]> monthlyArrearReportList) {
		// TODO Auto-generated method stub
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
		for (Object[] reportPayoutObj : monthlyArrearReportList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();
			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String designationName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String departmentName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			Date dateOfJoining = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;
			String gender = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			String jobLocation = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			BigDecimal amount = reportPayoutObj[7] != null ? (BigDecimal) reportPayoutObj[7] : null;
			if (reportPayoutObj.length == 9) {
				String processMonth = reportPayoutObj[8] != null ? (String) reportPayoutObj[8] : null;
				reportPayoutDto.setProcessMonth(processMonth);
			}

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setEmpName(empName);
			reportPayoutDto.setDesignationName(designationName);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutDto.setDateOfJoining(dateOfJoining);

			if (gender.equals("M"))
				reportPayoutDto.setGender("Male");

			if (gender.equals("F"))
				reportPayoutDto.setGender("Female");
			reportPayoutDto.setJobLocation(jobLocation);
			reportPayoutDto.setAmount(amount);
			reportPayoutList.add(reportPayoutDto);

		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> databaseListToUIDtoReports(List<Object[]> pFReportList, Long flag) {
		// TODO Auto-generated method stub

		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

		for (Object[] reportPayoutObj : pFReportList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String unNo = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String empName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			if (flag == 1) {
				Date epfJoining = reportPayoutObj[3] != null ? (Date) reportPayoutObj[3] : null;
				reportPayoutDto.setEpfJoining(epfJoining);
			} else if (flag == 2) {
				Date esicJoining = reportPayoutObj[3] != null ? (Date) reportPayoutObj[3] : null;
				reportPayoutDto.setEsicJoining(esicJoining);
			}
			Date pfExit = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setUnNo(unNo);
			reportPayoutDto.setEmpName(empName);

			reportPayoutDto.setEndDate(pfExit);

			reportPayoutList.add(reportPayoutDto);

		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> databaseListToModelDto(List<Object[]> paymentRecordList) {
		List<ReportPayOutDTO> reportPayoutDtoList = new ArrayList<>();

		for (Object[] reportPayoutObj : paymentRecordList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String bankName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2].toString() : null;
			String accountNumber = reportPayoutObj[3] != null ? (String) reportPayoutObj[3].toString() : null;
			String ifscCode = reportPayoutObj[4] != null ? (String) reportPayoutObj[4].toString() : null;
			BigDecimal netPayableAmount = reportPayoutObj[5] != null ? (BigDecimal) reportPayoutObj[5] : null;
			String transactionNo = reportPayoutObj[6] != null ? (String) reportPayoutObj[6].toString() : null;
			String transactionMode = reportPayoutObj[7] != null ? (String) reportPayoutObj[7].toString() : null;
			Date dateUpdate = reportPayoutObj[8] != null ? (Date) reportPayoutObj[8] : null;

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setEmpName(empName);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNumber);
			reportPayoutDto.setIfscCode(ifscCode);
			reportPayoutDto.setNetPayableAmount(netPayableAmount);
			reportPayoutDto.setTransactionNo(transactionNo);

			if (transactionMode != null)
				for (RecordPaymentEnum e : RecordPaymentEnum.values()) {
					if (String.valueOf(e.id).equals(transactionMode))
						reportPayoutDto.setTransactionMode(e.getTransactionMode());
				}

			reportPayoutDto.setDateUpdate(dateUpdate);
			reportPayoutDtoList.add(reportPayoutDto);

		}

		return reportPayoutDtoList;
	}

	public List<ArrearReportPayOutDTO> databaseListToUIDtoArrearReport(List<Object[]> esiArrearEcrList) {
		// TODO Auto-generated method stub

		List<ArrearReportPayOutDTO> arrearReportPayOutDTOList = new ArrayList<>();

		for (Object[] arrearReportPayOutObj : esiArrearEcrList) {
			ArrearReportPayOutDTO arrearReportPayOutDto = new ArrearReportPayOutDTO();

			String eSICNumber = arrearReportPayOutObj[0] != null ? (String) arrearReportPayOutObj[0] : null;
			String name = arrearReportPayOutObj[1] != null ? (String) arrearReportPayOutObj[1] : null;
			BigDecimal payDays = arrearReportPayOutObj[2] != null ? (BigDecimal) arrearReportPayOutObj[2] : null;
			BigDecimal netPayableAmount = arrearReportPayOutObj[3] != null ? (BigDecimal) arrearReportPayOutObj[3]
					: null;

			arrearReportPayOutDto.setESICNumber(eSICNumber);
			arrearReportPayOutDto.setName(name);
			arrearReportPayOutDto.setPayDays(payDays);
			arrearReportPayOutDto.setNetPayableAmount(netPayableAmount);
			arrearReportPayOutDTOList.add(arrearReportPayOutDto);
		}
		return arrearReportPayOutDTOList;
	}

	public List<PayStructureHdDTO> objectListToPayStructureReport(List<Object[]> payStructureList) {
		// TODO Auto-generated method stub
		List<PayStructureHdDTO> payStructureHdList = new ArrayList<>();

		for (Object[] payStructureHdObj : payStructureList) {

			PayStructureHdDTO payStructureHdDTO = new PayStructureHdDTO();

			String employeeCode = payStructureHdObj[0] != null ? (String) payStructureHdObj[0] : null;
			String empName = payStructureHdObj[1] != null ? (String) payStructureHdObj[1] : null;
			String designationName = payStructureHdObj[2] != null ? (String) payStructureHdObj[2] : null;
			String departmentName = payStructureHdObj[3] != null ? (String) payStructureHdObj[3] : null;
			String gradesName = payStructureHdObj[4] != null ? (String) payStructureHdObj[4] : null;
			String employeeType = payStructureHdObj[5] != null ? (String) payStructureHdObj[5] : null;
			String employeeStatus = payStructureHdObj[6] != null ? (String) payStructureHdObj[6] : null;
			Date effectiveDate = payStructureHdObj[7] != null ? (Date) payStructureHdObj[7] : null;
			Date dateUpdate = payStructureHdObj[8] != null ? (Date) payStructureHdObj[8] : null;
			BigDecimal grossPay = payStructureHdObj[9] != null ? (new BigDecimal(payStructureHdObj[9].toString()))
					: null;

			String amount = null;
			String payHeadId = null;
			payHeadId = payStructureHdObj[10] != null ? (String) payStructureHdObj[10] : null;
			amount = payStructureHdObj[11] != null ? (String) payStructureHdObj[11] : null;

			if (amount != null) {
				String[] amounts = amount.split(",");
				String[] payHeadIds = payHeadId.split(",");
				Map<String, String> map = new HashMap<String, String>();
				// System.out.println(amounts);
				for (int i = 0; i < payHeadIds.length; i++) {
					map.put(payHeadIds[i], amounts[i]);
				}
				payStructureHdDTO.setPayHeadsMap(map);
			}

			BigDecimal epfEmployee = payStructureHdObj[12] != null ? (new BigDecimal(payStructureHdObj[12].toString()))
					: null;
			BigDecimal epfEmployer = payStructureHdObj[13] != null ? (new BigDecimal(payStructureHdObj[13].toString()))
					: null;
			BigDecimal esiEmployee = payStructureHdObj[14] != null ? (new BigDecimal(payStructureHdObj[14].toString()))
					: null;
			BigDecimal esiEmployer = payStructureHdObj[15] != null ? (new BigDecimal(payStructureHdObj[15].toString()))
					: null;

			BigDecimal lwfEmployeeContr = payStructureHdObj[16] != null
					? (new BigDecimal(payStructureHdObj[16].toString()))
					: null;
			BigDecimal lwfEmployerContr = payStructureHdObj[17] != null
					? (new BigDecimal(payStructureHdObj[17].toString()))
					: null;

			BigDecimal professionalTax = payStructureHdObj[18] != null
					? (new BigDecimal(payStructureHdObj[18].toString()))
					: null;
			BigDecimal netPay = payStructureHdObj[19] != null ? (new BigDecimal(payStructureHdObj[19].toString()))
					: null;
			BigDecimal epfEmployeePension = payStructureHdObj[20] != null
					? (new BigDecimal(payStructureHdObj[20].toString()))
					: null;

			payStructureHdDTO.setEmployeeCode(employeeCode);
			payStructureHdDTO.setEmployeeName(empName);
			payStructureHdDTO.setDesignationName(designationName);
			payStructureHdDTO.setDepartmentName(departmentName);
			payStructureHdDTO.setGradesName(gradesName);

			if (employeeType != null) {
				if (employeeType.equals("PE")) {
					payStructureHdDTO.setEmployeeType("Permanent");
				} else if (employeeType.equals("CO")) {
					payStructureHdDTO.setEmployeeType("Contract");
				}
			}

			if (employeeStatus != null) {
				if (employeeStatus.equals("AC")) {
					payStructureHdDTO.setEmployeeStatus("Working");
				} else if (employeeStatus.equals("DE")) {
					payStructureHdDTO.setEmployeeStatus("Former");
				}
			}

			payStructureHdDTO.setEffectiveDate(effectiveDate);
			payStructureHdDTO.setDateUpdate(dateUpdate);
			payStructureHdDTO.setGrossPay(grossPay);
			payStructureHdDTO.setEpfEmployee(epfEmployee);
			payStructureHdDTO.setEpfEmployer(epfEmployer);
			payStructureHdDTO.setEsiEmployee(esiEmployee);
			payStructureHdDTO.setEsiEmployer(esiEmployer);
			payStructureHdDTO.setProfessionalTax(professionalTax);
			payStructureHdDTO.setNetPay(netPay);
			payStructureHdDTO.setLwfEmployeeAmount(lwfEmployeeContr);
			payStructureHdDTO.setLwfEmployerAmount(lwfEmployerContr);
			payStructureHdDTO.setEpfEmployeePension(epfEmployeePension);
			payStructureHdList.add(payStructureHdDTO);
		}

		return payStructureHdList;
	}

	public List<ReportPayOutDTO> objectListToMonthlySalarySheetReportList(List<Object[]> monthlyReportList) {
		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

		for (Object[] reportPayoutObj : monthlyReportList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
			String designationName = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
			String departmentName = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
			Date dateOfJoining = reportPayoutObj[4] != null ? (Date) reportPayoutObj[4] : null;
			String gender = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;
			String jobLocation = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;
			BigDecimal daysInMonth = reportPayoutObj[7] != null ? (new BigDecimal(reportPayoutObj[7].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString())) : null;
			BigDecimal payDays = reportPayoutObj[9] != null ? (new BigDecimal(reportPayoutObj[9].toString())) : null;
			BigDecimal monthlyGross = reportPayoutObj[10] != null ? (new BigDecimal(reportPayoutObj[10].toString()))
					: null;
			String amount = null;
			String payHeadId = null;
			payHeadId = reportPayoutObj[11] != null ? (String) reportPayoutObj[11] : null;
			amount = reportPayoutObj[12] != null ? (String) reportPayoutObj[12] : null;
			String[] amounts = amount.split(",");
			String[] payHeadIds = payHeadId.split(",");
			Map<String, String> map = new HashMap<String, String>();

			for (int i = 0; i < payHeadIds.length; i++) {
				map.put(payHeadIds[i], amounts[i]);
			}
			reportPayoutDto.setPayHeadsMap(map);

			BigDecimal netPayableAmount = reportPayoutObj[13] != null ? (new BigDecimal(reportPayoutObj[13].toString()))
					: null;
			String bankName = reportPayoutObj[14] != null ? (String) reportPayoutObj[14].toString() : null;
			String accountNumber = reportPayoutObj[15] != null ? (String) reportPayoutObj[15].toString() : null;
			String ifscCode = reportPayoutObj[16] != null ? (String) reportPayoutObj[16].toString() : null;
			String branchName = reportPayoutObj[17] != null ? (String) reportPayoutObj[17].toString() : null;

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setEmpName(empName);
			reportPayoutDto.setDesignationName(designationName);
			reportPayoutDto.setDepartmentName(departmentName);
			reportPayoutDto.setDateOfJoining(dateOfJoining);
			reportPayoutDto.setMonthalyGross(monthlyGross);

			if (gender.equals("M"))
				reportPayoutDto.setGender("Male");

			if (gender.equals("F"))
				reportPayoutDto.setGender("Female");

			reportPayoutDto.setDaysInMonth(daysInMonth.longValue());
			reportPayoutDto.setJobLocation(jobLocation);
			reportPayoutDto.setAbsent(absent.longValue());
			reportPayoutDto.setPayDays(payDays);
			reportPayoutDto.setNetPayableAmount(netPayableAmount);
			reportPayoutDto.setBankName(bankName);
			reportPayoutDto.setAccountNumber(accountNumber);
			reportPayoutDto.setIfscCode(ifscCode);
			reportPayoutDto.setBranchName(branchName);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public List<ReportPayOutDTO> databaseModelToIncomeTaxMonthlyList(List<Object[]> incomeTaxMonthlyObjList,
			Long financialYearId) {

		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

		if (financialYearId != 0L) {

			for (Object[] reportPayoutObj : incomeTaxMonthlyObjList) {

				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
				String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
				Date dateOfJoining = reportPayoutObj[2] != null ? (Date) reportPayoutObj[2] : null;
				String panNo = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
				BigDecimal tds = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString())) : null;
				String processMonth = reportPayoutObj[5] != null ? (String) reportPayoutObj[5] : null;

				reportPayoutDto.setEmployeeCode(employeeCode);
				reportPayoutDto.setEmpName(empName);
				reportPayoutDto.setDateOfJoining(dateOfJoining);
				reportPayoutDto.setPanNo(panNo);
				reportPayoutDto.setTds(tds);
				reportPayoutDto.setProcessMonth(processMonth);

				reportPayoutList.add(reportPayoutDto);
			}

		} else {

			for (Object[] reportPayoutObj : incomeTaxMonthlyObjList) {

				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
				String empName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
				Date dateOfJoining = reportPayoutObj[2] != null ? (Date) reportPayoutObj[2] : null;

				String panNo = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;

				BigDecimal toalEarning = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString()))
						: null;
				BigDecimal tds = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;

				reportPayoutDto.setEmployeeCode(employeeCode);
				reportPayoutDto.setEmpName(empName);
				reportPayoutDto.setDateOfJoining(dateOfJoining);
				reportPayoutDto.setPanNo(panNo);
				reportPayoutDto.setMonthalyGross(toalEarning);
				reportPayoutDto.setTds(tds);

				reportPayoutList.add(reportPayoutDto);
			}

		}

		return reportPayoutList;
	}

	public List<TdsHouseRentInfoDTO> objectListToRentPaidDetailsReport(List<Object[]> rentPaidDetailsObj) {

		List<TdsHouseRentInfoDTO> tdsHouseRentInfoList = new ArrayList<TdsHouseRentInfoDTO>();

		for (Object[] rentPaidDetail : rentPaidDetailsObj) {

			TdsHouseRentInfoDTO tdsHouseRentInfoDTO = new TdsHouseRentInfoDTO();

			String employeeCode = rentPaidDetail[0] != null ? (String) rentPaidDetail[0] : null;
			String employeeName = rentPaidDetail[1] != null ? (String) rentPaidDetail[1] : null;
			Date fromMonth = rentPaidDetail[2] != null ? (Date) rentPaidDetail[2] : null;
			Date toMonth = rentPaidDetail[3] != null ? (Date) rentPaidDetail[3] : null;
			String address = rentPaidDetail[4] != null ? (String) rentPaidDetail[4] : null;
			String city = rentPaidDetail[5] != null ? (String) rentPaidDetail[5] : null;
			BigDecimal rentPaid = rentPaidDetail[6] != null ? (new BigDecimal(rentPaidDetail[6].toString())) : null;
			String landLordName = rentPaidDetail[7] != null ? (String) rentPaidDetail[7] : null;
			String landLordAddr = rentPaidDetail[8] != null ? (String) rentPaidDetail[8] : null;
			String landLordPan = rentPaidDetail[9] != null ? (String) rentPaidDetail[9] : null;

			tdsHouseRentInfoDTO.setEmpCode(employeeCode);
			tdsHouseRentInfoDTO.setEmpName(employeeName);
			tdsHouseRentInfoDTO.setFromDate(fromMonth);
			tdsHouseRentInfoDTO.setToDate(toMonth);
			tdsHouseRentInfoDTO.setAddress(address);
			tdsHouseRentInfoDTO.setCity(city);
			tdsHouseRentInfoDTO.setTotalRental(rentPaid);
			tdsHouseRentInfoDTO.setLandlordName(landLordName);
			tdsHouseRentInfoDTO.setAddressOfLandlord(landLordAddr);
			tdsHouseRentInfoDTO.setLandlordPan(landLordPan);

			tdsHouseRentInfoList.add(tdsHouseRentInfoDTO);
		}
		return tdsHouseRentInfoList;
	}

	public List<ReportPayOutDTO> databaseListToUIDtoLWFReports(List<Object[]> labourWelfareFundMonthlyList,
			String wise) {
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();

		if (wise.equalsIgnoreCase("EW")) {
			for (Object[] LwfObj : labourWelfareFundMonthlyList) {
				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String employeeCode = LwfObj[0] != null ? (String) LwfObj[0] : null;
				String employeeName = LwfObj[1] != null ? (String) LwfObj[1] : null;
				String jobLocation = LwfObj[2] != null ? (String) LwfObj[2] : null;
				String state = LwfObj[3] != null ? (String) LwfObj[3] : null;
				BigDecimal standardGross = LwfObj[4] != null ? (BigDecimal) LwfObj[4] : null;
				BigDecimal lwfEmployeeContr = LwfObj[5] != null ? (BigDecimal) LwfObj[5] : null;
				BigDecimal lwfEmployerContr = LwfObj[6] != null ? (BigDecimal) LwfObj[6] : null;

				reportPayoutDto.setEmployeeCode(employeeCode);
				reportPayoutDto.setEmpName(employeeName);
				reportPayoutDto.setJobLocation(jobLocation);
				reportPayoutDto.setStateName(state);
				reportPayoutDto.setGrossSalary(standardGross);
				reportPayoutDto.setLwfEmployeeAmount(lwfEmployeeContr);
				reportPayoutDto.setLwfEmployerAmount(lwfEmployerContr);

				reportPayOutDTOList.add(reportPayoutDto);
			}
		} else if (wise.equalsIgnoreCase("SW")) {
			for (Object[] LwfObj : labourWelfareFundMonthlyList) {
				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String state = LwfObj[0] != null ? (String) LwfObj[0] : null;
				BigInteger totalEmployee = LwfObj[1] != null ? (BigInteger) LwfObj[1] : null;
				BigDecimal totalGrossAmount = LwfObj[2] != null ? (BigDecimal) LwfObj[2] : null;
				BigDecimal lwfEmployeeContr = LwfObj[3] != null ? (BigDecimal) LwfObj[3] : null;
				BigDecimal lwfEmployerContr = LwfObj[4] != null ? (BigDecimal) LwfObj[4] : null;

				reportPayoutDto.setStateName(state);
				reportPayoutDto.setTotalEmployee(totalEmployee.intValue());
				reportPayoutDto.setTotalEarning(totalGrossAmount);
				reportPayoutDto.setLwfEmployeeAmount(lwfEmployeeContr);
				reportPayoutDto.setLwfEmployerAmount(lwfEmployerContr);

				reportPayOutDTOList.add(reportPayoutDto);
			}
		}
		return reportPayOutDTOList;

	}

	public Map<String, List<ReportPayOutDTO>> databaseListToUIDtoPTMonthlyReports(List<Object[]> annualReportList,
			List<Object[]> processMonthList, String wise) {
		// TODO Auto-generated method stub

		Map<String, List<ReportPayOutDTO>> annualMap = new HashMap<String, List<ReportPayOutDTO>>();

		if (wise.equalsIgnoreCase("EW")) {

			for (Object[] annualSummary : processMonthList) {
				List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
				String summaryProccessMonth = annualSummary[0] != null ? (String) annualSummary[0] : null;

				for (Object[] reportPayoutObj : annualReportList) {
					String proccessMonth = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;

					if (summaryProccessMonth.equalsIgnoreCase(proccessMonth)) {

						ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

						String employeeCode = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
						String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
						String jobLocation = reportPayoutObj[2] != null ? (String) reportPayoutObj[2] : null;
						String state = reportPayoutObj[3] != null ? (String) reportPayoutObj[3] : null;
						BigDecimal profTaxGross = reportPayoutObj[4] != null ? (BigDecimal) reportPayoutObj[4] : null;
						BigDecimal amount = reportPayoutObj[5] != null ? (BigDecimal) reportPayoutObj[5] : null;
						String processMonth = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;

						reportPayoutDto.setEmployeeCode(employeeCode);
						reportPayoutDto.setEmpName(employeeName);
						reportPayoutDto.setJobLocation(jobLocation);
						reportPayoutDto.setStateName(state);
						reportPayoutDto.setTotalEarning(profTaxGross);
						reportPayoutDto.setAmount(amount);
						reportPayoutDto.setProcessMonth(processMonth);

						reportPayoutList.add(reportPayoutDto);
					}

				}
				annualMap.put(summaryProccessMonth, reportPayoutList);
			}
		} else if (wise.equalsIgnoreCase("SW")) {

			for (Object[] annualSummary : processMonthList) {
				List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
				String summaryProccessMonth = annualSummary[0] != null ? (String) annualSummary[0] : null;

				for (Object[] reportPayoutObj : annualReportList) {
					String proccessMonth = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;

					if (summaryProccessMonth.equalsIgnoreCase(proccessMonth)) {

						ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

						String state = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
						BigInteger totalEmployee = reportPayoutObj[1] != null ? (BigInteger) reportPayoutObj[1] : null;
						BigDecimal totalGrossAmount = reportPayoutObj[2] != null ? (BigDecimal) reportPayoutObj[2]
								: null;
						BigDecimal totalPTAmount = reportPayoutObj[3] != null ? (BigDecimal) reportPayoutObj[3] : null;
						String processMonth = reportPayoutObj[4] != null ? (String) reportPayoutObj[4] : null;

						reportPayoutDto.setStateName(state);
						reportPayoutDto.setTotalEmployee(totalEmployee.intValue());
						reportPayoutDto.setTotalEarning(totalGrossAmount);
						reportPayoutDto.setAmount(totalPTAmount);
						reportPayoutDto.setProcessMonth(processMonth);

						reportPayoutList.add(reportPayoutDto);
					}

				}
				annualMap.put(summaryProccessMonth, reportPayoutList);
			}
		}

		return annualMap;
	}

	public List<ReportPayOutDTO> databaseListToUIDtoProcessMonth(List<Object[]> processMonthList) {
		// TODO Auto-generated method stub
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();

		for (Object[] monthObj : processMonthList) {
			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String employeeCode = monthObj[1] != null ? (String) monthObj[1] : null;
			String processMonth = monthObj[0] != null ? (String) monthObj[0] : null;

			reportPayoutDto.setEmployeeCode(employeeCode);
			reportPayoutDto.setProcessMonth(processMonth);
			reportPayOutDTOList.add(reportPayoutDto);
		}
		return reportPayOutDTOList;
	}

	public List<ReportPayOutDTO> uiDTOtoPfAnnualList(List<Object[]> pfAnnualList) {

		List<ReportPayOutDTO> reportPayoutList = new ArrayList<ReportPayOutDTO>();

		for (Object[] reportPayoutObj : pfAnnualList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
			BigDecimal totalEarning = reportPayoutObj[1] != null ? (new BigDecimal(reportPayoutObj[1].toString()))
					: null;
			BigDecimal basicEarning = reportPayoutObj[2] != null ? (new BigDecimal(reportPayoutObj[2].toString()))
					: null;
			BigDecimal pensionEarningSalary = reportPayoutObj[3] != null
					? (new BigDecimal(reportPayoutObj[3].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[4] != null ? (new BigDecimal(reportPayoutObj[4].toString())) : null;

			BigDecimal providentFundEmployee = reportPayoutObj[5] != null
					? (new BigDecimal(reportPayoutObj[5].toString()))
					: null;

			BigDecimal providentFundEmployer = reportPayoutObj[6] != null
					? (new BigDecimal(reportPayoutObj[6].toString()))
					: null;

			BigDecimal providentFundEmployerPension = reportPayoutObj[7] != null
					? (new BigDecimal(reportPayoutObj[7].toString()))
					: null;

			BigDecimal dearnessAllowanceEarning = reportPayoutObj[8] != null
					? (new BigDecimal(reportPayoutObj[8].toString()))
					: null;

			reportPayoutDto.setProcessMonth(processMonth);
			reportPayoutDto.setTotalEarning(totalEarning);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setPensionEarningSalary(pensionEarningSalary);
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setProvidentFundEmployee(providentFundEmployee);
			reportPayoutDto.setProvidentFundEmployer(providentFundEmployer);
			reportPayoutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
			reportPayoutDto.setDearnessAllowanceEarning(dearnessAllowanceEarning);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;
	}

	public Map<String, List<ReportPayOutDTO>> objectListToPFControAnnualReport(List<Object[]> pfMonthlyList,
			List<Object[]> pfAnnualList) {

		Map<String, List<ReportPayOutDTO>> annualMap = new HashMap<String, List<ReportPayOutDTO>>();

		for (Object[] annualSummary : pfAnnualList) {

			List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

			String summaryProccessMonth = annualSummary[0] != null ? (String) annualSummary[0] : null;

			for (Object[] reportPayoutObj : pfMonthlyList) {
				String proccessMonth = reportPayoutObj[10] != null ? (String) reportPayoutObj[10] : null;

				if (summaryProccessMonth.equalsIgnoreCase(proccessMonth)) {

					ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

					String UANNo = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
					String employeeName = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
					BigDecimal totalEarning = reportPayoutObj[2] != null
							? (new BigDecimal(reportPayoutObj[2].toString()))
							: null;
					BigDecimal basicEarning = reportPayoutObj[3] != null
							? (new BigDecimal(reportPayoutObj[3].toString()))
							: null;
					BigDecimal pensionEarningSalary = reportPayoutObj[4] != null
							? (new BigDecimal(reportPayoutObj[4].toString()))
							: null;
					BigDecimal absent = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString()))
							: null;
					BigDecimal providentFundEmployee = reportPayoutObj[6] != null
							? (new BigDecimal(reportPayoutObj[6].toString()))
							: null;
					BigDecimal providentFundEmployer = reportPayoutObj[7] != null
							? (new BigDecimal(reportPayoutObj[7].toString()))
							: null;
					BigDecimal providentFundEmployerPension = reportPayoutObj[8] != null
							? (new BigDecimal(reportPayoutObj[8].toString()))
							: null;
					BigDecimal dearnessAllowanceEarning = reportPayoutObj[9] != null
							? (new BigDecimal(reportPayoutObj[9].toString()))
							: null;

					reportPayoutDto.setUnNo(UANNo);
					reportPayoutDto.setName(employeeName);
					reportPayoutDto.setTotalEarning(totalEarning);
					reportPayoutDto.setBasicEarning(basicEarning);
					reportPayoutDto.setPensionEarningSalary(pensionEarningSalary);
					reportPayoutDto.setAbsense(absent);
					reportPayoutDto.setProvidentFundEmployee(providentFundEmployee);
					reportPayoutDto.setProvidentFundEmployer(providentFundEmployer);
					reportPayoutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
					reportPayoutDto.setDearnessAllowanceEarning(dearnessAllowanceEarning);
					reportPayoutList.add(reportPayoutDto);
				}

			}
			annualMap.put(summaryProccessMonth, reportPayoutList);
		}
		return annualMap;
	}
    public List<ReportPayOutDTO> objectListToEmployeePFControList(List<Object[]> employeePFControList) {

		List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();

		for (Object[] reportPayoutObj : employeePFControList) {

			ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

			String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;

			BigDecimal totalEarning = reportPayoutObj[1] != null ? (new BigDecimal(reportPayoutObj[1].toString()))
					: null;
			BigDecimal basicEarning = reportPayoutObj[2] != null ? (new BigDecimal(reportPayoutObj[2].toString()))
					: null;
			BigDecimal pensionEarningSalary = reportPayoutObj[3] != null
					? (new BigDecimal(reportPayoutObj[3].toString()))
					: null;

			BigDecimal providentFundEmployee = reportPayoutObj[4] != null
					? (new BigDecimal(reportPayoutObj[4].toString()))
					: null;
			BigDecimal providentFundEmployer = reportPayoutObj[5] != null
					? (new BigDecimal(reportPayoutObj[5].toString()))
					: null;
			BigDecimal providentFundEmployerPension = reportPayoutObj[6] != null
					? (new BigDecimal(reportPayoutObj[6].toString()))
					: null;
			BigDecimal totalContribution = reportPayoutObj[7] != null ? (new BigDecimal(reportPayoutObj[7].toString()))
					: null;
			BigDecimal absent = reportPayoutObj[8] != null ? (new BigDecimal(reportPayoutObj[8].toString())) : null;

			reportPayoutDto.setProcessMonth(processMonth);
			reportPayoutDto.setTotalEarning(totalEarning);
			reportPayoutDto.setBasicEarning(basicEarning);
			reportPayoutDto.setPensionEarningSalary(pensionEarningSalary);
			reportPayoutDto.setAbsense(absent);
			reportPayoutDto.setProvidentFundEmployee(providentFundEmployee);
			reportPayoutDto.setProvidentFundEmployer(providentFundEmployer);
			reportPayoutDto.setProvidentFundEmployerPension(providentFundEmployerPension);
			reportPayoutDto.setTotalContribution(totalContribution);
			reportPayoutList.add(reportPayoutDto);
		}
		return reportPayoutList;

	}
	
	
	public List<ReportPayOutDTO> databaseListToUIDtoEsicReports(List<Object[]> esicStatementAnnualSummaryList,
			Long financialYearId, Long employeeId) {
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();

		 if (employeeId !=0L) {
				for (Object[] reportPayoutObj : esicStatementAnnualSummaryList) {
					ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

					String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
					BigDecimal grossSalary = reportPayoutObj[1] != null ? (new BigDecimal(reportPayoutObj[1].toString())): null;
					BigDecimal esi_Employee = reportPayoutObj[2] != null ? (BigDecimal) reportPayoutObj[2] : null;
					BigDecimal esi_Employer = reportPayoutObj[3] != null ? (BigDecimal) reportPayoutObj[3] : null;
					BigDecimal totalShare = reportPayoutObj[4] != null ? (BigDecimal) reportPayoutObj[4] : null;
					
					BigDecimal absense = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;
					
					
					reportPayoutDto.setProcessMonth(processMonth);
					reportPayoutDto.setGrossSalary(grossSalary);
					reportPayoutDto.setEsi_Employee(esi_Employee);
					reportPayoutDto.setEsi_Employer(esi_Employer);
					reportPayoutDto.setTotalEarning(totalShare);
					reportPayoutDto.setAbsense(absense);
					reportPayOutDTOList.add(reportPayoutDto);
				}
			}
		
		 if (financialYearId !=0L) {
			for (Object[] reportPayoutObj : esicStatementAnnualSummaryList) {
				ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

				String processMonth = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
				BigDecimal grossSalary = reportPayoutObj[1] != null ? (new BigDecimal(reportPayoutObj[1].toString())): null;
				BigDecimal esi_Employee = reportPayoutObj[2] != null ? (BigDecimal) reportPayoutObj[2] : null;
				BigDecimal esi_Employer = reportPayoutObj[3] != null ? (BigDecimal) reportPayoutObj[3] : null;
				BigDecimal totalShare = reportPayoutObj[4] != null ? (BigDecimal) reportPayoutObj[4] : null;
				
				BigDecimal absense = reportPayoutObj[5] != null ? (new BigDecimal(reportPayoutObj[5].toString())) : null;
				
				
				reportPayoutDto.setProcessMonth(processMonth);
				reportPayoutDto.setGrossSalary(grossSalary);
				reportPayoutDto.setEsi_Employee(esi_Employee);
				reportPayoutDto.setEsi_Employer(esi_Employer);
				reportPayoutDto.setTotalEarning(totalShare);
				reportPayoutDto.setAbsense(absense);
				reportPayOutDTOList.add(reportPayoutDto);
			}
		}
		return reportPayOutDTOList;

	}

	public Map<String, List<ReportPayOutDTO>> databaseListToUIDtoEsicContributionReports(
			List<Object[]> esicStatementAnnualMonthlyList, List<Object[]> esicStatementAnnualSummaryList, Long financialYearId) {
		// TODO Auto-generated method stub
		Map<String, List<ReportPayOutDTO>> annualMap = new HashMap<String, List<ReportPayOutDTO>>();
		
		 if (financialYearId !=0L) {

			for (Object[] annualSummary : esicStatementAnnualSummaryList) {
				List<ReportPayOutDTO> reportPayoutList = new ArrayList<>();
				String summaryProccessMonth = annualSummary[0] != null ? (String) annualSummary[0] : null;

				for (Object[] reportPayoutObj : esicStatementAnnualMonthlyList) {
					String proccessMonth = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;

					if (summaryProccessMonth.equalsIgnoreCase(proccessMonth)) {

						ReportPayOutDTO reportPayoutDto = new ReportPayOutDTO();

						String esiNo = reportPayoutObj[0] != null ? (String) reportPayoutObj[0] : null;
						String name = reportPayoutObj[1] != null ? (String) reportPayoutObj[1] : null;
						BigDecimal grossSalary = reportPayoutObj[2] != null ? (BigDecimal) reportPayoutObj[2] : null;
						BigDecimal esi_Employee = reportPayoutObj[3] != null ? (BigDecimal) reportPayoutObj[3] : null;
						BigDecimal esi_Employer = reportPayoutObj[4] != null ? (BigDecimal) reportPayoutObj[4] : null;
						BigDecimal absense = reportPayoutObj[5] != null ? (BigDecimal) reportPayoutObj[5] : null;
						String processMonth = reportPayoutObj[6] != null ? (String) reportPayoutObj[6] : null;

						reportPayoutDto.setEsiNo(esiNo);
						reportPayoutDto.setName(name);
						reportPayoutDto.setGrossSalary(grossSalary);
						reportPayoutDto.setEsi_Employee(esi_Employee);
						reportPayoutDto.setEsi_Employer(esi_Employer);
						reportPayoutDto.setAbsense(absense);
						reportPayoutDto.setProcessMonth(processMonth);

						reportPayoutList.add(reportPayoutDto);
					}

				}
				annualMap.put(summaryProccessMonth, reportPayoutList);
			}
		}

		return annualMap;

	}

    public List<ReportPayOutDTO> paymentTransferSheetReport(List<Object[]> objectList, String processMonth) {
		List<ReportPayOutDTO> reportPayOutDTOList = new ArrayList<ReportPayOutDTO>();
		for (Object[] reportPayOutObj : objectList) {
			if (reportPayOutObj != null) {
				ReportPayOutDTO reportPayOutDto = new ReportPayOutDTO();

				String employeeName = reportPayOutObj[0] != null ? (String) reportPayOutObj[0] : "";
				String employeeCode = reportPayOutObj[1] != null ? (String) reportPayOutObj[1] : "";
				Long companyId = reportPayOutObj[2] != null ? Long.parseLong(reportPayOutObj[2].toString()) : null;
				String companyName = reportPayOutObj[3] != null ? (String) reportPayOutObj[3] : "";
				String departmentName = reportPayOutObj[4] != null ? (String) reportPayOutObj[4] : "";
				String bankName = reportPayOutObj[5] != null ? (String) reportPayOutObj[5] : "";
				String accountNumber = reportPayOutObj[6] != null ? (String) reportPayOutObj[6] : null;
				String IFSCCode = reportPayOutObj[7] != null ? (String) reportPayOutObj[7] : null;
				Date dateOfJoining = reportPayOutObj[8] != null ? (Date) reportPayOutObj[8] : null;
				BigDecimal basic = reportPayOutObj[9] != null ? (BigDecimal) reportPayOutObj[9] : null;
				BigDecimal da = reportPayOutObj[10] != null ? (BigDecimal) reportPayOutObj[10] : null;
				BigDecimal conveyanceAllowance = reportPayOutObj[11] != null ? (BigDecimal) reportPayOutObj[11] : null;
				BigDecimal hra = reportPayOutObj[12] != null ? (BigDecimal) reportPayOutObj[12] : null;
				BigDecimal medicalAllowance = reportPayOutObj[13] != null ? (BigDecimal) reportPayOutObj[13] : null;
				BigDecimal advanceBonus = reportPayOutObj[14] != null ? (BigDecimal) reportPayOutObj[14] : null;
				BigDecimal specialAllowance = reportPayOutObj[15] != null ? (BigDecimal) reportPayOutObj[15] : null;
				BigDecimal companyBenefits = reportPayOutObj[16] != null ? (BigDecimal) reportPayOutObj[16] : null;
				BigDecimal grossSalary = reportPayOutObj[17] != null ? (BigDecimal) reportPayOutObj[17] : null;
				BigDecimal absense = reportPayOutObj[18] != null ? (BigDecimal) reportPayOutObj[18] : null;
				BigDecimal payDays = reportPayOutObj[19] != null ? (BigDecimal) reportPayOutObj[19] : null;
				BigDecimal basicEarning = reportPayOutObj[20] != null ? (BigDecimal) reportPayOutObj[20] : null;
				BigDecimal conveyanceAllowanceEarning = reportPayOutObj[21] != null ? (BigDecimal) reportPayOutObj[21]
						: null;
				BigDecimal hraEarning = reportPayOutObj[22] != null ? (BigDecimal) reportPayOutObj[22] : null;
				BigDecimal medicalAllowanceEarning = reportPayOutObj[23] != null ? (BigDecimal) reportPayOutObj[23]
						: null;
				BigDecimal advanceBonusEarning = reportPayOutObj[24] != null ? (BigDecimal) reportPayOutObj[24] : null;
				BigDecimal specialAllowanceEarning = reportPayOutObj[25] != null ? (BigDecimal) reportPayOutObj[25]
						: null;
				BigDecimal companyBenefitsEarning = reportPayOutObj[26] != null ? (BigDecimal) reportPayOutObj[26]
						: null;
				BigDecimal otherAllowanceEarning = reportPayOutObj[27] != null ? (BigDecimal) reportPayOutObj[27]
						: null;
				BigDecimal totalEarning = reportPayOutObj[28] != null ? (BigDecimal) reportPayOutObj[28] : null;
				BigDecimal loan = reportPayOutObj[29] != null ? (BigDecimal) reportPayOutObj[29] : null;
				BigDecimal edhocDeduction = reportPayOutObj[30] != null ? (BigDecimal) reportPayOutObj[30] : null;
				BigDecimal providentFundEmployee = reportPayOutObj[31] != null ? (BigDecimal) reportPayOutObj[31]
						: null;
				BigDecimal esi_Employee = reportPayOutObj[32] != null ? (BigDecimal) reportPayOutObj[32] : null;
				BigDecimal pt = reportPayOutObj[33] != null ? (BigDecimal) reportPayOutObj[33] : null;
				BigDecimal tds = reportPayOutObj[34] != null ? (BigDecimal) reportPayOutObj[34] : null;
				BigDecimal totalDeduction = reportPayOutObj[35] != null ? (BigDecimal) reportPayOutObj[35] : null;
				BigDecimal netPayableAmount = reportPayOutObj[36] != null ? (BigDecimal) reportPayOutObj[36] : null;
				BigDecimal otherEarning = reportPayOutObj[37] != null ? (BigDecimal) reportPayOutObj[37] : null;
				String transNo = reportPayOutObj[38] != null ? (String) reportPayOutObj[38] : null;
				String status = reportPayOutObj[39] != null ? (String) reportPayOutObj[39] : null;

				reportPayOutDto.setName(employeeName);
				reportPayOutDto.setEmployeeCode(employeeCode);
				reportPayOutDto.setBankName(bankName);
				reportPayOutDto.setDepartmentName(departmentName);
				reportPayOutDto.setIfscCode(IFSCCode);
				reportPayOutDto.setAccountNumber(accountNumber);
				reportPayOutDto.setDateOfJoining(dateOfJoining);
				reportPayOutDto.setBasic(basic);
				reportPayOutDto.setConveyanceAllowance(conveyanceAllowance);
				reportPayOutDto.setHra(hra);
				reportPayOutDto.setHraEarning(hraEarning);
				reportPayOutDto.setOtherAllowanceEarning(otherAllowanceEarning);
				reportPayOutDto.setAbsense(absense);
				reportPayOutDto.setAdvanceBonus(advanceBonus);
				reportPayOutDto.setAdvanceBonusEarning(advanceBonusEarning);
				reportPayOutDto.setBasicEarning(basicEarning);

				reportPayOutDto.setCompanyBenefits(companyBenefits);
				reportPayOutDto.setCompanyBenefitsEarning(companyBenefitsEarning);
				reportPayOutDto.setConveyanceAllowanceEarning(conveyanceAllowanceEarning);
				reportPayOutDto.setEsi_Employee(esi_Employee);
				reportPayOutDto.setGrossSalary(grossSalary);
				reportPayOutDto.setLoan(loan);
				reportPayOutDto.setMedicalAllowance(medicalAllowance);
				reportPayOutDto.setMedicalAllowanceEarning(medicalAllowanceEarning);
				reportPayOutDto.setNetPayableAmount(netPayableAmount);
				reportPayOutDto.setPayDays(payDays);
				reportPayOutDto.setProvidentFundEmployee(providentFundEmployee);
				reportPayOutDto.setPt(pt);
				reportPayOutDto.setOtherEarning(otherEarning);
				reportPayOutDto.setSpecialAllowance(specialAllowance);
				reportPayOutDto.setSpecialAllowanceEarning(specialAllowanceEarning);
				reportPayOutDto.setTds(tds);
				reportPayOutDto.setTotalDeduction(totalDeduction);
				reportPayOutDto.setTotalEarning(totalEarning);
				reportPayOutDto.setCompanyId(companyId);
				reportPayOutDto.setCompanyName(companyName);
				reportPayOutDto.setTransactionNo(transNo);

				if (transNo != null) {
					reportPayOutDto.setEmployeeStatus("Transferred");
				} else if (status != null && status.equals("HO")) {
					reportPayOutDto.setEmployeeStatus("HOLD");
				} else {
					reportPayOutDto.setEmployeeStatus("Need To Transfer");
				}

				reportPayOutDTOList.add(reportPayOutDto);
			}
		}

		return reportPayOutDTOList;
	}
	
	

}
