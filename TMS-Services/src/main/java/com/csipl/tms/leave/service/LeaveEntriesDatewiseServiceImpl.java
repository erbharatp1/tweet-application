package com.csipl.tms.leave.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.tms.dto.leave.LeaveBalanceSummryDTO;
import com.csipl.tms.dto.leave.SandwitchIssueResolver;
import com.csipl.tms.leave.repository.LeaveEntriesDatewiseRepository;
import com.csipl.tms.leave.repository.LeaveEntryRepository;
import com.csipl.tms.model.leave.TMSLeaveEntriesDatewise;
import com.csipl.tms.model.leave.TMSLeaveEntry;

@Service("leaveEntriesDatewiseService")
public class LeaveEntriesDatewiseServiceImpl implements LeaveEntriesDatewiseService {
	@Autowired
	LeaveEntriesDatewiseRepository leaveEntriesDatewiseRepository;

	@Autowired
	LeaveEntryRepository leaveEntriesRepository;

	@Override
	public TMSLeaveEntriesDatewise saveLeaveEntry(LeaveBalanceSummryDTO leaveBalanceSummryDto, TMSLeaveEntry leaveEntry)
			throws ParseException {
		List<TMSLeaveEntriesDatewise> leaveEntriesDatewiseList = new ArrayList<TMSLeaveEntriesDatewise>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("leaveEntry.id...." + leaveEntry.getLeaveId());

		for (String appliedDate : leaveBalanceSummryDto.getAppliedLeaveEnteriesDates()) {
			TMSLeaveEntriesDatewise tmsLeaveEntriesDatewise = new TMSLeaveEntriesDatewise();
			tmsLeaveEntriesDatewise.setTmsleaveEntry(leaveEntry);
			tmsLeaveEntriesDatewise.setCompanyId(leaveBalanceSummryDto.getCompanyId());
			tmsLeaveEntriesDatewise.setLeaveDate(formatter.parse(appliedDate));
			tmsLeaveEntriesDatewise.setLeaveStatus(leaveEntry.getStatus());
			tmsLeaveEntriesDatewise.setEmployeeId(leaveBalanceSummryDto.getEmployeeId());

			tmsLeaveEntriesDatewise.setLeaveNature(leaveBalanceSummryDto.getNature());
			tmsLeaveEntriesDatewise.setDay(new BigDecimal(1L));
			tmsLeaveEntriesDatewise.setHalfFullDay(leaveEntry.getHalfFullDay());
			System.out.println(appliedDate);
			leaveEntriesDatewiseList.add(tmsLeaveEntriesDatewise);
		}

		for (SandwitchIssueResolver sandwitchIssueResolver : leaveBalanceSummryDto.getSandwichDatesFromSetObj()) {
			TMSLeaveEntriesDatewise tmsLeaveEntriesDatewise = new TMSLeaveEntriesDatewise();
			tmsLeaveEntriesDatewise.setTmsleaveEntry(leaveEntry);
			tmsLeaveEntriesDatewise.setCompanyId(leaveBalanceSummryDto.getCompanyId());
			tmsLeaveEntriesDatewise.setLeaveDate(sandwitchIssueResolver.getSandwitchDate());
			tmsLeaveEntriesDatewise.setLeaveStatus(leaveEntry.getStatus());
			tmsLeaveEntriesDatewise.setEmployeeId(leaveBalanceSummryDto.getEmployeeId());
			tmsLeaveEntriesDatewise.setLeaveNature(sandwitchIssueResolver.getLeaveNature());
			tmsLeaveEntriesDatewise.setLeaveFromToSandwitchDate(sandwitchIssueResolver.getLeavefromtoDate());
			tmsLeaveEntriesDatewise.setDay(new BigDecimal(1L));
			tmsLeaveEntriesDatewise.setHalfFullDay(leaveEntry.getHalfFullDay());
			System.out.println(sandwitchIssueResolver.getLeavefromtoDate());
			leaveEntriesDatewiseList.add(tmsLeaveEntriesDatewise);
		}
		for (SandwitchIssueResolver sandwitchIssueResolver : leaveBalanceSummryDto.getSandwichDatesToSetObj()) {
			TMSLeaveEntriesDatewise tmsLeaveEntriesDatewise = new TMSLeaveEntriesDatewise();
			tmsLeaveEntriesDatewise.setTmsleaveEntry(leaveEntry);
			tmsLeaveEntriesDatewise.setCompanyId(leaveBalanceSummryDto.getCompanyId());
			tmsLeaveEntriesDatewise.setLeaveDate(sandwitchIssueResolver.getSandwitchDate());
			tmsLeaveEntriesDatewise.setLeaveStatus(leaveEntry.getStatus());
			tmsLeaveEntriesDatewise.setEmployeeId(leaveBalanceSummryDto.getEmployeeId());
			tmsLeaveEntriesDatewise.setLeaveNature(sandwitchIssueResolver.getLeaveNature());
			tmsLeaveEntriesDatewise.setLeaveFromToSandwitchDate(sandwitchIssueResolver.getLeavefromtoDate());
			tmsLeaveEntriesDatewise.setDay(new BigDecimal(1L));
			tmsLeaveEntriesDatewise.setHalfFullDay(leaveEntry.getHalfFullDay());
			System.out.println(sandwitchIssueResolver.getLeavefromtoDate());
			leaveEntriesDatewiseList.add(tmsLeaveEntriesDatewise);
		}
		leaveEntriesDatewiseRepository.save(leaveEntriesDatewiseList);
		// System.out.println("$$$$$$$");
		return null;
	}

	@Override
	public void updateLeaveEntry(TMSLeaveEntry leaveEntry) {
		Boolean flag = false;
		List<TMSLeaveEntriesDatewise> leaveEntriesDatewise = findAllByLeaveFromToSandwitchDate(
				DateUtils.getDateByDate(leaveEntry.getFromDate()));

		leaveEntriesDatewiseRepository.updateLeaveDatewiseEntry(leaveEntry.getStatus(), leaveEntry.getLeaveId());
		int days = leaveEntriesDatewiseRepository.updateLeaveDateSandwizewiseEntry(leaveEntry.getStatus(),
				leaveEntry.getFromDate(), leaveEntry.getEmployeeId());

		for (TMSLeaveEntriesDatewise obj : leaveEntriesDatewise) {
			flag = obj.getLeaveStatus().trim().equalsIgnoreCase("PEN");
		}

		/*
		 * leaveEntriesDatewise.stream() .filter(object
		 * ->"PEN".equalsIgnoreCase(object.getLeaveStatus()));
		 * 
		 * leaveEntriesDatewise.stream() .allMatch(status ->
		 * "CEN".equalsIgnoreCase(status.getLeaveStatus()));
		 * leaveEntriesDatewise.forEach(obj -> {
		 * 
		 * });
		 */

		if (flag) {
			List<TMSLeaveEntry> leaveEntryList = leaveEntriesRepository.getEmployeePendingLeaveEntryForDaysUpdate(
					leaveEntriesDatewise.get(0).getTmsleaveEntry().getLeaveId());
			leaveEntriesRepository.updateLeaveEntryDay(leaveEntryList.get(0).getDays().subtract(new BigDecimal(days)),
					leaveEntriesDatewise.get(0).getTmsleaveEntry().getLeaveId());
		}

	}

	@Override
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntry(Long employeeId, String processMonth) {
		// TODO Auto-generated method stub
		return leaveEntriesDatewiseRepository.getEmployeeLeaveEntry(employeeId,
				DateUtils.getMonthForProcessMonth(processMonth));
	}

	@Override
	public List<TMSLeaveEntriesDatewise> getEmployeePendingLeaveEntryDateWise(Long employeeId) {
		// TODO Auto-generated method stub
		return leaveEntriesDatewiseRepository.getEmployeePendingLeaveEntryDateWise(employeeId);

	}

	@Override
	public List<TMSLeaveEntriesDatewise> findAllByLeaveFromToSandwitchDate(Date leaveFromToSandwitchDate) {
		// TODO Auto-generated method stub
		return leaveEntriesDatewiseRepository.findAllByLeaveFromToSandwitchDate(leaveFromToSandwitchDate);
	}

	@Override
	public List<TMSLeaveEntriesDatewise> getAllLeaveEntryDateWise(Long employeeId) {
		// TODO Auto-generated method stub
		return leaveEntriesDatewiseRepository.getAllLeaveEntryDateWise(employeeId);
	}

	@Override
	public List<TMSLeaveEntriesDatewise> getEmployeeLeaveEntryNew(Long employeeId, String processMonth) {
		return leaveEntriesDatewiseRepository.getEmployeeLeaveEntryNew(employeeId,
				DateUtils.getMonthForProcessMonth(processMonth));
	}

	@Override
	public List<Date> getEmployeeLeaveDate(Long employeeId, Date startDate, Date endDate) {
		List<TMSLeaveEntriesDatewise> dateList = leaveEntriesDatewiseRepository.getEmployeeLeaveDate(employeeId,
				startDate, endDate);
		List<Date> newDateList = new ArrayList<>();

		for (TMSLeaveEntriesDatewise date : dateList) {

			// Date newdate = date[0] != null ? (Date) date[0] : null;
			newDateList.add(date.getLeaveDate());
		}
		return newDateList;
	}
	
	public List<TMSLeaveEntriesDatewise> getPenAprLeaveEntryDateWise(Long employeeId){
		return leaveEntriesDatewiseRepository.getPenAprLeaveEntryDateWise(employeeId);
	}
}
