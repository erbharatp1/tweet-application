package com.csipl.hrms.payroll.controller;

import com.csipl.hrms.service.adaptor.InvestmentAdaptor;
import com.csipl.hrms.service.adaptor.OtherIncomeAdaptor;
import com.csipl.hrms.service.adaptor.PreviousEmployerIncomeAdaptor;
import com.csipl.hrms.service.adaptor.TdsApprovedAdaptor;
import com.csipl.hrms.service.adaptor.TdsHouseRentInfoAdaptor;
import com.csipl.hrms.service.adaptor.TdsSummaryChangeAdaptor;
import com.csipl.hrms.service.adaptor.TdsTransactionAdaptor;
import com.csipl.hrms.service.adaptor.TdsTransactionFileAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.InvestmentService;
import com.csipl.hrms.service.payroll.OtherIncomeService;
import com.csipl.hrms.service.payroll.PreviousEmployerIncomeService;
import com.csipl.hrms.service.payroll.TdsApprovalMonthlyService;
import com.csipl.hrms.service.payroll.TdsApprovalService;
import com.csipl.hrms.service.payroll.TdsHouseRentInfoService;
import com.csipl.hrms.service.payroll.TdsStandardExemptionService;
import com.csipl.hrms.service.payroll.TdsSummaryBeforeDeclarationService;
import com.csipl.hrms.service.payroll.TdsTransactionFileService;
import com.csipl.hrms.service.payroll.TdsTransactionService;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.report.PayrollReportService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.candidate.CandidateIdProofDTO;
import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.InvestmentDTO;
import com.csipl.hrms.dto.payroll.OtherIncomeDTO;
import com.csipl.hrms.dto.payroll.PreviousEmployerIncomeFileDTO;
import com.csipl.hrms.dto.payroll.PreviousEmployerIncomeTdsDTO;
import com.csipl.hrms.dto.payroll.TdsGroupDTO;
import com.csipl.hrms.dto.payroll.TdsHouseRentInfoDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionFileDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionFileInfoDTO;
import com.csipl.hrms.dto.payroll.TransactionApprovedHdDTO;
import com.csipl.hrms.model.candidate.CandidateIdProof;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.PayStructureHd;
import com.csipl.hrms.model.payroll.OtherIncome;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeFile;
import com.csipl.hrms.model.payroll.PreviousEmployerIncomeTds;
import com.csipl.hrms.model.payroll.TdsGroup;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsHouseRentInfo;
import com.csipl.hrms.model.payroll.TdsSection;
import com.csipl.hrms.model.payroll.TdsStandardExemption;
import com.csipl.hrms.model.payroll.TdsTransaction;
import com.csipl.hrms.model.payroll.TdsTransactionFile;
import com.csipl.hrms.model.payroll.TdsTransactionFileInfo;
import com.csipl.hrms.model.payroll.TransactionApprovedHd;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayOut;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;

@RequestMapping("/investment")
@RestController
public class InvestmentController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(InvestmentController.class);
	DateUtils dateUtils = new DateUtils();

	@Autowired
	InvestmentService investmentService;

	@Autowired
	FinancialYearService financialYearService;

	@Autowired
	TdsApprovalService tdsApprovalService;

	@Autowired
	TdsTransactionService tdsTransactionService;

	@Autowired
	TdsHouseRentInfoService tdsHouseRentInfoService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	TdsTransactionFileService tdsTransactionFileService;

	@Autowired
	OtherIncomeService otherIncomeService;

	@Autowired
	TdsStandardExemptionService tdsStandardExemptionService;

	@Autowired
	PayStructureService payStructureService;

	@Autowired
	PreviousEmployerIncomeService previousEmployerIncomeService;

	@Autowired
	FinancialYearRepository financialYearRepository;

	@Autowired
	TdsApprovalMonthlyService tdsApprovalMonthlyService;

	@Autowired
	TdsSummaryBeforeDeclarationService tdsSummaryBeforeDeclarationService;

	@Autowired
	PayrollReportService payrollReportService;

	InvestmentAdaptor investmentAdaptor = new InvestmentAdaptor();
	TdsHouseRentInfoAdaptor tdsHouseRentInfoAdaptor = new TdsHouseRentInfoAdaptor();
	TdsTransactionAdaptor tdsTransactionAdaptor = new TdsTransactionAdaptor();
	TdsApprovedAdaptor tdsApprovedAdaptor = new TdsApprovedAdaptor();
	TdsTransactionFileAdaptor tdsTtansactionFileAdaptor = new TdsTransactionFileAdaptor();
	OtherIncomeAdaptor otherIncomeAdaptor = new OtherIncomeAdaptor();
	PreviousEmployerIncomeAdaptor previousEmployerIncomeAdaptor = new PreviousEmployerIncomeAdaptor();
	TdsSummaryChangeAdaptor tdsSummaryChangeAdaptor = new TdsSummaryChangeAdaptor();

	/**
	 * @param tdsGroupDto
	 *            This is the first parameter for getting tdsGroup Object from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void saveInvestment(@RequestBody TdsGroupDTO tdsGroupDto, HttpServletRequest req) {
		TdsGroup tdsGroup = new TdsGroup();
		if (tdsGroupDto.getTdsGroupId() == null)
			tdsGroup = investmentAdaptor.uiDtoToDatabaseModel(tdsGroupDto);
		else {
			Long tdsGroupId = tdsGroupDto.getTdsGroupId();
			TdsGroup tdsGroupUpdate = investmentService.getInvestment(tdsGroupId);
			tdsGroup = investmentAdaptor.uiDtoToDatabaseModel(tdsGroupDto);
			tdsGroup.setTdsGroupId(null);
			for (TdsSection tdsSection : tdsGroup.getTdsSections()) {
				tdsSection.setTdsSectionId(null);
			}
			tdsGroupUpdate.setEffectiveEndDate(dateUtils.getDateWirhYYYYMMDD(tdsGroupDto.getEffectiveStartDate()));
			investmentService.save(tdsGroupUpdate);
		}
		investmentService.save(tdsGroup);
	}

	/**
	 * to get List of Investment Objects from database
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<InvestmentDTO> findAllInvestment(@RequestParam("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		// List<TdsGroup> tdsGroupList = investmentService.getInvestmentList(companyId);
		// if (tdsGroupList != null)
		// return investmentAdaptor.databseModelToInvestmentDtoList(tdsGroupList);
		// else
		// throw new ErrorHandling("Tds entry not found in system");

		return null;
	}

	/**
	 * to get TdsGroup Object from database based on tdsGroupId (Primary Key)
	 */
	@RequestMapping(value = "/getInvestment/{tdsGroupId}", method = RequestMethod.GET)
	public @ResponseBody TdsGroupDTO findInvestment(@PathVariable("tdsGroupId") String tdsGroupId,
			HttpServletRequest req) {
		Long longTdsGroupId = Long.parseLong(tdsGroupId);
		return investmentAdaptor.databaseModelToUiDto(investmentService.getInvestment(longTdsGroupId));
	}

	/**
	 * to get List of TdsTransaction Objects from database based on employeeId and
	 * companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/tdsTransaction", method = RequestMethod.GET)
	public @ResponseBody List<TdsTransactionDTO> getTdsTransactionList(@RequestParam("employeeId") Long employeeId,
			@RequestParam("companyId") Long companyId, HttpServletRequest req) throws PayRollProcessException {
		// Long empId = Long.parseLong(employeeId);

		// DateUtils dateUtils=new DateUtils();
		// Date currentDate=dateUtils.getCurrentDate();

		FinancialYear financialYear = financialYearRepository.getFinancialYear(new Date(), companyId);
		//
		// if(!financialYear.equals(null)) {
		//
		// }
		System.out.println("Financial Year======" + financialYear.getFinancialYear() + "  Id===========  "
				+ financialYear.getFinancialYearId());
		List<TdsTransactionDTO> tdsTransactionDtoList = tdsTransactionAdaptor.databaseModelToObjectArray(
				tdsTransactionService.getTdsTransactionObjectList(employeeId, financialYear.getFinancialYearId()),
				financialYear.getFinancialYear());
		for (TdsTransactionDTO tdsTransactionDTO : tdsTransactionDtoList) {

			if (tdsTransactionDTO.getTdsTransactionId() != null && tdsTransactionDTO.getTdsTransactionId() != 0) {

				List<TdsTransactionFileInfo> tdsTransactionFileInfoList = tdsTransactionService
						.getTdsTransactionFileInfoList(tdsTransactionDTO.getTdsTransactionId());

				List<TdsTransactionFileInfoDTO> tdsTransactionFileInfoDTOList = new ArrayList<TdsTransactionFileInfoDTO>();
				for (TdsTransactionFileInfo tdsTransactionFileInfo : tdsTransactionFileInfoList) {
					TdsTransactionFileInfoDTO tdsTransactionFileInfoDTO = new TdsTransactionFileInfoDTO();

					tdsTransactionFileInfoDTO.setFileName(tdsTransactionFileInfo.getFileName());
					tdsTransactionFileInfoDTO.setOriginalFilename(tdsTransactionFileInfo.getOriginalFilename());
					tdsTransactionFileInfoDTO.setFilePath(tdsTransactionFileInfo.getFilePath());
					tdsTransactionFileInfoDTO
							.setTdsTransactionFileInfoId(tdsTransactionFileInfo.getTdsTransactionFileInfoId());
					tdsTransactionFileInfoDTO.setDateCreated(tdsTransactionFileInfo.getDateCreated());
					tdsTransactionFileInfoDTO.setDateUpdated(tdsTransactionFileInfo.getDateUpdated());
					tdsTransactionFileInfoDTO.setActiveStatus(tdsTransactionFileInfo.getActiveStatus());
					// tdsTransactionFileInfoDTO.setTdsTransaction(tdsTransactionDTO.getTdsTransactionId());

					tdsTransactionFileInfoDTOList.add(tdsTransactionFileInfoDTO);

				}

				tdsTransactionDTO.setTdsTransactionFileInfoDTO(tdsTransactionFileInfoDTOList);
			}
		}
		return tdsTransactionDtoList;
	}

	@RequestMapping(path = "/deleteTdsTransactionFileInfo/{tdsTransactionFileInfoId}", method = RequestMethod.GET)
	public @ResponseBody int deleteTdsTransactionFileInfo(
			@PathVariable("tdsTransactionFileInfoId") Long tdsTransactionFileInfoId) throws PayRollProcessException {

		return tdsTransactionService.deleteTdsTransactionFileInfo(tdsTransactionFileInfoId);
	}

	/**
	 * @param employeeId
	 *            This is the first parameter for getting employeeId from UI
	 * @param tdsTransactionDtoList
	 *            This is the second parameter for getting List of tdsTransaction
	 *            objects from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/tdsTransaction", method = RequestMethod.POST)
	public void saveTdsTransaction(@RequestParam("employeeId") String employeeId,
			@RequestParam("companyId") Long companyId,
			@RequestPart("info") List<TdsTransactionDTO> tdsTransactionDtoList,
			@RequestPart("fileInfo") List<MultipartFile> fileInfo, HttpServletRequest req)
			throws PayRollProcessException {

		System.out.println("fileInfo======" + fileInfo);
		System.out.println("tdsTransactionDtoList======" + tdsTransactionDtoList);

		Long empId = Long.parseLong(employeeId);
		List<TdsTransaction> tdsTransactionList = tdsTransactionAdaptor
				.uiDtoToTdsTransactionModelList(tdsTransactionDtoList, empId);
		List<TdsTransaction> tdsTransactionList1 = new ArrayList<TdsTransaction>();
		List<TdsTransaction> tdsTransactionListOld = tdsTransactionService.getTdsTransactionList(empId, companyId);

		tdsTransactionList.forEach(tdsTransaction -> {
			// no investment found
			if (tdsTransaction.getTdsTransactionId() == null && tdsTransaction.getInvestmentAmount() == null) {

			} else if (tdsTransaction.getTdsTransactionId() == null) {
				System.out.println("tdsTransaction.getTdsTransactionId()");
				tdsTransaction.setStatus("Pending");
				tdsTransactionList1.add(tdsTransaction);
				// boolean newFlag = tdsTransaction == null ||
				// tdsTransaction.getTdsTransactionId() == null;
				// editLogInfoWithoutCG(tdsTransaction, newFlag, req);
			} else {
				if (tdsTransaction.getInvestmentAmount() == null)
					tdsTransaction.setInvestmentAmount(new BigDecimal(0.0));
				for (TdsTransaction tdsTransactionOld : tdsTransactionListOld) {

					if (tdsTransactionOld.getTdsTransactionId().equals(tdsTransaction.getTdsTransactionId())) {
						System.out
								.println("tdsTransactionOld comparation start" + tdsTransactionOld.getInvestmentAmount()
										+ "---------------" + tdsTransaction.getInvestmentAmount());
						System.out.println("-----" + tdsTransactionOld.getInvestmentAmount()
								.compareTo(tdsTransaction.getInvestmentAmount()));
						if (tdsTransactionOld.getInvestmentAmount()
								.compareTo(tdsTransaction.getInvestmentAmount()) == 0)
							System.out.println("tdsTransactionOld.getInvestmentAmount()");
						else
							tdsTransaction.setStatus("Pending");

						tdsTransactionList1.add(tdsTransaction);
						// boolean newFlag = tdsTransaction == null ||
						// tdsTransaction.getTdsTransactionId() == null;
						// editLogInfoWithoutCG(tdsTransaction, newFlag, req);
					}
				} // end inner for
			}
		});

		tdsTransactionService.save(tdsTransactionList1, empId, companyId, fileInfo);
	}

	/**
	 * @param employeeId
	 *            This is the first parameter for getting employeeId from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * @param file
	 *            This is the third parameter for getting file Input from UI
	 * @param tdsTransactionDto
	 *            This is the forth parameter for getting tdsTransaction object from
	 *            UI
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/tdsTransactionFile", method = RequestMethod.POST)
	public void saveTdsTransactionFile(@RequestParam("employeeId") String employeeId, HttpServletRequest req,
			@RequestPart("uploadDocument") MultipartFile file,
			@RequestPart("info") TdsTransactionFileDTO tdsTransactionFileDto) throws ErrorHandling {
		Long empId = Long.parseLong(employeeId);
		TdsTransactionFile tdsTransactionFile = tdsTtansactionFileAdaptor
				.uiDtoToTdsTransactionFileModel(tdsTransactionFileDto, empId);
		/*
		 * boolean newFlag = tdsTransactionFile == null ||
		 * tdsTransactionFile.getTdsTransactionFileId() == null; //
		 * editLogInfoWithoutCG(tdsTransactionFile, newFlag, req); //
		 * List<TdsTransactionFile> //
		 * tdsTransactionFileList=tdsTransactionFileService.getTdsTransactionFiles(empId
		 * );
		 */ tdsTransactionFileService.saveTdsTransactionFile(tdsTransactionFile, empId, file);
	}

	@RequestMapping(path = "/tdsTransactionFile", method = RequestMethod.GET)
	public @ResponseBody List<TdsTransactionFileDTO> gettdsTransactionFiles(
			@RequestParam("employeeId") String employeeId, HttpServletRequest req) throws ErrorHandling {
		System.out.println("Tds Transaction file controller");
		Long empId = Long.parseLong(employeeId);
		List<TdsTransactionFile> tdsTransactionFileList = tdsTransactionFileService.getTdsTransactionFiles(empId);
		if (tdsTransactionFileList != null)
			return tdsTtansactionFileAdaptor.databaseModelToUiDtoList(tdsTransactionFileList);
		else
			throw new ErrorHandling("Tds transaction files not uploaded");
	}

	@RequestMapping(path = "/tdsTransactionFile", method = RequestMethod.DELETE)
	public void deleteEmpIdProof(@RequestParam("tdsTranactionFileId") String tdsTranactionFileId,
			HttpServletRequest req) {
		Long id = Long.parseLong(tdsTranactionFileId);
		tdsTransactionFileService.delete(id);
	}

	/**
	 * @param employeeId
	 *            This is the first parameter for getting employeeId from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(path = "/beforeTdsDeclaration/{employeeId}/{status}/{companyId}/{userId}", method = RequestMethod.GET)
	public TdsSummaryChangeDTO tdsSummaryBeforeDeclation(@PathVariable("employeeId") Long empId,
			@PathVariable("status") String status, @PathVariable("companyId") Long companyId,
			@PathVariable("userId") Long userId, HttpServletRequest req) throws PayRollProcessException {

		// tdsSummaryBeforeDeclation

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate(); 
		// Long empId = Long.parseLong(employeeId);

		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);

		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);

		// Actual tds calculation before declaration
		return tdsSummaryBeforeDeclarationService.getTdsSummaryBeforeDeclation(tdsGroupList, employee, financialYear, companyId, userId, false);

	}

	/**
	 * @param employeeId
	 *            This is the first parameter for getting employeeId from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(value = "/tdsDeclaration/{employeeId}/{status}/{companyId}/{userId}", method = RequestMethod.GET)
	public void tdsApproval(@PathVariable("employeeId") Long empId, @PathVariable("status") String status,
			@PathVariable("companyId") Long companyId, @PathVariable("userId") Long userId, HttpServletRequest req)
			throws PayRollProcessException {
		// My Tds Declaration
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);

		// List<PayOut> payOutList = payStructureService.processPayroll(companyId,
		// empId);
		TdsStandardExemption tdsStandardExemption = tdsStandardExemptionService.getTdsStandardExemption(companyId,
				financialYear);
		List<OtherIncome> otherIncomeList = otherIncomeService.findOtherIncomes(empId, companyId, financialYear);
		List<Object[]> reportPayOutSum = payrollReportService.getGrossSumEmployee(empId, companyId, financialYear);
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);

		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		List<TdsTransaction> tdsTrasactionList = tdsTransactionService.getTdsTrasactionListforApproval(empId,
				financialYear);

		logger.info("financialYear " + financialYear + "tdsGroupList : " + tdsGroupList + "tdsTrasactionist :"
				+ tdsTrasactionList + "getCompanyId" + companyId);

		// tds declaration approved amount calculation-----
		TransactionApprovedHd transactionApprovedHd = tdsApprovalService.createTdsApprovals(tdsGroupList,
				tdsTrasactionList, employee, companyId, financialYear, status);

		boolean newFlag = transactionApprovedHd == null || transactionApprovedHd.getTransactionApprovedHdId() == null;
		// editLogInfoWithoutCG(transactionApprovedHd, newFlag, req);

		transactionApprovedHd.setDateUpdate(new Date());
		transactionApprovedHd.setUserIdUpdate(userId);
		if (newFlag) {
			transactionApprovedHd.setDateCreated(new Date());
			transactionApprovedHd.setUserId(userId);
		}
		logger.info("transactionApprovedHd" + transactionApprovedHd);
		// Actual tds calculation
		tdsApprovalService.saveTdsApprovalsList(tdsGroupList, transactionApprovedHd, employee, financialYear, companyId,
				userId, tdsStandardExemption, otherIncomeList, false, tdsTrasactionList, status, reportPayOutSum);
	}

	
	@RequestMapping(path = "/beforeTdsDeclarationNewScheme/{employeeId}/{status}/{companyId}/{userId}", method = RequestMethod.GET)
	public TdsSummaryChangeDTO tdsSummaryBeforeDeclationNewScheme(@PathVariable("employeeId") Long empId,
			@PathVariable("status") String status, @PathVariable("companyId") Long companyId,
			@PathVariable("userId") Long userId, HttpServletRequest req) throws PayRollProcessException {
		// beforeTdsDeclarationNewScheme
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);
		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		// Actual tds calculation before declaration
		return tdsSummaryBeforeDeclarationService.getTdsSummaryBeforeDeclationNewScheme(tdsGroupList, employee,financialYear, companyId, userId, false);
	}
	
	@RequestMapping(value = "/tdsDeclarationNewScheme/{employeeId}/{status}/{companyId}/{userId}", method = RequestMethod.GET)
	public void tdsApprovalForNewScheme(@PathVariable("employeeId") Long empId, @PathVariable("status") String status,
			@PathVariable("companyId") Long companyId, @PathVariable("userId") Long userId, HttpServletRequest req)
			throws PayRollProcessException {
		// My Tds Declaration
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		TdsStandardExemption tdsStandardExemption = tdsStandardExemptionService.getTdsStandardExemption(companyId,
				financialYear);
		List<OtherIncome> otherIncomeList = otherIncomeService.findOtherIncomes(empId, companyId, financialYear);
		List<Object[]> reportPayOutSum = payrollReportService.getGrossSumEmployee(empId, companyId, financialYear);
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);

		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		List<TdsTransaction> tdsTrasactionList = new ArrayList<>();
		TransactionApprovedHd transactionApprovedHd = new TransactionApprovedHd();
		tdsApprovalService.saveTdsApprovalsNewScheme(tdsGroupList, transactionApprovedHd, employee, financialYear,
				companyId, userId, tdsStandardExemption, otherIncomeList, false, tdsTrasactionList, status,
				reportPayOutSum);
	}
	
	
	@RequestMapping(value = "/tdsDeclarationMonthly/{companyId}/{userId}", method = RequestMethod.GET)
	public int tdsApprovalMonthly(@PathVariable("companyId") Long companyId, @PathVariable("userId") Long userId,
			HttpServletRequest req) throws PayRollProcessException {
		// My Tds Declaration

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		TdsStandardExemption tdsStandardExemption = tdsStandardExemptionService.getTdsStandardExemption(companyId,
				financialYear);
		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		int count = 0;

		List<Integer> payStructureEmployeeIdList = payStructureService.getPayStructureHdEmployeeId(companyId);
		String status = "Approved";
		for (Integer emp : payStructureEmployeeIdList) {
			Long empId = emp.longValue();
			// Long empId = 428l;
			Employee employee = employeePersonalInformationService.findEmployeesById(empId);
			List<OtherIncome> otherIncomeList = otherIncomeService.findOtherIncomes(empId, companyId, financialYear);
			List<Object[]> grossSum = payrollReportService.getGrossSumEmployee(empId, companyId, financialYear);

			System.out.println("gossSum Data ================================= " + grossSum.get(0)[0]);

			List<TdsTransaction> tdsTrasactionList = tdsTransactionService.getTdsTrasactionListforApproval(empId,
					financialYear);

			logger.info("financialYear " + financialYear + "tdsGroupList : " + tdsGroupList + "tdsTrasactionist :"
					+ tdsTrasactionList + "getCompanyId" + companyId);

			// tds declaration approved amount calculation-----
			TransactionApprovedHd transactionApprovedHd = tdsApprovalMonthlyService.createTdsApprovals(tdsGroupList,
					tdsTrasactionList, employee, companyId, financialYear, status);
			if (employee.getTdsPlanType().equals(null) && employee.getTdsPlanType().equals("A")) {

				System.out.println("Employee Id===================== " + empId);

				boolean newFlag = transactionApprovedHd == null
						|| transactionApprovedHd.getTransactionApprovedHdId() == null;
				// editLogInfoWithoutCG(transactionApprovedHd, newFlag, req);

				transactionApprovedHd.setDateUpdate(new Date());
				transactionApprovedHd.setUserIdUpdate(userId);
				if (newFlag) {
					transactionApprovedHd.setDateCreated(new Date());
					transactionApprovedHd.setUserId(userId);
				}
				logger.info("transactionApprovedHd" + transactionApprovedHd);
				// Actual tds calculation
				tdsApprovalMonthlyService.saveTdsApprovalsMonthalyList(tdsGroupList, transactionApprovedHd, employee,
						financialYear, companyId, userId, tdsStandardExemption, otherIncomeList, false,
						tdsTrasactionList, status, grossSum);
				System.out.println("Tds List Size =============" + payStructureEmployeeIdList.size());
				count++;
				System.out.println("Employeee Idd   Count =============" + count);
			} else if (employee.getTdsPlanType().equals("B")) {
				tdsTrasactionList = new ArrayList<>();
				transactionApprovedHd = new TransactionApprovedHd();
				tdsApprovalService.saveTdsApprovalsNewScheme(tdsGroupList, transactionApprovedHd, employee,
						financialYear, companyId, userId, tdsStandardExemption, otherIncomeList, false,
						tdsTrasactionList, status, grossSum);
				count++;
			}

		}
		return (payStructureEmployeeIdList.size() == count) ? 1 : 0;
	}

	// get TdsSummary data
	// @RequestMapping(value = "/tdsSummary/{employeeId}/{companyId}", method =
	// RequestMethod.POST)
	// public TdsSummaryChangeDTO getTdsSummary(@PathVariable("employeeId") Long
	// employeeId,@PathVariable("companyId") Long companyId, HttpServletRequest req)
	// {
	//
	// DateUtils dateUtils = new DateUtils();
	// Date currentDate = dateUtils.getCurrentDate();
	// FinancialYear financialYear =
	// financialYearService.findCurrentFinancialYear(currentDate, companyId);
	//
	// return
	// tdsSummaryChangeAdaptor.databaseModelToUiDto(tdsApprovalService.getTdsSummary(employeeId,financialYear.getFinancialYearId()));
	// }

	@RequestMapping(path = "/tdsApproval", method = RequestMethod.GET)
	public @ResponseBody TransactionApprovedHdDTO gettdsApproved(@RequestParam("employeeId") String employeeId,
			@RequestParam("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		Long empId = Long.parseLong(employeeId);
		TransactionApprovedHd transactionApprovedHd = tdsApprovalService.getTdsApproved(empId, companyId);
		if (transactionApprovedHd != null)
			return tdsApprovedAdaptor.databaseModelToUiDto(transactionApprovedHd);
		else
			throw new ErrorHandling("Tds not yet approved");
	}

	@RequestMapping(path = "/otherIncome/{employeeId}/{companyId}", method = RequestMethod.POST)
	public List<OtherIncomeDTO> saveOtherIncome(@PathVariable("employeeId") String employeeId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException, JSONException {
		Long empId = Long.parseLong(employeeId);

		JSONArray jsonArray = new JSONArray(req.getParameter("fileInfo"));

		System.out.println("OtherIncome ============" + jsonArray);

		List<OtherIncomeDTO> otherIncomeDtoList = new ArrayList<OtherIncomeDTO>();

		Set<Integer> index = new HashSet<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < jsonArray.length(); i++) {

			OtherIncomeDTO otherIncome = new OtherIncomeDTO();

			// Employee employee = new Employee();
			// employee.setEmployeeId(empId);
			//
			// Company company = new Company();
			// company.setCompanyId(companyId);

			JSONObject jsonObj = jsonArray.getJSONObject(i);

			otherIncome.setEmployeeId(empId);
			otherIncome.setCompanyId(companyId);

			otherIncome.setAmount(new BigDecimal(jsonObj.optDouble("amount")));
			otherIncome.setDescription(jsonObj.getString("description"));
			otherIncome.setUserId(jsonObj.optLong("userId"));

			if (jsonObj.optLong("otherIncomeId") != 0l)
				otherIncome.setOtherIncomeId(jsonObj.optLong("otherIncomeId"));

			otherIncome.setUserId(jsonObj.optLong("userId"));

			if (jsonObj.has("attachment")) {
				String s1 = jsonObj.getString("attachment");
				System.out.println(s1 + "=====");
				otherIncome.setDocumentName(s1);
			} else if (jsonObj.has("attachment") && jsonObj.has("documentName")) {
				otherIncome.setOtherIncomeDoc(jsonObj.getString("attachment"));
				otherIncome.setDocumentName(jsonObj.getString("documentName"));

			}

			otherIncomeDtoList.add(otherIncome);
		}

		List<OtherIncome> otherIncomeList = otherIncomeAdaptor.uiDtoToOtherIncomeList(otherIncomeDtoList, empId);

		List<OtherIncome> otherIncomeListResult = otherIncomeService.save(otherIncomeList, companyId, req);
		return otherIncomeAdaptor.databaseModelToUiDtoList(otherIncomeListResult);

		// List<OtherIncome> otherIncomeList =
		// otherIncomeAdaptor.uiDtoToOtherIncomeList(otherIncomeDtoList, empId);
		// otherIncomeList.forEach(otherIncome -> {
		// otherIncome.setStatus("Pending");
		// // boolean newFlag = otherIncome == null || otherIncome.getOtherIncomeId() ==
		// // null;
		// // editLogInfoWithoutGroup(otherIncome, newFlag, req);
		// });
		// List<OtherIncome> otherIncomeListResult =
		// otherIncomeService.save(otherIncomeList, companyId);
		// return otherIncomeAdaptor.databaseModelToUiDtoList(otherIncomeListResult);
		// return null;
	}

	@RequestMapping(path = "/otherIncome", method = RequestMethod.GET)
	public @ResponseBody List<OtherIncomeDTO> findOtherIncomes(@RequestParam("employeeId") String employeeId,
			@RequestParam("companyId") Long companyId, HttpServletRequest req) throws PayRollProcessException {
		Long empId = Long.parseLong(employeeId);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		return otherIncomeAdaptor
				.databaseModelToUiDtoList(otherIncomeService.findOtherIncomes(empId, companyId, financialYear));
	}

	@RequestMapping(value = "deleteOtherIncome/{otherIncomeId}", method = RequestMethod.GET)
	public @ResponseBody int deleteOtherIncome(@PathVariable("otherIncomeId") String otherIncomeId,
			HttpServletRequest req) {
		logger.info("otherIncomeId is calling :" + "otherIncomeId  ==" + otherIncomeId);
		return otherIncomeService.deleteOtherIncome(Long.valueOf(otherIncomeId));
	}

	@RequestMapping(path = "/tdsPreviousEmployerIncome", method = RequestMethod.GET)
	public @ResponseBody List<PreviousEmployerIncomeTdsDTO> getPreviousEmployerIncomeList(
			@RequestParam("employeeId") String employeeId, @RequestParam("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("PreviousIncome employeeId - " + employeeId);
		Long empId = Long.parseLong(employeeId);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		List<PreviousEmployerIncomeTdsDTO> previousEmployerIncomeTdsDtoList = previousEmployerIncomeAdaptor
				.databaseModelToObjectArray(previousEmployerIncomeService.getPreviousEmployerIncomeObjectList(empId,
						financialYear.getFinancialYearId()));
		return previousEmployerIncomeTdsDtoList;
	}

	// @RequestMapping(path = "/tdsPreviousEmployerIncome", method =
	// RequestMethod.POST)
	// public void savePreviousEmployerIncomeList(@RequestParam("employeeId") String
	// employeeId,
	// @RequestBody List<PreviousEmployerIncomeTdsDTO>
	// PreviousEmployerIncomeTdsDtoList,
	// @RequestParam("companyId") Long companyId, HttpServletRequest req) throws
	// PayRollProcessException {
	// logger.info("PreviousIncome employeeId - " + employeeId);
	// Long empId = Long.parseLong(employeeId);
	// DateUtils dateUtils = new DateUtils();
	// Date currentDate = dateUtils.getCurrentDate();
	// FinancialYear financialYear =
	// financialYearService.findCurrentFinancialYear(currentDate, companyId);
	// List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList =
	// previousEmployerIncomeAdaptor
	// .uiDtoToDatabaseModelList(PreviousEmployerIncomeTdsDtoList, empId,
	// financialYear.getFinancialYearId());
	//
	// List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList1 = new
	// ArrayList<PreviousEmployerIncomeTds>();
	//
	// List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsListOld =
	// previousEmployerIncomeService
	// .getPreviousEmployerIncomeList(empId, companyId);
	//
	// previousEmployerIncomeTdsList.forEach(previousEmployerIncomeTds -> {
	// if (previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null
	// && previousEmployerIncomeTds.getAmount() == null) {
	//
	// } else if (previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() ==
	// null) {
	// previousEmployerIncomeTdsList1.add(previousEmployerIncomeTds);
	// // boolean newFlag = previousEmployerIncomeTds == null
	// // || previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null;
	// // editLogInfoWithoutCG(previousEmployerIncomeTds, newFlag, req);
	// } else {
	// if (previousEmployerIncomeTds.getAmount() == null)
	// previousEmployerIncomeTds.setAmount(new BigDecimal(0.0));
	// for (PreviousEmployerIncomeTds previousEmployerIncomeTdsOld :
	// previousEmployerIncomeTdsListOld) {
	//
	// if (previousEmployerIncomeTdsOld.getPreviousEmployerIncomeTdsId()
	// .equals(previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId())
	// && previousEmployerIncomeTdsOld.getAmount()
	// .compareTo(previousEmployerIncomeTds.getAmount()) != 0) {
	//
	// previousEmployerIncomeTdsList1.add(previousEmployerIncomeTds);
	// // boolean newFlag = previousEmployerIncomeTds == null
	// // || previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null;
	// // editLogInfoWithoutCG(previousEmployerIncomeTds, newFlag, req);
	// }
	// } // end inner for
	// }
	// });
	// previousEmployerIncomeService.save(previousEmployerIncomeTdsList1,
	// companyId);
	// }

	@PostMapping("/saveHouseRentInfo")
	public void saveHouseRentInfo(@RequestPart("fileInfo") List<MultipartFile> fileInfo, HttpServletRequest request)
			throws Exception {

		TdsHouseRentInfo tdsHouseRentInfo = new TdsHouseRentInfo();

		JSONObject jsonObj = new JSONObject(request.getParameter("fileInfo"));

		if (jsonObj.has("landlordName") && !jsonObj.isNull("landlordName")) {
			tdsHouseRentInfo.setLandlordName(jsonObj.getString("landlordName"));
		}

		if (jsonObj.has("landlordPan") && !jsonObj.isNull("landlordPan")) {
			tdsHouseRentInfo.setLandlordPan(jsonObj.getString("landlordPan"));
		}
		if (jsonObj.has("landlordAddress") && !jsonObj.isNull("landlordAddress")) {
			tdsHouseRentInfo.setAddressOfLandlord(jsonObj.getString("landlordAddress"));
		}
		if (jsonObj.has("tdsHouseRentInfoId") && !jsonObj.isNull("tdsHouseRentInfoId")) {
			tdsHouseRentInfo.setTdsHouseRentInfoId(Long.valueOf(jsonObj.optLong("tdsHouseRentInfoId")));
			tdsHouseRentInfo.setDateUpdated(new Date());
			String fromDate = jsonObj.getString("dateCreated");
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			tdsHouseRentInfo.setDateCreated(date1);
		} else {
			tdsHouseRentInfo.setDateCreated(new Date());
		}
		if (jsonObj.has("tdsTransactionId") && !jsonObj.isNull("tdsTransactionId")) {
			TdsTransaction tdsTransaction = new TdsTransaction();
			tdsTransaction.setTdsTransactionId(Long.valueOf(jsonObj.optLong("tdsTransactionId")));
			tdsHouseRentInfo.setTdsTransaction(tdsTransaction);
		}
		if (jsonObj.has("fromDate")) {
			String fromDate = jsonObj.getString("fromDate");
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			tdsHouseRentInfo.setFromDate(date1);
		}
		if (jsonObj.has("toDate")) {
			String toDate = jsonObj.getString("toDate");
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
			tdsHouseRentInfo.setToDate(date2);
		}
		if (jsonObj.has("totalRental")) {
			tdsHouseRentInfo.setTotalRental(BigDecimal.valueOf(jsonObj.optDouble("totalRental")));
		}
		if (jsonObj.has("rentalPropertyAddress") && !jsonObj.isNull("rentalPropertyAddress")) {
			tdsHouseRentInfo.setAddressOfRentalProperty(jsonObj.getString("rentalPropertyAddress"));
		}

		if (jsonObj.has("userId") && !jsonObj.isNull("userId")) {
			tdsHouseRentInfo.setUserId((BigInteger.valueOf(jsonObj.optLong("userId"))));
		}

		// tdsHouseRentInfo.setUserId(BigInteger.valueOf(Long.valueOf(userId)));
		tdsHouseRentInfo.setActiveStatus(StatusMessage.ACTIVE_CODE);
		System.out.println("--------->>>>>JSONObject====================" + " " + jsonObj);

		System.out.println("tdsHouseRentInfo========" + tdsHouseRentInfo.getLandlordName());

		tdsHouseRentInfoService.saveHouseRentInfo(tdsHouseRentInfo, fileInfo);

	}

	@RequestMapping(path = "/houseRentInfo/{tdsTransactionId}", method = RequestMethod.GET)
	public @ResponseBody List<TdsHouseRentInfoDTO> getHouseRentInfoList(
			@PathVariable("tdsTransactionId") String tdsTransactionId) throws PayRollProcessException {
		return tdsHouseRentInfoAdaptor
				.databaseModelToUiDtoList(tdsHouseRentInfoService.getHouseRentInfoList(Long.valueOf(tdsTransactionId)));
	}

	@RequestMapping(path = "/tdsHouseRentInfoFile/{tdsHouseRentFileInfoId}", method = RequestMethod.GET)
	public @ResponseBody int deleteHouseRentInfoFile(
			@PathVariable("tdsHouseRentFileInfoId") String tdsHouseRentFileInfoId) throws PayRollProcessException {

		return tdsHouseRentInfoService.deleteHouseRentInfoFile(Long.valueOf(tdsHouseRentFileInfoId));
	}

	@RequestMapping(path = "/deleteTdsHouseRentInfo/{tdsHouseRentInfoId}", method = RequestMethod.GET)
	public @ResponseBody int deleteTdsHouseRentInfo(@PathVariable("tdsHouseRentInfoId") String tdsHouseRentInfoId)
			throws PayRollProcessException {
		return tdsHouseRentInfoService.deleteTdsHouseRentInfo(Long.valueOf(tdsHouseRentInfoId));
	}

	@RequestMapping(path = "/UpdateStatusCount/{companyId}/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody Long getTDSTransactionUpdateStatusCount(@PathVariable("companyId") String companyId,
			@PathVariable("employeeId") String employeeId) throws PayRollProcessException {
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearRepository.getFinancialYear(currentDate, Long.valueOf(companyId));
		return tdsTransactionService.getTDSTransactionUpdateStatusCount(Long.valueOf(employeeId),
				financialYear.getFinancialYearId());
	}

	// new previous employeeIncome

	@RequestMapping(path = "/tdsPreviousEmployerIncome", method = RequestMethod.POST)
	public void savePreviousEmployerIncomeList(@RequestParam("employeeId") String employeeId,
			@RequestPart("info") List<PreviousEmployerIncomeTdsDTO> PreviousEmployerIncomeTdsDtoList,
			@RequestPart("fileInfo") List<MultipartFile> fileInfo, @RequestParam("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("PreviousIncome employeeId - " + employeeId);
		Long empId = Long.parseLong(employeeId);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList = previousEmployerIncomeAdaptor
				.uiDtoToDatabaseModelList(PreviousEmployerIncomeTdsDtoList, empId, financialYear.getFinancialYearId());

		List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsList1 = new ArrayList<PreviousEmployerIncomeTds>();

		List<PreviousEmployerIncomeTds> previousEmployerIncomeTdsListOld = previousEmployerIncomeService
				.getPreviousEmployerIncomeList(empId, companyId);

		previousEmployerIncomeTdsList.forEach(previousEmployerIncomeTds -> {
			if (previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null
					&& previousEmployerIncomeTds.getAmount() == null) {

			} else if (previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null) {
				previousEmployerIncomeTdsList1.add(previousEmployerIncomeTds);
				// boolean newFlag = previousEmployerIncomeTds == null
				// || previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null;
				// editLogInfoWithoutCG(previousEmployerIncomeTds, newFlag, req);
			} else {
				if (previousEmployerIncomeTds.getAmount() == null)
					previousEmployerIncomeTds.setAmount(new BigDecimal(0.0));
				for (PreviousEmployerIncomeTds previousEmployerIncomeTdsOld : previousEmployerIncomeTdsListOld) {

					if (previousEmployerIncomeTdsOld.getPreviousEmployerIncomeTdsId()
							.equals(previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId())
							&& previousEmployerIncomeTdsOld.getAmount()
									.compareTo(previousEmployerIncomeTds.getAmount()) != 0) {

						previousEmployerIncomeTdsList1.add(previousEmployerIncomeTds);
						// boolean newFlag = previousEmployerIncomeTds == null
						// || previousEmployerIncomeTds.getPreviousEmployerIncomeTdsId() == null;
						// editLogInfoWithoutCG(previousEmployerIncomeTds, newFlag, req);
					}
				} // end inner for
			}
		});

		previousEmployerIncomeService.save(previousEmployerIncomeTdsList1, empId, companyId, fileInfo,
				financialYear.getFinancialYearId());
	}

	// delete
	@RequestMapping(path = "/deletePreviousEmployerIncomeFileInfo/{previousEmployerIncomeFileId}", method = RequestMethod.GET)
	public @ResponseBody int deletePreviousEmployerIncomeFileInfo(
			@PathVariable("previousEmployerIncomeFileId") Long previousEmployerIncomeFileId)
			throws PayRollProcessException {

		return previousEmployerIncomeService.deletePreviousEmployerIncomeFileInfo(previousEmployerIncomeFileId);
	}

	// get All files List
	@RequestMapping(path = "/previousEmployerIncomeFile", method = RequestMethod.GET)
	public @ResponseBody List<PreviousEmployerIncomeFileDTO> getPreviousEmployerIncomeFileList(
			@RequestParam("employeeId") String employeeId, @RequestParam("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);

		Long empId = Long.parseLong(employeeId);
		List<PreviousEmployerIncomeFile> previousEmployerIncomeFileList = previousEmployerIncomeService
				.getPreviousEmployerIncomeFileList(empId, financialYear.getFinancialYearId());
		System.out.println(previousEmployerIncomeFileList);
		if (previousEmployerIncomeFileList != null)
			return previousEmployerIncomeAdaptor.databaseModelToUiDtoList(previousEmployerIncomeFileList);
		else
			throw new ErrorHandling("Tds Previous Employee Income files not uploaded");
	}

	@RequestMapping(path = "/financialYear/{companyId}", method = RequestMethod.GET)
	public @ResponseBody FinancialYearDTO getCurrentFinancialYear(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws ErrorHandling {

		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);

		FinancialYearDTO financialYearDTO = new FinancialYearDTO();

		financialYearDTO.setFinancialYearId(financialYear.getFinancialYearId());
		financialYearDTO.setFinancialYear(financialYear.getFinancialYear());
		financialYearDTO.setDateFrom(financialYear.getDateFrom());
		financialYearDTO.setDateTo(financialYear.getDateTo());
		return financialYearDTO;

	}

}
