package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface LoanIssuePaginationRepository {

	List<Object[]> getLoanIssueSearch(Long companyId, SearchDTO searcDto);

}
