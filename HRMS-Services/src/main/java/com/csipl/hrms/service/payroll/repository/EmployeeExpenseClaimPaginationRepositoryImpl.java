package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class EmployeeExpenseClaimPaginationRepositoryImpl implements EmployeeExpenseClaimPaginationRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findExpensesClaimPendingApprovals(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT concat(e.firstName, ' ', e.lastName), eec.dateCreated, SUM(eec.amountExpenses), eec.employeId FROM EmployeeExpenseClaims eec JOIN Employee e ON e.employeeId=eec.employeId "
						+ " WHERE e.companyId=:companyId AND eec.status='PEN' and eec.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");

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

	private void buildCondtion(StringBuilder sb, SearchDTO searcDto) {

		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( e.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  e.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%' )");

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

			sb.append(" GROUP BY eec.employeId ");
			sb.append(" order by eec.employeeExpeneseClaimId desc ");

		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" GROUP BY eec.employeId ");
				sb.append(" order by le.dateCreated asc ");
			} else {
				sb.append(" GROUP BY eec.employeId ");
				sb.append(" order by eec.dateCreated desc ");
			}
		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {

				sb.append(" GROUP BY eec.employeId ");
				sb.append(" order by e.firstName asc ");
			} else {
				sb.append(" GROUP BY eec.employeId ");
				sb.append(" order by e.firstName desc ");
			}

		} else {

			sb.append(" GROUP BY eec.employeId ");
			sb.append("order by eec.employeeExpeneseClaimId desc");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findExpensesClaimNonPendingApprovals(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT concat(e.firstName, ' ', e.lastName), eec.dateCreated, SUM(eec.amountExpenses), eec.employeId FROM EmployeeExpenseClaims eec JOIN Employee e ON e.employeeId=eec.employeId "
						+ " WHERE e.companyId=:companyId AND eec.status != 'PEN' and eec.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");

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

}
