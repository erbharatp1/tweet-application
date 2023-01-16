package com.csipl.hrms.service.employee;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.model.employee.AuthorizedSignatory;

/**
 * 
 * @author Bharat
 *
 */
public interface AuthorizedSignatoryService {

	AuthorizedSignatory save(AuthorizedSignatory authorizedSignatory, MultipartFile file, boolean b);

	AuthorizedSignatory findAuthorizedSignatoryById(Long letterId);

}
