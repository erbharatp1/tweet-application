package com.csipl.hrms.model.employee;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="EmployeeBulkUploadMaster")
@NamedQuery(name="EmployeeBulkUploadMaster.findAll", query="SELECT e FROM EmployeeBulkUploadMaster e")
public class EmployeeBulkUploadMaster {

	
	@Id
	private Long indexNumber;

	private static final long serialVersionUID = 1L;
	private String columnHead;

	private String fileCode;
	
	public EmployeeBulkUploadMaster() {
	}

	public String getColumnHead() {
		return this.columnHead;
	}

	public void setColumnHead(String columnHead) {
		this.columnHead = columnHead;
	}

	public String getFileCode() {
		return this.fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public Long getIndexNumber() {
		return this.indexNumber;
	}

	public void setIndexNumber(Long indexNumber) {
		this.indexNumber = indexNumber;
	}
}
