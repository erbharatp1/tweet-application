package com.csipl.tms.leave.report.adaptor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.hrms.service.util.ConverterUtil;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.leave.CompensatoryOffDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.TMSLeavePeriodDTO;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.service.Adaptor;

public class LeaveAttendanceReportAdapator implements Adaptor<TMSLeavePeriodDTO, TMSLeavePeriod> {

	@Override
	public List<TMSLeavePeriod> uiDtoToDatabaseModelList(List<TMSLeavePeriodDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TMSLeavePeriodDTO> databaseModelToUiDtoList(List<TMSLeavePeriod> leavePeriodList) {
		List<TMSLeavePeriodDTO> leavePeriodDtoList = new ArrayList<TMSLeavePeriodDTO>();
		for (TMSLeavePeriod leavePeriod : leavePeriodList) {
			leavePeriodDtoList.add(databaseModelToUiDto(leavePeriod));
		}
		return leavePeriodDtoList;
	}

	@Override
	public TMSLeavePeriod uiDtoToDatabaseModel(TMSLeavePeriodDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TMSLeavePeriodDTO databaseModelToUiDto(TMSLeavePeriod leavePeriod) {
		TMSLeavePeriodDTO leavePeriodDTO = new TMSLeavePeriodDTO();

		leavePeriodDTO.setLeavePeriodId(leavePeriod.getLeavePeriodId());
		leavePeriodDTO.setDateCreated(leavePeriod.getDateCreated());
		leavePeriodDTO.setDateUpdate(leavePeriod.getDateUpdate());
		leavePeriodDTO.setActiveStatus(leavePeriod.getActiveStatus());
		leavePeriodDTO.setEndDate(leavePeriod.getEndDate());
		leavePeriodDTO.setStartDate(leavePeriod.getStartDate());
		leavePeriodDTO.setLeavePeriodName(DateUtils.getMonthToYear(leavePeriod.getLeavePeriodName()));
		leavePeriodDTO.setUserId(leavePeriod.getUserId());
		leavePeriodDTO.setUserIdUpdate(leavePeriod.getUserIdUpdate());
		leavePeriodDTO.setCompanyId(leavePeriod.getCompanyId());
		leavePeriodDTO.setSession(leavePeriod.getLeavePeriodName());

		return leavePeriodDTO;
	}

	public List<AttendanceDTO> objectListToLeaveEntitlementReport(List<Object[]> attendanceList) {
		// TODO Auto-generated method stub

		List<AttendanceDTO> attendanceDtoList = new ArrayList<>();
		for (Object[] attendanceObj : attendanceList) {

			AttendanceDTO attendanceDTO = new AttendanceDTO();

			String employeeCode = attendanceObj[0] != null ? (String) attendanceObj[0] : null;
			String empName = attendanceObj[1] != null ? (String) attendanceObj[1] : null;
			String departmentName = attendanceObj[2] != null ? (String) attendanceObj[2] : null;
			String designationName = attendanceObj[3] != null ? (String) attendanceObj[3] : null;
			String jobLocation = attendanceObj[4] != null ? (String) attendanceObj[4] : null;
			Date dateOfJoining = attendanceObj[5] != null ? (Date) attendanceObj[5] : null;
			String reportingManager = attendanceObj[6] != null ? (String) attendanceObj[6] : null;
			String leaveScheme = attendanceObj[7] != null ? (String) attendanceObj[7] : null;
			Long employeeId = attendanceObj[8] != null ? Long.parseLong(attendanceObj[8].toString()) : null;
			String status = attendanceObj[9] != null ? (String) attendanceObj[9] : null;

			attendanceDTO.setEmployeeCode(employeeCode);
			attendanceDTO.setEmployeeName(empName);
			attendanceDTO.setDepartment(departmentName);
			attendanceDTO.setDesignation(designationName);
			attendanceDTO.setJobLocation(jobLocation);
			attendanceDTO.setDateOfJoining(dateOfJoining);
			attendanceDTO.setReportingTo(reportingManager);
			attendanceDTO.setLeaveScheme(leaveScheme);
			attendanceDTO.setEmployeeId(employeeId);
			attendanceDTO.setStatus(status);
			attendanceDtoList.add(attendanceDTO);

		}

		return attendanceDtoList;
	}

	public List<LeaveEntryDTO> objectListToLeaveRequestReport(List<Object[]> leaveRequestList) {
		// TODO Auto-generated method stub

		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();

		for (Object[] leaveRequestObj : leaveRequestList) {
			LeaveEntryDTO leaveEntryDTO = new LeaveEntryDTO();

			String employeeCode = leaveRequestObj[0] != null ? (String) leaveRequestObj[0] : null;
			String employee = leaveRequestObj[1] != null ? (String) leaveRequestObj[1] : null;
			String departmentName = leaveRequestObj[2] != null ? (String) leaveRequestObj[2] : null;
			String designation = leaveRequestObj[3] != null ? (String) leaveRequestObj[3] : null;
			String city = leaveRequestObj[4] != null ? (String) leaveRequestObj[4] : null;
			String reportingManager = leaveRequestObj[5] != null ? (String) leaveRequestObj[5] : null;
			Date dateCreated = leaveRequestObj[6] != null ? (Date) leaveRequestObj[6] : null;
			String leaveType = leaveRequestObj[7] != null ? (String) leaveRequestObj[7] : null;
			Date fromDate = leaveRequestObj[8] != null ? (Date) leaveRequestObj[8] : null;
			Date toDate = leaveRequestObj[9] != null ? (Date) leaveRequestObj[9] : null;
			BigDecimal days = leaveRequestObj[10] != null ? (new BigDecimal(leaveRequestObj[10].toString())) : null;
			String employeeRemark = leaveRequestObj[11] != null ? (String) leaveRequestObj[11] : null;
			String status = leaveRequestObj[12] != null ? (String) leaveRequestObj[12] : null;
			String actionTakenBy = leaveRequestObj[13] != null ? (String) leaveRequestObj[13] : null;
			Date actionableDate = leaveRequestObj[14] != null ? (Date) leaveRequestObj[14] : null;
			String approvalRemark = leaveRequestObj[15] != null ? (String) leaveRequestObj[15] : null;

			leaveEntryDTO.setEmployeeCode(employeeCode);
			leaveEntryDTO.setEmployeeName(employee);
			leaveEntryDTO.setDepartment(departmentName);
			leaveEntryDTO.setDesignation(designation);
			leaveEntryDTO.setJobLocation(city);
			leaveEntryDTO.setReportingManager(reportingManager);
			leaveEntryDTO.setDateCreated(dateCreated);

//			if (half_fullDay != null) {
//				if (half_fullDay.equals("F")) {
//					leaveEntryDTO.setHalf_fullDay("Full Day");
//				} else if (half_fullDay.equals("H")) {
//					leaveEntryDTO.setHalf_fullDay("Half day");
//				}
//			}
			leaveEntryDTO.setLeaveType(leaveType);
			leaveEntryDTO.setFromDate(fromDate);
			leaveEntryDTO.setToDate(toDate);
			leaveEntryDTO.setDays(days);
			leaveEntryDTO.setEmployeeRemark(employeeRemark);

			String requestStatus = null;
			if (status.equalsIgnoreCase(StatusMessage.APPROVED_CODE)) {
				requestStatus = StatusMessage.APPROVED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.REJECTED_CODE)) {
				requestStatus = StatusMessage.REJECTED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.CANCEL_CODE)) {
				requestStatus = StatusMessage.CANCEL_VALUE;
			} else {
				requestStatus = StatusMessage.PENDING_VALUE;
			}

			leaveEntryDTO.setStatus(requestStatus);
			leaveEntryDTO.setActionTakenBy(actionTakenBy);
			leaveEntryDTO.setActionableDate(actionableDate);
			leaveEntryDTO.setApprovalRemark(approvalRemark);

			leaveEntryDtoList.add(leaveEntryDTO);

		}
		return leaveEntryDtoList;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	public List<CompensatoryOffDTO> objectListToCompOffReqSummaryReport(List<Object[]> compOffReqSummaryObjList) {

		List<CompensatoryOffDTO> compensatoryOffDTOList = new ArrayList<CompensatoryOffDTO>();
		StatusMessage sm = new StatusMessage();

		for (Object[] report : compOffReqSummaryObjList) {

			CompensatoryOffDTO compensatoryOffDTO = new CompensatoryOffDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String departmentName = report[2] != null ? (String) report[2] : null;
			String designationName = report[3] != null ? (String) report[3] : null;
			String jobLocation = report[4] != null ? (String) report[4] : null;
			String reportingManager = report[5] != null ? (String) report[5] : null;
			Date requestedOn = report[6] != null ? (Date) report[6] : null;
			Date fromeDate = report[7] != null ? (Date) report[7] : null;
			Date toDate = report[8] != null ? (Date) report[8] : null;
			BigDecimal days = report[9] != null ? (BigDecimal) report[9] : null;
			String requesterRemark = report[10] != null ? (String) report[10] : null;
			String status = report[11] != null ? (String) report[11] : null;
			String actionTakenBy = report[12] != null ? (String) report[12] : null;
			Date actionTakenOn = report[13] != null ? (Date) report[13] : null;
			String actionerRemark = report[14] != null ? (String) report[14] : null;

			compensatoryOffDTO.setEmployeeCode(employeeCode);
			compensatoryOffDTO.setEmployeeName(employee);
			compensatoryOffDTO.setDepartment(departmentName);
			compensatoryOffDTO.setDesignation(designationName);
			compensatoryOffDTO.setJobLocation(jobLocation);
			compensatoryOffDTO.setReportingManager(reportingManager);
			compensatoryOffDTO.setRequestedOn(requestedOn);
			compensatoryOffDTO.setFromDate(fromeDate);
			compensatoryOffDTO.setToDate(toDate);
			compensatoryOffDTO.setDay(days.toString());
			compensatoryOffDTO.setRequesterRemark(requesterRemark);

			String compStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.APPROVED_CODE)) {
				compStatus = StatusMessage.APPROVED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.REJECTED_CODE)) {
				compStatus = StatusMessage.REJECTED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.CANCEL_CODE)) {
				compStatus = StatusMessage.CANCEL_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.PENDING_CODE)) {
				compStatus = StatusMessage.PENDING_VALUE;
			}
			compensatoryOffDTO.setStatus(compStatus);
			compensatoryOffDTO.setActionTakenBy(actionTakenBy);
			compensatoryOffDTO.setActionTakenOn(actionTakenOn);
			compensatoryOffDTO.setActionerRemark(actionerRemark);

			compensatoryOffDTOList.add(compensatoryOffDTO);
		}
		return compensatoryOffDTOList;
	}

	public List<AttendanceLogDTO> objectListToEarlyComersReport(List<Object[]> earlyComersObj) {

		List<AttendanceLogDTO> empLateComerDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : earlyComersObj) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attDate = report[0] != null ? ConverterUtil.getDate(report[0].toString()) : null;
			String empCode = report[1] != null ? (String) report[1] : null;
			String empName = report[2] != null ? (String) report[2] : null;
			String deptName = report[3] != null ? (String) report[3] : null;
			String desName = report[4] != null ? (String) report[4] : null;
			String jobLocationName = report[5] != null ? (String) report[5] : null;
			String repManagerName = report[6] != null ? (String) report[6] : null;
			String shift = report[7] != null ? (String) report[7] : null;
			String shiftDur = report[8] != null ? (String) report[8] : null;
			String timeIn = report[9] != null ? (String) report[9] : null;
			String timeOut = report[10] != null ? (String) report[10] : null;
			String totalTime = report[11] != null ? (String) report[11] : null;
			String earlyBefore = report[12].toString() != null ? report[12].toString() : null;
			String status = report[13] != null ? (String) report[13] : null;

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setEmployeeCode(empCode);
			attendanceLogDTO.setEmployeeName(empName);
			attendanceLogDTO.setDepartmentName(deptName);
			attendanceLogDTO.setDesignationName(desName);
			attendanceLogDTO.setJobLocation(jobLocationName);
			attendanceLogDTO.setReportingTo(repManagerName);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDur);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date3 = sdf.parse(timeIn);
				// new format
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time1 = sdf2.format(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date = sd.parse(timeOut);
				// new format
				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time2 = sd2.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setOutTime(time2);

			attendanceLogDTO.setTotalTime(totalTime);
			attendanceLogDTO.setEarlyTime(earlyBefore);

			String earlyStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				earlyStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				earlyStatus = StatusMessage.HALFDAY_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.ABSENT_CODE)) {
				earlyStatus = StatusMessage.ABSENT_VALUE;
			}

			attendanceLogDTO.setStatus(earlyStatus);

			empLateComerDTOList.add(attendanceLogDTO);
		}

		return empLateComerDTOList;
	}

	public List<AttendanceLogDTO> objectListToWorkedOnHolidaysReport(List<Object[]> workedOnHolidaysObj) {

		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : workedOnHolidaysObj) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attDate = report[0] != null ? ConverterUtil.getDate(report[0].toString()) : null;
			String empCode = report[1] != null ? (String) report[1] : null;
			String empName = report[2] != null ? (String) report[2] : null;
			String deptName = report[3] != null ? (String) report[3] : null;
			String desName = report[4] != null ? (String) report[4] : null;
			String jobLocation = report[5] != null ? (String) report[5] : null;
			String repManager = report[6] != null ? (String) report[6] : null;
			String shift = report[7] != null ? (String) report[7] : null;
			String shiftDur = report[8] != null ? (String) report[8] : null;
			String timeIn = report[9] != null ? (String) report[9] : null;
			String timeOut = report[10] != null ? (String) report[10] : null;
			String totalHr = report[11] != null ? (String) report[11] : null;
			String status = report[12] != null ? (String) report[12] : null;

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setEmployeeCode(empCode);
			attendanceLogDTO.setEmployeeName(empName);
			attendanceLogDTO.setDepartmentName(deptName);
			attendanceLogDTO.setDesignationName(desName);
			attendanceLogDTO.setJobLocation(jobLocation);
			attendanceLogDTO.setReportingTo(repManager);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDur);

			if (timeIn != null && !timeIn.equalsIgnoreCase("")) {
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date3 = sdf.parse(timeIn);
					// new format
					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
					// formatting the given time to new format with AM/PM
					time1 = sdf2.format(date3);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDTO.setInTime(time1);
			} else {
				attendanceLogDTO.setInTime("NA");
			}
			if (timeOut != null && !timeOut.equalsIgnoreCase("")) {
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date = sd.parse(timeOut);
					// new format
					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
					// formatting the given time to new format with AM/PM
					time2 = sd2.format(date);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDTO.setOutTime(time2);
			} else {
				attendanceLogDTO.setOutTime("NA");
			}
			attendanceLogDTO.setTotalTime(totalHr);

			String holidayStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				holidayStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				holidayStatus = StatusMessage.HALFDAY_VALUE;
			}
			attendanceLogDTO.setStatus(holidayStatus);

			attendanceLogDTOList.add(attendanceLogDTO);
		}

		return attendanceLogDTOList;
	}

	public List<AttendanceLogDTO> objectListToWorkedOnWeekOffReport(List<Object[]> workedOnWeekOffObj) {

		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : workedOnWeekOffObj) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			StatusMessage sm = new StatusMessage();

			Date attDate = report[0] != null ? ConverterUtil.getDate(report[0].toString()) : null;
			String attDay = report[1] != null ? (String) report[1] : null;
			String empCode = report[2] != null ? (String) report[2] : null;
			String empName = report[3] != null ? (String) report[3] : null;
			String deptName = report[4] != null ? (String) report[4] : null;
			String desName = report[5] != null ? (String) report[5] : null;
			String jobLocation = report[6] != null ? (String) report[6] : null;
			String repManager = report[7] != null ? (String) report[7] : null;
			String shift = report[8] != null ? (String) report[8] : null;
			String shiftDur = report[9] != null ? (String) report[9] : null;
			String timeIn = report[10] != null ? (String) report[10] : null;
			String timeOut = report[11] != null ? (String) report[11] : null;
			String totalHr = report[12] != null ? (String) report[12] : null;
			String status = report[13] != null ? (String) report[13] : null;
			Integer patId = report[14] != null ? (Integer) report[14] : null;
			String day = report[15] != null ? (String) report[15] : null;

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setAttDay(attDay);
			attendanceLogDTO.setEmployeeCode(empCode);
			attendanceLogDTO.setEmployeeName(empName);
			attendanceLogDTO.setDepartmentName(deptName);
			attendanceLogDTO.setDesignationName(desName);
			attendanceLogDTO.setJobLocation(jobLocation);
			attendanceLogDTO.setReportingTo(repManager);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDur);

			if (timeIn != null && !("").equals(timeIn)) {
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date3 = sdf.parse(timeIn);
					// new format
					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
					// formatting the given time to new format with AM/PM
					time1 = sdf2.format(date3);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDTO.setInTime(time1);
			}
			attendanceLogDTO.setInTime("NA");

			if (timeOut != null && !timeOut.equals("")) {
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date = sd.parse(timeOut);
					// new format
					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
					// formatting the given time to new format with AM/PM
					time2 = sd2.format(date);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDTO.setOutTime(time2);
			}
			attendanceLogDTO.setOutTime("NA");
			attendanceLogDTO.setTotalTime(totalHr);

			String holidayStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				holidayStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				holidayStatus = StatusMessage.HALFDAY_VALUE;
			}
			attendanceLogDTO.setStatus(holidayStatus);
			attendanceLogDTO.setPatternId(patId);
			attendanceLogDTO.setDay(day);

			String pday = attendanceLogDTO.getDay();

			String[] patDay = pday.split(",");

			List<String> list = new ArrayList<String>();

			list = Arrays.asList(patDay);

			for (int i = 0; i < list.size(); i++) {

				String patternDay = list.get(i);

				if (patternDay.equals(attendanceLogDTO.getAttDay())) {
					attendanceLogDTOList.add(attendanceLogDTO);
				}
			}

		}
		return attendanceLogDTOList;
	}

	public List<AttendanceLogDTO> objectListToEarlyLeaversList(List<Object[]> earlyLeaversObjectList) {

		List<AttendanceLogDTO> empEarlyLeaversDTOList = new ArrayList<AttendanceLogDTO>();
		for (Object[] report : earlyLeaversObjectList) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attDate = report[0] != null ? (Date) report[0] : null;
			String employeeCode = report[1] != null ? (String) report[1] : null;
			String employeeName = report[2] != null ? (String) report[2] : null;
			String department = report[3] != null ? (String) report[3] : null;
			String designation = report[4] != null ? (String) report[4] : null;
			String city = report[5] != null ? (String) report[5] : null;
			String reportingTo = report[6] != null ? (String) report[6] : null;
			String shift = report[7] != null ? (String) report[7] : null;
			String shiftDuration = report[8] != null ? (String) report[8] : null;
			String timeIn = report[9] != null ? (String) report[9] : null;
			String timeOut = report[10] != null ? (String) report[10] : null;
			String totalTime = report[11] != null ? (String) report[11] : null;
			String earlyBy = report[12] != null ? report[12].toString() : null;
			String status = report[13] != null ? (String) report[13] : null;

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setEmployeeCode(employeeCode);
			attendanceLogDTO.setEmployeeName(employeeName);
			attendanceLogDTO.setDepartmentName(department);
			attendanceLogDTO.setDesignationName(designation);
			attendanceLogDTO.setJobLocation(city);
			attendanceLogDTO.setReportingTo(reportingTo);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDuration);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date3 = sdf.parse(timeIn);
				// new format
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time1 = sdf2.format(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date = sd.parse(timeOut);
				// new format
				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time2 = sd2.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setOutTime(time2);

			attendanceLogDTO.setTotalTime(totalTime);
			attendanceLogDTO.setEarlyTime(earlyBy);

			String earlyStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				earlyStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				earlyStatus = StatusMessage.HALFDAY_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.ABSENT_CODE)) {
				earlyStatus = StatusMessage.ABSENT_VALUE;
			}

			attendanceLogDTO.setStatus(earlyStatus);

			empEarlyLeaversDTOList.add(attendanceLogDTO);
		}
		return empEarlyLeaversDTOList;

	}

	public List<AttendanceRegularizationRequestDTO> objectListToAttendanceRegularizationRequestDtoList(
			List<Object[]> arRequestObjectList) {

		List<AttendanceRegularizationRequestDTO> attendanceRegularizationRequestDTOList = new ArrayList<AttendanceRegularizationRequestDTO>();

		for (Object[] report : arRequestObjectList) {
			AttendanceRegularizationRequestDTO attendanceRegularizationRequestDTO = new AttendanceRegularizationRequestDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employee = report[1] != null ? (String) report[1] : null;
			String departmentName = report[2] != null ? (String) report[2] : null;
			String designation = report[3] != null ? (String) report[3] : null;
			String city = report[4] != null ? (String) report[4] : null;
			String reportingManager = report[5] != null ? (String) report[5] : null;
			Date dateCreated = report[6] != null ? (Date) report[6] : null;
			String arCategory = report[7] != null ? (String) report[7] : null;
			Date fromDate = report[8] != null ? (Date) report[8] : null;
			Date toDate = report[9] != null ? (Date) report[9] : null;
			Integer days = report[10] != null ? (Integer) report[10] : null;
			String employeeRemark = report[11] != null ? (String) report[11] : null;
			String status = report[12] != null ? (String) report[12] : null;
			String actionTakenBy = report[13] != null ? (String) report[13] : null;
			Date actionableDate = report[14] != null ? (Date) report[14] : null;
			String approvalRemark = report[15] != null ? (String) report[15] : null;

			attendanceRegularizationRequestDTO.setEmployeeCode(employeeCode);
			attendanceRegularizationRequestDTO.setEmployeeName(employee);
			attendanceRegularizationRequestDTO.setEmployeeDepartment(departmentName);
			attendanceRegularizationRequestDTO.setEmployeeDesignation(designation);
			attendanceRegularizationRequestDTO.setCityName(city);
			attendanceRegularizationRequestDTO.setReportingManagerName(reportingManager);
			attendanceRegularizationRequestDTO.setDateCreated(dateCreated);

			String reason = null;
			if (arCategory.equalsIgnoreCase(StatusMessage.AR_MISS_PUNCH_CODE)) {
				reason = StatusMessage.AR_MISS_PUNCH_VALUE;
			} else if (arCategory.equalsIgnoreCase(StatusMessage.AR_CORD_NOT_WORKING_CODE)) {
				reason = StatusMessage.AR_CORD_NOT_WORKING_VALUE;
			} else if (arCategory.equalsIgnoreCase(StatusMessage.AR_POWER_CUT_CODE)) {
				reason = StatusMessage.AR_POWER_CUT_VALUE;
			} else {
				reason = StatusMessage.AR_MECHINE_NOT_WORKING_VALUE;
			}

			attendanceRegularizationRequestDTO.setArCategory(reason);
			attendanceRegularizationRequestDTO.setFromDate(fromDate);
			attendanceRegularizationRequestDTO.setToDate(toDate);
			attendanceRegularizationRequestDTO.setDay(days);
			attendanceRegularizationRequestDTO.setEmployeeRemark(employeeRemark);

			String requestStatus = null;
			if (status.equalsIgnoreCase(StatusMessage.APPROVED_CODE)) {
				requestStatus = StatusMessage.APPROVED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.REJECTED_CODE)) {
				requestStatus = StatusMessage.REJECTED_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.CANCEL_CODE)) {
				requestStatus = StatusMessage.CANCEL_VALUE;
			} else {
				requestStatus = StatusMessage.PENDING_VALUE;
			}

			attendanceRegularizationRequestDTO.setStatus(requestStatus);
			attendanceRegularizationRequestDTO.setActionTakenBy(actionTakenBy);
			attendanceRegularizationRequestDTO.setActionableDate(actionableDate);
			attendanceRegularizationRequestDTO.setApprovalRemark(approvalRemark);

			attendanceRegularizationRequestDTOList.add(attendanceRegularizationRequestDTO);
		}
		return attendanceRegularizationRequestDTOList;

	}

	public List<AttendanceLogDTO> objectListToOverTimeDayWiseList(List<Object[]> otDetailsObjectList) {

		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : otDetailsObjectList) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attDate = report[0] != null ? (Date) report[0] : null;
			String employeeCode = report[1] != null ? (String) report[1] : null;
			String employeeName = report[2] != null ? (String) report[2] : null;
			String department = report[3] != null ? (String) report[3] : null;
			String designation = report[4] != null ? (String) report[4] : null;
			String city = report[5] != null ? (String) report[5] : null;
			String reportingTo = report[6] != null ? (String) report[6] : null;
			String shift = report[7] != null ? (String) report[7] : null;
			String shiftDuration = report[8] != null ? (String) report[8] : null;
			String timeIn = report[9] != null ? (String) report[9] : null;
			String timeOut = report[10] != null ? (String) report[10] : null;
			String totalHours = report[11] != null ? report[11].toString() : null;
			String overTime = report[12] != null ? report[12].toString() : null;

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setEmployeeCode(employeeCode);
			attendanceLogDTO.setEmployeeName(employeeName);
			attendanceLogDTO.setDepartmentName(department);
			attendanceLogDTO.setDesignationName(designation);
			attendanceLogDTO.setJobLocation(city);
			attendanceLogDTO.setReportingTo(reportingTo);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDuration);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date3 = sdf.parse(timeIn);
				// new format
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time1 = sdf2.format(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date = sd.parse(timeOut);
				// new format
				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time2 = sd2.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setOutTime(time2);

			attendanceLogDTO.setTotalTime(totalHours);
			attendanceLogDTO.setOverTime(overTime);

			attendanceLogDTOList.add(attendanceLogDTO);
		}
		return attendanceLogDTOList;

	}

	public List<AttendanceLogDTO> objectListToOverTimeMonthWiseList(List<Object[]> otDetailsObjectList) {

		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : otDetailsObjectList) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			String employeeCode = report[0] != null ? (String) report[0] : null;
			String employeeName = report[1] != null ? (String) report[1] : null;
			String department = report[2] != null ? (String) report[2] : null;
			String designation = report[3] != null ? (String) report[3] : null;
			String city = report[4] != null ? (String) report[4] : null;
			String reportingTo = report[5] != null ? (String) report[5] : null;
			String shift = report[6] != null ? (String) report[6] : null;
			String shiftDuration = report[7] != null ? (String) report[7] : null;
			Integer totalHr = report[8] != null ? ((BigDecimal) report[8]).intValue() : null;
			Integer totalMin = report[9] != null ? ((BigDecimal) report[9]).intValue() : null;
			Integer totalSec = report[10] != null ? ((BigDecimal) report[10]).intValue() : null;
			BigInteger otHr = report[11] != null ? (new BigInteger(report[11].toString())) : null;
			BigInteger otMin = report[12] != null ? (new BigInteger(report[12].toString())) : null;
			BigInteger otSec = report[13] != null ? (new BigInteger(report[13].toString())) : null;

			// Total Hours

			if (totalSec >= 60) {
				totalMin = totalMin + (totalSec / 60);
				totalSec = totalSec % 60;

				if (totalMin >= 60) {
					totalHr = totalHr + (totalMin / 60);
					totalMin = totalMin % 60;
					attendanceLogDTO.setTotalTime(
							(totalHr.toString().length() < 2 ? ("0" + totalHr.toString()) : totalHr.toString()) + ":"
									+ (totalMin.toString().length() < 2
											? ("0" + totalMin.toString())
											: totalMin.toString())
									+ ":" + (totalSec.toString().length() < 2 ? ("0" + totalSec.toString())
											: totalSec.toString()));

				} else {
					attendanceLogDTO.setTotalTime(
							(totalHr.toString().length() < 2 ? ("0" + totalHr.toString()) : totalHr.toString()) + ":"
									+ (totalMin.toString().length() < 2
											? ("0" + totalMin.toString())
											: totalMin.toString())
									+ ":" + (totalSec.toString().length() < 2 ? ("0" + totalSec.toString())
											: totalSec.toString()));
				}

			} else {

				if (totalMin >= 60) {
					totalHr = totalHr + (totalMin / 60);
					totalMin = totalMin % 60;
					attendanceLogDTO.setTotalTime(
							(totalHr.toString().length() < 2 ? ("0" + totalHr.toString()) : totalHr.toString()) + ":"
									+ (totalMin.toString().length() < 2
											? ("0" + totalMin.toString())
											: totalMin.toString())
									+ ":" + (totalSec.toString().length() < 2 ? ("0" + totalSec.toString())
											: totalSec.toString()));

				} else {
					attendanceLogDTO.setTotalTime(
							(totalHr.toString().length() < 2 ? ("0" + totalHr.toString()) : totalHr.toString()) + ":"
									+ (totalMin.toString().length() < 2
											? ("0" + totalMin.toString())
											: totalMin.toString())
									+ ":" + (totalSec.toString().length() < 2 ? ("0" + totalSec.toString())
											: totalSec.toString()));
				}
			}

			// Over Time

			BigInteger sec = new BigInteger("60");

			if (otSec.compareTo(sec) == 1) {
				otMin = otMin.add(otSec.divide(sec));
				otSec = otSec.remainder(sec);

				if (otMin.compareTo(sec) == 1) {
					otHr = otHr.add(otMin.divide(sec));
					otMin = otMin.remainder(sec);
					attendanceLogDTO.setOverTime(
							(otHr.toString().length() < 2 ? ("0" + otHr.toString()) : otHr.toString()) + ":"
									+ (otMin.toString().length() < 2 ? ("0" + otMin.toString()) : otMin.toString())
									+ ":"
									+ (otSec.toString().length() < 2 ? ("0" + otSec.toString()) : otSec.toString()));
				} else {

					attendanceLogDTO.setOverTime(
							(otHr.toString().length() < 2 ? ("0" + otHr.toString()) : otHr.toString()) + ":"
									+ (otMin.toString().length() < 2 ? ("0" + otMin.toString()) : otMin.toString())
									+ ":"
									+ (otSec.toString().length() < 2 ? ("0" + otSec.toString()) : otSec.toString()));

				}

			} else {

				if (otMin.compareTo(sec) == 1) {
					otHr = otHr.add(otMin.divide(sec));
					otMin = otMin.remainder(sec);
					attendanceLogDTO.setOverTime(
							(otHr.toString().length() < 2 ? ("0" + otHr.toString()) : otHr.toString()) + ":"
									+ (otMin.toString().length() < 2 ? ("0" + otMin.toString()) : otMin.toString())
									+ ":"
									+ (otSec.toString().length() < 2 ? ("0" + otSec.toString()) : otSec.toString()));

				} else {

					attendanceLogDTO.setOverTime(
							(otHr.toString().length() < 2 ? ("0" + otHr.toString()) : otHr.toString()) + ":"
									+ (otMin.toString().length() < 2 ? ("0" + otMin.toString()) : otMin.toString())
									+ ":"
									+ (otSec.toString().length() < 2 ? ("0" + otSec.toString()) : otSec.toString()));

				}

			}

			attendanceLogDTO.setEmployeeCode(employeeCode);
			attendanceLogDTO.setEmployeeName(employeeName);
			attendanceLogDTO.setDepartmentName(department);
			attendanceLogDTO.setDesignationName(designation);
			attendanceLogDTO.setJobLocation(city);
			attendanceLogDTO.setReportingTo(reportingTo);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDuration);

			attendanceLogDTOList.add(attendanceLogDTO);
		}
		return attendanceLogDTOList;
	}

	public List<AttendanceLogDTO> objectListToMissingPunchRecordList(List<Object[]> missingPunchRecordList) {
		// TODO Auto-generated method stub
		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] attendanceLogObj : missingPunchRecordList) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attendanceDate = attendanceLogObj[0] != null ? (Date) attendanceLogObj[0] : null;
			String employeeCode = attendanceLogObj[1] != null ? (String) attendanceLogObj[1] : null;
			String employee = attendanceLogObj[2] != null ? (String) attendanceLogObj[2] : null;
			String departmentName = attendanceLogObj[3] != null ? (String) attendanceLogObj[3] : null;
			String designation = attendanceLogObj[4] != null ? (String) attendanceLogObj[4] : null;
			String city = attendanceLogObj[5] != null ? (String) attendanceLogObj[5] : null;
			String reportingManager = attendanceLogObj[6] != null ? (String) attendanceLogObj[6] : null;
			String shiftName = attendanceLogObj[7] != null ? (String) attendanceLogObj[7] : null;
			String shiftDuration = attendanceLogObj[8] != null ? (String) attendanceLogObj[8] : null;
			String inTime = attendanceLogObj[9] != null ? (String) attendanceLogObj[9] : null;
			String status = attendanceLogObj[10] != null ? (String) attendanceLogObj[10] : null;

			attendanceLogDTO.setAttendanceDate(attendanceDate);
			attendanceLogDTO.setEmployeeCode(employeeCode);
			attendanceLogDTO.setEmployeeName(employee);
			attendanceLogDTO.setDepartmentName(departmentName);
			attendanceLogDTO.setDesignationName(designation);
			attendanceLogDTO.setJobLocation(city);
			attendanceLogDTO.setReportingTo(reportingManager);
			attendanceLogDTO.setShiftName(shiftName);
			attendanceLogDTO.setShiftDuration(shiftDuration);

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			if(inTime!=null) {
				try {
					Date date3 = sdf.parse(inTime);
					// new format
					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
					// formatting the given time to new format with AM/PM
					time1 = sdf2.format(date3);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDTO.setInTime(time1);
			}
			

			String employeeStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				employeeStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				employeeStatus = StatusMessage.HALFDAY_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.ABSENT_CODE)) {
				employeeStatus = StatusMessage.ABSENT_VALUE;
			}

			attendanceLogDTO.setStatus(employeeStatus);

			attendanceLogDTOList.add(attendanceLogDTO);

		}

		return attendanceLogDTOList;
	}

	public List<AttendanceDTO> objectListToLeaveEncashedReport(List<Object[]> attendanceList) {
		List<AttendanceDTO> attendanceDtoList = new ArrayList<>();
		for (Object[] attendanceObj : attendanceList) {

			AttendanceDTO attendanceDTO = new AttendanceDTO();

			String employeeCode = attendanceObj[0] != null ? (String) attendanceObj[0] : null;
			String empName = attendanceObj[1] != null ? (String) attendanceObj[1] : null;
			String departmentName = attendanceObj[2] != null ? (String) attendanceObj[2] : null;
			String designationName = attendanceObj[3] != null ? (String) attendanceObj[3] : null;
			String jobLocation = attendanceObj[4] != null ? (String) attendanceObj[4] : null;
			String reportingManager = attendanceObj[5] != null ? (String) attendanceObj[5] : null;
			Long employeeId = attendanceObj[6] != null ? Long.parseLong(attendanceObj[6].toString()) : null;

			attendanceDTO.setEmployeeCode(employeeCode);
			attendanceDTO.setEmployeeName(empName);
			attendanceDTO.setDepartment(departmentName);
			attendanceDTO.setDesignation(designationName);
			attendanceDTO.setJobLocation(jobLocation);
			attendanceDTO.setReportingTo(reportingManager);
			attendanceDTO.setEmployeeId(employeeId);
			attendanceDtoList.add(attendanceDTO);

		}

		return attendanceDtoList;
	}

	public List<AttendanceLogDTO> objectListToAttendanceLogsSumReport(List<Object[]> attendanceLogsSumObj) {

		List<AttendanceLogDTO> attendanceLogDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : attendanceLogsSumObj) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date attDate = report[0] != null ? ConverterUtil.getDate(report[0].toString()) : null;
			String empCode = report[1] != null ? (String) report[1] : "";
			String empName = report[2] != null ? (String) report[2] : "";
			String deptName = report[3] != null ? (String) report[3] : "";
			String desName = report[4] != null ? (String) report[4] : "";
			String jobLocation = report[5] != null ? (String) report[5] : "";
			String repManager = report[6] != null ? (String) report[6] : "";
			String shift = report[7] != null ? (String) report[7] : "";
			String shiftDur = report[8] != null ? (String) report[8] : "";
			String mode = report[9] != null ? (String) report[9] : "";
			String timeIn = report[10] != null ? (String) report[10] : "";
			String timeOut = report[11] != null ? (String) report[11] : "";
			String totalHr = report[12] != null ? (String) report[12] : "";
			String status = report[13] != null ? (String) report[13] : "";
			String lateBy = report[14] != null ? report[14].toString() : "";
			String earlyBy = report[15] != null ? report[15].toString() : "";
			String earlyBefore = report[16] != null ? report[16].toString() : "";
			String locationTimeIn = report[17] != null ? (String) report[17] : "";
			String locationTimeOut = report[18] != null ? (String) report[18] : "";

			attendanceLogDTO.setAttendanceDate(attDate);
			attendanceLogDTO.setEmployeeCode(empCode);
			attendanceLogDTO.setEmployeeName(empName);
			attendanceLogDTO.setDepartmentName(deptName);
			attendanceLogDTO.setDesignationName(desName);
			attendanceLogDTO.setJobLocation(jobLocation);
			attendanceLogDTO.setReportingTo(repManager);
			attendanceLogDTO.setShiftName(shift);
			attendanceLogDTO.setShiftDuration(shiftDur);

			if (mode.isEmpty()) {
				attendanceLogDTO.setMode("");
			} else {
				attendanceLogDTO.setMode(mode);
			}

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date3 = sdf.parse(timeIn);
				// new format
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time1 = sdf2.format(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date = sd.parse(timeOut);
				// new format
				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
				// formatting the given time to new format with AM/PM
				time2 = sd2.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLogDTO.setOutTime(time2);

			attendanceLogDTO.setTotalTime(totalHr);

			String logStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				logStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				logStatus = StatusMessage.HALFDAY_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.ABSENT_CODE)) {
				logStatus = StatusMessage.ABSENT_VALUE;
			}
			attendanceLogDTO.setStatus(logStatus);

			attendanceLogDTO.setLateBy(lateBy);
			attendanceLogDTO.setEarlyBy(earlyBy);
			attendanceLogDTO.setEarlyBefore(earlyBefore);

			if (locationTimeIn.isEmpty()) {
				attendanceLogDTO.setLocationTimeIn("");
			} else {
				attendanceLogDTO.setLocationTimeIn(locationTimeIn);
			}
			if (locationTimeOut.isEmpty()) {
				attendanceLogDTO.setLocationTimeOut("");
			} else {
				attendanceLogDTO.setLocationTimeOut(locationTimeOut);
			}

			attendanceLogDTOList.add(attendanceLogDTO);
		}

		return attendanceLogDTOList;
	}

	public List<AttendanceDTO> objectListToLeaveExpiryReport(List<Object[]> attendanceList) {
		List<AttendanceDTO> attendanceDtoList = new ArrayList<>();
		for (Object[] attendanceObj : attendanceList) {

			AttendanceDTO attendanceDTO = new AttendanceDTO();

			String employeeCode = attendanceObj[0] != null ? (String) attendanceObj[0] : null;
			String empName = attendanceObj[1] != null ? (String) attendanceObj[1] : null;
			String departmentName = attendanceObj[2] != null ? (String) attendanceObj[2] : null;
			String designationName = attendanceObj[3] != null ? (String) attendanceObj[3] : null;
			String jobLocation = attendanceObj[4] != null ? (String) attendanceObj[4] : null;
			String reportingManager = attendanceObj[5] != null ? (String) attendanceObj[5] : null;
			Long employeeId = attendanceObj[6] != null ? Long.parseLong(attendanceObj[6].toString()) : null;

			attendanceDTO.setEmployeeCode(employeeCode);
			attendanceDTO.setEmployeeName(empName);
			attendanceDTO.setDepartment(departmentName);
			attendanceDTO.setDesignation(designationName);
			attendanceDTO.setJobLocation(jobLocation);
			attendanceDTO.setReportingTo(reportingManager);
			attendanceDTO.setEmployeeId(employeeId);
			attendanceDtoList.add(attendanceDTO);

		}

		return attendanceDtoList;
	}

	public List<AttendanceLogDTO> objectListToShiftScheduleList(List<Object[]> otDetailsObjectList) {

		List<AttendanceLogDTO> attendanceDtoList = new ArrayList<AttendanceLogDTO>();
		for (Object[] attendanceObj : otDetailsObjectList) {

			AttendanceLogDTO attendanceDTO = new AttendanceLogDTO();

			String employeeCode = attendanceObj[0] != null ? (String) attendanceObj[0] : null;
			String empName = attendanceObj[1] != null ? (String) attendanceObj[1] : null;
			String departmentName = attendanceObj[2] != null ? (String) attendanceObj[2] : null;
			String designationName = attendanceObj[3] != null ? (String) attendanceObj[3] : null;
			String jobLocation = attendanceObj[4] != null ? (String) attendanceObj[4] : null;
			String reportingManager = attendanceObj[5] != null ? (String) attendanceObj[5] : null;
			String shift = attendanceObj[6] != null ? (String) attendanceObj[6] : null;
			String shiftDur = attendanceObj[7] != null ? (String) attendanceObj[7] : null;

			attendanceDTO.setEmployeeCode(employeeCode);
			attendanceDTO.setEmployeeName(empName);
			attendanceDTO.setDepartmentName(departmentName);
			attendanceDTO.setDesignationName(designationName);
			attendanceDTO.setJobLocation(jobLocation);
			attendanceDTO.setReportingTo(reportingManager);
			attendanceDTO.setShiftName(shift);
			attendanceDTO.setShiftDuration(shiftDur);

			attendanceDtoList.add(attendanceDTO);

		}
		return attendanceDtoList;
	}
}
