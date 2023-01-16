package com.csipl.hrms.service.authorization;
import java.util.List;
import com.csipl.hrms.model.authoriztion.RoleSubmenuActionMaster;

public interface RoleSubmenuActionMasterService {
	public List<RoleSubmenuActionMaster> save(List<RoleSubmenuActionMaster> subMenu);
	
	public List<Object[]> findAllRoleSubmenu(Long roleId,Long menuId);
	
	public List<Object[]> getAllRolesPermission(String roleDescription);
}
