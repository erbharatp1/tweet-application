package com.csipl.hrms.service.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;
import com.csipl.hrms.service.authorization.repository.SubmenuActionMasterRepository;
@Service("submenuActionMasterService")
public class SubmenuActionMasterServiceImpl implements SubmenuActionMasterService{

	
	@Autowired
	SubmenuActionMasterRepository submenuActionMasterRepository;
	
	@Override
	public SubmenuActionMaster save(SubmenuActionMaster submenuAction) {
	
		return submenuActionMasterRepository.save(submenuAction);
	}

	@Override
	public List<Object[]> findSubMenuAction(Long menuId) {
		
		return submenuActionMasterRepository.findSubMenuAction(menuId);
	}

}
