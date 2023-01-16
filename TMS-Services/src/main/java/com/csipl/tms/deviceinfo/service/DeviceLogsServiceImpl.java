package com.csipl.tms.deviceinfo.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.service.util.ConverterUtil;
import com.csipl.tms.deviceinfo.repository.DeviceLogsRepository;
import com.csipl.tms.dto.deviceinfo.DeviceLogsInfoDTO;
import com.csipl.tms.model.deviceinfo.DeviceLogsInfo;

@Transactional
@Service("deviceLogsService")
public class DeviceLogsServiceImpl implements DeviceLogsService {

	@Autowired
	DeviceLogsRepository deviceLogsRepository;

	@Override
	public int presentCountAll() {
		// TODO Auto-generated method stub
		return deviceLogsRepository.presentCountAll();
	}

	@Override
	public int presentCountReportingTo(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return deviceLogsRepository.presentCountReportingTo(companyId, employeeId);
	}

	@Override
	public int absentCountAll() {
		// TODO Auto-generated method stub
		return deviceLogsRepository.absentCountAll();
	}

	@Override
	public int absentCountReportingTo(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return deviceLogsRepository.absentCountReportingTo(companyId, employeeId);
	}

	@Override
	public int lateComersAll() {
		// TODO Auto-generated method stub
		return deviceLogsRepository.lateComersAll();
	}

	@Override
	public int lateComersReportingTo(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return deviceLogsRepository.lateComersReportingTo(companyId, employeeId);
	}

	@Override
	public void saveAll(List<DeviceLogsInfo> deviceLogsInfoList) {
		deviceLogsRepository.save(deviceLogsInfoList);

	}

	@Override
	public List<DeviceLogsInfoDTO> getAllLateComersList(Long companyId) {
		List<Object[]> objects = deviceLogsRepository.getAllLateComersList(companyId);
		List<DeviceLogsInfoDTO> deviceLogsInfoDTO = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] report : objects) {

			DeviceLogsInfoDTO dto = new DeviceLogsInfoDTO();
			dto.setEmpName(report[0].toString());
			dto.setEmpCode(report[1].toString());
			dto.setDepartmentName(report[2].toString());
			dto.setPunchRecords(report[3].toString());
			dto.setReportedLateBy(report[4].toString());
			String mode = report[5] != null ? (String) report[5] : null;
			dto.setMode(mode);
			dto.setStatus("Present");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;

	}

	@Override
	public List<DeviceLogsInfoDTO> getAllAbsentList(Long companyId) {
		List<Object[]> objects = deviceLogsRepository.getAllAbsentList(companyId);
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
	public List<DeviceLogsInfoDTO> getAllPresentList(Long companyId) {
		List<Object[]> objects = deviceLogsRepository.getAllPresentList(companyId);
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
			String newReporingTo = reporingTo.substring(0, reporingTo.length() - 6);
			dto.setReportedLateBy(newReporingTo);
			dto.setStatus("Present");
			deviceLogsInfoDTO.add(dto);
		}
		return deviceLogsInfoDTO;

	}

	@Override
	public List<DeviceLogsInfoDTO> getAllAbsentListByDate(Long companyId, Date selectDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = deviceLogsRepository.getAllAbsentListByDate(companyId, date);
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
	public List<DeviceLogsInfoDTO> getAllPresentListByDate(Long companyId, Date selectDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		System.out.println("DeviceLogsServiceImpl.getAllPresentListByDate()" + date);
		List<Object[]> objects = deviceLogsRepository.getAllPresentListByDate(companyId, date);
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
	public List<DeviceLogsInfoDTO> getAllLateComersListByDate(Long companyId, Date selectDate) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(selectDate);
		List<Object[]> objects = deviceLogsRepository.getAllLateComersListByDate(companyId, date);
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
	public void deleteByLogDate() {
		deviceLogsRepository.deleteByLogDate();
	}

	@Override
	public List<DeviceLogsInfoDTO> currentAttendanceReport(Long companyId, Date attendanceDate) {

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(attendanceDate);
		List<Object[]> objects = deviceLogsRepository.currentAttendanceReport(companyId, date);
		List<DeviceLogsInfoDTO> deviceLogsInfoList = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] deviceLogs : objects) {

			String firstName = null;
			String inTime = null, outTime, attDate;
			String leaveStatus;
			DeviceLogsInfoDTO deviceLogDto = new DeviceLogsInfoDTO();
			if (deviceLogs[0] != null) {
				attDate = deviceLogs[0].toString();
				deviceLogDto.setAttedanceDate(attDate);// 2019-10-23
			}
			if (deviceLogs[1] != null) {
				deviceLogDto.setEmployeeCode(deviceLogs[1].toString());// ATM-1087
			}
			if (deviceLogs[2] != null) {
				firstName = deviceLogs[2].toString();
				deviceLogDto.setName(firstName);// Kiyana Kie
			}
			if (deviceLogs[3] != null) {
				deviceLogDto.setDepartmentName(deviceLogs[3].toString());// Operation
			}
			if (deviceLogs[4] != null) {
				deviceLogDto.setDesignationName(deviceLogs[4].toString());// Project Manager
			}
			if (deviceLogs[5] != null) {
				deviceLogDto.setJobLocation(deviceLogs[5].toString());// Indore
			}
			if (deviceLogs[6] != null) {
				deviceLogDto.setReportingTo(deviceLogs[6].toString());// Bharat Patel
			}

			if (deviceLogs[7] != null) {
				deviceLogDto.setShift(deviceLogs[7].toString());// General
			}
			if (deviceLogs[8] != null) {
				deviceLogDto.setShiftDuration(deviceLogs[8].toString());// 10:30-19:00
			}
			if (deviceLogs[9] != null) {
				deviceLogDto.setMode(deviceLogs[9].toString());//
				if (!deviceLogs[9].toString().equalsIgnoreCase("")
						&& !deviceLogs[9].toString().equalsIgnoreCase("NA")) {
					deviceLogDto.setLeaveStatus("Present");
				}
			}

			if (deviceLogs[10] != null) {
				inTime = deviceLogs[10].toString();

				// deviceLogDto.setTimeIn(inTime);
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(inTime);

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					// e.printStackTrace();
				}
				deviceLogDto.setTimeIn(time1);

			}
			if (deviceLogs[11] != null) {
				outTime = deviceLogs[11].toString();

				// deviceLogDto.setTimeOut(outTime);
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(outTime);

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					// e.printStackTrace();
				}
				deviceLogDto.setTimeOut(time2);

			}

			if (deviceLogs[12] != null) {
				deviceLogDto.setTotalHours(deviceLogs[12].toString());
			}
			if (deviceLogs[13] != null) {
				leaveStatus = deviceLogs[13].toString();
				String attendanceDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				if (date.equals(attendanceDate1)) {
					if (leaveStatus.equals("Absent")) {
						deviceLogDto.setLeaveStatus("Under Process");
					} else if (leaveStatus.equals("Holiday")) {
						deviceLogDto.setLeaveStatus("Holiday");
					} else if (leaveStatus.equals("Half Day Leave")) {
						deviceLogDto.setLeaveStatus("Half Day Leave");
					} else if (leaveStatus.equals("Full Day Leave")) {
						deviceLogDto.setLeaveStatus("Full Day Leave");
					} else if (leaveStatus.equals("Present")) {
						deviceLogDto.setLeaveStatus("Present");
					} else if (leaveStatus.equals("Week-Off")) {
						deviceLogDto.setLeaveStatus("Week-Off");
					} else if (leaveStatus.equals("NA")) {
						deviceLogDto.setLeaveStatus("Absent");
					}
				} else
					deviceLogDto.setLeaveStatus(leaveStatus);//
			}

			if (deviceLogs[14] != null) {
				String reporingTo = deviceLogs[14] != null ? (String) deviceLogs[14] : null;
				if (reporingTo.equals("NA")) {
					deviceLogDto.setLateBy(reporingTo);
				} else {
					String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);//
					if (newReporingTo.startsWith("-")) {
						deviceLogDto.setLateBy("On Time");
					} else {

						deviceLogDto.setLateBy(newReporingTo);
					}
				}
			}
			if (deviceLogs[15] != null) {
				String leteReporingTo = deviceLogs[15] != null ? (String) deviceLogs[15] : null;
				if (leteReporingTo.equals("NA")) {
					deviceLogDto.setLateBy(leteReporingTo);
				} else {
					String newLateReporingTo = leteReporingTo.substring(0, leteReporingTo.length() - 7);
					if (newLateReporingTo.startsWith("-")) {
						deviceLogDto.setLeftEarlyBy("On Time");
					} else {
						deviceLogDto.setLeftEarlyBy(newLateReporingTo);//
					}
				}
			}
			if (deviceLogs[16] != null) {
				String earlybefore = deviceLogs[16] != null ? (String) deviceLogs[16] : null;
				if (earlybefore.equals("NA")) {
					deviceLogDto.setLateBy(earlybefore);
				} else {
					String newEarlyBefore = earlybefore.substring(0, earlybefore.length() - 7);//
					if (newEarlyBefore.startsWith("-")) {
						deviceLogDto.setEarlyBefore("On Time");
					} else {
						deviceLogDto.setEarlyBefore(newEarlyBefore);
					}
				}
			}
			if (deviceLogs[17] != null) {
				deviceLogDto.setLocationIn(deviceLogs[17].toString());//
			}
			if (deviceLogs[18] != null) {
				deviceLogDto.setLocationOut(deviceLogs[18].toString());//
			}
			deviceLogsInfoList.add(deviceLogDto);

		}

		return deviceLogsInfoList;

	}

	@Override
	public DeviceLogsInfoDTO punchInOnCDate(String nameOfUser) {
		DeviceLogsInfoDTO deviceLogsInfoDTO = new DeviceLogsInfoDTO();
		DateUtils dateUtil = new DateUtils();
		Date cdate = dateUtil.getCurrentDate();

		List<Object[]> deviceLogsInfoOBJ = deviceLogsRepository.punchInOnCDate(cdate, nameOfUser);
		if (deviceLogsInfoOBJ != null && deviceLogsInfoOBJ.size() > 0) {
			Object[] obj = deviceLogsInfoOBJ.get(0);
			String nameOfUserstr = ConverterUtil.getString(obj[0]);
			String logMinDate = ConverterUtil.getString(obj[1]);
			String mode = ConverterUtil.getString(obj[2]);

			System.out.print("nameOfUserstr " + nameOfUserstr + " logDate " + logMinDate + " mode " + mode);
			deviceLogsInfoDTO.setUserId(nameOfUserstr);
			deviceLogsInfoDTO.setLogMinDate(logMinDate);
			deviceLogsInfoDTO.setMode(mode);
		}

		return deviceLogsInfoDTO;
	}

	@Override
	public List<DeviceLogsInfoDTO> getTeamsAbsentListByDate(Long companyId, Long employeeId, Date selectDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = deviceLogsRepository.getTeamsAbsentListByDate(companyId, employeeId, date);
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
	public List<DeviceLogsInfoDTO> getTeamsPresentListByDate(Long companyId, Long employeeId, Date selectDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(selectDate);
		List<Object[]> objects = deviceLogsRepository.getTeamsPresentListByDate(companyId, employeeId, date);
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
	public List<DeviceLogsInfoDTO> getTeamsLateComersListByDate(Long companyId, Long employeeId, Date selectDate) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(selectDate);
		List<Object[]> objects = deviceLogsRepository.getTeamsLateComersListByDate(companyId, employeeId, date);
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

	// by
	@Override
	public List<DeviceLogsInfoDTO> getTeamsAttendanceReport(Long companyId, Long employeeId, Date attendanceDate) {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(attendanceDate);
		List<Object[]> objects = deviceLogsRepository.teamsAttendanceReport(companyId, employeeId, date);
		List<DeviceLogsInfoDTO> deviceLogsInfoList = new ArrayList<DeviceLogsInfoDTO>();
		for (Object[] deviceLogs : objects) {

			String firstName = null;
			String inTime = null, outTime, attDate;
			String leaveStatus;
			DeviceLogsInfoDTO deviceLogDto = new DeviceLogsInfoDTO();
			if (deviceLogs[0] != null) {
				attDate = deviceLogs[0].toString();
				deviceLogDto.setAttedanceDate(attDate);
			}
			if (deviceLogs[1] != null) {
				deviceLogDto.setEmployeeCode(deviceLogs[1].toString());
			}
			if (deviceLogs[2] != null) {
				firstName = deviceLogs[2].toString();
				deviceLogDto.setName(firstName);
			}
			if (deviceLogs[3] != null) {
				deviceLogDto.setDepartmentName(deviceLogs[3].toString());
			}
			if (deviceLogs[4] != null) {
				deviceLogDto.setDesignationName(deviceLogs[4].toString());
			}
			if (deviceLogs[5] != null) {
				deviceLogDto.setJobLocation(deviceLogs[5].toString());
			}
			if (deviceLogs[6] != null) {
				deviceLogDto.setReportingTo(deviceLogs[6].toString());
			}

			if (deviceLogs[7] != null) {
				deviceLogDto.setShift(deviceLogs[7].toString());
			}
			if (deviceLogs[8] != null) {
				deviceLogDto.setShiftDuration(deviceLogs[8].toString());
			}
			if (deviceLogs[9] != null) {
				deviceLogDto.setMode(deviceLogs[9].toString());
			}

			if (deviceLogs[10] != null) {
				inTime = deviceLogs[10].toString();

				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(inTime);

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (Exception e) {
					e.printStackTrace();
				}
				deviceLogDto.setTimeIn(time1);

			}
			if (deviceLogs[11] != null) {
				outTime = deviceLogs[11].toString();

				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(outTime);

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (Exception e) {
					e.printStackTrace();
				}
				deviceLogDto.setTimeOut(time2);

			}

			if (deviceLogs[12] != null) {
				deviceLogDto.setTotalHours(deviceLogs[12].toString());
			}
			if (deviceLogs[13] != null) {
				leaveStatus = deviceLogs[13].toString();
				String attendanceDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				if (date.equals(attendanceDate1)) {
					if (leaveStatus.equals("Absent")) {
						deviceLogDto.setLeaveStatus("Under Process");
					} else if (leaveStatus.equals("Holiday")) {
						deviceLogDto.setLeaveStatus("Holiday");
					} else if (leaveStatus.equals("Half Day Leave")) {
						deviceLogDto.setLeaveStatus("Half Day Leave");
					} else if (leaveStatus.equals("Full Day Leave")) {
						deviceLogDto.setLeaveStatus("Full Day Leave");
					} else if (leaveStatus.equals("Present")) {
						deviceLogDto.setLeaveStatus("Present");
					} else if (leaveStatus.equals("Week-Off")) {
						deviceLogDto.setLeaveStatus("Week-Off");
					} else if (leaveStatus.equals("NA")) {
						deviceLogDto.setLeaveStatus("Absent");
					}
				} else
					deviceLogDto.setLeaveStatus(leaveStatus);//
			}

			if (deviceLogs[14] != null) {
				String reporingTo = deviceLogs[14] != null ? (String) deviceLogs[14] : null;
				if (reporingTo.equals("NA")) {
					deviceLogDto.setLateBy(reporingTo);
				} else {
					String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);//
					if (newReporingTo.startsWith("-")) {
						deviceLogDto.setLateBy("On Time");
					} else {

						deviceLogDto.setLateBy(newReporingTo);
					}
				}
			}
			if (deviceLogs[15] != null) {
				String leteReporingTo = deviceLogs[15] != null ? (String) deviceLogs[15] : null;
				if (leteReporingTo.equals("NA")) {
					deviceLogDto.setLateBy(leteReporingTo);
				} else {
					String newLateReporingTo = leteReporingTo.substring(0, leteReporingTo.length() - 7);
					if (newLateReporingTo.startsWith("-")) {
						deviceLogDto.setLeftEarlyBy("On Time");
					} else {
						deviceLogDto.setLeftEarlyBy(newLateReporingTo);//
					}
				}
			}
			if (deviceLogs[16] != null) {
				String earlybefore = deviceLogs[16] != null ? (String) deviceLogs[16] : null;
				if (earlybefore.equals("NA")) {
					deviceLogDto.setLateBy(earlybefore);
				} else {
					String newEarlyBefore = earlybefore.substring(0, earlybefore.length() - 7);//
					if (newEarlyBefore.startsWith("-")) {
						deviceLogDto.setEarlyBefore("On Time");
					} else {
						deviceLogDto.setEarlyBefore(newEarlyBefore);
					}
				}
			}
			if (deviceLogs[17] != null) {
				deviceLogDto.setLocationIn(deviceLogs[17].toString());//
			}
			if (deviceLogs[18] != null) {
				deviceLogDto.setLocationOut(deviceLogs[18].toString());//
			}
			deviceLogsInfoList.add(deviceLogDto);

		}

		return deviceLogsInfoList;

	}

}
