package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payrollprocess.PayOutDTO;

public interface PayOutService {
	public List<PayOutDTO>getPayOutsBasedOnProcessMonthAndEmployeeId(Long employeeId,String processMonth);
	
}
