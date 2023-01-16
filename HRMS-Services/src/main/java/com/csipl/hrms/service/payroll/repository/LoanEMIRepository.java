package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.LoanEMI;

public interface LoanEMIRepository extends CrudRepository<LoanEMI, Long>{

	String deleteFromLoanEMI="DELETE FROM LoanEMI WHERE transactionNo = ?1  and processMonth=?2  and transactionFlag= 'LE'";
	@Modifying
	@Query(value=deleteFromLoanEMI ,nativeQuery = true)
	void deleteFromLoanEMI(Long transId, String processMonth);
	@Query(value="SELECT * FROM LoanEMI le WHERE le.transactionNo=?1" ,nativeQuery = true)
	List<LoanEMI> findLoanIssueList(Long transactionNo);
}