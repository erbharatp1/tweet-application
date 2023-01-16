package com.csipl.tms.leave.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.leave.TMSLeaveRules;

public interface TMSLeaveRulesRepository extends CrudRepository<TMSLeaveRules, Long>{
	  
	
	@Query(" from TMSLeaveRules where leaveRulehdId =?1 ")
    public List<TMSLeaveRules> findAllLeaveRule(Long LeaveRulehdId);
	@Query(nativeQuery = true,value = "SELECT mas.leaveName, ty.leaveTypeId FROM TMSLeaveType ty JOIN TMSLeaveTypeMaster mas on ty.leaveTypeMasterId= mas.leaveId WHERE ty.leaveTypeId=?1")
	public List<Object[]> findLeaveName(Long leaveTypeId);
}
