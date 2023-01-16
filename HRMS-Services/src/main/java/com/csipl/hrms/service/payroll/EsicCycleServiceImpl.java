package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.EsiCycle;
import com.csipl.hrms.service.payroll.repository.EsiCycleRepository;

 
@Service("esicCycleService")
public class EsicCycleServiceImpl implements EsicCycleService {
	
	@Autowired
	EsiCycleRepository  esiCycleRepository;

	@Override
	public List<EsiCycle> getAllEsicCycle() {
  		return esiCycleRepository.getAllEsicCycle();
	}

	@Override
	public List<EsiCycle> getEsicCycle(Long esicId) {
		// TODO Auto-generated method stub
		return esiCycleRepository.getEsicCycle(esicId);
	}

 
	 
	
}
