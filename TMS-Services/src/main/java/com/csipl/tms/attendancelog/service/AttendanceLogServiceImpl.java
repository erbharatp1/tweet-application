package com.csipl.tms.attendancelog.service;

import java.math.BigDecimal;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.csipl.hrms.service.payroll.ProfessionalTaxService;
import com.csipl.tms.attendancelog.repository.AttendanceLogPaginationRepository;
import com.csipl.tms.attendancelog.repository.AttendanceLogRepository;
import com.csipl.tms.attendanceregularizationrequest.adaptor.AttendanceAdaptor;
import com.csipl.tms.attendanceregularizationrequest.service.AttendanceRegularizationRequestService;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDetailsDTO;
import com.csipl.tms.dto.attendancelog.AttendanceValidationResult;
import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.dto.holiday.HolidayDTO;
import com.csipl.tms.dto.leave.LeaveBalanceDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.holiday.service.HolidayService;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.repository.LeaveEntryRepository;
import com.csipl.tms.leave.service.CompensatoryOffService;
import com.csipl.tms.leave.service.EmployeeLeaveService;
import com.csipl.tms.leave.service.LeaveEntriesDatewiseService;
import com.csipl.tms.leave.service.LeavePeriodService;
import com.csipl.tms.leave.service.LeaveRulesHdService;
import com.csipl.tms.model.attendancelog.AttendanceLog;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;
import com.csipl.tms.model.empcommondetails.EmpCommonDetail;
import com.csipl.tms.model.halfdayrule.HalfDayRule;
import com.csipl.tms.model.holiday.TMSHolidays;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.leave.TMSLeaveRules;
import com.csipl.tms.model.leave.TMSLeaveRulesHd;
import com.csipl.tms.rules.service.RulesService;
import com.csipl.tms.weekoffpattern.adaptor.WeekOffPatternAdaptor;
import com.csipl.tms.weekoffpattern.service.WeekOffPatternService;

@Transactional
@Service("attendanceLogService")
public class AttendanceLogServiceImpl implements AttendanceLogService {
	private static final Logger logger = LoggerFactory.getLogger(AttendanceLogServiceImpl.class);
	
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager entityManager;

	@Autowired
	AttendanceLogRepository attendanceLogRepository;

	@Autowired
	LeaveEntryRepository leaveEntryRepository;

	@Autowired
	WeekOffPatternService weekOffPatternService;

	@Autowired
	public AttendanceLogPaginationRepository attendanceLogPaginationRepository;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	AttendanceRegularizationRequestService attendanceRegularizationRequestService;

	@Autowired
	RulesService rulesService;

	@Autowired
	EmployeeLeaveService employeeLeaveService;

	@Autowired
	HolidayService holidayService;

	@Autowired
	LeaveEntriesDatewiseService leaveEntriesDatewiseService;

	@Autowired
	private CompensatoryOffService compensatoryOffService;

	@Autowired
	private org.springframework.core.env.Environment environment;

	AttendanceAdaptor attendanceAdaptor = new AttendanceAdaptor();
	@Autowired
	GradeService gradeService;

	@Autowired
	LeaveRulesHdService leaveRulesHdService;

	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();

	@Autowired
	LeavePeriodService leavePeriodService;
	
	@Autowired
	ProfessionalTaxService professionalTaxService;
	//
	@Override
	public void savePunchTimeLog(List<AttendanceLog> attendanceLogList) {
		attendanceLogRepository.deleteAttendanceVDate(new Date());

		attendanceLogRepository.save(attendanceLogList);
	}

	@Override
	public void savePunchTimeLogViaDate(List<AttendanceLog> attendanceLogList, Date date) {
		attendanceLogRepository.deleteAttendanceVDate(date);

		attendanceLogRepository.save(attendanceLogList);
	}

	@Override
	public List<Object[]> getAttendance(Long companyId, Long employeeId, String fromDate, String toDate) {

		String query = "SELECT * FROM AttendanceLogs al WHERE al.companyId=?1 AND al.employeeId=?2 AND al.attendanceDate >= ?3 AND al.attendanceDate <= ?4 ORDER BY attendanceDate ASC";
		Query nativeQuery = entityManager.createNativeQuery(query);
		nativeQuery.setParameter(1, companyId).setParameter(2, employeeId).setParameter(3, fromDate).setParameter(4,
				toDate);
		List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("Attendance resultList size------>" + resultList.size());
		return resultList;

	}

	@Override
	public List<AttendanceLog> getAttendanceLogDetails(Long companyId, String date) {

		/*
		 * String query =
		 * "SELECT al.inTime,al.location,al.mode,empdetail.employeeCode,empdetail.firstName,empdetail.lastName,empdetail.departmentName,empdetail.designationName,empdetail.companyId, al.outTime FROM AttendanceLogs al  JOIN EmpCommonDetails empdetail oN al.employeeCode = empdetail.employeeCode WHERE al.attendanceDate=?1 AND al.companyId =?2"
		 * ; Query nativeQuery = entityManager.createNativeQuery(query);
		 * nativeQuery.setParameter(1, date).setParameter(2, companyId); List<Object[]>
		 * resultList = nativeQuery.getResultList();
		 * System.out.println("Attendance resultList size------>" + resultList.size());
		 * return resultList;
		 */

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		try {
			date1 = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return attendanceLogRepository.getAttendanceLog(companyId, date1);

	}

	@Override
	public List<AttendanceLogDetailsDTO> getAttendanceLogDetailsDTOList(List<AttendanceLog> attendanceLogDetailsList,

			List<EmpCommonDetail> empCommonDetailList, HalfDayRule halfDayRule, List<TMSLeaveEntry> leaveList,
			List<HolidayDTO> holidayDtoList, Date date) {
		// TODO Auto-generated method stub

		System.out.println("attendanceLogDetailsList..." + attendanceLogDetailsList);
		List<AttendanceLogDetailsDTO> attendanceLogDetailsDtoList = new ArrayList<AttendanceLogDetailsDTO>();
		Long diffHours = 0l;
		for (EmpCommonDetail empCommonDetail : empCommonDetailList) {
			AttendanceLogDetailsDTO attendanceLogDetailsDto = new AttendanceLogDetailsDTO();
			attendanceLogDetailsDto.setDepartmentName(empCommonDetail.getDepartmentName());
			attendanceLogDetailsDto.setDesignationName(empCommonDetail.getDesignationName());
			attendanceLogDetailsDto.setName(empCommonDetail.getFirstName() + empCommonDetail.getLastName());
			attendanceLogDetailsDto.setEmployeeCode(empCommonDetail.getEmployeeCode());

			if ((holidayDtoList.size() != 0)
					&& (attendanceLogDetailsList == null || attendanceLogDetailsList.size() == 0)) {
				System.out.println("inside holiday....holidayDtoList.size()..." + holidayDtoList.size()
						+ " holiay list..." + holidayDtoList.toString());
				attendanceLogDetailsDto.setPublicHoliday("PH");
			} else {
				for (AttendanceLog attendanceLogDetails : attendanceLogDetailsList) {

					if (empCommonDetail.getEmployeeId().equals(attendanceLogDetails.getEmployeeId())) {

						attendanceLogDetailsDto.setPresent("P");
						attendanceLogDetailsDto.setFirstPunch(attendanceLogDetails.getInTime());
						attendanceLogDetailsDto.setLocation(attendanceLogDetails.getLocation());
						attendanceLogDetailsDto.setMode(attendanceLogDetails.getMode());

						/*
						 * shift (delayed time) calculation
						 */
						attendanceLogDetailsDto.setDelayedTime(attendanceLogDetails.getDelayedTime());

						/* calculation of worked hour */
						SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
						Date outDate = null;
						Date inDate = null;
						String outTime = attendanceLogDetails.getOutTime();
						String inTime = attendanceLogDetails.getInTime();
						try {
							outDate = (Date) sdf.parse(outTime);
							inDate = (Date) sdf.parse(inTime);
							Long diff = outDate.getTime() - inDate.getTime();
							diffHours = diff / (60 * 60 * 1000) % 24;

							System.out.println("worked hours.." + diffHours);
							attendanceLogDetailsDto.setWorkHours(diffHours);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						// calculation of overtime &early going
						Calendar cal = Calendar.getInstance();
						cal.setTime(inDate);
						int hours = (halfDayRule.getMaximumRequireHour()).intValue();
						cal.add(Calendar.HOUR_OF_DAY, hours); // this will add two hours
						inDate = cal.getTime();
						System.out.println("intimeeee...." + inDate);

						if (diffHours < halfDayRule.getMaximumRequireHour()) {
							Long diff = inDate.getTime() - outDate.getTime();
							diffHours = diff / (60 * 60 * 1000) % 24;
							long diffMinutes = diff / (60 * 1000) % 60;
							attendanceLogDetailsDto.setEarlyGoing(diffHours + ":" + diffMinutes);
							attendanceLogDetailsDto.setOverTime("0");
							System.out.println("early going.." + diffHours + ":" + diffMinutes);
						} else {
							Long diff = outDate.getTime() - inDate.getTime();
							diffHours = diff / (60 * 60 * 1000) % 24;
							long diffMinutes = diff / (60 * 1000) % 60;
							attendanceLogDetailsDto.setEarlyGoing("0");
							attendanceLogDetailsDto
									.setOverTime(diffHours.toString() + "hours" + ":" + diffHours + ":" + diffMinutes);

							System.out.println("overtime..." + diffHours);
						}

					}

				}
				/* leave calculation */
				for (TMSLeaveEntry leaveEntry : leaveList) {
					if (empCommonDetail.getEmployeeId().equals(leaveEntry.getEmployeeId())) {
						System.out.println("insidee leave if");
						attendanceLogDetailsDto.setLeave("L");
						/*
						 * attendanceLogDetailsDto.setPresent("0");
						 * attendanceLogDetailsDto.setAbsent("0");
						 */
					}

				}

				/* weekoff calculation */
				System.out.println(empCommonDetail.getEmployeeId());

				Object[] weekOffPatternObj = weekOffPatternService
						.getWeekOffPatternByEmp(empCommonDetail.getCompanyId(), empCommonDetail.getEmployeeId());
				String weekOffPattern = null;
				if (weekOffPatternObj != null) {
					System.out.println("weekoffpattern.." + weekOffPatternObj[0]);
					weekOffPattern = weekOffPatternObj[0].toString();

					WeekOffPatternAdaptor weekOffPatternAdaptor = new WeekOffPatternAdaptor();
					String[] weekOffPatternArray = weekOffPatternAdaptor.databaseModeObjlToUiDto(weekOffPattern);
					List<String> weekDayList = Arrays.asList(weekOffPatternArray);
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE");
					String dayName = simpleDateformat.format(date);
					if (weekDayList.contains(dayName.toUpperCase())) {
						attendanceLogDetailsDto.setWeekOff("Off");
					}
				}
				// attendanceLogDetailsDto.setPublicHoliday("0");
			}
			if (attendanceLogDetailsDto.getPresent() == null && attendanceLogDetailsDto.getLeave() == null
					&& attendanceLogDetailsDto.getWeekOff() == null
					&& attendanceLogDetailsDto.getPublicHoliday() == null) {
				System.out.println("absent...");
				attendanceLogDetailsDto.setAbsent("A");
			}
			System.out.println("Object..." + attendanceLogDetailsDto.toString());
			attendanceLogDetailsDtoList.add(attendanceLogDetailsDto);
		}
		// System.out.println("attendanceAdaptor...." + attendanceLogDetailsDtoList);
		return attendanceLogDetailsDtoList;
	}

	@Override
	public List<AttendanceLog> markBulkAttendance(List<AttendanceLog> attendanceLogList) {
		List<AttendanceLog> attendanceLogList1 = (List<AttendanceLog>) attendanceLogRepository.save(attendanceLogList);
		return attendanceLogList1;
	}

	/*
	 *@13-APR-2020
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AttendanceLogDTO calculateAttendanceForPayroll(Long employeeId, String processMonth, Long companyId) throws ErrorHandling, ParseException {
		// TODO Auto-generated method stub
		// final Map<Date, DateWiseAttendanceLogDTO> attendanceMap = new HashMap<Date,
		// DateWiseAttendanceLogDTO>();
		// final Map<Date, String> leaveEnteriesMap = new HashMap<Date, String>();
		// final Map<Date, String> attendanceLogMap = new HashMap<Date, String>();
		// final Map<Date, AttendanceLog> attendanceLogObjMap = new HashMap<Date,
		// AttendanceLog>();
		// final Map<Date, String> holidaydateMap = new HashMap<Date, String>();
		// final Map<Date, String> weeklyoffdateMap = new HashMap<Date, String>();
		Map<Date, String> attendanceRegMap = new HashMap<Date, String>();
//		EmployeeDTO employeeDto=getEmployeeByRestTamplate(employeeId.toString());
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
		DateFormat formater1 = new SimpleDateFormat("MMM-yyyy");
	 
		if (currentmonth == employeeJoiningDate.getMonth()
				&& (currentDate.getYear() == employeeJoiningDate.getYear())) {
			startDate =employeeJoiningDate;
		}
		//here we find leave rulse based on leave period Id
		String psMonthArray[] = processMonth.split("-");
		String psmonth = psMonthArray[0];
		int year =  Integer.valueOf(psMonthArray[1]);
		Calendar cal = Calendar.getInstance();
	     cal.set(Calendar.DATE ,1);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH , professionalTaxService.monthToValue(psmonth) );
		
        LocalDate psMonthLocalDate = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        logger.info(" companyId "+ companyId +" psMonthLocalDate "+psMonthLocalDate.toString());
        String formatedPsMonth = psMonthLocalDate.toString();
		TMSLeavePeriod tMSLeavePeriod = leavePeriodService.findLeavePeriodByProcessMonth(companyId, formatedPsMonth);
		
		if(tMSLeavePeriod == null) {
			throw new ErrorHandling("Leave Period Not Found Regarding Process Month");
		}
		logger.info(" tMSLeavePeriod id"+ tMSLeavePeriod.getLeavePeriodId());
		TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHdByLeavePeriodId(tMSLeavePeriod.getLeavePeriodId());
		//TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHd(1l);
		boolean weekOffBetweenTwoAbsent = false;
		boolean holidayBetweenTwoAbsent = false;
		boolean absentIsFoundWithoutInfo = false;
		if(tmsLeaveRuleHd!=null) {
		for (TMSLeaveRules tmsLeaveRule : tmsLeaveRuleHd.getTmsleaveRules()) {

			if (("HAA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoAbsent = true;
			}
			if (("WAF").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoAbsent = true;
			}
			if (("DAWI").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				absentIsFoundWithoutInfo = true;
			}
		}
		}
		attendanceValidationResult.setWeekOffBetweenTwoAbsent(weekOffBetweenTwoAbsent);
		attendanceValidationResult.setHolidayBetweenTwoAbsent(holidayBetweenTwoAbsent);
		int month = DateUtils.getMonthForProcessMonth(processMonth);
		Date endDate = new Date();

		List<Object[]> attendanceLogObjList = attendanceLogRepository.getAttendanceLogByEmployeeIdandMonth(employeeId,
				month, companyId);

		List<AttendanceLog> attendanceLogList = attendanceAdaptor.attendanceObjArraytoDtoList(attendanceLogObjList);

		// List<TMSLeaveEntry> tmsLeaveEntryList =
		// employeeLeaveService.getEmployeeLeaveEntry(employeeId, processMonth);

		List<TMSLeaveEntriesDatewise> tmsLeaveEntryDateWiseList = leaveEntriesDatewiseService
				.getEmployeeLeaveEntry(employeeId, processMonth);
		
		List<Date>	leaveEntriesDateList = leaveEntriesDatewiseService.getEmployeeLeaveDate(employeeId, startDate, endDate);
		
		// List<CompensatoryOff> employeeCompOffList =
		// compensatoryOffService.findMyCompOffExcludedPendingReqList(employeeId);
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
		// new weeklyoffpattern rule need to implemented here
		if (leaveBalanceDto.getTmsWeekOffChildPatternDTO() != null) {
//			String patternDays = leaveBalanceDto.getPatternDays();
			patternDayList = leaveBalanceDto.getTmsWeekOffChildPatternDTO();

		}

		weeklyOfPetternCount(startDate, endDate, patternDayList, attendanceValidationResult.getWeeklyoffdateMap());

//	   String[]  weekOffPattenList= weekOffPatternService.getWeekOffPatternByEmp(companyId, employeeId);

//	   List<String> patternDayList = new ArrayList<>();
//	    
//	   if(weekOffPattenList.length>0) {
//	   String patternDays = weekOffPattenList[0] != null ? weekOffPattenList[0] : "";
//	
//	   patternDayList = Arrays.asList(patternDays.split(","));
//	   }

//		weeklyOfPetternCount(startDate, endDate, patternDayList,attendanceValidationResult.getWeeklyoffdateMap() ) ;

		Collections.sort(attendanceLogList, (a1, a2) -> a1.getAttendanceDate().compareTo(a2.getAttendanceDate()));
		Collections.sort(tmsLeaveEntryDateWiseList, (a1, a2) -> a1.getLeaveDate().compareTo(a2.getLeaveDate()));
		Collections.sort(attendanceRegularizationRequestList, (a1, a2) -> a1.getFromDate().compareTo(a2.getFromDate()));
		holidayOfPetternCount(startDate, endDate, tmsHoliday, attendanceValidationResult.getHolidaydateMap());

		/*
		 * attendanceLogMap=attendanceLogList.stream()
		 * .collect(Collectors.toMap(AttendanceLog::getAttendanceDate,
		 * AttendanceLog::getStatus));
		 */

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

		Long sandwitchWeekoffCount = 0L, sandwitchHolidayCount = 0L;
		BigDecimal presentDays = new BigDecimal(0);
		BigDecimal leaveDays = new BigDecimal(0);
		BigDecimal weeklyoffDays = new BigDecimal(0);
		BigDecimal holidayDays = new BigDecimal(0);
		BigDecimal absense = new BigDecimal(0);
		BigDecimal absenseForCalender = new BigDecimal(0);
		AttendanceLogDTO attendance = new AttendanceLogDTO();

		holidayDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getHolidaydateMap(),
				attendance, StatusMessage.holidaysFlag);
		attendanceValidationResult.setHolidayDays(holidayDays.longValue());
		// System.out.println("holidaydateMap :--"+holidayDays);

		weeklyoffDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getWeeklyoffdateMap(),
				attendance, StatusMessage.weekoffFlag);
		attendanceValidationResult.setWeeklyoffDays(weeklyoffDays.longValue());
		// System.out.println("weeklyoffdateMap :--"+weeklyoffDays);

		leaveDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getLeaveEnteriesMap(),
				attendance, StatusMessage.leavedaysFlag);
		presentDays = AttendanceDate(attendanceValidationResult, attendanceValidationResult.getAttendanceLogMap(),
				attendance, StatusMessage.presentdaysFlag);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		// System.out.println("leaveEnteriesMap :--"+ leaveDays);

		// absense=new BigDecimal(days).subtract(leaveDays.add(new
		// BigDecimal(presentDays+leaveDays+weeklyoffDays+holidayDays)));
		// System.out.println("absense :--"+absense);
		Collection<DateWiseAttendanceLogDTO> dateWiseAttendanceLog = attendanceValidationResult.getAttendanceMap()
				.values();
		List<DateWiseAttendanceLogDTO> list = new ArrayList<>(dateWiseAttendanceLog);
		Collections.sort(list, (a1, a2) -> a1.getStart().compareTo(a2.getStart()));
		// absent count upto current date
		for (DateWiseAttendanceLogDTO dateWiseAttendanceLogDto : list) {
			String stringDate = sdf.format(dateWiseAttendanceLogDto.getStart());
			dateWiseAttendanceLogDto.setDayName(stringDate);
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
			String stringDate = sdf.format(dateWiseAttendanceLogDto.getStart());
			dateWiseAttendanceLogDto.setDayName(stringDate);
			if ((dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.Absent))
					|| (dateWiseAttendanceLogDto.getTitle().equals(StatusMessage.SandwitchLOP))) {
				if ((dateFormat.format(dateWiseAttendanceLogDto.getStart()))
						.compareTo(dateFormat.format(currentDate)) > 0) {
					dateWiseAttendanceLogDto.setTitle("");
					;
				}
			}
			attendanceMapobj.put(dateWiseAttendanceLogDto.getStart(), dateWiseAttendanceLogDto);

		}
		absenteesRule(list, attendanceMapobj, attendanceValidationResult);
		setAbsentIsFoundWithoutInfo(list, attendanceMapobj, attendanceValidationResult,absentIsFoundWithoutInfo, leaveEntriesDateList);
		leaveDays = leaveDays.subtract(attendanceValidationResult.getSandwitchHolidayCount()
				.subtract(attendanceValidationResult.getSandwitchWeekoffCount()));
		attendanceValidationResult.setTotleLeaveDays(leaveDays);
		// presentDays = AttendanceDate(
		// attendanceValidationResult.getAttendanceMap(),attendanceValidationResult.getAttendanceLogMap(),null,attendance,attendanceValidationResult.getAttendanceLogObjMap(),sandwitchWeekoffCount,sandwitchHolidayCount);
		// System.out.println("attendanceMap :--"+presentDays);
		attendanceValidationResult.setHolidayDays(
				attendanceValidationResult.getHolidayDays() - attendanceValidationResult.getHolidayAsAbsentDateCount());
		attendanceValidationResult.setWeeklyoffDays(
				attendanceValidationResult.getWeeklyoffDays() - attendanceValidationResult.getWeekoffAbsentDateCount());
		BigDecimal holidayWeekoffSum = new BigDecimal(
				attendanceValidationResult.getHolidayDays() + attendanceValidationResult.getWeeklyoffDays());
		absense = new BigDecimal(days)
				.subtract(attendanceValidationResult.getTotleLeaveDays().add((presentDays.add(holidayWeekoffSum))));
		BigDecimal payDays = attendanceValidationResult.getTotleLeaveDays().add((presentDays.add(holidayWeekoffSum)));
		attendance.setLeaves(attendanceValidationResult.getTotleLeaveDays());
		attendance.setEmployeeId(employeeId);
		attendance.setAbsense(absense);
		attendance.setEmployeeCode(employeeCode);
		attendance.setEmployeeName(employeeName);
		attendance.setWeekoff(new BigDecimal(attendanceValidationResult.getWeeklyoffDays()));
		attendance.setPublicholidays(new BigDecimal(attendanceValidationResult.getHolidayDays()));
		attendance.setPresense(presentDays);
		attendance.setProcessMonth(processMonth);
		attendance.setCompanyId(companyId);
		attendance.setPayDays(payDays);
		attendance.setDepartmentName(departmentName);
		attendance.setDesignationName(designationName);
		attendance.setReportingTo(reportingTo);
		attendance.setJobLocation(jobLocation);
		attendance.setAbsenseForCalender(absenseForCalender);
		attendance.setEvents(list);

		// BigDecimal actualLeaveAppliedDays = new BigDecimal(10);

		return attendance;
	}

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
//singya
	public void setAbsentIsFoundWithoutInfo(List<DateWiseAttendanceLogDTO> list, Map<Date, DateWiseAttendanceLogDTO> attendanceMap,
			AttendanceValidationResult attendanceValidationResult, boolean absentIsFoundWithoutInfo, List<Date> leaveEntryDate) {
		list.forEach(dateWiseAttendanceLogDTO->{
			if(!attendanceValidationResult.getHolidaydateMap().containsKey(dateWiseAttendanceLogDTO.getStart()) && 
					!attendanceValidationResult.getWeeklyoffdateMap().containsKey(dateWiseAttendanceLogDTO.getStart()) && 
					absentIsFoundWithoutInfo && !leaveEntryDate.contains(dateWiseAttendanceLogDTO.getStart()) && (dateWiseAttendanceLogDTO.getInTime()=="") ){
				if(dateWiseAttendanceLogDTO.getStart().compareTo(new Date())<0) {
					dateWiseAttendanceLogDTO.setTitle("A2");
				}
			}
		});
	}
	
	public BigDecimal AttendanceDate(AttendanceValidationResult attendanceValidationResult, Map<Date, String> mapDate,
			AttendanceLogDTO attendance, String daysMakerFlag) {

		boolean leaveFlag = false, absentFlag = false;
		Long absentDays = 0L;
		Long arDays = 0L;
		Long halfDays = 0L;
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
					System.out.println("DTO-----------" + dto.getTitle() + "_-----" + dto.getMode() + "----"
							+ attendanceValidationResult.getAttendanceMap().get(dateKey).getStart() + "----"
							+ attendance.getOutTime());

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
						// absenteesRule(attendanceValidationResult,daysMakerFlag,dateKey);
					}
//					else if (dto.getTitle().equals(StatusMessage.LeaveHalfday) || dto.getTitle().equals("P/2")) {
//						days = days.add(new BigDecimal(0.5));
//
//					}
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
						
						
					}
					 else if (dto.getTitle().equals(StatusMessage.ATTENDANCE_REGULARIZATION_CODE)) {
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

					else {
						days = days.add(new BigDecimal(1));
						//
						if (daysMakerFlag.equals(StatusMessage.weekoffFlag)) {
							if (attendanceValidationResult.getHolidaydateMap().containsKey(dateKey))
								attendanceValidationResult
										.setHolidayDays(attendanceValidationResult.getHolidayDays() - 1);
						
							
							
						}
						//
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
						if (daysMakerFlag.equals(StatusMessage.presentdaysFlag)) {// its for all type of leave LF ,LS
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

	/*
	 * private void absenteesRule(AttendanceValidationResult
	 * attendanceValidationResult, String daysMakerFlag, Date dateKey) { int
	 * absentWeekoffDaysCount = 0; int absentholidayDaysCount = 0;
	 * if(daysMakerFlag.equals(StatusMessage.presentdaysFlag)
	 * &&(attendanceValidationResult.isHolidayBetweenTwoAbsent() ||
	 * attendanceValidationResult.isWeekOffBetweenTwoAbsent())) { dateKey = new
	 * Date(dateKey.getTime() - 24 * 60 * 60 * 1000); boolean conditinCheckflag =
	 * true; while (conditinCheckflag) {
	 * if(attendanceValidationResult.getAttendanceLogObjMap().containsKey(dateKey)
	 * &&
	 * (attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getStatus()
	 * .equals("W"))) { conditinCheckflag = true; absentWeekoffDaysCount++; } else
	 * if(attendanceValidationResult.getAttendanceLogObjMap().containsKey(dateKey)
	 * && (
	 * attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getStatus().
	 * equals("H"))) { conditinCheckflag = true; absentholidayDaysCount++; } else
	 * if(attendanceValidationResult.getAttendanceLogObjMap().containsKey(dateKey)
	 * &&
	 * (attendanceValidationResult.getAttendanceLogObjMap().get(dateKey).getStatus()
	 * .equals("A"))) { conditinCheckflag = false; if(absentWeekoffDaysCount>0) {
	 * 
	 * } } else { break; }
	 * 
	 * dateKey = new Date(dateKey.getTime() - 24 * 60 * 60 * 1000); Calendar
	 * calendar = Calendar.getInstance(); calendar.setTime(dateKey);
	 * calendar.set(Calendar.HOUR, 0); calendar.set(Calendar.MINUTE, 0);
	 * calendar.set(Calendar.SECOND, 0); calendar.set(Calendar.HOUR_OF_DAY, 0);
	 * 
	 * dateKey = calendar.getTime();
	 * 
	 * } } }
	 */

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

	// old Code
//	public void weeklyOfPetternCount(Date fromDate, Date toDate, List<String> patternDayList,Map<Date, String> weeklyOffMap ) {
//			
//		List<LocalDate> dateList=	DateUtils.getDatesBetweenUsing(fromDate,toDate);
//	  
//		
//		for(int i=0;i<=dateList.size()-1;i++) {
//		
//			for (String patternDay : patternDayList) {
//	//		System.out.println("applay Days is " + patternDay);
//			if (patternDay.equalsIgnoreCase(new SimpleDateFormat("E").format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase())) {
//				
//				weeklyOffMap.put(DateUtils.getDateFromLocalDate(dateList.get(i)),"W");
//			}
//	    }
//		
//	   }
//		
//		
//	}

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
			// System.out.println("testing holiday form " + list + " -----" );

			if (dateList.contains(list)) {
				holidayMap.put(DateUtils.getDateFromLocalDate(list), "H");

				// System.out.println("testing holiday form ------" + list + " -----" );
			}
		});

		for (int i = 0; i <= dateList.size() - 1; i++) {
			// System.out.println("testing holiday for " +
			// DateUtils.getDateFromLocalDate(dateList.get(i)));

			for (int j = 0; j <= holidaydataList.size() - 1; j++) {
				// leaveValidationResult.getAllHolidayset().add(DateUtils.getDateFromLocalDate(holidaydataList.get(j)));
				if (DateUtils.getDateFromLocalDate(dateList.get(i))
						.compareTo(DateUtils.getDateFromLocalDate(holidaydataList.get(j))) == 0) {
					// holidayMap.put(DateUtils.getDateFromLocalDate(dateList.get(i)),"Holiday");
					// System.out.println("holiday matched date is " +
					// DateUtils.getDateFromLocalDate(dateList.get(i)));
					break;

				}

			}

		}

	}

	/**
	 * @param employeeId to get employee object based on employee id
	 */

	/*
	 * private EmployeeDTO getEmployeeByRestTamplate(String employeeId) { //String
	 * url = "http://localhost:8080/hrmsApi/employee/{employeeId}"; String url =
	 * environment.getProperty("application.employeeInfoTemp"); RestTemplate
	 * restTemplate = new RestTemplate(); HttpHeaders header = new HttpHeaders();
	 * header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); Map<String,
	 * String> params = new HashMap<>(); params.put("employeeId", employeeId);
	 * return restTemplate.getForObject(url, EmployeeDTO.class, params); }
	 */
	@Override
	public List<Object[]> attendanceReport(Long companyId, Date attendanceDate) {
		// TODO Auto-generated method stub
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		String date = sm.format(attendanceDate);
		return attendanceLogRepository.getAttendanceReport(companyId, date);
	}

	public List<Object[]> getLateCommersEmployeeListWithCount(Long companyId) {
		return attendanceLogRepository.getLateCommersEmployeeListWithCount(companyId,
				DateUtils.getMonthFirstDateUsingCurrentdate(), new Date());
	}

	@Override
	public List<Object[]> getLateCommersEmployeeListWithCountViaDate(Long companyId, Date date) {
		return attendanceLogRepository.getLateCommersEmployeeListWithCount(companyId,
				DateUtils.getMonthFirstDateUsingCurrentdate(), date);
	}

	@Override
	public List<Object[]> getCheckInFromPunchTimeDetails(Long employeeId) {

		return attendanceLogRepository.getCheckInRecordsFromPuchTimeDetails(employeeId);
	}

	@Override
	public List<AttendanceLog> getAttendanceLogPreviousDays(Long companyId, Date date) {
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(date);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);   
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     date = cal.getTime();
	     cal.add(Calendar.DATE, -30);
	   	 Date previousThirtyDays = cal.getTime();
	   	
	   	return attendanceLogRepository.getAttendanceLogPreviousDays(date , previousThirtyDays , companyId);
		
	}

	@Override
	@javax.transaction.Transactional
	public void updateAttendaceData(Long companyId, String employeeCode, Date date, String status, Long employeeId) {
		AttendanceLog attendanceLog= attendanceLogRepository.getAttendanceLogData(companyId, date, employeeCode);
		if(attendanceLog!=null) {
//			attendanceLogRepository.updateAttendaceData(companyId,employeeCode, date, status);
			attendanceLog.setCompanyId(companyId);
			attendanceLog.setEmployeeCode(employeeCode);
			attendanceLog.setAttendanceDate(date);
			attendanceLog.setUpdatedDate(new Date());
			attendanceLog.setStatus(status);
			attendanceLog.setEmployeeId(employeeId);
			attendanceLogRepository.save(attendanceLog);
		}
		else {
			AttendanceLog attendance = new AttendanceLog();
			attendance.setCompanyId(companyId);
			attendance.setEmployeeCode(employeeCode);
			attendance.setAttendanceDate(date);
//			attendance.setEmployeeId(employeeId);
			attendance.setUpdatedDate(new Date());
			attendance.setCreatedDate(new Date());
			attendance.setStatus(status);
			attendance.setEmployeeId(employeeId);
			attendanceLogRepository.save(attendance);
		}
	}

}
