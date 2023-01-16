package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.csipl.hrms.dto.authorization.EmployeeRoleMasterDTO;
import com.csipl.hrms.dto.authorization.RoleMasterDTO;
import com.csipl.hrms.dto.organisation.DesignationDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;

public class RoleMasterAdaptor implements Adaptor<RoleMasterDTO, RoleMaster> {

	@Override
	public List<RoleMaster> uiDtoToDatabaseModelList(List<RoleMasterDTO> roleMasterDtoList) {
		return roleMasterDtoList.stream().map(roles -> uiDtoToDatabaseModel(roles)).collect(Collectors.toList());
//		List<RoleMaster> roleMaster=new ArrayList<RoleMaster>();
//		for(RoleMasterDTO roleMasterDto:roleMasterDtoList) {
//		
//			roleMaster.add(uiDtoToDatabaseModel(roleMasterDto));
//		}
//		return roleMaster;
	}

	@Override
	public List<RoleMasterDTO> databaseModelToUiDtoList(List<RoleMaster> roleMasterList) {
		return roleMasterList.stream().map(role -> databaseModelToUiDto(role)).collect(Collectors.toList());
//		List<RoleMasterDTO> roleMasterDtoList = new ArrayList<RoleMasterDTO>();
//		for (RoleMaster roleMaster : roleMasterList) {
//
//			roleMasterDtoList.add(databaseModelToUiDto(roleMaster));
//		}
//		return roleMasterDtoList;

	}

	@Override
	public RoleMaster uiDtoToDatabaseModel(RoleMasterDTO roleMasterDTO) {

		RoleMaster roleMaster = new RoleMaster();

		roleMaster.setRoleId(roleMasterDTO.getRoleId());
		roleMaster.setRoleDescription(roleMasterDTO.getRoleDescription());
		roleMaster.setUserId(roleMasterDTO.getUserId());
		roleMaster.setUserIdUpdate(roleMasterDTO.getUserIdUpdate());
		roleMaster.setActiveStatus(roleMasterDTO.getActiveStatus());
		roleMaster.setDateUpdate(new Date());
		if (roleMasterDTO.getRoleId() != null) {
			roleMaster.setDateCreated(roleMasterDTO.getDateCreated());
		} else {
			roleMaster.setDateCreated(new Date());
		}

		return roleMaster;
	}

	@Override
	public RoleMasterDTO databaseModelToUiDto(RoleMaster roleMaster) {
		RoleMasterDTO roleMasterDto = new RoleMasterDTO();
		roleMasterDto.setRoleId(roleMaster.getRoleId());
		roleMasterDto.setRoleDescription(roleMaster.getRoleDescription());
		roleMasterDto.setUserId(roleMaster.getUserId());
		roleMasterDto.setUserIdUpdate(roleMaster.getUserIdUpdate());
		roleMasterDto.setDateCreated(roleMaster.getDateCreated());
		roleMasterDto.setActiveStatus(roleMaster.getActiveStatus());

		return roleMasterDto;
	}

	/**
	 * 
	 * @param empRoleMastersList
	 * @return
	 */
	public List<EmployeeRoleMasterDTO> databaseModelToUiDtoEmpList(List<Object[]> empRoleMastersList) {
		List<EmployeeRoleMasterDTO> masterDTOs = new ArrayList<EmployeeRoleMasterDTO>();
		String roleDescription = null;
		Long userId = null;
		Long userRolesSrNo = null;
		Long employeeId = null;
		Long roleId = null;
		Long deptId = null;
		String employeeCode = null;
		String firstName = null;
		String lastName = null;
		String departmentName = null;
		
		/**
		 * e.firstName,e.lastName, e.employeeId,e.employeeCode ,e.userId ,
		 * dept.departmentName,al.roleDescription,al.roleId
		 */
		for (Object[] objects : empRoleMastersList) {
			EmployeeRoleMasterDTO dto = new EmployeeRoleMasterDTO();
			if (objects[0] != null) {
				firstName = (objects[0].toString());
				dto.setFirstName(firstName);
			}
			if (objects[1] != null) {
				lastName = (objects[1].toString());
				dto.setLastName(lastName);
			}
			if (objects[2] != null) {
				employeeId = Long.valueOf(objects[2].toString());
				dto.setEmployeeId(employeeId);
			}
			if (objects[3] != null) {
				employeeCode = (objects[3].toString());
				dto.setEmployeeCode(employeeCode);
			}
			if (objects[4] != null) {
				userId = Long.valueOf(objects[4].toString());
				dto.setUserId(userId);
			}
			if (objects[5] != null) {
				departmentName = (objects[5].toString());
				dto.setDepartmentName(departmentName);
			}
			if (objects[6] != null) {
				roleDescription = (objects[6].toString());
				dto.setRoleDescription(roleDescription);
			}
			if (objects[7] != null) {
				roleId = Long.valueOf(objects[7].toString());
				dto.setRoleId(roleId);
			}
			if (objects[8] != null) {
				deptId = Long.valueOf(objects[8].toString());
				dto.setDepartmentId(deptId);
			}
			if (objects[9] != null) {
				userRolesSrNo = Long.valueOf(objects[9].toString());
				dto.setUserRolesSrNo(userRolesSrNo);
			}
			masterDTOs.add(dto);
		}
		return masterDTOs;
	}
	public List<EmployeeRoleMasterDTO> databaseModelToUiDtoEmp (List<Object[]> empRoleMastersList) {
		List<EmployeeRoleMasterDTO> masterDTOs = new ArrayList<EmployeeRoleMasterDTO>();
		String roleDescription = null;
		Long userId = null;
		Long userRolesSrNo = null;
		Long employeeId = null;
		Long roleId = null;
		Long deptId = null;
		String employeeCode = null;
		String firstName = null;
		String lastName = null;
		String departmentName = null;
		String employeeLogoPath;
		String dateOfJoining;
		String designationName;
		String gradesName;
	 
		for (Object[] objects : empRoleMastersList) {
			EmployeeRoleMasterDTO dto = new EmployeeRoleMasterDTO();
			if (objects[0] != null) {
				firstName = (objects[0].toString());
				dto.setFirstName(firstName);
			}
			if (objects[1] != null) {
				lastName = (objects[1].toString());
				dto.setLastName(lastName);
			}
			if (objects[2] != null) {
				employeeId = Long.valueOf(objects[2].toString());
				dto.setEmployeeId(employeeId);
			}
			if (objects[3] != null) {
				employeeCode = (objects[3].toString());
				dto.setEmployeeCode(employeeCode);
			}
			if (objects[4] != null) {
				userId = Long.valueOf(objects[4].toString());
				dto.setUserId(userId);
			}
			if (objects[5] != null) {
				departmentName = (objects[5].toString());
				dto.setDepartmentName(departmentName);
			}
			if (objects[6] != null) {
				roleDescription = (objects[6].toString());
				dto.setRoleDescription(roleDescription);
			}
			if (objects[7] != null) {
				roleId = Long.valueOf(objects[7].toString());
				dto.setRoleId(roleId);
			}
			if (objects[8] != null) {
				deptId = Long.valueOf(objects[8].toString());
				dto.setDepartmentId(deptId);
			}
			if (objects[9] != null) {
				userRolesSrNo = Long.valueOf(objects[9].toString());
				dto.setUserRolesSrNo(userRolesSrNo);
			}
			if (objects[10] != null) {
				employeeLogoPath = (objects[10].toString());
				dto.setEmployeeLogoPath(employeeLogoPath);
			}
			if (objects[11] != null) {
				dateOfJoining = (objects[11].toString());
				dto.setDateOfJoining(dateOfJoining);
			}
			if (objects[12] != null) {
				designationName = (objects[12].toString());
				dto.setDesignationName(designationName);
			}
			if (objects[13] != null) {
				gradesName = (objects[13].toString());
				dto.setGradesName(gradesName);
			}
			masterDTOs.add(dto);
		}
		return masterDTOs;
	}
	public List<EmployeeRoleMasterDTO> databaseModelToUiDtoRolePermission (List<Object[]> empRoleMastersList) {
		List<EmployeeRoleMasterDTO> masterDTOs = new ArrayList<EmployeeRoleMasterDTO>();
		String menuName = null;
		String title = null;
		for (Object[] objects : empRoleMastersList) {
			EmployeeRoleMasterDTO dto = new EmployeeRoleMasterDTO();
			if (objects[0] != null) {
				menuName = (objects[0].toString());
				dto.setMenuName(menuName);
			}
			if (objects[1] != null) {
				title = (objects[1].toString());
				dto.setTitle(title);
			}
			masterDTOs.add(dto);
		}
		return masterDTOs;
	}
}
