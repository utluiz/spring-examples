package br.com.starcode.agenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CadastroController {
	
	@RequestMapping(value = "/cadastrar",
			method = RequestMethod.POST)
	ModelAndView cadastrar(
		@RequestParam("mail") String email
	  ) {
		
		return new ModelAndView("home")
			.addObject("sucesso", 1)
			.addObject("email", email);
		
	}

}


