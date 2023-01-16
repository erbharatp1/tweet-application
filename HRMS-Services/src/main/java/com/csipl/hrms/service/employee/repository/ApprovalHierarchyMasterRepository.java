package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;

@Repository("approvalHierarchyMasterRepository")
public interface ApprovalHierarchyMasterRepository extends JpaRepository<ApprovalHierarchyMaster, Long> {

//	String GET_LETTERS_BY_ID = "SELECT ah.approvalHierarchyId, ah.designationId, ah.levels, ahm.activeStatus, ahm.letterId, ah.activeStatus as status, ahm.approvalHierarchyMasterId FROM ApprovalHierarchy ah JOIN ApprovalHierarchyMaster ahm ON "
//			+ "ahm.approvalHierarchyMasterId=ah.approvalHierarchyMasterId WHERE ahm.letterId=?1  and ahm.activeStatus='AC' ";

	String GET_LETTERS_BY_ID = " SELECT ah.approvalHierarchyId, ah.designationId, ah.levels, ahm.activeStatus, ahm.letterId, ah.activeStatus as status, ahm.approvalHierarchyMasterId FROM ApprovalHierarchy ah JOIN ApprovalHierarchyMaster ahm ON "
			+ "ahm.approvalHierarchyMasterId=ah.approvalHierarchyMasterId WHERE ahm.letterId=?1 ";

	@Query(value = GET_LETTERS_BY_ID, nativeQuery = true)
	public List<Object[]> findLetterApprovalById(Long letterId);

	String GET_MASTER_APPROVAL_LIST = "SELECT ahm.letterId, l.letterName, ahm.activeStatus FROM ApprovalHierarchyMaster ahm LEFT JOIN Letter l ON l.letterId=ahm.letterId WHERE ahm.companyId=?1";

	@Query(value = GET_MASTER_APPROVAL_LIST, nativeQuery = true)
	public List<Object[]> findMasterApprovalList(Long companyId);

	@Query(" from ApprovalHierarchyMaster where companyId=?1 ORDER BY approvalHierarchyMasterId ASC")
	public List<ApprovalHierarchyMaster> findLetterApprovals(Long companyId);

	@Query(" from ApprovalHierarchyMaster where companyId=?1 and letterId=?2 ")
	public ApprovalHierarchyMaster getMasterApprovalStatus(Long companyId, Long letterId);

	@Query(nativeQuery = true, value = " SELECT COUNT(ahm.approvalHierarchyMasterId) FROM ApprovalHierarchyMaster ahm WHERE  ahm.activeStatus='AC' AND ahm.letterId=?1 ")
	public int findApprovalHierarchyByStatus(Long letterId);
}
