package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.util.Date;

public class PayHeadDTO {

	private Long payHeadId;
	private String payHeadName;
	private String earningDeduction;
	private String expenseType;
	private String incomeType;
	private String earningDeductionValue;
	private String expenseTypeValue;
	private String incomeTypeValue;
	private String isApplicableOnGratuaty;
	private String isApplicableOnEsi;
	private String isApplicableOnPf;
	private String isApplicableOnPt;
	private String isApplicableOnLWS;
	private Long userId;
	private Date dateCreated;
	private Long userIdUpdate;
	private Long companyId;
	private String applicableOn;
	private String payHeadFlag;
	private String activeStatus;
 	private String basedOn;
	private String basedOnValue;
	private BigDecimal amount;
	private BigDecimal percentage;
	private String headType;
 	private BigDecimal montlyAmount;
 	private Long priority;
	public PayHeadDTO() {
		super();
	}

	public String getBasedOnValue() {
		return basedOnValue;
	}

	public void setBasedOnValue(String basedOnValue) {
		this.basedOnValue = basedOnValue;
	}

	public String getHeadType() {
		return headType;
	}

	public void setHeadType(String headType) {
		this.headType = headType;
	}

	public String getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(String basedOn) {
		this.basedOn = basedOn;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
 
	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getPayHeadFlag() {
		return payHeadFlag;
	}

	public void setPayHeadFlag(String payHeadFlag) {
		this.payHeadFlag = payHeadFlag;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getIsApplicableOnEsi() {
		return isApplicableOnEsi;
	}

	public void setIsApplicableOnEsi(String isApplicableOnEsi) {
		this.isApplicableOnEsi = isApplicableOnEsi;
	}

	public String getIsApplicableOnPf() {
		return isApplicableOnPf;
	}

	public void setIsApplicableOnPf(String isApplicableOnPf) {
		this.isApplicableOnPf = isApplicableOnPf;
	}

	public String getIsApplicableOnPt() {
		return isApplicableOnPt;
	}

	public void setIsApplicableOnPt(String isApplicableOnPt) {
		this.isApplicableOnPt = isApplicableOnPt;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getEarningDeductionValue() {
		return earningDeductionValue;
	}

	public void setEarningDeductionValue(String earningDeductionValue) {
		this.earningDeductionValue = earningDeductionValue;
	}

	public String getExpenseTypeValue() {
		return expenseTypeValue;
	}

	public void setExpenseTypeValue(String expenseTypeValue) {
		this.expenseTypeValue = expenseTypeValue;
	}

	public String getIncomeTypeValue() {
		return incomeTypeValue;
	}

	public void setIncomeTypeValue(String incomeTypeValue) {
		this.incomeTypeValue = incomeTypeValue;
	}

	public String getIsApplicableOnGratuaty() {
		return isApplicableOnGratuaty;
	}

	public void setIsApplicableOnGratuaty(String isApplicableOnGratuaty) {
		this.isApplicableOnGratuaty = isApplicableOnGratuaty;
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

	public String getEarningDeduction() {
		return earningDeduction;
	}

	public void setEarningDeduction(String earningDeduction) {
		this.earningDeduction = earningDeduction;
	}

	public String getApplicableOn() {
		return applicableOn;
	}

	public void setApplicableOn(String applicableOn) {
		this.applicableOn = applicableOn;
	}
 
 	public BigDecimal getMontlyAmount() {
		return montlyAmount;
	}
	public void setMontlyAmount(BigDecimal montlyAmount) {
		this.montlyAmount = montlyAmount;
	}

	/**
	 * @return the priority
	 */
	public Long getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getIsApplicableOnLWS() {
		return isApplicableOnLWS;
	}

	public void setIsApplicableOnLWS(String isApplicableOnLWS) {
		this.isApplicableOnLWS = isApplicableOnLWS;
	}

	
	
}
