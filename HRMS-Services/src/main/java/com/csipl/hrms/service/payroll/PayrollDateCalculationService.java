package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.InvestmentFinancialYearDTO;

public interface PayrollDateCalculationService {
	public List<Object[]> employeeSalarySlipProcessMonthLastSix(Long employeeId, Long companyId);
}
