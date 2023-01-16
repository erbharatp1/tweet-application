package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.dto.employee.CompanyPolicyDTO;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.model.employee.CompanyPolicy;

public interface CompanyPolicyService {
	
	public  List<CompanyPolicy> findAllCompanyPolicies(Long companyId);
	
	public CompanyPolicy save(CompanyPolicy companyPolicy);
	
	public void delete(Long questionId);

	public CompanyPolicy findCompanyPolicyById(Long policyId);

	//public List<Object[]> findEmployeeListforPolicy(Long companyId, EmployeeSearchDTO employeeSearchDto);

	public void getEmployeeCount(Long longCompanyId, EmployeeCountDTO employeeCountDto);

    public List<CompanyPolicyDTO> getAllCompanyPolicyByEmployee(Long employeeId);

}
