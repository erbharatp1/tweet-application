package com.csipl.hrms.service.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.dto.employee.DepartmentNode;
import com.csipl.hrms.dto.employee.EmployeeNode;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;

@Service
public class ReportToHierarchyServiceImpl implements ReportToHierarchyService {

	@Autowired
	EmployeePersonalInformationRepository employeePersonalInformationRepository;

	static Map<Long, EmployeeNode> employees;
	static EmployeeNode root;

	@Override
	public List<Object[]> getTeamHirarchy(Long companyId) {
		List<Object[]> objList = employeePersonalInformationRepository.getTeamHirarchy(companyId);
		return objList;
	}

	public static EmployeeNode hirarchy(List<Object[]> objList) {

		employees = new HashMap<Long, EmployeeNode>();
		readDataAndCreateMap(objList);
		if(root!=null)
		buildHierarchyTree(root);
		 printHierarchyTree(root, 0);
		return root;
	}

	private static void readDataAndCreateMap(List<Object[]> objList) {

		EmployeeNode employee = null;
		Long employeeId, reportToId = 0l;
		String employeeName, designationName, imageLogoPath, departmentName;

		for (Object[] objects : objList) {
			employee = new EmployeeNode();
			DepartmentNode departmentNode = new DepartmentNode();
			employeeId = objects[0] != null ? Long.parseLong(objects[0].toString()) : 0;
			employeeName = objects[1] != null ? (objects[1].toString()) : null;
			designationName = objects[2] != null ? (objects[2].toString()) : null;
			imageLogoPath = objects[3] != null ? (objects[3].toString()) : null;
			reportToId = objects[4] != null ? Long.parseLong(objects[4].toString()) : null;
			departmentName = objects[5] != null ? (objects[5].toString()) : null;
			
			
			employee.setName(employeeName);
			employee.setEmployeeId(employeeId);
			employee.setPositionName(designationName);
			employee.setImageUrl(imageLogoPath);
			employee.setReportToId(reportToId);
			employee.setProfileUrl(imageLogoPath);
			
			departmentNode.setType("department");
			departmentNode.setValue(departmentName);
			departmentNode.setDesc(departmentName+" Dept description");
			
			employee.setUnit(departmentNode);
			// if (objects.length> 1) {
			// employee = new EmployeeNode(values[0], values[1] + " " + values[2],
			// values[3]);
			// }
			// employee = new EmployeeNode(values[0], values[1] + " " + values[2], "0");

			employees.put(employee.getEmployeeId(), employee);
			if (employee.getReportToId()!=null && employee.getReportToId() == 0) {
				root = employee;
			}
		}

	}

	// scan whole employee hashMap to form a list of subordinates for the given id
	private static List<EmployeeNode> getSubordinatesById(Long rid) {
		List<EmployeeNode> subordinates = new ArrayList<EmployeeNode>();
		for (EmployeeNode e : employees.values()) {
			Long reportingToId = e.getReportToId();
			if (reportingToId!=null && reportingToId.equals(rid)) {
				subordinates.add(e);
			}
		}
		return subordinates;
	}
	// build tree recursively
	private static void buildHierarchyTree(EmployeeNode localRoot) {
		EmployeeNode employee = localRoot;
		List<EmployeeNode> subordinates = getSubordinatesById(employee.getEmployeeId());
		employee.setChildren(subordinates);
		if (subordinates.size() == 0) {
			return;
		}

		for (EmployeeNode e : subordinates) {
			buildHierarchyTree(e);
		}
	}

	// print tree recursively
	private static void printHierarchyTree(EmployeeNode localRoot, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
		System.out.println(localRoot.getName());

		List<EmployeeNode> subordinates = localRoot.getChildren();
		System.out.print(" ");
		for (EmployeeNode e : subordinates) {
			printHierarchyTree(e, level + 1);
		}
	}

}
