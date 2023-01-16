package com.csipl.hrms.employee.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.config.TokenProvider;
import com.csipl.hrms.dto.employee.LetterDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.model.employee.LetterDaclaration;
import com.csipl.hrms.service.adaptor.LetterAdaptor;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.AuthorizedSignatoryService;
import com.csipl.hrms.service.employee.EmployeeLetterService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.LetterDaclarationService;
import com.csipl.hrms.service.employee.LetterService;
import com.csipl.hrms.service.organization.CompanyService;
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
import com.itextpdf.tool.xml.XMLWorkerHelper;

@RestController
@RequestMapping("/letter")
public class LetterController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(LetterController.class);
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
	private LetterService letterService;
	@Autowired
	private	AuthorizedSignatoryService authorizedSignatoryService;
	@Autowired
	private LetterAdaptor letterAdaptor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	private EmployeeLetterService empLetterService;

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private LetterDaclarationService letterDaclarationService;
	/**
	 * 
	 * @param ltrDTO
	 * @param req
	 */
	@PostMapping(path = "/save")
	public void saveLetter(@RequestBody LetterDTO ltrDTO, HttpServletRequest req) {
		logger.info("saveLetter is calling : LetterDTO " + ltrDTO);
		Letter empLtr = letterAdaptor.uiDtoToDatabaseModel(ltrDTO);
		letterService.saveLtr(empLtr);

		logger.info("saveLetter is end  :" + empLtr);
	}

	/**
	 * 
	 * @param companyId
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@GetMapping(path = "/fetchList/{companyId}")
	public List<LetterDTO> getAllLetters(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {
		logger.info("getAllLetters is calling :  ");
		List<Letter> letterList = letterService.findAllLetter(companyId);
		logger.info("LetterController.getAllLetters()" + letterList.toString());

		return letterAdaptor.databaseModelToUiDtoList(letterList);

	}

	@GetMapping(path = "/getLetterByLetterId/{letterId}")
	public LetterDTO getLetterByOne(@PathVariable("letterId") Long letterId, HttpServletRequest req) {
		logger.info("getLetterByOne is calling : letterId  " + letterId);
		Letter letter = letterService.findLetter(letterId);
		logger.info("LetterController.getLetterByOne()" + letter.toString());
		return letterAdaptor.databaseModelToUiDto(letter);

	}

	@SuppressWarnings({ "deprecation" })
	@GetMapping(path = "/fetchLetter/{companyId}/{letterId}")
	public StreamingResponseBody getLetterById(@PathVariable("companyId") Long companyId,
			@PathVariable("letterId") Long letterId, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling {

		logger.info("getLetter is calling :  ");

		Letter letterList = letterService.findLetter(letterId);
		Company company = companyService.getCompany(companyId);
		logger.info("getAllLetters is end : Letter List " + letterList);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"Letter.pdf\"");
		// letterAdaptor.databaseModelToUiDto(letterList);

		ByteArrayInputStream bis = new SalaryPdfReport().generateLetterPdf(company, letterList);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

		// return letterAdaptor.databaseModelToUiDto(letterList);
	}

	@GetMapping(path = "/fetchLetterPdf/{letterId}")
	public void generatePDF(@PathVariable("letterId") Long letterId) throws ErrorHandling {
		logger.info("getLetter is calling :  ");

		Letter letterList = letterService.findLetter(letterId);

		try {
			OutputStream file = new FileOutputStream(new File(
					"E:\\apache-tomcat-8.5.20\\webapps\\Document\\Employee\\" + letterList.getLetterName() + ".pdf"));
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, file);
			StringBuilder htmlString = new StringBuilder();
			htmlString.append(new String(letterList.getLetterDecription()));
			document.open();

			InputStream is = new ByteArrayInputStream(htmlString.toString().getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			document.addCreationDate();
			document.close();
			System.out.println("LetterController.generatePDF()" + document);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAllLetters is end : Letter List " + letterList);

	}

	@GetMapping(value = "/sendLetterToEmployee/{companyId}/{empId}/{letterId}")
	public void sendLetterToEmployee(@PathVariable("companyId") Long companyId, @PathVariable("empId") Long empId,
			@PathVariable("letterId") Long letterId, HttpServletRequest req) {

		logger.info("sendLetterToEmployee..." + empId + "...EmployeeLetterDTO...");

		Employee employee = employeePersonalInformationService.findEmployeesById(empId);
		EmployeeLetter empLtr = empLetterService.findEmployeeLetterById(empId, letterId);
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
             Map<String, Object> claims= new HashMap<>();
             TokenProvider tokenProvider = TokenProvider.getTokenProvider();
              claims.put("user", TokenProvider.getTokenProvider());
              final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
               System.out.println("============token==========="+token);
		triggerEmailNew(companyId, empLtr, employee.getOfficialEmail(), empLtr.getEmpId(), empLtr.getLetterId(),token);

		
		 
         //  	EmployeeLetter letterList = empLetterService.findEmployeeLetterByEmpLetterId(empLtr.getEmpId(), empLtr.getLetterId(), empLtr.getEmpLetterId());
	     	//empLetterService.triggerDeclarationMail(letterList, token);
		
	}
	
	@GetMapping(value = "/sendLetterToEmployeeLetter/{companyId}/{empId}/{letterId}/{empLetterId}")
	public void sendLetterToEmployeeLetter(@PathVariable("companyId") Long companyId, @PathVariable("empId") Long empId,
			@PathVariable("letterId") Long letterId, @PathVariable("empLetterId") Long empLetterId,HttpServletRequest req) {
		logger.info("sendLetterToEmployeeLetter..." + empId + "...EmployeeLetterDTO...");
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);
		EmployeeLetter empLtr = empLetterService.findEmployeeLetterByEmpLetterId(empId, letterId, empLetterId);
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
             Map<String, Object> claims= new HashMap<>();
             TokenProvider tokenProvider = TokenProvider.getTokenProvider();
              claims.put("user", TokenProvider.getTokenProvider());
              final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
               System.out.println("============token==========="+token);
		triggerEmailNew(companyId, empLtr, employee.getOfficialEmail(), empLtr.getEmpId(), empLtr.getLetterId(),token);

	
        //   	EmployeeLetter letterList = empLetterService.findEmployeeLetterByEmpLetterId(empLtr.getEmpId(), empLtr.getLetterId(), empLetterId);
	  //  empLetterService.triggerDeclarationMail(letterList, token);
		
	}

	@SuppressWarnings("deprecation")
	private void triggerEmailNew(Long companyId, EmployeeLetter empLtr, String ofcMailId, Long empId, Long letterId, String token) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String tenantId = null;
		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		Employee employee = employeePersonalInformationService.findEmployeesById(empId);
		User user =loginService.findUserByUserId(employee.getUserId());
		Letter letterList = letterService.findLetter(letterId);
		Company company = companyService.getCompany(companyId);
		EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.LETTER_CODE);
		String emailLink = AppUtils.DECLARATION_URL + empLtr.getEmpLetterId()+ "&companyId=" + 1
				+ "&tenantId=" + tenantId +"&token="+token+"&username="+user.getLoginName();
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
				model.put("firstName", employee.getFirstName());
				model.put("link", emailLink);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"templates/letter_info_template.vm", "UTF-8", model);
				
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = employee.getPersonalEmail() + "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = employee.getPersonalEmail();
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

//			
//				mimeMessageHelper.setText("Hi "+employee.getFirstName()+",\r\n" + 
//						"Please keep attached letter herewith.\r\n" + 
//						"For any query, please do let us know.\r\n" + 
//						"\r\n" + 
//						"Regards,\r\n" + 
//						"HR Team\r\n" + 
//						company.getCompanyName());
				mimeMessageHelper.setText(text, true);
				
				
				ByteArrayOutputStream out = null;

				try {
					// construct the text body part
					MimeBodyPart textBodyPart = new MimeBodyPart();
					textBodyPart.setText(empLtr.getLetterDecription());
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
				//	Rectangle rectangle = new Rectangle(30, 30, 550, 800);
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
							+ company.getAddress1().getState().getStateName() + " - " + company.getAddress1().getPincode();
					PdfPTable c_logoTable = new PdfPTable(1);
					PdfPTable c_nameTable = new PdfPTable(1);
					c_logoTable.setWidthPercentage(80.0F);
					c_logoTable.setWidths(new float[] { 1.0F });
					PdfPCell c_compnyInfoCell = new PdfPCell();
					PdfPCell c_logoCell = new PdfPCell();
					PdfPCell c_nameCell = new PdfPCell();
					PdfPCell c_logoCellDigi = new PdfPCell();
					PdfPTable c_logoTableDigi = new PdfPTable(1);
					PdfPCell c_logoCell1 = new PdfPCell();
					PdfPTable c_logoTable1 = new PdfPTable(1);
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
					pathNew=path;
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
					
					String k = empLtr.getLetterDecription();
					HTMLWorker htmlWorker = new HTMLWorker(document);
					htmlWorker.parse(new StringReader(k));
					document.add(para);
					
					 //digital sign
					LetterDaclaration letterDaclaration = letterDaclarationService.findLetterDaclarationById(empLtr.getLetterId());
					AuthorizedSignatory authorizedSignatory = authorizedSignatoryService.findAuthorizedSignatoryById(empLtr.getLetterId());
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
							//	document.setMargins(0, 0, 30, 30);
								document.add(c_logoTableDigi);

							}
							//document.setMargins(0, 0, 30, 30);
							PdfPTable table = new PdfPTable(1);
							table.setWidthPercentage(100);
							 
							table.addCell(getCell("\n"+authorizedSignatory.getPersonName(), PdfPCell.ALIGN_LEFT));
							document.add(table);
							PdfPTable table1 = new PdfPTable(1);
							table1.setWidthPercentage(100);
							 
							table1.addCell(getCell(authorizedSignatory.getDesignationName(), PdfPCell.ALIGN_LEFT));
							document.add(table1);

						} // end
						
						// QR Code start
						if (authorizedSignatory.getQrCodeStatus().equals("Y")) {
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
					}//end if authorized Signatory
		 			//declaration 
					if (empLtr.getDeclarationStatus() != null) {
						if (empLtr.getDeclarationStatus().equalsIgnoreCase("APR")) {
 
							
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
								table2.addCell(getCell("Accepted by: " + employee.getFirstName()+" "+employee.getLastName(),
										PdfPCell.ALIGN_LEFT));
								document.add(table2);

								PdfPTable table3 = new PdfPTable(1);
								table3.setWidthPercentage(100);
								table3.addCell(
										getCell("Dated:  " + empLtr.getDeclarationDate().toString(), PdfPCell.ALIGN_LEFT));
								document.add(table3);
								PdfPTable table4 = new PdfPTable(1);
								table4.setWidthPercentage(100);
								table4.addCell(
										getCell("                                                                                   ", PdfPCell.ALIGN_LEFT));
								document.add(table4);

							} // end
						}
					}
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

	@DeleteMapping(value = "/deleteLetter/{letterId}")
	public void deleteLetter(@PathVariable("letterId") Long letterId) {

		logger.info("deleteLetter is calling :" + "deleteLetter" + letterId);
		letterService.deleteLetter(letterId);
	}
	
	
	@GetMapping(path = "/findLetterByType/{companyId}")
	public List<LetterDTO> findLetterByType(@PathVariable("companyId") Long companyId)
			throws ErrorHandling, PayRollProcessException {
		logger.info("findLetterByType is calling :  ");
		List<Letter> letterList = letterService.findLetterByType(companyId);
		logger.info("LetterController.findLetterByType()" + letterList.toString());

		return letterAdaptor.databaseModelToUiDtoList(letterList);

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
 
}
