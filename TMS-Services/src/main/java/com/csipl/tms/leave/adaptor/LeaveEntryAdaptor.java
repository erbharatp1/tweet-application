package com.csipl.tms.leave.adaptor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.tms.dto.daysattendancelog.DateWiseAttendanceLogDTO;
import com.csipl.tms.dto.leave.LeaveBalanceDTO;
import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.dto.weekofpattern.TMSWeekOffChildPatternDTO;
import com.csipl.tms.model.leave.EmployeeLeaveHistory;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.LeaveNotifyEmployee;
import com.csipl.tms.model.leave.LeaveSearchDTO;
import com.csipl.tms.model.leave.TMSLeaveEntry;
import com.csipl.tms.model.leave.TMSLeavePeriod;
import com.csipl.tms.model.leave.TMSLeaveType;
import com.csipl.tms.model.leave.TMSLeaveTypeMaster;

public class LeaveEntryAdaptor {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<TMSLeaveEntry> uiDtoToDatabaseModelList(List<LeaveEntryDTO> leaveEntryDtoList) {
		List<TMSLeaveEntry> leaveEntryList = new ArrayList<TMSLeaveEntry>();
		for (LeaveEntryDTO leaveEntryDto : leaveEntryDtoList) {
			// leaveEntryList.add(uiDtoToDatabaseModel(leaveEntryDto));
		}
		return leaveEntryList;
	}

	public TMSLeaveEntry uiDtoToDatabaseModel(LeaveBalanceSummryDTO leaveBalanceSummryDto) {
		TMSLeaveEntry leaveEntry = new TMSLeaveEntry();
		TMSLeaveType leaveType = new TMSLeaveType();
		List<LeaveNotifyEmployee> leaveNotifyEmployees = new ArrayList<LeaveNotifyEmployee>();
		leaveEntry.setApprovalRemark(leaveBalanceSummryDto.getApprovalRemark());
		leaveEntry.setDays(leaveBalanceSummryDto.getDays());

		leaveEntry.setEmployeeRemark(leaveBalanceSummryDto.getEmployeeRemark());
		leaveEntry.setFromDate(leaveBalanceSummryDto.getFromDate());
		leaveEntry.setHalfFullDay(leaveBalanceSummryDto.getHalf_fullDay());
		leaveEntry.setHalfDayFor(leaveBalanceSummryDto.getHalfDayFor());
		// leaveEntry.setIsApproved(leaveBalanceSummryDto.getIsApproved());
		// leaveEntry.setIsApproved();
		leaveEntry.setStatus(leaveBalanceSummryDto.getStatus());
		leaveEntry.setToDate(leaveBalanceSummryDto.getToDate());
		// leaveEntry.setIsRead(leaveBalanceSummryDto.getIsRead());
		// byte isRead = (byte) (leaveEntryDto.getIsRead() ? 1 : 0);
		// leaveEntry.setIsRead(isRead);
		leaveEntry.setCancleRemark(leaveBalanceSummryDto.getCancleRemark());
		leaveType.setLeaveTypeId(leaveBalanceSummryDto.getLeaveTypeId());
		leaveEntry.setCompanyId(leaveBalanceSummryDto.getCompanyId());
		leaveEntry.setTmsleaveType(leaveType);

		if (leaveBalanceSummryDto.getLeaveId() != null) {
			leaveEntry.setLeaveId(leaveBalanceSummryDto.getLeaveId());
			leaveEntry.setDateCreated(leaveBalanceSummryDto.getDateCreated());
		} else
			leaveEntry.setDateCreated(new Date());
		leaveEntry.setUserIdUpdate(leaveBalanceSummryDto.getUserIdUpdate());
		leaveEntry.setDateUpdate(new Date());
		leaveEntry.setUserId(leaveBalanceSummryDto.getUserId());
		leaveEntry.setEmployeeId(leaveBalanceSummryDto.getEmployeeId());
		leaveEntry.setApprovalId(leaveBalanceSummryDto.getApprovalId());
		leaveEntry.setEmployeeCode(leaveBalanceSummryDto.getEmployeeCode());
		leaveEntry.setHalfDayFor(leaveBalanceSummryDto.getHalfDayFor());
		if (leaveEntry.getApprovalId() != null)
			leaveEntry.setActionableDate(new Date());
		StringBuilder sb = new StringBuilder();
		if(leaveBalanceSummryDto.getNotifyEmployeeList()!=null)
		for (Employee employee : leaveBalanceSummryDto.getNotifyEmployeeList()) {
			sb.append(employee.getEmployeeId().toString()).append(",");
			
			LeaveNotifyEmployee leaveNotifyEmployee = new LeaveNotifyEmployee();
			leaveNotifyEmployee.setEmployeeId(employee.getEmployeeId());
			leaveNotifyEmployee.setTmsleaveEntry(leaveEntry);
			leaveNotifyEmployee.setCreatedDate(new Date());
			leaveNotifyEmployees.add(leaveNotifyEmployee);
		}
		
		if (sb.length() > 0)
			leaveEntry.setNotifyEmployee(sb.substring(0, sb.length() - 1).toString());
		
		leaveEntry.setLeaveNotifyEmployees(leaveNotifyEmployees);
		return leaveEntry;
	}

	public List<LeaveEntryDTO> databaseModelToUiDtoList(List<TMSLeaveEntry> leaveEntryList) {
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();

		for (TMSLeaveEntry leaveEntry : leaveEntryList) {

			leaveEntryDtoList.add(databaseModelToUiDto(leaveEntry, null, null, null));
		}
		return leaveEntryDtoList;
	}

	public LeaveEntryDTO databaseModelToUiDto(TMSLeaveEntry leaveEntry, Employee employeeEmp, Employee approvalEmp,
			List<EmployeeDTO> employeeDtoList) {
		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();

		leaveEntryDto.setLeaveId(leaveEntry.getLeaveId());
		// leaveEntryDto.setApprovalId(leaveEntry.getApprovalEmployee().getEmployeeId());
		leaveEntryDto.setApprovalRemark(leaveEntry.getApprovalRemark());
		leaveEntryDto.setDays(leaveEntry.getDays());
		leaveEntryDto.setEmployeeRemark(leaveEntry.getEmployeeRemark());
		leaveEntryDto.setFromDate(leaveEntry.getFromDate());
		leaveEntryDto.setToDate(leaveEntry.getToDate());
		leaveEntryDto.setDateCreated(leaveEntry.getDateCreated());
		leaveEntryDto.setDateUpdate(leaveEntryDto.getDateUpdate());
		leaveEntryDto.setHalfDayFor(leaveEntry.getHalfDayFor());
		// leaveEntryDto.setIsApproved(leaveEntry.getIsApproved());
		leaveEntryDto.setIsRead(leaveEntry.getIsRead());
		leaveEntryDto.setStatus(leaveEntry.getStatus());
		/*
		 * if (leaveEntryDto.getStatus().equals("P")) {
		 * leaveEntryDto.setStatusValue("Pending"); } else if
		 * (leaveEntryDto.getStatus().equals("R")) {
		 * leaveEntryDto.setStatusValue("Rejected"); } else if
		 * (leaveEntryDto.getStatus().equals("A")) {
		 * leaveEntryDto.setStatusValue("Approved"); } else if
		 * (leaveEntryDto.getStatus().equals("C")) {
		 * leaveEntryDto.setStatusValue("Canceled"); }
		 */
		if (leaveEntry.getHalfDayFor() != null) {

			if (leaveEntry.getHalfDayFor().equals("1"))
				leaveEntryDto.setHalfDayForValue("First Half");
			else if (leaveEntry.getHalfDayFor().equals("2"))
				leaveEntryDto.setHalfDayForValue("Second Half");
		}
		try {
		if(leaveEntry.getTmsleaveType()!=null && leaveEntry.getTmsleaveType().getLeaveTypeId()!=null) {
		leaveEntryDto.setLeaveTypeId(leaveEntry.getTmsleaveType().getLeaveTypeId());
		}
		if(leaveEntry.getTmsleaveType()!=null && leaveEntry.getTmsleaveType().getTmsleaveTypeMaster()!=null
				&& leaveEntry.getTmsleaveType().getTmsleaveTypeMaster().getLeaveName()!=null ) {
		leaveEntryDto.setLeaveType(leaveEntry.getTmsleaveType().getTmsleaveTypeMaster().getLeaveName());
		}
		}catch(Exception e) {
			e.getMessage();
		}
		if (leaveEntryDto.getStatus().equals(StatusMessage.PENDING_CODE)) {
			leaveEntryDto.setStatusValue(StatusMessage.PENDING_VALUE);
		} else if (leaveEntryDto.getStatus().equals(StatusMessage.REJECTED_CODE)) {
			leaveEntryDto.setStatusValue(StatusMessage.REJECTED_VALUE);
		} else if (leaveEntryDto.getStatus().equals(StatusMessage.APPROVED_CODE)) {
			leaveEntryDto.setStatusValue(StatusMessage.APPROVED_VALUE);
		} else if (leaveEntryDto.getStatus().equals(StatusMessage.CANCEL_CODE)) {
			leaveEntryDto.setStatusValue(StatusMessage.CANCEL_VALUE);
		}
		leaveEntryDto.setCompanyId(leaveEntry.getCompanyId());
		leaveEntryDto.setToDate(leaveEntry.getToDate());
		leaveEntryDto.setDateCreated(leaveEntry.getDateCreated());
		leaveEntryDto.setUserId(leaveEntry.getUserId());
		leaveEntryDto.setDateUpdate(leaveEntry.getDateUpdate());
		leaveEntryDto.setActionableDate(leaveEntry.getActionableDate());
		leaveEntryDto.setEmployeeId(leaveEntry.getEmployeeId());
		leaveEntryDto.setCancleRemark(leaveEntry.getCancleRemark());
		leaveEntryDto.setHalf_fullDay(leaveEntry.getHalfFullDay());
		leaveEntryDto.setNotifyEmployee(leaveEntry.getNotifyEmployee());
		leaveEntryDto.setNotifyEmployeeList(employeeDtoList);
		/*
		 * if (employeeDto != null) {
		 * leaveEntryDto.setEmployeeName(employeeDto.getFirstName() + " " +
		 * employeeDto.getLastName());
		 * leaveEntryDto.setDepartment(employeeDto.getDepartmentName());
		 * leaveEntryDto.setDesignation(employeeDto.getDesignationName());
		 * leaveEntryDto.setEmployeeCode(employeeDto.getEmployeeCode());
		 */

		if (employeeEmp != null) {
			leaveEntryDto.setEmployeeName(employeeEmp.getFirstName() + " " + employeeEmp.getLastName());
			leaveEntryDto.setDepartment(employeeEmp.getDepartment().getDepartmentName());
			leaveEntryDto.setDesignation(employeeEmp.getDesignation().getDesignationName());
			leaveEntryDto.setEmployeeCode(employeeEmp.getEmployeeCode());

		}
		if (approvalEmp != null) {
			leaveEntryDto.setApprovalId(approvalEmp.getEmployeeId());
			leaveEntryDto.setApprovalEmployeeName(approvalEmp.getFirstName() + " " + approvalEmp.getLastName());
			leaveEntryDto.setApprovalEmployeeDepartment(approvalEmp.getDepartment().getDepartmentName());
			leaveEntryDto.setApprovalEmployeeDesignation(approvalEmp.getDesignation().getDesignationName());
			leaveEntryDto.setApprovalEmployeeCode(approvalEmp.getEmployeeCode());
		}

		return leaveEntryDto;
	}

	public List<LeaveBalanceDTO> objectArrayToUiDtoList(List<Object[]> leaveBalanceObjectDtoList,
			List<Object[]> employeeDetailsList, Long employeeId) {
		List<LeaveBalanceDTO> leaveBalanceDtoList = new ArrayList<LeaveBalanceDTO>();

		Long departmentId = null, desigantionId = null, weekOffPatternId = null;
		Date doj = null;
		String patternDays = null;

		for (Object[] employeeDetails : employeeDetailsList) {
			departmentId = employeeDetails[0] != null ? Long.parseLong(employeeDetails[0].toString()) : null;
			desigantionId = employeeDetails[1] != null ? Long.parseLong(employeeDetails[1].toString()) : null;
			doj = employeeDetails[2] != null ? (Date) (employeeDetails[2]) : null;
			weekOffPatternId = employeeDetails[3] != null ? Long.parseLong(employeeDetails[3].toString()) : null;
			patternDays = employeeDetails[4] != null ? (String) employeeDetails[4] : null;
		}
		

		for (Object[] leaveBalanceObj : leaveBalanceObjectDtoList) {
			LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
			Long leaveTypeHdId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString()) : null;
			String leaveRuleType = leaveBalanceObj[1] != null ? (String) leaveBalanceObj[1] : null;
			String leaveType = leaveBalanceObj[2] != null ? (String) leaveBalanceObj[2] : null;
			Long yearlyLimit = leaveBalanceObj[3] != null ? Long.parseLong(leaveBalanceObj[3].toString()) : null;
			BigDecimal index = leaveBalanceObj[4] != null ? (new BigDecimal(leaveBalanceObj[4].toString())) : null;

			Long indexDays = leaveBalanceObj[5] != null ? Long.parseLong(leaveBalanceObj[5].toString()) : null;
			Long maxLeaveInMonth = leaveBalanceObj[6] != null ? Long.parseLong(leaveBalanceObj[6].toString()) : null;
			Long leaveFrequencyInMonth = leaveBalanceObj[7] != null ? Long.parseLong(leaveBalanceObj[7].toString())
					: null;
			// Long sumdays = leaveBalanceObj[8] != null ?
			// Long.parseLong(leaveBalanceObj[8].toString()) : 0;
			BigDecimal sumdays = leaveBalanceObj[8] != null ? (new BigDecimal(leaveBalanceObj[8].toString()))
					: new BigDecimal("0.0");

			Date effectiveStartDate = leaveBalanceObj[9] != null ? (Date) (leaveBalanceObj[9]) : null;
			Date effectiveEndDate = leaveBalanceObj[10] != null ? (Date) (leaveBalanceObj[10]) : null;
			Long leaveTypeId = leaveBalanceObj[11] != null ? Long.parseLong(leaveBalanceObj[11].toString()) : null;

			leaveBalanceDto.setLeaveTypeId(leaveTypeId);
			leaveBalanceDto.setEmployeeId(employeeId);
			leaveBalanceDto.setLeaveRuleType(leaveRuleType);
			leaveBalanceDto.setMaxLeaveInMonth(maxLeaveInMonth);
			leaveBalanceDto.setLeaveFrequencyInMonth(leaveFrequencyInMonth);
			leaveBalanceDto.setLeaveType(leaveType);
			leaveBalanceDto.setEffectiveStartDate(effectiveStartDate);
			leaveBalanceDto.setEffectiveEndDate(effectiveEndDate);
			leaveBalanceDto.setLeaveTaken(sumdays);
			leaveBalanceDto.setWeekOffPatternId(weekOffPatternId);
			leaveBalanceDto.setPatternDays(patternDays);
			leaveBalanceDto.setLeaveTypeHdId(leaveTypeHdId);

			if (doj.before(effectiveStartDate) || doj.equals(effectiveStartDate)) {
				if (leaveRuleType.equals("LC")) {
					leaveBalanceDto.setYearlyLimit(yearlyLimit);

					BigDecimal bigYearlyLimit = new BigDecimal(yearlyLimit);

					leaveBalanceDto.setLeaveBalance(bigYearlyLimit.subtract(sumdays));
				} else {
					Long days = daysDifference(new Date(), effectiveStartDate);
					long new1 = days / indexDays;
					Long totalLeaveYearly = calculateDays(new1, index);

					BigDecimal bigTotalLeaveYearly = new BigDecimal(totalLeaveYearly);
					// long balance = bigTotalLeaveYearly.subtract(sumdays);
					leaveBalanceDto.setYearlyLimit(totalLeaveYearly);
					leaveBalanceDto.setLeaveBalance(bigTotalLeaveYearly.subtract(sumdays));
				}
			} else {
				if (leaveRuleType.equals("LC")) {
					Long days = daysDifference(effectiveEndDate, doj);
					Long yearlyLeave = (long) Math.round(yearlyLimit.doubleValue() * days.doubleValue() / 365);
					BigDecimal bigYearlyLeave = new BigDecimal(yearlyLeave);
					leaveBalanceDto.setYearlyLimit(yearlyLeave);
					leaveBalanceDto.setLeaveBalance(bigYearlyLeave.subtract(sumdays));
				} else {
					Long days = daysDifference(new Date(), doj);
					long new1 = days / indexDays;
					Long totalLeaveYearly = calculateDays(new1, index);
					BigDecimal bigTotalLeaveYearly = new BigDecimal(totalLeaveYearly);
					// long balance = totalLeaveYearly - sumdays;
					leaveBalanceDto.setYearlyLimit(totalLeaveYearly);
					leaveBalanceDto.setLeaveBalance(bigTotalLeaveYearly.subtract(sumdays));
				}
			}
			
			leaveBalanceDtoList.add(leaveBalanceDto);
		}
		return leaveBalanceDtoList;
	}

	public long calculateDays(long new1, BigDecimal index) {
		BigDecimal itemCost = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;
		itemCost = index.multiply(new BigDecimal(new1));
		totalCost = totalCost.add(itemCost);
		return totalCost.longValue();
	}

	public long daysDifference(Date fromDate, Date toDate) {
		long diff = fromDate.getTime() - toDate.getTime();
		Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return days;
	}

	public LeaveBalanceDTO objectArrayToUiDto(List<Object[]> leaveBalanceObjectDtoList, Long employeeId) {
		
		LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();

		for (Object[] leaveBalanceObj : leaveBalanceObjectDtoList) {
			Long leaveTypeHdId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString()) : null;
			String leaveRuleType = leaveBalanceObj[1] != null ? (String) leaveBalanceObj[1] : null;
			String leaveType = leaveBalanceObj[2] != null ? (String) leaveBalanceObj[2] : null;
			Long yearlyLimit = leaveBalanceObj[3] != null ? Long.parseLong(leaveBalanceObj[3].toString()) : null;
			BigDecimal index = leaveBalanceObj[4] != null ? (new BigDecimal(leaveBalanceObj[4].toString())) : null;
			Long indexDays = leaveBalanceObj[5] != null ? Long.parseLong(leaveBalanceObj[5].toString()) : null;
			Long maxLeaveInMonth = leaveBalanceObj[6] != null ? Long.parseLong(leaveBalanceObj[6].toString()) : null;
			Long leaveFrequencyInMonth = leaveBalanceObj[7] != null ? Long.parseLong(leaveBalanceObj[7].toString())
					: null;
			BigDecimal sumdays = leaveBalanceObj[8] != null ? (new BigDecimal(leaveBalanceObj[8].toString()))
					: new BigDecimal("0.0");

			Date effectiveStartDate = leaveBalanceObj[9] != null ? (Date) (leaveBalanceObj[9]) : null;
			Date effectiveEndDate = leaveBalanceObj[10] != null ? (Date) (leaveBalanceObj[10]) : null;

			Date doj = leaveBalanceObj[11] != null ? (Date) (leaveBalanceObj[11]) : null;
			Long departmentId = leaveBalanceObj[12] != null ? Long.parseLong(leaveBalanceObj[12].toString()) : null;
			Long weekOffPatternId = leaveBalanceObj[13] != null ? Long.parseLong(leaveBalanceObj[13].toString()) : null;
			String patternDays = leaveBalanceObj[14] != null ? (String) leaveBalanceObj[14] : null;

			// Boolean isWeekOffAsPl = leaveBalanceObj[15] != null ? (Boolean)
			// leaveBalanceObj[15] : null;

			// String weekOffAsPlCount = leaveBalanceObj[16] != null ? (String)
			// leaveBalanceObj[16] : null;

			String leaveInProbation = leaveBalanceObj[17] != null ? (String) leaveBalanceObj[17] : null;

			Long probationDays = leaveBalanceObj[18] != null ? Long.parseLong(leaveBalanceObj[18].toString()) : null;

			leaveBalanceDto.setLeaveTypeHdId(leaveTypeHdId);
			leaveBalanceDto.setEmployeeId(employeeId);
			leaveBalanceDto.setLeaveRuleType(leaveRuleType);
			leaveBalanceDto.setMaxLeaveInMonth(maxLeaveInMonth);
			leaveBalanceDto.setLeaveFrequencyInMonth(leaveFrequencyInMonth);
			leaveBalanceDto.setLeaveType(leaveType);
			leaveBalanceDto.setEffectiveStartDate(effectiveStartDate);
			leaveBalanceDto.setEffectiveEndDate(effectiveEndDate);
			leaveBalanceDto.setLeaveTaken(sumdays);
			leaveBalanceDto.setWeekOffPatternId(weekOffPatternId);
			leaveBalanceDto.setPatternDays(patternDays);
			leaveBalanceDto.setIsLeaveInProbation(leaveInProbation);
			leaveBalanceDto.setProbationDays(probationDays);
			leaveBalanceDto.setDateOfJoining(doj);
			
			if (doj.before(effectiveStartDate) || doj.equals(effectiveStartDate)) {
				if (leaveRuleType.equals("LC")) {
					leaveBalanceDto.setYearlyLimit(yearlyLimit);
					BigDecimal bigYearlyLimit = new BigDecimal(yearlyLimit);
					leaveBalanceDto.setLeaveBalance(bigYearlyLimit.subtract(sumdays));
				} else {
					Long days = daysDifference(new Date(), effectiveStartDate);
					long new1 = days / indexDays;
					Long totalLeaveYearly = calculateDays(new1, index);
					BigDecimal bigTotalLeaveYearly = new BigDecimal(totalLeaveYearly);
					// long balance = bigTotalLeaveYearly.subtract(sumdays);
					leaveBalanceDto.setYearlyLimit(totalLeaveYearly);
					leaveBalanceDto.setLeaveBalance(bigTotalLeaveYearly.subtract(sumdays));
				}
			} else {
				if (leaveRuleType.equals("LC")) {
					Long days = daysDifference(effectiveEndDate, doj);
					Long yearlyLeave = (long) Math.round(yearlyLimit.doubleValue() * days.doubleValue() / 365);
					leaveBalanceDto.setYearlyLimit(yearlyLeave);
					BigDecimal bigYearlyLeave = new BigDecimal(yearlyLeave);

					leaveBalanceDto.setLeaveBalance(bigYearlyLeave.subtract(sumdays));
				} else {
					Long days = daysDifference(new Date(), doj);
					long new1 = days / indexDays;
					Long totalLeaveYearly = calculateDays(new1, index);
					BigDecimal bigTotalLeaveYearly = new BigDecimal(totalLeaveYearly);

					// long balance = totalLeaveYearly - sumdays;
					leaveBalanceDto.setYearlyLimit(totalLeaveYearly);
					leaveBalanceDto.setLeaveBalance(bigTotalLeaveYearly.subtract(sumdays));
				}
			}
			
		}
		return leaveBalanceDto;
	}

	public BigDecimal objectArrayToLongDays(List<Object[]> daysList) {
		BigDecimal days = new BigDecimal("0.0");

		for (Object[] requestedDays : daysList) {

			// days = requestedDays[1] != null ? Long.parseLong(requestedDays[1].toString())
			// : 0l;
			days = requestedDays[1] != null ? (new BigDecimal(requestedDays[1].toString())) : new BigDecimal("0.0");

		}
		return days;
	}

	public LeaveBalanceDTO weekOffPattenObjToUiDto(List<Object[]> weekOffPattenList) {

		LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
		List<TMSWeekOffChildPatternDTO> tmsWeekOffChildPatternDTOList = new ArrayList<TMSWeekOffChildPatternDTO>();
		for (Object[] leaveBalanceObj : weekOffPattenList) {
			TMSWeekOffChildPatternDTO tmsWeekOffChildPatternDTO = new TMSWeekOffChildPatternDTO();

			Long id = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString()) : null;
			Long patternId = leaveBalanceObj[1] != null ? Long.parseLong(leaveBalanceObj[1].toString()) : null;
			String dayName = leaveBalanceObj[2] != null ? (String) leaveBalanceObj[2] : null;
			Long positionOfDay = leaveBalanceObj[3] != null ? Long.parseLong(leaveBalanceObj[3].toString()) : null;
			String natureOfDay = leaveBalanceObj[4] != null ? (String) leaveBalanceObj[4] : null;

			tmsWeekOffChildPatternDTO.setId(id);
			tmsWeekOffChildPatternDTO.setPatternId(patternId);
			tmsWeekOffChildPatternDTO.setNatureOfDay(natureOfDay);
			tmsWeekOffChildPatternDTO.setPositionOfDay(positionOfDay);
			tmsWeekOffChildPatternDTO.setDayName(dayName);
			tmsWeekOffChildPatternDTOList.add(tmsWeekOffChildPatternDTO);

		}
		leaveBalanceDto.setTmsWeekOffChildPatternDTO(tmsWeekOffChildPatternDTOList);

		return leaveBalanceDto;
	}
//	public LeaveBalanceDTO weekOffPattenObjToUiDto(List<Object[]> weekOffPattenList) {
//
//		LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
//
//		for (Object[] leaveBalanceObj : weekOffPattenList) {
//			Long patternId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString()) : null;
//			String patternDays = leaveBalanceObj[1] != null ? (String) leaveBalanceObj[1] : null;
//			leaveBalanceDto.setWeekOffPatternId(patternId);
//			leaveBalanceDto.setPatternDays(patternDays);
//		}
//		return leaveBalanceDto;
//
//	}

	public LeaveEntryDTO pendingRequestLeaveAndARObjtoUiDto(List<Object[]> pendingRequestLeaveAndARObjList) {

		LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
		HashMap<Long, String> map = new HashMap<Long, String>();
		for (Object[] leaveObj : pendingRequestLeaveAndARObjList) {

			Long employeeId = leaveObj[0] != null ? Long.parseLong(leaveObj[0].toString()) : null;
			Long departmentId = leaveObj[1] != null ? Long.parseLong(leaveObj[1].toString()) : null;
			String deptName = leaveObj[2] != null ? (String) leaveObj[2] : null;
			map.put(departmentId, deptName);
			leaveEntryDto.setEmployeeId(employeeId);
			leaveEntryDto.setDepartmentId(departmentId);
			leaveEntryDto.setMap(map);

		}
		return leaveEntryDto;

	}

	public List<LeaveBalanceSummryDTO> leaveBalanceSummryObjToUiDto(List<Object[]> leaveBalanceSummryList) {

//leaveTypeId,leavePeriodId,leaveTypeMasterId,leaveMode,leaveName,yearlyLimit,carryForwordLeave,totalleave,consumed
//balanceleave,indexDays,indexDays,maxLeaveInMonth,leaveFrequencyInMonth

		List<LeaveBalanceSummryDTO> LeaveBalanceSummryList = new ArrayList<LeaveBalanceSummryDTO>();
		for (Object[] leaveBalanceObj : leaveBalanceSummryList) {
			LeaveBalanceSummryDTO leaveBalanceSummryDto = new LeaveBalanceSummryDTO();
			Long leaveTypeId = leaveBalanceObj[0] != null ? Long.parseLong(leaveBalanceObj[0].toString()) : null;
			Long leavePeriodId = leaveBalanceObj[1] != null ? Long.parseLong(leaveBalanceObj[1].toString()) : null;
			Long leaveTypeMasterId = leaveBalanceObj[2] != null ? Long.parseLong(leaveBalanceObj[2].toString()) : null;
			String leaveMode = leaveBalanceObj[3] != null ? (String) leaveBalanceObj[3] : null;
			String leaveName = leaveBalanceObj[4] != null ? (String) leaveBalanceObj[4] : null;
			Long yearlyLimit = leaveBalanceObj[5] != null ? Long.parseLong(leaveBalanceObj[5].toString()) : null;
			BigDecimal carryForwordLeave = leaveBalanceObj[6] != null ? (new BigDecimal(leaveBalanceObj[6].toString())) : null;

			BigDecimal totalleave = leaveBalanceObj[7] != null ? (new BigDecimal(leaveBalanceObj[7].toString())) : null;

			BigDecimal consumed = leaveBalanceObj[8] != null ? (new BigDecimal(leaveBalanceObj[8].toString())) : null;
			BigDecimal balanceleave = leaveBalanceObj[9] != null ? (new BigDecimal(leaveBalanceObj[9].toString()))
					: null;

			// Long consumed = leaveBalanceObj[8] != null ? getLong(leaveBalanceObj[8]) :
			// null;
			// Long
			// balanceleave=leaveBalanceObj[9]!=null?(getLong(leaveBalanceObj[9])):null;

			Long indexDays = leaveBalanceObj[10] != null ? Long.parseLong(leaveBalanceObj[10].toString()) : null;
			Long maxLeaveInMonth = leaveBalanceObj[11] != null ? Long.parseLong(leaveBalanceObj[11].toString()) : null;
			Long leaveFrequencyInMonth = leaveBalanceObj[12] != null ? Long.parseLong(leaveBalanceObj[12].toString())
					: null;
			String isLeaveInProbation = leaveBalanceObj[13] != null ? (getString(leaveBalanceObj[13])) : null;
			Long carryForwordLimit = leaveBalanceObj[14] != null ? Long.parseLong(leaveBalanceObj[14].toString())
					: null;
			String nature = leaveBalanceObj[15] != null ? (getString(leaveBalanceObj[15])) : null;
			Long notice = leaveBalanceObj[16] != null ? Long.parseLong(leaveBalanceObj[16].toString()) : null;
			Long weekoffAsPlCount = leaveBalanceObj[17] != null ? Long.parseLong(leaveBalanceObj[17].toString()) : null;

			String leavePeriodName = leaveBalanceObj[18] != null ? (getString(leaveBalanceObj[18])) : null;
			Date leavePeriodStartDate = leaveBalanceObj[19] != null ? (Date) (leaveBalanceObj[19]) : null;
			Date leavePeriodEndDate = leaveBalanceObj[20] != null ? (Date) (leaveBalanceObj[20]) : null;
			Date employeeJoiningDate = leaveBalanceObj[21] != null ? (Date) (leaveBalanceObj[21]) : null;

			Long encashLimit = leaveBalanceObj[22] != null ? (Long.parseLong(leaveBalanceObj[22].toString())) : null;
			// String
			// leaveMode=leaveBalanceObj[22]!=null?(getString(leaveBalanceObj[22])):null;
			BigDecimal openingLeave = leaveBalanceObj[23] != null ? (new BigDecimal(leaveBalanceObj[23].toString()))
					: null;

			String leaveCalculationOnProbation = leaveBalanceObj[24] != null ? (getString(leaveBalanceObj[24])) : null;
			Integer probabtionDays = leaveBalanceObj[25] != null ? Integer.parseInt(leaveBalanceObj[25].toString())
					: null;

			leaveBalanceSummryDto.setLeaveTypeId(leaveTypeId);
			leaveBalanceSummryDto.setTmsleavePeriodId(leavePeriodId);
			leaveBalanceSummryDto.setTmsleaveTypeMasterId(leaveTypeMasterId);
			leaveBalanceSummryDto.setLeaveMode(leaveMode);
			leaveBalanceSummryDto.setLeaveName(leaveName);
			leaveBalanceSummryDto.setYearlyLimit(yearlyLimit);
			leaveBalanceSummryDto.setCarryForwordLeave(carryForwordLeave);
			leaveBalanceSummryDto.setNature(nature);
			leaveBalanceSummryDto.setLeaveName(leaveName);
			leaveBalanceSummryDto.setEncashLimit(encashLimit);
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

					long timeDiff = Math.abs(leavePeriodEndDate.getTime() - leaveCalculationDate.getTime());
					double empJoinedDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;

					long leavePeriodTimeDiff = Math.abs(leavePeriodEndDate.getTime() - leavePeriodStartDate.getTime());
					double leavePeriodTimeDiffDays = Math.ceil(leavePeriodTimeDiff / (1000 * 3600 * 24)) + 1;

					// * Date 08/08/2019 change balance summury accroding to opening leave for
					// employee joining date after leave period date according to neelesh sir**/ *//
					// totalleave=(new BigDecimal(empJoinedDays).multiply(new
					// BigDecimal(totalleave)).divide(new BigDecimal(leavePeriodTimeDiffDays),
					// 2,RoundingMode.CEILING)).longValue();
					totalleave = (new BigDecimal(empJoinedDays).multiply(new BigDecimal(yearlyLimit))
							.divide(new BigDecimal(leavePeriodTimeDiffDays), 2, RoundingMode.CEILING));
					// totalleave=totalleave.subtract(openingLeave);

					MathContext m = new MathContext(2);
					totalleave = totalleave.setScale(0, RoundingMode.HALF_UP);
					;
					// totalleave=totalleave.setScale(2, BigDecimal.ROUND_HALF_UP);

					if (leaveMode.equals("IN")) {
						// totalleave = new BigDecimal(1.0);
						BigDecimal totalLeave = new BigDecimal(1.0);
						long timeDiffIndex = Math.abs(new Date().getTime() - leaveCalculationDate.getTime());
						double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24)) + 1;

						BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays)).divide(new BigDecimal(indexDays), 2,
								RoundingMode.CEILING);
					
						if (indexdays.compareTo(totalleave) > 0) {
							totalleave = indexdays;
							Double d = new Double(Math.round(totalleave.doubleValue()));
							totalleave = BigDecimal.valueOf(d).add(totalLeave);
							if (yearlyLimit <= (totalleave.longValue())) {
								totalleave = BigDecimal.valueOf(yearlyLimit);
							}
						} else {
						//	totalLeave = totalLeave.add(indexdays);
							totalleave= totalLeave.add(indexdays);
							totalleave = totalleave.setScale(0, RoundingMode.HALF_UP);
							//totalleave=totalleave.add(carryForwordLeave);
						}
						totalleave=totalleave.add(carryForwordLeave);
					} 
					if (leaveCalculationDate.after(new Date()) && leaveCalculationOnProbation.equals("N")) {
						totalleave = new BigDecimal(0);
						
					}

				    } else {
					if (leaveMode.equals("IN")) {
						BigDecimal totalLeave = new BigDecimal(1.0);
						long timeDiffIndex = Math.abs(new Date().getTime() - leavePeriodStartDate.getTime());
						double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24)) + 1;

						BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays)).divide(new BigDecimal(indexDays), 2,
								RoundingMode.CEILING);

						if (indexdays.compareTo(totalleave) > 0) {
							totalleave = indexdays;
							Double d = new Double(Math.round(totalleave.doubleValue()));
							totalleave = BigDecimal.valueOf(d).add(totalLeave);
							// totalleave.add(totalLeave);
						} else {
							totalleave= totalLeave.add(indexdays);
							totalleave = totalleave.setScale(0, RoundingMode.HALF_UP);
							
						}
						totalleave=totalleave.add(carryForwordLeave);
					}
				}
			}
			balanceleave = totalleave.subtract(consumed);
			totalleave = totalleave.setScale(2, BigDecimal.ROUND_HALF_UP);
			leaveBalanceSummryDto.setTotalLeave(totalleave);
			leaveBalanceSummryDto.setLeaveConsumedCount(consumed);
			leaveBalanceSummryDto.setLeaveBalancedCount(balanceleave);
			leaveBalanceSummryDto.setIndexDays(indexDays);
			leaveBalanceSummryDto.setMaxLeaveInMonth(maxLeaveInMonth);
			leaveBalanceSummryDto.setLeaveFrequencyInMonth(leaveFrequencyInMonth);
			leaveBalanceSummryDto.setIsLeaveInProbation(isLeaveInProbation);
			leaveBalanceSummryDto.setCarryForwardLimit(carryForwordLimit);
			leaveBalanceSummryDto.setNature(
					DropDownCache.getInstance().getDropDownValue(DropDownEnum.Nature.getDropDownName(), nature));
			leaveBalanceSummryDto.setNotice(notice);
			leaveBalanceSummryDto.setWeekOffAsPLCount(weekoffAsPlCount);
			LeaveBalanceSummryList.add(leaveBalanceSummryDto);
		}

		return LeaveBalanceSummryList;

	}

	public List<TeamLeaveOnCalenderDTO> teamLeaveOnCalenderObjToUiDto(List<Object[]> teamLeaveOnCalenderList ) {
		List<TeamLeaveOnCalenderDTO> teamLeaveOnCalenderDTOList = new ArrayList<TeamLeaveOnCalenderDTO>();
		Date frmDate =null;
		for (Object[] leaveObj : teamLeaveOnCalenderList) {
			TeamLeaveOnCalenderDTO teamLeaveOnCalenderDTO = new TeamLeaveOnCalenderDTO();
			BigInteger employeeId = leaveObj[0] != null ? (new BigInteger(leaveObj[0].toString())) : null;
			String employeeCode = leaveObj[1] != null ? (String) leaveObj[1] : null;
			String employeeName = leaveObj[2] != null ? (String) leaveObj[2] : null;
			String status = leaveObj[3] != null ? (String) leaveObj[3] : null;
			Date fromDate = leaveObj[4] != null ? (Date) leaveObj[4] : null;
			Date toDate = leaveObj[5] != null ? (Date) leaveObj[5] : null;
			BigInteger leaves = leaveObj[6] != null ? (new BigInteger(leaveObj[6].toString())) : null;
			frmDate = fromDate;
			teamLeaveOnCalenderDTO.setEmployeeId(employeeId.toString());
			teamLeaveOnCalenderDTO.setEmployeeCode(employeeCode);
			teamLeaveOnCalenderDTO.setEmployeeName(employeeName);
			teamLeaveOnCalenderDTO.setStatus(status);
			teamLeaveOnCalenderDTO.setFromDate(fromDate.toString());
			teamLeaveOnCalenderDTO.setToDate(toDate.toString());
			teamLeaveOnCalenderDTO.setLeaves(leaves.toString());
			teamLeaveOnCalenderDTOList.add(teamLeaveOnCalenderDTO);
		}
		
		if(teamLeaveOnCalenderDTOList.size() > 0) {
			return setEventsForCalendar(teamLeaveOnCalenderDTOList,frmDate);
		}
		
		return teamLeaveOnCalenderDTOList;

	}

	public List<TeamLeaveOnCalenderDTO> leaveAttendancePendingObjListToUiDto(List<Object[]> teamLeaveOnCalenderList,
			int size) {
		List<TeamLeaveOnCalenderDTO> TeamLeaveOnCalenderDTOList = new ArrayList<TeamLeaveOnCalenderDTO>();
		for (Object[] leaveObj : teamLeaveOnCalenderList) {
			TeamLeaveOnCalenderDTO teamLeaveOnCalenderDTO = new TeamLeaveOnCalenderDTO();
			/**
			 * firstName lastName employeeId fromDate toDate Id employeeCode status remark
			 * departmentName
			 */

			String firstName = leaveObj[0] != null ? (String) leaveObj[0] : null;
			String lastName = leaveObj[1] != null ? (String) leaveObj[1] : null;
			BigInteger employeeId = leaveObj[2] != null ? (new BigInteger(leaveObj[2].toString())) : null;
			Date fromDate = leaveObj[3] != null ? (Date) leaveObj[3] : null;
			Date toDate = leaveObj[4] != null ? (Date) leaveObj[4] : null;
			BigInteger Id = leaveObj[5] != null ? (new BigInteger(leaveObj[5].toString())) : null;
			String employeeCode = leaveObj[6] != null ? (String) leaveObj[6] : null;
			String status = leaveObj[7] != null ? (String) leaveObj[7] : null;
			String remark = leaveObj[8] != null ? (String) leaveObj[8] : null;
			String departmentName = leaveObj[9] != null ? (String) leaveObj[9] : null;

			teamLeaveOnCalenderDTO.setSize(size);
			teamLeaveOnCalenderDTO.setEmployeeId(employeeId.toString());
			teamLeaveOnCalenderDTO.setEmployeeName(firstName + " " + lastName);
			teamLeaveOnCalenderDTO.setStatus(status);
			teamLeaveOnCalenderDTO.setFromDate(fromDate.toString());
			teamLeaveOnCalenderDTO.setToDate(toDate.toString());
			teamLeaveOnCalenderDTO.setDepartmentName(departmentName);
			teamLeaveOnCalenderDTO.setRemark(remark);
			teamLeaveOnCalenderDTO.setId(Id.toString());
			teamLeaveOnCalenderDTO.setEmployeeCode(employeeCode.toString());
			TeamLeaveOnCalenderDTOList.add(teamLeaveOnCalenderDTO);
		}
		return TeamLeaveOnCalenderDTOList;

	}

	public static String getString(Object value) {
		String ret = null;
		if (value != null) {
			ret = String.valueOf(value);

		}
		return ret;
	}

	public static Long getLong(Object value) {
		Long ret = null;
		if (value != null) {
			String stringToConvert = String.valueOf(value);
			ret = Long.parseLong(stringToConvert);

		}
		return ret;
	}

	public List<LeaveEntryDTO> databaseModelObjToUiDtoList(List<Object[]> leaveEntryObjList) {
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();
		for (Object[] leaveEntryObj : leaveEntryObjList) {

			LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
			Long companyId = leaveEntryObj[1] != null ? Long.parseLong(leaveEntryObj[1].toString()) : null;
			Long employeeId = leaveEntryObj[2] != null ? Long.parseLong(leaveEntryObj[2].toString()) : null;
			String half_fullDay = leaveEntryObj[6] != null ? (String) leaveEntryObj[6] : null;
			String status = leaveEntryObj[7] != null ? (String) leaveEntryObj[7] : null;
			Date fromDate = leaveEntryObj[8] != null ? (Date) (leaveEntryObj[8]) : null;
			Date toDate = leaveEntryObj[9] != null ? (Date) (leaveEntryObj[9]) : null;
			// Long day = leaveEntryObj[10] != null ?
			// Long.parseLong(leaveEntryObj[10].toString()) : null;
			BigDecimal day = leaveEntryObj[10] != null ? (new BigDecimal(leaveEntryObj[10].toString()))
					: new BigDecimal("0.0");

			leaveEntryDto.setCompanyId(companyId);
			leaveEntryDto.setEmployeeId(employeeId);
			leaveEntryDto.setHalf_fullDay(half_fullDay);
			leaveEntryDto.setStatus(status);
			leaveEntryDto.setFromDate(fromDate);
			leaveEntryDto.setToDate(toDate);
			leaveEntryDto.setDays(day);
			leaveEntryDtoList.add(leaveEntryDto);
		}

		return leaveEntryDtoList;
	}

	public List<LeaveEntryDTO> databaseObjToUiDtoList(List<Object[]> objLeaveEntryList) {
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();
		for (Object[] leaveEntryObj : objLeaveEntryList) {
			LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
			String firstName = leaveEntryObj[0] != null ? (String) leaveEntryObj[0] : null;
			String lastName = leaveEntryObj[1] != null ? (String) leaveEntryObj[1] : null;
			String departmentName = leaveEntryObj[2] != null ? (String) leaveEntryObj[2] : null;
			String designationName = leaveEntryObj[3] != null ? (String) leaveEntryObj[3] : null;
			String employeeCode = leaveEntryObj[4] != null ? (String) leaveEntryObj[4] : null;
			Long leaveId = leaveEntryObj[5] != null ? Long.parseLong(leaveEntryObj[5].toString()) : null;
			String approvalRemark = leaveEntryObj[6] != null ? (String) leaveEntryObj[6] : null;
			BigDecimal days = leaveEntryObj[7] != null ? (new BigDecimal(leaveEntryObj[7].toString()))
					: new BigDecimal("0.0");
			String employeeRemark = leaveEntryObj[8] != null ? (String) leaveEntryObj[8] : null;
			Date fromDate = leaveEntryObj[9] != null ? (Date) (leaveEntryObj[9]) : null;
			String halfFull_day = leaveEntryObj[10] != null ? (String) leaveEntryObj[10] : null;
			String halfDayFor = leaveEntryObj[11] != null ? (String) leaveEntryObj[11] : null;
			Boolean isRead = leaveEntryObj[12] != null ? (Boolean) leaveEntryObj[12] : null;
			String status = leaveEntryObj[13] != null ? (String) leaveEntryObj[13] : null;
			Long leaveTypeId = leaveEntryObj[14] != null ? Long.parseLong(leaveEntryObj[14].toString()) : null;
			String leaveType = leaveEntryObj[15] != null ? (String) leaveEntryObj[15] : null;
			Long companyId = leaveEntryObj[16] != null ? Long.parseLong(leaveEntryObj[16].toString()) : null;
			Date toDate = leaveEntryObj[17] != null ? (Date) (leaveEntryObj[17]) : null;
			Date dateCreated = leaveEntryObj[18] != null ? (Date) (leaveEntryObj[18]) : null;
			Long userId = leaveEntryObj[19] != null ? Long.parseLong(leaveEntryObj[19].toString()) : null;
			Date dateUpdate = leaveEntryObj[20] != null ? (Date) (leaveEntryObj[20]) : null;
			Date actionDate = leaveEntryObj[21] != null ? (Date) (leaveEntryObj[21]) : null;
			Long employeeId = leaveEntryObj[22] != null ? Long.parseLong(leaveEntryObj[22].toString()) : null;
			leaveEntryDto.setEmployeeName(firstName + " " + lastName);
			leaveEntryDto.setDepartment(departmentName);
			leaveEntryDto.setDesignation(designationName);
			leaveEntryDto.setEmployeeCode(employeeCode);
			leaveEntryDto.setLeaveId(leaveId);
			leaveEntryDto.setApprovalRemark(approvalRemark);
			leaveEntryDto.setDays(days);
			leaveEntryDto.setEmployeeRemark(employeeRemark);
			leaveEntryDto.setFromDate(fromDate);
			leaveEntryDto.setHalf_fullDay(halfFull_day);
			leaveEntryDto.setHalfDayFor(halfDayFor);
			if (leaveEntryDto.getHalfDayFor() != null) {

				if (leaveEntryDto.getHalfDayFor().equals("1"))
					leaveEntryDto.setHalfDayForValue("First Half");
				else if (leaveEntryDto.getHalfDayFor().equals("2"))
					leaveEntryDto.setHalfDayForValue("Second Half");
			}

			byte isReadByte = (byte) (isRead ? 1 : 0);
			leaveEntryDto.setIsRead(isReadByte);
			leaveEntryDto.setStatus(status);
			if (leaveEntryDto.getStatus().equals(StatusMessage.PENDING_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.PENDING_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.REJECTED_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.REJECTED_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.APPROVED_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.APPROVED_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.CANCEL_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.CANCEL_VALUE);
			}
			leaveEntryDto.setLeaveTypeId(leaveTypeId);
			leaveEntryDto.setLeaveType(leaveType);
			leaveEntryDto.setCompanyId(companyId);
			leaveEntryDto.setToDate(toDate);
			leaveEntryDto.setDateCreated(dateCreated);
			leaveEntryDto.setUserId(userId);
			leaveEntryDto.setDateUpdate(dateUpdate);
			leaveEntryDto.setActionableDate(actionDate);
			leaveEntryDto.setEmployeeId(employeeId);
			leaveEntryDtoList.add(leaveEntryDto);
		}

		return leaveEntryDtoList;
	}

	public List<LeaveEntryDTO> modeltoDTOList(List<Object[]> objLeaveEntryList, LeaveSearchDTO leavesearchDto) {
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();
		for (Object[] leaveEntryObj : objLeaveEntryList) {
			LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();
			Long leaveId = leaveEntryObj[0] != null ? Long.parseLong(leaveEntryObj[0].toString()) : null;
			// Long leaveTypeId = leaveEntryObj[1] != null ?
			// Long.parseLong(leaveEntryObj[1].toString()) : null;
			Long employeeId = leaveEntryObj[1] != null ? Long.parseLong(leaveEntryObj[1].toString()) : null;
			Long approvalId = leaveEntryObj[2] != null ? Long.parseLong(leaveEntryObj[2].toString()) : null;
			Date dateCreated = leaveEntryObj[3] != null ? (Date) (leaveEntryObj[3]) : null;
			BigDecimal days = leaveEntryObj[4] != null ? (new BigDecimal(leaveEntryObj[4].toString()))
					: new BigDecimal("0.0");
			String status = leaveEntryObj[5] != null ? (String) leaveEntryObj[5] : null;
			String designationName = leaveEntryObj[6] != null ? (String) leaveEntryObj[6] : null;
			String firstName = leaveEntryObj[7] != null ? (String) leaveEntryObj[7] : null;
			String lastName = leaveEntryObj[8] != null ? (String) leaveEntryObj[8] : null;

			String leaveName = leaveEntryObj[9] != null ? (String) leaveEntryObj[9] : null;
			String employeeRemark = leaveEntryObj[10] != null ? (String) leaveEntryObj[10] : null;
			String approvalRemark = leaveEntryObj[11] != null ? (String) leaveEntryObj[11] : null;
			String cancelRemark = leaveEntryObj[12] != null ? (String) leaveEntryObj[12] : null;
			Date fromDate = leaveEntryObj[13] != null ? (Date) (leaveEntryObj[13]) : null;
			Date toDate = leaveEntryObj[14] != null ? (Date) (leaveEntryObj[14]) : null;
			String employeeCode = leaveEntryObj[15] != null ? (String) leaveEntryObj[15] : null;
			/*
			 * String approveEmpfirstName = leaveEntryObj[14] != null ? (String)
			 * leaveEntryObj[14] : null; String approveEmplastName = leaveEntryObj[15] !=
			 * null ? (String) leaveEntryObj[15] : null; String aproveEmpdesignationName =
			 * leaveEntryObj[16] != null ? (String) leaveEntryObj[16] : null;
			 */

			/*
			 * Date fromDate = leaveEntryObj[9] != null ? (Date) (leaveEntryObj[9]) : null;
			 * String halfFull_day = leaveEntryObj[10] != null ? (String) leaveEntryObj[10]
			 * : null; String halfDayFor = leaveEntryObj[11] != null ? (String)
			 * leaveEntryObj[11] : null; Boolean isRead = leaveEntryObj[12] != null ?
			 * (Boolean) leaveEntryObj[12] : null; String status = leaveEntryObj[13] != null
			 * ? (String) leaveEntryObj[13] : null; Long leaveTypeId = leaveEntryObj[14] !=
			 * null ? Long.parseLong(leaveEntryObj[14].toString()) : null; String leaveType
			 * = leaveEntryObj[15] != null ? (String) leaveEntryObj[15] : null; Long
			 * companyId = leaveEntryObj[16] != null ?
			 * Long.parseLong(leaveEntryObj[16].toString()) : null; Date toDate =
			 * leaveEntryObj[17] != null ? (Date) (leaveEntryObj[17]) : null;
			 * 
			 * Long userId = leaveEntryObj[19] != null ?
			 * Long.parseLong(leaveEntryObj[19].toString()) : null; Date dateUpdate =
			 * leaveEntryObj[20] != null ? (Date) (leaveEntryObj[20]) : null; Date
			 * actionDate = leaveEntryObj[21] != null ? (Date) (leaveEntryObj[21]) : null;
			 * Long employeeId = leaveEntryObj[22] != null ?
			 * Long.parseLong(leaveEntryObj[22].toString()) : null;
			 */

			leaveEntryDto.setLeaveId(leaveId);
			// leaveEntryDto.setLeaveTypeId(leaveTypeId);
			leaveEntryDto.setEmployeeName(firstName + " " + lastName);
			leaveEntryDto.setEmployeeId(employeeId);
			leaveEntryDto.setApprovalId(approvalId);
			leaveEntryDto.setDesignation(designationName);
			leaveEntryDto.setEmployeeCode(employeeCode);
			// leaveEntryDto.setEmployeeCode(employeeCode);

			leaveEntryDto.setDays(days);
			leaveEntryDto.setEmployeeRemark(employeeRemark);
			leaveEntryDto.setCancleRemark(cancelRemark);
			leaveEntryDto.setApprovalRemark(approvalRemark);
			if (leaveEntryDto.getHalfDayFor() != null) {

				if (leaveEntryDto.getHalfDayFor().equals("1"))
					leaveEntryDto.setHalfDayForValue("First Half");
				else if (leaveEntryDto.getHalfDayFor().equals("2"))
					leaveEntryDto.setHalfDayForValue("Second Half");
			}
			// leaveEntryDto.setApprovalEmployeeName(approveEmpfirstName+"
			// "+approveEmplastName);

			// leaveEntryDto.setApprovalEmployeeDesignation(aproveEmpdesignationName);
			leaveEntryDto.setStatus(status);

			if (leaveEntryDto.getStatus().equals(StatusMessage.PENDING_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.PENDING_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.REJECTED_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.REJECTED_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.APPROVED_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.APPROVED_VALUE);
			} else if (leaveEntryDto.getStatus().equals(StatusMessage.CANCEL_CODE)) {
				leaveEntryDto.setStatusValue(StatusMessage.CANCEL_VALUE);
			}
			leaveEntryDto.setLeaveType(leaveName);

			leaveEntryDto.setDateCreated(dateCreated);
			// byte isReadByte = (byte) (isRead ? 1 : 0);
			// leaveEntryDto.setIsRead(isReadByte);
			// leaveEntryDto.setCompanyId(companyId);
			leaveEntryDto.setToDate(toDate);
			// leaveEntryDto.setUserId(userId);
			// leaveEntryDto.setDateUpdate(dateUpdate);
			// leaveEntryDto.setActionableDate(actionDate);
			leaveEntryDto.setFromDate(fromDate);
			// leaveEntryDto.setHalf_fullDay(halfFull_day);
			// leaveEntryDto.setHalfDayFor(halfDayFor);

			leaveEntryDtoList.add(leaveEntryDto);
		}

		return leaveEntryDtoList;
	}

	public List<LeaveEntryDTO> objLeaveListToObjUiDtoList(List<Object[]> leaveEntryObjList) {
		List<LeaveEntryDTO> leaveEntryDtoList = new ArrayList<LeaveEntryDTO>();

		for (Object[] leaveEntryObj : leaveEntryObjList) {
			LeaveEntryDTO leaveEntryDto = new LeaveEntryDTO();

			String empCode = leaveEntryObj[0] != null ? (String) leaveEntryObj[0] : null;
			String firstName = leaveEntryObj[1] != null ? (String) leaveEntryObj[1] : null;
			String lastName = leaveEntryObj[2] != null ? (String) leaveEntryObj[2] : null;
			String departmentName = leaveEntryObj[3] != null ? (String) leaveEntryObj[3] : null;
			Long leaveId = leaveEntryObj[4] != null ? Long.parseLong(leaveEntryObj[4].toString()) : null;
			Date dateCreated = leaveEntryObj[5] != null ? (Date) (leaveEntryObj[5]) : null;
			Long leaveTypeId = leaveEntryObj[6] != null ? Long.parseLong(leaveEntryObj[6].toString()) : null;

			Date actionableDate = leaveEntryObj[7] != null ? (Date) (leaveEntryObj[7]) : null;
			Date fromDate = leaveEntryObj[8] != null ? (Date) (leaveEntryObj[8]) : null;
			Date toDate = leaveEntryObj[9] != null ? (Date) (leaveEntryObj[9]) : null;
			BigDecimal days = leaveEntryObj[10] != null ? (new BigDecimal(leaveEntryObj[10].toString()))
					: new BigDecimal("0.0");
			String status = leaveEntryObj[11] != null ? (String) leaveEntryObj[11] : null;
			Long employeeId = leaveEntryObj[12] != null ? Long.parseLong(leaveEntryObj[12].toString()) : null;
			String employeeRemark = leaveEntryObj[13] != null ? (String) leaveEntryObj[13] : null;
			String halfFullDay = leaveEntryObj[14] != null ? (String) leaveEntryObj[14] : null;
			Long userId = leaveEntryObj[15] != null ? Long.parseLong(leaveEntryObj[15].toString()) : null;
			String designationName = leaveEntryObj[16] != null ? (String) leaveEntryObj[16] : null;
			String employeeLogoPath = leaveEntryObj[17] != null ? (String) leaveEntryObj[17] : null;
			String leaveType = leaveEntryObj[18] != null ? (String) leaveEntryObj[18] : null;
			String approvalRemark = leaveEntryObj[19] != null ? (String) leaveEntryObj[19] : null;
			leaveEntryDto.setEmployeeCode(empCode);
			leaveEntryDto.setEmployeeName(firstName + " " + lastName);
			leaveEntryDto.setDepartment(departmentName);
			leaveEntryDto.setLeaveId(leaveId);
			leaveEntryDto.setDateCreated(dateCreated);
			leaveEntryDto.setEmployeeId(employeeId);
			leaveEntryDto.setEmployeeRemark(employeeRemark);
			leaveEntryDto.setApprovalRemark(approvalRemark);

			leaveEntryDto.setUserId(userId);
			leaveEntryDto.setDesignation(designationName);
			leaveEntryDto.setActionableDate(actionableDate);

			leaveEntryDto.setFromDate(fromDate);
			leaveEntryDto.setToDate(toDate);
			leaveEntryDto.setDays(days);
			leaveEntryDto.setHalf_fullDay(halfFullDay);
			leaveEntryDto.setEmployeeLogoPath(employeeLogoPath);
			leaveEntryDto.setLeaveTypeId(leaveTypeId);
			leaveEntryDto.setLeaveType(leaveType);

			if (status.equals(StatusMessage.PENDING_CODE)) {
				leaveEntryDto.setStatus(StatusMessage.PENDING_CODE);
				leaveEntryDto.setStatusValue(StatusMessage.PENDING_VALUE);
			}
			if (status.equals(StatusMessage.APPROVED_CODE)) {
				leaveEntryDto.setStatus(StatusMessage.APPROVED_CODE);
				leaveEntryDto.setStatusValue(StatusMessage.APPROVED_VALUE);
			}
			if (status.equals(StatusMessage.REJECTED_CODE)) {
				leaveEntryDto.setStatus(StatusMessage.REJECTED_CODE);
				leaveEntryDto.setStatusValue(StatusMessage.REJECTED_VALUE);
			}

			if (status.equals(StatusMessage.CANCEL_CODE)) {
				leaveEntryDto.setStatus(StatusMessage.CANCEL_CODE);
				leaveEntryDto.setStatusValue(StatusMessage.CANCEL_VALUE);
			}

			leaveEntryDtoList.add(leaveEntryDto);

		}

		return leaveEntryDtoList;
	}

	public List<EmployeeOpeningLeaveMaster> databaseObjectListToEmployeeOpenningLeave(
			List<Object[]> previousLeaveTypeList, List<EmployeeOpeningLeaveMaster> employeeOpeningList,
			TMSLeavePeriod tMSLeavePeriod) {
		List<EmployeeOpeningLeaveMaster> openingLeaveList = new ArrayList<EmployeeOpeningLeaveMaster>();
		for (Object[] leaveObj : previousLeaveTypeList) {

			EmployeeOpeningLeaveMaster employeeOpeningLeaveMaster = new EmployeeOpeningLeaveMaster();
			Long leavePeriodId = leaveObj[0] != null ? Long.parseLong(leaveObj[0].toString()) : null;
			Long leaveTypeMasterId = leaveObj[1] != null ? Long.parseLong(leaveObj[1].toString()) : null;
			String leaveName = leaveObj[2] != null ? (String) leaveObj[2] : null;
			BigDecimal totalleave = leaveObj[3] != null ? (new BigDecimal(leaveObj[4].toString())) : null;
			BigDecimal openleave = leaveObj[4] != null ? (new BigDecimal(leaveObj[4].toString())) : null;
			BigDecimal balanceleave = leaveObj[5] != null ? (new BigDecimal(leaveObj[5].toString())) : null;
			Long carryForwordLimit = leaveObj[6] != null ? Long.parseLong(leaveObj[6].toString()) : null;
			Long employeeId = leaveObj[7] != null ? Long.parseLong(leaveObj[7].toString()) : null;
			Long companyId = leaveObj[8] != null ? Long.parseLong(leaveObj[8].toString()) : null;

			employeeOpeningLeaveMaster.setTmsleavePeriod(tMSLeavePeriod);
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employeeOpeningLeaveMaster.setEmployee(employee);
			employeeOpeningLeaveMaster.setNoOfOpening(openleave);

			TMSLeaveTypeMaster TmsleaveTypeMaster = new TMSLeaveTypeMaster();
			TmsleaveTypeMaster.setLeaveId(leaveTypeMasterId);
			employeeOpeningLeaveMaster.setTmsleaveTypeMaster(TmsleaveTypeMaster);
			employeeOpeningLeaveMaster.setDateCreated(new Date());
			employeeOpeningLeaveMaster.setActiveStatus(StatusMessage.ACTIVE_CODE);
			employeeOpeningLeaveMaster.setStatus(StatusMessage.CARRYFORWORD_LEAVE_CODE);
			Company company = new Company();
			company.setCompanyId(companyId);
			employeeOpeningLeaveMaster.setCompany(company);
			employeeOpeningLeaveMaster.setUserId(tMSLeavePeriod.getUserId());
			// employeeOpeningLeaveMaster.setFromDate(fromDate);
			// employeeOpeningLeaveMaster.setToDate(toDate);
			// employeeOpeningLeaveMaster.setDays(day);
			employeeOpeningList.add(employeeOpeningLeaveMaster);
		}

		return openingLeaveList;
	}

	
	
	
	public List<EmployeeLeaveHistory> databaseObjectListToEmployeeLeaveHistory(
			List<Object[]> previousLeaveTypeList, List<EmployeeOpeningLeaveMaster> employeeOpeningList,
			TMSLeavePeriod tMSLeavePeriod) {
		List<EmployeeLeaveHistory> leaveHistoryList = new ArrayList<EmployeeLeaveHistory>();
		for (Object[] leaveObj : previousLeaveTypeList) {

			EmployeeLeaveHistory employeeLeaveHistoryMaster = new EmployeeLeaveHistory();
			Long leavePeriodId = leaveObj[0] != null ? Long.parseLong(leaveObj[0].toString()) : null;
			Long leaveTypeMasterId = leaveObj[1] != null ? Long.parseLong(leaveObj[1].toString()) : null;
			String leaveName = leaveObj[2] != null ? (String) leaveObj[2] : null;
			BigDecimal totalleave = leaveObj[3] != null ? (new BigDecimal(leaveObj[3].toString())) : null;
			BigDecimal openleave = leaveObj[4] != null ? (new BigDecimal(leaveObj[4].toString())) : null;
			BigDecimal balanceleave = leaveObj[5] != null ? (new BigDecimal(leaveObj[5].toString())) : null;
			Long carryForwordLimit = leaveObj[6] != null ? Long.parseLong(leaveObj[6].toString()) : null;
			Long employeeId = leaveObj[7] != null ? Long.parseLong(leaveObj[7].toString()) : null;
			Long companyId = leaveObj[8] != null ? Long.parseLong(leaveObj[8].toString()) : null;
			BigDecimal consumedleave = leaveObj[9] != null ? (new BigDecimal(leaveObj[9].toString()))
					: null;
			Long encashLimit = leaveObj[10] != null ? Long.parseLong(leaveObj[10].toString()) : null;
			BigDecimal encashLeave = leaveObj[11] != null ? (new BigDecimal(leaveObj[11].toString())) : null;
			Long indexDays = leaveObj[12] != null ? Long.parseLong(leaveObj[12].toString()) : null;
			String isLeaveInProbation = leaveObj[13] != null ? (getString(leaveObj[13])) : null;
			String nature = leaveObj[14] != null ? (getString(leaveObj[14])) : null;
			Long notice = leaveObj[15] != null ? Long.parseLong(leaveObj[15].toString()) : null;
			Long weekoffAsPlCount = leaveObj[16] != null ? Long.parseLong(leaveObj[16].toString()) : null;

			String leavePeriodName = leaveObj[17] != null ? (getString(leaveObj[17])) : null;
			Date employeeJoiningDate = leaveObj[18] != null ? (Date) (leaveObj[18]) : null;
			
			String leaveCalculationOnProbation = leaveObj[19] != null ? (getString(leaveObj[19])) : null;
			Integer probabtionDays = leaveObj[20] != null ? Integer.parseInt(leaveObj[20].toString())
					: null;
			
			Date leavePeriodStartDate = leaveObj[21] != null ? (Date) (leaveObj[21]) : null;
			Date leavePeriodEndDate = leaveObj[22] != null ? (Date) (leaveObj[22]) : null;
			String leaveMode = leaveObj[23] != null ? (String) leaveObj[23] : null;
			Long yearlyLimit = leaveObj[24] != null ? Long.parseLong(leaveObj[24].toString()) : null;
			
			int days =0;
			Date leaveCalculationDate = null;
			if (leaveCalculationOnProbation.equals("Y")) {
				leaveCalculationDate = employeeJoiningDate;
			} else {
				if(probabtionDays!=null)
				 days = probabtionDays;
				leaveCalculationDate = employeeJoiningDate;
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(leaveCalculationDate);
				cal.add(Calendar.DATE, days);
				leaveCalculationDate = cal.getTime();

			}
			if (leaveTypeMasterId != 7) {

				if (leaveCalculationDate.after(leavePeriodStartDate)
						|| leaveCalculationDate.compareTo(leavePeriodStartDate) == 0) {

					long timeDiff = Math.abs(leavePeriodEndDate.getTime() - leaveCalculationDate.getTime());
					double empJoinedDays = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;

					long leavePeriodTimeDiff = Math.abs(leavePeriodEndDate.getTime() - leavePeriodStartDate.getTime());
					double leavePeriodTimeDiffDays = Math.ceil(leavePeriodTimeDiff / (1000 * 3600 * 24)) + 1;

					// * Date 08/08/2019 change balance summury accroding to opening leave for
					// employee joining date after leave period date according to neelesh sir**/ *//
					// totalleave=(new BigDecimal(empJoinedDays).multiply(new
					// BigDecimal(totalleave)).divide(new BigDecimal(leavePeriodTimeDiffDays),
					// 2,RoundingMode.CEILING)).longValue();
					totalleave = (new BigDecimal(empJoinedDays).multiply(new BigDecimal(yearlyLimit))
							.divide(new BigDecimal(leavePeriodTimeDiffDays), 2, RoundingMode.CEILING));
					// totalleave=totalleave.subtract(openingLeave);

					MathContext m = new MathContext(2);
					totalleave = totalleave.setScale(0, RoundingMode.HALF_UP);
					;
					

					if (leaveMode.equals("IN")) {
						// totalleave = new BigDecimal(1.0);
						BigDecimal totalLeave = new BigDecimal(1.0);
						long timeDiffIndex = Math.abs(new Date().getTime() - leaveCalculationDate.getTime());
						double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24)) + 1;

						BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays)).divide(new BigDecimal(indexDays), 2,
								RoundingMode.CEILING);
					
						if (indexdays.compareTo(totalleave) > 0) {
							totalleave = indexdays;
							Double d = new Double(Math.round(totalleave.doubleValue()));
							totalleave = BigDecimal.valueOf(d).add(totalLeave);
							if (yearlyLimit <= (totalleave.longValue())) {
								totalleave = BigDecimal.valueOf(yearlyLimit);
							}
						} else {
							totalleave.add(totalLeave);
						}

					} 
					if (leaveCalculationDate.after(new Date()) && leaveCalculationOnProbation.equals("N")) {
						totalleave = new BigDecimal(0);
						
					}

				} else {
					if (leaveMode.equals("IN")) {
						BigDecimal totalLeave = new BigDecimal(1.0);
						long timeDiffIndex = Math.abs(new Date().getTime() - leavePeriodStartDate.getTime());
						double empJoinedIndexDays = Math.ceil(timeDiffIndex / (1000 * 3600 * 24)) + 1;

						BigDecimal indexdays = (new BigDecimal(empJoinedIndexDays)).divide(new BigDecimal(indexDays), 2,
								RoundingMode.CEILING);

						if (indexdays.compareTo(totalleave) > 0) {
							totalleave = indexdays;
							Double d = new Double(Math.round(totalleave.doubleValue()));
							totalleave = BigDecimal.valueOf(d).add(totalLeave);
							// totalleave.add(totalLeave);
						} else {
							totalleave.add(totalLeave);
						}

					}
				}
			}
			balanceleave = totalleave.subtract(consumedleave);
			totalleave = totalleave.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			TMSLeavePeriod tmsLeavePeriod = new TMSLeavePeriod();
			tmsLeavePeriod.setLeavePeriodId(leavePeriodId) ;
			employeeLeaveHistoryMaster.setTmsleavePeriod(tmsLeavePeriod);
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employeeLeaveHistoryMaster.setEmployee(employee);
			
			TMSLeaveTypeMaster TmsleaveTypeMaster = new TMSLeaveTypeMaster();
			TmsleaveTypeMaster.setLeaveId(leaveTypeMasterId);
			employeeLeaveHistoryMaster.setTmsleaveTypeMaster(TmsleaveTypeMaster);
			employeeLeaveHistoryMaster.setTotalLeave(totalleave);
			employeeLeaveHistoryMaster.setBalancedLeave(balanceleave);
			employeeLeaveHistoryMaster.setConsumedLeave(consumedleave);
			employeeLeaveHistoryMaster.setCarryforwardLeave(openleave);
			employeeLeaveHistoryMaster.setEncashLeave(encashLeave);
			
			BigDecimal leaveExpiryBalance = new BigDecimal(0.0);
			leaveExpiryBalance = totalleave.subtract(consumedleave);
			BigDecimal leaveEncashData = new BigDecimal(0.0);
			BigDecimal leaveExpiryFinalData = new BigDecimal(0.0);
			if (nature.equals("EC")) {

				// BigDecimal leaveEncashLimit = new BigDecimal(0.0);
				if (leaveExpiryBalance.doubleValue() >= encashLimit.doubleValue()) {

					leaveEncashData = leaveExpiryBalance.subtract(BigDecimal.valueOf(encashLimit));
					if (leaveEncashData.doubleValue() > carryForwordLimit.doubleValue()) {

						leaveExpiryFinalData = leaveEncashData.subtract(BigDecimal.valueOf(carryForwordLimit));
					} else {
						leaveExpiryFinalData.equals(0.0);

					}
				}

				else {
					if (leaveExpiryBalance.doubleValue() <= encashLimit.doubleValue()) {
						leaveExpiryFinalData.equals(0.0);
					}
				}
			}

			else {

				if (leaveExpiryBalance.doubleValue() >= carryForwordLimit.doubleValue()) {

					leaveExpiryFinalData = leaveExpiryBalance.subtract(BigDecimal.valueOf(carryForwordLimit));
				} else {
					leaveExpiryFinalData.equals(0.0);

				}

			}

			
			
			employeeLeaveHistoryMaster.setExpiredLeave(leaveExpiryFinalData);
			
			
			employeeLeaveHistoryMaster.setDateCreated(new Date());
			
			employeeLeaveHistoryMaster.setUserId(tMSLeavePeriod.getUserId());

			leaveHistoryList.add(employeeLeaveHistoryMaster);
		}

		return leaveHistoryList;
	}
	
	
	public static ArrayList<Date> getDateBetweens(String startDate, String endDate){
        //2019-05-16   yyyy-mm-dd
        ArrayList<Date> dateList = new ArrayList<>();

        String startDateArr[] = startDate.split("-");
        int startYear = Integer.valueOf(startDateArr[0]);
        int startMonth = Integer.valueOf(startDateArr[1])-1;
        int startDay = Integer.valueOf(startDateArr[2]);

        Calendar startCal = Calendar.getInstance();
        startCal.set(startYear,startMonth,startDay);

        String endDateArr[] = endDate.split("-");
        int endYear = Integer.valueOf(endDateArr[0]);
        int endMonth = Integer.valueOf(endDateArr[1])-1;
        int endDay = Integer.valueOf(endDateArr[2]);

        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear,endMonth,endDay);

        while(startCal.before(endCal) || startCal.equals(endCal)){
            dateList.add(startCal.getTime());
            startCal.add(Calendar.DATE, 1);
        }

        for(Date date : dateList){
           
        }

        return dateList;
    }
	
	
	public List<TeamLeaveOnCalenderDTO> setEventsForCalendar(List<TeamLeaveOnCalenderDTO> teamLeaveOnCalenderDTOList ,Date frmDate) {
		
		
		List<DateWiseAttendanceLogDTO> dateWiseAttendanceLogDTOList = new ArrayList();
		if(frmDate != null) {
			dateWiseAttendanceLogDTOList = listDates(frmDate);
		}
		
		ArrayList<Date> datesBw = new ArrayList();
		for(TeamLeaveOnCalenderDTO teamLeaveOnCalenderDTO: teamLeaveOnCalenderDTOList) {
			datesBw.addAll(getDateBetweens(teamLeaveOnCalenderDTO.getFromDate() , teamLeaveOnCalenderDTO.getToDate()));
		}
		
		for(Date dt: datesBw) {
			for(int i=0;i<dateWiseAttendanceLogDTOList.size();i++) {
				if(dateWiseAttendanceLogDTOList.get(i).getStarts().equalsIgnoreCase(dateFormat.format(dt))) {
					dateWiseAttendanceLogDTOList.get(i).setStarts(dateFormat.format(dt));
					dateWiseAttendanceLogDTOList.get(i).setTitle("L");
					dateWiseAttendanceLogDTOList.get(i).setTitleValue("L");
				}
			}
		}
		
		teamLeaveOnCalenderDTOList.get(0).setEvents(dateWiseAttendanceLogDTOList);;
		return teamLeaveOnCalenderDTOList;
	}
    
	public List<DateWiseAttendanceLogDTO> listDates(Date frmDate) {
		List<DateWiseAttendanceLogDTO> dateWiseAttendanceLogDTOList = new ArrayList();
		Calendar cal = Calendar.getInstance();
		cal.setTime(frmDate);
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		int myMonth = cal.get(Calendar.MONTH);

		while (myMonth==cal.get(Calendar.MONTH)) {
		  DateWiseAttendanceLogDTO dateWiseAttendanceLogDTO = new DateWiseAttendanceLogDTO();
		  dateWiseAttendanceLogDTO.setTitle(" ");
		  dateWiseAttendanceLogDTO.setTitleValue(" ");
		  Date starts = cal.getTime();
		  dateWiseAttendanceLogDTO.setStart(starts);
		  dateWiseAttendanceLogDTO.setStarts(dateFormat.format(starts));
		  
		  dateWiseAttendanceLogDTOList.add(dateWiseAttendanceLogDTO);
		  cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return dateWiseAttendanceLogDTOList;
	}
	
}
