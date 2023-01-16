package com.csipl.tms.leave.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;
import com.csipl.tms.model.leave.TMSLeaveEntry;

@Repository
public class LeaveRequestRepositoryImpl implements LeaveRequestRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getAllLeaveApprovalsPending(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId =:companyId and le.status = 'PEN' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;
	}

	/**
	 * @param employeeSearchDto
	 * @param sb
	 */
	private void buildCondtion(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeCode() != null && !searcDto.getEmployeeCode().equals("")
				&& !searcDto.getEmployeeCode().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeCode().equalsIgnoreCase("undefined")) {

			sb.append(" and ecd.employeeCode='" + searcDto.getEmployeeCode() + "'  ");
		}

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( ecd.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  ecd.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR ecd.employeeCode LIKE '%" + searcDto.getEmployeeName()
					+ "%'" + "OR dept.departmentName LIKE '%" + searcDto.getEmployeeName() + "%' )");

		}
		if (searcDto.getDepartment() != null && !searcDto.getDepartment().equals("")
				&& !searcDto.getDepartment().equalsIgnoreCase("null")
				&& !searcDto.getDepartment().equalsIgnoreCase("undefined")) {

			sb.append(" and dept.departmentName LIKE '" + searcDto.getDepartment() + "%' ");
		}

		if (searcDto.getDesignation() != null && !searcDto.getDesignation().equals("")
				&& !searcDto.getDesignation().equalsIgnoreCase("null")
				&& !searcDto.getDesignation().equalsIgnoreCase("undefined")) {

			sb.append(" and dept.designationName LIKE '" + searcDto.getDesignation() + "%' ");
		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		System.out.println("Active - " + active);
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by le.leaveId desc ");

		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by le.dateCreated asc ");
			} else {
				sb.append(" order by le.dateCreated desc ");
			}
		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ecd.firstName asc ");
			} else {
				sb.append(" order by ecd.firstName desc ");
			}

		} else {

			sb.append("order by le.leaveId desc");
		}
	}

	@Override
	public List<Object[]> getAllLeaveApprovalsNonPending(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,le.leaveId,le.dateCreated,le.leaveTypeId,le.actionableDate,le.fromDate,le.toDate,le.days,le.status,le.employeeId,le.employeeRemark,le.halfFullDay,le.userId ,deg.designationName ,ecd.employeeLogoPath,tm.leaveName,le.approvalRemark FROM TMSLeaveEntries le JOIN Employee ecd JOIN Department dept JOIN Designation deg JOIN TMSLeaveTypeMaster tm JOIN TMSLeaveType tl on  tl.leaveTypeId = le.leaveTypeId  and  tm.leaveId=tl.leaveTypeMasterId and ecd.employeeId=le.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where le.companyId= :companyId and le.status != 'PEN' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;
	}

	@Override
	public List<TMSLeaveEntry> getPendingLeaveReqbyPagination(Long employeeId, SearchDTO searcDto) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from TMSLeaveEntries tms where employeeId= :employeeId AND status='PEN'\r\n" + 
				  " and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionforPendingLeave(sb, searcDto);
		sortSearchQueryforPendingLeave(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<TMSLeaveEntry> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;
	}
	private void buildCondtionforPendingLeave(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeCode() != null && !searcDto.getEmployeeCode().equals("")
				&& !searcDto.getEmployeeCode().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeCode().equalsIgnoreCase("undefined")) {

			sb.append(" and tms.fromDate='" + searcDto.getEmployeeCode() + "'  ");
		}

	}
	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQueryforPendingLeave(StringBuilder sb, String active, String sortDirection) {
		System.out.println("Active - " + active);
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by tms.fromDate desc ");

		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tms.dateCreated asc ");
			} else {
				sb.append(" order by tms.dateCreated desc ");
			}
		}  else {

			sb.append("order by tms.fromDate desc");
		}
	}

}
