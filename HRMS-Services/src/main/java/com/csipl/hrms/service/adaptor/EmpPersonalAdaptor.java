package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.ActiveStatusEnum;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.dto.candidate.CandidateDTO;
import com.csipl.hrms.dto.candidate.CandidateLanguageDTO;
import com.csipl.hrms.dto.candidate.ProgressBarDTO;

import com.csipl.hrms.dto.employee.EmployeeSkillDTO;
import com.csipl.hrms.model.candidate.Candidate;
import com.csipl.hrms.model.candidate.CandidateAddress;
import com.csipl.hrms.model.candidate.CandidateLanguage;
import com.csipl.hrms.model.candidate.CandidatePersonal;
import com.csipl.hrms.model.common.Address;
import com.csipl.hrms.model.common.Branch;
import com.csipl.hrms.model.common.City;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.Groupg;
import com.csipl.hrms.model.common.Language;
import com.csipl.hrms.model.common.State;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLanguage;
import com.csipl.hrms.model.employee.EmployeeSkill;
import com.csipl.hrms.model.employee.Skill;
import com.csipl.hrms.model.organisation.Client;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.Designation;
import com.csipl.hrms.model.organisation.Project;
import com.csipl.tms.dto.shift.ShiftDTO;


public class EmpPersonalAdaptor implements Adaptor<CandidateDTO, Employee> {
	AddressAdaptor addressAdaptor = new AddressAdaptor();

	@Override
	public List<Employee> uiDtoToDatabaseModelList(List<CandidateDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CandidateDTO> databaseModelToUiDtoList(List<Employee> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	
//check ---------
	public Employee candidateDtoToDatabaseModel(CandidateDTO employeeDto, Employee employeeOld) {
System.out.println(employeeDto.getAdharNumber());
System.out.println(employeeOld.getAdharNumber());

		Employee employee = new Employee();
		Department department = new Department();
		Designation designation = new Designation();
		City city = new City();
		State state = new State();
		Branch branch =new Branch();
		Company company = new Company();
		//Groupg groupg = new Groupg();
		
		employee.setEmployeeId(employeeDto.getCandidateId());
		if(employeeDto.getBiometricId()!=null) {
		employee.setBiometricId(employeeDto.getBiometricId());
		}else {
			employee.setBiometricId(employeeOld.getBiometricId());
		}
		
		if(employeeDto.getOfficialEmail()!=null) {
			employee.setOfficialEmail(employeeDto.getOfficialEmail());
			}else {
				employee.setOfficialEmail(employeeOld.getOfficialEmail());
			}
		employee.setEmployeeCode(employeeOld.getEmployeeCode());
		employee.setUserId(employeeOld.getUserId());
		/*groupg.setGroupId(1l);
		employee.setGroupg(groupg);*/	
	    company.setCompanyId(employeeOld.getCompany().getCompanyId());
		employee.setCompany(company);
		employee.setDateCreated(employeeOld.getDateCreated());
		employee.setProbationDays(employeeOld.getProbationDays());
		employee.setNoticePeriodDays(employeeOld.getNoticePeriodDays());
		employee.setDateUpdate(new Date());
		employee.setGradesId(employeeOld.getGradesId());
		///employee.setLeaveSchemeId(employeeDto.getLeaveSchemeId());
		if(employeeDto.getLeaveSchemeId()!=null) {
			employee.setLeaveSchemeId(employeeDto.getLeaveSchemeId());
			}else {
				employee.setLeaveSchemeId(employeeOld.getLeaveSchemeId());
			}
		//employee.setAttendanceSchemeId(employeeDto.getAttendanceSchemeId());
		if(employeeDto.getAttendanceSchemeId()!=null) {
			employee.setAttendanceSchemeId(employeeDto.getAttendanceSchemeId());
			}else {
				employee.setAttendanceSchemeId(employeeOld.getAttendanceSchemeId());
			}
		employee.setUserIdUpdate(employeeDto.getUserIdUpdate());
		employee.setPersonalEmail(employeeDto.getEmailId());
		employee.setEndDate(employeeOld.getEndDate());
		if (employeeDto.getCandidateId() != null)
			employee.setDateCreated(employeeOld.getDateCreated());
		else
			employee.setDateCreated(new Date());
		if (employeeDto.getFirstName() != null && employeeDto.getCityId() != null) {
			if (employeeDto.getReportingToEmployee() != null)
				employee.setReportingToEmployee(Long.parseLong(employeeDto.getReportingToEmployee()));
			employee.setFirstName(employeeDto.getFirstName());
			employee.setMiddleName(employeeDto.getMiddleName());
			employee.setLastName(employeeDto.getLastName());
			employee.setDateOfJoining(employeeDto.getDateOfJoining());
			employee.setDateOfBirth(employeeOld.getDateOfBirth());
			employee.setGender(employeeOld.getGender());
			employee.setEmployeeLogoPath(employeeDto.getCandidateLogoPath());
			employee.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());
			employee.setMaritalStatus(employeeOld.getMaritalStatus());
			employee.setShiftId(employeeDto.getShiftId());
			employee.setPatternId(employeeDto.getPatternId());
			employee.setOfficialEmail(employeeDto.getOfficialEmail());
			employee.setPersonalEmail(employeeDto.getEmailId());
			employee.setContactNo(employeeDto.getMobile());
			employee.setGradesId(employeeOld.getGradesId());
			
			if (employeeDto.getContractStartDate() != null) {
				employee.setContractStartDate(employeeDto.getContractStartDate());
				employee.setContractOverDate(employeeDto.getContractOverDate());
			}
			if (employeeDto.getStateId() != null) {
				state.setStateId(employeeDto.getStateId());
				employee.setState(state);
			}
			else {
				employee.setState(employeeOld.getState());
				}
			employee.setAddress1(employeeOld.getAddress1());
			employee.setAddress2(employeeOld.getAddress2());
			employee.setAddress3(employeeOld.getAddress3());

			employee.setAnniversaryDate(employeeOld.getAnniversaryDate());
			employee.setBloodGroup(employeeOld.getBloodGroup());
			employee.setAlternateNumber(employeeOld.getAlternateNumber());
			if (employeeDto.getDepartmentId() != null) {
				department.setDepartmentName(employeeDto.getDepartmentName());
				department.setDepartmentId(employeeDto.getDepartmentId());
				employee.setDepartment(department);
			}
			
			if (employeeDto.getDesignationId() != null) {
				designation.setDesignationId(employeeDto.getDesignationId());
				designation.setDesignationName(employeeDto.getDesignationName());
				employee.setDesignation(designation);
			}
			//employee.setDesignation(employeeOld.getDesignation());
			if (employeeDto.getCityId() != null) {
				city.setCityId(employeeDto.getCityId());
				// city.setCityName(employeeDto.getCityName());
				employee.setCity(city);
			}
			
			if(employeeDto.getBranchId()!=null)
			{
				branch.setBranchId(employeeDto.getBranchId());
				employee.setBranch(branch);
			}
			
			employee.setReferenceName(employeeOld.getReferenceName());
			employee.setTimeContract(employeeDto.getTimeContract());

		
			employee.setEmployeeLanguages(employeeOld.getEmployeeLanguages());

			if(employeeDto.getAdharNumber()!=null) {
				employee.setAdharNumber(employeeDto.getAdharNumber());
			}
			else
				employee.setAdharNumber(employeeOld.getAdharNumber());

		} else {
			if (employeeOld.getReportingToEmployee() != null)
				employee.setReportingToEmployee(employeeOld.getReportingToEmployee());
			employee.setFirstName(employeeOld.getFirstName());
			employee.setMiddleName(employeeOld.getMiddleName());
			
			employee.setLastName(employeeOld.getLastName());
			employee.setDateOfBirth(employeeDto.getDateOfBirth());
			employee.setGender(employeeDto.getGender());
			employee.setEmployeeLogoPath(employeeOld.getEmployeeLogoPath());
			employee.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());
			employee.setMaritalStatus(employeeDto.getMaritalStatus());
			employee.setDateOfJoining(employeeOld.getDateOfJoining());
			employee.setGradesId(employeeOld.getGradesId());
			if (employeeDto.getContractStartDate() != null) {
				employee.setContractStartDate(employeeOld.getContractStartDate());
				employee.setContractOverDate(employeeOld.getContractOverDate());
			}
			if (employeeDto.getPermanentAddressDto() != null && employeeDto.getPermanentAddressDto().getCityId() != null) {
				employee.setAddress1(addressAdaptor.uiDtoToDatabaseModel(employeeDto.getPermanentAddressDto()));
			}

			if (employeeDto.getPresentAddressDto() != null && employeeDto.getPresentAddressDto().getCityId() != null) {
				employee.setAddress2(addressAdaptor.uiDtoToDatabaseModel(employeeDto.getPresentAddressDto()));
			}

			if (employeeDto.getReferenceAddressDto() != null && employeeDto.getReferenceAddressDto().getCityId() != null) {
				employee.setAddress3(addressAdaptor.uiDtoToDatabaseModel(employeeDto.getReferenceAddressDto()));
			}
			
			employee.setPersonalEmail(employeeOld.getPersonalEmail());
			employee.setContactNo(employeeOld.getContactNo());
			employee.setShiftId(employeeOld.getShiftId());
			employee.setPatternId(employeeOld.getPatternId());
			employee.setAnniversaryDate(employeeDto.getAnniversaryDate());
			employee.setBloodGroup(employeeDto.getBloodGroup());
			employee.setAlternateNumber(employeeDto.getAlternateNumber());
			
			if(employeeDto.getAdharNumber()!=null) {
				employee.setAdharNumber(employeeDto.getAdharNumber());
			}
			else
				employee.setAdharNumber(employeeOld.getAdharNumber());
			if (employeeDto.getDepartmentId() != null) {
				department.setDepartmentName(employeeDto.getDepartmentName());
				department.setDepartmentId(employeeDto.getDepartmentId());
				employee.setDepartment(department);
			} else {
				department.setDepartmentName(employeeOld.getDepartment().getDepartmentName());
				department.setDepartmentId(employeeOld.getDepartment().getDepartmentId());
				employee.setDepartment(department);
			}
			if (employeeDto.getDesignationId() != null) {
				designation.setDesignationId(employeeDto.getDesignationId());
				designation.setDesignationName(employeeDto.getDesignationName());
				employee.setDesignation(designation);
			} else {
				designation.setDesignationId(employeeOld.getDesignation().getDesignationId());
				designation.setDesignationName(employeeOld.getDesignation().getDesignationName());
				employee.setDesignation(designation);
			}

			if(employeeOld.getBranch()!=null) {
				branch.setBranchId(employeeOld.getBranch().getBranchId());
				employee.setBranch(branch);
			}
			
			
				employee.setCity(employeeOld.getCity());
				employee.setState(employeeOld.getState());
				
			employee.setReferenceName(employeeDto.getReferenceName());
			employee.setTimeContract(employeeOld.getTimeContract());
			
			if (employeeDto.getCandidateLanguageDto() != null) {
				System.out.println("================================in if block======================================"
						+ employeeDto.getCandidateLanguageDto().size());
				employee.setEmployeeLanguages(
						languageDtoListToDatabaseModelList(employeeDto.getCandidateLanguageDto(), employee));
			}

		}
		employee.setTdsLockUnlockStatus(employeeOld.getTdsLockUnlockStatus());
		employee.setTdsStatus(employeeOld.getTdsStatus());
		employee.setTdsPlanType(employeeOld.getTdsPlanType());
		employee.setEmpType(employeeOld.getEmpType());
		return employee;

	}

	public List<EmployeeLanguage> languageDtoListToDatabaseModelList(
			List<CandidateLanguageDTO> candidateLanguageDTOList, Employee employee) {

		List<EmployeeLanguage> employeeLanguageList = new ArrayList<EmployeeLanguage>();
		for (CandidateLanguageDTO candidateLanguageDTO : candidateLanguageDTOList) {
			employeeLanguageList.add(languageDtoToDbModel(candidateLanguageDTO, employee));
		}
		return employeeLanguageList;
	}

	public EmployeeLanguage languageDtoToDbModel(CandidateLanguageDTO candidateLanguageDTO, Employee employee) {
		Language language = new Language();
		EmployeeLanguage employeeLanguage = new EmployeeLanguage();
		employeeLanguage.setEmployeeLanguageId(candidateLanguageDTO.getCandidateLanguageId());

		employeeLanguage.setEmployee(employee);
		employeeLanguage.setLangRead(candidateLanguageDTO.getLangRead());
		employeeLanguage.setLangSpeak(candidateLanguageDTO.getLangSpeak());
		employeeLanguage.setLangWrite(candidateLanguageDTO.getLangWrite());
		language.setLanguageId(candidateLanguageDTO.getLanguageId());
		employeeLanguage.setLanguage(language);
		return employeeLanguage;
	}


	public CandidateDTO databaseModelToCandidateDto(Employee employee,ShiftDTO shiftDto) {
		CandidateDTO candidateDto = new CandidateDTO();

		candidateDto.setCandidateId(employee.getEmployeeId());
		candidateDto.setCandidateCode(employee.getEmployeeCode());
		candidateDto.setFirstName(employee.getFirstName());
		candidateDto.setMiddleName(employee.getMiddleName());
		candidateDto.setLastName(employee.getLastName());
		 candidateDto.setMobile(employee.getContactNo());
		 candidateDto.setAdharNumber(employee.getAdharNumber());
		 candidateDto.setLeaveSchemeId(employee.getLeaveSchemeId());
		
		 candidateDto.setAttendanceSchemeId(employee.getAttendanceSchemeId());
		 candidateDto.setEmailId(employee.getPersonalEmail());
		 candidateDto.setBiometricId(employee.getBiometricId());
		 candidateDto.setOfficialEmail(employee.getOfficialEmail());
		if (employee.getCity() != null)
			candidateDto.setCityId(employee.getCity().getCityId());
		
//		if(employee.getState()!=null)
//			candidateDto.setStateId(employee.getState().getStateId());
//		 candidateDto.setShiftId(shiftDto.getShiftId());
//		 candidateDto.setShiftName(shiftDto.getShiftFName());
		// candidateDto.setPatternId(employee.getPatternId());
		// candidateDto.setCandidateCode(employee.getEmployeeCode());
		if (employee.getReportingToEmployee() != null)
			candidateDto.setReportingToEmployee(employee.getReportingToEmployee().toString());
		candidateDto.setDateOfJoining(employee.getDateOfJoining());
		candidateDto.setContractStartDate(employee.getContractStartDate());
		candidateDto.setContractOverDate(employee.getContractOverDate());
		// candidateDto.setTimeContract(employee.getTimeContract());
		// candidateDto.setEmailId(employee.getEmailId());
		candidateDto.setDateOfBirth(employee.getDateOfBirth());
		candidateDto.setGender(employee.getGender());
		candidateDto.setGenderValue(DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(),
				employee.getGender()));
		candidateDto.setActiveStatus(employee.getActiveStatus());
		candidateDto.setAdharNumber(employee.getAdharNumber());
		candidateDto.setAnniversaryDate(employee.getAnniversaryDate());
		candidateDto.setBloodGroup(employee.getBloodGroup());
		candidateDto.setBloodGroupValue(DropDownCache.getInstance()
				.getDropDownValue(DropDownEnum.BloodGroup.getDropDownName(), employee.getBloodGroup()));
		// candidateDto.setCandidateStatus(candidate.getCandidateStatus());
		candidateDto.setCandidateLogoPath(employee.getEmployeeLogoPath());

		if (employee.getMaritalStatus() != null) {
			candidateDto.setMaritalStatus(employee.getMaritalStatus());
			candidateDto.setMaritalStatusValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), employee.getMaritalStatus()));
		}

		candidateDto.setNoticePeriodDays(employee.getNoticePeriodDays());
		
		candidateDto.setAlternateNumber(employee.getAlternateNumber());
		candidateDto.setTimeContract(employee.getTimeContract());

		if (employee.getTimeContract().equals("FT")) {
			candidateDto.setTimeContractValue("Full Time");
		} else
			candidateDto.setTimeContractValue("Part Time");

		if (employee.getDepartment() != null) {
			candidateDto.setDepartmentId(employee.getDepartment().getDepartmentId());
			candidateDto.setDepartmentName(employee.getDepartment().getDepartmentName());
		}
		
		if (employee.getBranch() != null) {
			 candidateDto.setBranchId(employee.getBranch().getBranchId());
		}
	
		// candidateDto.setCheckApproach(candidate.getCheckApproach());

		System.out.println(candidateDto.getCheckApproach());
		// if(employee.getTimeContract()=="FT") {
		// candidateDto.setTimeContractValue("Full Time");
		// }
		// else
		// candidateDto.setTimeContractValue("Part Time");
		System.out.println("timecontract value.." + candidateDto.getTimeContractValue());
		if (employee.getDesignation() != null) {
			candidateDto.setDesignationId((employee.getDesignation().getDesignationId()));
			candidateDto.setDesignationName(employee.getDesignation().getDesignationName());
		}
		System.out.println("candidateDto.designation.." + candidateDto.getDesignationName());
		if (employee.getAddress1() != null) {
			candidateDto.setPermanentAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress1()));
			candidateDto.setPermanentAddressValue(
					employee.getAddress1().getAddressText() + "," + employee.getAddress1().getCity().getCityName() + ","
							+ employee.getAddress1().getState().getStateName());

		}
		if (employee.getAddress2() != null) {
			candidateDto.setPresentAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress2()));
			candidateDto.setPresentAddressValue(
					employee.getAddress2().getAddressText() + "," + employee.getAddress2().getCity().getCityName() + ","
							+ employee.getAddress2().getState().getStateName());

		}
		if (employee.getAddress3() != null) {
			candidateDto.setReferenceAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress3()));
			candidateDto.setReferenceAddressValue(
					employee.getAddress3().getAddressText() + "," + employee.getAddress3().getCity().getCityName() + ","
							+ employee.getAddress3().getState().getStateName());
		}
		candidateDto.setReferenceName(employee.getReferenceName());
		System.out.println(employee.getEmployeeLanguages());
		if (employee.getEmployeeLanguages() != null)
			candidateDto.setCandidateLanguageDto(languageModelListToUiDtoList(employee.getEmployeeLanguages()));
		candidateDto.setShiftId(employee.getShiftId());
		candidateDto.setPatternId(employee.getPatternId());
		System.out.println("employee.."+candidateDto.toString());
		return candidateDto;
	}

	public List<CandidateLanguageDTO> languageModelListToUiDtoList(List<EmployeeLanguage> employeeLanguageList) {

		List<CandidateLanguageDTO> candidateLanguageDTOList = new ArrayList<CandidateLanguageDTO>();
		for (EmployeeLanguage employeeLanguage : employeeLanguageList) {
			candidateLanguageDTOList.add(languageModelToUiDto(employeeLanguage));
		}

		return candidateLanguageDTOList;
	}

	public CandidateLanguageDTO languageModelToUiDto(EmployeeLanguage employeeLanguage) {
		CandidateLanguageDTO candidateLanguageDTO = new CandidateLanguageDTO();
		candidateLanguageDTO.setCandidateLanguageId(employeeLanguage.getEmployeeLanguageId());
		candidateLanguageDTO.setCandidatePersonalId(employeeLanguage.getEmployee().getEmployeeId());
		candidateLanguageDTO.setLanguageName(employeeLanguage.getLanguage().getName());
		candidateLanguageDTO.setLanguageId(employeeLanguage.getLanguage().getLanguageId());
		candidateLanguageDTO.setLangRead(employeeLanguage.getLangRead());
		candidateLanguageDTO.setLangWrite(employeeLanguage.getLangWrite());
		candidateLanguageDTO.setLangSpeak(employeeLanguage.getLangSpeak());

		return candidateLanguageDTO;
	}

	@Override
	public Employee uiDtoToDatabaseModel(CandidateDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidateDTO databaseModelToUiDto(Employee employee) {

		CandidateDTO candidateDto = new CandidateDTO();

		candidateDto.setCandidateId(employee.getEmployeeId());
		candidateDto.setFirstName(employee.getFirstName());
		candidateDto.setMiddleName(employee.getMiddleName());
		candidateDto.setLastName(employee.getLastName());
		candidateDto.setOfficialEmail(employee.getOfficialEmail());
		// candidateDto.setMobile(employee.getMobile());
		if(employee.getCity()!=null)
		candidateDto.setCityId(employee.getCity().getCityId());
		// candidateDto.setShiftId(employee.getS);
		// candidateDto.setPatternId(employee.getPatternId());
		//candidateDto.setCandidateCode(employee.getEmployeeCode());
		if(employee.getReportingToEmployee()!=null)
		candidateDto.setReportingToEmployee(employee.getReportingToEmployee().toString());
		candidateDto.setDateOfJoining(employee.getDateOfJoining());
		candidateDto.setContractStartDate(employee.getContractStartDate());
		candidateDto.setContractOverDate(employee.getContractOverDate());
		// candidateDto.setTimeContract(employee.getTimeContract());
		// candidateDto.setEmailId(employee.getEmailId());
		candidateDto.setDateOfBirth(employee.getDateOfBirth());
		candidateDto.setGender(employee.getGender());
		candidateDto.setGenderValue(DropDownCache.getInstance().getDropDownValue(DropDownEnum.Gender.getDropDownName(),
				employee.getGender()));
		candidateDto.setActiveStatus(employee.getActiveStatus());
		candidateDto.setAdharNumber(employee.getAdharNumber());
		candidateDto.setAnniversaryDate(employee.getAnniversaryDate());
		candidateDto.setBloodGroup(employee.getBloodGroup());
		candidateDto.setBloodGroupValue(DropDownCache.getInstance().getDropDownValue(DropDownEnum.BloodGroup.getDropDownName(),
				employee.getBloodGroup()));
		// candidateDto.setCandidateStatus(candidate.getCandidateStatus());
		candidateDto.setCandidateLogoPath(employee.getEmployeeLogoPath());
		if (employee.getMaritalStatus() != null) {
			candidateDto.setMaritalStatus(employee.getMaritalStatus());
			candidateDto.setMaritalStatusValue(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.MaritalStatus.getDropDownName(), employee.getMaritalStatus()));
		}

		candidateDto.setNoticePeriodDays(employee.getNoticePeriodDays());
		candidateDto.setCandidateCode(employee.getEmployeeCode());
		candidateDto.setAlternateNumber(employee.getAlternateNumber());
		candidateDto.setTimeContract(employee.getTimeContract());
		
		
		if (employee.getTimeContract().equals("FT")) {
			candidateDto.setTimeContractValue("Full Time");
		} else
			candidateDto.setTimeContractValue("Part Time");
		
		
		if (employee.getDepartment() != null) {
			candidateDto.setDepartmentId(employee.getDepartment().getDepartmentId());
			candidateDto.setDepartmentName(employee.getDepartment().getDepartmentName());
		}
		// candidateDto.setCheckApproach(candidate.getCheckApproach());

		System.out.println(candidateDto.getCheckApproach());
		// if(employee.getTimeContract()=="FT") {
		// candidateDto.setTimeContractValue("Full Time");
		// }
		// else
		// candidateDto.setTimeContractValue("Part Time");
		System.out.println("timecontract value.." + candidateDto.getTimeContractValue());
		if (employee.getDesignation() != null) {
			candidateDto.setDesignationId((employee.getDesignation().getDesignationId()));
			candidateDto.setDesignationName(employee.getDesignation().getDesignationName());
		}
		System.out.println("candidateDto.designation.." + candidateDto.getDesignationName());
		if (employee.getAddress1() != null) {
			candidateDto.setPermanentAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress1()));
			candidateDto.setPermanentAddressValue(employee.getAddress1().getAddressText()+","+employee.getAddress1().getCity().getCityName()+","+employee.getAddress1().getState().getStateName());

		}
		if (employee.getAddress2() != null) {
			candidateDto.setPresentAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress2() ));
			candidateDto.setPermanentAddressValue(employee.getAddress2() .getAddressText()+","+employee.getAddress2() .getCity().getCityName()+","+employee.getAddress2().getState().getStateName());

		}
		if (employee.getAddress3() != null) {
			candidateDto.setReferenceAddressDto(addressAdaptor.databaseModelToUiDto(employee.getAddress3()));
			candidateDto.setPermanentAddressValue(employee.getAddress3().getAddressText()+","+employee.getAddress3().getCity().getCityName()+","+employee.getAddress3().getState().getStateName());
		}
		candidateDto.setReferenceName(employee.getReferenceName());
		System.out.println(employee.getEmployeeLanguages());
		if(employee.getEmployeeLanguages()!=null)
			candidateDto.setCandidateLanguageDto(languageModelListToUiDtoList(employee.getEmployeeLanguages()));
		
		return candidateDto;
 	}

	public ProgressBarDTO progressBarConversion(List<Object[]> progressBarObj) {
		ProgressBarDTO progressBarDto = new ProgressBarDTO();

		Long professionalBar = 0l, idAddressProof = 0l, personalBar = 0l, educationBar = 0l, familyBar = 0l,
				statutoryBar = 0l, candidateBar = 0l,employeeBankBar = 0l;
		
		Long count=0L;
		for (Object[] objects : progressBarObj) {
			personalBar = objects[0] != null ? Long.parseLong(objects[0].toString()) : 0;
			idAddressProof = objects[1] != null ? Long.parseLong(objects[1].toString()) : 0;
			professionalBar = objects[2] != null ? Long.parseLong(objects[2].toString()) : 0;
			educationBar = objects[3] != null ? Long.parseLong(objects[3].toString()) : 0;
			familyBar = objects[4] != null ? Long.parseLong(objects[4].toString()) : 0;
			statutoryBar = objects[5] != null ? Long.parseLong(objects[5].toString()) : 0;
			candidateBar = objects[6] != null ? Long.parseLong(objects[6].toString()) : 0;
			employeeBankBar = objects[7] != null ? Long.parseLong(objects[7].toString()) : 0;
			
			
			if (personalBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (idAddressProof != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (professionalBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (educationBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (familyBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (statutoryBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (candidateBar != 0L) {
				++count;
			} else {
				count = 0L;
			}
			if (employeeBankBar != 0L) {
				++count;
			} else {
				count = 0L;
			}


		}
		
		progressBarDto.setCount(count);
		
		Long statusCount=progressBarDto.getCount();
		
		
		Long finalCount=(long) ((12.5)*statusCount);
		progressBarDto.setFinalCount(finalCount);

		progressBarDto.setCandidateBar(candidateBar);
		progressBarDto.setPersonalBar(personalBar);
		progressBarDto.setIdAddressProof(idAddressProof);
		progressBarDto.setProfessionalBar(professionalBar);
		progressBarDto.setEducationBar(educationBar);
		progressBarDto.setFamilyBar(familyBar);
		progressBarDto.setStatutoryBar(statutoryBar);
		progressBarDto.setEmployeeBankBar(employeeBankBar);	
		
		return progressBarDto;
	}

}
