
package com.csipl.hrms.service.report;

import java.util.List;

import com.csipl.hrms.model.payroll.LoanIssue;

public interface LoanReportService {
	List<Object[]> findLoanConsolidatedStatement(Long companyId, List<String> loanStatus);

	List<Object[]> findLoanDetailedMonthlyStatement(Long companyId, Long loanAccountNo);

	List<Object[]> getEmiDetails(Long companyId, Long loanAccountNo, String activeStatus);

	List<LoanIssue> findAllLoanIssue(Long companyId, List<String> loanStatus);
	
	List<LoanIssue> getAllLoanIssues(Long companyId);

	LoanIssue getLoanEmiDetailsList(Long companyId, Long loanAccountNo, String activeStatus);
}
