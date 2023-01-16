package com.csipl.hrms.model.payrollprocess;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ArrearPayOut database table.
 * 
 */
@Embeddable
public class ArrearPayOutPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String processMonth;

	private Long employeeId;

	private Long payHeadId;

	public ArrearPayOutPK() {
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
	public Long getPayHeadId() {
		return this.payHeadId;
	}
	public void setPayHeadId(Long payHeadId) {
		this.payHeadId = payHeadId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArrearPayOutPK)) {
			return false;
		}
		ArrearPayOutPK castOther = (ArrearPayOutPK)other;
		return 
			this.processMonth.equals(castOther.processMonth)
			&& (this.employeeId == castOther.employeeId)
			&& (this.payHeadId == castOther.payHeadId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.processMonth.hashCode();
		hash = (int) (hash * prime + this.employeeId);
		hash = (int) (hash * prime + this.payHeadId);
		
		return hash;
	}
}