package com.csipl.hrms.service.organization.repository;

 
 import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.organisation.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long>{
 
 	@Query(" from Department where companyId=?1 ORDER BY  departmentId  DESC")
 	public List<Department> findAllDepartment(Long companyId );
 	
	@Query("FROM Department d where d.departmentId not in (SELECT p.id.departmentId FROM PayRollLock p where p.id.processMonth=?1 and p.isPayRollLocked='false')") 
 	public  List<Department> findDepartment(String processMonth);//,Long companyId
 	
	@Query(" from Department where departmentId=?1 ")
	public Department findDeptName(Long departmentId);

	@Query(" from Department where companyId=?1 and activeStatus='"+StatusMessage.ACTIVE_CODE+"' ORDER BY  departmentId  DESC")
	public List<Department> findAllActiveDepartment(Long companyId);
	
	@Query(nativeQuery = true,value = "SELECT * FROM Department dept JOIN Employee emp on dept.departmentId= emp.departmentId WHERE emp.companyId=?1 AND emp.employeeId=?2")
	public Department findDeptNameById(Long companyId ,Long employeeId);
}

