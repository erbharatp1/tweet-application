package com.csipl.hrms.service.recruitement.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.CandidateFinalEvalution;

@Repository("candidateFinalEvolutionRepository")
@Transactional
public interface CandidateFinalEvolutionRepository extends CrudRepository<CandidateFinalEvalution, Long> {

	@Query("From CandidateFinalEvalution where interviewScheduledId=?1")
	CandidateFinalEvalution CandidateFinalEvolutionDetails(Long id);

	public static final String UPDATE_DECLARATION_STATUS = "UPDATE CandidateFinalEvalution SET finalStatus = :finalStatus , positionClosedBy = :positionClosedBy WHERE id =:id ";

	@Modifying
	@Query(value = UPDATE_DECLARATION_STATUS)
	void updatePositionAsClosed(@Param("id") Long id, @Param("finalStatus") String finalStatus,
			@Param("positionClosedBy") Date positionClosedBy);

	@Query("From CandidateFinalEvalution")
	List<CandidateFinalEvalution> findAllCandidateEvalution();

}
