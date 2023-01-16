package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.CandidatePayStructure;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;

@Repository("candidatePayStructureRepository")
public interface CandidatePayStructureRepository extends CrudRepository<CandidatePayStructureHd, Long> {

	@Query(value = " from CandidatePayStructure WHERE candidatePayStructureHdId=?1")
	public List<CandidatePayStructure> findAllPayStructureById(Long payStructureHdId);
	
	@Query(value = "from CandidatePayStructureHd cp WHERE cp.interviewScheduler.interviewScheduleId=?1")
	public CandidatePayStructureHd getCandidatePaystructure(Long intrviewScheduleId);
}
