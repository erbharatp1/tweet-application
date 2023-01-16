package com.csipl.hrms.model.common;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ReportLov database table.
 * 
 */
@Entity
@NamedQuery(name = "ReportLov.findAll", query = "SELECT r FROM ReportLov r")
public class ReportLov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reportLovId;

	private String moduleName;

	private String reportId;

	private String reportName;

	private String status;

	public ReportLov() {
	}

	public int getReportLovId() {
		return this.reportLovId;
	}

	public void setReportLovId(int reportLovId) {
		this.reportLovId = reportLovId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}