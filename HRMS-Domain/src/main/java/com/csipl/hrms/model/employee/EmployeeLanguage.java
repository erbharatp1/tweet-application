package com.csipl.hrms.model.employee;

import java.io.Serializable;
import javax.persistence.*;

import com.csipl.hrms.model.common.Language;

/**
 * The persistent class for the EmployeeLanguage database table.
 * 
 */
@Entity
@NamedQuery(name = "EmployeeLanguage.findAll", query = "SELECT e FROM EmployeeLanguage e")
public class EmployeeLanguage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeLanguageId;

	private String langRead;

	private String langSpeak;

	private String langWrite;
	@Transient
	private String employeeCode;
	@Transient
	private String firstName;
	@Transient
	private String middleName;
	@Transient
	private String lastName;
	@Transient

	private String deptName;
	@Transient
	private String designation;
	@Transient
	private String hRead;
	@Transient
	private String hSpeak;
	@Transient
	private String hWrite;
	@Transient
	private String eRead;
	@Transient
	private String eSpeak;
	@Transient
	private String eWrite;
	@Transient
	private Integer langId;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	// bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name = "languageId")
	private Language language;

	public EmployeeLanguage() {
	}

	public Long getEmployeeLanguageId() {
		return this.employeeLanguageId;
	}

	public void setEmployeeLanguageId(Long employeeLanguageId) {
		this.employeeLanguageId = employeeLanguageId;
	}

	public String getLangRead() {
		return this.langRead;
	}

	public void setLangRead(String langRead) {
		this.langRead = langRead;
	}

	public String getLangSpeak() {
		return this.langSpeak;
	}

	public void setLangSpeak(String langSpeak) {
		this.langSpeak = langSpeak;
	}

	public String getLangWrite() {
		return this.langWrite;
	}

	public void setLangWrite(String langWrite) {
		this.langWrite = langWrite;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String gethRead() {
		return hRead;
	}

	public void sethRead(String hRead) {
		this.hRead = hRead;
	}

	public String gethSpeak() {
		return hSpeak;
	}

	public void sethSpeak(String hSpeak) {
		this.hSpeak = hSpeak;
	}

	public String gethWrite() {
		return hWrite;
	}

	public void sethWrite(String hWrite) {
		this.hWrite = hWrite;
	}

	public String geteRead() {
		return eRead;
	}

	public void seteRead(String eRead) {
		this.eRead = eRead;
	}

	public String geteSpeak() {
		return eSpeak;
	}

	public void seteSpeak(String eSpeak) {
		this.eSpeak = eSpeak;
	}

	public String geteWrite() {
		return eWrite;
	}

	public void seteWrite(String eWrite) {
		this.eWrite = eWrite;
	}

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

}
