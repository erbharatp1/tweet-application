package com.csipl.hrms.dto.payroll;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.payroll.TdsHouseRentFileInfo;

/**
 * The persistent class for the TdsHouseRentInfo database table.
 * 
 */

public class TdsHouseRentInfoDTO {

	private Long tdsHouseRentInfoId;

	private String activeStatus;

	private String addressOfLandlord;

	private String addressOfRentalProperty;

	private Date dateCreated;

	private Date dateUpdated;

	private Date fromDate;

	private String landlordName;

	private String landlordPan;

	private Date toDate;

	private BigDecimal totalRental;

	private BigInteger userId;

	private BigInteger userIdUpdate;

	private Long tdsTransactionId;

	private String empCode;
	private String empName;
	private String address;
	private String city;

	private List<TdsHouseRentFileInfoDTO> tdsHouseRentFileInfoDTO;

	public List<TdsHouseRentFileInfoDTO> getTdsHouseRentFileInfoDTO() {
		return tdsHouseRentFileInfoDTO;
	}

	public void setTdsHouseRentFileInfoDTO(List<TdsHouseRentFileInfoDTO> tdsHouseRentFileInfoDTO) {
		this.tdsHouseRentFileInfoDTO = tdsHouseRentFileInfoDTO;
	}

	public Long getTdsTransactionId() {
		return tdsTransactionId;
	}

	public void setTdsTransactionId(Long tdsTransactionId) {
		this.tdsTransactionId = tdsTransactionId;
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

	public Long getTdsHouseRentInfoId() {
		return tdsHouseRentInfoId;
	}

	public void setTdsHouseRentInfoId(Long tdsHouseRentInfoId) {
		this.tdsHouseRentInfoId = tdsHouseRentInfoId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}