package com.csipl.tms.locationmaster.adaptor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;
import com.csipl.tms.dto.latlonglocation.LocationMasterDTO;
import com.csipl.tms.model.latlonglocation.LocationMaster;

@Component
public class LocationMasterAdaptor {

	
	public List<LocationMaster> uiDtoToModelList(List<LocationMasterDTO> locationMasterDTOList) {
		return locationMasterDTOList.stream().map(item -> uiDtotoModel(item)).collect(Collectors.toList());
	}
	
	public LocationMaster uiDtotoModel(LocationMasterDTO locationMasterDTO) {
		LocationMaster locationMaster = new LocationMaster();
		if(locationMasterDTO.getLocationId() != null) {
		locationMaster.setLocationId(locationMasterDTO.getLocationId());
		}
		locationMaster.setLatitude(locationMasterDTO.getLatitude());
		locationMaster.setLongitude(locationMasterDTO.getLongitude());
		locationMaster.setRadius(locationMasterDTO.getRadius());
		locationMaster.setCreatedBy(locationMasterDTO.getCreatedBy());
		locationMaster.setCreatedDate(new Date());
		locationMaster.setLocationAddress(locationMasterDTO.getLocationAddress());
		return locationMaster;
	}
	
	public List<LocationMaster> uiMappingDtoToModelList(List<AttendanceLocationMappingDTO> attendanceLocationMappingDTO) {
		return attendanceLocationMappingDTO.stream().map(item -> uiMappingDtotoModel(item)).collect(Collectors.toList());
	}
	
	public LocationMaster uiMappingDtotoModel(AttendanceLocationMappingDTO locationMasterDTO) {
		LocationMaster locationMaster = new LocationMaster();
		if(locationMasterDTO.getLocationId() != null) {
		locationMaster.setLocationId(locationMasterDTO.getLocationId());
		}
		locationMaster.setLatitude(locationMasterDTO.getLatitude());
		locationMaster.setLongitude(locationMasterDTO.getLongitude());
		locationMaster.setRadius(locationMasterDTO.getRadius());
		locationMaster.setCreatedBy(locationMasterDTO.getCreatedBy());
		locationMaster.setCreatedDate(new Date());
		locationMaster.setLocationAddress(locationMasterDTO.getLocationAddress());
		return locationMaster;
	}
}
