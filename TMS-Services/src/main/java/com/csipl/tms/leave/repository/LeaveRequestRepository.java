package com.csipl.tms.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.model.leave.TMSLeaveEntry;

public interface LeaveRequestRepository {
 	
// 	@Query(nativeQuery = true,value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and le.status = 'PEN'"))
 	List<Object[]> getAllLeaveApprovalsPending(Long companyId, SearchDTO searcDto);
	
// 	@Query(nativeQuery = true, value = ("SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId=?1 and le.status != 'PEN'"))
	List<Object[]> getAllLeaveApprovalsNonPending(Long companyId, SearchDTO searcDto);

	List<TMSLeaveEntry> getPendingLeaveReqbyPagination(Long employeeId, SearchDTO searcDto);
	
}
