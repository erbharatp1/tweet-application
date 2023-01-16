package com.csipl.hrms.service.payroll.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payrollprocess.FinancialMonth;

public interface FinancialMonthRepository extends CrudRepository<FinancialMonth, Long> {
  	
//	@Query("from FinancialYear financialYear where financialYear.dateFrom <= ?1 and financialYear.dateTo >= ?1 And companyId=?2")
//	public FinancialYear getFinancialYear(Date today,Long  companyId); 
	
	@Query("from FinancialMonth where companyId=?1 ORDER BY companyId DESC")
	public FinancialMonth findFinancialMonth(Long  companyId); 
}
