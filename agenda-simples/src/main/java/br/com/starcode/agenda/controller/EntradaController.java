package br.com.starcode.agenda.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;
import br.com.starcode.agenda.domain.PrioridadeEntrada;
import br.com.starcode.agenda.service.EntradaService;

@Controller
public class EntradaController {
	
	@Autowired
	EntradaService entradaService;

	@RequestMapping("/entradas")
	public ModelAndView entradas(
			@ModelAttribute("filtro") FiltroEntrada filtro,
			@ModelAttribute("ordem") OrdenacaoEntrada ordem) {
		List<Entrada> entradas = entradaService.search(filtro, ordem);
		return new ModelAndView("entradas")
			.addObject("entradas", entradas);
	}
	
	@RequestMapping(value="/entrada", params="new")
	public ModelAndView nova() {
		Entrada entrada = new Entrada();
		entrada.setHorario(new Date());
		entrada.setPrioridadeEntrada(PrioridadeEntrada.NadaDeMais);
		return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
	}
	
	@RequestMapping(value="/entrada/{id}")
	public ModelAndView editar(@PathVariable("id") Integer id) {
		Entrada entrada = entradaService.findById(id);
		return new ModelAndView("editar-entrada")
			.addObject("entrada", entrada);
	}
	
}
