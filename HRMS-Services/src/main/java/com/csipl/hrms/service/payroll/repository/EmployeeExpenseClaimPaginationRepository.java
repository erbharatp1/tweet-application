package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface EmployeeExpenseClaimPaginationRepository {

	List<Object[]> findExpensesClaimPendingApprovals(Long companyId, SearchDTO searcDto);
	
	List<Object[]> findExpensesClaimNonPendingApprovals(Long companyId, SearchDTO searcDto);

}
