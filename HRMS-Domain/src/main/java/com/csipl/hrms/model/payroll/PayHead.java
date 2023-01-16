package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import com.csipl.hrms.model.BaseModel;
import com.csipl.hrms.model.employee.PayStructure;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the PayHeads database table.
 * 
 */
@Entity
@Table(name = "PayHeads")
@NamedQuery(name = "PayHead.findAll", query = "SELECT p FROM PayHead p")
public class PayHead extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payHeadId;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String earningDeduction;

	private String expenseType;

	private String incomeType;

	private String isApplicableOnGratuaty;

	private String isApplicableOnEsi;

	private String isApplicableOnPf;

	private String isApplicableOnPt;
	
	private String isApplicableOnLWS;

	// bi-directional many-to-one association to PayStructure
	@OneToMany(mappedBy = "payHead")
	private List<PayStructure> payStructures;

	private String payHeadName;
	private String payHeadFlag;
	private String activeStatus;

	private String basedOn;
	private BigDecimal amount;
	private BigDecimal percentage;
	private String headType;
	private Long priority;

	private Long sequenceId;
	
	
	
	
	public PayHead() {
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

	public Long getPayHeadId() {
		return this.payHeadId;
	}

	public void setPayHeadId(Long payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
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

	public String getEarningDeduction() {
		return this.earningDeduction;
	}

	public void setEarningDeduction(String earningDeduction) {
		this.earningDeduction = earningDeduction;
	}

	public String getExpenseType() {
		return this.expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getIncomeType() {
		return this.incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getIsApplicableOnEsi() {
		return this.isApplicableOnEsi;
	}

	public void setIsApplicableOnEsi(String isApplicableOnEsi) {
		this.isApplicableOnEsi = isApplicableOnEsi;
	}

	public String getIsApplicableOnGratuaty() {
		return this.isApplicableOnGratuaty;
	}

	public void setIsApplicableOnGratuaty(String isApplicableOnGratuaty) {
		this.isApplicableOnGratuaty = isApplicableOnGratuaty;
	}

	public String getIsApplicableOnPf() {
		return this.isApplicableOnPf;
	}

	public void setIsApplicableOnPf(String isApplicableOnPf) {
		this.isApplicableOnPf = isApplicableOnPf;
	}

	public String getIsApplicableOnPt() {
		return this.isApplicableOnPt;
	}

	public void setIsApplicableOnPt(String isApplicableOnPt) {
		this.isApplicableOnPt = isApplicableOnPt;
	}
	
	
	public String getIsApplicableOnLWS() {
		return isApplicableOnLWS;
	}

	public void setIsApplicableOnLWS(String isApplicableOnLWS) {
		this.isApplicableOnLWS = isApplicableOnLWS;
	}

	public String getPayHeadName() {
		return this.payHeadName;
	}

	public void setPayHeadName(String payHeadName) {
		this.payHeadName = payHeadName;
	}

	public List<PayStructure> getPayStructures() {
		return this.payStructures;
	}

	public void setPayStructures(List<PayStructure> payStructures) {
		this.payStructures = payStructures;
	}

	public PayStructure addPayStructure(PayStructure payStructure) {
		getPayStructures().add(payStructure);
		payStructure.setPayHead(this);

		return payStructure;
	}

	public PayStructure removePayStructure(PayStructure payStructure) {
		getPayStructures().remove(payStructure);
		payStructure.setPayHead(null);

		return payStructure;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
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

	@Override
	public String toString() {
		return "PayHead [payHeadId=" + payHeadId + ", allowModi=" + allowModi + ", dateCreated=" + dateCreated
				+ ", dateUpdate=" + dateUpdate + ", earningDeduction=" + earningDeduction + ", expenseType="
				+ expenseType + ", incomeType=" + incomeType + ", isApplicableOnGratuaty=" + isApplicableOnGratuaty
				+ ", isApplicableOnEsi=" + isApplicableOnEsi + ", isApplicableOnPf=" + isApplicableOnPf
				+ ", isApplicableOnPt=" + isApplicableOnPt + ", payStructures=" + payStructures + ", payHeadName="
				+ payHeadName + ", payHeadFlag=" + payHeadFlag + ", activeStatus=" + activeStatus + ", basedOn="
				+ basedOn + ", amount=" + amount + ", percentage=" + percentage + ", headType=" + headType
				+ ", priority=" + priority + "]";
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	

}