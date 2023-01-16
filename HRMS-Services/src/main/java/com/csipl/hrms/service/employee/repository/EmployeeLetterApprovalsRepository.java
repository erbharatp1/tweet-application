package com.csipl.hrms.service.employee.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface EmployeeLetterApprovalsRepository {

	List<Object[]> findPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto);

	List<Object[]> findNonPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto);

}
