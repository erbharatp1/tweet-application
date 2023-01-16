package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.List;

import com.csipl.hrms.model.payroll.LoanEMI;
import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.tms.dto.common.SearchDTO;
 
public interface LoanIssueService {
 	public List<Object[]> getAllLoanIssue(Long companyId );
 	 public LoanIssue save(LoanIssue loanIssue);
 	 public void  save( List<LoanIssue>  loanIssues);
 	 public List<LoanIssue> getLoanIssueDetails( long employeeId , String month );
	 public List<Object[]> getLoanIssue( long transactionNo );
	 public List<LoanIssue> getLoanIssueDetailsList( long employeeId);
	 public List<LoanIssue> getLoanTypeIssueDetailsList( String loanType);
	 public LoanIssue getLoanEmiDetailsList( long transactionNo);
	 public BigDecimal getTotalEmiAmount( long transactionNo);
	 public void setPendingAmount(BigDecimal totalEmiAmounts ,long txNo);
	 public List<LoanIssue> getMyLoanInfo(long employeeId);
	 public List<LoanIssue> findAllLoanIssue(Long companyId);
	/**
	 * findAllLoanIssueEmployee}
	 */
	public List<LoanIssue> findAllLoanIssueEmployee(Long companyId, Long employeeId);
	 
	 public LoanEMI saveLoanEmi(LoanEMI loanEMI);
	
		public List<Object[]> getLoanIssueSearch(Long companyId, SearchDTO searcDto);
		public List<Object[]> getLoanIssueSearchCount(Long companyId, SearchDTO searcDto);
 }
