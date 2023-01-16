package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.ApprovalHierarchy;

@Repository("approvalHierarchyRepository")
public interface ApprovalHierarchyRepository extends CrudRepository<ApprovalHierarchy, Long> {

	String GET_ALL_LETTER_APPROVALS = " SELECT ah.approvalHierarchyId, ah.approvalHierarchyMasterId, ahm.letterId, l.letterName, ahm.activeStatus, ah.levels, ah.designationId FROM ApprovalHierarchy ah LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.approvalHierarchyMasterId=ah.approvalHierarchyMasterId "
			+ "LEFT JOIN Letter l ON l.letterId=ahm.letterId where ah.companyId=?1 ORDER BY approvalHierarchyId DESC";

	@Query(value = GET_ALL_LETTER_APPROVALS, nativeQuery = true)
	public List<Object[]> findAllLetterApproval(Long companyId);

}
