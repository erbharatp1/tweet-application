package com.csipl.hrms.service.payroll;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.hrms.org.payrollprocess.dto.DepartmentProcessDTO;

public interface ProcessPayRollInformationService  {

 
	public List<DepartmentProcessDTO> processPayRollinformation(Long[] departmentId, String processMonth,Long companyId)throws PayRollProcessException, ErrorHandling, Exception ;
	
	public DepartmentProcessDTO getPayRollOverview(String processMonth, Long companyId, Date from, Date toDate);
}