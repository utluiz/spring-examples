package br.com.starcode.agenda.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.starcode.agenda.util.DateUtil;

@Repository
public class EntradaDaoImpl implements EntradaDao {
	
	@Autowired
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
						+ "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setTimestamp(1, DateUtil.getTimestamp(entrada.getHorario()));
				ps.setString(2, entrada.getDescricao());
				ps.setString(3, entrada.getPrioridade().getCode());
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
				entrada.getPrioridade().getCode(),
				entrada.getId());
	}

	public int delete(Integer id) {
		return jdbcTemplate.update("delete from entrada where id=?", id);
	}

	public List<Entrada> search(
			FiltroEntrada filtro,
			OrdenacaoEntrada ordem) {
		return jdbcTemplate.query(
				"select * from entrada "
				+ "where (? is null or date(horario) >= date(?)) "
				+ "and (? is null or date(horario) <= date(?)) "
				+ "and (? is null or UPPER(descricao) like CONCAT('%', ? , '%')) "
				+ "order by " + ordem.getOrderBy(), 
				new EntradaRowMapper(),
				filtro.getDe(), 
				filtro.getDe(),
				filtro.getAte(),
				filtro.getAte(),
				filtro.getDescricao(),
				filtro.getDescricao());
	}
	
}
