package com.csipl.hrms.service.report;

import java.util.List;

public interface TdsReportService {

	List<Object[]> getStatememntOfAnnualTax(Long companyId, Long financialYearId);
	List<Object[]> getDeclarationColumn(Long companyId, Long financialYearId);
	List<Object[]> getEmployeeTdsDeclaration(Long companyId, Long financialYearId);

}
