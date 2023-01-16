package com.csipl.hrms.service.common.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.common.TokenMaster;

@Transactional
public interface TokenMasterRepository extends CrudRepository<TokenMaster,Long>{

	@Query("SELECT count(*) from TokenMaster  where userId=?1 and tokenTypeId=?2")
	public int getUserCount(Long userId,Long tokenTypeId);
	//@Query(value=reportingTo,nativeQuery = true)
	
	
//	

	@Modifying
	@Query( "UPDATE TokenMaster SET tokenValue =?2  where userId =?1 and tokentypeId=?3")
	public int updateTokenMaster(Long userId, String token,Long tokentypeId);
}
