package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.OneTimeDeduction;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;
import com.csipl.hrms.model.payroll.PayHead;

public interface OneTimeEarningDeductionRepository  extends CrudRepository<OneTimeEarningDeduction, Long>{
	@Query(" from OneTimeEarningDeduction oe where oe.earningDeductionMonth=?2 AND oe.companyId=?1  AND oe.type ='EA' and oe.employeeId NOT IN (SELECT rp.employee.employeeId from ReportPayOut rp where  rp.employee.employeeId =oe.employeeId and rp.id.processMonth=?2 )  ")
    public List<OneTimeEarningDeduction> findOneTimeEarningList(Long  companyId,String payrollMonth);
	
	@Query(" from OneTimeEarningDeduction oe where oe.earningDeductionMonth=?2 AND oe.companyId=?1  AND oe.type ='DE' and oe.employeeId NOT IN (SELECT rp.employee.employeeId from ReportPayOut rp where  rp.employee.employeeId =oe.employeeId and rp.id.processMonth=?2 )  ")
    public List<OneTimeEarningDeduction> findOneTimeDeductionList(Long  companyId,String payrollMonth);
	
	String findOneTimeDeductionForEmployee =" from  OneTimeEarningDeduction otd where otd.employeeId=?1 and otd.earningDeductionMonth=?2 AND type ='DE'   ";
	@Query( findOneTimeDeductionForEmployee )
	public List<OneTimeEarningDeduction>  findOneTimeDeductionForEmployee(Long employeeId, String deductionMonth);
	
	
	String findOneTimeEarningForEmployee =" from  OneTimeEarningDeduction otd where otd.employeeId=?1 and otd.earningDeductionMonth=?2 AND type ='EA'   ";
	@Query( findOneTimeEarningForEmployee )
	public List<OneTimeEarningDeduction>  findOneTimeEarningForEmployee(Long employeeId, String earningMonth);
}
