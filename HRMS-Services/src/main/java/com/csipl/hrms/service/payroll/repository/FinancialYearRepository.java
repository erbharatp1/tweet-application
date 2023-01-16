package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface FinancialYearRepository extends CrudRepository<FinancialYear, Long> {
  	
	@Query("from FinancialYear financialYear where financialYear.dateFrom <= ?1 and financialYear.dateTo >= ?1 And companyId=?2 AND activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
	public FinancialYear getFinancialYear(Date today,Long  companyId); 
	
	
	
	@Query("from FinancialYear where companyId=?1 ORDER BY financialYearId DESC")
	public List<FinancialYear> findAllFinancialYear(Long  companyId); 
	
	@Query("from FinancialYear where companyId=?1 AND financialYearId=?2 ORDER BY financialYearId DESC")
	public FinancialYear findFinancialYear(Long  longcompanyId, Long financialYearId); 
}
