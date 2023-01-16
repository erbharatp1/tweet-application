package com.csipl.tms.deviceinfo.service;

import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;

public interface DeviceLogsService {

	public int presentCountAll();

	public int presentCountReportingTo(Long companyId, Long employeeId);

	public int absentCountAll();

	public int absentCountReportingTo(Long companyId, Long employeeId);

	public int lateComersAll();

	public int lateComersReportingTo(Long companyId, Long employeeId);

	public void saveAll(List<DeviceLogsInfo> deviceLogsInfoList);

	public List<DeviceLogsInfoDTO> getAllLateComersList(Long companyId );

	public List<DeviceLogsInfoDTO> getAllAbsentList(Long companyId );

	public List<DeviceLogsInfoDTO> getAllPresentList(Long companyId );

	public List<DeviceLogsInfoDTO> getAllAbsentListByDate(Long companyId, Date selectDate);

	public List<DeviceLogsInfoDTO> getAllPresentListByDate(Long companyId, Date selectDate);

	public List<DeviceLogsInfoDTO> getAllLateComersListByDate(Long companyId, Date selectDate);

	public void deleteByLogDate();

	public List<DeviceLogsInfoDTO> currentAttendanceReport(Long companyId, Date attendanceDate);
	
	public DeviceLogsInfoDTO punchInOnCDate(String nameOfUser);
	

	public List<DeviceLogsInfoDTO> getTeamsAbsentListByDate(Long companyId, Long employeeId, Date selectDate);

	public List<DeviceLogsInfoDTO> getTeamsPresentListByDate(Long companyId, Long employeeId, Date selectDate);

	public List<DeviceLogsInfoDTO> getTeamsLateComersListByDate(Long companyId, Long employeeId, Date selectDate);
	//by
	public List<DeviceLogsInfoDTO> getTeamsAttendanceReport(Long companyId, Long employeeId, Date attendanceDate);
	
}
