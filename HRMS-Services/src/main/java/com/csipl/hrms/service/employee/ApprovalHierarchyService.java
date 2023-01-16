package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.ApprovalHierarchy;

public interface ApprovalHierarchyService {

	public List<ApprovalHierarchy> save(List<ApprovalHierarchy> approvalHierarchy);

	public List<Object[]> getAllLetterApproval(Long companyId);

	public void delete(Long approvalHierarchyId);

}
