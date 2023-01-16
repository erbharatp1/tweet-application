package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.employee.EmployeeBank;


public interface BankDetailsService {
	public List<EmployeeBank>  saveAll(List<EmployeeBank> employeeBankList)throws ErrorHandling;
 	public List<EmployeeBank> findAllBankDetails(String empId);
	 public EmployeeBank save(EmployeeBank employeeBank)throws ErrorHandling;
	 public EmployeeBank update(EmployeeBank employeeBank);
	 public void delete(Long empBankId);
	 public EmployeeBank findBankDetails(Long employeeId);
}
