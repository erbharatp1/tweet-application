
package com.csipl.tms.attendanceCalculation.adaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDetailsDTO;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.empcommondetails.EmpCommonDetail;
import com.csipl.tms.model.halfdayrule.HalfDayRule;

public class AttendanceLogAdaptor { // implements Adaptor<AttendanceLogDetailsDTO, AttendanceLog> {

	public static SimpleDateFormat SDF_HHMMSS = new SimpleDateFormat("HH:mm:ss");

	public List<AttendanceLogDetailsDTO> objectListToAttendanceLogDetailsDto(

			List<AttendanceLog> attendanceLogDetailsList, List<EmpCommonDetail> empCommonDetailList,

			HalfDayRule halfDayRule) {

		return null;

	}

	public List<AttendanceLogDetailsDTO> modeltoDTOList(List<Object[]> attendanceLogDetails) {

		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = new ArrayList<AttendanceLogDetailsDTO>();

		for (Object[] attendanceLogDetailsObj : attendanceLogDetails) {

			String firstName = null, lastName;

			String inTimre = null, outTime;
			// np
			String time1 = null;
			String time2 = null;

			AttendanceLogDetailsDTO attendanceLogDetailsDto = new AttendanceLogDetailsDTO();

			if (attendanceLogDetailsObj[0] != null) {

				firstName = attendanceLogDetailsObj[0].toString();

				attendanceLogDetailsDto.setCharFirstName(Character.toString(firstName.charAt(0)).toUpperCase());

			}

			if (attendanceLogDetailsObj[1] != null) {

				lastName = attendanceLogDetailsObj[1].toString();

				attendanceLogDetailsDto.setCharLastName(Character.toString(lastName.charAt(0)).toUpperCase());

				attendanceLogDetailsDto.setName(firstName + " " + lastName);

			}

			if (attendanceLogDetailsObj[2] != null) {

				attendanceLogDetailsDto.setDesignationName(attendanceLogDetailsObj[2].toString());

			}

			if (attendanceLogDetailsObj[3] != null) {

				inTimre = attendanceLogDetailsObj[4].toString();
				// np

				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(inTimre);

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

			if (attendanceLogDetailsObj[4] != null) {

				// inTimre=attendanceLogDetailsObj[4].toString();

				outTime = attendanceLogDetailsObj[4].toString();
				// np
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(outTime);

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				// attendanceLogDetailsDto.setPunchRecord(inTimre + ", " + outTime);
				// np
				attendanceLogDetailsDto.setPunchRecord(time1 + ", " + time2);

			}

			if (attendanceLogDetailsObj[5] != null) {

				attendanceLogDetailsDto.setLocation(attendanceLogDetailsObj[5].toString());

			}

			if (attendanceLogDetailsObj[6] != null) {

				attendanceLogDetailsDto.setMode(attendanceLogDetailsObj[6].toString());

			}

			if (attendanceLogDetailsObj[7] != null) {

				attendanceLogDetailsDto.setDelayedTime(attendanceLogDetailsObj[7].toString());

			}

			/*
			 * if (attendanceLogDetailsObj[8] != null) {
			 * 
			 * attendanceLogDetailsDto.setEmployeeId(
			 * 
			 * (attendanceLogDetailsObj[8] != null ?
			 * Long.parseLong(attendanceLogDetailsObj[8].toString())
			 * 
			 * : null));
			 * 
			 * }
			 */

			if (attendanceLogDetailsObj[9] != null) {

				attendanceLogDetailsDto.setStatus(attendanceLogDetailsObj[9].toString());

			}

			if (attendanceLogDetailsObj[11] != null) {

				attendanceLogDetailsDto.setStatus(attendanceLogDetailsObj[11].toString());

			}

			if (attendanceLogDetailsObj[12] != null) {

				attendanceLogDetailsDto.setPresentCount(attendanceLogDetailsObj[12].toString());

			}

			if (attendanceLogDetailsObj[13] != null) {

				attendanceLogDetailsDto.setLeaveCount(attendanceLogDetailsObj[13].toString());

			}

			if (attendanceLogDetailsObj[14] != null) {

				attendanceLogDetailsDto.setAbsentCount(attendanceLogDetailsObj[14].toString());

			}
			attendanceLogDetailsDtoList.add(attendanceLogDetailsDto);

		}

		return attendanceLogDetailsDtoList;

	}

	public List<AttendanceLogDTO> databaseObjModelToUiDtoList(List<Object[]> objAttendanceLogList) {
		String statusValue = "";

		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();

		for (Object[] attendanceObj : objAttendanceLogList) {

			AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();

			if (attendanceObj[0] != null) {

				attendanceLogDto

						.setEmployeeId(attendanceObj[0] != null ? Long.parseLong(attendanceObj[0].toString()) : null);

			}

			if (attendanceObj[1] != null) {

				attendanceLogDto.setFirstName(attendanceObj[1].toString());

			}

			if (attendanceObj[2] != null) {

				attendanceLogDto.setLastName(attendanceObj[2].toString());

			}

			if (attendanceObj[3] != null) {

				attendanceLogDto.setAttendanceLogId(

						attendanceObj[3] != null ? Long.parseLong(attendanceObj[3].toString()) : null);

			} else {

			}

			if (attendanceObj[4] != null) {

				attendanceLogDto.setAttendanceDate((Date) attendanceObj[4]);

			}

			if (attendanceObj[5] != null) {

				attendanceLogDto.setEmployeeCode(attendanceObj[5].toString());

			}

			if (attendanceObj[6] != null) {

				attendanceLogDto

						.setCompanyId(attendanceObj[6] != null ? Long.parseLong(attendanceObj[6].toString()) : null);

			}

			if (attendanceObj[7] != null) {

				// attendanceLogDto.setInTime(attendanceObj[7].toString());
				// np
				String time1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(attendanceObj[7].toString());

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setInTime(time1);

			}

			if (attendanceObj[8] != null) {

				// attendanceLogDto.setOutTime(attendanceObj[8].toString());
				// np
				String time2 = null;
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(attendanceObj[8].toString());

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDto.setOutTime(time2);

			}

			if (attendanceObj[9] != null) {

				attendanceLogDto

						.setInDeviceId(attendanceObj[9] != null ? Long.parseLong(attendanceObj[9].toString()) : null);

			}

			if (attendanceObj[10] != null) {

				attendanceLogDto.setOutDeviceId(

						attendanceObj[10] != null ? Long.parseLong(attendanceObj[10].toString()) : null);

			}

			if (attendanceObj[11] != null) {

				attendanceLogDto.setLocation(attendanceObj[11].toString());

			}

			if (attendanceObj[12] != null) {

				attendanceLogDto.setMode(attendanceObj[12].toString());

			}

			if (attendanceObj[13] != null) {

				attendanceLogDto

						.setCreatedBy(attendanceObj[13] != null ? Long.parseLong(attendanceObj[13].toString()) : null);

			}

			if (attendanceObj[14] != null) {

				attendanceLogDto.setCreatedDate((Date) attendanceObj[14]);

			}

			if (attendanceObj[15] != null) {

				attendanceLogDto

						.setUpdatedBy(attendanceObj[15] != null ? Long.parseLong(attendanceObj[15].toString()) : null);

			}

			if (attendanceObj[16] != null) {

				attendanceLogDto.setDelayedTime(attendanceObj[16].toString());

			}
			if (attendanceObj[20] != null) {
				statusValue = attendanceObj[20].toString();
				attendanceLogDto.setStatusValue(statusValue);
			}
			if (attendanceObj[18] != null) {

				attendanceLogDto.setStatus(attendanceObj[18].toString());

//				if (attendanceLogDto.getStatus().equals("P"))
//
//					attendanceLogDto.setStatusValue("Present");
//
//				else if (attendanceLogDto.getStatus().equals("A"))
//
//					attendanceLogDto.setStatusValue("Absent");
//				else if (statusValue != null)
//					
//					attendanceLogDto.setStatusValue(statusValue);

			} else {
				attendanceLogDto.setStatus("A");
				if (statusValue != null)
					attendanceLogDto.setStatusValue(statusValue);
				else {
					attendanceLogDto.setStatusValue("Absent");

				}
				// attendanceLogDto.setStatus("");
				// attendanceLogDto.setStatusValue("Absent");

			}
			attendanceLogDtoList.add(attendanceLogDto);

		}

		return attendanceLogDtoList;

	}

	public List<AttendanceLog> uiDtoToDatabaseModelList(List<AttendanceLogDTO> attendanceLogDtoList,

			Date attendanceDate, String attendanceStatus) {

		List<AttendanceLog> attendanceLogList = new ArrayList<AttendanceLog>();

		for (AttendanceLogDTO attendanceLogDto : attendanceLogDtoList) {
			AttendanceLog attendanceLog = new AttendanceLog();

			if (!attendanceLogDto.getStatus().equals("L")) {

				attendanceLog.setAttendanceLogId(attendanceLogDto.getAttendanceLogId());

				attendanceLog.setAttendanceDate(attendanceDate);

				attendanceLog.setEmployeeId(attendanceLogDto.getEmployeeId());

				attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());

				attendanceLog.setCompanyId(attendanceLogDto.getCompanyId());

				attendanceLog.setStatus(attendanceStatus);

				attendanceLog.setUpdatedDate(new Date());
				// by
				String inTime = attendanceLogDto.getInTime();
				if (inTime != null) {
					String inTimeformated = timeFormatConversion(inTime);
					attendanceLog.setInTime(inTimeformated);
				} else {
					attendanceLog.setInTime(inTime);
				}

				String outTime = attendanceLogDto.getOutTime();
				if (outTime != null) {
					String outTimeformated = timeFormatConversion(outTime);
					attendanceLog.setOutTime(outTimeformated);
				} else {
					attendanceLog.setOutTime(outTime);
				}
				// --

				attendanceLog.setInDeviceId(attendanceLogDto.getInDeviceId());

				attendanceLog.setOutDeviceId(attendanceLogDto.getOutDeviceId());

				attendanceLog.setLocation(attendanceLogDto.getLocation());

				attendanceLog.setMode(attendanceLogDto.getMode());

				attendanceLog.setCreatedBy(attendanceLogDto.getCreatedBy());

				attendanceLog.setUpdatedBy(attendanceLogDto.getUpdatedBy());

				attendanceLog.setDelayedTime(attendanceLogDto.getDelayedTime());

				if (attendanceLogDto.getCreatedDate() != null)

					attendanceLog.setCreatedDate(attendanceLogDto.getCreatedDate());

				else

					attendanceLog.setCreatedDate(new Date());

				attendanceLogList.add(attendanceLog);

			}

		}

		return attendanceLogList;

	}

	public AttendanceLog uiDtoToDatabaseModel(AttendanceLogDTO attendanceLogDto, Date attendanceDate,

			String attendanceStatus) {

		AttendanceLog attendanceLog = new AttendanceLog();

		if (!attendanceLogDto.getStatus().equals("L")) {

			attendanceLog.setAttendanceLogId(attendanceLogDto.getAttendanceLogId());

			attendanceLog.setAttendanceDate(attendanceDate);

			attendanceLog.setEmployeeId(attendanceLogDto.getEmployeeId());

			attendanceLog.setEmployeeCode(attendanceLogDto.getEmployeeCode());

			attendanceLog.setCompanyId(attendanceLogDto.getCompanyId());

			attendanceLog.setStatus(attendanceStatus);

			attendanceLog.setUpdatedDate(new Date());

			// attendanceLog.setInTime(attendanceLogDto.getInTime());
			// np
			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date1 = sdf.parse(attendanceLogDto.getInTime());

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

				time1 = sdf2.format(date1);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLog.setInTime(time1);

			// attendanceLog.setOutTime(attendanceLogDto.getOutTime());
			// np
			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date2 = sd.parse(attendanceLogDto.getOutTime());

				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

				time2 = sd2.format(date2);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			attendanceLog.setOutTime(time2);

			attendanceLog.setInDeviceId(attendanceLogDto.getInDeviceId());

			attendanceLog.setOutDeviceId(attendanceLogDto.getOutDeviceId());

			attendanceLog.setLocation(attendanceLogDto.getLocation());

			attendanceLog.setMode(attendanceLogDto.getMode());

			attendanceLog.setCreatedBy(attendanceLogDto.getCreatedBy());

			attendanceLog.setUpdatedBy(attendanceLogDto.getUpdatedBy());

			attendanceLog.setDelayedTime(attendanceLogDto.getDelayedTime());

			if (attendanceLogDto.getCreatedDate() != null)

				attendanceLog.setCreatedDate(attendanceLogDto.getCreatedDate());

			else

				attendanceLog.setCreatedDate(new Date());

		}

		return attendanceLog;

	}

	public List<AttendanceLogDTO> databaseModelToUiDtoList(List<AttendanceLog> attendanceLogList1) {

		List<AttendanceLogDTO> attendanceLogDtoList = new ArrayList<AttendanceLogDTO>();

		for (AttendanceLog attendanceLog : attendanceLogList1) {

			attendanceLogDtoList.add(databaseModelToUiDto(attendanceLog));

		}

		return attendanceLogDtoList;

	}

	private AttendanceLogDTO databaseModelToUiDto(AttendanceLog attendanceLog) {

		AttendanceLogDTO attendanceLogDto = new AttendanceLogDTO();

		attendanceLogDto.setAttendanceLogId(attendanceLog.getAttendanceLogId());

		attendanceLogDto.setEmployeeId(attendanceLog.getEmployeeId());

		attendanceLogDto.setEmployeeCode(attendanceLog.getEmployeeCode());

		attendanceLogDto.setInTime(attendanceLog.getInTime());
		// np
//		String time1 = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//		try {
//			Date date1 = sdf.parse(attendanceLog.getInTime());
//
//			SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
//
//			time1 = sdf2.format(date1);
//
//		} catch (Exception e) {
//
//		}
//		attendanceLog.setInTime(time1);

		attendanceLogDto.setOutTime(attendanceLog.getOutTime());
		// np
//		String time2 = null;
//		SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
//		try {
//			Date date2 = sd.parse(attendanceLog.getOutTime());
//
//			SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
//
//			time2 = sd2.format(date2);
//
//		} catch (Exception e) {
//
//		}
//		attendanceLog.setOutTime(time2);

		attendanceLogDto.setInDeviceId(attendanceLog.getOutDeviceId());

		attendanceLogDto.setLocation(attendanceLog.getLocation());

		attendanceLogDto.setCompanyId(attendanceLog.getCompanyId());

		attendanceLogDto.setAttendanceDate(attendanceLog.getAttendanceDate());

		attendanceLogDto.setStatus(attendanceLog.getStatus());

		attendanceLogDto.setCreatedDate(attendanceLog.getCreatedDate());

		attendanceLogDto.setOutDeviceId(attendanceLog.getOutDeviceId());

		attendanceLogDto.setLocation(attendanceLog.getLocation());

		attendanceLogDto.setMode(attendanceLog.getMode());

		attendanceLogDto.setCreatedBy(attendanceLog.getCreatedBy());

		attendanceLogDto.setUpdatedBy(attendanceLog.getUpdatedBy());

		attendanceLogDto.setDelayedTime(attendanceLog.getDelayedTime());

		return attendanceLogDto;

	}

	public List<AttendanceLogDetailsDTO> modeltoDTONewList(List<Object[]> attendanceLogDetails, String dateSelect)
			throws ParseException {

		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = new ArrayList<AttendanceLogDetailsDTO>();
		int index = 1;
		for (Object[] attendanceLogDetailsObj : attendanceLogDetails) {

			String firstName = null, lastName;
			String inTime = null, outTime;
			// np
//			String time1 = null;
//			String time2 = null;

			String leaveStatus;
			AttendanceLogDetailsDTO attendanceLogDetailsDto = new AttendanceLogDetailsDTO();
			attendanceLogDetailsDto.setIndex(index);
			if (attendanceLogDetailsObj[0] != null) {
				firstName = attendanceLogDetailsObj[0].toString();
				attendanceLogDetailsDto.setCharFirstName(Character.toString(firstName.charAt(0)).toUpperCase());
			}
			if (attendanceLogDetailsObj[1] != null) {
				lastName = attendanceLogDetailsObj[1].toString();
				attendanceLogDetailsDto.setCharLastName(Character.toString(lastName.charAt(0)).toUpperCase());
				attendanceLogDetailsDto.setName(firstName + " " + lastName);
			}
			if (attendanceLogDetailsObj[2] != null) {
				attendanceLogDetailsDto.setDesignationName(attendanceLogDetailsObj[2].toString());
			}
			if (attendanceLogDetailsObj[3] != null) {

				inTime = attendanceLogDetailsObj[3].toString();
				// np
//				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//				try {
//					Date date1 = sdf.parse(inTime);
//
//					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
//
//					time1 = sdf2.format(date1);
//
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}

			}
			if (attendanceLogDetailsObj[4] != null) {

				outTime = attendanceLogDetailsObj[4].toString();
				// np
//				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
//				try {
//					Date date2 = sd.parse(outTime);
//
//					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
//
//					time2 = sd2.format(date2);
//
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}

				attendanceLogDetailsDto.setPunchRecord(inTime + " - " + outTime);
				// np
				// attendanceLogDetailsDto.setPunchRecord(time1 + " - " + time2);
			}
			if (attendanceLogDetailsObj[6] != null) {
				attendanceLogDetailsDto.setMode(attendanceLogDetailsObj[6].toString());
			}
			if (attendanceLogDetailsObj[9] != null) {
				attendanceLogDetailsDto.setStatus(attendanceLogDetailsObj[9].toString());
			}
			if (attendanceLogDetailsObj[11] != null) {
				attendanceLogDetailsDto.setEmployeeCode(attendanceLogDetailsObj[11].toString());
			}
			if (attendanceLogDetailsObj[12] != null) {
				attendanceLogDetailsDto.setDepartmentName(attendanceLogDetailsObj[12].toString());
			}
			if (attendanceLogDetailsObj[13] != null) {
				String reporingTo = attendanceLogDetailsObj[13] != null ? (String) attendanceLogDetailsObj[13] : null;
				String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);
				if (newReporingTo.startsWith("-")) {
					attendanceLogDetailsDto.setRepotedLateBy("On Time");
				} else {

					attendanceLogDetailsDto.setRepotedLateBy(newReporingTo);
				}
			}
			if (attendanceLogDetailsObj[14] != null) {
				String leteReporingTo = attendanceLogDetailsObj[14] != null ? (String) attendanceLogDetailsObj[14]
						: null;
				String newLateReporingTo = leteReporingTo.substring(0, leteReporingTo.length() - 7);

				if (newLateReporingTo.startsWith("-")) {
					attendanceLogDetailsDto.setLeftEarlyBy("On Time");
				} else {
					attendanceLogDetailsDto.setLeftEarlyBy(newLateReporingTo);
				}
			}
			if (attendanceLogDetailsObj[15] != null) {
				leaveStatus = attendanceLogDetailsObj[15].toString();
				String attendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

				if (dateSelect.equals(attendanceDate)) {
					if (leaveStatus.equals("Absent")) {
						attendanceLogDetailsDto.setLeaveStatus("Under Process");
					} else if (leaveStatus.equals("Holiday")) {
						attendanceLogDetailsDto.setLeaveStatus("Holiday");
					} else if (leaveStatus.equals("Half Day Leave")) {
						attendanceLogDetailsDto.setLeaveStatus("Half Day Leave");
					} else if (leaveStatus.equals("Full Day Leave")) {
						attendanceLogDetailsDto.setLeaveStatus("Full Day Leave");
					} else if (leaveStatus.equals("Present")) {
						attendanceLogDetailsDto.setLeaveStatus("Present");
					} else if (leaveStatus.equals("Week-Off")) {
						attendanceLogDetailsDto.setLeaveStatus("Week-Off");
					}
				} else
					attendanceLogDetailsDto.setLeaveStatus(leaveStatus);
			}
			try {
				if (attendanceLogDetailsObj[16] != null) {
					attendanceLogDetailsDto.setPresentCount(attendanceLogDetailsObj[16].toString());
				}
			} catch (Exception e) {
				e.getMessage();
			}
			try {
				if (attendanceLogDetailsObj[17] != null) {
					attendanceLogDetailsDto.setLeaveCount(attendanceLogDetailsObj[17].toString());
				}
			} catch (Exception e) {
				e.getMessage();
			}
			try {
				if (attendanceLogDetailsObj[18] != null) {
					attendanceLogDetailsDto.setAbsentCount(attendanceLogDetailsObj[18].toString());
				}
			} catch (Exception e) {
				e.getMessage();
			}
			attendanceLogDetailsDtoList.add(attendanceLogDetailsDto);
			index++;
		}

		return attendanceLogDetailsDtoList;

	}

	public List<AttendanceLogDetailsDTO> modeltoDTOCurrentDateList(List<Object[]> attendanceLogDetails,
			String dateSelect) {
		// Date", "Employee Code","Employee", "Department","Job Location","Reporting
		// Manager","Shift","Shift Duration","Punching Mode","Time In","Time Out","Total
		// Hours","Attendance Status","Late By","Early By","Early Before", "Location
		// -Time In", "Location-Time Out"

		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = new ArrayList<AttendanceLogDetailsDTO>();
		for (Object[] attendanceLogDetailsObj : attendanceLogDetails) {
			String firstName = null, lastName;
			String inTime = null, outTime, attDate;
			// np
			String time1 = null;
			String time2 = null;
			// String leaveStatus;
			AttendanceLogDetailsDTO attendanceLogDetailsDto = new AttendanceLogDetailsDTO();
			if (attendanceLogDetailsObj[0] != null) {
				attDate = attendanceLogDetailsObj[0].toString();
				attendanceLogDetailsDto.setAttedanceDate(attDate);
			}
			if (attendanceLogDetailsObj[1] != null) {
				attendanceLogDetailsDto.setEmployeeCode(attendanceLogDetailsObj[1].toString());
			}
			if (attendanceLogDetailsObj[2] != null) {
				firstName = attendanceLogDetailsObj[2].toString();
				attendanceLogDetailsDto.setName(firstName);
			}
			if (attendanceLogDetailsObj[3] != null) {
				attendanceLogDetailsDto.setDepartmentName(attendanceLogDetailsObj[3].toString());
			}
			if (attendanceLogDetailsObj[4] != null) {
				attendanceLogDetailsDto.setDesignationName(attendanceLogDetailsObj[4].toString());
			}
			if (attendanceLogDetailsObj[5] != null) {
				attendanceLogDetailsDto.setJobLocation(attendanceLogDetailsObj[5].toString());
			}
			if (attendanceLogDetailsObj[6] != null) {
				attendanceLogDetailsDto.setReportingTo(attendanceLogDetailsObj[6].toString());
			}

			if (attendanceLogDetailsObj[7] != null) {
				attendanceLogDetailsDto.setShift(attendanceLogDetailsObj[7].toString());
			}
			if (attendanceLogDetailsObj[8] != null) {
				attendanceLogDetailsDto.setShiftDuration(attendanceLogDetailsObj[8].toString());
			}
			if (attendanceLogDetailsObj[9] != null) {
				attendanceLogDetailsDto.setMode(attendanceLogDetailsObj[9].toString());
			}

			if (attendanceLogDetailsObj[10] != null) {
				inTime = attendanceLogDetailsObj[10].toString();

				// attendanceLogDetailsDto.setTimeIn(inTime);
				// np
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date1 = sdf.parse(inTime);

					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");

					time1 = sdf2.format(date1);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDetailsDto.setTimeIn(time1);
			}
			if (attendanceLogDetailsObj[11] != null) {
				outTime = attendanceLogDetailsObj[11].toString();

				// attendanceLogDetailsDto.setTimeOut(outTime);
				// np
				SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date2 = sd.parse(outTime);

					SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");

					time2 = sd2.format(date2);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				attendanceLogDetailsDto.setTimeOut(time2);
				// attendanceLogDetailsDto.setPunchRecord(inTime + " - " + outTime);
			}

			if (attendanceLogDetailsObj[12] != null) {
				attendanceLogDetailsDto.setTotalHours(attendanceLogDetailsObj[12].toString());
			}

			if (attendanceLogDetailsObj[13] != null) {
				String leaveStatus = attendanceLogDetailsObj[13].toString();
				String attendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				if (dateSelect.equals(attendanceDate)) {
					if (leaveStatus.equals("Absent")) {
						attendanceLogDetailsDto.setLeaveStatus("Under Process");
					} else if (leaveStatus.equals("Holiday")) {
						attendanceLogDetailsDto.setLeaveStatus("Holiday");
					} else if (leaveStatus.equals("Half Day Leave")) {
						attendanceLogDetailsDto.setLeaveStatus("Half Day Leave");
					} else if (leaveStatus.equals("Full Day Leave")) {
						attendanceLogDetailsDto.setLeaveStatus("Full Day Leave");
					} else if (leaveStatus.equals("Present")) {
						attendanceLogDetailsDto.setLeaveStatus("Present");
					} else if (leaveStatus.equals("Week-Off")) {
						attendanceLogDetailsDto.setLeaveStatus("Week-Off");
					}
				} else
					attendanceLogDetailsDto.setLeaveStatus(leaveStatus);
			}

			if (attendanceLogDetailsObj[14] != null) {
				String reporingTo = attendanceLogDetailsObj[14] != null ? (String) attendanceLogDetailsObj[14] : null;
				String newReporingTo = reporingTo.substring(0, reporingTo.length() - 7);
				if (newReporingTo.startsWith("-")) {
					attendanceLogDetailsDto.setLateBy("On Time");
				} else {

					attendanceLogDetailsDto.setLateBy(newReporingTo);
				}
			}
			if (attendanceLogDetailsObj[15] != null) {
				String leteReporingTo = attendanceLogDetailsObj[15] != null ? (String) attendanceLogDetailsObj[15]
						: null;
				String newLateReporingTo = leteReporingTo.substring(0, leteReporingTo.length() - 7);
				if (newLateReporingTo.startsWith("-")) {
					attendanceLogDetailsDto.setLeftEarlyBy("On Time");
				} else {
					attendanceLogDetailsDto.setLeftEarlyBy(newLateReporingTo);
				}
			}
			if (attendanceLogDetailsObj[16] != null) {
				String leteReporingTo = attendanceLogDetailsObj[16] != null ? (String) attendanceLogDetailsObj[16]
						: null;
				String newLateReporingTo = leteReporingTo.substring(0, leteReporingTo.length() - 7);
				if (newLateReporingTo.startsWith("-")) {
					attendanceLogDetailsDto.setEarlyBefore("On Time");
				} else {
					attendanceLogDetailsDto.setEarlyBefore(newLateReporingTo);
				}
			}
			if (attendanceLogDetailsObj[17] != null) {
				attendanceLogDetailsDto.setLocationIn(attendanceLogDetailsObj[17].toString());
			}
			if (attendanceLogDetailsObj[18] != null) {
				attendanceLogDetailsDto.setLocationOut(attendanceLogDetailsObj[18].toString());
			}
			attendanceLogDetailsDtoList.add(attendanceLogDetailsDto);

		}

		return attendanceLogDetailsDtoList;

	}

//	/// excluding employees on leave
//	public List<AttendanceLogDTO> attendanceDtoListLeaveExcluded(List<AttendanceLogDTO> attendanceDtoListLeave) {
//		Iterator<AttendanceLogDTO> iterator = attendanceDtoListLeave.iterator(); // it will return iterator
//		while (iterator.hasNext()) {
//			AttendanceLogDTO logDTO = iterator.next();
//			if (logDTO.getStatus() != null && logDTO.getStatus().equalsIgnoreCase("L")) {
//				iterator.remove(); // remove element if match condition
//			}
//		}
//		return attendanceDtoListLeave;
//	}
	
	/// excluding employees on leave OR AR
		public List<AttendanceLogDTO> attendanceDtoListLeaveExcluded(List<AttendanceLogDTO> attendanceDtoListLeave) {
			Iterator<AttendanceLogDTO> iterator = attendanceDtoListLeave.iterator(); // it will return iterator
			while (iterator.hasNext()) {
				AttendanceLogDTO logDTO = iterator.next();
				if (logDTO.getStatus() != null && (logDTO.getStatus().equalsIgnoreCase("L")
						|| logDTO.getStatus().equalsIgnoreCase("AR"))) {
					iterator.remove(); // remove element if match condition
				}
			}
			return attendanceDtoListLeave;
		}

	/// change pby2 to p/2
	public String changePbyTwo(String attendanceStatus) {
		String status = attendanceStatus;
		if (attendanceStatus.equalsIgnoreCase("Pby2")) {
			status = "p/2";
		}
		return status;
	}

	// change AM time format to 24 h time format
	public String timeFormatConversion(String validTimes) {
		String time = validTimes.trim();
		Calendar calendar = Calendar.getInstance();

		String times[] = time.split(":");
		Integer hours = Integer.valueOf(times[0]);
		Integer minute = Integer.valueOf(times[1]);
		String secWithAmpm = times[2];
		String sec[] = secWithAmpm.split(" ");
		Integer second = Integer.valueOf(sec[0]);

		calendar.set(Calendar.HOUR, hours);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);

		if (time.contains("AM")) {
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			String inTime = SDF_HHMMSS.format(calendar.getTime());
			return inTime;
		} else if (time.contains("PM")) {
			calendar.set(Calendar.HOUR, hours);
			String inTime = SDF_HHMMSS.format(calendar.getTime());
			return inTime;
		}

		return validTimes;
	}

	public List<AttendanceLogDTO> modeltoCheckInDTOList(List<Object[]> checkInList) throws ParseException {

		List<AttendanceLogDTO> deviceLogList = new ArrayList<AttendanceLogDTO>();

		for (Object[] obj : checkInList) {

			AttendanceLogDTO attendanceLogDTO = new AttendanceLogDTO();

			Date date = obj[0] != null ? (Date) obj[0] : null;
			String name = obj[1] != null ? (String) obj[1] : "";
			Long empId = obj[2] != null ? Long.parseLong(obj[2].toString()) : null;
			Date attendanceTime = obj[3] != null ? (Date) obj[3] : null;
			String mode = obj[4] != null ? (String) obj[4] : "";

			String inTime = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			Date inDate = sd.parse(attendanceTime.toString());
			SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
			inTime = sd2.format(inDate);
			String code = String.valueOf(mode.charAt(0));

			attendanceLogDTO.setDate(date);
			attendanceLogDTO.setUserName(name);
			attendanceLogDTO.setEmpId(empId);
			attendanceLogDTO.setModeCode(code);
			attendanceLogDTO.setCheckInTime(inTime);
			attendanceLogDTO.setMode(mode);

			deviceLogList.add(attendanceLogDTO);
		}

		return deviceLogList;
	}

	/// excluding employees who's payroll created
	public List<AttendanceLogDTO> attendanceDtoListPayrollExcluded(List<AttendanceLogDTO> attendanceDtoListData,
			List<ReportPayOut> reportPayOutForEmployee) {
		if (attendanceDtoListData == null || reportPayOutForEmployee == null) {
			return attendanceDtoListData;
		}

//			
//			List<AttendanceLogDTO> attendanceDtoL =	attendanceDtoListData.stream().filter( attendanceDto ->
//			reportPayOutForEmployee.stream().anyMatch(reportPayoutEmp ->   
//			!attendanceDto.getEmployeeCode().equalsIgnoreCase(reportPayoutEmp.getEmployeeCode()))).collect(Collectors.toList());

		List<AttendanceLogDTO> attendanceDtoL = attendanceDtoListData.stream()
				.filter(attendanceDto -> (reportPayOutForEmployee.stream()
						.filter(reportPayoutEmp -> attendanceDto.getEmployeeCode()
								.equalsIgnoreCase(reportPayoutEmp.getEmployeeCode()))
						.count()) < 1)
				.collect(Collectors.toList());

		return attendanceDtoL;
	}
}
