package com.csipl.hrms.service.payroll.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.Employee;

@Repository
public interface FinalSettlementRepository extends CrudRepository<Employee, Long>{

	String empList="select e.firstName ,e.lastName,e.employeeCode,dept.departmentName,s.exitDate,e.employeeId from Employee e JOIN Separation s  ON e.employeeId=s.employeeId JOIN Department dept ON e.departmentId=dept.departmentId  Where e.companyId=?1 and s.status=?2 and e.activeStatus='AC' GROUP BY e.employeeId";
	@Query(value=empList,nativeQuery=true)
	public List<Object[]> getEmployees(Long companyId,String ApprovalStatus);
	
	String totalAmount="SELECT sum(ps.amount) as totalAmount FROM PayStructure ps JOIN PayStructureHd phd on  phd.payStructureHdId=ps.payStructureHdId JOIN PayHeads ph On ph.payHeadId=ps.payHeadId\r\n" + 
			"WHERE  ph.isApplicableOnGratuaty='Y' and phd.employeeId=?1";
	@Query(value=totalAmount,nativeQuery=true)
	public BigDecimal getGratuityDeduction(Long employeeId);
	

}
