package com.csipl.tms.leave.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;

public interface EmployeeOpenningLeaveRepository extends CrudRepository<EmployeeOpeningLeaveMaster,Long>{
	
	 @Query("  FROM EmployeeOpeningLeaveMaster where employeeId= ?1 and leaveTypeMasterId = ?2 and leavePeriodId= ?3 ") 
	    public EmployeeOpeningLeaveMaster findEmployeeOpeningBalance(Long employeeId,Long leaveType,Long leavePeriod); 

}
