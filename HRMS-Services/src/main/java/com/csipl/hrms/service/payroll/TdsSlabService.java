package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;

public interface TdsSlabService {
	public List<TdsSlabHd> getAllTdsSlabHd(Long companyId);
 	public TdsSlabHd save(TdsSlabHd tdsSlabHd);
	public TdsSlabHd getTdsSlabHd(Long tdsSlabHdId);

	public List<TdsSlab> saveTdsSlab(List<TdsSlab> tdsSlab);
	
	public List<TdsSlab> findAllTdsSlabHd(Long tdsSLabHdId);
	public void deleteById(Long tdsSLabId);
	/**
	 * updateByStatus}
	 */
	public void updateByStatus(TdsSlab tdsSlab);
	/**
	 * findAllTdsSlabHd}
	 * @param planType 
	 */
	public List<TdsSlab> findAllTdsSlabHdById(Long tdsSLabHdId, String planType);
}
