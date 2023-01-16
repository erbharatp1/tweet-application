package com.csipl.hrms.dto.payroll;

import java.util.ArrayList;

public class AttendanceProcesDTO {
 private Long   departmentId;
 
 private String  processMonth;
 
 private ArrayList<Long>  employeeIdList ;
 
 private Long   companyId ;
 
public Long getDepartmentId() {
	return departmentId;
}
public void setDepartmentId(Long departmentId) {
	this.departmentId = departmentId;
}
public String getProcessMonth() {
	return processMonth;
}
public void setProcessMonth(String processMonth) {
	this.processMonth = processMonth;
}
public ArrayList<Long> getEmployeeIdList() {
	return employeeIdList;
}
public void setEmployeeIdList(ArrayList<Long> employeeIdList) {
	this.employeeIdList = employeeIdList;
}
public Long getCompanyId() {
	return companyId;
}
public void setCompanyId(Long companyId) {
	this.companyId = companyId;
}
}
