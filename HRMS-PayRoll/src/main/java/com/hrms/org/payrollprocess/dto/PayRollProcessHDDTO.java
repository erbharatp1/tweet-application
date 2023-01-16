package com.hrms.org.payrollprocess.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Esi;
import com.csipl.hrms.model.payroll.LabourWelfareFund;
import com.csipl.hrms.model.payroll.ProfessionalTax;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

public class PayRollProcessHDDTO {

	private List<PayRollProcessDTO> listPayRollProcessDTOs;
	//private Attendance attendance;
	private Epf epf;
	Esi esi;
	private String payMonth ;
	private ProfessionalTax professionalTax;
	private LabourWelfareFund labourWelfareFund;
	private BigDecimal totalGrossSalary;
	
	private Employee employee;


	private ReportPayOut reportPayOut;
	
	private Date esiLifeCycleEndDate;
	
	public List<PayRollProcessDTO> getListPayRollProcessDTOs() {
		return listPayRollProcessDTOs;
	}
	public void setListPayRollProcessDTOs(List<PayRollProcessDTO> listPayRollProcessDTOs) {
		this.listPayRollProcessDTOs = listPayRollProcessDTOs;
	}
	public Epf getEpf() {
		return epf;
	}
	public void setEpf(Epf epf) {
		this.epf = epf;
	}
	public Esi getEsi() {
		return esi;
	}
	public void setEsi(Esi esi) {
		this.esi = esi;
	}
	public String getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}
/*	public Attendance getAttendance() {
		return attendance;
	}
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}*/
	public BigDecimal getTotalGrossSalary() {
		return totalGrossSalary;
	}
	public void setTotalGrossSalary(BigDecimal totalGrossSalary) {
		this.totalGrossSalary = totalGrossSalary;
	}
	public ProfessionalTax getProfessionalTax() {
		return professionalTax;
	}
	public void setProfessionalTax(ProfessionalTax professionalTax) {
		this.professionalTax = professionalTax;
	}
	
	
	
	
	public LabourWelfareFund getLabourWelfareFund() {
		return labourWelfareFund;
	}
	public void setLabourWelfareFund(LabourWelfareFund labourWelfareFund) {
		this.labourWelfareFund = labourWelfareFund;
	}
	public ReportPayOut getReportPayOut() {
		return reportPayOut;
	}
	public void setReportPayOut(ReportPayOut reportPayOut) {
		this.reportPayOut = reportPayOut;
	}
	public Date getEsiLifeCycleEndDate() {
		return esiLifeCycleEndDate;
	}
	public void setEsiLifeCycleEndDate(Date esiLifeCycleEndDate) {
		this.esiLifeCycleEndDate = esiLifeCycleEndDate;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
}
