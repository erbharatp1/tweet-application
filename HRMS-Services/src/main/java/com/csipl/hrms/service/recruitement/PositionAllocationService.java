package com.csipl.hrms.service.recruitement;

import java.util.List;
import java.util.Map;

import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.model.recruitment.PositionAllocationXref;
import com.csipl.tms.dto.common.SearchDTO;

public interface PositionAllocationService {

	public void savePositionAllocation(PositionAllocationXref positionAllocationXref);

	public List<Object[]> findRecentPositions(Long companyId, String positionCode);

	public List<Object[]> findAssignedPositions(Long companyId, SearchDTO searcDto);

	public PositionAllocationXrefDTO findNoOfPosition(Long positionId);

	public List<Object[]> findPositiongById(Long positionId);

	public List<Object[]> findPositionAllocationById(Long positionId, Long positionAllocationId);

	public Map<String, Integer> getOverallBacklogs(Long companyId);

	public List<Object[]> findAssignToRecruiterList();
	
	public List<Object[]> findNoOfPositionList();

	public List<PositionAllocationXrefDTO> findRecruiterList(List<Object[]> positionAllocationList, List<Object[]> noOfPositionList);

	public String getCompany(Long companyId);

	public List<Object[]> findAssessment(Long companyId, Long interviewScheduleId);

	public List<Object[]> getLevelList(Long companyId, Long interviewScheduleId);

	public List<Object[]> getClosedAllocations(Long companyId);

}
