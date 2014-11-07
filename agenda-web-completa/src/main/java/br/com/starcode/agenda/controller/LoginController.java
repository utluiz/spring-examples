package br.com.starcode.agenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", params = "error")
	String loginError() {
		return "home";
	}
	
	@RequestMapping("/logout")
	String logout() {
		return "redirect:/";
	}
	
}
