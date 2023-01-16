package com.csipl.hrms.service.recruitement.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.GlobalConstantUtils;
import com.csipl.hrms.common.util.StorageUtil;
import com.csipl.hrms.dto.recruitment.JobDescriptionDTO;
import com.csipl.hrms.model.recruitment.JobDescription;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class JobDescriptionAdaptor implements Adaptor<JobDescriptionDTO, JobDescription> {

	StorageUtil storageUtil = new StorageUtil();

	@Override
	public List<JobDescription> uiDtoToDatabaseModelList(List<JobDescriptionDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobDescriptionDTO> databaseModelToUiDtoList(List<JobDescription> jobDescriptionList) {
		// TODO Auto-generated method stub
		return jobDescriptionList.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public JobDescription uiDtoToDatabaseModel(JobDescriptionDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobDescriptionDTO databaseModelToUiDto(JobDescription jobDescription) {
		// TODO Auto-generated method stub

		JobDescriptionDTO jobDescriptionDTO = new JobDescriptionDTO();

		jobDescriptionDTO.setJdId(jobDescription.getJdId());
		jobDescriptionDTO.setJdName(jobDescription.getJdName());
		jobDescriptionDTO.setJdFile(jobDescription.getJdFile());
		jobDescriptionDTO.setUserId(jobDescription.getUserId());
		jobDescriptionDTO.setUserIdUpdate(jobDescription.getUserIdUpdate());
		jobDescriptionDTO.setDateCreated(jobDescription.getDateCreated());
		jobDescriptionDTO.setUpdatedDate(jobDescription.getUpdatedDate());

		String dbPath=jobDescription.getJdFile();
		String[] path = dbPath.split("/");
		String jdFileName= path[2] ;
		
		jobDescriptionDTO.setJdFileName(jdFileName);
		return jobDescriptionDTO;

	}

	public JobDescription uiDtoToDatabaseModelForFileUpload(JobDescriptionDTO jobDescriptionDTO,
			MultipartFile multipartFile, boolean isFile) {
		// TODO Auto-generated method stub
		JobDescription jobDescription = new JobDescription();

		jobDescription.setJdId(jobDescriptionDTO.getJdId());
		jobDescription.setJdName(jobDescriptionDTO.getJdName());
		jobDescription.setJdFile(jobDescriptionDTO.getJdFile());
		jobDescription.setUserId(jobDescriptionDTO.getUserId());
		jobDescription.setUserIdUpdate(jobDescriptionDTO.getUserIdUpdate());
		jobDescription.setDateCreated(jobDescriptionDTO.getDateCreated());
		jobDescription.setUpdatedDate(jobDescriptionDTO.getUpdatedDate());
		if (isFile == false && jobDescription.getJdId() != null) {
			jobDescription.setJdFile(jobDescription.getJdFile());
		}

		String fileName = "";
		if (isFile) {
			fileName = jobDescriptionDTO.getJdName() + "_PDF_";
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			fileName = fileName + "." + extension;

			String path = GlobalConstantUtils.custom_separateor + "docfiles" + GlobalConstantUtils.custom_separateor
					+ "document" + fileName;
			String dbPath = path + GlobalConstantUtils.custom_separateor + fileName;
			storageUtil.store(multipartFile, path, fileName);
			jobDescription.setJdFile(dbPath);
		}

		return jobDescription;
	}

}
