package com.csipl.hrms.authorization.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.authorization.SubmenuActionMasterDTO;
import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;
import com.csipl.hrms.service.adaptor.SubmenuActionMasterAdaptor;
import com.csipl.hrms.service.authorization.SubmenuActionMasterService;

@RequestMapping("/subMenuAction")
@RestController
public class SubmenuActionMasterController {

	@Autowired
	SubmenuActionMasterService submenuActionMasterService;
	
	SubmenuActionMasterAdaptor submenuActionMasterAdaptor=new SubmenuActionMasterAdaptor();
	
	@RequestMapping(method = RequestMethod.POST)
	public void saveSubmenuAction(@RequestBody SubmenuActionMasterDTO submenuActionMasterDTO, HttpServletRequest req) {
		
		SubmenuActionMaster submenuActionMaster = submenuActionMasterAdaptor.uiDtoToDatabaseModel(submenuActionMasterDTO);
		SubmenuActionMaster  submenuAction=	submenuActionMasterService.save(submenuActionMaster);
		
	}
	
	@RequestMapping(path = "/submenu/{menuId}", method = RequestMethod.GET)
	public @ResponseBody List<SubmenuActionMasterDTO> subMenuActio(@PathVariable("menuId") Long menuId,
			HttpServletRequest req) {
		List<Object[]> submenuActionList = submenuActionMasterService.findSubMenuAction(menuId);
		List<SubmenuActionMasterDTO> submenuActionDtoList = submenuActionMasterAdaptor.databaseModelToSubmenuUiDto(submenuActionList);
		return submenuActionDtoList;
	}
}
