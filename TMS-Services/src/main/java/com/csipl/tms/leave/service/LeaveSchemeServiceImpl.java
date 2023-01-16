package com.csipl.tms.leave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.leave.repository.LeaveSchemeRepository;
import com.csipl.tms.model.leave.LeaveSchemeMaster;
@Service("leaveSchemeService")
public class LeaveSchemeServiceImpl implements LeaveSchemeService{

	@Autowired
	private LeaveSchemeRepository leaveSchemeRepository;

	@Override
	public List<LeaveSchemeMaster> findLeaveScheme(Long leavePeriodId) {
		// TODO Auto-generated method stub
		return leaveSchemeRepository.findAllLeaveScheme(leavePeriodId);
	}

	@Override
	public LeaveSchemeMaster save(LeaveSchemeMaster leaveScheme) {
		// TODO Auto-generated method stub
		return leaveSchemeRepository.save(leaveScheme);
	}

	@Override
	public List<LeaveSchemeMaster> findAllLeaveScheme() {
		// TODO Auto-generated method stub
		return leaveSchemeRepository.findLeaveScheme();
	}

	

	
	
	
}
