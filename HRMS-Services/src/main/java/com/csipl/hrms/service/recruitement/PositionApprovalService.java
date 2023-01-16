package com.csipl.hrms.service.recruitement;

import java.util.List;

import com.csipl.hrms.model.recruitment.PositionApprovalPerson;

public interface PositionApprovalService {

	List<PositionApprovalPerson> savePositionApproval(List<PositionApprovalPerson> positionApproval);

	List<PositionApprovalPerson> findAllPositionApprovals();

}
