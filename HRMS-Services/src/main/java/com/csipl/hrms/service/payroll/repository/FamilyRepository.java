package com.csipl.hrms.service.payroll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.ProfessionalInformation;
 public interface FamilyRepository extends CrudRepository<EmployeeFamily, Long> {
 	@Query(" from EmployeeFamily where employeeId=?1 ORDER BY  familyId  DESC")
	public List<EmployeeFamily> findAllEmployeeFamilyList(Long employeeId); 
 	
	 @Query(" from EmployeeFamily where employeeId=?1")
		public List<EmployeeFamily> findEmployeeFamily( Long empId);
	
	 
	
 }
