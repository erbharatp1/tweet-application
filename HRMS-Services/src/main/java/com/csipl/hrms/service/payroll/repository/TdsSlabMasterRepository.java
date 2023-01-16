package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.payroll.TdsSlabMaster;

public interface TdsSlabMasterRepository extends CrudRepository<TdsSlabMaster, Long> {
 	 
	  @Query(" from TdsSlabMaster where companyId=?1 ")
	  public List<TdsSlabMaster> findsSlabMaster(Long companyId);
	 


	
 }
