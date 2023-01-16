package com.csipl.hrms.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.common.ReportLov;
import com.csipl.hrms.service.common.repository.ReportLovRepository;

@Service("reportLovService")
public class ReportLovServiceImpl implements ReportLovService{

	@Autowired
	private ReportLovRepository reportLovRepository;
	
	
	@Override
	public List<ReportLov> findReportByModuleName(String moduleName) {
		
		return reportLovRepository.findReportByModuleName(moduleName);
	}

	@Override
	public List<ReportLov> findAllReport() {
		
		return reportLovRepository.findAllReport();
	}

}
