package com.csipl.hrms.org.controller;

 import java.util.List;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.organisation.CompanyDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.service.adaptor.CompanyAdaptor;
import com.csipl.hrms.service.organization.CompanyService;
import com.csipl.hrms.service.organization.StorageService;

@RestController
@RequestMapping("/company")
public class CompanyController  {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	CompanyAdaptor companyAdaptor = new CompanyAdaptor();

	@Autowired
	CompanyService companyService;
	@Autowired
	StorageService storageService;

	/**
	 * Method performed save operation with file
	 * 
	 * @param file
	 *            This is the first parameter for taking file Input
	 * @param companyDto
	 *            This is the second parameter for getting company Object from UI
	 * @param req
	 *            This is the third parameter to maintain user session
	 */
	@RequestMapping(value = "/file", method = RequestMethod.POST, consumes = "multipart/form-data")
	public void saveCompany(@RequestPart("uploadFile") MultipartFile file, @RequestPart("info") CompanyDTO companyDto) {
		logger.info("saveCompany is calling : " + " : CompanyDTO " + companyDto + ":uploadFile" + file);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!--->"+file.getOriginalFilename());
		MasterBook masterBook = new MasterBook(); 
		masterBook.setPrefixBook(companyDto.getPrefix());
		masterBook.setStartFrom(companyDto.getSeries());
		masterBook.setLastNo(companyDto.getSeries());
 		Company company = companyAdaptor.uiDtoToDatabaseModel(companyDto);
		companyService.save(company, file, true, masterBook);
 	}

	/**
	 * Method performed save operation without any file
	 * 
	 * @param companyDto
	 *            This is the first parameter for getting company Object from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody CompanyDTO companyDto) {
		logger.info("saveCompany is calling : " + " : CompanyDTO " + companyDto);
 		Company company = companyAdaptor.uiDtoToDatabaseModel(companyDto);
		logger.info("Company  : " + company );
		MasterBook masterBook = new MasterBook();
		masterBook.setPrefixBook(companyDto.getPrefix());
		masterBook.setStartFrom(companyDto.getSeries());
		masterBook.setLastNo(companyDto.getSeries());
 		companyService.save(company, null, false, masterBook);
 	}

	/**
	 * to get Company object from database based on companyId (primary key)
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody CompanyDTO getCompany(@PathVariable("companyId") Long companyId) {
		logger.info("saveCompany is calling :companyId "+companyId);
  		Company company = companyService.getCompany(companyId);
 		return companyAdaptor.databaseModelToUiDto(company);
	}

	/**
	 * to get List Company from database based on companyId (primary key)
	 */
	@RequestMapping(value="/{userRoles}/{companyId}",method = RequestMethod.GET)
	public @ResponseBody List<CompanyDTO> findAllCompanys(@PathVariable("userRoles") String userRoles,
			@PathVariable("companyId") Long companyId) throws ErrorHandling {
		logger.info("findAllCompanys is calling : ");
 		List<Company> companyList = companyService.getAllCompanys(companyId, userRoles);
		logger.info("findAllCompanys is end :  companyList" + companyList);
		if (companyList != null && companyList.size() > 0)
			return companyAdaptor.databaseModelToUiDtoList(companyList);
		throw new ErrorHandling("Company data not present");
	}

	 

}
