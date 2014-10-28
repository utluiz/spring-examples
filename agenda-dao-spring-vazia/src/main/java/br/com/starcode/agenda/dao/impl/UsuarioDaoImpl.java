package br.com.starcode.agenda.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.starcode.agenda.dao.UsuarioDao;
import br.com.starcode.agenda.domain.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Usuario findByNomeUsuario(String nomeUsuario) {
		throw new UnsupportedOperationException("Não implementado");
	}

	public void atualizarUltimoAcesso(Integer id, Date data) {
		throw new UnsupportedOperationException("Não implementado");
	}

}
