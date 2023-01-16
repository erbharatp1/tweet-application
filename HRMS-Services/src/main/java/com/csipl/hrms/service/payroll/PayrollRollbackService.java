package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;

public interface PayrollRollbackService {

	
	//by nidhi for rollback payroll
		public List<Object[]> getPayRollRegisterProcessMonth (Long companyID );
		public List<Object[]> getPayrollRegisterListbyHdId(Long Id, String processMonth);
		List<Object[]> fetchEmployeePayrollRegisterListForRollback(Long hdId);
		public void processRollbackPayroll(List<Long> EmployeeIds, String processMonth,Long payReghdId)throws PayRollProcessException, ErrorHandling, Exception ;
		
}
