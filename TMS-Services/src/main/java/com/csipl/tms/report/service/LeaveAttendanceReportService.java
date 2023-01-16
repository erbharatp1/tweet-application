package com.csipl.tms.report.service;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.tms.dto.attendancelog.AttendanceLogDTO;
import com.csipl.tms.dto.monthattendance.EmployeeLeaveGraphDTO;
import com.csipl.tms.model.leave.TMSLeavePeriod;

public interface LeaveAttendanceReportService {

	public AttendanceLogDTO getAttendanceReportMonthWise(Long employeeId, String processMonth, Long companyId) throws ErrorHandling;

	public List<AttendanceLogDTO> getLateComersReport(Long companyId, Long empId, List<Long> dptList, Date fDate,
			Date tDate);

	public List<EmployeeLeaveGraphDTO> getEmployeeOnLeavePercentage(Long companyId);

	public List<EmployeeLeaveGraphDTO> getLeaveTakenByLeaveType(Long companyId);

	public List<EmployeeLeaveGraphDTO> getEmployeeOnAbsent(Long companyId);

	public List<EmployeeLeaveGraphDTO> getEmployeeFrequentLeaveTaker(Long companyId);

	public List<AttendanceDTO> getLeaveEntitlementAndBalanceSummaryReport(Long companyId, Long employeeId,
			List<Long> departmentId, String[] typesOfLeavesId, Long leavePeriodId, String[] typesOfLeavesIdEntitlement,
			List<TMSLeavePeriod> leavePeriodList);

	public String[] getTypesOfLeaves(Long companyId);

	public List<TMSLeavePeriod> getLeavePeriod(Long companyId);

	// public TMSLeavePeriod getLeavePeriodId(Long companyId);

	public List<Object[]> getLeaveRequestSummaryReport(Long companyId, Long employeeId, List<Long> departmentList,
			Date fDate, Date tDate);

	public List<Object[]> getCompOffReqSummaryReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate);

	public List<Object[]> getEarlyComersReport(Long companyId, Long employeeId, List<Long> departmentIds, Date fDate,
			Date tDate);

	public List<Object[]> getWorkedOnHolidaysReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate);

	public List<Object[]> getWorkedOnWeekOffReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate);

	public List<Object[]> getEarlyLeaversReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fromDate, Date toDate);

	public List<Object[]> getArRequestReport(Long companyId, Long employeeId, List<Long> departmentList, Date fDate,
			Date tDate);

	public List<Object[]> getOverTimeDayWiseReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate);

	public List<Object[]> getOverTimeMonthWiseReport(Long companyId, int pMonth, int pYear, Long employeeId,
			List<Long> departmentIds);

	public List<Object[]> getMissingPunchRecordReport(Long companyId, Long employeeId, List<Long> departmentList,
			Date startDate, Date endDate);

	public List<Object[]> getAttendanceLogsSumReport(Long companyId, Long employeeId, List<Long> departmentIds,
			Date fDate, Date tDate);

	public String[] getTypesOfEncashedLeaves(Long companyId);

	public List<AttendanceDTO> getLeaveEncashedSummaryReport(Long companyId, Long employeeId, List<Long> departmentList,
			String[] typesOfLeavesId, Long leavePeriodId);

	public String[] getTypesOfLeavesEntitlement(Long companyId);

	public List<TMSLeavePeriod> getEncashedLeavePeriod(Long companyId);

	public TMSLeavePeriod getEncashedLeavePeriodId(Long companyId, Long leavePeriodId);

	public String[] getTypesOfExpiryLeaves(Long companyId);

	public List<AttendanceDTO> getLeaveExpirySummaryReport(Long companyId, Long employeeId, List<Long> departmentList,
			String[] typesOfLeavesExpiryId, Long leavePeriodId);

	public List<Object[]> getshiftScheduleSummary(Long companyId, Long employeeId, List<Long> departmentIds);

}
