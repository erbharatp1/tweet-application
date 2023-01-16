package com.csipl.tms.dto.shift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShiftDTO {
	private Long shiftId;

	private String activeStatus;

	private Date createdDate;

	private Date effectiveDate;

	private String endPeriod;

	private String endTime;

	private Long graceFrqInMonth;

	private String graceTime;

	private String shiftDuration;

	private String shiftFName;

	private String startPeriod;

	private String startTime;

	private Long userId;

	private Long companyId;

	private Long updateUserId;

	private String activeStatusValue;

	private String halfDayRuleflag;

	@JsonProperty("shiftdto")
	private List<ShiftDTO> shiftdto = new ArrayList<ShiftDTO>();

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getGraceFrqInMonth() {
		return graceFrqInMonth;
	}

	public void setGraceFrqInMonth(Long graceFrqInMonth) {
		this.graceFrqInMonth = graceFrqInMonth;
	}

	public String getGraceTime() {
		return graceTime;
	}

	public void setGraceTime(String graceTime) {
		this.graceTime = graceTime;
	}

	public String getShiftDuration() {
		return shiftDuration;
	}

	public void setShiftDuration(String shiftDuration) {
		this.shiftDuration = shiftDuration;
	}

	public String getShiftFName() {
		return shiftFName;
	}

	public void setShiftFName(String shiftFName) {
		this.shiftFName = shiftFName;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getActiveStatusValue() {
		return activeStatusValue;
	}

	public void setActiveStatusValue(String activeStatusValue) {
		this.activeStatusValue = activeStatusValue;
	}

	public List<ShiftDTO> getShiftdto() {
		return shiftdto;
	}

	public void setShiftdto(List<ShiftDTO> shiftdto) {
		this.shiftdto = shiftdto;
	}

	public String getHalfDayRuleflag() {
		return halfDayRuleflag;
	}

	public void setHalfDayRuleflag(String halfDayRuleflag) {
		this.halfDayRuleflag = halfDayRuleflag;
	}

}