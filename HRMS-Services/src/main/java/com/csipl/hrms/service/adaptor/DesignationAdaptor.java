package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.dto.organisation.DepartmentDTO;
import com.csipl.hrms.dto.organisation.DesignationDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.DeptDesignationMapping;
import com.csipl.hrms.model.organisation.Designation;

public class DesignationAdaptor implements Adaptor<DesignationDTO, Designation> {

	private static final Logger log = LoggerFactory.getLogger(DesignationAdaptor.class);

	public DesignationDTO designationdatabaseModelToUiDtoList(List<Object[]> deginationList) {
		DesignationDTO designationDto = new DesignationDTO();
		List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();
		Long designationId = null;
		Long userId = null;
		Long companyId = null;
		Long groupId = null;
		String designationName = null;
		String activeStatus = null;
		// String designationName ;
		for (Object[] designation : deginationList) {
			DepartmentDTO departmentDto = new DepartmentDTO();
			if (designation[0] != null) {
				designationId = Long.valueOf(designation[0].toString());
			}
			if (designation[1] != null) {
				designationName = (designation[1].toString());
			}
			if (designation[2] != null) {
				departmentDto.setCompanyId(Long.valueOf(designation[2].toString()));
				companyId = Long.valueOf(designation[2].toString());
			}
			if (designation[3] != null) {

				groupId = Long.valueOf(designation[3].toString());
			}
			if (designation[4] != null) {
				departmentDto.setDepartmentId(Long.valueOf(designation[4].toString()));
			}
			if (designation[5] != null) {
				departmentDto.setDepartmentName(designation[5].toString());
			}
			if (designation[6] != null) {
				departmentDto.setUserId(Long.valueOf(designation[6].toString()));
				userId = Long.valueOf(designation[6].toString());
			}
			if (designation[7] != null) {
				departmentDto.setActiveStatus(designation[7].toString());
				activeStatus = designation[7].toString();
			}
			if (designation[8] != null) {
				departmentDto.setUserIdUpdate(Long.valueOf(designation[8].toString()));
				designationDto.setUserIdUpdate(Long.valueOf(designation[8].toString()));
			}
			departmentList.add(departmentDto);
		}
		designationDto.setDesignationId(designationId);
		designationDto.setDesignationName(designationName);
		designationDto.setCompanyId(companyId);
		designationDto.setActiveStatus(activeStatus);
		designationDto.setUserId(userId);
		designationDto.setDepartment(departmentList);
		return designationDto;
	}

	public Designation uiDtoToDatabaseModel(DesignationDTO designationDto) {
		Designation designation = new Designation();
		if (designationDto.getDesignationId() != null && designationDto.getDesignationId() != 0) {
			designation.setDesignationId(designationDto.getDesignationId());
		}

		designation.setActiveStatus(designationDto.getActiveStatus());
		designation.setDesignationName(designationDto.getDesignationName());
		designation.setEffectiveEndDate(designationDto.getEffectiveEndDate());
		designation.setEffectiveStartDate(designationDto.getEffectiveStartDate());
		designation.setGroupId(designationDto.getGroupId());
		designation.setUserId(designationDto.getUserId());
		//designation.setGroupId(designationDto.getGroupId());
		designation.setCompanyId(designationDto.getCompanyId());
		designation.setDateCreated(new Date());
		designation.setDateCreated(designationDto.getDateCreated());
		designation.setDateUpdate(new Date());
		designation.setActiveStatus(designationDto.getActiveStatus());
		designation.setAllowModi(designationDto.getAllowModi());
		designation.setUserIdUpdate(designationDto.getUserIdUpdate());

		List<DeptDesignationMapping> DeptDesignationMappingList = new ArrayList<DeptDesignationMapping>();
		List<DepartmentDTO> departmentDtoList = designationDto.getDepartment();

		for (DepartmentDTO departmentDTO : departmentDtoList) {
			DeptDesignationMapping deptDesignationMapping = new DeptDesignationMapping();
			deptDesignationMapping.setDepartmentId(departmentDTO.getDepartmentId());
			deptDesignationMapping.setCompanyId(departmentDTO.getCompanyId());
			deptDesignationMapping.setActiveStatus(departmentDTO.getActiveStatus());
			deptDesignationMapping.setDesignation(designation);
			deptDesignationMapping.setUserId(designationDto.getUserId());

			DeptDesignationMappingList.add(deptDesignationMapping);
		}
		designation.setDeptDesignationMapping(DeptDesignationMappingList);

		return designation;
	}

	@Override
	public List<Designation> uiDtoToDatabaseModelList(List<DesignationDTO> designationDtoList) {
		List<Designation> designationList = new ArrayList<Designation>();
		designationDtoList.forEach(item -> {
			designationList.add(uiDtoToDatabaseModel(item));
		});

		return designationList;
	}

	@Override
	public List<DesignationDTO> databaseModelToUiDtoList(List<Designation> designationList) {
		List<DesignationDTO> departmentDtoList = new ArrayList<DesignationDTO>();
		designationList.forEach(item -> {
			departmentDtoList.add(databaseModelToUiDto(item));
		});
		return departmentDtoList;
	}

	@Override
	public DesignationDTO databaseModelToUiDto(Designation designation) {
		DesignationDTO designationDto = new DesignationDTO();
		designationDto.setDesignationId(designation.getDesignationId());
		designationDto.setDesignationName(designation.getDesignationName());
		designationDto.setCompanyId(designation.getCompanyId());
		designationDto.setActiveStatus(designation.getActiveStatus());
		//designationDto.setGroupId(designation.getGroupId());
		designationDto.setUserId(designation.getUserId());
		designationDto.setAllowModi(designation.getAllowModi());
		designationDto.setDateCreated(designation.getDateCreated());

		return designationDto;
	}

	public Department uiDepartmentDtoToDatabaseModel(DepartmentDTO departmentDTO, Designation designation) {
		Department department = new Department();
		department.setActiveStatus(departmentDTO.getActiveStatus());
		Company company = new Company();
		company.setCompanyId(designation.getCompanyId());
		department.setCompany(company);
		department.setUserId(departmentDTO.getUserId());
		department.setDepartmentId(departmentDTO.getDepartmentId());
		department.setDepartmentName(departmentDTO.getDepartmentName());
		department.setUserId(departmentDTO.getUserId());
		return department;
	}

	public List<Department> uiDepartmentDTOToDatabaseModelList(List<DepartmentDTO> departmentDTOList,
			Designation designation) {

		List<Department> departmentList = new ArrayList<Department>();
		for (DepartmentDTO departmentDTO : departmentDTOList) {
			departmentList.add(uiDepartmentDtoToDatabaseModel(departmentDTO, designation));
		}
		return departmentList;
	}

	public DepartmentDTO departmentDTODatabaseModelToUiDto(Department department) {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		Company company = new Company();
		company.setCompanyId(departmentDTO.getCompanyId());
		departmentDTO.setActiveStatus(department.getActiveStatus());
		departmentDTO.setCompanyId(company.getCompanyId());
		departmentDTO.setDateCreated(new Date());
		departmentDTO.setDepartmentName(department.getDepartmentName());
		departmentDTO.setDepartmentId(department.getDepartmentId());
		departmentDTO.setUserId(department.getUserId());
		departmentDTO.setUserIdUpdate(department.getUserIdUpdate());
		return departmentDTO;
	}

	public List<DepartmentDTO> departmentDatabaseModelToUiDtoList(List<Department> departmentList, Long departmentId) {
		List<DepartmentDTO> departmentDTOList = new ArrayList<DepartmentDTO>();
		for (Department department : departmentList) {
			DepartmentDTO dto = departmentDTODatabaseModelToUiDto(department);
			dto.setDepartmentId(departmentId);
			departmentDTOList.add(dto);
		}
		return departmentDTOList;
	}

	public List<DesignationDTO> databaseModelToUiDtoLists(List<Object[]> deginationList) {
		// DesignationDTO designationDto = new DesignationDTO();
		List<DesignationDTO> designationDtoList = new ArrayList<DesignationDTO>();
		Long designationId = null;
		Long userId = null;
		Long companyId = null;
		Long groupId = null;
		String designationName = null;
		String activeStatus = null;
		Long departmentId = null;
		// String designationName ;
		for (Object[] designation : deginationList) {
			DesignationDTO designationDto = new DesignationDTO();
			if (designation[0] != null) {
				designationId = Long.valueOf(designation[0].toString());
				designationDto.setDesignationId(designationId);
			}
			if (designation[1] != null) {
				designationName = (designation[1].toString());
				designationDto.setDesignationName(designationName);
			}
			if (designation[2] != null) {

				departmentId = Long.valueOf(designation[2].toString());
				designationDto.setDepartmentId(departmentId);
			}
			if (designation[3] != null) {

				companyId = Long.valueOf(designation[3].toString());
				designationDto.setCompanyId(companyId);
			}
			if (designation[4] != null) {
				userId = Long.valueOf(designation[4].toString());
				designationDto.setUserId(userId);
			}

			if (designation[5] != null) {
				designationDto.setActiveStatus(designation[5].toString());
				activeStatus = designation[5].toString();
			}
			/*
			 * if (designation[8] != null) {
			 * departmentDto.setUserIdUpdate(Long.valueOf(designation[8].toString()));
			 * designationDto.setUserIdUpdate(Long.valueOf(designation[8].toString())); }
			 */
			designationDtoList.add(designationDto);
		}
		// designationDto.setDesignationId(designationId);
		// designationDto.setDesignationName(designationName);
		// designationDto.setCompanyId(companyId);
		// designationDto.setActiveStatus(activeStatus);
		// designationDto.setUserId(userId);
		// designationDto.setDepartment(departmentList);
		return designationDtoList;
	}
	
}