package com.csipl.common.services.permission;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.common.dto.notification.RolesMenuDto;
import com.csipl.common.services.permission.repository.RolesPermissionRepository;



@Service("rolesPermissionServices")
public class RolesPermissionServicesImpl implements RolesPermissionServices{
	
	@Autowired
	RolesPermissionRepository rolesPermissionRepository;

	@Override
	public List<RolesMenuDto> getAllRolesPermission() {
		// TODO Auto-generated method stub
		
		List<Object[]> systemObjectList = rolesPermissionRepository.getAllRolesPermission();
		List<RolesMenuDto> rolesMenuDtoList = new ArrayList<RolesMenuDto>();
		  for(Object [] systemObject:systemObjectList) {
			
			  RolesMenuDto rolesMenu = new RolesMenuDto();
			  rolesMenu.setMenu(systemObject[0]!=null?String.valueOf( systemObject[0] ):"");
			  rolesMenu.setSubMenu(systemObject[0]!=null?String.valueOf( systemObject[1] ):"");
			  rolesMenu.setRole(systemObject[0]!=null?String.valueOf( systemObject[2] ):"");
			  rolesMenuDtoList.add(rolesMenu); 
			  
		  }
		 
		return rolesMenuDtoList;
	}

}
