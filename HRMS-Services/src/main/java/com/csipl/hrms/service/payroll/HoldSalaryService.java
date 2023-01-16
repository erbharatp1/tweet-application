package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
import com.csipl.hrms.model.payroll.HoldSalary;



public interface HoldSalaryService {
	public HoldSalary save(HoldSalary holdSalary);
	public List<Object[]> findAllHoldSalary(Long companyId,String payrollMonth);
	public HoldSalary findHoldSalaryById( Long holdSalaryId);
	public void deleteById(Long holdSalaryId);
	List<Object[]> holdSalarySearch( HoldSalaryDTO holdSalarySearchDto);
	public List<HoldSalary> searchEmployeeHoldDetails(Long employeeId);
}
