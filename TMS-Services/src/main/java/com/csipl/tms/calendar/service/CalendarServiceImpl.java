package com.csipl.tms.calendar.service;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.util.ConverterUtil;
import com.csipl.tms.attendancelog.service.AttendanceLogService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.calendar.CalendarDTO;
import com.csipl.tms.dto.daysattendancelog.DaysAttendanceLogDTO;
import com.csipl.tms.dto.halfdayrule.HalfDayRuleDTO;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.monthattendance.MonthAttendanceDTO;
import com.csipl.tms.model.shift.Shift;

@Transactional
@Service("calendarService")
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	AttendanceLogService attendanceLogService;

	@Override
	public CalendarDTO calendarLogs(Date empJoiningDateObj, Date fromDate, Date toDate,
			List<AttendanceLogDTO> attendanceLogDtoList, List<AttendanceRegularizationRequestDTO> arRequestDtoList,
			String[] weekOffPatternArray, List<HolidayDTO> holidayDtoList, Shift shiftNameObj,
			List<LeaveEntryDTO> leaveEntryDtoList, HalfDayRuleDTO halfDayRuleDto) {

		Long maxRequireHour = halfDayRuleDto.getMaximumRequireHour();
		Long minRequireHour = halfDayRuleDto.getMinimumRequireHour();

		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

		CalendarDTO calendarDto = new CalendarDTO();

		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simdf.format(fromDate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(simdf.parse(strDate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		List<MonthAttendanceDTO> monthAttendanceDtoList = new ArrayList<MonthAttendanceDTO>();
		List<DaysAttendanceLogDTO> daysAttendanceLogDtoList = new ArrayList<DaysAttendanceLogDTO>();

		for (int i = 1; i <= Integer.parseInt(dayFormat.format(toDate)); i++) {
			int j = 0;
			j++;
			MonthAttendanceDTO monthAttendanceDto = new MonthAttendanceDTO();
			monthAttendanceDto.setActionDate(c.getTime());

			monthAttendanceDtoList.add(monthAttendanceDto);
			c.add(Calendar.DATE, j);

		}

		Date empJoiningDate = empJoiningDateObj != null ? empJoiningDateObj : null;
		for (MonthAttendanceDTO monthAttendance : monthAttendanceDtoList) {

			String inTime = null;
			String outTime = null;
			String mode = null;
			// Date actionDate = null;
			DaysAttendanceLogDTO daysAttendanceLogDto = new DaysAttendanceLogDTO();
			/* DayLogsDTO dayLogsDto = new DayLogsDTO(); */
			/*
			 * Attendance
			 */

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String actDate = dateFormat.format(monthAttendance.getActionDate());

			Date currentData = new Date();
			// String currentData = dateFormat.format(crntDate);

			Date actionDate = null;
			try {
				actionDate = dateFormat.parse(actDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			if (currentData.compareTo(actionDate) > 0) {

				if (empJoiningDate.compareTo(actionDate) < 0) {

					// attendanceLogDtoList.forEach(attendanceLog -> {
					for (AttendanceLogDTO attendanceLog : attendanceLogDtoList) {

						String attendanceDate = dateFormat.format(attendanceLog.getAttendanceDate());

						if (attendanceDate.equals(actDate)) {

							SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

							Date outDate = null;
							Date inDate = null;

							outTime = attendanceLog.getOutTime();
							inTime = attendanceLog.getInTime();
							mode = attendanceLog.getMode();

							try {
								outDate = (Date) sdf.parse(outTime);
								inDate = (Date) sdf.parse(inTime);
								/*
								 * dayLogsDto.setFirstIn(inTime); dayLogsDto.setLastOut(outTime);
								 */
								Long diff = outDate.getTime() - inDate.getTime();
								Long diffHours = diff / (60 * 60 * 1000) % 24;

								if (minRequireHour < diffHours) {
									if (maxRequireHour > diffHours) {
										daysAttendanceLogDto.setTitle(StatusMessage.HALFDAY_CODE);

									}
									if (maxRequireHour <= diffHours) {
										daysAttendanceLogDto.setTitle(StatusMessage.PRESENT_CODE);
									}
								}

							} catch (ParseException e) {
								e.printStackTrace();
							}

						} else {
							/*
							 * ARRequest
							 * 
							 */

							for (AttendanceRegularizationRequestDTO ar : arRequestDtoList) {

								if (ar.getStatus().equals(StatusMessage.ABSENT_CODE)) {

									long diff = ar.getFromDate().getTime() - ar.getToDate().getTime();
									long diffDays = diff / (24 * 60 * 60 * 1000);
									List<String> dateList = new ArrayList<String>();
									final Calendar calendar = Calendar.getInstance();

									calendar.setTime(ar.getFromDate());
									String date = dateFormat.format(calendar.getTime());
									dateList.add(date);

									for (int i = 1; i <= diffDays; i++) {
										calendar.add(Calendar.DAY_OF_YEAR, i);
										String attDate = dateFormat.format(calendar.getTime());
										dateList.add(attDate);
									}

									if (dateList.contains(actDate))
										daysAttendanceLogDto.setTitle(StatusMessage.AR_CODE);

								} else {
									/*
									 * 
									 * EmpLeaveEntry
									 */

									for (LeaveEntryDTO leaveEntry : leaveEntryDtoList) {

										long leaveDiff = leaveEntry.getFromDate().getTime()
												- leaveEntry.getToDate().getTime();
										long leaveDiffDays = leaveDiff / (24 * 60 * 60 * 1000);
										List<String> leavedDteList = new ArrayList<String>();

										final Calendar leaveCalendar = Calendar.getInstance();

										leaveCalendar.setTime(leaveEntry.getFromDate());
										String leavedate = dateFormat.format(leaveCalendar.getTime());
										leavedDteList.add(leavedate);
										for (int i = 1; i <= leaveDiffDays; i++) {
											leaveCalendar.add(Calendar.DAY_OF_YEAR, i);
											String leaveDate = dateFormat.format(leaveCalendar.getTime());
											leavedDteList.add(leaveDate);
										}
										if (leavedDteList.contains(actDate)) {
											if ("A".equals(leaveEntry.getStatus())) {
												if ("H".equals(leaveEntry.getHalf_fullDay()))
													daysAttendanceLogDto.setTitle(StatusMessage.HALFDAY_CODE);

												if ("F".equals(leaveEntry.getHalf_fullDay()))
													daysAttendanceLogDto.setTitle(StatusMessage.PRESENT_CODE);
											}
										} else {
											/*
											 * Holiday
											 * 
											 */

											for (HolidayDTO holiday : holidayDtoList) {

												long holiDayDiff = holiday.getFromDate().getTime()
														- holiday.getToDate().getTime();
												long holiDayDiffDays = holiDayDiff / (24 * 60 * 60 * 1000);
												List<String> holiDayDateList = new ArrayList<String>();
												final Calendar holiDatCalendar = Calendar.getInstance();

												holiDatCalendar.setTime(holiday.getFromDate());
												String holiDayDate = dateFormat.format(holiDatCalendar.getTime());
												holiDayDateList.add(holiDayDate);

												for (int i = 1; i <= holiDayDiffDays; i++) {
													holiDatCalendar.add(Calendar.DAY_OF_YEAR, i);
													String holidayDate = dateFormat.format(holiDatCalendar.getTime());
													holiDayDateList.add(holidayDate);

												}
												if (holiDayDateList.contains(actDate)) {

													daysAttendanceLogDto.setTitle(StatusMessage.HALFDAY_CODE);
												}

											}

										}
									}
								}
							}
						}

					}

					// );
				}
			}
			/*
			 * WeekOff
			 */
			if (currentData.compareTo(actionDate) > 0) {
				if (empJoiningDate.compareTo(actionDate) < 0) {
					List<String> weekDayList = Arrays.asList(weekOffPatternArray);
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE");
					String dayName = simpleDateformat.format(monthAttendance.getActionDate());

					if (weekDayList.contains(dayName.toUpperCase())) {
						daysAttendanceLogDto.setTitle(null);
						daysAttendanceLogDto.setTitle(StatusMessage.OFF_CODE);
					}
					if (daysAttendanceLogDto.getTitle() == null) {
						daysAttendanceLogDto.setTitle(StatusMessage.ABSENT_CODE);
					}
				}
			}
			/*
			 * shift
			 */
			if (currentData.compareTo(actionDate) > 0) {
				if (empJoiningDate.compareTo(actionDate) < 0) {
					String title = "";
					String shift = null;
					if (daysAttendanceLogDto.getTitle() != null) {
						title = daysAttendanceLogDto.getTitle();
					}

					if (shiftNameObj != null) {
						shift = shiftNameObj.getShiftFName();
					}
					/*
					 * dayLogsDto.setShift(shift); dayLogsDto.setAttendance(title);
					 */
					daysAttendanceLogDto.setTitle(title);
					daysAttendanceLogDto.setInTime(inTime);
					daysAttendanceLogDto.setOutTime(outTime);
					daysAttendanceLogDto.setMode(mode);
					daysAttendanceLogDto.setShift(shift);
					;
					daysAttendanceLogDto.setStart(actDate);
					// dayLogsDto.setActionDate(actDate);
				}
			}
			/*
			 * Date empJoiningDate = empJoiningDateObj[0] != null ? (Date)
			 * (empJoiningDateObj[0]) : null; try { actionDate = dateFormat.parse(actDate);
			 * } catch (ParseException e) { e.printStackTrace(); }
			 * 
			 * if (empJoiningDate.compareTo(actionDate) > 0)
			 * daysAttendanceLogDto.setTitle("");
			 */

			// dayLogsDto.setAttendance(daysAttendanceLogDto.getTitle());

			daysAttendanceLogDtoList.add(daysAttendanceLogDto);

			/* dayLogsMap.put(actDate, dayLogsDto); */
		}
		calendarDto.setEvents(daysAttendanceLogDtoList);
		return calendarDto;
	}

	@Override
	public CalendarDTO calenderLogDates(AttendanceLogDTO attendanceLog) {
		CalendarDTO calendarDto = new CalendarDTO();
		/*
		 * for (AttendanceLogDTO attendanceLog : attendanceLogDtoList) {
		 * 
		 * }
		 */
		return null;
	}

	/*
	 * @Override public DayLogsDTO getDayLog(String date) { DayLogsDTO dayLogDto =
	 * null; if (dayLogsMap.get(date) != null) { dayLogDto = dayLogsMap.get(date); }
	 * return dayLogDto;
	 * 
	 * }
	 */

	@Override
	public List<EmployeeReportDTO> empAttendanceRatio(Long companyId, Long employeeId) throws ParseException , ErrorHandling{

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
		String fromDate = formatter.format(date);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		date = formatter.parse(fromDate);
		int year = DateUtils.getYear(date);
		int month = DateUtils.getMonth(date);

		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		String monthAcronym = shortMonths[month - 1];
		String processMonth = monthAcronym.toUpperCase() + "-" + String.valueOf(year);
		AttendanceLogDTO calendarDto = attendanceLogService.calculateAttendanceForPayroll(employeeId, processMonth,
				companyId);
	 
		EmployeeReportDTO e1 = new EmployeeReportDTO();
		EmployeeReportDTO e2 = new EmployeeReportDTO();
		EmployeeReportDTO e3 = new EmployeeReportDTO();

		e1.setEmpAttenance(calendarDto.getPresense().toString());
		e1.setEmpAttendanceeRange("present");
		e1.setColor("#575bde");
		e2.setEmpAttenance(calendarDto.getLeaves().toString());
		e2.setEmpAttendanceeRange("on leave");
		e2.setColor("#9ba7ed");	
		e3.setEmpAttenance(calendarDto.getAbsenseForCalender().toString());
		e3.setEmpAttendanceeRange("absent");
		e3.setColor("#879aff");
		employeeReportDTO.add(e1);
		employeeReportDTO.add(e2);
		employeeReportDTO.add(e3);

		return employeeReportDTO;
	}
//	employeeColor("#575bde"),employeerColor("#9ba7ed"),pensionColor("#879aff");
}	