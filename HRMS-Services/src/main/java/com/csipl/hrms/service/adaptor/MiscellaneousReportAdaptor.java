package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeAssetDTO;
import com.csipl.hrms.dto.payroll.LoanIssueDTO;


public class MiscellaneousReportAdaptor {

	public List<EmployeeAssetDTO> objectListToAssetAllocationReport(List<Object[]> assetAllocationObj) {
		
		List<EmployeeAssetDTO> employeeAssetList = new ArrayList<EmployeeAssetDTO>();
		

		for (Object[] employeeAssetObj : assetAllocationObj) {
			EmployeeAssetDTO employeeAssetDTO = new EmployeeAssetDTO();

			String employeeCode = employeeAssetObj[0] != null ? (String) employeeAssetObj[0] : null;
			String employeeName = employeeAssetObj[1] != null ? (String) employeeAssetObj[1] : null;
			String employeeDesignation = employeeAssetObj[2] != null ? (String) employeeAssetObj[2] : null;
			String department = employeeAssetObj[3] != null ? (String) employeeAssetObj[3] : null;
			String itemName = employeeAssetObj[4] != null ? (String) employeeAssetObj[4] : null;
			String issueDescription = employeeAssetObj[5] != null ? (String) employeeAssetObj[5] : null;
			Date allocatedOn = employeeAssetObj[6] != null ? (Date) employeeAssetObj[6] : null;
			Date receivedOn = employeeAssetObj[7] != null ? (Date) employeeAssetObj[7] : null;
			String recievedRemark = employeeAssetObj[8] != null ? (String) employeeAssetObj[8] : null;
			

			employeeAssetDTO.setEmployeeCode(employeeCode);
			employeeAssetDTO.setEmployeeName(employeeName);
			employeeAssetDTO.setEmployeeDesignation(employeeDesignation);
			employeeAssetDTO.setDepartment(department);
			employeeAssetDTO.setItemName(itemName);
			employeeAssetDTO.setIssueDescription(issueDescription);
			employeeAssetDTO.setAllocatedOn(allocatedOn);
			employeeAssetDTO.setReceivedOn(receivedOn);
			employeeAssetDTO.setRecievedRemark(recievedRemark);
			employeeAssetList.add(employeeAssetDTO);

		}
		// TODO Auto-generated method stub
		return employeeAssetList ;
	}

}
