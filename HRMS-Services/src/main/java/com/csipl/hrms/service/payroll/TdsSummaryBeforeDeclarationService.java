package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsStandardExemption;
import com.csipl.hrms.model.payroll.TdsSummaryChange;
import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TransactionApprovedHd;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;

public interface TdsSummaryBeforeDeclarationService {
	public TdsSummaryChangeDTO getTdsSummaryBeforeDeclation(List<TdsGroupSetup> tdsGroupList, Employee employee, FinancialYear financialYear, Long companyId, Long userId,boolean payrollflag);

	public TransactionApprovedHd createTdsApprovals( List<TdsGroupSetup> tdsGroupList,List<TdsTransaction> tdsTrasactionist,Employee employee,Long companyId,FinancialYear financialYear,String status );
	public TransactionApprovedHd getTdsApproved(Long employeeId, Long companyId);
	public TdsSummaryDTO getTdsSummary(Employee employee, Long companyId, List<PayOut> payOutList, TdsStandardExemption tdsStandardExemption);
	
	public TransactionApprovedHd getTransactionApprovedHd(Long employeeId,String FinancialYear);
	public TdsSummaryChange getTdsSummary(Long employeeId, Long financialYearId);
	public TdsSummaryChangeDTO getTdsSummaryBeforeDeclationNewScheme(List<TdsGroupSetup> tdsGroupList,Employee employee, FinancialYear financialYear, Long companyId, Long userId, boolean b);
}
