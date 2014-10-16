package br.com.starcode.agenda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.starcode.agenda.domain.Usuario;
import br.com.starcode.agenda.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam("nome-usuario") String nomeUsuario,
			@RequestParam("senha") String senha,
			HttpSession sessao,
			Model model) {
		try {
			Usuario usuario = usuarioService.autenticarUsuario(nomeUsuario);
			sessao.setAttribute("usuario", usuario);
			return "redirect:/entradas";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "home";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession sessao) {
		sessao.invalidate();
		return "redirect:/";
	}
	
}
