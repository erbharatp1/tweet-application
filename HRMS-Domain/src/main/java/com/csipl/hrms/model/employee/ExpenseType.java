package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ExpenseType database table.
 * 
 */
@Entity
@NamedQuery(name="ExpenseType.findAll", query="SELECT e FROM ExpenseType e")
public class ExpenseType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long expenseTypeId;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	private Date dateUpdate;

	private String expenseTypeName;

	private Long userId;

	private Long userIdUpdate;

	public ExpenseType() {
	}

	public Long getExpenseTypeId() {
		return this.expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
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

	public String getExpenseTypeName() {
		return this.expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
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

}