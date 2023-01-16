package com.csipl.hrms.service.report.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.employee.EmployeeAsset;


public interface MiscellaneousReportRepository extends CrudRepository<EmployeeAsset, Long>  {

	String AllAssetAllocation="Select e.employeeCode,  CONCAT(e.firstName, ' ', e.lastName) as employeeName, "
			+ "des.designationName, dp.departmentName, im.itemName, ea.issueDescription, ea.dateFrom, ea.dateTo, ea.recievedRemark  FROM Employee e LEFT JOIN "
			+ " Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des  ON des.designationId= e.designationId \r\n" + 
			" JOIN EmployeeAssets ea ON ea.employeeId=e.employeeId   LEFT JOIN Item im ON im.itemId=ea.itemId Where e.companyId=?1";
	@Query(value = AllAssetAllocation, nativeQuery = true)

	List<Object[]> findAllAssetAllocation(Long companyId);

	
	String RunningAssetAllocation=" Select e.employeeCode,  CONCAT(e.firstName, ' ', e.lastName) as employeeName,\r\n" + 
			"	 des.designationName, dp.departmentName, im.itemName, ea.issueDescription, ea.dateFrom, ea.dateTo, ea.recievedRemark  FROM Employee e \r\n"
			+ " LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des  ON des.designationId= e.designationId \r\n"
			+ "	 JOIN EmployeeAssets ea ON ea.employeeId=e.employeeId\r\n"
			+ "   LEFT JOIN Item im ON im.itemId=ea.itemId\r\n" + "   "
			+ "  Where e.companyId=?1  and ea.dateTo IS NULL";
	
	@Query(value = RunningAssetAllocation, nativeQuery = true)
	List<Object[]> findRunningAssetAllocation(Long companyId);

	String SetteledAssetAllocation="Select e.employeeCode,  CONCAT(e.firstName, ' ', e.lastName) as employeeName,"
			+ " des.designationName, dp.departmentName, im.itemName, ea.issueDescription, ea.dateFrom, ea.dateTo, ea.recievedRemark "
			+ " FROM Employee e LEFT JOIN Department dp ON dp.departmentId=e.departmentId  LEFT JOIN Designation des  ON des.designationId= e.designationId \r\n" + 
			"  JOIN EmployeeAssets ea ON ea.employeeId=e.employeeId  LEFT JOIN Item im ON im.itemId=ea.itemId  Where e.companyId=?1  and ea.dateTo IS NOT NULL";
	
	@Query(value = SetteledAssetAllocation, nativeQuery = true)

	List<Object[]> findSetteledAssetAllocation(Long companyId);
	
	String CurrentRoleSummery = "select emp.employeeCode, CONCAT(emp.firstName, ' ', emp.lastName) as employeeName,"
			+ "des.designationName, dep.departmentName ,rm.roleDescription,emp.activeStatus From Employee emp "
			+ "LEFT JOIN Designation des on des.designationId=emp.designationId LEFT JOIN Department dep on "
			+ "dep.departmentId=emp.departmentId LEFT JOIN Users user On user.nameOfUser = emp.employeeCode "
			+ "LEFT JOIN  UserRoles ur on ur.userId=user.userId LEFT JOIN RoleMaster rm on rm.roleId=ur.roleId "
			+ "where emp.companyId=?1 and emp.activeStatus=?2 or emp.activeStatus=?3 GROUP BY emp.employeeId "
			+ "ORDER By emp.firstName Asc";

	@Query(value = CurrentRoleSummery, nativeQuery = true)
	List<Object[]> findAllCurrentRoleSummary(Long companyId, String status1, String status2);

	String TicketSummary = "select tds.ticketRaisingHDId ,emp.employeeCode, CONCAT(emp.firstName, ' ', emp.lastName) as employeeName, "
			+ "	des.designationName, dep.departmentName,tr.ticketNo,tt.category,tr.dateCreated,"
			+ "	tr.status,tr.dateUpdate,concat(em.firstName,' ',em.lastName,'(',em.employeeCode,')-',tds.description) as comment "
			+ "	From Employee emp LEFT JOIN Designation des on des.designationId=emp.designationId "
			+ "	LEFT JOIN Department dep on  dep.departmentId=emp.departmentId "
			+ "	LEFT JOIN TicketRaisingHD tr on tr.employeeId=emp.employeeId "
			+ "	LEFT JOIN TicketType tt on tt.ticketTypeId=tr.ticketTypeId  "
			+ "	LEFT JOIN TicketDesc tds on  tds.ticketRaisingHDId=tr.ticketRaisingHDId  "
			+ "	LEFT JOIN Employee em ON tds.employeeId=em.employeeId "
			+ "	WHERE emp.companyId=?1 and tr.dateCreated>=?2 and  tr.dateUpdate<=?3 and tr.ticketNo IS  NOT NULL  ORDER BY tr.ticketRaisingHDId DESC";

	@Query(value = TicketSummary, nativeQuery = true)
	List<Object[]> findAllTicketSummary(Long companyId, Date startDate, Date endDate);


}
