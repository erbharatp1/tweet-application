package com.csipl.tms.leave.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.dto.leave.LeaveBalanceDTO;
import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.dto.leave.LeaveEntryDTO;
import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeaveEntry;

public interface EmployeeLeaveService {

	public TMSLeaveEntry saveLeaveEntry(TMSLeaveEntry leaveEntry, LeaveBalanceSummryDTO leaveBalanceSummryDto);

	public boolean validateLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummryDto) throws ParseException, ErrorHandling;

	public List<Object[]> leaveEntryList(Long companyId);

	public LeaveEntryDTO getLeaveEntry(Long leaveId);

	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId, String processMonth);

	public List<TMSLeaveEntry> getEmployeeLeaveEntry(Long employeeId);

	public List<TMSLeaveEntriesDatewise> getEmployeePendingLeaveEntryDateWise(Long employeeId);

	public List<TMSLeaveEntry> getEmployeePendingLeaveEntry(Long employeeId);

	public List<LeaveBalanceDTO> getEmployeeLeaveBalance(Long employeeId);

	public List<LeaveBalanceSummryDTO> getEmployeeLeaveBalanceSummry(Long employeeId, Long companyId);

	public List<TeamLeaveOnCalenderDTO> getTeamLeaveOnCalender(String employeeId, String currentDate);

	public String appliedLeaveDays(LeaveEntryDTO leaveEntryDto) throws ParseException, ErrorHandling;

	public List<TMSLeaveEntry> getEmployeeApprovedLeaveEntry(Long employeeId);

	public List<TMSLeaveEntry> getAllEmployeeApprovedLeaveEntry(Long companyId);

	public List<Object[]> getMonthEmployeeLeaveEntry(long companyId, long employeeId, String fromDate, String toDate);

	public List<TMSLeaveEntry> getEmployeeLeaveEntryListByDate(Date date);

	public LeaveEntryDTO leaveCount(Long companyId, Long employeeId);

	public void actonOnPendingLeaveAttendace(List<TeamLeaveOnCalenderDTO> actonLeaveAttendace);
	
	

	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	public LeaveEntryDTO getMyTeamLeaveCount(Long companyId, Long employeeId);

	public LeaveEntryDTO getAllTimeMyTeamLeaveCount(Long companyId, Long employeeId);
	
	public List<Object[]> getLeaveApprovalsPending(Long companyId, Long employeeId);

	public List<Object[]> getLeaveApprovalsNonPending(Long companyId, Long employeeId);
	
	public LeaveEntryDTO isPendingRequestLeaveAndARByMonth(int month,int year) ;

	public void updateById(TMSLeaveEntry tMSLeaveEntry);

	public Long getapprovependingByEmpId(Long companyId, Long employeeId);

	TeamLeaveOnCalenderDTO getTeamLeaveOnCalenderNew(Long employeeId, String processMonth);

	public	Long getapprovepending(Long companyId, Long leavePeriodId);
	
	public LeaveEntryDTO allEmployeeLeaveCount(Long companyId);

	public List<Object[]> getAllLeaveApprovalsPending(Long companyId, SearchDTO searcDto);
	
	public List<Object[]> getAllLeaveApprovalsNonPending(Long companyId, SearchDTO searcDto);
	
	public List<LeaveEntryDTO> getEmployeesAllTypeLeaveEntry(Long companyId,Long employeeId);


	public boolean validateBulkLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummary,
			List<String> validateBulkLeaveEntry) throws ParseException, ErrorHandling;

	public LeaveValidationResult appliedBulkLeaveDays(LeaveEntryDTO value, List<String> exceptionEmployeeCode) throws ParseException, ErrorHandling;

	public void getPendingLeaveReqCount(Long longCompanyId, EntityCountDTO entityCountDto);

	public void getNonPendingLeaveReqCount(Long longCompanyId, EntityCountDTO entityCountDto);

	public List<TMSLeaveEntry> getPendingLeaveReqbyPagination(Long employeeId, SearchDTO searcDto);
}
