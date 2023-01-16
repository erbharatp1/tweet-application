package com.csipl.hrms.model.common;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long companyId;

	private String activeStatus;

	private String allowModi;

	private String authorizedPerson;

	private String companyAbbreviation;

	@Lob
	private byte[] companyLogo;

	private String companyLogoPath;

	private String companyName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String domainName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	private String emailId;

	private String epfNo;

	private String esicNo;

	private String gstNo;

	private String gumastaNo;

	@Column(name="ImportExportCode")
	private String importExportCode;

	private String mobile;

	private String nagarnigamNo;

	private String panNo;

	private String registrationNo;

	private Long retirementAge;

	private String tanNo;

	private String typeOfIndustry;

	private Long userId;

	private Long userIdUpdate;

	private String website;
	
	@Transient
	private String prefix;
	@Transient
   	private BigDecimal series;
/*
	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="company")
	private List<Attendance> attendances;

	//bi-directional many-to-one association to AttendanceLog
	@OneToMany(mappedBy="company")
	private List<AttendanceLog> attendanceLogs;

	//bi-directional many-to-one association to Bank
	@OneToMany(mappedBy="company")
	private List<Bank> banks;*/

	//bi-directional many-to-one association to Branch
	@OneToMany(mappedBy="company")
	private List<Branch> branches;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public BigDecimal getSeries() {
		return series;
	}

	public void setSeries(BigDecimal series) {
		this.series = series;
	}

	//bi-directional many-to-one association to Address
	@ManyToOne( cascade = CascadeType.ALL)
	@JoinColumn(name="registeredOfficeAddressId")
	private Address address1;

	private Long corporateOfficeAddressId;


	//bi-directional many-to-one association to Groupg
	@ManyToOne
	@JoinColumn(name="groupId")
	private Groupg groupg;

	/*//bi-directional many-to-one association to Department
	@OneToMany(mappedBy="company")
	private List<Department> departments;

	//bi-directional many-to-one association to DrowpdownHd
	@OneToMany(mappedBy="company")
	private List<DrowpdownHd> drowpdownHds;

	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="company")
	private List<Employee> employees;

	//bi-directional many-to-one association to Epf
	@OneToMany(mappedBy="company")
	private List<Epf> epfs;

	//bi-directional many-to-one association to Esi
	@OneToMany(mappedBy="company")
	private List<Esi> esis;

	//bi-directional many-to-one association to FinancialYear
	@OneToMany(mappedBy="company")
	private List<FinancialYear> financialYears;

	//bi-directional many-to-one association to Grade
	@OneToMany(mappedBy="company")
	private List<Grade> grades;

	//bi-directional many-to-one association to Gratuaty
	@OneToMany(mappedBy="company")
	private List<Gratuaty> gratuaties;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="company")
	private List<Item> items;

	//bi-directional many-to-one association to LoanIssue
	@OneToMany(mappedBy="company")
	private List<LoanIssue> loanIssues;

	//bi-directional many-to-one association to MandatoryInfo
	@OneToMany(mappedBy="company")
	private List<MandatoryInfo> mandatoryInfos;

	//bi-directional many-to-one association to MasterBook
	@OneToMany(mappedBy="company")
	private List<MasterBook> masterBooks;

	//bi-directional many-to-one association to MasterBookType
	@OneToMany(mappedBy="company")
	private List<MasterBookType> masterBookTypes;

	//bi-directional many-to-one association to OneTimeEarningDeduction
	@OneToMany(mappedBy="company")
	private List<OneTimeEarningDeduction> oneTimeEarningDeductions;

	//bi-directional many-to-one association to Overtime
	@OneToMany(mappedBy="company")
	private List<Overtime> overtimes;

	//bi-directional many-to-one association to PayHead
	@OneToMany(mappedBy="company")
	private List<PayHead> payHeads;

	//bi-directional many-to-one association to PayRollLock
	@OneToMany(mappedBy="company")
	private List<PayRollLock> payRollLocks;

	//bi-directional many-to-one association to TdsGroup
	@OneToMany(mappedBy="company")
	private List<TdsGroup> tdsGroups;

	//bi-directional many-to-one association to TdsSlabHd
	@OneToMany(mappedBy="company")
	private List<TdsSlabHd> tdsSlabHds;

	//bi-directional many-to-one association to TicketRaisingHD
	@OneToMany(mappedBy="company")
	private List<TicketRaisingHD> ticketRaisingHds;

	//bi-directional many-to-one association to TicketType
	@OneToMany(mappedBy="company")
	private List<TicketType> ticketTypes;*/

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="company")
	private List<User> users;

	public Company() {
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAllowModi() {
		return this.allowModi;
	}

	public void setAllowModi(String allowModi) {
		this.allowModi = allowModi;
	}

	public String getAuthorizedPerson() {
		return this.authorizedPerson;
	}

	public void setAuthorizedPerson(String authorizedPerson) {
		this.authorizedPerson = authorizedPerson;
	}

	public String getCompanyAbbreviation() {
		return this.companyAbbreviation;
	}

	public void setCompanyAbbreviation(String companyAbbreviation) {
		this.companyAbbreviation = companyAbbreviation;
	}

	public byte[] getCompanyLogo() {
		return this.companyLogo;
	}

	public void setCompanyLogo(byte[] companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyLogoPath() {
		return this.companyLogoPath;
	}

	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEpfNo() {
		return this.epfNo;
	}

	public void setEpfNo(String epfNo) {
		this.epfNo = epfNo;
	}

	public String getEsicNo() {
		return this.esicNo;
	}

	public void setEsicNo(String esicNo) {
		this.esicNo = esicNo;
	}

	public String getGstNo() {
		return this.gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getGumastaNo() {
		return this.gumastaNo;
	}

	public void setGumastaNo(String gumastaNo) {
		this.gumastaNo = gumastaNo;
	}

	public String getImportExportCode() {
		return this.importExportCode;
	}

	public void setImportExportCode(String importExportCode) {
		this.importExportCode = importExportCode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNagarnigamNo() {
		return this.nagarnigamNo;
	}

	public void setNagarnigamNo(String nagarnigamNo) {
		this.nagarnigamNo = nagarnigamNo;
	}

	public String getPanNo() {
		return this.panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getRegistrationNo() {
		return this.registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Long getRetirementAge() {
		return this.retirementAge;
	}

	public void setRetirementAge(Long retirementAge) {
		this.retirementAge = retirementAge;
	}

	public String getTanNo() {
		return this.tanNo;
	}

	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}

	public String getTypeOfIndustry() {
		return this.typeOfIndustry;
	}

	public void setTypeOfIndustry(String typeOfIndustry) {
		this.typeOfIndustry = typeOfIndustry;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return this.userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/*public List<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Attendance addAttendance(Attendance attendance) {
		getAttendances().add(attendance);
		attendance.setCompany(this);

		return attendance;
	}

	public Attendance removeAttendance(Attendance attendance) {
		getAttendances().remove(attendance);
		attendance.setCompany(null);

		return attendance;
	}

	public List<AttendanceLog> getAttendanceLogs() {
		return this.attendanceLogs;
	}

	public void setAttendanceLogs(List<AttendanceLog> attendanceLogs) {
		this.attendanceLogs = attendanceLogs;
	}

	public AttendanceLog addAttendanceLog(AttendanceLog attendanceLog) {
		getAttendanceLogs().add(attendanceLog);
		attendanceLog.setCompany(this);

		return attendanceLog;
	}

	public AttendanceLog removeAttendanceLog(AttendanceLog attendanceLog) {
		getAttendanceLogs().remove(attendanceLog);
		attendanceLog.setCompany(null);

		return attendanceLog;
	}

	public List<Bank> getBanks() {
		return this.banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public Bank addBank(Bank bank) {
		getBanks().add(bank);
		bank.setCompany(this);

		return bank;
	}

	public Bank removeBank(Bank bank) {
		getBanks().remove(bank);
		bank.setCompany(null);

		return bank;
	}*/

	public List<Branch> getBranches() {
		return this.branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public Branch addBranch(Branch branch) {
		getBranches().add(branch);
		branch.setCompany(this);

		return branch;
	}

	public Branch removeBranch(Branch branch) {
		getBranches().remove(branch);
		branch.setCompany(null);

		return branch;
	}

	public Address getAddress1() {
		return this.address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

 
	public Groupg getGroupg() {
		return this.groupg;
	}

	public void setGroupg(Groupg groupg) {
		this.groupg = groupg;
	}
/*
	public List<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department addDepartment(Department department) {
		getDepartments().add(department);
		department.setCompany(this);

		return department;
	}

	public Department removeDepartment(Department department) {
		getDepartments().remove(department);
		department.setCompany(null);

		return department;
	}

	public List<DrowpdownHd> getDrowpdownHds() {
		return this.drowpdownHds;
	}

	public void setDrowpdownHds(List<DrowpdownHd> drowpdownHds) {
		this.drowpdownHds = drowpdownHds;
	}

	public DrowpdownHd addDrowpdownHd(DrowpdownHd drowpdownHd) {
		getDrowpdownHds().add(drowpdownHd);
		drowpdownHd.setCompany(this);

		return drowpdownHd;
	}

	public DrowpdownHd removeDrowpdownHd(DrowpdownHd drowpdownHd) {
		getDrowpdownHds().remove(drowpdownHd);
		drowpdownHd.setCompany(null);

		return drowpdownHd;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setCompany(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setCompany(null);

		return employee;
	}

	public List<Epf> getEpfs() {
		return this.epfs;
	}

	public void setEpfs(List<Epf> epfs) {
		this.epfs = epfs;
	}

	public Epf addEpf(Epf epf) {
		getEpfs().add(epf);
		epf.setCompany(this);

		return epf;
	}

	public Epf removeEpf(Epf epf) {
		getEpfs().remove(epf);
		epf.setCompany(null);

		return epf;
	}

	public List<Esi> getEsis() {
		return this.esis;
	}

	public void setEsis(List<Esi> esis) {
		this.esis = esis;
	}

	public Esi addEsi(Esi esi) {
		getEsis().add(esi);
		esi.setCompany(this);

		return esi;
	}

	public Esi removeEsi(Esi esi) {
		getEsis().remove(esi);
		esi.setCompany(null);

		return esi;
	}

	public List<FinancialYear> getFinancialYears() {
		return this.financialYears;
	}

	public void setFinancialYears(List<FinancialYear> financialYears) {
		this.financialYears = financialYears;
	}

	public FinancialYear addFinancialYear(FinancialYear financialYear) {
		getFinancialYears().add(financialYear);
		financialYear.setCompany(this);

		return financialYear;
	}

	public FinancialYear removeFinancialYear(FinancialYear financialYear) {
		getFinancialYears().remove(financialYear);
		financialYear.setCompany(null);

		return financialYear;
	}

	public List<Grade> getGrades() {
		return this.grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	public Grade addGrade(Grade grade) {
		getGrades().add(grade);
		grade.setCompany(this);

		return grade;
	}

	public Grade removeGrade(Grade grade) {
		getGrades().remove(grade);
		grade.setCompany(null);

		return grade;
	}

	public List<Gratuaty> getGratuaties() {
		return this.gratuaties;
	}

	public void setGratuaties(List<Gratuaty> gratuaties) {
		this.gratuaties = gratuaties;
	}

	public Gratuaty addGratuaty(Gratuaty gratuaty) {
		getGratuaties().add(gratuaty);
		gratuaty.setCompany(this);

		return gratuaty;
	}

	public Gratuaty removeGratuaty(Gratuaty gratuaty) {
		getGratuaties().remove(gratuaty);
		gratuaty.setCompany(null);

		return gratuaty;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setCompany(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setCompany(null);

		return item;
	}

	public List<LoanIssue> getLoanIssues() {
		return this.loanIssues;
	}

	public void setLoanIssues(List<LoanIssue> loanIssues) {
		this.loanIssues = loanIssues;
	}

	public LoanIssue addLoanIssue(LoanIssue loanIssue) {
		getLoanIssues().add(loanIssue);
		loanIssue.setCompany(this);

		return loanIssue;
	}

	public LoanIssue removeLoanIssue(LoanIssue loanIssue) {
		getLoanIssues().remove(loanIssue);
		loanIssue.setCompany(null);

		return loanIssue;
	}

	public List<MandatoryInfo> getMandatoryInfos() {
		return this.mandatoryInfos;
	}

	public void setMandatoryInfos(List<MandatoryInfo> mandatoryInfos) {
		this.mandatoryInfos = mandatoryInfos;
	}

	public MandatoryInfo addMandatoryInfo(MandatoryInfo mandatoryInfo) {
		getMandatoryInfos().add(mandatoryInfo);
		mandatoryInfo.setCompany(this);

		return mandatoryInfo;
	}

	public MandatoryInfo removeMandatoryInfo(MandatoryInfo mandatoryInfo) {
		getMandatoryInfos().remove(mandatoryInfo);
		mandatoryInfo.setCompany(null);

		return mandatoryInfo;
	}

	public List<MasterBook> getMasterBooks() {
		return this.masterBooks;
	}

	public void setMasterBooks(List<MasterBook> masterBooks) {
		this.masterBooks = masterBooks;
	}

	public MasterBook addMasterBook(MasterBook masterBook) {
		getMasterBooks().add(masterBook);
		masterBook.setCompany(this);

		return masterBook;
	}

	public MasterBook removeMasterBook(MasterBook masterBook) {
		getMasterBooks().remove(masterBook);
		masterBook.setCompany(null);

		return masterBook;
	}

	public List<MasterBookType> getMasterBookTypes() {
		return this.masterBookTypes;
	}

	public void setMasterBookTypes(List<MasterBookType> masterBookTypes) {
		this.masterBookTypes = masterBookTypes;
	}

	public MasterBookType addMasterBookType(MasterBookType masterBookType) {
		getMasterBookTypes().add(masterBookType);
		masterBookType.setCompany(this);

		return masterBookType;
	}

	public MasterBookType removeMasterBookType(MasterBookType masterBookType) {
		getMasterBookTypes().remove(masterBookType);
		masterBookType.setCompany(null);

		return masterBookType;
	}

	public List<OneTimeEarningDeduction> getOneTimeEarningDeductions() {
		return this.oneTimeEarningDeductions;
	}

	public void setOneTimeEarningDeductions(List<OneTimeEarningDeduction> oneTimeEarningDeductions) {
		this.oneTimeEarningDeductions = oneTimeEarningDeductions;
	}

	public OneTimeEarningDeduction addOneTimeEarningDeduction(OneTimeEarningDeduction oneTimeEarningDeduction) {
		getOneTimeEarningDeductions().add(oneTimeEarningDeduction);
		oneTimeEarningDeduction.setCompany(this);

		return oneTimeEarningDeduction;
	}

	public OneTimeEarningDeduction removeOneTimeEarningDeduction(OneTimeEarningDeduction oneTimeEarningDeduction) {
		getOneTimeEarningDeductions().remove(oneTimeEarningDeduction);
		oneTimeEarningDeduction.setCompany(null);

		return oneTimeEarningDeduction;
	}

	public List<Overtime> getOvertimes() {
		return this.overtimes;
	}

	public void setOvertimes(List<Overtime> overtimes) {
		this.overtimes = overtimes;
	}

	public Overtime addOvertime(Overtime overtime) {
		getOvertimes().add(overtime);
		overtime.setCompany(this);

		return overtime;
	}

	public Overtime removeOvertime(Overtime overtime) {
		getOvertimes().remove(overtime);
		overtime.setCompany(null);

		return overtime;
	}

	public List<PayHead> getPayHeads() {
		return this.payHeads;
	}

	public void setPayHeads(List<PayHead> payHeads) {
		this.payHeads = payHeads;
	}

	public PayHead addPayHead(PayHead payHead) {
		getPayHeads().add(payHead);
		payHead.setCompany(this);

		return payHead;
	}

	public PayHead removePayHead(PayHead payHead) {
		getPayHeads().remove(payHead);
		payHead.setCompany(null);

		return payHead;
	}

	public List<PayRollLock> getPayRollLocks() {
		return this.payRollLocks;
	}

	public void setPayRollLocks(List<PayRollLock> payRollLocks) {
		this.payRollLocks = payRollLocks;
	}

	public PayRollLock addPayRollLock(PayRollLock payRollLock) {
		getPayRollLocks().add(payRollLock);
		payRollLock.setCompany(this);

		return payRollLock;
	}

	public PayRollLock removePayRollLock(PayRollLock payRollLock) {
		getPayRollLocks().remove(payRollLock);
		payRollLock.setCompany(null);

		return payRollLock;
	}

	public List<TdsGroup> getTdsGroups() {
		return this.tdsGroups;
	}

	public void setTdsGroups(List<TdsGroup> tdsGroups) {
		this.tdsGroups = tdsGroups;
	}

	public TdsGroup addTdsGroup(TdsGroup tdsGroup) {
		getTdsGroups().add(tdsGroup);
		tdsGroup.setCompany(this);

		return tdsGroup;
	}

	public TdsGroup removeTdsGroup(TdsGroup tdsGroup) {
		getTdsGroups().remove(tdsGroup);
		tdsGroup.setCompany(null);

		return tdsGroup;
	}

	public List<TdsSlabHd> getTdsSlabHds() {
		return this.tdsSlabHds;
	}

	public void setTdsSlabHds(List<TdsSlabHd> tdsSlabHds) {
		this.tdsSlabHds = tdsSlabHds;
	}

	public TdsSlabHd addTdsSlabHd(TdsSlabHd tdsSlabHd) {
		getTdsSlabHds().add(tdsSlabHd);
		tdsSlabHd.setCompany(this);

		return tdsSlabHd;
	}

	public TdsSlabHd removeTdsSlabHd(TdsSlabHd tdsSlabHd) {
		getTdsSlabHds().remove(tdsSlabHd);
		tdsSlabHd.setCompany(null);

		return tdsSlabHd;
	}

	public List<TicketRaisingHD> getTicketRaisingHds() {
		return this.ticketRaisingHds;
	}

	public void setTicketRaisingHds(List<TicketRaisingHD> ticketRaisingHds) {
		this.ticketRaisingHds = ticketRaisingHds;
	}

	public TicketRaisingHD addTicketRaisingHd(TicketRaisingHD ticketRaisingHd) {
		getTicketRaisingHds().add(ticketRaisingHd);
		ticketRaisingHd.setCompany(this);

		return ticketRaisingHd;
	}

	public TicketRaisingHD removeTicketRaisingHd(TicketRaisingHD ticketRaisingHd) {
		getTicketRaisingHds().remove(ticketRaisingHd);
		ticketRaisingHd.setCompany(null);

		return ticketRaisingHd;
	}

	public List<TicketType> getTicketTypes() {
		return this.ticketTypes;
	}

	public void setTicketTypes(List<TicketType> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}

	public TicketType addTicketType(TicketType ticketType) {
		getTicketTypes().add(ticketType);
		ticketType.setCompany(this);

		return ticketType;
	}

	public TicketType removeTicketType(TicketType ticketType) {
		getTicketTypes().remove(ticketType);
		ticketType.setCompany(null);

		return ticketType;
	}*/

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setCompany(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setCompany(null);

		return user;
	}

	public Long getCorporateOfficeAddressId() {
		return corporateOfficeAddressId;
	}

	public void setCorporateOfficeAddressId(Long corporateOfficeAddressId) {
		this.corporateOfficeAddressId = corporateOfficeAddressId;
	}

}