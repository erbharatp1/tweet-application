package com.csipl.hrms.service.payroll;


import java.util.List;

import com.csipl.hrms.dto.payroll.EsiDTO;
import com.csipl.hrms.model.payroll.Esi;

public interface EsicService {
  	 public Esi save(Esi esic);
 	 public Esi getESI( Long companyId );

 	 public Esi getAllESI( Long companyId );

 	 public Esi getActiveESI( Long companyId );

 	 public Esi getESIByPayrollPsMonth(String payPsMonth, Long companyId ) ;
 			 //throws PayRollProcessException;
 	public List<EsiDTO> findAllEsiDescByDate(Long companyId);
 	
 	public Esi saveEsics(Esi existingEsic , Esi newEsic);
}
