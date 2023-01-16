package com.csipl.hrms.service.authorization;

import java.util.List;

import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;



public interface SubmenuActionMasterService {

	public SubmenuActionMaster save(SubmenuActionMaster submenuAction);
	
	public List<Object[]> findSubMenuAction(Long menuId);
}
