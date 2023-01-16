package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.csipl.hrms.dto.employee.EmployeeNomineeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeNominee;
import com.csipl.hrms.model.employee.EmployeeStatuary;

public class EmployeeNomineeAdaptor  implements Adaptor<EmployeeNomineeDTO,EmployeeNominee>{

	@Override
	public List<EmployeeNominee> uiDtoToDatabaseModelList(List<EmployeeNomineeDTO> employeeNomineeDTOList) {
		List<EmployeeNominee> employeeNomineeList = new ArrayList<EmployeeNominee>();
		for (EmployeeNomineeDTO nominee : employeeNomineeDTOList) {
			employeeNomineeList.add(uiDtoToDatabaseModel(nominee));
		}
		return employeeNomineeList;
	}

	public List<EmployeeStatuary> uiDtoToDatabaseModelList1(List<EmployeeNomineeDTO> employeeNomineeDTOList,List<EmployeeStatuary> employeeNomineeList) {
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		for (EmployeeStatuary statuary : employeeNomineeList) {
			for (EmployeeNomineeDTO employeeNomineeDTO : employeeNomineeDTOList) {
			if(statuary.getStatuaryId()==employeeNomineeDTO.getStaturyHeadId())
			employeeNomineeList.add(uiDtoToDatabaseModel1(statuary,employeeNomineeDTO));
		}}
		return employeeNomineeList;
	}

	
	public EmployeeStatuary uiDtoToDatabaseModel1(EmployeeStatuary employeeStatuary,EmployeeNomineeDTO employeeNomineeDTO) {
		//EmployeeStatuary employeeStatuary=new EmployeeStatuary();
		EmployeeFamily family = new EmployeeFamily();
		Employee employee =new Employee();
		employee.setEmployeeId(employeeNomineeDTO.getEmployeeId());
		family.setFamilyId(employeeNomineeDTO.getFamilyId());
		
		//employeeStatuary.setFamilyId(employeeNomineeDTO.getFamilyId());
		employeeStatuary.setEmployee(employee);
		employeeStatuary.setStatuaryId(employeeNomineeDTO.getStaturyHeadId());
		
		//employeeNominee.setEmployeeFamily(family);
		//employeeStatuary.setEmployeeId(employeeNomineeDTO.getEmployeeId());
		//employeeNominee.setEmployeeNomineeid(employeeNomineeDTO.getEmployeeNomineeid());
		//employeeStatuary.setStaturyHeadId(employeeStatuary.getS);
		//employeeStatuary.setStaturyHeadName(employeeStatuary.get);
		employeeStatuary.setUserId(employeeNomineeDTO.getUserId());
		employeeStatuary.setUserIdUpdate(employeeNomineeDTO.getUserIdUpdate());
		employeeStatuary.setDateUpdate(new Date());
		if(employeeNomineeDTO.getEmployeeNomineeid() != null) {
			employeeStatuary.setDateCreated(employeeNomineeDTO.getDateCreated());
		
		}
		else
		{
			employeeStatuary.setDateCreated(new Date());
		}
		return employeeStatuary;
	}
	
	@Override
	public List<EmployeeNomineeDTO> databaseModelToUiDtoList(List<EmployeeNominee> employeeNomineeList) {
		List<EmployeeNomineeDTO> employeeNomineeDTOList = new ArrayList<EmployeeNomineeDTO>();
		for (EmployeeNominee employeeNominee : employeeNomineeList) {
			employeeNomineeDTOList.add(databaseModelToUiDto(employeeNominee));
		}
		return employeeNomineeDTOList;
	}

	@Override
	public EmployeeNominee uiDtoToDatabaseModel(EmployeeNomineeDTO employeeNomineeDTO) {
		EmployeeNominee employeeNominee=new EmployeeNominee();
		EmployeeFamily family = new EmployeeFamily();
		family.setFamilyId(employeeNomineeDTO.getFamilyId());
		employeeNominee.setEmployeeFamily(family);
		employeeNominee.setEmployeeId(employeeNomineeDTO.getEmployeeId());
		employeeNominee.setEmployeeNomineeid(employeeNomineeDTO.getEmployeeNomineeid());
		//employeeNominee.setStaturyHeadId(employeeNomineeDTO.getStaturyHeadId());
		employeeNominee.setStaturyHeadName(employeeNomineeDTO.getStaturyHeadName());
		employeeNominee.setUserId(employeeNomineeDTO.getUserId());
		employeeNominee.setUserIdUpdate(employeeNomineeDTO.getUserIdUpdate());
		employeeNominee.setDateUpdate(new Date());
		if(employeeNomineeDTO.getEmployeeNomineeid() != null) {
			employeeNominee.setDateCreated(employeeNomineeDTO.getDateCreated());
		
		}
		else
		{
			employeeNominee.setDateCreated(new Date());
		}
		return employeeNominee;
	}

	@Override
	public EmployeeNomineeDTO databaseModelToUiDto(EmployeeNominee employeeNominee) {
		EmployeeNomineeDTO employeeNomineeDTO = new EmployeeNomineeDTO();
	
		employeeNomineeDTO.setEmployeeId(employeeNominee.getEmployeeId());
		employeeNomineeDTO.setDateCreated(employeeNominee.getDateCreated());
		employeeNomineeDTO.setDateUpdate(employeeNominee.getDateUpdate());
		employeeNomineeDTO.setEmployeeNomineeid(employeeNominee.getEmployeeNomineeid());
		employeeNomineeDTO.setFamilyId(employeeNominee.getEmployeeFamily().getFamilyId());
		//employeeNomineeDTO.setStaturyHeadId(employeeNominee.getStaturyHeadId());
		employeeNomineeDTO.setStaturyHeadName(employeeNominee.getStaturyHeadName());
		employeeNomineeDTO.setUserId(employeeNominee.getUserId());
		employeeNomineeDTO.setUserIdUpdate(employeeNominee.getUserIdUpdate());
		
		
		return employeeNomineeDTO;
	}
	public List<EmployeeNomineeDTO> statuaryModelToUiDtoList(List<EmployeeStatuary> employeeStatuaryList) {
		List<EmployeeNomineeDTO> employeeNomineeDTOList = new ArrayList<EmployeeNomineeDTO>();
		for (EmployeeStatuary employeeStataury : employeeStatuaryList) {
			employeeNomineeDTOList.add(databaseModelToUiDto(employeeStataury));
		}
		return employeeNomineeDTOList;
	}
	
	public EmployeeNomineeDTO databaseModelToUiDto(EmployeeStatuary employeeStataury) {
		EmployeeNomineeDTO employeeNomineeDTO = new EmployeeNomineeDTO();
	
		employeeNomineeDTO.setEmployeeId(employeeStataury.getEmployee().getEmployeeId());
		employeeNomineeDTO.setDateCreated(employeeStataury.getDateCreated());
		employeeNomineeDTO.setDateUpdate(employeeStataury.getDateUpdate());
		//employeeNomineeDTO.setEmployeeNomineeid(employeeStataury.getEmployeeNomineeid());
		employeeNomineeDTO.setFamilyId(employeeStataury.getEmployeeFamily().getFamilyId());
		//employeeNomineeDTO.setStaturyHeadId(employeeNominee.getStaturyHeadId());
		//employeeNomineeDTO.setStaturyHeadName(employeeStataury.getStaturyHeadName());
		employeeNomineeDTO.setUserId(employeeStataury.getUserId());
		employeeNomineeDTO.setUserIdUpdate(employeeStataury.getUserIdUpdate());
		
		
		return employeeNomineeDTO;
	}
}
