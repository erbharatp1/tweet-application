package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.EmployeeNominee;



public interface EmployeeNomineeService {
	public List<EmployeeNominee> save(List<EmployeeNominee> employeeNominee);
	public List<EmployeeNominee> findAllEmployeeNominee(Long employeeId);
}
