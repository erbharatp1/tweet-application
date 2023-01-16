package com.csipl.tms.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.tms.model.leave.TMSLeavePeriod;

public interface LeavePeriodRepository extends CrudRepository<TMSLeavePeriod, Long>{
	@Query(" from TMSLeavePeriod where companyId =?1 AND activeStatus!='"+StatusMessage.ACTIVE_CODE+"' ")
    public List<TMSLeavePeriod> findAllLeavePeriod(Long companyId);
	
	@Query(" from TMSLeavePeriod where companyId =?1 AND activeStatus='AC'")
	  public List<TMSLeavePeriod> findleavePeriodStatus(Long companyId);
	
	@Query(" from TMSLeavePeriod where companyId =?1 AND activeStatus='AC'")
	  public TMSLeavePeriod leavePeriod(Long companyId);
	
	@Query(" from TMSLeavePeriod where companyId =?1  ")
	  public List<TMSLeavePeriod> findLeavePeriod(Long companyId);

//	@Query(" from TMSLeavePeriod where companyId =?1 ")
//	public TMSLeavePeriod findLeavePeriodId(Long companyId);

	@Query(" from TMSLeavePeriod where companyId =?1 AND activeStatus='DE' ")
	public List<TMSLeavePeriod> findEncashedLeavePeriod(Long companyId);

	@Query(" from TMSLeavePeriod where companyId =?1 AND leavePeriodId =?2 AND activeStatus='DE'")
	public TMSLeavePeriod getEncashedLeavePeriodId(Long companyId, Long leavePeriodId);
	
	//@Query(" FROM TMSLeavePeriod WHERE activeStatus = 'DE' AND dateUpdate =(select MAX(dateUpdate) FROM TMSLeavePeriod )")
	String QUERY_LASTUPDATEDPERIOD = "select lp.leavePeriodId FROM TMSLeavePeriod lp WHERE lp.activeStatus = 'DE' AND lp.dateUpdate =(select MAX(dateUpdate) FROM TMSLeavePeriod )";
	@Query(value = QUERY_LASTUPDATEDPERIOD, nativeQuery = true)
	public Object[] findLastupdatedPeriod();
	
	@Query(value ="select * from TMSLeavePeriod tlp where tlp.companyId =?1 AND ?2 BETWEEN CAST(tlp.startDate AS DATE) AND CAST(tlp.endDate AS DATE)" ,nativeQuery = true)
	public TMSLeavePeriod findLeavePeriodByProcessMonth(Long companyId ,String  processMonth);
	
	@Query(value ="select * from TMSLeavePeriod tlp where tlp.companyId =?1  AND tlp.activeStatus='AC' AND ?2 BETWEEN CAST(tlp.startDate AS DATE) AND CAST(tlp.endDate AS DATE)" ,nativeQuery = true)
	public List<TMSLeavePeriod> findLeavePeriodByProcessMonthWithACflag(Long companyId ,String  processMonth);
	
}