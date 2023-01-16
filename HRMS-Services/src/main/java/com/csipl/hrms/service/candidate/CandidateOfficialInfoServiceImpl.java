package com.csipl.hrms.service.candidate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.candidate.Candidate;
import com.csipl.hrms.model.candidate.CandidateEducation;
import com.csipl.hrms.model.candidate.CandidateFamily;
import com.csipl.hrms.model.candidate.CandidateIdProof;
import com.csipl.hrms.model.candidate.CandidateNominee;
import com.csipl.hrms.model.candidate.CandidateOfficialInformation;
import com.csipl.hrms.model.candidate.CandidatePersonal;
import com.csipl.hrms.model.candidate.CandidateProfessionalInformation;
import com.csipl.hrms.model.candidate.CandidateSkill;
import com.csipl.hrms.model.candidate.CandidateStatuary;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeBank;
import com.csipl.hrms.model.employee.EmployeeEducation;
import com.csipl.hrms.model.employee.EmployeeFamily;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.EmployeeSkill;
import com.csipl.hrms.model.employee.EmployeeStatuary;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.candidate.adaptor.CandidateToEmployeeConversionAdaptor;
import com.csipl.hrms.service.candidate.repository.CandidateOfficialInfoRepository;
import com.csipl.hrms.service.candidate.repository.CandidatePersonalRepository;
import com.csipl.hrms.service.candidate.repository.CandidateRepository;
import com.csipl.hrms.service.employee.BankDetailsService;
import com.csipl.hrms.service.employee.EmployeeEducationService;
import com.csipl.hrms.service.employee.EmployeeIdProofService;
import com.csipl.hrms.service.employee.EmployeeNomineeService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.EmployeeSkillService;
import com.csipl.hrms.service.employee.EmployeeStatuaryService;
import com.csipl.hrms.service.employee.FamilyService;
import com.csipl.hrms.service.employee.ProfessionalInformationService;
import com.csipl.hrms.service.payroll.PayrollControlService;

@Transactional
@Service("officialInfoService")
public class CandidateOfficialInfoServiceImpl implements CandidateOfficialInfoService {

	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	CandidateOfficialInfoRepository candidateOfficialInfoRepository;
	@Autowired
	CandidatePersonalRepository candidatePersonalRepository;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	@Autowired
	EmployeeIdProofService employeeIdProofService;
	@Autowired
	CandidateIdAddressService candidateIdAddressService;
	@Autowired
	CandidateProfessionalInformationService candidateProfessionalInformationService;
	@Autowired
	ProfessionalInformationService professionalInformationService;
	@Autowired
	CandidateEducationService candidateEducationService;
	@Autowired
	EmployeeEducationService employeeEducationService;
	@Autowired
	CandidateFamilyService candidateFamilyService;
	@Autowired
	FamilyService familyService;
	@Autowired
	CandidateSkillService candidateSkillService;
	@Autowired
	EmployeeSkillService employeeSkillService;
	@Autowired
	EmployeeStatuaryService employeeStatuaryService;
	@Autowired
	CandidateStatuaryService candidateStatuaryService;
	@Autowired
	BankDetailsService bankDetailsService;
	@Autowired
	EmployeeNomineeService employeeNomineeService;
	@Autowired
	CandidateNomineeService candidateNomineeService;
	@Autowired
	PayrollControlService payrollControlService;
	private static final Logger logger = LoggerFactory.getLogger(CandidateOfficialInfoServiceImpl.class);
	
	@Transactional
	@Override
	public CandidateOfficialInformation save(CandidateOfficialInformation candidateOfficialInformation,
			HttpServletRequest request) throws ErrorHandling {

		Long employeeId = 0l;
		System.out
				.println("candidateOfficialInformation" + candidateOfficialInformation.getCandidateId());
		System.out.println();
		CandidateOfficialInformation candidateOfficialInfo = candidateOfficialInfoRepository.save(candidateOfficialInformation);
		
		
		
		if (candidateOfficialInfo != null) {
			Long userId = candidateOfficialInfo.getUserId();
			Long candidateId = candidateOfficialInfo.getCandidateId();
			CandidateToEmployeeConversionAdaptor candidateToEmployeeConversionAdaptor = new CandidateToEmployeeConversionAdaptor();
		
			//status update into candidate table
			Candidate candidate = candidateRepository.findOne(candidateId);
			if(candidate == null) {
				throw new ErrorHandling(" Candidate record not found regarding candidate Id " +candidateId);
			}
			
			String psMonth = DateUtils.getDateStringWithYYYYMMDD(candidate.getDateOfJoining());
			String processMonth = psMonth.toUpperCase();
			logger.info("processMonth  " + processMonth);
			//here we will check process month is locked or not if locked throw exception
			List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
			if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
				throw new ErrorHandling(processMonth +"  is locked, Candidate can not join in this duration");
			}
			
			candidate.setCandidateStatus(StatusMessage.ONBOARD_CODE);
			candidateRepository.save(candidate);

			/*
			 * CandidatePersonal To Employee convert
			 * 
			 */

			CandidatePersonal candidatePersonal = candidatePersonalRepository.findCandidatePersonal(candidateId);
			System.out.println(candidatePersonal);
			// List<CandidateSkill> candidateSkillList =
			// candidateSkillService.getCandidateSkill(candidateId);
			
			Employee employee = candidateToEmployeeConversionAdaptor.candidateInfoToEmployeeInfoConversion(candidate,
					candidateOfficialInfo, candidatePersonal);
			
			
			System.out.println("Roles in official-------------------------------------------------"+candidateOfficialInformation.getRole());
			
			employee.setRole(candidateOfficialInformation.getRole());
			//Employee employeeObj = employeePersonalInformationService.save(employee, null, false);

			
			Employee employeeObj = employeePersonalInformationService.saveEmployee(employee);

			/*
			 * Employee common detail transform
			 * 
			 */
			// EmpCommonDetailsAdaptor empCommonDetailAdaptor = new
			// EmpCommonDetailsAdaptor();
			// EmpCommonDetail empCommonDetail =
			// empCommonDetailAdaptor.employeeToEmpCommonDetail(employeeObj);
			// empCommonDetailService.save(empCommonDetail);

			/*
			 * 
			 * CandidateIdProof To EmployeeIdProof convert
			 * 
			 */

		     employeeId = employeeObj.getEmployeeId();

			List<CandidateIdProof> candidateIdProofList = candidateIdAddressService.findAllCandidateIdProofs(candidateId);
			List<EmployeeIdProof> employeeIdProofList = candidateToEmployeeConversionAdaptor.candidateIdProofToEmployeeIdProofConversion(candidateIdProofList, candidateOfficialInfo,
							employeeId, userId);
			employeeIdProofService.saveEmployeeIdProof(employeeIdProofList);

			/*
			 * CandidateProfessionalInformation To ProfessionalInformation convert
			 * 
			 */

			List<CandidateProfessionalInformation> candidateProfessionalInformationList = candidateProfessionalInformationService.getAllCandidateProfessionalInformation(candidateId);
			
			
			List<ProfessionalInformation> employeeProfessionalInformation = candidateToEmployeeConversionAdaptor
					.candidateProfessionalInfoToEmployeeProfessionalInfoConversion(candidateProfessionalInformationList,candidateOfficialInfo, employeeId, userId);
			professionalInformationService.saveAll(employeeProfessionalInformation);
			
			
			/*
			 * CandidateSkill To EmployeeSkill convert
			 * 
			 */

			List<CandidateSkill> candidateSkillList = candidateSkillService.getCandidateSkill(candidateId);
			List<EmployeeSkill> employeeSkillList = candidateToEmployeeConversionAdaptor
					.candidateSkillToEmployeeSkillconversion(candidateSkillList, candidateOfficialInfo, employeeId,
							userId);
			System.out.println("Skill Size -" + employeeSkillList.size() + "------" + employeeSkillList.toString());
			employeeSkillService.save(employeeSkillList);

			/*
			 * CandidateEducation To EmployeeEducation convert
			 * 
			 */

			List<CandidateEducation> candidateEducationList = candidateEducationService.findAllEduInformation(candidateId);
			List<EmployeeEducation> employeeEducationList = candidateToEmployeeConversionAdaptor.candidateEducationToEmployeeEducationConversion(candidateEducationList, candidateOfficialInfo,
							employeeId, userId);
			employeeEducationService.saveEducation(employeeEducationList);

			/*
			 * CandidateFamily To EmployeeFamily convert
			 * 
			 */

			List<CandidateFamily> candidateFamilyList = candidateFamilyService.findAllFamilyList(candidateId);
			List<EmployeeFamily> employeeFamilyList = candidateToEmployeeConversionAdaptor.candidateFamilyToEmployeeFamilyConversion(candidateFamilyList, candidateOfficialInfo, employeeId,
							userId);
			familyService.saveAll(employeeFamilyList);

			/*
			 * CandidateFamily To EmployeeFamily convert
			 * 
			 */
			List<CandidateNominee> candidateNomineeList = candidateNomineeService.findAllNominee(candidateId);
			
			/*List<EmployeeNominee> employeeNomineeList = candidateToEmployeeConversionAdaptor
					.candidateNomineeToEmployeeNominee(candidateNomineeList);
			employeeNomineeService.save(employeeNomineeList);*/

		

			/*
			 * CandidateStatuary To EmployeeStatuary convert
			 * 
			 */

			List<EmployeeStatuary> employeeStatuaryList = candidateToEmployeeConversionAdaptor
					.candidateStatouryToEmployeeStatuory(candidateOfficialInfo, employeeId);
			employeeStatuaryService.save(employeeStatuaryList, employeeId);

			/*
			 * CandidateStatuary To EmployeeBank convert
			 * 
			 */
			CandidateStatuary candidateStatuary = candidateStatuaryService.findCandidateStatuary(candidateId);
			EmployeeBank employeeBank = candidateToEmployeeConversionAdaptor
					.candidateBankToEmployeeBankConversion(candidateStatuary, employeeId, userId);
			bankDetailsService.save(employeeBank);
		}
		candidateOfficialInfo.setEmployeeId(employeeId);
		return candidateOfficialInfo;
	}

	@Override
	public CandidateOfficialInformation findCandidateOfficialInformation(Long candidateId) {

		return candidateOfficialInfoRepository.findCandidateOfficialInformation(candidateId);
	}

	@Override
	public String lastEmployeeCode() {
		// TODO Auto-generated method stub
		return candidateOfficialInfoRepository.lastEmployeeCode();
	}

}
