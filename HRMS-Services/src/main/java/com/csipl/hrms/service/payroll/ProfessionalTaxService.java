package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.ProfessionalTaxDTO;
import com.csipl.hrms.dto.payroll.ProfessionalTaxInfoDTO;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;

public interface ProfessionalTaxService {

	public List<ProfessionalTax> getAllProfessionalTax(Long companyId);

	public ProfessionalTax save(ProfessionalTax professionalTax);
	public ProfessionalTax saveProfessionalTaxs(ProfessionalTax existingProfessionalTax , ProfessionalTax professionalTax);
	 public ProfessionalTax findProfessionalTaxOfEmployee( long stateId, Long companyId );
	 public ProfessionalTax findProfessionalTaxOfEmployeeByProcessMonth( long stateId, Long companyId ,String payPsMonth);
	 public ProfessionalTax findProfessionalTax( long professionalHeadId ,Long companyId);

	/**
	 * findAllProfessionalTax}
	 */
	public List<State> findAllState(Long companyId);
	
	

	public void updateByActiveStatus(ProfessionalTaxInfo professionalTaxInfo) ;
	//this will check payroll has been created or not
	public boolean payrollCheck(Date date ,Long finencialYearId ,Long comapnyId);
	public int monthToValue(String months); 
	
	public List<ProfessionalTaxDTO>findAllPTDescByDate(Long companyId);
	public List<ProfessionalTaxDTO>findAllPTStateIdDescByDate(Long companyId , Long professionalHeadId);
	public ProfessionalTax findAllPTProfessionalTaxIdDescByDate(Long professionalTaxId,Long comapnyId);
	public boolean payrollCheckObj(String psMonth, Long finencialYearId, Long comapnyId, Long employeeId, BigDecimal grossPay);
}
