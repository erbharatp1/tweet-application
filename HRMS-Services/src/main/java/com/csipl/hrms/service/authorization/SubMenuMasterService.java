package com.csipl.hrms.service.authorization;

import java.util.List;

import com.csipl.hrms.model.authoriztion.MenuMaster;
import com.csipl.hrms.model.authoriztion.SubMenuMaster;

public interface SubMenuMasterService {
	
	
	public List<SubMenuMaster> save(List<SubMenuMaster> subMenu);
	public List<SubMenuMaster> findSubMenu(Long menuId);
	public List<MenuMaster> findAllMenu(Long companyId);
}
