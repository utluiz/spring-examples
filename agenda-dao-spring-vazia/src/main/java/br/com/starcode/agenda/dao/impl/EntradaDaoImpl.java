package br.com.starcode.agenda.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.starcode.agenda.dao.EntradaDao;
import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;

@Repository
public class EntradaDaoImpl implements EntradaDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Entrada findById(int id) {
		throw new UnsupportedOperationException("Não implementado");
	}

	public void insert(final Entrada entrada) {
		throw new UnsupportedOperationException("Não implementado");
	}

	public int update(Entrada entrada) {
		throw new UnsupportedOperationException("Não implementado");
	}

	public int delete(Integer id) {
		throw new UnsupportedOperationException("Não implementado");
	}

	public List<Entrada> search(
			FiltroEntrada filtro,
			OrdenacaoEntrada ordem) {
		throw new UnsupportedOperationException("Não implementado");
	}
	
}
