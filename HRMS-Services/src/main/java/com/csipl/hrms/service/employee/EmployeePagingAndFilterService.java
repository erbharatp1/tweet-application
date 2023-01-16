package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;

public interface EmployeePagingAndFilterService {
	public List<Object[]> getEmployeeByPagingAndFilter(long companyId, EmployeeSearchDTO employeeSearchDto);// ,
																											// SearchDTO
																											// searchDto,
																											// );

	public void getEmployeeCount(long companyId, EmployeeCountDTO employeeCountDto);

	public void getEmployeeCountWithTdsStatus(long companyId, EmployeeCountDTO employeeCountDto);

	public void getEmployeeCountDE(long companyId, EmployeeCountDTO employeeCountDto);

	public void getEmployeeSeparatingCount(long companyId, EmployeeCountDTO employeeCountDto);

	public List<Object[]> getAllEmployeeByPagingAndFilter(long companyId, EmployeeSearchDTO employeeSearchDto);

	public List<Object[]> findEmployeeListforPolicy(Long companyId, EmployeeSearchDTO employeeSearcDto);

}
