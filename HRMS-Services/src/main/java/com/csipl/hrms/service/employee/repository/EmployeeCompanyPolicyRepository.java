package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.EmployeeCompanyPolicy;

@Repository("employeeCompanyPolicyRepository")
@Transactional
public interface EmployeeCompanyPolicyRepository extends CrudRepository<EmployeeCompanyPolicy, Long> {
	public static final String DELETE_COMPANY_POLICY_BY_ID = "DELETE FROM EmployeeCompanyPolicy WHERE companyPolicyId=?1";
	@Query(" from EmployeeCompanyPolicy WHERE companyPolicyId=?1 ORDER BY employeeCompanyPolicyId DESC")
	List<EmployeeCompanyPolicy> findAllPolicies(Long companyPolicyId);
	
	@Modifying
	@Query(value = DELETE_COMPANY_POLICY_BY_ID, nativeQuery = true)
	public void deleteCompanyPolicyById(Long companyPolicyId);
}
