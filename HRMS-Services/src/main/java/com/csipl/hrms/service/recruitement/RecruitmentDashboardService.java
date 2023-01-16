package com.csipl.hrms.service.recruitement;

import java.util.List;
import java.util.Map;

import com.csipl.hrms.dto.recruitment.PositionSourceGraphDTO;

public interface RecruitmentDashboardService {

	public List<PositionSourceGraphDTO> getPositionCreatedSource(Long companyId, Long processMonth);

	public Map<String, String> getOverallBacklogs(Long companyId, Long processMonth);

	public List<PositionSourceGraphDTO> getPositionClosedBySource();

	public List<PositionSourceGraphDTO> getPositionClosedByMonth(Long companyId);

	public Map<String, PositionSourceGraphDTO> getRecruiterBacklogs(Long companyId, Long processMonth);

}
