package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payroll.TdsGroup;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsStandardExemption;
import com.csipl.hrms.model.payroll.TdsSummaryChange;
import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TransactionApprovedHd;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;

public interface TdsApprovalService {

	public void saveTdsApprovalsList(List<TdsGroupSetup> tdsGroupList,TransactionApprovedHd transactionApprovedHd, Employee employee, FinancialYear financialYear, Long companyId, Long userId, TdsStandardExemption tdsStandardExemption, List<OtherIncome> otherIncomeList,boolean payrollflag,List<TdsTransaction> tdsTrasactionist, String status, List<Object[]> reportPayOutSum);
	public TransactionApprovedHd createTdsApprovals( List<TdsGroupSetup> tdsGroupList,List<TdsTransaction> tdsTrasactionist,Employee employee,Long companyId,FinancialYear financialYear,String status );
	public TransactionApprovedHd getTdsApproved(Long employeeId, Long companyId);
	public TdsSummaryDTO getTdsSummary(Employee employee, Long companyId, List<PayOut> payOutList, TdsStandardExemption tdsStandardExemption);
	
	public TransactionApprovedHd getTransactionApprovedHd(Long employeeId,String FinancialYear);
	public TdsSummaryChange getTdsSummary(Long employeeId, Long financialYearId);
	public void saveTdsApprovalsNewScheme(List<TdsGroupSetup> tdsGroupList,TransactionApprovedHd transactionApprovedHd, Employee employee, FinancialYear financialYear, Long companyId, Long userId, TdsStandardExemption tdsStandardExemption, List<OtherIncome> otherIncomeList,boolean payrollflag,List<TdsTransaction> tdsTrasactionist, String status, List<Object[]> reportPayOutSum);

//	public void saveTdsApprovalsMonthalyList(List<TdsGroupSetup> tdsGroupList,TransactionApprovedHd transactionApprovedHd, Employee employee, FinancialYear financialYear, Long companyId, Long userId, TdsStandardExemption tdsStandardExemption, List<OtherIncome> otherIncomeList,boolean payrollflag,List<TdsTransaction> tdsTrasactionist, String status, String processMonth, List<Object[]> grossSum);

	
}
