package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.payroll.TdsSlabHd;
@Repository
public interface TdsSlabHdRepository extends CrudRepository<TdsSlabHd, Long> {
 	 @Query(" from TdsSlabHd where companyId=?1 AND activeStatus='AC' ")
    public List<TdsSlabHd> findAllTdsSlabHdList( Long companyId ); 
 	 
 	 @Query(" from TdsSlabHd where companyId=?1 AND finencialYearId=?2")
     public List<TdsSlabHd>  finddsSlab(Long companyId,Long finencialYearId);

	


	
 }
