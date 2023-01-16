package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.employee.EmployeeIdProof;

public interface EmployeeIdProofRepository extends CrudRepository<EmployeeIdProof, Long>{
	@Query(" from EmployeeIdProof where employeeId=?1  ORDER BY  employeeIdProofsId  DESC")
    public List<EmployeeIdProof> findAllEmpIDProof(Long employeeId);

	String countIdProof="SELECT COUNT(*) from EmployeeIdProofs ep join Employee e on ep.employeeId= e.employeeId where e.activeStatus='AC' and ep.idTypeId=?1 and ep.idNumber=?2";  
		@Query(value = countIdProof, nativeQuery = true)	
	 public int checkIdProof(String idType,String idNumber );
		
		// check EmployeeIdProof 
		String countIdProofexist="SELECT COUNT(*) from EmployeeIdProofs eip join Employee e on eip.employeeId= e.employeeId where e.employeeId !=?1 and e.activeStatus='AC' and eip.idTypeId=?2 and eip.idNumber=?3";  
		@Query(value = countIdProofexist, nativeQuery = true)	
		public int checkIdProofExist(Long employeeId,String idTypeId,String idNumber );
		
		
}
