package com.csipl.hrms.service.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.payroll.Gratuaty;

public interface GratuityRepository extends CrudRepository<Gratuaty, Long> {

	@Query(" from Gratuaty where companyId=?1 and activeStatus='"+StatusMessage.ACTIVE_CODE+"' ")
	public  Gratuaty  findAllGratuity(Long cmopanyId);
	
	@Query(" from Gratuaty gr where gr.company.companyId = ?1 and gr.activeStatus!='AC' ORDER BY gr.effectiveDate DESC")
	public  List<Gratuaty>  findAllGratuityByDescEffectiveDate(Long companyId);

}
