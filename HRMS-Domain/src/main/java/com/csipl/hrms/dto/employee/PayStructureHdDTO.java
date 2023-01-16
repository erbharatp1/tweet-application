package com.csipl.hrms.dto.employee;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csipl.hrms.dto.payrollprocess.PayOutDTO;

public class PayStructureHdDTO {
	private Long payStructureHdId;
	private Long gradesId;
	private String gradesName;
	private BigDecimal grossPay;
	private Date effectiveDate;
	private String activeStatus;
	private Long employeeId;
 	private Long userId;
	private Date dateCreated;
	private boolean updateFlag;
	private Date dateEnd;
	private String firstName;
	private String lastName;
	private String employeeCode;
	private String departmentName;
	private String designationName;
	private String isNoPFDeduction;	
	private boolean revisionUpdateFlag;
	private Long userIdUpdate;
	private Long companyId;
	private String processMonth;
	private BigDecimal ctc;
	private BigDecimal netPay;
	private String employeeName;
	private String employeeType;
	private String employeeStatus;
	private BigDecimal epfEmployee;
	private BigDecimal epfEmployer;
	private BigDecimal esiEmployee;
	private BigDecimal esiEmployer;
	private BigDecimal professionalTax;
	private Date dateUpdate;

	private BigDecimal lwfEmployeeAmount;
 	private BigDecimal lwfEmployerAmount;
 	private BigDecimal  epfEmployeePension;
    private Date dateOfJoining;

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	private List<String> processMonthList;

	List<PayOutDTO> payOutDtoList;

	private List<PayStructureDTO> payStructureDtoList;
	private Map<String,String> payHeadsMap;
	
 	
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

	public BigDecimal getEsiEmployee() {
		return esiEmployee;
	}

	public void setEsiEmployee(BigDecimal esiEmployee) {
		this.esiEmployee = esiEmployee;
	}

	public BigDecimal getEsiEmployer() {
		return esiEmployer;
	}

	public void setEsiEmployer(BigDecimal esiEmployer) {
		this.esiEmployer = esiEmployer;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}


	 

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}



	public Long getPayStructureHdId() {
		return payStructureHdId;
	}

	public void setPayStructureHdId(Long payStructureHdId) {
		this.payStructureHdId = payStructureHdId;
	}

	public Long getGradesId() {
		return gradesId;
	}

	public void setGradesId(Long gradesId) {
		this.gradesId = gradesId;
	}

	public String getGradesName() {
		return gradesName;
	}

	public void setGradesName(String gradesName) {
		this.gradesName = gradesName;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

 	public List<PayStructureDTO> getPayStructureDtoList() {
		return payStructureDtoList;
	}

	public void setPayStructureDtoList(List<PayStructureDTO> payStructureDtoList) {
		this.payStructureDtoList = payStructureDtoList;
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
	public boolean isUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getIsNoPFDeduction() {
		return isNoPFDeduction;
	}

	public void setIsNoPFDeduction(String isNoPFDeduction) {
		this.isNoPFDeduction = isNoPFDeduction;
	}

	public boolean isRevisionUpdateFlag() {
		return revisionUpdateFlag;
	}

	public void setRevisionUpdateFlag(boolean revisionUpdateFlag) {
		this.revisionUpdateFlag = revisionUpdateFlag;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getProcessMonth() {
		return processMonth;
	}

	public void setProcessMonth(String processMonth) {
		this.processMonth = processMonth;
	}

 

	public List<PayOutDTO> getPayOutDtoList() {
		return payOutDtoList;
	}

	public void setPayOutDtoList(List<PayOutDTO> payOutDtoList) {
		this.payOutDtoList = payOutDtoList;
	}

	public List<String> getProcessMonthList() {
		return processMonthList;
	}

	public void setProcessMonthList(List<String> processMonthList) {
		this.processMonthList = processMonthList;
	}

	public BigDecimal getCtc() {
		return ctc;
	}

	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Map<String,String> getPayHeadsMap() {
		return payHeadsMap;
	}

	public void setPayHeadsMap(Map<String,String> payHeadsMap) {
		this.payHeadsMap = payHeadsMap;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
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

	public BigDecimal getEpfEmployeePension() {
		return epfEmployeePension;
	}

	public void setEpfEmployeePension(BigDecimal epfEmployeePension) {
		this.epfEmployeePension = epfEmployeePension;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}


	
 

	

 
}
