package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.EmployeeCompanyPolicy;
import com.csipl.hrms.service.employee.repository.EmployeeCompanyPolicyRepository;

@Service("employeeCompanyPolicyService")
@Transactional
public class EmployeeCompanyPolicyServiceImpl implements EmployeeCompanyPolicyService {

	@Autowired
	private EmployeeCompanyPolicyRepository employeeCompanyPolicyRepository;

	@Override
	public List<EmployeeCompanyPolicy> save(List<EmployeeCompanyPolicy> employeecompanyPolicy) {

		List<EmployeeCompanyPolicy> subMenu = (List<EmployeeCompanyPolicy>) employeeCompanyPolicyRepository
				.save(employeecompanyPolicy);

		return subMenu;
	}

	@Override
	public List<EmployeeCompanyPolicy> findAllEmployeePolicies(Long companyPolicyId) {

		return employeeCompanyPolicyRepository.findAllPolicies(companyPolicyId);
	}

}
