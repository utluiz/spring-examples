package br.com.starcode.agenda.dao;

import java.util.List;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;

public interface EntradaDao {

	Entrada findById(int id);

	void insert(Entrada entrada);

	int update(Entrada entrada);

	int delete(Integer id);

	List<Entrada> search(FiltroEntrada filtro, OrdenacaoEntrada ordem);

}