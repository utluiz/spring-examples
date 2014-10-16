package br.com.starcode.agenda.dao.postgresqltemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.starcode.agenda.dao.EntradaDao;
import br.com.starcode.agenda.dao.mapper.EntradaRowMapper;
import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;

@Repository
@Qualifier("pg")
public class EntradaDaoPostgreSqlJdbcTemplateImpl implements EntradaDao {
	
	@Autowired
	@Qualifier("pg")
	private JdbcTemplate jdbcTemplate;
	
	public Entrada findById(int id) {
		try {
			return jdbcTemplate.queryForObject(
					"select * from entrada where id = ?", 
					new EntradaRowMapper(), 
					id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void insert(final Entrada entrada) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"insert into entrada (horario, descricao, prioridade, id_usuario) "
						+ "values (?, ?, ?, ?)", new String[] {"id"});
				ps.setTimestamp(1, new Timestamp(entrada.getHorario().getTime()));
				ps.setString(2, entrada.getDescricao());
				ps.setString(3, entrada.getPrioridadeEntrada().getCode());
				ps.setInt(4, entrada.getIdUsuario());
				return ps;
			}
		}, keyHolder);
		entrada.setId(keyHolder.getKey().intValue());
	}

	public int update(Entrada entrada) {
		return jdbcTemplate.update(
				"update entrada set horario=?, descricao=?, prioridade=? where id=?",
				entrada.getHorario(),
				entrada.getDescricao(),
				entrada.getPrioridadeEntrada().getCode(),
				entrada.getId());
	}

	public int delete(Integer id) {
		return jdbcTemplate.update("delete from entrada where id=?", id);
	}

	public List<Entrada> search(
			FiltroEntrada filtro,
			OrdenacaoEntrada ordem) {
		
		StringBuilder criterios = new StringBuilder();
		List<Object> parametros = new ArrayList<Object>();
		if (filtro.getDe() != null) {
			criterios.append(" date_trunc('day', horario) >= date_trunc('day', ?::timestamp) ");
			parametros.add(filtro.getDe());
		}
		if (filtro.getAte() != null) {
			if (!parametros.isEmpty()) criterios.append(" and ");
			criterios.append(" date_trunc('day', horario) <= date_trunc('day', ?::timestamp) ");
			parametros.add(filtro.getAte());
		}
		if (filtro.getDescricao() != null) {
			if (!parametros.isEmpty()) criterios.append(" and ");
			criterios.append(" UPPER(descricao) like CONCAT('%', ? , '%') ");
			parametros.add(filtro.getDescricao().toUpperCase());
		}
		
		String query = "select * from entrada ";
		if (!parametros.isEmpty()) {
			query += "where" + criterios.toString(); 
		}
		
		return jdbcTemplate.query(query + " order by " + ordem.getOrderBy(), 
				new EntradaRowMapper(),
				parametros.toArray());
	}
	
}
