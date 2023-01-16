package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.List;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.FinalSettlement;
import com.csipl.hrms.model.payroll.FinalSettlementReport;
import com.csipl.hrms.model.payroll.Gratuaty;

public interface FinalSettlementService {

	List<Object[]> getEmployees(Long companyId);

	BigDecimal calculateGratuity(Gratuaty gratuaty,Employee employee);
	
	BigDecimal getGratuityDeduction(Long employeeId);
	
	public FinalSettlement save(FinalSettlement finalSettlemet);
	
	public FinalSettlement getFinalSettlementById(Long employeeId);

	List<Object[]> getFinalSettlementEmployee(Long companyId,String status);
	
	public List<FinalSettlementReport> saveReport(List<FinalSettlementReport> finalSettlemetList);
	public List<FinalSettlementReport> getFinalSettlementReport(Long employeeId);

	void generateLetter(Long employeeId, List<FinalSettlementReport> finalSettlementReportList);
	
}
