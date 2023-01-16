package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;

public interface OneTimeEarningDeductionService {
	public void saveAll(List<OneTimeEarningDeduction> oneTimeEarningDeductionlist);
	public List<OneTimeEarningDeduction> getOneTimeEarningList (Long companyID,String payrollMonth);
	public List<OneTimeEarningDeduction> getOneTimeDeductionList (Long companyID,String payrollMonth);
	public List<OneTimeEarningDeduction> findOneTimeDeductionForEmployee(Long employeeId, String deductionMonth);
	public List<OneTimeEarningDeduction> findOneTimeEarningForEmployee(Long employeeId, String deductionMonth);
	
	public OneTimeEarningDeduction delete(Long ID);
}

