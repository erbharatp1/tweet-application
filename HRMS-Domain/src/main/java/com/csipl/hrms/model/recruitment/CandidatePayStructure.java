package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.payroll.PayHead;

/**
 * The persistent class for the PayStructure database table.
 * 
 */
@Entity
@NamedQuery(name = "CandidatePayStructure.findAll", query = "SELECT p FROM CandidatePayStructure p")
public class CandidatePayStructure implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long candidatePaystructureId;

	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	private Long userId;

	// bi-directional many-to-one association to PayHead
	@ManyToOne
	@JoinColumn(name = "payHeadId")
	private PayHead payHead;

	// bi-directional many-to-one association to PayStructureHd
	@ManyToOne
	@JoinColumn(name = "candidatePayStructureHdId")
	private CandidatePayStructureHd candidatePayStructureHd;

	public CandidatePayStructure() {
	}

	public Long getCandidatePaystructureId() {
		return candidatePaystructureId;
	}

	public void setCandidatePaystructureId(Long candidatePaystructureId) {
		this.candidatePaystructureId = candidatePaystructureId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public PayHead getPayHead() {
		return payHead;
	}

	public void setPayHead(PayHead payHead) {
		this.payHead = payHead;
	}

	public CandidatePayStructureHd getCandidatePayStructureHd() {
		return candidatePayStructureHd;
	}

	public void setCandidatePayStructureHd(CandidatePayStructureHd candidatePayStructureHd) {
		this.candidatePayStructureHd = candidatePayStructureHd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
