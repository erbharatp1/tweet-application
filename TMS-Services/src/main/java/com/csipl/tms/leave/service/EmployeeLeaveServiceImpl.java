package com.csipl.tms.leave.service;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DayEnum;
import com.csipl.hrms.common.enums.LeaveTypeMasterEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.PushNotificationsServiceImpl;
import com.csipl.hrms.service.common.PushNotificationsServices;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.common.repository.FcmNotificationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.organization.CompanyServiceImpl;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.tms.dto.attendancelog.AttendanceValidationResult;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.dto.leave.LeaveBalanceDTO;
import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.SandwitchIssueResolver;
import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.holiday.adaptor.HolidayAdaptor;
import com.csipl.tms.holiday.repository.HolidayRepository;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.repository.EmployeeLeaveEarnRepository;
import com.csipl.tms.leave.repository.LeaveEntryRepository;
import com.csipl.tms.leave.repository.LeaveRequestRepository;
import com.csipl.tms.leave.repository.TMSLeaveRulesRepository;
import com.csipl.tms.model.holiday.TMSHolidays;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.csipl.tms.model.leave.TMSLeaveRules;
import com.csipl.tms.model.leave.TMSLeaveRulesHd;
import com.csipl.tms.rules.repository.SandWichRuleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Transactional
@Service("employeeLeaveService")

public class EmployeeLeaveServiceImpl implements EmployeeLeaveService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	public static final String ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(EmployeeLeaveServiceImpl.class);
	static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager eManager;

//	@Autowired
//	private NotificationServices notificationServices;
	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	LeaveRulesHdService leaveRulesHdService;

	@Autowired
	LeaveEntryRepository leaveEntryRepository;

	@Autowired
	LeaveRequestRepository leaveRequestRepository;

	@Autowired
	TMSLeaveRulesRepository tmsLeaveTypeRepository;

	@Autowired
	EmployeeLeaveEarnRepository employeeLeaveEarnRepository;

	@Autowired
	SandWichRuleRepository sandWichRuleRepository;

	@Autowired
	HolidayRepository holidayRepository;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	EmployeePersonalInformationRepository employeePersonalInformationRepository;

	@Autowired
	EmployeeLeaveService employeeLeaveService;

	@Autowired
	LeaveEntriesDatewiseService employeeLeaveDatewiseService;

	@Autowired
	private LeaveTypeService leaveTypeService;

	@Autowired
	ReportPayOutRepository reportPayOutRepository;

	@Autowired
	PushNotificationsServices pushNotificationsServices;

	@Autowired
	PushNotificationsServiceImpl fcm;

	@Autowired
	FcmNotificationRepository fcmNotificationRepository;

	@Autowired
	private CompanyServiceImpl companyServiceImpl;

	@Autowired
	private Environment environment;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	LeaveEntriesDatewiseService leaveEntriesDatewiseService;
	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
	HolidayAdaptor holidayAdaptor = new HolidayAdaptor();
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	int holidayMatchedCount = 0;

	@Override
	public boolean validateLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummryDto)
			throws ParseException, ErrorHandling {
		int appliedLeaveCount = 0;
		// validate leave entry is already present in database or not
		if (leaveBalanceSummryDto.getLeaveId() != null)
			appliedLeaveCount = leaveEntryRepository.checkDateValidation(leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getFromDate(), leaveBalanceSummryDto.getToDate(),
					leaveBalanceSummryDto.getLeaveId());
		else
			appliedLeaveCount = leaveEntryRepository.checkDateValidation1(leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getFromDate(), leaveBalanceSummryDto.getToDate());
		logger.info("appliedLeaveCount.." + appliedLeaveCount);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(leaveBalanceSummryDto.getEmployeeId());
		logger.info("appliedLeaveCount :" + appliedLeaveCount);
		if (appliedLeaveCount < 1) {

			String leaveFromDateInString = getDateStringWirhYYYYMMDD(leaveBalanceSummryDto.getFromDate());
			String leaveToDateInString = getDateStringWirhYYYYMMDD(leaveBalanceSummryDto.getToDate());

			Date leavefromDate = new SimpleDateFormat("yyyy-MM-dd").parse(leaveFromDateInString);
			Date leavetoDate = new SimpleDateFormat("yyyy-MM-dd").parse(leaveToDateInString);

			// returning count number of month between leave applied days
			BigDecimal noOfMonths = checkNumberOfMonths(leavefromDate, leavetoDate);

			// returining first date of month
			LocalDate leaveMonthStartLocalDate = leavefromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			leaveMonthStartLocalDate = leaveMonthStartLocalDate.withDayOfMonth(1);

			// returining last date of month
			LocalDate leaveMonthEndLocalDate = leavetoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			leaveMonthEndLocalDate = leaveMonthEndLocalDate.withDayOfMonth(leaveMonthEndLocalDate.lengthOfMonth());

			Date leaveMonthStartDate = Date
					.from(leaveMonthStartLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			Date leaveMonthEndDate = Date.from(leaveMonthEndLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			// check how many leave applied between leave applied month
			BigDecimal approvedPendingDaysCount = (BigDecimal) leaveEntryRepository.getEmployeeApprovedLeaveInDuration(
					leaveMonthStartDate, leaveMonthEndDate, leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getLeaveTypeId());

			// BigDecimal approvedPendingDays =
			// leaveEntryAdaptor.objectArrayToLongDays(approvedPendingDaysList);

			// check how many times leave apply between leave applied month
			int frequencyCount = leaveEntryRepository.getEmployeeMonthlyFrequencyCount(leaveMonthStartDate,
					leaveMonthEndDate, leaveBalanceSummryDto.getEmployeeId(), leaveBalanceSummryDto.getLeaveTypeId());
			BigDecimal frequencyCountOfEmployee = new BigDecimal(frequencyCount);

			BigDecimal leavePendingBalanceCount = new BigDecimal("0");
			BigDecimal maxLeaveInMonthly = new BigDecimal("0.0");
			BigDecimal leaveFrequencyMonthly = new BigDecimal("0.0");
			BigDecimal leaveAppliedDaysCount = new BigDecimal("0.0");

			Long probationDays = 0l;

			if (leaveBalanceSummryDto.getDays() != null)
				leaveAppliedDaysCount = leaveBalanceSummryDto.getDays();

			if (leaveBalanceSummryDto.getLeaveBalancedCount() != null)
				leavePendingBalanceCount = leaveBalanceSummryDto.getLeaveBalancedCount();

			if (leaveBalanceSummryDto.getMaxLeaveInMonth() != null) {
				maxLeaveInMonthly = new BigDecimal(noOfMonths.longValue() * leaveBalanceSummryDto.getMaxLeaveInMonth());
				maxLeaveInMonthly = maxLeaveInMonthly.subtract(approvedPendingDaysCount); // .add(employeePndingLeaveInDurationBasedOnPKDays);
			}

			if (leaveBalanceSummryDto.getLeaveFrequencyInMonth() != null)
				leaveFrequencyMonthly = new BigDecimal(leaveBalanceSummryDto.getLeaveFrequencyInMonth());

			if (employee.getProbationDays() != null)// get provision days from employee table
				probationDays = employee.getProbationDays();

			// provision condition check
			if (leaveBalanceSummryDto.getIsLeaveInProbation() != null
					&& leaveBalanceSummryDto.getIsLeaveInProbation().equals("Y")
					|| probationDays < getDifferenceDays(getFormatedDate(employee.getDateOfJoining()), // employee.getDateOfJoining()
																										// pass from
																										// employee
																										// table
							new Date())) {

				//
				if (leaveAppliedDaysCount.compareTo(leavePendingBalanceCount) > 0) {
					logger.info("You can't apply leave, due to insufficient balance.");
					throw new ErrorHandling("You can't apply leave, due to insufficient balance.");
				} else {

					if (leaveAppliedDaysCount.compareTo(maxLeaveInMonthly) > 0) {
						throw new ErrorHandling("You can't apply  " + leaveAppliedDaysCount + " day(s) "
								+ leaveBalanceSummryDto.getLeaveName() + " in a month, limit exceeded");
					} else {

						// BigDecimal bigLeaveFrequencyMonthly = new BigDecimal(leaveFrequencyMonthly);
						// BigDecimal bigFrequencyCount = new BigDecimal(frequencyCount);
						if (frequencyCountOfEmployee.compareTo(leaveFrequencyMonthly) < 0) {
							// logger.info("in if
							// leaveEntryRepository.save(leaveEntry);"+leaveEntryRepository.save(leaveEntry));

							return true;

						} else {
							logger.info("You can't apply leave, monthly limit quota exceeded.");
							throw new ErrorHandling("You can't apply leave, monthly limit quota exceeded.");
						}
					}

				}
			} else {
				logger.info("Can't apply leave in probation period");

				throw new ErrorHandling("Can't apply leave in probation period");
			}

		} else {
			logger.info("You have already applied leave in the given duration.");
			throw new ErrorHandling("You have already applied leave in the given duration.");

		}
		// return true;
	}

	@Override
	public boolean validateBulkLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummryDto, List<String> messageList)
			throws ParseException, ErrorHandling {
		int appliedLeaveCount = 0;
		// validate leave entry is already present in database or not
		if (leaveBalanceSummryDto.getLeaveId() != null)
			appliedLeaveCount = leaveEntryRepository.checkDateValidation(leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getFromDate(), leaveBalanceSummryDto.getToDate(),
					leaveBalanceSummryDto.getLeaveId());
		else
			appliedLeaveCount = leaveEntryRepository.checkDateValidation1(leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getFromDate(), leaveBalanceSummryDto.getToDate());
		logger.info("appliedLeaveCount.." + appliedLeaveCount);
		Employee employee = employeePersonalInformationService.getEmployeeInfo(leaveBalanceSummryDto.getEmployeeId());
		logger.info("appliedLeaveCount :" + appliedLeaveCount);
		if (appliedLeaveCount < 1) {

			String leaveFromDateInString = getDateStringWirhYYYYMMDD(leaveBalanceSummryDto.getFromDate());
			String leaveToDateInString = getDateStringWirhYYYYMMDD(leaveBalanceSummryDto.getToDate());

			Date leavefromDate = new SimpleDateFormat("yyyy-MM-dd").parse(leaveFromDateInString);
			Date leavetoDate = new SimpleDateFormat("yyyy-MM-dd").parse(leaveToDateInString);

			// returning count number of month between leave applied days
			BigDecimal noOfMonths = checkNumberOfMonths(leavefromDate, leavetoDate);

			// returining first date of month
			LocalDate leaveMonthStartLocalDate = leavefromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			leaveMonthStartLocalDate = leaveMonthStartLocalDate.withDayOfMonth(1);

			// returining last date of month
			LocalDate leaveMonthEndLocalDate = leavetoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			leaveMonthEndLocalDate = leaveMonthEndLocalDate.withDayOfMonth(leaveMonthEndLocalDate.lengthOfMonth());

			Date leaveMonthStartDate = Date
					.from(leaveMonthStartLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			Date leaveMonthEndDate = Date.from(leaveMonthEndLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			// check how many leave applied between leave applied month
			BigDecimal approvedPendingDaysCount = (BigDecimal) leaveEntryRepository.getEmployeeApprovedLeaveInDuration(
					leaveMonthStartDate, leaveMonthEndDate, leaveBalanceSummryDto.getEmployeeId(),
					leaveBalanceSummryDto.getLeaveTypeId());

			// BigDecimal approvedPendingDays =
			// leaveEntryAdaptor.objectArrayToLongDays(approvedPendingDaysList);

			// check how many times leave apply between leave applied month
			int frequencyCount = leaveEntryRepository.getEmployeeMonthlyFrequencyCount(leaveMonthStartDate,
					leaveMonthEndDate, leaveBalanceSummryDto.getEmployeeId(), leaveBalanceSummryDto.getLeaveTypeId());
			BigDecimal frequencyCountOfEmployee = new BigDecimal(frequencyCount);

			BigDecimal leavePendingBalanceCount = new BigDecimal("0");
			BigDecimal maxLeaveInMonthly = new BigDecimal("0.0");
			BigDecimal leaveFrequencyMonthly = new BigDecimal("0.0");
			BigDecimal leaveAppliedDaysCount = new BigDecimal("0.0");

			Long probationDays = 0l;

			if (leaveBalanceSummryDto.getDays() != null)
				leaveAppliedDaysCount = leaveBalanceSummryDto.getDays();

			if (leaveBalanceSummryDto.getLeaveBalancedCount() != null)
				leavePendingBalanceCount = leaveBalanceSummryDto.getLeaveBalancedCount();

			if (leaveBalanceSummryDto.getMaxLeaveInMonth() != null) {
				maxLeaveInMonthly = new BigDecimal(noOfMonths.longValue() * leaveBalanceSummryDto.getMaxLeaveInMonth());
				maxLeaveInMonthly = maxLeaveInMonthly.subtract(approvedPendingDaysCount); // .add(employeePndingLeaveInDurationBasedOnPKDays);
			}

			if (leaveBalanceSummryDto.getLeaveFrequencyInMonth() != null)
				leaveFrequencyMonthly = new BigDecimal(leaveBalanceSummryDto.getLeaveFrequencyInMonth());

			if (employee.getProbationDays() != null)// get provision days from employee table
				probationDays = employee.getProbationDays();

			// provision condition check
			if (leaveBalanceSummryDto.getIsLeaveInProbation() != null
					&& leaveBalanceSummryDto.getIsLeaveInProbation().equals("Y")
					|| probationDays < getDifferenceDays(getFormatedDate(employee.getDateOfJoining()), // employee.getDateOfJoining()
																										// pass from
																										// employee
																										// table
							new Date())) {

				//
				if (leaveAppliedDaysCount.compareTo(leavePendingBalanceCount) > 0) {
					logger.info("You can't apply leave, due to insufficient balance. "
							+ leaveBalanceSummryDto.getEmployeeCode());
//					throw new ErrorHandling("You can't apply leave, due to insufficient balance. "+leaveBalanceSummryDto.getEmployeeCode());
					messageList.add("You can't apply leave, due to insufficient balance. " + leaveBalanceSummryDto.getEmployeeCode());

				} else {

					if (leaveAppliedDaysCount.compareTo(maxLeaveInMonthly) > 0) {
//						throw new ErrorHandling("You can't apply  " + leaveAppliedDaysCount + " day(s) "
//								+ leaveBalanceSummryDto.getLeaveName() + " in a month, limit exceeded " +leaveBalanceSummryDto.getEmployeeCode());
						messageList.add("You can't apply  " + leaveAppliedDaysCount + " day(s) "
								+ leaveBalanceSummryDto.getLeaveName() + " in a month, limit exceeded "
								+ leaveBalanceSummryDto.getEmployeeCode());
					} else {

						// BigDecimal bigLeaveFrequencyMonthly = new BigDecimal(leaveFrequencyMonthly);
						// BigDecimal bigFrequencyCount = new BigDecimal(frequencyCount);
						if (frequencyCountOfEmployee.compareTo(leaveFrequencyMonthly) < 0) {
							// logger.info("in if
							// leaveEntryRepository.save(leaveEntry);"+leaveEntryRepository.save(leaveEntry));

							return true;

						} else {
							logger.info("You can't apply leave, monthly limit quota exceeded. " + leaveBalanceSummryDto.getEmployeeCode());
//							throw new ErrorHandling("You can't apply leave, monthly limit quota exceeded. "+leaveBalanceSummryDto.getEmployeeCode());
							messageList.add("You can't apply leave, monthly limit quota exceeded. " + leaveBalanceSummryDto.getEmployeeCode());
						}
					}
				}
			} else {
				logger.info("Can't apply leave in probation period " + leaveBalanceSummryDto.getEmployeeCode());
//				throw new ErrorHandling("Can't apply leave in probation period "+leaveBalanceSummryDto.getEmployeeCode());
				messageList.add("Can't apply leave in probation period " + leaveBalanceSummryDto.getEmployeeCode());
			}

		} else {
			logger.info("You have already applied leave in the given duration.");
//			throw new ErrorHandling("You have already applied leave in the given duration. "+leaveBalanceSummryDto.getEmployeeCode());
			messageList.add("You have already applied leave in the given duration. " + leaveBalanceSummryDto.getEmployeeCode());

		}
		return true;
	}

	@Override
	public LeaveValidationResult appliedBulkLeaveDays(LeaveEntryDTO leaveEntryDto, List<String> employeeCode)
			throws ParseException, ErrorHandling {

		TreeSet<Date> holidayset = new TreeSet<>();
		TreeSet<Date> leaveEnterieSet = new TreeSet<>();
		TreeSet<Date> weeklyOffSet = new TreeSet<>();
		Date fromDates = leaveEntryDto.getFromDate();
		String processMonth = DateUtils.getMonthOfProcess(fromDates).toUpperCase() + "-"
				+ DateUtils.getYearOfProcess(fromDates);

		ReportPayOut reportPayOut = reportPayOutRepository.isPayrollProcessed(leaveEntryDto.getEmployeeId(),
				processMonth);

		if (reportPayOut != null) {
//			throw new ErrorHandling(
//					"You cannot apply leave on requested duration as payroll has been created already.");
			employeeCode.add(leaveEntryDto.getEmployeeCode());
			return null;
		}

		LeaveValidationResult leaveValidationResult = new LeaveValidationResult();
		Long leaveMasterId = leaveEntryDto.getLeaveTypeId();

		// SandWichRule sandWichRule =
		// sandWichRuleRepository.getSandwichRules(leaveEntryDto.getCompanyId());
		TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHd(leaveEntryDto.getCompanyId());
		logger.info("tmsLeaveRuleHdList...." + tmsLeaveRuleHd);

		boolean weekOffBetweenTwoLeave = false;
		boolean holidayBetweenTwoAbsent = false;
		boolean holidayBetweenTwoLeave = false;
		boolean weekOffBetweenTwoAbsent = false;
		for (TMSLeaveRules tmsLeaveRule : tmsLeaveRuleHd.getTmsleaveRules()) {
			if (("WRR").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoLeave = true;
			}
			if (("WOL").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoLeave = true;
			}
			if (("HOA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoAbsent = true;
			}
			if (("WOA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoAbsent = true;
			}
		}

		List<Object[]> holidayList = holidayRepository.findAllHoliday(leaveEntryDto.getCompanyId());// leaveEntryDto.getCompanyId()
		List<TMSHolidays> holidaysList = holidayAdaptor.holidayObjectlistToDatabaseModelList(holidayList);
		logger.info("holidayList...." + holidayList);

		// new weekly off pattern implement
//		List<Object[]> weekOffPattenList = leaveEntryRepository.getWeekOffPattern(leaveEntryDto.getEmployeeId());

		List<Object[]> weekOffPattenList = leaveEntryRepository.getWeekOffPatternList(leaveEntryDto.getEmployeeId());

		LeaveBalanceDTO leaveBalanceDto = leaveEntryAdaptor.weekOffPattenObjToUiDto(weekOffPattenList);

		List<TMSLeaveEntry> tmsLeaveEntryList = employeeLeaveService
				.getEmployeeLeaveEntry(leaveEntryDto.getEmployeeId());

		Collections.sort(tmsLeaveEntryList, (a1, a2) -> a1.getFromDate().compareTo(a2.getFromDate()));
		tmsLeaveEntryList.forEach(leaves -> {

			DateUtils.getDatesBetweenUsing(leaves.getFromDate(), leaves.getToDate()).forEach(localdate -> {
				leaveEnterieSet.add(DateUtils.getDateFromLocalDate(localdate));
			});

		});

		leaveValidationResult.setLeaveEnterieSet(leaveEnterieSet);

		logger.info("leaveBalanceDto...." + leaveBalanceDto);
		String strFromDate = getDateStringWirhYYYYMMDD(leaveEntryDto.getFromDate());
		String strToDate = getDateStringWirhYYYYMMDD(leaveEntryDto.getToDate());

		Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(strFromDate);
		Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(strToDate);
		List<LocalDate> dateList = DateUtils.getDatesBetweenUsingForFormate(leaveEntryDto.getFromDate(),
				leaveEntryDto.getToDate());

		/*
		 * for(int i=0;i<=dateList.size()-1;i++) {
		 * 
		 * // leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).
		 * toString()); }
		 */
		/*
		 * long timeDiff = Math.abs(toDate.getTime() - fromDate.getTime()); double
		 * diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
		 * logger.info("diffDays...."+diffDays);
		 */
		leaveValidationResult.setTotalLeaveApplyDays(new BigDecimal(0.0));
		leaveValidationResult.setHolidaysOffCount(new BigDecimal(0.0));
		leaveValidationResult.setWeekllyOffCount(new BigDecimal(0.0));
		leaveValidationResult.setWeekholidaysOffCount(new BigDecimal(0.0));
		leaveValidationResult.setSandwhichDaysCount(new BigDecimal(0.0));

		List<TMSWeekOffChildPatternDTO> patternDayList = new ArrayList<>();
		// new weeklyoffpattern rule need to implemented here
		if (leaveBalanceDto.getTmsWeekOffChildPatternDTO() != null) {
//			String patternDays = leaveBalanceDto.getPatternDays();
			patternDayList = leaveBalanceDto.getTmsWeekOffChildPatternDTO();
			patternDayList.forEach(day -> {
				leaveValidationResult.getWeeklydayset().add(day.getDayName());
			});
		}

//old code
//		List<String> patternDayList = new ArrayList<>();
//		if (leaveBalanceDto.getPatternDays() != null) {
//			String patternDays = leaveBalanceDto.getPatternDays();
//			patternDayList = Arrays.asList(patternDays.split(","));
//			patternDayList.forEach(day -> {
//				leaveValidationResult.getWeeklydayset().add(day);
//			});
//		}

//		weeklyOfPetternCount(fromDate, toDate, patternDayList, leaveValidationResult, weeklyOffSet);

		weeklyOfPetternCount(fromDate, toDate, patternDayList, leaveValidationResult, weeklyOffSet);
		holidayOfPetternCount(fromDate, toDate, holidaysList, leaveValidationResult, holidayset);

		if (fromDate.compareTo(toDate) == 0) {
			if (weeklyOffSet.contains(fromDate))
				throw new ErrorHandling("You can't apply leave on weeklyOff");
			if (holidayset.contains(fromDate))
				throw new ErrorHandling("You can't apply leave on holiday");
		}

		if (weekOffBetweenTwoLeave || holidayBetweenTwoLeave || weekOffBetweenTwoAbsent || holidayBetweenTwoAbsent) {
			sandwichOfPatternCountForFromDate(fromDate, leaveValidationResult, weekOffBetweenTwoLeave,
					holidayBetweenTwoLeave, weekOffBetweenTwoAbsent, holidayBetweenTwoAbsent, patternDayList);
			sandwichOfPatternCountForToDate(toDate, leaveValidationResult, weekOffBetweenTwoLeave,
					holidayBetweenTwoLeave, weekOffBetweenTwoAbsent, holidayBetweenTwoAbsent, patternDayList);

		}

		BigDecimal actualLeaveAppliedDays = actualLeaveAppliedDaysCount(leaveValidationResult, fromDate, toDate,
				weekOffBetweenTwoLeave, holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent,
				leaveBalanceDto.getTmsWeekOffChildPatternDTO());

		// old code
//		BigDecimal actualLeaveAppliedDays = actualLeaveAppliedDaysCount(leaveValidationResult, fromDate, toDate,
//				weekOffBetweenTwoLeave, holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent);

		leaveValidationResult.setActulappliedDaysCount(actualLeaveAppliedDays);
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDatesFromSet());
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDatesToSet());
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDates());
		leaveValidationResult
				.setSandwhichDaysCount(new BigDecimal(leaveValidationResult.getFinalSandwichDates().size()));
		leaveValidationResult.setTotalLeaveApplyDays(
				leaveValidationResult.getActulappliedDaysCount().add(leaveValidationResult.getSandwhichDaysCount()));
		if (leaveValidationResult.getLeaveAsAbsentDaysCount() != null)
			leaveValidationResult.setTotalLeaveApplyDays(leaveValidationResult.getTotalLeaveApplyDays()
					.add(leaveValidationResult.getLeaveAsAbsentDaysCount()));

		LeaveTypeMasterEnum leaveTypeEnum = LeaveTypeMasterEnum.getByValue(leaveMasterId);
		logger.info("enum2==>" + String.valueOf(leaveTypeEnum));
		if (StatusMessage.LOP.equalsIgnoreCase(String.valueOf(leaveTypeEnum))) {

			if (StatusMessage.FULL_DAY.equalsIgnoreCase(leaveEntryDto.getHalf_fullDay())) {

				leaveValidationResult.setLeaveNature(StatusMessage.LeaveLOP);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.SandwitchLOP);

			} else {
				leaveValidationResult.setLeaveNature(StatusMessage.HalfDayLOP);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.SandwitchLOP);

			}
		} else {

			if (StatusMessage.FULL_DAY.equalsIgnoreCase(leaveEntryDto.getHalf_fullDay())) {

				leaveValidationResult.setLeaveNature(StatusMessage.LeaveFullDays);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.LeaveSandwitch);

			} else {
				leaveValidationResult.setLeaveNature(StatusMessage.LeaveHalfday);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.LeaveSandwitch);

			}

		}

		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.setTimeZone(TimeZone.getDefault());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String leaveValidationResultJson = null;

		// BigDecimal actualLeaveAppliedDays = new BigDecimal(10);
		return leaveValidationResult;
	}

	@Transactional
	@SuppressWarnings("null")
	@Override
	public TMSLeaveEntry saveLeaveEntry(TMSLeaveEntry leaveEntry, LeaveBalanceSummryDTO leaveBalanceSummryDto) {
		TMSLeaveEntry tmsLeaveEntry = leaveEntryRepository.save(leaveEntry);
		logger.info("Before 	employeeLeaveDatewiseService ");
		if (leaveBalanceSummryDto.getLeaveId() == null) {
			try {
				employeeLeaveDatewiseService.saveLeaveEntry(leaveBalanceSummryDto, tmsLeaveEntry);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			employeeLeaveDatewiseService.updateLeaveEntry(leaveEntry);

		}
		String leaveName = null;
		List<Object[]> findLeaveName = tmsLeaveTypeRepository.findLeaveName(leaveBalanceSummryDto.getLeaveTypeId());
		for (Object[] objects : findLeaveName) {
			leaveName = objects[0].toString();
		}
		String firstName = "";
		// String leaveName = leaveBalanceSummryDto.getLeaveName();
		String toReporting = "", ccReportingTo = "";
		Long toReportingId = 0L;
//		notificationMailSmsDto.setNotificationType("LA");
		List<Object[]> reportingTo = employeePersonalInformationService
				.getEmpReportingToEmail(tmsLeaveEntry.getEmployeeId());

		Employee employee = employeePersonalInformationService.getEmployeeInfo(tmsLeaveEntry.getEmployeeId());
		String query = "";
		List<String> ccNotify = new ArrayList<String>();
//		if (tmsLeaveEntry.getStatus().equals(StatusMessage.CANCEL_CODE)) {
//		List<Object[]> notifyLeave=	leaveEntryRepository.getEmpNotifyLeaveEntry(tmsLeaveEntry.getLeaveId());
//	
//			if (notifyLeave != null) {
//				for (Object tmsLeave[] : notifyLeave) {
//					Long empId = tmsLeave[0] != null ? Long.parseLong(tmsLeave[0].toString()) : null;
//					query = "SELECT emp.officialEmail,emp.employeeId  FROM Employee emp  WHERE  emp.companyId=?1  AND emp.employeeId IN ("
//							+ empId + ") AND emp.activeStatus='AC' ";
//				}
//			}
//			 
//		} else {
		query = "SELECT emp.officialEmail,emp.employeeId  FROM Employee emp  WHERE  emp.companyId=?1  AND emp.employeeId IN ("
				+ tmsLeaveEntry.getNotifyEmployee() + ") AND emp.activeStatus='AC' ";
		// }
		Query nativeQuery = eManager.createNativeQuery(query);
		nativeQuery.setParameter(1, tmsLeaveEntry.getCompanyId());
		List<Object[]> resultList = nativeQuery.getResultList();
		if (resultList != null) {
			for (Object strings[] : resultList) {
				String officialEmail = strings[0] != null ? (String) strings[0] : null;
				if (officialEmail != null)
					ccNotify.add(officialEmail);
			}
		}

		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		EmailNotificationMaster listEmail = null;
		if (tmsLeaveEntry.getApprovalId() == null) {
			listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.LEAVE_MAIL_CODE);
		} else {
			listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.LEAVE_APPROVAL_OR_REJECT_CODE);
		}
		JavaMailSenderImpl mailSender = null;
		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("EmployeeLeaveServiceImpl .getJavaMailSender()" + listEmail.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);

		} else {
			logger.info("EmployeeLeaveServiceImpl.getJavaMailSender()" + listEmail.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");
		} // close email config
		if (tmsLeaveEntry.getApprovalId() == null) {
			if (reportingTo.size() > 0) {
				for (Object obj[] : reportingTo) {
					firstName = obj[4].toString();
					if (obj[2] != null)
						toReportingId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
					toReporting = obj[2] != null ? (String) obj[2] : null;
				}
			}
			// ReportingToManager
			List<Object[]> reportingToManager = employeePersonalInformationService
					.getEmpReportingToEmail(toReportingId);
			if (reportingToManager.size() > 0) {
				for (Object obj[] : reportingToManager) {
					ccReportingTo = obj[2] != null ? (String) obj[2] : null;
				}
			}

			if (tmsLeaveEntry.getStatus().equals(StatusMessage.CANCEL_CODE)) {
				ArrayList<String> reportinsgTo = pushNotificationsServices
						.getReportingEmployeeCode(employee.getEmployeeId());
				try {
					fcm.fcmRequest(reportinsgTo, "Leave Apply",
							employee.getFirstName() + " " + employee.getLastName() + " cancelled leave", "teamleave",
							tmsLeaveEntry.getLeaveId(), StatusMessage.DESCRIPTION);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			} else {
				ArrayList<String> reportinsgTo = pushNotificationsServices
						.getReportingEmployeeCode(employee.getEmployeeId());
				try {
					fcm.fcmRequest(reportinsgTo, "Leave Apply",
							employee.getFirstName() + " " + employee.getLastName() + " applied leave", "teamleave",
							tmsLeaveEntry.getLeaveId(), StatusMessage.RAF);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String fromDate = sm.format(tmsLeaveEntry.getFromDate());
			String toDate = sm.format(tmsLeaveEntry.getToDate());
			String path = environment.getProperty("application.companyLogoPath");
			Company company = companyServiceImpl.getCompany(tmsLeaveEntry.getCompanyId());
			String companyLogoPath = path + company.getCompanyLogoPath();

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", employee.getFirstName());
				model.put("lastName", employee.getLastName());
				model.put("fromDate", fromDate);
				model.put("toDate", toDate);
				model.put("category", leaveBalanceSummryDto.getLeaveName());
				model.put("day", tmsLeaveEntry.getDays());
				model.put("companyLogoPath", companyLogoPath);
				model.put("employeeCode", employee.getEmployeeCode());
//				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_request.vm",
//						"UTF-8", model);
				String text = "";

				if (tmsLeaveEntry.getStatus().equals(StatusMessage.CANCEL_CODE)) {
					// Employee cancelledBy =
					// employeePersonalInformationService.fetchEmployeeByIdCode(tmsLeaveEntry.getApprovalId());
					model.put("cancelledByfirstName", employee.getFirstName());
					model.put("cancelledByLastName", employee.getLastName());
					model.put("cancelledByEmpCode", employee.getEmployeeCode());
					model.put("employeeCode", employee.getEmployeeCode());
					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_cancelled.vm","UTF-8", model);

				} else
					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_request.vm",
							"UTF-8", model);

				// }
//			else
//					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_approved.vm",
//							"UTF-8", model);

				if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
					mimeMessageHelper.setFrom(listEmail.getUserName());
				} else {
					mimeMessageHelper.setFrom(listEmail.getUserName());
				}
				if (listEmail.getSubject() != null && !listEmail.getSubject().trim().equals("")) {
					mimeMessageHelper.setSubject(listEmail.getSubject());
				} else {
					mimeMessageHelper.setSubject(listEmail.getSubject());
				}

				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
					String bccList = listEmail.getBcc();
					String[] bcc = bccList.split(",");
					mimeMessageHelper.setBcc(bcc);
				}

				/**
				 * getIsApplicableOnReportingTo
				 */
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = listEmail.getToMail();

					if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {

						if (toReporting != null && !toReporting.equals("")) {
							toList = toReporting + "," + toList;
						}
					}
					String[] to1 = toList.split(",");
					mimeMessageHelper.setTo(to1);

				} else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
					String toList = toReporting;
					mimeMessageHelper.setTo(toList);
				} else {
					if (toReporting != null || toReporting.equalsIgnoreCase("null")
							|| toReporting.equalsIgnoreCase("")) {
						String toListNew = toReporting;
						mimeMessageHelper.setTo(toListNew);
					}
//					else {
//						String toListNew = "noreply@gmail.com";
//						mimeMessageHelper.setTo(toListNew);
//					}
				}
				/**
				 * getIsApplicableOnReportingToManager
				 */

				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
						if (ccReportingTo != null && !ccReportingTo.equals("")) {
							ccList = ccReportingTo + "," + ccList;
						}
					}
					String[] cc1 = ccList.split(",");
					if (ccNotify.size() > 0) {
						ccNotify.addAll(Arrays.asList(cc1));
						String[] stringArray = ccNotify
								.toArray(new String[0].toString().replace("[", "").replace("]", "").split(","));
						mimeMessageHelper.setCc(stringArray);
					} else
						mimeMessageHelper.setCc(cc1);

				} // close if
				else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
					String ccList = "";
					ccList = ccReportingTo;
					mimeMessageHelper.setCc(ccList);
				}

				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();

					if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
						if (ccReportingTo != null && !ccReportingTo.equals("")) {
							ccList = ccReportingTo + "," + ccList;
						}
					}
					String[] cc1 = ccList.split(",");
					if (ccNotify.size() > 0) {
						ccNotify.addAll(Arrays.asList(cc1));
						String[] stringArray = ccNotify
								.toArray(new String[0].toString().replace("[", "").replace("]", "").split(","));
						mimeMessageHelper.setCc(stringArray);
					}
				} // close if
				else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
					String ccList = "";
					if (ccReportingTo != null && !ccReportingTo.equals("")) {
						ccList = ccReportingTo;
						if (ccNotify.size() > 0) {
							ccNotify.addAll(Arrays.asList(ccList));
							String[] stringArray = ccNotify
									.toArray(new String[0].toString().replace("[", "").replace("]", "").split(","));
							mimeMessageHelper.setCc(stringArray);
						}
					}
				}

				mimeMessageHelper.setText(text, true);
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("mail send Successfully");
					} catch (Exception ex) {
						logger.info("mail send Failed");
						ex.printStackTrace();
					}
				} else
					logger.info("mail send failed");
			} // close try block
			catch (MessagingException e) {

				e.printStackTrace();
			}
		} // if close
		else {
			String status = "";
			String toReporting1 = "", ccReportingTo1 = null;
			Long toReportingIds = 0L;
			if (tmsLeaveEntry.getStatus().equals(StatusMessage.APPROVED_CODE)) {
				status = StatusMessage.APPROVED_VALUE;
				Long useId = fcmNotificationRepository.getUserId(employee.getEmployeeId());
				ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
				try {
					fcm.fcmRequest(tokenList, "Leave Apply", "Leave has been Approved", "myleave",
							tmsLeaveEntry.getLeaveId(), StatusMessage.DESCRIPTION);
				} catch (Exception e) {
					// e.printStackTrace();
				}

			} else {
				status = StatusMessage.REJECTED_VALUE;
				Long useId = fcmNotificationRepository.getUserId(employee.getEmployeeId());
				ArrayList<String> tokenList = fcmNotificationRepository.getTokenList(useId);
				try {
					fcm.fcmRequest(tokenList, "Leave Apply", "Leave has been Rejected", "myleave",
							tmsLeaveEntry.getLeaveId(), StatusMessage.DESCRIPTION);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			List<Object[]> reportingToApp = employeePersonalInformationService
					.getEmpReportingToEmail(tmsLeaveEntry.getEmployeeId());

			// String empCode = "";
			if (reportingToApp.size() > 0) {
				for (Object obj[] : reportingToApp) {
					toReportingIds = Long.valueOf(obj[0].toString());
					// empCode = obj[1].toString();
					toReporting1 = obj[2] != null ? (String) obj[2] : null;

				}
			}
			// ReportingToManager
			List<Object[]> reportingToManager = employeePersonalInformationService
					.getEmpReportingToEmail(toReportingIds);
			if (reportingToManager.size() > 0) {
				for (Object obj[] : reportingToManager) {
					ccReportingTo1 = obj[2] != null ? (String) obj[2] : null;
				}
			}

			SimpleDateFormat sm = new SimpleDateFormat(StatusMessage.DATE_FORMAT);
			String fromDate = sm.format(tmsLeaveEntry.getFromDate());
			String toDate = sm.format(tmsLeaveEntry.getToDate());
			String path = environment.getProperty("application.companyLogoPath");
			Company company = companyServiceImpl.getCompany(tmsLeaveEntry.getCompanyId());
			String companyLogoPath = path + company.getCompanyLogoPath();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", employee.getFirstName());
				model.put("lastName", employee.getLastName());
				model.put("fromDate", fromDate);
				model.put("toDate", toDate);
				model.put("status", status);
				model.put("companyLogoPath", companyLogoPath);
				model.put("category", leaveName);
				String text = "";
				if (tmsLeaveEntry.getStatus().equals(StatusMessage.APPROVED_CODE)) {
					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_approved.vm",
							"UTF-8", model);
				}
				if (tmsLeaveEntry.getStatus().equals(StatusMessage.CANCEL_CODE)) {
					System.out.println("EmployeeLeaveServiceImpl.saveLeaveEntry()" + tmsLeaveEntry.getApprovalId());
					Employee cancelledBy = employeePersonalInformationService
							.fetchEmployeeByIdCode(tmsLeaveEntry.getApprovalId());
					model.put("cancelledByfirstName", cancelledBy.getFirstName());
					model.put("cancelledByLastName", cancelledBy.getLastName());
					model.put("cancelledByEmpCode", cancelledBy.getEmployeeCode());
					model.put("employeeCode", employee.getEmployeeCode());
					System.out.println("EmployeeLeaveServiceImpl.saveLeaveEntry()" + cancelledBy.getFirstName());
					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/leave_cancelled.vm",
							"UTF-8", model);
				}
				if (listEmail.getFromMail() != null && !listEmail.getFromMail().trim().equals("")) {
					mimeMessageHelper.setFrom(listEmail.getUserName());
				} else {
					mimeMessageHelper.setFrom(listEmail.getUserName());
				}
				if (listEmail.getSubject() != null && !listEmail.getSubject().trim().equals("")) {
					mimeMessageHelper.setSubject(listEmail.getSubject());
				} else {
					mimeMessageHelper.setSubject(listEmail.getSubject());
				}

				/**
				 * getIsApplicableOnReportingTo
				 */
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = "";
					if (employee.getOfficialEmail() == null || employee.getOfficialEmail().equals("")) {
						toList = listEmail.getToMail();
					} else {
						toList = employee.getOfficialEmail() + "," + listEmail.getToMail();
					}
					if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
						if (toReporting1 != null && !toReporting1.equals("")) {
							toList = toReporting1 + "," + toList;
						}

					}
					String[] to1 = toList.split(",");
					mimeMessageHelper.setTo(to1);

				} // close if
				else if (listEmail.getIsApplicableOnReportingTo().equals("Y")) {
					String toList = employee.getOfficialEmail() + "," + toReporting1;
					String[] to1 = toList.split(",");
					mimeMessageHelper.setTo(to1);
				} else {
					if (toReporting1 != null || toReporting1.equalsIgnoreCase("null")
							|| toReporting1.equalsIgnoreCase("")) {
						String toListNew = employee.getOfficialEmail();
						mimeMessageHelper.setTo(toListNew);
					} else {
						String toListNew = StatusMessage.EMAIL;
						mimeMessageHelper.setTo(toListNew);
					}
				} // end
				/**
				 * getIsApplicableOnReportingToManager
				 */

				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();

					if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
						if (ccReportingTo1 != null && !ccReportingTo1.equals("")) {
							ccList = ccReportingTo1 + "," + ccList;
						}

					}
					String[] cc1 = ccList.split(",");
					mimeMessageHelper.setCc(cc1);

				} else if (listEmail.getIsApplicableOnReportingToManager().equals("Y")) {
					String ccList = null;
					if (ccReportingTo1 != null) {
						ccList = ccReportingTo1;
						mimeMessageHelper.setCc(ccList);
					}
				}

				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
					String bccList = listEmail.getBcc();
					String[] bcc = bccList.split(",");
					mimeMessageHelper.setBcc(bcc);
				}
				mimeMessageHelper.setText(text, true);
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("mail send Successfully");
					} catch (Exception ex) {
						logger.info("mail send fail");
						ex.printStackTrace();
					}
				} // close if
				else
					logger.info("mail send fail");
			} catch (MessagingException e) {

				e.printStackTrace();
			}
		}
		return leaveEntry;
	}// close method

	@Override
	public List<Object[]> leaveEntryList(Long companyId) {
		return leaveEntryRepository.leaveEntryList(companyId);
	}

	@Override
	public LeaveEntryDTO getLeaveEntry(Long leaveId) {
		Employee approvalEmp = null;
		Employee employeeEmp = null;

		TMSLeaveEntry tmsLeaveEntry = leaveEntryRepository.findOne(leaveId);
		if (tmsLeaveEntry.getEmployeeId() != null) {
			Long employeeId = tmsLeaveEntry.getEmployeeId();
			employeeEmp = employeePersonalInformationService.getEmployeeInfo(employeeId);
		}
		if (tmsLeaveEntry.getApprovalId() != null) {
			Long approvalId = tmsLeaveEntry.getApprovalId();
			approvalEmp = employeePersonalInformationService.getEmployeeInfo(approvalId);
		}

		logger.info("employeeDto---->" + tmsLeaveEntry.getNotifyEmployee());
		String query = "SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath,emp.officialEmail ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId WHERE  emp.companyId=?1  AND emp.employeeId IN ("
				+ tmsLeaveEntry.getNotifyEmployee() + ") AND emp.activeStatus='AC' ";

		Query nativeQuery = eManager.createNativeQuery(query);
		nativeQuery.setParameter(1, tmsLeaveEntry.getCompanyId());
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoListNotifyEmployee(resultList);
		LeaveEntryDTO leaveEntryDto = leaveEntryAdaptor.databaseModelToUiDto(tmsLeaveEntry, employeeEmp, approvalEmp,
				employeeDtoList);

		return leaveEntryDto;
	}

	@Override
	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId) {
		return leaveEntryRepository.getEmployeeLeaveEntry(employeeId);
	}

	@Override
	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId, String processMonth) {
		// TODO Auto-generated method stub

		return leaveEntryRepository.getEmployeeLeaveEntry(employeeId, DateUtils.getMonthForProcessMonth(processMonth));
	}

	@Override
	public List<LeaveBalanceDTO> getEmployeeLeaveBalance(Long employeeId) {
		List<Object[]> leaveBalanceObjectDtoList = leaveEntryRepository.getEmployeeLeaveBalance(employeeId);
		List<Object[]> employeeDetailsList = leaveEntryRepository.getEmployeeDetails(employeeId);
		return leaveEntryAdaptor.objectArrayToUiDtoList(leaveBalanceObjectDtoList, employeeDetailsList, employeeId);
	}

	@Override
	public String appliedLeaveDays(LeaveEntryDTO leaveEntryDto) throws ParseException, ErrorHandling {

		TreeSet<Date> holidayset = new TreeSet<>();
		TreeSet<Date> leaveEnterieSet = new TreeSet<>();
		TreeSet<Date> weeklyOffSet = new TreeSet<>();
		Date fromDates = leaveEntryDto.getFromDate();
		String processMonth = DateUtils.getMonthOfProcess(fromDates).toUpperCase() + "-"
				+ DateUtils.getYearOfProcess(fromDates);

		ReportPayOut reportPayOut = reportPayOutRepository.isPayrollProcessed(leaveEntryDto.getEmployeeId(),
				processMonth);

		if (reportPayOut != null)
			throw new ErrorHandling(
					"You cannot apply leave on requested duration as payroll has been created already.");

		LeaveValidationResult leaveValidationResult = new LeaveValidationResult();
		Long leaveMasterId = leaveTypeService.getLeaveTypeById(leaveEntryDto.getLeaveTypeId()).getTmsleaveTypeMaster()
				.getLeaveId();

		// SandWichRule sandWichRule =
		// sandWichRuleRepository.getSandwichRules(leaveEntryDto.getCompanyId());
		TMSLeaveRulesHd tmsLeaveRuleHd = leaveRulesHdService.findLeaveRulesHd(leaveEntryDto.getCompanyId());
		logger.info("tmsLeaveRuleHdList...." + tmsLeaveRuleHd);

		boolean weekOffBetweenTwoLeave = false;
		boolean holidayBetweenTwoAbsent = false;
		boolean holidayBetweenTwoLeave = false;
		boolean weekOffBetweenTwoAbsent = false;
		for (TMSLeaveRules tmsLeaveRule : tmsLeaveRuleHd.getTmsleaveRules()) {
			if (("WRR").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoLeave = true;
			}
			if (("WOL").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoLeave = true;
			}
			if (("HOA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				holidayBetweenTwoAbsent = true;
			}
			if (("WOA").equals(tmsLeaveRule.getTmsleaveRuleMaster().getRuleCode())
					&& ("AC").equals(tmsLeaveRule.getActiveStatus())) {
				weekOffBetweenTwoAbsent = true;
			}
		}

		List<Object[]> holidayList = holidayRepository.findAllHoliday(leaveEntryDto.getCompanyId());// leaveEntryDto.getCompanyId()
		List<TMSHolidays> holidaysList = holidayAdaptor.holidayObjectlistToDatabaseModelList(holidayList);
		logger.info("holidayList...." + holidayList);

		// new weekly off pattern implement
//		List<Object[]> weekOffPattenList = leaveEntryRepository.getWeekOffPattern(leaveEntryDto.getEmployeeId());

		List<Object[]> weekOffPattenList = leaveEntryRepository.getWeekOffPatternList(leaveEntryDto.getEmployeeId());

		LeaveBalanceDTO leaveBalanceDto = leaveEntryAdaptor.weekOffPattenObjToUiDto(weekOffPattenList);

		List<TMSLeaveEntry> tmsLeaveEntryList = employeeLeaveService
				.getEmployeeLeaveEntry(leaveEntryDto.getEmployeeId());

		Collections.sort(tmsLeaveEntryList, (a1, a2) -> a1.getFromDate().compareTo(a2.getFromDate()));
		tmsLeaveEntryList.forEach(leaves -> {

			DateUtils.getDatesBetweenUsing(leaves.getFromDate(), leaves.getToDate()).forEach(localdate -> {
				leaveEnterieSet.add(DateUtils.getDateFromLocalDate(localdate));
			});

		});

		leaveValidationResult.setLeaveEnterieSet(leaveEnterieSet);

		logger.info("leaveBalanceDto...." + leaveBalanceDto);
		String strFromDate = getDateStringWirhYYYYMMDD(leaveEntryDto.getFromDate());
		String strToDate = getDateStringWirhYYYYMMDD(leaveEntryDto.getToDate());

		Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(strFromDate);
		Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(strToDate);
		List<LocalDate> dateList = DateUtils.getDatesBetweenUsingForFormate(leaveEntryDto.getFromDate(),
				leaveEntryDto.getToDate());

		/*
		 * for(int i=0;i<=dateList.size()-1;i++) {
		 * 
		 * // leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).
		 * toString()); }
		 */
		/*
		 * long timeDiff = Math.abs(toDate.getTime() - fromDate.getTime()); double
		 * diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
		 * logger.info("diffDays...."+diffDays);
		 */
		leaveValidationResult.setTotalLeaveApplyDays(new BigDecimal(0.0));
		leaveValidationResult.setHolidaysOffCount(new BigDecimal(0.0));
		leaveValidationResult.setWeekllyOffCount(new BigDecimal(0.0));
		leaveValidationResult.setWeekholidaysOffCount(new BigDecimal(0.0));
		leaveValidationResult.setSandwhichDaysCount(new BigDecimal(0.0));

		List<TMSWeekOffChildPatternDTO> patternDayList = new ArrayList<>();
		// new weeklyoffpattern rule need to implemented here
		if (leaveBalanceDto.getTmsWeekOffChildPatternDTO() != null) {
//			String patternDays = leaveBalanceDto.getPatternDays();
			patternDayList = leaveBalanceDto.getTmsWeekOffChildPatternDTO();
			patternDayList.forEach(day -> {
				leaveValidationResult.getWeeklydayset().add(day.getDayName());
			});
		}

//old code
//		List<String> patternDayList = new ArrayList<>();
//		if (leaveBalanceDto.getPatternDays() != null) {
//			String patternDays = leaveBalanceDto.getPatternDays();
//			patternDayList = Arrays.asList(patternDays.split(","));
//			patternDayList.forEach(day -> {
//				leaveValidationResult.getWeeklydayset().add(day);
//			});
//		}

//		weeklyOfPetternCount(fromDate, toDate, patternDayList, leaveValidationResult, weeklyOffSet);

		weeklyOfPetternCount(fromDate, toDate, patternDayList, leaveValidationResult, weeklyOffSet);
		holidayOfPetternCount(fromDate, toDate, holidaysList, leaveValidationResult, holidayset);

		if (fromDate.compareTo(toDate) == 0) {
			if (weeklyOffSet.contains(fromDate))
				throw new ErrorHandling("You can't apply leave on weeklyOff");
			if (holidayset.contains(fromDate))
				throw new ErrorHandling("You can't apply leave on holiday");
		}

		if (weekOffBetweenTwoLeave || holidayBetweenTwoLeave || weekOffBetweenTwoAbsent || holidayBetweenTwoAbsent) {
			sandwichOfPatternCountForFromDate(fromDate, leaveValidationResult, weekOffBetweenTwoLeave,
					holidayBetweenTwoLeave, weekOffBetweenTwoAbsent, holidayBetweenTwoAbsent, patternDayList);
			sandwichOfPatternCountForToDate(toDate, leaveValidationResult, weekOffBetweenTwoLeave,
					holidayBetweenTwoLeave, weekOffBetweenTwoAbsent, holidayBetweenTwoAbsent, patternDayList);

		}

		BigDecimal actualLeaveAppliedDays = actualLeaveAppliedDaysCount(leaveValidationResult, fromDate, toDate,
				weekOffBetweenTwoLeave, holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent,
				leaveBalanceDto.getTmsWeekOffChildPatternDTO());

		// old code
//		BigDecimal actualLeaveAppliedDays = actualLeaveAppliedDaysCount(leaveValidationResult, fromDate, toDate,
//				weekOffBetweenTwoLeave, holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent);

		leaveValidationResult.setActulappliedDaysCount(actualLeaveAppliedDays);
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDatesFromSet());
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDatesToSet());
		leaveValidationResult.getFinalSandwichDates().addAll(leaveValidationResult.getSandwichDates());
		leaveValidationResult
				.setSandwhichDaysCount(new BigDecimal(leaveValidationResult.getFinalSandwichDates().size()));
		leaveValidationResult.setTotalLeaveApplyDays(
				leaveValidationResult.getActulappliedDaysCount().add(leaveValidationResult.getSandwhichDaysCount()));
		if (leaveValidationResult.getLeaveAsAbsentDaysCount() != null)
			leaveValidationResult.setTotalLeaveApplyDays(leaveValidationResult.getTotalLeaveApplyDays()
					.add(leaveValidationResult.getLeaveAsAbsentDaysCount()));

		LeaveTypeMasterEnum leaveTypeEnum = LeaveTypeMasterEnum.getByValue(leaveMasterId);
		logger.info("enum2==>" + String.valueOf(leaveTypeEnum));
		if (StatusMessage.LOP.equalsIgnoreCase(String.valueOf(leaveTypeEnum))) {

			if (StatusMessage.FULL_DAY.equalsIgnoreCase(leaveEntryDto.getHalf_fullDay())) {

				leaveValidationResult.setLeaveNature(StatusMessage.LeaveLOP);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.SandwitchLOP);

			} else {
				leaveValidationResult.setLeaveNature(StatusMessage.HalfDayLOP);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.SandwitchLOP);

			}
		} else {

			if (StatusMessage.FULL_DAY.equalsIgnoreCase(leaveEntryDto.getHalf_fullDay())) {

				leaveValidationResult.setLeaveNature(StatusMessage.LeaveFullDays);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.LeaveSandwitch);

			} else {
				leaveValidationResult.setLeaveNature(StatusMessage.LeaveHalfday);
				if (leaveValidationResult.getSandwhichDaysCount().compareTo(BigDecimal.ZERO) > 0)
					leaveValidationResult.setSandwitchNature(StatusMessage.LeaveSandwitch);

			}

		}

		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.setTimeZone(TimeZone.getDefault());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String leaveValidationResultJson = null;
		try {
			leaveValidationResultJson = objectMapper.writeValueAsString(leaveValidationResult);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// BigDecimal actualLeaveAppliedDays = new BigDecimal(10);
		return leaveValidationResultJson;
	}

	public static String getDateStringWirhYYYYMMDD(Date date) {
		String dateStr = df.format(date);

		return dateStr;
	}

	public void sandwichOfPatternCountForFromDate(Date fromDate, LeaveValidationResult leaveValidationResult,
			boolean weekendSandwizeFlag, boolean holidaySandwizeFlag, boolean weekOffBetweenTwoAbsent,
			boolean holidayBetweenTwoAbsent, List<TMSWeekOffChildPatternDTO> patternDayList) {

		List<Date> appliedSandwhichDates = new ArrayList<Date>();

		boolean sandwizappliedeflag = false;
		boolean leaveAsAbsentappliedeflag = false;
		int sandwhichDaysCount = 0;
		int leaveAsAbsentDaysCount = 0;
		fromDate = new Date(fromDate.getTime() - 24 * 60 * 60 * 1000);
		boolean conditinCheckflag = true;
		while (conditinCheckflag) {
			// i
			conditinCheckflag = false;

			if (leaveValidationResult.getWeeklydayset()
					.contains(new SimpleDateFormat("E").format(fromDate).toUpperCase())) {

				logger.info(fromDate + "==weeklyOff===");

				// new weeklyOffPattern rule impl
				for (TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO : patternDayList) {
					logger.info("applay Days is " + tmsWeekOffChildPatternDTO);
					if (tmsWeekOffChildPatternDTO.getDayName()
							.equalsIgnoreCase(new SimpleDateFormat("E").format(fromDate).toUpperCase())) {

						Instant instant = Instant.ofEpochMilli(fromDate.getTime());
						LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
						LocalDate fromLocalDate = localDateTime.toLocalDate();

						Date weeklyOffDate = DateUtils.findWeeklyOffDate(
								DayEnum.valueOf(tmsWeekOffChildPatternDTO.getDayName()).getDayId(),
								Integer.valueOf(tmsWeekOffChildPatternDTO.getPositionOfDay().toString()),
								fromLocalDate.getMonthValue() - 1, fromLocalDate.getYear());

						Instant instantWeek = Instant.ofEpochMilli(weeklyOffDate.getTime());
						LocalDateTime localDateTimeWeek = LocalDateTime.ofInstant(instantWeek, ZoneId.systemDefault());
						LocalDate weeklyOffLocalDate = localDateTimeWeek.toLocalDate();

						if (weeklyOffLocalDate.compareTo(fromLocalDate) == 0) {
							if (weekendSandwizeFlag) {
								conditinCheckflag = true;
								leaveValidationResult.getSandwichDatesFromSet().add(fromDate);
								sandwhichDaysCount++;
							}
							if (weekOffBetweenTwoAbsent) {
								conditinCheckflag = true;
								leaveValidationResult.getLeaveAsAbsentDates().add(fromDate);
								leaveAsAbsentDaysCount++;
							}

						}

					}
				}

//				if (weekendSandwizeFlag) {
//					conditinCheckflag = true;
//					leaveValidationResult.getSandwichDatesFromSet().add(fromDate);
//					sandwhichDaysCount++;
//				}
//				if (weekOffBetweenTwoAbsent) {
//					conditinCheckflag = true;
//					leaveValidationResult.getLeaveAsAbsentDates().add(fromDate);
//					leaveAsAbsentDaysCount++;
//				}

			}

			else if (DateCheckInTreeSet(leaveValidationResult.getAllHolidayset(), fromDate)) {
				if (holidaySandwizeFlag) {
					conditinCheckflag = true;
					sandwhichDaysCount++;
					leaveValidationResult.getSandwichDatesFromSet().add(fromDate);
				}
				if (holidayBetweenTwoAbsent) {
					conditinCheckflag = true;
					leaveValidationResult.getLeaveAsAbsentDates().add(fromDate);
					leaveAsAbsentDaysCount++;
				}

			}

			else if (DateCheckInTreeSet(leaveValidationResult.getLeaveEnterieSet(), fromDate)) {
				if (sandwhichDaysCount > 0) {
					sandwizappliedeflag = true;
					break;

				}
				if (leaveAsAbsentDaysCount > 0) {
					leaveAsAbsentappliedeflag = true;
					break;
				}

				else
					break;
			}

			logger.info("privios  : " + fromDate);
			fromDate = new Date(fromDate.getTime() - 24 * 60 * 60 * 1000);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);

			fromDate = calendar.getTime();
			logger.info("next :" + fromDate);
		}

		if (sandwizappliedeflag) {

			Iterator<Date> itDates = leaveValidationResult.getSandwichDatesFromSet().iterator();

			while (itDates.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = itDates.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				sirObj.setLeavefromtoDate(fromDate);
				sirObj.setLeaveNature(StatusMessage.LeaveSandwitch);
				leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj);
			}

			leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
			leaveValidationResult.setSandwhichDaysCount(new BigDecimal(sandwhichDaysCount));
		}
		/*
		 * else { leaveValidationResult.getSandwichDatesFromSet().clear();
		 * leaveValidationResult.getSandwichDatesFromSetObj().clear(); }
		 */
		if (leaveAsAbsentappliedeflag) {

			Iterator<Date> absentDate = leaveValidationResult.getLeaveAsAbsentDates().iterator();

			while (absentDate.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = absentDate.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				sirObj.setLeavefromtoDate(fromDate);
				sirObj.setLeaveNature(StatusMessage.SandwitchLOP);
				leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj);
			}
			leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
			leaveValidationResult.setLeaveAsAbsentDaysCount(new BigDecimal(leaveAsAbsentDaysCount));
		}
		/*
		 * else { leaveValidationResult.getSandwichDatesFromSet().clear();
		 * leaveValidationResult.getSandwichDatesFromSetObj().clear(); }
		 */
		if (!sandwizappliedeflag && !leaveAsAbsentappliedeflag) {
			if (leaveValidationResult.getSandwichDatesFromSet().size() > 0)
				leaveValidationResult.getSandwichDatesFromSet().clear();
			if (leaveValidationResult.getSandwichDatesFromSetObj().size() > 0)
				leaveValidationResult.getSandwichDatesFromSetObj().clear();
			if (leaveValidationResult.getAppliedSandwhichDates().size() > 0)
				leaveValidationResult.getAppliedSandwhichDates().clear();
		}
	}

	public boolean DateCheckInTreeSet(TreeSet<Date> leaveEnteriesDateSet, Date date) {

		Iterator<Date> itDates = leaveEnteriesDateSet.iterator();
		boolean conditional = false;
		while (itDates.hasNext()) {
			conditional = itDates.next().equals(date);
			if (conditional)
				break;

		}

		return conditional;

	}

	public static int getAlternatePattenWeeklyOff(LeaveValidationResult leaveValidationResult,
			List<TMSWeekOffChildPatternDTO> patternDayList, List<LocalDate> dateList, int actulappliedDaysCount) {

		List<Long> longList = new ArrayList<Long>();
		Map<String, List<Long>> positionDayMap = new HashMap<String, List<Long>>();

		for (TMSWeekOffChildPatternDTO weekOffChildPatternDTO : patternDayList) {

			if (positionDayMap.containsKey(weekOffChildPatternDTO.getDayName())) {
				longList.add(weekOffChildPatternDTO.getPositionOfDay());
			} else {
				if (!longList.isEmpty()) {
					// positionDayMap.put(weekOffChildPatternDTO.getDayName(), longList);
					longList = new ArrayList<Long>();
					longList.add(weekOffChildPatternDTO.getPositionOfDay());
					positionDayMap.put(weekOffChildPatternDTO.getDayName(), longList);
				} else {
					longList.add(weekOffChildPatternDTO.getPositionOfDay());
					positionDayMap.put(weekOffChildPatternDTO.getDayName(), longList);
				}

			}

		}

		for (int i = 0; i <= dateList.size() - 1; i++) {

			if (leaveValidationResult.getWeeklydayset().contains(
					new SimpleDateFormat("E").format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase())) {

				List<Integer> week = new ArrayList<Integer>();
				week.add(1);
				week.add(2);
				week.add(3);
				week.add(4);
				week.add(5);

				List<Integer> newweek = new ArrayList<Integer>();

				List<Long> weekmap = positionDayMap.get(new SimpleDateFormat("E")
						.format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase());

				for (Integer item : week) {
					if (!weekmap.contains(Long.valueOf(item.toString()))) {
						newweek.add(item);
					}
				}

				for (Integer PositionOfDay : newweek) {
					Date weeklyOffDate = DateUtils.findWeeklyOffDate(
							DayEnum.valueOf(new SimpleDateFormat("E")
									.format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase()).getDayId(),
							PositionOfDay, dateList.get(i).getMonthValue() - 1, dateList.get(i).getYear());

					Instant instant = Instant.ofEpochMilli(weeklyOffDate.getTime());
					LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
					LocalDate weeklyOffLocalDate = localDateTime.toLocalDate();

					LocalDate leaveApplyDate = dateList.get(i);

					if (weeklyOffLocalDate.compareTo(leaveApplyDate) == 0) {
						leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).toString());
						actulappliedDaysCount++;
					}

				}
			}

		}

		return actulappliedDaysCount;
	}

// new WeeklyOffPattern rule implemented 
	public BigDecimal sandwichOfPatternCount(Date fromDate, Date toDate, LeaveValidationResult leaveValidationResult,
			boolean weekendSandwizeFlag, boolean holidayAsAbsentFlag, boolean holidayBetweenTwoLeave,
			boolean weekOffBetweenTwoAbsent, List<TMSWeekOffChildPatternDTO> patternDayList) {

		List<Date> appliedSandwhichDates = new ArrayList<Date>();

		int sandwhichDaysCount = 0;
		int actulappliedDaysCount = 0;
		int leaveAsAbsentDaysCount = 0;
		List<LocalDate> dateList = DateUtils.getDatesBetweenUsingForFormate(fromDate, toDate);
//		boolean sandwhitchFlag=false;
//		if(sandwhitchFlag)
		actulappliedDaysCount = getAlternatePattenWeeklyOff(leaveValidationResult, patternDayList, dateList,
				actulappliedDaysCount);

		for (int i = 0; i <= dateList.size() - 1; i++) {

			int index = 0;
			if (leaveValidationResult.getWeeklydayset().contains(
					new SimpleDateFormat("E").format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase())) {

//				List<Long> weekmap =	positionDayMap.get(new SimpleDateFormat("E").format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase());

				if (index == 0)
					for (TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO : patternDayList) {
						logger.info("applay Days is " + tmsWeekOffChildPatternDTO);
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
								logger.info(fromDate + "==weeklyOff===");
								// commented both if code so that count of applied date must be increase
								leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).toString());
								actulappliedDaysCount++;
								// commented for leave rule LS
								if (weekendSandwizeFlag) {

									// leaveValidationResult.getSandwichDates()
									// .add(DateUtils.getDateFromLocalDate(dateList.get(i)));
									// sandwhichDaysCount++;
									index++;
//									sandwhitchFlag=true;
								}
								if (weekOffBetweenTwoAbsent) {
									// leaveValidationResult.getLeaveAsAbsentDates()
									// .add(DateUtils.getDateFromLocalDate(dateList.get(i)));
									// leaveAsAbsentDaysCount++;
									index++;
								}
							}

						}

					}

			} else if (DateCheckInTreeSet(leaveValidationResult.getAllHolidayset(),
					DateUtils.getDateFromLocalDate(dateList.get(i)))) {
				// else
				// if(leaveValidationResult.getAllHolidayset().contains(DateUtils.getDateFromLocalDate(dateList.get(i))))
				// {

				if (holidayBetweenTwoLeave) {
					sandwhichDaysCount++;
					leaveValidationResult.getSandwichDates().add(DateUtils.getDateFromLocalDate(dateList.get(i)));
				}
				if (holidayAsAbsentFlag) {
					leaveAsAbsentDaysCount++;
					leaveValidationResult.getLeaveAsAbsentDates().add(DateUtils.getDateFromLocalDate(dateList.get(i)));
				}

			}

			else {
				leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).toString());
				actulappliedDaysCount++;
			}

		}

		Iterator<Date> itDates = leaveValidationResult.getSandwichDates().iterator();

		while (itDates.hasNext()) {
			appliedSandwhichDates.add(itDates.next());
		}

		if (leaveValidationResult.getSandwichDates().size() > 0) {
			Iterator<Date> sandwitchDate = leaveValidationResult.getSandwichDates().iterator();

			while (sandwitchDate.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = sandwitchDate.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				// sirObj.setLeavefromtoDate(fromDate);
				sirObj.setLeaveNature(StatusMessage.LeaveSandwitch);
				leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj);
			}

		}
		if (leaveValidationResult.getLeaveAsAbsentDates().size() > 0) {
			Iterator<Date> absentDate = leaveValidationResult.getLeaveAsAbsentDates().iterator();

			while (absentDate.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = absentDate.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				// sirObj.setLeavefromtoDate(fromDate);
				sirObj.setLeaveNature(StatusMessage.SandwitchLOP);
				leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj);
			}

		}
		leaveValidationResult.setActulappliedDaysCount(new BigDecimal(actulappliedDaysCount));
		leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
		leaveValidationResult.setSandwhichDaysCount(
				leaveValidationResult.getSandwhichDaysCount().add(new BigDecimal(sandwhichDaysCount)));
		leaveValidationResult.setLeaveAsAbsentDaysCount(
				leaveValidationResult.getLeaveAsAbsentDaysCount().add(new BigDecimal(leaveAsAbsentDaysCount)));

		return new BigDecimal(actulappliedDaysCount);

	}

	// old code
	/*
	 * public BigDecimal sandwichOfPatternCount(Date fromDate, Date toDate,
	 * LeaveValidationResult leaveValidationResult, boolean weekendSandwizeFlag,
	 * boolean holidayAsAbsentFlag, boolean holidayBetweenTwoLeave, boolean
	 * weekOffBetweenTwoAbsent) {
	 * 
	 * List<Date> appliedSandwhichDates = new ArrayList<Date>();
	 * 
	 * int sandwhichDaysCount = 0; int actulappliedDaysCount = 0; int
	 * leaveAsAbsentDaysCount = 0; List<LocalDate> dateList =
	 * DateUtils.getDatesBetweenUsingForFormate(fromDate, toDate);
	 * 
	 * for (int i = 0; i <= dateList.size() - 1; i++) {
	 * 
	 * if (leaveValidationResult.getWeeklydayset().contains( new
	 * SimpleDateFormat("E").format(DateUtils.getDateFromLocalDate(dateList.get(i)))
	 * .toUpperCase())) { //
	 * if(leaveValidationResult.getWeeklyOffSet().contains(DateUtils.
	 * getDateFromLocalDate(dateList.get(i)))) // { logger.info(fromDate +
	 * "==weeklyOff===");
	 * 
	 * if (weekendSandwizeFlag) {
	 * leaveValidationResult.getSandwichDates().add(DateUtils.getDateFromLocalDate(
	 * dateList.get(i))); sandwhichDaysCount++; }
	 * 
	 * if (weekOffBetweenTwoAbsent) {
	 * leaveValidationResult.getLeaveAsAbsentDates().add(DateUtils.
	 * getDateFromLocalDate(dateList.get(i))); leaveAsAbsentDaysCount++; }
	 * 
	 * } else if (DateCheckInTreeSet(leaveValidationResult.getAllHolidayset(),
	 * DateUtils.getDateFromLocalDate(dateList.get(i)))) { // else //
	 * if(leaveValidationResult.getAllHolidayset().contains(DateUtils.
	 * getDateFromLocalDate(dateList.get(i)))) // {
	 * 
	 * if (holidayBetweenTwoLeave) { sandwhichDaysCount++;
	 * leaveValidationResult.getSandwichDates().add(DateUtils.getDateFromLocalDate(
	 * dateList.get(i))); } if (holidayAsAbsentFlag) { leaveAsAbsentDaysCount++;
	 * leaveValidationResult.getLeaveAsAbsentDates().add(DateUtils.
	 * getDateFromLocalDate(dateList.get(i))); }
	 * 
	 * }
	 * 
	 * else {
	 * 
	 * leaveValidationResult.getAppliedLeaveEnteriesDates().add(dateList.get(i).
	 * toString()); actulappliedDaysCount++; }
	 * 
	 * }
	 * 
	 * Iterator<Date> itDates = leaveValidationResult.getSandwichDates().iterator();
	 * 
	 * while (itDates.hasNext()) { appliedSandwhichDates.add(itDates.next()); }
	 * 
	 * if (leaveValidationResult.getSandwichDates().size() > 0) { Iterator<Date>
	 * sandwitchDate = leaveValidationResult.getSandwichDates().iterator();
	 * 
	 * while (sandwitchDate.hasNext()) { SandwitchIssueResolver sirObj = new
	 * SandwitchIssueResolver(); Date date = sandwitchDate.next();
	 * appliedSandwhichDates.add(date); sirObj.setSandwitchDate(date); //
	 * sirObj.setLeavefromtoDate(fromDate);
	 * sirObj.setLeaveNature(StatusMessage.LeaveSandwitch);
	 * leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj); }
	 * 
	 * } if (leaveValidationResult.getLeaveAsAbsentDates().size() > 0) {
	 * Iterator<Date> absentDate =
	 * leaveValidationResult.getLeaveAsAbsentDates().iterator();
	 * 
	 * while (absentDate.hasNext()) { SandwitchIssueResolver sirObj = new
	 * SandwitchIssueResolver(); Date date = absentDate.next();
	 * appliedSandwhichDates.add(date); sirObj.setSandwitchDate(date); //
	 * sirObj.setLeavefromtoDate(fromDate);
	 * sirObj.setLeaveNature(StatusMessage.SandwitchLOP);
	 * leaveValidationResult.getSandwichDatesFromSetObj().add(sirObj); }
	 * 
	 * } leaveValidationResult.setActulappliedDaysCount(new
	 * BigDecimal(actulappliedDaysCount));
	 * leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
	 * leaveValidationResult.setSandwhichDaysCount(
	 * leaveValidationResult.getSandwhichDaysCount().add(new
	 * BigDecimal(sandwhichDaysCount)));
	 * leaveValidationResult.setLeaveAsAbsentDaysCount(
	 * leaveValidationResult.getLeaveAsAbsentDaysCount().add(new
	 * BigDecimal(leaveAsAbsentDaysCount)));
	 * 
	 * return new BigDecimal(actulappliedDaysCount);
	 * 
	 * }
	 */

	public BigDecimal sandwichOfPatternCountForToDate(Date toDate, LeaveValidationResult leaveValidationResult,
			boolean weekendSandwizeFlag, boolean holidaySandwizeFlag, boolean weekOffBetweenTwoAbsent,
			boolean holidayBetweenTwoAbsent, List<TMSWeekOffChildPatternDTO> patternDayList) {

		List<Date> appliedSandwhichDates = new ArrayList<Date>();
		// SandwitchIssueResolver sirObj= new SandwitchIssueResolver();
		boolean sandwizappliedeflag = false;
		int sandwhichDaysCount = 0;
		boolean leaveAsAbsentappliedeflag = false;

		int leaveAsAbsentDaysCount = 0;
		toDate = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

		boolean conditinCheckflag = true;
		while (conditinCheckflag) {
			// i
			conditinCheckflag = false;

			if (leaveValidationResult.getWeeklydayset()
					.contains(new SimpleDateFormat("E").format(toDate).toUpperCase())) {
				logger.info(toDate + "==weeklyOff===");

				for (TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO : patternDayList) {
					logger.info("applay Days is " + tmsWeekOffChildPatternDTO);
					if (tmsWeekOffChildPatternDTO.getDayName()
							.equalsIgnoreCase(new SimpleDateFormat("E").format(toDate).toUpperCase())) {

						Instant instant = Instant.ofEpochMilli(toDate.getTime());
						LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
						LocalDate toLocalDate = localDateTime.toLocalDate();

						Date weeklyOffDate = DateUtils.findWeeklyOffDate(
								DayEnum.valueOf(tmsWeekOffChildPatternDTO.getDayName()).getDayId(),
								Integer.valueOf(tmsWeekOffChildPatternDTO.getPositionOfDay().toString()),
								toLocalDate.getMonthValue() - 1, toLocalDate.getYear());

						Instant instantWeek = Instant.ofEpochMilli(weeklyOffDate.getTime());
						LocalDateTime localDateTimeWeek = LocalDateTime.ofInstant(instantWeek, ZoneId.systemDefault());
						LocalDate weeklyOffLocalDate = localDateTimeWeek.toLocalDate();

						if (weeklyOffLocalDate.compareTo(toLocalDate) == 0) {
							if (weekendSandwizeFlag) {
								conditinCheckflag = true;
								leaveValidationResult.getSandwichDatesToSet().add(toDate);
								sandwhichDaysCount++;
							}

							if (weekOffBetweenTwoAbsent) {
								conditinCheckflag = true;
								leaveValidationResult.getLeaveAsAbsentDates().add(toDate);
								leaveAsAbsentDaysCount++;
							}

						}

					}
				}

//				if (weekendSandwizeFlag) {
//					conditinCheckflag = true;
//					leaveValidationResult.getSandwichDatesToSet().add(toDate);
//					sandwhichDaysCount++;
//				}
//
//				if (weekOffBetweenTwoAbsent) {
//					conditinCheckflag = true;
//					leaveValidationResult.getLeaveAsAbsentDates().add(toDate);
//					leaveAsAbsentDaysCount++;
//				}
			} else if (DateCheckInTreeSet(leaveValidationResult.getAllHolidayset(), toDate)) {

				if (holidaySandwizeFlag) {
					conditinCheckflag = true;
					sandwhichDaysCount++;
					leaveValidationResult.getSandwichDatesToSet().add(toDate);
				}
				if (holidayBetweenTwoAbsent) {
					conditinCheckflag = true;
					leaveValidationResult.getLeaveAsAbsentDates().add(toDate);
					leaveAsAbsentDaysCount++;
				}
				logger.info(toDate + "==holiday===");

			}

			else if (DateCheckInTreeSet(leaveValidationResult.getLeaveEnterieSet(), toDate)) {

				if (sandwhichDaysCount > 0) {
					sandwizappliedeflag = true;

					logger.info(toDate + "==leaveEnteries===");
				}
				if (leaveAsAbsentDaysCount > 0) {
					leaveAsAbsentappliedeflag = true;
					break;
				} else
					break;
			}

			logger.info("next  : " + toDate);
			toDate = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(toDate);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			toDate = calendar.getTime();
			logger.info("next :" + toDate);

		}

		if (sandwizappliedeflag) {

			Iterator<Date> itDates = leaveValidationResult.getSandwichDatesToSet().iterator();

			while (itDates.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = itDates.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				sirObj.setLeavefromtoDate(toDate);
				sirObj.setLeaveNature(StatusMessage.LeaveSandwitch);
				leaveValidationResult.getSandwichDatesToSetObj().add(sirObj);
			}
			leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
			leaveValidationResult.setSandwhichDaysCount(
					leaveValidationResult.getSandwhichDaysCount().add(new BigDecimal(sandwhichDaysCount)));

		} /*
			 * else { leaveValidationResult.getSandwichDatesToSet().clear();
			 * leaveValidationResult.getSandwichDatesToSetObj().clear(); }
			 */
		if (leaveAsAbsentappliedeflag) {

			Iterator<Date> absentDate = leaveValidationResult.getLeaveAsAbsentDates().iterator();

			while (absentDate.hasNext()) {
				SandwitchIssueResolver sirObj = new SandwitchIssueResolver();
				Date date = absentDate.next();
				appliedSandwhichDates.add(date);
				sirObj.setSandwitchDate(date);
				sirObj.setLeavefromtoDate(toDate);
				sirObj.setLeaveNature(StatusMessage.SandwitchLOP);
				leaveValidationResult.getSandwichDatesToSetObj().add(sirObj);
			}
			leaveValidationResult.setAppliedSandwhichDates(appliedSandwhichDates);
			leaveValidationResult.setLeaveAsAbsentDaysCount(
					leaveValidationResult.getLeaveAsAbsentDaysCount().add(new BigDecimal(leaveAsAbsentDaysCount)));
		} /*
			 * else { leaveValidationResult.getSandwichDatesFromSet().clear();
			 * leaveValidationResult.getSandwichDatesFromSetObj().clear(); }
			 */

		if (!leaveAsAbsentappliedeflag && !sandwizappliedeflag) {
			if (leaveValidationResult.getSandwichDatesToSet().size() > 0)
				leaveValidationResult.getSandwichDatesToSet().clear();
			if (leaveValidationResult.getSandwichDatesToSetObj().size() > 0)
				leaveValidationResult.getSandwichDatesToSetObj().clear();
			if (leaveValidationResult.getLeaveAsAbsentDates().size() > 0)
				leaveValidationResult.getLeaveAsAbsentDates().clear();

		}

		return new BigDecimal(sandwhichDaysCount);

	}

	// new weeklyoffpattern implemented by singya bhalse
	public void weeklyOfPetternCount(Date fromDate, Date toDate, List<TMSWeekOffChildPatternDTO> patternDayList,
			LeaveValidationResult leaveValidationResult, TreeSet<Date> weeklyOffSet) {
		int matchedCount = 0;
		List<Date> applyHolidays = new ArrayList<Date>();

		List<LocalDate> dateList = DateUtils.getDatesBetweenUsing(fromDate, toDate);

		for (int i = 0; i <= dateList.size() - 1; i++) {

			for (TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO : patternDayList) {
				logger.info("applay Days is " + tmsWeekOffChildPatternDTO);
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
						matchedCount++;
						applyHolidays.add(DateUtils.getDateFromLocalDate(dateList.get(i)));
						weeklyOffSet.add(DateUtils.getDateFromLocalDate(dateList.get(i)));
					}

				}
			}

		}

		leaveValidationResult.setApplyHolidays(applyHolidays);
		leaveValidationResult.setWeeklyOffSet(weeklyOffSet);
		leaveValidationResult.setWeekllyOffCount(new BigDecimal(matchedCount));
	}

//old code
//	public void weeklyOfPetternCount(Date fromDate, Date toDate, List<String> patternDayList,
//			LeaveValidationResult leaveValidationResult, TreeSet<Date> weeklyOffSet) {
//		int matchedCount = 0;
//		List<Date> applyHolidays = new ArrayList<Date>();
//
//		List<LocalDate> dateList = DateUtils.getDatesBetweenUsing(fromDate, toDate);
//
//		for (int i = 0; i <= dateList.size() - 1; i++) {
//
//			for (String patternDay : patternDayList) {
//				logger.info("applay Days is " + patternDay);
//				if (patternDay.equalsIgnoreCase(new SimpleDateFormat("E")
//						.format(DateUtils.getDateFromLocalDate(dateList.get(i))).toUpperCase())) {
//					matchedCount++;
//					applyHolidays.add(DateUtils.getDateFromLocalDate(dateList.get(i)));
//					weeklyOffSet.add(DateUtils.getDateFromLocalDate(dateList.get(i)));
//				}
//			}
//
//		}
//
//		leaveValidationResult.setApplyHolidays(applyHolidays);
//		leaveValidationResult.setWeeklyOffSet(weeklyOffSet);
//		leaveValidationResult.setWeekllyOffCount(new BigDecimal(matchedCount));
//	}

	public void holidayOfPetternCount(Date fromDate, Date toDate, List<TMSHolidays> holidayList,
			LeaveValidationResult leaveValidationResult, TreeSet<Date> holidayset) {

		List<LocalDate> dateList = DateUtils.getDatesBetweenUsing(fromDate, toDate);
		// int holidayMatchedCount = 0;
		int holidayWeeklyMatchedCount = 0;

		for (int i = 0; i <= dateList.size() - 1; i++) {
			// Do your job here with `date`.
			logger.info("testing holiday for " + DateUtils.getDateFromLocalDate(dateList.get(i)));

			for (TMSHolidays holiday : holidayList) {

				logger.info("testing holiday form " + holiday.getFromDate() + " -----" + holiday.getToDate());
				List<LocalDate> holidaydataList = DateUtils.getDatesBetweenUsing(holiday.getFromDate(),
						holiday.getToDate());
				for (int j = 0; j <= holidaydataList.size() - 1; j++) {
					leaveValidationResult.getAllHolidayset()
							.add(DateUtils.getDateFromLocalDate(holidaydataList.get(j)));
					if (DateUtils.getDateFromLocalDate(dateList.get(i))
							.equals(DateUtils.getDateFromLocalDate(holidaydataList.get(j)))) {
						holidayMatchedCount++;
						holidayset.add(DateUtils.getDateFromLocalDate(dateList.get(i)));
						logger.info("holiday matched date is " + DateUtils.getDateFromLocalDate(dateList.get(i)));

					}

				}

			}

		}
		leaveValidationResult.setHolidaysOffCount(new BigDecimal(holidayMatchedCount));
		// leaveValidationResult.setWeekholidaysOffCount(new
		// BigDecimal(holidayWeeklyMatchedCount));
		leaveValidationResult.setHolidayset(holidayset);
		logger.info("holidayMatchedCount is :" + holidayMatchedCount + " holidayWeeklyMatchedCount : "
				+ holidayWeeklyMatchedCount);

	}

	// new weeklyOffPattern
	public BigDecimal actualLeaveAppliedDaysCount(LeaveValidationResult leaveValidationResult, Date fromDate,
			Date toDate, boolean weekOffBetweenTwoLeave, boolean holidayBetweenTwoAbsent,
			boolean holidayBetweenTwoLeave, boolean weekOffBetweenTwoAbsent,
			List<TMSWeekOffChildPatternDTO> patternDayList) {
		BigDecimal actualLeaveApplied = new BigDecimal(0);
		// if(weekOffBetweenTwoAbsent||holidayBetweenTwoAbsent) {
		actualLeaveApplied = sandwichOfPatternCount(fromDate, toDate, leaveValidationResult, weekOffBetweenTwoLeave,
				holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent, patternDayList);

		return actualLeaveApplied;
	}

	// old code
//	public BigDecimal actualLeaveAppliedDaysCount(LeaveValidationResult leaveValidationResult, Date fromDate,
//			Date toDate, boolean weekOffBetweenTwoLeave, boolean holidayBetweenTwoAbsent,
//			boolean holidayBetweenTwoLeave, boolean weekOffBetweenTwoAbsent) {
//		BigDecimal actualLeaveApplied = new BigDecimal(0);
//		// if(weekOffBetweenTwoAbsent||holidayBetweenTwoAbsent) {
//		actualLeaveApplied = sandwichOfPatternCount(fromDate, toDate, leaveValidationResult, weekOffBetweenTwoLeave,
//				holidayBetweenTwoAbsent, holidayBetweenTwoLeave, weekOffBetweenTwoAbsent);
//
//		return actualLeaveApplied;
//	}

	public BigDecimal checkNumberOfMonths(Date start, Date end) {
		Calendar start1 = Calendar.getInstance();
		start1.setTime(start);
		Calendar end1 = Calendar.getInstance();
		end1.setTime(end);

		return new BigDecimal(((end1.get(Calendar.YEAR) - start1.get(Calendar.YEAR)) * 12) + 1
				+ (end1.get(Calendar.MONTH) - start1.get(Calendar.MONTH)));

	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();

		long result = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return result;
	}

	public Date getFormatedDate(Date date) {

		String dateObj = df.format(date);
		Date formatedDate = null;
		try {
			formatedDate = df.parse(dateObj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatedDate;

	}

	@Override
	public List<Object[]> getMonthEmployeeLeaveEntry(long companyId, long employeeId, String fromDate, String toDate) {

		String query = "SELECT * FROM TMSLeaveEntries WHERE companyId=?1 and employeeId=?2 and fromDate >=?3 and toDate <=?4 ORDER BY fromDate ASC";
		Query nativeQuery = eManager.createNativeQuery(query);
		nativeQuery.setParameter(1, companyId).setParameter(2, employeeId).setParameter(3, fromDate).setParameter(4,
				toDate);
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("resultList size------>" + resultList.size());
		return resultList;
	}

	@Override
	public List<TMSLeaveEntry> getEmployeeApprovedLeaveEntry(Long employeeId) {
		return leaveEntryRepository.getEmployeeApprovedLeaveEntry(employeeId);
	}

	@Override
	public List<TMSLeaveEntry> getEmployeeLeaveEntryListByDate(Date date) {
		return leaveEntryRepository.getEmployeeLeaveEntryListByDate(date);
	}

	@Override
	public LeaveEntryDTO leaveCount(Long companyId, Long employeeId) {
		int leaveCount = leaveEntryRepository.leaveCount(companyId, employeeId);
		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
		leaveEntryDto.setLeaveCount(leaveCount);
		return leaveEntryDto;
	}

	@Override
	public List<TMSLeaveEntriesDatewise> getEmployeePendingLeaveEntryDateWise(Long employeeId) {
		// TODO Auto-generated method stub
		return employeeLeaveDatewiseService.getEmployeePendingLeaveEntryDateWise(employeeId);
		// return leaveEntryRepository.getEmployeePendingLeaveEntry(employeeId);
	}

	@Override
	public List<TMSLeaveEntry> getEmployeePendingLeaveEntry(Long employeeId) {
		// TODO Auto-generated method stub

		return leaveEntryRepository.getEmployeePendingLeaveEntry(employeeId);
	}

	@Override
	public List<TMSLeaveEntry> getAllEmployeeApprovedLeaveEntry(Long companyId) {
		// TODO Auto-generated method stub
		return leaveEntryRepository.getAllEmployeeApprovedLeaveEntry(companyId);
	}

	@Override
	public List<LeaveBalanceSummryDTO> getEmployeeLeaveBalanceSummry(Long employeeId, Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> leaveBalanceSummryObjectList = leaveEntryRepository.getEmployeeLeaveBalanceSummry(employeeId,
				companyId);
		List<LeaveBalanceSummryDTO> leaveBalanceDtoList = leaveEntryAdaptor
				.leaveBalanceSummryObjToUiDto(leaveBalanceSummryObjectList);

		logger.info("===leave balance summary list====" + leaveBalanceDtoList.size());

		return leaveBalanceDtoList;
	}

	@Override
	public List<TeamLeaveOnCalenderDTO> getTeamLeaveOnCalender(String employeeId, String currentDate) {
		// TODO Auto-generated method stub
		List<Object[]> teamLeaveOnCalenderObjectList = leaveEntryRepository.getTeamLeaveOnCalender(employeeId,
				currentDate);
		List<TeamLeaveOnCalenderDTO> leaveBalanceDtoList = leaveEntryAdaptor
				.teamLeaveOnCalenderObjToUiDto(teamLeaveOnCalenderObjectList);
		logger.info("===size of list====" + leaveBalanceDtoList.size());

		return leaveBalanceDtoList;
	}

	@Override
	public void actonOnPendingLeaveAttendace(List<TeamLeaveOnCalenderDTO> actonLeaveAttendace) {
		for (TeamLeaveOnCalenderDTO teamLeaveOnCalenderDTO : actonLeaveAttendace) {
			leaveEntryRepository.actonOnPendingLeaveAttendace(teamLeaveOnCalenderDTO.getStatus(),
					Long.parseLong(teamLeaveOnCalenderDTO.getLeaveId()));
		}

	}

	@Override
	public LeaveEntryDTO getMyTeamLeaveCount(Long companyId, Long employeeId) {
		int leaveCount = leaveEntryRepository.countMyTeamPandingReq(companyId, employeeId);
		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
		leaveEntryDto.setLeaveCountMyTeam(leaveCount);
		return leaveEntryDto;

	}

	@Override
	public LeaveEntryDTO getAllTimeMyTeamLeaveCount(Long companyId, Long employeeId) {
		int leaveCount = leaveEntryRepository.countAllTimeMyTeamPandingReq(companyId, employeeId);
		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
		leaveEntryDto.setLeaveCountMyTeam(leaveCount);
		return leaveEntryDto;

	}

	public List<Object[]> getLeaveApprovalsPending(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return leaveEntryRepository.getLeaveApprovalsPending(companyId, employeeId);
	}

	@Override
	public List<Object[]> getLeaveApprovalsNonPending(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return leaveEntryRepository.getLeaveApprovalsNonPending(companyId, employeeId);

	}

	@Override
	public void updateById(TMSLeaveEntry tMSLeaveEntry) {
		leaveEntryRepository.leaveStatusUpdate(tMSLeaveEntry.getLeaveId(), tMSLeaveEntry.getStatus(),
				tMSLeaveEntry.getApprovalId(), tMSLeaveEntry.getActionableDate());
	}

	@Override
	public LeaveEntryDTO isPendingRequestLeaveAndARByMonth(int month, int year) {
		// TODO Auto-generated method stub

		List<Object[]> pendingRequestLeaveAndARObjList = leaveEntryRepository.isPendingRequestLeaveAndARByMonth(month,
				year);

		return leaveEntryAdaptor.pendingRequestLeaveAndARObjtoUiDto(pendingRequestLeaveAndARObjList);
	}

	@Override
	public Long getapprovependingByEmpId(Long companyId, Long employeeId) {
		int obj = leaveEntryRepository.leaveCountAprPen(companyId, employeeId);
//		BigInteger count = obj[0] != null ? (new BigInteger(obj[0].toString())) : null;
		System.out.println("++++++++++++++++count+++++++++++++++++++" + obj);

		return (long) obj;
	}

	@Override
	public Long getapprovepending(Long companyId, Long leavePeriodId) {
		int obj = leaveEntryRepository.leaveCountApr(companyId, leavePeriodId);
//		BigInteger count = obj[0] != null ? (new BigInteger(obj[0].toString())) : null;
		System.out.println("++++++++++++++++count+++++++++++++++++++" + obj);

		return (long) obj;
	}

	@Override
	public TeamLeaveOnCalenderDTO getTeamLeaveOnCalenderNew(Long employeeId, String processMonth) {
		Date currentDate = new Date();
		int currentMonth = currentDate.getMonth();
		Date startDate = DateUtils.getDateForProcessMonth(processMonth);
		Long leaveDays = new Long(0);
		String attendanceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int month = DateUtils.getMonthForProcessMonth(processMonth);
		Date endDate = new Date();
		String empId = String.valueOf(employeeId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		AttendanceValidationResult attendanceValidationResult = new AttendanceValidationResult();
		TeamLeaveOnCalenderDTO attendance = new TeamLeaveOnCalenderDTO();
		List<Object[]> teamLeaveOnCalenderObjectList = leaveEntryRepository.getTeamLeaveOnCalender(empId,
				attendanceDate);
		List<TeamLeaveOnCalenderDTO> teamOnLeaveDtoList = leaveEntryAdaptor
				.teamLeaveOnCalenderObjToUiDto(teamLeaveOnCalenderObjectList);
		logger.info("=------=======-------========-----teamOnLeaveDtoList----=========--------"
				+ teamOnLeaveDtoList.toString());
		List<TMSLeaveEntriesDatewise> tmsLeaveEntryDateWiseList = leaveEntriesDatewiseService
				.getEmployeeLeaveEntryNew(employeeId, processMonth);
		logger.info("tmsLeaveEntryDateWiseList-================--------" + tmsLeaveEntryDateWiseList.toString());
		int days = 0;
		if (month == (currentMonth + 1)) {
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

		Collections.sort(teamOnLeaveDtoList, (a1, a2) -> a1.getFromDate().compareTo(a2.getToDate()));
		Collections.sort(tmsLeaveEntryDateWiseList, (a1, a2) -> a1.getLeaveDate().compareTo(a2.getLeaveDate()));
		Collection<DateWiseAttendanceLogDTO> dateWiseAttendanceLog = attendanceValidationResult.getAttendanceMap()
				.values();
		List<DateWiseAttendanceLogDTO> list = new ArrayList<>(dateWiseAttendanceLog);
		Collections.sort(list, (a1, a2) -> a1.getStart().compareTo(a2.getStart()));

		tmsLeaveEntryDateWiseList.forEach(leaves -> {
			attendanceValidationResult.getLeaveEnteriesMap().put(leaves.getLeaveDate(), leaves.getLeaveNature());

		});
		int count = 0;
		List<DateWiseAttendanceLogDTO> listNew = new ArrayList<DateWiseAttendanceLogDTO>();
		for (DateWiseAttendanceLogDTO dateWiseAttendanceLogDto : list) {

			String startDateNew = new SimpleDateFormat("yyyy-MM-dd").format(dateWiseAttendanceLogDto.getStart());
			DateWiseAttendanceLogDTO attendanceLogDTO = new DateWiseAttendanceLogDTO();
			for (TMSLeaveEntriesDatewise dateWise : tmsLeaveEntryDateWiseList) {

				Date leaveDate = dateWise.getLeaveDate();
				String leaveDateNew = new SimpleDateFormat("yyyy-MM-dd").format(leaveDate);

				if (startDateNew.equals(leaveDateNew)) {
					String title = dateWise.getLeaveStatus();
					attendanceLogDTO.setTitle(title);

					attendanceLogDTO.setMode(dateWiseAttendanceLogDto.getMode());
					attendanceLogDTO.setStart(dateWiseAttendanceLogDto.getStart());
					attendanceLogDTO.setTitleValue(dateWiseAttendanceLogDto.getTitleValue());
					System.out.println("EmployeeLeaveServiceImpl.================------------->>>"
							+ attendanceLogDTO.getTitle().toString());
				} else {
					attendanceLogDTO.setTitle("");
					attendanceLogDTO.setMode(dateWiseAttendanceLogDto.getMode());
					attendanceLogDTO.setStart(dateWiseAttendanceLogDto.getStart());
					attendanceLogDTO.setTitleValue(dateWiseAttendanceLogDto.getTitleValue());
				}

			}

			listNew.add(attendanceLogDTO);
		}
		// List<DateWiseAttendanceLogDTO> cc2 =
		// listNew.stream().filter(item->item.getLeaveDate()).distinct().sequential()collect(Collectors.toList());
		// List<DateWiseAttendanceLogDTO> distinctElements = list.stream() .filter(
		// distinctByKey(p -> p.getLeaveDate()) ) .collect( Collectors.toList() );
		attendance.setEvents(listNew);

		return attendance;
	}

	@Override
	public LeaveEntryDTO allEmployeeLeaveCount(Long companyId) {
		int leaveCount = leaveEntryRepository.allEmployeeLeaveCount(companyId);
		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
		leaveEntryDto.setLeaveCountMyTeam(leaveCount);
		return leaveEntryDto;
	}

	public List<Object[]> getAllLeaveApprovalsPending(Long companyId, SearchDTO searcDto) {

		return leaveRequestRepository.getAllLeaveApprovalsPending(companyId, searcDto);
	}

	@Override
	public List<Object[]> getAllLeaveApprovalsNonPending(Long companyId, SearchDTO searcDto) {

		return leaveRequestRepository.getAllLeaveApprovalsNonPending(companyId, searcDto);

	}

	public List<LeaveEntryDTO> getEmployeesAllTypeLeaveEntry(Long companyId, Long employeeId) {
		List<TMSLeaveEntry> leaveEntryList = leaveEntryRepository.getEmployeesAllTypeLeaveEntry(companyId, employeeId);
		List<LeaveEntryDTO> leaveEntryDTOList = new ArrayList<>();
		for (TMSLeaveEntry tle : leaveEntryList) {
			Employee approvalEmp = null;
			Employee employeeEmp = null;

			TMSLeaveEntry tmsLeaveEntry = leaveEntryRepository.findOne(tle.getLeaveId());
			if (tmsLeaveEntry.getEmployeeId() != null) {
				employeeEmp = employeePersonalInformationService.getEmployeeInfo(employeeId);
			}
			if (tmsLeaveEntry.getApprovalId() != null) {
				Long approvalId = tmsLeaveEntry.getApprovalId();
				approvalEmp = employeePersonalInformationService.getEmployeeInfo(approvalId);
			}

			String query = "SELECT emp.employeeId,emp.firstName,emp.lastName,emp.employeeCode,emp.employeeLogoPath,emp.officialEmail ,dept.departmentId, dept.departmentName, desg.designationId,desg.designationName FROM Employee emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg ON desg.designationId= emp.designationId WHERE  emp.companyId=?1  AND emp.employeeId IN ("
					+ tmsLeaveEntry.getNotifyEmployee() + ") AND emp.activeStatus='AC' ";

			Query nativeQuery = eManager.createNativeQuery(query);
			nativeQuery.setParameter(1, tmsLeaveEntry.getCompanyId());
			final List<Object[]> resultList = nativeQuery.getResultList();
			List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
					.databaseObjModelToUiDtoListNotifyEmployee(resultList);
			LeaveEntryDTO leaveEntryDto = leaveEntryAdaptor.databaseModelToUiDto(tmsLeaveEntry, employeeEmp,
					approvalEmp, employeeDtoList);
			leaveEntryDTOList.add(leaveEntryDto);
		}
		return leaveEntryDTOList;
	}

	@Override
	public void getPendingLeaveReqCount(Long longCompanyId, EntityCountDTO searchDto) {

		searchDto.setCount(leaveEntryRepository.getPendingLeaveReqCount(longCompanyId));
	}

	@Override
	public void getNonPendingLeaveReqCount(Long longCompanyId, EntityCountDTO searchDto) {

		searchDto.setCount(leaveEntryRepository.getNonPendingLeaveReqCount(longCompanyId));
	}

	@Override
	public List<TMSLeaveEntry> getPendingLeaveReqbyPagination(Long employeeId, SearchDTO searcDto) {
		// TODO Auto-generated method stub

		int offset = searcDto.getOffset();
		int limit = searcDto.getLimit();
		int page = (offset + limit) / limit;
        
		logger.info("myOffset.....>" + offset);
		logger.info("limit.....>" + limit);
		logger.info("page.....>" + page);
		logger.info("dataStatus.....>" + searcDto.getDataStatus());
		
		List<TMSLeaveEntry> pendingLeaveReqbyPagination=new ArrayList<TMSLeaveEntry>();
		
		if(searcDto.getDataStatus().equalsIgnoreCase("pen")) {
			/*logger.info("SELECT * FROM TMSLeaveEntries  WHERE employeeId=" + employeeId
					+ " AND status='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) limit " + limit + " offset"
					+ offset);*/
			pendingLeaveReqbyPagination= leaveEntryRepository
					.getPendingLeaveReqbyPagination(employeeId, limit, offset);
		}else {
			/*logger.info("SELECT * FROM TMSLeaveEntries  WHERE employeeId=" + employeeId
					+ " AND status!='PEN' and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) limit " + limit + " offset"
					+ offset);*/
			pendingLeaveReqbyPagination= leaveEntryRepository
			.getNonPendingLeaveReqbyPagination(employeeId, limit, offset);
		}
		
		return pendingLeaveReqbyPagination;
	}

}
