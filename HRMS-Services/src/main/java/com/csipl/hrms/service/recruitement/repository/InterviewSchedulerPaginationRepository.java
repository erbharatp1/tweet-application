package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface InterviewSchedulerPaginationRepository {

	List<Object[]> findAssignedInterviewSchedule(Long companyId, SearchDTO searcDto);

}
