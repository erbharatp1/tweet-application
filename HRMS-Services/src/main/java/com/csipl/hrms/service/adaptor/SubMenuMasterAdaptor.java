package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.authorization.MenuMasterDTO;
import com.csipl.hrms.dto.authorization.SubMenuMasterDTO;

import com.csipl.hrms.model.authoriztion.MenuMaster;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.model.authoriztion.SubMenuMaster;


public class SubMenuMasterAdaptor  implements Adaptor<SubMenuMasterDTO,SubMenuMaster>{

	@Override
	public List<SubMenuMaster> uiDtoToDatabaseModelList(List<SubMenuMasterDTO> subMenuDtoList) {
		List<SubMenuMaster> subMenuList = new ArrayList<SubMenuMaster>();
		subMenuDtoList.forEach(subMenuDto -> {
			subMenuList.add(uiDtoToDatabaseModel(subMenuDto));
		});

		return subMenuList;
	}

	@Override
	public List<SubMenuMasterDTO> databaseModelToUiDtoList(List<SubMenuMaster> subMasterList) {
		List<SubMenuMasterDTO> subMasterDtoList = new ArrayList<SubMenuMasterDTO>();
		subMasterList.forEach(subMemu -> {
			subMasterDtoList.add(databaseModelToUiDto(subMemu));
		});
		return subMasterDtoList;
	}

	@Override
	public SubMenuMaster uiDtoToDatabaseModel(SubMenuMasterDTO subMenuDto) {
		SubMenuMaster subMenu=new SubMenuMaster();
		MenuMaster menuMaster=new MenuMaster();
		
	
	
		menuMaster.setMenuId(subMenuDto.getMenuId());
		subMenu.setMenuMaster(menuMaster);
		
		subMenu.setDateUpdate(new Date());
//		
//		subMenu.setSubmenuActionMasters(submenuActionMasters);;
//		subMenu.setSubMenuId(subMenuDto.getSubMenuId());
//		subMenu.setStatus(subMenuDto.getStatus());
//		subMenu.setUserId(subMenuDto.getUserId());
//		subMenu.setUserIdUpdate(subMenuDto.getUserIdUpdate());
//		            
		if(subMenuDto.getSubmenuId() != null) {
			subMenu.setDateCreated(subMenuDto.getDateCreated());
		}else {
			
			subMenu.setDateCreated(new Date());
		}
		
		return subMenu;
	}

	@Override
	public SubMenuMasterDTO databaseModelToUiDto(SubMenuMaster subMenu) {
		SubMenuMasterDTO subMenuDTO=new SubMenuMasterDTO();
	
		subMenuDTO.setMenuId(subMenu.getMenuMaster().getMenuId());
	
		subMenuDTO.setDateUpdate(new Date());
		subMenuDTO.setSubmenuName(subMenu.getSubmenuName());
		subMenuDTO.setSubmenuId(subMenu.getSubmenuId());
		subMenuDTO.setStatus(subMenu.getStatus());
		subMenuDTO.setUserId(subMenu.getUserId());
		subMenuDTO.setUserIdUpdate(subMenu.getUserIdUpdate());
		subMenuDTO.setDateCreated(subMenu.getDateCreated());
		return subMenuDTO;
	}

	public List<MenuMasterDTO> databaseModelToUiDtoMenuList(List<MenuMaster> menuList) {
		List<MenuMasterDTO> menuMasterDtoList = new ArrayList<MenuMasterDTO>();
		menuList.forEach(menuMaster -> {
			menuMasterDtoList.add(databaseModelToMenuUiDto(menuMaster));
		});
		return menuMasterDtoList;
	}

	private MenuMasterDTO databaseModelToMenuUiDto(MenuMaster menuMaster) {
		MenuMasterDTO menuMasterDTO=new MenuMasterDTO();
		menuMasterDTO.setCompanyId(menuMaster.getCompany().getCompanyId());
		menuMasterDTO.setMenuId(menuMaster.getMenuId());
		menuMasterDTO.setMenuName(menuMaster.getMenuName());
		return menuMasterDTO;
	}

}
