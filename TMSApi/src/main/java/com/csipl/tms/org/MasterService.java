package com.csipl.tms.org;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by JavaDeveloperZone on 22-01-2017.
 */

public class MasterService {
	
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
    public static Map<Object, Object> getDataSourceHashMap() {

       
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/demo_database");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        
        HashMap hashMap = new HashMap();
        RestTemplate restTemplate = new RestTemplate();
		try {
			 TenantDTO  data =	restTemplate.getForObject("http://10.1.1.48.:8080/saasApi/tenants/allTenants", TenantDTO.class);
			 
			for(Tenant tenant :data.getTenants()) {
				System.out.println("===========Data====="+tenant.getIdentifier());

				for (TenantServerConnection tenantServerConnections : tenant.getTenantServerConnections()) {
					DriverManagerDataSource dataSource = new DriverManagerDataSource();
					String schemaName = tenantServerConnections.getSchemaName();
					String serverPort = tenantServerConnections.getSchemaServerPort();
					String userName = tenantServerConnections.getSchemaUsername();
					String password = tenantServerConnections.getSchemaPassword();
					String schemaServer = tenantServerConnections.getSchemaServer();
					String url = "jdbc:mysql://" + schemaServer + ":" + serverPort +"/"+ schemaName;

					dataSource.setDriverClassName("com.mysql.jdbc.Driver");
					dataSource.setUrl(url);
					dataSource.setUsername(userName);
					dataSource.setPassword(password);
					
					 hashMap.put(tenant.getIdentifier(), dataSource);
					 
					 break;
				}
			 
				
			}
			 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		 hashMap.entrySet().forEach(action->{
			 System.out.println(action.toString());
		 });
//		System.out.println(response.getBody());
        
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://10.1.1.100:3306/super_db");
//		dataSource.setUsername("root");
//		dataSource.setPassword("nWekTMKd&K5NkQ");

//        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
//        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource1.setUrl("jdbc:mysql://10.1.1.100:3306/infosys");
//		dataSource1.setUsername("root");
//		dataSource1.setPassword("nWekTMKd&K5NkQ");

//        HashMap hashMap = new HashMap();
//        hashMap.put("tenantId1", dataSource);
//        hashMap.put("tenantId2", dataSource1);
        return hashMap;
    }
}