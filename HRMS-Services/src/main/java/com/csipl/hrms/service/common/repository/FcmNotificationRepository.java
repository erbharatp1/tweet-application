package com.csipl.hrms.service.common.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.employee.Employee;

public interface FcmNotificationRepository  extends CrudRepository<Employee, Long> {

	String reportingTo="SELECT (SELECT us.userId from Users us JOIN Employee e ON us.nameOfUser=e.employeeCode WHERE e.employeeId=emp.ReportingToEmployee ) as userId from Employee emp WHERE emp.employeeId=?1";
	@Query(value=reportingTo,nativeQuery = true)
	public Long getEmpReportingToUserId(Long employeeId);
	
	
	@Query(value="SELECT us.userId FROM Employee emp JOIN Users us ON emp.employeeCode=us.nameOfUser WHERE emp.employeeId=?1",nativeQuery = true)
	public Long getUserId(Long employeeId);
	
	@Query("SELECT tokenValue FROM TokenMaster WHERE userId=?1 ")
	ArrayList<String> getTokenList(Long userId);
//	SELECT tokenValue FROM `TokenMaster` WHERE userId=196
}

