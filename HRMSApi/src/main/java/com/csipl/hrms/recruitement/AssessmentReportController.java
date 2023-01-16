package com.csipl.hrms.recruitement;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.recruitement.AssessmentService;
import com.csipl.hrms.service.recruitement.adaptor.InterviewSchedulerAdaptor;
import com.csipl.hrms.service.util.AssessmentReportPdf;

@RestController
@RequestMapping("/assessmentReport")
public class AssessmentReportController {

	@Autowired
	private AssessmentService assessmentService;

	@Autowired
	CompanyService companyService;

	@Autowired
	private InterviewSchedulerAdaptor interviewSchedulerAdaptor;

   private static final Logger logger = LoggerFactory.getLogger(AssessmentReportController.class);
	
	/*
	 * Created by shubham yaduwanshi
	 * for generating assessment report card pdf
	 */
	@GetMapping(path = "/downloadPdf/{companyId}/{interviewScheduleId}")
	public StreamingResponseBody reportCard(@PathVariable("companyId") Long companyId, @PathVariable("interviewScheduleId") Long interviewScheduleId,HttpServletRequest req, HttpServletResponse response) throws Exception {
		
        logger.info("reportCard() calling");
        Company company = null;
        List<Object[]> assessmentDetails = assessmentService.findAssessment(companyId, interviewScheduleId);
		List<Object[]> levelList = assessmentService.getLevelList(companyId, interviewScheduleId);
		company = companyService.getCompany(companyId);
		InterviewSchedulerDTO interviewSchedulerDTO= interviewSchedulerAdaptor.databaseModelToInterviewSchedulerUiDto(assessmentDetails, levelList);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"report_card.pdf\"");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		
		ByteArrayInputStream bis =new AssessmentReportPdf().reportCardPdf(interviewSchedulerDTO,company);
		logger.info("reportCard() ending");
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = bis.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};
	}
}
