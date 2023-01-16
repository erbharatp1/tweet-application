package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.dto.payroll.GratuityDTO;
import com.csipl.hrms.model.payroll.Gratuaty;

public interface GratuityService {
 	public  Gratuaty  getAllGratuity(Long companyId);
 	public Gratuaty save(Gratuaty gratuity);
 	public  List<GratuityDTO>  findAllGratuityByDescEffectiveDate(Long companyId);
 	public Gratuaty saveGratuatys(Gratuaty existingGratuity , Gratuaty newGratuity);
  }
