package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.payroll.TdsDeduction;

public interface TdsDeductionService {
 
	public TdsDeduction save(TdsDeduction tdsDeduction);
	
	public List<Object[]> findAlltdsDeduction(Long companyId,Long financialYearId);
	
	public List<Object[]> findAllEmployee(EmployeeSearchDTO employeeSearchDto);
	
	public List<Object[]> findAllEmployeesWithTdsStatus(EmployeeSearchDTO employeeSearchDto, Long financialYearId);
	
	public  TdsDeduction getTdsDeduction(Long companyId, Long financialYearId, Long EmployeeId);
	
	public int updateTdsLockUnlockStatus(List<EmployeeDTO> employeeDTOList,String  tdsLockUnlockStatus);

	public String getTdsLockUnlockStatus(Long companyId, Long employeeId);

	public List<Object[]> getTdsSummary(Long companyId, Long financialYearId, Long employeeId);
	
	public void resetTdsScheme(List<EmployeeDTO> employeeDTOList);
	
}
