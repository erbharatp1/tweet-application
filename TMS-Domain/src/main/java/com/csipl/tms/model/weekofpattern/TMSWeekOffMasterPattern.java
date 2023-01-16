package com.csipl.tms.model.weekofpattern;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TMSWeekOffMasterPattern database table.
 * 
 */
@Entity
@Table(name = "TMSWeekOffMasterPattern")
@NamedQuery(name="TMSWeekOffMasterPattern.findAll", query="SELECT t FROM TMSWeekOffMasterPattern t")
public class TMSWeekOffMasterPattern implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long patternId;

	private String activeStatus;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private String patternName;

	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	private Long updateUserId;

	private Long userId;

	//bi-directional many-to-one association to TMSWeekOffChildPattern
	@OneToMany(mappedBy="tmsweekOffMasterPattern", cascade = CascadeType.ALL)
	private List<TMSWeekOffChildPattern> tmsweekOffChildPatterns;

	public TMSWeekOffMasterPattern() {
	}

	public Long getPatternId() {
		return this.patternId;
	}

	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPatternName() {
		return this.patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TMSWeekOffChildPattern> getTmsweekOffChildPatterns() {
		return this.tmsweekOffChildPatterns;
	}

	public void setTmsweekOffChildPatterns(List<TMSWeekOffChildPattern> tmsweekOffChildPatterns) {
		this.tmsweekOffChildPatterns = tmsweekOffChildPatterns;
	}

	public TMSWeekOffChildPattern addTmsweekOffChildPattern(TMSWeekOffChildPattern tmsweekOffChildPattern) {
		getTmsweekOffChildPatterns().add(tmsweekOffChildPattern);
		tmsweekOffChildPattern.setTmsweekOffMasterPattern(this);

		return tmsweekOffChildPattern;
	}

	public TMSWeekOffChildPattern removeTmsweekOffChildPattern(TMSWeekOffChildPattern tmsweekOffChildPattern) {
		getTmsweekOffChildPatterns().remove(tmsweekOffChildPattern);
		tmsweekOffChildPattern.setTmsweekOffMasterPattern(null);

		return tmsweekOffChildPattern;
	}

}