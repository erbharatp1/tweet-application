package com.csipl.tms.shift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.shift.Shift;

public interface ShiftRepository extends CrudRepository<Shift, Long> {
	@Query(" from Shift where companyId =?1 AND activeStatus='AC' ORDER BY  shiftId  DESC ")
	public List<Shift> findAllShift(Long companyId);

	@Query(" from Shift where shiftId=?1 ORDER BY  shiftId  DESC ")
	public Shift findShift(Long shiftId);

	@Query(" SELECT count(*) from Shift  where companyId=?1 ")
	public int getShiftCount(Long longCompanyId);

	String SHIFTDURATION = " SELECT e.employeeId,e.employeeCode,concat(e.firstName,' ', e.lastName) ,tm.shiftId,tm.shiftFName,TIME_FORMAT(tm.startTime, '%h:%i:%s %p'),TIME_FORMAT(tm.endTime, '%h:%i:%s %p'),tm.shiftDuration FROM Employee e LEFT JOIN TMSShift tm ON tm.shiftId=e.shiftId where e.employeeId=?1 ";

	@Query(value = SHIFTDURATION, nativeQuery = true)
	public List<Object[]> findShiftDuration(Long employeeId);

	@Query(" from Shift where companyId =?1 ORDER BY  shiftId  DESC ")
	public List<Shift> fetchAllShift(Long companyId);
}