package com.csipl.hrms.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;

public class PayRegisterExcelWriter {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(PayRegisterExcelWriter.class);
	
	public static void addCustomObj(CustomEmpBank prvObj, ReportPayOutDTO report,HashMap<String ,CustomEmpBank> map  ) {
		 prvObj=map.get(report.getBankName());
		Integer countVal=prvObj.getCount()+1;
		BigDecimal netAmount=report.getNetPayableAmount().add(prvObj.getNetPayableAmount());
		prvObj.setCount(countVal);
		prvObj.setNetPayableAmount(netAmount);
		System.out.println("=====bankName=="+report.getBankName()+"===count"+prvObj.getCount());
	
	}
	
	
	public static Workbook paymentTransferSheet(List<ReportPayOutDTO> reportPayOutList, String[] columns,String[] sheet2Columns, String processMonth
			) throws IOException, InvalidFormatException {
	
		logger.info("paymentTransferSheet is calling : ");
		
		HashMap<String ,CustomEmpBank> map = new HashMap<String ,CustomEmpBank> ();
		for(ReportPayOutDTO report:reportPayOutList) {
			CustomEmpBank obj=new CustomEmpBank();
			obj.setCount(1);
			obj.setBankName(report.getBankName());
			obj.setNetPayableAmount(report.getNetPayableAmount());
			
		    if(map.get(report.getBankName())!=null?true:false) {
		    	addCustomObj(obj,report,map);
		    }else {
		    	map.put(report.getBankName(),obj );
		    }
		}
		int index = 0;
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Payment Summary");
		for(Map.Entry<String, CustomEmpBank> entry : map.entrySet()) {
			String bankName= entry.getValue().getBankName();
			 Sheet moreSheet = workbook.createSheet(bankName); 
		
			 for(int i=0 ;i <=reportPayOutList.size();i++) {
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			Font rowFont = workbook.createFont();
			rowFont.setBold(true);
			rowFont.setFontHeightInPoints((short) 14);
			rowFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

			CellStyle rowCellStyle = workbook.createCellStyle();
			rowCellStyle.setFont(rowFont);

			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
			Row row = moreSheet.createRow(0);
			String month = processMonth.substring(0, 3);
			int year = Integer.valueOf( processMonth.substring(4, 8));
			
			row.createCell(0).setCellValue("Bank Transfer Statement for the month of " + month + "," + year);
			Row headerRow = moreSheet.createRow(2);
			for (int j = 0; j< sheet2Columns.length; j++) {
				Cell cell = headerRow.createCell(j);
				cell.setCellValue(sheet2Columns[j]);
				cell.setCellStyle(headerCellStyle);
			}
			
			for (int k = 0; k < sheet2Columns.length; k++) {
				moreSheet.autoSizeColumn(k);
			}
			int rowNum = 3;
			int serialNo = 0;
			for(ReportPayOutDTO reportPayOut : reportPayOutList) {
				if(reportPayOut.getBankName().equals(entry.getValue().getBankName())) {
				Row row2 = moreSheet.createRow(rowNum++);
				row2.createCell(0).setCellValue(++serialNo);
				row2.createCell(1).setCellValue(reportPayOut.getEmployeeCode());
				row2.createCell(2).setCellValue(reportPayOut.getName());
				row2.createCell(3).setCellValue(reportPayOut.getDepartmentName());
				row2.createCell(4).setCellValue(reportPayOut.getBankName());
				row2.createCell(5).setCellValue(reportPayOut.getIfscCode());
				row2.createCell(6).setCellValue(reportPayOut.getAccountNumber());
				row2.createCell(7).setCellValue(convertBigdecimalToString(reportPayOut.getNetPayableAmount()));
				row2.createCell(8).setCellValue(reportPayOut.getEmployeeStatus());
				}
			}
			}
		}

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		Font rowFont = workbook.createFont();
		rowFont.setBold(true);
		rowFont.setFontHeightInPoints((short) 14);
		rowFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);

		CellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFont(rowFont);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		Row row = sheet.createRow(0);

		Cell company = row.createCell(0);
		company.setCellValue(reportPayOutList.get(0).getCompanyName());
		company.setCellStyle(rowCellStyle);
		System.out.println(reportPayOutList.get(0).getCompanyName());

		Row row1 = sheet.createRow(1);
		String month = processMonth.substring(0, 3);
		int year = Integer.valueOf( processMonth.substring(4, 8));
		
		row1.createCell(0).setCellValue("Bank Transfer Statement for the month of " + month + "," + year);
		Row headerRow = sheet.createRow(2);

		//creating headers
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i].toString());
			cell.setCellStyle(headerCellStyle);

		}
		
	
		int rowNum = 3;
		int totalCount=0;
		BigDecimal totalNetPay =BigDecimal.ZERO; 
			for(Map.Entry<String, CustomEmpBank> entry : map.entrySet()) {
				Row row2 = sheet.createRow(rowNum++);
				String bankName= entry.getValue().getBankName();
				Integer headCount= entry.getValue().getCount();
				BigDecimal netPay= entry.getValue().getNetPayableAmount();
				 totalCount =totalCount+headCount;
				 totalNetPay= totalNetPay.add(netPay);
				//System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue().getCount()+" Net amount"+ entry.getValue().getNetPayableAmount());
			row2.createCell(0).setCellValue(bankName);
			row2.createCell(1).setCellValue(headCount);
			row2.createCell(2).setCellValue(convertBigdecimalToString(netPay));
			
			}
			Row row2 = sheet.createRow(rowNum++);

			Cell cell = row2.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(headerCellStyle);
			
			row2.createCell(1).setCellValue(totalCount);
			row2.createCell(2).setCellValue(convertBigdecimalToString(totalNetPay));
		//	rowNum++;
		// Resize all columns to fit the content size
		
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return workbook;
	}
	
	
	//generate Salary sheet based on Pay sturture
		public static Workbook salarySheetHd(List<ReportPayOutDTO> reportPayOutDTOList, Object[] columns,
				String processMonth, String[] earnngPayHeadsId, String[] deductionPayHeadsId) {

			// , Map<String, String> payHeadsMap
			Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Salary Sheet");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

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
			CellStyle headerCellStyle111 = workbook.createCellStyle();
			headerCellStyle111.setFont(headerFont);
			headerCellStyle111.setAlignment(HorizontalAlignment.LEFT);
			headerCellStyle111.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle headerCellStyle112 = workbook.createCellStyle();
			headerCellStyle112.setAlignment(HorizontalAlignment.LEFT);
			headerCellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle cellStyle112 = workbook.createCellStyle();
			cellStyle112.setAlignment(HorizontalAlignment.RIGHT);
			cellStyle112.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle cellStyleCentre = workbook.createCellStyle();
			cellStyleCentre.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCentre.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle headerCellStyleTotal = workbook.createCellStyle();
			headerCellStyleTotal.setFont(headerFont);
			headerCellStyleTotal.setAlignment(HorizontalAlignment.RIGHT);
			headerCellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle headerCellStyleCode = workbook.createCellStyle();
			headerCellStyleCode.setFont(headerFont);
			headerCellStyleCode.setAlignment(HorizontalAlignment.LEFT);
			headerCellStyleCode.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle addZero = workbook.createCellStyle();
			addZero.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
			addZero.setAlignment(HorizontalAlignment.RIGHT);
			addZero.setVerticalAlignment(VerticalAlignment.CENTER);
			CellStyle addZeroBold = workbook.createCellStyle();
			addZeroBold.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
			addZeroBold.setFont(headerFont);
			addZeroBold.setAlignment(HorizontalAlignment.RIGHT);
			addZeroBold.setVerticalAlignment(VerticalAlignment.CENTER);
			Row row0 = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			Cell cellCom = row0.createCell(0);
			cellCom.setCellValue("Salary Sheet - " + processMonth);
			cellCom.setCellStyle(headerCellStyle111);

			Row headerRow = sheet.createRow(1);
			// Creating cells
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i].toString());
				cell.setCellStyle(headerCellStyle);

			}

			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			
			CellStyle dateCellStyles = workbook.createCellStyle();
			dateCellStyles.setDataFormat(createHelper.createDataFormat().getFormat(""));
			
			int rowNum = 2;
			// BigDecimal sum = BigDecimal.ZERO;

			BigDecimal totalSum = BigDecimal.ZERO;
			BigDecimal deductionTotalSum = BigDecimal.ZERO;
			BigDecimal totalNetPayable = BigDecimal.ZERO;

			Map<String, BigDecimal> totalSumMap = new HashMap<String, BigDecimal>();
			Map<String, BigDecimal> deductionTotalSumMap = new HashMap<String, BigDecimal>();

			if (reportPayOutDTOList != null) {
				int index = 0;
				int deductionIndex = 0;

				for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
					BigDecimal sum = BigDecimal.ZERO;
					BigDecimal deductionSum = BigDecimal.ZERO;
					Row row = sheet.createRow(rowNum++);

					if (reportPayOutDTO.getEmployeeCode() != null && reportPayOutDTO.getEmployeeCode().trim() != "")
						row.createCell(0).setCellValue(reportPayOutDTO.getEmployeeCode());
					else
						row.createCell(0).setCellValue("Under Process");

					row.createCell(1).setCellValue(reportPayOutDTO.getEmpName());

					row.createCell(2).setCellValue(reportPayOutDTO.getDesignationName());

					row.createCell(3).setCellValue(reportPayOutDTO.getDepartmentName());

					Cell createdDateCell = row.createCell(4);
					createdDateCell.setCellValue(reportPayOutDTO.getDateOfJoining());
					createdDateCell.setCellStyle(dateCellStyle);

					row.createCell(5).setCellValue(reportPayOutDTO.getGender());
					row.createCell(6).setCellValue(reportPayOutDTO.getJobLocation());
					row.createCell(7).setCellValue(reportPayOutDTO.getDaysInMonth());
					row.createCell(8).setCellValue(reportPayOutDTO.getAbsent());

					// if (reportPayOutDTO.getPayDays() != null)
					row.createCell(9).setCellValue(reportPayOutDTO.getPayDays().doubleValue());

//					row.createCell(10).setCellValue(convertBigdecimalToDouble(reportPayOutDTO.getMonthalyGross()));
					Cell cell10 = row.createCell(10);
					cell10.setCellStyle(addZero);
					cell10.setCellValue( (reportPayOutDTO.getMonthalyGross().doubleValue()));
					Map<String, String> payHeadsMap = reportPayOutDTO.getPayHeadsMap();

					int value = 11;

					for (int i = 0; i < earnngPayHeadsId.length; i++) {
						Cell cell = row.createCell(value);
						cell.setCellStyle(addZero);
						if(payHeadsMap.get((earnngPayHeadsId[i]))!=null)
						cell.setCellValue(new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i]))).doubleValue());
					 
						value++;
						if (payHeadsMap.get((earnngPayHeadsId[i])) != null) {
							BigDecimal bigDecimalCurrency = new BigDecimal(payHeadsMap.get((earnngPayHeadsId[i])));
							if (index == 0) {
								totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency);
							} else {
								BigDecimal currentSum = totalSumMap.get(earnngPayHeadsId[i]);
								if (currentSum == null)
									currentSum = BigDecimal.ZERO;
								totalSumMap.put(earnngPayHeadsId[i], bigDecimalCurrency.add(currentSum));
							}
							sum = sum.add(bigDecimalCurrency);
						}

					}
					index++;

					Cell cells = row.createCell(value);
					cells.setCellValue(sum.doubleValue());
					cells.setCellStyle(addZero);

					value++;
					totalSum = totalSum.add(sum);
					for (int i = 0; i < deductionPayHeadsId.length; i++) {
						Cell cell = row.createCell(value);
						cell.setCellStyle(addZero);
						if(payHeadsMap.get((deductionPayHeadsId[i]))!=null)
						cell.setCellValue(new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i]))).doubleValue());
						
						value++;
						if (payHeadsMap.get((deductionPayHeadsId[i])) != null) {
							BigDecimal currency = new BigDecimal(payHeadsMap.get((deductionPayHeadsId[i])));
							if (deductionIndex == 0) {
								deductionTotalSumMap.put(deductionPayHeadsId[i], currency);
							} else {
								BigDecimal deductionCurrentSum = deductionTotalSumMap.get(deductionPayHeadsId[i]);
								if (deductionCurrentSum == null)
									deductionCurrentSum = BigDecimal.ZERO;
								deductionTotalSumMap.put(deductionPayHeadsId[i], currency.add(deductionCurrentSum));

							}
							deductionSum = deductionSum.add(currency);
						}
					}

					deductionIndex++;

					Cell cell = row.createCell(value);
					cell.setCellValue( deductionSum.doubleValue());
					cell.setCellStyle(addZero);
					value++;
					deductionTotalSum = deductionTotalSum.add(deductionSum);

					totalNetPayable = totalNetPayable.add(reportPayOutDTO.getNetPayableAmount());

					Cell net = row.createCell(value++);
					net.setCellValue(reportPayOutDTO.getNetPayableAmount().doubleValue());
					net.setCellStyle(addZero);

					row.createCell(value++).setCellValue(reportPayOutDTO.getBankName());

					row.createCell(value++).setCellValue(reportPayOutDTO.getAccountNumber());

					Cell cell1=row.createCell(value++);
					cell1.setCellValue(reportPayOutDTO.getIfscCode());

					row.createCell(value++).setCellValue(reportPayOutDTO.getBranchName());

				}

			}

			headerCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			int newValue = 11;
			Row rowNew = sheet.createRow(rowNum);

			for (int i = 0; i < earnngPayHeadsId.length; i++) {
				Cell createdDateCell = rowNew.createCell(newValue);
				createdDateCell.setCellStyle(addZeroBold);
//				if(totalSumMap.get((earnngPayHeadsId[i]))!=null)
//				createdDateCell.setCellValue(totalSumMap.get((earnngPayHeadsId[i])).doubleValue());
				createdDateCell.setCellValue(convertBigdecimalToDouble(totalSumMap.get((earnngPayHeadsId[i]))));
				newValue++;
			}
			Cell t = rowNew.createCell(newValue);
			t.setCellValue(totalSum.doubleValue());
			t.setCellStyle(addZeroBold);
			newValue++;
			for (int i = 0; i < deductionPayHeadsId.length; i++) {
				Cell createdDateCell = rowNew.createCell(newValue);
				createdDateCell.setCellStyle(addZeroBold);
//				if(totalSumMap.get((deductionPayHeadsId[i]))!=null)
//				createdDateCell.setCellValue( deductionTotalSumMap.get((deductionPayHeadsId[i])).doubleValue());
				createdDateCell.setCellValue(convertBigdecimalToDouble(deductionTotalSumMap.get((deductionPayHeadsId[i]))));
				newValue++;
			}

			Cell createdDateCell = rowNew.createCell(newValue);
			createdDateCell.setCellValue( deductionTotalSum.doubleValue());
			createdDateCell.setCellStyle(addZeroBold);
			newValue++;

			Cell netpay = rowNew.createCell(newValue);
			netpay.setCellValue(totalNetPayable.doubleValue());
			netpay.setCellStyle(addZeroBold);
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			return workbook;

		}

	
	
	private static double convertBigdecimalToDouble(BigDecimal bigDecimalValue) {
		double doubleValue;

		if (bigDecimalValue != null)
			doubleValue = bigDecimalValue.doubleValue();
		else
			doubleValue = 0.0;
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


}
