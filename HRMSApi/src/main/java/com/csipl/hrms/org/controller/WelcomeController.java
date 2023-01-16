package com.csipl.hrms.org.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


import com.csipl.common.services.permission.RolesPermissionServices;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.config.JwtTokenUtil;
import com.csipl.hrms.config.TokenProvider;
import com.csipl.hrms.dto.organisation.UserDTO;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.users.UserService;
import com.csipl.hrms.util.UserAccessHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Welcome")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WelcomeController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	boolean status = false;

	@Autowired
	LoginService loginService;

	@Autowired
	UserService userService;

//	@Autowired
//	LoginRepository loginRepository;

	@Autowired
	RolesPermissionServices rolesPermissionServices;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;

	@Autowired
	Environment env;

	@Autowired
	DomainNameScheduler domainNameScheduler;

	/*@Resource(name = "tokenStore")
	TokenStore tokenStore;*/
	
	 @Autowired
     private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(path = "/log", method = RequestMethod.POST)
	public @ResponseBody void welcome22(HttpServletRequest req) {
		logger.info("Hi Sir Welcone ");
	}

	@RequestMapping(path = "/log1", method = RequestMethod.POST)
	public @ResponseBody void welcome11(@RequestParam("id") String id, HttpServletRequest req) {
		logger.info("Hi Sir Welcone ");
	}

//	@RequestMapping(path = "/rolesPermission", method = RequestMethod.GET)
//	public @ResponseBody List<RolesMenuDto> systemPermission() {
//		logger.info("======systemPermission===============");
//		return rolesPermissionServices.getAllRolesPermission();
//	}

	@RequestMapping(path = "/userLogOut", method = RequestMethod.GET)
	public @ResponseBody Boolean userLogOut(HttpServletRequest req) {
		logger.info("Hi Sir Welcone ");
		HttpSession session = req.getSession();
		session.setAttribute("User", null);
		session.invalidate();

		return true;
	}

	@RequestMapping(path = "/loginActiveDeactiveStatus/{activeDeactiveStatus}/{employeeCode}", method = RequestMethod.GET)
	public int loginActiveDeactiveStatus(@PathVariable String activeDeactiveStatus, @PathVariable String employeeCode,
			HttpServletRequest req) {
		int update = userService.loginActiveDeactiveStatus(activeDeactiveStatus, employeeCode);
		return update;
	}

	@RequestMapping(path = "/checkLoginActiveDeactive", method = RequestMethod.POST)
	public @ResponseBody UserDTO checkLoginActiveDeactive(@RequestBody UserDTO userForm, HttpServletRequest req)
			throws SQLException {

		String username = userForm.getUsername();
		UserDTO userDTO = new UserDTO();

		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		List<String> domainNameLis = domainNameScheduler.domainNameList;

		if (domainNameLis.contains(tenantId)) {
			System.out.println("tenantId======== " + tenantId);

			if ((username != null && (!username.trim().equals("")))) {

				User user = new User();
				String name[] = username.split("-");

				if (username.contains("-")) {

					if (name[0].equals("Administrator")) {
						user = loginService.findUserByUserName(name[0].trim());
					} else {
						user = loginService.findUserByUserName(username.trim());
					}
				}
				if (user.getActiveStatus() != null && user.getActiveStatus().equalsIgnoreCase("AC")) {
					userDTO.setCompanyId(user.getCompany().getCompanyId());
					userDTO.setUserIdUpadate(user.getUserId());
					userDTO.setUserId(user.getUserId());
					userDTO.setNameOfUser(user.getNameOfUser());
					userDTO.setActiveStatus(user.getActiveStatus());
					userDTO.setMessage("success");
				} else {
					userDTO.setActiveStatus("DE");
					userDTO.setMessage("Error! Account Deactivated");
				}

			}
		} else {
			userDTO.setActiveStatus("DE");
			userDTO.setMessage("Error! subscription expired");
		}
		return userDTO;

	}

	@ApiOperation(value = "welcome", response = ErrorHandling.class, notes = "User login authentication", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public @ResponseBody UserDTO welcome1(@RequestBody UserDTO userForm, HttpServletRequest req) throws SQLException {

		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		Long MAX_ATTEMPTS = 0L;
		final int ATTEMPTS = 99;
		// HttpHeaders headers = new HttpHeaders();
		ErrorHandling error = new ErrorHandling();
		HttpSession session = req.getSession(true);
		UserDTO userDTO = new UserDTO();

		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		List<String> domainNameLis = domainNameScheduler.domainNameList;

//		List<String> domainNameLis =DataBaseConnection.getCustomerResgistrationData(env);

		if (domainNameLis.contains(tenantId)) {
			System.out.println("tenantId======== " + tenantId);

			// logger.info("Login Attentication user name " + userForm.getUsername());
			// logger.info("Login Attentication user password " + userForm.getPassword());
			try {
				 String name[] = userForm.getUsername().split("-");
				 String userName =null ;
				 if (name[0].equals("Administrator")) {
					 userName = name[0].trim();
				 }
				 else {
					 userName =userForm.getUsername().trim();
				 }
                final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,(userForm.getPassword()).trim()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch(Exception e ) {
				userDTO.setMessage("Error! Invalid credentials");
				logger.info("Please Check your UserName and Password ");
			}
			
                        //  final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForm.getUsername().trim(),(userForm.getPassword()).trim()));
			String username = userForm.getUsername();
                      //	String name[] = username.split("-");
			User user = null;
		    String name[] = username.split("-");
			if (name[0].equals("Administrator")) {
				user = loginService.findUserByUserName(name[0].trim());
			} else {
				user = loginService.findUserByUserName(username.trim());
			}   	
	  	 
                 System.out.println("============Login==========="+userForm.getUsername());
                
               //  System.out.println("============getAuthentication==========="+ SecurityContextHolder.getContext().getAuthentication().getName());
                // final String token = jwtTokenUtil.generateToken(user);
                 Map<String, Object> claims= new HashMap<>();
                 TokenProvider tokenProvider = TokenProvider.getTokenProvider();
                  //  final String token = jwtTokenUtil.generateToken(user);
                  claims.put("user", TokenProvider.getTokenProvider());
                  final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
                   System.out.println("============token==========="+token);
	 
			String password = userForm.getPassword();

			if ((username != null && (!username.trim().equals("")))
					&& (password != null && (!password.trim().equals("")))) {

				//User user = new User();
			//	

				if (username.contains("-")) {

	                //  String name[] = username.split("-");
					if (name[0].equals("Administrator")) {
						user = loginService.findUserByUserName(name[0].trim());
					} else {
						user = loginService.findUserByUserName(username.trim());
					}

					logger.info("Login user>>    " + user);

					if (user != null) {
						if (user.getActiveStatus() != null && user.getActiveStatus().equalsIgnoreCase("AC")) {

							if (user.getUserAttempts() < ATTEMPTS) {

								if (user.getUserPassword().equals(AppUtils.SHA1(password).trim())) {

									UserAccessHelper userAccessHelper = new UserAccessHelper();
									userDTO = userAccessHelper.setRolePermissionForUIUser(userDTO, user);
									userDTO.setCompanyId(user.getCompany().getCompanyId());
									userDTO.setUserIdUpadate(user.getUserId());
									userDTO.setUserId(user.getUserId());
									// userDTO.setGroupId(user.getCompany().getGroupg().getGroupId());
									userDTO.setUserId(user.getUserId());
									userDTO.setNameOfUser(user.getNameOfUser());
									userDTO.setActiveStatus(user.getActiveStatus());
									userDTO.setLoginName(user.getLoginName());
									if (user.getLoginName().equals("Administrator")) {
										userDTO.setUsername("Administrator");
										userDTO.setEmailOfUser(user.getEmailOfUser());
										userDTO.setMobile(user.getUserMobileNo());
										userDTO.setMessage("success");
										 userDTO.setToken(token);
									} else if (user.getLoginName().equals("Super Administrator")) {
										userDTO.setUsername("Super Administrator");
										userDTO.setEmailOfUser(user.getEmailOfUser());
										userDTO.setMobile(user.getUserMobileNo());
										 userDTO.setToken(token);
										userDTO.setMessage("success");
									} else {
										Employee emp = employeePersonalInformationService
												.findEmployees(user.getNameOfUser(), user.getCompany().getCompanyId());

										session.setAttribute("User", user);
										if (emp != null) {
											if (user.getChangePassword() == null
													|| ("").equals(user.getChangePassword())) {
												error.setErrorMessage("change");
												userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
												userDTO.setMessage("change");
												
											} else {
												userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
												if (user.getUserAttempts() != 0)
													userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());

												userDTO.setEmailOfUser(user.getEmailOfUser());
												userDTO.setMessage("success");

											}
											userDTO.setEmployeeId(emp.getEmployeeId());
                                            userDTO.setToken(token);
										} else {
											logger.info("Unauthorized User ");
											userDTO.setMessage("Unauthorized User");
										}
									}
								} else {

									if (user.getUserAttempts() < ATTEMPTS) {

										MAX_ATTEMPTS = user.getUserAttempts();
										logger.info("MAX_ATTEMPTS >-" + MAX_ATTEMPTS);
										MAX_ATTEMPTS = MAX_ATTEMPTS + 1;
										userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());
										userDTO.setUserAttempts(MAX_ATTEMPTS);
										userDTO.setMessage("You have made  " + MAX_ATTEMPTS
												+ "  unsuccessful attempt(s) out of 99 allowed attempts.");

									} else {

										logger.info("Error! User Blockedsss  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
										userDTO.setMessage("Error! User Blocked");
									}
								}

							} else {
								logger.info("Error! User Blocked  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
								userDTO.setMessage("Error! User Blocked");
							}
						} else {
							userDTO.setMessage("Error! Account Deactivated");
							logger.info("Account Deactivated");
						}
					} else {
						userDTO.setMessage("Error! Invalid credentials");
						logger.info("Please Check your UserName and Password ");
					}
				} else {
					userDTO.setMessage("Error! Invalid credentials");
					logger.info("Please Check your UserName and Password ");
				}

			} else {
				userDTO.setMessage("Error! Invalid credentials");
				logger.info("Please Check your UserName and Password ");
			}

		} else {
			userDTO.setMessage("Error! Invalid user name or subscription expired");
		}
		return userDTO;

	}
	
	
	@ApiOperation(value = "welcome", response = ErrorHandling.class, notes = "User login authentication", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = "/login1", method = RequestMethod.POST)
	public @ResponseBody UserDTO welcome(@RequestBody UserDTO userForm, HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {
		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		Long MAX_ATTEMPTS = 0L;
		final int ATTEMPTS = 99;
		ErrorHandling error = new ErrorHandling();
		HttpSession session = req.getSession(true);
		UserDTO userDTO = new UserDTO();

		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
		List<String> domainNameLis = domainNameScheduler.domainNameList;
		if (domainNameLis.contains(tenantId)) {
			try {
                final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForm.getUsername().trim(),(userForm.getPassword()).trim()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String username = userForm.getUsername();
              	String name[] = username.split("-");
              	  User user =loginService.findUserByUserName(username);
              	 Map<String, Object> claims= new HashMap<>();
                 TokenProvider tokenProvider = TokenProvider.getTokenProvider();
                  //  final String token = jwtTokenUtil.generateToken(user);
                  claims.put("user", TokenProvider.getTokenProvider());
                  final String token=  jwtTokenUtil.doGenerateTokenByClaims( claims,user.getLoginName(),user.getUserPassword());
                  String password = userForm.getPassword();
                  if ((username != null && (!username.trim().equals("")))
      					&& (password != null && (!password.trim().equals("")))) {
                	  if (username.contains("-")) {
                		  if (name[0].equals("Administrator")) {
      						user = loginService.findUserByUserName(name[0].trim());
      					} else {
      						user = loginService.findUserByUserName(username.trim());
      					}  
                	  }
                	  if (user != null) {
                		  if (user.getUserAttempts() < ATTEMPTS) {
                			  if (user.getUserPassword().equals(AppUtils.SHA1(password).trim())) {  
                					UserAccessHelper userAccessHelper = new UserAccessHelper();
									userDTO = userAccessHelper.setRolePermissionForUIUser(userDTO, user);  
									userDTO.setUserId(user.getUserId());
									if (user.getLoginName().equals("Administrator")) {
										userDTO.setUsername("Administrator");
										userDTO.setEmailOfUser(user.getEmailOfUser());
										userDTO.setMobile(user.getUserMobileNo());
										userDTO.setMessage("success");
									} else if (user.getLoginName().equals("Super Administrator")) {
										userDTO.setUsername("Super Administrator");
										userDTO.setEmailOfUser(user.getEmailOfUser());
										userDTO.setMobile(user.getUserMobileNo());
										userDTO.setMessage("success");
									} else {
										Employee emp = employeePersonalInformationService
												.findEmployees(user.getNameOfUser(), user.getCompany().getCompanyId());
										if (emp != null) {
											if (user.getChangePassword() == null
													|| ("").equals(user.getChangePassword())) {
												error.setErrorMessage("change");
												userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
												userDTO.setMessage("change");
												}
											else {
											
												userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
												
												if (user.getUserAttempts() != 0)
													userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());

												userDTO.setEmailOfUser(user.getEmailOfUser());
												userDTO.setMessage("success");
                                               
											}
											userDTO.setToken(token);
											userDTO.setEmployeeId(emp.getEmployeeId());
										}
										 else {
												logger.info("Unauthorized User ");
												userDTO.setMessage("Unauthorized User");
											}
										
									}
                				  
                			  }else {

									if (user.getUserAttempts() < ATTEMPTS) {

										MAX_ATTEMPTS = user.getUserAttempts();
										logger.info("MAX_ATTEMPTS >-" + MAX_ATTEMPTS);
										MAX_ATTEMPTS = MAX_ATTEMPTS + 1;
										userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());
										userDTO.setUserAttempts(MAX_ATTEMPTS);
										userDTO.setMessage("You have made  " + MAX_ATTEMPTS
												+ "  unsuccessful attempt(s) out of 99 allowed attempts.");

									} else {

										logger.info("Error! User Blockedsss  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
										userDTO.setMessage("Error! User Blocked");
									}
								}
                		  }
                		  else {
								logger.info("Error! User Blocked  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
								userDTO.setMessage("Error! User Blocked");
							}

                		  
                	  } else {
  						userDTO.setMessage("Error! Invalid credentials");
  						logger.info("Please Check your UserName and Password ");
  					}
                	  
                  }
			}
			catch(Exception e) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				res.setContentType(MediaType.APPLICATION_JSON_VALUE);
				res.getWriter().write("{\"status\": \"412\",\"message\": \" Error! Invalid user name or password \"}");
				res.getWriter().flush();
				userDTO.setMessage("Error! Invalid credentials");
					logger.info("Please Check your UserName and Password ");
			}
			
			
			
		}
		else {
			userDTO.setMessage("Error! Invalid user name or subscription expired");
		}
		return userForm;
		
	}

	@ApiOperation(value = "welcome", response = ErrorHandling.class, notes = "User login authentication", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = "/loginApp", method = RequestMethod.POST)
	public @ResponseBody UserDTO LoginApp(@RequestBody UserDTO userForm, HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {String tenantId = null;
	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

	Long MAX_ATTEMPTS = 0L;
	final int ATTEMPTS = 99;
	// HttpHeaders headers = new HttpHeaders();
	ErrorHandling error = new ErrorHandling();
	HttpSession session = req.getSession(true);
	UserDTO userDTO = new UserDTO();

	if (attr != null) {
		tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
		if (tenantId == null)
			tenantId = attr.getRequest().getHeader("tenantId");
	}
	List<String> domainNameLis = domainNameScheduler.domainNameList;

//	List<String> domainNameLis =DataBaseConnection.getCustomerResgistrationData(env);

	if (domainNameLis.contains(tenantId)) {
		System.out.println("tenantId======== " + tenantId);

		// logger.info("Login Attentication user name " + userForm.getUsername());
		// logger.info("Login Attentication user password " + userForm.getPassword());
		try {
			 String name[] = userForm.getUsername().split("-");
			 String userName =null ;
			 if (name[0].equals("Administrator")) {
				 userName = name[0].trim();
			 }
			 else {
				 userName =userForm.getUsername().trim();
			 }
            final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,(userForm.getPassword()).trim()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(Exception e ) {
			userDTO.setMessage("Error! Invalid credentials");
			logger.info("Please Check your UserName and Password ");
		}
		
                    //  final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForm.getUsername().trim(),(userForm.getPassword()).trim()));
		String username = userForm.getUsername();
                  //	String name[] = username.split("-");
		User user = null;
	    String name[] = username.split("-");
		if (name[0].equals("Administrator")) {
			user = loginService.findUserByUserName(name[0].trim());
		} else {
			user = loginService.findUserByUserName(username.trim());
		}   	
  	 
             System.out.println("============Login==========="+userForm.getUsername());
            
           //  System.out.println("============getAuthentication==========="+ SecurityContextHolder.getContext().getAuthentication().getName());
            // final String token = jwtTokenUtil.generateToken(user);
             Map<String, Object> claims= new HashMap<>();
             TokenProvider tokenProvider = TokenProvider.getTokenProvider();
              //  final String token = jwtTokenUtil.generateToken(user);
              claims.put("user", TokenProvider.getTokenProvider());
              final String token=  jwtTokenUtil.doGenerateTokenByClaimsForApp( claims,user.getLoginName(),user.getUserPassword());
               System.out.println("============token==========="+token);
 
		String password = userForm.getPassword();

		if ((username != null && (!username.trim().equals("")))
				&& (password != null && (!password.trim().equals("")))) {

			//User user = new User();
		//	

			if (username.contains("-")) {

                //  String name[] = username.split("-");
				if (name[0].equals("Administrator")) {
					user = loginService.findUserByUserName(name[0].trim());
				} else {
					user = loginService.findUserByUserName(username.trim());
				}

				logger.info("Login user>>    " + user);

				if (user != null) {
					if (user.getActiveStatus() != null && user.getActiveStatus().equalsIgnoreCase("AC")) {

						if (user.getUserAttempts() < ATTEMPTS) {

							if (user.getUserPassword().equals(AppUtils.SHA1(password).trim())) {

								UserAccessHelper userAccessHelper = new UserAccessHelper();
								userDTO = userAccessHelper.setRolePermissionForUIUser(userDTO, user);
								userDTO.setCompanyId(user.getCompany().getCompanyId());
								userDTO.setUserIdUpadate(user.getUserId());
								userDTO.setUserId(user.getUserId());
								// userDTO.setGroupId(user.getCompany().getGroupg().getGroupId());
								userDTO.setUserId(user.getUserId());
								userDTO.setNameOfUser(user.getNameOfUser());
								userDTO.setActiveStatus(user.getActiveStatus());
								userDTO.setLoginName(user.getLoginName());
								if (user.getLoginName().equals("Administrator")) {
									userDTO.setUsername("Administrator");
									userDTO.setEmailOfUser(user.getEmailOfUser());
									userDTO.setMobile(user.getUserMobileNo());
									userDTO.setMessage("success");
									 userDTO.setToken(token);
								} else if (user.getLoginName().equals("Super Administrator")) {
									userDTO.setUsername("Super Administrator");
									userDTO.setEmailOfUser(user.getEmailOfUser());
									userDTO.setMobile(user.getUserMobileNo());
									 userDTO.setToken(token);
									userDTO.setMessage("success");
								} else {
									Employee emp = employeePersonalInformationService
											.findEmployees(user.getNameOfUser(), user.getCompany().getCompanyId());

									session.setAttribute("User", user);
									if (emp != null) {
										if (user.getChangePassword() == null
												|| ("").equals(user.getChangePassword())) {
											error.setErrorMessage("change");
											userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
											userDTO.setMessage("change");
											
										} else {
											userDTO.setUsername(emp.getFirstName() + " " + emp.getLastName());
											if (user.getUserAttempts() != 0)
												userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());

											userDTO.setEmailOfUser(user.getEmailOfUser());
											userDTO.setMessage("success");

										}
										userDTO.setEmployeeId(emp.getEmployeeId());
                                        userDTO.setToken(token);
									} else {
										logger.info("Unauthorized User ");
										userDTO.setMessage("Unauthorized User");
									}
								}
							} else {

								if (user.getUserAttempts() < ATTEMPTS) {

									MAX_ATTEMPTS = user.getUserAttempts();
									logger.info("MAX_ATTEMPTS >-" + MAX_ATTEMPTS);
									MAX_ATTEMPTS = MAX_ATTEMPTS + 1;
									userService.userAttemptsUpdate(MAX_ATTEMPTS, user.getNameOfUser());
									userDTO.setUserAttempts(MAX_ATTEMPTS);
									userDTO.setMessage("You have made  " + MAX_ATTEMPTS
											+ "  unsuccessful attempt(s) out of 99 allowed attempts.");

								} else {

									logger.info("Error! User Blockedsss  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
									userDTO.setMessage("Error! User Blocked");
								}
							}

						} else {
							logger.info("Error! User Blocked  MAX_ATTEMPTS  " + MAX_ATTEMPTS);
							userDTO.setMessage("Error! User Blocked");
						}
					} else {
						userDTO.setMessage("Error! Account Deactivated");
						logger.info("Account Deactivated");
					}
				} else {
					userDTO.setMessage("Error! Invalid credentials");
					logger.info("Please Check your UserName and Password ");
				}
			} else {
				userDTO.setMessage("Error! Invalid credentials");
				logger.info("Please Check your UserName and Password ");
			}

		} else {
			userDTO.setMessage("Error! Invalid credentials");
			logger.info("Please Check your UserName and Password ");
		}

	} else {
		userDTO.setMessage("Error! Invalid user name or subscription expired");
	}
	return userDTO;
}
	@RequestMapping(path = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody UserDTO changePassword(@RequestBody UserDTO user1, HttpServletRequest req) {
		User user = loginService.findUserByUserName(user1.getLoginName().trim());
		try {
			String password = user1.getNewpassword();
			user.setChangePassword(user.getUserPassword());
			user.setUserPassword(AppUtils.SHA1(password));
			loginService.create(user);
			user1.setMessage("success");

			if (user.getUserAttempts() > 5) {
				user.setUserAttempts(0l);
				loginService.updateById(user);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage());
			user1.setMessage("fail");
		}
		return user1;
	}

	@RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody UserDTO updatePassword(@RequestBody UserDTO user1) throws ErrorHandling {
		User user = loginService.findUserByUserName(user1.getLoginName().trim());
		try {
			// logger.info("user.getUserPassword()>>>>>" + user.getUserPassword());
			// logger.info("user1.getOldPassword()>>>>>" + user1.getOldPassword());
			if (user.getUserPassword().equals(AppUtils.SHA1(user1.getOldPassword()))) {
				user.setChangePassword(AppUtils.SHA1(user1.getOldPassword()));
				user.setUserPassword(AppUtils.SHA1(user1.getNewpassword()));
				loginService.create(user);
				user1.setMessage("success");
			} else {
				user1.setMessage("fail");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage());
			user1.setMessage("fail");
		}
		return user1;
	}

	@RequestMapping(path = "/changeOldPassword", method = RequestMethod.POST)
	public @ResponseBody UserDTO changeOldPassword(@RequestBody UserDTO user1, HttpServletRequest req)
			throws ErrorHandling {

		User user = loginService.findUserByUserName(user1.getLoginName().trim());

		logger.info("<<<<<<<<<<<<<<Change Password method start>>>>>>>>>>>>>>");
		String password = user1.getNewpassword();

		String oldPassword = user1.getUserPassword();
		String matchPassword = AppUtils.SHA1(oldPassword);

		user.setChangePassword(user.getUserPassword());
		logger.info("---------ChangePassword---------- " + user.getChangePassword());
		user.setUserPassword(AppUtils.SHA1(password));
		logger.info("---------User Password---------- " + user.getUserPassword());

		logger.info("User details in change password   " + user);
		if (matchPassword.equals(user.getChangePassword())) {
			loginService.create(user);
			user1.setMessage("success");
			logger.info("<<<<<<<<<<<<<<Change Password Completed>>>>>>>>>>>>>>");
		} else {
			logger.info("<<<<<<<<<<<<<<Error part>>>>>>>>>>>>>>");
			throw new ErrorHandling("Please enter correct old password");
		}

		if (user.getUserAttempts() > 5) {
			user.setUserAttempts(0l);
			loginService.updateById(user);
		}

		return user1;
	}

}
