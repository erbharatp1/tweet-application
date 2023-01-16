
package com.csipl.hrms.service.payroll;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;

@Transactional
@Service("reportPayOutService")
public class ReportPayOutServiceImpl implements ReportPayOutService {

	@Autowired
	private ReportPayOutRepository reportPayOutRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;
	
	
	
	
	/**
	 * to get List of Objects from database based on departmentId And processMonth
	 */
	@Override
	public List<Object[]> findPayOutReportOfDepartment(Long departmentId, String processMonth) {
		return reportPayOutRepository.findPayOutReportOfDepartment(departmentId, processMonth);

	}

	/**
	 * to get List of Objects from database based on companyId And processMonth
	 */
	@Override
	public List<Object[]> findPayOutReportOfCompany(Long companyId, String processMonth) {
		return reportPayOutRepository.findPayOutReportOfCompany(companyId, processMonth);
	}

	/**
	 * to get List of ReportPayOut objects from database based on departmentId And
	 * processMonth
	 */
	@Override
	public List<ReportPayOut> findEmployeesPayOutReport(Long departmentId, String processMonth) {
		return reportPayOutRepository.findEmployeesPayOutReport(departmentId, processMonth);
	}

	/**
	 * to get List of ReportPayOut objects from database based on processMonth And
	 * companyId
	 */
	@Override
	public ReportPayOut findEmployeePayOutPdf(String employeeCode, String processMonth, Long companyId) {
		return reportPayOutRepository.findEmployeePayOutPdf(employeeCode, processMonth, companyId);
	}

	/**
	 * to get List of ReportPayOut objects from database based on processMonth And
	 * companyId
	 */
	@Override
	public List<Object[]> findEsiEpfReport(String processMonth, Long companyId) {
		return reportPayOutRepository.findEsiEpfReport(processMonth, companyId);
	}

	/**
	 * to get List of ReportPayOut objects from database based on esicNo
	 */
	@Override
	public List<ReportPayOut> findEsiReport(String esicNo) {
		return reportPayOutRepository.findEsiReport(esicNo);
	}

	@Override
	public List<ReportPayOut> findPfReport(String uanno) {
		return reportPayOutRepository.findPfReport(uanno);
	}

	/**
	 * to get List of ReportPayOut objects from database based on processMonth And
	 * companyId
	 */
	@Override
	public List<ReportPayOut> findAllEmployeesPayOutReport(Long companyId, String processMonth) {
		return reportPayOutRepository.findAllEmployeesPayOutReport(companyId, processMonth);
	}

	/**
	 * to get List of ReportPayOut objects from database based on processMonth And
	 * companyId
	 */
	@Override
	public List<ReportPayOut> findAllEmployeesPaysheet(Long companyId, String processMonth) {
		return reportPayOutRepository.findAllEmployeesPaysheet(companyId, processMonth);
	}

	/**
	 * to get List of ReportPayOut objects from database based on companyId And
	 * processMonth
	 */
	@Override
	public List<Object[]> findEmployeesESIPayOutReport(Long companyId, String processMonth) {
		return reportPayOutRepository.findEmployeesESIPayOutReport(companyId, processMonth);
	}

	/**
	 * Save OR update List of ReportPayOut objects into database
	 */
	@Override
	public void save(List<ReportPayOut> payout) {
		reportPayOutRepository.save(payout);
	}

	@Override
	public List<Object[]> findAllEmployeesPayOutReportForSalaryProcess(Long companyId, String processMonth) {
		return reportPayOutRepository.findAllEmployeesPayOutReportForSalaryProcess(companyId, processMonth);
	}

	/**
	 * to get List of ReportPayOutSalary objects from database based on departmentId
	 * And processMonth
	 */
	@Override
	public List<Object[]> findEmployeesPayOutReportForSalaryProcess(Long departmentId, String processMonth) {
		return reportPayOutRepository.findEmployeesPayOutReportForSalaryProcess(departmentId, processMonth);

	}

	/**
	 * to check is payroll process or not for employee
	 */
	@Override
	public Long checkEmployeePayroll(Long empId) {
		return reportPayOutRepository.checkEmployeePayrollProcess(empId);
	}

	/**
	 * to check is payroll process or not for SalarySlip based on employeeCode and
	 * processMonth
	 */
	@Override
	public Long checkEmployeePayrollForSalarySlip(String employeeCode, String processMonth, Long companyId) {
		return reportPayOutRepository.checkEmployeePayrollForSalarySlip(employeeCode, processMonth, companyId);
	}

	@Override
	public Long checkPayrollOfEmployee(Long employeeId, String processMonth) {
		return reportPayOutRepository.checkPayrollOfEmployee(employeeId, processMonth);
	}

	@Override
	public ReportPayOut findReportPayout(Long employeeId, String processMonth, Long companyId) {

		return reportPayOutRepository.findReportPayout(employeeId, processMonth, companyId);
	}

	@Override
	public void saveReportPayout(ReportPayOut reportPayout) {
		reportPayOutRepository.save(reportPayout);

	}

	@Override
	public List<ReportPayOut> findEmployeesPaysheetBydept(Long companyId, Long departmentId, String processMonth) {

		return reportPayOutRepository.findEmployeesPaysheetBydept(companyId, departmentId, processMonth);
	}

	@Override
	public List<Object[]> findReportPayoutForRecordPayment(String processMonth, Long companyId) {
		// TODO Auto-generated method stub
		return reportPayOutRepository.findAllReportPayoutListForRecordPayment(companyId, processMonth);
	}

	@Override
	public int updateReportPayout(Long employeeId, String transctionNo, String mode,String processMonth, Date processDate,
			Long companyId ) {
	System.out.println("...."+employeeId+"...."+transctionNo+"..."+processMonth+"..."+companyId);
			
// 		int count=reportPayOutRepository.updatePayrollLockFlagInPayRegister(employeeId, processMonth);
//	System.out.println("Row Count=========== "+count);
		return reportPayOutRepository.updateReportPayoutForRecordPayment(employeeId,transctionNo,processMonth,1l,processDate,mode, new Date());
	}

	@Override
	public List<ReportPayOut> getSalary(Long employeeId) {
		return reportPayOutRepository.getSalary(employeeId);
	}

	@Override
	public List<ReportPayOut> createSalary(Long employeeId) {
		return reportPayOutRepository.createSalary(employeeId);
	}

	@Override
	public List<String> getReportPayOutProcessMonthList(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		  return reportPayOutRepository.getReportPayOutProcessMonthList(companyId, financialYearId);
	}

	@Override
	public List<FinancialYear> findAllFinancialYear(Long companyId) {
		return financialYearRepository.findAllFinancialYear(companyId);
	}

	@Override
	public List<String> getReportPayOutMonth(Long employeeId) {
		
		return reportPayOutRepository.getReportPayOutMonth(employeeId);
	}

	@Override
	public Map<String, Long> recordPaymentCount(String companyId, String payrollMonth) {
		Map<String, Long> countMap = new HashMap<String, Long>();
		Long recordPaymentUpdatedCount = reportPayOutRepository.recordPaymentUpdatedCount(companyId, payrollMonth);
		Long recordPaymentPendingCount = reportPayOutRepository.recordPaymentPendingCount(companyId, payrollMonth);
		countMap.put("updated", recordPaymentUpdatedCount);
		countMap.put("pending", recordPaymentPendingCount);
		return countMap;
	}

	@Override
	public List<Object[]> payOutInfo(Long employeeId) {
		// TODO Auto-generated method stub
		return reportPayOutRepository.payOutInfo(employeeId);
	}

	@Override
	public List<Object[]> lastPaid(Long employeeId) {
		// TODO Auto-generated method stub
		return reportPayOutRepository.lastPaid(employeeId);
	}

	@Override
	public List<Object[]> findPfWorkingConsultantReport(String processMonth, Long longcompanyId) {
		// TODO Auto-generated method stub
		return reportPayOutRepository.findPfWorkingConsultantReport(processMonth,longcompanyId);
	}

	@Override
	public List<Object[]> getEsicConsutantEmployeeCount(String processMonth, Long longcompanyId) {
		// TODO Auto-generated method stub
		  return reportPayOutRepository.getEsicConsutantEmployeeCount(processMonth,longcompanyId);
	}

	@Override
	public List<String> getProcessMonthListByfinancialYear(Long financialYearId) {
	 
		  return reportPayOutRepository.getProcessMonthListByfinancialYear(financialYearId);
	}

	@Override
	public List<Object[]> findPfWorkingConsultantReportMonthly(Long financialYearId, Long companyId) {
		
		return reportPayOutRepository.findPfWorkingConsultantReportMonthly(financialYearId, companyId);
	}
	
	
	@Override
    public List<Object[]> getEmployeePFInfo(Long companyId, Long employeeId, String activeStatus) {
		
		return reportPayOutRepository.findEmployeePFInfo(companyId, employeeId, activeStatus);
	}

	
	
	@Override
	public List<Object[]> findEsicContributionStatementAnnualSummary(Long companyId, Long financialYearId, Long employeeId) {
		// TODO Auto-generated method stub
		if (employeeId != 0L) {
			return reportPayOutRepository.findEsicStatementEmployeeWise(companyId, employeeId);
		} 
		else if (financialYearId != 0L) {
			return reportPayOutRepository.findEsicStatementAnnualSummery(companyId,
					financialYearId);
		}
		return null;
	}

	@Override
	public List<Object[]> findEsicContributionStatementAnnualMonthly(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		
		if (financialYearId != 0L) {
			return reportPayOutRepository.findEsicStatementAnnualMonthly(companyId, financialYearId);
		}
		return null;
	}

	@Override
	public List<Object[]> findEmployeeEsic(Long employeeId, Long companyId) {
		// TODO Auto-generated method stub
		return reportPayOutRepository.findEmployeeEsic(companyId, employeeId);
	}
	
	@Override
	public List<String> getPayHeadList(Long companyId, String status) {
		return   reportPayOutRepository.getPayHeadList(companyId, status);
	}
}
