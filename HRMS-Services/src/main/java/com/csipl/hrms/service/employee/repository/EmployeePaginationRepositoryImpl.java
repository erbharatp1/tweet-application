package com.csipl.hrms.service.employee.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;

@Repository
public class EmployeePaginationRepositoryImpl implements EmployeePaginationRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getAllEmployeeDetails(Long companyId, EmployeeSearchDTO employeeSearchDTO) {
		// TODO Auto-generated method stub
		System.out.println(
				"getActiveStatus-----------------------------------------" + employeeSearchDTO.getActiveStaus());
		StringBuilder sb = new StringBuilder();

		sb.append(
				"SELECT emp.employeeId, emp.employeeCode, CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)),LOWER(SUBSTRING(emp.firstName ,2))), CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName ,2))) , ");
		sb.append(
				"gd.gradesName, dept.departmentName,  design.designationName, emp.dateOfJoining ,emp.endDate, emp.employeeLogoPath , "
						+ " emp.dateOfBirth, CONCAT(UPPER(SUBSTRING(emp1.firstName ,1,1)),LOWER(SUBSTRING(emp1.firstName ,2))) AS reportingFirstName, CONCAT(UPPER(SUBSTRING(emp1.lastName ,1,1)),LOWER(SUBSTRING(emp1.lastName ,2))) AS reportingLastName , emp.endDate as resignationOn");
		sb.append(" FROM  Employee emp LEFT JOIN Employee emp1 ON emp1.employeeId=emp.ReportingToEmployee");
		sb.append(
				" JOIN Grades gd ON gd.gradesId = emp.gradesId  JOIN Department dept ON emp.departmentId = dept.departmentId ");
		sb.append(
				"JOIN Designation design  ON  emp.designationId = design.designationId  WHERE emp.companyId =:companyId ");
//		sb.append("Group By emp.employeeId ");

//		Group By emp.employeeId
		String active = employeeSearchDTO.getActive();
		String sortDirection = employeeSearchDTO.getSortDirection();
		int offset = employeeSearchDTO.getOffset();

		buildCondtion(employeeSearchDTO, sb);

		sortSearchQuery(sb, active, sortDirection);

		int limit = employeeSearchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------" + search);
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
	private void buildCondtion(EmployeeSearchDTO employeeSearchDto, StringBuilder sb) {
		if (employeeSearchDto.getEmployeeCode() != null && !employeeSearchDto.getEmployeeCode().equals("")
				&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("null")
				&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("undefined")) {

			sb.append(" and emp.employeeCode='" + employeeSearchDto.getEmployeeCode() + "'  ");
		}

		if (employeeSearchDto.getEmployeeName() != null && !employeeSearchDto.getEmployeeName().equals("")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("null")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( emp.firstName LIKE '" + employeeSearchDto.getEmployeeName() + "%' or  emp.lastName LIKE '"
					+ employeeSearchDto.getEmployeeName() + "%'" + "OR emp.employeeCode LIKE '%"
					+ employeeSearchDto.getEmployeeName() + "%'" + "OR dept.departmentName LIKE '%"
					+ employeeSearchDto.getEmployeeName() + "%' )");

		}
		if (employeeSearchDto.getDepartment() != null && !employeeSearchDto.getDepartment().equals("")
				&& !employeeSearchDto.getDepartment().equalsIgnoreCase("null")
				&& !employeeSearchDto.getDepartment().equalsIgnoreCase("undefined")) {

			sb.append(" and dept.departmentName LIKE '" + employeeSearchDto.getDepartment() + "%' ");
		}

		if (employeeSearchDto.getDesignation() != null && !employeeSearchDto.getDesignation().equals("")
				&& !employeeSearchDto.getDesignation().equalsIgnoreCase("null")
				&& !employeeSearchDto.getDesignation().equalsIgnoreCase("undefined")) {

			sb.append(" and design.designationName LIKE '" + employeeSearchDto.getDesignation() + "%' ");
		}

		if (employeeSearchDto.getGrade() != null && !employeeSearchDto.getGrade().equals("")
				&& !employeeSearchDto.getGrade().equalsIgnoreCase("null")
				&& !employeeSearchDto.getGrade().equalsIgnoreCase("undefined")) {

			sb.append(" and gd.gradesName LIKE '" + employeeSearchDto.getGrade() + "%' ");
		}
		if (employeeSearchDto.getReportingTo() != null && !employeeSearchDto.getReportingTo().equals("")
				&& !employeeSearchDto.getReportingTo().equalsIgnoreCase("null")
				&& !employeeSearchDto.getReportingTo().equalsIgnoreCase("undefined")) {

			sb.append(" and emp1.firstName LIKE '" + employeeSearchDto.getGrade() + "%' ");
		}

		if (employeeSearchDto.getDoj() != null && !employeeSearchDto.getDoj().equals("")
				&& !employeeSearchDto.getDoj().equalsIgnoreCase("null")
				&& !employeeSearchDto.getDoj().equalsIgnoreCase("undefined")) {

			sb.append(" and emp.dateOfJoining  = '" + employeeSearchDto.getDoj() + "%' ");
		}
		if (employeeSearchDto.getDob() != null && !employeeSearchDto.getDob().equals("")
				&& !employeeSearchDto.getDob().equalsIgnoreCase("null")
				&& !employeeSearchDto.getDob().equalsIgnoreCase("undefined")) {

			sb.append(" and emp.dateOfBirth  = '" + employeeSearchDto.getDob() + "%' ");
		}

		if (employeeSearchDto.getTabName() != null && employeeSearchDto.getTabName().equals("empOnboard")) {
			sb.append(" and emp.endDate IS null ");
		}

		if (employeeSearchDto.getTabName() != null && employeeSearchDto.getTabName().equals("separating")) {
			sb.append(" and emp.endDate IS NOT NULL");
		}

		sb.append(" and  emp.activeStatus = '" + employeeSearchDto.getActiveStaus() + "' ");

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append("order by emp.employeeId desc ");

		}
//		else if (active != null && (active.trim().equals("employeeCode"))) {
//
//			if (sortDirection.equals("asc")) {
//				sb.append(" order by  LENGTH( emp.employeeCode ), emp.employeeCode   asc ");
//			} else {
//				sb.append(" order by  LENGTH( emp.employeeCode ), emp.employeeCode  desc ");
//			}
//
//		}

		else if (active != null && (active.trim().equals("employeeCode"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.employeeCode asc ");
			} else {
				sb.append(" order by emp.employeeCode desc ");
			}
		} else if (active != null && (active.trim().equals("employeeId"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.employeeId asc ");
			} else {
				sb.append(" order by emp.employeeId desc ");
			}
		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName asc ");
			} else {
				sb.append(" order by emp.firstName desc ");
			}

		} else if (active != null && (active.trim().equals("gradeName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by gd.gradesName asc ");
			} else {
				sb.append(" order by gd.gradesName desc ");
			}

		} else if (active != null && (active.trim().equals("departmentName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by dept.departmentName asc ");
			} else {
				sb.append(" order by dept.departmentName desc ");
			}

		} else if (active != null && (active.trim().equals("designationName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by design.designationName asc ");
			} else {
				sb.append(" order by design.designationName desc ");
			}

		} else if (active != null && (active.trim().equals("dateOfJoining"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.dateOfJoining asc ");
			} else {
				sb.append(" order by emp.dateOfJoining desc ");
			}

		} else if (active != null && (active.trim().equals("dateOfBirth"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.dateOfBirth asc ");
			} else {
				sb.append(" order by emp.dateOfBirth desc ");
			}

		} else if (active != null && (active.trim().equals("ReportingToEmployee"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp1.firstName asc ");
			} else {
				sb.append(" order by emp1.firstName desc ");
			}

		} else {

			sb.append("order by emp.employeeId desc");
		}
	}

	@Override
	public List<Object[]> findEmployeeListforPolicy(Long companyId, EmployeeSearchDTO employeeSearchDTO) {
		// TODO Auto-generated method stub
		System.out.println(
				"getActiveStatus-----------------------------------------" + employeeSearchDTO.getActiveStaus());
		StringBuilder sb = new StringBuilder();

		sb.append(
				"SELECT CONCAT(emp.firstName,' ',emp.lastName ), emp.employeeCode, dept.departmentName, emp.employeeId, dept.departmentId FROM Employee emp ");
		sb.append(
				"LEFT JOIN Department dept ON dept.departmentId=emp.departmentId WHERE emp.companyId =:companyId AND emp.activeStatus='AC' ");
//		sb.append("Group By emp.employeeId ");

//		Group By emp.employeeId
		String active = employeeSearchDTO.getActive();
		String sortDirection = employeeSearchDTO.getSortDirection();
		int offset = employeeSearchDTO.getOffset();

		buildCondtion(employeeSearchDTO, sb);

		sortSearchQuery(sb, active, sortDirection);

		int limit = employeeSearchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------" + search);
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

}
