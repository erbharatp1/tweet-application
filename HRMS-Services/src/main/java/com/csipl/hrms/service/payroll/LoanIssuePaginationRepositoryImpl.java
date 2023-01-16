package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;

@Repository
public class LoanIssuePaginationRepositoryImpl implements LoanIssuePaginationRepository {

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Object[]> getLoanIssueSearch(Long companyId, SearchDTO searcDto) {

		StringBuilder sb = new StringBuilder();
		sb.append("select loanIssue.transactionNo, loanIssue.employeeId,concat(emp.firstName,' ', emp.lastName) as 'empName', emp.employeeCode,dep.departmentName,des.designationName,loanIssue.loanAmount,\r\n" + 
				"loanIssue.noOfEmi,loanIssue.loanPendingAmount ,loanIssue.emiAmount  from Employee emp JOIN LoanIssue loanIssue on emp.employeeId=loanIssue.employeeId JOIN \r\n" + 
				"Department dep ON emp.departmentId=dep.departmentId JOIN Designation des On emp.designationId=des.designationId  where loanIssue.activeStatus='AC' and loanIssue.companyId=:companyId ");

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

			sb.append(" and ( emp.firstName LIKE '" + searcDto.getEmployeeName() + "%' or  emp.lastName LIKE '"
					+ searcDto.getEmployeeName() + "%'" + "OR p.positionCode LIKE '%" + searcDto.getEmployeeName()
					+ "%'" + "OR p.positionTitle LIKE '%" + searcDto.getEmployeeName() + "%'"
					+ "OR p.jobLocation LIKE '%" + searcDto.getEmployeeName() + "%'" + "OR p.employeementType LIKE '%"
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

			sb.append(" order by loanIssue.loanAmount desc ");

		} else if (active != null && (active.trim().equals("loanAmount"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by loanIssue.loanAmount asc ");
			} else {
				sb.append(" order by loanIssue.loanAmount desc ");
			}

		} else if (active != null && (active.trim().equals("transactionNo"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by loanIssue.transactionNo asc ");
			} else {
				sb.append(" order by loanIssue.transactionNo desc ");
			}

		} else if (active != null && (active.trim().equals("noOfEmi"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by loanIssue.noOfEmi asc ");
			} else {
				sb.append(" order by loanIssue.noOfEmi desc ");
			}

		} else if (active != null && (active.trim().equals("loanPendingAmount"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by loanIssue.loanPendingAmount asc ");
			} else {
				sb.append(" order by loanIssue.loanPendingAmount desc ");
			}

		} else if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName asc ");
			} else {
				sb.append(" order by emp.firstName desc ");
			}

		} 
		else {

			sb.append(" order by loanIssue.transactionNo desc");
		}
	}
}
