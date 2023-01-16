package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.payroll.TdsTransactionDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payroll.TdsTransaction;

public class TdsTransactionAdaptor{

	private static final Logger logger = LoggerFactory.getLogger(TdsTransactionAdaptor.class);
	public List<TdsTransactionDTO> databaseModelToObjectArray(List<Object[]> objectTdsTransactionList, String financialYear) {
		List<TdsTransactionDTO> tdsTransactionDtoList = new ArrayList<>();
		for (Object[] tdsInvestmentSummaryObj : objectTdsTransactionList) {
			TdsTransactionDTO tdsTransactionDto = new TdsTransactionDTO();
			DateUtils dateUtils = new DateUtils();
			Long tdsGroupId = tdsInvestmentSummaryObj[0] != null ? Long.parseLong(tdsInvestmentSummaryObj[0].toString())
					: null;
			Long tdsSectionId = tdsInvestmentSummaryObj[1] != null
					? Long.parseLong(tdsInvestmentSummaryObj[1].toString())
					: null;
			String tdsSectionName = tdsInvestmentSummaryObj[2] != null ? (String) tdsInvestmentSummaryObj[2] : null;
//			String tdsDescription = tdsInvestmentSummaryObj[3] != null ? (String) tdsInvestmentSummaryObj[3] : null;
			String tdsGroupName = tdsInvestmentSummaryObj[4] != null ? (String) tdsInvestmentSummaryObj[4] : null;
			BigDecimal investmentAmount = tdsInvestmentSummaryObj[5] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[5].toString()))
					: null;
			Long tdsTransactionId = tdsInvestmentSummaryObj[6] != null
					? Long.parseLong(tdsInvestmentSummaryObj[6].toString())
					: null;
			String approveStatus = tdsInvestmentSummaryObj[7] != null ? (String) tdsInvestmentSummaryObj[7] : null;
			Long userId = tdsInvestmentSummaryObj[8] != null ? Long.parseLong(tdsInvestmentSummaryObj[8].toString())
					: null;
			Date dateCreated = tdsInvestmentSummaryObj[9] != null ? (Date) tdsInvestmentSummaryObj[9] : null;
			BigDecimal maxLimit = tdsInvestmentSummaryObj[3] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[3].toString()))
					: null;
			Long employeeId = tdsInvestmentSummaryObj[10] != null
					? Long.parseLong(tdsInvestmentSummaryObj[10].toString())
					: null;
			String status = tdsInvestmentSummaryObj[11] != null ? (String) tdsInvestmentSummaryObj[11] : null;
//			String financialYear = tdsInvestmentSummaryObj[12] != null ? (String) tdsInvestmentSummaryObj[12] : null;
 			String city = tdsInvestmentSummaryObj[13] != null ? (String) tdsInvestmentSummaryObj[13] : null;
 			Long tdsGroupMasterId=tdsInvestmentSummaryObj[14] != null ? Long.parseLong(tdsInvestmentSummaryObj[14].toString()): null;
 			BigDecimal tdsGroupmaxLimit = tdsInvestmentSummaryObj[15] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[15].toString()))
					: null;
			String remarks = tdsInvestmentSummaryObj[16] != null ? (String) tdsInvestmentSummaryObj[16] : null;
//			String landlordName = tdsInvestmentSummaryObj[17] != null ? (String) tdsInvestmentSummaryObj[17] : null;
//			String landlordPAN = tdsInvestmentSummaryObj[18] != null ? (String) tdsInvestmentSummaryObj[18] : null;
			
 			tdsTransactionDto.setTdsGroupSetupId(tdsGroupId);
			tdsTransactionDto.setTdsSectionSetupId(tdsSectionId);
			tdsTransactionDto.setTdsSectionName(tdsSectionName);
			tdsTransactionDto.setTdsGroupName(tdsGroupName);
			tdsTransactionDto.setInvestmentAmount(investmentAmount);
//			tdsTransactionDto.setTdsDescription(tdsGroupName  + " - " + tdsSectionName);
			tdsTransactionDto.setTdsDescription(tdsSectionName);
			tdsTransactionDto.setTdsTransactionId(tdsTransactionId);
			tdsTransactionDto.setApproveStatus(approveStatus);
			tdsTransactionDto.setUserId(userId);
			if (dateCreated != null)
				tdsTransactionDto.setDateCreated(dateUtils.getDateStringWirhYYYYMMDD(dateCreated));
			tdsTransactionDto.setMaxLimit(maxLimit);
			tdsTransactionDto.setEmployeeId(employeeId);
			tdsTransactionDto.setStatus(status);
			tdsTransactionDto.setFinancialYear(financialYear);
			tdsTransactionDto.setCity(city);
			tdsTransactionDto.setTdsGroupMasterId(tdsGroupMasterId);
			tdsTransactionDto.setTdsGroupmaxLimit(tdsGroupmaxLimit);
			tdsTransactionDto.setRemarks(remarks);
//			tdsTransactionDto.setLandlordName(landlordName);
//			tdsTransactionDto.setLandlordPAN(landlordPAN);
			tdsTransactionDtoList.add(tdsTransactionDto);

			/*
			 * for (TdsTransactionDTO tdsTransactionDto1:tdsTransactionDtoList) {
			 * tdsTransactionDto1.setSrno(srno); }
			 */
		}

		return tdsTransactionDtoList;
	}

	public List<TdsTransaction> uiDtoToTdsTransactionModelList(List<TdsTransactionDTO> tdsTransactionDtoList,
			Long employeeId) {
		List<TdsTransaction> tdsTransactionList = new ArrayList<>();

		for (TdsTransactionDTO tdsTransactionDto : tdsTransactionDtoList) {
			tdsTransactionList.add(uiDtoToDatabaseModel(tdsTransactionDto, employeeId));
		}
		return tdsTransactionList;
	}

	public TdsTransaction uiDtoToDatabaseModel(TdsTransactionDTO tdsTransactionDto, Long employeeId) {
		TdsTransaction tdsTransaction = new TdsTransaction();
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		TdsSectionSetup tdsSectionSetup = new TdsSectionSetup();
		Employee employee = new Employee();
		DateUtils dateUtils = new DateUtils();
		tdsGroupSetup.setTdsGroupId(tdsTransactionDto.getTdsGroupSetupId());
		tdsSectionSetup.setTdsSectionId(tdsTransactionDto.getTdsSectionSetupId());
		tdsTransaction.setTdsSectionSetup(tdsSectionSetup);
		tdsTransaction.setStatus(tdsTransactionDto.getStatus());
		tdsTransaction.setTdsGroupSetup(tdsGroupSetup);
		tdsTransaction.setTdsTransactionId(tdsTransactionDto.getTdsTransactionId());
		tdsTransaction.setInvestmentAmount(tdsTransactionDto.getInvestmentAmount());
		tdsTransaction.setApproveStatus(tdsTransactionDto.getApproveStatus());
		tdsTransaction.setProof(tdsTransactionDto.getProof());
		tdsTransaction.setNoOfDocuments(Long.valueOf(tdsTransactionDto.getNoOfDocuments()));
		tdsTransaction.setMaxLimit(tdsTransactionDto.getMaxLimit());
		tdsTransaction.setFileLocation(tdsTransactionDto.getFileLocation());
		tdsTransaction.setCity(tdsTransactionDto.getCity());
		tdsTransaction.setApprovedAmount(tdsTransactionDto.getApprovedAmount());
		employee.setEmployeeId(employeeId);
		tdsTransaction.setEmployee(employee);
		tdsTransaction.setUserId(tdsTransactionDto.getUserId());
		tdsTransaction.setRemarks(tdsTransactionDto.getRemarks());
		tdsTransaction.setUserIdUpdate(tdsTransactionDto.getUserIdUpdate());
		tdsTransaction.setTdsTransactionUpdateStatus(tdsTransactionDto.getTdsTransactionUpdateStatus());
		/*tdsTransaction.setLandlordName(tdsTransactionDto.getLandlordName());
		tdsTransaction.setLandlordPAN(tdsTransactionDto.getLandlordPAN());*/
		tdsTransaction.setDateUpdate(new Date());
		if (tdsTransactionDto.getDateCreated() != null)
			tdsTransaction.setDateCreated(dateUtils.getDateWirhYYYYMMDD(tdsTransactionDto.getDateCreated()));
		else
			tdsTransaction.setDateCreated(new Date());
		return tdsTransaction;
	}

	public List<TdsTransactionDTO> databaseModelToTdsInvestmentArray(List<Object[]> objectInvestmentSummaryList) {
		List<TdsTransactionDTO> tdsInvestmentSummaryList = new ArrayList<>();
		for (Object[] tdsInvestmentSummaryObj : objectInvestmentSummaryList) {

			DateUtils dateUtils = new DateUtils();
			TdsTransactionDTO tdsTransactionDto = new TdsTransactionDTO();
			Long tdsGroupId = tdsInvestmentSummaryObj[0] != null ? Long.parseLong(tdsInvestmentSummaryObj[0].toString())
					: null;
			Long tdsSectionId = tdsInvestmentSummaryObj[1] != null
					? Long.parseLong(tdsInvestmentSummaryObj[1].toString())
					: null;
			String tdsSectionName = tdsInvestmentSummaryObj[2] != null ? (String) tdsInvestmentSummaryObj[2] : null;
			String tdsDescription = tdsInvestmentSummaryObj[3] != null ? (String) tdsInvestmentSummaryObj[3] : null;
			BigDecimal maxLimit = tdsInvestmentSummaryObj[4] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[4].toString()))
					: null;
//			String financialYear = tdsInvestmentSummaryObj[5] != null ? (String) tdsInvestmentSummaryObj[5] : null;
			BigDecimal investmentAmount = tdsInvestmentSummaryObj[6] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[6].toString()))
					: null;
			Date dateCreated = tdsInvestmentSummaryObj[7] != null ? (Date) (tdsInvestmentSummaryObj[7]) : null;
			String status = tdsInvestmentSummaryObj[8] != null ? (String) tdsInvestmentSummaryObj[8] : null;
			BigDecimal approvedAmount = tdsInvestmentSummaryObj[9] != null
					? (new BigDecimal(tdsInvestmentSummaryObj[9].toString()))
					: null;
			tdsTransactionDto.setTdsGroupSetupId(tdsGroupId);
			tdsTransactionDto.setTdsSectionSetupId(tdsSectionId);
			tdsTransactionDto.setTdsSectionName(tdsSectionName);
			tdsTransactionDto.setInvestmentAmount(investmentAmount);
			tdsTransactionDto.setTdsDescription(tdsDescription);
			tdsTransactionDto.setDateCreated(dateUtils.getDateStringWirhYYYYMMDD(dateCreated));
			tdsTransactionDto.setStatus(status);
//			tdsTransactionDto.setFinancialYear(financialYear);
			tdsTransactionDto.setApprovedAmount(approvedAmount);
			tdsTransactionDto.setMaxLimit(maxLimit);
			tdsInvestmentSummaryList.add(tdsTransactionDto);
		}

		return tdsInvestmentSummaryList;
	}

}
