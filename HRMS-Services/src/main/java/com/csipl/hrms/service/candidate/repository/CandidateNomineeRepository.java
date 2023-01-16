package com.csipl.hrms.service.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.csipl.hrms.model.candidate.CandidateNominee;

public interface CandidateNomineeRepository extends CrudRepository<CandidateNominee,Long>{

	@Query(" from CandidateNominee where candidateId=?1  ORDER BY  candidateNomineeid  DESC")
	 public List<CandidateNominee> findAllNominee(Long candidateId);
}
