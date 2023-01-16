package com.csipl.hrms.dto.payroll;

public class EsiCycleDTO {
	private Long esiCycleId;

	private String fromperiod;

	private String toperiod;

    private Long esiId;

	public EsiCycleDTO() {
	
	}

	public EsiCycleDTO(Long esiCycleId, String fromperiod, String toperiod, Long esiId) {
		this.esiCycleId = esiCycleId;
		this.fromperiod = fromperiod;
		this.toperiod = toperiod;
		this.esiId = esiId;
	}

	public Long getEsiCycleId() {
		return esiCycleId;
	}

	public void setEsiCycleId(Long esiCycleId) {
		this.esiCycleId = esiCycleId;
	}

	public String getFromperiod() {
		return fromperiod;
	}

	public void setFromperiod(String fromperiod) {
		this.fromperiod = fromperiod;
	}

	public String getToperiod() {
		return toperiod;
	}

	public void setToperiod(String toperiod) {
		this.toperiod = toperiod;
	}

	public Long getEsiId() {
		return esiId;
	}

	public void setEsiId(Long esiId) {
		this.esiId = esiId;
	}
	
}
