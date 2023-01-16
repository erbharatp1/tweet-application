package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.SearchDTO;

public interface EmployeeLetterService {

	List<EmployeeLetter> findAllEmpLetter(Long companyId, String empStatus, String HRStatus);

	EmployeeLetter saveLtr(EmployeeLetter ltr);

	List<Object[]> findAllPendingEmployeeDocumentView();

	public EmployeeLetter findEmployeeLetterById(Long empId, Long letterId);

	List<Object[]> fetchLetterList(Long companyId);

	List<Object[]> findPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto);

	List<Object[]> findNonPendingLetterList(Long companyId, Long employeeId, SearchDTO searcDto);

	void updateById(EmployeeLettersTransaction employeeLettersTransaction);

	EmployeeLetter getEmployeeLetterById(Long employeeLetterId);

	EmployeeLetter findEmployeeLetter(Long employeeLetterId, Long employeeId, String status);

	EmployeeLetter findEmpLetterByStatus(Long employeeLetterId, Long employeeId);
	
	List<Object[]> employeeLetterByEmployeeId(Long employeeId);

	void updateAfterRealeseLetter(EmployeeLetter employeeLetter);
	
	List<Object[]> findAllEmpLetterActiveStatus(Long companyId, String status);

	EmployeeLetter generateLetter(Long companyId, Long letterId, Long employeeId);
	
	List<Object[]> findAllEmployeeLetterView(Long employeeId);
	
	EmployeeLetter findEmployeeLetterByStatus(Long empId, Long letterId);

	EmployeeLetter findEmployeeLetterByEmpLetterId(Long empId, Long letterId, Long empLetterId);

	void getLetterApprovalNonPendingCount(Long longCompanyId, Long employeeId, EntityCountDTO entityCountDto);

	List<Object[]> findPendingLetterList(Long companyId, Long employeeId);

	void updateDeclarationStatus(Long empLetterId, String string, Long empId);

	EmployeeLetter triggerDeclarationMail(EmployeeLetter letterList, String token);

	EmployeeLetter findOneEmpLetter(Long empLetterId);

}
