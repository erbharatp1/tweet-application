package com.csipl.tms.locationmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.locationmaster.repository.LocationMasterRepository;
import com.csipl.tms.model.latlonglocation.LocationMaster;

@Service("locationMasterService")
public class LocationMasterServiceImpl implements LocationMasterService{

	@Autowired
	LocationMasterRepository locationMasterRepository;
	
	@Override
	public List<LocationMaster> save(List<LocationMaster> locationMaster) {
		return (List<LocationMaster>) locationMasterRepository.save(locationMaster);
	}

}
