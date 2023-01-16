package com.csipl.tms.attendanceregularizationrequest.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class ARRequestPagingAndFilterRepositoryImpl implements ARRequestPagingAndFilterRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findARRequestPagedAndFilterResult(Long companyId, Boolean status, SearchDTO searcDto) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode, CONCAT(UPPER(SUBSTRING(ecd.firstName ,1,1)),LOWER(SUBSTRING(ecd.firstName ,2))),   CONCAT(UPPER(SUBSTRING(ecd.lastName ,1,1)),LOWER(SUBSTRING(ecd.lastName ,2))) ,d.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,de.designationName, ar.approvalRemark  FROM TMSARRequest ar JOIN Employee ecd on ecd.employeeId=ar.employeeId JOIN Department d on d.departmentId=ecd.departmentId JOIN Designation de on de.designationId= ecd.designationId where ar.companyId =:companyId and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion(status, sb, searcDto);
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
	private void buildCondtion(Boolean status, StringBuilder sb, SearchDTO searcDto) {

		if (status) {
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
						+ "%'" + "OR d.departmentName LIKE '%" + searcDto.getEmployeeName() + "%' )");

			}
			if (searcDto.getDepartment() != null && !searcDto.getDepartment().equals("")
					&& !searcDto.getDepartment().equalsIgnoreCase("null")
					&& !searcDto.getDepartment().equalsIgnoreCase("undefined")) {

				sb.append(" and d.departmentName LIKE '" + searcDto.getDepartment() + "%' ");
			}

			if (searcDto.getDesignation() != null && !searcDto.getDesignation().equals("")
					&& !searcDto.getDesignation().equalsIgnoreCase("null")
					&& !searcDto.getDesignation().equalsIgnoreCase("undefined")) {

				sb.append(" and de.designationName LIKE '" + searcDto.getDesignation() + "%' ");
			}
			sb.append(" AND ar.status = 'PEN'");

		} else {
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
						+ "%'" + "OR d.departmentName LIKE '%" + searcDto.getEmployeeName() + "%' )");

			}
			if (searcDto.getDepartment() != null && !searcDto.getDepartment().equals("")
					&& !searcDto.getDepartment().equalsIgnoreCase("null")
					&& !searcDto.getDepartment().equalsIgnoreCase("undefined")) {

				sb.append(" and d.departmentName LIKE '" + searcDto.getDepartment() + "%' ");
			}

			if (searcDto.getDesignation() != null && !searcDto.getDesignation().equals("")
					&& !searcDto.getDesignation().equalsIgnoreCase("null")
					&& !searcDto.getDesignation().equalsIgnoreCase("undefined")) {

				sb.append(" and de.designationName LIKE '" + searcDto.getDesignation() + "%' ");
			}
			sb.append(" AND ar.status !='PEN'");
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

			sb.append(" order by ar.arID desc ");

		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ar.dateCreated asc ");
			} else {
				sb.append(" order by ar.dateCreated desc ");
			}
		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ecd.firstName asc ");
			} else {
				sb.append(" order by ecd.firstName desc ");
			}

		} else if (active != null && (active.trim().equals("fromDate"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ar.fromDate asc ");
			} else {
				sb.append(" order by ar.fromDate desc ");
			}

		} else if (active != null && (active.trim().equals("days"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ar.days asc ");
			} else {
				sb.append(" order by ar.days desc ");
			}

		} else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ar.status asc ");
			} else {
				sb.append(" order by ar.status desc ");
			}

		} else if (active != null && (active.trim().equals("arCategory"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by ar.arCategory asc ");
			} else {
				sb.append(" order by ar.arCategory desc ");
			}

		} else {

			sb.append("order by ar.arID desc");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllEmpApprovalsPending(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId=:companyId and ar.status='PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion1(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("ARPending Query Start-----------------------");
		System.out.println(search);
		System.out.println("ARPending Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}

	private void buildCondtion1(StringBuilder sb, SearchDTO searcDto) {

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllApprovalsNonPending(Long companyId, Long employeeId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT ecd.employeeCode,ecd.firstName,ecd.lastName,dept.departmentName,ar.arID,ar.dateCreated,ar.arCategory,ar.actionableDate,ar.fromDate,ar.toDate,ar.days,ar.status,ar.employeeId,ar.employeeRemark,ar.userId  ,deg.designationName ,ecd.employeeLogoPath,ar.approvalRemark FROM TMSARRequest ar JOIN Employee ecd JOIN Department dept JOIN Designation deg on ecd.employeeId=ar.employeeId and ecd.departmentId =dept.departmentId AND deg.designationId=ecd.designationId where ar.companyId= :companyId and  ecd.ReportingToEmployee= :employeeId and ar.status != 'PEN' and ar.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtion1(sb, searcDto);
		sortSearchQuery(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("ARNonPending Query Start-----------------------");
		System.out.println(search);
		System.out.println("ARNonPending Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> findAllMassCommunications(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT mc.massCommunicationId, mc.title, mc.description, mc.dateFrom, mc.dateTo FROM MassCommunication mc WHERE mc.companyId=:companyId ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForMassCommunication(sb, searcDto);
		sortSearchQueryForMassCommunication(sb, active, sortDirection);
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

	private void buildCondtionForMassCommunication(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getTitle() != null && !searcDto.getTitle().equals("")
				&& !searcDto.getTitle().equalsIgnoreCase("null")
				&& !searcDto.getTitle().equalsIgnoreCase("undefined")) {

			sb.append(" and ( mc.title LIKE '" + searcDto.getTitle() + "%' or  mc.description LIKE '"
					+ searcDto.getTitle() + "%'" + "OR mc.title LIKE '%" + searcDto.getTitle() + "%' )");

		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQueryForMassCommunication(StringBuilder sb, String active, String sortDirection) {

		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by mc.massCommunicationId desc ");

		}
//		else if (active != null && (active.trim().equals("dateCreated"))) {
//
//			if (sortDirection.equals("asc")) {
//				sb.append(" order by ar.dateCreated asc ");
//			} else {
//				sb.append(" order by ar.dateCreated desc ");
//			}
//		} 

	}

	@Override
	public List<Object[]> getARRequestDetailsbyPagination(Long employeeId, SearchDTO searchDTO) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT arr.arID,arr.employeeId,arr.approvalId,arr.companyId,arr.approvalRemark,arr.employeeRemark,arr.cancelRemark,\r\n"
						+ "arr.dateCreated,arr.arCategory,arr.actionableDate,arr.fromDate,arr.toDate,arr.days,arr.status FROM TMSARRequest arr \r\n"
						+ "where arr.employeeId=:employeeId AND arr.status !='PEN' and arr.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)\r\n"
						+ " ");

		String active = searchDTO.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searchDTO.getSortDirection();
		int offset = searchDTO.getOffset();
		buildCondtionForARRequest(sb, searchDTO);
		sortSearchQueryForARRequest(sb, active, sortDirection);
		int limit = searchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}

	private void buildCondtionForARRequest(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( arr.status LIKE '" + searcDto.getEmployeeName() + "%' or  arr.dateCreated LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR arr.arCategory LIKE '%" + searcDto.getEmployeeName()
					+ "%' )");
		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQueryForARRequest(StringBuilder sb, String active, String sortDirection) {

		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append("  ORDER BY arr.dateCreated ASC ");

		} else {
			sb.append("ORDER BY arr.dateCreated ASC");
		}
	}

	@Override
	public List<Object[]> getARPendingRequestDetailsbyPagination(Long employeeId, SearchDTO searchDTO) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT arr.arID,arr.employeeId,arr.approvalId,arr.companyId,arr.approvalRemark,arr.employeeRemark,arr.cancelRemark,\r\n"
						+ "arr.dateCreated,arr.arCategory,arr.actionableDate,arr.fromDate,arr.toDate,arr.days,arr.status FROM TMSARRequest arr \r\n"
						+ "where arr.employeeId=:employeeId AND arr.status ='PEN' and arr.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH)\r\n"
						+ " ");

		String active = searchDTO.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searchDTO.getSortDirection();
		int offset = searchDTO.getOffset();
		buildCondtionForARPendingRequest(sb, searchDTO);
		sortSearchQueryForARPendingRequest(sb, active, sortDirection);
		int limit = searchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}

	private void buildCondtionForARPendingRequest(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( arr.status LIKE '" + searcDto.getEmployeeName() + "%' or  arr.dateCreated LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR arr.arCategory LIKE '%" + searcDto.getEmployeeName()
					+ "%' )");
		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQueryForARPendingRequest(StringBuilder sb, String active, String sortDirection) {

		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append("  ORDER BY arr.dateCreated ASC ");

		} else {
			sb.append("ORDER BY arr.dateCreated ASC");
		}
	}

	@Override
	public List<Object[]> getTeamsAbsentListByDate(Long companyId, Long employeeId, String selectDate,
			SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName  FROM Employee e JOIN Department dept ON e.departmentId=dept.departmentId "
						+ "	WHERE e.employeeCode NOT IN (SELECT userId FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date)=:selectDate ) "
						+ " and e.companyId=:companyId AND e.ReportingToEmployee=:ReportingToEmployee  AND e.activeStatus='AC' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getTeamsAbsentListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getTeamsAbsentListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setParameter("ReportingToEmployee", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsAbsentListByDate result Size ---" + resultList.size());
		return resultList;

	}

	private void buildCondtionForAbsentList(StringBuilder sb, SearchDTO searcDto) {

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
					+ "%'" + "OR dept.departmentName LIKE '%" + searcDto.getEmployeeName() + "%' )");

		}
		if (searcDto.getDepartment() != null && !searcDto.getDepartment().equals("")
				&& !searcDto.getDepartment().equalsIgnoreCase("null")
				&& !searcDto.getDepartment().equalsIgnoreCase("undefined")) {

			sb.append(" and dept.departmentName LIKE '" + searcDto.getDepartment() + "%' ");
		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQueryForAbsentList(StringBuilder sb, String active, String sortDirection) {

		System.out.println("Active - " + active);
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" GROUP BY e.employeeCode ");

			sb.append(" order by e.ReportingToEmployee desc ");

		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" GROUP BY e.employeeCode ");

				sb.append(" order by e.firstName asc ");
			} else {
				sb.append(" GROUP BY e.employeeCode ");

				sb.append(" order by e.firstName desc ");
			}

		} else {

			sb.append(" GROUP BY e.employeeCode ");

			sb.append(" order by e.ReportingToEmployee desc ");
		}

	}

	@Override
	public List<Object[]> getTeamsPresentListByDate(Long companyId, Long employeeId, String selectDate,
			SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,dl.mode  ,concat(TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime),'') as ReportedLateBy ,sh.startTime from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId  	WHERE   e.companyId=:companyId  AND e.ReportingToEmployee= :ReportingToEmployee AND e.activeStatus='AC'  "
						+ "	and cast(dl.logDate as date)= :selectDate ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getTeamsPresentListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getTeamsPresentListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setParameter("ReportingToEmployee", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsPresentListByDate result Size ---" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> getTeamsLateComersListByDate(Long companyId, Long employeeId, String selectDate,
			SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime)as ReportedLateBy ,dl.mode,sh.startTime ,sh.graceTime "
						+ "	from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId "
						+ "	WHERE e.companyId=:companyId  AND e.ReportingToEmployee=:ReportingToEmployee  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= :selectDate AND e.activeStatus='AC' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getTeamsLateComersListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getTeamsLateComersListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setParameter("ReportingToEmployee", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsLateComersListByDate result Size ---" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> getAllLateComersListByDate(Long companyId, String selectDate, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime)as ReportedLateBy ,dl.mode,sh.startTime "
						+ "	from DeviceLogsInfo dl JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId JOIN Department dept ON e.departmentId=dept.departmentId "
						+ "	WHERE e.companyId=:companyId  and  cast(dl.logDate as time) > sh.graceTime AND cast(dl.logDate as date)= :selectDate AND e.activeStatus='AC' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getAllLateComersListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getAllLateComersListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsLateComersListByDate result Size ---" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> getAllAbsentListByDate(Long companyId, String selectDate, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName  FROM Employee e JOIN Department dept ON e.departmentId=dept.departmentId "
						+ " WHERE e.employeeCode NOT IN (SELECT userId FROM DeviceLogsInfo dl WHERE cast(dl.logDate as date)=:selectDate ) and e.companyId=:companyId  AND e.activeStatus='AC' ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getAllAbsentListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getAllAbsentListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsLateComersListByDate result Size ---" + resultList.size());
		return resultList;

	}

	@Override
	public List<Object[]> getAllPresentListByDate(Long companyId, String selectDate, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" select concat( e.firstName,' ',e.lastName),e.employeeCode,dept.departmentName,cast(dl.logDate as time) as PunchRecords,dl.mode  ,concat(TIMEDIFF(cast(dl.logDate as time) ,sh.graceTime),'') as ReportedLateBy ,sh.startTime "
						+ " from DeviceLogsInfo dl 	JOIN Employee e JOIN TMSShift sh on dl.userId = e.employeeCode and sh.shiftId=e.shiftId  	JOIN Department dept ON e.departmentId=dept.departmentId  	WHERE   e.companyId=:companyId  AND e.activeStatus='AC'  and cast(dl.logDate as date)=:selectDate ");

		String active = searcDto.getActive();
		System.out.println(" 1-Active  - " + active);
		String sortDirection = searcDto.getSortDirection();
		int offset = searcDto.getOffset();
		buildCondtionForAbsentList(sb, searcDto);
		sortSearchQueryForAbsentList(sb, active, sortDirection);
		int limit = searcDto.getLimit();

		String search = sb.toString();

		System.out.println("getAllPresentListByDate Query Start-----------------------");
		System.out.println(search);
		System.out.println("getAllPresentListByDate Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("selectDate", selectDate);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("getTeamsLateComersListByDate result Size ---" + resultList.size());
		return resultList;

	}

}
