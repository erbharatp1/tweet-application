package com.csipl.tms.leave.adaptor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.tms.dto.leave.LeaveSchemeMasterDTO;
import com.csipl.tms.dto.shift.ShiftDTO;
import com.csipl.tms.model.leave.LeaveSchemeMaster;
import com.csipl.tms.model.shift.Shift;
import com.csipl.tms.service.Adaptor;

public class LeaveSchemeAdaptor implements Adaptor<LeaveSchemeMasterDTO, LeaveSchemeMaster>{

	@Override
	public List<LeaveSchemeMaster> uiDtoToDatabaseModelList(List<LeaveSchemeMasterDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaveSchemeMasterDTO> databaseModelToUiDtoList(List<LeaveSchemeMaster> leaveSchemeList) {
		
		List<LeaveSchemeMasterDTO> leaveSchemeDTOList = new ArrayList<LeaveSchemeMasterDTO>();
		for (LeaveSchemeMaster leave : leaveSchemeList) {
			leaveSchemeDTOList.add(databaseModelToUiDto(leave));
		}
		return leaveSchemeDTOList;
	}

	@Override
	public LeaveSchemeMaster uiDtoToDatabaseModel(LeaveSchemeMasterDTO leaveSchemeDTO) {
		LeaveSchemeMaster leaveSchemeMaster =new LeaveSchemeMaster();
		leaveSchemeMaster.setDateUpdates(new Date());
		leaveSchemeMaster.setLeaveSchemeId(leaveSchemeDTO.getLeaveSchemeId());
		leaveSchemeMaster.setLeaveSchemeName(leaveSchemeDTO.getLeaveSchemeName());
		leaveSchemeMaster.setUserId(leaveSchemeDTO.getUserId());
		leaveSchemeMaster.setUserIdUpdate(leaveSchemeDTO.getUserIdUpdate());
		leaveSchemeMaster.setStatus(leaveSchemeDTO.getStatus());
		
		leaveSchemeMaster.setLeavePeriodId(leaveSchemeDTO.getLeavePeriodId());
		
		if(leaveSchemeDTO.getLeaveSchemeId() != null) {
			leaveSchemeMaster.setDateCreated(leaveSchemeDTO.getDateCreated());
		}else {
			leaveSchemeMaster.setDateCreated(new Date());
		}
		return leaveSchemeMaster;
	}

	@Override
	public LeaveSchemeMasterDTO databaseModelToUiDto(LeaveSchemeMaster leaveScheme) {
		LeaveSchemeMasterDTO leaveSchemeDTO=new LeaveSchemeMasterDTO();
		leaveSchemeDTO.setDateCreated(leaveScheme.getDateCreated());
		leaveSchemeDTO.setDateUpdates(leaveScheme.getDateUpdates());
		leaveSchemeDTO.setLeaveSchemeId(leaveScheme.getLeaveSchemeId());
		leaveSchemeDTO.setLeaveSchemeName(leaveScheme.getLeaveSchemeName());
		leaveSchemeDTO.setUserId(leaveScheme.getUserId());
		leaveSchemeDTO.setUserIdUpdate(leaveScheme.getUserIdUpdate());
		leaveSchemeDTO.setStatus(leaveScheme.getStatus());
		leaveSchemeDTO.setLeavePeriodId(leaveScheme.getLeavePeriodId());
		return leaveSchemeDTO;
	}

}
