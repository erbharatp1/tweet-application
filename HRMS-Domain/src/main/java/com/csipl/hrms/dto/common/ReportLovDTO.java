package com.csipl.hrms.dto.common;

public class ReportLovDTO {

	private Integer reportLovId;
	private String reportName;
	private String reportId;
	private String moduleName;
	private String status;

	public Integer getReportLovId() {
		return reportLovId;
	}

	public void setReportLovId(Integer reportLovId) {
		this.reportLovId = reportLovId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
