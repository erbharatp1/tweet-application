package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.HoldSalary;


public class HoldSalaryAdaptor implements Adaptor<HoldSalaryDTO,HoldSalary>{

	@Override
	public List<HoldSalary> uiDtoToDatabaseModelList(List<HoldSalaryDTO> holdSalaryList) {
	
		return null;
	}

	@Override
	public List<HoldSalaryDTO> databaseModelToUiDtoList(List<HoldSalary> holdSalaryList) {
	
		List<HoldSalaryDTO> holdSalaryDTOList = new ArrayList<HoldSalaryDTO>();
		for (HoldSalary HoldSalary : holdSalaryList) {
			holdSalaryDTOList.add(databaseModelToUiDto(HoldSalary));
		}
		return holdSalaryDTOList;
	}
	public List<HoldSalaryDTO> holddatabaseModelToUiDtoList(List<Object[]> holdSalaryList) {
		List<HoldSalaryDTO> holdSalaryDTOList = new ArrayList<HoldSalaryDTO>();
		for (Object[] holdSalary : holdSalaryList) {
			HoldSalaryDTO holdSalaryDTO=new HoldSalaryDTO();
			if (holdSalary[0] != null) {
				holdSalaryDTO.setHoldSalaryId(Long.valueOf(holdSalary[0].toString()));
			}
			if (holdSalary[1] != null) {
				holdSalaryDTO.setRemark(holdSalary[1].toString());
			}
			if (holdSalary[2] != null) {
				holdSalaryDTO.setEmployeeCode(holdSalary[2].toString());
			}
			if (holdSalary[3] != null) {
				holdSalaryDTO.setEmployeeId(Long.valueOf(holdSalary[3].toString()));
			}
			if (holdSalary[4] != null) {
				holdSalaryDTO.setFirstName(holdSalary[4].toString());
			}
			if (holdSalary[5] != null) {
				holdSalaryDTO.setLastName(holdSalary[5].toString());
			
			}
			
			if (holdSalary[6] != null) {
				holdSalaryDTO.setGrossPay(new BigDecimal(holdSalary[6].toString()));
			}
			if (holdSalary[7] != null) {
				holdSalaryDTO.setDepartmentName(holdSalary[7].toString());
			
			}
			if (holdSalary[8] != null) {
				holdSalaryDTO.setGradesName(holdSalary[8].toString());
			}
//			h.payrollMonth,h.status,h.userId,h.userIdUpdate,h.dateCreated,h.dateUpdate,
			if (holdSalary[9] != null) {
				holdSalaryDTO.setPayrollMonth(holdSalary[9].toString());
			}
			if (holdSalary[10] != null) {
				holdSalaryDTO.setStatus(holdSalary[10].toString());
			}
			if (holdSalary[11] != null) {
				holdSalaryDTO.setUserId(Long.valueOf(holdSalary[11].toString()));
			}
			if (holdSalary[12] != null) {
				holdSalaryDTO.setUserIdUpdate(Long.valueOf(holdSalary[11].toString()));
			}
			if (holdSalary[13] != null) {
				holdSalaryDTO.setDateCreated(new Date());
			}
			if (holdSalary[14] != null) {
				holdSalaryDTO.setDateCreated(new Date());
			}
			if (holdSalary[15] != null) {
				holdSalaryDTO.setCompanyId(Long.valueOf(holdSalary[15].toString()));
			}
			
			holdSalaryDTOList.add(holdSalaryDTO);
		}
		
		return holdSalaryDTOList;
	}
	
	@Override
	public HoldSalary uiDtoToDatabaseModel(HoldSalaryDTO holdSalaryDTO) {
	
		
	HoldSalary holdSalary=new HoldSalary();
		
		holdSalary.setHoldSalaryId(holdSalaryDTO.getHoldSalaryId());
		if (holdSalaryDTO.getHoldSalaryId() != null){
			holdSalary.setHoldSalaryId(holdSalaryDTO.getHoldSalaryId());
			holdSalary.setDateCreated(holdSalaryDTO.getDateCreated());
		}else {
			holdSalary.setDateCreated(new Date());
		}
		holdSalary.setCompanyId(holdSalaryDTO.getCompanyId());
		holdSalary.setDateUpdate(holdSalaryDTO.getDateUpdate());
		holdSalary.setPayrollMonth(holdSalaryDTO.getPayrollMonth());
		holdSalary.setRemark(holdSalaryDTO.getRemark());
		holdSalary.setStatus(holdSalaryDTO.getStatus());
		holdSalary.setUserId(holdSalaryDTO.getUserId());
		holdSalary.setUserIdUpdate(holdSalaryDTO.getUserIdUpdate());
		
		Employee employee = new Employee();
		employee.setEmployeeId(holdSalaryDTO.getEmployeeId());
		holdSalary.setEmployee(employee);
		
		return holdSalary;
	}

	public HoldSalary holduiDtoToDatabaseModel(HoldSalaryDTO holdSalaryDTO,Long employeeId) {
		
	HoldSalary holdSalary=new HoldSalary();
		
		holdSalary.setHoldSalaryId(holdSalaryDTO.getHoldSalaryId());
		if (holdSalaryDTO.getHoldSalaryId() == null){
			holdSalary.setHoldSalaryId(holdSalaryDTO.getHoldSalaryId());
			holdSalary.setDateCreated(holdSalaryDTO.getDateCreated());
		}else {
			holdSalary.setDateCreated(new Date());
		}
		holdSalary.setCompanyId(holdSalaryDTO.getCompanyId());
		holdSalary.setDateUpdate(holdSalaryDTO.getDateUpdate());
		holdSalary.setPayrollMonth(holdSalaryDTO.getPayrollMonth());
		holdSalary.setRemark(holdSalaryDTO.getRemark());
		holdSalary.setStatus(holdSalaryDTO.getStatus());
		holdSalary.setUserId(holdSalaryDTO.getUserId());
		holdSalary.setUserIdUpdate(holdSalaryDTO.getUserIdUpdate());
		
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		holdSalary.setEmployee(employee);
		
		return holdSalary;
	}
	
	
	@Override
	public HoldSalaryDTO databaseModelToUiDto(HoldSalary holdSalary) {
		HoldSalaryDTO holdSalaryDTO=new HoldSalaryDTO();
		holdSalaryDTO.setCompanyId(holdSalary.getCompanyId());
		
		holdSalaryDTO.setDateCreated(holdSalary.getDateCreated());
		holdSalaryDTO.setDateUpdate(holdSalary.getDateUpdate());
		holdSalaryDTO.setEmployeeId(holdSalary.getEmployee().getEmployeeId());
		holdSalaryDTO.setHoldSalaryId(holdSalary.getHoldSalaryId());
		holdSalaryDTO.setPayrollMonth(holdSalary.getPayrollMonth());
		holdSalaryDTO.setRemark(holdSalary.getRemark());
		holdSalaryDTO.setStatus(holdSalary.getStatus());
		holdSalaryDTO.setCompanyId(holdSalary.getCompanyId());
		holdSalaryDTO.setUserId(holdSalary.getUserId());
		holdSalaryDTO.setUserIdUpdate(holdSalary.getUserIdUpdate());
		return holdSalaryDTO;
	}

 
}
