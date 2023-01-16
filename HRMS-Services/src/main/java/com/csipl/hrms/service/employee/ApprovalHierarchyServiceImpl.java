package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.ApprovalHierarchy;
import com.csipl.hrms.service.employee.repository.ApprovalHierarchyRepository;

@Service("approvalHierarchyService")
@Transactional
public class ApprovalHierarchyServiceImpl implements ApprovalHierarchyService {
	@Autowired
	private ApprovalHierarchyRepository approvalHierarchyRepository;

	@Override
	public List<ApprovalHierarchy> save(List<ApprovalHierarchy> approvalHierarchy) {
		List<ApprovalHierarchy> approvalHierarchies = (List<ApprovalHierarchy>) approvalHierarchyRepository
				.save(approvalHierarchy);

		return approvalHierarchies;
	}

	@Override
	public List<Object[]> getAllLetterApproval(Long companyId) {

		return approvalHierarchyRepository.findAllLetterApproval(companyId);
	}

	@Override
	public void delete(Long approvalHierarchyId) {
		
		approvalHierarchyRepository.delete(approvalHierarchyId);
	}

}
