package com.csipl.hrms.service.payroll;

import java.util.List;
import java.util.Map;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.payroll.PayHead;

public interface PayHeadService {

	public List<PayHead> getAllPayHeads(String opt,Long companyId);
	
	public List<PayHead> getAllOneTimePayHeads(String opt,Long companyId);
	
	public List<PayHead> findAllEarnigPaystructurePayHeads(Long  companyId);

	public PayHead save(PayHead payhead) throws ErrorHandling;

 	public PayHead findPayHeadById( long payHeadId );
	
	public List<PayHead> findActivePayHeadOfCompany( long  companyId ); 
	
	public List<PayHead> findAllPayHeadOfCompany( long  companyId ); 
	//public String getPriority(Long priority);

	public Map<String, Long> getSquenceId(Long companyId);
	
}
