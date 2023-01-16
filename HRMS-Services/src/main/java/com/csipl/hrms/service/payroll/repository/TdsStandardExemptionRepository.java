package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.TdsStandardExemption;

public interface TdsStandardExemptionRepository extends CrudRepository<TdsStandardExemption, Long> {

	@Query(" from TdsStandardExemption where companyId =?1")
    public List<TdsStandardExemption> findAllTdsStandardExemption(Long companyId);
	
	@Query(" from TdsStandardExemption where companyId =?1 and financialYear.financialYearId =?2 and activeStatus='"+StatusMessage.ACTIVE_CODE+"' ")
    public TdsStandardExemption findTdsStandardExemption(Long companyId, Long financialYearId);
}
