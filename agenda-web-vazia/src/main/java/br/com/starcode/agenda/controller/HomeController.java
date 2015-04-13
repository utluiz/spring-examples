package br.com.starcode.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@Autowired
	IntegrantesDao integrantesDao;

	@RequestMapping("/")
	String showHome() {
		return "home";
	}
	
	@RequestMapping("/sobre")
	ModelAndView showAbout() {
		List<String> integrantes = 
		  integrantesDao.listarIntegrantes();
		return new ModelAndView("sobre")
			.addObject("grupo", integrantes);
	}
	
}



