/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 21/03/2012 - 15:35:45
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;


@Entity
public class Configuracao implements Serializable {

	@Id
	@Column(name = "id")
	public long id;

	@Column(name = "caminho_anexos")
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
		
		TypedQuery<Configuracao> q = JPA.em().createQuery("select c from Configuracao c where id = 0", Configuracao.class);
		return q.getSingleResult();
		
	}
	
}
