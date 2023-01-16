package com.csipl.hrms.service.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Service
public class PushNotificationsService {
 
	@Autowired
	Environment env;
	
  private static final String FIREBASE_SERVER_KEY = "AAAANE-2q1g:APA91bHgWFq1A8bkf7Lg5c4X3AH9O9mJzfpruz0t63K9xCr5Y6OMZicslcadJN1zV9BcpcZIxSHg8RMxTbkgVb2cjcGZufkVShgdqoHZy9pE0pY1G5IYbvx94RRNPTI11KT84BwN0tID";
  private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	//private static final String FIREBASE_SERVER_KEY = "fcm.server.token";
	//private static final String FIREBASE_API_URL = "fcm.end.point";
 
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {
 
    RestTemplate restTemplate = new RestTemplate();
    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" +FIREBASE_SERVER_KEY ));
    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
    restTemplate.setInterceptors(interceptors);
    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
    return CompletableFuture.completedFuture(firebaseResponse);
  }



}