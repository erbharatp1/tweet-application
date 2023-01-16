package com.csipl.hrms.model.recruitment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.employee.Employee;

@Entity
@NamedQuery(name = "PositionAllocationXref.findAll", query = "SELECT p FROM PositionAllocationXref p")
public class PositionAllocationXref implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionAllocationId;

	// bi-directional many-to-one association to Position
	@ManyToOne
	@JoinColumn(name = "positionId")
	private Position position;

	private Long noOfPosition;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "recruiterEmployeeId")
	private Employee employee;

	private Long userId;

	@Temporal(TemporalType.DATE)
	private Date datecreated;

	public Long getPositionAllocationId() {
		return positionAllocationId;
	}

	public void setPositionAllocationId(Long positionAllocationId) {
		this.positionAllocationId = positionAllocationId;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Long getNoOfPosition() {
		return noOfPosition;
	}

	public void setNoOfPosition(Long noOfPosition) {
		this.noOfPosition = noOfPosition;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	
	
	
	
	

}
