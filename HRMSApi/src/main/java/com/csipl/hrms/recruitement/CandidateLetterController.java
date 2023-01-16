package com.csipl.hrms.recruitement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.config.TokenProvider;
import com.csipl.hrms.dto.recruitment.CandidateLetterDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.recruitment.CandidateLetter;
import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;
import com.csipl.hrms.model.recruitment.CandidatePayStructureHd;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.recruitement.CandidateLetterService;
import com.csipl.hrms.service.recruitement.CandidateLetterTemplateMasterService;
import com.csipl.hrms.service.recruitement.InterviewSchedulerService;
import com.csipl.hrms.service.recruitement.PositionService;
import com.csipl.hrms.service.recruitement.adaptor.CandidateLetterAdaptor;
import com.csipl.hrms.service.recruitement.repository.CandidatePayStructureHdRepository;
import com.csipl.hrms.service.util.SalaryPdfReport;
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

@RestController
@RequestMapping("/candidateLetter")
public class CandidateLetterController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateLetterController.class);

	public static final String ENCODING = "UTF-8";
	private String companyName = "";
	private String companyAddress = "";
	private String companyWebSite = "";
	private String pathNew = "";
	public static final int fontSize = 9;
	public static final int fontSizeForHeading = 12;
	public static final FontFamily fontFamily = Font.FontFamily.HELVETICA;
	public static final int normalFontType = Font.NORMAL;
	public static final BaseColor fontColorBlack = BaseColor.BLACK;
	Font paraFont = new Font(fontFamily, fontSize, normalFontType, fontColorBlack);

	PdfPCell cell = new PdfPCell();

	PdfPCell cellHeader = new PdfPCell();

	@Autowired
	private CandidateLetterService candidateLetterService;

	@Autowired
	private InterviewSchedulerService interviewSchedulerService;

	@Autowired
	private CandidateLetterAdaptor candidateLetterAdaptor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private PositionService positionService;

	@Autowired
	private LetterService letterService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CandidatePayStructureHdRepository candidatePayStructureHdRepository;

	@Autowired
	CandidateLetterTemplateMasterService candidateLetterTemplateMasterService;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private LoginService loginService;

	@Autowired
	ResourceLoader resourceLoader;

	@PostMapping(path = "/saveCandidateLetter")
	public void saveCandidateLetter(@RequestBody CandidateLetterDTO candidateLetterDTO, HttpServletRequest req) {

		logger.info("saveCandidateLetter is calling... ");
		CandidateLetter candidateLetter = candidateLetterAdaptor.uiDtoToDatabaseModel(candidateLetterDTO);
		// empLetterService.saveLtr(empLtr);
		logger.info("saveCandidateLetter is end...");
		candidateLetterService.saveCandidateLetter(candidateLetter);
	}

	@PutMapping(value = "/updateSelectedAnnexure")
	public void updateSelectedAnnexure(@RequestBody CandidateLetterDTO candidateLetterDTO, HttpServletRequest req) {
		logger.info(" updateSelectedAnnexure is calling :  ");
		candidateLetterService.updateSelectedAnnexure(candidateLetterDTO.getCandidateLetterId(),
				candidateLetterDTO.getAnnexureStatus(), candidateLetterDTO.getInterviewScheduleId());
		
		logger.info(" updateSelectedAnnexure is end :  ");
	}

	@GetMapping(value = "/candidateLetterById/{interviewScheduleId}")
	public @ResponseBody CandidateLetterDTO getCandidateLetterById(
			@PathVariable("interviewScheduleId") Long interviewScheduleId, HttpServletRequest req) {

		logger.info("getCandidateLetterById is calling...");

		CandidateLetter candidateLetter = candidateLetterService.getCandidateLetterById(interviewScheduleId);

		return candidateLetterAdaptor.databaseModelToUiDto(candidateLetter);
	}

	@GetMapping(value = "/getCandidateOfferLetterById/{candidateLetterId}")
	public @ResponseBody CandidateLetterDTO getCandidateOfferLetterById(
			@PathVariable("candidateLetterId") Long candidateLetterId, HttpServletRequest req) {
		logger.info("getCandidateOfferLetterById is calling....");
		return candidateLetterAdaptor
				.databaseModelToUiDto(candidateLetterService.getCandidateOfferLetterById(candidateLetterId));
	}

	@PutMapping(value = "/updateDeclarationStatus")
	public void updateDeclarationStatus(@RequestBody CandidateLetterDTO candidateLetterDTO, HttpServletRequest req) {
		logger.info("save EployeeLettersTransaction is calling :  " + candidateLetterDTO);
		candidateLetterService.updateDeclarationStatus(candidateLetterDTO.getCandidateLetterId(),
				candidateLetterDTO.getDeclerationStatus(), candidateLetterDTO.getInterviewScheduleId());
	}

	@GetMapping(value = "/getOfferLetterId")
	public @ResponseBody CandidateLetterDTO getOfferLetterId() {

		logger.info("getCandidateLetterById is calling...");

		CandidateLetter candidateLetter = candidateLetterService.getOfferLetterId();

		return candidateLetterAdaptor.databaseModelToUiDto(candidateLetter);
	}

	@GetMapping(path = "/generateOfferLetter/{companyId}/{interviewScheduleId}")
	public StreamingResponseBody generateOfferLetter(@PathVariable("companyId") Long companyId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId, HttpServletRequest req,
			HttpServletResponse response) throws ErrorHandling, IOException {

		logger.info("generateOfferLetter is calling :  ");

		CandidateLetter candidateLetterList = candidateLetterService.getCandidateLetterById(interviewScheduleId);
		Company company = companyService.getCompany(companyId);

		logger.info("generateOfferLetter is end " + candidateLetterList);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"CandidateOfferLetter.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		ByteArrayInputStream bis = new SalaryPdfReport().generateOfferLetter(company, candidateLetterList);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

	}

	@GetMapping(value = "/sendLetterToCandidate/{companyId}/{interviewScheduleId}")
	public void sendLetterToCandidate(@PathVariable("companyId") Long companyId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId, HttpServletRequest req) {

		logger.info("sendLetterToCandidate..." + interviewScheduleId);

		InterviewScheduler employee = interviewSchedulerService.findCandidateById(interviewScheduleId);
		CandidateLetter empLtr = candidateLetterService.getCandidateLetterById(interviewScheduleId);
		// String k = empLtr.getLetterDecription();
		User user = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String username = null;
		if (attr != null) {
			username = attr.getRequest().getParameter("username"); // find parameter from request
			if (username == null)
				username = attr.getRequest().getHeader("username");
		}
		String name[] = username.split("-");
		if (name[0].equals("Administrator")) {
			user = loginService.findUserByUserName(name[0].trim());
		} else {
			user = loginService.findUserByUserName(username.trim());
		}
		Map<String, Object> claims = new HashMap<>();
		// TokenProvider tokenProvider = TokenProvider.getTokenProvider();
		claims.put("user", TokenProvider.getTokenProvider());
		final String token = jwtTokenUtil.doGenerateTokenByClaims(claims, user.getLoginName(), user.getUserPassword());
		System.out.println("============token===========" + token);
		triggerEmailNew(companyId, empLtr, employee.getCandidateEmailId(), employee, empLtr.getLetter().getLetterId(),
				token);

	}

	@SuppressWarnings("deprecation")
	private void triggerEmailNew(Long companyId, CandidateLetter empLtr, String ofcMailId, InterviewScheduler employee,
			Long letterId, String token) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String tenantId = null;
		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		User user = loginService.findUserByUserId(employee.getUserId());
		Letter letterList = letterService.findLetter(letterId);
		Company company = companyService.getCompany(companyId);
		Position position = positionService.findPositionById(employee.getPosition().getPositionId());
		CandidatePayStructureHd payStructureHd = candidatePayStructureHdRepository
				.monthValidationList(employee.getInterviewScheduleId());
		CandidateLetterTemplateMaster candidateLetterTemplateMaster = candidateLetterTemplateMasterService
				.getCandidateLetterByTemplateType("CLF");

		String emailLink = AppUtils.ACCEPTANCE_URL + empLtr.getCandidateLetterId() + "&companyId=" + 1 + "&tenantId="
				+ tenantId + "&token=" + token + "&username=" + user.getLoginName();

		EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.LETTER_CODE);

		JavaMailSenderImpl mailSender = null;
		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========letter==.getJavaMailSender()===============" + listEmail.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);

		} else {
			logger.info("===========letter==.getJavaMailSender()================" + listEmail.toString());
			mailSender.setHost(confugration.getHost());
			mailSender.setPort(confugration.getPort());
			mailSender.setUsername(listEmail.getUserName());
			mailSender.setPassword(listEmail.getPassword());
			props.put("mail.transport.protocol", confugration.getProtocol());
			props.put("mail.smtp.auth", confugration.getAuth());
			props.put("mail.smtp.ssl.trust", confugration.getSslName());
			props.put("mail.smtp.starttls.enable", confugration.getStarttlsName());
			props.put("mail.mime.charset", ENCODING);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");

			try {

				logger.info("Mail is sending...");

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setSubject(letterList.getLetterName());
				mimeMessageHelper.setFrom(listEmail.getFromMail());
				Map<String, Object> model = new HashMap<String, Object>();
				Map<String, Object> model1 = new HashMap<String, Object>();

				if (employee.getCandidateName() != null) {
					model.put("EMPLOYEE_NAME", employee.getCandidateName());
				} else {
					model.put("EMPLOYEE_NAME", "");
				}

				if (employee.getCandidateName() != null) {
					model1.put("EMPLOYEE_NAME", employee.getCandidateName());
				} else {
					model1.put("EMPLOYEE_NAME", "");
				}

				if (company.getCompanyName() != null) {
					model.put("COMPANY_NAME", company.getCompanyName());
				} else {
					model.put("COMPANY_NAME", "");
				}

				if (company.getEmailId() != null) {
					model.put("COMPANY_EMAIL", company.getEmailId());
				} else {
					model.put("COMPANY_EMAIL", "");
				}

				if (position.getPositionTitle() != null) {
					model.put("POSITION_TITLE", position.getPositionTitle());
				} else {
					model.put("POSITION_TITLE", "");
				}

				if (position.getPositionCode() != null) {
					model.put("POSITION_CODE", position.getPositionCode());
				} else {
					model.put("POSITION_CODE", "");
				}

				if (payStructureHd.getCtc() != null) {
					model.put("OFFERED_CTC", payStructureHd.getCtc());
				} else {
					model.put("OFFERED_CTC", "");
				}

				if (payStructureHd.getEffectiveDate() != null) {
					model.put("DATE_OF_JOINING", payStructureHd.getEffectiveDate());
				} else {
					model.put("DATE_OF_JOINING", "");
				}

				model.put("HIRING_SPOC", "Neelesh Sharma");
				model.put("REPORTING_TIME", "10:00 am");
				model1.put("link", emailLink);

				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(
						classLoader.getResource(".").getFile() + "/templates/candidate_offer_letter_template.vm");
				logger.info("File --->>" + file);
				if (file.createNewFile()) {
					logger.info("File is created!");
				} else {
					logger.info("File already exists.");
				}
				try {

					FileWriter fw = new FileWriter(file);
					fw.write(candidateLetterTemplateMaster.getBodyValue() + "\r\n "
							+ candidateLetterTemplateMaster.getFooterValue());
					fw.close();
				} catch (Exception e) {
					System.out.println(e);
				}

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"/templates/candidate_offer_letter_template.vm", "UTF-8", model);

				String acceptance = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"/templates/offer_letter_acceptance.vm", "UTF-8", model1);

				String message = text + acceptance;

				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = employee.getCandidateEmailId() + "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = employee.getCandidateEmailId();
					mimeMessageHelper.setTo(to);
				}
				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					String[] cc1 = ccList.split(",");
					mimeMessageHelper.setCc(cc1);
				}

				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
					String bccList1 = listEmail.getBcc();
					String[] bcc1 = bccList1.split(",");
					mimeMessageHelper.setBcc(bcc1);
				}

				mimeMessageHelper.setText(message, true);

				ByteArrayOutputStream out = null;

				try {
					// construct the text body part
					MimeBodyPart textBodyPart = new MimeBodyPart();
					textBodyPart.setText(empLtr.getLetterDescription());
					// now write the PDF content to the output stream
					out = new ByteArrayOutputStream();

					// Document document = new Document();
					Document document = new Document(PageSize.A4, 50, 45, 50, 40);
					PdfWriter writer = PdfWriter.getInstance(document, out);

					Paragraph para = new Paragraph();
					para.setFont(paraFont);

					document.open();
					writer.setPageEvent(new MyFooter());
					Rectangle rectangle = new Rectangle(36, 36, 559, 806);
					// Rectangle rectangle = new Rectangle(30, 30, 550, 800);
					rectangle.setBorder(Rectangle.BOX);
					rectangle.setBorderWidth(2);
					document.setMarginMirroring(false);
					writer.setBoxSize("rectangle", rectangle);
					Font compnyNameFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
					Font compnyInfoFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
					companyName = company.getCompanyName();
					companyWebSite = company.getWebsite();
					companyAddress = "Regd. Office: " + company.getAddress1().getAddressText() + " "
							+ company.getAddress1().getCity().getCityName() + " "
							+ company.getAddress1().getState().getStateName() + " - "
							+ company.getAddress1().getPincode();
					PdfPTable c_logoTable = new PdfPTable(1);
					PdfPTable c_nameTable = new PdfPTable(1);
					c_logoTable.setWidthPercentage(80.0F);
					c_logoTable.setWidths(new float[] { 1.0F });
					PdfPCell c_compnyInfoCell = new PdfPCell();
					PdfPCell c_logoCell = new PdfPCell();
					PdfPCell c_nameCell = new PdfPCell();
					PdfPCell c_logoCellDigi = new PdfPCell();
					// PdfPTable c_logoTableDigi = new PdfPTable(1);
					PdfPCell c_logoCell1 = new PdfPCell();
					// PdfPTable c_logoTable1 = new PdfPTable(1);
					c_logoCell.setBorder(Rectangle.NO_BORDER);
					c_logoCell1.setBorder(Rectangle.NO_BORDER);
					c_logoCell.setBorder(Rectangle.NO_BORDER);
					c_logoCellDigi.setBorder(Rectangle.NO_BORDER);
					c_nameCell.setPhrase(new Phrase(company.getCompanyName(), compnyNameFont));
					c_compnyInfoCell.setPhrase(new Phrase(company.getAddress1().getState().getStateName() + " "
							+ company.getAddress1().getCity().getCityName() + " - "
							+ company.getAddress1().getPincode(), compnyInfoFont));
					c_logoCell.setBorder(Rectangle.NO_BORDER);
					c_nameTable.addCell(c_nameCell);
					c_nameTable.addCell(c_compnyInfoCell);
					String path = company.getCompanyLogoPath();
					pathNew = path;
					String rootPath = System.getProperty("catalina.home");

					rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator
							+ path;

					Image image = Image.getInstance(rootPath);

					if (image != null) {

						float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 19;
						image.setAbsolutePosition(200, 200);
						image.setAlignment(Image.ORIGINAL_WMF);
						image.setAlignment(Image.ALIGN_CENTER);
						image.scalePercent(scaler);
						c_logoCell.addElement(image);
						c_logoTable.addCell(c_logoCell);
						document.add(c_logoTable);
					}

					String k = empLtr.getLetterDescription();
					HTMLWorker htmlWorker = new HTMLWorker(document);
					htmlWorker.parse(new StringReader(k));
					document.add(para);

					logger.info("Mail sent...");
					document.close();

					byte[] bytes = out.toByteArray();

					// construct the pdf body part
					DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
					MimeBodyPart pdfBodyPart = new MimeBodyPart();
					pdfBodyPart.setDataHandler(new DataHandler(dataSource));
					pdfBodyPart.setFileName(letterList.getLetterType());
					mimeMessageHelper.addAttachment(pdfBodyPart.getFileName(), dataSource);

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					// clean off
					if (null != out) {
						try {
							out.close();
							out = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("Mail sent successfully");
					} catch (Exception ex) {
						logger.info("Mail sent failed");
						ex.printStackTrace();
					}
				} else
					logger.info("Mail sent failed");
			} catch (Exception e) {
				logger.info("Mail sent failed");
				e.printStackTrace();
			}
		}
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

	@GetMapping(path = "/generateCandidateLetter/{companyId}/{interviewScheduleId}")
	public void generateCandidateLetter(@PathVariable("companyId") Long companyId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId) throws ErrorHandling {
		candidateLetterService.generateCandidateLetter(companyId, interviewScheduleId);
	}

}
