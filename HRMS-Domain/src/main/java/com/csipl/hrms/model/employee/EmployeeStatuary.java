package com.csipl.hrms.model.employee;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the EmployeeStatuary database table.
 * 
 */
@Entity
@NamedQuery(name="EmployeeStatuary.findAll", query="SELECT e FROM EmployeeStatuary e")
public class EmployeeStatuary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long statuaryId;

	private String allowModi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	private Date dateTo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String isApplicable;

	private String statuaryNumber;

	private String statuaryType;

	private Long userId;

	private Long userIdUpdate;
	private String  status;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	//bi-directional many-to-one association to EmployeeFamily
	@ManyToOne
	@JoinColumn(name="familyId")
	private EmployeeFamily employeeFamily;

	public EmployeeStatuary() {
	}

	public Long getStatuaryId() {
		return this.statuaryId;
	}

	public void setStatuaryId(Long statuaryId) {
		this.statuaryId = statuaryId;
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

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateUpdate() {
		return this.dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getIsApplicable() {
		return this.isApplicable;
	}

	public void setIsApplicable(String isApplicable) {
		this.isApplicable = isApplicable;
	}

	public String getStatuaryNumber() {
		return this.statuaryNumber;
	}

	public void setStatuaryNumber(String statuaryNumber) {
		this.statuaryNumber = statuaryNumber;
	}

	public String getStatuaryType() {
		return this.statuaryType;
	}

	public void setStatuaryType(String statuaryType) {
		this.statuaryType = statuaryType;
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

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeFamily getEmployeeFamily() {
		return this.employeeFamily;
	}

	public void setEmployeeFamily(EmployeeFamily employeeFamily) {
		this.employeeFamily = employeeFamily;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

}