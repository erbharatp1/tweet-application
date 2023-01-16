package com.csipl.tms.attendanceregularizationrequest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.attendanceregularizationrequest.repository.ARRequestPagingAndFilterRepository;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;

@Service("aRRequestPagingAndFilterService")
public class ARRequestPagingAndFilterServiceImpl implements ARRequestPagingAndFilterService {

	@Autowired
	ARRequestPagingAndFilterRepository aRRequestPagingAndFilterRepository;

	@Override
	public List<Object[]> getARByPagingAndFilter(Long companyId, Boolean status, SearchDTO searcDto) {
		return aRRequestPagingAndFilterRepository.findARRequestPagedAndFilterResult(companyId, status, searcDto);

	}

	/**
	 * to get List of MassCommunications from database based on companyId
	 */
	@Override
	public List<Object[]> getAllMassCommunications(Long companyId, SearchDTO searcDto) {
		// return massCommunicationRepository.findAllMassCommunications(companyId);

		return aRRequestPagingAndFilterRepository.findAllMassCommunications(companyId, searcDto);
	}

	@Override
	public List<DeviceLogsInfoDTO> getTeamsAbsentListByDate(Long companyId, Long employeeId, Date selectDate,
			SearchDTO searcDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getTeamsAbsentListByDate(companyId, employeeId,
				date, searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());
			dto.setStatus("Absent");
			dto.setPunchRecords("NA");
			dto.setReportedLateBy("NA");
			dto.setMode("NA");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;
	}

	@Override
	public List<DeviceLogsInfoDTO> getTeamsPresentListByDate(Long companyId, Long employeeId, Date selectDate,
			SearchDTO searcDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getTeamsPresentListByDate(companyId, employeeId,
				date, searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());

			dto.setPunchRecords(report[3].toString());
			String mode = report[4] != null ? (String) report[4] : null;
			dto.setMode(mode);
			String reporingTo = report[5] != null ? (String) report[5] : null;
			String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);
			if (newReporingTo.startsWith("-")) {
				dto.setReportedLateBy("On Time");
			} else {
				dto.setReportedLateBy(newReporingTo);
			}
			dto.setStartTime(report[6].toString());
			dto.setStatus("Present");

			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;
	}

	@Override
	public List<DeviceLogsInfoDTO> getTeamsLateComersListByDate(Long companyId, Long employeeId, Date selectDate,
			SearchDTO searcDto) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(selectDate);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getTeamsLateComersListByDate(companyId, employeeId,
				date, searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());
			dto.setPunchRecords(report[3].toString());
			dto.setReportedLateBy(report[4].toString());
			String mode = report[5] != null ? (String) report[5] : null;
			dto.setStartTime(report[6].toString());
			dto.setMode(mode);
			dto.setStatus("Late");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;
	}

	@Override
	public List<DeviceLogsInfoDTO> getAllLateComersListByDate(Long companyId, Date selectDate, SearchDTO searcDto) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(selectDate);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getAllLateComersListByDate(companyId, date,
				searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());
			dto.setPunchRecords(report[3].toString());
			dto.setReportedLateBy(report[4].toString());
			String mode = report[5] != null ? (String) report[5] : null;
			dto.setStartTime(report[6].toString());
			dto.setMode(mode);
			dto.setStatus("Late");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;

	}

	@Override
	public List<DeviceLogsInfoDTO> getAllAbsentListByDate(Long companyId, Date selectDate, SearchDTO searcDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getAllAbsentListByDate(companyId, date, searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());
			dto.setStatus("Absent");
			dto.setPunchRecords("NA");
			dto.setReportedLateBy("NA");
			dto.setMode("NA");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;

	}

	@Override
	public List<DeviceLogsInfoDTO> getAllPresentListByDate(Long companyId, Date selectDate, SearchDTO searcDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		System.out.println("DeviceLogsServiceImpl.getAllPresentListByDate()" + date);
		List<Object[]> objects = aRRequestPagingAndFilterRepository.getAllPresentListByDate(companyId, date, searcDto);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());

			dto.setPunchRecords(report[3].toString());
			String mode = report[4] != null ? (String) report[4] : null;
			dto.setMode(mode);
			String reporingTo = report[5] != null ? (String) report[5] : null;
			String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);
			if (newReporingTo.startsWith("-")) {
				dto.setReportedLateBy("On Time");
			} else {
				dto.setReportedLateBy(newReporingTo);
			}
			dto.setStartTime(report[6].toString());
			dto.setStatus("Present");

			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;

	}

}
