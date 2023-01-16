package com.csipl.hrms.service.payroll;

import java.util.List;
import com.csipl.hrms.model.payroll.TdsGroup;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface InvestmentService {

	public List<TdsGroupSetup> getInvestmentList( Long companyId, FinancialYear financialYear);
	public TdsGroup getInvestment(long tdsGroupId);
	public void save(TdsGroup tdsGroup);
}
