package br.com.starcode.agenda.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;
import br.com.starcode.agenda.domain.PrioridadeEntrada;
import br.com.starcode.agenda.domain.Usuario;
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
		entrada.setPrioridade(PrioridadeEntrada.NadaDeMais);
		return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
		
	}

	@RequestMapping(value="/entrada/{id}")
	public ModelAndView editar(@PathVariable("id") Integer id) {
		
		Entrada entrada = entradaService.findById(id);
		if (entrada == null) {
			return new ModelAndView("editar-entrada")
				.addObject("erro", "Entrada '" + id + "' não encontrada para edição!");
		} else {
			return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
		}
			
	}
	
	@RequestMapping(value="/entrada", method = RequestMethod.POST)
	public ModelAndView confirmarNova(
			@ModelAttribute("entrada") Entrada entrada,
			HttpSession sessao) {
		
		try {
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			if (usuario == null) {
				throw new RuntimeException("Usuário não autenticado");
			}
			entrada.setIdUsuario(usuario.getId());
			entradaService.insert(entrada);
			return new ModelAndView("redirect:/entradas")
					.addObject("msg", "Registro '" + entrada.getId() + "' atualizado com sucesso!");
		} catch (Exception e) {
			return new ModelAndView("redirect:/entradas")
					.addObject("erro", e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/entrada/{id}", method = RequestMethod.POST)
	public ModelAndView confirmarEdicao(
			@ModelAttribute("entrada") Entrada entrada,
			HttpSession sessao) {
		
		try {
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			if (usuario == null) {
				throw new RuntimeException("Usuário não autenticado");
			}
			entrada.setIdUsuario(usuario.getId());
			
			entradaService.update(entrada);
			return new ModelAndView("redirect:/entradas")
					.addObject("msg", "Registro '" + entrada.getId() + "' atualizado com sucesso!");
		} catch (Exception e) {
			return new ModelAndView("redirect:/entradas")
					.addObject("erro", e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/entrada/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Integer id) {
		
		try {
			entradaService.delete(id);
			return new ModelAndView("redirect:/entradas")
					.addObject("msg", "Registro '" + id + "' não removido porque não foi encontrado!");
		} catch (Exception e) {
			return new ModelAndView("redirect:/entradas")
					.addObject("erro", e.getMessage());
		}
		
	}
	
}
