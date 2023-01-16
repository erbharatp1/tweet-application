package com.csipl.hrms.config;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * The persistent class for the TokenMaster database table.
 * 
 */
@Entity
@Table(name = "TokenMaster")
@NamedQuery(name="TokenMaster.findAll", query="SELECT t FROM TokenMaster t")
public class TokenMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;

	private Long createdBy;


	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	
	
	

	private Long tokentypeId;

	private String tokenValue;

	private Long userId;

	public TokenMaster() {
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

	public Long getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getTokentypeId() {
		return this.tokentypeId;
	}

	public void setTokentypeId(Long tokentypeId) {
		this.tokentypeId = tokentypeId;
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}