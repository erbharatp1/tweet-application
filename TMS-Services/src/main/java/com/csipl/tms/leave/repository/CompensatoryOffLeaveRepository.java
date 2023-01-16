package com.csipl.tms.leave.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface CompensatoryOffLeaveRepository {

	List<Object[]> getAllEmpApprovalsPendingCompOff(Long companyId, SearchDTO searcDto);

	List<Object[]> getApprovalsNonPendingCompOff(Long companyId, Long employeeId, SearchDTO searcDto);
	
	
}
