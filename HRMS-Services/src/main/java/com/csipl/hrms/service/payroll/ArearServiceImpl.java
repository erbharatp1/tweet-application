package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.ArearCalculation;
import com.csipl.hrms.model.payroll.ArearMaster;
import com.csipl.hrms.service.payroll.repository.ArearMasterRepository;
import com.csipl.hrms.service.payroll.repository.ArearRepository;

@Service("arearService")
public class ArearServiceImpl implements ArearService {

	@Autowired
	ArearMasterRepository arearMasterRepository;

	@Autowired
	private ArearRepository arearRepository;

	@Override
	public ArearCalculation save(ArearCalculation arear) {

		return arearRepository.save(arear);
	}

	@Override
	public List<Object[]> findAllArear(Long companyId) {
		return arearRepository.findAllArear(companyId);
	}

	@Override
	public ArearMaster save(ArearMaster arearMaster) {
		return arearMasterRepository.save(arearMaster);
	}

	@Override
	public List<Object[]> findArearCalculation(Long arearId) {
		return arearRepository.findArearCalculation(arearId);
	}

	@Override
	public ArearMaster findArearCalculationByemployeeIdAndProcessMonth(Long employeeId, String processMonth) {
		// TODO Auto-generated method stub
		return arearMasterRepository.findArearCalculationByemployeeIdAndProcessMonth( employeeId, processMonth);
	}
    @Transactional
	@Override
	public Boolean delete(Long arearId) {
		ArearMaster arearMaster=	arearMasterRepository.findOne(arearId);
		/*StringBuilder sb = new StringBuilder();
		for(ArearCalculation aCalculation :arearMaster.getArearCalculations() ) {
			sb.append(aCalculation.getPayrollMonth());
			sb.append(',');
		}*/
		
		System.out.println(arearMasterRepository.deleteArearReportPayout(arearId,arearMaster.getEmployee().getEmployeeId()));
		System.out.println(arearMasterRepository.deleteArearPayout(arearId,arearMaster.getEmployee().getEmployeeId()));
		arearMasterRepository.delete(arearId);
		return true;
	}

	// @Override
	// public List<ArearCalculation> findArearCalculation(Long arearId) {
	// // TODO Auto-generated method stub
	// return arearRepository.findArearCalculation(arearId);
	// }
	

	 @Override
	public int getArear(Long employeeId) {
	int count = arearMasterRepository.getArear(employeeId);
	if(count > 0) {
	return 1;
	}else
	{
	return 0;
	}

	}

}
