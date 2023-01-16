package com.csipl.hrms.service.employee.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class EmployeeLetterApprovalsRepositoryImpl implements EmployeeLetterApprovalsRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> findPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		int offset = 0;
		int limit = 0;

		sb.append(
				" SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId "
						+ "LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId "
						+ "WHERE el.activeStatus='AC' AND e.companyId=:companyId AND ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId)  AND ah.levels ='L1' AND elt.status='PEN' AND el.dateUpdate IS NOT null AND elt.employeeLetterTransactionId IS NOT NULL ");

		offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		limit = searcDto.getLimit();

		sb.append(
				" UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el "
						+ "LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId "
						+ "AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=:companyId  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId)  AND ah.levels ='L2' AND elt.status='PEN' AND elt.employeeLetterTransactionId IS NOT NULL AND elt.employeeLetterTransactionId IS NOT NULL ");

		offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		limit = searcDto.getLimit();

		sb.append(
				" UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el "
						+ "LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId "
						+ "AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=:companyId AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId)  AND ah.levels ='L3' AND elt.status='PEN' AND elt.employeeLetterTransactionId IS NOT NULL ");

		offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		limit = searcDto.getLimit();

		sb.append(
				" UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status, elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el "
						+ "LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId "
						+ "AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=:companyId  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId)  AND ah.levels ='L4' AND elt.status='PEN' AND elt.employeeLetterTransactionId IS NOT NULL ");

		offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		limit = searcDto.getLimit();

		sb.append(
				" UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el "
						+ "LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId "
						+ "AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=:companyId  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId)  AND ah.levels ='L5' AND elt.status='PEN' AND elt.employeeLetterTransactionId IS NOT NULL ");

		offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		limit = searcDto.getLimit();

		String search = sb.toString();
		System.out.println("findPendingLetterList Query Start-----------------------");
		System.out.println(search);
		System.out.println("findPendingLetterList Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("employeeId", employeeId);
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

			sb.append(" and e.employeeCode='" + searcDto.getEmployeeCode() + "'  ");
		}

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( e.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  e.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR e.employeeCode LIKE '%" + searcDto.getEmployeeName()
					+ "%'" + "OR dep.departmentName LIKE '%" + searcDto.getEmployeeName() + "%' )");

		}
		if (searcDto.getDepartment() != null && !searcDto.getDepartment().equals("")
				&& !searcDto.getDepartment().equalsIgnoreCase("null")
				&& !searcDto.getDepartment().equalsIgnoreCase("undefined")) {

			sb.append(" and dep.departmentName LIKE '" + searcDto.getDepartment() + "%' ");
		}

		if (searcDto.getDesignation() != null && !searcDto.getDesignation().equals("")
				&& !searcDto.getDesignation().equalsIgnoreCase("null")
				&& !searcDto.getDesignation().equalsIgnoreCase("undefined")) {

			sb.append(" and des.designationName LIKE '" + searcDto.getDesignation() + "%' ");
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

			sb.append(" order by el.empLetterId desc ");

		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by e.firstName asc ");
			} else {
				sb.append(" order by e.firstName desc ");
			}

		} else {

			sb.append("order by el.empLetterId desc");
		}
	}

	@Override
	public List<Object[]> findNonPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status AS Elt_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=:employeeId) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status ,concat(emp.firstName,' ',emp.lastName) AS approvedBy ,elt.dateUpdate ,elt.approvalRemarks,desi.designationName as approvedByDesignation FROM EmployeeLettersTransaction elt "
						+ "LEFT JOIN EmployeeLetter el ON el.empLetterId=elt.employeeLetterId  LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId "
						+ "LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId AND ah.levels=elt.levels AND elt.designationId=ah.designationId LEFT JOIN Employee emp ON emp.employeeId=elt.approvalId  LEFT JOIN Designation desi ON desi.designationId=elt.designationId  WHERE e.companyId=:companyId AND elt.designationId=(SELECT emp.designationId FROM Employee emp WHERE emp.employeeId=:employeeId) AND elt.status !='PEN' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("findNonPendingLetterList Query Start-----------------------");
		System.out.println(search);
		System.out.println("findNonPendingLetterList Query End-----------------------");
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
