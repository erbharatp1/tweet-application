package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeStatuary;

public class EmployeeStatuaryAdaptor implements Adaptor<EmployeeStatuaryDTO,EmployeeStatuary> {

	
	
	public List<EmployeeStatuary> empStaturyDtoToDatabaseModelList(List<EmployeeStatuaryDTO> employeeStatuaryDTOList,String empId) {
		List<EmployeeStatuary> employeeStatuaryList=new ArrayList<EmployeeStatuary>();
		for(EmployeeStatuaryDTO employeeStatuaryDTO: employeeStatuaryDTOList) {
			if(employeeStatuaryDTO.getFamilyId()!=null)
			employeeStatuaryList.add(uiDtoToDatabaseModel(employeeStatuaryDTO,empId));
		}
		return employeeStatuaryList;
	}
	
	
	@Override
	public List<EmployeeStatuaryDTO> databaseModelToUiDtoList(List<EmployeeStatuary> employeeStatuaryList) {
		List<EmployeeStatuaryDTO> employeeStatuaryDTOList=new ArrayList<EmployeeStatuaryDTO>();
		for(EmployeeStatuary employeeStatuary: employeeStatuaryList) {
			employeeStatuaryDTOList.add(databaseModelToUiDto(employeeStatuary));
		}
		return employeeStatuaryDTOList;
	}
	

	public EmployeeStatuary uiDtoToDatabaseModel(EmployeeStatuaryDTO employeeStatuaryDto,String empId) {
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		EmployeeStatuary employeeStatuary=new EmployeeStatuary();
		DateUtils dateUtils = new DateUtils();
		Long employeeId=Long.parseLong(empId);
		Employee employee=new Employee();
		employee.setEmployeeId(employeeId);
		employeeStatuary.setEmployee(employee);
		employeeStatuary.setStatuaryId(employeeStatuaryDto.getStatuaryId());
		
		employeeStatuary.setStatus(employeeStatuaryDto.getStatus());
		employeeStatuary.setDateTo(new Date());
		
		if(employeeStatuaryDto.getDateFrom()!=null && !("".equals(employeeStatuaryDto.getDateFrom())))
		employeeStatuary.setDateFrom(dateUtils.getDateWirhYYYYMMDD( employeeStatuaryDto.getDateFrom()));
		if(employeeStatuaryDto.getDateTo()!=null && !("".equals(employeeStatuaryDto.getDateTo())))
		employeeStatuary.setDateTo(dateUtils.getDateWirhYYYYMMDD( employeeStatuaryDto.getDateTo()));
		if(employeeStatuaryDto.getStatuaryNumber()!=null)
		employeeStatuary.setStatuaryNumber(employeeStatuaryDto.getStatuaryNumber());
		employeeStatuary.setStatuaryType(employeeStatuaryDto.getStatuaryType());
		EmployeeFamily employeeFamily = new EmployeeFamily();
		employeeFamily.setFamilyId(employeeStatuaryDto.getFamilyId());
		employeeStatuary.setEmployeeFamily(employeeFamily);
		System.out.println(employeeStatuary.getEmployeeFamily().getFamilyId());
		employeeStatuary.setUserId(employeeStatuaryDto.getUserId());
		if(employeeStatuaryDto.getDateCreated()==null)
			employeeStatuary.setDateCreated(new Date());
		else
		employeeStatuary.setDateCreated(employeeStatuaryDto.getDateCreated());
		//employeeStatuary.setDateFrom(new Date());
		employeeStatuary.setDateUpdate(new Date());
		employeeStatuary.setUserIdUpdate(employeeStatuaryDto.getUserIdUpdate());
		mandatoryInfoCheck.setUserId(employeeStatuaryDto.getUserId());
		employeeStatuary.setIsApplicable(employeeStatuaryDto.getIsApplicable());
		employeeStatuary.setStatus(employeeStatuaryDto.getStatus());
		System.out.println("UserId>>>>>>>>>>>>>>"+mandatoryInfoCheck.getUserId());
		mandatoryInfoCheck.setDateCreated(employeeStatuaryDto.getDateCreated());
		System.out.println("Date>>>>>>>>>>>>>>>>>"+employeeStatuary.getDateFrom());
		return employeeStatuary;
	}
	

	@Override
	public EmployeeStatuaryDTO databaseModelToUiDto(EmployeeStatuary employeeStatuary) {
		EmployeeStatuaryDTO employeeStatuaryDto=new EmployeeStatuaryDTO();
		DateUtils dateUtils = new DateUtils();
		if(employeeStatuary.getDateFrom()!=null) {
		String dateFrom = dateUtils.getDateStringWirhYYYYMMDD(employeeStatuary.getDateFrom());
		employeeStatuaryDto.setDateFrom(dateFrom);
		}
		
		if(employeeStatuary.getDateTo()!=null) {
		String dateTo = dateUtils.getDateStringWirhYYYYMMDD(employeeStatuary.getDateTo());
		employeeStatuaryDto.setDateTo(dateTo);
		}
		
		employeeStatuaryDto.setStatuaryNumber(employeeStatuary.getStatuaryNumber());
		employeeStatuaryDto.setStatuaryType(employeeStatuary.getStatuaryType());
		
		employeeStatuaryDto.setStatuaryId(employeeStatuary.getStatuaryId());
		if(employeeStatuary.getEmployeeFamily()!=null) {
		employeeStatuaryDto.setFamilyId(employeeStatuary.getEmployeeFamily().getFamilyId());
		employeeStatuaryDto.setFamilyValue(employeeStatuary.getEmployeeFamily().getName());
		employeeStatuaryDto.setFamilyRelation(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.Relation.getDropDownName(),employeeStatuary.getEmployeeFamily().getRelation()));;
		}
		employeeStatuaryDto.setUserId(employeeStatuary.getUserId());
		employeeStatuaryDto.setDateCreated(employeeStatuary.getDateCreated());
	if(employeeStatuary.getStatuaryType().equals(StatusMessage.MEDICAL_INSURANCE_CODE))
		employeeStatuaryDto.setStatuaryTypeValue(StatusMessage.MEDICAL_INSURANCE_VALUE);
	
	if(employeeStatuary.getStatuaryType().equals(StatusMessage.ESIC_CODE))
		employeeStatuaryDto.setStatuaryTypeValue(StatusMessage.ESIC_VALUE);
	
	if(employeeStatuary.getStatuaryType().equals(StatusMessage.ACCIDENTAL_INSURANCE_CODE))
		employeeStatuaryDto.setStatuaryTypeValue(StatusMessage.ACCIDENTAL_INSURANCE_VALUE);
	
	if(employeeStatuary.getStatuaryType().equals(StatusMessage.PF_CODE))
		employeeStatuaryDto.setStatuaryTypeValue(StatusMessage.PF_VALUE);
	
	if(employeeStatuary.getStatuaryType().equals(StatusMessage.UAN_CODE))
		employeeStatuaryDto.setStatuaryTypeValue(StatusMessage.UAN_VALUE);
	employeeStatuaryDto.setIsApplicable(employeeStatuary.getIsApplicable());
	employeeStatuaryDto.setStatus(employeeStatuary.getStatus());
		//eduInformationDTO.setEmployeeId(1L);
		return employeeStatuaryDto;
	}

	@Override
	public List<EmployeeStatuary> uiDtoToDatabaseModelList(List<EmployeeStatuaryDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeStatuary uiDtoToDatabaseModel(EmployeeStatuaryDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<EmployeeStatuary> empStaturyDtoToDatabaseModelList(List<EmployeeStatuaryDTO> employeeStatuaryDtoList,Employee employee) {
		List<EmployeeStatuary> employeeStatuaryList=new ArrayList<EmployeeStatuary>();
		for(EmployeeStatuaryDTO employeeStatuaryDTO: employeeStatuaryDtoList) {
			employeeStatuaryList.add(uiDtoToDatabaseModel(employeeStatuaryDTO,employee));
		}
		return employeeStatuaryList;
	}
	public EmployeeStatuary uiDtoToDatabaseModel(EmployeeStatuaryDTO employeeStatuaryDto,Employee employee) {
		DateUtils dateUtils = new DateUtils();
		EmployeeStatuary employeeStatuary=new EmployeeStatuary();
 		employeeStatuary.setEmployee(employee);
		employeeStatuary.setStatuaryId(employeeStatuaryDto.getStatuaryId());
		if(employeeStatuaryDto.getDateFrom()!=null && !("".equals(employeeStatuaryDto.getDateFrom())))
		employeeStatuary.setDateFrom(dateUtils.getDateWirhYYYYMMDD( employeeStatuaryDto.getDateFrom()));
		if(employeeStatuaryDto.getDateTo()!=null && !("".equals(employeeStatuaryDto.getDateTo())))
		employeeStatuary.setDateTo(dateUtils.getDateWirhYYYYMMDD( employeeStatuaryDto.getDateTo()));
 		employeeStatuary.setStatuaryNumber(employeeStatuaryDto.getStatuaryNumber());
		employeeStatuary.setStatuaryType(employeeStatuaryDto.getStatuaryType());
		EmployeeFamily employeeFamily = new EmployeeFamily();
		employeeFamily.setFamilyId(employeeStatuaryDto.getFamilyId());
		employeeStatuary.setEmployeeFamily(employeeFamily);
		
		employeeStatuary.setEmployee(employee);
		employeeStatuary.setUserId(employee.getUserId());
		employeeStatuary.setDateCreated(new Date());
		return employeeStatuary;
	}
}
