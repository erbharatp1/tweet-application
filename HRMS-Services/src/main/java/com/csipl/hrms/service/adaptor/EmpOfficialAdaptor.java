package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.candidate.CandidateOfficialInformationDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeStatuary;

public class EmpOfficialAdaptor {
	public CandidateOfficialInformationDTO databaseModelToUiDto(Employee employee,
			List<EmployeeStatuary> employeeStatuaryList) {
		CandidateOfficialInformationDTO candidateOfficialInformationDto = new CandidateOfficialInformationDTO();
		candidateOfficialInformationDto.setCandidateId(employee.getEmployeeId());

		for (EmployeeStatuary employeeStatuary : employeeStatuaryList) {
			if (employeeStatuary.getStatuaryType().equals(StatusMessage.UAN_CODE)) {
				candidateOfficialInformationDto.setUanNumber(employeeStatuary.getStatuaryNumber());
				candidateOfficialInformationDto.setUanId(employeeStatuary.getStatuaryId());
				//candidateOfficialInformationDto.setIsPfApplicable(employeeStatuary.getIsApplicable();
			}
			if (employeeStatuary.getStatuaryType().equals(StatusMessage.PF_CODE)) {
				candidateOfficialInformationDto.setPfNumber(employeeStatuary.getStatuaryNumber());
				candidateOfficialInformationDto.setPfEnrollDate(employeeStatuary.getDateFrom());
				candidateOfficialInformationDto.setPfExitDate(employeeStatuary.getDateTo());
				candidateOfficialInformationDto.setPfId(employeeStatuary.getStatuaryId());
				candidateOfficialInformationDto.setIsPfApplicable(employeeStatuary.getIsApplicable());
				if(employeeStatuary.getEmployeeFamily()!=null)
				candidateOfficialInformationDto.setPfFamilyId(employeeStatuary.getEmployeeFamily().getFamilyId());
				
			}
			if (employeeStatuary.getStatuaryType().equals(StatusMessage.ACCIDENTAL_INSURANCE_CODE)) {
				candidateOfficialInformationDto.setAccidentalInsurance(employeeStatuary.getStatuaryNumber());
				candidateOfficialInformationDto.setAiFromDate(employeeStatuary.getDateFrom());
				candidateOfficialInformationDto.setAiToDate(employeeStatuary.getDateTo());
				candidateOfficialInformationDto.setAiId(employeeStatuary.getStatuaryId());
				candidateOfficialInformationDto.setIsAiApplicable(employeeStatuary.getIsApplicable());
				if(employeeStatuary.getEmployeeFamily()!=null)
				candidateOfficialInformationDto.setAiFamilyId(employeeStatuary.getEmployeeFamily().getFamilyId());

			}
			if (employeeStatuary.getStatuaryType().equals(StatusMessage.ESIC_CODE)) {
				candidateOfficialInformationDto.setEsiNumber(employeeStatuary.getStatuaryNumber());
				candidateOfficialInformationDto.setEsiEnrollDate(employeeStatuary.getDateFrom());
				candidateOfficialInformationDto.setEsiExitDate(employeeStatuary.getDateTo());
				candidateOfficialInformationDto.setEsicId(employeeStatuary.getStatuaryId());
				candidateOfficialInformationDto.setIsEsicApplicable(employeeStatuary.getIsApplicable());
				if(employeeStatuary.getEmployeeFamily()!=null)
				candidateOfficialInformationDto.setEsicFamilyId(employeeStatuary.getEmployeeFamily().getFamilyId());

			}
			if (employeeStatuary.getStatuaryType().equals(StatusMessage.MEDICAL_INSURANCE_CODE)) {
				candidateOfficialInformationDto.setMedicalInsurance(employeeStatuary.getStatuaryNumber());
				candidateOfficialInformationDto.setMiFromDate(employeeStatuary.getDateFrom());
				candidateOfficialInformationDto.setMiToDate(employeeStatuary.getDateTo());
				candidateOfficialInformationDto.setMiId(employeeStatuary.getStatuaryId());
				candidateOfficialInformationDto.setIsMiApplicable(employeeStatuary.getIsApplicable());
				if(employeeStatuary.getEmployeeFamily()!=null)
				candidateOfficialInformationDto.setMiFamilyId(employeeStatuary.getEmployeeFamily().getFamilyId());
			}

			candidateOfficialInformationDto.setUserId(employeeStatuary.getUserId());
			candidateOfficialInformationDto.setUserIdUpdate(employeeStatuary.getUserIdUpdate());
			candidateOfficialInformationDto.setDateCreated(employeeStatuary.getDateCreated());
			candidateOfficialInformationDto.setDateUpdate(employeeStatuary.getDateUpdate());
			candidateOfficialInformationDto.setStatus(employeeStatuary.getStatus());
		}

		candidateOfficialInformationDto.setGrade(employee.getGradesId());
		candidateOfficialInformationDto.setProbationDays(employee.getProbationDays());
		candidateOfficialInformationDto.setNoticePeriod(employee.getNoticePeriodDays());
		candidateOfficialInformationDto.setBiometricId(employee.getBiometricId());
		candidateOfficialInformationDto.setOfficialEmail(employee.getOfficialEmail());
		return candidateOfficialInformationDto;
	}

	public Employee UIDtoToEmployeeModel(CandidateOfficialInformationDTO candidateOfficialInformationDto,
			Employee employee) {
		employee.setNoticePeriodDays(candidateOfficialInformationDto.getNoticePeriod());
		employee.setProbationDays(candidateOfficialInformationDto.getProbationDays());
		employee.setGradesId(candidateOfficialInformationDto.getGrade());
		employee.setRole(candidateOfficialInformationDto.getRole());
		employee.setBiometricId(candidateOfficialInformationDto.getBiometricId());
		employee.setOfficialEmail(candidateOfficialInformationDto.getOfficialEmail());
		return employee;
	}

	public List<EmployeeStatuary> UIDtoToStatutoryModelList(
			CandidateOfficialInformationDTO candidateOfficialInformationDto, Long empId) {
		List<EmployeeStatuary> empStatutoryList = new ArrayList<EmployeeStatuary>();

		if (candidateOfficialInformationDto.getPfId() != null) {
			EmployeeStatuary employeeStatuary = new EmployeeStatuary();
			System.out.println("PF notes..." + candidateOfficialInformationDto.getPfId());
			employeeStatuary.setStatuaryId(candidateOfficialInformationDto.getPfId());
			employeeStatuary.setStatuaryType(StatusMessage.PF_CODE);
			employeeStatuary.setStatuaryNumber(candidateOfficialInformationDto.getPfNumber());
			employeeStatuary.setDateFrom(candidateOfficialInformationDto.getPfEnrollDate());
			employeeStatuary.setDateTo(candidateOfficialInformationDto.getPfExitDate());
			employeeStatuary.setIsApplicable(candidateOfficialInformationDto.getIsPfApplicable());
			
			Employee employee = new Employee();
			employee.setEmployeeId(empId);
			employeeStatuary.setEmployee(employee);
			if(candidateOfficialInformationDto.getPfFamilyId()!=null) {
			EmployeeFamily employeefamily = new EmployeeFamily();
			employeefamily.setFamilyId(candidateOfficialInformationDto.getPfFamilyId());
			employeeStatuary.setEmployeeFamily(employeefamily);
			}
			empStatutoryList.add(employeeStatuary);
		}

		if (candidateOfficialInformationDto.getAiId() != null) {
			System.out.println("AI notes..." + candidateOfficialInformationDto.getAiId());
			EmployeeStatuary employeeStatuary = new EmployeeStatuary();
			employeeStatuary.setStatuaryId(candidateOfficialInformationDto.getAiId());
			employeeStatuary.setStatuaryType(StatusMessage.ACCIDENTAL_INSURANCE_CODE);
			employeeStatuary.setStatuaryNumber(candidateOfficialInformationDto.getAccidentalInsurance());
			employeeStatuary.setDateFrom(candidateOfficialInformationDto.getAiFromDate());
			employeeStatuary.setDateTo(candidateOfficialInformationDto.getAiToDate());
			employeeStatuary.setIsApplicable(candidateOfficialInformationDto.getIsAiApplicable());
			Employee employee = new Employee();
			employee.setEmployeeId(empId);
			employeeStatuary.setEmployee(employee);
			if(candidateOfficialInformationDto.getAiFamilyId()!=null) {
			EmployeeFamily employeefamily = new EmployeeFamily();
			employeefamily.setFamilyId(candidateOfficialInformationDto.getAiFamilyId());
			employeeStatuary.setEmployeeFamily(employeefamily);
			}
			empStatutoryList.add(employeeStatuary);
		}
		if (candidateOfficialInformationDto.getUanId() != null) {
			System.out.println("UN notes..." + candidateOfficialInformationDto.getUanId());

			EmployeeStatuary employeeStatuary = new EmployeeStatuary();
			employeeStatuary.setStatuaryId(candidateOfficialInformationDto.getUanId());
			employeeStatuary.setStatuaryType(StatusMessage.UAN_CODE);
			employeeStatuary.setStatuaryNumber(candidateOfficialInformationDto.getUanNumber());
			employeeStatuary.setIsApplicable(candidateOfficialInformationDto.getIsPfApplicable());
			employeeStatuary.setDateFrom(candidateOfficialInformationDto.getPfEnrollDate());
			employeeStatuary.setDateTo(candidateOfficialInformationDto.getPfExitDate());
			Employee employee = new Employee();
			employee.setEmployeeId(empId);
			employeeStatuary.setEmployee(employee);
			if(candidateOfficialInformationDto.getPfFamilyId()!=null) {
			EmployeeFamily employeefamily = new EmployeeFamily();
			employeefamily.setFamilyId(candidateOfficialInformationDto.getPfFamilyId());
			employeeStatuary.setEmployeeFamily(employeefamily);
			}
			empStatutoryList.add(employeeStatuary);
		}
		if (candidateOfficialInformationDto.getEsicId() != null) {
			System.out.println("ESIC notes..." + candidateOfficialInformationDto.getEsicId());
			EmployeeStatuary employeeStatuary = new EmployeeStatuary();
			employeeStatuary.setStatuaryId(candidateOfficialInformationDto.getEsicId());
			employeeStatuary.setStatuaryType(StatusMessage.ESIC_CODE);
			employeeStatuary.setStatuaryNumber(candidateOfficialInformationDto.getEsiNumber());
			employeeStatuary.setDateFrom(candidateOfficialInformationDto.getEsiEnrollDate());
			employeeStatuary.setDateTo(candidateOfficialInformationDto.getEsiExitDate());
			if(candidateOfficialInformationDto.getEsicFamilyId()!=null) {
			EmployeeFamily employeefamily = new EmployeeFamily();
			employeefamily.setFamilyId(candidateOfficialInformationDto.getEsicFamilyId());
			employeeStatuary.setEmployeeFamily(employeefamily);
			}
			System.out.println("esicApplicabler -------->"+candidateOfficialInformationDto.getIsEsicApplicable());
			
			employeeStatuary.setIsApplicable(candidateOfficialInformationDto.getIsEsicApplicable());

			Employee employee = new Employee();
			employee.setEmployeeId(empId);
			employeeStatuary.setEmployee(employee);
			empStatutoryList.add(employeeStatuary);
		}
		if (candidateOfficialInformationDto.getMiId() != null) {
			System.out.println("MI notes..." + candidateOfficialInformationDto.getMiId());

			EmployeeStatuary employeeStatuary = new EmployeeStatuary();
			employeeStatuary.setStatuaryId(candidateOfficialInformationDto.getMiId());
			employeeStatuary.setStatuaryType(StatusMessage.MEDICAL_INSURANCE_CODE);
			employeeStatuary.setStatuaryNumber(candidateOfficialInformationDto.getMedicalInsurance());
			employeeStatuary.setDateFrom(candidateOfficialInformationDto.getMiFromDate());
			employeeStatuary.setDateTo(candidateOfficialInformationDto.getMiToDate());
			employeeStatuary.setIsApplicable(candidateOfficialInformationDto.getIsMiApplicable());
			Employee employee = new Employee();
			employee.setEmployeeId(empId);
			employeeStatuary.setEmployee(employee);
			if(candidateOfficialInformationDto.getMiFamilyId()!=null) {
			EmployeeFamily employeefamily = new EmployeeFamily();
			employeefamily.setFamilyId(candidateOfficialInformationDto.getMiFamilyId());
			employeeStatuary.setEmployeeFamily(employeefamily);
			}
			empStatutoryList.add(employeeStatuary);
		}

		for (EmployeeStatuary employeeStatuary : empStatutoryList) {
			/*
			 * Employee employee = new Employee(); employee.setEmployeeId(employeeId);
			 * employeeStatuary.setEmployee(employee);
			 */
			employeeStatuary.setUserId(candidateOfficialInformationDto.getUserId());
			employeeStatuary.setUserIdUpdate(candidateOfficialInformationDto.getUserId());
			employeeStatuary.setDateCreated(candidateOfficialInformationDto.getDateCreated());
			employeeStatuary.setDateUpdate(new Date());
		}
		return empStatutoryList;
	}

	public List<EmployeeStatuary> databaseModelTodbModelList(List<EmployeeStatuary> empStaturyList) {
		List<EmployeeStatuary> empStatutoryNewList = new ArrayList<EmployeeStatuary>();
		
//		for (EmployeeStatuary employeeNewStatuary : empStaturyList) {
//			EmployeeStatuary employeeStatuary = new EmployeeStatuary ();
//			employeeStatuary.setStatuaryId(null);
//			employeeStatuary.setStatuaryType(employeeNewStatuary.getStatuaryType());
//			employeeStatuary.setStatuaryNumber(employeeNewStatuary.getStatuaryNumber());
//			employeeStatuary.setDateFrom(employeeNewStatuary.getDateFrom());
//			employeeStatuary.setDateTo(employeeNewStatuary.getDateTo());
//			employeeStatuary.setIsApplicable(employeeNewStatuary.getIsApplicable());
//			Employee employee = new Employee();
//			employee.setEmployeeId(employeeNewStatuary.getEmployee().getEmployeeId());
//			employeeStatuary.setEmployee(employee);
//			employeeStatuary.setUserId(employeeNewStatuary.getUserId());
//			employeeStatuary.setUserIdUpdate(employeeNewStatuary.getUserId());
//			employeeStatuary.setDateCreated(employeeNewStatuary.getDateCreated());
//			employeeStatuary.setDateUpdate(new Date());
//			employeeStatuary.setStatus("AC");
//			employeeStatuary.setEffectiveStartDate(new Date());
//			empStatutoryNewList.add(employeeStatuary);
//		}
		
		return empStatutoryNewList;
		
	}

	public List<EmployeeStatuary> databaseModelTodbModelList(
			CandidateOfficialInformationDTO candidateOfficialInfo, Long employeeId) {
		
		List<EmployeeStatuary> employeeStatuaryList = new ArrayList<EmployeeStatuary>();
		//if (candidateOfficialInfo.getUanNumber() != null && !candidateOfficialInfo.getUanNumber().equals("")) {
			EmployeeStatuary employeeStatuary1 = new EmployeeStatuary();
			employeeStatuary1.setStatuaryNumber(candidateOfficialInfo.getUanNumber());
			employeeStatuary1.setStatuaryType("UA");
			employeeStatuary1.setStatus("AC");
			employeeStatuary1.setEffectiveStartDate(new Date());
			employeeStatuary1.setDateFrom(candidateOfficialInfo.getPfEnrollDate());
			employeeStatuary1.setDateTo(candidateOfficialInfo.getPfExitDate());
			if(candidateOfficialInfo.getPfFamilyId()!=null) {
				EmployeeFamily employeefamily = new EmployeeFamily();
				employeefamily.setFamilyId(candidateOfficialInfo.getPfFamilyId());
				employeeStatuary1.setEmployeeFamily(employeefamily);
				}
			System.out.println("IsPfApplicable----------------------------"+candidateOfficialInfo.getIsPfApplicable());
			if( candidateOfficialInfo.getIsPfApplicable()!=null)
			employeeStatuary1.setIsApplicable( candidateOfficialInfo.getIsPfApplicable());
			else
				employeeStatuary1.setIsApplicable( "N");
			// employeeStatuary.setDateTo(dateTo);
			employeeStatuaryList.add(employeeStatuary1);
		//}

		//if (candidateOfficialInfo.getPfNumber() != null && !candidateOfficialInfo.getPfNumber().equals("")) {
			EmployeeStatuary employeeStatuary2 = new EmployeeStatuary();
			employeeStatuary2.setStatuaryNumber(candidateOfficialInfo.getPfNumber());
			employeeStatuary2.setStatuaryType("PF");
			employeeStatuary2.setStatus("AC");
			employeeStatuary2.setEffectiveStartDate(new Date());
			employeeStatuary2.setDateFrom(candidateOfficialInfo.getPfEnrollDate());
			employeeStatuary2.setDateTo(candidateOfficialInfo.getPfExitDate());
			if(candidateOfficialInfo.getPfFamilyId()!=null) {
				EmployeeFamily employeefamily = new EmployeeFamily();
				employeefamily.setFamilyId(candidateOfficialInfo.getPfFamilyId());
				employeeStatuary2.setEmployeeFamily(employeefamily);
				}
			System.out.println("IsPfApplicable----------------------"+candidateOfficialInfo.getIsPfApplicable());
			if( candidateOfficialInfo.getIsPfApplicable()!=null)
				employeeStatuary2.setIsApplicable( candidateOfficialInfo.getIsPfApplicable());
				else
					employeeStatuary2.setIsApplicable( "N");
			
			// employeeStatuary.setDateTo(dateTo);
			employeeStatuaryList.add(employeeStatuary2);
		//}

		//if (candidateOfficialInfo.getAccidentalInsurance() != null
				//&& !candidateOfficialInfo.getAccidentalInsurance().equals("")) {
			EmployeeStatuary employeeStatuary3 = new EmployeeStatuary();
			employeeStatuary3.setStatuaryNumber(candidateOfficialInfo.getAccidentalInsurance());
			employeeStatuary3.setStatuaryType("AC");
			employeeStatuary3.setStatus("AC");
			employeeStatuary3.setEffectiveStartDate(new Date());
			employeeStatuary3.setDateFrom(candidateOfficialInfo.getAiFromDate());
			employeeStatuary3.setDateTo(candidateOfficialInfo.getAiToDate());
			if(candidateOfficialInfo.getAiFamilyId()!=null) {
				EmployeeFamily employeefamily = new EmployeeFamily();
				employeefamily.setFamilyId(candidateOfficialInfo.getAiFamilyId());
				employeeStatuary3.setEmployeeFamily(employeefamily);
				}
			System.out.println("IsAiApplicable----------------------"+candidateOfficialInfo.getIsAiApplicable());
			if(candidateOfficialInfo.getIsAiApplicable()!=null)
			employeeStatuary3.setIsApplicable( candidateOfficialInfo.getIsAiApplicable());
			else
				employeeStatuary3.setIsApplicable( "N");
			employeeStatuaryList.add(employeeStatuary3);
		//}

		//if (candidateOfficialInfo.getEsiNumber() != null && !candidateOfficialInfo.getEsiNumber().equals("")) {
			EmployeeStatuary employeeStatuary4 = new EmployeeStatuary();
			employeeStatuary4.setStatuaryNumber(candidateOfficialInfo.getEsiNumber());
			employeeStatuary4.setStatuaryType("ES");
			employeeStatuary4.setStatus("AC");
			employeeStatuary4.setEffectiveStartDate(new Date());
			employeeStatuary4.setDateFrom(candidateOfficialInfo.getEsiEnrollDate());
			employeeStatuary4.setDateTo(candidateOfficialInfo.getEsiExitDate());
			System.out.println("IsEsicApplicable----------------------"+candidateOfficialInfo.getIsEsicApplicable());
			if(candidateOfficialInfo.getEsicFamilyId()!=null) {
				EmployeeFamily employeefamily = new EmployeeFamily();
				employeefamily.setFamilyId(candidateOfficialInfo.getEsicFamilyId());
				employeeStatuary4.setEmployeeFamily(employeefamily);
				}
			if(candidateOfficialInfo.getIsEsicApplicable()!=null)
			employeeStatuary4.setIsApplicable( candidateOfficialInfo.getIsEsicApplicable());
			else
				employeeStatuary4.setIsApplicable( "N");
			// employeeStatuary.setDateTo(dateTo);
			employeeStatuaryList.add(employeeStatuary4);
		//}

	///	if (candidateOfficialInfo.getMedicalInsurance() != null
				//&& !candidateOfficialInfo.getMedicalInsurance().equals("")) {
			EmployeeStatuary employeeStatuary5 = new EmployeeStatuary();
			employeeStatuary5.setStatuaryNumber(candidateOfficialInfo.getMedicalInsurance());
			employeeStatuary5.setStatuaryType("ME");
			employeeStatuary5.setStatus("AC");
			employeeStatuary5.setEffectiveStartDate(new Date());
			employeeStatuary5.setDateFrom(candidateOfficialInfo.getMiFromDate());
			System.out.println("IsMiApplicable----------------------"+candidateOfficialInfo.getIsMiApplicable());
			employeeStatuary5.setDateTo(candidateOfficialInfo.getMiToDate());
			if(candidateOfficialInfo.getMiFamilyId()!=null) {
				EmployeeFamily employeefamily = new EmployeeFamily();
				employeefamily.setFamilyId(candidateOfficialInfo.getMiFamilyId());
				employeeStatuary5.setEmployeeFamily(employeefamily);
				}
			if(candidateOfficialInfo.getIsMiApplicable()!=null)
			employeeStatuary5.setIsApplicable( candidateOfficialInfo.getIsMiApplicable());
			else
				employeeStatuary5.setIsApplicable( "N");	
			employeeStatuaryList.add(employeeStatuary5);
		//}

		for (EmployeeStatuary employeeStatuary : employeeStatuaryList) {
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employeeStatuary.setUserId(candidateOfficialInfo.getUserId());
			employeeStatuary.setUserIdUpdate(candidateOfficialInfo.getUserId());
			employeeStatuary.setDateCreated(candidateOfficialInfo.getDateCreated());
			employeeStatuary.setDateUpdate(new Date());
			employeeStatuary.setEmployee(employee);
		}

		return employeeStatuaryList;
	}
}
