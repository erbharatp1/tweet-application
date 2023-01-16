package com.csipl.hrms.model.common;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


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
	private Long tokenTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	private String tokenType;

	private Long userId;

	//bi-directional many-to-one association to TokenMaster
	@OneToMany(mappedBy="tokenTypeMaster")
	private List<TokenMaster> tokenMasters;

	public TokenTypeMaster() {
	}

	public Long getTokenTypeId() {
		return this.tokenTypeId;
	}

	public void setTokenTypeId(Long tokenTypeId) {
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

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TokenMaster> getTokenMasters() {
		return this.tokenMasters;
	}

	public void setTokenMasters(List<TokenMaster> tokenMasters) {
		this.tokenMasters = tokenMasters;
	}

	public TokenMaster addTokenMaster(TokenMaster tokenMaster) {
		getTokenMasters().add(tokenMaster);
		tokenMaster.setTokenTypeMaster(this);

		return tokenMaster;
	}

	public TokenMaster removeTokenMaster(TokenMaster tokenMaster) {
		getTokenMasters().remove(tokenMaster);
		tokenMaster.setTokenTypeMaster(null);

		return tokenMaster;
	}

}