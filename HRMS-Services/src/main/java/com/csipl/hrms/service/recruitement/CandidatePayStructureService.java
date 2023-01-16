package com.csipl.hrms.service.recruitement;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.employee.PayStructureHdDTO;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;

public interface CandidatePayStructureService {
  	public PayStructureHdDTO calculateEarningDeduction(Long companyId, Long employeeId, PayStructureHd payStructureHd,Boolean flag,Boolean exitsPaystructurePayheadsFlag) throws PayRollProcessException;
  	public void saveCandidatePayStructure(CandidatePayStructureHd candidatePaystructurehd);
	public CandidatePayStructureHd getCandidatePaystructure(Long intrviewScheduleId);
}
