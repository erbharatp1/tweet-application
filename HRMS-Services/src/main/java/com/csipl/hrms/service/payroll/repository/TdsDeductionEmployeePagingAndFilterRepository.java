package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;

public interface TdsDeductionEmployeePagingAndFilterRepository {
	public List<Object[]> findAllEmployees(EmployeeSearchDTO employeeSearchDto);
	public List<Object[]> findAllEmployeesWithTdsStatus(EmployeeSearchDTO employeeSearchDto, Long financialYearId);
	
}
