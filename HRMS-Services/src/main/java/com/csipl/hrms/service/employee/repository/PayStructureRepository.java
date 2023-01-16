package com.csipl.hrms.service.employee.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.PayStructure;
import com.csipl.hrms.model.employee.PayStructureHd;
@Transactional
public interface PayStructureRepository extends CrudRepository<PayStructureHd, Long> {

	@Query(" from PayStructureHd where  ( dateEnd is null or dateEnd>=?1) and (effectiveDate is NOT null and effectiveDate<=?1) and employeeId=?2")
	public PayStructureHd employeeCurrentPayStructure(Date currentDate, Long employeeId);

	@Query(" from PayStructureHd where  (dateEnd is null) and (effectiveDate is NOT null and effectiveDate>?1) and employeeId=?2")
	public PayStructureHd getPayRevision(Date currentDate, Long employeeId);

	@Query(" from PayStructureHd where  employeeId=?1")
	public List<PayStructureHd> getEmployeePayRevisionList(Long employeeId);

	String findEmployees = "select e.firstName,e.lastName,e.dateOfJoining,g.gradesName,dept.departmentName,e.dateOfBirth,des.designationName,e.employeeCode,e.employeeId,emp.firstName AS reportingFirstName, emp.lastName AS reportingLastName,e.employeeLogoPath from Employee e JOIN Grades g ON e.gradesId=g.gradesId  JOIN Department dept ON dept.departmentId=e.departmentId JOIN Designation des ON e.designationId=des.designationId JOIN Employee emp ON emp.employeeId=e.ReportingToEmployee where e.employeeId not in (select hd.employeeId from PayStructureHd hd) and e.companyId=?1 order BY e.dateCreated DESC ";

	@Query(value = findEmployees, nativeQuery = true)
	public List<Object[]> getEmployees(Long companyId);

 	@Query("from PayStructureHd  where  employeeId=?1 AND  dateEnd is null")
 	public PayStructureHd  monthValidationList(Long employeeId);
 	
 	@Query("from PayStructureHd  where  employeeId=?2 AND  dateEnd=?1")
	public PayStructureHd findPayStructureOnDateAndEmpId(Date subtractDays, Long employeeId);
 	
 	
	@Query(value= "select SUM(ps.amount) from PayStructureHd payHd JOIN PayStructure ps ON ps.payStructureHdId=payHd.payStructureHdId JOIN PayHeads pHeads On pHeads.payHeadId=ps.payHeadId  where  ( payHd.dateEnd is null or payHd.dateEnd>=CURRENT_DATE) and (payHd.effectiveDate is NOT null and payHd.effectiveDate<=CURRENT_DATE) and payHd.employeeId=?1 and pHeads.isApplicableOnEsi='Y'",nativeQuery=true)
	public BigDecimal calculateSumOfHeadsBasedOnEsic ( Long employeeId);

	@Query(value="SELECT emp.employeeCode FROM PayStructureHd phd JOIN Employee emp ON phd.employeeId=emp.employeeId WHERE phd.dateEnd is null and phd.activeStatus = 'AC'",nativeQuery=true)
	public List<String> getEmployeeIdFromPayStructureHD();
	
//	SELECT emp.employeeCode FROM PayStructureHd phd JOIN Employee emp ON phd.employeeId=emp.employeeId WHERE phd.dateEnd is null and phd.activeStatus = 'AC'
	@Query(value="SELECT e.employeeId from Employee e JOIN PayStructureHd ps ON ps.employeeId=e.employeeId WHERE e.companyId=?1 and ( ps.dateEnd is null or ps.dateEnd>=?2) and (ps.effectiveDate is NOT null and ps.effectiveDate<=?2) and e.activeStatus='AC' and ps.activeStatus='AC' group by e.employeeId ",nativeQuery=true)
	public List<Integer> getPayStructureHdEmployeeId(Long companyId, Date date);

	
	@Query("select count(*) from PayStructureHd WHERE employeeId=?1   ") 
 	public Long checkEmployeePayStructure(Long employeeId);
	
	// getPays of all employee to check already exist in bulk upload pay structure
	public static final String ALLEMPLOYEEPAYS = "SELECT ps.employeeId , emp.employeeCode FROM PayStructureHd ps LEFT JOIN Employee emp on ps.employeeId = emp.employeeId where emp.companyId =?1 GROUP BY ps.employeeId";
	@Query(value = ALLEMPLOYEEPAYS, nativeQuery = true)
	public List<Object[]> getAllEmployeePays(Long companyId);
	
	 
	
}
