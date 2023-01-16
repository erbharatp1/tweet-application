package com.csipl.hrms.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.customer.CustomerSubscriptionDTO;
import com.csipl.hrms.service.customer.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customerSubscription")
	public CustomerSubscriptionDTO getCustomerSubscriptionDetails() {
		return customerService.getCustomerSubscriptionDetails();

	}
}
