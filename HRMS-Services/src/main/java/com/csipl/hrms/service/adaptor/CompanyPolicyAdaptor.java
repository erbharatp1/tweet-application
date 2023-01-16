package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.GlobalConstantUtils;
import com.csipl.hrms.common.util.StorageUtil;
import com.csipl.hrms.dto.employee.CompanyPolicyDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.employee.CompanyPolicy;
import com.csipl.hrms.service.util.ConverterUtil;

@Component
public class CompanyPolicyAdaptor implements Adaptor<CompanyPolicyDTO, CompanyPolicy> {

	StorageUtil storageUtil = new StorageUtil();

	@Override
	public List<CompanyPolicy> uiDtoToDatabaseModelList(List<CompanyPolicyDTO> companyPolicyList) {

		return companyPolicyList.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<CompanyPolicyDTO> databaseModelToUiDtoList(List<CompanyPolicy> companyPolicyList) {

		return companyPolicyList.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public CompanyPolicy uiDtoToDatabaseModel(CompanyPolicyDTO companyPolicyDTO) {
		CompanyPolicy companyPolicy = new CompanyPolicy();

		companyPolicy.setPolicyId(companyPolicyDTO.getPolicyId());
		companyPolicy.setPolicyName(companyPolicyDTO.getPolicyName());
		companyPolicy.setActiveStatus(companyPolicyDTO.getActiveStatus());
		companyPolicy.setDateCreated(companyPolicyDTO.getDateCreated());
		companyPolicy.setUserId(companyPolicyDTO.getUserId());
		companyPolicy.setUserIdUpdate(companyPolicyDTO.getUserIdUpdate());
		companyPolicy.setCompanyId(companyPolicyDTO.getCompanyId());
		companyPolicy.setFileLocation(companyPolicyDTO.getFileLocation());

		return companyPolicy;
	}

	@Override
	public CompanyPolicyDTO databaseModelToUiDto(CompanyPolicy companyPolicy) {

		CompanyPolicyDTO companyPolicyDTO = new CompanyPolicyDTO();

		companyPolicyDTO.setPolicyId(companyPolicy.getPolicyId());
		companyPolicyDTO.setPolicyName(companyPolicy.getPolicyName());
		companyPolicyDTO.setActiveStatus(companyPolicy.getActiveStatus());
		companyPolicyDTO.setDateCreated(companyPolicy.getDateCreated());
		companyPolicyDTO.setUserId(companyPolicy.getUserId());
		companyPolicyDTO.setUserIdUpdate(companyPolicy.getUserIdUpdate());
		companyPolicyDTO.setCompanyId(companyPolicy.getCompanyId());
		companyPolicyDTO.setFileLocation(companyPolicy.getFileLocation());

		return companyPolicyDTO;
	}

	public CompanyPolicy uiDtoToDatabaseModelForFileUpload(CompanyPolicyDTO companyPolicyDTO,
			MultipartFile multipartFile, boolean isFile) {

		CompanyPolicy companyPolicy = new CompanyPolicy();

		companyPolicy.setPolicyId(companyPolicyDTO.getPolicyId());
		companyPolicy.setPolicyName(companyPolicyDTO.getPolicyName());
		companyPolicy.setActiveStatus(companyPolicyDTO.getActiveStatus());
		companyPolicy.setDateCreated(companyPolicyDTO.getDateCreated());
		companyPolicy.setUserId(companyPolicyDTO.getUserId());
		companyPolicy.setUserIdUpdate(companyPolicyDTO.getUserIdUpdate());
		companyPolicy.setCompanyId(companyPolicyDTO.getCompanyId());

		String fileName = "";
		if (isFile) {
			fileName = companyPolicyDTO.getPolicyName() + "_PDF_";
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			fileName = fileName + "." + extension;

			String path = GlobalConstantUtils.custom_separateor + "docfiles" + GlobalConstantUtils.custom_separateor
					+ "document" + fileName;
			String dbPath = path + GlobalConstantUtils.custom_separateor + fileName;
			storageUtil.store(multipartFile, path, fileName);
			companyPolicy.setFileLocation(dbPath);
		}

		return companyPolicy;
	}

	public List<CompanyPolicyDTO> databaseModelToUiDtoEmployeeList(List<Object[]> employeeList,
			EmployeeSearchDTO employeeSearchDto) {

		List<CompanyPolicyDTO> employeeDTOList = new ArrayList<CompanyPolicyDTO>();

		int index = 1;
		for (Object[] empObj : employeeList) {
			CompanyPolicyDTO emp = new CompanyPolicyDTO();

			emp.setIndex(index);

			if (empObj[0] != null) {
				emp.setEmployeeName(empObj[0].toString());
			}

			if (empObj[1] != null) {
				emp.setEmployeeCode(empObj[1].toString());
			}

			if (empObj[2] != null) {
				emp.setDepartmentName(empObj[2].toString());
			}

			long empId = ConverterUtil.getLong(empObj[3]);
			emp.setEmployeeId(empId);

			long deptId = ConverterUtil.getLong(empObj[4]);
			emp.setDepartmentId(deptId);

			employeeDTOList.add(emp);
			index++;
		}

		if (employeeSearchDto.getActive() != null && (employeeSearchDto.getActive().trim().equals("index"))) {

			if (employeeSearchDto.getSortDirection().equals("asc")) {
				Collections.sort(employeeDTOList);
			} else {

				Collections.sort(employeeDTOList, Collections.reverseOrder());
			}

		}

		return employeeDTOList;
	}

}
