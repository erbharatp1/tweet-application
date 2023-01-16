package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.employee.EmployeeStatuary;

public interface EmployeeStatuaryService {
 	  public List<EmployeeStatuary> save(List<EmployeeStatuary> employeeStatuaryList,Long empId)throws ErrorHandling;
	  public List<EmployeeStatuary> findAllEmployeeStatuary(Long empId);
	  public List<EmployeeStatuary> findAllEmployeeStatuaryWithoutUAN(Long empId);
	  public List<EmployeeStatuary> findEmployeeStatuaryNominee(Long empId);
	 // public EmployeeStatuary EmployeeStatuary(Long empId);
	  public void delete(Long statuaryId); 
 }
