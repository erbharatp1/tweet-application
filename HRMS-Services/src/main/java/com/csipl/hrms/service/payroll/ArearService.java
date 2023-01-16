package com.csipl.hrms.service.payroll;
import java.util.List;
import com.csipl.hrms.model.payroll.ArearCalculation;
import com.csipl.hrms.model.payroll.ArearMaster;

public interface ArearService {
	public ArearCalculation save(ArearCalculation arear);
	public List<Object[]> findAllArear(Long companyId);
	public List<Object[]> findArearCalculation(Long arearId);
	public ArearMaster save(ArearMaster arearMaster);
	public ArearMaster findArearCalculationByemployeeIdAndProcessMonth(Long employeeId ,String processMonth);
	public Boolean delete(Long arearId);
	public int getArear(Long employeeId);
}
