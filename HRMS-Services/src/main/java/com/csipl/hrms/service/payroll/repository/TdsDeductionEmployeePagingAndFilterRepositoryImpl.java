package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;

@Repository
public class TdsDeductionEmployeePagingAndFilterRepositoryImpl implements TdsDeductionEmployeePagingAndFilterRepository{

	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllEmployees(EmployeeSearchDTO employeeSearchDto) {
		 
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT emp.employeeId,emp.firstName,emp.lastName,"
				+ "emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,"
				+ "desg.designationName, emp.dateOfJoining, emp.tdsLockUnlockStatus  FROM Employee"
				+ " emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg "
				+ "ON desg.designationId= emp.designationId where emp.companyId=:companyId "
				+ "AND emp.endDate is null");
		
		
		String active = employeeSearchDto.getActive();
		String sortDirection = employeeSearchDto.getSortDirection();
		int offset = employeeSearchDto.getOffset();

		buildCondtion(employeeSearchDto, sb);
		sortSearchQuery(sb, active, sortDirection);

		int limit = employeeSearchDto.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", employeeSearchDto.getCompanyId());
		
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;	
		 
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllEmployeesWithTdsStatus(EmployeeSearchDTO employeeSearchDto,Long financialYearId) {
		 
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT emp.employeeId,emp.firstName,emp.lastName,"
				+ "emp.employeeCode,emp.employeeLogoPath ,dept.departmentId, dept.departmentName, desg.designationId,"
				+ "desg.designationName, emp.dateOfJoining, emp.tdsLockUnlockStatus , emp.tdsStatus, tsc.netYearlyIncome, tsc.taxableIncome, "
				+ " tsc.chapter6a+tsc.section10+tsc.section24+tsc.exempStandard+tsc.exempPtAmount+tsc.professionalTax as totalRebate FROM Employee "
				+ " emp JOIN Department dept on dept.departmentId = emp.departmentId JOIN Designation desg "
				+ "ON desg.designationId= emp.designationId LEFT JOIN TdsSummaryChange tsc on tsc.employeeId=emp.employeeId and tsc.financialYearId=:financialYearId and tsc.active='AC'"
				+ " where emp.companyId=:companyId "
				+ "AND emp.endDate is null AND tdsStatus is NOT null");
		
		
		String active = employeeSearchDto.getActive();
		String sortDirection = employeeSearchDto.getSortDirection();
		int offset = employeeSearchDto.getOffset();

		buildCondtion(employeeSearchDto, sb);
		sortSearchQuery(sb, active, sortDirection);

		int limit = employeeSearchDto.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------");
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", employeeSearchDto.getCompanyId());
		nativeQuery.setParameter("financialYearId", financialYearId);
		
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;	
		 
	}
	
	
	
	private void buildCondtion(EmployeeSearchDTO employeeSearchDto, StringBuilder sb) {
		 
		if (employeeSearchDto.getEmployeeName() != null && !employeeSearchDto.getEmployeeName().equals("")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("null")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("undefined")) {

//			sb.append(" and ( emp.firstName LIKE '" + employeeSearchDto.getEmployeeName() + "%' or  emp.lastName LIKE '"
//					+ employeeSearchDto.getEmployeeName() + "%'  )");
			sb.append(" and ( emp.firstName LIKE '" + employeeSearchDto.getEmployeeName() + "%' or  emp.lastName LIKE '"
					+ employeeSearchDto.getEmployeeName() + "%'" + "OR emp.employeeCode LIKE '%"+employeeSearchDto.getEmployeeName()+ "%' )");
		}
		if (employeeSearchDto.getDepartment() != null && !employeeSearchDto.getDepartment().equals("")
				&& !employeeSearchDto.getDepartment().equalsIgnoreCase("null")
				&& !employeeSearchDto.getDepartment().equalsIgnoreCase("undefined")) {

			sb.append(" and dept.departmentId LIKE '" + employeeSearchDto.getDepartment() + "%' ");
		}

		if (employeeSearchDto.getStatus() != null && !employeeSearchDto.getStatus().equals("")
				&& !employeeSearchDto.getStatus().equalsIgnoreCase("null")
				&& !employeeSearchDto.getStatus().equalsIgnoreCase("undefined")) {

			sb.append(" and emp.tdsLockUnlockStatus LIKE '" + employeeSearchDto.getStatus() + "%' ");
		}

		

//		if (employeeSearchDto.getTabName() != null && employeeSearchDto.getTabName().equals("empOnboard")) {
//			sb.append(" and emp.endDate IS null ");
//		}
//
//		if (employeeSearchDto.getTabName() != null && employeeSearchDto.getTabName().equals("separating")) {
//			sb.append(" and emp.endDate !='' ");
		//}

//		sb.append(" and  emp.activeStatus = '" + employeeSearchDto.getActiveStaus() + "' ");
		sb.append(" and  emp.activeStatus = '" + StatusMessage.ACTIVE_CODE + "' ");
	}

	
	
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
	 
		if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName asc ");
			} else {
				sb.append(" order by emp.firstName desc ");
			}

		}  else if (active != null && (active.trim().equals("tdsLockUnlockStatus"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.tdsLockUnlockStatus asc ");
			} else {
				sb.append(" order by emp.tdsLockUnlockStatus desc ");
			}

		}
//		else if (active != null && (active.trim().equals("designationName"))) {
//
//			if (sortDirection.equals("asc")) {
//				sb.append(" order by design.designationName asc ");
//			} else {
//				sb.append(" order by design.designationName desc ");
//			}
//
//		} 
		else if (active != null && (active.trim().equals("dateOfJoining"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.dateOfJoining asc ");
			} else {
				sb.append(" order by emp.dateOfJoining desc ");
			}

		} else {

			sb.append("order by emp.employeeId desc");
		}
	}
}
