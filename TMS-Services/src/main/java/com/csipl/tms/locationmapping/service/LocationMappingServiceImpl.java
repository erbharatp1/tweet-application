package com.csipl.tms.locationmapping.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;
import com.csipl.tms.locationmapping.repository.LocationMappingRepository;

@Service("locationMappingService")
@Transactional
public class LocationMappingServiceImpl implements LocationMappingService{

	@Autowired
	LocationMappingRepository locationMappingRepository;
	
	@Override
	public void deleteLocmappingById(List<AttendanceLocationMappingDTO> attendanceLocationMappingDTOList) {
		for(AttendanceLocationMappingDTO attendanceLocationMappingDTO: attendanceLocationMappingDTOList) {
		locationMappingRepository.deleteLocMapById(attendanceLocationMappingDTO.getAttendanceLocationId());
		}
	}

}
