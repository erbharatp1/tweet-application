package com.csipl.hrms.dto.recruitment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InterviewLevelDTO {
	private String interviewMode;
	private String interviewerName;
	private String levelName;
	private String status;
	private String remarks;
	private Long evalutionId;
	private Long interviewScheduleId;
	private Long levelId;
	private String level;
	private String filePath;
	private String fileName;
	private Date dateUpdated;
	public Long getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(Long evalutionId) {
		this.evalutionId = evalutionId;
	}

	public Long getInterviewScheduleId() {
		return interviewScheduleId;
	}

	public void setInterviewScheduleId(Long interviewScheduleId) {
		this.interviewScheduleId = interviewScheduleId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
