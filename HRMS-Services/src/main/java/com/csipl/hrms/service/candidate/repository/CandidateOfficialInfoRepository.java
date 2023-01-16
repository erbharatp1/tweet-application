package com.csipl.hrms.service.candidate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.candidate.CandidateOfficialInformation;
import com.csipl.hrms.model.employee.Employee;


public interface CandidateOfficialInfoRepository extends CrudRepository<CandidateOfficialInformation, Long>{

	@Query("from CandidateOfficialInformation where candidateId=?1")
	public CandidateOfficialInformation findCandidateOfficialInformation(Long candidateId);
	
	
	 
	 @Query(nativeQuery=true,value="SELECT employeeCode FROM Employee ORDER BY employeeId DESC LIMIT 1")
		public String lastEmployeeCode();
}
