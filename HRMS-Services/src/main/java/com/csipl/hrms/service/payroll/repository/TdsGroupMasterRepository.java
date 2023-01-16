package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;

public interface TdsGroupMasterRepository extends CrudRepository<TdsGroupSetup, Long> {
	  public static final String FIND_GROUP_MASTER="from TdsGroupMaster where companyId=?1";
	public static final String UPDATE_BY_ID="Update TdsGroupSetup d SET d.maxLimit=:maxLimit WHERE d.tdsGroupId=:tdsGroupId";
	
 	 @Query(FIND_GROUP_MASTER)
     public List<TdsGroupMaster> findTdsGroupMaster(Long companyId); 
	/*
	 * @Modifying
	 * 
	 * @Query(UPDATE_BY_ID) public void updateById(@Param("tdsGroupId") Long
	 * tdsGroupId, @Param("maxLimit") BigDecimal maxLimit);
	 */


	
 }
