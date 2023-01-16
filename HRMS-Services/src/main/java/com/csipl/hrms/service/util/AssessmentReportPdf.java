package com.csipl.hrms.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.common.Company;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class AssessmentReportPdf {
/*
 * created by shubham yaduwanshi
 */
	Document document = new Document(PageSize.A4, 0, 0, 0, 0);
	public static final FontFamily fontFamily = Font.FontFamily.HELVETICA;
	public static final FontFamily fontFamilyForHeading = Font.FontFamily.TIMES_ROMAN;
	public static final int fontSize = 13;
	public static final int fontSizeForHeading = 12;
	public static final int normalFontType = Font.NORMAL;
	final BaseColor fontColorBlack = BaseColor.BLACK;
	final BaseColor compBackground = new BaseColor(222, 222, 222);
	final BaseColor candInfoBackground = new BaseColor(250, 250, 250);
	final BaseColor tableHeader = new BaseColor(90, 90, 90);
	Font paraFont = new Font(fontFamily, fontSize, normalFontType, fontColorBlack);
	Font tableHeaderFont = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(255, 255, 255));
	private static final Logger logger = LoggerFactory.getLogger(AssessmentReportPdf.class);

	public ByteArrayInputStream reportCardPdf(InterviewSchedulerDTO interviewScheduler, Company company)
			throws DocumentException, MalformedURLException, IOException {

		Font compnyNameFont = new Font(FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(51, 51, 51));
		Font compnyInfoFont = new Font(FontFamily.HELVETICA, 14, Font.NORMAL, new BaseColor(51, 51, 51));
		Font reportCard = new Font(FontFamily.HELVETICA, 15, Font.NORMAL, new BaseColor(74, 74, 74));
		Font candidateName = new Font(FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(6, 6, 6));
		Font formalFont = new Font(FontFamily.HELVETICA, 13, Font.BOLD, new BaseColor(0, 0, 0));
		Font grayfont = new Font(FontFamily.HELVETICA, 13, Font.NORMAL, new BaseColor(141, 141, 141));
		Font resultfont = new Font(FontFamily.HELVETICA, 16, Font.NORMAL);
		Font pass = new Font(FontFamily.HELVETICA, 14, Font.BOLD, fontColorBlack);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();
		logger.info("inside reportCard()");

		List<InterviewLevelDTO> interviewLevelList = interviewScheduler.getInterviewLevelDTOList();
		String finalStatus = null;
		String result_remark = null;
		String date_update = null;
		String level_result_status = null;
		SimpleDateFormat formattedDate = new SimpleDateFormat("dd MMMM yyyy");

		if (company.getCompanyLogoPath() != null && !company.getCompanyLogoPath().equals("")) {
			PdfPTable c_logoTable = new PdfPTable(1);
			c_logoTable.setWidthPercentage(80.0F);
			c_logoTable.setWidths(new float[] { 1.0F });

			// Image Code
			PdfPCell c_logoCell = new PdfPCell();
			c_logoCell.setBorder(Rectangle.NO_BORDER);
			String rootPath = System.getProperty("catalina.home");
			logger.info("CompanyLogoPath :" + company.getCompanyLogoPath());

			String path = company.getCompanyLogoPath() != null ? company.getCompanyLogoPath() : "";
			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + path;
			logger.info("rootPath :" + rootPath);

			try {
				Image image = Image.getInstance(rootPath);
				if (image != null) {

					System.out.println("image" + image);
					float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 42;
					image.setAlignment(Image.ORIGINAL_WMF);
					image.setAlignment(Image.ALIGN_CENTER);
					image.scalePercent(scaler);
					c_logoCell.addElement(image);
					c_logoCell.setPaddingTop(12f);
					c_logoCell.setPaddingBottom(5f);
					c_logoTable.addCell(c_logoCell);
					document.add(c_logoTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		PdfPTable c_nameTable = new PdfPTable(1);
		c_nameTable.setWidthPercentage(100f);
		PdfPCell c_nameCell = new PdfPCell();
		PdfPCell c_compnyInfoCell = new PdfPCell();
		c_nameCell.setPhrase(new Phrase(company.getCompanyName(), compnyNameFont));
		c_nameCell.setBackgroundColor(compBackground);
		c_nameCell.setBorder(Rectangle.NO_BORDER);
		c_nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
		c_nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		c_nameCell.setPaddingTop(14f);

		c_compnyInfoCell.setPhrase(new Phrase(company.getAddress1().getState().getStateName() + " "
				+ company.getAddress1().getCity().getCityName() + " - " + company.getAddress1().getPincode(),
				compnyInfoFont));
		c_compnyInfoCell.setBackgroundColor(compBackground);
		c_compnyInfoCell.setBorder(Rectangle.NO_BORDER);
		c_compnyInfoCell.setVerticalAlignment(Element.ALIGN_CENTER);
		c_compnyInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		c_compnyInfoCell.setPaddingBottom(14f);

		c_nameTable.addCell(c_nameCell);
		c_nameTable.addCell(c_compnyInfoCell);
		c_nameTable.setSpacingBefore(5);
		document.add(c_nameTable);

		Paragraph name = new Paragraph("Report Card", reportCard);
		name.setAlignment(Element.ALIGN_CENTER);
		name.setSpacingBefore(2);
		name.setSpacingAfter(15);
		document.add(name);

		PdfPTable cand_detail_table = new PdfPTable(2);
		float[] columnWidths = new float[] { 10f, 4f };
		cand_detail_table.setWidths(columnWidths);
		cand_detail_table.setWidthPercentage(95);
		cand_detail_table.setSpacingBefore(20);

		PdfPCell candidate = new PdfPCell(new Paragraph(interviewScheduler.getCandidateName(), candidateName));
		candidate.setPaddingTop(13);
		candidate.setPaddingLeft(15);
		candidate.setBackgroundColor(candInfoBackground);
		candidate.setBorder(Rectangle.NO_BORDER);
		candidate.setBorder(Rectangle.LEFT | Rectangle.TOP);
		candidate.setFixedHeight(20f);

		Paragraph gradeAndLevel = new Paragraph();
		gradeAndLevel.setAlignment(Element.ALIGN_LEFT);
		gradeAndLevel.add(new Chunk("Grade: ", formalFont));
		gradeAndLevel.add(new Chunk(interviewScheduler.getGradeName() + ",  ", grayfont));
		gradeAndLevel.add(new Chunk("Level: ", formalFont));
		gradeAndLevel.add(new Chunk(interviewScheduler.getPositionType(), grayfont));
		PdfPCell gradeAndLevelCell = new PdfPCell(gradeAndLevel);
		gradeAndLevelCell.setPaddingTop(13);
		gradeAndLevelCell.setPaddingRight(15);
		gradeAndLevelCell.setBorder(Rectangle.NO_BORDER);
		gradeAndLevelCell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		gradeAndLevelCell.setFixedHeight(32f);
		gradeAndLevelCell.setBackgroundColor(candInfoBackground);

		Paragraph positionAndId = new Paragraph();
		positionAndId.setAlignment(Element.ALIGN_LEFT);
		positionAndId.add(new Chunk(interviewScheduler.getPositionTitle(), grayfont));
		positionAndId.add(new Chunk(" | ", grayfont));
		positionAndId.add(new Chunk(interviewScheduler.getPositionCode(), grayfont));
		PdfPCell positionAndIdCell = new PdfPCell(positionAndId);
		positionAndIdCell.setPaddingLeft(15);
		positionAndIdCell.setBorder(Rectangle.NO_BORDER);
		positionAndIdCell.setBorder(Rectangle.LEFT);
		positionAndIdCell.setBackgroundColor(candInfoBackground);
		positionAndIdCell.setFixedHeight(20f);

		Paragraph joblocation = new Paragraph();
		joblocation.setAlignment(Element.ALIGN_LEFT);
		joblocation.add(new Chunk("Job Location:", grayfont));
		joblocation.add(new Chunk(interviewScheduler.getJobLocation(), grayfont));
		PdfPCell joblocationCell = new PdfPCell(joblocation);
		joblocationCell.setBorder(Rectangle.NO_BORDER);
		joblocationCell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
		joblocationCell.setFixedHeight(20f);
		joblocationCell.setRowspan(2);
		joblocationCell.setBackgroundColor(candInfoBackground);

		Paragraph mobileAndEmail = new Paragraph();
		mobileAndEmail.setAlignment(Element.ALIGN_LEFT);
		mobileAndEmail.setSpacingBefore(15);
		mobileAndEmail.add(new Chunk(interviewScheduler.getCandidateContactNo(), grayfont));
		mobileAndEmail.add(new Chunk(" | ", grayfont));
		mobileAndEmail.add(new Chunk(interviewScheduler.getCandidateEmailId(), grayfont));
		PdfPCell mobileAndEmailCell = new PdfPCell(mobileAndEmail);
		mobileAndEmailCell.setPaddingLeft(15);
		mobileAndEmailCell.setPaddingBottom(13);
		mobileAndEmailCell.setBorder(Rectangle.NO_BORDER);
		mobileAndEmailCell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
		mobileAndEmailCell.setBackgroundColor(candInfoBackground);


		cand_detail_table.addCell(candidate);
		cand_detail_table.addCell(gradeAndLevelCell);
		cand_detail_table.addCell(positionAndIdCell);
		cand_detail_table.addCell(joblocationCell);
		cand_detail_table.addCell(mobileAndEmailCell);
		
		document.add(cand_detail_table);
		
		//check final result status
		if (interviewScheduler.getFinalStatus() == null) {
			finalStatus = "result awaited";
		} else {
			finalStatus = interviewScheduler.getFinalStatus();
		}
		Paragraph result = new Paragraph();
		result.setAlignment(Element.ALIGN_LEFT);
		result.setSpacingBefore(15);
		result.add(new Chunk("    " + "Result: ", resultfont));
		result.add(new Chunk(finalStatus, pass));
		document.add(result);

		for (InterviewLevelDTO levelDetails : interviewLevelList) {
			logger.info("lavel Status:" + levelDetails.getStatus());
			level_result_status = levelDetails.getStatus();
			//condition for printing details of pass and rejected levels
			if (level_result_status != null) {
				logger.info("inside table print:" + levelDetails.getStatus());
				if (levelDetails.getStatus().equalsIgnoreCase("P")) {
					level_result_status = "Pass";
				} else if (levelDetails.getStatus().equalsIgnoreCase("R")) {
					level_result_status = "Rejected";
				} else {
					level_result_status = "Pending";
				}
                //for showing  assessment details
				PdfPTable round_info_table = new PdfPTable(3);
				float[] columnSize = new float[] { 1f, 1f, 1f };
				round_info_table.setWidths(columnSize);
				round_info_table.setWidthPercentage(95);
				round_info_table.setSpacingBefore(20);

				//for showing level name like L1-Technical
				PdfPCell levelName = new PdfPCell(
						new Paragraph(levelDetails.getLevel() + "-" + levelDetails.getLevelName(), tableHeaderFont));
				levelName.setPaddingLeft(14);
				levelName.setPaddingTop(12);
				levelName.setPaddingBottom(12);
				levelName.setBorder(Rectangle.NO_BORDER);
				levelName.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
				levelName.setBackgroundColor(tableHeader);

				//for showing level result status like Result :Pass
				PdfPCell level_result = new PdfPCell(new Paragraph("Result :" + level_result_status, tableHeaderFont));
				level_result.setPaddingTop(12);
				level_result.setPaddingBottom(12);
				level_result.setHorizontalAlignment(Element.ALIGN_CENTER);
				level_result.setBorder(Rectangle.NO_BORDER);
				level_result.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				level_result.setBackgroundColor(tableHeader);

				//for showing interviewer name
				PdfPCell takenBy = new PdfPCell(
						new Paragraph("Taken By :" + levelDetails.getInterviewerName(), tableHeaderFont));
				takenBy.setPaddingTop(12);
				takenBy.setPaddingBottom(12);
				takenBy.setPaddingRight(10);
				takenBy.setHorizontalAlignment(Element.ALIGN_RIGHT);
				takenBy.setBorder(Rectangle.NO_BORDER);
				takenBy.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				takenBy.setBackgroundColor(tableHeader);

				//check remark is available or not in given level
				if (levelDetails.getRemarks() == null) {
					result_remark = "remark not available";
				} else {
					result_remark = levelDetails.getRemarks();
					result_remark = result_remark.replace("\n", "").replace("\r", "");
				}

				Paragraph reamarkAndDate = new Paragraph();
				reamarkAndDate.add(new Chunk(result_remark, paraFont));
				/*
				 * if (levelDetails.getDateUpdated() != null) { date_update =
				 * formattedDate.format(levelDetails.getDateUpdated()); reamarkAndDate.add(new
				 * Chunk("\n\nupdated on "+date_update, paraFont)); }
				 */
				
				PdfPCell remark = new PdfPCell(reamarkAndDate);
				remark.setColspan(3);
				remark.setPaddingTop(9);
				remark.setPaddingLeft(14);
				remark.setPaddingBottom(18);
				remark.setLeading(12, 0.4f);
				remark.setBorder(Rectangle.NO_BORDER);
				remark.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT);

				round_info_table.addCell(levelName);
				round_info_table.addCell(level_result);
				round_info_table.addCell(takenBy);
				round_info_table.addCell(remark);

				document.add(round_info_table);
			}
		}

		document.close();

		return new ByteArrayInputStream(out.toByteArray());
	}
}
