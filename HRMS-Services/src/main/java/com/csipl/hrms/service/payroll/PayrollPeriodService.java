package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.payrollprocess.FinancialMonth;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface PayrollPeriodService {
	public ErrorHandling save(FinancialYear financialYear);
	
	public FinancialMonth getFinancialMonth(Long companyId);
	public FinancialYear findFinancialYear(String financialYear, Long companyId);

	public FinancialYear findLatestFinancialYear(Long companyId);

	public void updateById(FinancialYear financialYear);

	public List<FinancialYear> getFinancialYearList(Long companyId);
	
	 

}
