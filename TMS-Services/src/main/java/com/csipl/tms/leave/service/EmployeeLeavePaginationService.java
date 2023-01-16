package com.csipl.tms.leave.service;

import java.util.List;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
 import com.csipl.tms.dto.leave.TeamLeaveOnCalenderDTO;
import com.csipl.tms.model.leave.LeaveSearchDTO;

public interface EmployeeLeavePaginationService {
	public void getEntityCount(long companyId, EntityCountDTO entityCountDto);
	public void getPendingEntityCount(long companyId, EntityCountDTO entityCountDto);
	public List<Object[]> getPendingEmployeeLeavebyPagination( long companyId, LeaveSearchDTO leaveSearchDto );
 
	List<TeamLeaveOnCalenderDTO> getLeaveAttendancePendingList(EmployeeSearchDTO employeeSearchDto,
			String processMonth);
	
	public List<TeamLeaveOnCalenderDTO> getPendingEmployeeLeaveAndAR( EmployeeSearchDTO employeeSearchDto,
			String processMonth );
}
