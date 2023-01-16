package com.csipl.hrms.model.payrollprocess;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ArrearReportPayOut database table.
 * 
 */
@Embeddable
public class ArrearReportPayOutPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String processMonth;

	private Long employeeId;

	public ArrearReportPayOutPK() {
	}
	public String getProcessMonth() {
		return this.processMonth;
	}
	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}
	public Long getEmployeeId() {
		return this.employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArrearReportPayOutPK)) {
			return false;
		}
		ArrearReportPayOutPK castOther = (ArrearReportPayOutPK)other;
		return 
			this.processMonth.equals(castOther.processMonth)
			&& (this.employeeId == castOther.employeeId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.processMonth.hashCode();
		hash = (int) (hash * prime + this.employeeId);
		
		return hash;
	}
}