package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.csipl.hrms.model.employee.EmployeeNominee;

public interface EmployeeNomineeRepository extends CrudRepository<EmployeeNominee,Long>{
	@Query(" from EmployeeNominee where employeeId=?1  ORDER BY  employeeNomineeid	  DESC")
	 public List<EmployeeNominee> findAllEmployeeNominee(Long employeeId);
}
