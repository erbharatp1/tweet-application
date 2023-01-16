package com.csipl.hrms.model.common;


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


/**
 * The persistent class for the TokenMaster database table.
 * 
 */
@Entity
@NamedQuery(name="TokenMaster.findAll", query="SELECT t FROM TokenMaster t")
public class TokenMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tokenId;

	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	private String tokenValue;

	//bi-directional many-to-one association to TokenTypeMaster
	@ManyToOne
	@JoinColumn(name="tokentypeId" )
	private TokenTypeMaster tokenTypeMaster;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public TokenMaster() {
	}

	public Long getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public TokenTypeMaster getTokenTypeMaster() {
		return this.tokenTypeMaster;
	}

	public void setTokenTypeMaster(TokenTypeMaster tokenTypeMaster) {
		this.tokenTypeMaster = tokenTypeMaster;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
