package com.csipl.tms.leave.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeaveEntry;

public interface LeaveEntriesDatewiseService {
	public TMSLeaveEntriesDatewise saveLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummryDto,TMSLeaveEntry leaveEntry) throws ParseException ;
	public void updateLeaveEntry(TMSLeaveEntry leaveEntry) ;
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntry(Long employeeId,String processMonth);
	public List<TMSLeaveEntriesDatewise> getEmployeePendingLeaveEntryDateWise(Long employeeId);
	public List<TMSLeaveEntriesDatewise> findAllByLeaveFromToSandwitchDate(Date leaveFromToSandwitchDate);
	public List<TMSLeaveEntriesDatewise> getAllLeaveEntryDateWise(Long employeeId);
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntryNew(Long employeeId,String processMonth);
	public List<Date> getEmployeeLeaveDate(Long employeeId, Date startDate, Date endDate);
	public List<TMSLeaveEntriesDatewise> getPenAprLeaveEntryDateWise(Long employeeId);
	
}
