package org.fatec.sistema.model;

import java.util.List;

public class SubEntidade {

	String[] itens;
	List<String> itensLista; 
	
	public String[] getItens() {
		return itens;
	}
	
	public List<String> getItensLista() {
		return itensLista;
	}
	
	public void setItens(String[] itens) {
		this.itens = itens;
	}

	public void setItensLista(List<String> itensLista) {
		this.itensLista = itensLista;
	}
}
