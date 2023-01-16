package com.csipl.hrms.service.payroll.repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.LoanIssue;


 
public interface LoanIssueRepository extends CrudRepository<LoanIssue, Long> {
	
	
	//String queryForMyLoan="SELECT * FROM LoanEMI le WHERE (le.transactionNo=(SELECT li.transactionNo from LoanIssue li LEFT JOIN Employee e on e.employeeId=li.employeeId where e.employeeCode=?1 ))";
	//String queryForMyLoan=" FROM LoanIssue loanIssue where loanIssue.employee.employeeId=?1";
	
	String findAllLoanIssue = " SELECT loan.employeeId, emp.firstName, emp.lastName , loan.loanAmount,dep.departmentName , des.designationName , emp.employeeCode,loan.loanPendingAmount,loan.transactionNo FROM LoanIssue loan JOIN Employee emp ON emp.employeeId = loan.employeeId and loan.companyId=?1 JOIN Department dep ON dep.departmentId = emp.departmentId JOIN Designation des ON des.designationId = emp.designationId ";
	
	@Query(value = findAllLoanIssue, nativeQuery = true)
	public List<Object[]> findAllLoanIssue(Long companyId);
	
	@Query(" from LoanIssue loanIssue where loanIssue.emiStartDate <= ?2 and loanIssue.employee.employeeId = ?1 and  activeStatus='AC' ") 
    public List<LoanIssue> getLoanIssueDetails( long employeeId, Date today );
	
	String findLoanIssue="select loanIssue.employeeId,emp.firstName,emp.lastName, loanIssue.loanAmount,dep.departmentName,des.designationName,emp.employeeCode,loanIssue.loanPendingAmount,"
			+ " loanIssue.transactionNo,g.gradesName,loanIssue.dateCreated,loanIssue.noOfEmi,loanIssue.emiAmount,emp.dateOfJoining from Employee emp JOIN LoanIssue loanIssue on emp.employeeId=loanIssue.employeeId JOIN Department dep ON emp.departmentId=dep.departmentId"
			+ " JOIN Designation des On emp.designationId=des.designationId JOIN Grades g ON emp.gradesId=g.gradesId where loanIssue.transactionNo=?1";
	@Query(value=findLoanIssue, nativeQuery = true) 
    public List<Object[]> getLoanIssue( long transactionNo);
	
	@Query(" from LoanIssue loanIssue where  loanIssue.employee.employeeId = ?1 and  activeStatus='AC' ") 
	public List<LoanIssue>  getLoanIssueDetailsList( long employeeId);
	
	@Query(" from LoanIssue loanIssue where  loanIssue.loanType = ?1 and  activeStatus='AC' ") 
	  public List<LoanIssue>  getLoanTypeIssueDetailsList( String loanType);
	
	@Query(" from LoanIssue loanIssue where  loanIssue.transactionNo=?1  ") 
    public LoanIssue  getLoanEmiDetailsList( long transactionNo);
	
	@Query("from LoanIssue loanIssue where loanIssue.employee.employeeId=?1") 
    public List<LoanIssue>  getMyLoanList( long employeeId);
	
	
	@Query("SELECT SUM(emiAmount) FROM LoanEMI where transactionNo=?1")
	public BigDecimal getTotalEmiAmount( long transactionNo );
	
	String updatePendingAmount = "UPDATE LoanIssue loanIssue SET loanIssue.loanPendingAmount =?1 HERE loanIssue.transactionNo =?2";
	@Query(value = updatePendingAmount ,  nativeQuery = true ) 
	public void setPendingAmount(BigDecimal loanPendingAmount , long transactionNo);
	

	@Query("from LoanIssue loanIssue where loanIssue.companyId=?1 and  activeStatus='AC'")
	public List<LoanIssue> findAllLoanSanction(Long companyId);

	/**
	 * findAllLoanSanctionEmp}
	 * FROM  LoanIssue loanIssue where loanIssue.companyId=1 AND  loanIssue.employeeId=1605
	 */
	@Query("from LoanIssue loanIssue where loanIssue.companyId=?1 AND  loanIssue.employee.employeeId=?2") 
	public List<LoanIssue> findAllLoanSanctionEmp(Long companyId, Long employeeId);
	

	//String getLoanIssueDetail = "FROM LoanIssue where employeeId =:employeeId and concat(monthName(issueDate),'-',Year(issueDate))=:processMonth";
	// MONTH(issueDate) =?1 and Year(issueDate) =?2 and   int month,int year, 
//	@Query(value = getLoanIssueDetail ,  nativeQuery = true ) 
	@Query("FROM LoanIssue where employeeId =?1 and concat(monthName(issueDate),'-',Year(issueDate))=?2 ") 
	public LoanIssue getLoanIssueDetailOnBasisOfProcessMonth(Long employeeId,String processMonth);

	
	/*@Query(value="FROM LoanIssue where employeeId =:employeeId and concat(monthName(issueDate),'-',Year(issueDate))=:processMonth", nativeQuery = true)
	public LoanIssue getLoanIssueDetailOnBasisOfProcessMonth(@Param("employeeId") Long employeeId ,@Param("processMonth") String processMonth);
	*/
	
	String updateloanIssue = "UPDATE LoanIssue loan  SET loan.activeStatus='CE' where loan.transactionNo=?1 and loan.employeeId=?2";
	@Query(value = updateloanIssue ,  nativeQuery = true ) 
	@Modifying
	public void upadteLoanIssueAfterRollback(long transactionNo, long employeeId);
	
	
}
