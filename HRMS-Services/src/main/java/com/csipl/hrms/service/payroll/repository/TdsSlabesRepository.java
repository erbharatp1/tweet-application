package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.payroll.TdsSlab;
@Transactional
@Repository
public interface TdsSlabesRepository extends CrudRepository<TdsSlab, Long> {

	public static final String UPDATE_BY_ID_AND_STATUS = "Update TdsSlab ts SET ts.activeStatus=:activeStatus WHERE ts.tdsSLabId=:tdsSLabId";
//	public static final String FIND_ALL_TDS_SLAB_BY_CATEGORY = "FROM TdsSlab where tdsSLabHdId=?1  AND activeStatus= 'AC' ";
	public static final String FIND_ALL_TDS_SLAB_BY_CATEGORY = "FROM TdsSlab where tdsSLabHdId=?1  AND activeStatus= 'AC' AND tdsSlabPlanType=?2 ";
	@Query(FIND_ALL_TDS_SLAB_BY_CATEGORY)
	public List<TdsSlab> findAllTdsSlabByCategory(Long tdsSLabHdId);

	@Modifying
	@Query(UPDATE_BY_ID_AND_STATUS)
	public void updateByStatus(@Param("tdsSLabId") Long tdsSLabId, @Param("activeStatus") String activeStatus);

	@Query(FIND_ALL_TDS_SLAB_BY_CATEGORY)
	public List<TdsSlab> findAllTdsSlabById(Long tdsSLabHdId, String planType);
}

