package com.csipl.hrms.service.adaptor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.OneTimeEarningDeductionDTO;

import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;

public class OneTimeEarningDeductionAdaptor implements Adaptor<OneTimeEarningDeductionDTO , OneTimeEarningDeduction>{

	@Override
	public List<OneTimeEarningDeduction> uiDtoToDatabaseModelList(List<OneTimeEarningDeductionDTO> oneTimeEarningDeductionDtoList) {
		List<OneTimeEarningDeduction> oneTimeEarningDeductionList = new ArrayList<OneTimeEarningDeduction>();
	for (OneTimeEarningDeductionDTO oneTimeEarningDeductionDto : oneTimeEarningDeductionDtoList) {
		oneTimeEarningDeductionList.add(uiDtoToDatabaseModel(oneTimeEarningDeductionDto));
	}
		return oneTimeEarningDeductionList;
	}

	public List<OneTimeEarningDeductionDTO> databaseModelToUiDtoList(List<OneTimeEarningDeduction> oneTimeEarningDeductionList, List<EmployeeDTO> employeeDtoList) {
		List<OneTimeEarningDeductionDTO> oneTimeEarningDeductionDtoList = new ArrayList<OneTimeEarningDeductionDTO>();
		for (OneTimeEarningDeduction oneTimeEarningDeduction : oneTimeEarningDeductionList) {
			oneTimeEarningDeductionDtoList.add(databaseModelToUiDto(oneTimeEarningDeduction, employeeDtoList));
		}
		return oneTimeEarningDeductionDtoList;
	}

	@Override
	public OneTimeEarningDeduction uiDtoToDatabaseModel(OneTimeEarningDeductionDTO earningDeductionDto) {
		OneTimeEarningDeduction earningDeduction = new OneTimeEarningDeduction(); 
		earningDeduction.setEmployeeId(earningDeductionDto.getEmployeeId());
		earningDeduction.setPayHeadId(earningDeductionDto.getPayHeadId());
		earningDeduction.setAmount(earningDeductionDto.getAmount());
		earningDeduction.setRemarks(earningDeductionDto.getRemarks());
		earningDeduction.setEarningDeductionMonth(earningDeductionDto.getEarningDeductionMonth());
		earningDeduction.setCompanyId(earningDeductionDto.getCompanyId());
		earningDeduction.setUserId(earningDeductionDto.getUserId());
		earningDeduction.setId(earningDeductionDto.getId());
		earningDeduction.setType(earningDeductionDto.getType());
		if(earningDeductionDto.getId()==null)
		earningDeduction.setDateCreated(new Date());
		else
			earningDeduction.setDateCreated(earningDeductionDto.getDateCreated());
		
		earningDeduction.setUpdateDate(new Date());
		return earningDeduction;
	}

	public OneTimeEarningDeductionDTO databaseModelToUiDto(OneTimeEarningDeduction earningDeduction, List<EmployeeDTO> employeeDtoList) {
		OneTimeEarningDeductionDTO earningDeductionDto =new OneTimeEarningDeductionDTO();
		earningDeductionDto.setEmployeeId(earningDeduction.getEmployeeId());
		earningDeductionDto.setPayHeadId(earningDeduction.getPayHeadId());
		earningDeductionDto.setAmount(earningDeduction.getAmount());
		earningDeductionDto.setRemarks(earningDeduction.getRemarks());
		earningDeductionDto.setEarningDeductionMonth(earningDeduction.getEarningDeductionMonth());
		earningDeductionDto.setCompanyId(earningDeduction.getCompanyId());
		earningDeductionDto.setUserId(earningDeduction.getUserId());
		earningDeductionDto.setId(earningDeduction.getId());
		earningDeductionDto.setType(earningDeduction.getType());
		earningDeductionDto.setDateCreated(earningDeduction.getDateCreated());
		 
		employeeDtoList.forEach(data->{
			if(data.getEmployeeId().compareTo(earningDeduction.getEmployeeId())==0)  
				earningDeductionDto.setFullNameCodeValues(data.getFullNameCodeVaues());
		});
		
		System.out.println(earningDeductionDto.toString());
		return earningDeductionDto;
	}

	@Override
	public List<OneTimeEarningDeductionDTO> databaseModelToUiDtoList(List<OneTimeEarningDeduction> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OneTimeEarningDeductionDTO databaseModelToUiDto(OneTimeEarningDeduction dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

}
