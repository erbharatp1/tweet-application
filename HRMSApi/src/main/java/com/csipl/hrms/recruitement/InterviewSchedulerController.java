package com.csipl.hrms.recruitement;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.common.model.EmailConfiguration;
import com.csipl.common.model.EmailNotificationMaster;
import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.GlobalConstantUtils;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.recruitment.CandidateEvolutionDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.recruitment.CandidateEvolution;
import com.csipl.hrms.model.recruitment.CandidateLetterTemplateMaster;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.JobDescription;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;
import com.csipl.hrms.service.common.EmailNotificationService;
import com.csipl.hrms.service.common.repository.EmailConfigurationRepository;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.recruitement.CandidateLetterTemplateMasterService;
import com.csipl.hrms.service.recruitement.InterviewSchedulerService;
import com.csipl.hrms.service.recruitement.JobDescriptionService;
import com.csipl.hrms.service.recruitement.PositionService;
import com.csipl.hrms.service.recruitement.adaptor.InterviewSchedulerAdaptor;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
@RequestMapping("/interviewScheduler")
public class InterviewSchedulerController {

	private static final Logger logger = LoggerFactory.getLogger(InterviewSchedulerController.class);

	@Autowired
	private InterviewSchedulerService interviewSchedulerService;

	@Autowired
	private InterviewSchedulerAdaptor interviewSchedulerAdaptor;

	@Autowired
	private PositionService positionService;

	@Autowired
	EmailConfigurationRepository emailConfugrationRepository;

	@Autowired
	EmailNotificationService emailNotificationService;
	
	@Autowired
	private JobDescriptionService jobDescriptionService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	CandidateLetterTemplateMasterService candidateLetterTemplateMasterService;
	
	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;
	

	public static final String ENCODING = "UTF-8";

	@PostMapping(path = "/saveInterviewScheduler")
	public void save(@RequestBody InterviewSchedulerDTO interviewSchedulerDTO) {
		logger.info("save is calling : interviewSchedulerDTO " + interviewSchedulerDTO);
		Long levelId = null;
	
		if (interviewSchedulerDTO.getInterviewScheduleId() != null && interviewSchedulerDTO.getInterviewScheduleId() != 0) {
			Long interviewScheduleId = interviewSchedulerDTO.getInterviewScheduleId();
			InterviewScheduler interviewSchedule = interviewSchedulerService.findInterviewScheduleById(interviewScheduleId);

			InterviewScheduler interviewScheduler = interviewSchedulerAdaptor.uiDtoToDatabaseModels(interviewSchedulerDTO,interviewSchedule);
			interviewSchedulerService.save(interviewScheduler);
			logger.info("save is calling :  Done");
			triggerEmailNew(interviewScheduler,interviewSchedulerDTO);
			}

		else {
			List<PositionInterviewlevelXRef> positionInterviewlevelXRefList	=interviewSchedulerService.findlevelsByPositionId(interviewSchedulerDTO.getPositionId());
			InterviewScheduler interviewScheduler = interviewSchedulerAdaptor.uiDtoToDatabaseModels1(interviewSchedulerDTO, levelId,positionInterviewlevelXRefList);
			interviewSchedulerService.save(interviewScheduler);
			logger.info("save is calling :  Done");
			triggerEmailNew(interviewScheduler,interviewSchedulerDTO);
		}

	}

	private void triggerEmailNew(InterviewScheduler interviewScheduler, InterviewSchedulerDTO interviewSchedulerDTO) {
		logger.info("triggerEmailNew start ");
		EmailConfiguration confugration = null;
		confugration = emailConfugrationRepository.findEMail();
		
		String CandidateName = interviewScheduler.getCandidateName();
		String CandidateEmailId = interviewScheduler.getCandidateEmailId();
		Long positionId=interviewScheduler.getPosition().getPositionId();
		
		Long employeeId=interviewScheduler.getRecuiterId();
		Long interviewLevelId=null;
		
		for(CandidateEvolutionDTO candidateEvolutionDTO:interviewSchedulerDTO.getCandidateEvolutionList())
			{
			interviewLevelId=candidateEvolutionDTO.getLevelId();
			}
		
		
		Date interviewDate = null;
		 String interviewTime = null;  String interviewMode = null ;
	
		if (interviewScheduler.getCandidateEvolution() != null) {
			for (CandidateEvolution candidateEvolution : interviewScheduler.getCandidateEvolution()) {
				if (candidateEvolution.getPositionInterviewlevelXRef().getLevelId().equals(interviewLevelId)) {
					interviewDate = candidateEvolution.getInterviewDate();
					interviewMode = candidateEvolution.getInterviewMode();
					interviewTime = candidateEvolution.getInterviewTime();
				
					
				}
			}
		}
		
//		List<Object[]> LevelList =interviewSchedulerService.getLevelList(interviewScheduleId,interviewLevelId);

		List<PositionInterviewlevelXRef> positionInterviewlevelXRefList	=interviewSchedulerService.findlevelsByPositionId(positionId);
		 String positionTitle = null;
		 String positionCode=null;  	 String levelName = null;   String levelIndex = null; 
		for(PositionInterviewlevelXRef positionInterviewlevelXRef:positionInterviewlevelXRefList) {
		
			positionCode=positionInterviewlevelXRef.getPosition().getPositionCode();
			positionTitle =positionInterviewlevelXRef.getPosition().getPositionTitle();
//			levelIndex=positionInterviewlevelXRef.getLevelIndex();
//			levelName=positionInterviewlevelXRef.getLevelName();
		
		}
		
		String templateFlag="ISF";
		
		CandidateLetterTemplateMaster candidateLetterTemplateMaster = candidateLetterTemplateMasterService.getCandidateLetterByTemplateType(templateFlag);
		Employee employee = employeePersonalInformationService.findEmployeesById(employeeId);
		
		Position position=positionService.findPositionById(positionId);
		
		JobDescription jobDescriptionList = jobDescriptionService.findJobDescriptionById(position.getJdId());
		
		String path = environment.getProperty("application.companyLogoPath");
		
		Company company = companyService.getCompany(employee.getCompany().getCompanyId());
		String companyLogoPath = path + company.getCompanyLogoPath();
		String companyName=employee.getCompany().getCompanyName();
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
		String currentDT = sm.format(new Date());
		if (candidateLetterTemplateMaster != null) {
			sb.append(candidateLetterTemplateMaster.getLetterDescription().replace("@Candidate_Name@", CandidateName)
					.replace("@Company_Name@", companyName)
					.replace("@Position_Title@", positionTitle)
					.replace("@Position_Code@", positionCode)
					.replace("@Interview_Mode@", interviewMode)
					.replace("@Interview_Date_And_Time@", interviewDate +  " :" + interviewTime)
					.replace("@Hiring_SPOC@", employee.getFirstName() +  " " + employee.getLastName()));

		}


		EmailNotificationMaster listEmail = emailNotificationService.findEMailListByStatus(StatusMessage.LETTER_CODE);
		JavaMailSenderImpl mailSender = null;

		mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		if (confugration.getHost().equals(StatusMessage.HOST_NAME_SMTP)) {
			logger.info("===========Interview Schedule==.getJavaMailSender()===============" + listEmail.toString());
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
			logger.info("===========Interview Schedule==.getJavaMailSender()================" + listEmail.toString());
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
				mimeMessageHelper.setSubject("Interview Schedule");
				mimeMessageHelper.setFrom(listEmail.getFromMail());
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("CandidateName", sb.toString());
				model.put("companyLogoPath", companyLogoPath);
				model.put("companyName", companyName);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"/templates/interview_schedule_.vm", "UTF-8", model);
				mimeMessageHelper.setFrom(listEmail.getFromMail());
				mimeMessageHelper.setSubject("Interview Schedule");

				if (listEmail.getToMail() != null && !listEmail.getToMail().equals("")) {
					String toList = CandidateEmailId + "," + listEmail.getToMail();
					String[] to = toList.split(",");
					mimeMessageHelper.setTo(to);
				} else {
					String to = CandidateEmailId;
					mimeMessageHelper.setTo(to);
				}
				if (listEmail.getCc() != null && !listEmail.getCc().equals("")) {
					String ccList = listEmail.getCc();
					String[] cc1 = ccList.split(",");
					mimeMessageHelper.setCc(cc1);
				}

				mimeMessageHelper.setText(text, true);
				
				if (candidateLetterTemplateMaster.getJobDescription().equals("Y")) {

					String rootPath = System.getProperty("catalina.home");

					rootPath = rootPath + GlobalConstantUtils.custom_separateor + HrmsGlobalConstantUtil.APP_BASE_FOLDER
							+ jobDescriptionList.getJdFile();

					logger.info("rootPath is :" + rootPath);

					try {
							File file = new File(rootPath);
//							byte[] bytesArray = new byte[(int) file.length()];
//							ByteArrayInputStream bis = new ByteArrayInputStream(bytesArray);
							  logger.info( "length: "+ file.length());
							//mimeMessageHelper.addAttachment("attachment", new ByteArrayResource(IOUtils.toByteArray(bis)));
							mimeMessageHelper.addAttachment("Job_Description_Attachment", file);
						    logger.info("Added a file atachment: {}", jobDescriptionList.getJdName());
						
					} catch (MessagingException ex) {
						logger.info("Failed to add a file atachment: {}", jobDescriptionList.getJdName(), ex);
					}

				}
				
				
				if (listEmail.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
					try {
						mailSender.send(mimeMessageHelper.getMimeMessage());
						logger.info("Interview Schedule mail send Successfully");
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("Interview Schedule  mail send Failed");
					}
				}

		
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	}

	
	@GetMapping(value = "/getInterviewScheduleById/{interviewScheduleId}/{levelId}")
	public @ResponseBody InterviewSchedulerDTO getInterviewScheduleById(
			@PathVariable("interviewScheduleId") Long interviewScheduleId,@PathVariable("levelId") Long levelId, HttpServletRequest req) {
		logger.info("InterviewSchedulerController.getInterviewScheduleById()");

		InterviewScheduler interviewScheduler  =interviewSchedulerService.findInterviewScheduleById(interviewScheduleId);
		return interviewSchedulerAdaptor.databaseModelToInterviewScheduleUiDto(interviewScheduler,levelId);
	}
	
	
	
	
	@PutMapping(value = "/updateByInterviewScheduler")
	public void updateByInterviewScheduler(@RequestBody InterviewSchedulerDTO interviewSchedulerDTO, HttpServletRequest req) {
		logger.info(" activeStatusupdate is calling :  " + interviewSchedulerDTO);

		InterviewScheduler interviewScheduler = new InterviewScheduler();
		
		interviewScheduler.setInterviewScheduleId(interviewSchedulerDTO.getInterviewScheduleId());
		interviewScheduler.setActiveStatus(interviewSchedulerDTO.getActiveStatus());
		interviewScheduler.setDeclineRemark(interviewSchedulerDTO.getDeclineRemark());
		interviewSchedulerService.updateByInterviewScheduler(interviewScheduler);

	}

	@RequestMapping(value = "/findAllInterviewSchedulerDetails/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<InterviewSchedulerDTO> getInterviewSchedulerDetails(@PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto) throws PayRollProcessException {

		logger.info("InterviewSchedulerController.getInterviewSchedulerDetails()");

		List<Object[]> interviewSchedulerList = interviewSchedulerService.getInterviewSchedulerDetailsList(companyId,searcDto);
		List<Object[]> candidateEvolutionList=interviewSchedulerService.getCandidateEvolutionList();
		return interviewSchedulerAdaptor.databaseModelToUiDtoLists(interviewSchedulerList,candidateEvolutionList);

	}

	@RequestMapping(value = "/interviewScheduleCount/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getInterviewScheduleCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();

		List<Object[]> interviewSchedulerList = interviewSchedulerService.getInterviewSchedulerDetailsList(companyId,searcDto);

		count = interviewSchedulerList.size();

		System.out.println("getInterviewScheduleCount :" + count);

		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}

	

}
