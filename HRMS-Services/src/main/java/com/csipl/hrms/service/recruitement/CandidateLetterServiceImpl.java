package com.csipl.hrms.service.recruitement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.recruitment.CandidateLetter;
import com.csipl.hrms.model.recruitment.CandidatePayStructure;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.payroll.repository.PayHeadRepository;
import com.csipl.hrms.service.recruitement.repository.CandidateFinalEvolutionRepository;
import com.csipl.hrms.service.recruitement.repository.CandidateLetterRepository;
import com.csipl.hrms.service.recruitement.repository.CandidatePayStructureHdRepository;
import com.csipl.hrms.service.recruitement.repository.CandidatePayStructureRepository;

@Service
@Transactional
public class CandidateLetterServiceImpl implements CandidateLetterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateLetterServiceImpl.class);

	@Autowired
	private CandidateLetterRepository candidateLetterRepository;

	@Autowired
	private CandidatePayStructureHdRepository candidatePayStructureHdRepository;

	@Autowired
	private CandidatePayStructureRepository candidatePayStructureRepository;

	@Autowired
	private LetterService letterService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private InterviewSchedulerService interviewSchedulerService;

	@Autowired
	private PayHeadRepository payHeadRepository;

	@Autowired
	CandidateFinalEvolutionRepository candidateFinalEvolutionRepository;

	@Override
	public CandidateLetter saveCandidateLetter(CandidateLetter candidateLetter) {

		return candidateLetterRepository.save(candidateLetter);
	}

	@Override
	public CandidateLetter getCandidateLetterById(Long interviewScheduleId) {

		return candidateLetterRepository.findCandidateLetterById(interviewScheduleId);
	}

	@Override
	public CandidateLetter getOfferLetterId() {

		return candidateLetterRepository.findOfferLetterId();
	}

	@Override
	public void generateCandidateLetter(Long companyId, Long interviewScheduleId) throws ErrorHandling {

		CandidatePayStructureHd payStructureHd = null;

		// TODO Auto-generated method stub
		LOGGER.info("-----------------=Start===CandidateOfferLetter====-----------");
		Letter ltr = null;
		Long count = 0l;
		count = candidatePayStructureHdRepository.checkEmployeePayStructure(interviewScheduleId);

		if (count == 1l) {
			payStructureHd = candidatePayStructureHdRepository.monthValidationList(interviewScheduleId);
			ltr = letterService.findLetterByType(StatusMessage.CANDIDATE_OFFER_LETTER_CODE);
		} else {
			payStructureHd = candidatePayStructureHdRepository.monthValidationList(interviewScheduleId);
			ltr = letterService.findLetterByType(StatusMessage.CANDIDATE_OFFER_LETTER_CODE);
		}

		if (ltr != null) {

			// CandidateLetter employeeLetter =
			// empLetterService.findEmpLetterByStatus(employeeLetterId, ltr.getLetterId());
			CandidateLetter empLetter = new CandidateLetter();
			Company company = companyService.getCompany(companyId);
			InterviewScheduler interviewScheduler = interviewSchedulerService.findCandidateById(interviewScheduleId);
			Position position = positionService.findPositionById(interviewScheduler.getPosition().getPositionId());
//			Employee employee = employeePersonalInformationService
//					.getEmployeeInfo(payStructureHd.getEmployee().getEmployeeId());
//			empLetter.setEmpId(employee.getEmployeeId());
//
//			empLetter.setEmpLetterId(employeeLetter.getEmpLetterId());
			// employeeLetter.setLetterDecription(ltr.getLetterDecription());
			SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
			String effictive = sm.format(payStructureHd.getEffectiveDate());
			String currentDT = sm.format(new Date());
			String doj = sm.format(payStructureHd.getEffectiveDate());

			BigDecimal basic_year = new BigDecimal(0);
			BigDecimal hra = new BigDecimal(0);
			int count1 = 0;
			List<CandidatePayStructure> list = candidatePayStructureRepository
					.findAllPayStructureById(payStructureHd.getCandidatePaystructureHdId());
			for (CandidatePayStructure item : list) {
				if (count1 == 0) {
					basic_year = item.getAmount();
					count1++;
				}
				hra = item.getAmount();

			}

			BigDecimal professinal_Tx = new BigDecimal(0);
			BigDecimal professinal_Tx_Year = new BigDecimal(0);
			BigDecimal employee_PF = new BigDecimal(0);
			BigDecimal employee_PF_year = new BigDecimal(0);
			BigDecimal employer_PF = new BigDecimal(0);
			BigDecimal employer_PF_year = new BigDecimal(0);
			// BigDecimal employee_HRA = new BigDecimal(0);
			// BigDecimal employee_HRA_Year = new BigDecimal(0);
			BigDecimal employee_Esic = new BigDecimal(0);
			BigDecimal employee_Esic_Year = new BigDecimal(0);
			BigDecimal employer_Esic = new BigDecimal(0);
			BigDecimal employer_Esic_Year = new BigDecimal(0);
			// BigDecimal current_Ad_Bonus = new BigDecimal(0);
			// BigDecimal current_Ad_Bonus_Year = new BigDecimal(0);
			BigDecimal gross_Salary_Year = new BigDecimal(0);
			BigDecimal current_CTC_Year = new BigDecimal(0);

			basic_year.add(basic_year.multiply(new BigDecimal(12)));

			if (payStructureHd.getProfessionalTax() != null) {
				professinal_Tx = professinal_Tx.add(payStructureHd.getProfessionalTax());
				professinal_Tx_Year = professinal_Tx_Year
						.add(payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEpfEmployee() != null) {
				employee_PF = employee_PF.add(payStructureHd.getEpfEmployee());
				employee_PF_year = employee_PF_year.add(payStructureHd.getEpfEmployee().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEpfEmployer() != null) {
				employer_PF = employer_PF.add(payStructureHd.getEpfEmployer());
				employer_PF_year = employer_PF_year.add(payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)));
			}

			if (payStructureHd.getEsiEmployer() != null) {
				employer_Esic = employer_Esic.add(payStructureHd.getEsiEmployer());
				employer_Esic_Year = employer_Esic_Year
						.add(payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)));
			}
			if (payStructureHd.getEsiEmployee() != null) {
				employee_Esic = employee_Esic.add(payStructureHd.getEsiEmployee());
				employee_Esic_Year = employee_Esic_Year
						.add(payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)));
			}

			gross_Salary_Year.add(payStructureHd.getGrossPay().multiply(new BigDecimal(12)));
			current_CTC_Year.add(payStructureHd.getCtc().multiply(new BigDecimal(12)));
			/**
			 * new code
			 */
			String desc1 = ltr.getLetterDecription().replace(StatusMessage.EMPLOYEE_NAME,
					interviewScheduler.getCandidateName());
			String desc2 = desc1.replace(StatusMessage.LOCATION, position.getJobLocation());
			String desc3 = desc2.replace(StatusMessage.LETTER_NAME, ltr.getLetterName());
			String desc4 = desc3.replace(StatusMessage.COMPANY_NAME, company.getCompanyName());
			String desc5 = desc4.replace(StatusMessage.DESIGNATION_NAME, position.getPositionTitle());
			String desc6 = desc5.replace(StatusMessage.EFFECTIVE_DATE, effictive);
			String desc7 = desc6.replace(StatusMessage.CURRENT_DATE, currentDT);
			String desc8 = desc7.replace(StatusMessage.EMPLOYEE_BASIC, payStructureHd.getNetPay().toString());
			String desc9 = desc8.replace(StatusMessage.DATE_OF_JOINING, doj);
			String desc10 = desc9.replace(StatusMessage.EMPLOYEE_ESIC, employee_Esic.toString());

			StringBuilder sb1 = new StringBuilder(desc10);

			String desc11 = desc10.concat(
					"<p style=\"margin:0;font-size: 11px;line-height: 16px;\">&nbsp;&nbsp;<strong style=\"margin:0;font-size: 11px;line-height: 16px;\"> ANNEXURE – I</strong></p>");

			String desc12 = desc11.concat(
					"<br><table border='1'><tbody><tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Salary Component</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Monthly</strong></span></td><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Amount Annually</strong></span></td></tr>");

			StringBuilder sb = new StringBuilder(desc12);

			for (CandidatePayStructure pay : list) {
				pay.setPayHead(payHeadRepository.findOne(pay.getPayHead().getPayHeadId()));
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
								+ pay.getPayHead().getPayHeadName() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pay.getAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pay.getAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");

			}

			if (payStructureHd.getGrossPay() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Gross</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getGrossPay() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getGrossPay().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEpfEmployee() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>PF-Employee </span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployee() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployee().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getEpfEmployer() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>PF-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployer() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEpfEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEpfEmployer() != null || payStructureHd.getEpfEmployer() != null) {
				BigDecimal pensionEmp = new BigDecimal(0);
				BigDecimal empr = new BigDecimal(0);
				BigDecimal emp = new BigDecimal(0);
				emp = payStructureHd.getEpfEmployee();
				empr = payStructureHd.getEpfEmployer();
				pensionEmp = emp.subtract(empr);
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Pension-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pensionEmp + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ pensionEmp.multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getEsiEmployee() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employee</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployee() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployee().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getEsiEmployer() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>ESI-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployer() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getEsiEmployer().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getProfessionalTax() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>Professional Tax</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getProfessionalTax() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getProfessionalTax().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getLwfEmployeeAmount() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>LWF-Employee</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployeeAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployeeAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}
			if (payStructureHd.getLwfEmployerAmount() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>LWF-Employer</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployerAmount() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getLwfEmployerAmount().multiply(new BigDecimal(12)) + "</span></td></tr>");
			}

			if (payStructureHd.getNetPay() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>Nat Payable</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getNetPay().toBigInteger() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getNetPay().multiply(new BigDecimal(12)).toBigInteger() + "</span></td></tr>");
			}

			if (payStructureHd.getCtc() != null) {
				sb.append(
						"<tr><td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span><strong>CTC</strong></span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getCtc().toBigInteger() + "</span></td>");
				sb.append("<td class=\"ck-editor__editable ck-editor__nested-editable\" contenteditable=\"true\"><span>"
						+ payStructureHd.getCtc().multiply(new BigDecimal(12)).toBigInteger() + "</span></td></tr>");
			}
			if (ltr.getLetterName().equals("Candidate Offer Letter")) {

				sb.append(
						"</tr></tbody></table><br><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>DECLARATION BY THE EMPLOYEE&nbsp;</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;I have read and understood the above terms and conditions of services and agreed without any coercion or pressure to abide by the same.</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p><p>Date: <strong>"
								+ currentDT + "</strong></p><p>Place: <strong>" + position.getJobLocation()
								+ "</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></div>");

				sb1.append(
						"</tr></tbody></table><br><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>DECLARATION BY THE EMPLOYEE&nbsp;</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;I have read and understood the above terms and conditions of services and agreed without any coercion or pressure to abide by the same.</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p><p>Date: <strong>"
								+ currentDT + "</strong></p><p>Place: <strong>" + position.getJobLocation()
								+ "</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></div>");
			}

			empLetter.setHideAnnexure(sb1.toString());
			empLetter.setLetterDescription(sb.toString());
			empLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
			InterviewScheduler ish = new InterviewScheduler();
			ish.setInterviewScheduleId(interviewScheduleId);
			empLetter.setInterviewScheduler(ish);
			Letter letter = new Letter();
			letter.setLetterId(ltr.getLetterId());
			empLetter.setLetter(letter);
			empLetter.setCandidateStatus(StatusMessage.PENDING_CODE);
			empLetter.setDateCreated(new Date());
			empLetter.setDeclerationDate(new Date());
			empLetter.setUserId(ltr.getUserId());
			empLetter.setHrStatus(StatusMessage.PENDING_CODE);
			empLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);

			empLetter = candidateLetterRepository.save(empLetter);

			LOGGER.info("generate Candidate Letter Successfully");
		} else
			throw new ErrorHandling("Letter not create Please contact to Admin");

	}

	@Override
	public CandidateLetter getCandidateOfferLetterById(Long candidateLetterId) {

		return candidateLetterRepository.findOne(candidateLetterId);
	}

	@Override
	public void updateDeclarationStatus(Long candidateLetterId, String declerationStatus, Long interviewScheduleId) {

		candidateLetterRepository.updateDeclarationStatus(candidateLetterId, declerationStatus, interviewScheduleId,
				new Date());
	}

	@Override
	public void updateSelectedAnnexure(Long candidateLetterId, String annexureStatus, Long interviewScheduleId) {

		candidateLetterRepository.updateSelectedAnnexure(candidateLetterId, annexureStatus, interviewScheduleId);
	}

}
