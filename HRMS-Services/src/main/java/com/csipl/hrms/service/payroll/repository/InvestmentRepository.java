package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsGroup;
import com.csipl.hrms.model.payroll.TdsGroupSetup;

public interface InvestmentRepository extends CrudRepository<TdsGroupSetup, Long> {
	
//	@Query(" from TdsGroupSetup where  (effectiveEndDate is null or effectiveEndDate>?1) and (effectiveStartDate is NOT null and effectiveStartDate<=?1) and companyId =?2  and financialYear.financialYearId=?3")
//    public List<TdsGroupSetup> findAllInvestment(Date today, Long companyId, Long financialYearId);
	
	@Query(" from TdsGroupSetup where companyId =?1  and financialYear.financialYearId=?2  and activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
    public List<TdsGroupSetup> findAllInvestment(Long companyId, Long financialYearId);
	
	@Query(" from TdsGroup where tdsGroupId=?1")
	public TdsGroupSetup findInvestment(long tdsGroupId);	
}
