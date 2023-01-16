package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.model.employee.EmployeeExpenseClaim;
import com.csipl.tms.dto.common.SearchDTO;

public interface EmployeeExpenseClaimsService {

	EmployeeExpenseClaim save(EmployeeExpenseClaim employeeExpenseClaim, MultipartFile file, boolean b);

	List<EmployeeExpenseClaim> findExpenseList(Long companyId);

	EmployeeExpenseClaim findById(Long employeeExpeneseClaimId);

	//np
	List<Object[]> getExpensesClaimPendingApprovals(Long companyId, SearchDTO searcDto);

	List<Object[]> getExpensesClaimNonPendingApprovals(Long companyId, SearchDTO searcDto);

	List<Object[]> getExpenseClaimSummary(Long employeeId);

	void updateByStatus(Long[] employeeExpeneseClaimIds);
	 
}
