package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface PositionPaginationRepository {

	List<Object[]> findAssignedPositions(Long companyId, SearchDTO searcDto);

}
