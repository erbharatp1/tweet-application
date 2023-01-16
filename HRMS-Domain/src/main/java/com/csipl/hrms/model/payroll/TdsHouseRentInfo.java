package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.Date;
import java.math.BigInteger;
import java.util.List;

/**
 * The persistent class for the TdsHouseRentInfo database table.
 * 
 */
@Entity
@NamedQuery(name = "TdsHouseRentInfo.findAll", query = "SELECT t FROM TdsHouseRentInfo t")
public class TdsHouseRentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tdsHouseRentInfoId;

	private String activeStatus;

	@Lob
	private String addressOfLandlord;

	@Lob
	private String addressOfRentalProperty;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	@Temporal(TemporalType.DATE)
	private Date fromDate;

	private String landlordName;

	private String landlordPan;

	@Temporal(TemporalType.DATE)
	private Date toDate;

	private BigDecimal totalRental;

	private BigInteger userId;

	private BigInteger userIdUpdate;

	// bi-directional many-to-one association to TdsHouseRentFileInfo
	@OneToMany(mappedBy = "tdsHouseRentInfo",cascade = CascadeType.ALL)
	private List<TdsHouseRentFileInfo> tdsHouseRentFileInfos;

	// bi-directional many-to-one association to TdsTransaction
	@ManyToOne
	@JoinColumn(name = "tdsTransactionId")
	private TdsTransaction tdsTransaction;

	public TdsHouseRentInfo() {
	}

	public Long getTdsHouseRentInfoId() {
		return tdsHouseRentInfoId;
	}

	public void setTdsHouseRentInfoId(Long tdsHouseRentInfoId) {
		this.tdsHouseRentInfoId = tdsHouseRentInfoId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAddressOfLandlord() {
		return this.addressOfLandlord;
	}

	public void setAddressOfLandlord(String addressOfLandlord) {
		this.addressOfLandlord = addressOfLandlord;
	}

	public String getAddressOfRentalProperty() {
		return this.addressOfRentalProperty;
	}

	public void setAddressOfRentalProperty(String addressOfRentalProperty) {
		this.addressOfRentalProperty = addressOfRentalProperty;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getLandlordName() {
		return this.landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordPan() {
		return this.landlordPan;
	}

	public void setLandlordPan(String landlordPan) {
		this.landlordPan = landlordPan;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getTotalRental() {
		return this.totalRental;
	}

	public void setTotalRental(BigDecimal totalRental) {
		this.totalRental = totalRental;
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(BigInteger userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public List<TdsHouseRentFileInfo> getTdsHouseRentFileInfos() {
		return this.tdsHouseRentFileInfos;
	}

	public void setTdsHouseRentFileInfos(List<TdsHouseRentFileInfo> tdsHouseRentFileInfos) {
		this.tdsHouseRentFileInfos = tdsHouseRentFileInfos;
	}

	public TdsHouseRentFileInfo addTdsHouseRentFileInfo(TdsHouseRentFileInfo tdsHouseRentFileInfo) {
		getTdsHouseRentFileInfos().add(tdsHouseRentFileInfo);
		tdsHouseRentFileInfo.setTdsHouseRentInfo(this);

		return tdsHouseRentFileInfo;
	}

	public TdsHouseRentFileInfo removeTdsHouseRentFileInfo(TdsHouseRentFileInfo tdsHouseRentFileInfo) {
		getTdsHouseRentFileInfos().remove(tdsHouseRentFileInfo);
		tdsHouseRentFileInfo.setTdsHouseRentInfo(null);

		return tdsHouseRentFileInfo;
	}

	public TdsTransaction getTdsTransaction() {
		return this.tdsTransaction;
	}

	public void setTdsTransaction(TdsTransaction tdsTransaction) {
		this.tdsTransaction = tdsTransaction;
	}

}