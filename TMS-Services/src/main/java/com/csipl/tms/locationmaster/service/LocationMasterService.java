package com.csipl.tms.locationmaster.service;

import java.util.List;

import com.csipl.tms.model.latlonglocation.LocationMaster;

public interface LocationMasterService {

	public List<LocationMaster> save(List<LocationMaster> locationMaster);
}
