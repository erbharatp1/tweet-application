package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.EmployeeExpenseClaimDTO;
import com.csipl.hrms.dto.employee.ExpenseTypeDTO;
import com.csipl.hrms.model.employee.EmployeeExpenseClaim;
import com.csipl.hrms.model.employee.ExpenseType;

@Component
public class EmployeeExpenseClaimsAdaptor implements Adaptor<EmployeeExpenseClaimDTO, EmployeeExpenseClaim> {

	@Override
	public List<EmployeeExpenseClaim> uiDtoToDatabaseModelList(List<EmployeeExpenseClaimDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeExpenseClaimDTO> databaseModelToUiDtoList(List<EmployeeExpenseClaim> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public EmployeeExpenseClaim uiDtoToDatabaseModel(EmployeeExpenseClaimDTO uiobj) {
		EmployeeExpenseClaim claim = new EmployeeExpenseClaim();
		claim.setAmountExpenses(uiobj.getAmountExpenses());
		claim.setApprovalDate(uiobj.getApprovalDate());
		claim.setApprovalRemark(uiobj.getApprovalRemark());
		claim.setBillNumber(uiobj.getBillNumber());
		claim.setClaimDate(uiobj.getClaimDate());
		claim.setCompanyId(uiobj.getCompanyId());
		claim.setDateCreated(uiobj.getDateCreated());
		claim.setDateUpdate(uiobj.getDateUpdate());
		claim.setEmployeeExpeneseClaimId(uiobj.getEmployeeExpeneseClaimId());
		claim.setEmployeId(uiobj.getEmployeId());
		claim.setExpenseTypeId(uiobj.getExpenseTypeId());
		claim.setFilePath(uiobj.getFilePath());
		claim.setMarchantName(uiobj.getMarchantName());
		claim.setPayrollMonth(uiobj.getPayrollMonth());
		claim.setRecheckRemark(uiobj.getRecheckRemark());
		claim.setRemark(uiobj.getRemark());
		claim.setStatus(uiobj.getStatus());
		claim.setVarifyStatus(uiobj.getVarifyStatus());
		claim.setUserId(uiobj.getUserId());
		claim.setUserIdUpDate(uiobj.getUserIdUpDate());

		claim.setExpenceTitle(uiobj.getExpenceTitle());
		return claim;
	}

	@Override
	public EmployeeExpenseClaimDTO databaseModelToUiDto(EmployeeExpenseClaim empExClaim) {
		EmployeeExpenseClaimDTO claimDTO = new EmployeeExpenseClaimDTO();
		claimDTO.setAmountExpenses(empExClaim.getAmountExpenses());
		claimDTO.setApprovalDate(empExClaim.getApprovalDate());
		claimDTO.setApprovalRemark(empExClaim.getApprovalRemark());
		claimDTO.setBillNumber(empExClaim.getBillNumber());
		claimDTO.setClaimDate(empExClaim.getClaimDate());
		claimDTO.setCompanyId(empExClaim.getCompanyId());
		claimDTO.setDateCreated(empExClaim.getDateCreated());
		claimDTO.setDateUpdate(empExClaim.getDateUpdate());
		claimDTO.setEmployeeExpeneseClaimId(empExClaim.getEmployeeExpeneseClaimId());
		claimDTO.setEmployeId(empExClaim.getEmployeId());
		claimDTO.setExpenseTypeId(empExClaim.getExpenseTypeId());
		claimDTO.setFilePath(empExClaim.getFilePath());
		claimDTO.setMarchantName(empExClaim.getMarchantName());
		claimDTO.setPayrollMonth(empExClaim.getPayrollMonth());
		claimDTO.setRecheckRemark(empExClaim.getRecheckRemark());
		claimDTO.setRemark(empExClaim.getRemark());
		claimDTO.setStatus(empExClaim.getStatus());
		claimDTO.setVarifyStatus(empExClaim.getVarifyStatus());
		claimDTO.setUserId(empExClaim.getUserId());
		claimDTO.setUserIdUpDate(empExClaim.getUserIdUpDate());

		claimDTO.setExpenceTitle(empExClaim.getExpenceTitle());
		return claimDTO;
	}

	public List<ExpenseTypeDTO> findExpenseTypeList(List<ExpenseType> claimsList) {

		return claimsList.stream().map(item -> expenseTypeList(item)).collect(Collectors.toList());
	}

	public ExpenseTypeDTO expenseTypeList(ExpenseType expenseType) {
		ExpenseTypeDTO expenseTypeDTO = new ExpenseTypeDTO();
		expenseTypeDTO.setCompanyId(expenseType.getCompanyId());
		expenseTypeDTO.setDateCreated(expenseType.getDateCreated());
		expenseTypeDTO.setDateUpdate(expenseType.getDateUpdate());
		expenseTypeDTO.setExpenseTypeId(expenseType.getExpenseTypeId());
		expenseTypeDTO.setExpenseTypeName(expenseType.getExpenseTypeName());
		expenseTypeDTO.setUserId(expenseType.getUserId());
		return expenseTypeDTO;
	}

	public List<EmployeeExpenseClaimDTO> objLeaveListToObjUiDtoList(List<Object[]> expensesPendingList) {

		List<EmployeeExpenseClaimDTO> expensesDtoList = new ArrayList<EmployeeExpenseClaimDTO>();

		for (Object[] obj : expensesPendingList) {

			EmployeeExpenseClaimDTO expensesDto = new EmployeeExpenseClaimDTO();

			String employeeName = obj[0] != null ? (String) obj[0] : null;
			Date dateCreated = obj[1] != null ? (Date) (obj[1]) : null;
			Long amountExpenses = obj[2] != null ? Long.parseLong(obj[2].toString()) : null;
			Long employeId = obj[3] != null ? Long.parseLong(obj[3].toString()) : null;

			expensesDto.setEmployeeName(employeeName);
			expensesDto.setDateCreated(dateCreated);
			expensesDto.setAmountExpenses(amountExpenses);
			expensesDto.setEmployeId(employeId);

			expensesDtoList.add(expensesDto);

		}

		return expensesDtoList;
	}

	public List<EmployeeExpenseClaimDTO> databaseModeltouiObjExpenseSummaryDto(List<Object[]> expenseClaimList) {

		List<EmployeeExpenseClaimDTO> expensesDtoList = new ArrayList<EmployeeExpenseClaimDTO>();

		for (Object[] obj : expenseClaimList) {

			EmployeeExpenseClaimDTO expensesDto = new EmployeeExpenseClaimDTO();

			String expenceTitle = obj[0] != null ? (String) obj[0] : null;
			String expenseTypeName = obj[1] != null ? (String) obj[1] : null;
			Date claimDate = obj[2] != null ? (Date) (obj[2]) : null;
			Long amountExpenses = obj[3] != null ? Long.parseLong(obj[3].toString()) : null;
			String marchantName = obj[4] != null ? (String) obj[4] : null;
			String filePath = obj[5] != null ? (String) obj[5] : null;
			String employeeName = obj[6] != null ? (String) obj[6] : null;
			String remark = obj[7] != null ? (String) obj[7] : null;
			Long employeeExpeneseClaimId = obj[8] != null ? Long.parseLong(obj[8].toString()) : null;

			expensesDto.setExpenceTitle(expenceTitle);
			expensesDto.setClaimDate(claimDate);
			expensesDto.setMarchantName(marchantName);
			expensesDto.setFilePath(filePath);
			expensesDto.setEmployeeName(employeeName);
			expensesDto.setAmountExpenses(amountExpenses);
			expensesDto.setExpenseTypeName(expenseTypeName);
			expensesDto.setRemark(remark);
			expensesDto.setEmployeeExpeneseClaimId(employeeExpeneseClaimId);

			expensesDtoList.add(expensesDto);

		}

		return expensesDtoList;
	}

}
