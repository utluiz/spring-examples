package br.com.starcode.agenda.dao.postgresqltemplate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.starcode.agenda.dao.UsuarioDao;
import br.com.starcode.agenda.dao.mapper.UsuarioRowMapper;
import br.com.starcode.agenda.domain.Usuario;

@Repository
@Qualifier("pg")
public class UsuarioDaoPostgreSqlJdbcTemplateImpl implements UsuarioDao {
	
	@Autowired
	@Qualifier("pg")
	private JdbcTemplate jdbcTemplate;
	
	public Usuario findByNomeUsuario(String nomeUsuario) {
		try {
			return jdbcTemplate.queryForObject(
					"select * from usuario where nome_usuario = ?", 
					new UsuarioRowMapper(), 
					nomeUsuario);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void atualizarUltimoAcesso(Integer id, Date data) {
		jdbcTemplate.update(
				"update usuario set ultimo_acesso = ? where id = ?", data, id);
	}

}
