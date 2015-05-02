package br.com.starcode.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.starcode.agenda.dao.EntradaDao;
import br.com.starcode.agenda.model.Entrada;
import br.com.starcode.agenda.model.FiltroEntrada;
import br.com.starcode.agenda.model.OrdenacaoEntrada;

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
		validarEntrada(entrada);
		entradaDao.insert(entrada);
	}
	
	public int update(Entrada entrada) {
		if (entrada.getId() == null) {
			throw new RuntimeException("ID deve ser informado para atualizar!");
		}
		validarEntrada(entrada);
		int qtd = entradaDao.update(entrada);
		if (qtd == 0) {
			throw new RuntimeException("Registro '" + entrada.getId() + "' não encontrado para !");
		}
		return qtd;
	}
	
	public int delete(Integer id) {
		int qtd = entradaDao.remove(id);
		if (qtd == 0) {
			throw new RuntimeException("Registro '" + id + "' não encontrado para remoção!");
		}
		return qtd;
	}
	
	private void validarEntrada(Entrada entrada) {
		if (entrada.getDescricao() == null || entrada.getDescricao().isEmpty()) {
			throw new RuntimeException("Descrição deve ser informada!");
		}
		if (entrada.getHorario() == null) {
			throw new RuntimeException("Horário deve ser informado!");
		}
		if (entrada.getIdUsuario() == null) {
			throw new RuntimeException("Usuário deve ser informado!");
		}
		if (entrada.getPrioridade() == null) {
			throw new RuntimeException("Prioridade deve ser informada!");
		}
	}
	
}
