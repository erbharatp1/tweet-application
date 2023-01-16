package com.csipl.tms.leave.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.tms.model.leave.EmployeeLeaveEarn;

public interface EmployeeLeaveEarnRepository extends CrudRepository<EmployeeLeaveEarn, Long> {
	
	public static final String PAYROLL_LEAVE_AR = "CALL 	pro_payroll_input_leave_ar( :p_comp_id,:p_process_month,:p_process_year) ";

	
	@Query(" from EmployeeLeaveEarn where companyId=?1")
	public ArrayList<EmployeeLeaveEarn> leaveTypeList(Long companyId);

	 @Modifying
	@Query("UPDATE EmployeeLeaveEarn SET leaveTaken=leaveTaken+?4 WHERE financialYear=?3 AND leaveTypeId=?2 AND employeeId=?1")
	public void updateEmplyeeLeaveEarn(Long employeeId, Long leaveTypeId, String financialYear, Long days);
	 
				@Query(value = PAYROLL_LEAVE_AR, nativeQuery = true)
		public List<Object[]> getpayrollLeaveAndAr(@Param(value = "p_comp_id") Long p_comp_id,@Param(value = "p_process_month") String p_process_month,@Param(value = "p_process_year") String p_process_year);
		
 }
