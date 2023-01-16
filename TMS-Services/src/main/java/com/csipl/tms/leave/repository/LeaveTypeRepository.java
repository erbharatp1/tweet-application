package com.csipl.tms.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.csipl.tms.model.leave.TMSLeaveType;

public interface LeaveTypeRepository extends CrudRepository<TMSLeaveType, Long>{
	@Query(" from TMSLeaveType where companyId =?1 ")
    public List<TMSLeaveType> findAllLeaveType(Long companyId);
	@Query(" from TMSLeaveType where leaveTypeId =?1 ") 
 	public TMSLeaveType getLeaveTypeById(Long leaveTypeId);
	
	@Query(" from TMSLeaveType where leavePeriodId =?1 and leaveSchemeId = ?2")
    public List<TMSLeaveType> findAllPeriod(Long leavePeriodId,Long leaveSchemeId);
	
//	@Query("from TMSLeaveType lt WHERE lt.leavePeriodId= (SELECT DISTINCT(ol.leavePeriodId) FROM EmployeeOpeningLeaveMaster ol WHERE ol.activeStatus = 'AC') AND lt.activeStatus ='AC' ")
	String PREVIOUS_OPEN_LEAVE="SELECT  a.leavePeriodId,a.leaveTypeMasterId,a.leaveName,a.totalleave,IF((a.balanceleave-a.optype) >a.carryForwardLimit,a.carryForwardLimit,(a.balanceleave-a.optype))as openleave,(a.balanceleave-a.optype) as balanceleave,a.carryForwardLimit,a.employeeId,a.companyId,(a.consumed + a.optype) as consumed ,a.encashLimit,IF((a.balanceleave-a.optype) >a.encashLimit,a.encashLimit,(a.balanceleave-a.optype))as encashLeave,a.indexDays,a.isLeaveInProbation,a.nature,\r\n" + 
			"						a.notice,a.weekOffAsPLCount,a.leavePeriodName,a.dateOfJoining,a.leaveProbationCalculatedOn ,a.probationDays,a.startDate,a.endDate,a.leaveMode, a.yearlyLimit  \r\n" + 
			"									from ((SELECT  \r\n" + 
			"									  tmsLeaveType.leavePeriodId,tmsLeaveType.leaveTypeMasterId,ltm.leaveName ,  \r\n" + 
			"								 (\r\n" + 
			"						   CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( (SELECT  SUM(days) FROM TMSCompensantoryOff  ec LEFT JOIN TMSLeavePeriod lp ON lp.leavePeriodId = ec.leavePeriodId WHERE lp.leavePeriodId =?1 AND ec.employeeId =?1 AND ec.STATUS = 'APR'  ) + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?1 AND STATUS  = 'CF' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0), 0 )  \r\n" + 
			"						ELSE( tmsLeaveType.yearlyLimit + IFNULL( ( SELECT noOfOpening FROM  EmployeeOpeningLeaveMaster WHERE   employeeId =?1 AND STATUS  = 'CF' \r\n" + 
			"						AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0)  \r\n" + 
			"						  ) \r\n" + 
			"						  END \r\n" + 
			"						) AS totalleave,SUM(IFNULL(tmsLeaveEntries.days,0) ) AS consumed, (IF((IFNULL(tmsLeaveType.yearlyLimit,0) )-SUM(IFNULL(tmsLeaveEntries.days,0))>tmsLeaveType.carryForwardLimit,tmsLeaveType.carryForwardLimit, ((IFNULL( (tmsLeaveType.yearlyLimit +IFNULL(ol.noOfOpening, 0)),0) )-SUM(IFNULL(tmsLeaveEntries.days,0)) )))as openleave,  \r\n" + 
			"									( ( CASE WHEN( tmsLeaveType.leaveTypeMasterId = 7) THEN IFNULL( ( SELECT   SUM(days) FROM TMSCompensantoryOff WHERE   employeeId =?1 AND STATUS = 'APR' ),0) \r\n" + 
			"						 ELSE(  tmsLeaveType.yearlyLimit + IFNULL(( SELECT   noOfOpening FROM EmployeeOpeningLeaveMaster WHERE employeeId =?1 AND  STATUS = 'CF'\r\n" + 
			"						 AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId), 0) \r\n" + 
			"						    ) \r\n" + 
			"						  END\r\n" + 
			"						)- SUM( \r\n" + 
			"						  IFNULL(tmsLeaveEntries.days, 0)  \r\n" + 
			"						) \r\n" + 
			"						)AS balanceleave,  \r\n" + 
			"									tmsLeaveType.carryForwardLimit,employee.employeeId ,employee.companyId ,SUM(IFNULL(tmsLeaveEntries.days,0))  ,IFNULL(( SELECT  noOfOpening FROM    EmployeeOpeningLeaveMaster WHERE  employeeId =?1 AND STATUS  = 'OP' AND leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND leavePeriodId = lp.leavePeriodId ),\r\n" + 
			"						 0) as optype,tmsLeaveType.encashLimit,tmsLeaveType.indexDays,tmsLeaveType.isLeaveInProbation,tmsLeaveType.nature,  \r\n" + 
			"						tmsLeaveType.notice,tmsLeaveType.weekOffAsPLCount,lp.leavePeriodName,employee.dateOfJoining, tmsLeaveType.leaveProbationCalculatedOn,employee.probationDays,lp.startDate,lp.endDate, tmsLeaveType.leaveMode,tmsLeaveType.yearlyLimit \r\n" + 
			"									FROM TMSLeavePeriod lp \r\n" + 
			"									JOIN Employee employee ON employee.employeeId =?1  \r\n" + 
			"									LEFT JOIN LeaveSchemeMaster lsm ON lsm.leaveSchemeId = employee.leaveSchemeId \r\n" + 
			"									 JOIN TMSLeaveType tmsLeaveType ON lp.leavePeriodId = tmsLeaveType.leavePeriodId AND tmsLeaveType.leaveSchemeId =  employee.leaveSchemeId AND tmsLeaveType.activeStatus = 'AC' \r\n" + 
			"									LEFT JOIN TMSLeaveTypeMaster ltm ON ltm.leaveId = tmsLeaveType.leaveTypeMasterId  \r\n" + 
			"									LEFT JOIN TMSLeaveEntries tmsLeaveEntries ON tmsLeaveEntries.leaveTypeId = tmsLeaveType.leaveTypeId AND tmsLeaveEntries.employeeId =?1 AND ( tmsLeaveEntries.status = 'APR' ) \r\n" + 
			"									 LEFT JOIN TMSLeaveCarryForward lc ON lc.leavePeriodId = tmsLeaveType.leavePeriodId AND lc.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND lc.employeeId = tmsLeaveEntries.employeeId \r\n" + 
			"									LEFT JOIN EmployeeOpeningLeaveMaster ol ON ol.leavePeriodId = lp.leavePeriodId AND ol.leaveTypeMasterId = tmsLeaveType.leaveTypeMasterId AND ol.employeeId =?1  \r\n" + 
			"									WHERE lp.leavePeriodId=?3 AND ltm.companyId =?2 \r\n" + 
			"									GROUP BY tmsLeaveType.leaveTypeId) as a )";
	@Query(value = PREVIOUS_OPEN_LEAVE, nativeQuery = true)
	public List<Object[]> findAllpreviousOpeningLeaveType(Long employeeId,Long companyId,String periodId);
	
}
