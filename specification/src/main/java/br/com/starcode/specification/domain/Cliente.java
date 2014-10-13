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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;


@Entity
public class Cliente implements Serializable {

	@Id
	@Column(name = "codcliente", insertable = false, updatable = false)
	//@Required
	public String codigo;
	
	@Column(name = "nome", insertable = false, updatable = false)
	public String nome;
	
	public Cliente() {
	}

	public Cliente(String codigo) {
		
		this.codigo = codigo;
		
	}

	public String getCodigo() {
		
		return codigo;
		
	}
	
	public String getNome() {
		
		return nome;
		
	}
	
	/**
	 * Se o nome for grande, tenta diminuir quebrando por "-" ou por espaço
	 */
	public String getNomeResumido() {
		
		if (nome.length() > 30) {
			
			String[] ns = nome.split("-");
			if (ns.length > 1) {
				
				return ns[0];
				
			} else {
				
				ns = nome.split(" ");
				return ns[0];
				
			}
			
		} else {
			
			return nome;
			
		}
		
	}

	public static Cliente get(String codigo) {
		
		return JPA.em().find(Cliente.class, codigo);
		
	}
	
	public static List<Cliente> list() {
		
		TypedQuery<Cliente> q = JPA.em().createQuery("select c from Cliente c order by nome", Cliente.class);
		return q.getResultList();
		
	}

}
