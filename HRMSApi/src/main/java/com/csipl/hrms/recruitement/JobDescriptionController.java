package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.csipl.hrms.dto.employee.AuthorizedSignatoryDTO;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.recruitment.JobDescriptionDTO;
import com.csipl.hrms.model.recruitment.JobDescription;
import com.csipl.hrms.service.recruitement.JobDescriptionService;
import com.csipl.hrms.service.recruitement.adaptor.JobDescriptionAdaptor;

@RestController
@RequestMapping("/jobDescription")
public class JobDescriptionController {

	private static final Logger logger = LoggerFactory.getLogger(JobDescriptionController.class);

	@Autowired
	private JobDescriptionAdaptor jobDescriptionAdaptor;

	@Autowired
	private JobDescriptionService jobDescriptionService;

	/**
	 * Method performed save operation with file
	 * 
	 */
	@RequestMapping(value = "/uploadJobDescriptionWithFile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public JobDescriptionDTO saveJobDescriptionWithFile(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") JobDescriptionDTO jobDescriptionDTO, HttpServletRequest req) {

		JobDescription jobDescription = jobDescriptionAdaptor.uiDtoToDatabaseModelForFileUpload(jobDescriptionDTO, file,
				true);

		JobDescription jobDescriptionSave = jobDescriptionService.save(jobDescription);

		logger.info("jobDescription is calling : jobDescription " + jobDescriptionDTO + "uploadFile" + file);
		return jobDescriptionAdaptor.databaseModelToUiDto(jobDescriptionSave);

	}

	@PostMapping
	public JobDescriptionDTO save(@RequestBody JobDescriptionDTO jobDescriptionDTO) {
		logger.info("JobDescription is calling : " + " : saveAutho " + jobDescriptionDTO);
		JobDescription jobDescription = jobDescriptionAdaptor.uiDtoToDatabaseModelForFileUpload(jobDescriptionDTO, null,
				false);
		JobDescription jobDescriptionSave = jobDescriptionService.save(jobDescription);
		logger.info("jobDescription is calling : jobDescription " + jobDescriptionDTO );
		return jobDescriptionAdaptor.databaseModelToUiDto(jobDescriptionSave);
	}

	@GetMapping(path = "/getJobDescriptionList")
	public @ResponseBody List<JobDescriptionDTO> getAllJobDescriptionList() throws ErrorHandling {

		List<JobDescription> jobDescriptionList = jobDescriptionService.findAllJobDescription();
		logger.info("getAllJobDescription is end : JobDescription List " + jobDescriptionList);
			return jobDescriptionAdaptor.databaseModelToUiDtoList(jobDescriptionList);

	}

	@GetMapping(path = "/getJobDescriptionById/{jdId}")
	public @ResponseBody JobDescriptionDTO getJobDescriptionById(@PathVariable("jdId") Long jdId) throws ErrorHandling {

		logger.info("getJobDescriptionById is calling :  ");

		JobDescription jobDescriptionList = jobDescriptionService.findJobDescriptionById(jdId);
		return jobDescriptionAdaptor.databaseModelToUiDto(jobDescriptionList);
	}

	@DeleteMapping(value = "/deleteJobDescription/{jdId}")
	public void deleteJobDescriptionById(@PathVariable("jdId") Long jdId) {

		logger.info("deleteJobDescription is calling :" + "jdId" + jdId);

		jobDescriptionService.deleteJobDescriptionById(jdId);
		logger.info("deleteJobDescription is calling Successfully :");
	}

}
