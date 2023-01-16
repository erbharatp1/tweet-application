package com.csipl.tms.attendanceregularizationrequest.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;

public interface ARRequestPagingAndFilterRepository {

	List<Object[]> findARRequestPagedAndFilterResult(Long companyId, Boolean status, SearchDTO searcDto);

	List<Object[]> getAllEmpApprovalsPending(Long companyId, SearchDTO searcDto);

	List<Object[]> getAllApprovalsNonPending(Long companyId, Long employeeId, SearchDTO searcDto);

	List<Object[]> findAllMassCommunications(Long companyId, SearchDTO searcDto);

	List<Object[]> getTeamsAbsentListByDate(Long companyId, Long employeeId, String selectDate, SearchDTO searcDto);

	List<Object[]> getARRequestDetailsbyPagination(Long empId, SearchDTO searchDTO);

	List<Object[]> getARPendingRequestDetailsbyPagination(Long empId, SearchDTO searcDto);

	List<Object[]> getTeamsPresentListByDate(Long companyId, Long employeeId, String selectDate, SearchDTO searcDto);

	List<Object[]> getTeamsLateComersListByDate(Long companyId, Long employeeId, String selectDate, SearchDTO searcDto);

	List<Object[]> getAllLateComersListByDate(Long companyId, String date, SearchDTO searcDto);

	List<Object[]> getAllAbsentListByDate(Long companyId, String date, SearchDTO searcDto);

	List<Object[]> getAllPresentListByDate(Long companyId, String date, SearchDTO searcDto);

}
