package com.csipl.hrms.service.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.enums.StandardEarningEnum;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.report.repository.PayrollReportRepository;

@Service("payrollReportService")
public class PayrollReportServiceImpl implements PayrollReportService {

	@Autowired
	PayrollReportRepository payrollReportRepository;

	@Autowired
	FinancialYearRepository financialYearRepository;

	@Override
	public List<Object[]> findProvisionReport(Long companyId, Date fromDate, Date toDate, Long departmentId) {
		System.out.println("service===fromdate" + fromDate + "toDate==" + toDate);
		if (departmentId == 0) {
			return payrollReportRepository.findProvisionReport(companyId, fromDate, toDate);
		} else
			return payrollReportRepository.findProvisionReportbydeptId(companyId, fromDate, toDate, departmentId);
	}

	@Override
	public List<Object[]> findPayrollReportByMonth(Long companyId, Long departmentId, String processMonth) {
		if (departmentId == 0)
			return payrollReportRepository.findPayrollReportByMonth(companyId, processMonth);
		else
			return payrollReportRepository.findPayrollReportByMonthBydept(companyId, departmentId, processMonth);
	}

	@Override
	public List<Object[]> findPayrollReportByempId(Long companyId, Long employeeId, String fromProcessMonth,
			String toProcessMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findPayrollReportByEmpId(companyId, employeeId, fromProcessMonth,
				toProcessMonth);
	}

	@Override
	public List<Object[]> findPayRollBankTRF(Long companyId, String processMonth, String bankName) {
		return payrollReportRepository.findPayRollBankTRF(companyId, processMonth, bankName);
	}

	@Override
	public Long checkForRecoReprotAvailability(Long longDeptId, String processMonth, String checkReco) {
		return payrollReportRepository.checkForRecoReprotAvailability(checkReco, longDeptId, processMonth);
	}

	@Override
	public List<Object[]> findReconciliationReport(Long companyId, Long longDeptId, String processMonth,
			String checkReco) {
		String status = null;
		if (checkReco.equals("true")) {
			status = "AC";
			if (longDeptId == 0) {

				return payrollReportRepository.findNonReconciliationReportWithoutDept(companyId, processMonth,
						checkReco, status);
			} else
				return payrollReportRepository.findNonReconciliationReportWithDept(companyId, longDeptId, processMonth,
						checkReco, status);
		} else {
			status = "AC";
			if (longDeptId == 0)
				return payrollReportRepository.findReconciliationReportWithoutDept(companyId, processMonth, checkReco,
						status);
			else
				return payrollReportRepository.findReconciliationReportWithDept(companyId, longDeptId, processMonth,
						checkReco, status);
		}
	}

	@Override
	public List<Object[]> findPTReport(Long companyId, String fromProcessMonth, String toProcessMonth,
			Long departmentId, Long empId, Long stateId) {
		return payrollReportRepository.findPTReport(companyId, fromProcessMonth, toProcessMonth, empId, stateId,
				departmentId);
	}

	@Override
	public List<Object[]> findEpfEcrReport(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findEpfEcrReport(companyId, processMonth);
	}

	@Override
	public List<Object[]> findEpfReport(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findEpfReport(companyId, processMonth);
	}

	@Override
	public List<Object[]> findEsicEcrReport(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findEsicEcrReport(companyId, processMonth);
	}

	@Override
	public List<Object[]> findEsicReport(Long companyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findEsicReport(companyId, processMonth);
	}

	@Override
	public List<Object[]> getTotal(Long companyId, String processMonth) {

		return payrollReportRepository.getTotal(companyId, processMonth);
	}

	@Override
	public List<Object[]> findEarningDeductionForEmployee(Long companyId, List<Long> departmentId, String processMonth,
			String type) {
		// TODO Auto-generated method stub
		return payrollReportRepository.findEarningDeductionForEmployee(companyId, departmentId, processMonth, type);
	}

	@Override
	public List<Object[]> getSalarySheetData(Long longcompanyId, List<Long> ids, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getSalarySheetData(longcompanyId, ids, processMonth);
		// return null;
	}

	@Override
	public String[] getEarnngPayHeads(Long longcompanyId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getEarnngPayHeads(longcompanyId);
	}

	@Override
	public String[] getDeductionPayHeads(Long longcompanyId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getDeductionPayHeads(longcompanyId);
	}

	@Override
	public List<Object[]> findProfessionalTaxStatementMonthly(Long longcompanyId, String processMonth, String wise) {

		if (wise.equalsIgnoreCase("EW")) {
			return payrollReportRepository.findProfessionalTaxStatementMonthlyEmployeeWise(longcompanyId, processMonth);
		} else if (wise.equalsIgnoreCase("SW")) {
			return payrollReportRepository.findProfessionalTaxStatementMonthlyStateWise(longcompanyId, processMonth);
		}
		return null;
	}

	@Override
	public List<Object[]> getArrearMonthlyReport(Long longcompanyId, List<Long> departmentDTOList,
			String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getArrearMonthlyReport(longcompanyId, departmentDTOList, processMonth);

	}

	@Override
	public List<Object[]> getArrearAnnualyReport(Long longcompanyId, Long financialYrId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getArrearAnnualyReport(longcompanyId, financialYrId);
	}

	@Override
	public FinancialYear findFinancialYear(Long longcompanyId, Long financialYearId) {
		// TODO Auto-generated method stub
		return financialYearRepository.findFinancialYear(longcompanyId, financialYearId);
	}

	@Override
	public List<Object[]> getPFExitStatementReport(Long longcompanyId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getPFExitStatementReport(longcompanyId, fromDate, toDate);
	}

	@Override
	public List<Object[]> getESIExitStatementReport(Long longcompanyId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getESIExitStatementReport(longcompanyId, fromDate, toDate);
	}

	@Override
	public List<Object[]> getPaymentRecordStatementReport(Long longcompanyId, String processMonth,
			List<Long> departmentDTOList) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getPaymentRecordStatementReport(longcompanyId, processMonth, departmentDTOList);
	}

	@Override
	public List<Object[]> getESIArrearECRReport(Long longcompanyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getESIArrearECRReport(longcompanyId, processMonth);
	}

	@Override
	public List<Object[]> getEsicExcludedEmployee(Long longcompanyId, String processMonth) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getEsicExcludedEmployee(longcompanyId, processMonth);
	}

	// @Override
	// public List<Object[]> findEpfArrearEcrReport(Long longcompanyId, String
	// processMonth) {
	// // TODO Auto-generated method stub
	// return
	// payrollReportRepository.findEpfArrearEcrReport(longcompanyId,processMonth);
	// }
	@Override
	public String[] getcurrentPayHeads(Long longcompanyId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getcurrentPayHeads(longcompanyId);
	}

	@Override
	public List<Object[]> getPayStructureReport(Long longcompanyId, Long longemployeeId, List<Long> ids,
			String status) {
		if (longemployeeId.longValue() > 0) {
			return payrollReportRepository.findPayStructureEmployeeWise(longcompanyId, longemployeeId, new Date());
		} else if (!ids.equals(0)) {
			return payrollReportRepository.findPayStructureDepartmentWise(longcompanyId, ids, new Date(), status);
		}
		return null;
	}

	@Override
	public List<Object[]> getAnnualSummary(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		Long pfPayHeadId = StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction();
		Long esicPayHeadId = StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction();
		Long ptPayHeadId = StandardDeductionEnum.PT.getStandardDeduction();
		Long tdsPayHeadId = StandardDeductionEnum.TDS.getStandardDeduction();
		Long loanPayHeadId = StandardDeductionEnum.Loand_And_advance.getStandardDeduction();
		return payrollReportRepository.getAnnualSummary(companyId, financialYearId, pfPayHeadId, esicPayHeadId,
				ptPayHeadId, tdsPayHeadId, loanPayHeadId);
	}

	@Override
	public List<Object[]> getSalarySheetAnnual(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getSalarySheetAnnual(companyId, financialYearId);
	}

	@Override
	public List<Object[]> getcurrentCostToCompanyReport(Long companyId, Long employeeId, List<Long> departmentList,
			String status) {

		// DateUtils dateUtils = new DateUtils();
		// Date currentDate = dateUtils.getCurrentDate();
		if (!employeeId.equals(0l)) {
			return payrollReportRepository.getcurrentCostToCompanyReport(companyId, employeeId, new Date());
		} else if (departmentList != null) {
			return payrollReportRepository.getcurrentCostToCompanyDepartmentWiseReport(companyId, departmentList,
					new Date(), status);
		}
		return null;
	}

	@Override
	public List<ReportPayOut> findAllEmployee(Long companyId, String processMonth) {

		return payrollReportRepository.findAllEmployee(companyId, processMonth);
	}

	@Override
	public List<Object[]> getGrossSumEmployee(Long empId, Long companyId, FinancialYear financialYear) {

		return payrollReportRepository.getGrossSumEmployee(empId, companyId, financialYear.getFinancialYearId());
	}

	@Override
	public String[] getAllEarnngPayHeads(Long companyId) {

		List<Long> earnngPayHeadsId = new ArrayList<>();

		Long arr[] = { StandardEarningEnum.BasicSalary.getStandardEarning(),
				StandardEarningEnum.AdvanceBonus.getStandardEarning(),
				StandardEarningEnum.HouseRentAllowance.getStandardEarning(),
				StandardEarningEnum.LeaveTravelAllowance.getStandardEarning(),
				StandardEarningEnum.ConveyanceAllowance.getStandardEarning(),
				StandardEarningEnum.SpecialAllowance.getStandardEarning(),
				StandardEarningEnum.MedicalAllowance.getStandardEarning(),
				StandardEarningEnum.DearnessAllowance.getStandardEarning(), };

		earnngPayHeadsId = Arrays.asList(arr);

		return payrollReportRepository.getAllEarnngPayHeads(companyId, earnngPayHeadsId);
	}

	@Override
	public String[] getAllDeductionPayHeads(Long companyId) {
		List<Long> deductionPayHeads = new ArrayList<>();

		Long arr[] = { StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction(),
				StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction(),
				StandardDeductionEnum.Pension_Employer_Contribution.getStandardDeduction(),
				StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction(),
				StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction(),
				StandardDeductionEnum.PT.getStandardDeduction(), StandardDeductionEnum.LWF.getStandardDeduction(),

		};

		deductionPayHeads = Arrays.asList(arr);

		return payrollReportRepository.getAllDeductionPayHeads(companyId, deductionPayHeads);
	}

	@Override
	public List<Object[]> getIncomeTaxMonthlyReport(Long companyId, List<Long> departmentIds, String processMonth) {

		return payrollReportRepository.getIncomeTaxMonthlyReport(companyId, departmentIds, processMonth);
	}

	@Override
	public List<Object[]> getIncomeTaxAnnualyReport(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getIncomeTaxAnnualyReport(companyId, financialYearId);
	}

	@Override
	public String[] getMonthByFinId(Long financialYearId) {
		// TODO Auto-generated method stub
		return payrollReportRepository.getMonthByFinId(financialYearId);
	}

	@Override
	public List<Object[]> getRentPaidLandlordDetailsReport(Long companyId, Long financialYearId) {

		return payrollReportRepository.getRentPaidLandlordDetails(companyId, financialYearId);
	}

	@Override
	public List<Object[]> findLabourWelfareFundSummaryMonthly(Long companyId, String processMonth, String wise) {
		// TODO Auto-generated method stub
		if (wise.equalsIgnoreCase("EW")) {
			return payrollReportRepository.findLabourWelfareFundSummaryMonthlyEmployeeWise(companyId, processMonth);
		} else if (wise.equalsIgnoreCase("SW")) {
			return payrollReportRepository.findLabourWelfareFundSummaryMonthlyStateWise(companyId, processMonth);
		}
		return null;
	}

	@Override
	public List<Object[]> findProfessionalTaxStatementAnnualSummary(Long companyId, Long financialYearId, String wise) {
		// TODO Auto-generated method stub

		if (wise.equalsIgnoreCase("EW")) {
			return payrollReportRepository.findProfessionalTaxStatementEmployeeAnnualSummary(companyId,
					financialYearId);
		} else if (wise.equalsIgnoreCase("SW")) {
			return payrollReportRepository.findProfessionalTaxStatementStateWiseAnnualSummery(companyId,
					financialYearId);
		}
		return null;
	}

	@Override
	public List<Object[]> findProfessionalTaxStatementAnnualMonthly(Long companyId, Long financialYearId, String wise) {
		// TODO Auto-generated method stub
		if (wise.equalsIgnoreCase("EW")) {
			return payrollReportRepository.findPtStatementEmployeeWiseMonthly(companyId, financialYearId);
		} else if (wise.equalsIgnoreCase("SW")) {
			return payrollReportRepository.findPtStatementStateWiseMonthly(companyId, financialYearId);
		}
		return null;
	}

	@Override
	public List<Object[]> findProcessMonth(Long companyId, Long financialYearId, String wise) {
		// TODO Auto-generated method stub
		if (wise.equalsIgnoreCase("EW") || wise.equalsIgnoreCase("SW")) {
			return payrollReportRepository.findProcessMonth(companyId, financialYearId);
		}

		return null;
	}

	@Override
	public List<Object[]> getPFControAnnual(Long companyId, Long financialYearId) {

		return payrollReportRepository.findPFControAnnual(companyId, financialYearId);
	}

	@Override
	public List<Object[]> getPFControMonthly(Long companyId, Long financialYearId) {

		return payrollReportRepository.findPFControMonthly(companyId, financialYearId);
	}

	@Override
	public List<Object[]> getTotalPFControbution(Long companyId, Long financialYearId) {

		return payrollReportRepository.findTotalPFControbution(companyId, financialYearId);
	}

	@Override
	public List<Object[]> getTotalMonthly(Long companyId, Long financialYearId) {

		return payrollReportRepository.getTotalMonthly(companyId, financialYearId);
	}

    @Override
	public List<Object[]> getPFContributionEmpWise(Long companyId, Long employeeId, String activeStatus) {
		
		return payrollReportRepository.findPFContributionEmpWise(companyId, employeeId, activeStatus);
	}

	@Override
	public List<Object[]> getTotalOfEmployee(Long companyId, Long employeeId, String activeStatus) {
		
		return payrollReportRepository.findTotalOfEmployee(companyId, employeeId, activeStatus);
	}
    @Override
	public List<Object[]> getPaymentTransferSheet(Long financialYearId, String processMonth, Long departmentId) {
		
		return payrollReportRepository.findPaymentTransferSheet(financialYearId, processMonth, departmentId);
	}
}
