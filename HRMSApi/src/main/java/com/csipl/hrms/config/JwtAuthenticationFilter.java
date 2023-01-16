package com.csipl.hrms.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.csipl.hrms.model.common.User;
import com.csipl.hrms.org.Tenant;
import com.csipl.hrms.service.authorization.LoginService;
import com.csipl.hrms.service.users.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.csipl.hrms.config.Constants.HEADER_STRING;
import static com.csipl.hrms.config.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
	@Autowired
	LoginService loginService;
	
	TokenProvider tokenProvider = new TokenProvider();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    	
    	final String requestTokenHeader = req.getHeader("Authorization");
     	logger.error("requestTokenHeader.   " +requestTokenHeader);
        
    	
    	Tenant tenant = null;
		String username = req.getHeader("username");
		String tenantId = req.getHeader("tenantId");
		String password = null;
		String authToken = null; 
		Boolean authUrl = false;
		Boolean authUser = false;
    	String header = req.getHeader(HEADER_STRING);
    	
    	tokenProvider.setTenantId(tenantId);
    	tokenProvider.setUsername(username);
    	tokenProvider.setTokenProvider(tokenProvider);
      //  String username = null;
        //String authToken = null;
    /*	if (req.getRequestURI().endsWith("/isAlive") || req.getRequestURI().endsWith("/process-activation")
				|| req.getRequestURI().endsWith("/activate-account")
				|| req.getRequestURI().endsWith("/forgot-password")) {
			chain.doFilter(req, res);
			return;
		}*/
    	/*if (header == null ) {

			res.setStatus(HttpStatus.BAD_REQUEST.value());
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
			res.getWriter().write("{\"status\": \"401\",\"message\": \"No UserName Supplied\"}");
			res.getWriter().flush();
			return;

		}*/
    	
    	String name[] = username.split("-");
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
        	System.out.println("header value"+header);
            authToken = header.replace(TOKEN_PREFIX,"");
            authUrl = true;
            try {
            	String userName=null;
            	if (name[0].equals("Administrator")) {
            		userName =name[0].trim();
            	}
            	else
            		userName =req.getHeader("username");
				UserDetails u = userDetailsService.loadUserByUsername(userName);
				password = u.getPassword();

				Claims claims = jwtTokenUtil.getAllClaimsFromTokenSigningKey(authToken, u.getPassword());
				
				
				//Map<String, Object> claimsObj = claims;
				//Object obj = claimsObj.get("user");
				//TokenProvider provider = getMapFromResponseObject(obj);
			//	tenant = provider.getTenant();

				username = claims.getSubject();
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
           /* try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }*/
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                	
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
            authUrl = true;
           // res.setStatus(HttpStatus.NOT_FOUND.value());
			/*res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
			res.getWriter().write("{\"status\": \"401\",\"message\": \"You are not authorized to access LauraMac platform for this Tenant\"}");
			res.getWriter().flush();
			return;*/
        }
        if (authUrl) {
        	
        	User user =null;
        	if (name[0].equals("Administrator")) {
				user = loginService.findUserByUserName(name[0].trim());
			} else {
				user = loginService.findUserByUserName(req.getHeader("username").trim());
			}
        	//User user =loginService.findUserByUserName(req.getHeader("username"));
        	if(user!=null) {
        	/*	if (user.getActiveStatus() != null && user.getActiveStatus().equalsIgnoreCase("DE")) {
        			res.setStatus(HttpStatus.UNAUTHORIZED.value());
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					res.setContentType(MediaType.APPLICATION_JSON_VALUE);
					res.getWriter().write("{\"status\": \"401\",\"message\": \" Your account has been de-activated, please reach out to your administrator to activate your account \"}");
					res.getWriter().flush();
					return;		
            	}*/
        		//else{
        			tokenProvider.setTenant(tenant);
					tokenProvider.setId(user.getUserId());
					tokenProvider.setUsername(user.getLoginName());
					tokenProvider.setFirstName(user.getNameOfUser());
					tokenProvider.setEmail(user.getEmailOfUser());
				//	tokenProvider.setIsTenantOwner(IsTenantOwner);
					//tokenProvider.setUserCompany(user.getCompany());
					//tokenProvider.setPersona(tenantCompanyPersona.getPersona());
				/*	tokenProvider
							.setDto(userRoleService.findSystemUserRolesAndPermission(systemUser.getUsername()));*/
					tokenProvider.setTokenProvider(tokenProvider);
					 chain.doFilter(req, res);
        		//}
        		
        	}
        	else {
        		res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				res.setContentType(MediaType.APPLICATION_JSON_VALUE);
				res.getWriter().write("{\"status\": \"500\",\"message\": \" Invalid credaintial \"}");
				res.getWriter().flush();
				return;		
        	}
        	
        }

       // chain.doFilter(req, res);
       
    }
    private TokenProvider getMapFromResponseObject(Object myObj) {
		ObjectMapper m = new ObjectMapper();
		TokenProvider props = m.convertValue(myObj, TokenProvider.class);
		return props;
	}
}