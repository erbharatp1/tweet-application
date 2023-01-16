package com.csipl.hrms.service.adaptor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.report.InterestingThoughtDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.InterestingThought;

public class InterestingThoughtsAdaptor implements Adaptor<InterestingThoughtDTO, InterestingThought> {

	@Override
	public List<InterestingThought> uiDtoToDatabaseModelList(List<InterestingThoughtDTO> interestingThoughtDtoList) {
		// TODO Auto-generated method stub
		return interestingThoughtDtoList.stream().map(interestingThought -> uiDtoToDatabaseModel(interestingThought))
				.collect(Collectors.toList());
	}

	@Override
	public List<InterestingThoughtDTO> databaseModelToUiDtoList(List<InterestingThought> interestingThoughtList) {
		return interestingThoughtList.stream().map(interestingThought -> databaseModelToUiDto(interestingThought))
				.collect(Collectors.toList());
	}

	@Override
	public InterestingThoughtDTO databaseModelToUiDto(InterestingThought interestingThought) {
		InterestingThoughtDTO interestingThoughtDTO = new InterestingThoughtDTO();
		interestingThoughtDTO.setCompanyId(interestingThought.getCompany().getCompanyId());
		interestingThoughtDTO.setInterestingThoughtsId(interestingThought.getInterestingThoughtsId());
		interestingThoughtDTO.setThoughts(interestingThought.getThoughts());

		interestingThoughtDTO.setDateCreated(interestingThought.getDateCreated());

		if (interestingThought.getDateUpdate() != null) {
			interestingThoughtDTO.setDateUpdate(interestingThought.getDateUpdate());
		}
		interestingThoughtDTO.setUserId(interestingThought.getUserId());
		interestingThoughtDTO.setEmployeeId(interestingThought.getEmployee().getEmployeeId());
		interestingThoughtDTO.setUserIdUpdate(interestingThought.getUserIdUpdate());
		interestingThoughtDTO.setEmpName(
				interestingThought.getEmployee().getFirstName() + " " + interestingThought.getEmployee().getLastName());
		if(interestingThought.getEmployee().getFirstName()!=null) {
			interestingThoughtDTO.setFirstLetter(Character.toString(interestingThought.getEmployee().getFirstName().charAt(0)).toUpperCase());
			}
			if(interestingThought.getEmployee().getLastName()!=null) {
			interestingThoughtDTO.setLastLetter(Character.toString(interestingThought.getEmployee().getLastName().charAt(0)).toUpperCase());  
			}
		interestingThoughtDTO.setEmployeeLogoPath(interestingThought.getEmployee().getEmployeeLogoPath());
		return interestingThoughtDTO;
	}

	@Override
	public InterestingThought uiDtoToDatabaseModel(InterestingThoughtDTO interestingThoughtDTO) {
		InterestingThought interestingThought = new InterestingThought();
		Company company = new Company();
		company.setCompanyId(interestingThoughtDTO.getCompanyId());
		interestingThought.setCompany(company);

		Employee employee = new Employee();
		employee.setEmployeeId(interestingThoughtDTO.getEmployeeId());
		interestingThought.setEmployee(employee);
		interestingThought.setInterestingThoughtsId(interestingThoughtDTO.getInterestingThoughtsId());
		interestingThought.setThoughts(interestingThoughtDTO.getThoughts());

		// interestingThought.setDateCreated(interestingThoughtDTO.getDateCreated());
		if (interestingThoughtDTO.getInterestingThoughtsId() == null) {
			interestingThought.setDateCreated(new Date());
		}

		else {
			interestingThought.setDateCreated(interestingThoughtDTO.getDateCreated());

		}
		interestingThought.setDateUpdate(new Date());
		// interestingThoughtDTO.setDateCreated(interestingThoughtDTO.getDateCreated());
		// interestingThought.setDateUpdate(interestingThoughtDTO.getDateUpdate());
		interestingThought.setUserId(interestingThoughtDTO.getUserId());
		interestingThought.setUserIdUpdate(interestingThoughtDTO.getUserIdUpdate());

		return interestingThought;
	}

}
