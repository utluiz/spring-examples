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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;
import br.com.starcode.agenda.domain.Prioridade;
import br.com.starcode.agenda.domain.Usuario;
import br.com.starcode.agenda.service.EntradaService;
import br.com.starcode.agenda.util.DateUtil;

/**
 * VIEW -> JSP
 * CONTROLLERS
 * SERVICES 
 * REPOSITORY
 * JPA / DAO
 */
@Controller
public class EntradaController {
	
	@Autowired
	EntradaService entradaService;

	@RequestMapping("/entradas")
	ModelAndView entradas(
			@ModelAttribute("filtro") FiltroEntrada filtro,
			@ModelAttribute("ordem") OrdenacaoEntrada ordem) {
		List<Entrada> entradas = entradaService.search(filtro, ordem);
		return new ModelAndView("entradas")
			.addObject("entradas", entradas);
	}
	
	@RequestMapping(value="/entrada", params="new")
	ModelAndView nova() {
		Entrada entrada = new Entrada();
		entrada.setHorario(new Date());
		entrada.setPrioridade(Prioridade.NadaDeMais);
		return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
	}

	@RequestMapping(value="/{escritorio}/login")
	ModelAndView editar(
			@PathVariable("escritorio") Integer id, 
			RedirectAttributes redirectAttributes) {
		try {
			Entrada entrada = entradaService.findById(id);
			if (entrada == null) {
				throw new Exception("Entrada '" + id + "' não encontrada para edição!");
			}
			return new ModelAndView("editar-entrada")
			.addObject("entrada", entrada);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());      
			return new ModelAndView("redirect:/entradas");
		}
	}
	
	@RequestMapping(value="/entrada", method = RequestMethod.POST)
	ModelAndView confirmarNova(
			Entrada entrada,
			@RequestParam("hora") String horario,
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		try {
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//prepare entrada
			entrada.setIdUsuario(usuario.getId());
			entrada.setHorario(DateUtil.mergeWithHour(entrada.getHorario(), horario));
			//insert 
			entradaService.insert(entrada);
			//success
			redirectAttributes.addFlashAttribute("msg", "Registro '" + entrada.getId() + "' inserido com sucesso!");
			return new ModelAndView("redirect:/entradas");
		} catch (Exception e) {
			return new ModelAndView("editar-entrada")
					.addObject("erro", e.getMessage());
		}
	}
	
	@RequestMapping(value="/entrada/{id}", method = RequestMethod.POST)
	ModelAndView confirmarEdicao(
			Entrada entrada,
			@RequestParam("hora") String horario,
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		try {
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//prepare entrada
			entrada.setIdUsuario(usuario.getId());
			entrada.setHorario(DateUtil.mergeWithHour(entrada.getHorario(), horario));
			//update
			entradaService.update(entrada);
			//success message
			redirectAttributes.addFlashAttribute("msg", "Registro '" + entrada.getId() + "' atualizado com sucesso!"); 
			return new ModelAndView("redirect:/entradas");
		} catch (Exception e) {
			return new ModelAndView("editar-entrada")
					.addObject("erro", e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/entrada/remover/{id}")
	ModelAndView remover(
			@PathVariable("id") Integer id, 
			RedirectAttributes redirectAttributes) {
		try {
			entradaService.delete(id);
			redirectAttributes.addFlashAttribute("msg", "Registro '" + id + "' removido com sucesso!");      
			return new ModelAndView("redirect:/entradas");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());      
			return new ModelAndView("redirect:/entradas");
		}
		
	}
	
}
