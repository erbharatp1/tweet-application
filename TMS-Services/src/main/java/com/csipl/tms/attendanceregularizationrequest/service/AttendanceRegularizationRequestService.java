package com.csipl.tms.attendanceregularizationrequest.service;

import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.tms.dto.attendanceregularizationrequest.AttendanceRegularizationRequestDTO;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.model.attendanceregularizationrequest.AttendanceRegularizationRequest;

public interface AttendanceRegularizationRequestService {

	public AttendanceRegularizationRequest save(AttendanceRegularizationRequest attendanceRegularizationRequest)
			throws ErrorHandling;

	public List<Object[]> getAllARRequest(Long companyId);

	public AttendanceRegularizationRequest getARRequest(Long arId);

	public List<AttendanceRegularizationRequest> getEmployeePendingARRequest(Long employeeId);

	public List<AttendanceRegularizationRequest> getEmployeeApprovedARRequest(Long employeeId, Long companyId,
			String processMonth);

	public List<AttendanceRegularizationRequest> getEmployeeARRequest(Long employeeId);

	public List<Object[]> getARRequestforMonth(Long companyId, Long employeeId, String fromDate, String toDate);

	public AttendanceRegularizationRequestDTO arCount(Long companyId, Long employeeId);

	public List<Object[]> getAllPendingARRequest(Long companyId);

	public void getPendingARCount(Long longCompanyId, EntityCountDTO employeeCountDto);

	public void getNonPendingARCount(Long longCompanyId, EntityCountDTO entityCountDto);

	public AttendanceRegularizationRequestDTO countMyTeamPendingARCount(Long employeeId, Long companyId);

	public AttendanceRegularizationRequestDTO countAllTimeMyTeamPendingARCount(Long employeeId, Long companyId);

	public List<Object[]> getAllApprovalsPending(Long companyId, Long employeeId);

	public List<Object[]> getAllEmpApprovalsPending(Long companyId, SearchDTO searcDto);

	public List<Object[]> getAllApprovalsNonPending(Long companyId, Long employeeId, SearchDTO searcDto);

	public void updateById(AttendanceRegularizationRequest attendanceRegularizationRequest);

	public List<AttendanceRegularizationRequest> getEmployeeARRequestList(Long employeeId);

	public AttendanceRegularizationRequestDTO allEmployeePendingARCount(Long companyId);

	public AttendanceRegularizationRequestDTO allTimeAllEmployeePendingARCount(Long companyId);

	public List<AttendanceRegularizationRequest> getPenAprARRequestList(Long employeeId);

	public void getARCountPending(Long longCompanyId, EntityCountDTO entityCountDto);

	public void getARCountNonPending(Long longCompanyId, EntityCountDTO entityCountDto);
	
	public List<Object[]> getARRequestDetailsbyPagination(Long empId, SearchDTO searchDTO);

	public List<Object[]> getARPendingRequestDetailsbyPagination(Long empId, SearchDTO searcDto);

}
