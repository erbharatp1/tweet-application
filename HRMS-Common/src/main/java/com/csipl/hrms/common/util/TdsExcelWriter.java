package com.csipl.hrms.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.payroll.TdsTransactionDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.common.Company;

public class TdsExcelWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
	private static int HashMap;

	private static double convertBigdecimalToDouble(BigDecimal bigDecimalValue) {
		double doubleValue;
		String str123;
		if (bigDecimalValue != null) {
			doubleValue = bigDecimalValue.doubleValue();
			str123 = bigDecimalValue.toString();
			// System.out.println("string value..." + str123);
		} else
			doubleValue = 0;
		return doubleValue;
	}

	private static String convertBigdecimalToString(BigDecimal bigDecimalValue) {
		double doubleValue;
		String strValue;
		if (bigDecimalValue != null) {
			strValue = bigDecimalValue.toString();
		} else
			strValue = "0.00";
		return strValue;
	}
 

	public static Workbook annualTax(List<TdsSummaryChangeDTO> tdsSummaryChangeDTO, String[] columns,
			String financialYear, Company company) {
		 
//		String toProcessMnth = (fromProcessMonth.equals(toProcessMonth) ? "" : ("to " + toProcessMonth));
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
 
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Statement of Annual Tax");

		// Create a Font for styling header cells 
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font headerFont1 = workbook.createFont();
		headerFont1.setBold(true);
		headerFont1.setFontHeightInPoints((short) 11);
		headerFont1.setColor(IndexedColors.BLACK.getIndex());
		
		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.RIGHT);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
/*		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);*/
		

		CellStyle addZeroBold = workbook.createCellStyle();
		addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		addZeroBold.setFont(headerFont1);
		addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
		addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle headerCellStyle1t = workbook.createCellStyle();
		headerCellStyle1t.setFont(headerFont1);
		headerCellStyle1t.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1t.setVerticalAlignment(VerticalAlignment.CENTER);
		
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// Create Other rows and cells with employees data 
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		Cell companyCell =row0.createCell(0);
		companyCell.setCellStyle(headerCellStyle11);
		companyCell.setCellValue("For "+company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue("Statement of Annual Tax for FY "+financialYear);
		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));
		BigDecimal totalGrossPaid = BigDecimal.ZERO;
		BigDecimal totalGrossSum = BigDecimal.ZERO;
		
		if (tdsSummaryChangeDTO.isEmpty() || tdsSummaryChangeDTO.equals(" ")) {

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);
 
			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			System.out.println("Tds Report");
			// Create a Row
			Row headerRow = sheet.createRow(2);

			// Creating cells
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle1);
			}
 
			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.##"));
			int rowNum = 3;
 
			BigDecimal sumPTamout = new BigDecimal(0);

			for (TdsSummaryChangeDTO tdsSummaryChange : tdsSummaryChangeDTO) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(tdsSummaryChange.getEmployeeCode());
					row.createCell(1).setCellValue(tdsSummaryChange.getEmployeeName() );
					row.createCell(2).setCellValue(tdsSummaryChange.getDepartment());
					row.createCell(3).setCellValue(tdsSummaryChange.getDesignation());
					
					Cell dojCell =row.createCell(4);
					dojCell.setCellStyle(dateCellStyle);
					dojCell.setCellValue(tdsSummaryChange.getDateOFJoining());
					row.createCell(5).setCellValue(tdsSummaryChange.getPanNumber());
					
					Cell totalGrossPaidCell = row.createCell(6);
					totalGrossPaidCell.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalGrossPaid()));
					totalGrossPaidCell.setCellStyle(cellStyle112);
					
					totalGrossPaid = totalGrossPaid.add(tdsSummaryChange.getTotalGrossPaid() != null ? tdsSummaryChange.getTotalGrossPaid() : new BigDecimal(0.00));
					
					
					Cell totalGrossToBePaidCell = row.createCell(7);
					totalGrossToBePaidCell.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalGrossToBePaid()));
					totalGrossToBePaidCell.setCellStyle(cellStyle112);
					
					
					Cell totalGross = row.createCell(8);
					totalGross.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid())));
					totalGross.setCellStyle(cellStyle112);
					
					totalGrossSum = totalGrossSum.add(tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid()) != null ? tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid()) : new BigDecimal(0.00));
					
					Cell hra = row.createCell(9);
					hra.setCellValue(convertBigdecimalToString(tdsSummaryChange.getSection10()));
					hra.setCellStyle(cellStyle112);
					 
					Cell providentFundEmployers = row.createCell(10);
					providentFundEmployers.setCellValue(convertBigdecimalToString(tdsSummaryChange.getExempPfAmount().add(tdsSummaryChange.getProvidentFund())));
					providentFundEmployers.setCellStyle(cellStyle112);
					
					Cell standardDeduction = row.createCell(11);
					standardDeduction.setCellValue(convertBigdecimalToString(tdsSummaryChange.getExempStandard()));
					standardDeduction.setCellStyle(cellStyle112);
					
					Cell professionalEmploymentTax = row.createCell(12);
					professionalEmploymentTax.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalIncomeProfessionalTax()));
					professionalEmploymentTax.setCellStyle(cellStyle112);
					
					Cell taxableIncome = row.createCell(13);
					taxableIncome.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid()).subtract(tdsSummaryChange.getSection10().add(tdsSummaryChange.getExempPfAmount().add(tdsSummaryChange.getProvidentFund())).add(tdsSummaryChange.getExempStandard()).add(tdsSummaryChange.getTotalIncomeProfessionalTax()))));
					taxableIncome.setCellStyle(cellStyle112); 
					
					Cell otherIncomeReceived = row.createCell(14);
					otherIncomeReceived.setCellValue(convertBigdecimalToString(tdsSummaryChange.getOtherIncome().add(tdsSummaryChange.getPreEmpIncome())));
					otherIncomeReceived.setCellStyle(cellStyle112); 
					
					Cell totalGrossUnderAllHeads = row.createCell(15);
					totalGrossUnderAllHeads.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid()).subtract(tdsSummaryChange.getSection10().add(tdsSummaryChange.getExempPfAmount().add(tdsSummaryChange.getProvidentFund())).add(tdsSummaryChange.getExempStandard()).add(tdsSummaryChange.getTotalIncomeProfessionalTax())).add(tdsSummaryChange.getOtherIncome().add(tdsSummaryChange.getPreEmpIncome()))));
					totalGrossUnderAllHeads.setCellStyle(cellStyle112);
					 
					Cell deductionfromChapterVIA = row.createCell(16);
					deductionfromChapterVIA.setCellValue(convertBigdecimalToString(tdsSummaryChange.getChapter6a().subtract(tdsSummaryChange.getExempPfAmount().add(tdsSummaryChange.getProvidentFund()))));
					deductionfromChapterVIA.setCellStyle(cellStyle112); 
					
					Cell deductionfromSection24 = row.createCell(17); 
					deductionfromSection24.setCellValue(convertBigdecimalToString(tdsSummaryChange.getSection24()));
					deductionfromSection24.setCellStyle(cellStyle112);
					
					Cell totalTaxableIncome = row.createCell(18);
					totalTaxableIncome.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTaxableIncome()));
					totalTaxableIncome.setCellStyle(cellStyle112);
					
					Cell taxOnTaxableIncome = row.createCell(19);
					taxOnTaxableIncome.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTax()));
					taxOnTaxableIncome.setCellStyle(cellStyle112);
					
					if((tdsSummaryChange.getTotalGrossToBePaid().add(tdsSummaryChange.getTotalGrossPaid())).compareTo(new BigDecimal(HrmsGlobalConstantUtil.section84ARebateLimit))<=0) {
						Cell taxExemptionUnderRebate = row.createCell(20);
						taxExemptionUnderRebate.setCellValue(convertBigdecimalToString(new BigDecimal(HrmsGlobalConstantUtil.section84ARebateAmount)));
						taxExemptionUnderRebate.setCellStyle(cellStyle112);
					}else {
						Cell taxExemptionUnderRebate = row.createCell(20);
						taxExemptionUnderRebate.setCellValue(convertBigdecimalToString(new BigDecimal("0.00")));
						taxExemptionUnderRebate.setCellStyle(cellStyle112);
					}
					 
					Cell incomeTax = row.createCell(21);
					incomeTax.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTax()));
					incomeTax.setCellStyle(cellStyle112);
					
					Cell educationalCess = row.createCell(22);
					educationalCess.setCellValue(convertBigdecimalToString(tdsSummaryChange.getEducationCess()));
					educationalCess.setCellStyle(cellStyle112);
					
					Cell surcharge = row.createCell(23);
					surcharge.setCellValue(convertBigdecimalToString(tdsSummaryChange.getSurcharge()));
					surcharge.setCellStyle(cellStyle112);
					
					Cell totalTaxLiability = row.createCell(24);
					totalTaxLiability.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalTax()));
					totalTaxLiability.setCellStyle(cellStyle112);
					
					Cell taxPaid = row.createCell(25);
					taxPaid.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalTaxPaid().add(tdsSummaryChange.getPotalTax())));
					taxPaid.setCellStyle(cellStyle112);
					
					Cell totalTaxPayable = row.createCell(26);
					totalTaxPayable.setCellValue(convertBigdecimalToString(tdsSummaryChange.getNetTaxYearly()));
					totalTaxPayable.setCellStyle(cellStyle112);
					 
//					sumPTamout = sumPTamout.add(reportPayOut.getPt() != null ? reportPayOut.getPt() : BigDecimal.ZERO);
//					Cell createdCell = row.createCell(4);
//					createdCell.setCellValue(convertBigdecimalToString(reportPayOut.getPt()));
//					createdCell.setCellStyle(cellStyle112);
//
//					row.createCell(5).setCellValue(reportPayOut.getProcessMonth());
//					row.createCell(6).setCellValue(reportPayOut.getStateName());
			}
			// headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			
			Row row = sheet.createRow(rowNum);

			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
			Cell cellT = row.createCell(0);
			cellT.setCellValue(" Total ");
			cellT.setCellStyle(headerCellStyle1t);

			Cell cellT5 = row.createCell(6);
			cellT5.setCellValue(convertBigdecimalToString(totalGrossPaid));
			cellT5.setCellStyle(addZeroBold);
			
			
			Cell cellT8 = row.createCell(8);
			cellT8.setCellValue(convertBigdecimalToString(totalGrossSum));
			cellT8.setCellStyle(addZeroBold);
			
//			Row row11 = sheet.createRow(rowNum++);
//			for (int i = 0; i < 3; i++) {
//				Cell cell00 = row11.createCell(i);
//				cell00.setCellStyle(headerCellStyle1);
//			}
 
//			Cell cell0 = row11.createCell(3);
//			cell0.setCellValue(" Total");
//			cell0.setCellStyle(headerCellStyle1);
//			Cell cell11 = row11.createCell(4);
//			cell11.setCellValue(convertBigdecimalToDouble(sumPTamout));
//			cell11.setCellStyle(headerCellStyle1);
//			Cell cell15 = row11.createCell(5);
//			cell15.setCellStyle(headerCellStyle1);
//			Cell cell16 = row11.createCell(6);
//			cell16.setCellStyle(headerCellStyle1);
			// Resize all columns to fit the content size
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
 
		return workbook;
	 
	}

	public static Workbook employeeTdsDeclaration(Map<String, List<TdsTransactionDTO>> map, List<String> columns,
			String financialYear, Company company, List<Long> sectionIdList) {
		 
//		String toProcessMnth = (fromProcessMonth.equals(toProcessMonth) ? "" : ("to " + toProcessMonth));
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
 
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Employee TDS Declaration");

		// Create a Font for styling header cells 
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle headerCellStyle1 = workbook.createCellStyle();
		headerCellStyle1.setFont(headerFont);
		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle1.setFillPattern(FillPatternType.FINE_DOTS);
		
		CellStyle headerCellStyle11 = workbook.createCellStyle();
		headerCellStyle11.setFont(headerFont);
		headerCellStyle11.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
/*		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);*/
		
		CellStyle cellStyle112 = workbook.createCellStyle();
		cellStyle112.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
		cellStyle112.setAlignment(HorizontalAlignment.CENTER);
		cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);
		 
		
		// Create Other rows and cells with employees data 
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		Cell companyCell =row0.createCell(0);
		companyCell.setCellStyle(headerCellStyle11);
		companyCell.setCellValue("For "+company.getCompanyName());

		Row row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		row1.createCell(0).setCellValue("Statement of Annual Tax for FY "+financialYear);
		// sheet.addMergedRegion(new CellRangeAddress(0,1,1,2));
 
		int cellSizeAutoSum = 0;
		
		if (map.isEmpty() || map.equals(" ")) {
			Font headerFont1 = workbook.createFont();
			headerFont1.setBold(true);
			headerFont1.setFontHeightInPoints((short) 16);
			headerFont1.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle12 = workbook.createCellStyle();
			headerCellStyle12.setFont(headerFont);
			headerCellStyle12.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle12.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle12.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle12.setFillPattern(FillPatternType.FINE_DOTS);

			Row headerRow = sheet.createRow(6);
			sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 8));
			Cell cell0 = headerRow.createCell(3);
			cell0.setCellValue(" Data not available");
			cell0.setCellStyle(headerCellStyle12);
		} else {
			System.out.println("Tds Report");
			// Create a Row
			
			Row row2 = sheet.createRow(2);
			
			Row headerRow = sheet.createRow(3);

			  
			int value=6;
			int other=7;
			// Creating cells
			for (int i = 0; i < columns.size(); i++) {
			 
				if(i<=5) {
					
//					sheet.addMergedRegion(new CellRangeAddress(2,3,0,i));
					
					Cell cell = headerRow.createCell(i);
//					cell.setCellValue(columns.get(i));
					cell.setCellStyle(headerCellStyle1);
					
					Cell cell2 = row2.createCell(i);
					cell2.setCellValue(columns.get(i));
					cell2.setCellStyle(headerCellStyle1);
				}else {
					 
					if (columns.size() - 1 == i) {
						Cell cells = headerRow.createCell(value);
						// cell.setCellValue(columns.get(i));
						cells.setCellStyle(headerCellStyle1);

						Cell otherincome = row2.createCell(value);
						otherincome.setCellValue(columns.get(i));
						otherincome.setCellStyle(headerCellStyle1);

						// break;
					} else {

						sheet.addMergedRegion(new CellRangeAddress(2, 2, value, other));
 
						Cell cell = row2.createCell(value);
						cell.setCellValue(columns.get(i));
						cell.setCellStyle(headerCellStyle1);

						if(columns.get(i).equals("NM")|| columns.get(i).equals("ME")) {
							Cell cell1 = row2.createCell(value);
							cell1.setCellValue("Section 10");
							cell1.setCellStyle(headerCellStyle1); 
						}
						
						Cell declar = headerRow.createCell(value);
						declar.setCellValue("Declared");
						declar.setCellStyle(headerCellStyle1);

						Cell Approved = headerRow.createCell(other);
						Approved.setCellValue("Approved");
						Approved.setCellStyle(headerCellStyle1);

						value++;
						other++;

						value = other++;
					}
				}
				
			}
 
			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.##"));
			int rowNum = 4; 
 
			BigDecimal sumPTamout = new BigDecimal(0);
			int otherIncomCell = --other;
			cellSizeAutoSum=--other;
			for(Map.Entry<String, List<TdsTransactionDTO>> data :map.entrySet()) {
				Row row = sheet.createRow(rowNum++);
				int	cellNum=6;
				
				for (Long col : sectionIdList) {
					Long sectionId = col;
					int i=0;
					for (TdsTransactionDTO tdsSummaryChange : data.getValue()) {
						i++;
						if (sectionIdList.contains(tdsSummaryChange.getTdsSectionSetupId())) {

							if (sectionId.equals(tdsSummaryChange.getTdsSectionSetupId())) {
								row.createCell(0).setCellValue(tdsSummaryChange.getEmployeeCode());
								row.createCell(1).setCellValue(tdsSummaryChange.getEmployeeName());
								row.createCell(2).setCellValue(tdsSummaryChange.getDepartment());
								row.createCell(3).setCellValue(tdsSummaryChange.getDesignation());
								Cell dojCell = row.createCell(4);
								dojCell.setCellStyle(dateCellStyle);
								dojCell.setCellValue(tdsSummaryChange.getDateOFJoining());
								row.createCell(5).setCellValue(tdsSummaryChange.getPanNumber());

								
								 Cell otherIncome = row.createCell(otherIncomCell);
								 otherIncome.setCellValue(convertBigdecimalToString(tdsSummaryChange.getTotalOtherIncome()));
								 otherIncome.setCellStyle(cellStyle112);
								
								Cell declaredAmount = row.createCell(cellNum++);
								declaredAmount.setCellValue(convertBigdecimalToString(tdsSummaryChange.getInvestmentAmount()));
								declaredAmount.setCellStyle(cellStyle112);

								Cell approvedAmount = row.createCell(cellNum++);
								approvedAmount.setCellValue(convertBigdecimalToString(tdsSummaryChange.getApprovedAmount()));
								approvedAmount.setCellStyle(cellStyle112);
								break;
							}
							
							if (data.getValue().size()  == i) {
								Cell declaredAmount = row.createCell(cellNum++);
								declaredAmount.setCellValue("0.00");
								declaredAmount.setCellStyle(cellStyle112);

								Cell approvedAmount = row.createCell(cellNum++);
								approvedAmount.setCellValue("0.00");
								approvedAmount.setCellStyle(cellStyle112);
								break;
							}

						} else {

							Cell declaredAmount = row.createCell(cellNum++);
							declaredAmount.setCellValue("0.00");
							declaredAmount.setCellStyle(cellStyle112);

							Cell approvedAmount = row.createCell(cellNum++);
							approvedAmount.setCellValue("0.00");
							approvedAmount.setCellStyle(cellStyle112);
							break;
						}
						
					}
					
					 
				}
				
			}
			// headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
//			Row row11 = sheet.createRow(rowNum++);
//			for (int i = 0; i < 3; i++) {
//				Cell cell00 = row11.createCell(i);
//				cell00.setCellStyle(headerCellStyle1);
//			}
 
//			Cell cell0 = row11.createCell(3);
//			cell0.setCellValue(" Total");
//			cell0.setCellStyle(headerCellStyle1);
//			Cell cell11 = row11.createCell(4);
//			cell11.setCellValue(convertBigdecimalToDouble(sumPTamout));
//			cell11.setCellStyle(headerCellStyle1);
//			Cell cell15 = row11.createCell(5);
//			cell15.setCellStyle(headerCellStyle1);
//			Cell cell16 = row11.createCell(6);
//			cell16.setCellStyle(headerCellStyle1);
			// Resize all columns to fit the content size
			 
		
		}
		
		int celln=	sheet.getRow(2).getPhysicalNumberOfCells();
		
		for (int i = 0; i < sheet.getRow(3).getPhysicalNumberOfCells(); i++) {
			sheet.setColumnWidth(i, 15 * 400);
		  }
		 
		return workbook;
	 
	}
}
