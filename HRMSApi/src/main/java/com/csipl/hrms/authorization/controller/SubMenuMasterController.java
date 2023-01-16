package com.csipl.hrms.authorization.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.authorization.MenuMasterDTO;
import com.csipl.hrms.dto.authorization.SubMenuMasterDTO;
import com.csipl.hrms.model.authoriztion.MenuMaster;
import com.csipl.hrms.model.authoriztion.SubMenuMaster;

import com.csipl.hrms.service.adaptor.SubMenuMasterAdaptor;
import com.csipl.hrms.service.authorization.SubMenuMasterService;


@RequestMapping("/subMenu")
@RestController
public class SubMenuMasterController {
	
	private static final Logger logger = LoggerFactory.getLogger(SubMenuMasterController.class);

	@Autowired
	SubMenuMasterService subMenuMasterService;
	
	SubMenuMasterAdaptor subMenuMasterAdaptor = new SubMenuMasterAdaptor();
	
	@RequestMapping(method = RequestMethod.POST)
	public void saveSubMenu(@RequestBody List<SubMenuMasterDTO> subMenuMasterDTOList, HttpServletRequest req) {
		logger.info("saveCandidateFamily is calling : CandidateFamilyDTO "+ subMenuMasterDTOList);
		List<SubMenuMaster> subMenuMasterList = subMenuMasterAdaptor.uiDtoToDatabaseModelList(subMenuMasterDTOList);
	List<SubMenuMaster> subMenubj =	subMenuMasterService.save(subMenuMasterList);
	
	}

	
	@RequestMapping(value="/{menuId}", method = RequestMethod.GET)
	public @ResponseBody List<SubMenuMasterDTO> getSubMenu(@PathVariable("menuId") Long menuId,
			HttpServletRequest req) {
		
		logger.info("SubMenuMasterDTO SubMenuMasterDTO  menuId is :" + menuId);
		List<SubMenuMaster> submenuList=subMenuMasterService.findSubMenu(menuId);
		
		List<SubMenuMasterDTO> submenuDTOList=subMenuMasterAdaptor.databaseModelToUiDtoList(submenuList);
		logger.info("getting  submenuDetails :==========================="  + submenuDTOList);
		return submenuDTOList;
	}
	
	@RequestMapping(value="allMenu/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<MenuMasterDTO> getAllMenu(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) {
		
		logger.info("MenuMasterDTO MenuMasterDTO  companyId is :" + companyId);
  	List<MenuMaster> menuList=subMenuMasterService.findAllMenu(companyId);	
  	
  		List<MenuMasterDTO> menuDTOList=subMenuMasterAdaptor.databaseModelToUiDtoMenuList(menuList);
  
		return menuDTOList;
	}
	
}









