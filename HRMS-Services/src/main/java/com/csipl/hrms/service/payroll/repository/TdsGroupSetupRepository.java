package com.csipl.hrms.service.payroll.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payroll.TdsGroupSetup;

public interface TdsGroupSetupRepository extends CrudRepository<TdsGroupSetup, Long> {
 public static final String FIND_GROUP_SETUP="from TdsGroupSetup where companyId=?1 AND tdsGroupMasterId=?2 AND activeStatus='AC'" ;
	  public static final String UPDATE_BY_ID="Update TdsGroupSetup d SET d.maxLimit=:maxLimit WHERE d.tdsGroupId=:tdsGroupId";
	  public static final String  FIND_BY_FINANICAL_YEAR="from TdsGroupSetup where companyId=?1 AND financialYearId =?2";
	 
	  public static final String FIND_BY_COMPANY_ID="from TdsGroupSetup where companyId=?1 and activeStatus='AC'";
	  
	  @Query(FIND_GROUP_SETUP)
     public TdsGroupSetup findGroupSetup(Long companyId,Long tdsGroupMasterId); 

	
	  
 	@Modifying
 	@Query(UPDATE_BY_ID)
 	public void updateById(@Param("tdsGroupId") Long tdsGroupId, @Param("maxLimit") BigDecimal maxLimit);


	/**
	 * findByFinanicalYear}
	 */
 	 @Query(FIND_BY_FINANICAL_YEAR)
	public List<TdsGroupSetup> findByFinanicalYearId(Long companyId, Long financialYearId);

 	 @Query(FIND_BY_COMPANY_ID)
 	public List<TdsGroupSetup> findByCompanyId(Long companyId);
 	 
 	
 }
