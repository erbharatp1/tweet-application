package com.csipl.hrms.service.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.EmployeeLettersTransactionDTO;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;

@Component
public class EmployeeLettersTransactionAdaptor
		implements Adaptor<EmployeeLettersTransactionDTO, EmployeeLettersTransaction> {

	@Override
	public List<EmployeeLettersTransaction> uiDtoToDatabaseModelList(List<EmployeeLettersTransactionDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeLettersTransactionDTO> databaseModelToUiDtoList(List<EmployeeLettersTransaction> dbobj) {
		// TODO Auto-generated method stub
		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public EmployeeLettersTransaction uiDtoToDatabaseModel(
			EmployeeLettersTransactionDTO employeeLettersTransactionDTO) {

		EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();

		employeeLettersTransaction.setApprovalId(employeeLettersTransactionDTO.getApprovalId());
		employeeLettersTransaction.setLevels(employeeLettersTransactionDTO.getLevels());
		employeeLettersTransaction.setDesignationId(employeeLettersTransactionDTO.getDesignationId());
		employeeLettersTransaction.setStatus(employeeLettersTransactionDTO.getStatus());
		employeeLettersTransaction.setApprovalRemarks(employeeLettersTransactionDTO.getApprovalRemarks());
		employeeLettersTransaction.setCompanyId(employeeLettersTransactionDTO.getCompanyId());
		employeeLettersTransaction.setUserId(employeeLettersTransactionDTO.getUserId());
		employeeLettersTransaction.setDateCreated(employeeLettersTransactionDTO.getDateCreated());
		employeeLettersTransaction.setDateUpdate(employeeLettersTransactionDTO.getDateUpdate());

		employeeLettersTransaction
				.setEmployeeLetterTransactionId(employeeLettersTransactionDTO.getEmployeeLetterTransactionId());
		employeeLettersTransaction.setUserId(employeeLettersTransactionDTO.getUserId());
		employeeLettersTransaction.setUserIdUpdate(employeeLettersTransactionDTO.getUserIdUpdate());

		return employeeLettersTransaction;
	}

	@Override
	public EmployeeLettersTransactionDTO databaseModelToUiDto(EmployeeLettersTransaction employeeLettersTransaction) {
		EmployeeLettersTransactionDTO empLettersTransactionDTO = new EmployeeLettersTransactionDTO();
		empLettersTransactionDTO.setApprovalLevel(employeeLettersTransaction.getApprovalLevel());
		empLettersTransactionDTO.setApprovalId(employeeLettersTransaction.getApprovalId());
		empLettersTransactionDTO.setLevels(employeeLettersTransaction.getLevels());
		empLettersTransactionDTO.setDesignationId(employeeLettersTransaction.getDesignationId());
		empLettersTransactionDTO.setDesignationName(employeeLettersTransaction.getDesignationName());
		if (employeeLettersTransaction.getStatus().equalsIgnoreCase("PEN")) {
			empLettersTransactionDTO.setStatus(StatusMessage.AWAITED_VALUE);
		}
		if (employeeLettersTransaction.getStatus().equalsIgnoreCase("APR")) {
			empLettersTransactionDTO.setStatus(StatusMessage.APPROVED_VALUE);
		}
		if (employeeLettersTransaction.getStatus().equalsIgnoreCase("REJ")) {
			empLettersTransactionDTO.setStatus(StatusMessage.REJECTED_VALUE);
		}
		else
			empLettersTransactionDTO.setStatus(StatusMessage.AWAITED_VALUE);
		empLettersTransactionDTO.setApprovalRemarks(employeeLettersTransaction.getApprovalRemarks());
		empLettersTransactionDTO.setCompanyId(employeeLettersTransaction.getCompanyId());
		empLettersTransactionDTO.setUserId(employeeLettersTransaction.getUserId());
		empLettersTransactionDTO.setDateCreated(employeeLettersTransaction.getDateCreated());
		empLettersTransactionDTO.setDateUpdate(employeeLettersTransaction.getDateUpdate());
		empLettersTransactionDTO
				.setEmployeeLetterTransactionId(employeeLettersTransaction.getEmployeeLetterTransactionId());
		empLettersTransactionDTO.setUserId(employeeLettersTransaction.getUserId());
		empLettersTransactionDTO.setUserIdUpdate(employeeLettersTransaction.getUserIdUpdate());

		return empLettersTransactionDTO;
	}

}
