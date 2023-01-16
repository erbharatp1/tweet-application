package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.service.employee.repository.ApprovalHierarchyMasterRepository;

@Service("approvalHierarchyMasterService")
@Transactional
public class ApprovalHierarchyMasterServiceImpl implements ApprovalHierarchyMasterService {
	@Autowired
	private ApprovalHierarchyMasterRepository approvalHierarchyMasterRepository;

	@Override
	public ApprovalHierarchyMaster saveHierarchy(ApprovalHierarchyMaster approvalHierarchyMaster) {

		return approvalHierarchyMasterRepository.save(approvalHierarchyMaster);
	}

	@Override
	public List<Object[]> findLetterApprovalById(Long letterId) {

		return approvalHierarchyMasterRepository.findLetterApprovalById(letterId);
	}

	@Override
	public List<Object[]> getMasterApprovalList(Long companyId) {

		return approvalHierarchyMasterRepository.findMasterApprovalList(companyId);
	}

	@Override
	public List<ApprovalHierarchyMaster> getLetterApprovals(Long companyId) {
		
		return approvalHierarchyMasterRepository.findLetterApprovals(companyId);
	}

	@Override
	public ApprovalHierarchyMaster getMasterApprovalStatus(Long companyId, Long letterId) {
		// TODO Auto-generated method stub
		return approvalHierarchyMasterRepository.getMasterApprovalStatus(companyId,letterId);
	}

	@Override
	public int findApprovalHierarchyByStatus(Long letterId) {
		// TODO Auto-generated method stub
		return approvalHierarchyMasterRepository.findApprovalHierarchyByStatus(letterId);
	}

}
