package com.csipl.hrms.service.organization;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.service.adaptor.CompanyAdaptor;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.repository.CompanyRepository;
import com.csipl.hrms.service.report.EmployeeReportService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	CompanyAdaptor companyAdaptor = new CompanyAdaptor();

	@Autowired
	StorageService storageService;

	@Autowired
	EmployeeReportService employeeReportService;
	
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private MasterBookRepository masterBookRepository;
	/**
	 * Method performed fetch operation if admin logged in than all company list fetched otherwise based on companyId
 	 */
	@Override
	public List<Company> getAllCompanys(Long compnyeeId,String userRoles) {
		List<Company> companyList = null;
		boolean flag = false;
		if (userRoles.equals("SuperAdmin")) {

			flag = true;
		}
		if(flag)
			companyList = companyRepository.findAllCompany();
		else
			companyList = companyRepository.findAllCompany(compnyeeId);
		return companyList;
	}
       
	/**
	 * Method performed save operation  
 	 */
	@Override
	public Company save(Company company, MultipartFile file, boolean fileFlag,  MasterBook masterBook) {
		if(fileFlag == false && company.getCompanyId() !=null) {
			System.out.println("!!!!!!!!!!!!!!    if    !!!!!!!!!");
			Company company2 = companyRepository.findOne(company.getCompanyId());
			company.setCompanyLogoPath(company2.getCompanyLogoPath());
			company = companyRepository.save(company);
		}
		
		Company company1 = companyRepository.save(company);
		String fileName = "";
		if (fileFlag) {
//			fileName = "Company_" + company1.getCompanyId().toString();
//			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//			fileName = fileName + "." + extension;
			fileName = file.getOriginalFilename();
			System.out.println("File with extension : " + fileName);
//			String path = File.separator + "images" + File.separator + "companyImages";
			String path = storageService.createFilePath(HrmsGlobalConstantUtil.COMPANY_IMAGES);
			String dbPath = path + File.separator + fileName;
			storageService.store(file, path, fileName);
			company1.setCompanyLogoPath(dbPath);
		}
		EmployeeReportDTO employeeReportDTO =	employeeReportService.countEMPIMPTODAYDATE(company1.getCompanyId(), "");
		
		if(employeeReportDTO.getEmpCount().equals("0"))
		masterBookRepository.updateEmployeeCodePrefix(masterBook.getPrefixBook(), masterBook.getStartFrom(), masterBook.getLastNo(), company1.getCompanyId());
		
		return companyRepository.save(company1);
	}

	/**
	 * to get company information based on companyId(primary key)  
 	 */
	@Override
	public Company getCompany(Long companyId) {
		Company company = companyRepository.findOne(companyId);
		MasterBook masterBook =masterBookRepository.findMasterBook(companyId, "EMPNO");
		company.setSeries(masterBook.getStartFrom());
		company.setPrefix(masterBook.getPrefixBook());
		return company;
	}

}
