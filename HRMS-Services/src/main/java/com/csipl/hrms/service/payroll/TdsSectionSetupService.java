package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsPayrollHd;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public interface TdsSectionSetupService {

	public List<TdsSectionSetup> getAllTdsSection(Long tdsGroupId);

	public List<TdsSectionSetup> saveTdsSection(List<TdsSectionSetup> TdsSectionSetupList);

	/**
	 * deleteById}
	 */
	public void deleteById(Long tdsSectionId);

	public TdsGroupSetup getByGroupId(Long companyId, Long tdsGroupMasterId);

	public TdsGroupSetup saveTdsGroup(TdsGroupSetup tdsGroupSetup);
	
	public void updateById(TdsGroupSetup tdsGroupSetup);

	/**
	 * updateByStatus}
	 */
	void updateByStatus(TdsSectionSetup tdsSectionSetup);

	/**
	 * findByFinanicalYear}
	 */
	public TdsGroupSetup findByFinanicalYear(Long companyId);

	/**
	 * getAllTdsGroupSetup}
	 */
	public TdsGroupMaster getAllTdsGroupSetup(String tdsGroupName, Long companyId);

	/**
	 * saveTdsGroupSetup}
	 */
	public List<TdsGroupSetup> saveTdsGroupSetup(List<TdsGroupSetup> tdsSectionSetupList);

	public List<TdsGroupSetup> findByFinanicalYear(Long companyId, Long financialYearId);

}
