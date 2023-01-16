package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.payroll.EsiCycle;

public interface EsicCycleService {
 		List<EsiCycle> getAllEsicCycle();
 		List<EsiCycle> getEsicCycle(Long esicId);
 }
