package com.csipl.tms.locationmaster.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.latlonglocation.LocationMaster;


@Repository
public interface LocationMasterRepository extends CrudRepository <LocationMaster, Long>{ 

}
