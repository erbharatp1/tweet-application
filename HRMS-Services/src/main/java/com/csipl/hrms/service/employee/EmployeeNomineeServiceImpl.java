package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.employee.EmployeeNominee;
import com.csipl.hrms.service.employee.repository.EmployeeNomineeRepository;
@Service("employeeNominee")
public class EmployeeNomineeServiceImpl implements EmployeeNomineeService {
	@Autowired
	EmployeeNomineeRepository employeeNomineeRepository;

	@Override
	public List<EmployeeNominee> save(List<EmployeeNominee> employeeNominee) {
		
		return (List<EmployeeNominee>) employeeNomineeRepository.save(employeeNominee);
	}

	@Override
	public List<EmployeeNominee> findAllEmployeeNominee(Long employeeId) {
		
		return employeeNomineeRepository.findAllEmployeeNominee(employeeId);
	}
	
}
