package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.employee.Employee;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ArearMaster database table.
 * 
 */
@Entity
@NamedQuery(name="ArearMaster.findAll", query="SELECT a FROM ArearMaster a")
public class ArearMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long arearId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arearFrom;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arearTo;

	private BigDecimal basicSalary;

	private String bookedPayrollMonth;

	private Long companyId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private Long isBooked;

	private BigDecimal specialAllowance;

	private Long userId;

	private Long userIdUpdate;

	//bi-directional many-to-one association to ArearCalculation

	@OneToMany(mappedBy="arearMaster", cascade = CascadeType.ALL )
	private List<ArearCalculation> arearCalculations;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;

	public ArearMaster() {
	}

	public Long getArearId() {
		return this.arearId;
	}

	public void setArearId(Long arearId) {
		this.arearId = arearId;
	}

	public Date getArearFrom() {
		return this.arearFrom;
	}

	public void setArearFrom(Date arearFrom) {
		this.arearFrom = arearFrom;
	}

	public Date getArearTo() {
		return this.arearTo;
	}

	public void setArearTo(Date arearTo) {
		this.arearTo = arearTo;
	}

	public BigDecimal getBasicSalary() {
		return this.basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getBookedPayrollMonth() {
		return this.bookedPayrollMonth;
	}

	public void setBookedPayrollMonth(String bookedPayrollMonth) {
		this.bookedPayrollMonth = bookedPayrollMonth;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Long getIsBooked() {
		return this.isBooked;
	}

	public void setIsBooked(Long isBooked) {
		this.isBooked = isBooked;
	}

	public BigDecimal getSpecialAllowance() {
		return this.specialAllowance;
	}

	public void setSpecialAllowance(BigDecimal specialAllowance) {
		this.specialAllowance = specialAllowance;
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

	public List<ArearCalculation> getArearCalculations() {
		return this.arearCalculations;
	}

	public void setArearCalculations(List<ArearCalculation> arearCalculations) {
		this.arearCalculations = arearCalculations;
	}

	public ArearCalculation addArearCalculation(ArearCalculation arearCalculation) {
		getArearCalculations().add(arearCalculation);
		arearCalculation.setArearMaster(this);

		return arearCalculation;
	}

	public ArearCalculation removeArearCalculation(ArearCalculation arearCalculation) {
		getArearCalculations().remove(arearCalculation);
		arearCalculation.setArearMaster(null);

		return arearCalculation;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}