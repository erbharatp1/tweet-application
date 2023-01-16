package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.EmployeeCompanyPolicy;

public interface EmployeeCompanyPolicyService {
	
	public List<EmployeeCompanyPolicy> save(List<EmployeeCompanyPolicy> employeeCompanyPolicy);

	public List<EmployeeCompanyPolicy> findAllEmployeePolicies(Long companyPolicyId);
	
}
