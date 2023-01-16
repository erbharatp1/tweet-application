package com.csipl.hrms.service.recruitement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;

@Repository("candidatePayStructureHdRepository")
public interface CandidatePayStructureHdRepository extends CrudRepository<CandidatePayStructureHd, Long> {

	@Query(" select count(*) from CandidatePayStructureHd WHERE interviewScheduleId=?1 ")
	public Long checkEmployeePayStructure(Long employeeId);
	
	@Query("from CandidatePayStructureHd  where  interviewScheduleId=?1 ")
 	public CandidatePayStructureHd  monthValidationList(Long interviewScheduleId);

}
