package com.csipl.tms.leave.service;

import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.model.leave.CompensatoryOff;

public interface CompensatoryOffService {
	public void saveAll(CompensatoryOff compensatoryOff) throws ErrorHandling;
	public List<CompensatoryOff> findAllCompensatoryOff(Long companyId);
	public List<CompensatoryOff> findMyCompOffPendingReqList(Long employeeId);
	public List<CompensatoryOff> findMyCompOffExcludedPendingReqList(Long employeeId); 
	public List<CompensatoryOff> findAllCompOffPendingReqList(Long companyId);
	public List<CompensatoryOff> findAllCompOffExcludedPendingReqList(Long companyId); 
	public CompensatoryOff getCompensatoryOff(Long tmsCompensantoryOffId);
	public List<Object[]> getApprovalsPendingCompOff(Long companyId,Long employeeId);
	public List<Object[]> getAllEmpApprovalsPendingCompOff(Long companyId, SearchDTO searcDto);
	public List<Object[]> getApprovalsNonPendingCompOff(Long companyId,Long employeeId, SearchDTO searcDto);
	/**
	 * compOffCountMyTeam}
	 */
	public CompensatoryOffDTO compOffCountMyTeam(Long employeeId, Long companyId);
	public CompensatoryOffDTO compOffAllTimeCountMyTeam(Long employeeId, Long companyId);
	/**
	 * compOffCountMy}
	 */
	public CompensatoryOffDTO compOffCountMy(Long employeeId, Long companyId);
	public List<CompensatoryOff> getPendingCompffOfEntitybyPagination(Long employeeId, SearchDTO searcDto);
}
