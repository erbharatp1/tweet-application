package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.FinalSettlementDTO;
import com.csipl.hrms.model.payroll.FinalSettlement;
import com.csipl.hrms.model.payroll.FinalSettlementReport;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public class FinalSettlementAdaptor {
	
	
	public List<EmployeeDTO> databaseModelObjtoUiDTO(List<Object[]> employees)
	{
		List<EmployeeDTO> employeeDTOList=new ArrayList<EmployeeDTO>();
		
		for(Object[] emp:employees)
		{
			EmployeeDTO employeeDTO=new EmployeeDTO();
			
			String 	firstName=emp[0]!=null?(String)emp[0]:null;
			String 	lastName=emp[1]!=null?(String)emp[1]:null;
			String employeeCode=emp[2]!=null?(String)emp[2]:null;
			String departmentName=emp[3]!=null?(String)emp[3]:null;
			Date exitDate=emp[4]!=null?(Date)emp[4]:null;
			Long employeeId=emp[5]!=null?Long.parseLong(emp[5].toString()):null;
			
			employeeDTO.setFirstName(firstName);
			employeeDTO.setLastName(lastName);
			employeeDTO.setEmployeeCode(employeeCode);
			employeeDTO.setExitDate(exitDate);
			employeeDTO.setDepartmentName(departmentName);
			employeeDTO.setEmployeeId(employeeId);
			
			employeeDTOList.add(employeeDTO);
		}
		
		
		return employeeDTOList;
	}

	public FinalSettlement finalSettlementDtoToDatabaseModel(FinalSettlementDTO finalSettlementDto) {
		FinalSettlement	finalSettlement = new FinalSettlement();
		finalSettlement.setDateCreated(new Date());
		finalSettlement.setEmployeeId(finalSettlementDto.getEmployeeId());
		finalSettlement.setFinalSettlementId(finalSettlementDto.getFinalSettlementId());
		finalSettlement.setGratuity(finalSettlementDto.getGratuity());
		finalSettlement.setIncomeTax(finalSettlementDto.getIncomeTax());
		finalSettlement.setLeaveEncashment(finalSettlementDto.getLeaveEncashment());
		finalSettlement.setLoan(finalSettlementDto.getLoan());
		finalSettlement.setNetPayable(finalSettlementDto.getNetPayable());
		finalSettlement.setSalaryPayable(finalSettlementDto.getSalaryPayable());
		finalSettlement.setUserId(finalSettlementDto.getUserId());
		finalSettlement.setCompanyId(finalSettlementDto.getCompanyId());
		
		return finalSettlement;
	}

	public List<EmployeeDTO> databaseObjModelToUiDtoList(List<Object[]> employeeObjList) {

		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] employeeObj : employeeObjList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			if (employeeObj[0] != null) {
				employeeDto.setEmployeeId(employeeObj[0] != null ? Long.parseLong(employeeObj[0].toString()) : null);
			}

			if (employeeObj[1] != null) {
				employeeDto.setFirstName(employeeObj[1].toString());
			}
			if (employeeObj[2] != null) {
				employeeDto.setLastName(employeeObj[2].toString());
			}
			if (employeeObj[3] != null) {
				employeeDto.setEmployeeCode(employeeObj[3].toString());
			}

			if (employeeObj[4] != null) {
				employeeDto.setCityId(employeeObj[4] != null ? Long.parseLong(employeeObj[4].toString()) : null);
			}

			if (employeeObj[5] != null) {
				employeeDto.setActiveStatus(employeeObj[5].toString());
			}
			
			employeeDto.setEmployeeLogoPath(employeeObj[6] != null ? (String) employeeObj[6] : null);
			
			employeeDto.setFullNameCodeVaues(employeeDto.getFirstName() + " " + employeeDto.getLastName() + " ("
					+ employeeDto.getEmployeeCode() + ")");

			employeeDtoList.add(employeeDto);

		}
		return employeeDtoList;
		
		}
	
	public List<FinalSettlementReport> reportPayoutToFinalReport(List<ReportPayOut> reportPayOutList, List<Object[]> lastPaid) {
	List<FinalSettlementReport> finalSettlementReportList = new ArrayList<FinalSettlementReport>();
//	String processMonth=null;
//	BigDecimal netPayableAmount = BigDecimal.ZERO;
if(lastPaid.isEmpty()) {
	for (ReportPayOut reportPayOut : reportPayOutList) {
		FinalSettlementReport finalSettlementReport=new FinalSettlementReport();
		
			finalSettlementReport.setSalaryPayable(reportPayOut.getNetPayableAmount());
			finalSettlementReport.setSalaryPayableMonth(reportPayOut.getId().getProcessMonth());
			finalSettlementReport.setEmployeeId(reportPayOut.getEmployee().getEmployeeId());
		
			finalSettlementReportList.add(finalSettlementReport);
			
	
	}
}else {
	for (ReportPayOut reportPayOut : reportPayOutList) {
		FinalSettlementReport finalSettlementReport=new FinalSettlementReport();
		for (Object[] last : lastPaid) {
			String	 processMonth=last[0]!=null?(String)last[0]:null;
			BigDecimal netPayableAmount=last[1]!=null?(BigDecimal)last[1]:null;
			finalSettlementReport.setLastPaidMonth(processMonth);
			finalSettlementReport.setLastPaidSalary(netPayableAmount);
			finalSettlementReport.setSalaryPayable(reportPayOut.getNetPayableAmount());
			finalSettlementReport.setSalaryPayableMonth(reportPayOut.getId().getProcessMonth());
			finalSettlementReport.setEmployeeId(reportPayOut.getEmployee().getEmployeeId());
		
			finalSettlementReportList.add(finalSettlementReport);
	
		
	
	}
}
	

	
	}
	return finalSettlementReportList;
}

}
