//package com.csipl.tms.org;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//public class CustomRoutingDataSource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();     // get request object
//        if(attr!=null) {
//            String tenantId1 = attr.getRequest().getParameter("tenantId");       // find parameter from request
//        	String tenantId = attr.getRequest().getHeader("tenantId");
//            return tenantId;
//        }else{
//            return "csipl.in";             // default data source
//        }
//    }
//}