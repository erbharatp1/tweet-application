package com.csipl.tms.attendanceregularizationrequest.service;

import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;

public interface ARRequestPagingAndFilterService {

	List<Object[]> getARByPagingAndFilter(Long companyId, Boolean status, SearchDTO searcDto);

	public List<Object[]> getAllMassCommunications(Long companyId, SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getTeamsAbsentListByDate(Long companyId, Long employeeId, Date selectDate,
			SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getTeamsPresentListByDate(Long companyId, Long employeeId, Date date,
			SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getTeamsLateComersListByDate(Long companyId, Long employeeId, Date date,
			SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getAllLateComersListByDate(Long companyId, Date actionDate, SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getAllAbsentListByDate(Long companyId, Date actionDate, SearchDTO searcDto);

	public List<DeviceLogsInfoDTO> getAllPresentListByDate(Long companyId, Date actionDate, SearchDTO searcDto);

}
