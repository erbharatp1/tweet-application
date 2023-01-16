package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.EmployeeRoleSummaryDTO;

public class MiscellaneousRoleSummaryReportAdaptor {

	public List<EmployeeRoleSummaryDTO> objectListToRoleSummaryReport(List<Object[]> roleSummaryObj) {
		
		List<EmployeeRoleSummaryDTO> employeeRoleSummaryList=new ArrayList<EmployeeRoleSummaryDTO>();
		
		
		for(Object[] employeeRoleSummaryObj:roleSummaryObj) {
			 EmployeeRoleSummaryDTO employeeRoleSummaryDTO=new EmployeeRoleSummaryDTO();
			 
			 String employeeCode=employeeRoleSummaryObj[0]!=null? (String) employeeRoleSummaryObj[0]:null;
			 String employeeName=employeeRoleSummaryObj[1]!=null? (String) employeeRoleSummaryObj[1]:null;
			 String employeeDesignation=employeeRoleSummaryObj[2]!=null? (String) employeeRoleSummaryObj[2]:null;
			 String employeeDepartment=employeeRoleSummaryObj[3]!=null? (String) employeeRoleSummaryObj[3]:null;
			 String employeeRole=employeeRoleSummaryObj[4]!=null? (String) employeeRoleSummaryObj[4]:null;
			 String activeStatus=employeeRoleSummaryObj[5]!=null? (String) employeeRoleSummaryObj[5]:null;
			 activeStatus=activeStatus.equals(StatusMessage.ACTIVE_CODE) ? "Working" : "Former" ;
			 
			 
			 employeeRoleSummaryDTO.setEmployeeCode(employeeCode);
			 employeeRoleSummaryDTO.setEmployeeName(employeeName);
			 employeeRoleSummaryDTO.setEmployeeDesignation(employeeDesignation);
			 employeeRoleSummaryDTO.setEmployeeDepartment(employeeDepartment);
			 employeeRoleSummaryDTO.setEmployeeRole(employeeRole);
			 employeeRoleSummaryDTO.setActiveStatus(activeStatus);
			 employeeRoleSummaryList.add(employeeRoleSummaryDTO);
			
		}
	
		return  employeeRoleSummaryList;
	}

}
