package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.EmployeeExpenseClaim;

@Repository
public interface EmployeeExpenseClaimRepository extends CrudRepository<EmployeeExpenseClaim, Long> {
	
	public static final String UPDATE_BY_STATUS = "UPDATE EmployeeExpenseClaim SET status = :status , batchId=:batchId  WHERE employeeExpeneseClaimId =:empExpeneseClaimId";
	
	@Query("from EmployeeExpenseClaim where companyId=?1 and status is NULL  ORDER BY employeeExpeneseClaimId DESC")
	List<EmployeeExpenseClaim> findExpenseList(Long companyId);

	// np
	String EXPENSE_CLAIM_SUMMARY = " SELECT eec.expenceTitle, et.expenseTypeName, eec.claimDate, eec.amountExpenses, eec.marchantName, eec.filePath, concat(e.firstName, ' ', e.lastName), eec.remark, employeeExpeneseClaimId FROM EmployeeExpenseClaims eec JOIN ExpenseType et ON et.expenseTypeId=eec.expenseTypeId JOIN Employee e ON e.employeeId=eec.employeId WHERE eec.employeId=?1 ";

	@Query(value = EXPENSE_CLAIM_SUMMARY, nativeQuery = true)
	List<Object[]> findExpenseClaimSummary(Long employeeId);
	
	@Modifying
	@Query(value = UPDATE_BY_STATUS)
	void updateByStatus(@Param("empExpeneseClaimId") Long empExpeneseClaimId, @Param("status") String status, @Param("batchId") Long batchId);

	
	@Query(value = "SELECT MAX(ec.batchId) FROM EmployeeExpenseClaims ec" , nativeQuery = true) 
 	public Long checkMaxBatchId();
	
}