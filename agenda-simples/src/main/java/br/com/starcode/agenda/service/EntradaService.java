package br.com.starcode.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.starcode.agenda.dao.EntradaDao;
import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;

@Service
public class EntradaService {

	@Autowired
	EntradaDao entradaDao;
	
	public Entrada findById(Integer id) {
		return entradaDao.findById(id);
	}
	
	public List<Entrada> search(FiltroEntrada filtro, OrdenacaoEntrada ordem) {
		return entradaDao.search(filtro, ordem);
	}
	
	public void insert(Entrada entrada) {
		entradaDao.insert(entrada);
	}
	
	public int update(Entrada entrada) {
		return entradaDao.update(entrada);
	}
	
	public int delete(Integer id) {
		return entradaDao.delete(id);
	}
	
}
