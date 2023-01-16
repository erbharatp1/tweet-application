package com.csipl.hrms.service.payroll;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

 
public interface ReportPayOutService {
	public List<Object[]> findPayOutReportOfDepartment(Long departmentId,String processMonth);
	
	public List<Object[]> findPayOutReportOfCompany(Long companyId,String processMonth);
	
 	public List<ReportPayOut> findEmployeesPayOutReport(Long departmentId,String processMonth);
 	
 	public List<ReportPayOut> findEmployeesPaysheetBydept(Long companyId,Long departmentId,String processMonth);
 	
	public ReportPayOut findEmployeePayOutPdf(String  employeeCode,String processMonth,Long companyId);
	
	public List<Object[]> findEsiEpfReport(String processMonth,Long companyId);
	
	public List<Object[]> findEmployeesESIPayOutReport( Long companyId, String processMonth );
	
	public List<ReportPayOut> findEsiReport(String esicNo);
	
	public List<ReportPayOut> findPfReport(String uanno);
	
 	public List<ReportPayOut> findAllEmployeesPayOutReport(Long companyId,String processMonth);
 	
 	public List<ReportPayOut> findAllEmployeesPaysheet(Long companyId,String processMonth);
 	
 	public void save(List<ReportPayOut> payout);
 	
 	public List<Object[]>  findAllEmployeesPayOutReportForSalaryProcess(Long companyId, String processMonth);
 	
 	public	List<Object[]>  findEmployeesPayOutReportForSalaryProcess(Long departmentId, String processMonth);
 	
	public Long checkEmployeePayroll(Long empId);
	
	public Long checkEmployeePayrollForSalarySlip(String  employeeCode,String processMonth,Long companyId);
	
	public Long checkPayrollOfEmployee(Long employeeId,String processMonth);
	
	public void saveReportPayout(ReportPayOut reportPayout);
	
	public ReportPayOut findReportPayout(Long employeeId,String processMonth,Long companyId);
	
	public List<Object[]> findReportPayoutForRecordPayment(String processMonth,Long companyId);
	
	public int updateReportPayout(Long employeeId,String transctionNo,String mode,String processMonth,Date processDate,Long companyId);

	public List<ReportPayOut> getSalary(Long employeeId);
	
	public  List<ReportPayOut>  createSalary(Long employeeId);
	
	public List<String>	getReportPayOutProcessMonthList(Long companyId, Long financialYearId);
	
	public List<FinancialYear>	findAllFinancialYear(Long companyId);
	
	public List<String> getReportPayOutMonth(Long employeeId);

	public Map<String, Long> recordPaymentCount(String companyId, String payrollMonth);
	
	public List<Object[]> payOutInfo(Long employeeId);
	
	public List<Object[]> lastPaid(Long employeeId);

	public List<Object[]> findPfWorkingConsultantReport(String processMonth, Long longcompanyId);

	public List<Object[]> getEsicConsutantEmployeeCount(String processMonth, Long longcompanyId);
	
	public	List<String> getProcessMonthListByfinancialYear( Long financialYearId);

	public List<Object[]> findPfWorkingConsultantReportMonthly(Long financialYearId, Long companyId);
    public List<Object[]> getEmployeePFInfo(Long companyId, Long employeeId, String activeStatus);
	
	
	public List<Object[]> findEsicContributionStatementAnnualSummary(Long companyId, Long financialYearId, Long employeeId);

	public List<Object[]> findEsicContributionStatementAnnualMonthly(Long companyId, Long financialYearId);

	public List<Object[]> findEmployeeEsic(Long employeeId, Long companyId);

	public List<String> getPayHeadList(Long companyId, String status);
}
