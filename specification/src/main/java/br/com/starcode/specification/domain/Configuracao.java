package br.com.starcode.specification.domain;

import java.io.Serializable;

public class Configuracao implements Serializable {

	public long id;
	public String caminhoAnexos;

	public Configuracao() {
	}
	
	public Configuracao(String caminhoAnexos) {
		this.caminhoAnexos = caminhoAnexos;
	}
	
	public long getId() {
		return id;
	}

	public String getCaminhoAnexos() {
		return caminhoAnexos;
	}
	
	public static Configuracao get() {
		
//		TypedQuery<Configuracao> q = JPA.em().createQuery("select c from Configuracao c where id = 0", Configuracao.class);
//		return q.getSingleResult();
		return new Configuracao(".");
		
	}
	
}
