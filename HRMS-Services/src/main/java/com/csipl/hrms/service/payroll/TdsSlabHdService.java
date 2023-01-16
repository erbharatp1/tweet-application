package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.model.payroll.TdsSlabHd;

public interface TdsSlabHdService {
	/**
	 * 
	 * getAllTdsSlabHd}
	 */
	public List<TdsSlabHd> getAllTdsSlabHd(Long companyId);

	/**
	 * 
	 * findTdsSlabHdByFinencailYear}
	 */
	public List<TdsSlabHd> findTdsSlabHdByFinencailYear(Long companyId, Long finencialYearId);

	/**
	 * saveTdsSlabHd}
	 */
	public List<TdsSlabHd> saveTdsSlabHd(List<TdsSlabHd> tdsSlabList);
}
