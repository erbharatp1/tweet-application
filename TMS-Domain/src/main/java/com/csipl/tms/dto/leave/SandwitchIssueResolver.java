package com.csipl.tms.dto.leave;

import java.util.Date;

public class SandwitchIssueResolver implements Comparable<SandwitchIssueResolver> {
	
	public Date sandwitchDate= new Date();
	public Date leaveFromToSandwitchDate=new Date();
	public String leaveNature;
	public int leavePerId;
	public Date getSandwitchDate() {
		return sandwitchDate;
	}
	public void setSandwitchDate(Date sandwitchDate) {
		this.sandwitchDate = sandwitchDate;
	}
	public Date getLeavefromtoDate() {
		return leaveFromToSandwitchDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + leavePerId;
		result = prime * result + ((leaveFromToSandwitchDate == null) ? 0 : leaveFromToSandwitchDate.hashCode());
		result = prime * result + ((sandwitchDate == null) ? 0 : sandwitchDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SandwitchIssueResolver other = (SandwitchIssueResolver) obj;
		if (leavePerId != other.leavePerId)
			return false;
		if (leaveFromToSandwitchDate == null) {
			if (other.leaveFromToSandwitchDate != null)
				return false;
		} else if (!leaveFromToSandwitchDate.equals(other.leaveFromToSandwitchDate))
			return false;
		if (sandwitchDate == null) {
			if (other.sandwitchDate != null)
				return false;
		} else if (!sandwitchDate.equals(other.sandwitchDate))
			return false;
		return true;
	}
	public void setLeavefromtoDate(Date leavefromtoDate) {
		this.leaveFromToSandwitchDate = leavefromtoDate;
	}
	public int getLeavePerId() {
		return leavePerId;
	}
	public void setLeavePerId(int leavePerId) {
		this.leavePerId = leavePerId;
	}
	@Override
	public int compareTo(SandwitchIssueResolver o) {
		// TODO Auto-generated method stub
		return this.sandwitchDate.compareTo(o.sandwitchDate);
	}
	public String getLeaveNature() {
		return leaveNature;
	}
	public void setLeaveNature(String leaveNature) {
		this.leaveNature = leaveNature;
	}
	
	

}
