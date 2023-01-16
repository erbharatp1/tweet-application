package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.RoleSubmenuActionMaster;


public interface RoleSubmenuActionMasterRepository extends CrudRepository<RoleSubmenuActionMaster, Long>{
	
	String findSubMenuAction = "SELECT rm.roleSubmenuActionId,rm.roleId,rm.submenuActionId,rm.status FROM RoleSubmenuActionMaster rm JOIN SubmenuActionMaster sm on sm.submenuActionId=rm.submenuActionId JOIN  SubMenuMaster su ON su.submenuId = sm.submenuId WHERE rm.roleId=?1 AND su.menuId =?2";
	@Query(value = findSubMenuAction, nativeQuery = true)
	public List<Object[]>  findSubMenu(Long roleId,Long menuId);
//	SELECT rm.roleDescription,sm.uniqueCode ,sm.status  FROM SubmenuActionMaster sm JOIN RoleSubmenuActionMaster sa ON sa.submenuActionId = sm.submenuActionId JOIN RoleMaster rm ON rm.roleId = sa.roleId WHERE sa.status='AC'

	String fetchRolesPermissionList ="SELECT rm.roleDescription,sm.uniqueCode ,sm.status  FROM SubmenuActionMaster sm JOIN RoleSubmenuActionMaster sa ON sa.submenuActionId = sm.submenuActionId JOIN RoleMaster rm ON rm.roleId = sa.roleId WHERE sa.status='AC' AND rm.roleDescription =?1" ;
		@Query(value=fetchRolesPermissionList ,nativeQuery = true)
		public List<Object[]> getAllRolesPermission(String roleDescription);

}
