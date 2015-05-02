package br.com.starcode.agenda.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.starcode.agenda.dao.UsuarioDao;
import br.com.starcode.agenda.model.Usuario;

@Controller
public class LoginController {
	
	@Autowired UsuarioDao usuarioDao;
	
	/**
	 * Faz a autenticação do usuário.
	 * Recebe os dados (long e senha), busca no banco o usuário e
	 * então verifica se o login existe e se a senha está correta.
	 * Se ocorrer qualquer erro, volta à tela inicial com a mensagem de erro. 
	 */
	@RequestMapping(value = "/login")
	ModelAndView loginError(
			@RequestParam String nomeUsuario, 
			@RequestParam String senha,
			HttpSession sessao) {
		
		//busca usuário
		Usuario usuarioDoBanco = usuarioDao.findByNomeUsuario(nomeUsuario);
		
		//verifica usuário e senha
		if (usuarioDoBanco != null
				&& usuarioDoBanco.getSenha().equals(senha)) {
			
			usuarioDao.atualizarUltimoAcesso(usuarioDoBanco.getId(), new Date());
			
			//ok, salva o usuário na sessão e mostra entradas da agenda
			sessao.setAttribute("usuario", usuarioDoBanco);
			return new ModelAndView("redirect:/entradas");
			
		} else {
			
			//erro, mostra mensagem e fica na tela "home"
			return new ModelAndView("home")
				.addObject("erro", "Usuário ou senha inválidos");
			
		}
		
	}
	
	/**
	 * Sai do sistema. Este método invalida a sessão do usuário e
	 * redireciona para a "home"
	 */
	@RequestMapping("/logout")
	String logout(HttpSession sessao) {
		
		sessao.invalidate();
		return "redirect:/";
		
	}
	
}
