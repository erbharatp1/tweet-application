package com.csipl.hrms.service.payroll;

import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AttendanceDTO {

	private String employeeCode;
	private String employeeName;
	private int presense;
	private int weekOff;
	private int publicholidays;
	private String processMonth;	
	private int paidLeave;
	private int casualLeave;
	private int seekleave;
	private int absense;
	private Long companyId;
	private Long userId;
	private Long employeeId;
	private Long departmentId;
	private Long payDays;
	private String designation;
	private String department;
	private String jobLocation;
	private String reportingTo;
	private Long carryforwardLimit;
	private String status;
	
	private BigDecimal carryForward;
	private BigDecimal consumed;
	private BigDecimal balance;
	private BigDecimal annualQuota;
	private BigDecimal carryForwardTotal;
	private BigDecimal consumedTotal;
	private BigDecimal balanceTotal;
	private BigDecimal annualQuotaTotal;
    private BigDecimal leaveConsumed;
    private BigDecimal consumedLeaveTotal;
    private BigDecimal closingBalanceLeave;
	private BigDecimal leaveEncashed;
	private BigDecimal leaveBalance;
	
	private BigDecimal closingBalanceLeaveTotal;
	private BigDecimal leaveEncashedTotal;
	private BigDecimal days;
	private BigDecimal leaveExpiryBalance;
	private BigDecimal leaveExpiry;
	private BigDecimal consumeLeaveExpiryBalance;
	private BigDecimal leaveExpiryFinalData;
	private BigDecimal leaveClosingBalance;
	private BigDecimal leaveEncashData;
	
	public BigDecimal getLeaveEncashData() {
		return leaveEncashData;
	}

	public void setLeaveEncashData(BigDecimal leaveEncashData) {
		this.leaveEncashData = leaveEncashData;
	}

	public BigDecimal getLeaveClosingBalance() {
		return leaveClosingBalance;
	}

	public void setLeaveClosingBalance(BigDecimal leaveClosingBalance) {
		this.leaveClosingBalance = leaveClosingBalance;
	}

	public BigDecimal getLeaveExpiryFinalData() {
		return leaveExpiryFinalData;
	}

	public void setLeaveExpiryFinalData(BigDecimal leaveExpiryFinalData) {
		this.leaveExpiryFinalData = leaveExpiryFinalData;
	}

	private Long indexDays;
	private String leaveMode;
	private String nature;
	private Date fromDate;
	private Date toDate;
	private Date dateCreated;
	private Long encashLimit;
	private BigDecimal openingLeave;
	private Long yearlyLimit;
	private Long leaveId;
	private Date dateOfJoining;
	private String leaveScheme;
	
	
	Map<String, List<BigDecimal>> leaveTypeMap;
	Map<String, List<BigDecimal>> leaveEntitlementTypeMap;
	Map<String, List<BigDecimal>> leaveEncashMap;
	Map<String, List<BigDecimal>> leaveClosedBalanceMap;
	Map<String, List<BigDecimal>> leaveExpiryMap;
	
	public Long getCarryforwardLimit() {
		return carryforwardLimit;
	}

	public Map<String, List<BigDecimal>> getLeaveExpiryMap() {
		return leaveExpiryMap;
	}

	public void setLeaveExpiryMap(Map<String, List<BigDecimal>> leaveExpiryMap) {
		this.leaveExpiryMap = leaveExpiryMap;
	}

	public void setCarryforwardLimit(Long carryforwardLimit) {
		this.carryforwardLimit = carryforwardLimit;
	}

	public BigDecimal getDays() {
		return days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
	}

	public BigDecimal getLeaveExpiryBalance() {
		return leaveExpiryBalance;
	}

	public void setLeaveExpiryBalance(BigDecimal leaveExpiryBalance) {
		this.leaveExpiryBalance = leaveExpiryBalance;
	}

	
	public BigDecimal getLeaveExpiry() {
		return leaveExpiry;
	}

	public void setLeaveExpiry(BigDecimal leaveExpiry) {
		this.leaveExpiry = leaveExpiry;
	}

	public BigDecimal getConsumeLeaveExpiryBalance() {
		return consumeLeaveExpiryBalance;
	}

	public void setConsumeLeaveExpiryBalance(BigDecimal consumeLeaveExpiryBalance) {
		this.consumeLeaveExpiryBalance = consumeLeaveExpiryBalance;
	}

	public BigDecimal getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(BigDecimal leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	
	public Long getYearlyLimit() {
		return yearlyLimit;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getLeaveScheme() {
		return leaveScheme;
	}

	public void setLeaveScheme(String leaveScheme) {
		this.leaveScheme = leaveScheme;
	}

	public void setYearlyLimit(Long yearlyLimit) {
		this.yearlyLimit = yearlyLimit;
	}

	public Long getIndexDays() {
		return indexDays;
	}

	public void setIndexDays(Long indexDays) {
		this.indexDays = indexDays;
	}

	public String getLeaveMode() {
		return leaveMode;
	}

	public void setLeaveMode(String leaveMode) {
		this.leaveMode = leaveMode;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getEncashLimit() {
		return encashLimit;
	}

	public void setEncashLimit(Long encashLimit) {
		this.encashLimit = encashLimit;
	}

	public BigDecimal getOpeningLeave() {
		return openingLeave;
	}

	public void setOpeningLeave(BigDecimal openingLeave) {
		this.openingLeave = openingLeave;
	}

	
	
	public BigDecimal getClosingBalanceLeave() {
		return closingBalanceLeave;
	}

	public void setClosingBalanceLeave(BigDecimal closingBalanceLeave) {
		this.closingBalanceLeave = closingBalanceLeave;
	}

	public BigDecimal getLeaveEncashed() {
		return leaveEncashed;
	}

	public void setLeaveEncashed(BigDecimal leaveEncashed) {
		this.leaveEncashed = leaveEncashed;
	}

	public BigDecimal getClosingBalanceLeaveTotal() {
		return closingBalanceLeaveTotal;
	}

	public void setClosingBalanceLeaveTotal(BigDecimal closingBalanceLeaveTotal) {
		this.closingBalanceLeaveTotal = closingBalanceLeaveTotal;
	}

	public BigDecimal getLeaveEncashedTotal() {
		return leaveEncashedTotal;
	}

	public void setLeaveEncashedTotal(BigDecimal leaveEncashedTotal) {
		this.leaveEncashedTotal = leaveEncashedTotal;
	}

	public Map<String, List<BigDecimal>> getLeaveEncashMap() {
		return leaveEncashMap;
	}

	public void setLeaveEncashMap(Map<String, List<BigDecimal>> leaveEncashMap) {
		this.leaveEncashMap = leaveEncashMap;
	}

	public Map<String, List<BigDecimal>> getLeaveClosedBalanceMap() {
		return leaveClosedBalanceMap;
	}

	public void setLeaveClosedBalanceMap(Map<String, List<BigDecimal>> leaveClosedBalanceMap) {
		this.leaveClosedBalanceMap = leaveClosedBalanceMap;
	}

	public BigDecimal getLeaveConsumed() {
		return leaveConsumed;
	}

	public void setLeaveConsumed(BigDecimal leaveConsumed) {
		this.leaveConsumed = leaveConsumed;
	}

	public BigDecimal getConsumedLeaveTotal() {
		return consumedLeaveTotal;
	}

	public void setConsumedLeaveTotal(BigDecimal consumedLeaveTotal) {
		this.consumedLeaveTotal = consumedLeaveTotal;
	}

	public Map<String, List<BigDecimal>> getLeaveEntitlementTypeMap() {
		return leaveEntitlementTypeMap;
	}

	public void setLeaveEntitlementTypeMap(Map<String, List<BigDecimal>> leaveEntitlementTypeMap) {
		this.leaveEntitlementTypeMap = leaveEntitlementTypeMap;
	}

	
	public Map<String, List<BigDecimal>> getLeaveTypeMap() {
		return leaveTypeMap;
	}

	public void setLeaveTypeMap(Map<String, List<BigDecimal>> leaveTypeMap) {
		this.leaveTypeMap = leaveTypeMap;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public BigDecimal getCarryForward() {
		return carryForward;
	}

	public void setCarryForward(BigDecimal carryForward) {
		this.carryForward = carryForward;
	}

	public BigDecimal getConsumed() {
		return consumed;
	}

	public void setConsumed(BigDecimal consumed) {
		this.consumed = consumed;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getAnnualQuota() {
		return annualQuota;
	}

	public void setAnnualQuota(BigDecimal annualQuota) {
		this.annualQuota = annualQuota;
	}

	public BigDecimal getCarryForwardTotal() {
		return carryForwardTotal;
	}

	public void setCarryForwardTotal(BigDecimal carryForwardTotal) {
		this.carryForwardTotal = carryForwardTotal;
	}

	public BigDecimal getConsumedTotal() {
		return consumedTotal;
	}

	public void setConsumedTotal(BigDecimal consumedTotal) {
		this.consumedTotal = consumedTotal;
	}

	public BigDecimal getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(BigDecimal balanceTotal) {
		this.balanceTotal = balanceTotal;
	}

	public BigDecimal getAnnualQuotaTotal() {
		return annualQuotaTotal;
	}

	public void setAnnualQuotaTotal(BigDecimal annualQuotaTotal) {
		this.annualQuotaTotal = annualQuotaTotal;
	}

	
	
	public AttendanceDTO() {
	
	}
	
	public AttendanceDTO(String employeeCode, String employeeName, int presense, int weekOff, int publicholidays,
			int paidLeave, int casualLeave, int seekleave, int absense, Long companyId, 
			Long departmentId ,Long payDays) {
		
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.presense = presense;
		this.weekOff = weekOff;
		this.publicholidays = publicholidays;
		this.paidLeave = paidLeave;
		this.casualLeave = casualLeave;
		this.seekleave = seekleave;
		this.absense = absense;
		this.companyId = companyId;
	
		this.departmentId = departmentId;
		this.payDays=payDays;
	}
	
	public int getAbsense() {
		return absense;
	}
	public void setAbsense(int absense) {
		this.absense = absense;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public int getWeekOff() {
		return weekOff;
	}
	public void setWeekOff(int weekOff) {
		this.weekOff = weekOff;
	}
	
	public int getPaidLeave() {
		return paidLeave;
	}
	public void setPaidLeave(int paidLeave) {
		this.paidLeave = paidLeave;
	}
	public int getCasualLeave() {
		return casualLeave;
	}
	public void setCasualLeave(int casualLeave) {
		this.casualLeave = casualLeave;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getPresense() {
		return presense;
	}
	public void setPresense(int presense) {
		this.presense = presense;
	}
	public int getPublicholidays() {
		return publicholidays;
	}
	public void setPublicholidays(int publicholidays) {
		this.publicholidays = publicholidays;
	}
	public int getSeekleave() {
		return seekleave;
	}
	public void setSeekleave(int seekleave) {
		this.seekleave = seekleave;
	}

	
	
	 public Long getPayDays() {
		return payDays;
	}

	public void setPayDays(Long payDays) {
		this.payDays = payDays;
	}
	
	

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

	@Override
     public String toString() {
        return new ToStringBuilder(this)
                .append("employeeCode", this.employeeCode)
                .append("employeeName", this.employeeName)
                .append("presense", this.presense)
                .append("weekOff", this.weekOff)
                .append("publicholidays", this.publicholidays)
                .append("paidLeave", this.paidLeave)
                .append("casualLeave", this.casualLeave)
                .append("seekleave", this.seekleave)
                .append("absense", this.absense)
                .append("seekleave", this.seekleave)
                .append("companyId", this.companyId)
                .append("employeeId", this.employeeId)
                .append("departmentId", this.departmentId)
                .toString();
    }

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
