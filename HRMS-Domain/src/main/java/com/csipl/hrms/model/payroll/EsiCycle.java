package com.csipl.hrms.model.payroll;



import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the EsiCycle database table.
 * 
 */
@Entity
@NamedQuery(name="EsiCycle.findAll", query="SELECT e FROM EsiCycle e")
public class EsiCycle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long esiCycleId;

	private String fromperiod;

	private String toperiod;

	//bi-directional many-to-one association to Esi
	@ManyToOne
	@JoinColumn(name="esiId")
	private Esi esi;

	public EsiCycle() {
	}

	public Long getEsiCycleId() {
		return this.esiCycleId;
	}

	public void setEsiCycleId(Long esiCycleId) {
		this.esiCycleId = esiCycleId;
	}

	public String getFromperiod() {
		return this.fromperiod;
	}

	public void setFromperiod(String fromperiod) {
		this.fromperiod = fromperiod;
	}

	public String getToperiod() {
		return this.toperiod;
	}

	public void setToperiod(String toperiod) {
		this.toperiod = toperiod;
	}
	@JsonIgnore
	public Esi getEsi() {
		return this.esi;
	}

	public void setEsi(Esi esi) {
		this.esi = esi;
	}

}