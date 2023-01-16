package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.Esi;
@Transactional
public interface EsicRepository extends CrudRepository<Esi, Long> {
	
	/*@Query("from Esi ORDER BY esiId  DESC")
    public List<Esi> findAllEsic();*/
	
	//@Query(" from Esi esi where esi.effectiveDate <= ?2 and esi.company.companyId = ?1 and activeStatus='AC' ") 
  //  public Esi getESI( long companyId, Date today );
  
    public Esi findByEffectiveDateLessThanEqualAndActiveStatusAndCompany(Date today,String status,Company company);
	

	@Query(" from Esi esi where esi.company.companyId = ?1 and activeStatus='AC' ") 
    public Esi getAllESI(long companyId);

	
	@Query(" from Esi where  companyId =?1 and activeStatus=?2 ") 
    public Esi getActiveESI( long companyId,String activeStatus );
	
	public static final String DELETE_ESI_CYCLE = "DELETE FROM EsiCycle WHERE esiId = ?";
	@Modifying
	@Query(value = DELETE_ESI_CYCLE, nativeQuery = true)
	public void deleteEsiCycleById(Long esiId);
    
	public static final String ESI_BY_PS_MONTH = "from Esi esi where (esi.effectiveDate<=?1 AND ?1<= esi.effectiveEndDate) OR (esi.effectiveDate <=?1 AND  esi.effectiveEndDate IS NULL ) AND esi.company.companyId=?2";
	@Query(ESI_BY_PS_MONTH) 
    public List<Esi> getESIByPayrollPsMonth( Date payPsMonth, Long companyId );
	
	@Query(" from Esi esi where esi.company.companyId =?1 and esi.activeStatus!='AC' ORDER BY esi.effectiveDate DESC ") 
 	public List<Esi> findAllEsiDescByDate(Long companyId);
}
