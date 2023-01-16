package com.csipl.tms.model.weekofpattern;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TMSWeekOffChildPattern database table.
 * 
 */
@Entity
@Table(name = "TMSWeekOffChildPattern")
@NamedQuery(name="TMSWeekOffChildPattern.findAll", query="SELECT t FROM TMSWeekOffChildPattern t")
public class TMSWeekOffChildPattern implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String activeStatus;

	private String dayName;

	private String natureOfDay;

	private Long positionOfDay;

	//bi-directional many-to-one association to TMSWeekOffMasterPattern
	@ManyToOne
	@JoinColumn(name="patternId")
	private TMSWeekOffMasterPattern tmsweekOffMasterPattern;

	public TMSWeekOffChildPattern() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getDayName() {
		return this.dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public String getNatureOfDay() {
		return this.natureOfDay;
	}

	public void setNatureOfDay(String natureOfDay) {
		this.natureOfDay = natureOfDay;
	}

	public Long getPositionOfDay() {
		return this.positionOfDay;
	}

	public void setPositionOfDay(Long positionOfDay) {
		this.positionOfDay = positionOfDay;
	}

	public TMSWeekOffMasterPattern getTmsweekOffMasterPattern() {
		return this.tmsweekOffMasterPattern;
	}

	public void setTmsweekOffMasterPattern(TMSWeekOffMasterPattern tmsweekOffMasterPattern) {
		this.tmsweekOffMasterPattern = tmsweekOffMasterPattern;
	}

}