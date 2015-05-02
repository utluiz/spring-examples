package br.com.starcode.agenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	/**
	 * Mapeia a URL raiz, isto é, esta é a página principal, a "home".
	 * Mostra a página "home.jsp"
	 */
	@RequestMapping("/")
	String home() {
		return "home";
	}
	
	/**
	 * Mostra a página "sobre" (sobre.jsp)
	 */
	@RequestMapping("/sobre")
	String sobre() {
		return "sobre";
	}
	
}




