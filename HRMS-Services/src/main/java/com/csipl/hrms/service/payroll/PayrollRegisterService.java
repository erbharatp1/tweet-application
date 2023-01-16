package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.payrollprocess.PayRegisterHd;

public interface PayrollRegisterService {
	public void save (PayRegisterHd payRegisterHd);
	public List<PayRegisterHd> getPayrollRegisterList (Long companyID,String payrollMonth);
	public List<Object[]> getPayrollRegisterListBypayMonth (Long companyID,String payrollMonth);
	public List<Object[]> getPayrollRegisterListById (Long Id,String processMonth);
	public List<Object[]> getPayrollRegisterListForPaymentTransfer(Long Id,String processMonth);
	public int updatePayrollLockFlagInPayRegister(List<Long> employeeId, String processMonth);
	public List<Object[]> getSalarySheetByHd(Long longcompanyId, Long id, String processMonth);

	
}
