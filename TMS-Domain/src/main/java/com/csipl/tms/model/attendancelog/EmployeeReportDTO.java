package com.csipl.tms.model.attendancelog;

import java.util.Date;

public class EmployeeReportDTO {

	String empName;
	String empCode;
	String empGrade;
	String empDesignation;
	String empDetp;
	String empBirthday;
	String anniversarydate;
	String joiningDate;
	String birthdaydate;
	String workanniversarydate;
	String anniversarydateCount;
	String birthdaydateCount;
	String workanniversarydateCount;
	String empCtc;
	String empBankName;
	String bankPer;
	String title;
	String desc;
	String genStatus;
	String disStatus;
	String amtEmployee;
	String amtEmployer;
	String amtPension;
	String payHeadName;
	String payHeadAmount;
	String uId, uA, mI, bA, eS, aI;
	String empCount;
	String deptCount;
	String holidayCount;
	String TicketNotification;
	String SepartionNotification;
	String notification;
	String countNotification;
	String headcountNotification;
	String countTicketNotification;
	String countSeprationNotification;
	String dateCreated;
	String malePer, femalePer, otherPer;

	String ticketLogged, ticketResolved, ticketPending;

	String processMonth;

	String empAge;
	String empRange;
	String empAttenance;
	String empAttendanceeRange;
	String url;

	private String firstName;
	private String lastName;
	private String employeeCode;
	private String departmentName;
	private String designationName;
	private String dateOfBirth;
	private String dateTo;
	private String dateFrom;
	private String employeeLogoPath;
	private Long day;
	private String fromDateString;
	private String toDateString;
	private String holidayName;
	private Date fromDate;
	private Date toDate;
	private String daysName;
	private String initCanCount;
	private String penCanCount;
	private String empNoticePeriodCount;
	private String empExitThisMonthCount;
	private String empFinalSettlementCount;
	private String totalEmpCount;
	private String deactiveEmpCount;
	private Long teamOnLeaveMonth;
	private Long teamOnLeaveToday;

	private String leaveCount;
	private String seprationCount;
	private String compOffCount;
	private String arrequestCount;
	private String helpCount;
	private Long totalCountNotify;
	private Long employeeAmt;
	private Long employeerAmt;
	private String employeeRemark;
	private String color;
	private Long allLeaveCount;

	public Long getAllLeaveCount() {
		return allLeaveCount;
	}

	public void setAllLeaveCount(Long allLeaveCount) {
		this.allLeaveCount = allLeaveCount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getEmployeeAmt() {
		return employeeAmt;
	}

	public void setEmployeeAmt(Long employeeAmt) {
		this.employeeAmt = employeeAmt;
	}

	public Long getEmployeerAmt() {
		return employeerAmt;
	}

	public void setEmployeerAmt(Long employeerAmt) {
		this.employeerAmt = employeerAmt;
	}

	public Long getTotalCountNotify() {
		return totalCountNotify;
	}

	public void setTotalCountNotify(Long totalCountNotify) {
		this.totalCountNotify = totalCountNotify;
	}

	public Long getTeamOnLeaveMonth() {
		return teamOnLeaveMonth;
	}

	public void setTeamOnLeaveMonth(Long teamOnLeaveMonth) {
		this.teamOnLeaveMonth = teamOnLeaveMonth;
	}

	public Long getTeamOnLeaveToday() {
		return teamOnLeaveToday;
	}

	public void setTeamOnLeaveToday(Long teamOnLeaveToday) {
		this.teamOnLeaveToday = teamOnLeaveToday;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public String getEmpBirthday() {
		return empBirthday;
	}

	public void setEmpBirthday(String empBirthday) {
		this.empBirthday = empBirthday;
	}

	public String getAnniversarydate() {
		return anniversarydate;
	}

	public void setAnniversarydate(String anniversarydate) {
		this.anniversarydate = anniversarydate;
	}

	public String getBirthdaydate() {
		return birthdaydate;
	}

	public void setBirthdaydate(String birthdaydate) {
		this.birthdaydate = birthdaydate;
	}

	public String getWorkanniversarydate() {
		return workanniversarydate;
	}

	public void setWorkanniversarydate(String workanniversarydate) {
		this.workanniversarydate = workanniversarydate;
	}

	public String getAnniversarydateCount() {
		return anniversarydateCount;
	}

	public void setAnniversarydateCount(String anniversarydateCount) {
		this.anniversarydateCount = anniversarydateCount;
	}

	public String getBirthdaydateCount() {
		return birthdaydateCount;
	}

	public void setBirthdaydateCount(String birthdaydateCount) {
		this.birthdaydateCount = birthdaydateCount;
	}

	public String getWorkanniversarydateCount() {
		return workanniversarydateCount;
	}

	public void setWorkanniversarydateCount(String workanniversarydateCount) {
		this.workanniversarydateCount = workanniversarydateCount;
	}

	public String getEmpCtc() {
		return empCtc;
	}

	public void setEmpCtc(String empCtc) {
		this.empCtc = empCtc;
	}

	public String getEmpBankName() {
		return empBankName;
	}

	public void setEmpBankName(String empBankName) {
		this.empBankName = empBankName;
	}

	public String getBankPer() {
		return bankPer;
	}

	public String getEmpAttenance() {
		return empAttenance;
	}

	public void setEmpAttenance(String empAttenance) {
		this.empAttenance = empAttenance;
	}

	public String getEmpAttendanceeRange() {
		return empAttendanceeRange;
	}

	public void setEmpAttendanceeRange(String empAttendanceeRange) {
		this.empAttendanceeRange = empAttendanceeRange;
	}

	public void setBankPer(String bankPer) {
		this.bankPer = bankPer;
	}

	public String getPayHeadName() {
		return payHeadName;
	}

	public void setPayHeadName(String payHeadName) {
		this.payHeadName = payHeadName;
	}

	public String getPayHeadAmount() {
		return payHeadAmount;
	}

	public void setPayHeadAmount(String payHeadAmount) {
		this.payHeadAmount = payHeadAmount;
	}

	public String getAmtEmployee() {
		return amtEmployee;
	}

	public void setAmtEmployee(String amtEmployee) {
		this.amtEmployee = amtEmployee;
	}

	public String getAmtEmployer() {
		return amtEmployer;
	}

	public void setAmtEmployer(String amtEmployer) {
		this.amtEmployer = amtEmployer;
	}

	public String getAmtPension() {
		return amtPension;
	}

	public void setAmtPension(String amtPension) {
		this.amtPension = amtPension;
	}

	public String getEmpCount() {
		return empCount;
	}

	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}

	public String getDeptCount() {
		return deptCount;
	}

	public void setDeptCount(String deptCount) {
		this.deptCount = deptCount;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuA() {
		return uA;
	}

	public void setuA(String uA) {
		this.uA = uA;
	}

	public String getmI() {
		return mI;
	}

	public void setmI(String mI) {
		this.mI = mI;
	}

	public String getbA() {
		return bA;
	}

	public void setbA(String bA) {
		this.bA = bA;
	}

	public String geteS() {
		return eS;
	}

	public void seteS(String eS) {
		this.eS = eS;
	}

	public String getaI() {
		return aI;
	}

	public void setaI(String aI) {
		this.aI = aI;
	}

	public String getEmployeeLogoPath() {
		return employeeLogoPath;
	}

	public void setEmployeeLogoPath(String employeeLogoPath) {
		this.employeeLogoPath = employeeLogoPath;
	}

	public String getEmpDetp() {
		return empDetp;
	}

	public void setEmpDetp(String empDetp) {
		this.empDetp = empDetp;
	}

	public String getCountNotification() {
		return countNotification;
	}

	public void setCountNotification(String countNotification) {
		this.countNotification = countNotification;
	}

	public String getCountTicketNotification() {
		return countTicketNotification;
	}

	public void setCountTicketNotification(String countTicketNotification) {
		this.countTicketNotification = countTicketNotification;
	}

	public String getCountSeprationNotification() {
		return countSeprationNotification;
	}

	public void setCountSeprationNotification(String countSeprationNotification) {
		this.countSeprationNotification = countSeprationNotification;
	}

	public String getTicketNotification() {
		return TicketNotification;
	}

	public void setTicketNotification(String ticketNotification) {
		TicketNotification = ticketNotification;
	}

	public String getSepartionNotification() {
		return SepartionNotification;
	}

	public void setSepartionNotification(String separtionNotification) {
		SepartionNotification = separtionNotification;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getHeadcountNotification() {
		return headcountNotification;
	}

	public void setHeadcountNotification(String headcountNotification) {
		this.headcountNotification = headcountNotification;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMalePer() {
		return malePer;
	}

	public void setMalePer(String malePer) {
		this.malePer = malePer;
	}

	public String getFemalePer() {
		return femalePer;
	}

	public void setFemalePer(String femalePer) {
		this.femalePer = femalePer;
	}

	public String getOtherPer() {
		return otherPer;
	}

	public void setOtherPer(String otherPer) {
		this.otherPer = otherPer;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	public String getEmpAge() {
		return empAge;
	}

	public void setEmpAge(String empAge) {
		this.empAge = empAge;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmpRange() {
		return empRange;
	}

	public void setEmpRange(String empRange) {
		this.empRange = empRange;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getGenStatus() {
		return genStatus;
	}

	public void setGenStatus(String genStatus) {
		this.genStatus = genStatus;
	}

	public String getDisStatus() {
		return disStatus;
	}

	public void setDisStatus(String disStatus) {
		this.disStatus = disStatus;
	}

	public String getTicketLogged() {
		return ticketLogged;
	}

	public void setTicketLogged(String ticketLogged) {
		this.ticketLogged = ticketLogged;
	}

	public String getTicketResolved() {
		return ticketResolved;
	}

	public void setTicketResolved(String ticketResolved) {
		this.ticketResolved = ticketResolved;
	}

	public String getTicketPending() {
		return ticketPending;
	}

	public void setTicketPending(String ticketPending) {
		this.ticketPending = ticketPending;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SepartionNotification == null) ? 0 : SepartionNotification.hashCode());
		result = prime * result + ((TicketNotification == null) ? 0 : TicketNotification.hashCode());
		result = prime * result + ((aI == null) ? 0 : aI.hashCode());
		result = prime * result + ((amtEmployee == null) ? 0 : amtEmployee.hashCode());
		result = prime * result + ((amtEmployer == null) ? 0 : amtEmployer.hashCode());
		result = prime * result + ((amtPension == null) ? 0 : amtPension.hashCode());
		result = prime * result + ((anniversarydate == null) ? 0 : anniversarydate.hashCode());
		result = prime * result + ((anniversarydateCount == null) ? 0 : anniversarydateCount.hashCode());
		result = prime * result + ((bA == null) ? 0 : bA.hashCode());
		result = prime * result + ((bankPer == null) ? 0 : bankPer.hashCode());
		result = prime * result + ((birthdaydate == null) ? 0 : birthdaydate.hashCode());
		result = prime * result + ((birthdaydateCount == null) ? 0 : birthdaydateCount.hashCode());
		result = prime * result + ((countNotification == null) ? 0 : countNotification.hashCode());
		result = prime * result + ((countSeprationNotification == null) ? 0 : countSeprationNotification.hashCode());
		result = prime * result + ((countTicketNotification == null) ? 0 : countTicketNotification.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((deptCount == null) ? 0 : deptCount.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((disStatus == null) ? 0 : disStatus.hashCode());
		result = prime * result + ((eS == null) ? 0 : eS.hashCode());
		result = prime * result + ((empAge == null) ? 0 : empAge.hashCode());
		result = prime * result + ((empBankName == null) ? 0 : empBankName.hashCode());
		result = prime * result + ((empBirthday == null) ? 0 : empBirthday.hashCode());
		result = prime * result + ((empCode == null) ? 0 : empCode.hashCode());
		result = prime * result + ((empCount == null) ? 0 : empCount.hashCode());
		result = prime * result + ((empCtc == null) ? 0 : empCtc.hashCode());
		result = prime * result + ((empDesignation == null) ? 0 : empDesignation.hashCode());
		result = prime * result + ((empDetp == null) ? 0 : empDetp.hashCode());
		result = prime * result + ((empGrade == null) ? 0 : empGrade.hashCode());
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		result = prime * result + ((empRange == null) ? 0 : empRange.hashCode());
		result = prime * result + ((employeeLogoPath == null) ? 0 : employeeLogoPath.hashCode());
		result = prime * result + ((femalePer == null) ? 0 : femalePer.hashCode());
		result = prime * result + ((genStatus == null) ? 0 : genStatus.hashCode());
		result = prime * result + ((headcountNotification == null) ? 0 : headcountNotification.hashCode());
		result = prime * result + ((mI == null) ? 0 : mI.hashCode());
		result = prime * result + ((malePer == null) ? 0 : malePer.hashCode());
		result = prime * result + ((notification == null) ? 0 : notification.hashCode());
		result = prime * result + ((otherPer == null) ? 0 : otherPer.hashCode());
		result = prime * result + ((payHeadAmount == null) ? 0 : payHeadAmount.hashCode());
		result = prime * result + ((payHeadName == null) ? 0 : payHeadName.hashCode());
		result = prime * result + ((processMonth == null) ? 0 : processMonth.hashCode());
		result = prime * result + ((ticketLogged == null) ? 0 : ticketLogged.hashCode());
		result = prime * result + ((ticketPending == null) ? 0 : ticketPending.hashCode());
		result = prime * result + ((ticketResolved == null) ? 0 : ticketResolved.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((uA == null) ? 0 : uA.hashCode());
		result = prime * result + ((uId == null) ? 0 : uId.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((workanniversarydate == null) ? 0 : workanniversarydate.hashCode());
		result = prime * result + ((workanniversarydateCount == null) ? 0 : workanniversarydateCount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeReportDTO other = (EmployeeReportDTO) obj;
		if (SepartionNotification == null) {
			if (other.SepartionNotification != null)
				return false;
		} else if (!SepartionNotification.equals(other.SepartionNotification))
			return false;
		if (TicketNotification == null) {
			if (other.TicketNotification != null)
				return false;
		} else if (!TicketNotification.equals(other.TicketNotification))
			return false;
		if (aI == null) {
			if (other.aI != null)
				return false;
		} else if (!aI.equals(other.aI))
			return false;
		if (amtEmployee == null) {
			if (other.amtEmployee != null)
				return false;
		} else if (!amtEmployee.equals(other.amtEmployee))
			return false;
		if (amtEmployer == null) {
			if (other.amtEmployer != null)
				return false;
		} else if (!amtEmployer.equals(other.amtEmployer))
			return false;
		if (amtPension == null) {
			if (other.amtPension != null)
				return false;
		} else if (!amtPension.equals(other.amtPension))
			return false;
		if (anniversarydate == null) {
			if (other.anniversarydate != null)
				return false;
		} else if (!anniversarydate.equals(other.anniversarydate))
			return false;
		if (anniversarydateCount == null) {
			if (other.anniversarydateCount != null)
				return false;
		} else if (!anniversarydateCount.equals(other.anniversarydateCount))
			return false;
		if (bA == null) {
			if (other.bA != null)
				return false;
		} else if (!bA.equals(other.bA))
			return false;
		if (bankPer == null) {
			if (other.bankPer != null)
				return false;
		} else if (!bankPer.equals(other.bankPer))
			return false;
		if (birthdaydate == null) {
			if (other.birthdaydate != null)
				return false;
		} else if (!birthdaydate.equals(other.birthdaydate))
			return false;
		if (birthdaydateCount == null) {
			if (other.birthdaydateCount != null)
				return false;
		} else if (!birthdaydateCount.equals(other.birthdaydateCount))
			return false;
		if (countNotification == null) {
			if (other.countNotification != null)
				return false;
		} else if (!countNotification.equals(other.countNotification))
			return false;
		if (countSeprationNotification == null) {
			if (other.countSeprationNotification != null)
				return false;
		} else if (!countSeprationNotification.equals(other.countSeprationNotification))
			return false;
		if (countTicketNotification == null) {
			if (other.countTicketNotification != null)
				return false;
		} else if (!countTicketNotification.equals(other.countTicketNotification))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (deptCount == null) {
			if (other.deptCount != null)
				return false;
		} else if (!deptCount.equals(other.deptCount))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (disStatus == null) {
			if (other.disStatus != null)
				return false;
		} else if (!disStatus.equals(other.disStatus))
			return false;
		if (eS == null) {
			if (other.eS != null)
				return false;
		} else if (!eS.equals(other.eS))
			return false;
		if (empAge == null) {
			if (other.empAge != null)
				return false;
		} else if (!empAge.equals(other.empAge))
			return false;
		if (empBankName == null) {
			if (other.empBankName != null)
				return false;
		} else if (!empBankName.equals(other.empBankName))
			return false;
		if (empBirthday == null) {
			if (other.empBirthday != null)
				return false;
		} else if (!empBirthday.equals(other.empBirthday))
			return false;
		if (empCode == null) {
			if (other.empCode != null)
				return false;
		} else if (!empCode.equals(other.empCode))
			return false;
		if (empCount == null) {
			if (other.empCount != null)
				return false;
		} else if (!empCount.equals(other.empCount))
			return false;
		if (empCtc == null) {
			if (other.empCtc != null)
				return false;
		} else if (!empCtc.equals(other.empCtc))
			return false;
		if (empDesignation == null) {
			if (other.empDesignation != null)
				return false;
		} else if (!empDesignation.equals(other.empDesignation))
			return false;
		if (empDetp == null) {
			if (other.empDetp != null)
				return false;
		} else if (!empDetp.equals(other.empDetp))
			return false;
		if (empGrade == null) {
			if (other.empGrade != null)
				return false;
		} else if (!empGrade.equals(other.empGrade))
			return false;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;
		if (empRange == null) {
			if (other.empRange != null)
				return false;
		} else if (!empRange.equals(other.empRange))
			return false;
		if (employeeLogoPath == null) {
			if (other.employeeLogoPath != null)
				return false;
		} else if (!employeeLogoPath.equals(other.employeeLogoPath))
			return false;
		if (femalePer == null) {
			if (other.femalePer != null)
				return false;
		} else if (!femalePer.equals(other.femalePer))
			return false;
		if (genStatus == null) {
			if (other.genStatus != null)
				return false;
		} else if (!genStatus.equals(other.genStatus))
			return false;
		if (headcountNotification == null) {
			if (other.headcountNotification != null)
				return false;
		} else if (!headcountNotification.equals(other.headcountNotification))
			return false;
		if (mI == null) {
			if (other.mI != null)
				return false;
		} else if (!mI.equals(other.mI))
			return false;
		if (malePer == null) {
			if (other.malePer != null)
				return false;
		} else if (!malePer.equals(other.malePer))
			return false;
		if (notification == null) {
			if (other.notification != null)
				return false;
		} else if (!notification.equals(other.notification))
			return false;
		if (otherPer == null) {
			if (other.otherPer != null)
				return false;
		} else if (!otherPer.equals(other.otherPer))
			return false;
		if (payHeadAmount == null) {
			if (other.payHeadAmount != null)
				return false;
		} else if (!payHeadAmount.equals(other.payHeadAmount))
			return false;
		if (payHeadName == null) {
			if (other.payHeadName != null)
				return false;
		} else if (!payHeadName.equals(other.payHeadName))
			return false;
		if (processMonth == null) {
			if (other.processMonth != null)
				return false;
		} else if (!processMonth.equals(other.processMonth))
			return false;
		if (ticketLogged == null) {
			if (other.ticketLogged != null)
				return false;
		} else if (!ticketLogged.equals(other.ticketLogged))
			return false;
		if (ticketPending == null) {
			if (other.ticketPending != null)
				return false;
		} else if (!ticketPending.equals(other.ticketPending))
			return false;
		if (ticketResolved == null) {
			if (other.ticketResolved != null)
				return false;
		} else if (!ticketResolved.equals(other.ticketResolved))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (uA == null) {
			if (other.uA != null)
				return false;
		} else if (!uA.equals(other.uA))
			return false;
		if (uId == null) {
			if (other.uId != null)
				return false;
		} else if (!uId.equals(other.uId))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (workanniversarydate == null) {
			if (other.workanniversarydate != null)
				return false;
		} else if (!workanniversarydate.equals(other.workanniversarydate))
			return false;
		if (workanniversarydateCount == null) {
			if (other.workanniversarydateCount != null)
				return false;
		} else if (!workanniversarydateCount.equals(other.workanniversarydateCount))
			return false;
		return true;
	}

	public String getHolidayCount() {
		return holidayCount;
	}

	public void setHolidayCount(String holidayCount) {
		this.holidayCount = holidayCount;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getFromDateString() {
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	public String getToDateString() {
		return toDateString;
	}

	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDaysName() {
		return daysName;
	}

	public void setDaysName(String daysName) {
		this.daysName = daysName;
	}

	public String getInitCanCount() {
		return initCanCount;
	}

	public void setInitCanCount(String initCanCount) {
		this.initCanCount = initCanCount;
	}

	public String getPenCanCount() {
		return penCanCount;
	}

	public void setPenCanCount(String penCanCount) {
		this.penCanCount = penCanCount;
	}

	public String getEmpNoticePeriodCount() {
		return empNoticePeriodCount;
	}

	public void setEmpNoticePeriodCount(String empNoticePeriodCount) {
		this.empNoticePeriodCount = empNoticePeriodCount;
	}

	public String getEmpExitThisMonthCount() {
		return empExitThisMonthCount;
	}

	public void setEmpExitThisMonthCount(String empExitThisMonthCount) {
		this.empExitThisMonthCount = empExitThisMonthCount;
	}

	public String getEmpFinalSettlementCount() {
		return empFinalSettlementCount;
	}

	public void setEmpFinalSettlementCount(String empFinalSettlementCount) {
		this.empFinalSettlementCount = empFinalSettlementCount;
	}

	public String getTotalEmpCount() {
		return totalEmpCount;
	}

	public void setTotalEmpCount(String totalEmpCount) {
		this.totalEmpCount = totalEmpCount;
	}

	public String getDeactiveEmpCount() {
		return deactiveEmpCount;
	}

	public void setDeactiveEmpCount(String deactiveEmpCount) {
		this.deactiveEmpCount = deactiveEmpCount;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}

	public String getSeprationCount() {
		return seprationCount;
	}

	public void setSeprationCount(String seprationCount) {
		this.seprationCount = seprationCount;
	}

	public String getCompOffCount() {
		return compOffCount;
	}

	public void setCompOffCount(String compOffCount) {
		this.compOffCount = compOffCount;
	}

	public String getArrequestCount() {
		return arrequestCount;
	}

	public void setArrequestCount(String arrequestCount) {
		this.arrequestCount = arrequestCount;
	}

	public String getHelpCount() {
		return helpCount;
	}

	public void setHelpCount(String helpCount) {
		this.helpCount = helpCount;
	}

	/**
	 * @return the employeeRemark
	 */
	public String getEmployeeRemark() {
		return employeeRemark;
	}

	/**
	 * @param employeeRemark the employeeRemark to set
	 */
	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}
}
