
package com.csipl.hrms.service.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.LoanIssue;


public interface LoanReportRepository extends CrudRepository<LoanIssue, Long> {

//	String LoanReport = "SELECT e.employeeCode, concat(e.firstName,' ', e.lastName) as employeeName, des.designationName, dept.departmentName, \r\n"
//			+ "  li.transactionNo as loanAccountNo, li.loanAmount, li.issueDate, li.activeStatus,li.emiStartDate, li.noOfEmi, \r\n"
//			+ "  (li.noOfEmi-Count(le.emiNo)) as remainingEmi,CEIL((li.loanAmount-sum(le.emiAmount))/li.emiAmount) as remainingEmi, \r\n"
//			+ "  (li.noOfEmi-Count(le.emiNo))*li.emiAmount as outStandingAmount , Count(le.emiNo)*li.emiAmount as loanRecoverAmount  \r\n"
//			+ "  FROM LoanIssue li   LEFT JOIN  Employee e  on li.employeeId=e.employeeId  \r\n"
//			+ " LEFT JOIN LoanEMI le on le.transactionNo=li.transactionNo  \r\n"
//			+ "  LEFT JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Department dept ON dept.departmentId=e.departmentId \r\n"
//			+ "  where  e.companyId =?1 and li.activeStatus IN ?2 Group BY li.transactionNo";
	
	
	String LoanReport = "SELECT e.employeeCode, concat(e.firstName,' ', e.lastName) as employeeName, des.designationName, dept.departmentName, \r\n"
			+ "		 li.transactionNo as loanAccountNo, li.loanAmount, li.issueDate, li.activeStatus,li.emiStartDate, li.noOfEmi, \r\n"
			+ "		 CEIL((li.loanAmount-sum(CASE WHEN (le.emiAmount IS null )THEN 0 ELSE le.emiAmount END)) /li.emiAmount ) as remainingEmi, \r\n"
			+ "      (li.loanAmount-sum(CASE WHEN (le.emiAmount IS null )THEN 0 ELSE le.emiAmount END)) as OutStandingAmount, \r\n" + 
			"        SUM(CASE WHEN (le.emiAmount IS null )THEN 0 ELSE le.emiAmount END) as loanRecoverAmount                    \r\n"
			+ "	     FROM LoanIssue li   LEFT JOIN  Employee e  on li.employeeId=e.employeeId  \r\n"
			+ "		 LEFT JOIN LoanEMI le on le.transactionNo=li.transactionNo AND le.emiStatus='AC'  \r\n"
			+ "	     LEFT JOIN Designation des ON des.designationId=e.designationId \r\n"
			+ "      LEFT JOIN Department dept ON dept.departmentId=e.departmentId \r\n"
			+ "	     where  e.companyId =?1 AND li.activeStatus IN ?2 Group BY  li.transactionNo";

	@Query(value = LoanReport, nativeQuery = true)
	public List<Object[]> findLoanConsolidatedStatement(Long longCompanyId, List<String> loanStatus);

	String loanDetailed = "SELECT e.employeeCode, concat(e.firstName,' ', e.lastName) as employeeName, des.designationName, dept.departmentName, \r\n"
			+ "  li.transactionNo as loanAccountNo, li.loanAmount, li.issueDate, li.activeStatus, \r\n"
			+ "  li.emiStartDate, li.noOfEmi , li.emiAmount\r\n"
			+ "  FROM LoanIssue li   LEFT JOIN  Employee e  on li.employeeId=e.employeeId  \r\n"
			+ " LEFT JOIN LoanEMI le on le.transactionNo=li.transactionNo  \r\n"
			+ "  LEFT JOIN Designation des ON des.designationId=e.designationId LEFT JOIN Department dept ON dept.departmentId=e.departmentId \r\n"
			+ "  where   e.companyId =?1 and li.transactionNo=?2 Group BY li.transactionNo ";

	@Query(value = loanDetailed, nativeQuery = true)
	public List<Object[]> findLoanDetailedMonthlyStatement(Long longCompanyId, Long loanAccountNo);

	String LoanAccount = " SELECT  li.emiStartDate, li.emiAmount, le.emiDate,le.remarks FROM LoanIssue li  \r\n"
			+ " LEFT JOIN LoanEMI le on le.transactionNo=li.transactionNo where   li.companyId =?1 and le.emiStatus ='AC' and li.transactionNo=?2";
//li.activeStatus ='CE'
	@Query(value = LoanAccount, nativeQuery = true)
	public List<Object[]> getActiveEmiDetails(Long longCompanyId, Long loanAccountNo);
	
	
	String LoanAccountNo = " SELECT  li.emiStartDate, li.emiAmount, le.emiDate,le.remarks FROM LoanIssue li  \r\n"
			+ " LEFT JOIN LoanEMI le on le.transactionNo=li.transactionNo where   li.companyId =?1 and le.emiStatus ='CE' and li.transactionNo=?2";

	@Query(value = LoanAccountNo, nativeQuery = true)
	public List<Object[]> getSettledEmiDetails(Long longCompanyId, Long loanAccountNo);

	
	@Query("from LoanIssue loanIssue where loanIssue.companyId=?1 and  loanIssue.activeStatus in ?2 ")
	public List<LoanIssue> findAllLoanIssueConsolidated(Long longCompanyId, List<String> loanStatus);
	
	@Query("from LoanIssue loanIssue where loanIssue.companyId=?1 ")
	public List<LoanIssue> findAllLoanIssues(Long longCompanyId);

	@Query(" from LoanIssue loanIssue where   loanIssue.companyId=?1 AND loanIssue.transactionNo=?2 AND loanIssue.activeStatus='AC' ") 
	public LoanIssue getActiveLoanEmiDetails(Long company, Long loanAccountNo);

	@Query(" from LoanIssue loanIssue where   loanIssue.companyId=?1 AND loanIssue.transactionNo=?2 AND loanIssue.activeStatus='CE' ") 
	public LoanIssue getSettledLoanEmiDetails(Long company, Long loanAccountNo);

}
