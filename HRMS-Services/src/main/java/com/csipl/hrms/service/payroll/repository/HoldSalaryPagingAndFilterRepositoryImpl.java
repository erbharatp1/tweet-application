package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
@Repository
public class HoldSalaryPagingAndFilterRepositoryImpl implements HoldSalaryPagingAndFilterRepository{
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(HoldSalaryPagingAndFilterRepositoryImpl.class);
	
	
	@Override
	public List<Object[]> holdSalarySearch(HoldSalaryDTO holdSalarySearchDto) {
		String Query = "CALL pro_empholdsalary_report(:p_selected_month ,:p_dept_id,:p_desg_id ,:p_grad_id,:p_company_id)";
		Query nativeQuery = em.createNativeQuery(Query);
		
		System.out.println("nativeQuery>>>>>> "+nativeQuery);
		
		
		if (holdSalarySearchDto.getPayrollMonth() != null) {
			nativeQuery.setParameter("p_selected_month", holdSalarySearchDto.getPayrollMonth());
			logger.info("p_selected_month---" + holdSalarySearchDto.getPayrollMonth());
		} else {
			nativeQuery.setParameter("p_selected_month", "");

		}
		
		if (holdSalarySearchDto.getDepartmentId() != null) {
			logger.info("dept Id()---" + holdSalarySearchDto.getDepartmentId());
			nativeQuery.setParameter("p_dept_id", holdSalarySearchDto.getDepartmentId());
		} else {
			nativeQuery.setParameter("p_dept_id", "");
		}

		if (holdSalarySearchDto.getDesignationId() != null) {
			logger.info("desg Id()---" + holdSalarySearchDto.getDesignationId());
			nativeQuery.setParameter("p_desg_id", holdSalarySearchDto.getDesignationId());
		} else {
			nativeQuery.setParameter("p_desg_id", "");
		}
		if (holdSalarySearchDto.getGradesId() != null) {
			logger.info("grade Id()---" + holdSalarySearchDto.getGradesId());
			nativeQuery.setParameter("p_grad_id", holdSalarySearchDto.getGradesId());
		} else {
			nativeQuery.setParameter("p_grad_id", "");
		}
		
		
		if (holdSalarySearchDto.getCompanyId() != null) {
			logger.info("p_company_id---" + holdSalarySearchDto.getCompanyId());
			nativeQuery.setParameter("p_company_id", holdSalarySearchDto.getCompanyId());
		} else {
			nativeQuery.setParameter("p_company_id", "");
		}
		@SuppressWarnings("unchecked")
		final List<Object[]> resultList = nativeQuery.getResultList();
		logger.info("result Size ---" + resultList.size());
		return resultList;
		
		
//		dfdfdf
	}

}

