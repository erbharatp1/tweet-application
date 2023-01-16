package com.csipl.hrms.dto.customer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CustomerSubscriptionDTO {
	private Long subscriptionId;
	private Timestamp dateCreated;
	private Long NoOfSubscriberTo;
	private Date dateSubscribed;

	private Date dateUnsubsribed;

	private String module;

	private Date offerEndDate;

	private BigDecimal otherCost;

	private String paymentTerm;

	private String remarks;

	private Long softwareId;
	private Long invoiceId;

	private Date validTo;
	private String activeStatus;

	private Long currentPlanId;

	private String organizationName;
	private String addressText;

	private String planName;
	private BigDecimal amountExcludingTax;

	private BigDecimal amountIncludingTax;
	private String paymentStatus;

	private Long customerId;

	private String subscriptionPeriod;
	private String packegeType;

	private String invoiceNo;

	 

	 
	public Long getNoOfSubscriberTo() {
		return NoOfSubscriberTo;
	}

	public void setNoOfSubscriberTo(Long noOfSubscriberTo) {
		NoOfSubscriberTo = noOfSubscriberTo;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateSubscribed() {
		return dateSubscribed;
	}

	public void setDateSubscribed(Date dateSubscribed) {
		this.dateSubscribed = dateSubscribed;
	}

	public Date getDateUnsubsribed() {
		return dateUnsubsribed;
	}

	public void setDateUnsubsribed(Date dateUnsubsribed) {
		this.dateUnsubsribed = dateUnsubsribed;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getOfferEndDate() {
		return offerEndDate;
	}

	public void setOfferEndDate(Date offerEndDate) {
		this.offerEndDate = offerEndDate;
	}

	public BigDecimal getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

 

	public Long getCurrentPlanId() {
		return currentPlanId;
	}

	public void setCurrentPlanId(Long currentPlanId) {
		this.currentPlanId = currentPlanId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getSubscriptionPeriod() {
		return subscriptionPeriod;
	}

	public void setSubscriptionPeriod(String subscriptionPeriod) {
		this.subscriptionPeriod = subscriptionPeriod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	public BigDecimal getAmountExcludingTax() {
		return amountExcludingTax;
	}

	public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
		this.amountExcludingTax = amountExcludingTax;
	}

	public BigDecimal getAmountIncludingTax() {
		return amountIncludingTax;
	}

	public void setAmountIncludingTax(BigDecimal amountIncludingTax) {
		this.amountIncludingTax = amountIncludingTax;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPackegeType() {
		return packegeType;
	}

	public void setPackegeType(String packegeType) {
		this.packegeType = packegeType;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
