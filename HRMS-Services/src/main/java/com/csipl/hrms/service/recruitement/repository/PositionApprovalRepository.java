package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.recruitment.PositionApprovalPerson;

@Repository("positionApprovalRepository")
public interface PositionApprovalRepository extends CrudRepository<PositionApprovalPerson, Long> {

	@Query(" from PositionApprovalPerson where status='AC' ")
	List<PositionApprovalPerson> findAllPositionApprovals();

}
