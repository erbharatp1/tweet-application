package com.csipl.hrms.service.recruitement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.csipl.hrms.model.recruitment.CandidateEvolution;

@Repository
public interface CandidateEvolutionRepository extends CrudRepository<CandidateEvolution, Long> {

	@Query("From CandidateEvolution where evalutionId=?1")
	CandidateEvolution CandidateEvolutionDetails(Long evalutionId);
}
