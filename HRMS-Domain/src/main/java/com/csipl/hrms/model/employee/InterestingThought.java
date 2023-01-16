package com.csipl.hrms.model.employee;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.common.Company;

/**
 * The persistent class for the InterestingThoughts database table.
 * 
 */
@Entity
@Table(name = "InterestingThoughts")
@NamedQuery(name = "InterestingThought.findAll", query = "SELECT i FROM InterestingThought i")
public class InterestingThought implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long interestingThoughtsId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	private String thoughts;

	private Long userId;

	private Long userIdUpdate;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	public InterestingThought() {
	}

	public Date getDateCreated() {
		return dateCreated;
	}

    public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

   public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDateUpdate(Timestamp dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getThoughts() {
		return this.thoughts;
	}

	public void setThoughts(String thoughts) {
		this.thoughts = thoughts;
	}

	public Long getInterestingThoughtsId() {
		return interestingThoughtsId;
	}

	public void setInterestingThoughtsId(Long interestingThoughtsId) {
		this.interestingThoughtsId = interestingThoughtsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



}