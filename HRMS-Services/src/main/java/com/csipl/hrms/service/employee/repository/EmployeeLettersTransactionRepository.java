package com.csipl.hrms.service.employee.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.EmployeeLettersTransaction;

@Repository
@Transactional
public interface EmployeeLettersTransactionRepository extends CrudRepository<EmployeeLettersTransaction, Long> {
	public static final String UPDATE_BY_ID = "Update EmployeeLettersTransaction elt SET elt.status=:status , elt.approvalRemarks=:approvalRemarks,elt.approvalId=:approvalId,elt.dateUpdate=:dateUpdate WHERE elt.employeeLetterTransactionId=:employeeLetterTransactionId";
	public static final String FIND_ALL_LIST = " from EmployeeLettersTransaction";
	public static final String FIND_BY_LETTER_ID = " from EmployeeLettersTransaction et WHERE et.employeeLetter.empLetterId=?1 AND et.status='PEN' AND et.levels='L1'";
 
	public static final String FIND_ALL_APPROVAL_STATUS = "SELECT et.employeeLetterTransactionId,et.employeeLetterId,et.approvalId,desi.designationId,et.status,ah.levels as 'approvalLevel' ,desi.designationName FROM EmployeeLetter el LEFT JOIN ApprovalHierarchyMaster ahm  on el.letterId =ahm.letterId LEFT JOIN ApprovalHierarchy ah on ahm.approvalHierarchyMasterId=ah.approvalHierarchyMasterId LEFT JOIN EmployeeLettersTransaction et on el.empLetterId=et.employeeLetterId and ah.levels=et.levels LEFT JOIN Designation desi on desi.designationId=ah.designationId  WHERE ahm.companyId=?1 and el.letterId=?2 AND el.empId=?3 AND el.activeStatus='AC' ";
 
	public static final String FIND_EMPLETTER_BY_STATUS = "";

 
	@Modifying
	@Query(value = UPDATE_BY_ID)
	void updateById(@Param("employeeLetterTransactionId") Long employeeLetterTransactionId,
			@Param("status") String status, @Param("approvalRemarks") String approvalRemarks,
			@Param("approvalId") Long approvalId, @Param("dateUpdate") Date dateUpdate);

	@Query(value = FIND_ALL_LIST)
	List<EmployeeLettersTransaction> findAllList();

	@Query(value = FIND_BY_LETTER_ID)
	EmployeeLettersTransaction findByLetterId(Long employeeLetterId);
 
	
	@Query(value = FIND_ALL_APPROVAL_STATUS,nativeQuery = true)
	List<Object[]> findAllApprovalStatus(Long companyId,Long letterId,Long employeeId);

 

	@Query(value = FIND_EMPLETTER_BY_STATUS)
	EmployeeLettersTransaction findEmpLetterByStatus(Long employeeLetterId);
	
	@Query(value = " from EmployeeLettersTransaction et WHERE et.employeeLetter.empLetterId=?1")
	List<EmployeeLettersTransaction> findByLetterList(Long empLetterId);
 
}
