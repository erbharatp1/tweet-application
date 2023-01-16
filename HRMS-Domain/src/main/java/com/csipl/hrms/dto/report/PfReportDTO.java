package com.csipl.hrms.dto.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PfReportDTO {
	
	private String name;
	private String uanno;
	private Date dob;
	private String aadharNo;
	private String fatherName;
	private String mobileNo;
	private List<PfReportTableDTO> pfReportTableDtoList = new ArrayList<PfReportTableDTO>();
	
	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	private Long CompanyId;
	private String dateFrom;
	private String dateTo;
	private String empName;
	private String empCode;
	private String statuaryType;
	private String pfNo;
	private Date pfIssueDate;
	private Date pfExpiryDate;
	private String statuaryNumber;
	private String departmentName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUanno() {
		return uanno;
	}
	public void setUanno(String uanno) {
		this.uanno = uanno;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public List<PfReportTableDTO> getPfReportTableDtoList() {
		return pfReportTableDtoList;
	}
	public void setPfReportTableDtoList(List<PfReportTableDTO> pfReportTableDtoList) {
		this.pfReportTableDtoList = pfReportTableDtoList;
	}
	public Long getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(Long companyId) {
		CompanyId = companyId;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getStatuaryType() {
		return statuaryType;
	}
	public void setStatuaryType(String statuaryType) {
		this.statuaryType = statuaryType;
	}
	public String getPfNo() {
		return pfNo;
	}
	public void setPfNo(String pfNo) {
		this.pfNo = pfNo;
	}
	public Date getPfIssueDate() {
		return pfIssueDate;
	}
	public void setPfIssueDate(Date pfIssueDate) {
		this.pfIssueDate = pfIssueDate;
	}
	public Date getPfExpiryDate() {
		return pfExpiryDate;
	}
	public void setPfExpiryDate(Date pfExpiryDate) {
		this.pfExpiryDate = pfExpiryDate;
	}
	public String getStatuaryNumber() {
		return statuaryNumber;
	}
	public void setStatuaryNumber(String statuaryNumber) {
		this.statuaryNumber = statuaryNumber;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
}
