package com.csipl.hrms.common.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.csipl.hrms.service.common.PushNotificationsService;
import com.csipl.hrms.service.common.PushNotificationsServices;


public class FcmNotificationConroller {

//	 @GetMapping("/fcm")
//	    public String fcmNotification() throws JSONException {
//		  sendFCMNotification(211l);
//	    	return "FABHR FIREBASE NOTIFICATION";
//	    }
	
	  
	  @Autowired
	  PushNotificationsService androidPushNotificationsService;
	  
	  @Autowired
	  PushNotificationsServices pushNotificationsServices;

	 
//	  @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
//	  public ResponseEntity<String> send()   throws JSONException{
//	  String token = "f_dvm8P2LAs:APA91bEvmibi4OPJ0F8Imb8pee3DCQcL3nPn_L2qU2719bpHzlq3xYH-o35mcud5Pz69qr8hRl1AeSAX8UM1lQ64i-6YL_taQp6KjDItjDBISpTF8WS0-6JS-Knq2XCwzdF04eiDZb1B";
//	    JSONObject body = new JSONObject();
//	    //body.put("to", "/topics/" + TOPIC);
//	    body.put("to", token);
//	    body.put("priority", "high");
//	 
//	    JSONObject notification = new JSONObject();
//	    notification.put("title", "Welcome Notification NC");
//	    notification.put("body", "Happy Message!");
//	    
//	    JSONObject body2 = new JSONObject();
//	    body2.put("Key-1", "NC Data 1");
//	    body2.put("Key-2", "NC Data 2");
//	 
//	    body.put("notification", notification);
//	    body.put("data", body2);
//	 
//	    HttpEntity<String> request = new HttpEntity<>(body.toString());
//	 
//	    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
//	    CompletableFuture.allOf(pushNotification).join();
//	    sendFCMNotification(211l);
//	    try {
//	      String firebaseResponse = pushNotification.get();
//	      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
//	    } catch (InterruptedException e) {
//	      e.printStackTrace();
//	    } catch (ExecutionException e) {
//	      e.printStackTrace();
//	    }
//	    
//	    
//	    return new ResponseEntity<>("FCM Push Notification ERROR!", HttpStatus.BAD_REQUEST);
//	  }
//	  
	  public void sendFCMNotification(Long employeeId) throws JSONException {
		  ArrayList<String> reportinsgTo = pushNotificationsServices.getReportingEmployeeCode(employeeId);
		  fcmRequest(reportinsgTo,"Leave","I am onleave","hii");
		
		 		  
	  }
	  
	  public void sendFCMLeave(Long employeeId) {
		 
		 		  
	  }
	  
	  public ResponseEntity<String> fcmRequest(ArrayList<String> registration_ids , String title , String msg,String notificationType)   throws JSONException{
		    JSONObject body = new JSONObject();
		    
		    body.put("registration_ids", new JSONArray(registration_ids));
		    body.put("priority", "high");
		    //body.put("android_channel_id", "FabHr");
		  
		    	
		    JSONObject data = new JSONObject();
		    data.put("title", title);
		    data.put("body", msg);
		    data.put("type", notificationType);
		    //data.put("applyId", applyId);
		    body.put("data", data);
		    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+body.toString());
		    HttpEntity<String> request = new HttpEntity<>(body.toString());
		 
		    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		    CompletableFuture.allOf(pushNotification).join();
		    
		    try {
		      String firebaseResponse = pushNotification.get();
		      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } catch (ExecutionException e) {
		      e.printStackTrace();
		    }
		    return new ResponseEntity<>("FCM Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		  }
	  
	  public ResponseEntity<String> fcmAttendanceRequest(String topic ,String title , String msg,String notificationType)   throws JSONException{
		    JSONObject body = new JSONObject();
		    body.put("to", "/topics/" + topic);
		    body.put("priority", "high");
		 
		    JSONObject notification = new JSONObject();
		    notification.put("title", title);
		    notification.put("body", msg);
		    
		    JSONObject body2 = new JSONObject();
		    body2.put("type", notificationType);
		    body2.put("Key-2", "NC Data 2");
		 
		    body.put("notification", notification);
		    body.put("data", body2);
		 
		    HttpEntity<String> request = new HttpEntity<>(body.toString());
		 
		    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		    CompletableFuture.allOf(pushNotification).join();
		    
		    try {
		      String firebaseResponse = pushNotification.get();
		      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } catch (ExecutionException e) {
		      e.printStackTrace();
		    }
		    return new ResponseEntity<>("FCM Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		  }
	  
}
