package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface OtherIncomeService {
	public List<OtherIncome> save(List<OtherIncome> otherIncomeList, Long companyId, HttpServletRequest req);
	public List<OtherIncome> findOtherIncomes(Long employeeId, Long companyId, FinancialYear financialYear);
	public int deleteOtherIncome(Long otherIncomeId);
	public BigDecimal getTotalOtherIncome(Long employeeId, Long companyId);
}
