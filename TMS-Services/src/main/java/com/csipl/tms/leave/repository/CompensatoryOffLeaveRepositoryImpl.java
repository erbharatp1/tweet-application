package com.csipl.tms.leave.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class CompensatoryOffLeaveRepositoryImpl implements CompensatoryOffLeaveRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getAllEmpApprovalsPendingCompOff(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId  ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where co.companyId=:companyId and co.status = 'PEN' ");

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

			sb.append(" and deg.designationName LIKE '" + searcDto.getDesignation() + "%' ");
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

			sb.append(" order by co.tmsCompensantoryOffId desc ");

		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by co.dateCreated asc ");
			} else {
				sb.append(" order by co.dateCreated desc ");
			}
		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ecd.firstName asc ");
			} else {
				sb.append(" order by ecd.firstName desc ");
			}

		} else if (active != null && (active.trim().equals("fromDate"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by co.fromDate asc ");
			} else {
				sb.append(" order by co.fromDate desc ");
			}

		} else if (active != null && (active.trim().equals("days"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by co.days asc ");
			} else {
				sb.append(" order by co.days desc ");
			}

		} else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by co.status asc ");
			} else {
				sb.append(" order by co.status desc ");
			}

		} else {

			sb.append("order by co.tmsCompensantoryOffId desc");
		}
	}

	@Override
	public List<Object[]> getApprovalsNonPendingCompOff(Long companyId, Long employeeId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,co.tmsCompensantoryOffId,co.dateCreated,co.fromDate,co.toDate,co.days,co.status,co.employeeId,co.remark,co.userId  ,deg.designationName ,ecd.employeeLogoPath FROM TMSCompensantoryOff co JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=co.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where co.companyId=:companyId and  ecd.ReportingToEmployee=:employeeId and co.status != 'PEN' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("ApprovalsNonPendingCompOff Query Start-----------------------");
		System.out.println(search);
		System.out.println("ApprovalsNonPendingCompOff Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;
	}

}
