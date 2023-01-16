package com.csipl.hrms.service.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.authoriztion.RoleSubmenuActionMaster;
import com.csipl.hrms.service.authorization.repository.RoleSubmenuActionMasterRepository;

@Service("RoleSubMenuMasterService")
public class RoleSubmenuActionMasterServiceImpl implements RoleSubmenuActionMasterService{

	@Autowired
	RoleSubmenuActionMasterRepository roleSubmenuActionMasterRepository;
	
	@Override
	public List<RoleSubmenuActionMaster> save(List<RoleSubmenuActionMaster> subMenuList) {
		List<RoleSubmenuActionMaster> subMenu = (List<RoleSubmenuActionMaster>) roleSubmenuActionMasterRepository.save(subMenuList);
		return subMenu;
	}

	@Override
	public List<Object[]> findAllRoleSubmenu(Long roleId,Long menuId) {
		// TODO Auto-generated method stub
		return roleSubmenuActionMasterRepository.findSubMenu(roleId,menuId);
	}

	@Override
	public List<Object[]> getAllRolesPermission(String roleDescription) {
		// TODO Auto-generated method stub
		return roleSubmenuActionMasterRepository.getAllRolesPermission(roleDescription);
	}

}
