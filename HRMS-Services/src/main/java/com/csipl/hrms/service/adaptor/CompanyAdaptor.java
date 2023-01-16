package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.organisation.CompanyDTO;
import com.csipl.hrms.model.common.Company;

public class CompanyAdaptor implements Adaptor<CompanyDTO, Company> {

	AddressAdaptor addressAdaptor = new AddressAdaptor();

	@Override
	public List<Company> uiDtoToDatabaseModelList(List<CompanyDTO> companyDtoList) {
		List<Company> companyList = new ArrayList<Company>();
		for (CompanyDTO companyDto : companyDtoList) {
			companyList.add(uiDtoToDatabaseModel(companyDto));
		}
		return companyList;
	}

	@Override
	public List<CompanyDTO> databaseModelToUiDtoList(List<Company> companyList) {
		List<CompanyDTO> companyDtoList = new ArrayList<CompanyDTO>();
		for (Company company : companyList) {
			companyDtoList.add(databaseModelToUiDto(company));
		}
		return companyDtoList;
	}

	@Override
	public Company uiDtoToDatabaseModel(CompanyDTO companyDto) {

		Company company = new Company();

		if (companyDto != null) {
			company.setUserId(companyDto.getUserId());
			if (companyDto.getCompanyId() != null) {
				company.setCompanyId(companyDto.getCompanyId());
			}
			company.setCompanyName(companyDto.getCompanyName());
			/*Groupg groupg = new Groupg();
			if (companyDto.getGroupId() != null) {
				groupg.setGroupId(companyDto.getGroupId());
			} else {
				groupg.setGroupId(1l);
				company.setGroupg(groupg); // comment for future enhancement

			}

			groupg.setGroupName(companyDto.getGroupName());
			company.setGroupg(groupg);*/
			company.setAddress1(addressAdaptor.uiDtoToDatabaseModel(companyDto.getAddress1()));
		//	company.setAddress2(addressAdaptor.uiDtoToDatabaseModel(companyDto.getAddress2()));
			company.setRetirementAge(companyDto.getRetirementAge());
			company.setPanNo(companyDto.getPanNo());
			company.setRegistrationNo(companyDto.getRegistrationNo());
			company.setEpfNo(companyDto.getEpfNo());
			company.setEsicNo(companyDto.getEsicNo());
			company.setGstNo(companyDto.getGstNo());
			company.setDateOfBirth(companyDto.getDateOfBirth());
			company.setCompanyLogoPath(companyDto.getCompanyLogoPath());
			company.setDateUpdate(new Date());
			company.setUserIdUpdate(companyDto.getUserIdUpdate());
			company.setDomainName(companyDto.getDomainName());
			
			
 
 			company.setActiveStatus(companyDto.getActiveStatus());
			company.setAuthorizedPerson(companyDto.getAuthorizedPerson());
			company.setCompanyAbbreviation(companyDto.getCompanyAbbreviation());
 			company.setEffectiveStartDate(companyDto.getEffectiveStartDate());
			company.setEffectiveEndDate(companyDto.getEffectiveEndDate());
			company.setEmailId(companyDto.getEmailId());
			company.setGumastaNo(companyDto.getGumastaNo());
			company.setImportExportCode(companyDto.getImportExportCode());
			company.setMobile(companyDto.getMobile());
			company.setNagarnigamNo(companyDto.getNagarnigamNo());
			company.setTanNo(companyDto.getTanNo());
			company.setTypeOfIndustry(companyDto.getTypeOfIndustry());
			company.setWebsite(companyDto.getWebsite());
		   	
			
			
			
			

		}

		if (company.getCompanyId() == null)
			company.setDateCreated(new Date());
		else
			company.setDateCreated(companyDto.getDateCreated());

		return company;
	}

	@Override
	public CompanyDTO databaseModelToUiDto(Company company) {

		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setCompanyId(company.getCompanyId());
		companyDto.setCompanyName(company.getCompanyName());

		if (company.getGroupg() != null) {
			companyDto.setGroupId(company.getGroupg().getGroupId());
			companyDto.setGroupName(company.getGroupg().getGroupName());
		}

		if (company.getAddress1() != null)
			companyDto.setAddress1(addressAdaptor.databaseModelToUiDto(company.getAddress1()));

	/*	if (company.getAddress2() != null)
			companyDto.setAddress2(addressAdaptor.databaseModelToUiDto(company.getAddress2()));*/

		companyDto.setRetirementAge(company.getRetirementAge());
		companyDto.setPanNo(company.getPanNo());
		companyDto.setRegistrationNo(company.getRegistrationNo());
		companyDto.setEpfNo(company.getEpfNo());
		companyDto.setEsicNo(company.getEsicNo());
		companyDto.setGstNo(company.getGstNo());
		companyDto.setUserId(company.getUserId());
		companyDto.setDateCreated(company.getDateCreated());
		companyDto.setDateOfBirth(company.getDateOfBirth());
		companyDto.setCompanyLogoPath(company.getCompanyLogoPath());
		companyDto.setDomainName(company.getDomainName());
	   	companyDto.setActiveStatus(company.getActiveStatus());
	   	companyDto.setAuthorizedPerson(company.getAuthorizedPerson());
	   	companyDto.setCompanyAbbreviation(company.getCompanyAbbreviation());
	   	companyDto.setDateUpdate(company.getDateUpdate());
	   	companyDto.setEffectiveStartDate(company.getEffectiveStartDate());
	   	companyDto.setEffectiveEndDate(company.getEffectiveEndDate());
	   	companyDto.setEmailId(company.getEmailId());
	   	companyDto.setGumastaNo(company.getGumastaNo());
	   	companyDto.setImportExportCode(company.getImportExportCode());
	   	companyDto.setMobile(company.getMobile());
	   	companyDto.setNagarnigamNo(company.getNagarnigamNo());
	   	companyDto.setTanNo(company.getTanNo());
	   	companyDto.setTypeOfIndustry(company.getTypeOfIndustry());
	   	companyDto.setWebsite(company.getWebsite());
	   	companyDto.setSeries(company.getSeries());
	   	companyDto.setPrefix(company.getPrefix());
	   	
	   	
 		return companyDto;
	}
}
