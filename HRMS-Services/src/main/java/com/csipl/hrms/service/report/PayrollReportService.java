package com.csipl.hrms.service.report;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public interface PayrollReportService {
	public List<Object[]>  findPTReport(Long companyId ,String fromProcessMonth,String toProcessMonth,Long departmentId,Long empId,Long stateId);
	public List<Object[]>  findProvisionReport(Long companyId ,Date fromDate,Date toDate,Long departmentId);
	public List<Object[]>  findPayrollReportByMonth(Long companyId,Long departmentId, String processMonth);
	public List<Object[]>  findPayrollReportByempId(Long companyId,Long employeeId, String fromProcessMonth, String toProcessMonth);
	public List<Object[]> findPayRollBankTRF(Long companyId , String processMonth, String bankName );
	public Long checkForRecoReprotAvailability(Long longDeptId,String processMonth, String checkReco);
	public List<Object[]> findReconciliationReport(Long companyId, Long longDeptId, String processMonth, String checkReco);
	public List<Object[]>  findEpfEcrReport(Long companyId, String processMonth);
	public List<Object[]>  findEpfReport(Long companyId, String processMonth);
	public List<Object[]>  findEsicEcrReport(Long companyId, String processMonth);
	public List<Object[]>  findEsicReport(Long companyId, String processMonth);
	public List<Object[]>  getTotal(Long companyId, String processMonth);
	public List<Object[]>  findEarningDeductionForEmployee(Long companyId, List<Long> departmentId, String processMonth, String type );
	public List<Object[]> getSalarySheetData(Long longcompanyId, List<Long> ids, String processMonth);
	public String[] getEarnngPayHeads(Long longcompanyId);
	String[] getDeductionPayHeads(Long longcompanyId);
	public List<Object[]> findProfessionalTaxStatementMonthly(Long longcompanyId, String processMonth, String wise);
	public List<Object[]> getArrearMonthlyReport(Long longcompanyId, List<Long> departmentDTOList, String processMonth);
	public List<Object[]> getArrearAnnualyReport(Long longcompanyId, Long financialYrId);
	public FinancialYear findFinancialYear(Long longcompanyId, Long financialYearId);
	
	public List<Object[]> getPFExitStatementReport(Long longcompanyId, Date fromDate, Date toDate);
	public List<Object[]> getESIExitStatementReport(Long longcompanyId, Date fromDate, Date toDate);
	
	public List<Object[]> getPaymentRecordStatementReport(Long longcompanyId, String processMonth, List<Long> departmentDTOList);
	public List<Object[]> getESIArrearECRReport(Long longcompanyId, String processMonth);
	public List<Object[]> getEsicExcludedEmployee(Long longcompanyId, String processMonth);
	//public List<Object[]> findEpfArrearEcrReport(Long longcompanyId, String processMonth);
	public String[] getcurrentPayHeads(Long longcompanyId);
	public List<Object[]> getPayStructureReport(Long longcompanyId, Long longemployeeId,List<Long> ids, String status);
	public List<Object[]> getAnnualSummary(Long  companyId, Long financialYearId);
	public List<Object[]> getSalarySheetAnnual(Long companyId, Long financialYearId);
	public List<Object[]> getcurrentCostToCompanyReport(Long companyId, Long employeeId, List<Long> departmentList, String status);
	public List<ReportPayOut> findAllEmployee(Long companyId, String processMonth);
	
	public List<Object[]> getGrossSumEmployee(Long empId, Long companyId, FinancialYear financialYear);
	public String[] getAllEarnngPayHeads(Long companyId);
	public String[] getAllDeductionPayHeads(Long companyId);
	
	public List<Object[]> getIncomeTaxMonthlyReport(Long companyId, List<Long> departmentIds, String processMonth);
	public List<Object[]> getIncomeTaxAnnualyReport(Long companyId, Long financialYearId);
	public  String [] getMonthByFinId(Long financialYearId);
	
	public List<Object[]> getRentPaidLandlordDetailsReport(Long companyId, Long financialYearId);
	
	public List<Object[]> findLabourWelfareFundSummaryMonthly(Long companyId, String processMonth, String wise);
	
	public List<Object[]> findProfessionalTaxStatementAnnualSummary(Long companyId, Long financialYearId, String wise);
	public List<Object[]> findProfessionalTaxStatementAnnualMonthly(Long companyId, Long financialYearId, String wise);
	public List<Object[]> findProcessMonth(Long companyId, Long financialYearId, String wise);
	
	public List<Object[]> getPFControAnnual(Long companyId, Long financialYearId);
	
	public List<Object[]> getPFControMonthly(Long companyId, Long financialYearId);
	
	public List<Object[]> getTotalPFControbution(Long companyId, Long financialYearId);
	public List<Object[]> getTotalMonthly(Long companyId, Long financialYearId);
	
    public List<Object[]> getPFContributionEmpWise(Long companyId, Long employeeId, String activeStatus);


	public List<Object[]> getTotalOfEmployee(Long companyId, Long employeeId, String activeStatus);
    public List<Object[]> getPaymentTransferSheet(Long financialYearId, String processMonth, Long departmentId);

}

