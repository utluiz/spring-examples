package br.com.starcode.agenda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.starcode.agenda.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(value = "/login", params = "error")
	String loginError() {
		return "home";
	}
	
	@RequestMapping("/logout")
	String logout(HttpSession sessao) {
		return "redirect:/";
	}
	
}
