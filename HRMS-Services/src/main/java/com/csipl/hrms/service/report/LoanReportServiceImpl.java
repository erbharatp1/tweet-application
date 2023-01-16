
package com.csipl.hrms.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.LoanIssue;
import com.csipl.hrms.service.report.repository.LoanReportRepository;

@Service
public class LoanReportServiceImpl implements LoanReportService {

	@Autowired
	private LoanReportRepository loanReportRepository;

	@Override
	public List<Object[]> findLoanConsolidatedStatement(Long companyId, List<String> loanStatus) {

		return loanReportRepository.findLoanConsolidatedStatement(companyId, loanStatus);
	}


	@Override
	public List<Object[]> findLoanDetailedMonthlyStatement(Long companyId, Long loanAccountNo) {
		// TODO Auto-generated method stub
		return loanReportRepository.findLoanDetailedMonthlyStatement(companyId, loanAccountNo);
	}

	@Override
	public List<Object[]> getEmiDetails(Long companyId, Long loanAccountNo,  String activeStatus) {
		// TODO Auto-generated method stub
		if(activeStatus.equals("AC"))
		{
			//Running Emi Details
			return loanReportRepository.getActiveEmiDetails(companyId, loanAccountNo);
		}
		else if(activeStatus.equals("CE"))
		{
			return loanReportRepository.getSettledEmiDetails(companyId, loanAccountNo);
		}
		return null;
	}


	@Override
	public List<LoanIssue> findAllLoanIssue(Long companyId, List<String> loanStatus) {
		// TODO Auto-generated method stub
		return loanReportRepository.findAllLoanIssueConsolidated(companyId,loanStatus);
	}


	@Override
	public LoanIssue getLoanEmiDetailsList(Long companyId, Long loanAccountNo, String activeStatus) {
		// TODO Auto-generated method stub
		if(activeStatus.equals("AC"))
		{
			//Running Emi Details
			return loanReportRepository.getActiveLoanEmiDetails(companyId, loanAccountNo);
		}
		else if(activeStatus.equals("CE"))
		{
			return loanReportRepository.getSettledLoanEmiDetails(companyId, loanAccountNo);
		}
		return null;
	}


	@Override
	public List<LoanIssue> getAllLoanIssues(Long companyId) {
		
		return loanReportRepository.findAllLoanIssues(companyId);
	}

}
