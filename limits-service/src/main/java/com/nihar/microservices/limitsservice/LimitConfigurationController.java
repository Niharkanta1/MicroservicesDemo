package com.nihar.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nihar.microservices.limitsservice.bean.LimitConfiguration;

@RestController
public class LimitConfigurationController {
	
	@Autowired
	private Configuration config;
	
	@GetMapping("limits")
	public LimitConfiguration retrieveLimitConfiguration() {
		return new LimitConfiguration(config.getMinimum(), config.getMaximum());
	}
}
