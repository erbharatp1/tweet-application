package com.csipl.hrms.service.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.dto.employee.CompanyPolicyDTO;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.EmployeeLetterDTO;
import com.csipl.hrms.model.employee.CompanyPolicy;
import com.csipl.hrms.service.employee.repository.CompanyPolicyRepository;
import com.csipl.hrms.service.employee.repository.EmployeeCompanyPolicyRepository;

@Service("companyPolicyService")
@Transactional
public class CompanyPolicyServiceImpl implements CompanyPolicyService {

	@Autowired
	private EmployeeCompanyPolicyRepository employeeCompanyPolicyRepository;
	@Autowired
	private CompanyPolicyRepository companyPolicyRepository;

	@Override
	public List<CompanyPolicy> findAllCompanyPolicies(Long companyId) {

		return companyPolicyRepository.findAllPolicies(companyId);
	}

	@Override
	public CompanyPolicy save(CompanyPolicy companyPolicy) {

		return companyPolicyRepository.save(companyPolicy);
	}

	@Override
	@Transactional
	public void delete(Long policyId) {
		employeeCompanyPolicyRepository.deleteCompanyPolicyById(policyId);
		companyPolicyRepository.deleteCompanyPolicyById(policyId);
	}

	@Override
	public CompanyPolicy findCompanyPolicyById(Long policyId) {

		return companyPolicyRepository.getCompanyPolicyById(policyId);
	}

//	@Override
//	public List<Object[]> findEmployeeListforPolicy(Long companyId, EmployeeSearchDTO employeeSearchDto) {
//		
//		return companyPolicyRepository.findEmployeeListforPolicy(companyId, employeeSearchDto);
//	}

	@Override
	public void getEmployeeCount(Long companyId, EmployeeCountDTO searchDto) {

		searchDto.setCount(companyPolicyRepository.employeeSearch(companyId));
	}

	public List<CompanyPolicyDTO> getAllCompanyPolicyByEmployee(Long employeeId) {
		List<Object[]> list = companyPolicyRepository.getAllCompanyPolicyByEmployee(employeeId);
		List<CompanyPolicyDTO> companyPolicyDTOs = new ArrayList<CompanyPolicyDTO>();
		for (Object[] objects : list) {

			CompanyPolicyDTO companyPolicyDTO = new CompanyPolicyDTO();
			Long policyId = objects[0] != null ? Long.parseLong(objects[0].toString()) : null;
			String policyName = objects[1] != null ? (String) objects[1] : null;
			String fileLocation = objects[2] != null ? (String) objects[2] : null;
			companyPolicyDTO.setPolicyId(policyId);
			companyPolicyDTO.setPolicyName(policyName);
			companyPolicyDTO.setFileLocation(fileLocation);
			companyPolicyDTOs.add(companyPolicyDTO);
		}
		return companyPolicyDTOs;
	}

}
