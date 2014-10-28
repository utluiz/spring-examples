package br.com.starcode.agenda.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.starcode.agenda.domain.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Usuario usuario =  new Usuario();
		usuario.setId(rs.getInt("id"));
		usuario.setNomeUsuario(rs.getString("nome_usuario"));
		usuario.setSenha(rs.getString("senha"));
		usuario.setNome(rs.getString("nome"));
		usuario.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
		return usuario;
	}

}
