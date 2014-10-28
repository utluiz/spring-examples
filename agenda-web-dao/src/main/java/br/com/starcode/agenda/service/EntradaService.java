package br.com.starcode.agenda.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;

@Service
public class EntradaService {

	public Entrada findById(Integer id) {
		return null;
	}
	
	public List<Entrada> search(FiltroEntrada filtro, OrdenacaoEntrada ordem) {
		return null;
	}
	
	public void insert(Entrada entrada) {
	}
	
	public int update(Entrada entrada) {
		return -1;
	}
	
	public int delete(Integer id) {
		return -1;
	}
	
	void validarEntrada(Entrada entrada) {
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
