package com.csipl.tms.leave.repository;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.tms.model.leave.LeaveSearchDTO;

public interface EmployeeLeavePaginationRepository {
	public List<Object[]> getPendingEmployeeLeavebyPagination( Long companyId, LeaveSearchDTO leaveSearchDto );
 	List<Object[]> getLeaveAttendancePendingList(EmployeeSearchDTO employeeSearchDto, boolean flag,String month, String year);
}
