package com.csipl.hrms.service.recruitement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.dto.recruitment.OverallBacklogsGraphDTO;
import com.csipl.hrms.dto.recruitment.PositionSourceGraphDTO;
import com.csipl.hrms.service.recruitement.repository.RecruitmentDashboardRepository;
import com.csipl.hrms.service.util.ConverterUtil;

@Transactional
@Service("recruitmentDashboardService")
public class RecruitmentDashboardServiceImpl implements RecruitmentDashboardService {

	@Autowired
	private RecruitmentDashboardRepository recruitmentDashboardRepository;

	@Override
	public List<PositionSourceGraphDTO> getPositionCreatedSource(Long companyId, Long processMonth) {
		List<Object[]> empReportList = recruitmentDashboardRepository.findPositionCreatedSource(companyId,
				processMonth);
		List<PositionSourceGraphDTO> employeeLeaveGraphDTO = new ArrayList<PositionSourceGraphDTO>();

		for (Object[] report : empReportList) {

			PositionSourceGraphDTO empDto = new PositionSourceGraphDTO();

			String sourceOfPosition = report[0] != null ? (String) report[0] : null;
//			Long positionCount = report[1] != null ? Long.parseLong(report[1].toString()) : null;

//			empDto.setSourceOfPosition(DropDownCache.getInstance()
//					.getDropDownValue(DropDownEnum.sourceOfPosition.getDropDownName(), sourceOfPosition));
			if (sourceOfPosition.equals("SN")) {
				empDto.setSourceOfPosition("New");
			} else if (sourceOfPosition.equals("SE")) {
				empDto.setSourceOfPosition("Existing");
			} else if (sourceOfPosition.equals("SA")) {
				empDto.setSourceOfPosition("Additional");
			}

			empDto.setPositionCount(ConverterUtil.getString(report[1]));

			employeeLeaveGraphDTO.add(empDto);

		}

		return employeeLeaveGraphDTO;
	}

	@Override
	public Map<String, String> getOverallBacklogs(Long companyId, Long processMonth) {

		Integer totalPositionCount = recruitmentDashboardRepository.findTotalPositionCount(companyId, processMonth);
		Integer closedPositionCount = recruitmentDashboardRepository.findClosedPositionCount(companyId, processMonth);
		Integer openPositionCount;

		if (closedPositionCount != 0) {
			openPositionCount = totalPositionCount - closedPositionCount;
		} else {
			openPositionCount = totalPositionCount;
		}

		OverallBacklogsGraphDTO overallBacklogsGraphDTO = new OverallBacklogsGraphDTO();

		overallBacklogsGraphDTO.setTotalPositionCount(totalPositionCount);
		overallBacklogsGraphDTO.setClosedPositionCount(closedPositionCount);
		overallBacklogsGraphDTO.setOpenPositionCount(openPositionCount);

		Map<String, String> positionMap = new HashMap<String, String>();
		positionMap.put("Total Position", overallBacklogsGraphDTO.getTotalPositionCount().toString());
		positionMap.put("Closed", overallBacklogsGraphDTO.getClosedPositionCount().toString());
		positionMap.put("Open", overallBacklogsGraphDTO.getOpenPositionCount().toString());

		return positionMap;
	}

	@Override
	public List<PositionSourceGraphDTO> getPositionClosedBySource() {

		List<Object[]> positionClosedList = recruitmentDashboardRepository.findPositionClosedBySource();

		List<PositionSourceGraphDTO> employeeLeaveGraphDTO = new ArrayList<PositionSourceGraphDTO>();

		for (Object[] report : positionClosedList) {

			PositionSourceGraphDTO empDto = new PositionSourceGraphDTO();

			String sourceOfProfile = report[0] != null ? (String) report[0] : null;

//			empDto.setSourceOfPosition(DropDownCache.getInstance()
//					.getDropDownValue(DropDownEnum.sourceOfPosition.getDropDownName(), sourceOfPosition));

			if (sourceOfProfile.equals("LI")) {
				empDto.setSourceOfProfile("LinkedIn");
			} else if (sourceOfProfile.equals("NC")) {
				empDto.setSourceOfProfile("Naukri.com");
			} else if (sourceOfProfile.equals("MN")) {
				empDto.setSourceOfProfile("Monster.com");
			}

			empDto.setSourceOfProfileCount(ConverterUtil.getString(report[1]));

			employeeLeaveGraphDTO.add(empDto);

		}

		return employeeLeaveGraphDTO;
	}

	@Override
	public List<PositionSourceGraphDTO> getPositionClosedByMonth(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> positionClosedByMonthList = recruitmentDashboardRepository.findPositionClosedByMonth(companyId);
		List<PositionSourceGraphDTO> positionDtoList = new ArrayList<PositionSourceGraphDTO>();

		// logger.info("getPositionClosedByMonth service is calling");

		for (Object[] report : positionClosedByMonthList) {

			PositionSourceGraphDTO positionDto = new PositionSourceGraphDTO();

			String monthName = report[1] != null ? (String) report[1] : null;

			positionDto.setMonth(ConverterUtil.getString(report[0]));
			positionDto.setMonthName(monthName);
			positionDto.setYear(ConverterUtil.getString(report[2]));
			positionDto.setPositionClosedByMonthCount(ConverterUtil.getString(report[3]));

			positionDtoList.add(positionDto);

		}

		return positionDtoList;

	}

	@Override
	public Map<String, PositionSourceGraphDTO> getRecruiterBacklogs(Long companyId, Long processMonth) {

		List<Object[]> totalCount = recruitmentDashboardRepository.findTotalCount(companyId, processMonth);
		List<Object[]> closedCount = recruitmentDashboardRepository.findClosedCount(companyId, processMonth);
		Map<String, PositionSourceGraphDTO> hashMap = new HashMap<String, PositionSourceGraphDTO>();
		Map<String, Long> map = new HashMap<String, Long>();

		for (Object[] obj : closedCount) {

			String recruiter = obj[0] != null ? (String) obj[0] : null;
			Long closeCount = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
			// positionDto.setTotalCount(ConverterUtil.getString(obj[1]));

			map.put(recruiter, closeCount);
		}

		for (Object[] obj : totalCount) {

			PositionSourceGraphDTO positionDto = new PositionSourceGraphDTO();

			String recruiter = obj[0] != null ? (String) obj[0] : null;
			Long totalCnt = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
			// positionDto.setCloseCount(ConverterUtil.getString(obj[1]));
			Long close = map.get(recruiter);

			positionDto.setRecruiter(recruiter);
			positionDto.setTotalCount(totalCnt);

			if (totalCnt != null) {
				if (close == null) {
					close = 0l;
					positionDto.setCloseCount(0l);
				}
				Long open = totalCnt - close;
				String openCount = open.toString();
				String totalPositionCount = totalCnt.toString();
				String closePositionCount = close.toString();
				positionDto.setOpenCount(openCount);
				positionDto.setTotalPositionCount(totalPositionCount);
				positionDto.setClosePositionCount(closePositionCount);
			}

			hashMap.put(recruiter, positionDto);

		}

		return hashMap;
	}

}
