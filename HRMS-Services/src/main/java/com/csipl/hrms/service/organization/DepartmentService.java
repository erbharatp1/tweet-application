 package com.csipl.hrms.service.organization;

import java.util.List;
import com.csipl.hrms.model.organisation.Department;

    public interface DepartmentService {
  
   	public Department save(Department department);
  	public List<Department> findAllDepartment(Long companyId);
	public Department findDepartment(Long departmentId);
	public List< Department >findDepartmentByProcessMonth(String processMonth,Long companyId);
	public List<Department> findAllActiveDepartment(Long companyId);
	public Department findDeptNameById(Long companyId ,Long departmentId);
	
 }
