package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.employee.Separation;
import com.csipl.hrms.model.payroll.FinalSettlement;
import com.csipl.hrms.model.payroll.FinalSettlementReport;
import com.csipl.hrms.model.payroll.Gratuaty;
import com.csipl.hrms.service.employee.ApprovalHierarchyMasterService;
import com.csipl.hrms.service.employee.EmployeeLetterService;
import com.csipl.hrms.service.employee.EmployeeLettersTransactionService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.hrms.service.employee.SeparationService;
import com.csipl.hrms.service.payroll.repository.FinalSettleRepository;
import com.csipl.hrms.service.payroll.repository.FinalSettlementReportRepository;
import com.csipl.hrms.service.payroll.repository.FinalSettlementRepository;

@Service("finalSettlementService")
public class FinalSettlementServiceImpl implements FinalSettlementService {
	private static final Logger logger = LoggerFactory.getLogger(FinalSettlementServiceImpl.class);
	@Autowired
	FinalSettlementRepository finalSettlementRepository;

	@Autowired
	FinalSettlementService finalSettlementService;

	@Autowired
	FinalSettleRepository finalSettleRepository;

	@Autowired
	FinalSettlementReportRepository finalSettlementReportRepository;

	@Autowired
	private EmployeeLetterService empLetterService;
	@Autowired
	private ApprovalHierarchyMasterService approvalHierarchyMasterService;
	@Autowired
	private LetterService letterService;
	@Autowired
	private EmployeeLettersTransactionService employeeLettersTransactionService;
	@Autowired
	private SeparationService separationService;

	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;

	@Override
	public List<Object[]> getEmployees(Long companyId) {
		// TODO Auto-generated method stub
		return finalSettlementRepository.getEmployees(companyId, StatusMessage.APPROVED_CODE);

	}

	@Override
	public BigDecimal calculateGratuity(Gratuaty gratuaty, Employee employee) {
		int experience;
		BigDecimal totalAmount = finalSettlementService.getGratuityDeduction(employee.getEmployeeId());
		BigDecimal gratAmount = totalAmount.divide(new BigDecimal(gratuaty.getNoOfDaysDevide()))
				.multiply(new BigDecimal(gratuaty.getNoOfDays()));
		Calendar dateOfJoining = Calendar.getInstance();
		dateOfJoining.setTime(employee.getDateOfJoining());
		Calendar today = new GregorianCalendar();
		today.setTime(new Date());
		int yearsInBetween = today.get(Calendar.YEAR) - dateOfJoining.get(Calendar.YEAR);
		int monthsDiff = today.get(Calendar.MONTH) - dateOfJoining.get(Calendar.MONTH);
		if (monthsDiff <= 6)
			experience = yearsInBetween;
		else
			experience = yearsInBetween + 1;

		BigDecimal gratuatyAmount = gratAmount.multiply(new BigDecimal(experience));

		return gratuatyAmount;
	}

	@Override
	public BigDecimal getGratuityDeduction(Long employeeId) {
		return finalSettlementRepository.getGratuityDeduction(employeeId);
	}

	@Override
	public FinalSettlement save(FinalSettlement finalSettlement) {
		// TODO Auto-generated method stub
		return finalSettleRepository.save(finalSettlement);
	}

	@Override
	public FinalSettlement getFinalSettlementById(Long employeeId) {
		// TODO Auto-generated method stub
		return finalSettleRepository.getFinalSettlementById(employeeId);
	}

	@Override
	public List<Object[]> getFinalSettlementEmployee(Long companyId, String status) {
		// TODO Auto-generated method stub
		status = "DE";
		return finalSettleRepository.getFinalSettlementEmployee(companyId, status);
	}

	@Override
	public List<FinalSettlementReport> saveReport(List<FinalSettlementReport> finalSettlemetList) {
//		List<FinalSettlementReport> finalSettlementReportList = new ArrayList<>();
//		for (FinalSettlementReport finalSettlement : finalSettlemetList) {
//			FinalSettlementReport report = 	finalSettlementReportRepository.save(finalSettlement);
//			finalSettlementReportList.add(report);
//		}

		return (List<FinalSettlementReport>) finalSettlementReportRepository.save(finalSettlemetList);
	}

	@Override
	public List<FinalSettlementReport> getFinalSettlementReport(Long employeeId) {
		// TODO Auto-generated method stub
		return finalSettlementReportRepository.getFinalSettlementReport(employeeId);
	}

	@Override
	public void generateLetter(Long employeeId, List<FinalSettlementReport> finalSettlementReportList) {
		 
		logger.info("FinalSettlementServiceImpl.generateLetter() start");
		Employee employee = employeePersonalInformationService.findEmployeesById(employeeId);
		Separation separation = separationService.getSeparationInfo(employeeId);

		EmployeeLetter employeeLetter = new EmployeeLetter();
		EmployeeLettersTransaction employeeLettersTransaction = new EmployeeLettersTransaction();

		employeeLetter.setEmpId(employeeId);
		Letter ltr = null;
				//letterService.findLetterByType(StatusMessage.EXPERIENCE_LETTER_CODE);

		// fetch all letter list based on grading system
		List<Letter> ltrList = new ArrayList<Letter>();
		ltrList = letterService.findLetterByTypeList(StatusMessage.EXPERIENCE_LETTER_CODE);

		for (Letter letter : ltrList) {
			if (ltrList.size() > 1) {
				if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
					if (letter.getGradeId().equals(employee.getGradesId())) {
						ltr = letter;
					}
				}
			} else {
				if (letter.getEnableGrade().equals(StatusMessage.YES_CODE)) {
					if (letter.getGradeId().equals(employee.getGradesId())) {
						ltr = letter;
					}
				} else {
					String enableGrade = StatusMessage.NO_CODE;
					ltr = letterService.findLetterByEnableGrade(StatusMessage.EXPERIENCE_LETTER_CODE, enableGrade);

				}
			}
		} // close for loop 
		
		employeeLetter.setLetterDecription(ltr.getLetterDecription());
		SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
		String fromDate = sm.format(employee.getDateOfJoining());
		String toDate = sm.format(separation.getExitDate());
		String currentDT = sm.format(new Date());
 
		employeeLetter.setLetterDecription(ltr.getLetterDecription()
				.replace(StatusMessage.EMPLOYEE_NAME, employee.getFirstName() + " " + employee.getLastName())
				.replace(StatusMessage.FROM_DATE, fromDate).replace(StatusMessage.TO_DATE, toDate).replace(StatusMessage.CURRENT_DATE, currentDT)
				.replace(StatusMessage.DESIGNATION_NAME, employee.getDesignation().getDesignationName())
				.replace(StatusMessage.COMPANY_NAME, employee.getCompany().getCompanyName()));
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
		employeeLetter.setLetterId(ltr.getLetterId());
		employeeLetter.setEmpStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setHRStatus(StatusMessage.PENDING_CODE);
		employeeLetter.setActiveStatus(StatusMessage.ACTIVE_CODE);
		employeeLetter.setDateCreated(new Date());
		employeeLetter.setUserId(employee.getUserId());
		EmployeeLetter empLetter = empLetterService.saveLtr(employeeLetter);

		ApprovalHierarchyMaster approvalHierarchyMaster = approvalHierarchyMasterService
				.getMasterApprovalStatus(ltr.getCompanyId(), ltr.getLetterId());

		if (approvalHierarchyMaster.getActiveStatus().equalsIgnoreCase(StatusMessage.ACTIVE_CODE)) {
			List<Object[]> approvalList = approvalHierarchyMasterService.findLetterApprovalById(ltr.getLetterId());

			int i = 0;
			Long designationId = 0l;
			for (Object[] obj : approvalList) {
				if (i == 0) {
					designationId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
					i++;
				}
			}

			employeeLettersTransaction.setEmployeeLetter(empLetter);
			employeeLettersTransaction.setLevels(StatusMessage.LEVEL_CODE);
			employeeLettersTransaction.setStatus(StatusMessage.PENDING_CODE);
			employeeLettersTransaction.setUserId(employee.getUserId());
			employeeLettersTransaction.setUserIdUpdate(employee.getUserId());
			employeeLettersTransaction.setCompanyId(employee.getCompanyId());
			employeeLettersTransaction.setDesignationId(designationId);
			employeeLettersTransaction.setDateCreated(new Date());
		//	employeeLettersTransaction.setDateUpdate(new Date());

			employeeLettersTransactionService.saveEmpLetterTransaction(employeeLettersTransaction);
		}
		logger.info("FinalSettlementServiceImpl.generateLetter() Successful");

	}

}
