package com.csipl.hrms.service.payroll.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.payroll.Epf;

public interface EpfRepository extends CrudRepository<Epf, Long> {
	
	/*@Query(" from Epf ORDER BY  epfId  DESC ") 
 	public List<Epf> findAllEpfs();*/
	
	@Query(" from Epf epf where epf.company.companyId = ?1 and activeStatus='AC' ") 
 	public Epf getEPF( Long companyId );
	
	public static final String EPF_BY_PS_MONTH = "from Epf epf where (epf.effectiveDate<=?1 AND ?1<= epf.effectiveEndDate) OR (epf.effectiveDate <=?1 AND  epf.effectiveEndDate IS NULL ) AND epf.company.companyId=?2";
	@Query(EPF_BY_PS_MONTH) 
	public List<Epf> getEPFByPayrollPsMonth(Date time, Long companyId);
	
	@Query(" from Epf epf where epf.company.companyId = ?1 and epf.activeStatus!='AC' ORDER BY epf.effectiveDate DESC ") 
 	public List<Epf> findAllEpfsDescByDate(Long companyId);
	
}
