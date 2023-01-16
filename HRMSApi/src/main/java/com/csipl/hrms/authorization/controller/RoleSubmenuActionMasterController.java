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

import com.csipl.common.dto.notification.RolesMenuDto;
import com.csipl.hrms.dto.authorization.RoleSubmenuActionMasterDTO;

import com.csipl.hrms.model.authoriztion.RoleSubmenuActionMaster;
import com.csipl.hrms.service.adaptor.RoleSubmenuActionMasterAdaptor;
import com.csipl.hrms.service.authorization.RoleSubmenuActionMasterService;

@RequestMapping("/roleSubmenuAction")
@RestController
public class RoleSubmenuActionMasterController {
	private static final Logger logger = LoggerFactory.getLogger(RoleSubmenuActionMasterController.class);

	@Autowired
	RoleSubmenuActionMasterService roleSubmenuActionMasterService;

	RoleSubmenuActionMasterAdaptor roleSubmenuActionMasterAdaptor = new RoleSubmenuActionMasterAdaptor();

	@RequestMapping(method = RequestMethod.POST)
	public void saveSubMenu(@RequestBody List<RoleSubmenuActionMasterDTO> submenuActionMasterDTOList,
			HttpServletRequest req) {
		logger.info("saveCandidateFamily is calling : CandidateFamilyDTO " + submenuActionMasterDTOList);
		List<RoleSubmenuActionMaster> submenuActionMasterList = roleSubmenuActionMasterAdaptor
				.uiDtoToDatabaseModelList(submenuActionMasterDTOList);
		List<RoleSubmenuActionMaster> subMenubj = roleSubmenuActionMasterService.save(submenuActionMasterList);

	}

	@RequestMapping(path = "/roleSubmenu/{roleId}/{menuId}", method = RequestMethod.GET)
	public @ResponseBody List<RoleSubmenuActionMasterDTO> roleSubmenuActionMaster(@PathVariable("roleId") Long roleId,
			@PathVariable("menuId") Long menuId, HttpServletRequest req) {
		logger.info("roleSubmenuActionMaster is calling : roleId " + roleId);
		List<Object[]> roleSubmenuActionList = roleSubmenuActionMasterService.findAllRoleSubmenu(roleId, menuId);
		List<RoleSubmenuActionMasterDTO> submenuActionDtoList = roleSubmenuActionMasterAdaptor
				.databaseModelActionToUiDtoList(roleSubmenuActionList);
		return submenuActionDtoList;
	}

	@RequestMapping(path = "/rolesPermission/{roleDescription}", method = RequestMethod.GET)
	public @ResponseBody List<RoleSubmenuActionMasterDTO> systemPermission(
			@PathVariable("roleDescription") String roleDescription, HttpServletRequest req) {
		logger.info("======systemPermission===============");
		List<Object[]> roleSubmenuActionList = roleSubmenuActionMasterService.getAllRolesPermission(roleDescription);
		List<RoleSubmenuActionMasterDTO> roleSubmenuActionMasterDTOList = roleSubmenuActionMasterAdaptor.databaseRolePermissionToUiDtoList(roleSubmenuActionList);
		
		return roleSubmenuActionMasterDTOList;
		
		
	}
}






