package br.com.starcode.agenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	String home() {
		return "home";
	}
	
	@RequestMapping("/sobre")
	String sobre() {
		return "sobre";
	}
	
}
