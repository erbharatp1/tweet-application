package com.csipl.hrms.model.payrollprocess;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ArrearPayOut database table.
 * 
 */
@Entity
@NamedQuery(name="ArrearPayOut.findAll", query="SELECT a FROM ArrearPayOut a")
public class ArrearPayOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArrearPayOutPK id;

	private BigDecimal amount;

	public ArrearPayOut() {
	}

	public ArrearPayOutPK getId() {
		return this.id;
	}

	public void setId(ArrearPayOutPK id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}