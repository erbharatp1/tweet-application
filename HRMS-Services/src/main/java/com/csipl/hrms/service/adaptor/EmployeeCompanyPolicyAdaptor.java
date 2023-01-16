package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.EmployeeCompanyPolicyDTO;
import com.csipl.hrms.model.employee.CompanyPolicy;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeCompanyPolicy;
import com.csipl.hrms.model.organisation.Department;

@Component
public class EmployeeCompanyPolicyAdaptor implements Adaptor<EmployeeCompanyPolicyDTO, EmployeeCompanyPolicy> {

	@Override
	public List<EmployeeCompanyPolicy> uiDtoToDatabaseModelList(List<EmployeeCompanyPolicyDTO> roleSubmenuActionList) {
		List<EmployeeCompanyPolicy> roleSubmenuList = new ArrayList<EmployeeCompanyPolicy>();
		roleSubmenuActionList.forEach(subMenuDto -> {
			roleSubmenuList.add(uiDtoToDatabaseModel(subMenuDto));
		});

		return roleSubmenuList;
	}

	@Override
	public List<EmployeeCompanyPolicyDTO> databaseModelToUiDtoList(List<EmployeeCompanyPolicy> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public EmployeeCompanyPolicy uiDtoToDatabaseModel(EmployeeCompanyPolicyDTO uiobj) {

		EmployeeCompanyPolicy employeeCompanyPolicy = new EmployeeCompanyPolicy();

		employeeCompanyPolicy.setEmployeeCompanyPolicyId(uiobj.getEmployeeCompanyPolicyId());

		Employee emp = new Employee();
		emp.setEmployeeId(uiobj.getEmployeeId());
		employeeCompanyPolicy.setEmployee(emp);

		CompanyPolicy companyPolicy = new CompanyPolicy();
		companyPolicy.setPolicyId(uiobj.getCompanyPolicyId());
		employeeCompanyPolicy.setCompanyPolicy(companyPolicy);

		Department dept = new Department();
		dept.setDepartmentId(uiobj.getDepartmentId());
		employeeCompanyPolicy.setDepartment(dept);
		employeeCompanyPolicy.setDateCreated(uiobj.getDateCreated());
		employeeCompanyPolicy.setStatus(uiobj.getStatus());
		employeeCompanyPolicy.setDateUpdated(uiobj.getDateUpdated());
		employeeCompanyPolicy.setUserId(uiobj.getUserId());
		employeeCompanyPolicy.setUserIdUpdate(uiobj.getUserIdUpdate());

		return employeeCompanyPolicy;
	}

	@Override
	public EmployeeCompanyPolicyDTO databaseModelToUiDto(EmployeeCompanyPolicy dbobj) {

		EmployeeCompanyPolicyDTO empCompanyPolicyDTO = new EmployeeCompanyPolicyDTO();

		empCompanyPolicyDTO.setEmployeeCompanyPolicyId(dbobj.getEmployeeCompanyPolicyId());
		empCompanyPolicyDTO.setCompanyPolicyId(dbobj.getCompanyPolicy().getPolicyId());
		empCompanyPolicyDTO.setEmployeeId(dbobj.getEmployee().getEmployeeId());
		empCompanyPolicyDTO.setDepartmentId(dbobj.getDepartment().getDepartmentId());
		empCompanyPolicyDTO.setStatus(dbobj.getStatus());
		empCompanyPolicyDTO.setDateCreated(dbobj.getDateCreated());
		empCompanyPolicyDTO.setDateUpdated(dbobj.getDateUpdated());
		empCompanyPolicyDTO.setUserId(dbobj.getUserId());
		empCompanyPolicyDTO.setUserIdUpdate(dbobj.getUserIdUpdate());

		return empCompanyPolicyDTO;
	}

}
