package com.csipl.hrms.service.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.service.employee.repository.EmployeeLettersTransactionRepository;

@Service
@Transactional
public class EmployeeLettersTransactionServiceImpl implements EmployeeLettersTransactionService {

	@Autowired
	private EmployeeLettersTransactionRepository employeeLettersTransactionRepository;

	@Override
	public EmployeeLettersTransaction saveEmpLetterTransaction(EmployeeLettersTransaction employeeLettersTransaction) {
		// TODO Auto-generated method stub
		return employeeLettersTransactionRepository.save(employeeLettersTransaction);
	}

	@Override
	public List<EmployeeLettersTransaction> findAllEmpLetterTransactionList() {
		// TODO Auto-generated method stub
		return employeeLettersTransactionRepository.findAllList();
	}

	@Override
	public List<EmployeeLettersTransaction> findAllApprovalStatus(Long companyId, Long letterId, Long employeeId) {
		List<Object[]> objects = employeeLettersTransactionRepository.findAllApprovalStatus(companyId, letterId,
				employeeId);
		List<EmployeeLettersTransaction> lettersTransactions = new ArrayList<EmployeeLettersTransaction>();
		for (Object[] objects2 : objects) {
			EmployeeLettersTransaction transaction = new EmployeeLettersTransaction();

			Long employeeLetterTransactionId = objects2[0] != null ? Long.parseLong(objects2[0].toString()) : null;
			Long employeeLetterId = objects2[1] != null ? Long.parseLong(objects2[1].toString()) : null;
			Long approveId = objects2[2] != null ? Long.parseLong(objects2[2].toString()) : null;
			Long designationId = objects2[3] != null ? Long.parseLong(objects2[3].toString()) : null;
			String status = objects2[4] != null ? (String) objects2[4] : null;
			String lavels = objects2[5] != null ? (String) objects2[5] : null;
			String designationName = objects2[6] != null ? (String) objects2[6] : null;
			transaction.setEmployeeLetterTransactionId(employeeLetterTransactionId);
			EmployeeLetter letter = new EmployeeLetter();
			letter.setLetterId(employeeLetterId);
			transaction.setEmployeeLetter(letter);
			transaction.setApprovalId(approveId);
			transaction.setDesignationId(designationId);
			transaction.setStatus(status);
			transaction.setLevels(lavels);
			transaction.setDesignationName(designationName);
			lettersTransactions.add(transaction);
		}
		return lettersTransactions;
	}

}
