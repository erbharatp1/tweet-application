package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;

public interface ApprovalHierarchyMasterService {

	public ApprovalHierarchyMaster saveHierarchy(ApprovalHierarchyMaster approvalHierarchyMaster);

	public List<Object[]> findLetterApprovalById(Long letterId);

	public List<Object[]> getMasterApprovalList(Long companyId);

	public List<ApprovalHierarchyMaster> getLetterApprovals(Long companyId);
	
	public ApprovalHierarchyMaster getMasterApprovalStatus(Long companyId,Long letterId);
	
	public int findApprovalHierarchyByStatus(Long letterId);

}
