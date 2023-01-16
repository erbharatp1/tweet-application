package com.csipl.hrms.config;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TokenTypeMaster database table.
 * 
 */
@Entity
@NamedQuery(name="TokenTypeMaster.findAll", query="SELECT t FROM TokenTypeMaster t")
public class TokenTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tokenTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	private String tokenType;

	private int userId;

	public TokenTypeMaster() {
	}

	public int getTokenTypeId() {
		return this.tokenTypeId;
	}

	public void setTokenTypeId(int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}