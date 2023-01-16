package com.csipl.hrms.employee.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.dto.employee.TicketCountDTO;
import com.csipl.hrms.dto.employee.TicketRaisingHdDTO;
import com.csipl.hrms.dto.employee.TicketTypeDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.TicketDesc;
import com.csipl.hrms.model.employee.TicketRaisingHD;
import com.csipl.hrms.model.employee.TicketType;
import com.csipl.hrms.service.adaptor.TicketManagementAdaptor;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.TicketRaisingService;
import com.csipl.hrms.service.employee.TicketTypeService;
import com.csipl.tms.dto.common.SearchDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tms")
public class TicketManagementController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TicketManagementController.class);
	public static final String ENCODING = "UTF-8";

	@Autowired
	TicketTypeService ticketTypeService;

	@Autowired
	TicketRaisingService ticketRaisingService;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	TicketManagementAdaptor ticketManagementAdaptor = new TicketManagementAdaptor();

	/**
	 * @param ticketTypeDTO This is the first parameter for getting Skill Object
	 *                      from UI
	 * @param req           This is the second parameter to maintain user session
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "You have already applied Ticket in the given duration") })

	@ApiOperation(value = "Save or Update Ticket raise")
	@RequestMapping(value = "/ticketType", method = RequestMethod.POST)
	public void save(@RequestBody TicketTypeDTO ticketTypeDTO, HttpServletRequest req) {
		TicketType ticketType = ticketManagementAdaptor.uiDtoToDatabaseModel(ticketTypeDTO);
		ticketTypeService.save(ticketType);
	}

	/**
	 * to get List of TicketType from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 *//*
		 * @RequestMapping(path = "/ticketType", method = RequestMethod.GET)
		 * public @ResponseBody List<TicketTypeDTO> getAllTicketType(HttpServletRequest
		 * req) throws ErrorHandling, PayRollProcessException { List<TicketType>
		 * ticketTypeList = ticketTypeService.findAllTicketType(getCompanyId(req)); if
		 * (ticketTypeList != null && ticketTypeList.size() > 0) return
		 * ticketManagementAdaptor.databaseModelToUiDtoList(ticketTypeList); else throw
		 * new ErrorHandling(" ticketType Data not present"); }
		 */
	@RequestMapping(value = "/ticketType/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TicketTypeDTO> fetchAllTicket(@PathVariable("companyId") String companyId)
			throws ErrorHandling, PayRollProcessException {
		Long companyIdValue = Long.parseLong(companyId);
		List<TicketType> ticketTypeList = ticketTypeService.findAllTicketType(companyIdValue);
		if (ticketTypeList != null && ticketTypeList.size() > 0)
			return ticketManagementAdaptor.databaseModelToUiDtoList(ticketTypeList);
		else
			throw new ErrorHandling(" ticketType Data not present");
	}

	/**
	 * to get TicketType Object from database based on ticketTypeId (Primary Key)
	 */
	@RequestMapping(value = "/ticketTypeById/{ticketTypeId}", method = RequestMethod.GET)
	public @ResponseBody TicketTypeDTO getTicketType(@PathVariable("ticketTypeId") String ticketTypeID,
			HttpServletRequest req) throws ErrorHandling {
		Long ticketTypeId = Long.parseLong(ticketTypeID);
		TicketType ticketType = ticketTypeService.findTicketType(ticketTypeId);
		if (ticketType != null)
			return ticketManagementAdaptor.databaseModelToUiDto(ticketType);
		else
			throw new ErrorHandling(" ticketType Data not present");
	}

	/**
	 * Method performed save operation with file
	 * 
	 * @param file               This is the first parameter for taking file Input
	 * @param ticketRaisingHdDto This is the second parameter for getting
	 *                           ticketRaisingHd Object from UI
	 * @param req                This is the third parameter to maintain user
	 *                           session
	 * @throws PayRollProcessException
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "You have already applied Ticket in the given duration") })

	@ApiOperation(value = "Save or Update Ticket raise")

	@RequestMapping(value = "/ticketRaisingFile/{emailId}", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public void saveTicketRaisingHd(@PathVariable("emailId") String emailId,
			@RequestPart("uploadFile") MultipartFile file, @RequestPart("info") TicketRaisingHdDTO ticketRaisingHdDto,
			HttpServletRequest req) throws PayRollProcessException {

		logger.info("saveTicketRaisingHd..." + emailId + "...file,..." + file + "...TicketRaisingHdDTO..."
				+ ticketRaisingHdDto);

		TicketRaisingHD ticketRaisingHd = ticketManagementAdaptor.uiDtoToTicketRaisingHdModel(ticketRaisingHdDto);
		Employee employee = employeePersonalInformationService
				.findEmployeesById(ticketRaisingHd.getEmployee().getEmployeeId());

		if (ticketRaisingHd.getTicketRaisingHDId() != null)
			ticketRaisingHd.setCreatedBy(ticketRaisingHdDto.getUserId());
		TicketRaisingHD ticketRaisingHd1 = ticketRaisingService.save(ticketRaisingHd, file, true,
				ticketRaisingHdDto.getCompanyId());
		TicketType ticketType = ticketTypeService.findTicketType(ticketRaisingHdDto.getTicketTypeId());
		byte[] byteArr;
		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			byteArr = file.getBytes();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArr);
			// ticketRaisingService.mailSend(inputStream, ticketRaisingHd1, emailId,
			// employee.getOfficialEmail(), ticketRaisingHdDto, extension, employee,
			// ticketType);
			if (ticketRaisingHdDto.getTicketRaisingHDId() == null) {
				logger.info("New tickect :");
				triggerEmailNew(inputStream, ticketRaisingHd1, employee.getOfficialEmail(),
						ticketRaisingHdDto.getEmailId(), ticketRaisingHdDto, extension, employee, ticketType);
			} else
				triggerEmail(inputStream, ticketRaisingHd1, employee.getOfficialEmail(),
						ticketRaisingHdDto.getEmailId(), ticketRaisingHdDto, extension, employee, ticketType);
			logger.info("email sent...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param emailId            This is the first parameter for taking emailId from
	 *                           UI
	 * @param ticketRaisingHdDto This is the second parameter for getting
	 *                           ticketRaisingHd Object from UI
	 * @param req                This is the third parameter to maintain user
	 *                           session
	 * @throws PayRollProcessException
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "You have already applied Ticket in the given duration") })

	@ApiOperation(value = "Save or Update Ticket raise")
	@RequestMapping(value = "/ticketRaising/{emailId}", method = RequestMethod.POST)
	public void saveTicketRaisingHd(@PathVariable("emailId") String emailId,
			@RequestBody TicketRaisingHdDTO ticketRaisingHdDto, HttpServletRequest req) throws PayRollProcessException {
		logger.info("saveTicketRaisingHd..." + emailId + "...TicketRaisingHdDTO..." + ticketRaisingHdDto);
		TicketRaisingHD ticketRaisingHd = ticketManagementAdaptor.uiDtoToTicketRaisingHdModel(ticketRaisingHdDto);
		Employee employee = employeePersonalInformationService
				.findEmployeesById(ticketRaisingHd.getEmployee().getEmployeeId());

		ticketRaisingHd.setDateUpdate(new Date());

		if (ticketRaisingHd.getTicketRaisingHDId() != null)
			ticketRaisingHd.setCreatedBy(ticketRaisingHdDto.getUserId());

		TicketRaisingHD ticketRaisingHd2 = ticketRaisingService.save(ticketRaisingHd, null, false,
				ticketRaisingHdDto.getCompanyId());

		TicketRaisingHD ticketRaisingHd1 = ticketRaisingService
				.findTicketRaising(ticketRaisingHd2.getTicketRaisingHDId());

		TicketType ticketType = ticketTypeService.findTicketType(ticketRaisingHdDto.getTicketTypeId());

		if (ticketRaisingHdDto.getTicketRaisingHDId() == null) {
			logger.info("New tickect :");
			triggerEmailNew(null, ticketRaisingHd1, employee.getOfficialEmail(), ticketRaisingHdDto.getEmailId(),
					ticketRaisingHdDto, null, employee, ticketType);
		} else
			triggerEmail(null, ticketRaisingHd1, employee.getOfficialEmail(), ticketRaisingHdDto.getEmailId(),
					ticketRaisingHdDto, null, employee, ticketType);

		logger.info("email sent...");
	}

	/**
	 * to get List of TicketRaisingHds from database based on companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/ticketRaising/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TicketRaisingHdDTO> getAllTicketRaisng(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllTicketRaisng...companyId" + companyId);
		Long companyID = Long.parseLong(companyId);
		List<TicketRaisingHD> ticketRaisingHDList = ticketRaisingService.findAllTicketRaising(companyID);
		if (ticketRaisingHDList != null && ticketRaisingHDList.size() > 0)
			return ticketManagementAdaptor.databaseModelTicketRaisingDtoList(ticketRaisingHDList);
		else
			throw new ErrorHandling(" ticketType Data not present");

	}

	/**
	 * to get List of TicketRaisingHds from database based on employeeId
	 */
	@RequestMapping(value = "/empTicketRaising/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<TicketRaisingHdDTO> getTicketRaisng(@PathVariable("employeeId") String employeeId,
			HttpServletRequest req) throws ErrorHandling {
		Long empId = Long.parseLong(employeeId);
		List<TicketRaisingHD> ticketRaisingHDList = ticketRaisingService.findAllEmpTicketRaising(empId);
		if (ticketRaisingHDList != null && ticketRaisingHDList.size() > 0)
			return ticketManagementAdaptor.databaseModelTicketRaisingDtoList(ticketRaisingHDList);
		else
			throw new ErrorHandling(" ticketType Data not present");

	}

	/**
	 * to get TicketRaisingHd Object from database based on ticketRaisingHdId
	 * (Primary Key)
	 */
	@RequestMapping(value = "/ticketRaisingById/{ticketRaisingHDId}", method = RequestMethod.GET)
	public @ResponseBody TicketRaisingHdDTO getTicketRaising(
			@PathVariable("ticketRaisingHDId") String ticketRaisingHDId, HttpServletRequest req) throws ErrorHandling {
		Long ticketRaisingHdId = Long.parseLong(ticketRaisingHDId);
		logger.info("getTicketRaising...ticketRaisingHDId" + ticketRaisingHDId);
		TicketRaisingHD ticketRaisingHD = ticketRaisingService.findTicketRaising(ticketRaisingHdId);
		if (ticketRaisingHD != null)
			return ticketManagementAdaptor.databaseModelToTicketRaisingDto(ticketRaisingHD);
		else
			throw new ErrorHandling(" ticketRaising Data not present");
	}

	/**
	 * Method Performed email sending logic
	 */
	public void triggerEmail(ByteArrayInputStream bis, TicketRaisingHD ticketRaising, String empEmail,
			String supportEmail, TicketRaisingHdDTO ticketRaisingHdDto, String extension, Employee employee,
			TicketType ticketType) {

		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.HELP_DESK_CODE);
		JavaMailSenderImpl mailSender = null;

		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========Help==.getJavaMailSender()===============" + listEmail.toString());
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
			logger.info("===========help==.getJavaMailSender()================" + listEmail.toString());
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
		}
		String desc = null;
		String msg = "Ticket ID: " + ticketRaising.getTicketNo() + "\r\n" + "Ticket Category : "
				+ ((ticketType != null) ? ticketType.getCategory() : " ");
		String msgemp = "Employee Code : " + ((employee != null) ? employee.getEmployeeCode() : " ") + "\r\n"
				+ "Employee Name : "
				+ ((employee != null) ? employee.getFirstName() + " " + employee.getLastName() : " ") + "\r\n"
				+ "Department : "
				+ ((employee != null)
						? ((employee.getDepartment() != null) ? employee.getDepartment().getDepartmentName() : " ")
						: "")
				+ "\r\n";
		/*
		 * + "\r\nTAT:" + ticketType.getTat() + " working days\r\n	";
		 */

		for (TicketDesc ticketdesc : ticketRaising.getTicketDescs()) {
			desc = ticketdesc.getDescription();
		}
		try {

			// mimeMessageHelper.setCc("");
			if (ticketRaisingHdDto.getUser().equals(HrmsGlobalConstantUtil.ESS)) {
				logger.info("ESS USER");
				{
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
					logger.info("supportEmail :" + supportEmail);
					logger.info("SEND MAIL TO ESS");
					mimeMessageHelper.setFrom(listEmail.getFromMail());
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						String toList =  supportEmail + "," +listEmail.getToMail();
						String[] to = toList.split(",");
						mimeMessageHelper.setTo(to);
					} else {
						String toList = supportEmail;
						mimeMessageHelper.setTo(toList);
					}
					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();
						String[] cc = ccList.split(",");
						mimeMessageHelper.setCc(cc);
					}
					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList = listEmail.getBcc();
						String[] bcc = bccList.split(",");
						mimeMessageHelper.setBcc(bcc);
					}
					mimeMessageHelper.setText("Hi Team, " + "\r\n" + "\r\n"
							+ "Thanks for your cooperation. With reference to your logged ticket we are sharing the below information:\r\n"
							+ "\r\n" + msg + "\r\n" + "Description of Ticket : " + desc + "\r\n" + msgemp + "\r\n"
							+ String.format(HrmsGlobalConstantUtil.STRING_FORMAT) + "\r\n" + "The Helpdesk Team");
					if (extension != null) {
						String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE + "." + extension;
						mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(bis)));
					}
					mailSender.send(mimeMessageHelper.getMimeMessage());
				}
//				{
//					MimeMessage mimeMessage = mailSender.createMimeMessage();
//					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//					mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
//					logger.info("SEND MAIL TO FSS");
//					mimeMessageHelper.setFrom(listEmail.getFromMail());
//					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
//						String toList = listEmail.getToMail() + "," + empEmail;
//						String[] to = toList.split(",");
//						mimeMessageHelper.setTo(to);
//					} else {
//						String toList = empEmail;
//						mimeMessageHelper.setTo(toList);
//					}
//					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
//						String ccList = listEmail.getCc();
//						String[] cc = ccList.split(",");
//						mimeMessageHelper.setCc(cc);
//					}
//					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
//						String bccList = listEmail.getBcc();
//						String[] bcc = bccList.split(",");
//						mimeMessageHelper.setBcc(bcc);
//					}
//					mimeMessageHelper.setText("Hi Team, " + "\r\n" + "\r\n"
//							+ "We have received ticket that needs to resolve at earliest. Following are the details of the ticket:\r\n"
//							+ "\r\n" + msg + "\r\n" + "Description of Ticket : " + desc + "\r\n" + msgemp + "\r\n"
//							+ "Thanks & Regards," + "\r\n"
//							+ ((employee != null) ? employee.getFirstName() + " " + employee.getLastName() : " "));
//					if (extension != null) {
//						String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE + "." + extension;
//						mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(bis)));
//					}
//					mailSender.send(mimeMessageHelper.getMimeMessage());
//				}
			}
			if (ticketRaisingHdDto.getUser().equals(HrmsGlobalConstantUtil.FSS)) {
				logger.info("FSS USER");
				{
					logger.info("SEND MAIL TO ESS");
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
					mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
					logger.info("emailTo---- :" + empEmail);

					mimeMessageHelper.setFrom(listEmail.getFromMail());
					if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
						if(empEmail !=null) {
							String toList = listEmail.getToMail()+","+empEmail;
							String[] to = toList.split(",");
							mimeMessageHelper.setTo(to);
						}
						else {
							String toList = listEmail.getToMail();
							mimeMessageHelper.setTo(toList);
						}
					 
						
					} else {
						String toList = empEmail;
						mimeMessageHelper.setTo(toList);
					}
					if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
						String ccList = listEmail.getCc();
						String[] cc = ccList.split(",");
						mimeMessageHelper.setCc(cc);
					}
					if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
						String bccList = listEmail.getBcc();
						String[] bcc = bccList.split(",");
						mimeMessageHelper.setBcc(bcc);
					}

					mimeMessageHelper.setText("Hi " + employee.getFirstName() + " " + employee.getLastName() + ","
							+ "\r\n" + "\r\n"
							+ "Thanks for your cooperation. With reference to your logged ticket we are sharing the below information:"
							+ "\r\n" + "\r\n" + msg + "\r\n" + "Description of Ticket : " + desc + "\r\n" + msgemp
							+ "\r\n" + "Thanks & Regards," + "\r\n" + "The Helpdesk Team");
					if (extension != null) {
						String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE + "." + extension;
						mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(bis)));
					}
					if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
						try {
							mailSender.send(mimeMessageHelper.getMimeMessage());
							logger.info("mail send Successfully");
						} catch (Exception ex) {
							ex.getMessage();
						}
					}
					else
						logger.info("mail send fail");

				}
				/*
				 * { logger.info("SEND MAIL TO FSS"); MimeMessage mimeMessage =
				 * mailSender.createMimeMessage(); MimeMessageHelper mimeMessageHelper = new
				 * MimeMessageHelper(mimeMessage, true);
				 * mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
				 * logger.info("emailTo :" + supportEmail);
				 * 
				 * mimeMessageHelper.setFrom(listEmail.getFromMail()); if (listEmail.getToMail()
				 * != null && !listEmail.getToMail().equals("")) { String toList =
				 * listEmail.getToMail()+","+supportEmail; String[] to = toList.split(",");
				 * mimeMessageHelper.setTo(to); } else { String toList = supportEmail ;
				 * mimeMessageHelper.setTo(toList); } if (listEmail.getCc() != null &&
				 * !listEmail.getCc().equals("")) { String ccList = listEmail.getCc(); String[]
				 * cc = ccList.split(","); mimeMessageHelper.setCc(cc); } if (listEmail.getBcc()
				 * != null && !listEmail.getBcc().equals("")) { String bccList =
				 * listEmail.getBcc(); String[] bcc = bccList.split(",");
				 * mimeMessageHelper.setBcc(bcc); } mimeMessageHelper.setText( "Hi " + "Team," +
				 * "\r\n" + "Ticket has been responded, below is the required information:" +
				 * "\r\n" + "\r\n" + msg + "\r\n" + "Description of Ticket : " + desc + "\r\n" +
				 * msgemp + "\r\n" + "Thanks & Regards," + "\r\n" + "The Helpdesk Team"); if
				 * (extension != null) { String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE +
				 * "." + extension; mimeMessageHelper.addAttachment(fileName, new
				 * ByteArrayResource(IOUtils.toByteArray(bis))); }
				 * 
				 * if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) { try {
				 * mailSender.send(mimeMessageHelper.getMimeMessage());
				 * logger.info("mail send Successfully"); } catch (Exception ex) {
				 * ex.getMessage(); } }
				 * 
				 * }
				 */
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method Performed email sending logic
	 */
	public void triggerEmailNew(ByteArrayInputStream bis, TicketRaisingHD ticketRaising, String empEmail,
			String supportEmail, TicketRaisingHdDTO ticketRaisingHdDto, String extension, Employee employee,
			TicketType ticketType) {

		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.HELP_DESK_CODE);
		JavaMailSenderImpl mailSender = null;

		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========help==.getJavaMailSender()===============" + listEmail.toString());
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
			logger.info("===========help==.getJavaMailSender()================" + listEmail.toString());
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
		}

		String desc = null;
		String msg = "Ticket ID : " + ticketRaising.getTicketNo() + "\r\n" + "Ticket Category : "
				+ ((ticketType != null) ? ticketType.getCategory() : " ");
		String msgemp = "Employee Code : " + ((employee != null) ? employee.getEmployeeCode() : " ") + "\r\n"
				+ "Employee Name : "
				+ ((employee != null) ? employee.getFirstName() + " " + employee.getLastName() : " ") + "\r\n"
				+ "Department : "
				+ ((employee != null)
						? ((employee.getDepartment() != null) ? employee.getDepartment().getDepartmentName() : " ")
						: "")
				+ "\r\n";

		for (TicketDesc ticketdesc : ticketRaising.getTicketDescs()) {
			desc = ticketdesc.getDescription();
		}
		try {
			{
				logger.info("If ESS-  new ticket");
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
				logger.info("emailTo :" + supportEmail);

				mimeMessageHelper.setFrom(listEmail.getFromMail());
				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList =   supportEmail+ "," +listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String toList = supportEmail;
					mimeMessageHelper.setTo(toList);
				}
				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					String[] cc = ccList.split(",");
					mimeMessageHelper.setCc(cc);
				}
				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
					String bccList = listEmail.getBcc();
					String[] bcc = bccList.split(",");
					mimeMessageHelper.setBcc(bcc);
				}

				mimeMessageHelper.setText("Hi Team, " + "\r\n" + "\r\n" +

						"We have received ticket that needs to be resolved at earliest. Following are the details of the ticket:\r\n"
						+ "\r\n" + msg + "\r\n" + "Description of Ticket : " + desc + "\r\n" + msgemp + "\r\n"
						+ "Thanks & Regards," + "\r\n"
						+ ((employee != null) ? employee.getFirstName() + " " + employee.getLastName() : " "));
				if (extension != null) {
					String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE + "." + extension;
					mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(bis)));
				}

				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("mail send Successfully");
					} catch (Exception ex) {
						ex.getMessage();
					}
				}
			}
//			{
//				MimeMessage mimeMessage = mailSender.createMimeMessage();
//				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//				mimeMessageHelper.setSubject(ticketRaising.getTicketNo());
//				mimeMessageHelper.setFrom(listEmail.getFromMail());
//				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
//					String toList = listEmail.getToMail() + "," + empEmail;
//					String[] to = toList.split(",");
//					mimeMessageHelper.setTo(to);
//				} else {
//					String toList = empEmail;
//					mimeMessageHelper.setTo(toList);
//				}
//				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
//					String ccList = listEmail.getCc();
//					String[] cc = ccList.split(",");
//					mimeMessageHelper.setCc(cc);
//				}
//				if (listEmail.getBcc() != null && !listEmail.getBcc().equals("")) {
//					String bccList = listEmail.getBcc();
//					String[] bcc = bccList.split(",");
//					mimeMessageHelper.setBcc(bcc);
//				}
//
//				mimeMessageHelper.setText("Hi " + employee.getFirstName() + " " + employee.getLastName() + "," + "\r\n"
//						+ "\r\n" + "Thanks for getting in touch. Just letting you know that we have received"
//						+ " your ticket and our support Team will get back to you as soon as possible.\r\n"
//						+ "\tFor your reference, here are the details of your ticket:\r\n" + "\r\n" + msg + "\r\n"
//						+ "Description of Ticket : " + desc + "\r\n" + msgemp + "\r\n" + "\r\n" + "Thanks & Regards,"
//						+ "\r\n" + "The Helpdesk Team");
//				if (extension != null) {
//					String fileName = HrmsGlobalConstantUtil.TICKET_ISSUE + "." + extension;
//					mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(bis)));
//				}
//
//				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
//					try {
//						mailSender.send(mimeMessageHelper.getMimeMessage());
//						logger.info("mail send Successfully");
//					} catch (Exception ex) {
//						ex.getMessage();
//					}
//				}
//			}

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * to get List of TicketRaisingHds from database based on companyId and
	 * status=Open and current date
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/ticketRaisingOpen/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TicketRaisingHdDTO> getAllTicketRaisingOpen(@PathVariable("companyId") String companyId,
			HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		logger.info("getAllTicketRaisingOpen is calling : ..companyId :" + companyId);
		Long companyID = Long.parseLong(companyId);
		List<TicketRaisingHD> ticketRaisingHDList = ticketRaisingService.findAllTicketRaisingOpen(companyID);
		if (ticketRaisingHDList != null && ticketRaisingHDList.size() > 0)
			return ticketManagementAdaptor.databaseModelTicketRaisingDtoList(ticketRaisingHDList);
		else
			throw new ErrorHandling("No one open ticket ");

	}

	@RequestMapping(value = "delete/{ticketTypeId}", method = RequestMethod.GET)
	public @ResponseBody int deleteTicketType(@PathVariable("ticketTypeId") String ticketTypeId,
			HttpServletRequest req) {
		logger.info("deleteTicketType is calling :" + "ticketTypeId  ==" + ticketTypeId);
		return ticketTypeService.delete(Long.valueOf(ticketTypeId));
	}
	/*
	 * Shubham yaduwanshi
	 */
	
	@RequestMapping(value = "/searchEntity/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TicketRaisingHdDTO> searchEntity(@PathVariable("employeeId") String employeeId,@RequestBody SearchDTO searchDTO)  {
		    logger.info("searchEntity is calling :"+employeeId);
			Long empId = Long.parseLong(employeeId);
		    List<Object[]> ticketRaisingHDList = ticketRaisingService.getTicketRaisingbyPagination(empId, searchDTO);	
		    logger.info("list size:"+ticketRaisingHDList.size());
			return ticketManagementAdaptor.databaseModelTicketRaisingDtoListWithPagination(ticketRaisingHDList);
	
	}
	
	
	@RequestMapping(value = "/ticketCount/{employeeId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TicketCountDTO getTicketCount(@PathVariable("employeeId") String employeeId,@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) {	
		logger.info("getAllTicketRaisng...companyId" + employeeId+" page size:"+pageSize);
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		TicketCountDTO ticketCountDto = new TicketCountDTO();
		Long empId = Long.parseLong(employeeId);
		List<Object[]> ticketRaisingHDList = ticketRaisingService.getTicketRaisingbyPagination(empId, searcDto);	
        count=ticketRaisingHDList.size();
		logger.info("getAllTicketRaisng...count" + count);

		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		ticketCountDto.setPageIndexs(pageIndexList);
		ticketCountDto.setCount(count);
		logger.info("getAllTicketRaisng...pages" + ticketCountDto.getPageIndexs());

		return ticketCountDto;
	}
	
	/*
	 * Shubham yaduwanshi
	 */
	@RequestMapping(value = "/getTicketDetails/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TicketRaisingHdDTO> getTicketDetails(@PathVariable("companyId") String companyId,@RequestBody SearchDTO searchDTO)  {
		    logger.info("getTicketDetails is calling :"+companyId);
			Long cmpId = Long.parseLong(companyId);
		    List<Object[]> ticketDetailsHDList = ticketRaisingService.getTicketDetailsbyPagination(cmpId, searchDTO);	
		    logger.info("list size:"+ticketDetailsHDList.size());
			return ticketManagementAdaptor.databaseModelTicketDetailsDtoListWithPagination(ticketDetailsHDList);
	}
	
	@RequestMapping(value = "/getTicketCountDetails/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TicketCountDTO getTicketCountDetails(@PathVariable("companyId") String companyId,@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) {	
		logger.info("getTicketCountDetails...companyId" + companyId+" page size:"+pageSize);
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		int parPageRecord = Integer.parseInt(pageSize);
		TicketCountDTO ticketCountDto = new TicketCountDTO();
		Long cmpId = Long.parseLong(companyId);
		List<Object[]> ticketRaisingHDList = ticketRaisingService.getTicketDetailsbyPagination(cmpId, searcDto);	
        count=ticketRaisingHDList.size();
		logger.info("getTicketCountDetails...count" + count);
		System.out.println("Count " + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : " + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		ticketCountDto.setPageIndexs(pageIndexList);
		ticketCountDto.setCount(count);
		logger.info("getTicketCountDetails...pages" + ticketCountDto.getPageIndexs());

		return ticketCountDto;
	}


}
