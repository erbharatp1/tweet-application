package com.csipl.hrms.service.authorization;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.authoriztion.MenuMaster;
import com.csipl.hrms.model.authoriztion.SubMenuMaster;
import com.csipl.hrms.service.authorization.repository.MenuMasterRepository;
import com.csipl.hrms.service.authorization.repository.SubMenuMasterRepository;


@Service("SubMenuMasterService")
public class SubMenuMasterServiceImpl implements SubMenuMasterService {

	@Autowired
	SubMenuMasterRepository subMenuMasterRepository;
	
	@Autowired
	MenuMasterRepository menuMasterRepository;
	@Override
	public List<SubMenuMaster> save(List<SubMenuMaster> subMenuList) {
		List<SubMenuMaster> subMenu = (List<SubMenuMaster>) subMenuMasterRepository.save(subMenuList);
		return subMenu;
	}

	@Override
	public List<SubMenuMaster> findSubMenu(Long menuId) {
		return subMenuMasterRepository.findSubMenu( menuId);
	}

	@Override
	public List<MenuMaster> findAllMenu(Long companyId) {
		// TODO Auto-generated method stub
		return menuMasterRepository.findAllMenu(companyId);
	}

}
