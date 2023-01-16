package com.csipl.tms.locationmapping.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;
import com.csipl.tms.locationmapping.service.LocationMappingService;
import com.csipl.tms.locationmaster.repository.LocationMasterRepository;



@RequestMapping("/locationmapping")
@RestController
public class LocationMappingController {
	private static final Logger logger = LoggerFactory.getLogger(LocationMasterRepository.class);
	
	@Autowired
	LocationMappingService locationMappingService;
	
	@PostMapping(value = "/deleteLocationMapiing")
	public void deleteLocationMapiing(@RequestBody  List<AttendanceLocationMappingDTO> attendanceLocationMappingDTOList)  {
		  logger.info("deleteLocationMapiing");
		  locationMappingService.deleteLocmappingById(attendanceLocationMappingDTOList);
	}
	
}
