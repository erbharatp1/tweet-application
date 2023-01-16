package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;



  public interface SubmenuActionMasterRepository extends CrudRepository<SubmenuActionMaster, Long>{

	
//	  SELECT sa.submenuActionId,sa.submenuId,sa.urlPath,sa.title,sa.uniqueCode,sa.description FROM SubmenuActionMaster sa JOIN SubMenuMaster sm ON sa.submenuId = sm.submenuId WHERE sm.menuId=1
		String findSubMenuAction = "SELECT sa.submenuActionId,sa.submenuId,sa.urlPath,sa.title,sa.uniqueCode,sa.description FROM SubmenuActionMaster sa JOIN SubMenuMaster sm ON sa.submenuId = sm.submenuId WHERE sm.menuId=?1";
		
		@Query(value = findSubMenuAction, nativeQuery = true)
		public List<Object[]> findSubMenuAction(Long menuId);
  }
