package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.TdsSlabHd;

public interface TdsSlabRepository extends CrudRepository<TdsSlabHd, Long> {
 	 @Query(" from TdsSlabHd where companyId=?1  ORDER BY  tdsSLabHdId  DESC")
    public List<TdsSlabHd> findAllTdsSlabList( Long companyId ); 
 	 
 	@Query(" from TdsSlabHd where companyId=?1  AND activeStatus='AC' AND financialYear.financialYearId=?2 ORDER BY  tdsSLabHdId  DESC")
    public List<TdsSlabHd> findAllTdsSlabList( Long companyId, Long financialYearId ); 
 	 
 	 	 @Query(" from TdsSlabHd where companyId=?1 AND tdsCategory=?2 AND activeStatus='AC' AND financialYear.financialYearId=?3 ORDER BY  tdsSLabHdId  DESC")
     public TdsSlabHd finddsSlab(Long companyId,String tdsCategory,Long financialYearId); 
 	 
 
 	 	 
 	 	 @Query(" from TdsSlabHd where tdsSlabMasterId=?1 AND companyId =?2 AND activeStatus='AC'")
 	     public TdsSlabHd finddsSlabHd(Long tdsSlabMasterId,Long companyId); 
 	 	 
 	 	@Query(" from TdsSlabHd where tdsSlabMasterId=?2 AND companyId =?1 AND activeStatus='AC' AND financialYear.financialYearId=?3 ORDER BY  tdsSLabHdId  DESC")
	     public TdsSlabHd finddsSlab(Long companyId, Long tdsSlabMasterId, Long financialYearId); 

 		 @Query(" from TdsSlabHd where companyId =?1 AND activeStatus='AC'")
 	     public List<TdsSlabHd> getAllSlabHd(Long companyId); 
 }
