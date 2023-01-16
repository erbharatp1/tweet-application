
package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.LoanIssueDTO;

import com.csipl.hrms.model.payroll.LoanIssue;

public class LoanReportAdaptor implements Adaptor<LoanIssueDTO, LoanIssue> {



	@Override
	public List<LoanIssue> uiDtoToDatabaseModelList(List<LoanIssueDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanIssueDTO> databaseModelToUiDtoList(List<LoanIssue> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanIssue uiDtoToDatabaseModel(LoanIssueDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanIssueDTO databaseModelToUiDto(LoanIssue dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<LoanIssueDTO> databaseListsToUIDtoLists(List<Object[]> loanDetailedReportList) {
		// TODO Auto-generated method stub
		List<LoanIssueDTO> loanIssueList = new ArrayList<LoanIssueDTO>();

		for (Object[] loanIssueObj : loanDetailedReportList) {

			LoanIssueDTO loanIssueDto = new LoanIssueDTO();

			String employeeCode = loanIssueObj[0] != null ? (String) loanIssueObj[0] : null;
			String employeeName = loanIssueObj[1] != null ? (String) loanIssueObj[1] : null;
			String designationName = loanIssueObj[2] != null ? (String) loanIssueObj[2] : null;
			String departmentName = loanIssueObj[3] != null ? (String) loanIssueObj[3] : null;
			Long loanAccountNo = loanIssueObj[4] != null ? Long.parseLong(loanIssueObj[4].toString()) : null;
			BigDecimal loanAmount = loanIssueObj[5] != null ? (new BigDecimal(loanIssueObj[5].toString())) : null;
			Date issueDate = loanIssueObj[6] != null ? (Date) loanIssueObj[6] : null;
			String activeStatus = loanIssueObj[7] != null ? (String) loanIssueObj[7] : null;
			Date emiStartDate = loanIssueObj[8] != null ? (Date) loanIssueObj[8] : null;
			Integer noOfEmi = loanIssueObj[9] != null ? Integer.parseInt(loanIssueObj[9].toString()) : null;
			BigDecimal emiAmount = loanIssueObj[10] != null ? (new BigDecimal(loanIssueObj[10].toString())) : null;

		    loanIssueDto.setEmployeeName(employeeName);
			loanIssueDto.setEmployeeCode(employeeCode);
			loanIssueDto.setDesignationName(designationName);
			loanIssueDto.setLoanAmount(loanAmount);
			loanIssueDto.setDepartmentName(departmentName);
			loanIssueDto.setLoanAccountNo(loanAccountNo);
			loanIssueDto.setIssueDate(issueDate);
			loanIssueDto.setEmiStartDate(emiStartDate);
			loanIssueDto.setEmiAmount(emiAmount);
			loanIssueDto.setActiveStatus(activeStatus);
			loanIssueDto.setNoOfEmi(noOfEmi);
			loanIssueList.add(loanIssueDto);
		}

		return loanIssueList;
	}

	

}
