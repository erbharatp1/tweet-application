package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.ArearMaster;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.hrms.org.payrollprocess.dto.PayrollInputDTO;


public interface PayRollService {
	//public void processPayRoll( long companyId, String processMonth, long userId  );
	
	//public void processPayRollByDepartment( Long companyId, Long departmentId, Epf epf, Esi esi, Long userId , PayrollControl payrollControl, DrowpdownHd bankList );
	public List<PayOut> processPayRoll(long companyId, long employeeId, PayStructureHd payStructureHdForArrear,boolean isArrearCalculation) throws PayRollProcessException ;
	public List<PayOut> processPayRollForTds(long companyId, long employeeId,PayStructureHd payStructureHd ) ;
    public void processPayRollByEmployees(Long companyId, String processMonth, List<Long> EmployeeIds,long userId, boolean isArrearCalculation, PayStructureHd payStructureHdForArrear,ArearMaster arearMaster  ) throws PayRollProcessException;
    public void processPayRollByDepartments( Long companyId,String processMonth, List<Long> departmentIdList, Long userId,
			PayStructureHd payStructureHdForArrear  ) throws PayRollProcessException;
    public List<PayOut> processPayRollForEarningDeductionView(long companyId, long employeeId, PayStructureHd payStructureHd,Boolean flag) throws PayRollProcessException;
    public void employeeArrearCalculation(Long companyId, List<String> processMonthList, Long employeeId, Long userId,PayStructureHd payStructureHdForArrear) throws PayRollProcessException;;
 
    public PayrollInputDTO isPendingRequestLeaveAndARByMonth(int month);
    
  
}
 