package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.PayStructure;
@Transactional
public interface PayStructureTransactionRepository extends CrudRepository<PayStructure, Long> {

	 
	@Query(value = " from PayStructure ps WHERE ps.payStructureHd.payStructureHdId=?1" ) 
	public List<PayStructure> findAllPayStructureById(Long payStructureHdId);
	
}
