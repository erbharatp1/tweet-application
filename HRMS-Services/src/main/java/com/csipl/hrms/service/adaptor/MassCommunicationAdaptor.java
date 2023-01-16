package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.csipl.hrms.common.enums.ActiveStatusEnum;
import com.csipl.hrms.dto.employee.CompanyAnnouncementDTO;
import com.csipl.hrms.dto.employee.MassCommunicationDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.CompanyAnnouncement;
import com.csipl.hrms.model.employee.MassCommunication;
import com.csipl.hrms.model.organisation.Department;

public class MassCommunicationAdaptor implements Adaptor<MassCommunicationDTO, MassCommunication> {

	@Override
	public List<MassCommunication> uiDtoToDatabaseModelList(List<MassCommunicationDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	public List<CompanyAnnouncement> uiDtoToDatabaseModelListCompanyAnnouncement(List<CompanyAnnouncementDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(item -> uiDtoToDatabaseModelCompanyAnnouncement(item)).collect(Collectors.toList());
	}

	@Override
	public List<MassCommunicationDTO> databaseModelToUiDtoList(List<MassCommunication> massCommunicationlist) {

		List<MassCommunicationDTO> massCommunicationDtoList = new ArrayList<MassCommunicationDTO>();
		for (MassCommunication massCommunication : massCommunicationlist) {
			massCommunicationDtoList.add(databaseModelToUiDto(massCommunication));
		}
		return massCommunicationDtoList;
	}

	@Override
	public MassCommunication uiDtoToDatabaseModel(MassCommunicationDTO massCommunicationDto) {
		MassCommunication massCommunication = new MassCommunication();

		massCommunication.setMassCommunicationId(massCommunicationDto.getMassCommunicationId());
		massCommunication.setTitle(massCommunicationDto.getTitle());
//		for (Long ids : massCommunicationDto.getDepartmentId()) {
//			Department department = new Department();
//			department.setDepartmentId(ids);
//			massCommunication.setDepartment(department);
//		}

		massCommunication.setDateFrom(massCommunicationDto.getDateFrom());
		massCommunication.setDateTo(massCommunicationDto.getDateTo());
		massCommunication.setDescription(massCommunicationDto.getDescription());
		massCommunication.setUserId(massCommunicationDto.getUserId());
		massCommunication.setDateCreated(massCommunicationDto.getDateCreated());
		massCommunication.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());

		// massCommunication.setCompanyAnnouncement(uiDtoToDatabaseModelListCompanyAnnouncement(uiDtoToDatabaseModelCompanyAnnouncement(announcement))
		List<CompanyAnnouncement> list = new ArrayList<>();
		List<Long> deptId = new ArrayList<Long>();
		deptId = massCommunicationDto.getDepartmentId().stream().distinct().collect(Collectors.toList());
		for (Long ids : deptId) {
			CompanyAnnouncement announcement = new CompanyAnnouncement();
			announcement.setActiveStatus("AC");
			announcement.setUserId(massCommunicationDto.getUserId());
			Department department = new Department();
			department.setDepartmentId(ids);
			announcement.setDepartment(department);
			announcement.setUserId(massCommunicationDto.getUserId());
			announcement.setDateCreated(massCommunicationDto.getDateCreated());
			announcement.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());

			announcement.setMassCommunication(massCommunication);
			// Id(massCommunicationDto.getMassCommunicationId());
			if (massCommunicationDto.getDateCreated() == null)
				announcement.setDateCreated(new Date());
			else
				announcement.setDateCreated(massCommunicationDto.getDateCreated());
			announcement.setUserIdUpdate(massCommunicationDto.getUserIdUpdate());
			announcement.setDateUpdate(new Date());

			list.add(announcement);
		}
		Company company = new Company();
		company.setCompanyId(massCommunicationDto.getCompanyId());
		massCommunication.setCompany(company);
		massCommunication.setCompanyAnnouncements(list);
		if (massCommunicationDto.getDateCreated() == null)
			massCommunication.setDateCreated(new Date());
		else
			massCommunication.setDateCreated(massCommunicationDto.getDateCreated());
		massCommunication.setUserIdUpdate(massCommunicationDto.getUserIdUpdate());
		massCommunication.setDateUpdate(new Date());
		return massCommunication;
	}

	@Override
	public MassCommunicationDTO databaseModelToUiDto(MassCommunication massCommunication) {
		MassCommunicationDTO massCommunicationDto = new MassCommunicationDTO();
		List<Long> deptIds = new ArrayList<Long>();
		for (CompanyAnnouncement object : massCommunication.getCompanyAnnouncements()) {
			deptIds.add(object.getDepartment().getDepartmentId());
			massCommunicationDto.setDeptId(object.getDepartment().getDepartmentId());
		}

		massCommunicationDto.setDepartmentId(deptIds);
		massCommunicationDto.setMassCommunicationId(massCommunication.getMassCommunicationId());
		massCommunicationDto.setTitle(massCommunication.getTitle());

		massCommunicationDto.setDateFrom(massCommunication.getDateFrom());
		massCommunicationDto.setDateTo(massCommunication.getDateTo());
		massCommunicationDto.setDescription(massCommunication.getDescription());
		massCommunicationDto.setUserId(massCommunication.getUserId());
		massCommunicationDto.setDateCreated(massCommunication.getDateCreated());
		massCommunicationDto.setDepartmentId(deptIds);
		return massCommunicationDto;
	}

	public CompanyAnnouncement uiDtoToDatabaseModelCompanyAnnouncement(CompanyAnnouncementDTO announcementDTO) {
		CompanyAnnouncement companyAnnouncement = new CompanyAnnouncement();

		companyAnnouncement.setCompanyAnnouncementId(announcementDTO.getCompanyAnnouncementId());
		MassCommunication communication = new MassCommunication();
		communication.setMassCommunicationId(announcementDTO.getMassCommunicationId());
		companyAnnouncement.setMassCommunication(communication);
		for (Long ids : announcementDTO.getDepartmentId()) {
			Department department = new Department();
			department.setDepartmentId(ids);
			companyAnnouncement.setDepartment(department);
		}
		companyAnnouncement.setUserId(announcementDTO.getUserId());
		companyAnnouncement.setDateCreated(announcementDTO.getDateCreated());
		companyAnnouncement.setActiveStatus(ActiveStatusEnum.ActiveStatus.getActiveStatus());

		// companyAnnouncement.set(announcementDTO.getCompanyId());
		if (announcementDTO.getDateCreated() == null)
			companyAnnouncement.setDateCreated(new Date());
		else
			companyAnnouncement.setDateCreated(announcementDTO.getDateCreated());
		companyAnnouncement.setUserIdUpdate(announcementDTO.getUserIdUpdate());
		companyAnnouncement.setDateUpdate(new Date());
		return companyAnnouncement;
	}

	public CompanyAnnouncementDTO databaseModelToUiDtoCompanyAnnouncement(CompanyAnnouncement companyAnnouncement) {
		CompanyAnnouncementDTO massCommunicationDto = new CompanyAnnouncementDTO();
		List<Long> deptIds = new ArrayList<Long>();
		deptIds.add(companyAnnouncement.getDepartment().getDepartmentId());
		massCommunicationDto.setCompanyAnnouncementId(companyAnnouncement.getCompanyAnnouncementId());
		massCommunicationDto
				.setMassCommunicationId(companyAnnouncement.getMassCommunication().getMassCommunicationId());
		// massCommunicationDto.setMassCommunicationId(massCommunication.getMassCommunicationId());
		// massCommunicationDto.setTitle(massCommunication.getTitle());
		massCommunicationDto.setDeptId(companyAnnouncement.getDepartment().getDepartmentId());
		massCommunicationDto.setDepartmentName(companyAnnouncement.getDepartment().getDepartmentName());
		// massCommunicationDto.setDateFrom(massCommunication.getDateFrom());
		// massCommunicationDto.setDateTo(massCommunication.getDateTo());
		// massCommunicationDto.setDescription(massCommunication.getDescription());
		massCommunicationDto.setUserId(companyAnnouncement.getUserId());
		massCommunicationDto.setDateCreated(companyAnnouncement.getDateCreated());
		massCommunicationDto.setDepartmentId(deptIds);
		return massCommunicationDto;
	}

	public List<MassCommunicationDTO> databaseModelToDtoList(List<Object[]> massCommunicationList) {

		List<MassCommunicationDTO> massCommunicationDTOList = new ArrayList<MassCommunicationDTO>();

		for (Object[] obj : massCommunicationList) {

			MassCommunicationDTO massCommunicationDTO = new MassCommunicationDTO();

			Long massCommunicationId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			String title = obj[1] != null ? (String) obj[1] : null;
			String description = obj[2] != null ? (String) obj[2] : null;
			Date dateFrom = obj[3] != null ? (Date) obj[3] : null;
			Date dateTo = obj[4] != null ? (Date) obj[4] : null;

			massCommunicationDTO.setMassCommunicationId(massCommunicationId);
			massCommunicationDTO.setTitle(title);
			massCommunicationDTO.setDescription(description);
			massCommunicationDTO.setDateFrom(dateFrom);
			massCommunicationDTO.setDateTo(dateTo);

			massCommunicationDTOList.add(massCommunicationDTO);

		}
		return massCommunicationDTOList;
	}

}
