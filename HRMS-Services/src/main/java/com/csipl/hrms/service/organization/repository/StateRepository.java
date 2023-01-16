package com.csipl.hrms.service.organization.repository;
  import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.common.Country;
import com.csipl.hrms.model.common.State;
  public interface StateRepository extends CrudRepository<State, Long> {
	  public static final String FIND_BY_ID= "SELECT * FROM State s where s.stateId NOT in (select stateId from ProfessionalTax pt where pt.companyId=?1)";
		
	  public static final String FIND_STATE_BY_LABOUR= "SELECT * FROM State s where s.stateId NOT in (select stateId from LabourWelfareFund pt where pt.companyId=?1)";
		
	  @Query("from State ")
    public List<State> findAllState();
	
	
	@Query(nativeQuery = true,value = FIND_BY_ID)
    public List<State> findStateById(Long companyId);
	
	@Query(nativeQuery = true,value = FIND_STATE_BY_LABOUR)
    public List<State> findStateByIdLabour(Long companyId);
}