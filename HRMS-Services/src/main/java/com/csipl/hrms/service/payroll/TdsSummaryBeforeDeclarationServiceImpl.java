package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsHistory;
import com.csipl.hrms.model.payroll.TdsPayrollHd;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.model.payroll.TdsStandardExemption;
import com.csipl.hrms.model.payroll.TdsSummaryChange;
import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TransactionApprovedHd;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.service.adaptor.TdsSummaryChangeAdaptor;
import com.csipl.hrms.service.employee.repository.PayStructureRepository;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.OtherIncomeRepository;
import com.csipl.hrms.service.payroll.repository.PreviousEmployerIncomeRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.csipl.hrms.service.payroll.repository.TdsApprovalRepository;
import com.csipl.hrms.service.payroll.repository.TdsCityMasterRepository;
import com.csipl.hrms.service.payroll.repository.TdsHistoryRepository;
import com.csipl.hrms.service.payroll.repository.TdsPayrolHdRepository;
import com.csipl.hrms.service.payroll.repository.TdsSlabRepository;
import com.csipl.hrms.service.payroll.repository.TdsSummaryChangeRepository;
import com.csipl.hrms.service.payroll.repository.TdsTransactionRepository;
import com.csipl.hrms.service.report.PayrollReportService;
import com.hrms.org.payrollprocess.tds.InvestmentTax;
import com.hrms.org.payrollprocess.tds.InvestmentTaxBeforeDeclation;

@Service
public class TdsSummaryBeforeDeclarationServiceImpl implements TdsSummaryBeforeDeclarationService{

	
	private static final Logger logger = LoggerFactory.getLogger(TdsApprovalServiceImpl.class);

	@Autowired
	FinancialYearRepository financialYearRepository;
	
	@Autowired
	TdsApprovalRepository tdsApprovalRepository;

	@Autowired
	TdsSlabRepository tdsSlabRepository;

	@Autowired
	PayrollReportService payrollReportService;
	
	@Autowired
	private PayStructureRepository payStructureRepository;

	@Autowired
	private TdsPayrolHdRepository tdsPayrolHdRepository;

	@Autowired
	TdsTransactionRepository tdsTransactionRepository;

	@Autowired
	ReportPayOutRepository reportPayOutRepository;

	@Autowired
	OtherIncomeRepository otherIncomeRepository;

	@Autowired
	TdsSummaryChangeRepository tdsSummaryChangeRepository;
	
	@Autowired
	TdsCityMasterRepository tdsCityMasterRepository;
	
	@Autowired
	TdsHistoryRepository tdsHistoryRepository;
	
	@Autowired
	PreviousEmployerIncomeRepository previousEmployerIncomeRepository;
	
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;
	
	InvestmentTax investmentTaxNewScheme = new InvestmentTax();
	InvestmentTaxBeforeDeclation investmentTax = new InvestmentTaxBeforeDeclation();
 
	@Override
	public TdsSummaryChangeDTO getTdsSummaryBeforeDeclation(List<TdsGroupSetup> tdsGroupList,  
			Employee employee, FinancialYear financialYear, Long companyId, Long userId, boolean payrollflag) {

		//jay
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		BigDecimal tdsStandardExemptionAmount=new BigDecimal(0.0);
		TdsSummaryChangeAdaptor tdsSummaryChangeAdaptor=new TdsSummaryChangeAdaptor();
		
		TdsHistory tdsHistory=new TdsHistory();
		
		List<TdsSlabHd> tdsSlabHdList=tdsSlabRepository.findAllTdsSlabList(companyId, financialYear.getFinancialYearId());
		
		TdsSlabHd tdsSlabHd = null;
		int age = DateUtils.getAgeCalaulation(employee.getDateOfBirth(), currentDate);
		logger.info("age : " + age +"employee category : "+employee.getGender());
		
		Long tdsSlabMasterId=0l;
		
		if(employee.getGender().equalsIgnoreCase("M")) {
			  tdsSlabMasterId=StatusMessage.MALE;
		}else if(employee.getGender().equalsIgnoreCase("F")) {
			  tdsSlabMasterId=StatusMessage.FEMALE;
		}else if(employee.getGender().equalsIgnoreCase("T")) {
			  tdsSlabMasterId=StatusMessage.TRANSGENDER;
		}
		
		for (TdsGroupSetup tdsGroupSetup : tdsGroupList) {
			if (tdsGroupSetup.getTdsGroupMaster().getTdsGroupMasterId() == StatusMessage.OTHER_STANDARD) {
				for (TdsSectionSetup tdsSectionSetup : tdsGroupSetup.getTdsSectionSetups()) {
					if (tdsSectionSetup.getTdsSectionName().equals("Standard Exemption")) {
						tdsStandardExemptionAmount = tdsSectionSetup.getMaxLimit();
					}
				}
			}
		}
		
		
		if (age >= 60)
			tdsSlabHd = tdsSlabRepository.finddsSlab(companyId, StatusMessage.SENIOR_CITIZEN, financialYear.getFinancialYearId());
		else
			tdsSlabHd = tdsSlabRepository.finddsSlab(companyId, tdsSlabMasterId, financialYear.getFinancialYearId());// employee.getGender()
	 
		logger.info("financialYear.getFinancialYear-------" + financialYear.getFinancialYear() +"employee id :"+ employee.getEmployeeId());

		BigDecimal totalOtherIncome = otherIncomeRepository.findOtherIncomeSum(employee.getEmployeeId(),financialYear.getFinancialYearId());
		
		List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList=previousEmployerIncomeRepository.getAllPreviousEmployerIncome(employee.getEmployeeId(),financialYear.getFinancialYearId());
		
		totalOtherIncome = totalOtherIncome == null ? new BigDecimal(0.0) : totalOtherIncome;
//		tdsStandardExemption= tdsStandardExemption == null ? new TdsStandardExemption() : tdsStandardExemption;
		
		//TdsSummaryDTO tdsSummaryDto = investmentTax.calcutateExamptionAndTaxAmount(payOutList, tdsStandardExemption,totalOtherIncome, tdsSlabHd, financialYear, employee, previousEmployerIncomeTdsList);
		//TdsPayrollHd tdsPayrollHd = investmentTax.calculateEmployeeIvestment( tdsSlabHd,transactionApprovedHd, financialYear.getFinancialYear(), tdsSummaryDto,employee, previousEmployerIncomeTdsList);
 
		PayStructureHd payStructureHd = payStructureRepository.employeeCurrentPayStructure(currentDate,employee.getEmployeeId());
		
		TdsSummaryChangeDTO tdsSummaryChangeDto = investmentTax.calcutateChangeExamptionAndTaxAmount(payStructureHd, tdsStandardExemptionAmount,totalOtherIncome, tdsSlabHdList, financialYear, employee, previousEmployerIncomeTdsList);
		
		TdsPayrollHd tdsChangePayrollHd = investmentTax.calculateChangeEmployeeIvestment( tdsSlabHd, financialYear.getFinancialYear(), tdsSummaryChangeDto,employee);
		
		tdsSummaryChangeDto.setFinancialYear(financialYear.getFinancialYear());
		 
//		TdsSummaryChange tdsSummaryChange=tdsSummaryChangeAdaptor.uiDtoToDatabaseModel(tdsSummaryChangeDto);//tdsSummaryAdaptor.uiDtoToDatabaseModel(tdsSummaryDto);
		
		return  tdsSummaryChangeDto;
		
//		logger.info(tdsSummaryChangeDto.toString());
//		tdsSummaryChange.setEmployee(employee);
//		tdsSummaryChange.setFinancialYearId(financialYear.getFinancialYearId());
//		tdsSummaryChange.setActive("AC");
//		tdsSummaryChange.setDateCreated(new Date());
//		tdsSummaryChange.setUserId(userId);
//		// history object
//		if(payStrFlag==false) {
//		TransactionApprovedHd transactionApprovedHdPrivious = tdsApprovalRepository
//				.getTransactionApprovedHd(employee.getEmployeeId(), financialYear.getFinancialYear());
//		if (transactionApprovedHdPrivious != null) {
//			transactionApprovedHdPrivious.setDateUpdate(new Date());
//			transactionApprovedHdPrivious.setActive("DE");
//			tdsApprovalRepository.save(transactionApprovedHdPrivious);
//		}
//		}
//		// history object
//		TdsPayrollHd tdsPayrollHdPrivious = tdsPayrolHdRepository.getTdsPayrollHd(employee.getEmployeeId(),
//				financialYear.getFinancialYear());
//		if (tdsPayrollHdPrivious != null) {
//			tdsPayrollHdPrivious.setDateUpdate(new Date());
//			tdsPayrollHdPrivious.setActive("DE");
//			tdsPayrolHdRepository.save(tdsPayrollHdPrivious);
//		}
//		
//		TdsSummaryChange tdsSummaryChangePrevious=tdsSummaryChangeRepository.findTdsSummary(employee.getEmployeeId(),
//				financialYear.getFinancialYearId());
//		if (tdsSummaryChangePrevious != null) {
//			tdsSummaryChangePrevious.setDateUpdate(new Date());
//			tdsSummaryChangePrevious.setActive("DE");
//			tdsSummaryChangeRepository.save(tdsSummaryChangePrevious);
//		}
//		
//		// new declaration
//		if(transactionApprovedHd!=null) {
//		transactionApprovedHd.setActive("AC");
//		tdsApprovalRepository.save(transactionApprovedHd);
//		}
//		tdsChangePayrollHd.setActive("AC");
//		tdsChangePayrollHd.setDateUpdate(new Date());
//		tdsPayrolHdRepository.save(tdsChangePayrollHd);
//		tdsSummaryChangeRepository.save(tdsSummaryChange);
//		if(tdsTrasactionistDb!=null)
//		tdsTransactionRepository.save(tdsTrasactionistDb);
//		//tdsTransactionRepository.updateStatus(employee.getEmployeeId(),financialYear.getFinancialYear());
//		//otherIncomeRepository.updateStatus(employee.getEmployeeId(),financialYear.getFinancialYear());
//		
//		TransactionApprovedHd transactionApprovedHdDb=tdsApprovalRepository.getTransactionApprovedHd(employee.getEmployeeId(), financialYear.getFinancialYear());
//		if(transactionApprovedHdDb!=null) {
//		tdsHistory.setFinancialYear(financialYear.getFinancialYear());
//		tdsHistory.setStatus(transactionApprovedHdDb.getStatus());
//		tdsHistory.setDateCreated(new Date());
//		tdsHistory.setApproveId(userId);
//		Employee emp=new Employee();
//		emp.setEmployeeId(employee.getEmployeeId());
//		tdsHistory.setEmployee(emp);
//		tdsHistoryRepository.save(tdsHistory);
//		}
	
		
	}
	
	@Override
	public TdsSummaryChangeDTO getTdsSummaryBeforeDeclationNewScheme(List<TdsGroupSetup> tdsGroupList,
			Employee employee, FinancialYear financialYear, Long companyId, Long userId, boolean b) {


		//jay
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		BigDecimal tdsStandardExemptionAmount=new BigDecimal(0.0);
		TdsSummaryChangeAdaptor tdsSummaryChangeAdaptor=new TdsSummaryChangeAdaptor();
		
		TdsHistory tdsHistory=new TdsHistory();
		
		List<TdsSlabHd> tdsSlabHdList=tdsSlabRepository.findAllTdsSlabList(companyId, financialYear.getFinancialYearId());
		
		TdsSlabHd tdsSlabHd = null;
		int age = DateUtils.getAgeCalaulation(employee.getDateOfBirth(), currentDate);
		logger.info("age : " + age +"employee category : "+employee.getGender());
		
		Long tdsSlabMasterId=0l;
		
		if(employee.getGender().equalsIgnoreCase("M")) {
			  tdsSlabMasterId=StatusMessage.MALE;
		}else if(employee.getGender().equalsIgnoreCase("F")) {
			  tdsSlabMasterId=StatusMessage.FEMALE;
		}else if(employee.getGender().equalsIgnoreCase("T")) {
			  tdsSlabMasterId=StatusMessage.TRANSGENDER;
		}
		
		for (TdsGroupSetup tdsGroupSetup : tdsGroupList) {
			if (tdsGroupSetup.getTdsGroupMaster().getTdsGroupMasterId() == StatusMessage.OTHER_STANDARD) {
				for (TdsSectionSetup tdsSectionSetup : tdsGroupSetup.getTdsSectionSetups()) {
					if (tdsSectionSetup.getTdsSectionName().equals("Standard Exemption")) {
						tdsStandardExemptionAmount = tdsSectionSetup.getMaxLimit();
					}
				}
			}
		}
		
		TransactionApprovedHd transactionApprovedHd = new TransactionApprovedHd();
		
		if (age >= 60)
			tdsSlabHd = tdsSlabRepository.finddsSlab(companyId, StatusMessage.SENIOR_CITIZEN, financialYear.getFinancialYearId());
		else
			tdsSlabHd = tdsSlabRepository.finddsSlab(companyId, tdsSlabMasterId, financialYear.getFinancialYearId());// employee.getGender()
	 
		logger.info("financialYear.getFinancialYear-------" + financialYear.getFinancialYear() +"employee id :"+ employee.getEmployeeId());

//		BigDecimal totalOtherIncome = otherIncomeRepository.findOtherIncomeSum(employee.getEmployeeId(),financialYear.getFinancialYearId());
		
//		List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList=previousEmployerIncomeRepository.getAllPreviousEmployerIncome(employee.getEmployeeId(),financialYear.getFinancialYearId());
		BigDecimal totalOtherIncome=   new BigDecimal(0.0);
		totalOtherIncome = totalOtherIncome == null ? new BigDecimal(0.0) : totalOtherIncome;
		
		List<PreviousEmployerIncomeTds>	previousEmployerIncomeTdsList= new ArrayList<>();
		
//		tdsStandardExemption= tdsStandardExemption == null ? new TdsStandardExemption() : tdsStandardExemption;
		List<Object[]> reportPayOutSum = payrollReportService.getGrossSumEmployee(employee.getEmployeeId(), companyId, financialYear);
		//TdsSummaryDTO tdsSummaryDto = investmentTax.calcutateExamptionAndTaxAmount(payOutList, tdsStandardExemption,totalOtherIncome, tdsSlabHd, financialYear, employee, previousEmployerIncomeTdsList);
		//TdsPayrollHd tdsPayrollHd = investmentTax.calculateEmployeeIvestment( tdsSlabHd,transactionApprovedHd, financialYear.getFinancialYear(), tdsSummaryDto,employee, previousEmployerIncomeTdsList);
 
		PayStructureHd payStructureHd = payStructureRepository.employeeCurrentPayStructure(currentDate,employee.getEmployeeId());
		
		TdsSummaryChangeDTO tdsSummaryChangeDto = investmentTaxNewScheme.calcutateChangeExamptionAndTaxAmountForNewScheme(payStructureHd,transactionApprovedHd, tdsStandardExemptionAmount,totalOtherIncome, tdsSlabHdList, financialYear, employee, previousEmployerIncomeTdsList, reportPayOutSum);
		
		TdsPayrollHd tdsChangePayrollHd = investmentTaxNewScheme.calculateChangeEmployeeIvestmentForNewScheme( tdsSlabHd, financialYear.getFinancialYear(), tdsSummaryChangeDto,employee);
				
		tdsSummaryChangeDto.setFinancialYear(financialYear.getFinancialYear());
		 
//		TdsSummaryChange tdsSummaryChange=tdsSummaryChangeAdaptor.uiDtoToDatabaseModel(tdsSummaryChangeDto);//tdsSummaryAdaptor.uiDtoToDatabaseModel(tdsSummaryDto);
		
		return  tdsSummaryChangeDto;
		
 
		
	
	}

	@Override
	public TransactionApprovedHd createTdsApprovals(List<TdsGroupSetup> tdsGroupList,
			List<TdsTransaction> tdsTrasactionist, Employee employee, Long companyId, FinancialYear financialYear,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionApprovedHd getTdsApproved(Long employeeId, Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsSummaryDTO getTdsSummary(Employee employee, Long companyId, List<PayOut> payOutList,
			TdsStandardExemption tdsStandardExemption) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionApprovedHd getTransactionApprovedHd(Long employeeId, String FinancialYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsSummaryChange getTdsSummary(Long employeeId, Long financialYearId) {
		// TODO Auto-generated method stub
		return null;
	}

}
