package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.authorization.SubmenuActionMasterDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.authoriztion.SubMenuMaster;
import com.csipl.hrms.model.authoriztion.SubmenuActionMaster;

public class SubmenuActionMasterAdaptor implements Adaptor<SubmenuActionMasterDTO,SubmenuActionMaster>{

	@Override
	public List<SubmenuActionMaster> uiDtoToDatabaseModelList(List<SubmenuActionMasterDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubmenuActionMasterDTO> databaseModelToUiDtoList(List<SubmenuActionMaster> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubmenuActionMaster uiDtoToDatabaseModel(SubmenuActionMasterDTO submenuActionMasterDTO) {
		SubmenuActionMaster submenuActionMaster=new SubmenuActionMaster();
		SubMenuMaster subMenuMaster=new SubMenuMaster();
		
		submenuActionMaster.setUserId(submenuActionMasterDTO.getUserId());
		submenuActionMaster.setDateCreated(new Date());
		submenuActionMaster.setDescription(submenuActionMasterDTO.getDescription());
		submenuActionMaster.setStatus(submenuActionMasterDTO.getStatus());
		submenuActionMaster.setSubmenuActionId(submenuActionMasterDTO.getSubmenuActionId());
		submenuActionMaster.setTitle(submenuActionMasterDTO.getTitle());
		submenuActionMaster.setUrlPath(submenuActionMasterDTO.getUrlPath());
		System.out.println("subMenuId==============>>"+submenuActionMasterDTO.getSubmenuId());
		submenuActionMaster.setUniqueCode(submenuActionMasterDTO.getUniqueCode());
		subMenuMaster.setSubmenuId(submenuActionMasterDTO.getSubmenuId());
		submenuActionMaster.setSubMenuMaster(subMenuMaster);
		return submenuActionMaster;
	}

	@Override
	public SubmenuActionMasterDTO databaseModelToUiDto(SubmenuActionMaster dbobj) {
		// TODO Auto-generated method stub
		return null;
	}
//	sa.submenuActionId,sa.submenuId,sa.urlPath,sa.title,sa.uniqueCode,sa.description
	public List<SubmenuActionMasterDTO> databaseModelToSubmenuUiDto(List<Object[]> submenuActionList) {
		List<SubmenuActionMasterDTO> submenuActionMasterDTOList=new ArrayList<SubmenuActionMasterDTO>();
		for(Object[] submenuObj:submenuActionList)
		{
			SubmenuActionMasterDTO submenuActionMasterDTO=new SubmenuActionMasterDTO();
			 Long submenuActionId=submenuObj[0]!=null?Long.parseLong(submenuObj[0].toString()):null;
			 Long submenuId = submenuObj[1]!=null?Long.parseLong(submenuObj[1].toString()):null;
			 String urlPath=submenuObj[2]!=null?(String)submenuObj[2]:null;
			 String title=submenuObj[3]!=null?(String)submenuObj[3]:null;
			 String uniqueCode=submenuObj[4]!=null?(String)submenuObj[4]:null;
			 String description=submenuObj[5]!=null?(String)submenuObj[5]:null;
		
			submenuActionMasterDTO.setSubmenuActionId(submenuActionId);
			submenuActionMasterDTO.setSubmenuId(submenuId);
			submenuActionMasterDTO.setUrlPath(urlPath);
			submenuActionMasterDTO.setTitle(title);
			submenuActionMasterDTO.setUniqueCode(uniqueCode);
			submenuActionMasterDTO.setDescription(description);
			submenuActionMasterDTOList.add(submenuActionMasterDTO);
			
		}
		
		
		return submenuActionMasterDTOList;
	}

	

}
