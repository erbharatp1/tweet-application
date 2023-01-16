package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.CompanyPolicy;

@Repository("companyPolicyRepository")
@Transactional
public interface CompanyPolicyRepository extends CrudRepository<CompanyPolicy, Long> {
	public static final String DELETE_COMPANY_POLICY_BY_ID = "DELETE FROM CompanyPolicy WHERE policyId =?1";
	@Query(" from CompanyPolicy where companyId=?1 ORDER BY  policyId  DESC")
	public List<CompanyPolicy> findAllPolicies(Long companyId);

	@Query(" from CompanyPolicy where policyId=?1 ORDER BY  policyId  DESC")
	public CompanyPolicy getCompanyPolicyById(Long policyId);

	@Query(" SELECT count(*) from Employee  where companyId=?1 AND activeStatus = 'AC'")
	public int employeeSearch(Long companyId);

    @Query(nativeQuery = true,value = " SELECT cp.policyId ,cp.policyName ,cp.fileLocation FROM EmployeeCompanyPolicy ecp JOIN CompanyPolicy cp on cp.policyId=ecp.companyPolicyId WHERE ecp.status='AC' AND  ecp.employeeId=?1 ORDER by cp.policyId DESC")
	public List<Object[]> getAllCompanyPolicyByEmployee(Long employeeId);

	@Modifying
	@Query(value = DELETE_COMPANY_POLICY_BY_ID, nativeQuery = true)
	public void deleteCompanyPolicyById(Long companyPolicyId);
}
