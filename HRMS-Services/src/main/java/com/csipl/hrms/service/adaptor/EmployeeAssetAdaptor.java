package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.client.SystemDefaultCredentialsProvider;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeAssetDTO;
import com.csipl.hrms.dto.employee.EmployeeIdProofDTO;
import com.csipl.hrms.dto.employee.ProfessionalInformationDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeAsset;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.organisation.Item;

public class EmployeeAssetAdaptor implements Adaptor<EmployeeAssetDTO, EmployeeAsset> {

	public List<EmployeeAsset> empAssetsDtoToDatabaseModelList(List<EmployeeAssetDTO> employeeAssetDtoList,
			String empId) {
		List<EmployeeAsset> employeeAssetList = new ArrayList<EmployeeAsset>();
		for (EmployeeAssetDTO employeeAssetDTO : employeeAssetDtoList) {
			employeeAssetList.add(empAssetsDtoToDatabaseModel(employeeAssetDTO, empId));
		}
		return employeeAssetList;
	}

	@Override
	public List<EmployeeAssetDTO> databaseModelToUiDtoList(List<EmployeeAsset> EmployeeAssetList) {
		List<EmployeeAssetDTO> EmployeeAssetDtoList = new ArrayList<EmployeeAssetDTO>();
		for (EmployeeAsset employeeAsset : EmployeeAssetList) {

			EmployeeAssetDtoList.add(databaseModelToUiDto(employeeAsset));
		}
		return EmployeeAssetDtoList;
	}

	public EmployeeAsset empAssetsDtoToDatabaseModel(EmployeeAssetDTO employeeAssetDto, String empId) {
		DateUtils dateUtils = new DateUtils();
		EmployeeAsset employeeAsset = new EmployeeAsset();
		Long employeeId = Long.parseLong(empId);
		employeeAsset.setEmployeeAssetsId(employeeAssetDto.getEmployeeAssetsId());
		Item item = new Item();
		item.setItemId(employeeAssetDto.getItemId());
		employeeAsset.setItem(item);
		employeeAsset.setIssueDescription(employeeAssetDto.getIssueDescription());
		employeeAsset.setAmount(employeeAssetDto.getAmount());
		employeeAsset.setQuantity(employeeAssetDto.getQuantity());

		System.out.println("DTO Date from : " + employeeAssetDto.getDateFrom());
		System.out
				.println("DTO Date to :" + employeeAssetDto.getDateTo() + ":" + employeeAssetDto.getDateFrom() != null);
		System.out.println(employeeAssetDto.getDateFrom() != null && ("").equals(employeeAssetDto.getDateFrom()));
		if (employeeAssetDto.getDateFrom() != null && !("").equals(employeeAssetDto.getDateFrom()))
			employeeAsset.setDateFrom(dateUtils.getDateWirhYYYYMMDD(employeeAssetDto.getDateFrom()));

		if (employeeAssetDto.getDateTo() != null && !("").equals(employeeAssetDto.getDateTo()))
			employeeAsset.setDateTo(dateUtils.getDateWirhYYYYMMDD(employeeAssetDto.getDateTo()));

		employeeAsset.setActiveStatus(employeeAssetDto.getActiveStatus());

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employeeAsset.setEmployee(employee);
		employeeAsset.setActiveStatus(StatusMessage.ACTIVE_CODE);
		System.out.println(employeeAssetDto.getRecievedRemark());
		employeeAsset.setRecievedRemark(employeeAssetDto.getRecievedRemark());
		// employeeAsset.setEmployeeId(employeeId);
		employeeAsset.setUserId(employeeAssetDto.getUserId());
		if (employeeAssetDto.getDateCreated() == null)
			employeeAsset.setDateCreated(new Date());
		else
			employeeAsset.setDateCreated(employeeAssetDto.getDateCreated());
		employeeAsset.setDateUpdate(new Date());
		employeeAsset.setUserIdUpdate(employeeAssetDto.getUserIdUpdate());
		employeeAsset.setCompanyId(employeeAssetDto.getCompanyId());
		
		return employeeAsset;
	}

	@Override
	public EmployeeAssetDTO databaseModelToUiDto(EmployeeAsset employeeAsset) {
		DateUtils dateUtils = new DateUtils();
		EmployeeAssetDTO employeeAssetDto = new EmployeeAssetDTO();
		employeeAssetDto.setEmployeeAssetsId(employeeAsset.getEmployeeAssetsId());
		employeeAssetDto.setIssueDescription(employeeAsset.getIssueDescription());
		employeeAssetDto.setItemId(employeeAsset.getItem().getItemId());
		employeeAssetDto.setQuantity(employeeAsset.getQuantity());
		employeeAssetDto.setAmount(employeeAsset.getAmount());
		
		
		if(employeeAsset.getEmployee()!=null) {
			employeeAssetDto.setEmployeeName(employeeAsset.getEmployee().getFirstName()+" "+employeeAsset.getEmployee().getLastName());
			if(employeeAsset.getEmployee().getDesignation()!=null)
			employeeAssetDto.setEmployeeDesignation(employeeAsset.getEmployee().getDesignation().getDesignationName());
		}
		
		employeeAssetDto.setItemName(employeeAsset.getItem().getItemName());
		employeeAssetDto.setEmployeeId(employeeAsset.getEmployee().getEmployeeId());
		
		if (employeeAsset.getDateFrom() != null) {
			String dateFrom = dateUtils.getDateStringWirhYYYYMMDD(employeeAsset.getDateFrom());
			employeeAssetDto.setDateFrom(dateFrom);
		}
		if (employeeAsset.getDateTo() != null) {
			String dateTo = dateUtils.getDateStringWirhYYYYMMDD(employeeAsset.getDateTo());
			employeeAssetDto.setDateTo(dateTo);
		}
		employeeAssetDto.setActiveStatus(employeeAsset.getActiveStatus());
		employeeAssetDto.setUserId(employeeAsset.getUserId());
		employeeAssetDto.setDateCreated(employeeAsset.getDateCreated());
		employeeAssetDto.setRecievedRemark(employeeAsset.getRecievedRemark());
		return employeeAssetDto;
	}

	@Override
	public EmployeeAsset uiDtoToDatabaseModel(EmployeeAssetDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeAsset> uiDtoToDatabaseModelList(List<EmployeeAssetDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	 
	public List<EmployeeAssetDTO> databaseModelObjectToUiDtoList(List<Object[]> EmployeeAssetList) {
		List<EmployeeAssetDTO> EmployeeAssetDtoList = new ArrayList<EmployeeAssetDTO>();
		for (Object[] employeeAsset : EmployeeAssetList) {
			EmployeeAssetDtoList.add(databaseModelObjectToUiDto(employeeAsset));
		}
		return EmployeeAssetDtoList;
	}
	
	public EmployeeAssetDTO databaseModelObjectToUiDto(Object[] employeeAsset) {

		EmployeeAssetDTO employeeAssetDTO = new EmployeeAssetDTO();
		if (employeeAsset[0] != null) {
			employeeAssetDTO.setEmployeeId(Long.valueOf(employeeAsset[0].toString()));
		}
		 
		if (employeeAsset[1] != null && employeeAsset[2] != null) {
			employeeAssetDTO.setEmployeeName(employeeAsset[1].toString() + " " + employeeAsset[2].toString());
		}
		if (employeeAsset[3] != null) {
			employeeAssetDTO.setEmployeeDesignation(employeeAsset[3].toString());
		}
		
		if (employeeAsset[4] != null) {
			employeeAssetDTO.setEmployeeCode(employeeAsset[4].toString());
		}
		return employeeAssetDTO;
	}
}
