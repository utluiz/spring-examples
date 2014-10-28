package br.com.starcode.agenda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", params = "error")
	String loginError() {
		return "home";
	}
	
	@RequestMapping("/logout")
	String logout(HttpSession sessao) {
		return "redirect:/";
	}
	
}
