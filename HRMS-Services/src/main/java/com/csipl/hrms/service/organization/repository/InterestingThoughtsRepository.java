package com.csipl.hrms.service.organization.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.InterestingThought;

@Repository
@Transactional
public interface InterestingThoughtsRepository extends CrudRepository<InterestingThought, Long> {
//	String FATCH_INTERESTINGTHOUGHT = "SELECT concat(emp.firstName,' ',emp.lastName)AS empName, emp.employeeLogoPath,it.InterestingThoughtsId,it.thoughts,it.companyId,it.employeeId,it.userId,it.dateCreated,it.dateUpdate,it.userIdUpdate from InterestingThoughts it  JOIN Employee emp ON emp.employeeId=it.employeeId where it.companyId=?1";

	// @Query(" from InterestingThought where companyId=?1 ORDER BY
	// interestingThoughtsId DESC ")
//	@Query(value = FATCH_INTERESTINGTHOUGHT)
	 @Query(" from InterestingThought where companyId=?1 ORDER BY interestingThoughtsId DESC ")
	public List<InterestingThought> findInterestingThought(Long companyId);

	@Query(" from InterestingThought where interestingthoughtsId=?1 and companyId=?2  ORDER BY  interestingThoughtsId  DESC ")
	public InterestingThought findOne(Long interestingthoughtsId, Long companyId);

}
