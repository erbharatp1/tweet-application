package com.csipl.hrms.service.employee.repository;

import java.util.List;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;

public interface EmployeePaginationRepository {

	List<Object[]> getAllEmployeeDetails(Long companyId, EmployeeSearchDTO employeeSearchDto);
	
	public List<Object[]> findEmployeeListforPolicy(Long companyId, EmployeeSearchDTO employeeSearchDTO);
}
