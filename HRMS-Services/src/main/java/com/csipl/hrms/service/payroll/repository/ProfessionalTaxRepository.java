package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;

 
public interface ProfessionalTaxRepository extends CrudRepository<ProfessionalTax, Long> {

	  public static final String UPDATE_BY_ID="Update ProfessionalTaxInfo pt SET pt.activeStatus=:activeStatus WHERE pt.professionalTaxInfoId=:professionalTaxInfoId";
	
	
	//@Query(" from ProfessionalTax  where  (effectiveEndDate is null or effectiveEndDate>?1) and (effectiveStartDate is NOT null and effectiveStartDate<=?1) and companyId=?2 AND activeStatus= 'AC'")
	  @Query(" from ProfessionalTax  where   companyId=?1 AND activeStatus= 'AC'")
	  public List<ProfessionalTax> findAllProfessionalTax(Long companyId);
 	
	  
	  @Query(" from ProfessionalTax profTax where profTax.state.stateId=?1 and companyId=?2 AND activeStatus= 'AC' ")
	    public List<ProfessionalTax> findProfessionalTaxOfEmployees( long stateId, long companyId );
	  
	  
 	@Query("from ProfessionalTax profTax where ((profTax.effectiveStartDate<=?1 AND ?1<= profTax.effectiveEndDate) OR (profTax.effectiveStartDate <=?1 AND  profTax.effectiveEndDate IS NULL )) AND profTax.state.stateId=?2 AND profTax.company.companyId=?3")
    public List<ProfessionalTax> findProfessionalTaxOfEmployee(  Date date ,Long stateId,Long companyId);
 //	from Esi esi where (esi.effectiveDate<=?1 AND ?1<= esi.effectiveEndDate) OR (esi.effectiveDate <=?1 AND  esi.effectiveEndDate IS NULL ) AND esi.company.companyId=?2
	
 	@Query(" from ProfessionalTax pt where professionalHeadId=?1 and companyId=?2 ")
    public ProfessionalTax findProfessionalTax( Long professionalHeadId ,Long companyId);
	
	

	@Modifying
 	@Query(UPDATE_BY_ID)
 	public void updateByStatus(@Param("professionalTaxInfoId") Long professionalTaxInfoId, @Param("activeStatus") String activeStatus);

	@Query(" from ProfessionalTax pt where pt.company.companyId =?1  AND pt.activeStatus!= 'AC' ORDER BY pt.effectiveStartDate DESC ") 
	 public List<ProfessionalTax> findAllPTDescByDate(Long companyId);
	
	@Query(" from ProfessionalTax pt where pt.company.companyId =?1 and pt.professionalHeadId =?2 ORDER BY pt.effectiveStartDate DESC ") 
	 public List<ProfessionalTax> findAllPTStateIdDescByDate(Long companyId ,Long professionalHeadId);
	
	//@Query(value="select * from ProfessionalTaxInfo pti where pti.ProfessionalTaxId =?1  ORDER BY pti.ProfessionalTaxId DESC ",nativeQuery = true) 
	// public List<ProfessionalTaxInfo> findAllPTProfessionalTaxIdDescByDate(Long professionalTaxId);
	
	@Query("from ProfessionalTax pti where pti.professionalHeadId =?1 and pti.company.companyId =?2 ORDER BY pti.professionalHeadId DESC ") 
	 public ProfessionalTax findPTProfessionalTaxByHeadId(Long professionalTaxId,Long companyId);
	
 }



 
