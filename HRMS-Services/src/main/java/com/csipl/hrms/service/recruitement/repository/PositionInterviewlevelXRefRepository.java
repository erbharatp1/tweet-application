package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;

@Repository("positionInterviewlevelXRefRepository")
public interface PositionInterviewlevelXRefRepository extends CrudRepository<PositionInterviewlevelXRef, Long> {
	@Query(value = "FROM PositionInterviewlevelXRef   WHERE  positionId =?1")
	List<PositionInterviewlevelXRef> findlevelsByPositionId(Long positionId);
}
