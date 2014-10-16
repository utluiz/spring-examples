package br.com.starcode.agenda.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.PrioridadeEntrada;

public class EntradaRowMapper implements RowMapper<Entrada> {

	@Override
	public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
		Entrada e = new Entrada();
		e.setId(rs.getInt("id"));
		e.setHorario(rs.getTimestamp("horario"));
		e.setDescricao(rs.getString("descricao"));
		e.setPrioridadeEntrada(PrioridadeEntrada.fromCode(rs.getString("prioridade")));
		e.setIdUsuario(rs.getInt("id_usuario"));
		return e;
	}

}
