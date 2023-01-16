package com.csipl.hrms.service.payroll.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.payroll.ArearMaster;



public interface ArearMasterRepository extends CrudRepository<ArearMaster,Long>{
	
	//and am.isBooked=0
	  @Query("from ArearMaster am where   am.employee.employeeId=?1 and am.bookedPayrollMonth=?2 and am.isBooked=1 ")
	  public ArearMaster findArearCalculationByemployeeIdAndProcessMonth(Long employeeId ,String processMonth);
	   
	  @Query(nativeQuery=true, value="SELECT COUNT(*) FROM ArearMaster WHERE employeeId=?1 and isBooked = 0")
	   public int getArear(Long employeeId);
	
	  @Query(nativeQuery=true, value="DELETE FROM `ArrearReportPayOut`  WHERE `processMonth` IN (select ac.payrollMonth from ArearCalculation ac where arearId =?2) and employeeId =?1")
	  @Modifying
	  public int deleteArearReportPayout(Long employeeId,Long arearId);
	  
	  @Query(nativeQuery=true, value="DELETE FROM `ArrearPayOut`  WHERE `processMonth` IN (select ac.payrollMonth from ArearCalculation ac where arearId =?2) and employeeId =?1")
	  @Modifying 
	  public int deleteArearPayout(Long employeeId,Long arearId);
}
