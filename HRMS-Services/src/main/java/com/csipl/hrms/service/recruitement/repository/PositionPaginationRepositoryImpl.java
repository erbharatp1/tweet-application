package com.csipl.hrms.service.recruitement.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class PositionPaginationRepositoryImpl implements PositionPaginationRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAssignedPositions(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT p.positionCode, p.positionTitle, p.requiredExperience, p.jobLocation, p.employeementType, p.sourceOfPosion, concat(e.firstName, ' ', e.lastName)as recruiter, e.employeeCode, pa.noOfPosition, p.positionId, pa.positionAllocationId FROM PositionAllocationXref pa LEFT JOIN Employee e ON e.employeeId=pa.recruiterEmployeeId "
						+ " JOIN Position p ON p.positionId=pa.positionId WHERE e.companyId=:companyId AND pa.datecreated > date_sub(now(),Interval 6 month) ");

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

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( e.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  e.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR p.positionCode LIKE '%" + searcDto.getEmployeeName()
					+ "%'" + "OR p.positionTitle LIKE '%" + searcDto.getEmployeeName() + "%'"
					+ "OR p.jobLocation LIKE '%" + searcDto.getEmployeeName() + "%'" + "OR p.employeementType LIKE '%"
					+ searcDto.getEmployeeName() + "%' )");

		}

		if (searcDto.getPositionCode() != null && !searcDto.getPositionCode().equals("")
				&& !searcDto.getPositionCode().equalsIgnoreCase("null")
				&& !searcDto.getPositionCode().equalsIgnoreCase("undefined")) {

			sb.append(" and p.positionCode LIKE '" + searcDto.getPositionCode() + "%' ");
		}

		if (searcDto.getPositionTitle() != null && !searcDto.getPositionTitle().equals("")
				&& !searcDto.getPositionTitle().equalsIgnoreCase("null")
				&& !searcDto.getPositionTitle().equalsIgnoreCase("undefined")) {

			sb.append(" and p.positionTitle LIKE '" + searcDto.getPositionTitle() + "%' ");
		}

		if (searcDto.getJobLocation() != null && !searcDto.getJobLocation().equals("")
				&& !searcDto.getJobLocation().equalsIgnoreCase("null")
				&& !searcDto.getJobLocation().equalsIgnoreCase("undefined")) {

			sb.append(" and p.jobLocation LIKE '" + searcDto.getJobLocation() + "%' ");
		}

		if (searcDto.getEmployementType() != null && !searcDto.getEmployementType().equals("")
				&& !searcDto.getEmployementType().equalsIgnoreCase("null")
				&& !searcDto.getEmployementType().equalsIgnoreCase("undefined")) {

			sb.append(" and p.employeementType LIKE '" + searcDto.getEmployementType() + "%' ");
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

			sb.append(" order by pa.positionAllocationId desc ");

		} else if (active != null && (active.trim().equals("positionTitle"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by p.positionTitle asc ");
			} else {
				sb.append(" order by p.positionTitle desc ");
			}

		} else if (active != null && (active.trim().equals("jobLocation"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by p.jobLocation asc ");
			} else {
				sb.append(" order by p.jobLocation desc ");
			}

		} else if (active != null && (active.trim().equals("employeementType"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by p.employeementType asc ");
			} else {
				sb.append(" order by p.employeementType desc ");
			}

		} else if (active != null && (active.trim().equals("sourceOfPosion"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by p.sourceOfPosion asc ");
			} else {
				sb.append(" order by p.sourceOfPosion desc ");
			}

		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by e.firstName asc ");
			} else {
				sb.append(" order by e.firstName desc ");
			}

		} else if (active != null && (active.trim().equals("noOfPosition"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by pa.noOfPosition asc ");
			} else {
				sb.append(" order by pa.noOfPosition desc ");
			}

		} else {

			sb.append("order by pa.positionAllocationId desc");
		}
	}
}
