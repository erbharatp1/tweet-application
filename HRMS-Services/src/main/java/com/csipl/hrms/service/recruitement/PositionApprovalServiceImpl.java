package com.csipl.hrms.service.recruitement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.recruitment.PositionApprovalPerson;
import com.csipl.hrms.service.recruitement.repository.PositionApprovalRepository;

@Service("positionApprovalService")
@Transactional
public class PositionApprovalServiceImpl implements PositionApprovalService {

	@Autowired
	private PositionApprovalRepository positionApprovalRepository;

	@Override
	public List<PositionApprovalPerson> savePositionApproval(List<PositionApprovalPerson> positionApproval) {

		return (List<PositionApprovalPerson>) positionApprovalRepository.save(positionApproval);
	}

	@Override
	public List<PositionApprovalPerson> findAllPositionApprovals() {

		return (List<PositionApprovalPerson>) positionApprovalRepository.findAllPositionApprovals();
	}

}
