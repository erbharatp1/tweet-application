package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.LabourWelfareFundDTO;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.LabourWelfareFundInfo;

public interface LabourWelfareFundService {

	public List<LabourWelfareFund> getAllProfessionalTax(Long companyId);

	public LabourWelfareFund save(LabourWelfareFund professionalTax);
	
	 public LabourWelfareFund findLabourWelfareFundEmployee( Long stateId, Long companyId,String payrollMonth );
	 public LabourWelfareFund findLabourWelfareFund( Long labourWelfareFundHeadId ,Long companyId);

	
	public List<State> findAllState(Long companyId);

	public void updateByActiveStatus(LabourWelfareFundInfo professionalTaxInfo) ;

	public List<LabourWelfareFund> getAllLabourWelfareFund(Long companyId);

	public void updateApplicableStatus(String applicableStatus);

	public int getActiveCount(Long companyId);
	
	public List<LabourWelfareFundDTO>findAllLWFDescByDate(Long companyId);
	public List<LabourWelfareFundDTO>findAllLWFStateIdDescByDate(Long companyId , Long stateId);
	
	public LabourWelfareFund findAllLWFById(Long companyId , Long labourWelfareFundHeadId);

}
