package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

	@GetMapping("/map-register")
	public String registerMapping() {
		return "register"; // returning to register.html page
	}

	@GetMapping("/map-login")
	public String loginMapping() {
		return "index"; // returning to login.html page
	}

	@GetMapping("/samplePayment")
	public String samplePayment() {
		return "samplePayment";
	}
}
