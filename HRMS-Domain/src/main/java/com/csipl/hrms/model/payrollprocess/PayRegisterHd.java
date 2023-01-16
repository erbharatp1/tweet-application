package com.csipl.hrms.model.payrollprocess;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the PayRegisterHd database table.
 * 
 */
@Entity
@NamedQuery(name = "PayRegisterHd.findAll", query = "SELECT p FROM PayRegisterHd p")
public class PayRegisterHd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payRegisterHdId;
	
	@Column(name = "companyId")
	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String processMonth;

	private Long userId;

	private Long userIdUpdate;

	// bi-directional many-to-one association to PayRegister
	@OneToMany(mappedBy = "payRegisterHd", cascade = CascadeType.ALL)
	private List<PayRegister> payRegisters;

	public PayRegisterHd() {
	}

	public Long getPayRegisterHdId() {
		return this.payRegisterHdId;
	}

	public void setPayRegisterHdId(Long payRegisterHdId) {
		this.payRegisterHdId = payRegisterHdId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getProcessMonth() {
		return this.processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public List<PayRegister> getPayRegisters() {
		return this.payRegisters;
	}

	public void setPayRegisters(List<PayRegister> payRegisters) {
		this.payRegisters = payRegisters;
	}

	public PayRegister addPayRegister(PayRegister payRegister) {
		getPayRegisters().add(payRegister);
		payRegister.setPayRegisterHd(this);

		return payRegister;
	}

	public PayRegister removePayRegister(PayRegister payRegister) {
		getPayRegisters().remove(payRegister);
		payRegister.setPayRegisterHd(null);

		return payRegister;
	}

}