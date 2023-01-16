package com.csipl.hrms.service.payroll.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

 import com.csipl.hrms.model.payroll.EsiCycle;

public interface EsiCycleRepository extends CrudRepository<EsiCycle, Long> {
	
	@Query(" from EsiCycle ") 
    public List<EsiCycle> getAllEsicCycle();
	
	@Query(" from EsiCycle where esi.esiId =?1 ") 
    public List<EsiCycle> getEsicCycle(Long esiId);

 }
