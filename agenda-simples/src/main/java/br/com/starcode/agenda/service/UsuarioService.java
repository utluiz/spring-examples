package br.com.starcode.agenda.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.starcode.agenda.dao.UsuarioDao;
import br.com.starcode.agenda.domain.Usuario;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioDao usuarioDao;

	public Usuario autenticarUsuario(String nomeUsuario) {
		Usuario usuario = usuarioDao.findByNomeUsuario(nomeUsuario);
		usuarioDao.atualizarUltimoAcesso(usuario.getId(), new Date());
		return usuario;
	}
	
}
