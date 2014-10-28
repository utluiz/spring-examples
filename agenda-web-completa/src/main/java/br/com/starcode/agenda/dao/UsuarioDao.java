package br.com.starcode.agenda.dao;

import java.util.Date;

import br.com.starcode.agenda.domain.Usuario;

public interface UsuarioDao {

	Usuario findByNomeUsuario(String string);

	void atualizarUltimoAcesso(Integer id, Date data);

}