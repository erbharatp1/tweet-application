//package com.csipl.tms.org;
//import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//
//public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
//
//    @Override
//    public String resolveCurrentTenantIdentifier() {
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        String tenantId = attr.getRequest().getParameter("tenantId");
//        return tenantId;
//    }
//
//    @Override
//    public boolean validateExistingCurrentSessions() {
//        return true;
//    }
//}