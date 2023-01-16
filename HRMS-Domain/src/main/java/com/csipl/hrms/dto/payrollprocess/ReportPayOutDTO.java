package com.csipl.hrms.dto.payrollprocess;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import com.csipl.hrms.dto.report.EmployeeReportDTO;

public class ReportPayOutDTO extends EmployeeReportDTO{
	private String name;
	private String employeeCode;
	private String bankName;
	private String accountNumber;
	private Date dateOfJoining;
	private Date endDate;
	private BigDecimal basic;
	private BigDecimal conveyanceAllowance;	
	private BigDecimal hra;	
	private BigDecimal hraEarning;
	private BigDecimal otherAllowance;
	private BigDecimal overtime;
	private BigDecimal otherAllowanceEarning;
    private BigDecimal absense;
	private BigDecimal advanceBonus;
	private BigDecimal advanceBonusEarning;
	private BigDecimal basicEarning;
	private BigDecimal casualleave;
	private BigDecimal companyBenefits;
	private BigDecimal companyBenefitsEarning;
	private BigDecimal conveyanceAllowanceEarning;
	private BigDecimal employeeLoansAdvnace;
	private BigDecimal employeeLoansAdvnaceEarning;
	
	
	
	private BigDecimal epfEmployee;
	private BigDecimal epfEmployer;
	private BigDecimal professionalTax;
	
	private BigDecimal esi_Employee;
	private BigDecimal esi_Employer;
	private BigDecimal grossSalary;
	private BigDecimal loan;
	private BigDecimal otherDeduction;
	private BigDecimal netPayableAmountTotal;
	private String transactionMode;
	private BigDecimal medicalAllowance;
	private BigDecimal medicalAllowanceEarning;
	private BigDecimal netPayableAmount;
	private BigDecimal paidleave;
	private BigDecimal payDays;
	private BigDecimal presense;
	private BigDecimal providentFundEmployee;
	private BigDecimal providentFundEmployer;
	private BigDecimal providentFundEmployerPension;
	private BigDecimal pt;
	private BigDecimal publicholidays;
	private BigDecimal seekleave;
	private BigDecimal specialAllowance;
	private BigDecimal specialAllowanceEarning;
	private BigDecimal tds;
	private BigDecimal totalDeduction;
	private BigDecimal totalEarning;
	private BigDecimal weekoff;
	private Long cityId;
	private Long companyId;
	private String companyName;
	private String unNo;
	private String panNo;
	private String aadharNo;
	private String stateName;
	private Date provisionDateCreated;
	private BigDecimal dearnessAllowanceEarning;
	private BigDecimal dearnessAllowance;
	private Long daysInMonth;
	private Long payableDays;
	private String branchName;
	private String jobLocation;
	private Long headCount;
	private Map<String,String> payHeadsMap;
	 private Long absent;
	 
	 private Date effectiveDate;
	 private BigDecimal ctc;
	 private String employeeType;
	 private String employeeStatus;
	 private BigDecimal lwfEmployeeAmount;
	 private BigDecimal lwfEmployerAmount;
	 private BigDecimal epfEmployeePension;
	 private BigDecimal totalContribution;
	 
	 private String processMonth;
		private BigDecimal apr;
		private BigDecimal may;
		private BigDecimal jun;
		private BigDecimal jul;
		private BigDecimal aug;
		private BigDecimal sep;
		private BigDecimal oct;
		private BigDecimal nov;
		private BigDecimal dec;
		private BigDecimal jan;
		private BigDecimal feb;
		private BigDecimal mar;
	 
	 
		
		
	public String getProcessMonth() {
			return processMonth;
		}
		public void setProcessMonth(String processMonth) {
			this.processMonth = processMonth;
		}
		public BigDecimal getApr() {
			return apr;
		}
		public void setApr(BigDecimal apr) {
			this.apr = apr;
		}
		public BigDecimal getMay() {
			return may;
		}
		public void setMay(BigDecimal may) {
			this.may = may;
		}
		public BigDecimal getJun() {
			return jun;
		}
		public void setJun(BigDecimal jun) {
			this.jun = jun;
		}
		public BigDecimal getJul() {
			return jul;
		}
		public void setJul(BigDecimal jul) {
			this.jul = jul;
		}
		public BigDecimal getAug() {
			return aug;
		}
		public void setAug(BigDecimal aug) {
			this.aug = aug;
		}
		public BigDecimal getSep() {
			return sep;
		}
		public void setSep(BigDecimal sep) {
			this.sep = sep;
		}
		public BigDecimal getOct() {
			return oct;
		}
		public void setOct(BigDecimal oct) {
			this.oct = oct;
		}
		public BigDecimal getNov() {
			return nov;
		}
		public void setNov(BigDecimal nov) {
			this.nov = nov;
		}
		public BigDecimal getDec() {
			return dec;
		}
		public void setDec(BigDecimal dec) {
			this.dec = dec;
		}
		public BigDecimal getJan() {
			return jan;
		}
		public void setJan(BigDecimal jan) {
			this.jan = jan;
		}
		public BigDecimal getFeb() {
			return feb;
		}
		public void setFeb(BigDecimal feb) {
			this.feb = feb;
		}
		public BigDecimal getMar() {
			return mar;
		}
		public void setMar(BigDecimal mar) {
			this.mar = mar;
		}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public BigDecimal getCtc() {
		return ctc;
	}
	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public Long getAbsent() {
		return absent;
	}
	public void setAbsent(Long absent) {
		this.absent = absent;
	}
	private String departmentName;
	private BigDecimal monthalyGross;
	private Date reconciliationDate;
	private String transactionNo;
	private String designationName;
	private String fatherName;
	private String nominee;
	private String nomineeRelation;
	private Date DOB;
	private Date epfJoining;
	private Date esicJoining;
	private String maritalStatus;
	private String ifscCode;
	private String mobNo;
	private String email;
	private String gender;
	private BigDecimal PensionEarningSalary;
	private String esiNo;
	private Long employeeId;
	private Date transactionDate;
	private BigDecimal otherEarning;
	private BigDecimal amount;
	private  int totalEmployee;
	private Date dateUpdate;
	private BigDecimal adminPer;
	private BigDecimal adminCharge;
	
	public Date getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public BigDecimal getDearnessAllowance() {
		return dearnessAllowance;
	}
	public void setDearnessAllowance(BigDecimal dearnessAllowance) {
		this.dearnessAllowance = dearnessAllowance;
	}
	public BigDecimal getDearnessAllowanceEarning() {
		return dearnessAllowanceEarning;
	}
	public void setDearnessAllowanceEarning(BigDecimal dearnessAllowanceEarning) {
		this.dearnessAllowanceEarning = dearnessAllowanceEarning;
	}
	//private Long departmentId;
	//private Long cityId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public BigDecimal getBasic() {
		return basic;
	}
	public void setBasic(BigDecimal basic) {
		this.basic = basic;
	}
	public BigDecimal getConveyanceAllowance() {
		return conveyanceAllowance;
	}
	public void setConveyanceAllowance(BigDecimal conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}
	public BigDecimal getHra() {
		return hra;
	}
	public void setHra(BigDecimal hra) {
		this.hra = hra;
	}
	public BigDecimal getHraEarning() {
		return hraEarning;
	}
	public void setHraEarning(BigDecimal hraEarning) {
		this.hraEarning = hraEarning;
	}
	public BigDecimal getOtherAllowance() {
		return otherAllowance;
	}
	public void setOtherAllowance(BigDecimal otherAllowance) {
		this.otherAllowance = otherAllowance;
	}
	public BigDecimal getAbsense() {
		return absense;
	}
	public void setAbsense(BigDecimal absense) {
		this.absense = absense;
	}
	public BigDecimal getAdvanceBonus() {
		return advanceBonus;
	}
	public void setAdvanceBonus(BigDecimal advanceBonus) {
		this.advanceBonus = advanceBonus;
	}
	public BigDecimal getAdvanceBonusEarning() {
		return advanceBonusEarning;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public void setAdvanceBonusEarning(BigDecimal advanceBonusEarning) {
		this.advanceBonusEarning = advanceBonusEarning;
	}
	public BigDecimal getBasicEarning() {
		return basicEarning;
	}
	public void setBasicEarning(BigDecimal basicEarning) {
		this.basicEarning = basicEarning;
	}
	public BigDecimal getCasualleave() {
		return casualleave;
	}
	public void setCasualleave(BigDecimal casualleave) {
		this.casualleave = casualleave;
	}
	public BigDecimal getCompanyBenefits() {
		return companyBenefits;
	}
	public void setCompanyBenefits(BigDecimal companyBenefits) {
		this.companyBenefits = companyBenefits;
	}
	public BigDecimal getCompanyBenefitsEarning() {
		return companyBenefitsEarning;
	}
	public void setCompanyBenefitsEarning(BigDecimal companyBenefitsEarning) {
		this.companyBenefitsEarning = companyBenefitsEarning;
	}
	public BigDecimal getConveyanceAllowanceEarning() {
		return conveyanceAllowanceEarning;
	}
	public void setConveyanceAllowanceEarning(BigDecimal conveyanceAllowanceEarning) {
		this.conveyanceAllowanceEarning = conveyanceAllowanceEarning;
	}
	public BigDecimal getEmployeeLoansAdvnace() {
		return employeeLoansAdvnace;
	}
	public void setEmployeeLoansAdvnace(BigDecimal employeeLoansAdvnace) {
		this.employeeLoansAdvnace = employeeLoansAdvnace;
	}
	public BigDecimal getEmployeeLoansAdvnaceEarning() {
		return employeeLoansAdvnaceEarning;
	}
	public void setEmployeeLoansAdvnaceEarning(BigDecimal employeeLoansAdvnaceEarning) {
		this.employeeLoansAdvnaceEarning = employeeLoansAdvnaceEarning;
	}
	
	public BigDecimal getEsi_Employer() {
		return esi_Employer;
	}
	public void setEsi_Employer(BigDecimal esi_Employer) {
		this.esi_Employer = esi_Employer;
	}
	public BigDecimal getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}
	public BigDecimal getLoan() {
		return loan;
	}
	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}
	public BigDecimal getMedicalAllowance() {
		return medicalAllowance;
	}
	public void setMedicalAllowance(BigDecimal medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}
	public BigDecimal getMedicalAllowanceEarning() {
		return medicalAllowanceEarning;
	}
	public void setMedicalAllowanceEarning(BigDecimal medicalAllowanceEarning) {
		this.medicalAllowanceEarning = medicalAllowanceEarning;
	}
	public BigDecimal getNetPayableAmount() {
		return netPayableAmount;
	}
	public void setNetPayableAmount(BigDecimal netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}
	public BigDecimal getPaidleave() {
		return paidleave;
	}
	public void setPaidleave(BigDecimal paidleave) {
		this.paidleave = paidleave;
	}
	public BigDecimal getPayDays() {
		return payDays;
	}
	public void setPayDays(BigDecimal payDays) {
		this.payDays = payDays;
	}
	public BigDecimal getPresense() {
		return presense;
	}
	public void setPresense(BigDecimal presense) {
		this.presense = presense;
	}
	public BigDecimal getProvidentFundEmployee() {
		return providentFundEmployee;
	}
	public void setProvidentFundEmployee(BigDecimal providentFundEmployee) {
		this.providentFundEmployee = providentFundEmployee;
	}
	public BigDecimal getProvidentFundEmployer() {
		return providentFundEmployer;
	}
	public void setProvidentFundEmployer(BigDecimal providentFundEmployer) {
		this.providentFundEmployer = providentFundEmployer;
	}
	public BigDecimal getProvidentFundEmployerPension() {
		return providentFundEmployerPension;
	}
	public void setProvidentFundEmployerPension(BigDecimal providentFundEmployerPension) {
		this.providentFundEmployerPension = providentFundEmployerPension;
	}
	public BigDecimal getPt() {
		return pt;
	}
	public void setPt(BigDecimal pt) {
		this.pt = pt;
	}
	public BigDecimal getPublicholidays() {
		return publicholidays;
	}
	public void setPublicholidays(BigDecimal publicholidays) {
		this.publicholidays = publicholidays;
	}
	public BigDecimal getSeekleave() {
		return seekleave;
	}
	public void setSeekleave(BigDecimal seekleave) {
		this.seekleave = seekleave;
	}
	public BigDecimal getSpecialAllowance() {
		return specialAllowance;
	}
	public void setSpecialAllowance(BigDecimal specialAllowance) {
		this.specialAllowance = specialAllowance;
	}
	public BigDecimal getSpecialAllowanceEarning() {
		return specialAllowanceEarning;
	}
	public void setSpecialAllowanceEarning(BigDecimal specialAllowanceEarning) {
		this.specialAllowanceEarning = specialAllowanceEarning;
	}
	public BigDecimal getTds() {
		return tds;
	}
	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}
	public BigDecimal getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(BigDecimal totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public BigDecimal getTotalEarning() {
		return totalEarning;
	}
	public void setTotalEarning(BigDecimal totalEarning) {
		this.totalEarning = totalEarning;
	}
	public BigDecimal getWeekoff() {
		return weekoff;
	}
	public void setWeekoff(BigDecimal weekoff) {
		this.weekoff = weekoff;
	}
	
	/**
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public BigDecimal getOvertime() {
		return overtime;
	}
	public void setOvertime(BigDecimal overtime) {
		this.overtime = overtime;
	}
	public BigDecimal getOtherAllowanceEarning() {
		return otherAllowanceEarning;
	}
	public void setOtherAllowanceEarning(BigDecimal otherAllowanceEarning) {
		this.otherAllowanceEarning = otherAllowanceEarning;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public BigDecimal getEsi_Employee() {
		return esi_Employee;
	}
	public void setEsi_Employee(BigDecimal esi_Employee) {
		this.esi_Employee = esi_Employee;
	}
	
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Date getProvisionDateCreated() {
		return provisionDateCreated;
	}
	public void setProvisionDateCreated(Date provisionDateCreated) {
		this.provisionDateCreated = provisionDateCreated;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public Date getReconciliationDate() {
		return reconciliationDate;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public void setReconciliationDate(Date reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getNomineeRelation() {
		return nomineeRelation;
	}
	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public Date getEpfJoining() {
		return epfJoining;
	}
	public void setEpfJoining(Date epfJoining) {
		this.epfJoining = epfJoining;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public BigDecimal getPensionEarningSalary() {
		return PensionEarningSalary;
	}
	public void setPensionEarningSalary(BigDecimal pensionEarningSalary) {
		PensionEarningSalary = pensionEarningSalary;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEsiNo() {
		return esiNo;
	}
	public void setEsiNo(String esiNo) {
		this.esiNo = esiNo;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
//	public BigDecimal getNetPayableAmountTotal() {
//		return netPayableAmountTotal;
//	}
//	public void setNetPayableAmountTotal(BigDecimal netPayableAmountTotal) {
//		this.netPayableAmountTotal = netPayableAmountTotal;
//	}
	public BigDecimal getOtherDeduction() {
		return otherDeduction;
	}
	public void setOtherDeduction(BigDecimal otherDeduction) {
		this.otherDeduction = otherDeduction;
	}
	public BigDecimal getOtherEarning() {
		return otherEarning;
	}
	public void setOtherEarning(BigDecimal otherEarning) {
		this.otherEarning = otherEarning;
	}
	public Map<String,String> getPayHeadsMap() {
		return payHeadsMap;
	}
	public void setPayHeadsMap(Map<String,String> payHeadsMap) {
		this.payHeadsMap = payHeadsMap;
	}
 
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	 
	 
	public String getTransactionMode() {
		return transactionMode;
	}
	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}
	 
 	
	public Long getDaysInMonth() {
		return daysInMonth;
	}
	public void setDaysInMonth(Long daysInMonth) {
		this.daysInMonth = daysInMonth;
	}
	public Long getPayableDays() {
		return payableDays;
	}
	public void setPayableDays(Long payableDays) {
		this.payableDays = payableDays;
	}
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	public BigDecimal getMonthalyGross() {
		return monthalyGross;
	}
	public void setMonthalyGross(BigDecimal monthalyGross) {
		this.monthalyGross = monthalyGross;
	}
	public BigDecimal getNetPayableAmountTotal() {
		return netPayableAmountTotal;
	}
	public void setNetPayableAmountTotal(BigDecimal netPayableAmountTotal) {
		this.netPayableAmountTotal = netPayableAmountTotal;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(int totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getHeadCount() {
		return headCount;
	}
	public void setHeadCount(Long headCount) {
		this.headCount = headCount;
	}
	public Date getEsicJoining() {
		return esicJoining;
	}
	public void setEsicJoining(Date esicJoining) {
		this.esicJoining = esicJoining;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getEpfEmployee() {
		return epfEmployee;
	}
	public void setEpfEmployee(BigDecimal epfEmployee) {
		this.epfEmployee = epfEmployee;
	}
	public BigDecimal getEpfEmployer() {
		return epfEmployer;
	}
	public void setEpfEmployer(BigDecimal epfEmployer) {
		this.epfEmployer = epfEmployer;
	}
	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}
	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}
	public BigDecimal getAdminPer() {
		return adminPer;
	}
	public void setAdminPer(BigDecimal adminPer) {
		this.adminPer = adminPer;
	}
	
	public BigDecimal getEpfEmployeePension() {
		return epfEmployeePension;
	}
	public void setEpfEmployeePension(BigDecimal epfEmployeePension) {
		this.epfEmployeePension = epfEmployeePension;
	}
	public BigDecimal getLwfEmployeeAmount() {
		return lwfEmployeeAmount;
	}
	public void setLwfEmployeeAmount(BigDecimal lwfEmployeeAmount) {
		this.lwfEmployeeAmount = lwfEmployeeAmount;
	}
	public BigDecimal getLwfEmployerAmount() {
		return lwfEmployerAmount;
	}
	public void setLwfEmployerAmount(BigDecimal lwfEmployerAmount) {
		this.lwfEmployerAmount = lwfEmployerAmount;
	}
	public BigDecimal getAdminCharge() {
		return adminCharge;
	}
	public void setAdminCharge(BigDecimal adminCharge) {
		this.adminCharge = adminCharge;
	}
	
	public BigDecimal getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(BigDecimal totalContribution) {
		this.totalContribution = totalContribution;
	}
	
	
	
	
	
	 
	
 }
