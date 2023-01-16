package com.csipl.hrms.service.report;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.service.report.repository.MiscellaneousReportRepository;


@Service
public class MiscellaneousReportServiceImpl implements MiscellaneousReportService {

	@Autowired
	private MiscellaneousReportRepository miscellaneousReportRepository ;


	@Override
	public List<Object[]> findAssetAllocationAndRecoveryReport(Long companyId, Long flag) {
		// TODO Auto-generated method stub

		if (flag == 0) {
			return miscellaneousReportRepository.findAllAssetAllocation(companyId);
		} else if (flag == 1) {
			return miscellaneousReportRepository.findRunningAssetAllocation(companyId);
		} else if (flag == 2) {
			return miscellaneousReportRepository.findSetteledAssetAllocation(companyId);

		}
		return null;
	}
	
	/*
	 * Shubham yaduwanshi
	 */
	@Override
	public List<Object[]> currentRoleSummaryReport(Long companyId, Long EmpStatusflag) {
		if (EmpStatusflag == 0) {
			// EmpStatusflag=0 for selecting working and former employee
			return miscellaneousReportRepository.findAllCurrentRoleSummary(companyId, StatusMessage.ACTIVE_CODE,
					StatusMessage.DEACTIVE_CODE);
		} else if (EmpStatusflag == 1) {
			// EmpStatusflag=1 for selecting working employee
			return miscellaneousReportRepository.findAllCurrentRoleSummary(companyId, StatusMessage.ACTIVE_CODE,
					StatusMessage.ACTIVE_CODE);
		} else if (EmpStatusflag == 2) {
			// EmpStatusflag=1 for selecting former employee
			return miscellaneousReportRepository.findAllCurrentRoleSummary(companyId, StatusMessage.DEACTIVE_CODE,
					StatusMessage.DEACTIVE_CODE);
		}
		return null;
	}

	/*
	 * Shubham yaduwanshi
	 */
	@Override
	public List<Object[]> ticketsSummaryReport(Long companyId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return miscellaneousReportRepository.findAllTicketSummary(companyId, startDate, endDate);
	}

}
