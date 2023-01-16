package com.csipl.tms.leave.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.leave.adaptor.LeaveEntryAdaptor;
import com.csipl.tms.leave.repository.EmployeeLeaveEarnRepository;
import com.csipl.tms.leave.repository.EmployeeLeavePaginationRepository;
import com.csipl.tms.leave.repository.LeaveEntryRepository;
import com.csipl.tms.model.leave.LeaveSearchDTO;

@Service
public class EmployeeLeavePaginationServiceImpl  implements EmployeeLeavePaginationService{
	@Autowired
	LeaveEntryRepository leaveEntryRepository;
	@Autowired
	EmployeeLeavePaginationRepository employeeLeavePaginationRepository;
	@Autowired
	EmployeeLeaveEarnRepository employeeLeaveEarnRepository;
	
	
	LeaveEntryAdaptor leaveEntryAdaptor = new LeaveEntryAdaptor();
	final static DateFormat formater1 = new SimpleDateFormat("MMM-yyyy");

	
	
	@Override
	public void getEntityCount(long companyId, EntityCountDTO entityCountDto) {
	
		entityCountDto.setCount(leaveEntryRepository.entitySearch(companyId));
		
	}
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> getPendingEmployeeLeavebyPagination(long companyId, LeaveSearchDTO leaveSearchDto) {
		// TODO Auto-generated method stub
		return employeeLeavePaginationRepository.getPendingEmployeeLeavebyPagination(companyId, leaveSearchDto);
	}
	@Override
	public void getPendingEntityCount(long companyId, EntityCountDTO entityCountDto) {
		entityCountDto.setCount(leaveEntryRepository.pendingEntitySearch(companyId));
		
	}
	@Override
	public List<TeamLeaveOnCalenderDTO> getLeaveAttendancePendingList(EmployeeSearchDTO employeeSearchDto, String processMonth) {
		
		System.out.println("processMonth>>>>>"+processMonth);
		Date fromDate=getDateForProcessMonth(processMonth);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
  		month=month+1;
  		boolean flag=true;
		List<Object[]> leaveAttendancePendingList =employeeLeavePaginationRepository.getLeaveAttendancePendingList(employeeSearchDto,flag,String.valueOf(month),String.valueOf(year));
		flag=false;
		List<Object[]> leaveAttendancePendingList1 =employeeLeavePaginationRepository.getLeaveAttendancePendingList(employeeSearchDto,flag,String.valueOf(month),String.valueOf(year));
		int size=leaveAttendancePendingList1.size();
		List<TeamLeaveOnCalenderDTO> leaveBalanceDtoList = leaveEntryAdaptor.leaveAttendancePendingObjListToUiDto(leaveAttendancePendingList,size);
  		return leaveBalanceDtoList;
	}
	public static Date getDateForProcessMonth(String processMonth) {

		Date date = null;
		try {
			date = formater1.parse(processMonth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}
	 public static Date getLastDateOfMonth(Date date){
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	        return cal.getTime();
	    }
	@Override
	public List<TeamLeaveOnCalenderDTO> getPendingEmployeeLeaveAndAR(EmployeeSearchDTO employeeSearchDto, String processMonth) {

		System.out.println("processMonth>>>>>"+processMonth);
		Date fromDate=getDateForProcessMonth(processMonth);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
  		month=month+1;
  		boolean flag=true;
		List<Object[]> leaveAttendancePendingList =employeeLeaveEarnRepository.getpayrollLeaveAndAr(employeeSearchDto.getCompanyId(),String.valueOf(month),String.valueOf(year));
			//	employeeLeavePaginationRepository.getLeaveAttendancePendingList(employeeSearchDto,flag,String.valueOf(month),String.valueOf(year));
		flag=false;
		List<Object[]> leaveAttendancePendingList1 =employeeLeavePaginationRepository.getLeaveAttendancePendingList(employeeSearchDto,flag,String.valueOf(month),String.valueOf(year));
		int size=leaveAttendancePendingList.size();
		List<TeamLeaveOnCalenderDTO> leaveBalanceDtoList = leaveEntryAdaptor.leaveAttendancePendingObjListToUiDto(leaveAttendancePendingList,size);
  		return leaveBalanceDtoList;
	}
}
