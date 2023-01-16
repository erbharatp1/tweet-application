package com.csipl.hrms.service.employee;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
  
public interface PayStructureService {
	public PayStructureHd save(PayStructureHd payStructureHd,Long companyId, List<String> list)  throws ErrorHandling, PayRollProcessException;
	public void  updateCTC( PayStructureHd payStructureHd, List<PayOut> payOutList );
	
	public PayStructureHd employeeCurrentPayStructure(Long employeeId);
	public PayStructureHd findPayStructureForPayroll(Long employeeId,String processMonth);
	
	public List<PayOut> processPayroll(Long companyId,Long employeeId, PayStructureHd payStructureHdForArrear,
			boolean isArrearCalculation) throws PayRollProcessException;
	public List<PayOut>  processPayrollForTds(Long companyId,Long employeeId,PayStructureHd payStructureHd);
 	public PayStructureHd getPayStructureHd(Long payStructureHdId);
	public void saveAll(List<PayStructureHd> payStructureHdList,Long empId,Long companyId ,PayStructureHd payStructure);
 	public void deletePayRevision(Long payStructureHdId);
	public	void deleteFuturePayStructure(Long employeeId);
	public List<PayStructureHd> getEmployeePayRevisionList(Long employeeId);
	public PayStructureHd getPayStructure(Long longPayStructureHdId);
	public List<Object[]> getEmployees(Long companyId);
 	public List<String> arrearCalculationMonths(String processMonth, Long employeeId);
 	public PayStructureHd getEndDateNullPayStructure(Long employeeId);
 	public PayStructureHd findPayStructureOnDate(Date subtractDays, Long employeeId);
  	public List<PayOut> processPayrollForEarningDeductionView(Long companyId, Long employeeId, PayStructureHd payStructureHd,Boolean flag,Boolean exitsPaystructurePayheadsFlag) throws PayRollProcessException;
  	public PayStructureHdDTO calculateEarningDeduction(Long companyId, Long employeeId, PayStructureHd payStructureHd,Boolean flag,Boolean exitsPaystructurePayheadsFlag) throws PayRollProcessException;
  	public List<Integer> getPayStructureHdEmployeeId(Long companyId);
	public void letterGenerater(PayStructureHd payStructureHd, Long companyId, List<String> processMonthList);
	public void generateLetters(Long companyId, Long employeeId, Long employeeLetterId) throws ErrorHandling;
	
	public List<ReportPayOut> findReportPayoutForMonth(Date attendanceDate , Long companyId);
}
