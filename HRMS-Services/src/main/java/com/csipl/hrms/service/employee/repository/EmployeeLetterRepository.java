package com.csipl.hrms.service.employee.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.EmployeeLetter;

@Repository
@Transactional
public interface EmployeeLetterRepository extends CrudRepository<EmployeeLetter, Long> {
	public static final String FIND_EMP_LETTER_ACTIVE_STATUS = "SELECT CONCat((CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)),LOWER(SUBSTRING(emp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName ,2)))) )as 'employeeName' ,emp.employeeCode as 'empCode',CONCat((CONCAT(UPPER(SUBSTRING(aprEmp.firstName ,1,1)),LOWER(SUBSTRING(aprEmp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(aprEmp.lastName ,1,1)),LOWER(SUBSTRING(aprEmp.lastName ,2)))) )as 'hrName',aprEmp.employeeCode as 'hrCode',el.dateCreated,ltr.letterName,el.empLetterId FROM EmployeeLetter el join Employee emp on emp.employeeId=el.empId LEFT JOIN Users us on us.userId = el.userId LEFT join Employee aprEmp on aprEmp.employeeCode = us.nameOfUser  LEFT JOIN Letter ltr  on el.letterId=ltr.letterId WHERE el.activeStatus='AC'  AND  !el.letterDecription   AND ltr.companyId=?1 AND emp.activeStatus=?2 ORDER BY dateCreated   DESC";
	// public static final String EMPLOYEE_LETTER_VIEW_MONTHWISE = "select
	// CONCat((CONCAT(UPPER(SUBSTRING(emp.firstName
	// ,1,1)),LOWER(SUBSTRING(emp.firstName ,2)))),' ',(
	// CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName
	// ,2)))) )as 'EmployeeName' ,emp.employeeCode as
	// 'empCode',CONCat((CONCAT(UPPER(SUBSTRING(aprEmp.firstName
	// ,1,1)),LOWER(SUBSTRING(aprEmp.firstName ,2)))),' ',(
	// CONCAT(UPPER(SUBSTRING(aprEmp.lastName ,1,1)),LOWER(SUBSTRING(aprEmp.lastName
	// ,2)))) )as 'HrName',aprEmp.employeeCode as
	// 'hrCode',el.dateCreated,ltr.letterName FROM EmployeeLetter el join Employee
	// emp on emp.employeeId=el.empId LEFT JOIN Users us on us.userId = el.userId
	// LEFT join Employee aprEmp on aprEmp.employeeCode = us.nameOfUser LEFT JOIN
	// Letter ltr on el.letterId=ltr.letterId WHERE el.activeStatus='AC' AND
	// el.dateCreated > DATE_SUB(now(), INTERVAL 1 MONTH) AND ltr.companyId=?1 ORDER
	// BY empLetterId DESC";
	public static final String EMPLOYEE_LETTER_VIEW_MONTHWISE = "select CONCat((CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)),LOWER(SUBSTRING(emp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName ,2)))) )as 'EmployeeName' ,emp.employeeCode as 'empCode',CONCat((CONCAT(UPPER(SUBSTRING(aprEmp.firstName ,1,1)),LOWER(SUBSTRING(aprEmp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(aprEmp.lastName ,1,1)),LOWER(SUBSTRING(aprEmp.lastName ,2)))) )as 'HrName',aprEmp.employeeCode as 'hrCode',el.dateCreated,ltr.letterName ,el.letterId ,el.empLetterId ,el.declarationStatus FROM EmployeeLetter el join Employee emp on emp.employeeId=el.empId LEFT JOIN Users us on us.userId = el.userId LEFT join Employee aprEmp on aprEmp.employeeCode = us.nameOfUser  LEFT JOIN Letter ltr  on el.letterId=ltr.letterId WHERE el.activeStatus='AC'  AND  !el.letterDecription AND el.dateCreated > DATE_SUB(now(), INTERVAL 1 MONTH)  AND ltr.companyId=?1 ORDER BY  empLetterId  DESC";
	public static final String UPDATE_BY_ID = "Update EmployeeLetter el SET el.activeStatus =:activeStatus WHERE el.empLetterId=:empLetterId";

	@Query(" from EmployeeLetter where empId=?1  and empStatus=?2 AND HRStatus=?3 ORDER BY  empLetterId  DESC")
	List<EmployeeLetter> findAllEmpLetter(Long companyId, String empStatus, String HRStatus);

//	public static final String PENDING_EMPLOYEE_DOCUMENT_VIEW = "Select empLat.empId, e.firstName, e.lastName, des.designationName,  e.employeeCode, "
//			+ " dept.departmentName, e.dateOfJoining, let.letterType, empLat.letterId, empLat.empLetterId,empLat.letterDecription ,empLat.dateUpdate, empLat.userIdUpdate, empLat.realeseStatus ,e.employeeLogoPath\r\n"
//			+ "From    EmployeeLetter empLat\r\n" + "LEFT JOIN  Employee e on e.employeeId=empLat.empId \r\n"
//			+ "LEFT JOIN Department dept on dept.departmentId=e.departmentId\r\n"
//			+ "LEFT JOIN Letter let on let.letterId=empLat.letterId\r\n"
//			+ "LEFT JOIN Designation des on des.designationId=e.designationId\r\n"
//			+ "where empLat.activeStatus='AC'  And empLat.HRStatus='PEN' AND  empLat.empStatus='PEN' ORDER BY empLat.empLetterId DESC   ";
	public static final String PENDING_EMPLOYEE_DOCUMENT_VIEW = "Select empLat.empId, e.firstName, e.lastName, des.designationName,  e.employeeCode,   dept.departmentName, e.dateOfJoining, let.letterType, empLat.letterId, empLat.empLetterId,empLat.letterDecription ,empLat.dateUpdate, empLat.userIdUpdate, empLat.realeseStatus ,e.employeeLogoPath From    EmployeeLetter empLat LEFT JOIN  Employee e on e.employeeId=empLat.empId  LEFT JOIN Department dept on dept.departmentId=e.departmentId LEFT JOIN Letter let on let.letterId=empLat.letterId LEFT JOIN Designation des on des.designationId=e.designationId where empLat.activeStatus='AC'  And empLat.HRStatus='PEN' AND  empLat.empStatus='PEN' AND let.letterType  IN(let.letterType='Absconded Letter') AND let.letterType  IN(let.letterType='Warning letter') AND let.letterType  IN(let.letterType='Appreciation Letter') ORDER BY empLat.empLetterId DESC";

	@Query(value = PENDING_EMPLOYEE_DOCUMENT_VIEW, nativeQuery = true)
	List<Object[]> findAllPendingEmployeeDocumentView();

	@Query(" from EmployeeLetter where empId=?1 AND letterId=?2 AND activeStatus='AC' ")
	EmployeeLetter findEmployeeLetterById(Long empId, Long letterId);

	@Query(value = EMPLOYEE_LETTER_VIEW_MONTHWISE, nativeQuery = true)
	List<Object[]> findAllEmpLetter(Long companyId);

	String PENDING_LETTER_LIST = "SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=?1  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=?2)  AND ah.levels ='L1'  AND el.dateUpdate IS NOT null  UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=?1  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=?2)  AND ah.levels ='L2' UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId "
			+ "WHERE el.activeStatus='AC' AND e.companyId=?1  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=?2)  AND ah.levels ='L3' UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,"
			+ "elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=?1  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=?2)  AND ah.levels ='L4' UNION SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status as approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId  LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId  LEFT JOIN EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE el.activeStatus='AC' AND e.companyId=?1  AND  ah.designationId= (SELECT e.designationId FROM Employee e WHERE e.employeeId=?2)  AND ah.levels ='L5'";

	// String PENDING_LETTER_LIST = "SELECT concat(e.firstName,' ',e.lastName) AS
	// Name ,e.employeeCode,dep.departmentName,e.dateOfJoining,
	// l.letterType,elt.status as
	// approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) as
	// desId,el.letterId,el.empId, elt.levels as
	// elt_status,elt.employeeLetterTransactionId
	// ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN
	// Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON
	// dep.departmentId = e.departmentId LEFT JOIN Designation des ON
	// des.designationId=e.designationId LEFT JOIN Letter l ON
	// l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON
	// ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON
	// ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId LEFT JOIN
	// EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND
	// ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE
	// el.activeStatus='AC' AND e.companyId=?1 AND ah.designationId= (SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) AND ah.levels ='L1'
	// ORDER BY ah.approvalHierarchyMasterId DESC UNION SELECT concat(e.firstName,'
	// ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining,
	// l.letterType,elt.status as
	// approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) as
	// desId,el.letterId,el.empId, elt.levels as
	// elt_status,elt.employeeLetterTransactionId
	// ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN
	// Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON
	// dep.departmentId = e.departmentId LEFT JOIN Designation des ON
	// des.designationId=e.designationId LEFT JOIN Letter l ON
	// l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON
	// ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON
	// ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId LEFT JOIN
	// EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND
	// ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE
	// el.activeStatus='AC' AND e.companyId=?1 AND ah.designationId= (SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) AND ah.levels ='L2'
	// ORDER BY ah.approvalHierarchyMasterId DESC UNION SELECT concat(e.firstName,'
	// ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining,
	// l.letterType,elt.status as
	// approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) as
	// desId,el.letterId,el.empId, elt.levels as
	// elt_status,elt.employeeLetterTransactionId
	// ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN
	// Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON
	// dep.departmentId = e.departmentId LEFT JOIN Designation des ON
	// des.designationId=e.designationId LEFT JOIN Letter l ON
	// l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON
	// ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON
	// ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId LEFT JOIN
	// EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND
	// ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE
	// el.activeStatus='AC' AND e.companyId=?1 AND ah.designationId= (SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) AND ah.levels ='L3'
	// ORDER BY ah.approvalHierarchyMasterId DESC UNION SELECT concat(e.firstName,'
	// ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining,
	// l.letterType,elt.status as
	// approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) as
	// desId,el.letterId,el.empId, elt.levels as
	// elt_status,elt.employeeLetterTransactionId
	// ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN
	// Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON
	// dep.departmentId = e.departmentId LEFT JOIN Designation des ON
	// des.designationId=e.designationId LEFT JOIN Letter l ON
	// l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON
	// ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON
	// ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId LEFT JOIN
	// EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND
	// ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE
	// el.activeStatus='AC' AND e.companyId=?1 AND ah.designationId= (SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) AND ah.levels ='L4'
	// ORDER BY ah.approvalHierarchyMasterId DESC UNION SELECT concat(e.firstName,'
	// ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining,
	// l.letterType,elt.status as
	// approval_Status,des.designationName,ah.designationId ,ah.levels,(SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) as
	// desId,el.letterId,el.empId, elt.levels as
	// elt_status,elt.employeeLetterTransactionId
	// ,el.empLetterId,elt.approvalId,elt.status FROM EmployeeLetter el LEFT JOIN
	// Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON
	// dep.departmentId = e.departmentId LEFT JOIN Designation des ON
	// des.designationId=e.designationId LEFT JOIN Letter l ON
	// l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON
	// ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON
	// ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId LEFT JOIN
	// EmployeeLettersTransaction elt ON elt.employeeLetterId=el.empLetterId AND
	// ah.levels=elt.levels AND ah.designationId=elt.designationId WHERE
	// el.activeStatus='AC' AND e.companyId=?1 AND ah.designationId= (SELECT
	// e.designationId FROM Employee e WHERE e.employeeId=?2) AND ah.levels ='L5'
	// ORDER BY ah.approvalHierarchyMasterId DESC";
	@Query(value = PENDING_LETTER_LIST, nativeQuery = true)
	List<Object[]> findPendingLetterList(Long companyId, Long employeeId);

	String NON_PENDING_LETTER_LIST = "SELECT concat(e.firstName,' ',e.lastName) AS Name ,e.employeeCode,dep.departmentName,e.dateOfJoining, l.letterType,elt.status AS Elt_Status,des.designationName,ah.designationId ,ah.levels,(SELECT e.designationId FROM Employee e WHERE e.employeeId=?2) as desId,el.letterId,el.empId, elt.levels as elt_status,elt.employeeLetterTransactionId ,el.empLetterId,elt.approvalId,elt.status ,concat(emp.firstName,' ',emp.lastName) AS approvedBy ,elt.dateUpdate ,elt.approvalRemarks,desi.designationName as approvedByDesignation FROM EmployeeLettersTransaction elt LEFT JOIN EmployeeLetter el ON el.empLetterId=elt.employeeLetterId  LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId AND ah.levels=elt.levels AND elt.designationId=ah.designationId LEFT JOIN Employee emp ON emp.employeeId=elt.approvalId  LEFT JOIN Designation desi ON desi.designationId=elt.designationId  WHERE e.companyId=?1 AND elt.designationId=(SELECT emp.designationId FROM Employee emp WHERE emp.employeeId=?2) AND elt.status !='PEN'";

	// String NON_PENDING_LETTER_LIST = "";
	@Query(value = NON_PENDING_LETTER_LIST, nativeQuery = true)
	List<Object[]> findNonPendingLetterList(Long companyId, Long employeeId);

	@Query(value = " from EmployeeLetter el WHERE el.empId=?2 AND el.empLetterId=?1")
	EmployeeLetter findEmployeeLetter(Long employeeLetterId, Long employeeId);

	@Query(value = " SELECT el.* FROM  EmployeeLetter el LEFT JOIN EmployeeLettersTransaction et on el.empLetterId=et.employeeLetterId and   et.levels='"
			+ StatusMessage.LEVEL_CODE
			+ "' WHERE el.empLetterId=?1 AND el.letterId=?2 AND et.approvalId is null", nativeQuery = true)
	EmployeeLetter findEmpLetterByStatus(Long employeeLetterId, Long letterId);

	@Query(value = "SELECT  ltr.letterType, el.empId,el.letterId,el.empLetterId ,el.dateCreated FROM EmployeeLetter el LEFT JOIN Letter ltr on el.letterId=ltr.letterId WHERE el.empId=?1 AND el.HRStatus='APR' AND el.activeStatus='AC'ORDER BY el.empLetterId DESC", nativeQuery = true)
	List<Object[]> findEmployeeLetterByEmployeeId(Long employeeId);

	@Modifying
	@Query(value = UPDATE_BY_ID)
	void updateById(@Param("empLetterId") Long empLetterId, @Param("activeStatus") String activeStatus);

	@Query(value = FIND_EMP_LETTER_ACTIVE_STATUS, nativeQuery = true)
	List<Object[]> findAllEmpLetterActiveStatus(Long companyId, String activeStatus);

	@Query(value = "select CONCAT((CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)),LOWER(SUBSTRING(emp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),LOWER(SUBSTRING(emp.lastName ,2)))) )as 'EmployeeName' ,emp.employeeCode as 'empCode',CONCat((CONCAT(UPPER(SUBSTRING(aprEmp.firstName ,1,1)),LOWER(SUBSTRING(aprEmp.firstName ,2)))),' ',( CONCAT(UPPER(SUBSTRING(aprEmp.lastName ,1,1)),LOWER(SUBSTRING(aprEmp.lastName ,2)))) )as 'HrName',aprEmp.employeeCode as 'hrCode',el.dateCreated,ltr.letterName,el.letterId ,el.empLetterId ,el.declarationStatus FROM EmployeeLetter el join Employee emp on emp.employeeId=el.empId LEFT JOIN Users us on us.userId = el.userId LEFT join Employee aprEmp on aprEmp.employeeCode = us.nameOfUser  LEFT JOIN Letter ltr  on el.letterId=ltr.letterId WHERE el.activeStatus='AC' AND el.HRStatus='APR' AND   el.empId=?1  ORDER BY el.empLetterId DESC", nativeQuery = true)
	List<Object[]> findAllEmployeeLetterView(Long employeeId);

	@Query(" from EmployeeLetter where empId=?1 AND letterId=?2 AND activeStatus='AC' AND HRStatus='PEN' ")
	EmployeeLetter findEmployeeLetterByStatus(Long empId, Long letterId);

	@Query(" from EmployeeLetter where empId=?1 AND letterId=?2 AND empLetterId=?3 AND activeStatus='AC' ")
	EmployeeLetter findEmployeeLetterByEmpLetterId(Long empId, Long letterId, Long empLetterId);

	public static final String LETTER_APPROVAL_NON_PENDING_COUNT = " SELECT COUNT(*) FROM EmployeeLettersTransaction elt LEFT JOIN EmployeeLetter el ON el.empLetterId=elt.employeeLetterId  LEFT JOIN Employee e ON el.empId=e.employeeId LEFT JOIN Department dep ON dep.departmentId = e.departmentId LEFT JOIN Designation des ON des.designationId=e.designationId  LEFT JOIN Letter l ON l.letterId=el.letterId LEFT JOIN ApprovalHierarchyMaster ahm ON ahm.letterId=el.letterId LEFT JOIN ApprovalHierarchy ah ON ah.approvalHierarchyMasterId=ahm.approvalHierarchyMasterId AND ah.levels=elt.levels AND elt.designationId=ah.designationId LEFT JOIN Employee emp ON emp.employeeId=elt.approvalId  LEFT JOIN Designation desi ON desi.designationId=elt.designationId  WHERE e.companyId=?1 AND elt.designationId=(SELECT emp.designationId FROM Employee emp WHERE emp.employeeId=?2) AND elt.status !='PEN' ";
		@Query(nativeQuery = true, value = LETTER_APPROVAL_NON_PENDING_COUNT)
	int getLetterApprovalNonPendingCount(Long longCompanyId, Long employeeId);

	public static final String UPDATE_DECLARATION_STATUS = "UPDATE EmployeeLetter SET declarationDate = :declarationDate , declarationStatus =:declarationStatus WHERE empLetterId =:empLetterId AND empId=:empId" ;
	
	@Modifying
	@Query(value = UPDATE_DECLARATION_STATUS)
	void updateDeclarationStatus(@Param("empLetterId")Long empLetterId, @Param("declarationStatus")String declarationStatus,@Param("empId") Long empId, @Param("declarationDate")Date declarationDate);


}
