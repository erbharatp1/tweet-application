package com.csipl.hrms.service.common;

import java.util.List;

import com.csipl.hrms.model.common.ReportLov;

public interface ReportLovService {
	
	 public List<ReportLov> findReportByModuleName(String moduleName);
	 public List<ReportLov> findAllReport();

}
