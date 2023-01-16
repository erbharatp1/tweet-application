package com.csipl.tms.locationmapping.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.latlonglocation.AttendanceLocationMapping;

@Repository
public interface LocationMappingRepository extends CrudRepository<AttendanceLocationMapping, Long>{
	
	
	public static final String DELETE_LOC_MAP_BY_ID = "Delete From AttendanceLocationMapping Where attendanceLocationId=?1";
	@Modifying
	@Query(value = DELETE_LOC_MAP_BY_ID, nativeQuery = true)
	public void deleteLocMapById(Long attendanceLocationId);

}
