package com.contactmanager.webContactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(Model m) {
		m.addAttribute("title","home page");
		return "home";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

}
