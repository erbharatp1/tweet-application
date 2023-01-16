package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.csipl.common.dto.notification.RolesMenuDto;
import com.csipl.hrms.dto.authorization.RoleSubmenuActionMasterDTO;
import com.csipl.hrms.dto.authorization.SubmenuActionMasterDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.authoriztion.RoleSubmenuActionMaster;
import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;


public class RoleSubmenuActionMasterAdaptor  implements Adaptor<RoleSubmenuActionMasterDTO,RoleSubmenuActionMaster>{

	@Override
	public List<RoleSubmenuActionMaster> uiDtoToDatabaseModelList(List<RoleSubmenuActionMasterDTO> roleSubmenuActionList) {
		List<RoleSubmenuActionMaster> roleSubmenuList = new ArrayList<RoleSubmenuActionMaster>();
		roleSubmenuActionList.forEach(subMenuDto -> {
			roleSubmenuList.add(uiDtoToDatabaseModel(subMenuDto));
		});

		return roleSubmenuList;
	}

	@Override
	public List<RoleSubmenuActionMasterDTO> databaseModelToUiDtoList(List<RoleSubmenuActionMaster> roleSubmenuActionMasterList) {
		List<RoleSubmenuActionMasterDTO> rolesubmenuMasterDtoList = new ArrayList<RoleSubmenuActionMasterDTO>();
		roleSubmenuActionMasterList.forEach(subMemu -> {
			rolesubmenuMasterDtoList.add(databaseModelToUiDto(subMemu));
		});
		return rolesubmenuMasterDtoList;
	}

	@Override
	public RoleSubmenuActionMaster uiDtoToDatabaseModel(RoleSubmenuActionMasterDTO roleSubmasterDto) {
		RoleSubmenuActionMaster roleSubmenuActionMaster=new RoleSubmenuActionMaster();
		
		SubmenuActionMaster submenuActionMaster=new SubmenuActionMaster();
		submenuActionMaster.setSubmenuActionId(roleSubmasterDto.getSubmenuActionId());
		roleSubmenuActionMaster.setSubmenuActionMaster(submenuActionMaster);
		
		RoleMaster roleMaster=new RoleMaster();
		roleMaster.setRoleId(roleSubmasterDto.getRoleId());
		roleSubmenuActionMaster.setRoleMaster(roleMaster);
		
		roleSubmenuActionMaster.setStatus(roleSubmasterDto.getStatus());
		roleSubmenuActionMaster.setRoleSubmenuActionId(roleSubmasterDto.getRoleSubmenuActionId());
		
		return roleSubmenuActionMaster;
	}

	@Override
	public RoleSubmenuActionMasterDTO databaseModelToUiDto(RoleSubmenuActionMaster roleSubmenuActionMaster) {
		RoleSubmenuActionMasterDTO roleSubmenuActionMasterDTO=new RoleSubmenuActionMasterDTO();
		roleSubmenuActionMasterDTO.setRoleSubmenuActionId( roleSubmenuActionMaster.getRoleSubmenuActionId());
		roleSubmenuActionMasterDTO.setStatus(roleSubmenuActionMaster.getStatus());
		roleSubmenuActionMasterDTO.setRoleId(roleSubmenuActionMaster.getRoleMaster().getRoleId());
		roleSubmenuActionMasterDTO.setSubmenuActionId(roleSubmenuActionMaster.getSubmenuActionMaster().getSubmenuActionId());
		return  roleSubmenuActionMasterDTO;
	}
//	rm.roleSubmenuActionId,rm.roleId,rm.submenuActionId,rm.status
	public List<RoleSubmenuActionMasterDTO> databaseModelActionToUiDtoList(List<Object[]> roleSubmenuActionList) {
		List<RoleSubmenuActionMasterDTO> roleSubmenuActionMasterDTO=new ArrayList<RoleSubmenuActionMasterDTO>();
		for(Object[] roleSubmenuObj:roleSubmenuActionList)
		{
			RoleSubmenuActionMasterDTO roleSubmenuActionDTO=new RoleSubmenuActionMasterDTO();
			 Long roleSubmenuActionId=roleSubmenuObj[0]!=null?Long.parseLong(roleSubmenuObj[0].toString()):null;
			 Long roleId = roleSubmenuObj[1]!=null?Long.parseLong(roleSubmenuObj[1].toString()):null;
			 Long submenuActionId = roleSubmenuObj[2]!=null?Long.parseLong(roleSubmenuObj[2].toString()):null;
			 String status=roleSubmenuObj[3]!=null?(String)roleSubmenuObj[3]:null;
			
		
			 roleSubmenuActionDTO.setRoleSubmenuActionId(roleSubmenuActionId);
			 roleSubmenuActionDTO.setRoleId(roleId);
			 roleSubmenuActionDTO.setStatus(status);
			 roleSubmenuActionDTO.setSubmenuActionId(submenuActionId);
			
			roleSubmenuActionMasterDTO.add(roleSubmenuActionDTO);
			
		}
		
		return roleSubmenuActionMasterDTO;
	}

	public List<RoleSubmenuActionMasterDTO> databaseRolePermissionToUiDtoList(List<Object[]> roleSubmenuActionList) {
		
		List<RoleSubmenuActionMasterDTO> rolesPermissionDtoList = new ArrayList<RoleSubmenuActionMasterDTO>();
		  for(Object [] roleObj:roleSubmenuActionList) {
			
			  RoleSubmenuActionMasterDTO rolesMenu = new RoleSubmenuActionMasterDTO();
			  rolesMenu.setRoleDescription(roleObj[0]!=null?String.valueOf( roleObj[0] ):"");
			  rolesMenu.setUniqueCode(roleObj[0]!=null?String.valueOf( roleObj[1] ):"");
			  rolesMenu.setStatus(roleObj[0]!=null?String.valueOf( roleObj[2] ):"");
			  rolesPermissionDtoList.add(rolesMenu); 
			  
		  }
		 
		return rolesPermissionDtoList;
	}



}
