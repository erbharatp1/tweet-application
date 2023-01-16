package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.EmployeeStatuary;

@Transactional
public interface EmployeeStatuaryRepository extends CrudRepository<EmployeeStatuary, Long>{
 
	@Query(" from EmployeeStatuary where employeeId=?1 and status = 'AC' ") 
    public List<EmployeeStatuary> findAllEmployeeStatuarys(Long employeeId); 
	
	@Query(" from EmployeeStatuary where employeeId=?1 and statuaryType != 'UA' and status = 'AC'") 
    public List<EmployeeStatuary> findAllEmployeeStatuarysWithoutUAN(Long employeeId); 
	
	@Query(" from EmployeeStatuary where employeeId=?1 and familyId IS NOT NULL and statuaryType != 'UA' and status = 'AC'") 
    public List<EmployeeStatuary> findAllEmployeeNominee(Long employeeId); 
	
	
	String pfCount="SELECT COUNT(statuaryNumber) FROM EmployeeStatuary es join Employee e on es.employeeId= e.employeeId\r\n" + 
	 		"where  e.activeStatus='AC' and es.statuaryNumber=?1 and es.statuaryType =?2 ";
	 @Query(value = pfCount, nativeQuery = true)
		public int pfCheck(String uan,String statuaryType);
	 
	/* @Query("update EmployeeStatuary s set  s.statuaryNumber= ?1 and s.isApplicable='Y' where  s.statuaryType =?2 ")
	 @Modifying
		public int updateStatuary(String statuaryNum,String statuaryType);*/
	
	 @Query(" from EmployeeStatuary where employeeId=?1 and statuaryType=?2 and status = 'AC'  ") 
	    public EmployeeStatuary findEmployeeStatuary(Long employeeId,String statuaryType); 
	 
	 // this query only checking whether we are getting single row data or multiple when bulk upload pay structure
	 @Query(" from EmployeeStatuary where employeeId=?1 and statuaryType=?2 and status = 'AC'  ") 
	    public List<EmployeeStatuary> findEmployeeStatuaryIsList(Long employeeId,String statuaryType); 
	
	 
	// check statuary 
	String countStatuary="SELECT COUNT(*) from EmployeeStatuary es join Employee e on es.employeeId= e.employeeId where e.employeeId !=?1 and e.activeStatus='AC' and es.statuaryType=?2 and es.statuaryNumber=?3";  
	@Query(value = countStatuary, nativeQuery = true)	
	public int checkStatuaryExist(Long employeeId,String statuaryType,String statuaryNumber );
			
			
}
