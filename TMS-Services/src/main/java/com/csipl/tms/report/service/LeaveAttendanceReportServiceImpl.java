package com.csipl.tms.report.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DayEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.organisation.Grade;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.GradeService;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.hrms.service.payroll.ProfessionalTaxService;
import com.csipl.hrms.service.payroll.repository.AttendanceRepository;
import com.csipl.hrms.service.util.ConverterUtil;
import com.csipl.tms.attendancelog.repository.AttendanceLogRepository;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceAdaptor;
import com.csipl.tms.attendanceregularizationrequest.service.AttendanceRegularizationRequestService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceValidationResult;
import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.dto.leave.LeaveBalanceDTO;
import com.csipl.tms.dto.monthattendance.EmployeeLeaveGraphDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.holiday.service.HolidayService;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.report.adaptor.LeaveAttendanceReportAdapator;
import com.csipl.tms.leave.repository.CompensatoryOffRepository;
import com.csipl.tms.leave.repository.LeaveEntriesDatewiseRepository;
import com.csipl.tms.leave.repository.LeaveEntryRepository;
import com.csipl.tms.leave.repository.LeavePeriodRepository;
import com.csipl.tms.leave.service.LeaveEntriesDatewiseService;
import com.csipl.tms.leave.service.LeavePeriodService;
import com.csipl.tms.leave.service.LeaveRulesHdService;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;
import com.csipl.tms.model.holiday.TMSHolidays;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.leave.TMSLeaveRules;
import com.csipl.tms.model.leave.TMSLeaveRulesHd;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;

@Transactional
@Service("leaveAttendanceReportService")
public class LeaveAttendanceReportServiceImpl implements LeaveAttendanceReportService {

	private static final Logger logger = LoggerFactory.getLogger(LeaveAttendanceReportServiceImpl.class);
	@Autowired
	AttendanceLogRepository attendanceLogRepository;

	@Autowired
	LeaveEntriesDatewiseService leaveEntriesDatewiseService;

	@Autowired
	HolidayService holidayService;

	@Autowired
	AttendanceRegularizationRequestService attendanceRegularizationRequestService;

	@Autowired
	WeekOffPatternService weekOffPatternService;

	@Autowired
	LeaveEntriesDatewiseRepository leaveEntriesDatewiseRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	private LeavePeriodRepository leavePeriodRepository;

	@Autowired
	private LeaveEntryRepository leaveEntryRepository;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	GradeService gradeService;

	@Autowired
	private CompensatoryOffRepository compensatoryOffRepository;

	@Autowired
	LeaveRulesHdService leaveRulesHdService;

	AttendanceAdaptor attendanceAdaptor = new AttendanceAdaptor();

	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	LeaveAttendanceReportAdapator leaveAttendanceReportAdapator = new LeaveAttendanceReportAdapator();

	@Autowired
	private org.springframework.core.env.Environment environment;
	
	@Autowired
	LeavePeriodService leavePeriodService;
	
	@Autowired
	ProfessionalTaxService professionalTaxService;

	/*
	 * to generate monthly attendance report
	 */

	private void absenteesRule(List<DateWiseAttendanceLogDTO> list, Map<Date, DateWiseAttendanceLogDTO> attendanceMap,
			AttendanceValidationResult attendanceValidationResult) {
		List<Date> holidayAsAbsentDateList = new ArrayList<Date>();
		List<Date> weekoffAsAbsentDateList = new ArrayList<Date>();
		Long holidayAsAbsentDateCount = 0l;
		Long weekoffAbsentDateCount = 0l;
		for (DateWiseAttendanceLogDTO dateWiseAttendanceLogDTO : list) {
			if (dateWiseAttendanceLogDTO.getTitle().equals(StatusMessage.Absent)) {
				boolean conditinCheckflag = true;
				Date dateKey = new Date(dateWiseAttendanceLogDTO.getStart().getTime() - 24 * 60 * 60 * 1000);
				while (conditinCheckflag) {

					if (attendanceMap.containsKey(dateKey) && attendanceMap.get(dateKey).getTitle().equals("W")) {
						if (attendanceValidationResult.isWeekOffBetweenTwoAbsent()) {
							conditinCheckflag = true;
							weekoffAsAbsentDateList.add(dateKey);
							weekoffAbsentDateCount++;
						}
					} else if (attendanceMap.containsKey(dateKey)
							&& attendanceMap.get(dateKey).getTitle().equals("H")) {
						if (attendanceValidationResult.isHolidayBetweenTwoAbsent()) {
							conditinCheckflag = true;
							holidayAsAbsentDateList.add(dateKey);
							holidayAsAbsentDateCount++;
						}
					} else if (attendanceMap.containsKey(dateKey)
							&& attendanceMap.get(dateKey).getTitle().equals(StatusMessage.Absent)) {
						conditinCheckflag = false;
						for (DateWiseAttendanceLogDTO dateWiseAttendanceLogdto : list) {
							for (Date date : weekoffAsAbsentDateList) {
								if (dateWiseAttendanceLogdto.getStart().equals(date)) {
									dateWiseAttendanceLogdto.setTitle(StatusMessage.Absent);
								}
								// attendanceValidationResult.setAbsense(attendanceValidationResult.getAbsense()+1l);
								// attendanceValidationResult.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays()-1l);
							}
							for (Date holidaydate : holidayAsAbsentDateList) {
								if (dateWiseAttendanceLogdto.getStart().equals(holidaydate)) {
									dateWiseAttendanceLogdto.setTitle(StatusMessage.Absent);
									// attendanceValidationResult.setAbsense(attendanceValidationResult.getAbsense()+1l);
									// attendanceValidationResult.setHolidayDays(attendanceValidationResult.getHolidayDays()-1l);;
								}

								// System.out.println(list);
								// holidayAsAbsentDateCount--;
							}

						}
					} else {
						conditinCheckflag = false;
						holidayAsAbsentDateList.clear();
						weekoffAsAbsentDateList.clear();
					}
					dateKey = new Date(dateKey.getTime() - 24 * 60 * 60 * 1000);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateKey);
					calendar.set(Calendar.HOUR, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.HOUR_OF_DAY, 0);

					dateKey = calendar.getTime();
				}

			}

		}
		System.out.println(weekoffAbsentDateCount);
		System.out.println(holidayAsAbsentDateCount);
		attendanceValidationResult.setHolidayAsAbsentDateCount(holidayAsAbsentDateCount);
		attendanceValidationResult.setWeekoffAbsentDateCount(weekoffAbsentDateCount);

	}

	@Override
	public AttendanceLogDTO getAttendanceReportMonthWise(Long employeeId, String processMonth, Long companyId) throws ErrorHandling {
		Map<Date, String> attendanceRegMap = new HashMap<Date, String>();

//		EmployeeDTO employeeDto = getEmployeeByRestTamplate(employeeId.toString());

		Employee employee = employeePersonalInformationService.getEmployeeInfo(employeeId);
		Grade grade = gradeService.findGradeDetails(employee.getGradesId());
		EmployeeDTO employeeDto = employeePersonalInformationAdaptor.databaseModelToUiDto(employee);
		employeeDto.setGradeName(grade.getGradesName());

		AttendanceValidationResult attendanceValidationResult = new AttendanceValidationResult();
		Date currentDate = new Date();
		int currentmonth = currentDate.getMonth();
		Date employeeJoiningDate = employeeDto.getDateOfJoining();
		Date startDate = DateUtils.getDateForProcessMonth(processMonth);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date endDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(processMonth));
		if (currentmonth == employeeJoiningDate.getMonth()
				&& (currentDate.getYear() == employeeJoiningDate.getYear())) {
			startDate = employeeJoiningDate;
		}
		
		
		//here we find leave rulse based on leave period Id
		String psMonthArray[] = processMonth.split("-");
		String psmonth = psMonthArray[0];
		int years =  Integer.valueOf(psMonthArray[1]);
		Calendar cal = Calendar.getInstance();
	     cal.set(Calendar.DATE ,1);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.YEAR,years);
        cal.set(Calendar.MONTH , professionalTaxService.monthToValue(psmonth) );
		
        LocalDate psMonthLocalDate = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        logger.info(" companyId "+ companyId +" psMonthLocalDate "+psMonthLocalDate.toString());
        String formatedPsMonth = psMonthLocalDate.toString();
		TMSLeavePeriod tMSLeavePeriod = leavePeriodService.findLeavePeriodByProcessMonth(companyId, formatedPsMonth);
		
		if(tMSLeavePeriod == null) {
			throw new ErrorHandling("Leave Period Not Found Regarding Process Month");
		}
		logger.info(" tMSLeavePeriod id"+ tMSLeavePeriod.getLeavePeriodId());
		/*
		 find leave rules based on leave period id not by the company id
		 */
		TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHdByLeavePeriodId(tMSLeavePeriod.getLeavePeriodId());
		
		///--///-TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHd(1l);
		boolean weekOffBetweenTwoAbsent = false;
		boolean holidayBetweenTwoAbsent = false;

		for (TMSLeaveRules tmsLeaveRule : tmsLeaveRuleHd.getTmsleaveRules()) {

			if (("HAA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoAbsent = true;
			}
			if (("WAF").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoAbsent = true;
			}
		}
		attendanceValidationResult.setWeekOffBetweenTwoAbsent(weekOffBetweenTwoAbsent);
		attendanceValidationResult.setHolidayBetweenTwoAbsent(holidayBetweenTwoAbsent);
		int month = DateUtils.getMonthForProcessMonth(processMonth);
		int year = DateUtils.getYearForProcessMonth(processMonth);
		Date endDate = new Date();

		List<Object[]> attendanceLogObjList = attendanceLogRepository
				.getAttendanceLogByEmployeeIdandMonthAndYear(employeeId, companyId, month, year);

		// logger.info("get this month attendance log service======");
		List<AttendanceLog> attendanceLogList = attendanceAdaptor.attendanceObjArraytoDtoList(attendanceLogObjList);
		List<TMSLeaveEntriesDatewise> tmsLeaveEntryDateWiseList = leaveEntriesDatewiseService
				.getEmployeeLeaveEntry(employeeId, processMonth);
		int days = 0;
		// according to neelesh sir if salary process in same month then it calculate
		// salary (days calculation) basis on startDate and endDate otherwise it
		// calculate days by process month start date and end date if in it next month
		if (month == (currentmonth + 1)) {
			long numOfDaysBetween = ChronoUnit.DAYS.between(DateUtils.toLocalDate(startDate),
					DateUtils.toLocalDate(endDate));
			days = (int) numOfDaysBetween;
		} else {

			days = DateUtils.findMonthDay(DateUtils.getYear(DateUtils.getDateForProcessMonth(processMonth)),
					DateUtils.getMonth(DateUtils.getDateForProcessMonth(processMonth)));
		}
		endDate = DateUtils.getLastDateOfMonth(DateUtils.getDateForProcessMonth(processMonth));

		DateUtils.getDatesBetweenUsingForFormate(startDate, endDate).forEach(localdate -> {

			DateWiseAttendanceLogDTO dto = new DateWiseAttendanceLogDTO(StatusMessage.Absent,
					DateUtils.getDateFromLocalDateWithYYYYMMDD(localdate), "", "");

			attendanceValidationResult.getAttendanceMap().put(DateUtils.getDateFromLocalDate(localdate), dto);

		});

		List<AttendanceRegularizationRequest> attendanceRegularizationRequestList = attendanceRegularizationRequestService
				.getEmployeeApprovedARRequest(employeeId, companyId, processMonth);

		List<TMSHolidays> tmsHoliday = holidayService.findAllHoliday(companyId);

		// new weeklyOffPattern implements
		List<Object[]> weekOffPattenList = leaveEntryRepository.getWeekOffPatternList(employeeId);
		LeaveBalanceDTO leaveBalanceDto = leaveEntryAdaptor.weekOffPattenObjToUiDto(weekOffPattenList);

		List<TMSWeekOffChildPatternDTO> patternDayList = new ArrayList<>();
		if (leaveBalanceDto.getTmsWeekOffChildPatternDTO() != null) {
			patternDayList = leaveBalanceDto.getTmsWeekOffChildPatternDTO();

		}
		weeklyOfPetternCount(startDate, endDate, patternDayList, attendanceValidationResult.getWeeklyoffdateMap());
		Collections.sort(attendanceLogList, (a1, a2) -> a1.getAttendanceDate().compareTo(a2.getAttendanceDate()));
		Collections.sort(tmsLeaveEntryDateWiseList, (a1, a2) -> a1.getLeaveDate().compareTo(a2.getLeaveDate()));
		Collections.sort(attendanceRegularizationRequestList, (a1, a2) -> a1.getFromDate().compareTo(a2.getFromDate()));
		holidayOfPetternCount(startDate, endDate, tmsHoliday, attendanceValidationResult.getHolidaydateMap());

		attendanceLogList.forEach(attendanceLog -> attendanceValidationResult.getAttendanceLogMap()
				.put(attendanceLog.getAttendanceDate(), attendanceLog.getStatus()));
		attendanceLogList.forEach(attendanceLog -> attendanceValidationResult.getAttendanceLogObjMap()
				.put(attendanceLog.getAttendanceDate(), attendanceLog));
		String employeeCode = null;
		String employeeName = null;
		String departmentName = null;
		String designationName = null;
		String reportingTo = null;
		String jobLocation = null;

		for (AttendanceLog attendanceLog : attendanceLogList) {
			employeeCode = attendanceLog.getEmployeeCode();
			employeeName = attendanceLog.getEmployeeName();
			departmentName = attendanceLog.getDepartmentName();
			designationName = attendanceLog.getDesignationName();
			reportingTo = attendanceLog.getReportingTo();
			jobLocation = attendanceLog.getJobLocation();

		}
		tmsLeaveEntryDateWiseList.forEach(leaves -> {
			attendanceValidationResult.getLeaveEnteriesMap().put(leaves.getLeaveDate(), leaves.getLeaveNature());

		});
		attendanceRegularizationRequestList.forEach(attendanceReg -> {

			DateUtils.getDatesBetweenUsingForFormate(attendanceReg.getFromDate(), attendanceReg.getToDate())
					.forEach(localdate -> {

						if (attendanceValidationResult.getAttendanceLogMap()
								.containsKey(DateUtils.getDateFromLocalDate(localdate)))
							attendanceValidationResult.getAttendanceLogMap()
									.put(DateUtils.getDateFromLocalDate(localdate), "P");
						else
							attendanceValidationResult.getAttendanceLogMap()
									.put(DateUtils.getDateFromLocalDate(localdate), "AR");

					});
		});
		Long sandwitchWeekoffCount = 0L, sandwitchHolidayCount = 0L, halfDays = 0L, arDays = 0L;
		BigDecimal presentDays = new BigDecimal(0);
		BigDecimal leaveDays = new BigDecimal(0);
		BigDecimal weeklyoffDays = new BigDecimal(0);
		BigDecimal holidayDays = new BigDecimal(0);
		BigDecimal absense = new BigDecimal(0);
		BigDecimal totalPresentDays = new BigDecimal(0);
		BigDecimal totalDaysWorked = new BigDecimal(0);
		BigDecimal halfDayCount = new BigDecimal(0);
		BigDecimal arDaysCount = new BigDecimal(0);
		BigDecimal absenseForCalender = new BigDecimal(0);
		AttendanceLogDTO attendance = new AttendanceLogDTO();
		holidayDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getHolidaydateMap(),
				attendance, StatusMessage.holidaysFlag);
		attendanceValidationResult.setHolidayDays(holidayDays.longValue());

		weeklyoffDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getWeeklyoffdateMap(),
				attendance, StatusMessage.weekoffFlag);
		attendanceValidationResult.setWeeklyoffDays(weeklyoffDays.longValue());
		// System.out.println("weeklyoffdateMap :--" + weeklyoffDays);

		leaveDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getLeaveEnteriesMap(),
				attendance, StatusMessage.leavedaysFlag);
		// System.out.println("leaveEnteriesMap :--"+ leaveDays);

//		leaveDays = leaveDays.subtract(attendanceValidationResult.getSandwitchHolidayCount()
//				.subtract(attendanceValidationResult.getSandwitchWeekoffCount()));
//		attendanceValidationResult.setTotleLeaveDays(leaveDays);
		presentDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getAttendanceLogMap(),
				attendance, StatusMessage.presentdaysFlag);
		// System.out.println("attendanceMap :--"+presentDays);

//		BigDecimal holidayWeekoffSum = new BigDecimal(
//				attendanceValidationResult.getHolidayDays() + attendanceValidationResult.getWeeklyoffDays());
//		absense = new BigDecimal(days)
//				.subtract(attendanceValidationResult.getTotleLeaveDays().add((presentDays.add(holidayWeekoffSum))));
//		BigDecimal payDays = attendanceValidationResult.getTotleLeaveDays().add((presentDays.add(holidayWeekoffSum)));

		Collection<DateWiseAttendanceLogDTO> dateWiseAttendanceLog = attendanceValidationResult.getAttendanceMap()
				.values();
		List<DateWiseAttendanceLogDTO> list = new ArrayList<DateWiseAttendanceLogDTO>(dateWiseAttendanceLog);
		Collections.sort(list, (a1, a2) -> a1.getStart().compareTo(a2.getStart()));

//		 //absent count upto current date
		for (DateWiseAttendanceLogDTO dateWiseAttendanceLogDto : list) {
			if ((dateFormat.format(dateWiseAttendanceLogDto.getStart()))
					.compareTo(dateFormat.format(currentDate)) == 0) {
				break;
			}
			if ((dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.Absent))
					|| (dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.SandwitchLOP))) {
				absenseForCalender = absenseForCalender.add(new BigDecimal(1));
			}else if(dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.HalfDay)
					&& !attendanceValidationResult.getLeaveEnteriesMap().containsKey(dateWiseAttendanceLogDto.getStart())
					) {
				absenseForCalender = absenseForCalender.add(new BigDecimal(0.5));
			}else if(dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.HalfDay) 
					&& attendanceValidationResult.getLeaveEnteriesMap().containsKey(dateWiseAttendanceLogDto.getStart()) 
					&& !attendanceValidationResult.getLeaveEnteriesMap().get(dateWiseAttendanceLogDto.getStart()).equals("LF")) {
				absenseForCalender = absenseForCalender.add(new BigDecimal(0.5));
			}
		}

		Map<Date, DateWiseAttendanceLogDTO> attendanceMapobj = new HashMap<Date, DateWiseAttendanceLogDTO>();
		// title setting after current date
		for (DateWiseAttendanceLogDTO dateWiseAttendanceLogDto : list) {

			//Date d1=s
			
			if ((dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.Absent))
					|| (dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.SandwitchLOP))) {
				if ((dateFormat.format(dateWiseAttendanceLogDto.getStart()))
						.compareTo(dateFormat.format(currentDate)) > 0) {
					dateWiseAttendanceLogDto.setTitle("");
				}

			}
			attendanceMapobj.put(dateWiseAttendanceLogDto.getStart(), dateWiseAttendanceLogDto);
		}

		absenteesRule(list, attendanceMapobj, attendanceValidationResult);
		leaveDays = leaveDays.subtract(attendanceValidationResult.getSandwitchHolidayCount()
				.subtract(attendanceValidationResult.getSandwitchWeekoffCount()));
		if(attendanceValidationResult.getTotleLeaveDays()!=null) {
			attendanceValidationResult.setTotleLeaveDays(attendanceValidationResult.getTotleLeaveDays().add(leaveDays));
		}else {
		attendanceValidationResult.setTotleLeaveDays(leaveDays);
		}
		attendanceValidationResult.setHolidayDays(
				attendanceValidationResult.getHolidayDays() - attendanceValidationResult.getHolidayAsAbsentDateCount());
		attendanceValidationResult.setWeeklyoffDays(
				attendanceValidationResult.getWeeklyoffDays() - attendanceValidationResult.getWeekoffAbsentDateCount());

		BigDecimal holidayWeekoffSum = new BigDecimal(
				attendanceValidationResult.getHolidayDays() + attendanceValidationResult.getWeeklyoffDays());

		absense = new BigDecimal(days)
				.subtract(attendanceValidationResult.getTotleLeaveDays().add((presentDays.add(holidayWeekoffSum))));

		// only present days
		// presentDays is calculating (Leave+present+AR+halfday or p/2)

		halfDayCount = new BigDecimal(attendanceValidationResult.getHalfDays());
		arDaysCount = new BigDecimal(attendanceValidationResult.getArDays());
		BigDecimal divisor = new BigDecimal(2.0);
		BigDecimal halfD = halfDayCount.divide(divisor);
		// BigDecimal presentDay=presentDays.add(arDaysCount);
		// BigDecimal onlyPresent =
		// presentDays.subtract((halfDayCount.divide(divisor)).add(arDaysCount));
		BigDecimal onlyPresent = presentDays.subtract(halfDayCount.divide(divisor));
		BigDecimal presentDay = presentDays.add(arDaysCount);
//		absense = new BigDecimal(days)
//				.subtract(attendanceValidationResult.getTotleLeaveDays().add((presentDay.add(holidayWeekoffSum))));

		BigDecimal payDays = attendanceValidationResult.getTotleLeaveDays().add((presentDay.add(holidayWeekoffSum)));

		attendance.setLeaves(attendanceValidationResult.getTotleLeaveDays());
		
		BigDecimal absenseForCal= absenseForCalender;
		
		//BigDecimal absenseEmployeeForCalender=absenseForCal.add(halfDayCount.divide(divisor));
		
		// attendance.setLeaves(leaveDays);
		attendance.setEmployeeId(employeeId);
		attendance.setAbsense(absense);
		attendance.setEmployeeCode(employeeCode);
		attendance.setEmployeeName(employeeName);
		attendance.setWeekoff(new BigDecimal(attendanceValidationResult.getWeeklyoffDays()));
		attendance.setPublicholidays(new BigDecimal(attendanceValidationResult.getHolidayDays()));
		attendance.setPresense(onlyPresent);
		attendance.setHalfD(halfD);
		attendance.setArD(arDaysCount);
		attendance.setProcessMonth(processMonth);
		attendance.setCompanyId(companyId);
		attendance.setPayDays(payDays);
		attendance.setDepartmentName(departmentName);
		attendance.setDesignationName(designationName);
		attendance.setReportingTo(reportingTo);
		attendance.setJobLocation(jobLocation);
		attendance.setTotalPresentDays(presentDay);
		//attendance.setAbsenseEmployeeForCalender(absenseEmployeeForCalender);
		attendance.setAbsenseForCalender(absenseForCalender);
		attendance.setEvents(list);

		return attendance;

	}

	// new weeklyOffPatternRule Implements
	public void weeklyOfPetternCount(Date fromDate, Date toDate, List<TMSWeekOffChildPatternDTO> patternDayList,
			Map<Date, String> weeklyOffMap) {

		List<LocalDate> dateList = DateUtils.getDatesBetweenUsing(fromDate, toDate);

		for (int i = 0; i <= dateList.size() - 1; i++) {

			for (TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO : patternDayList) {

				if (tmsWeekOffChildPatternDTO.getDayName().equalsIgnoreCase(new SimpleDateFormat("E")
						.format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase())) {

					Date weeklyOffDate = DateUtils.findWeeklyOffDate(
							DayEnum.valueOf(tmsWeekOffChildPatternDTO.getDayName()).getDayId(),
							Integer.valueOf(tmsWeekOffChildPatternDTO.getPositionOfDay().toString()),
							dateList.get(i).getMonthValue() - 1, dateList.get(i).getYear());

					Instant instant = Instant.ofEpochMilli(weeklyOffDate.getTime());
					LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
					LocalDate weeklyOffLocalDate = localDateTime.toLocalDate();

					LocalDate leaveApplyDate = dateList.get(i);

					if (weeklyOffLocalDate.compareTo(leaveApplyDate) == 0) {
						weeklyOffMap.put(DateUtils.getDateFromLocalDate(dateList.get(i)), "W");
					}

				}
			}
		}

	}

	public void holidayOfPetternCount(Date fromDate, Date toDate, List<TMSHolidays> holidayList,
			Map<Date, String> holidayMap) {
		List<LocalDate> dateList = DateUtils.getDatesBetweenUsing(fromDate, toDate);
		List<LocalDate> holidaydataList = new ArrayList<LocalDate>();

		for (TMSHolidays holiday : holidayList) {

			// System.out.println("testing holiday form " + holiday.getFromDate() + " -----"
			// + holiday.getToDate());

			holidaydataList.addAll(DateUtils.getDatesBetweenUsing(holiday.getFromDate(), holiday.getToDate()));
		}
		holidaydataList.forEach(list -> {
			if (dateList.contains(list)) {
				holidayMap.put(DateUtils.getDateFromLocalDate(list), "H");
			}
		});
		for (int i = 0; i <= dateList.size() - 1; i++) {
			for (int j = 0; j <= holidaydataList.size() - 1; j++) {
				if (DateUtils.getDateFromLocalDate(dateList.get(i))
						.compareTo(DateUtils.getDateFromLocalDate(holidaydataList.get(j))) == 0) {
					break;
				}
			}
		}
	}

	public BigDecimal AttendanceDate(AttendanceValidationResult attendanceValidationResult, Map<Date, String> mapDate,
			AttendanceLogDTO attendance, String daysMakerFlag) {

		boolean leaveFlag = false, absentFlag = false;
		Long absentDays = 0L;
		Long halfDays = 0L;
		Long arDays = 0L;
		BigDecimal days = new BigDecimal(0);
		BigDecimal leaveDay = new BigDecimal(0);
		for (Map.Entry<Date, DateWiseAttendanceLogDTO> attendanceEntry : attendanceValidationResult.getAttendanceMap()
				.entrySet()) {
			Date dateKey = attendanceEntry.getKey();
			String statusValue = attendanceEntry.getValue().getTitle();
			if (mapDate.containsKey(dateKey)) {// for week-off handling
				if (attendanceValidationResult.getAttendanceMap().get(dateKey).getTitle()
						.equalsIgnoreCase(StatusMessage.LeaveSandwitch)) {
					DateWiseAttendanceLogDTO dto = new DateWiseAttendanceLogDTO();
					dto.setTitle(StatusMessage.LeaveSandwitch);
					dto.setStart(attendanceValidationResult.getAttendanceMap().get(dateKey).getStart());
					dto.setInTime(" ");
					dto.setOutTime(" ");
					dto.setMode(" ");
					// dto.setShift(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getShiftName());
					dto.setTitleValue("Leave as Sandwitch");
					attendanceValidationResult.getAttendanceMap().replace(dateKey, dto);
					// weekoffHolidayDaysCount(attendanceValidationResult,dateKey,StatusMessage.LeaveSandwitch);
					attendanceValidationResult
							.setLeaveDays(attendanceValidationResult.getLeaveDays().add(new BigDecimal(1)));
					leaveFlag = true;
				} else if (attendanceValidationResult.getAttendanceMap().get(dateKey).getTitle()
						.equalsIgnoreCase(StatusMessage.SandwitchLOP)) {
					DateWiseAttendanceLogDTO dto = new DateWiseAttendanceLogDTO();
					dto.setTitle(StatusMessage.SandwitchLOP);
					dto.setStart(attendanceValidationResult.getAttendanceMap().get(dateKey).getStart());
					dto.setInTime(" ");
					dto.setOutTime(" ");
					dto.setMode(" ");
					// dto.setShift(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getShiftName());
					dto.setTitleValue("Leave as Absent");
					weekoffHolidayDaysCount(attendanceValidationResult, dateKey, StatusMessage.SandwitchLOP);
					absentDays++;
					attendanceValidationResult.getAttendanceMap().replace(dateKey, dto);

				} else {
					DateWiseAttendanceLogDTO dto = new DateWiseAttendanceLogDTO();
					dto.setTitle(mapDate.get(dateKey));
					dto.setStart(attendanceValidationResult.getAttendanceMap().get(dateKey).getStart());
					if (attendanceValidationResult.getAttendanceLogObjMap().containsKey(dateKey)) {
						dto.setInTime(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getInTime());
						dto.setOutTime(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getOutTime());
						dto.setShift(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getShiftName());
						dto.setMode(attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getMode());
					}
//					System.out.println("DTO-----------" + dto.getTitle() + "_-----" + dto.getMode() + "----"
//							+ attendanceValidationResult.getAttendanceMap().get(dateKey).getStart() + "----"
//							+ attendance.getOutTime());

					if (dto.getTitle().equals(StatusMessage.Absent)) {
						absentDays++;
					if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)
							&& (dto.getTitle().equals("A")))
						attendanceValidationResult
								.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);
					
					if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey)
							&& (dto.getTitle().equals("A")))
						attendanceValidationResult
								.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);
					
					}
					
					else if (dto.getTitle().equals(StatusMessage.LeaveHalfday) || dto.getTitle().equals("P/2")
							|| dto.getTitle().equals(StatusMessage.LeaveSandwitch)
							) {
						days = days.add(new BigDecimal(0.5));
						halfDays++;
						attendanceValidationResult.setHalfDays(halfDays);
						
						if(dto.getTitle().equals("P/2") && daysMakerFlag.equalsIgnoreCase(StatusMessage.presentdaysFlag)) {
						if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey)
							&&	attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)) {
						days = days.subtract(new BigDecimal(0.5));
						dto.setTitle("H");
						}	
						else if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey)) {
						days = days.subtract(new BigDecimal(0.5));
						dto.setTitle("H");
						}
						else if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)) {
								//attendanceValidationResult.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);
							days = days.subtract(new BigDecimal(0.5));
							dto.setTitle("W");
						}
						
						if (attendanceValidationResult.getLeaveEnteriesMap().containsKey(dateKey)
								&& attendanceValidationResult.getLeaveEnteriesMap().get(dateKey).equals("LF")
								&& (dto.getTitle().equals("P/2") )) {

								attendanceValidationResult.setTotleLeaveDays(
										attendanceValidationResult.getTotleLeaveDays().subtract(new BigDecimal(0.5)));
						}
						}
						
						
					} else if (dto.getTitle().equals(StatusMessage.ATTENDANCE_REGULARIZATION_CODE)) {
						arDays++;
						attendanceValidationResult.setArDays(arDays);

						if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)
								&& (dto.getTitle().equals("AR") || dto.getTitle().equals("P")))
							attendanceValidationResult
									.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);
						
						if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey)
								&& (dto.getTitle().equals("AR")))
							attendanceValidationResult
									.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);
						
						
					}

//					 else if(dto.getTitle().equals(StatusMessage.LeaveHalfday) || dto.getTitle().equals(StatusMessage.HalfDay) ) {
//						 //days = days .add(new BigDecimal(0.5));
//						 days = days.add(new BigDecimal(1));
//						 attendanceValidationResult.setHalfDays(attendanceValidationResult.getHalfDays() + 1);
//						
//					 }
//					 else if(dto.getTitle().equals(StatusMessage.ATTENDANCE_REGULARIZATION_CODE)) {
//						 attendanceValidationResult.setArDays(attendanceValidationResult.getArDays() + 1);
//					 }

					else {
						days = days.add(new BigDecimal(1));
						//
						if (daysMakerFlag.equals(StatusMessage.weekoffFlag)) {
							if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
								attendanceValidationResult
										.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);

						}

						if (daysMakerFlag.equals(StatusMessage.leavedaysFlag)) {// its for all type of leave LF ,LS
							if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey))
								attendanceValidationResult
										.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);

							if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
								attendanceValidationResult
										.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);

							if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)
									&& attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
								attendanceValidationResult
										.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() + 1);
						}

						//
						if (daysMakerFlag.equalsIgnoreCase(StatusMessage.presentdaysFlag)) {// its for all type of leave
																							// LF ,LS
							if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey))
								attendanceValidationResult
										.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);

							if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
								attendanceValidationResult
										.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);

//							if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)
//									&& attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
//								attendanceValidationResult
//										.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() + 1);

							if (attendanceValidationResult.getLeaveEnteriesMap().containsKey(dateKey)
									&& (dto.getTitle().equals("P") || dto.getTitle().equals("AR"))) {

								attendanceValidationResult.setTotleLeaveDays(
										attendanceValidationResult.getTotleLeaveDays().subtract(new BigDecimal(1)));
							}

							if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)
									&& (dto.getTitle().equals("P")))
								attendanceValidationResult
										.setWeeklyoffDays(attendanceValidationResult.getWeeklyoffDays() - 1);

						}

					}
					attendanceValidationResult.getAttendanceMap().replace(dateKey, dto);
				}

			}

		}
		attendanceValidationResult.setAbsense(absentDays);
		if (attendanceValidationResult.getLeaveDays() != null
				&& (attendanceValidationResult.getLeaveDays().compareTo(BigDecimal.ZERO) == 0)) {
			attendanceValidationResult.setLeaveDays(days);
			return attendanceValidationResult.getLeaveDays();
		}

		if (leaveFlag)
			attendance.setLeaves(attendanceValidationResult.getLeaveDays().add(leaveDay));

		return days;

	}

	public void weekoffHolidayDaysCount(AttendanceValidationResult attendanceValidationResult, Date dateKey,
			String status) {
		Boolean weekoffFlag = false;
		Boolean holidayFlag = false;
		if (status == StatusMessage.SandwitchLOP) {
			if (attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateKey)) {
				attendanceValidationResult.setSandwitchWeekoffCount(
						attendanceValidationResult.getSandwitchWeekoffCount().add(new BigDecimal(1)));
				weekoffFlag = true;
			}

			if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey)) {
				attendanceValidationResult.setSandwitchHolidayCount(
						attendanceValidationResult.getSandwitchWeekoffCount().add(new BigDecimal(1)));
				holidayFlag = true;
			}
			if (weekoffFlag && holidayFlag) {
				attendanceValidationResult.setSandwitchWeekoffCount(
						attendanceValidationResult.getSandwitchWeekoffCount().subtract(new BigDecimal(1)));
			}

		}

	}

	public BigDecimal getEmployeeData(int i, List<Object[]> employeeCountList, Long leaveCount, Long totalAc, Long totalDe) {
//		Long totalActive = 0l;
//		Long totalFormer = 0l;
		int len = employeeCountList.size();
		BigDecimal totalFormer = new BigDecimal(totalDe);
		BigDecimal totalActive = new BigDecimal(totalAc);
		BigDecimal empPer = new BigDecimal(0);
		BigDecimal totalleave = new BigDecimal(leaveCount);
//		for (Object[] count : employeeCountList) {
//			if (len >= i) {
//				totalFormer = totalFormer.add(ConverterUtil.getBigDecimal(count[0]));
//				//totalActive = ConverterUtil.getBigDecimal(count[1]);
//			}
//			len--;
//		}
		
		BigDecimal totalEmp= totalActive.add(totalFormer);
		System.out.println("Total Employee Singya Bhalse="+ totalEmp);
//		System.out.println("Total leaveCount ="+ leaveCount);
		if(leaveCount!=0l)
		  empPer=totalleave.divide(totalEmp, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100 )).setScale(2, BigDecimal.ROUND_HALF_UP);
		return empPer;
	}
	
	
	@Override
	public List<EmployeeLeaveGraphDTO> getEmployeeOnLeavePercentage(Long companyId) {
		List<Object[]> empReportList = leaveEntriesDatewiseRepository.getEmployeeOnLeavePercentage(companyId);
		List<EmployeeLeaveGraphDTO> employeeLeaveGraphDTO = new ArrayList<EmployeeLeaveGraphDTO>();
		List<String> months=	leaveEntriesDatewiseRepository.getlastSixProcessMonth();
		List<Object[]> employeeCountList = leaveEntriesDatewiseRepository.getLastSixMonthEmployeeCount(companyId);
		
		
		Map<String, Long> map= new HashMap<>();
		for (Object[] report : empReportList) {
			map.put(ConverterUtil.getString(report[0]).toUpperCase(), ConverterUtil.getLong(report[1]));
		}
		int count =6;
		int interval =1 ;
		for (String month : months) {
			
			EmployeeLeaveGraphDTO empDto = new EmployeeLeaveGraphDTO();
			for (Map.Entry<String, Long> data : map.entrySet()) {
				if (map.containsKey(month)) {
					if (month.equalsIgnoreCase(data.getKey())) {
						System.out.println("Process Month => "+ month);
						Long totalAc = leaveEntriesDatewiseRepository.getActiveEmployeeByDate(Long.valueOf(interval));
						Long totalDe = leaveEntriesDatewiseRepository.getFormerEmployeeByDate(Long.valueOf(interval));
						BigDecimal totalEmp=	getEmployeeData(count, employeeCountList,data.getValue(), totalAc, totalDe);
						totalEmp.setScale(2, BigDecimal.ROUND_HALF_UP);
						Long leaveCount = 0L;
						empDto.setMonth(data.getKey());
						System.out.println("Total Employee % ="+ totalEmp);
						empDto.setEmployeeOnLeavePercentage(totalEmp);
						employeeLeaveGraphDTO.add(empDto);
						
						break;
					}
				} else {
					Long leaveCount = 0L;
					empDto.setMonth(month);
					// empCount = ConverterUtil.getLong(report[2]);

					empDto.setEmployeeOnLeavePercentage(new BigDecimal(0));

					employeeLeaveGraphDTO.add(empDto);
					break;
				}

			}
			count--;
			 interval++;
		}
		return employeeLeaveGraphDTO;
	}

	@Override
	public List<EmployeeLeaveGraphDTO> getLeaveTakenByLeaveType(Long companyId) {
		List<Object[]> empReportList = leaveEntriesDatewiseRepository.getLeaveTakenByLeaveType(companyId);
		List<EmployeeLeaveGraphDTO> employeeLeaveGraphDTO = new ArrayList<EmployeeLeaveGraphDTO>();

		for (Object[] report : empReportList) {

			EmployeeLeaveGraphDTO empDto = new EmployeeLeaveGraphDTO();

			empDto.setLeaveType(ConverterUtil.getString(report[0]));
			empDto.setLeaveTypeCount(ConverterUtil.getString(report[1]));
			employeeLeaveGraphDTO.add(empDto);

		}

		return employeeLeaveGraphDTO;
	}

	@Override
	public List<EmployeeLeaveGraphDTO> getEmployeeOnAbsent(Long companyId) {
		List<Object[]> empReportList = leaveEntriesDatewiseRepository.getEmployeeOnAbsent(companyId);
		List<EmployeeLeaveGraphDTO> employeeLeaveGraphDTO = new ArrayList<EmployeeLeaveGraphDTO>();
		List<Object[]> employeeCountList = leaveEntriesDatewiseRepository.getLastSixMonthEmployeeCount(companyId);
		int count=6;
		Long interval =1l;
		for (Object[] report : empReportList) {
 
			EmployeeLeaveGraphDTO empDto = new EmployeeLeaveGraphDTO();
			Long totalAc = leaveEntriesDatewiseRepository.getActiveEmployeeByDate(interval);
			Long totalDe = leaveEntriesDatewiseRepository.getFormerEmployeeByDate(interval);
//			Long empCount = ConverterUtil.getLong(ConverterUtil.getString(report[0]));
			String pMonth = ConverterUtil.getString(report[1]);
			Long empcount = ConverterUtil.getLong(report[0]);
			BigDecimal totalEmp=	getEmployeeData(count, employeeCountList,empcount, totalAc, totalDe);
//			String[] parts = pMonth.split("-");
//			String month = parts[0];// MAY
			empDto.setMonth(pMonth);
			
//			if (empAbsent != 0L)
				empDto.setEmployeeOnAbsent(totalEmp);
//			else
//				empDto.setEmployeeOnAbsent(0L);
			employeeLeaveGraphDTO.add(empDto);
			interval=interval++;
		}

		return employeeLeaveGraphDTO;
	}

	@Override
	public List<EmployeeLeaveGraphDTO> getEmployeeFrequentLeaveTaker(Long companyId) {
		List<Object[]> empReportList = leaveEntriesDatewiseRepository.getEmployeeFrequentLeaveTaker(companyId);
		List<EmployeeLeaveGraphDTO> employeeLeaveGraphDTO = new ArrayList<EmployeeLeaveGraphDTO>();

		for (Object[] report : empReportList) {

			EmployeeLeaveGraphDTO empDto = new EmployeeLeaveGraphDTO();
			Long leaveCount = report[0] != null ? Long.parseLong(report[0].toString()) : null;
			if (leaveCount >= 3) {
				empDto.setFreLeaveCount(leaveCount.toString());
				empDto.setEmpName(ConverterUtil.getString(report[1]));
				employeeLeaveGraphDTO.add(empDto);
			} else
				continue;

		}

		return employeeLeaveGraphDTO;
	}

	@Override
	public List<AttendanceLogDTO> getLateComersReport(Long companyId, Long empId, List<Long> deptList, Date fDate,
			Date tDate) {
		List<Object[]> empLateComerList = new ArrayList<Object[]>();
		if (deptList.size() > 0) {
			empLateComerList = attendanceLogRepository.getLateComersListFromAttendanceLogDepartmentWise(companyId,
					fDate, tDate, deptList);
		} else if (empId != null) {
			empLateComerList = attendanceLogRepository.getLateComersListFromAttendanceLogEmployeeWise(companyId, fDate,
					tDate, empId);
		}
		logger.info("lateComerList is calling : " + empLateComerList);
		List<AttendanceLogDTO> empLateComerDTOList = new ArrayList<AttendanceLogDTO>();

		for (Object[] report : empLateComerList) {
			AttendanceLogDTO dto = new AttendanceLogDTO();
			Date attDate = report[0] != null ? ConverterUtil.getDate(report[0].toString()) : null;
			String repName = report[6] != null ? (String) report[6] : "";
			String shiftName = report[7] != null ? (String) report[7] : null;
			String shiftDuration = report[8] != null ? (String) report[8] : null;
			String timeIn = report[9] != null ? (String) report[9] : null;
			String timeOut = report[10] != null ? (String) report[10] : null;
			String totalTime = report[11] != null ? (String) report[11] : null;
			// String delayedTime = report[12] != null ? (String) report[12] : null;
			String status = report[13] != null ? (String) report[13] : null;

			dto.setAttendanceDate(attDate);
			dto.setEmployeeCode(report[1].toString());
			dto.setEmployeeName(report[2].toString());
			dto.setDepartmentName(report[3].toString());
			dto.setDesignationName(report[4].toString());
			dto.setJobLocation(report[5].toString());
			dto.setReportingTo(repName);
			dto.setShiftName(shiftName);
			dto.setShiftDuration(shiftDuration);

//			dto.setInTime(report[9].toString());
//			dto.setOutTime(report[10].toString());

			String time1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date3 = sdf.parse(timeIn);
				SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss aa");
				time1 = sdf2.format(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setInTime(time1);

			String time2 = null;
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
			try {
				Date date = sd.parse(timeOut);
				SimpleDateFormat sd2 = new SimpleDateFormat("hh:mm:ss aa");
				time2 = sd2.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setOutTime(time2);

			dto.setTotalTime(totalTime);
			dto.setDelayedTime(report[12].toString());

			String lateStatus = null;

			if (status.equalsIgnoreCase(StatusMessage.PRESENT_CODE)) {
				lateStatus = StatusMessage.PRESENT_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.HALFDAY_CODE)) {
				lateStatus = StatusMessage.HALFDAY_VALUE;
			} else if (status.equalsIgnoreCase(StatusMessage.ABSENT_CODE)) {
				lateStatus = StatusMessage.ABSENT_VALUE;
			}

			dto.setStatus(lateStatus);

			empLateComerDTOList.add(dto);
		}
		return empLateComerDTOList;
	}

	/**
	 * @param employeeId to get employee object based on employee id
	 */

	private EmployeeDTO getEmployeeByRestTamplate(String employeeId) {
		String url = environment.getProperty("application.employeeInfoTemp");
		// String url = "http://localhost:8080/hrmsApi/employee/{employeeId}";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, String> params = new HashMap<>();
		params.put("employeeId", employeeId);
		return restTemplate.getForObject(url, EmployeeDTO.class, params);
	}

	public static String getString(Object value) {
		String ret = null;
		if (value != null) {
			ret = String.valueOf(value);

		}
		return ret;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<AttendanceDTO> getLeaveEntitlementAndBalanceSummaryReport(Long companyId, Long employeeId,
			List<Long> departmentId, String[] typesOfLeavesId, Long leavePeriodId, String[] typesOfLeavesIdEntitlement,
			List<TMSLeavePeriod> leavePeriodList) {
		if (employeeId.longValue() > 0) {
			List<Object[]> employeeData = attendanceRepository.findLeaveBalanceSummaryEmployeeWise(companyId,
					employeeId, leavePeriodId);

			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveEntitlementReport(employeeData);

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveTypeMap = new HashMap<>();
				Map<String, List<BigDecimal>> leaveEntitlementTypeMap = new HashMap<>();

				BigDecimal carryForwardTotal = new BigDecimal(0.0);
				BigDecimal consumedTotal = new BigDecimal(0.0);
				BigDecimal balanceTotal = new BigDecimal(0.0);
				BigDecimal annualQuotaTotal = new BigDecimal(0.0);
				BigDecimal consumedLeaveTotal = new BigDecimal(0.0);
				BigDecimal leaveBalance = new BigDecimal(0.0);
				BigDecimal leaveAnnualQuota = new BigDecimal(0.0);
				BigDecimal leaveConsume = new BigDecimal(0.0);

				for (TMSLeavePeriod tmsLeavePeriod : leavePeriodList) {
					Long tmsLeavePeriodId = tmsLeavePeriod.getLeavePeriodId();
					String status = tmsLeavePeriod.getActiveStatus();
					if (tmsLeavePeriodId.equals(leavePeriodId)) {
						if (status.equals("AC")) {
							List<Object[]> leaveEntitilementList = attendanceRepository
									.findLeaveEntitlementByActiveSession(companyId, employeeId, leavePeriodId);
							// leaveEntitilementList.addAll(leaveEntitilementLists);

							for (Object[] leaveEntitlement : leaveEntitilementList) {

								List<BigDecimal> leaveTypeList = new ArrayList<>();

								BigDecimal carryForward = leaveEntitlement[1] != null
										? (new BigDecimal(leaveEntitlement[1].toString()))
										: new BigDecimal(0.0);
								BigDecimal consumed = leaveEntitlement[2] != null
										? (new BigDecimal(leaveEntitlement[2].toString()))
										: new BigDecimal(0.0);
								BigDecimal balance = leaveEntitlement[3] != null
										? (new BigDecimal(leaveEntitlement[3].toString()))
										: new BigDecimal(0.0);
								BigDecimal annualQuota = leaveEntitlement[4] != null
										? (new BigDecimal(leaveEntitlement[4].toString()))
										: new BigDecimal(0.0);
								Long leaveTypeMasterId = leaveEntitlement[0] != null
										? Long.parseLong(leaveEntitlement[0].toString())
										: null;
								String leaveMode = leaveEntitlement[5] != null ? (String) leaveEntitlement[5] : null;
								Long indexDays = leaveEntitlement[6] != null
										? Long.parseLong(leaveEntitlement[6].toString())
										: null;
								String nature = leaveEntitlement[7] != null ? (getString(leaveEntitlement[7])) : null;
								Long yearlyLimit = leaveEntitlement[8] != null
										? Long.parseLong(leaveEntitlement[8].toString())
										: null;
								Date leavePeriodStartDate = leaveEntitlement[9] != null ? (Date) (leaveEntitlement[9])
										: null;
								Date leavePeriodEndDate = leaveEntitlement[10] != null ? (Date) (leaveEntitlement[10])
										: null;
								Date employeeJoiningDate = leaveEntitlement[11] != null ? (Date) (leaveEntitlement[11])
										: null;
								Long encashLimit = leaveEntitlement[12] != null
										? (Long.parseLong(leaveEntitlement[12].toString()))
										: null;
								BigDecimal openingLeave = leaveEntitlement[13] != null
										? (new BigDecimal(leaveEntitlement[13].toString()))
										: null;
								String leaveCalculationOnProbation = leaveEntitlement[14] != null
										? (getString(leaveEntitlement[14]))
										: null;
								Integer probabtionDays = leaveEntitlement[15] != null
										? Integer.parseInt(leaveEntitlement[15].toString())
										: null;

								attendanceDTO.setLeaveMode(leaveMode);
								attendanceDTO.setNature(nature);
								attendanceDTO.setEncashLimit(encashLimit);
								attendanceDTO.setYearlyLimit(yearlyLimit);
								attendanceDTO.setLeaveId(leaveTypeMasterId);

								Date leaveCalculationDate = null;
								if (leaveCalculationOnProbation.equals("Y")) {
									leaveCalculationDate = employeeJoiningDate;
								} else {
									int days = probabtionDays;
									leaveCalculationDate = employeeJoiningDate;
									GregorianCalendar cal = new GregorianCalendar();
									cal.setTime(leaveCalculationDate);
									cal.add(Calendar.DATE, days);
									leaveCalculationDate = cal.getTime();

								}

								if (leaveTypeMasterId != 7) {

									if (leaveCalculationDate.after(leavePeriodStartDate)
											|| leaveCalculationDate.compareTo(leavePeriodStartDate) == 0) {

										long timeDiff = Math
												.abs(leavePeriodEndDate.getTime() - leaveCalculationDate.getTime());
										double empJoinedDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;

										long leavePeriodTimeDiff = Math
												.abs(leavePeriodEndDate.getTime() - leavePeriodStartDate.getTime());
										double leavePeriodTimeDiffDays = Math
												.ceil(leavePeriodTimeDiff / (1000 * 3600 * 24)) + 1;

										// * Date 08/08/2019 change balance summury accroding to opening leave for
										// employee joining date after leave period date according to neelesh sir**/ *//
										// totalleave=(new BigDecimal(empJoinedDays).multiply(new
										// BigDecimal(totalleave)).divide(new BigDecimal(leavePeriodTimeDiffDays),
										// 2,RoundingMode.CEILING)).longValue();
										annualQuota = (new BigDecimal(empJoinedDays)
												.multiply(new BigDecimal(yearlyLimit))
												.divide(new BigDecimal(leavePeriodTimeDiffDays), 2,
														RoundingMode.CEILING));
										// totalleave=totalleave.subtract(openingLeave);

										MathContext m = new MathContext(2);
										annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);
										;
										// totalleave=totalleave.setScale(2, BigDecimal.ROUND_HALF_UP);

										if (leaveMode.equals("IN")) {
											// totalleave = new BigDecimal(1.0);
											BigDecimal totalLeave = new BigDecimal(1.0);
											long timeDiffIndex = Math
													.abs(new Date().getTime() - leaveCalculationDate.getTime());
											double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24))
													+ 1;

											BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays))
													.divide(new BigDecimal(indexDays), 2, RoundingMode.CEILING);

											if (indexdays.compareTo(annualQuota) > 0) {
												annualQuota = indexdays;
												Double d = new Double(Math.round(annualQuota.doubleValue()));
												annualQuota = BigDecimal.valueOf(d).add(totalLeave);
												if (yearlyLimit <= (annualQuota.longValue())) {
													annualQuota = BigDecimal.valueOf(yearlyLimit);
												}
											} else {
												// totalLeave = totalLeave.add(indexdays);
												annualQuota = totalLeave.add(indexdays);
												annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);
												// totalleave=totalleave.add(carryForwordLeave);
											}
											annualQuota = annualQuota.add(carryForward);
										}
										if (leaveCalculationDate.after(new Date())
												&& leaveCalculationOnProbation.equals("N")) {
											annualQuota = new BigDecimal(0);

										}

									} else {
										if (leaveMode.equals("IN")) {
											BigDecimal totalLeave = new BigDecimal(1.0);
											long timeDiffIndex = Math
													.abs(new Date().getTime() - leavePeriodStartDate.getTime());
											double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24))
													+ 1;

											BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays))
													.divide(new BigDecimal(indexDays), 2, RoundingMode.CEILING);

											if (indexdays.compareTo(annualQuota) > 0) {
												annualQuota = indexdays;
												Double d = new Double(Math.round(annualQuota.doubleValue()));
												annualQuota = BigDecimal.valueOf(d).add(totalLeave);
												// totalleave.add(totalLeave);
											} else {
												annualQuota = totalLeave.add(indexdays);
												annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);

											}
											annualQuota = annualQuota.add(carryForward);
										}
									}
								}

								balance = annualQuota.subtract(consumed);
								annualQuota = annualQuota.setScale(2, BigDecimal.ROUND_HALF_UP);

//												if(!(leaveEntitlement[0]).equals(6) || !(leaveEntitlement[0]).equals(7))
//												{
								if ((leaveEntitlement[0]).equals(1) || (leaveEntitlement[0]).equals(2)
										|| (leaveEntitlement[0]).equals(3)) {
									attendanceDTO.setCarryForward(carryForward);
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);

									leaveTypeList.add(carryForward);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);

									carryForwardTotal = carryForwardTotal.add(carryForward);
									consumedTotal = consumedTotal.add(consumed);
									balanceTotal = balanceTotal.add(balance);
									annualQuotaTotal = annualQuotaTotal.add(annualQuota);
								}

								if ((leaveEntitlement[0]).equals(6)) {
									attendanceDTO.setConsumed(consumed);
									leaveTypeList.add(consumed);
									consumedLeaveTotal = consumedLeaveTotal.add(consumed);
								}

								if ((leaveEntitlement[0]).equals(7)) {
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);
									leaveConsume = leaveConsume.add(consumed);
									leaveBalance = leaveBalance.add(balance);
									leaveAnnualQuota = leaveAnnualQuota.add(annualQuota);
								}

								leaveTypeMap.put(leaveEntitlement[0].toString(), leaveTypeList);
							}
							attendanceDTO.setAnnualQuotaTotal(annualQuotaTotal.add(leaveAnnualQuota));
							attendanceDTO.setCarryForwardTotal(carryForwardTotal);
							attendanceDTO.setConsumedTotal(consumedTotal.add(consumedLeaveTotal).add(leaveConsume));
							attendanceDTO.setBalanceTotal(balanceTotal.add(leaveBalance));
							attendanceDTO.setLeaveTypeMap(leaveTypeMap);
							attendanceDTO.setLeaveEntitlementTypeMap(leaveEntitlementTypeMap);
							attendanceDTO.setConsumedLeaveTotal(consumedLeaveTotal);

						}
						if (status.equals("DE")) {
							List<Object[]> leaveEntitilementList = attendanceRepository
									.findLeaveEntitlementByDeactiveSession(companyId, employeeId, leavePeriodId);
							// leaveEntitilementList.addAll(leaveEntitilementLists);

							for (Object[] leaveEntitlement : leaveEntitilementList) {

								List<BigDecimal> leaveTypeList = new ArrayList<>();

								Long leaveTypeMasterId = leaveEntitlement[0] != null
										? Long.parseLong(leaveEntitlement[0].toString())
										: null;
								BigDecimal carryForward = leaveEntitlement[1] != null
										? (new BigDecimal(leaveEntitlement[1].toString()))
										: new BigDecimal(0.0);
								BigDecimal consumed = leaveEntitlement[2] != null
										? (new BigDecimal(leaveEntitlement[2].toString()))
										: new BigDecimal(0.0);
								BigDecimal balance = leaveEntitlement[3] != null
										? (new BigDecimal(leaveEntitlement[3].toString()))
										: new BigDecimal(0.0);
								BigDecimal annualQuota = leaveEntitlement[4] != null
										? (new BigDecimal(leaveEntitlement[4].toString()))
										: new BigDecimal(0.0);

								if ((leaveEntitlement[0]).equals(1) || (leaveEntitlement[0]).equals(2)
										|| (leaveEntitlement[0]).equals(3)) {
									attendanceDTO.setCarryForward(carryForward);
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);

									leaveTypeList.add(carryForward);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);

									carryForwardTotal = carryForwardTotal.add(carryForward);
									consumedTotal = consumedTotal.add(consumed);
									balanceTotal = balanceTotal.add(balance);
									annualQuotaTotal = annualQuotaTotal.add(annualQuota);
								}

								if ((leaveEntitlement[0]).equals(6)) {
									attendanceDTO.setConsumed(consumed);
									leaveTypeList.add(consumed);
									consumedLeaveTotal = consumedLeaveTotal.add(consumed);
								}

								if ((leaveEntitlement[0]).equals(7)) {
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);
									leaveConsume = leaveConsume.add(consumed);
									leaveBalance = leaveBalance.add(balance);
									leaveAnnualQuota = leaveAnnualQuota.add(annualQuota);
								}

								leaveTypeMap.put(leaveEntitlement[0].toString(), leaveTypeList);

							}
							attendanceDTO.setAnnualQuotaTotal(annualQuotaTotal.add(leaveAnnualQuota));
							attendanceDTO.setCarryForwardTotal(carryForwardTotal);
							attendanceDTO.setConsumedTotal(consumedTotal.add(consumedLeaveTotal).add(leaveConsume));
							attendanceDTO.setBalanceTotal(balanceTotal.add(leaveBalance));
							attendanceDTO.setLeaveTypeMap(leaveTypeMap);
							attendanceDTO.setLeaveEntitlementTypeMap(leaveEntitlementTypeMap);
							attendanceDTO.setConsumedLeaveTotal(consumedLeaveTotal);

						}
					}
				}

			}
			return attendanceDTOList;
		}

		else if (departmentId.size() > 0) {
			List<Object[]> employeeData = attendanceRepository.findLeaveBalanceSummaryDepartmentWise(companyId,
					departmentId, leavePeriodId);
			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveEntitlementReport(employeeData);
			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveTypeMap = new HashMap<>();
				Map<String, List<BigDecimal>> leaveEntitlementTypeMap = new HashMap<>();

				BigDecimal carryForwardTotal = new BigDecimal(0.0);
				BigDecimal consumedTotal = new BigDecimal(0.0);
				BigDecimal balanceTotal = new BigDecimal(0.0);
				BigDecimal annualQuotaTotal = new BigDecimal(0.0);
				BigDecimal consumedLeaveTotal = new BigDecimal(0.0);
				BigDecimal leaveBalance = new BigDecimal(0.0);
				BigDecimal leaveAnnualQuota = new BigDecimal(0.0);
				BigDecimal leaveConsume = new BigDecimal(0.0);

				// List<Object[]> leaveEntitilementList = new ArrayList<>();
				for (TMSLeavePeriod tmsLeavePeriod : leavePeriodList) {
					Long tmsLeavePeriodId = tmsLeavePeriod.getLeavePeriodId();
					String status = tmsLeavePeriod.getActiveStatus();
					if (tmsLeavePeriodId.equals(leavePeriodId)) {
						if (status.equals("AC")) {
							List<Object[]> leaveEntitilementList = attendanceRepository
									.findLeaveEntitlementByActiveSession(companyId, attendanceDTO.getEmployeeId(),
											leavePeriodId);
							// leaveEntitilementList.addAll(leaveEntitilementLists);
							for (Object[] leaveEntitlement : leaveEntitilementList) {

								List<BigDecimal> leaveTypeList = new ArrayList<>();

								BigDecimal carryForward = leaveEntitlement[1] != null
										? (new BigDecimal(leaveEntitlement[1].toString()))
										: new BigDecimal(0.0);
								BigDecimal consumed = leaveEntitlement[2] != null
										? (new BigDecimal(leaveEntitlement[2].toString()))
										: new BigDecimal(0.0);
								BigDecimal balance = leaveEntitlement[3] != null
										? (new BigDecimal(leaveEntitlement[3].toString()))
										: new BigDecimal(0.0);
								BigDecimal annualQuota = leaveEntitlement[4] != null
										? (new BigDecimal(leaveEntitlement[4].toString()))
										: new BigDecimal(0.0);
								Long leaveTypeMasterId = leaveEntitlement[0] != null
										? Long.parseLong(leaveEntitlement[0].toString())
										: null;
								String leaveMode = leaveEntitlement[5] != null ? (String) leaveEntitlement[5] : null;
								Long indexDays = leaveEntitlement[6] != null
										? Long.parseLong(leaveEntitlement[6].toString())
										: null;
								String nature = leaveEntitlement[7] != null ? (getString(leaveEntitlement[7])) : null;
								Long yearlyLimit = leaveEntitlement[8] != null
										? Long.parseLong(leaveEntitlement[8].toString())
										: null;
								Date leavePeriodStartDate = leaveEntitlement[9] != null ? (Date) (leaveEntitlement[9])
										: null;
								Date leavePeriodEndDate = leaveEntitlement[10] != null ? (Date) (leaveEntitlement[10])
										: null;
								Date employeeJoiningDate = leaveEntitlement[11] != null ? (Date) (leaveEntitlement[11])
										: null;
								Long encashLimit = leaveEntitlement[12] != null
										? (Long.parseLong(leaveEntitlement[12].toString()))
										: null;
								BigDecimal openingLeave = leaveEntitlement[13] != null
										? (new BigDecimal(leaveEntitlement[13].toString()))
										: null;
								String leaveCalculationOnProbation = leaveEntitlement[14] != null
										? (getString(leaveEntitlement[14]))
										: null;
								Integer probabtionDays = leaveEntitlement[15] != null
										? Integer.parseInt(leaveEntitlement[15].toString())
										: null;

								attendanceDTO.setLeaveMode(leaveMode);
								attendanceDTO.setNature(nature);
								attendanceDTO.setEncashLimit(encashLimit);
								attendanceDTO.setYearlyLimit(yearlyLimit);
								attendanceDTO.setLeaveId(leaveTypeMasterId);

								Date leaveCalculationDate = null;
								if (leaveCalculationOnProbation.equals("Y")) {
									leaveCalculationDate = employeeJoiningDate;
								} else {

									if (probabtionDays != null) {
										int days = probabtionDays;
										leaveCalculationDate = employeeJoiningDate;
										GregorianCalendar cal = new GregorianCalendar();
										cal.setTime(leaveCalculationDate);
										cal.add(Calendar.DATE, days);
										leaveCalculationDate = cal.getTime();
									} else {
										int days = 0;
										leaveCalculationDate = employeeJoiningDate;
										GregorianCalendar cal = new GregorianCalendar();
										cal.setTime(leaveCalculationDate);
										cal.add(Calendar.DATE, days);
										leaveCalculationDate = cal.getTime();
									}

								}

								if (leaveTypeMasterId != 7) {

									if (leaveCalculationDate.after(leavePeriodStartDate)
											|| leaveCalculationDate.compareTo(leavePeriodStartDate) == 0) {

										long timeDiff = Math
												.abs(leavePeriodEndDate.getTime() - leaveCalculationDate.getTime());
										double empJoinedDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;

										long leavePeriodTimeDiff = Math
												.abs(leavePeriodEndDate.getTime() - leavePeriodStartDate.getTime());
										double leavePeriodTimeDiffDays = Math
												.ceil(leavePeriodTimeDiff / (1000 * 3600 * 24)) + 1;

										// * Date 08/08/2019 change balance summury accroding to opening leave for
										// employee joining date after leave period date according to neelesh sir**/ *//
										// totalleave=(new BigDecimal(empJoinedDays).multiply(new
										// BigDecimal(totalleave)).divide(new BigDecimal(leavePeriodTimeDiffDays),
										// 2,RoundingMode.CEILING)).longValue();
										annualQuota = (new BigDecimal(empJoinedDays)
												.multiply(new BigDecimal(yearlyLimit))
												.divide(new BigDecimal(leavePeriodTimeDiffDays), 2,
														RoundingMode.CEILING));
										// totalleave=totalleave.subtract(openingLeave);

										MathContext m = new MathContext(2);
										annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);
										;
										// totalleave=totalleave.setScale(2, BigDecimal.ROUND_HALF_UP);

										if (leaveMode.equals("IN")) {
											// totalleave = new BigDecimal(1.0);
											BigDecimal totalLeave = new BigDecimal(1.0);
											long timeDiffIndex = Math
													.abs(new Date().getTime() - leaveCalculationDate.getTime());
											double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24))
													+ 1;

											BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays))
													.divide(new BigDecimal(indexDays), 2, RoundingMode.CEILING);

											if (indexdays.compareTo(annualQuota) > 0) {
												annualQuota = indexdays;
												Double d = new Double(Math.round(annualQuota.doubleValue()));
												annualQuota = BigDecimal.valueOf(d).add(totalLeave);
												if (yearlyLimit <= (annualQuota.longValue())) {
													annualQuota = BigDecimal.valueOf(yearlyLimit);
												}
											} else {
												// totalLeave = totalLeave.add(indexdays);
												annualQuota = totalLeave.add(indexdays);
												annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);
												// totalleave=totalleave.add(carryForwordLeave);
											}
											annualQuota = annualQuota.add(carryForward);
										}
										if (leaveCalculationDate.after(new Date())
												&& leaveCalculationOnProbation.equals("N")) {
											annualQuota = new BigDecimal(0);

										}

									} else {
										if (leaveMode.equals("IN")) {
											BigDecimal totalLeave = new BigDecimal(1.0);
											long timeDiffIndex = Math
													.abs(new Date().getTime() - leavePeriodStartDate.getTime());
											double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24))
													+ 1;

											BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays))
													.divide(new BigDecimal(indexDays), 2, RoundingMode.CEILING);

											if (indexdays.compareTo(annualQuota) > 0) {
												annualQuota = indexdays;
												Double d = new Double(Math.round(annualQuota.doubleValue()));
												annualQuota = BigDecimal.valueOf(d).add(totalLeave);
												// totalleave.add(totalLeave);
											} else {
												annualQuota = totalLeave.add(indexdays);
												annualQuota = annualQuota.setScale(0, RoundingMode.HALF_UP);

											}
											annualQuota = annualQuota.add(carryForward);
										}
									}
								}

								balance = annualQuota.subtract(consumed);
								annualQuota = annualQuota.setScale(2, BigDecimal.ROUND_HALF_UP);

//												if(!(leaveEntitlement[0]).equals(6) || !(leaveEntitlement[0]).equals(7))
//												{
								if ((leaveEntitlement[0]).equals(1) || (leaveEntitlement[0]).equals(2)
										|| (leaveEntitlement[0]).equals(3)) {
									attendanceDTO.setCarryForward(carryForward);
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);

									leaveTypeList.add(carryForward);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);

									carryForwardTotal = carryForwardTotal.add(carryForward);
									consumedTotal = consumedTotal.add(consumed);
									balanceTotal = balanceTotal.add(balance);
									annualQuotaTotal = annualQuotaTotal.add(annualQuota);
								}

								if ((leaveEntitlement[0]).equals(6)) {
									attendanceDTO.setConsumed(consumed);
									leaveTypeList.add(consumed);
									consumedLeaveTotal = consumedLeaveTotal.add(consumed);
								}

								if ((leaveEntitlement[0]).equals(7)) {
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);
									leaveConsume = leaveConsume.add(consumed);
									leaveBalance = leaveBalance.add(balance);
									leaveAnnualQuota = leaveAnnualQuota.add(annualQuota);
								}

								leaveTypeMap.put(leaveEntitlement[0].toString(), leaveTypeList);
							}
							attendanceDTO.setAnnualQuotaTotal(annualQuotaTotal.add(leaveAnnualQuota));
							attendanceDTO.setCarryForwardTotal(carryForwardTotal);
							attendanceDTO.setConsumedTotal(consumedTotal.add(consumedLeaveTotal).add(leaveConsume));
							attendanceDTO.setBalanceTotal(balanceTotal.add(leaveBalance));
							attendanceDTO.setLeaveTypeMap(leaveTypeMap);
							attendanceDTO.setLeaveEntitlementTypeMap(leaveEntitlementTypeMap);
							attendanceDTO.setConsumedLeaveTotal(consumedLeaveTotal);
						}
						if (status.equals("DE")) {
							List<Object[]> leaveEntitilementList = attendanceRepository
									.findLeaveEntitlementByDeactiveSession(companyId, attendanceDTO.getEmployeeId(),
											leavePeriodId);
							// leaveEntitilementList.addAll(leaveEntitilementLists);
							for (Object[] leaveEntitlement : leaveEntitilementList) {

								List<BigDecimal> leaveTypeList = new ArrayList<>();

								Long leaveTypeMasterId = leaveEntitlement[0] != null
										? Long.parseLong(leaveEntitlement[0].toString())
										: null;
								BigDecimal carryForward = leaveEntitlement[1] != null
										? (new BigDecimal(leaveEntitlement[1].toString()))
										: new BigDecimal(0.0);
								BigDecimal consumed = leaveEntitlement[2] != null
										? (new BigDecimal(leaveEntitlement[2].toString()))
										: new BigDecimal(0.0);
								BigDecimal balance = leaveEntitlement[3] != null
										? (new BigDecimal(leaveEntitlement[3].toString()))
										: new BigDecimal(0.0);
								BigDecimal annualQuota = leaveEntitlement[4] != null
										? (new BigDecimal(leaveEntitlement[4].toString()))
										: new BigDecimal(0.0);

								if ((leaveEntitlement[0]).equals(1) || (leaveEntitlement[0]).equals(2)
										|| (leaveEntitlement[0]).equals(3)) {
									attendanceDTO.setCarryForward(carryForward);
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);

									leaveTypeList.add(carryForward);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);

									carryForwardTotal = carryForwardTotal.add(carryForward);
									consumedTotal = consumedTotal.add(consumed);
									balanceTotal = balanceTotal.add(balance);
									annualQuotaTotal = annualQuotaTotal.add(annualQuota);
								}

								if ((leaveEntitlement[0]).equals(6)) {
									attendanceDTO.setConsumed(consumed);
									leaveTypeList.add(consumed);
									consumedLeaveTotal = consumedLeaveTotal.add(consumed);
								}

								if ((leaveEntitlement[0]).equals(7)) {
									attendanceDTO.setConsumed(consumed);
									attendanceDTO.setBalance(balance);
									attendanceDTO.setAnnualQuota(annualQuota);
									leaveTypeList.add(consumed);
									leaveTypeList.add(balance);
									leaveTypeList.add(annualQuota);
									leaveConsume = leaveConsume.add(consumed);
									leaveBalance = leaveBalance.add(balance);
									leaveAnnualQuota = leaveAnnualQuota.add(annualQuota);
								}

								leaveTypeMap.put(leaveEntitlement[0].toString(), leaveTypeList);

							}
							attendanceDTO.setAnnualQuotaTotal(annualQuotaTotal.add(leaveAnnualQuota));
							attendanceDTO.setCarryForwardTotal(carryForwardTotal);
							attendanceDTO.setConsumedTotal(consumedTotal.add(consumedLeaveTotal).add(leaveConsume));
							attendanceDTO.setBalanceTotal(balanceTotal.add(leaveBalance));
							attendanceDTO.setLeaveTypeMap(leaveTypeMap);
							attendanceDTO.setLeaveEntitlementTypeMap(leaveEntitlementTypeMap);
							attendanceDTO.setConsumedLeaveTotal(consumedLeaveTotal);

						}
					}
				}

			}

			return attendanceDTOList;
		}
		return null;

	}

	@Override
	public String[] getTypesOfLeaves(Long companyId) {
		// TODO Auto-generated method stub
		return attendanceRepository.getTypesOfLeaves(companyId);
	}

	@Override
	public List<TMSLeavePeriod> getLeavePeriod(Long companyId) {
		// TODO Auto-generated method stub
		return leavePeriodRepository.findLeavePeriod(companyId);

	}

//	@Override
//	public TMSLeavePeriod getLeavePeriodId(Long companyId) {
//		// TODO Auto-generated method stub
//		return leavePeriodRepository.findLeavePeriodId(companyId);
//	}

	@Override
	public List<Object[]> getLeaveRequestSummaryReport(Long companyId, Long employeeId, List<Long> departmentList,
			Date fDate, Date tDate) {

		// TODO Auto-generated method stub
		if (employeeId != 0L) {
			return leaveEntryRepository.getLeaveRequestSummaryEmployeeWise(companyId, employeeId, fDate, tDate);
		} else if (departmentList.size() > 0) {
			return leaveEntryRepository.getLeaveRequestSummaryDepartmentWise(companyId, departmentList, fDate, tDate);
		}
		return null;

	}

	@Override
	public List<Object[]> getCompOffReqSummaryReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate) {

		if (employeeId != 0L) {
			return compensatoryOffRepository.getCompOffReqSummaryEmpWise(companyId, employeeId, fDate, tDate);
		} else {
			return compensatoryOffRepository.getCompOffReqSummaryDeptWise(companyId, departmentIds, fDate, tDate);
		}
	}

	@Override
	public List<Object[]> getEarlyComersReport(Long companyId, Long employeeId, List<Long> departmentIds, Date fDate,
			Date tDate) {
		if (employeeId != 0) {
			return attendanceLogRepository.getEarlyComersListEmployeeWise(companyId, fDate, tDate, employeeId);
		} else if (departmentIds.size() > 0) {
			return attendanceLogRepository.getEarlyComersListDeptWise(companyId, fDate, tDate, departmentIds);
		}
		return null;
	}

	@Override
	public List<Object[]> getWorkedOnHolidaysReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate) {

		if (employeeId != 0L) {

			return attendanceLogRepository.getWorkedOnHolidayDetailsEmpWise(companyId, employeeId, fDate, tDate);

		} else if (departmentIds.size() > 0) {

			return attendanceLogRepository.getWorkedOnHolidayDetailsDeptWise(companyId, departmentIds, fDate, tDate);

		}
		return null;

	}

	@Override
	public List<Object[]> getWorkedOnWeekOffReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate) {

		if (employeeId != 0) {

			return attendanceLogRepository.getWorkedOnWeekOffDetailsEmpWise(companyId, employeeId, fDate, tDate);

		} else if (departmentIds.size() > 0) {

			return attendanceLogRepository.getWorkedOnWeekOffDetailsDeptWise(companyId, departmentIds, fDate, tDate);
		}
		return null;
	}

	@Override
	public List<Object[]> getEarlyLeaversReport(Long companyId, Long employeeId, List<Long> departmentIds, Date fDate,
			Date tDate) {

		if (employeeId != 0L) {
			return attendanceLogRepository.getEarlyLeaversDetailsEmployeeWise(companyId, fDate, tDate, employeeId);
		} else if (departmentIds.size() > 0) {
			return attendanceLogRepository.getEarlyLeaversDetailsDepartmentWise(companyId, fDate, tDate, departmentIds);
		}
		return null;
	}

	@Override
	public List<Object[]> getArRequestReport(Long companyId, Long employeeId, List<Long> departmentList, Date fDate,
			Date tDate) {

		if (employeeId != 0L) {
			return attendanceLogRepository.getArRequestReportEmloyeeWise(companyId, employeeId, fDate, tDate);
		} else {
			return attendanceLogRepository.getArRequestReportDepartmentWise(companyId, departmentList, fDate, tDate);
		}
	}

	@Override
	public List<Object[]> getOverTimeDayWiseReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate) {

		if (departmentIds.size() > 0) {
			return attendanceLogRepository.getOverTimeDayWiseDetailsDepartmentWise(companyId, fDate, tDate,
					departmentIds);

		} else if (employeeId != 0L) {
			return attendanceLogRepository.getOverTimeDayWiseDetailsEmployeeWise(companyId, fDate, tDate, employeeId);

		}

		return null;
	}

	@Override
	public List<Object[]> getOverTimeMonthWiseReport(Long companyId, int pMonth, int pYear, Long employeeId,
			List<Long> departmentIds) {

		if (departmentIds.size() > 0) {
			return attendanceLogRepository.getOverTimeMonthWiseDetailsDepartmentWise(companyId, pMonth, pYear,
					departmentIds);

		} else if (employeeId != 0L) {

			return attendanceLogRepository.getOverTimeMonthWiseDetailsEmployeeWise(companyId, pMonth, pYear,
					employeeId);

		}
		return null;
	}

	@Override
	public List<Object[]> getMissingPunchRecordReport(Long companyId, Long employeeId, List<Long> departmentList,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		if (employeeId != 0L) {
			return attendanceLogRepository.getMissingPunchRecordEmployeeWise(companyId, employeeId, startDate, endDate);
		} else if (departmentList.size() > 0) {
			return attendanceLogRepository.getMissingPunchRecordDepartmentWise(companyId, departmentList, startDate,
					endDate);
		}
		return null;
	}

	@Override
	public String[] getTypesOfEncashedLeaves(Long companyId) {
		// TODO Auto-generated method stub
		return attendanceRepository.findTypesOfEncashedLeave(companyId);
	}

	@Override
	public List<AttendanceDTO> getLeaveEncashedSummaryReport(Long companyId, Long employeeId, List<Long> departmentId,
			String[] typesOfLeavesId, Long leavePeriodId) {
		// TODO Auto-generated method stub

		if (employeeId != 0L) {
			List<Object[]> employeeData = attendanceRepository.findLeaveEncashedSummaryEmployeeWise(companyId,
					employeeId, leavePeriodId);

			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveEncashedReport(employeeData);

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveClosedBalanceMap = new HashMap<>();
				Map<String, List<BigDecimal>> leaveEncashMap = new HashMap<>();

				BigDecimal closingBalanceLeaveTotal = new BigDecimal(0.0);
				BigDecimal leaveEncashedTotal = new BigDecimal(0.0);

				List<Object[]> closingBalanceLeaveData = attendanceRepository.findClosingBalanceLeave(companyId,
						employeeId, leavePeriodId);

				for (Object[] leaveBalanceObj : closingBalanceLeaveData) {

					List<BigDecimal> leaveTypeList = new ArrayList<>();
					List<BigDecimal> leaveEncashList = new ArrayList<>();

					Long leaveTypeMasterId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString())
							: null;
					BigDecimal totalleave = leaveBalanceObj[1] != null ? (new BigDecimal(leaveBalanceObj[1].toString()))
							: new BigDecimal(0.0);
					BigDecimal consumeBalance = leaveBalanceObj[2] != null
							? (new BigDecimal(leaveBalanceObj[2].toString()))
							: null;
					BigDecimal balancedLeave = leaveBalanceObj[3] != null
							? (new BigDecimal(leaveBalanceObj[3].toString()))
							: new BigDecimal(0.0);
					BigDecimal leaveEncashData = leaveBalanceObj[4] != null
							? (new BigDecimal(leaveBalanceObj[4].toString()))
							: new BigDecimal(0.0);

					attendanceDTO.setClosingBalanceLeave(balancedLeave);
					leaveTypeList.add(balancedLeave);
					leaveClosedBalanceMap.put(leaveBalanceObj[0].toString(), leaveTypeList);
					closingBalanceLeaveTotal = closingBalanceLeaveTotal.add(balancedLeave);

					attendanceDTO.setLeaveEncashData(leaveEncashData);
					leaveEncashList.add(leaveEncashData);
					leaveEncashMap.put(leaveBalanceObj[0].toString(), leaveEncashList);
					leaveEncashedTotal = leaveEncashedTotal.add(leaveEncashData);
				}

				attendanceDTO.setLeaveEncashMap(leaveEncashMap);
				attendanceDTO.setLeaveClosedBalanceMap(leaveClosedBalanceMap);
				attendanceDTO.setClosingBalanceLeaveTotal(closingBalanceLeaveTotal);
				attendanceDTO.setLeaveEncashedTotal(leaveEncashedTotal);

			}

			return attendanceDTOList;

		}

		else if (departmentId.size() > 0) {

			List<Object[]> employeeData = attendanceRepository.findLeaveEncashSummaryDepartmentWise(companyId,
					departmentId, leavePeriodId);
			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveEncashedReport(employeeData);

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveClosedBalanceMap = new HashMap<>();
				Map<String, List<BigDecimal>> leaveEncashMap = new HashMap<>();

				BigDecimal closingBalanceLeaveTotal = new BigDecimal(0.0);
				BigDecimal leaveEncashedTotal = new BigDecimal(0.0);

				List<Object[]> closingBalanceLeaveData = attendanceRepository.findClosingBalanceLeave(companyId,
						attendanceDTO.getEmployeeId(), leavePeriodId);

				for (Object[] leaveBalanceObj : closingBalanceLeaveData) {

					List<BigDecimal> leaveTypeList = new ArrayList<>();
					List<BigDecimal> leaveEncashList = new ArrayList<>();

					Long leaveTypeMasterId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString())
							: null;
					BigDecimal totalleave = leaveBalanceObj[1] != null ? (new BigDecimal(leaveBalanceObj[1].toString()))
							: new BigDecimal(0.0);
					BigDecimal consumeBalance = leaveBalanceObj[2] != null
							? (new BigDecimal(leaveBalanceObj[2].toString()))
							: null;
					BigDecimal balancedLeave = leaveBalanceObj[3] != null
							? (new BigDecimal(leaveBalanceObj[3].toString()))
							: new BigDecimal(0.0);
					BigDecimal leaveEncashData = leaveBalanceObj[4] != null
							? (new BigDecimal(leaveBalanceObj[4].toString()))
							: new BigDecimal(0.0);

					attendanceDTO.setClosingBalanceLeave(balancedLeave);
					leaveTypeList.add(balancedLeave);
					leaveClosedBalanceMap.put(leaveBalanceObj[0].toString(), leaveTypeList);
					closingBalanceLeaveTotal = closingBalanceLeaveTotal.add(balancedLeave);

					attendanceDTO.setLeaveEncashData(leaveEncashData);
					leaveEncashList.add(leaveEncashData);
					leaveEncashMap.put(leaveBalanceObj[0].toString(), leaveEncashList);
					leaveEncashedTotal = leaveEncashedTotal.add(leaveEncashData);
				}
				attendanceDTO.setLeaveEncashMap(leaveEncashMap);
				attendanceDTO.setLeaveClosedBalanceMap(leaveClosedBalanceMap);
				attendanceDTO.setClosingBalanceLeaveTotal(closingBalanceLeaveTotal);
				attendanceDTO.setLeaveEncashedTotal(leaveEncashedTotal);

			}

			return attendanceDTOList;

		}

		return null;

	}

	@Override
	public List<Object[]> getAttendanceLogsSumReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate) {

		if (employeeId != 0) {

			return attendanceLogRepository.getAttendanceLogsSummaryEmpWise(companyId, employeeId, fDate, tDate);

		} else if (departmentIds.size() > 0) {

			return attendanceLogRepository.getAttendanceLogsSummaryDeptWise(companyId, departmentIds, fDate, tDate);
		}
		return null;
	}

	@Override
	public String[] getTypesOfLeavesEntitlement(Long companyId) {
		// TODO Auto-generated method stub
		return attendanceRepository.getTypesOfLeavesEntitlement(companyId);
	}

	@Override
	public List<TMSLeavePeriod> getEncashedLeavePeriod(Long companyId) {
		// TODO Auto-generated method stub
		return leavePeriodRepository.findEncashedLeavePeriod(companyId);
	}

	@Override
	public TMSLeavePeriod getEncashedLeavePeriodId(Long companyId, Long leavePeriodId) {
		// TODO Auto-generated method stub
		return leavePeriodRepository.getEncashedLeavePeriodId(companyId, leavePeriodId);
	}

	@Override
	public String[] getTypesOfExpiryLeaves(Long companyId) {
		// TODO Auto-generated method stub
		return attendanceRepository.findTypesOfExpiryLeaves(companyId);
	}

	@Override
	public List<AttendanceDTO> getLeaveExpirySummaryReport(Long companyId, Long employeeId, List<Long> departmentId,
			String[] typesOfLeavesExpiryId, Long leavePeriodId) {
		if (employeeId != 0L) {
			List<Object[]> employeeData = attendanceRepository.findLeaveExpirySummaryEmployeeWise(companyId, employeeId,
					leavePeriodId);

			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveExpiryReport(employeeData);

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveExpiryMap = new HashMap<>();

				List<Object[]> expiryLeaveData = attendanceRepository.findExpiryLeave(companyId, employeeId,
						leavePeriodId);

				for (Object[] leaveBalanceObj : expiryLeaveData) {

					List<BigDecimal> expiryLeaveTypeList = new ArrayList<>();
					Long leaveTypeMasterId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString())
							: null;
					BigDecimal leaveExpiry = leaveBalanceObj[1] != null
							? (new BigDecimal(leaveBalanceObj[1].toString()))
							: new BigDecimal(0.0);

					attendanceDTO.setLeaveExpiryFinalData(leaveExpiry);
					expiryLeaveTypeList.add(leaveExpiry);
					leaveExpiryMap.put(leaveBalanceObj[0].toString(), expiryLeaveTypeList);
				}

				attendanceDTO.setLeaveExpiryMap(leaveExpiryMap);

			}
			return attendanceDTOList;
		}

		else if (departmentId.size() > 0) {

			List<Object[]> employeeData = attendanceRepository.findLeaveExpirySummaryDepartmentWise(companyId,
					departmentId, leavePeriodId);
			List<AttendanceDTO> attendanceDTOList = leaveAttendanceReportAdapator
					.objectListToLeaveExpiryReport(employeeData);

			for (AttendanceDTO attendanceDTO : attendanceDTOList) {

				Map<String, List<BigDecimal>> leaveExpiryMap = new HashMap<>();

				List<Object[]> expiryLeaveData = attendanceRepository.findExpiryLeave(companyId,
						attendanceDTO.getEmployeeId(), leavePeriodId);

				for (Object[] leaveBalanceObj : expiryLeaveData) {

					List<BigDecimal> expiryLeaveTypeList = new ArrayList<>();
					Long leaveTypeMasterId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString())
							: null;
					BigDecimal leaveExpiry = leaveBalanceObj[1] != null
							? (new BigDecimal(leaveBalanceObj[1].toString()))
							: new BigDecimal(0.0);

					attendanceDTO.setLeaveExpiryFinalData(leaveExpiry);
					expiryLeaveTypeList.add(leaveExpiry);
					leaveExpiryMap.put(leaveBalanceObj[0].toString(), expiryLeaveTypeList);
				}

				attendanceDTO.setLeaveExpiryMap(leaveExpiryMap);

			}
			return attendanceDTOList;

		}

		return null;

	}

	public List<Object[]> getshiftScheduleSummary(Long companyId, Long employeeId, List<Long> departmentIds) {

		if (!departmentIds.isEmpty()) {

			return attendanceLogRepository.getShiftScheduleSumDepartmentWise(companyId, departmentIds);

		} else if (employeeId != 0L) {

			return attendanceLogRepository.getShiftScheduleSumEmployeeWise(companyId, employeeId);

		}
		return null;
	}

}
