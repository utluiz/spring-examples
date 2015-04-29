package br.com.starcode.agenda.domain;

import java.util.Date;

public class FiltroEntrada {

	private Date de;
	private Date ate;
	private String descricao;
	
	public Date getDe() {
		return de;
	}
	
	public void setDe(Date de) {
		this.de = de;
	}
	
	public Date getAte() {
		return ate;
	}
	
	public void setAte(Date ate) {
		this.ate = ate;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
