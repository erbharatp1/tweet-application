package com.csipl.hrms.service.payroll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.csipl.hrms.model.payroll.TdsSectionSetup;

public interface TdsSectionSetupRepository extends CrudRepository<TdsSectionSetup, Long> {
	public static final String FIND_ALL_TDS_SECTIONS="from TdsSectionSetup  where tdsGroupId=?1 AND activeStatus= 'AC' ";
	public static final String UPDATE_BY_ID="Update TdsSectionSetup ts SET ts.activeStatus=:activeStatus WHERE ts.tdsSectionId=:tdsSectionId";
	 	
	
	@Query(FIND_ALL_TDS_SECTIONS)
	public List<TdsSectionSetup> findAllTdsSections(Long tdsGroupId);
	
	@Modifying
 	@Query(UPDATE_BY_ID)
 	public void updateByStatus(@Param("tdsSectionId") Long tdsSectionId, @Param("activeStatus") String activeStatus);

	
}
