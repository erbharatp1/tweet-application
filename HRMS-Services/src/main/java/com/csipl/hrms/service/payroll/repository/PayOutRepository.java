package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payrollprocess.PayOut;



public interface PayOutRepository extends CrudRepository<PayOut, Long>  {
	String employeeArrearCalculationOnPayOut = "SELECT  employeeId,payHeadId,processMonth,amount FROM PayOut WHERE employeeId=?1 and processMonth=?2";
	@Query(value = employeeArrearCalculationOnPayOut, nativeQuery = true)
	List<Object[]> employeeArrearCalculationOnPayOut(Long employeeId, String processMonth);

	String getPayOutsBasedOnProcessMonthAndEmployeeId = "SELECT  po.employeeId,po.payHeadId,po.processMonth,po.amount,ph.payHeadName,ph.earningDeduction FROM PayOut po JOIN PayHeads ph ON po.payHeadId=ph.payHeadId WHERE employeeId=?1 and processMonth=?2";
	@Query(value = getPayOutsBasedOnProcessMonthAndEmployeeId, nativeQuery = true)
	List<Object[]> getPayOutsBasedOnProcessMonthAndEmployeeId(Long employeeId, String processMonth);
	
	
	String getloadID = "select loanId FROM PayOut where employeeId =?1 and processMonth=?2 and LoanID IS not null";
	@Query(value = getloadID, nativeQuery = true) 
	public String  getLoanID(Long employeeId,String processMonth);
}
