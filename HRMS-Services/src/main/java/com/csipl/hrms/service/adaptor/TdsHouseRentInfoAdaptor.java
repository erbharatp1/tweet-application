package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.payroll.TdsHouseRentFileInfoDTO;
import com.csipl.hrms.dto.payroll.TdsHouseRentInfoDTO;
import com.csipl.hrms.model.payroll.TdsHouseRentFileInfo;
import com.csipl.hrms.model.payroll.TdsHouseRentInfo;

public class TdsHouseRentInfoAdaptor implements Adaptor<TdsHouseRentInfoDTO, TdsHouseRentInfo>{

	@Override
	public List<TdsHouseRentInfo> uiDtoToDatabaseModelList(List<TdsHouseRentInfoDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsHouseRentInfoDTO> databaseModelToUiDtoList(List<TdsHouseRentInfo> tdsHouseRentInfoList) {
		
		List<TdsHouseRentInfoDTO>  tdsHouseRentInfoDTOList = new ArrayList<TdsHouseRentInfoDTO>();
		
		for(TdsHouseRentInfo tdsHouseRentInfo:tdsHouseRentInfoList) {
			tdsHouseRentInfoDTOList.add(databaseModelToUiDto(tdsHouseRentInfo));
		}
		return tdsHouseRentInfoDTOList;
	}

	@Override
	public TdsHouseRentInfo uiDtoToDatabaseModel(TdsHouseRentInfoDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsHouseRentInfoDTO databaseModelToUiDto(TdsHouseRentInfo tdsHouseRentInfo) {
		
		TdsHouseRentInfoDTO tdsHouseRentInfoDTO = new TdsHouseRentInfoDTO();
		
		tdsHouseRentInfoDTO.setTdsHouseRentInfoId(tdsHouseRentInfo.getTdsHouseRentInfoId());
		tdsHouseRentInfoDTO.setAddressOfLandlord(tdsHouseRentInfo.getAddressOfLandlord());
		tdsHouseRentInfoDTO.setFromDate(tdsHouseRentInfo.getFromDate());
		tdsHouseRentInfoDTO.setLandlordName(tdsHouseRentInfo.getLandlordName());
		tdsHouseRentInfoDTO.setLandlordPan(tdsHouseRentInfo.getLandlordPan());
		tdsHouseRentInfoDTO.setToDate(tdsHouseRentInfo.getToDate());
		tdsHouseRentInfoDTO.setDateCreated(tdsHouseRentInfo.getDateCreated());
		tdsHouseRentInfoDTO.setDateUpdated(tdsHouseRentInfo.getDateUpdated());
		tdsHouseRentInfoDTO.setTotalRental(tdsHouseRentInfo.getTotalRental());
		tdsHouseRentInfoDTO.setAddressOfRentalProperty(tdsHouseRentInfo.getAddressOfRentalProperty());
		List <TdsHouseRentFileInfoDTO> tdsHouseRentFileDTOList  = new ArrayList<TdsHouseRentFileInfoDTO>();
		
		for(TdsHouseRentFileInfo tdsHouseRentFileInfo : tdsHouseRentInfo.getTdsHouseRentFileInfos()) {
			TdsHouseRentFileInfoDTO tdsHouseRentFileDTO  = new TdsHouseRentFileInfoDTO();
			
			if(tdsHouseRentFileInfo.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
				tdsHouseRentFileDTO.setFileName(tdsHouseRentFileInfo.getFileName());
				tdsHouseRentFileDTO.setFilePath(tdsHouseRentFileInfo.getFilePath());
				tdsHouseRentFileDTO.setTdsHouseRentFileInfoId(tdsHouseRentFileInfo.getTdsHouseRentFileInfoId());
				tdsHouseRentFileDTO.setUserId(tdsHouseRentFileInfo.getUserId());
				tdsHouseRentFileDTO.setUserIdUpdate(tdsHouseRentFileInfo.getUserIdUpdate());
				tdsHouseRentFileDTO.setActiveStatus(tdsHouseRentFileInfo.getActiveStatus());
				tdsHouseRentFileDTO.setDateCreated(tdsHouseRentFileInfo.getDateCreated());
				tdsHouseRentFileDTO.setOriginalFilename(tdsHouseRentFileInfo.getOriginalFilename());
				tdsHouseRentFileDTO.setTdsHouseRentInfoId(tdsHouseRentFileInfo.getTdsHouseRentInfo().getTdsHouseRentInfoId());
				tdsHouseRentFileDTOList.add(tdsHouseRentFileDTO);
			}
		}
		tdsHouseRentInfoDTO.setTdsHouseRentFileInfoDTO(tdsHouseRentFileDTOList); 
		return tdsHouseRentInfoDTO;
	}

	 
}
