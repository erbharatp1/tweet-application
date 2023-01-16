package com.csipl.hrms.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.service.report.repository.TdsReportRepository;

@Service
public class TdsReportServiceImpl implements TdsReportService {

	@Autowired
	TdsReportRepository tdsReportRepository; 
	
	@Override
	public List<Object[]> getStatememntOfAnnualTax(Long companyId, Long financialYearId) {
		return tdsReportRepository.getStatememntOfAnnualTax(companyId,  financialYearId);
	}

	@Override
	public List<Object[]> getDeclarationColumn(Long companyId, Long financialYearId) {
		
		return tdsReportRepository.getDeclarationColumn(companyId,financialYearId);
	}

	@Override
	public List<Object[]> getEmployeeTdsDeclaration(Long companyId, Long financialYearId) {
		return tdsReportRepository.getEmployeeTdsDeclaration(companyId,financialYearId);
	}

}
