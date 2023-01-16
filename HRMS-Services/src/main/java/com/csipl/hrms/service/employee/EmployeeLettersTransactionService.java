package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.EmployeeLettersTransaction;

public interface EmployeeLettersTransactionService {

	EmployeeLettersTransaction saveEmpLetterTransaction(EmployeeLettersTransaction employeeLettersTransaction);

	List<EmployeeLettersTransaction> findAllEmpLetterTransactionList();

	List<EmployeeLettersTransaction> findAllApprovalStatus(Long companyId,Long letterId,Long employeeId);
}
