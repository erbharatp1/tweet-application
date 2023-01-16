package com.csipl.hrms.service.report;

import java.util.Date;
import java.util.List;


public interface MiscellaneousReportService {

	List<Object[]> findAssetAllocationAndRecoveryReport(Long companyId, Long flag);

	List<Object[]> currentRoleSummaryReport(Long companyId, Long flag);

	List<Object[]> ticketsSummaryReport(Long companyId, Date startDate, Date endDate);

}
