package com.csipl.tms.leave.service;

import java.util.List;

import com.csipl.tms.model.leave.LeaveSchemeMaster;

public interface LeaveSchemeService {

	public List<LeaveSchemeMaster> findLeaveScheme(Long leavePeriodId);

	public LeaveSchemeMaster save(LeaveSchemeMaster leaveScheme);
	  public List<LeaveSchemeMaster> findAllLeaveScheme();
}
