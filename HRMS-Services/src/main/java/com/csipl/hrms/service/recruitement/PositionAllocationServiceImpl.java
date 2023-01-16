package com.csipl.hrms.service.recruitement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.model.recruitment.PositionAllocationXref;
import com.csipl.hrms.service.recruitement.repository.PositionAllocationRepository;
import com.csipl.hrms.service.recruitement.repository.PositionPaginationRepository;
import com.csipl.tms.dto.common.SearchDTO;

@Service("positionAllocationService")
@Transactional
public class PositionAllocationServiceImpl implements PositionAllocationService {

	@Autowired
	private PositionAllocationRepository positionAllocationRepository;

	@Autowired
	private PositionPaginationRepository positionPaginationRepository;

	@Override
	public void savePositionAllocation(PositionAllocationXref positionAllocationXref) {

		positionAllocationRepository.save(positionAllocationXref);
	}

	@Override
	public List<Object[]> findRecentPositions(Long companyId, String positionCode) {

		return positionAllocationRepository.findRecentPositions(companyId, positionCode);
	}

	@Override
	public List<Object[]> findAssignedPositions(Long companyId, SearchDTO searcDto) {

		return positionPaginationRepository.findAssignedPositions(companyId, searcDto);
	}

	@Override
	public PositionAllocationXrefDTO findNoOfPosition(Long positionId) {
		Integer count = positionAllocationRepository.findNoOfPosition(positionId);
		PositionAllocationXrefDTO positionAllocationXrefDTO = new PositionAllocationXrefDTO();
		positionAllocationXrefDTO.setPositionCount(count);
		return positionAllocationXrefDTO;
	}

	@Override
	public List<Object[]> findPositiongById(Long positionId) {

		return positionAllocationRepository.findPositiongById(positionId);

	}

	@Override
	public List<Object[]> findPositionAllocationById(Long positionId, Long positionAllocationId) {

		return positionAllocationRepository.findPositionAllocationById(positionId, positionAllocationId);
	}

	@Override
	public Map<String, Integer> getOverallBacklogs(Long companyId) {

		Integer totalPositionCount = positionAllocationRepository.findTotalPositionCount(companyId);
		Integer closedPositionCount = positionAllocationRepository.findClosedPositionCount(companyId);
		Integer openPositionCount;

		if (closedPositionCount != 0) {
			openPositionCount = totalPositionCount - closedPositionCount;
		} else {
			openPositionCount = totalPositionCount;
		}

		PositionAllocationXrefDTO overallBacklogsGraphDTO = new PositionAllocationXrefDTO();

		overallBacklogsGraphDTO.setClosedPositionCount(closedPositionCount);
		overallBacklogsGraphDTO.setOpenPositionCount(openPositionCount);

		Map<String, Integer> positionMap = new HashMap<String, Integer>();
		positionMap.put("Closed", overallBacklogsGraphDTO.getClosedPositionCount());
		positionMap.put("Open", overallBacklogsGraphDTO.getOpenPositionCount());

		return positionMap;
	}

	@Override
	public List<Object[]> findAssignToRecruiterList() {

		return positionAllocationRepository.findAssignToRecruiterList();
	}

	@Override
	public List<Object[]> findNoOfPositionList() {

		return positionAllocationRepository.findNoOfPositionList();
	}

	@Override
	public List<PositionAllocationXrefDTO> findRecruiterList(List<Object[]> positionAllocationList,
			List<Object[]> noOfPositionList) {

		List<PositionAllocationXrefDTO> positionAllocationXrefDTOList = new ArrayList<PositionAllocationXrefDTO>();

		Map<Long, Long> map = new HashMap<Long, Long>();

		for (Object[] obj : noOfPositionList) {
			Long postnId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			Long noPosition = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;

			map.put(postnId, noPosition);
		}

		for (Object[] report : positionAllocationList) {
			PositionAllocationXrefDTO empDto = new PositionAllocationXrefDTO();

			Long positionId = report[4] != null ? Long.parseLong(report[4].toString()) : null;
			Long noOfPosition = report[5] != null ? Long.parseLong(report[5].toString()) : null;
			Long allocatedPosition = map.get(positionId);
			String positionTitle = report[0] != null ? (String) report[0] : null;
			String positionCode = report[1] != null ? (String) report[1] : null;
			String requiredExperience = report[2] != null ? (String) report[2] : null;
			String jobLocation = report[3] != null ? (String) report[3] : null;

			empDto.setPositionId(positionId);
			empDto.setPositionTitle(positionTitle);
			empDto.setPositionCode(positionCode);
			empDto.setRequiredExperience(requiredExperience);
			empDto.setJobLocation(jobLocation);

			if (allocatedPosition == null) {
				allocatedPosition = noOfPosition;
				positionAllocationXrefDTOList.add(empDto);
			} else if (!allocatedPosition.equals(noOfPosition)) {
				positionAllocationXrefDTOList.add(empDto);
			}

		}

		return positionAllocationXrefDTOList;
	}

	@Override
	public String getCompany(Long companyId) {

		return positionAllocationRepository.getCompany(companyId);
	}

	@Override
	public List<Object[]> findAssessment(Long companyId, Long interviewScheduleId) {
		return positionAllocationRepository.findAssessment(companyId, interviewScheduleId);
	}

	@Override
	public List<Object[]> getLevelList(Long companyId, Long interviewScheduleId) {
		return positionAllocationRepository.getLevelList(companyId, interviewScheduleId);
	}

	@Override
	public List<Object[]> getClosedAllocations(Long companyId) {

		return positionAllocationRepository.findClosedAllocations(companyId);
	}

}
