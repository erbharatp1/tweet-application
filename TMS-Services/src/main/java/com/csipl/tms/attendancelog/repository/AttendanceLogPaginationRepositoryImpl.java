package com.csipl.tms.attendancelog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.dto.search.EmployeeSearchDTO;
 
@Repository
public class AttendanceLogPaginationRepositoryImpl implements AttendanceLogPaginationRepository {
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;
	private static final Logger logger = LoggerFactory.getLogger(AttendanceLogPaginationRepositoryImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAttendanceLogPaginationList(Long companyId, EmployeeSearchDTO employeeSearchDto,
			String strAttendanceDate, String activeStatus) {
		 
 	
		logger.info("strAttendanceDate---" + strAttendanceDate + " -companyId" +companyId +"activeStatus>>"+activeStatus);
		String Query = "CALL pro_bulkattendance_view(:p_selected_date ,:p_employee_name,:p_sorttype ,:p_activestatus,:p_employee_code ,:p_dept_id,:p_desg_id,:p_company_id,:p_active_value)";
		Query nativeQuery = em.createNativeQuery(Query);
		nativeQuery.setParameter("p_selected_date", strAttendanceDate);
		 
		if (employeeSearchDto.getEmployeeName() != null) {
			nativeQuery.setParameter("p_employee_name", employeeSearchDto.getEmployeeName());
			logger.info("EmployeeName---" + employeeSearchDto.getEmployeeName());
		} else {
			nativeQuery.setParameter("p_employee_name", "");

		}

		if (employeeSearchDto.getEmployeeCode() != null) {
			logger.info("EmployeeCode()---" + employeeSearchDto.getEmployeeCode());
			nativeQuery.setParameter("p_employee_code", employeeSearchDto.getEmployeeCode());
		} else {
			nativeQuery.setParameter("p_employee_code", "");
		}
		
		if (employeeSearchDto.getDepartmentId() != null) {
			logger.info("dept Id()---" + employeeSearchDto.getDepartmentId());
			nativeQuery.setParameter("p_dept_id", employeeSearchDto.getDepartmentId());
		} else {
			nativeQuery.setParameter("p_dept_id", "");
		}
		
		if (employeeSearchDto.getDesignationId() != null) {
			logger.info("desg Id()---" + employeeSearchDto.getDesignationId());
			nativeQuery.setParameter("p_desg_id", employeeSearchDto.getDesignationId());
		} else {
			nativeQuery.setParameter("p_desg_id", "");
		}
		if (employeeSearchDto.getAttendaceStatus() != null) {
			logger.info("p_active_value()---" + employeeSearchDto.getAttendaceStatus());
			nativeQuery.setParameter("p_active_value", employeeSearchDto.getAttendaceStatus());
		} else {
			nativeQuery.setParameter("p_active_value", "");
		}
		nativeQuery.setParameter("p_sorttype", "");
		nativeQuery.setParameter("p_activestatus",activeStatus );
		//nativeQuery.setParameter("p_offset","");
		//nativeQuery.setParameter("p_limit","");
	//	nativeQuery.setParameter("p_active_value", "");
		nativeQuery.setParameter("p_company_id", companyId);
		@SuppressWarnings("unchecked")
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("result Size ---" + resultList.size());
		logger.info("Query>>>> ---" + Query);

		return resultList;
	
  	}

	/**
	 * @param employeeSearchDto
	 * @param sb
	 */
	private void buildCondtion(EmployeeSearchDTO employeeSearchDto, StringBuilder sb) {

		if (employeeSearchDto.getEmployeeName() != null && !employeeSearchDto.getEmployeeName().equals("")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("null")
				&& !employeeSearchDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" WHERE ( emp.firstName LIKE '" + employeeSearchDto.getEmployeeName()
					+ "%' or  emp.lastName LIKE '" + employeeSearchDto.getEmployeeName() + "%'  )");
		}

		if (employeeSearchDto.getDepartmentId() != null) {
			sb.append(" Where  emp.departmentId=" + employeeSearchDto.getDepartmentId());
			if (employeeSearchDto.getDesignationId() != null) {
				sb.append(" and emp.designationId=" + employeeSearchDto.getDesignationId());
			}
			if (employeeSearchDto.getEmployeeCode() != null && !employeeSearchDto.getEmployeeCode().equals("")
					&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("null")
					&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("undefined")) {
				sb.append(" and emp.employeeCode='" + employeeSearchDto.getEmployeeCode() + "'  ");
			}
		} else {
			if (employeeSearchDto.getDesignationId() != null) {
				sb.append(" WHERE emp.designationId=" + employeeSearchDto.getDesignationId());
				if (employeeSearchDto.getEmployeeCode() != null && !employeeSearchDto.getEmployeeCode().equals("")
						&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("null")
						&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("undefined")) {
					sb.append(" and emp.employeeCode='" + employeeSearchDto.getEmployeeCode() + "'  ");
				}
			} else {
				if (employeeSearchDto.getEmployeeCode() != null && !employeeSearchDto.getEmployeeCode().equals("")
						&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("null")
						&& !employeeSearchDto.getEmployeeCode().equalsIgnoreCase("undefined")) {
					sb.append(" Where emp.employeeCode='" + employeeSearchDto.getEmployeeCode() + "'  ");
				}
			}
		}

	}

	/**
	 * @param sb
	 * @param active
	 * @param sortDirection
	 */
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {

		if (active != null && (active.trim().equals("firstName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName asc ");
			} else {
				sb.append(" order by emp.firstName desc ");
			}

		}
	}

}
