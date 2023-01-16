package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeDTO;

public class MyTeamAdaptor {

	
//	public  List<MyTeamDTO> dbObjectToDtoList(List<Object[]> objectList){
//		
//		Object[] emp =objectList.get(0);
//		String data[]= new String[emp.length];
//		System.out.println("sagar");
//		
////		MyTeamDTO employeeDTOList = new MyTeamDTO();
//		
//		List<MyTeamDTO> employeeDTOList = new ArrayList<MyTeamDTO>();
//		Long employeeId = 0l;
//		String employeeName,designationName, imageLogoPath;
//		int count=0;
//		for (Object[] objects : objectList) {
//			count++;
//			MyTeamDTO employeeDTO = new MyTeamDTO();
//
//			employeeId = objects[0] != null ? Long.parseLong(objects[0].toString()) : 0;
//			employeeName = objects[1] != null ? (objects[1].toString()) : null;
//			designationName = objects[2] != null ? (objects[2].toString()) : null;
//			imageLogoPath = objects[3] != null ? (objects[3].toString()) : null;
//
//			employeeDTO.setName(employeeName);
//			employeeDTO.setEmployeeId(employeeId);
//			employeeDTO.setPositionName(designationName);
//			employeeDTO.setImageUrl(imageLogoPath);
//
//			List<MyTeamDTO> employeeList = new ArrayList<MyTeamDTO>();
//			String Splitdata[] = objects[4].toString().split("-");
//
//			for (int i = 0; i < Splitdata.length; i++) {
//				MyTeamDTO employee = new MyTeamDTO();
//				String objdata[] = Splitdata[i].split(",");
//
//				if (i == 0) {
//					employee.setEmployeeId(Long.valueOf(objdata[0].toString()));
//					employee.setName(objdata[1]);
//					employee.setPositionName(objdata[2]);
//					employee.setImageUrl(objdata[3]);
//					employeeList.add(employee);
//				} else {
//					employee.setEmployeeId(Long.valueOf(objdata[1].toString()));
//					employee.setName(objdata[2]);
//					employee.setPositionName(objdata[3]);
//					employee.setImageUrl(objdata[4]);
//					employeeList.add(employee);
//				}
//
//			}
//			System.out.println("dattaaaa====count---------- " + count);
//			employeeDTO.setChildren(employeeList);
//			// String objdata = Splitdata[0];
//			employeeDTOList.add(employeeDTO);
//		}
//		
//		return employeeDTOList;
//		
//	}
	 
}
