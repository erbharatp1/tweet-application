package com.csipl.tms.locationmapping.service;

import java.util.List;

import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;

public interface LocationMappingService {

	public void deleteLocmappingById(List<AttendanceLocationMappingDTO> attendanceLocationMappingDTOList);
}
