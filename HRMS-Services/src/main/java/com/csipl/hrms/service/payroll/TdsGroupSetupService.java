package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.payroll.TdsGroupSetup;

public interface TdsGroupSetupService {

	

//	public TdsGroupSetup getByGroupId(Long companyId, String tdsGroupName);
//
	public List<TdsGroupSetup> saveTdsGroup(List<TdsGroupSetup> tdsGroupSetupList);
	
	public List<TdsGroupSetup> findByCompanyId(Long companyId);

   
	
}
