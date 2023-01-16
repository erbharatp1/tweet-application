package com.csipl.hrms.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.enums.StandardDeductionEnum;
import com.csipl.hrms.common.util.GlobalConstantUtils;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.employee.EmployeeLetterDTO;
import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.common.City;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import com.csipl.hrms.model.employee.CompanyPolicy;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeExpenseClaim;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.employee.LetterDaclaration;
import com.csipl.hrms.model.employee.Separation;
import com.csipl.hrms.model.payroll.FinalSettlement;
import com.csipl.hrms.model.payroll.FinalSettlementReport;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.model.recruitment.CandidateLetter;
import com.csipl.hrms.service.employee.AuthorizedSignatoryService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class SalaryPdfReport extends PdfPageEventHelper {
	private static final Logger logger = LoggerFactory.getLogger(SalaryPdfReport.class);

	@Autowired
	AuthorizedSignatoryService authorizedSignatoryService;

	Document document = new Document(PageSize.A4, 36, 36, 54, 36);
	public static final FontFamily fontFamily = Font.FontFamily.HELVETICA;
	public static final FontFamily fontFamilyForHeading = Font.FontFamily.TIMES_ROMAN;
	public static final int fontSize = 9;
	public static final int fontSizeForHeading = 12;
	public static final int normalFontType = Font.NORMAL;
	final BaseColor fontColorBlack = BaseColor.BLACK;
	public static final BaseColor redFontColor = BaseColor.RED;
	final int boldFontType = Font.BOLD;
	final int boldFont = Font.BOLD;
	Font paraFont = new Font(fontFamily, fontSize, normalFontType, fontColorBlack);
	Font headingFont = new Font(fontFamilyForHeading, fontSizeForHeading, boldFont, fontColorBlack);
	Font subHeadingFont = new Font(fontFamilyForHeading, fontSize, boldFontType);
	PdfPCell cell = new PdfPCell();

	PdfPCell cellHeader = new PdfPCell();

	private String companyName = "";
	private String companyAddress = "";
	private String companyWebSite = "";
	String pathNew = "";

	public ByteArrayInputStream salaryPdfReport(ReportPayOut reportPayOut, Company companay, City city,
			Employee employee, List<PayOutDTO> payOutDTOList) throws Exception {
		/*
		 * Code For process Month
		 */
		String USER_PASSWORD = reportPayOut.getEmployeeCode();
		String OWNER_PASSWORD = "csipl";
		String processMonth = null;
		if (reportPayOut != null)
			processMonth = reportPayOut.getId().getProcessMonth();
		logger.info("salaryPdfReport is calling : " + " processMonth  " + processMonth);

		String[] shortMonths = new DateFormatSymbols().getMonths();
		// System.out.println(Integer.valueOf(processMonth) % 100);
		// String monthAcronym = shortMonths[(Integer.valueOf(processMonth) % 100) -
		// 1].toUpperCase();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		Rectangle rectangle = new Rectangle(30, 30, 550, 800);
		rectangle.setBorder(Rectangle.BOX);
		rectangle.setBorderWidth(2);
		pdfWriter.setBoxSize("rectangle", rectangle);
		pdfWriter.setEncryption(USER_PASSWORD.getBytes(), OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,
				PdfWriter.ENCRYPTION_AES_128);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		pdfWriter.setPageEvent(event);
		document.open();
		employeeInfo(reportPayOut, companay, city, processMonth, employee);
		earningsAndDeductionsDetails(reportPayOut, payOutDTOList);
		document.close();

		return new ByteArrayInputStream(out.toByteArray());
	}// salaryPdfReport

	public void employeeInfo(ReportPayOut reportPayOut, Company company, City city, String processMonth,
			Employee employee) throws Exception {

		cell.setBorder(Rectangle.NO_BORDER);
		cellHeader.setBorder(Rectangle.NO_BORDER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cellHeader.setBorder(Rectangle.NO_BORDER);
		cellHeader.setBorder(Rectangle.BOTTOM);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setPadding(1.5F);

		/*
		 * Code By Ravindra Singh Parihar Date:22/02/2018
		 */
		Font compnyNameFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		Font compnyInfoFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		if (company.getCompanyLogoPath() != null && !company.getCompanyLogoPath().equals("")) {
			PdfPTable c_logoTable = new PdfPTable(1);
			c_logoTable.setWidthPercentage(80.0F);
			c_logoTable.setWidths(new float[] { 1.0F });

			// Image Code
			PdfPCell c_logoCell = new PdfPCell();
			c_logoCell.setBorder(Rectangle.NO_BORDER);
			String rootPath = System.getProperty("catalina.home");
			logger.info("salaryPdfReport is calling : " + " CompanyLogoPath  " + company.getCompanyLogoPath());

			String path = company.getCompanyLogoPath() != null ? company.getCompanyLogoPath() : "";
			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;
			logger.info("salaryPdfReport is calling : " + " rootPath  " + rootPath);

			try {
				Image image = Image.getInstance(rootPath);
				if (image != null) {

					System.out.println("image" + image);
					float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
					image.setAlignment(Image.ORIGINAL_WMF);
					image.setAlignment(Image.ALIGN_CENTER);
					image.scalePercent(scaler);
					c_logoCell.addElement(image);
					c_logoTable.addCell(c_logoCell);
					document.add(c_logoTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			PdfPTable c_nameTable = new PdfPTable(1);
			c_nameTable.setWidthPercentage(80.0F);
			c_nameTable.setWidths(new float[] { 1.0F });
			PdfPCell c_nameCell = new PdfPCell();
			PdfPCell c_compnyInfoCell = new PdfPCell();

			c_nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
			c_nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			c_nameCell.setBorder(Rectangle.NO_BORDER);
			c_nameCell.setPhrase(new Phrase(company.getCompanyName(), compnyNameFont));

			c_compnyInfoCell.setVerticalAlignment(Element.ALIGN_CENTER);
			c_compnyInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			c_compnyInfoCell.setBorder(Rectangle.NO_BORDER);

			c_compnyInfoCell.setPhrase(new Phrase(company.getAddress1().getState().getStateName() + " "
					+ company.getAddress1().getCity().getCityName() + " - " + company.getAddress1().getPincode(),
					compnyInfoFont));
			c_nameTable.addCell(c_nameCell);
			c_nameTable.addCell(c_compnyInfoCell);
			document.add(c_nameTable);

		}
		PdfPTable tableHead = new PdfPTable(1);
		tableHead.setSpacingBefore(15.0F);
		tableHead.setSpacingAfter(10.0F);
		tableHead.setWidthPercentage(80.0F);
		tableHead.setWidths(new float[] { 1.0F });
		cellHeader.setPaddingBottom(5);
		cellHeader.setPaddingTop(15);
		cellHeader.setPhrase(new Phrase("PAY SLIP- " + processMonth, subHeadingFont));
		tableHead.addCell(cellHeader);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(80.0F);
		table.setWidths(new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		cell.setPhrase(new Phrase("Employee Name", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(employee.getFirstName() + " " + employee.getLastName(), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PAN ", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getPanno(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Employee Id", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getEmployeeCode(), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("UID", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getAadharNo(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Designation", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(
				employee.getDesignation() != null ? employee.getDesignation().getDesignationName() : "", paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("UAN", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getUanno(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Location", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(city.getCityName(), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PF Number", paraFont));//
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getPFNumber(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Date of Joining", paraFont));
		table.addCell(cell);
		String dOfJo = reportPayOut.getDateOfJoining() != null ? reportPayOut.getDateOfJoining().toString() : "";
		if (!dOfJo.equals("")) {
			String[] dOfJos = dOfJo.split("-");
			if (dOfJos.length > 2)
				dOfJo = dOfJos[2] + "-" + dOfJos[1] + "-" + dOfJos[0];
		}
		cell.setPhrase(new Phrase(dOfJo, paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("ESI Number", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getESICNumber() != null ? reportPayOut.getESICNumber().toString() : "",
				paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Bank Name", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getBankName(), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Account No", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(reportPayOut.getAccountNumber(), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Pay Days", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(convertBigdecimalToStringThrowInt(reportPayOut.getPayableDays()), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Absent days", paraFont));
		table.addCell(cell);
		if (reportPayOut.getAbsense() != null) {
			cell.setPhrase(new Phrase(convertBigdecimalToStringThrowInt(reportPayOut.getAbsense()), paraFont));
			table.addCell(cell);
		} else {
			cell.setPhrase(new Phrase("NA", paraFont));
			table.addCell(cell);
		}

		cell.setPhrase(new Phrase("Standard Basic", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(convertBigdecimalToStringThrowInt(reportPayOut.getBasic()), paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Standard Gross", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(convertBigdecimalToStringThrowInt(reportPayOut.getGrossSalary()), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("", paraFont));
		table.addCell(cell);
		document.add(tableHead);
		document.add(table);
	}// ImployeeInfo

	public void earningsAndDeductionsDetails(ReportPayOut reportPayOut, List<PayOutDTO> payOutDTOList)
			throws Exception {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(4);
		table.setSpacingAfter(10.0F);
		PdfPCell cellED = new PdfPCell();
		cellED.setPaddingBottom(5);
		cellED.setPaddingTop(15);

		cellED.setBorder(Rectangle.NO_BORDER);
		cellED.setBorder(Rectangle.NO_BORDER);
		cellED.setBorder(Rectangle.BOTTOM);
		cellED.setPaddingLeft(2);
		table.setWidthPercentage(80.0F);
		table.setWidths(new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		cellED.setPhrase(new Phrase("Earnings", subHeadingFont));
		table.addCell(cellED);
		cellED.setPhrase(new Phrase("Amount ", subHeadingFont));
		table.addCell(cellED);
		cellED.setPhrase(new Phrase("Deductions :", subHeadingFont));
		table.addCell(cellED);
		cellED.setPhrase(new Phrase("Amount", subHeadingFont));
		table.addCell(cellED);
		document.add(table);

		/*
		 * Earnings and Deductions details validation code... by ravindra singh parihar
		 * date:21/2/2018
		 * 
		 */
		/*
		 * BigDecimal basicEarning = reportPayOut.getBasicEarning(); String
		 * basicEarning1 = basicEarning != null && basicEarning.doubleValue() > 0 ?
		 * basicEarning.toString() : "";
		 * 
		 * BigDecimal hraEarning = reportPayOut.getHRAEarning(); String hraEarning1 =
		 * hraEarning != null && hraEarning.doubleValue() > 0 ? hraEarning.toString() :
		 * "";
		 * 
		 * BigDecimal conveyanceAllowanceEarning =
		 * reportPayOut.getConveyanceAllowanceEarning(); String
		 * conveyanceAllowanceEarning1 = conveyanceAllowanceEarning != null &&
		 * conveyanceAllowanceEarning.doubleValue() > 0 ?
		 * conveyanceAllowanceEarning.toString() : "";
		 * 
		 * BigDecimal advanceBonusEarning = reportPayOut.getAdvanceBonusEarning();
		 * String advanceBonusEarning1 = advanceBonusEarning != null &&
		 * advanceBonusEarning.doubleValue() > 0
		 * 
		 * ? advanceBonusEarning.toString() : "";
		 * 
		 * BigDecimal medicalAllowanceEarning =
		 * reportPayOut.getMedicalAllowanceEarning(); String medicalAllowanceEarning1 =
		 * medicalAllowanceEarning != null && medicalAllowanceEarning.doubleValue() > 0
		 * ? medicalAllowanceEarning.toString() : "";
		 * 
		 * BigDecimal specialAllowanceEarning =
		 * reportPayOut.getSpecialAllowanceEarning(); String specialAllowanceEarning1 =
		 * specialAllowanceEarning != null && specialAllowanceEarning.doubleValue() > 0
		 * ? specialAllowanceEarning.toString() : "";
		 * 
		 * BigDecimal uniformAllowanceEarning =
		 * reportPayOut.getUniformAllowanceEarning(); String uniformAllowanceEarning1 =
		 * uniformAllowanceEarning != null && uniformAllowanceEarning.doubleValue() > 0
		 * ? uniformAllowanceEarning.toString() : ""; BigDecimal
		 * leaveTravelAllowanceEarning = reportPayOut.getLeaveTravelAllowanceEarning();
		 * String leaveTravelAllowanceEarning1 = leaveTravelAllowanceEarning != null &&
		 * leaveTravelAllowanceEarning.doubleValue() > 0 ?
		 * leaveTravelAllowanceEarning.toString() : ""; BigDecimal otherAllowanceEarning
		 * = reportPayOut.getOtherAllowanceEarning(); String otherAllowanceEarning1 =
		 * otherAllowanceEarning != null && otherAllowanceEarning.doubleValue() > 0 ?
		 * otherAllowanceEarning.toString() : ""; BigDecimal companyBenefitsEarning =
		 * reportPayOut.getCompanyBenefitsEarning(); String companyBenefitsEarning1 =
		 * companyBenefitsEarning != null && companyBenefitsEarning.doubleValue() > 0 ?
		 * companyBenefitsEarning.toString() : ""; BigDecimal dearnessAllowanceEarning =
		 * reportPayOut.getDearnessAllowanceEarning(); String dearnessAllowanceEarning1
		 * = dearnessAllowanceEarning != null && dearnessAllowanceEarning.doubleValue()
		 * > 0 ? dearnessAllowanceEarning.toString() : "";
		 */
		/*
		 * code by ravindra singh parihar date:21/2/2018
		 * 
		 */
		Paragraph paragraph = new Paragraph();
		PdfPCell cell123 = new PdfPCell();
		cell123.setVerticalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell124 = new PdfPCell();
		cell124.setBorder(Rectangle.NO_BORDER);
		PdfPCell cell124A = new PdfPCell();
		cell124A.setVerticalAlignment(Element.ALIGN_RIGHT);
		cell124A.setBorder(Rectangle.NO_BORDER);
		cell124A.setPaddingLeft(Element.ALIGN_RIGHT);
		cell124A.setPaddingLeft(2);
		PdfPCell cell125 = new PdfPCell();
		cell125.setBorder(Rectangle.NO_BORDER);
		PdfPCell cell125A = new PdfPCell();
		cell125A.setVerticalAlignment(Element.ALIGN_RIGHT);
		cell125A.setPaddingLeft(Element.ALIGN_RIGHT);
		cell125A.setBorder(Rectangle.NO_BORDER);
		cell125A.setPaddingLeft(2);

		// Main table

		PdfPTable mainTable = new PdfPTable(2);

		mainTable.setWidthPercentage(100.0F);
		mainTable.setWidths(new float[] { 1.0F, 1.0F });
		// First table
		PdfPCell firstTableCell = new PdfPCell();
		firstTableCell.setBorder(PdfPCell.NO_BORDER);
		PdfPTable firstTable = new PdfPTable(2);
		firstTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		firstTable.setWidthPercentage(80.0F);
		BigDecimal totalEarning = new BigDecimal(0);

		if (payOutDTOList != null && payOutDTOList.size() > 0) {
			for (PayOutDTO payOutDto : payOutDTOList) {
				if (payOutDto.getEarningDeduction().equals("EA")) {

					cell124.setPhrase(new Phrase(payOutDto.getPayHeadName(), paraFont));
					firstTable.addCell(cell124);
					cell124A.setPhrase(new Phrase(payOutDto.getAmount().toString(), paraFont));
					firstTable.addCell(cell124A);
					totalEarning = totalEarning.add(payOutDto.getAmount());
				}
			}
		}

		/*
		 * 
		 * // append code if (!basicEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Basic Salary", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase(new Phrase(basicEarning1, paraFont));
		 * firstTable.addCell(cell124A); }
		 * 
		 * if (!hraEarning1.equals("")) { cell124.setPhrase(new Phrase("HRA",
		 * paraFont)); firstTable.addCell(cell124); cell124A.setPhrase(new
		 * Phrase(hraEarning1, paraFont)); firstTable.addCell(cell124A); }
		 * 
		 * if (!conveyanceAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Conveyance Allowance", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase(new Phrase(conveyanceAllowanceEarning1, paraFont));
		 * firstTable.addCell(cell124A); }
		 * 
		 * if (!advanceBonusEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Advance Bonus", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase(new Phrase(advanceBonusEarning1, paraFont));
		 * firstTable.addCell(cell124A); }
		 * 
		 * if (!uniformAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Uniform Allowance", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase( new
		 * Phrase(convertBigdecimalToString(reportPayOut.getUniformAllowanceEarning()),
		 * paraFont)); firstTable.addCell(cell124A); }
		 * 
		 * if (!leaveTravelAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("LTA", paraFont)); firstTable.addCell(cell124); cell124A.setPhrase(
		 * new
		 * Phrase(convertBigdecimalToString(reportPayOut.getLeaveTravelAllowanceEarning(
		 * )), paraFont)); firstTable.addCell(cell124A); } if
		 * (!otherAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Other Allowance", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase( new
		 * Phrase(convertBigdecimalToString(reportPayOut.getOtherAllowanceEarning()),
		 * paraFont)); firstTable.addCell(cell124A); } if
		 * (!companyBenefitsEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Company Benefits", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase( new
		 * Phrase(convertBigdecimalToString(reportPayOut.getCompanyBenefitsEarning()),
		 * paraFont)); firstTable.addCell(cell124A); }
		 * 
		 * if (!dearnessAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("DA Allowance", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase(new Phrase(dearnessAllowanceEarning1, paraFont));
		 * firstTable.addCell(cell124A); } if (!medicalAllowanceEarning1.equals("")) {
		 * cell124.setPhrase(new Phrase("Medical Allowance", paraFont));
		 * firstTable.addCell(cell124); cell124A.setPhrase(new
		 * Phrase(medicalAllowanceEarning1, paraFont)); firstTable.addCell(cell124A); }
		 * 
		 * if (!specialAllowanceEarning1.equals("")) { cell124.setPhrase(new
		 * Phrase("Special Allowance", paraFont)); firstTable.addCell(cell124);
		 * cell124A.setPhrase(new Phrase(specialAllowanceEarning1, paraFont));
		 * firstTable.addCell(cell124A); }
		 */

		firstTableCell.addElement(firstTable);
		mainTable.addCell(firstTableCell);

		/*
		 * code By RAvindra singh Date:22/02/2018
		 */
		/*
		 * BigDecimal providentFundEmployee = reportPayOut.getProvidentFundEmployee();
		 * String providentFundEmployee1 = providentFundEmployee != null &&
		 * providentFundEmployee.doubleValue() > 0 ? providentFundEmployee.toString() :
		 * "";
		 * 
		 * BigDecimal eSI_Employee = reportPayOut.getESI_Employee(); String
		 * eSI_Employee1 = eSI_Employee != null && eSI_Employee.doubleValue() > 0 ?
		 * eSI_Employee.toString() : "";
		 * 
		 * BigDecimal tds = reportPayOut.getTds(); String tds1 = tds != null &&
		 * tds.doubleValue() > 0 ? tds.toString() : "";
		 * 
		 * BigDecimal pt = reportPayOut.getPt(); String pt1 = pt != null &&
		 * pt.doubleValue() > 0 ? pt.toString() : "";
		 * 
		 * BigDecimal employeeLoan = reportPayOut.getLoan(); String employeeLoan1 =
		 * employeeLoan != null && employeeLoan.doubleValue() > 0 ?
		 * employeeLoan.toString() : "";
		 * 
		 * BigDecimal totalDeduction = reportPayOut.getTotalDeduction(); String
		 * totalDeduction1 = totalDeduction != null && totalDeduction.doubleValue() > 0
		 * ? totalDeduction.toString() : "";
		 * 
		 * BigDecimal otherDeduction = reportPayOut.getOtherDeduction(); String
		 * otherDeduction1 = otherDeduction != null && otherDeduction.doubleValue() > 0
		 * ? otherDeduction.toString() : "";
		 */

		PdfPCell secondTableCell = new PdfPCell();
		secondTableCell.setBorder(PdfPCell.NO_BORDER);
		PdfPTable secondTable = new PdfPTable(2);
		secondTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		secondTable.setWidthPercentage(80.0F);

		BigDecimal totalCalculatedDeduction = new BigDecimal(0);

		if (payOutDTOList != null && payOutDTOList.size() > 0) {
			for (PayOutDTO payOutDto : payOutDTOList) {
				if (payOutDto.getEarningDeduction().equals("DE")
						&& payOutDto.getAmount().compareTo(new BigDecimal(0.00)) > 0
						&& StandardDeductionEnum.PF_Employer_Contribution.getStandardDeduction() != payOutDto
								.getPayHeadId()
						&& StandardDeductionEnum.Pension_Employer_Contribution.getStandardDeduction() != payOutDto
								.getPayHeadId()
						&& StandardDeductionEnum.ESI_Employer_Contribution.getStandardDeduction() != payOutDto
								.getPayHeadId()
						&& StandardDeductionEnum.LWF_Employer.getStandardDeduction() != payOutDto.getPayHeadId()) {

					if (StandardDeductionEnum.PF_Employee_Contribution.getStandardDeduction() == payOutDto
							.getPayHeadId()) {
						cell125.setPhrase(new Phrase("EPF", paraFont));
						secondTable.addCell(cell125);
						cell125A.setPhrase(new Phrase(payOutDto.getAmount().toString(), paraFont));
						secondTable.addCell(cell125A);
						totalCalculatedDeduction = totalCalculatedDeduction.add(payOutDto.getAmount());
					} else if (StandardDeductionEnum.ESI_Employee_Contribution.getStandardDeduction() == payOutDto
							.getPayHeadId()) {
						cell125.setPhrase(new Phrase("ESI", paraFont));
						secondTable.addCell(cell125);
						cell125A.setPhrase(new Phrase(payOutDto.getAmount().toString(), paraFont));
						secondTable.addCell(cell125A);
						totalCalculatedDeduction = totalCalculatedDeduction.add(payOutDto.getAmount());
					} else {
						cell125.setPhrase(new Phrase(payOutDto.getPayHeadName(), paraFont));
						secondTable.addCell(cell125);
						cell125A.setPhrase(new Phrase(payOutDto.getAmount().toString(), paraFont));
						secondTable.addCell(cell125A);
						totalCalculatedDeduction = totalCalculatedDeduction.add(payOutDto.getAmount());
					}
				}

			}
		}

		/*
		 * 
		 * if (!providentFundEmployee1.equals("")) { cell125.setPhrase(new Phrase("EPF",
		 * paraFont)); secondTable.addCell(cell125); cell125A.setPhrase(new
		 * Phrase(providentFundEmployee1, paraFont)); secondTable.addCell(cell125A); }
		 * if (!eSI_Employee1.equals("")) { cell125.setPhrase(new Phrase("ESI",
		 * paraFont)); secondTable.addCell(cell125); cell125A.setPhrase(new
		 * Phrase(eSI_Employee1, paraFont)); secondTable.addCell(cell125A); } if
		 * (!tds1.equals("")) { cell125.setPhrase(new Phrase("TDS:", paraFont));
		 * secondTable.addCell(cell125); cell125A.setPhrase(new Phrase(tds1, paraFont));
		 * secondTable.addCell(cell125A); } if (!pt1.equals("")) { cell125.setPhrase(new
		 * Phrase("Professional Tax", paraFont)); secondTable.addCell(cell125);
		 * cell125A.setPhrase(new Phrase(pt1, paraFont)); secondTable.addCell(cell125A);
		 * } if (!employeeLoan1.equals("")) { cell125.setPhrase(new
		 * Phrase("Loan & Advance", paraFont)); secondTable.addCell(cell125);
		 * cell125A.setPhrase(new Phrase(employeeLoan1, paraFont));
		 * secondTable.addCell(cell125A); }
		 * 
		 * if (!otherDeduction1.equals("")) { cell125.setPhrase(new
		 * Phrase("Other Deduction", paraFont)); secondTable.addCell(cell125);
		 * cell125A.setPhrase(new Phrase(otherDeduction1, paraFont));
		 * secondTable.addCell(cell125A); }
		 */

		secondTableCell.addElement(secondTable);
		mainTable.addCell(secondTableCell);
		paragraph.add(mainTable);

		PdfPCell cellE = new PdfPCell();
		cellE.setBorder(Rectangle.NO_BORDER);
		cellE.setBorder(Rectangle.BOTTOM);
		cellE.setPaddingBottom(5);

		cellE.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell cellD = new PdfPCell();
		cellD.setBorder(Rectangle.NO_BORDER);
		cellD.setBorder(Rectangle.BOTTOM);
		cellD.setPaddingBottom(5);
		cellD.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPCell cellfEA = new PdfPCell();
		cellfEA.setBorder(Rectangle.NO_BORDER);
		cellfEA.setBorder(Rectangle.BOTTOM);
		cellfEA.setPaddingBottom(5);

		cellfEA.setVerticalAlignment(Element.ALIGN_CENTER);
		cellfEA.setPaddingLeft(2);
		PdfPCell cellfDA = new PdfPCell();
		cellfDA.setBorder(Rectangle.NO_BORDER);
		cellfDA.setBorder(Rectangle.BOTTOM);
		cellfDA.setPaddingBottom(5);

		cellfDA.setVerticalAlignment(Element.ALIGN_CENTER);

		cellfDA.setPaddingLeft(2);
		PdfPTable tablefooter = new PdfPTable(4);
		tablefooter.setWidthPercentage(80.0F);
		tablefooter.setWidths(new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		cellE.setPhrase(new Phrase("Total Earnings", subHeadingFont));
		tablefooter.addCell(cellE);
		cellfEA.setPhrase(new Phrase(convertBigdecimalToString(totalEarning), subHeadingFont));
		tablefooter.addCell(cellfEA);
		cellD.setPhrase(new Phrase("Total Deductions", subHeadingFont));
		tablefooter.addCell(cellD);
		cellfDA.setPhrase(new Phrase(convertBigdecimalToString(totalCalculatedDeduction), subHeadingFont));
		tablefooter.addCell(cellfDA);
		paragraph.add(tablefooter);
		document.add(paragraph);
		PdfPTable tblNetAmountInWord = new PdfPTable(2);
		tblNetAmountInWord.setWidthPercentage(80.0F);
		tblNetAmountInWord.setWidths(new float[] { 0.5F, 1.5F });
		PdfPCell cellNetAmountInWord = new PdfPCell();

		cellNetAmountInWord.setBorder(Rectangle.NO_BORDER);
		cellNetAmountInWord.setBorder(Rectangle.BOTTOM);
		cellNetAmountInWord.setPaddingBottom(5);

		cellNetAmountInWord.setVerticalAlignment(Element.ALIGN_CENTER);
		cellNetAmountInWord.setHorizontalAlignment(Element.ALIGN_LEFT);

		cellNetAmountInWord.setPhrase(new Phrase("Amount in words(Rs.)", subHeadingFont));
		tblNetAmountInWord.addCell(cellNetAmountInWord);
		cellNetAmountInWord.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellNetAmountInWord.setPhrase(
				new Phrase(convertToIndianCurrency(convertBigdecimalToString(reportPayOut.getNetPayableAmount())),
						subHeadingFont));
		tblNetAmountInWord.addCell(cellNetAmountInWord);
		PdfPTable tblNetAmount = new PdfPTable(4);
		tblNetAmount.setWidthPercentage(80.0F);
		tblNetAmount.setWidths(new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		PdfPCell cellNetAmount = new PdfPCell();
		cellNetAmount.setBorder(Rectangle.NO_BORDER);
		cellNetAmount.setBorder(Rectangle.BOTTOM);
		cellNetAmount.setPaddingBottom(5);

		cellNetAmount.setVerticalAlignment(Element.ALIGN_CENTER);

		cellNetAmount.setPhrase(new Phrase("Net Amount", subHeadingFont));
		tblNetAmount.addCell(cellNetAmount);
		cellNetAmount.setPhrase(new Phrase("", subHeadingFont));
		tblNetAmount.addCell(cellNetAmount);
		cellNetAmount.setPhrase(new Phrase("", subHeadingFont));
		tblNetAmount.addCell(cellNetAmount);
		cellNetAmount
				.setPhrase(new Phrase(convertBigdecimalToString(reportPayOut.getNetPayableAmount()), subHeadingFont));
		cellNetAmount.setVerticalAlignment(Element.ALIGN_RIGHT);
		tblNetAmount.addCell(cellNetAmount);
		PdfPTable msgTbl = new PdfPTable(1);
		msgTbl.setWidthPercentage(80.0F);
		msgTbl.setWidths(new float[] { 1.0F });
		PdfPCell cellMsg = new PdfPCell();
		cellMsg.setBorder(Rectangle.NO_BORDER);
		cellMsg.setPaddingLeft(2);
		cellMsg.setPaddingTop(37);
		cellMsg.setPhrase(
				new Phrase("This is a computer generated salary slip, does not require seal and signature.", paraFont));
		msgTbl.addCell(cellMsg);
		document.add(tblNetAmount);
		document.add(tblNetAmountInWord);
		document.add(msgTbl);

	}// earningsAndDeductionsDetails

	private static String convertBigdecimalToString(BigDecimal value) {
		String stringValue = "";
		if (value != null && !value.equals("")) {
			stringValue = value.toString();
		}
		return stringValue;
	}// convertBigdecimalToString

	private static String convertBigdecimalToStringThrowInt(BigDecimal value) {
		String stringValue = "";
		if (value != null && !value.equals("")) {
			Integer intValue = value.intValue();
			stringValue = intValue.toString();
		}
		return stringValue;
	}// convertBigdecimalToStringThrowInt

	public static String convertToIndianCurrency(String num) {
		BigDecimal bd = new BigDecimal(num);
		long number = bd.longValue();
		long no = bd.longValue();
		int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
		int digits_length = String.valueOf(no).length();
		int i = 0;
		ArrayList<String> str = new ArrayList<>();
		HashMap<Integer, String> words = new HashMap<>();
		words.put(0, "");
		words.put(1, "One");
		words.put(2, "Two");
		words.put(3, "Three");
		words.put(4, "Four");
		words.put(5, "Five");
		words.put(6, "Six");
		words.put(7, "Seven");
		words.put(8, "Eight");
		words.put(9, "Nine");
		words.put(10, "Ten");
		words.put(11, "Eleven");
		words.put(12, "Twelve");
		words.put(13, "Thirteen");
		words.put(14, "Fourteen");
		words.put(15, "Fifteen");
		words.put(16, "Sixteen");
		words.put(17, "Seventeen");
		words.put(18, "Eighteen");
		words.put(19, "Nineteen");
		words.put(20, "Twenty");
		words.put(30, "Thirty");
		words.put(40, "Forty");
		words.put(50, "Fifty");
		words.put(60, "Sixty");
		words.put(70, "Seventy");
		words.put(80, "Eighty");
		words.put(90, "Ninety");
		String digits[] = { "", "Hundred", "Thousand", "Lakh", "Crore" };
		while (i < digits_length) {
			int divider = (i == 2) ? 10 : 100;
			number = no % divider;
			no = no / divider;
			i += divider == 10 ? 1 : 2;
			if (number > 0) {
				int counter = str.size();
				String plural = (counter > 0 && number > 9) ? "s" : "";
				String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural
						: words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " "
								+ words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;
				str.add(tmp);
			} else {
				str.add("");
			}
		}

		Collections.reverse(str);
		String rupees = String.join(" ", str).trim();

		String paise = (decimal) > 0
				? " And  " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " "
						+ words.get(Integer.valueOf((int) (decimal % 10)))
				: "And Zero ";
		return rupees + paise + " Paise";
	}// convertToIndianCurrency

	public ByteArrayInputStream finalSettlementStatement(Company company, Employee employee, City city,
			FinalSettlement finalSettlement, Separation separation,
			List<FinalSettlementReport> finalSettlementReportList, ReportPayOutDTO lastPaidValue) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		Rectangle rectangle = new Rectangle(30, 30, 550, 800);
		pdfWriter.setBoxSize("rectangle", rectangle);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		pdfWriter.setPageEvent(event);
		document.open();
		finalSettlement(company, employee, city, finalSettlement, separation, finalSettlementReportList, lastPaidValue);
		// employeeInfo(reportPayOut, companay, city, processMonth, employee);
		// earningsAndDeductionsDetails(reportPayOut,payOutDTOList);
		document.close();

		return new ByteArrayInputStream(out.toByteArray());
	}

	public void finalSettlement(Company company, Employee employee, City city, FinalSettlement finalSettlement,
			Separation separation, List<FinalSettlementReport> finalSettlementReportList, ReportPayOutDTO lastPaidValue)
			throws Exception {

		BigDecimal lastPaid = BigDecimal.ZERO;
		String lastPaidMonth = null;
		for (FinalSettlementReport finalSettle : finalSettlementReportList) {
			lastPaid = finalSettle.getLastPaidSalary();
			lastPaidMonth = finalSettle.getLastPaidMonth();
		}

//		employee.setNoticeDate(separation.getDateCreated().toString());
//		employee.setEndDate(separation.getEndDate());
		long difference = separation.getEndDate().getTime() - separation.getExitDate().getTime();
		long shortFallDays = (difference / (1000 * 60 * 60 * 24));
		System.out.println("shortFall Days ---->" + shortFallDays);

		long daysServed;

		if (employee.getNoticePeriodDays() != null) {
			daysServed = employee.getNoticePeriodDays() - shortFallDays;
		} else {
			daysServed = shortFallDays;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateCreated = formatter.format(separation.getDateCreated());
		System.out.println("getDateCreated ---->" + dateCreated);

		cell.setBorder(Rectangle.NO_BORDER);
		cellHeader.setBorder(Rectangle.NO_BORDER);
		cell.setVerticalAlignment(Element.ALIGN_LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellHeader.setBorder(Rectangle.NO_BORDER);
		cellHeader.setBorder(Rectangle.BOTTOM);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setPadding(1.5F);

		/*
		 * Code By Ravindra Singh Parihar Date:22/02/2018
		 */
		Font compnyNameFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		Font compnyInfoFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		PdfPTable c_logoTable = new PdfPTable(1);
		c_logoTable.setWidthPercentage(80.0F);
		c_logoTable.setWidths(new float[] { 1.0F });

		// Image Code
		PdfPCell c_logoCell = new PdfPCell();
		c_logoCell.setBorder(Rectangle.NO_BORDER);
		String rootPath = System.getProperty("catalina.home");

		String path = company.getCompanyLogoPath() != null ? company.getCompanyLogoPath() : "";
		rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;
		try {
			Image image = Image.getInstance(rootPath);
			if (image != null) {

				System.out.println("image" + image);
				float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
				image.setAlignment(Image.ORIGINAL_WMF);
				image.setAlignment(Image.ALIGN_CENTER);
				image.scalePercent(scaler);
				c_logoCell.addElement(image);
				c_logoTable.addCell(c_logoCell);
				document.add(c_logoTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		PdfPTable c_nameTable = new PdfPTable(1);
		c_nameTable.setWidthPercentage(80.0F);
		c_nameTable.setWidths(new float[] { 1.0F });
		PdfPCell c_nameCell = new PdfPCell();
		PdfPCell c_compnyInfoCell = new PdfPCell();
		PdfPCell c_statement = new PdfPCell();
		// PdfPCell c_blank = new PdfPCell();

//		c_blank.setVerticalAlignment(Element.ALIGN_CENTER);
//		c_blank.setHorizontalAlignment(Element.ALIGN_CENTER);
//		c_blank.setBorder(Rectangle.NO_BORDER);

		c_statement.setVerticalAlignment(Element.ALIGN_CENTER);
		c_statement.setHorizontalAlignment(Element.ALIGN_CENTER);
		c_statement.setBorder(Rectangle.NO_BORDER);
		c_statement.setPhrase(new Phrase("Statement of Final Settlement", headingFont));

		c_nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
		c_nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		c_nameCell.setBorder(Rectangle.NO_BORDER);
		c_nameCell.setPhrase(new Phrase(company.getCompanyName(), compnyNameFont));

		c_compnyInfoCell.setVerticalAlignment(Element.ALIGN_CENTER);
		c_compnyInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		c_compnyInfoCell.setBorder(Rectangle.NO_BORDER);

		c_compnyInfoCell.setPhrase(new Phrase(company.getAddress1().getState().getStateName() + " "
				+ company.getAddress1().getCity().getCityName() + " - " + company.getAddress1().getPincode(),
				compnyInfoFont));
		c_nameTable.addCell(c_nameCell);
		c_nameTable.addCell(c_compnyInfoCell);
		// c_blank.setPhrase(new Phrase(" "));
		// c_nameTable.addCell(c_blank);
		c_nameTable.addCell(c_statement);
		document.add(c_nameTable);

		PdfPTable tableHead = new PdfPTable(1);
		tableHead.setSpacingBefore(15.0F);
		tableHead.setSpacingAfter(10.0F);
		tableHead.setWidthPercentage(80.0F);
		tableHead.setWidths(new float[] { 1.0F });
		cellHeader.setPaddingBottom(5);
		cellHeader.setPaddingTop(15);
		// cellHeader.setPhrase(
		// new Phrase("PAY SLIP- " +processMonth, subHeadingFont));
		tableHead.addCell(cellHeader);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(80);
		// table.setWidths(new float[] { 1.0F, 1.0F });
		// table.setVerticalAlignment(Element.ALIGN_CENTER);
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setPhrase(new Phrase("Employee Name", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(employee.getFirstName() + " " + employee.getLastName(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("", paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Employee Code ", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(employee.getEmployeeCode(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Designation", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(
				employee.getDesignation() != null ? employee.getDesignation().getDesignationName() : "", paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Depertment", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : "",
				paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Job Location ", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(company.getAddress1().getCity().getCityName(), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Date of Joining", paraFont));
		table.addCell(cell);
		String dOfJo = employee.getDateOfJoining() != null ? employee.getDateOfJoining().toString() : "";
		if (!dOfJo.equals("")) {
			String[] dOfJos = dOfJo.split("-");
			if (dOfJos.length > 2)
				dOfJo = dOfJos[2] + "-" + dOfJos[1] + "-" + dOfJos[0];
		}
		cell.setPhrase(new Phrase(dOfJo, paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resigned on ", paraFont));
		table.addCell(cell);
		String create = dateCreated != null ? dateCreated : "";
		if (!create.equals("")) {
			String[] creates = create.split("-");
			if (creates.length > 2)
				create = creates[2] + "-" + creates[1] + "-" + creates[0];
		}
		cell.setPhrase(new Phrase(create, paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Notice days served", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(daysServed), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Date of Exit", paraFont));
		table.addCell(cell);
		String endD = separation.getExitDate() != null ? separation.getExitDate().toString() : "";
		if (!endD.equals("")) {
			String[] endDs = endD.split("-");
			if (endDs.length > 2)
				endD = endDs[2] + "-" + endDs[1] + "-" + endDs[0];
		}
		cell.setPhrase(new Phrase(endD, paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Standard Notice in days", paraFont));
		table.addCell(cell);

		String noticePeriod = employee.getNoticePeriodDays() != null ? employee.getNoticePeriodDays().toString() : "";
		cell.setPhrase(new Phrase(noticePeriod, paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Notice days shortfall ", paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase(String.valueOf(shortFallDays), paraFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Last Paid ", paraFont));
		table.addCell(cell);
		if (lastPaid != null) {
			cell.setPhrase(new Phrase(lastPaid.toString(), paraFont));
			table.addCell(cell);
		} else {
			cell.setPhrase(new Phrase("NA", paraFont));
			table.addCell(cell);
		}

		cell.setPhrase(new Phrase("Last Paid of the month", paraFont));
		table.addCell(cell);
		if (lastPaidMonth != null) {
			cell.setPhrase(new Phrase(lastPaidMonth.toString(), paraFont));
			table.addCell(cell);
		} else {
			cell.setPhrase(new Phrase("NA", paraFont));
			table.addCell(cell);
		}

		cell.setPhrase(new Phrase(" ", paraFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase(" ", paraFont));
		table.addCell(cell);

		PdfPTable table1 = new PdfPTable(4); // 4 columns.

		// table1.setWidthPercentage(75.0F);
		float[] columnWidths = new float[] { 20f, 10f, 10f, 10f };
		table1.setWidths(columnWidths);

		PdfPCell cell1 = new PdfPCell(new Phrase("Earning(E)", subHeadingFont));

		// cell1.setBackgroundColor(redFontColor);
		table1.addCell(cell1);

		PdfPCell cell2 = new PdfPCell(new Phrase("Amount", subHeadingFont));

		table1.addCell(cell2);
		PdfPCell cell3 = new PdfPCell(new Phrase("Deduction(D)", subHeadingFont));
		table1.addCell(cell3);
		PdfPCell cell4 = new PdfPCell(new Phrase("Amount", subHeadingFont));
		table1.addCell(cell4);

		cell1.setBorderColor(BaseColor.GRAY);
		cell2.setBorderColor(BaseColor.GRAY);
		cell3.setBorderColor(BaseColor.GRAY);
		cell4.setBorderColor(BaseColor.GRAY);

		int count = 0;
		BigDecimal totalAmoumt = BigDecimal.ZERO;
		if (finalSettlementReportList != null && finalSettlementReportList.size() > 0) {
			for (FinalSettlementReport finalSettle : finalSettlementReportList) {
				cell1.setPhrase(new Phrase("Salary Payable - " + finalSettle.getSalaryPayableMonth(), paraFont));
				table1.addCell(cell1);
				cell2.setPhrase(new Phrase(finalSettle.getSalaryPayable().toString(), paraFont));
				table1.addCell(cell2);
				if (count == 0) {
					cell3.setPhrase(new Phrase("Loan", paraFont));
					table1.addCell(cell3);
					cell4.setPhrase(new Phrase(finalSettlement.getLoan().toString(), paraFont));
					table1.addCell(cell4);
					count++;
				} else {
					cell3.setPhrase(new Phrase(" ", paraFont));
					table1.addCell(cell3);
					cell4.setPhrase(new Phrase(" ", paraFont));
					table1.addCell(cell4);
				}
				totalAmoumt = totalAmoumt.add(finalSettle.getSalaryPayable());
			}
		}

		if (finalSettlement.getLeaveEncashment() != null) {
			cell1.setPhrase(new Phrase("Leave Encashment", paraFont));
			table1.addCell(cell1);
			cell2.setPhrase(new Phrase(finalSettlement.getLeaveEncashment().toString(), paraFont));
			table1.addCell(cell2);
			cell3.setPhrase(new Phrase(" ", paraFont));
			table1.addCell(cell3);
			cell4.setPhrase(new Phrase(" ", paraFont));
			table1.addCell(cell4);
			totalAmoumt = totalAmoumt.add(finalSettlement.getLeaveEncashment());
		}

		if (finalSettlement.getGratuity() != null) {
			cell1.setPhrase(new Phrase("Gratuity", paraFont));
			table1.addCell(cell1);
			cell2.setPhrase(new Phrase(finalSettlement.getGratuity().toString(), paraFont));
			table1.addCell(cell2);
			cell3.setPhrase(new Phrase(" ", paraFont));
			table1.addCell(cell3);
			cell4.setPhrase(new Phrase(" ", paraFont));
			table1.addCell(cell4);
			totalAmoumt = totalAmoumt.add(finalSettlement.getGratuity());
		}
		cell1.setPhrase(new Phrase("Total", subHeadingFont));
		table1.addCell(cell1);
		cell2.setPhrase(new Phrase(String.valueOf(totalAmoumt), subHeadingFont));
		table1.addCell(cell2);
		cell3.setPhrase(new Phrase("Total", subHeadingFont));
		table1.addCell(cell3);
		cell4.setPhrase(new Phrase(finalSettlement.getLoan().toString(), subHeadingFont));
		table1.addCell(cell4);

		BigDecimal netPayable = BigDecimal.ZERO;
		BigDecimal loan = BigDecimal.ZERO;
		loan = finalSettlement.getLoan();
		netPayable = totalAmoumt.subtract(loan);

		PdfPTable tablefoot = new PdfPTable(1);
		tablefoot.setSpacingBefore(15.0F);
		tablefoot.setSpacingAfter(10.0F);
		tablefoot.setWidthPercentage(80.0F);
		tablefoot.setWidths(new float[] { 1.0F });
		PdfPCell c_netpayable = new PdfPCell();
		PdfPCell c_netpayinrs = new PdfPCell();

		c_netpayinrs.setVerticalAlignment(Element.ALIGN_LEFT);
		c_netpayinrs.setHorizontalAlignment(Element.ALIGN_LEFT);
		c_netpayinrs.setBorder(Rectangle.NO_BORDER);

		c_netpayinrs
				.setPhrase(new Phrase(convertToIndianCurrency(convertBigdecimalToString(netPayable)), subHeadingFont));

		c_netpayable.setVerticalAlignment(Element.ALIGN_LEFT);
		c_netpayable.setHorizontalAlignment(Element.ALIGN_LEFT);
		c_netpayable.setBorder(Rectangle.NO_BORDER);
		c_netpayable.setPhrase(new Phrase("Net Payable (E-D)     " + "₹" + netPayable, paraFont));
		tablefoot.addCell(c_netpayable);
		tablefoot.addCell(c_netpayinrs);

		// Font grayFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL,
		// new CMYKColor(0, 0, 0, 93));
		PdfPTable msgTbl = new PdfPTable(1);
		msgTbl.setWidthPercentage(80.0F);
		msgTbl.setWidths(new float[] { 1.0F });
		PdfPCell cellMsg = new PdfPCell();
		cellMsg.setBorder(Rectangle.NO_BORDER);

		cellMsg.setPaddingLeft(2);
		cellMsg.setPaddingTop(37);
		cellMsg.setPhrase(
				new Phrase("This is a computer generated statement, does not require seal and signature.", paraFont));

		msgTbl.addCell(cellMsg);

		document.add(tableHead);
		document.add(table);
		document.add(table1);
		document.add(tablefoot);
		document.add(msgTbl);
	}

//	class MyFooter extends PdfPageEventHelper {
//		
//		public void onStartPage(PdfWriter writer, Document document) {
// 			try {
//				PdfPTable c_logoTable = new PdfPTable(1);
//				PdfPCell c_logoCell = new PdfPCell();
//				c_logoCell.setBorder(Rectangle.NO_BORDER);
//				 
//				String rootPath = System.getProperty("catalina.home");
//
//				rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator
//						+ pathNew;
//
//				Image image = Image.getInstance(rootPath);
//
//				if (image != null) {
//
//					float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
//					image.setAbsolutePosition(200, 200);
//					image.setAlignment(Image.TOP);
//					image.setAlignment(Image.ORIGINAL_WMF);
//					image.setAlignment(Image.ALIGN_CENTER);
//					image.scalePercent(scaler);
//					c_logoCell.addElement(image);
//					c_logoTable.addCell(c_logoCell);
//					document.add(c_logoTable);
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//		public void onEndPage(PdfWriter writer, Document document) {
//			PdfContentByte cb = writer.getDirectContent();
//			BaseColor colorBlue = BaseColor.BLUE;
// 
//			Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//			Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
//			Font font3 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
//			font3.setColor(colorBlue);
//
// 
//			Phrase phrase = new Phrase(companyName, font);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase, 30, 30, 0);
//			Phrase phrase2 = new Phrase(companyAddress, font2);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase2, 30, 15, 0);
//			Phrase phraseWebSite = new Phrase(companyWebSite, font3);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phraseWebSite, 30, 3, 0);
//		}
//
// 
//	}

	@SuppressWarnings("deprecation")
	public ByteArrayInputStream masterTemplatePdf(Company company, EmployeeLetter letterList,

			EmployeeLetterDTO letterDTO, Letter ltrList, AuthorizedSignatory authorizedSignatory,
			LetterDaclaration letterDaclaration) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Document document = new Document(PageSize.A4, 50, 45, 50, 40);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
			// document.setMargins(30, 30, 30, 30);
			document.setMarginMirroring(false);
			Paragraph para = new Paragraph();
			para.setFont(paraFont);

			document.open();

			Rectangle rectangle = new Rectangle(30, 30, 550, 800);
			rectangle.setBorder(Rectangle.BOX);
			rectangle.setBorderWidth(2);
			pdfWriter.setBoxSize("rectangle", rectangle);

			Font compnyNameFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			Font compnyInfoFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

			// Image Code
			PdfPTable c_logoTable = new PdfPTable(1);
			PdfPTable c_logoTable1 = new PdfPTable(1);
			PdfPTable c_logoTableDigi = new PdfPTable(1);
			PdfPTable c_nameTable = new PdfPTable(1);
			c_logoTable.setWidthPercentage(100);
			// c_logoTable.setWidths(new float[] { 1.0F });
			PdfPCell c_compnyInfoCell = new PdfPCell();
			PdfPCell c_logoCell = new PdfPCell();
			PdfPCell c_logoCell1 = new PdfPCell();
			PdfPCell c_logoCellDigi = new PdfPCell();
			c_logoCell.setBorder(Rectangle.NO_BORDER);
			c_logoCell1.setBorder(Rectangle.NO_BORDER);
			c_logoCellDigi.setBorder(Rectangle.NO_BORDER);
			PdfPCell c_nameCell = new PdfPCell();
			c_nameCell.setPhrase(new Phrase(company.getCompanyName(), compnyNameFont));
			c_compnyInfoCell.setPhrase(new Phrase(company.getAddress1().getState().getStateName() + " "
					+ company.getAddress1().getCity().getCityName() + " - " + company.getAddress1().getPincode(),
					compnyInfoFont));

			c_nameTable.addCell(c_nameCell);
			c_nameTable.addCell(c_compnyInfoCell);
			String path = company.getCompanyLogoPath();
			pathNew = path;
			companyName = company.getCompanyName();
			companyWebSite = company.getWebsite();
			companyAddress = "Regd. Office: " + company.getAddress1().getAddressText() + " "
					+ company.getAddress1().getCity().getCityName() + " "
					+ company.getAddress1().getState().getStateName() + " - " + company.getAddress1().getPincode();
			String rootPath = System.getProperty(StatusMessage.CATALINA_HOME_CODE);
			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;
			Image image = Image.getInstance(rootPath);
			if (image != null) {
				float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
				image.setAbsolutePosition(200, 200);
				image.setAlignment(Image.TOP);
				image.setAlignment(Image.ORIGINAL_WMF);
				image.setAlignment(Image.ALIGN_CENTER);
				image.scalePercent(scaler);
				c_logoCell.addElement(image);
				c_logoTable.addCell(c_logoCell);
				document.add(c_logoTable);

			}

			pdfWriter.setPageEvent(new MyFooter());
			String k = letterList.getLetterDecription();
			// StringBuilder sb= new StringBuilder(k.replace("<p>", "<p
			// style=\"margin:0;font-size: 14px;line-height: 26px;\">"));

			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));
			document.add(para);

			// digital sign
			if (authorizedSignatory != null) {
				if (authorizedSignatory.getSignatureImagePath() != null) {
					String rootPathDigi = System.getProperty(StatusMessage.CATALINA_HOME_CODE);

					rootPathDigi = rootPathDigi + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER
							+ File.separator + authorizedSignatory.getSignatureImagePath();

					Image imageDigi = Image.getInstance(rootPathDigi);

					if (imageDigi != null) {

						float scaler = ((document.getPageSize().getWidth()) / imageDigi.getWidth()) * 19;
						imageDigi.setAbsolutePosition(200, 200);
						imageDigi.setAlignment(Image.BOTTOM);
						imageDigi.setAlignment(Image.ORIGINAL_PNG);
						imageDigi.setAlignment(Image.ALIGN_LEFT);
						imageDigi.scalePercent(scaler);
						c_logoCellDigi.addElement(imageDigi);
						c_logoTableDigi.addCell(c_logoCellDigi);
						c_logoTableDigi.setWidthPercentage(100);
						// document.setMargins(0, 0, 30, 30);
						document.add(c_logoTableDigi);

					}

					PdfPTable table = new PdfPTable(1);
					table.setWidthPercentage(100);

					table.addCell(getCell(authorizedSignatory.getPersonName(), PdfPCell.ALIGN_LEFT));
					document.add(table);
					PdfPTable table1 = new PdfPTable(1);
					table1.setWidthPercentage(100);

					table1.addCell(getCell(authorizedSignatory.getDesignationName(), PdfPCell.ALIGN_LEFT));
					document.add(table1);

				} // end

				// QR Code start Bharat
				if (authorizedSignatory.getQrCodeStatus().equals(StatusMessage.YES_CODE)) {
					if (authorizedSignatory.getQrCodeImagePath() != null) {
						String rootPathQr = System.getProperty(StatusMessage.CATALINA_HOME_CODE);
						rootPathQr = rootPathQr + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER
								+ File.separator + authorizedSignatory.getQrCodeImagePath();
						Image imageQR = Image.getInstance(rootPathQr);
						if (imageQR != null) {
							float scaler = ((document.getPageSize().getWidth()) / imageQR.getWidth()) * 19;
							imageQR.setAbsolutePosition(200, 200);
							imageQR.setAlignment(Image.BOTTOM);
							imageQR.setAlignment(Image.ORIGINAL_PNG);
							imageQR.setAlignment(Image.ALIGN_LEFT);
							imageQR.scalePercent(scaler);
							c_logoCell1.addElement(imageQR);
							c_logoTable1.addCell(c_logoCell1);
							c_logoTable1.setWidthPercentage(100);
							document.add(c_logoTable1);
						}

					}
				} // QR Code end

			} // end if authorized Signatory

			pdfWriter.setPageEvent(new MyFooter());

			// end if authorized Signatory

			// declaration
			if (letterList.getDeclarationStatus() != null) {
				if (letterList.getDeclarationStatus().equalsIgnoreCase("APR")) {

					if (letterDaclaration.getHeading() != null && letterDaclaration.getDeclarationContant() != null) {
						PdfPTable table = new PdfPTable(1);
						table.setWidthPercentage(100);
						table.addCell(getCell(letterDaclaration.getHeading(), PdfPCell.ALIGN_CENTER));
						document.add(table);

						PdfPTable table1 = new PdfPTable(1);
						table1.setWidthPercentage(100);
						table1.addCell(getCell(letterDaclaration.getDeclarationContant(), PdfPCell.ALIGN_LEFT));
						document.add(table1);

						PdfPTable table2 = new PdfPTable(1);
						table2.setWidthPercentage(100);
						table2.addCell(getCell("Accepted by: " + letterDTO.getEmployeeName(), PdfPCell.ALIGN_LEFT));
						document.add(table2);

						PdfPTable table3 = new PdfPTable(1);
						table3.setWidthPercentage(100);
						table3.addCell(
								getCell("Dated:  " + letterList.getDeclarationDate().toString(), PdfPCell.ALIGN_LEFT));
						document.add(table3);
						PdfPTable table4 = new PdfPTable(1);
						table4.setWidthPercentage(100);
						table4.addCell(getCell(
								"                                                                                   ",
								PdfPCell.ALIGN_LEFT));
						document.add(table4);

					} // end
				}
			}
			pdfWriter.setPageEvent(new MyFooter());

			document.close();
			// fileLocation.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

//	public PdfPCell getCell(String text, int alignment) {
//		Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
//		Phrase phrase = new Phrase(text, font);
//	    PdfPCell cell = new PdfPCell(new Phrase(phrase));
//	    cell.setPadding(3);
//	    cell.setSpaceCharRatio(0);
//	    
//	    cell.setHorizontalAlignment(alignment);
//	    cell.setBorder(PdfPCell.NO_BORDER);
//	    return cell;
//	}

	@SuppressWarnings("deprecation")
	public ByteArrayInputStream generateLetterPdf(Company company, Letter letterList) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			Document document = new Document();
			PdfWriter.getInstance(document, out);
			document.open();

			// Image Code
			PdfPTable c_logoTable = new PdfPTable(1);
			c_logoTable.setWidthPercentage(80.0F);
			c_logoTable.setWidths(new float[] { 1.0F });

			PdfPCell c_logoCell = new PdfPCell();
			c_logoCell.setBorder(Rectangle.NO_BORDER);

			String path = company.getCompanyLogoPath();
			String rootPath = System.getProperty("catalina.home");

			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;

			Image image = Image.getInstance(rootPath);

			if (image != null) {

				float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
				image.setAbsolutePosition(200, 200);
				image.setAlignment(Image.TOP);
				image.setAlignment(Image.ORIGINAL_WMF);
				image.setAlignment(Image.ALIGN_CENTER);
				image.scalePercent(scaler);
				c_logoCell.addElement(image);
				c_logoTable.addCell(c_logoCell);
				document.add(c_logoTable);
			}
			String k = letterList.getLetterDecription();

			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));

			document.close();
			// fileLocation.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generatePolicyFile(CompanyPolicy companyPolicyList) throws IOException {

		String rootPath = System.getProperty("catalina.home");

		rootPath = rootPath + GlobalConstantUtils.custom_separateor + HrmsGlobalConstantUtil.APP_BASE_FOLDER
				+ companyPolicyList.getFileLocation();

		logger.info("rootPath is :" + rootPath);

		File file = new File(rootPath);

		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();

		return new ByteArrayInputStream(bytesArray);
	}

	class MyFooter extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {
			try {
				PdfPTable c_logoTable = new PdfPTable(1);
				PdfPCell c_logoCell = new PdfPCell();
				c_logoCell.setBorder(Rectangle.NO_BORDER);

				String rootPath = System.getProperty("catalina.home");

				rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator
						+ pathNew;

				Image image = Image.getInstance(rootPath);

				if (image != null) {

					float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
					image.setAbsolutePosition(200, 200);
					image.setAlignment(Image.TOP);
					image.setAlignment(Image.ORIGINAL_WMF);
					image.setAlignment(Image.ALIGN_CENTER);
					image.scalePercent(scaler);
					c_logoCell.addElement(image);
					c_logoTable.addCell(c_logoCell);
					document.add(c_logoTable);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void onEndPage(PdfWriter writer, Document document) {
			Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
			Font font3 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
			font.setColor(62, 61, 61);
			font2.setColor(62, 61, 61);
			font3.setColor(62, 61, 61);
			Phrase phraseNew = new Phrase("\n", font);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phraseNew, 50, 50, 0);
			Phrase phrase = new Phrase(companyName, font);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase, 50, 40, 0);
			Phrase phrase2 = new Phrase(companyAddress, font2);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase2, 50, 25, 0);
			Phrase phraseWebSite = new Phrase(companyWebSite, font3);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, phraseWebSite, 500, 32, 0);
//			 ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
		}

	}

	public PdfPCell getCell(String text, int alignment) {
		Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
		Phrase phrase = new Phrase(text, font);
		PdfPCell cell = new PdfPCell(new Phrase(phrase));

		cell.setPadding(3);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	@SuppressWarnings("deprecation")
	public ByteArrayInputStream generateOfferLetter(Company company, CandidateLetter candidateLetterList) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			Document document = new Document();
			PdfWriter.getInstance(document, out);
			document.open();

			// Image Code
			PdfPTable c_logoTable = new PdfPTable(1);
			c_logoTable.setWidthPercentage(80.0F);
			c_logoTable.setWidths(new float[] { 1.0F });

			PdfPCell c_logoCell = new PdfPCell();
			c_logoCell.setBorder(Rectangle.NO_BORDER);

			String path = company.getCompanyLogoPath();
			String rootPath = System.getProperty("catalina.home");

			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;

			Image image = Image.getInstance(rootPath);

			if (image != null) {

				float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
				image.setAbsolutePosition(200, 200);
				image.setAlignment(Image.TOP);
				image.setAlignment(Image.ORIGINAL_WMF);
				image.setAlignment(Image.ALIGN_CENTER);
				image.scalePercent(scaler);
				c_logoCell.addElement(image);
				c_logoTable.addCell(c_logoCell);
				document.add(c_logoTable);
			}
			String k = candidateLetterList.getLetterDescription();

			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));

			document.close();
			// fileLocation.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	//np
	public ByteArrayInputStream generateInvoice(EmployeeExpenseClaim invoice) throws IOException {
		


		String rootPath = System.getProperty("catalina.home");

		rootPath = rootPath + GlobalConstantUtils.custom_separateor + HrmsGlobalConstantUtil.APP_BASE_FOLDER
				+ invoice.getFilePath();

		logger.info("generateInvoice rootPath is :" + rootPath);

		File file = new File(rootPath);

		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();

		return new ByteArrayInputStream(bytesArray);
	
	}

}
