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

import br.com.starcode.agenda.model.Entrada;
import br.com.starcode.agenda.model.FiltroEntrada;
import br.com.starcode.agenda.model.OrdenacaoEntrada;
import br.com.starcode.agenda.model.Prioridade;
import br.com.starcode.agenda.model.Usuario;
import br.com.starcode.agenda.service.EntradaService;
import br.com.starcode.agenda.util.DateUtil;

@Controller
public class EntradaController {
	
	/**
	 * Injeta o serviço de entradas (camada Service) 
	 */
	@Autowired EntradaService entradaService;

	/**
	 * Mapeia a URL "/entradas", lista as entradas da agenda através do método search
	 * e recebe dois parâmetros:
	 *  - Filtro: permite filtrar as entradas da agenda por período de data e descrição
	 *  - Ordem: permite ordenar as entradas por data ou descrição 
	 */
	@RequestMapping("/entradas")
	ModelAndView buscarEntradas(
			@ModelAttribute("filtro") FiltroEntrada filtro,
			@ModelAttribute("ordem") OrdenacaoEntrada ordem,
			HttpSession sessao) {
		
		//verifica usuário
		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		//volta à página inicial se não estiver logado
		if (usuario == null) return new ModelAndView("redirect:/");
		
		List<Entrada> entradas = entradaService.search(filtro, ordem);
		return new ModelAndView("entradas")
			.addObject("entradas", entradas);
		
	}
	
	/**
	 * Mapeia a URL "/entradas?new", cria um novo objeto Entrada com a data e hora atual
	 * e a prioridade padrão NadaDeMais, depois mostra a página de edição da entrada
	 * para permitir a inclusão de uma nova entrada
	 */
	@RequestMapping(value="/entrada", params="new")
	ModelAndView cadastrarNovaEntrada(HttpSession sessao) {
		
		//verifica usuário
		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		//volta à página inicial se não estiver logado
		if (usuario == null) return new ModelAndView("redirect:/");
		
		//criar nova entrada com dados default
		Entrada entrada = new Entrada();
		entrada.setHorario(new Date());
		entrada.setPrioridade(Prioridade.NadaDeMais);
		
		//mostrar tela
		return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
		
	}

	/**
	 * Método para mostrar a tela de edição da Entrada com os dados de uma entrada.
	 * Mapeia a url "/entrada/<id-da-entrada>", isto é, o ID da entrada
	 * vai na própria URL, de acordo com o padrão REST.
	 * O método procura a entrada pelo ID que está na URL e mostra a tela de edição.
	 * Se ocorrer algum erro, ele redireciona para a tela inicial e mostra a mensagem. 
	 * O objeto RedirectAttributes permite colocar a mensagem de erro para ser exibida
	 * após o redirecionamento.
	 */
	@RequestMapping(value="/entrada/{id}")
	ModelAndView editarEntrada(
			@PathVariable("id") Integer id, 
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		
		try {
			
			//verifica usuário
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//volta à página inicial se não estiver logado
			if (usuario == null) return new ModelAndView("redirect:/");

			//busca entrada
			Entrada entrada = entradaService.findById(id);
			if (entrada == null) {
				throw new Exception("Entrada '" + id + "' não encontrada para edição!");
			}
			
			//mostra tela de edição
			return new ModelAndView("editar-entrada")
				.addObject("entrada", entrada);
			
		} catch (Exception e) {
			
			//mostra erro, se houver
			redirectAttributes.addFlashAttribute("erro", e.getMessage());      
			return new ModelAndView("redirect:/entradas");
			
		}
		
	}
	
	/**
	 * Método que recebe os dados de uma nova entrada a ser cadastrada.
	 * Recebe o objeto Entrada e a hora em separado pois o objeto Date do java
	 * contém data e hora, mas na tela essa informação foi dividida em dois campos separados.
	 * O método recupera também o usuário logado, depois ele junta a data e hora no mesmo campo e
	 * insere a nova entrada no banco de dados.
	 * Enfim, o método redireciona para a lista de entradas e mostra uma mensagem de sucesso.
	 */
	@RequestMapping(value="/entrada", method = RequestMethod.POST)
	ModelAndView confirmarNovaEntrada(
			Entrada entrada,
			@RequestParam("hora") String horario,
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		
		try {
			
			//verifica usuário
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//volta à página inicial se não estiver logado
			if (usuario == null) return new ModelAndView("redirect:/");
			
			//prepare entrada
			entrada.setIdUsuario(usuario.getId());
			entrada.setHorario(DateUtil.mergeWithHour(entrada.getHorario(), horario));
			
			//salva no banco 
			entradaService.insert(entrada);
			
			//success
			redirectAttributes.addFlashAttribute("msg", "Registro '" + entrada.getId() + "' inserido com sucesso!");
			return new ModelAndView("redirect:/entradas");
			
		} catch (Exception e) {
			
			//mostra erro, se houver
			return new ModelAndView("editar-entrada")
					.addObject("erro", e.getMessage());
			
		}
		
	}
	
	/**
	 * Método que recebe os dados da entrada sendo editada.
	 * Recebe o objeto Entrada e a hora em separado pois o objeto Date do java
	 * contém data e hora, mas na tela essa informação foi dividida em dois campos separados.
	 * O método recupera também o usuário logado, depois ele junta a data e hora no mesmo campo e
	 * atualiza a entrada no banco de dados.
	 * Enfim, o método redireciona para a lista de entradas e mostra uma mensagem de sucesso.
	 */
	@RequestMapping(value="/entrada/{id}", method = RequestMethod.POST)
	ModelAndView confirmarAtualizacaoEntrada(
			Entrada entrada,
			@RequestParam("hora") String horario,
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		
		try {
			
			//verifica usuário
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//volta à página inicial se não estiver logado
			if (usuario == null) return new ModelAndView("redirect:/");
			
			//prepare entrada
			entrada.setIdUsuario(usuario.getId());
			entrada.setHorario(DateUtil.mergeWithHour(entrada.getHorario(), horario));
			
			//update
			entradaService.update(entrada);
			
			//sucesso
			redirectAttributes.addFlashAttribute("msg", "Registro '" + entrada.getId() + "' atualizado com sucesso!"); 
			return new ModelAndView("redirect:/entradas");
			
		} catch (Exception e) {
			
			//mostra erro, se houver
			return new ModelAndView("editar-entrada")
					.addObject("erro", e.getMessage());
			
		}
		
	}
	
	/**
	 * Remove uma entrada.
	 * O método mapeia a URL contendo o ID: "/entrada/remover/<id a ser removido>".
	 * ELe então chama o método de serviço para remover e redireciona para a página inicial
	 * mostrando uma mensagem de sucesso. 
	 */
	@RequestMapping(value="/entrada/remover/{id}")
	ModelAndView removerEntrada(
			@PathVariable("id") Integer id, 
			RedirectAttributes redirectAttributes,
			HttpSession sessao) {
		
		try {
			
			//verifica usuário
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			//volta à página inicial se não estiver logado
			if (usuario == null) return new ModelAndView("redirect:/");
			
			//remove
			entradaService.delete(id);
			
			//mostra mensagem na listagem
			redirectAttributes.addFlashAttribute("msg", "Registro '" + id + "' removido com sucesso!");      
			return new ModelAndView("redirect:/entradas");
			
		} catch (Exception e) {
			
			//mostra erro, se houver
			redirectAttributes.addFlashAttribute("erro", e.getMessage());      
			return new ModelAndView("redirect:/entradas");
			
		}
		
	}
	
}
