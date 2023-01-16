package com.csipl.tms.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.leave.LeaveSchemeMaster;




public interface LeaveSchemeRepository extends CrudRepository <LeaveSchemeMaster, Long> {
	@Query(" from LeaveSchemeMaster where leavePeriodId =?1 ORDER BY  leaveSchemeId  DESC ")
    public List<LeaveSchemeMaster> findAllLeaveScheme(Long leavePeriodId);
	
	@Query(" from LeaveSchemeMaster where status ='AC' ORDER BY  leaveSchemeId  DESC ")
    public List<LeaveSchemeMaster> findLeaveScheme();
}
