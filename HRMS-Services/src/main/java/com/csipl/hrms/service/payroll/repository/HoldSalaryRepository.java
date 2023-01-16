package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payroll.HoldSalary;



public interface HoldSalaryRepository extends CrudRepository<HoldSalary,Long>{ 
	
	@Query(nativeQuery=true, value="SELECT h.holdSalaryId,h.remark,e.employeeCode,e.employeeId,e.firstName,e.lastName,p.grossPay,d.departmentName,g.gradesName,h.payrollMonth,h.status,h.userId,h.userIdUpdate,h.dateCreated,h.dateUpdate,h.companyId FROM HoldSalary h join Employee e on h.employeeId=e.employeeId JOIN PayStructureHd p on h.employeeId=p.employeeId join Department d on e.departmentId=d.departmentId join Grades g on g.gradesId=e.gradesId where h.status='HO' and h.companyId=?1 and h.payrollMonth=?2 GROUP BY e.employeeId")
    public List<Object[]> findAllHoldSalary(Long companyId,String payrollMonth);
	
 	@Query(" from HoldSalary where companyId =?1 ORDER BY  holdSalaryId  DESC ")
    public List<HoldSalary> findAllHoldSalary(Long companyId);
	
	@Query(" from HoldSalary where holdSalaryId=?1 ORDER BY  holdSalaryId  DESC ")
    public HoldSalary findHoldSalaryById(Long holdSalaryId);
	
	@Query("from HoldSalary where employeeId=?1 and status='HO'")
	public List<HoldSalary> searchEmployeeHoldDetails(Long employeeId);
	
	@Query("SELECT count(employeeId) FROM HoldSalary where employeeId=?1")
	public int holdSalaryCount(Long employeeId);

	@Query(value="UPDATE HoldSalary SET status = 'BO' WHERE employeeId =:employeeId", nativeQuery = true)
	public int holdSalaryUpdate(@Param("employeeId") Long employeeId);
 }
