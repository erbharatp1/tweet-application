package com.csipl.tms.attendancetype.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.attendancetype.AttendanceType;


@Repository
public interface AttendanceTypeRepository extends CrudRepository<AttendanceType, Long> {

	@Query("FROM AttendanceType")
	List<AttendanceType> getAllAttendanceType();
}
