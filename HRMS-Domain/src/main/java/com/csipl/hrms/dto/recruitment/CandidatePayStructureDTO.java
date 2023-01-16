package com.csipl.hrms.dto.recruitment;

import java.math.BigDecimal;
import java.util.Date;

public class CandidatePayStructureDTO {
	private Long payStructureId;
	private Long candidatePaystructureHdId;
	private Long payHeadId;
	private String payHeadName;
	private BigDecimal amount;
	private Long userId;
	private Date dateCreated;
	public Long getPayStructureId() {
		return payStructureId;
	}
	public void setPayStructureId(Long payStructureId) {
		this.payStructureId = payStructureId;
	}
	public Long getCandidatePaystructureHdId() {
		return candidatePaystructureHdId;
	}
	public void setCandidatePaystructureHdId(Long candidatePaystructureHdId) {
		this.candidatePaystructureHdId = candidatePaystructureHdId;
	}
	public Long getPayHeadId() {
		return payHeadId;
	}
	public void setPayHeadId(Long payHeadId) {
		this.payHeadId = payHeadId;
	}
	public String getPayHeadName() {
		return payHeadName;
	}
	public void setPayHeadName(String payHeadName) {
		this.payHeadName = payHeadName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
