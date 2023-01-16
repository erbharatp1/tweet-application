package com.csipl.hrms.org;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csipl.hrms.config.TokenProvider;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {
	static String DEFAULT_TENANTID = "csipl.in";
//	TokenProvider tokenProvider = new TokenProvider();
	@Override
	protected Object determineCurrentLookupKey() {
		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); // get
	
		if(TokenProvider.getTokenProvider()!=null)
		tenantId = TokenProvider.getTokenProvider().getTenantId();
		
		
//		tenantId  =	tokenProvider.getTenantId();
		// request
		//DEFAULT_TENANTID	=TenantContext.getTenantId();																											// object
//		if (attr != null) {
////			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
//			if(tenantId==null)
////			tenantId = attr.getRequest().getHeader("tenantId");
//			if (tenantId != null && !tenantId.equals(""))
//				return tenantId;
//			 
//			else
//				return DEFAULT_TENANTID;
//		}tenantId	 =TenantContext.getTenantId();
//		 if(tenantId != null && !tenantId.equals("")) {
//			 System.out.println("tenantId======================---------- "+tenantId);
//			 return tenantId;
//		 }
		if(tenantId!=null) {
			return tenantId;
		}
		else {
			return DEFAULT_TENANTID; // default data source
		}
	}
}