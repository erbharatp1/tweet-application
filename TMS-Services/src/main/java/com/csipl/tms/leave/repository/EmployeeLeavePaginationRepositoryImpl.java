package com.csipl.tms.leave.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.tms.model.leave.LeaveSearchDTO;

@Repository
public class EmployeeLeavePaginationRepositoryImpl implements EmployeeLeavePaginationRepository {
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getPendingEmployeeLeavebyPagination(Long companyId, LeaveSearchDTO leaveSearchDto) {
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT le.leaveId , le.employeeId, le.approvalId,le.dateCreated,le.days,le.status");
		sb.append(
				",desig.designationName,  CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)),LOWER(SUBSTRING(emp.firstName ,2))),   CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName ,2))),LTM.leaveName,le.employeeRemark,le.approvalRemark,le.cancleRemark,le.fromDate,le.toDate,emp.employeeCode ");
		sb.append(
				"FROM TMSLeaveEntries le JOIN Employee emp ON emp.employeeId=le.employeeId JOIN Designation desig ON desig.designationId=emp.designationId ");
		sb.append(
				"JOIN TMSLeaveType LT ON LT.leaveTypeId=le.leaveTypeId JOIN TMSLeaveTypeMaster LTM ON LT.leaveTypeMasterId=LTM.leaveId ");
		/*
		 * sb.append( "JOIN TMSLeaveType LT ON LT.leaveTypeId=le.leaveTypeId ");
		 */
		sb.append(" WHERE le.companyId =:companyId and  le.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)");

		// sb.append("WHERE le.status='C'");

		String active = leaveSearchDto.getActive();
		String sortDirection = leaveSearchDto.getSortDirection();
		int offset = leaveSearchDto.getOffset();

		buildCondtion(leaveSearchDto, sb);
		sortSearchQuery(sb, active, sortDirection);

		int limit = leaveSearchDto.getLimit();

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
	private void buildQuery(LeaveSearchDTO leaveSearchDTO, StringBuilder sb) {

		if (leaveSearchDTO.getStatus().equals("APR") || leaveSearchDTO.getStatus().equals("REJ")) {
			sb.append(",appEmp.firstName,appEmp.lastName,appDesig.designationName");
			sb.append(" FROM TMSLeaveEntries le JOIN Employee emp ON emp.employeeId=le.employeeId");
			sb.append("JOIN Employee appEmp ON appEmp.employeeId=le.approvalId");
			sb.append("JOIN Designation appDesig ON appDesig.designationId=appEmp.designationId");
		} else {
			sb.append(" FROM TMSLeaveEntries le JOIN Employee emp ON emp.employeeId=le.employeeId ");
		}

	}

	/**
	 * @param employeeSearchDto
	 * @param sb
	 */
	private void buildCondtion(LeaveSearchDTO leaveSearchDTO, StringBuilder sb) {
		if (leaveSearchDTO.getStatusFlag())
			sb.append(" and le.status='PEN'");
		else
			sb.append("and (le.status='CEN' OR le.status='APR' OR le.status='REJ')");

		if (leaveSearchDTO.getEmployeeName() != null && !leaveSearchDTO.getEmployeeName().equals("")
				&& !leaveSearchDTO.getEmployeeName().equalsIgnoreCase("null")
				&& !leaveSearchDTO.getEmployeeName().equalsIgnoreCase("undefined")) {

//			sb.append(" and ( emp.firstName LIKE '" + leaveSearchDTO.getEmployeeName() + "%' or  emp.lastName LIKE '"
//					+ leaveSearchDTO.getEmployeeName() + "%'  )");
			sb.append(" and ( emp.firstName LIKE '" + leaveSearchDTO.getEmployeeName() + "%' or  emp.lastName LIKE '"
					+ leaveSearchDTO.getEmployeeName() + "%'" + "OR emp.employeeCode LIKE '%"+leaveSearchDTO.getEmployeeName()+ "%' )");
		}

		sb.append(" and  emp.activeStatus = '" + leaveSearchDTO.getActiveStaus() + "' ");
	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by le.leaveId desc ");

			 
		} else if (active != null && (active.trim().equals("employeeName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName asc ");
			} else {
				sb.append(" order by emp.firstName desc ");
			}
		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.dateCreated asc ");
			} else {
				sb.append(" order by emp.dateCreated desc ");
			}

		}
		else if (active != null && (active.trim().equals("leaveName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by LTM.leaveName asc ");
			} else {
				sb.append(" order by LTM.leaveName desc ");
			}

		} else if (active != null && (active.trim().equals("days"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by le.days asc ");
			} else {
				sb.append(" order by le.days desc ");
			}

		} else if (active != null && (active.trim().equals("designationName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by design.designationName asc ");
			} else {
				sb.append(" order by design.designationName desc ");
			}

		} else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by le.status asc ");
			} else {
				sb.append(" order by le.status desc ");
			}

		} 
		else if (active != null && (active.trim().equals("fromDate"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by le.fromDate asc ");
			} else {
				sb.append(" order by le.fromDate desc ");
			}

		}
		else {

			sb.append("order by le.leaveId desc");
		}
	}

	/**
	 * @param employeeSearchDto
	 * @param sb
	 */
	private void leaveAttendanceBuildQuery(EmployeeSearchDTO employeeSearchDto, StringBuilder sb) {
 

		if (employeeSearchDto.getEmployeeName() != null && !employeeSearchDto.getEmployeeName().equals("")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("null")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( e.firstName LIKE '" + employeeSearchDto.getEmployeeName() + "%' or  e.lastName LIKE '"
					+ employeeSearchDto.getEmployeeName() + "%'  )");
		}

		if (employeeSearchDto.getDepartmentId() != null) {
			sb.append(" and   e.departmentId=" + employeeSearchDto.getDepartmentId());
			if (employeeSearchDto.getDesignationId() != null) {
				sb.append(" and e.designationId=" + employeeSearchDto.getDesignationId());
			}

		} else {
			if (employeeSearchDto.getDesignationId() != null) {
				sb.append(" and e.designationId=" + employeeSearchDto.getDesignationId());
			}
		}
 

	}

//	@Override
//	public List<Object[]> getLeaveAttendancePendingList(EmployeeSearchDTO employeeSearchDto, boolean flag,
//			String month, String year) {
//		System.out.println(">>>>>>>>>>>>>>EmployeeLeavePaginationRepositoryImpl>>>>companyId");
//		StringBuilder sb = new StringBuilder();
//		sb.append(
//				"select e.firstName,e.lastName, dept.departmentName, e.employeeId, tle.status,'waiting for approval' as remark,tle.fromDate,tle.toDate ,tle.leaveId, (CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN    'Holiday'   WHEN(tle.halfFullDay='H') Then 'Half Day Leave'   when(tle.halfFullDay='F') THEN 'Full Day Leave'  else    'Absent'  END )  as 'leave status' ,e.employeeCode from Employee e \r\n" + 
//				"left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and Month(al.attendanceDate) ='04' and year(al.attendanceDate) ='2018' and al.companyId=:companyId \r\n" + 
//				"left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='PEN' and  Month(tle.fromDate)=:month and Year(tle.fromDate)=:year and tle.companyId=1\r\n" + 
//				"left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=:companyId   join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=:companyId");
//		
// 
//		
//		leaveAttendanceBuildQuery(employeeSearchDto, sb);
//		
//		String active = employeeSearchDto.getActive();
//  		String sortDirection = employeeSearchDto.getSortDirection();
// 		
//  		leaveAttendanceSortSearchQuery(sb, active, sortDirection);
//
//		String search = sb.toString();
//		System.out.println("Query Start-----------------------");
//		System.out.println(search);
//		System.out.println("Query End-----------------------");
//		Query nativeQuery = em.createNativeQuery(search);
//		nativeQuery.setParameter("companyId", employeeSearchDto.getCompanyId());
//		nativeQuery.setParameter("month", month);
//		nativeQuery.setParameter("year", year);
// 
//		int offset = employeeSearchDto.getOffset();
//
//		int limit = employeeSearchDto.getLimit();
//
//		if (flag) {
//			nativeQuery.setFirstResult(offset);
//			nativeQuery.setMaxResults(limit);
//		}
//		final List<Object[]> resultList = nativeQuery.getResultList();
//		System.out.println("result Size ---" + resultList.size());
//		return resultList;
//	}
	@Override
	public List<Object[]> getLeaveAttendancePendingList(EmployeeSearchDTO employeeSearchDto, boolean flag,
			String month, String year) {
		System.out.println(">>>>>>>>>>>>>>EmployeeLeavePaginationRepositoryImpl>>>>companyId");
		StringBuilder sb = new StringBuilder();
//		sb.append(
//				"select e.firstName,e.lastName, dept.departmentName, e.employeeId, tle.status,'waiting for approval' as remark,tle.fromDate,tle.toDate ,tle.leaveId, (CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN    'Holiday'   WHEN(tle.halfFullDay='H') Then 'Half Day Leave'   when(tle.halfFullDay='F') THEN 'Full Day Leave'  else    'Absent'  END )  as 'leave status' ,e.employeeCode from Employee e \r\n" + 
//				"left OUTER join AttendanceLogs al on al.employeeId=e.employeeId  and Month(al.attendanceDate) ='04' and year(al.attendanceDate) ='2018' and al.companyId=:companyId \r\n" + 
//				"left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='PEN' and  Month(tle.fromDate)=:month and Year(tle.fromDate)=:year and tle.companyId=1\r\n" + 
//				"left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=:companyId   join Department dept  on  dept.departmentId= e.departmentId  and dept.companyId=:companyId");
//		
 sb.append("select e.firstName,e.lastName, dept.departmentName, e.employeeId, tle.status,'waiting for approval'\r\n" + 
 		" as remark,tle.fromDate,tle.toDate ,tle.leaveId \r\n" + 
 		" , (CASE when((tle.fromDate=th.fromDate) or (tle.toDate=th.toDate)) THEN  \r\n" + 
 		" 'Holiday'   WHEN(tle.halfFullDay='H') Then 'Half Day Leave'   when(tle.halfFullDay='F') THEN 'Full Day Leave' \r\n" + 
 		" else    'Absent'  END )  as 'leave status' ,e.employeeCode,al.arID from Employee e \r\n" + 
 		"left OUTER join TMSARRequest al on al.employeeId=e.employeeId  and Month(al.fromDate) \r\n" + 
 		"=:month and year(al.fromDate) =:year and al.companyId=:companyId \r\n" + 
 		"left OUTER join TMSLeaveEntries tle on tle.employeeId=e.employeeId and tle.status='PEN' and \r\n" + 
 		" Month(tle.fromDate)=:month and Year(tle.fromDate)=:year and tle.companyId=:companyId\r\n" + 
 		" left OUTER join TMSHolidays th on  th.fromDate= tle.fromDate or th.toDate=tle.toDate and th.companyId=:companyId\r\n" + 
 		"  join Department dept  on  dept.departmentId= e.departmentId AND dept.companyId=:companyId");
		
		leaveAttendanceBuildQuery(employeeSearchDto, sb);
		
		String active = employeeSearchDto.getActive();
  		String sortDirection = employeeSearchDto.getSortDirection();
 		
  		leaveAttendanceSortSearchQuery(sb, active, sortDirection);

		String search = sb.toString();
		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", employeeSearchDto.getCompanyId());
		nativeQuery.setParameter("month", month);
		nativeQuery.setParameter("year", year);
 
		int offset = employeeSearchDto.getOffset();

		int limit = employeeSearchDto.getLimit();

		if (flag) {
			nativeQuery.setFirstResult(offset);
			nativeQuery.setMaxResults(limit);
		}
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;
	}
	
	
	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void leaveAttendanceSortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by tle.leaveId desc ");

		} /*else if (active != null && (active.trim().equals("employeeCode"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by  LENGTH( sp.dateCreated ), sp.dateCreated   asc ");
			} else {
				sb.append(" order by  LENGTH( sp.dateCreated ), sp.dateCreated  desc ");
			}

		}*/ else if (active != null && (active.trim().equals("employeeCode"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by e.employeeCode asc ");
			} else {
				sb.append(" order by e.employeeCode desc ");
			}
		}
			else if (active != null && (active.trim().equals("firstName"))) {

				if (sortDirection.equals("asc")) {
					sb.append(" order by e.firstName asc ");
				} else {
					sb.append(" order by e.firstName desc ");
				}

		} else if (active != null && (active.trim().equals("department"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by e.departmentId asc ");
			} else {
				sb.append(" order by e.departmentId desc ");
			}

		} else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tle.status asc ");
			} else {
				sb.append(" order by tle.status desc ");
			}

		}   else {
 			sb.append(" order by tle.leaveId desc ");
		}
	}
}
